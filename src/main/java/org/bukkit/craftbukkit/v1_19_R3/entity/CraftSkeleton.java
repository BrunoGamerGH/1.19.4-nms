package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.monster.EntitySkeleton;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;

public class CraftSkeleton extends CraftAbstractSkeleton implements Skeleton {
   public CraftSkeleton(CraftServer server, EntitySkeleton entity) {
      super(server, entity);
   }

   public boolean isConverting() {
      return this.getHandle().fT();
   }

   public int getConversionTime() {
      Preconditions.checkState(this.isConverting(), "Entity is not converting");
      return this.getHandle().bS;
   }

   public void setConversionTime(int time) {
      if (time < 0) {
         this.getHandle().bS = -1;
         this.getHandle().aj().b(EntitySkeleton.d, false);
      } else {
         this.getHandle().b(time);
      }
   }

   public EntitySkeleton getHandle() {
      return (EntitySkeleton)this.entity;
   }

   @Override
   public String toString() {
      return "CraftSkeleton";
   }

   @Override
   public EntityType getType() {
      return EntityType.SKELETON;
   }

   public SkeletonType getSkeletonType() {
      return SkeletonType.NORMAL;
   }
}
