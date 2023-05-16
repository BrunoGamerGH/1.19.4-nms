package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.EntityPose;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Camel;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse.Variant;

public class CraftCamel extends CraftAbstractHorse implements Camel {
   public CraftCamel(CraftServer server, net.minecraft.world.entity.animal.camel.Camel entity) {
      super(server, entity);
   }

   public net.minecraft.world.entity.animal.camel.Camel getHandle() {
      return (net.minecraft.world.entity.animal.camel.Camel)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftCamel";
   }

   @Override
   public EntityType getType() {
      return EntityType.CAMEL;
   }

   public Variant getVariant() {
      return Variant.CAMEL;
   }

   public boolean isDashing() {
      return this.getHandle().w();
   }

   public void setDashing(boolean dashing) {
      this.getHandle().w(dashing);
   }

   public boolean isSitting() {
      return this.getHandle().al() == EntityPose.k;
   }

   public void setSitting(boolean sitting) {
      if (sitting) {
         this.getHandle().gd();
      } else {
         this.getHandle().ge();
      }
   }
}
