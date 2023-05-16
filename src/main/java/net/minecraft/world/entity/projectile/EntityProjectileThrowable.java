package net.minecraft.world.entity.projectile;

import net.minecraft.SystemUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;

public abstract class EntityProjectileThrowable extends EntityProjectile implements ItemSupplier {
   private static final DataWatcherObject<ItemStack> b = DataWatcher.a(EntityProjectileThrowable.class, DataWatcherRegistry.h);

   public EntityProjectileThrowable(EntityTypes<? extends EntityProjectileThrowable> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityProjectileThrowable(EntityTypes<? extends EntityProjectileThrowable> entitytypes, double d0, double d1, double d2, World world) {
      super(entitytypes, d0, d1, d2, world);
   }

   public EntityProjectileThrowable(EntityTypes<? extends EntityProjectileThrowable> entitytypes, EntityLiving entityliving, World world) {
      super(entitytypes, entityliving, world);
   }

   public void a(ItemStack itemstack) {
      if (!itemstack.a(this.j()) || itemstack.t()) {
         this.aj().b(b, SystemUtils.a(itemstack.o(), itemstack1 -> {
            if (!itemstack1.b()) {
               itemstack1.f(1);
            }
         }));
      }
   }

   protected abstract Item j();

   public Item getDefaultItemPublic() {
      return this.j();
   }

   public ItemStack k() {
      return this.aj().a(b);
   }

   @Override
   public ItemStack i() {
      ItemStack itemstack = this.k();
      return itemstack.b() ? new ItemStack(this.j()) : itemstack;
   }

   @Override
   protected void a_() {
      this.aj().a(b, ItemStack.b);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      ItemStack itemstack = this.k();
      if (!itemstack.b()) {
         nbttagcompound.a("Item", itemstack.b(new NBTTagCompound()));
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      ItemStack itemstack = ItemStack.a(nbttagcompound.p("Item"));
      this.a(itemstack);
   }
}
