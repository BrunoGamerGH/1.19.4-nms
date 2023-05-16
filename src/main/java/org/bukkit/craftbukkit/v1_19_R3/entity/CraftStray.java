package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntitySkeletonStray;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Stray;
import org.bukkit.entity.Skeleton.SkeletonType;

public class CraftStray extends CraftAbstractSkeleton implements Stray {
   public CraftStray(CraftServer server, EntitySkeletonStray entity) {
      super(server, entity);
   }

   @Override
   public String toString() {
      return "CraftStray";
   }

   @Override
   public EntityType getType() {
      return EntityType.STRAY;
   }

   public SkeletonType getSkeletonType() {
      return SkeletonType.STRAY;
   }
}
