package net.minecraft.world.entity.projectile;

import net.minecraft.SystemUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;

public abstract class EntityFireballFireball extends EntityFireball implements ItemSupplier {
   private static final DataWatcherObject<ItemStack> e = DataWatcher.a(EntityFireballFireball.class, DataWatcherRegistry.h);

   public EntityFireballFireball(EntityTypes<? extends EntityFireballFireball> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityFireballFireball(
      EntityTypes<? extends EntityFireballFireball> entitytypes, double d0, double d1, double d2, double d3, double d4, double d5, World world
   ) {
      super(entitytypes, d0, d1, d2, d3, d4, d5, world);
   }

   public EntityFireballFireball(
      EntityTypes<? extends EntityFireballFireball> entitytypes, EntityLiving entityliving, double d0, double d1, double d2, World world
   ) {
      super(entitytypes, entityliving, d0, d1, d2, world);
   }

   public void a(ItemStack itemstack) {
      if (!itemstack.a(Items.tb) || itemstack.t()) {
         this.aj().b(e, SystemUtils.a(itemstack.o(), itemstack1 -> itemstack1.f(1)));
      }
   }

   public ItemStack o() {
      return this.aj().a(e);
   }

   @Override
   public ItemStack i() {
      ItemStack itemstack = this.o();
      return itemstack.b() ? new ItemStack(Items.tb) : itemstack;
   }

   @Override
   protected void a_() {
      this.aj().a(e, ItemStack.b);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      ItemStack itemstack = this.o();
      if (!itemstack.b()) {
         nbttagcompound.a("Item", itemstack.b(new NBTTagCompound()));
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      ItemStack itemstack = ItemStack.a(nbttagcompound.p("Item"));
      if (!itemstack.b()) {
         this.a(itemstack);
      }
   }
}
