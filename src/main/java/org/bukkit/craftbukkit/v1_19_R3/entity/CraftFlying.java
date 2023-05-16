package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.EntityFlying;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Flying;

public class CraftFlying extends CraftMob implements Flying {
   public CraftFlying(CraftServer server, EntityFlying entity) {
      super(server, entity);
   }

   public EntityFlying getHandle() {
      return (EntityFlying)this.entity;
   }

   @Override
   public String toString() {
      return "CraftFlying";
   }
}
