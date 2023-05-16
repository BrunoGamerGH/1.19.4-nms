package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.EntityLightning;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LightningStrike.Spigot;

public class CraftLightningStrike extends CraftEntity implements LightningStrike {
   private final Spigot spigot = new Spigot() {
      public boolean isSilent() {
         return CraftLightningStrike.this.getHandle().isSilent;
      }
   };

   public CraftLightningStrike(CraftServer server, EntityLightning entity) {
      super(server, entity);
   }

   public boolean isEffect() {
      return this.getHandle().h;
   }

   public EntityLightning getHandle() {
      return (EntityLightning)this.entity;
   }

   @Override
   public String toString() {
      return "CraftLightningStrike";
   }

   public EntityType getType() {
      return EntityType.LIGHTNING;
   }

   public Spigot spigot() {
      return this.spigot;
   }
}
