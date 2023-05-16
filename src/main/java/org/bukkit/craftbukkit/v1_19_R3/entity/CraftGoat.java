package org.bukkit.craftbukkit.v1_19_R3.entity;

import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Goat;

public class CraftGoat extends CraftAnimals implements Goat {
   public CraftGoat(CraftServer server, net.minecraft.world.entity.animal.goat.Goat entity) {
      super(server, entity);
   }

   public net.minecraft.world.entity.animal.goat.Goat getHandle() {
      return (net.minecraft.world.entity.animal.goat.Goat)super.getHandle();
   }

   @Override
   public EntityType getType() {
      return EntityType.GOAT;
   }

   @Override
   public String toString() {
      return "CraftGoat";
   }

   public boolean hasLeftHorn() {
      return this.getHandle().fS();
   }

   public void setLeftHorn(boolean hasHorn) {
      this.getHandle().aj().b(net.minecraft.world.entity.animal.goat.Goat.cc, hasHorn);
   }

   public boolean hasRightHorn() {
      return this.getHandle().fY();
   }

   public void setRightHorn(boolean hasHorn) {
      this.getHandle().aj().b(net.minecraft.world.entity.animal.goat.Goat.cd, hasHorn);
   }

   public boolean isScreaming() {
      return this.getHandle().gc();
   }

   public void setScreaming(boolean screaming) {
      this.getHandle().w(screaming);
   }
}
