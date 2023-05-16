package net.minecraft.world.level.block.entity;

import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tags.TagsItem;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.slf4j.Logger;

public class ChiseledBookShelfBlockEntity extends TileEntity implements IInventory {
   public static final int c = 6;
   private static final Logger d = LogUtils.getLogger();
   private final NonNullList<ItemStack> e;
   public int f;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 1;

   @Override
   public List<ItemStack> getContents() {
      return this.e;
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
   public void setMaxStackSize(int size) {
      this.maxStack = size;
   }

   @Override
   public Location getLocation() {
      return this.o == null ? null : new Location(this.o.getWorld(), (double)this.p.u(), (double)this.p.v(), (double)this.p.w());
   }

   public ChiseledBookShelfBlockEntity(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.L, blockposition, iblockdata);
      this.e = NonNullList.a(6, ItemStack.b);
      this.f = -1;
   }

   private void c(int i) {
      if (i >= 0 && i < 6) {
         this.f = i;
         IBlockData iblockdata = this.q();

         for(int j = 0; j < ChiseledBookShelfBlock.b.size(); ++j) {
            boolean flag = !this.a(j).b();
            BlockStateBoolean blockstateboolean = ChiseledBookShelfBlock.b.get(j);
            iblockdata = iblockdata.a(blockstateboolean, Boolean.valueOf(flag));
         }

         Objects.requireNonNull(this.o).a(this.p, iblockdata, 3);
      } else {
         d.error("Expected slot 0-5, got {}", i);
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      this.e.clear();
      ContainerUtil.b(nbttagcompound, this.e);
      this.f = nbttagcompound.h("last_interacted_slot");
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      ContainerUtil.a(nbttagcompound, this.e, true);
      nbttagcompound.a("last_interacted_slot", this.f);
   }

   public int f() {
      return (int)this.e.stream().filter(Predicate.not(ItemStack::b)).count();
   }

   @Override
   public void a() {
      this.e.clear();
   }

   @Override
   public int b() {
      return 6;
   }

   @Override
   public boolean aa_() {
      return this.e.stream().allMatch(ItemStack::b);
   }

   @Override
   public ItemStack a(int i) {
      return this.e.get(i);
   }

   @Override
   public ItemStack a(int i, int j) {
      ItemStack itemstack = Objects.requireNonNullElse(this.e.get(i), ItemStack.b);
      this.e.set(i, ItemStack.b);
      if (!itemstack.b()) {
         this.c(i);
      }

      return itemstack;
   }

   @Override
   public ItemStack b(int i) {
      return this.a(i, 1);
   }

   @Override
   public void a(int i, ItemStack itemstack) {
      if (itemstack.a(TagsItem.au)) {
         this.e.set(i, itemstack);
         this.c(i);
      }
   }

   @Override
   public int ab_() {
      return this.maxStack;
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return IInventory.a(this, entityhuman);
   }

   @Override
   public boolean b(int i, ItemStack itemstack) {
      return itemstack.a(TagsItem.au) && this.a(i).b();
   }

   public int g() {
      return this.f;
   }
}
