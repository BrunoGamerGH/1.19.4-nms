package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntitySkeletonAbstract;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.Skeleton.SkeletonType;

public abstract class CraftAbstractSkeleton extends CraftMonster implements AbstractSkeleton {
   public CraftAbstractSkeleton(CraftServer server, EntitySkeletonAbstract entity) {
      super(server, entity);
   }

   public void setSkeletonType(SkeletonType type) {
      throw new UnsupportedOperationException("Not supported.");
   }
}
