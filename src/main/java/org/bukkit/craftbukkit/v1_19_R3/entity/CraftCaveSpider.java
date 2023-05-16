package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityCaveSpider;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.EntityType;

public class CraftCaveSpider extends CraftSpider implements CaveSpider {
   public CraftCaveSpider(CraftServer server, EntityCaveSpider entity) {
      super(server, entity);
   }

   public EntityCaveSpider getHandle() {
      return (EntityCaveSpider)this.entity;
   }

   @Override
   public String toString() {
      return "CraftCaveSpider";
   }

   @Override
   public EntityType getType() {
      return EntityType.CAVE_SPIDER;
   }
}
