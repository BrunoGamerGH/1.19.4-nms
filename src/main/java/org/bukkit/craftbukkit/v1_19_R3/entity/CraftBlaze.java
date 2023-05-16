package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityBlaze;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EntityType;

public class CraftBlaze extends CraftMonster implements Blaze {
   public CraftBlaze(CraftServer server, EntityBlaze entity) {
      super(server, entity);
   }

   public EntityBlaze getHandle() {
      return (EntityBlaze)this.entity;
   }

   @Override
   public String toString() {
      return "CraftBlaze";
   }

   @Override
   public EntityType getType() {
      return EntityType.BLAZE;
   }
}
