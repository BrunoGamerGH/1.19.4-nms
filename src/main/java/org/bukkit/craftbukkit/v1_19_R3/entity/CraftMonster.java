package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityMonster;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Monster;

public class CraftMonster extends CraftCreature implements Monster, CraftEnemy {
   public CraftMonster(CraftServer server, EntityMonster entity) {
      super(server, entity);
   }

   public EntityMonster getHandle() {
      return (EntityMonster)this.entity;
   }

   @Override
   public String toString() {
      return "CraftMonster";
   }
}
