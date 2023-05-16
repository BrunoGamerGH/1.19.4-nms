package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntitySpider;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;

public class CraftSpider extends CraftMonster implements Spider {
   public CraftSpider(CraftServer server, EntitySpider entity) {
      super(server, entity);
   }

   public EntitySpider getHandle() {
      return (EntitySpider)this.entity;
   }

   @Override
   public String toString() {
      return "CraftSpider";
   }

   @Override
   public EntityType getType() {
      return EntityType.SPIDER;
   }
}
