package org.bukkit.craftbukkit.v1_19_R3.entity;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Interaction.PreviousInteraction;

public class CraftInteraction extends CraftEntity implements Interaction {
   public CraftInteraction(CraftServer server, net.minecraft.world.entity.Interaction entity) {
      super(server, entity);
   }

   public net.minecraft.world.entity.Interaction getHandle() {
      return (net.minecraft.world.entity.Interaction)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftInteraction";
   }

   public EntityType getType() {
      return EntityType.INTERACTION;
   }

   public float getInteractionWidth() {
      return this.getHandle().j();
   }

   public void setInteractionWidth(float width) {
      this.getHandle().a(width);
   }

   public float getInteractionHeight() {
      return this.getHandle().k();
   }

   public void setInteractionHeight(float height) {
      this.getHandle().b(height);
   }

   public boolean isResponsive() {
      return this.getHandle().o();
   }

   public void setResponsive(boolean response) {
      this.getHandle().a(response);
   }

   public PreviousInteraction getLastAttack() {
      net.minecraft.world.entity.Interaction.PlayerAction last = this.getHandle().k;
      return last != null ? new CraftInteraction.CraftPreviousInteraction(last.a(), last.b()) : null;
   }

   public PreviousInteraction getLastInteraction() {
      net.minecraft.world.entity.Interaction.PlayerAction last = this.getHandle().l;
      return last != null ? new CraftInteraction.CraftPreviousInteraction(last.a(), last.b()) : null;
   }

   private static class CraftPreviousInteraction implements PreviousInteraction {
      private final UUID uuid;
      private final long timestamp;

      public CraftPreviousInteraction(UUID uuid, long timestamp) {
         this.uuid = uuid;
         this.timestamp = timestamp;
      }

      public OfflinePlayer getPlayer() {
         return Bukkit.getOfflinePlayer(this.uuid);
      }

      public long getTimestamp() {
         return this.timestamp;
      }
   }
}
