package net.minecraft.world.level.block.entity;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.IInventory;
import net.minecraft.world.InventoryLargeChest;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerChest;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockChest;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyChestType;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;

public class TileEntityChest extends TileEntityLootable implements LidBlockEntity {
   private static final int c = 1;
   private NonNullList<ItemStack> f;
   public final ContainerOpenersCounter g;
   private final ChestLidController j;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;

   @Override
   public List<ItemStack> getContents() {
      return this.f;
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

   protected TileEntityChest(TileEntityTypes<?> tileentitytypes, BlockPosition blockposition, IBlockData iblockdata) {
      super(tileentitytypes, blockposition, iblockdata);
      this.f = NonNullList.a(27, ItemStack.b);
      this.g = new ContainerOpenersCounter() {
         @Override
         protected void a(World world, BlockPosition blockposition1, IBlockData iblockdata1) {
            TileEntityChest.a(world, blockposition1, iblockdata1, SoundEffects.ef);
         }

         @Override
         protected void b(World world, BlockPosition blockposition1, IBlockData iblockdata1) {
            TileEntityChest.a(world, blockposition1, iblockdata1, SoundEffects.ed);
         }

         @Override
         protected void a(World world, BlockPosition blockposition1, IBlockData iblockdata1, int i, int j) {
            TileEntityChest.this.a(world, blockposition1, iblockdata1, i, j);
         }

         @Override
         protected boolean a(EntityHuman entityhuman) {
            if (!(entityhuman.bP instanceof ContainerChest)) {
               return false;
            } else {
               IInventory iinventory = ((ContainerChest)entityhuman.bP).l();
               return iinventory == TileEntityChest.this
                  || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).a(TileEntityChest.this);
            }
         }
      };
      this.j = new ChestLidController();
   }

   public TileEntityChest(BlockPosition blockposition, IBlockData iblockdata) {
      this(TileEntityTypes.b, blockposition, iblockdata);
   }

   @Override
   public int b() {
      return 27;
   }

   @Override
   protected IChatBaseComponent g() {
      return IChatBaseComponent.c("container.chest");
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.f = NonNullList.a(this.b(), ItemStack.b);
      if (!this.d(nbttagcompound)) {
         ContainerUtil.b(nbttagcompound, this.f);
      }
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (!this.e(nbttagcompound)) {
         ContainerUtil.a(nbttagcompound, this.f);
      }
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityChest tileentitychest) {
      tileentitychest.j.a();
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, SoundEffect soundeffect) {
      BlockPropertyChestType blockpropertychesttype = iblockdata.c(BlockChest.c);
      if (blockpropertychesttype != BlockPropertyChestType.b) {
         double d0 = (double)blockposition.u() + 0.5;
         double d1 = (double)blockposition.v() + 0.5;
         double d2 = (double)blockposition.w() + 0.5;
         if (blockpropertychesttype == BlockPropertyChestType.c) {
            EnumDirection enumdirection = BlockChest.h(iblockdata);
            d0 += (double)enumdirection.j() * 0.5;
            d2 += (double)enumdirection.l() * 0.5;
         }

         world.a(null, d0, d1, d2, soundeffect, SoundCategory.e, 0.5F, world.z.i() * 0.1F + 0.9F);
      }
   }

   @Override
   public boolean a_(int i, int j) {
      if (i == 1) {
         this.j.a(j > 0);
         return true;
      } else {
         return super.a_(i, j);
      }
   }

   @Override
   public void d_(EntityHuman entityhuman) {
      if (!this.q && !entityhuman.F_()) {
         this.g.a(entityhuman, this.k(), this.p(), this.q());
      }
   }

   @Override
   public void c(EntityHuman entityhuman) {
      if (!this.q && !entityhuman.F_()) {
         this.g.b(entityhuman, this.k(), this.p(), this.q());
      }
   }

   @Override
   protected NonNullList<ItemStack> f() {
      return this.f;
   }

   @Override
   protected void a(NonNullList<ItemStack> nonnulllist) {
      this.f = nonnulllist;
   }

   @Override
   public float a(float f) {
      return this.j.a(f);
   }

   public static int a(IBlockAccess iblockaccess, BlockPosition blockposition) {
      IBlockData iblockdata = iblockaccess.a_(blockposition);
      if (iblockdata.q()) {
         TileEntity tileentity = iblockaccess.c_(blockposition);
         if (tileentity instanceof TileEntityChest) {
            return ((TileEntityChest)tileentity).g.a();
         }
      }

      return 0;
   }

   public static void a(TileEntityChest tileentitychest, TileEntityChest tileentitychest1) {
      NonNullList<ItemStack> nonnulllist = tileentitychest.f();
      tileentitychest.a(tileentitychest1.f());
      tileentitychest1.a(nonnulllist);
   }

   @Override
   protected Container a(int i, PlayerInventory playerinventory) {
      return ContainerChest.a(i, playerinventory, this);
   }

   public void i() {
      if (!this.q) {
         this.g.c(this.k(), this.p(), this.q());
      }
   }

   protected void a(World world, BlockPosition blockposition, IBlockData iblockdata, int i, int j) {
      Block block = iblockdata.b();
      world.a(blockposition, block, 1, j);
   }

   @Override
   public boolean t() {
      return true;
   }
}
