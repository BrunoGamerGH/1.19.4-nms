package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.horse.EntityHorseSkeleton;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.Horse.Variant;

public class CraftSkeletonHorse extends CraftAbstractHorse implements SkeletonHorse {
   public CraftSkeletonHorse(CraftServer server, EntityHorseSkeleton entity) {
      super(server, entity);
   }

   @Override
   public String toString() {
      return "CraftSkeletonHorse";
   }

   @Override
   public EntityType getType() {
      return EntityType.SKELETON_HORSE;
   }

   public Variant getVariant() {
      return Variant.SKELETON_HORSE;
   }

   public EntityHorseSkeleton getHandle() {
      return (EntityHorseSkeleton)this.entity;
   }

   public boolean isTrapped() {
      return this.getHandle().r();
   }

   public void setTrapped(boolean trapped) {
      this.getHandle().w(trapped);
   }

   public int getTrapTime() {
      return this.getHandle().bW;
   }

   public void setTrapTime(int trapTime) {
      this.getHandle().bW = trapTime;
   }
}
