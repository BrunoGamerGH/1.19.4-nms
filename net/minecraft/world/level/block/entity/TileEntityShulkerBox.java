package net.minecraft.world.level.block.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.IWorldInventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.monster.EntityShulker;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerShulkerBox;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockShulkerBox;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;

public class TileEntityShulkerBox extends TileEntityLootable implements IWorldInventory {
   public static final int c = 9;
   public static final int f = 3;
   public static final int g = 27;
   public static final int j = 1;
   public static final int k = 10;
   public static final float l = 0.5F;
   public static final float m = 270.0F;
   public static final String n = "Items";
   private static final int[] r = IntStream.range(0, 27).toArray();
   private NonNullList<ItemStack> s;
   public int t;
   private TileEntityShulkerBox.AnimationPhase u;
   private float v;
   private float w;
   @Nullable
   private final EnumColor x;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;
   public boolean opened;

   @Override
   public List<ItemStack> getContents() {
      return this.s;
   }

   @Override
   public void onOpen(CraftHumanEntity who) {
      this.transaction.add(who);
   }

   @Override
   public void onClose(CraftHumanEntity who) {
      this.transaction.remove(who);
   }

   @Override
   public List<HumanEntity> getViewers() {
      return this.transaction;
   }

   @Override
   public int ab_() {
      return this.maxStack;
   }

   @Override
   public void setMaxStackSize(int size) {
      this.maxStack = size;
   }

   public TileEntityShulkerBox(@Nullable EnumColor enumcolor, BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.x, blockposition, iblockdata);
      this.s = NonNullList.a(27, ItemStack.b);
      this.u = TileEntityShulkerBox.AnimationPhase.a;
      this.x = enumcolor;
   }

   public TileEntityShulkerBox(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.x, blockposition, iblockdata);
      this.s = NonNullList.a(27, ItemStack.b);
      this.u = TileEntityShulkerBox.AnimationPhase.a;
      this.x = BlockShulkerBox.a(iblockdata.b());
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityShulkerBox tileentityshulkerbox) {
      tileentityshulkerbox.b(world, blockposition, iblockdata);
   }

   private void b(World world, BlockPosition blockposition, IBlockData iblockdata) {
      this.w = this.v;
      switch(this.u) {
         case a:
            this.v = 0.0F;
            break;
         case b:
            this.v += 0.1F;
            if (this.v >= 1.0F) {
               this.u = TileEntityShulkerBox.AnimationPhase.c;
               this.v = 1.0F;
               d(world, blockposition, iblockdata);
            }

            this.c(world, blockposition, iblockdata);
            break;
         case c:
            this.v = 1.0F;
            break;
         case d:
            this.v -= 0.1F;
            if (this.v <= 0.0F) {
               this.u = TileEntityShulkerBox.AnimationPhase.a;
               this.v = 0.0F;
               d(world, blockposition, iblockdata);
            }
      }
   }

   public TileEntityShulkerBox.AnimationPhase i() {
      return this.u;
   }

   public AxisAlignedBB a(IBlockData iblockdata) {
      return EntityShulker.a(iblockdata.c(BlockShulkerBox.a), 0.5F * this.a(1.0F));
   }

   private void c(World world, BlockPosition blockposition, IBlockData iblockdata) {
      if (iblockdata.b() instanceof BlockShulkerBox) {
         EnumDirection enumdirection = iblockdata.c(BlockShulkerBox.a);
         AxisAlignedBB axisalignedbb = EntityShulker.a(enumdirection, this.w, this.v).a(blockposition);
         List<Entity> list = world.a_(null, axisalignedbb);
         if (!list.isEmpty()) {
            for(int i = 0; i < list.size(); ++i) {
               Entity entity = list.get(i);
               if (entity.C_() != EnumPistonReaction.d) {
                  entity.a(
                     EnumMoveType.d,
                     new Vec3D(
                        (axisalignedbb.b() + 0.01) * (double)enumdirection.j(),
                        (axisalignedbb.c() + 0.01) * (double)enumdirection.k(),
                        (axisalignedbb.d() + 0.01) * (double)enumdirection.l()
                     )
                  );
               }
            }
         }
      }
   }

   @Override
   public int b() {
      return this.s.size();
   }

   @Override
   public boolean a_(int i, int j) {
      if (i == 1) {
         this.t = j;
         if (j == 0) {
            this.u = TileEntityShulkerBox.AnimationPhase.d;
            d(this.k(), this.p, this.q());
         }

         if (j == 1) {
            this.u = TileEntityShulkerBox.AnimationPhase.b;
            d(this.k(), this.p, this.q());
         }

         return true;
      } else {
         return super.a_(i, j);
      }
   }

   private static void d(World world, BlockPosition blockposition, IBlockData iblockdata) {
      iblockdata.a(world, blockposition, 3);
   }

   @Override
   public void d_(EntityHuman entityhuman) {
      if (!this.q && !entityhuman.F_()) {
         if (this.t < 0) {
            this.t = 0;
         }

         ++this.t;
         if (this.opened) {
            return;
         }

         this.o.a(this.p, this.q().b(), 1, this.t);
         if (this.t == 1) {
            this.o.a(entityhuman, GameEvent.k, this.p);
            this.o.a(null, this.p, SoundEffects.uK, SoundCategory.e, 0.5F, this.o.z.i() * 0.1F + 0.9F);
         }
      }
   }

   @Override
   public void c(EntityHuman entityhuman) {
      if (!this.q && !entityhuman.F_()) {
         --this.t;
         if (this.opened) {
            return;
         }

         this.o.a(this.p, this.q().b(), 1, this.t);
         if (this.t <= 0) {
            this.o.a(entityhuman, GameEvent.j, this.p);
            this.o.a(null, this.p, SoundEffects.uJ, SoundCategory.e, 0.5F, this.o.z.i() * 0.1F + 0.9F);
         }
      }
   }

   @Override
   protected IChatBaseComponent g() {
      return IChatBaseComponent.c("container.shulkerBox");
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.f(nbttagcompound);
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (!this.e(nbttagcompound)) {
         ContainerUtil.a(nbttagcompound, this.s, false);
      }
   }

   public void f(NBTTagCompound nbttagcompound) {
      this.s = NonNullList.a(this.b(), ItemStack.b);
      if (!this.d(nbttagcompound) && nbttagcompound.b("Items", 9)) {
         ContainerUtil.b(nbttagcompound, this.s);
      }
   }

   @Override
   protected NonNullList<ItemStack> f() {
      return this.s;
   }

   @Override
   protected void a(NonNullList<ItemStack> nonnulllist) {
      this.s = nonnulllist;
   }

   @Override
   public int[] a(EnumDirection enumdirection) {
      return r;
   }

   @Override
   public boolean a(int i, ItemStack itemstack, @Nullable EnumDirection enumdirection) {
      return !(Block.a(itemstack.c()) instanceof BlockShulkerBox);
   }

   @Override
   public boolean b(int i, ItemStack itemstack, EnumDirection enumdirection) {
      return true;
   }

   public float a(float f) {
      return MathHelper.i(f, this.w, this.v);
   }

   @Nullable
   public EnumColor j() {
      return this.x;
   }

   @Override
   protected Container a(int i, PlayerInventory playerinventory) {
      return new ContainerShulkerBox(i, playerinventory, this);
   }

   public boolean v() {
      return this.u == TileEntityShulkerBox.AnimationPhase.a;
   }

   public static enum AnimationPhase {
      a,
      b,
      c,
      d;
   }
}
