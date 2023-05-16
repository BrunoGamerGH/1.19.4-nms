package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.boss.EntityComplexPart;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.Entity;

public class CraftEnderDragonPart extends CraftComplexPart implements EnderDragonPart {
   public CraftEnderDragonPart(CraftServer server, EntityComplexPart entity) {
      super(server, entity);
   }

   public EnderDragon getParent() {
      return (EnderDragon)super.getParent();
   }

   @Override
   public EntityComplexPart getHandle() {
      return (EntityComplexPart)this.entity;
   }

   @Override
   public String toString() {
      return "CraftEnderDragonPart";
   }

   public void damage(double amount) {
      this.getParent().damage(amount);
   }

   public void damage(double amount, Entity source) {
      this.getParent().damage(amount, source);
   }

   public double getHealth() {
      return this.getParent().getHealth();
   }

   public void setHealth(double health) {
      this.getParent().setHealth(health);
   }

   public double getAbsorptionAmount() {
      return this.getParent().getAbsorptionAmount();
   }

   public void setAbsorptionAmount(double amount) {
      this.getParent().setAbsorptionAmount(amount);
   }

   public double getMaxHealth() {
      return this.getParent().getMaxHealth();
   }

   public void setMaxHealth(double health) {
      this.getParent().setMaxHealth(health);
   }

   public void resetMaxHealth() {
      this.getParent().resetMaxHealth();
   }
}
