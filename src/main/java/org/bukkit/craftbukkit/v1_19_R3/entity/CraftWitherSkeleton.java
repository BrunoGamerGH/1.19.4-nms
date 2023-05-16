package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntitySkeletonWither;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Skeleton.SkeletonType;

public class CraftWitherSkeleton extends CraftAbstractSkeleton implements WitherSkeleton {
   public CraftWitherSkeleton(CraftServer server, EntitySkeletonWither entity) {
      super(server, entity);
   }

   @Override
   public String toString() {
      return "CraftWitherSkeleton";
   }

   @Override
   public EntityType getType() {
      return EntityType.WITHER_SKELETON;
   }

   public SkeletonType getSkeletonType() {
      return SkeletonType.WITHER;
   }
}
