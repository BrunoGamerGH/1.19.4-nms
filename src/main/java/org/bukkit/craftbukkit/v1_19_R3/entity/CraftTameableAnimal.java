package org.bukkit.craftbukkit.v1_19_R3.entity;

import java.util.UUID;
import net.minecraft.world.entity.EntityTameableAnimal;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Tameable;

public class CraftTameableAnimal extends CraftAnimals implements Tameable, Creature {
   public CraftTameableAnimal(CraftServer server, EntityTameableAnimal entity) {
      super(server, entity);
   }

   public EntityTameableAnimal getHandle() {
      return (EntityTameableAnimal)super.getHandle();
   }

   public UUID getOwnerUUID() {
      try {
         return this.getHandle().T_();
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   public void setOwnerUUID(UUID uuid) {
      this.getHandle().b(uuid);
   }

   public AnimalTamer getOwner() {
      if (this.getOwnerUUID() == null) {
         return null;
      } else {
         AnimalTamer owner = this.getServer().getPlayer(this.getOwnerUUID());
         if (owner == null) {
            owner = this.getServer().getOfflinePlayer(this.getOwnerUUID());
         }

         return owner;
      }
   }

   public boolean isTamed() {
      return this.getHandle().q();
   }

   public void setOwner(AnimalTamer tamer) {
      if (tamer != null) {
         this.setTamed(true);
         this.getHandle().setTarget(null, null, false);
         this.setOwnerUUID(tamer.getUniqueId());
      } else {
         this.setTamed(false);
         this.setOwnerUUID(null);
      }
   }

   public void setTamed(boolean tame) {
      this.getHandle().x(tame);
      if (!tame) {
         this.setOwnerUUID(null);
      }
   }

   public boolean isSitting() {
      return this.getHandle().w();
   }

   public void setSitting(boolean sitting) {
      this.getHandle().y(sitting);
      this.getHandle().z(sitting);
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName() + "{owner=" + this.getOwner() + ",tamed=" + this.isTamed() + "}";
   }
}
