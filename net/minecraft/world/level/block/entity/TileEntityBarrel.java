package net.minecraft.world.level.block.entity;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerChest;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockBarrel;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;

public class TileEntityBarrel extends TileEntityLootable {
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;
   private NonNullList<ItemStack> c = NonNullList.a(27, ItemStack.b);
   public final ContainerOpenersCounter f = new ContainerOpenersCounter() {
      @Override
      protected void a(World world, BlockPosition blockposition1, IBlockData iblockdata1) {
         TileEntityBarrel.this.a(iblockdata1, SoundEffects.bj);
         TileEntityBarrel.this.a(iblockdata1, true);
      }

      @Override
      protected void b(World world, BlockPosition blockposition1, IBlockData iblockdata1) {
         TileEntityBarrel.this.a(iblockdata1, SoundEffects.bi);
         TileEntityBarrel.this.a(iblockdata1, false);
      }

      @Override
      protected void a(World world, BlockPosition blockposition1, IBlockData iblockdata1, int i, int j) {
      }

      @Override
      protected boolean a(EntityHuman entityhuman) {
         if (entityhuman.bP instanceof ContainerChest) {
            IInventory iinventory = ((ContainerChest)entityhuman.bP).l();
            return iinventory == TileEntityBarrel.this;
         } else {
            return false;
         }
      }
   };

   @Override
   public List<ItemStack> getContents() {
      return this.c;
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
   public void setMaxStackSize(int i) {
      this.maxStack = i;
   }

   public TileEntityBarrel(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.A, blockposition, iblockdata);
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (!this.e(nbttagcompound)) {
         ContainerUtil.a(nbttagcompound, this.c);
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.c = NonNullList.a(this.b(), ItemStack.b);
      if (!this.d(nbttagcompound)) {
         ContainerUtil.b(nbttagcompound, this.c);
      }
   }

   @Override
   public int b() {
      return 27;
   }

   @Override
   protected NonNullList<ItemStack> f() {
      return this.c;
   }

   @Override
   protected void a(NonNullList<ItemStack> nonnulllist) {
      this.c = nonnulllist;
   }

   @Override
   protected IChatBaseComponent g() {
      return IChatBaseComponent.c("container.barrel");
   }

   @Override
   protected Container a(int i, PlayerInventory playerinventory) {
      return ContainerChest.a(i, playerinventory, this);
   }

   @Override
   public void d_(EntityHuman entityhuman) {
      if (!this.q && !entityhuman.F_()) {
         this.f.a(entityhuman, this.k(), this.p(), this.q());
      }
   }

   @Override
   public void c(EntityHuman entityhuman) {
      if (!this.q && !entityhuman.F_()) {
         this.f.b(entityhuman, this.k(), this.p(), this.q());
      }
   }

   public void i() {
      if (!this.q) {
         this.f.c(this.k(), this.p(), this.q());
      }
   }

   public void a(IBlockData iblockdata, boolean flag) {
      this.o.a(this.p(), iblockdata.a(BlockBarrel.b, Boolean.valueOf(flag)), 3);
   }

   public void a(IBlockData iblockdata, SoundEffect soundeffect) {
      BaseBlockPosition baseblockposition = iblockdata.c(BlockBarrel.a).q();
      double d0 = (double)this.p.u() + 0.5 + (double)baseblockposition.u() / 2.0;
      double d1 = (double)this.p.v() + 0.5 + (double)baseblockposition.v() / 2.0;
      double d2 = (double)this.p.w() + 0.5 + (double)baseblockposition.w() / 2.0;
      this.o.a(null, d0, d1, d2, soundeffect, SoundCategory.e, 0.5F, this.o.z.i() * 0.1F + 0.9F);
   }
}
