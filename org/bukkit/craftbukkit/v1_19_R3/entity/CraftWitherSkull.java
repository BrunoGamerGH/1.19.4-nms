package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.projectile.EntityWitherSkull;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;

public class CraftWitherSkull extends CraftFireball implements WitherSkull {
   public CraftWitherSkull(CraftServer server, EntityWitherSkull entity) {
      super(server, entity);
   }

   public void setCharged(boolean charged) {
      this.getHandle().a(charged);
   }

   public boolean isCharged() {
      return this.getHandle().o();
   }

   public EntityWitherSkull getHandle() {
      return (EntityWitherSkull)this.entity;
   }

   @Override
   public String toString() {
      return "CraftWitherSkull";
   }

   @Override
   public EntityType getType() {
      return EntityType.WITHER_SKULL;
   }
}
