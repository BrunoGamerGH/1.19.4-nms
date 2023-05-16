package org.bukkit.craftbukkit.v1_19_R3.entity;

import java.util.UUID;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.animal.horse.EntityHorseAbstract;
import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryAbstractHorse;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.inventory.AbstractHorseInventory;

public abstract class CraftAbstractHorse extends CraftAnimals implements AbstractHorse {
   public CraftAbstractHorse(CraftServer server, EntityHorseAbstract entity) {
      super(server, entity);
   }

   public EntityHorseAbstract getHandle() {
      return (EntityHorseAbstract)this.entity;
   }

   public void setVariant(Variant variant) {
      throw new UnsupportedOperationException("Not supported.");
   }

   public int getDomestication() {
      return this.getHandle().gn();
   }

   public void setDomestication(int value) {
      Validate.isTrue(value >= 0, "Domestication cannot be less than zero");
      Validate.isTrue(value <= this.getMaxDomestication(), "Domestication cannot be greater than the max domestication");
      this.getHandle().t(value);
   }

   public int getMaxDomestication() {
      return this.getHandle().gt();
   }

   public void setMaxDomestication(int value) {
      Validate.isTrue(value > 0, "Max domestication cannot be zero or less");
      this.getHandle().maxDomestication = value;
   }

   public double getJumpStrength() {
      return this.getHandle().gq();
   }

   public void setJumpStrength(double strength) {
      Validate.isTrue(strength >= 0.0, "Jump strength cannot be less than zero");
      this.getHandle().a(GenericAttributes.m).a(strength);
   }

   public boolean isTamed() {
      return this.getHandle().gh();
   }

   public void setTamed(boolean tamed) {
      this.getHandle().x(tamed);
   }

   public AnimalTamer getOwner() {
      return this.getOwnerUUID() == null ? null : this.getServer().getOfflinePlayer(this.getOwnerUUID());
   }

   public void setOwner(AnimalTamer owner) {
      if (owner != null) {
         this.setTamed(true);
         this.getHandle().setTarget(null, null, false);
         this.setOwnerUUID(owner.getUniqueId());
      } else {
         this.setTamed(false);
         this.setOwnerUUID(null);
      }
   }

   public UUID getOwnerUUID() {
      return this.getHandle().T_();
   }

   public void setOwnerUUID(UUID uuid) {
      this.getHandle().b(uuid);
   }

   public boolean isEatingHaystack() {
      return this.getHandle().gk();
   }

   public void setEatingHaystack(boolean eatingHaystack) {
      this.getHandle().A(eatingHaystack);
   }

   public AbstractHorseInventory getInventory() {
      return new CraftInventoryAbstractHorse(this.getHandle().cn);
   }
}
