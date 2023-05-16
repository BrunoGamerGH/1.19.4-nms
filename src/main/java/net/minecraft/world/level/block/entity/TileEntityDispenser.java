package net.minecraft.world.level.block.entity;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerDispenser;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;

public class TileEntityDispenser extends TileEntityLootable {
   public static final int c = 9;
   private NonNullList<ItemStack> f;
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

   protected TileEntityDispenser(TileEntityTypes<?> tileentitytypes, BlockPosition blockposition, IBlockData iblockdata) {
      super(tileentitytypes, blockposition, iblockdata);
      this.f = NonNullList.a(9, ItemStack.b);
   }

   public TileEntityDispenser(BlockPosition blockposition, IBlockData iblockdata) {
      this(TileEntityTypes.f, blockposition, iblockdata);
   }

   @Override
   public int b() {
      return 9;
   }

   public int a(RandomSource randomsource) {
      this.e(null);
      int i = -1;
      int j = 1;

      for(int k = 0; k < this.f.size(); ++k) {
         if (!this.f.get(k).b() && randomsource.a(j++) == 0) {
            i = k;
         }
      }

      return i;
   }

   public int a(ItemStack itemstack) {
      for(int i = 0; i < this.f.size(); ++i) {
         if (this.f.get(i).b()) {
            this.a(i, itemstack);
            return i;
         }
      }

      return -1;
   }

   @Override
   protected IChatBaseComponent g() {
      return IChatBaseComponent.c("container.dispenser");
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

   @Override
   protected NonNullList<ItemStack> f() {
      return this.f;
   }

   @Override
   protected void a(NonNullList<ItemStack> nonnulllist) {
      this.f = nonnulllist;
   }

   @Override
   protected Container a(int i, PlayerInventory playerinventory) {
      return new ContainerDispenser(i, playerinventory, this);
   }
}
