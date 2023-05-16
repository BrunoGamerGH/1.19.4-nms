package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.world.entity.animal.EntityFox;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Fox.Type;

public class CraftFox extends CraftAnimals implements Fox {
   public CraftFox(CraftServer server, EntityFox entity) {
      super(server, entity);
   }

   public EntityFox getHandle() {
      return (EntityFox)super.getHandle();
   }

   @Override
   public EntityType getType() {
      return EntityType.FOX;
   }

   @Override
   public String toString() {
      return "CraftFox";
   }

   public Type getFoxType() {
      return Type.values()[this.getHandle().r().ordinal()];
   }

   public void setFoxType(Type type) {
      Preconditions.checkArgument(type != null, "type");
      this.getHandle().a(EntityFox.Type.values()[type.ordinal()]);
   }

   public boolean isCrouching() {
      return this.getHandle().bT();
   }

   public void setCrouching(boolean crouching) {
      this.getHandle().y(crouching);
   }

   public boolean isSitting() {
      return this.getHandle().w();
   }

   public void setSitting(boolean sitting) {
      this.getHandle().w(sitting);
   }

   public void setSleeping(boolean sleeping) {
      this.getHandle().C(sleeping);
   }

   public AnimalTamer getFirstTrustedPlayer() {
      UUID uuid = this.getHandle().aj().a(EntityFox.cc).orElse(null);
      if (uuid == null) {
         return null;
      } else {
         AnimalTamer player = this.getServer().getPlayer(uuid);
         if (player == null) {
            player = this.getServer().getOfflinePlayer(uuid);
         }

         return player;
      }
   }

   public void setFirstTrustedPlayer(AnimalTamer player) {
      if (player == null && this.getHandle().aj().a(EntityFox.cd).isPresent()) {
         throw new IllegalStateException("Must remove second trusted player first");
      } else {
         this.getHandle().aj().b(EntityFox.cc, player == null ? Optional.empty() : Optional.of(player.getUniqueId()));
      }
   }

   public AnimalTamer getSecondTrustedPlayer() {
      UUID uuid = this.getHandle().aj().a(EntityFox.cd).orElse(null);
      if (uuid == null) {
         return null;
      } else {
         AnimalTamer player = this.getServer().getPlayer(uuid);
         if (player == null) {
            player = this.getServer().getOfflinePlayer(uuid);
         }

         return player;
      }
   }

   public void setSecondTrustedPlayer(AnimalTamer player) {
      if (player != null && !this.getHandle().aj().a(EntityFox.cc).isPresent()) {
         throw new IllegalStateException("Must add first trusted player first");
      } else {
         this.getHandle().aj().b(EntityFox.cd, player == null ? Optional.empty() : Optional.of(player.getUniqueId()));
      }
   }

   public boolean isFaceplanted() {
      return this.getHandle().fS();
   }
}
