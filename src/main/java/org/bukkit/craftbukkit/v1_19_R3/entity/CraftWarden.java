package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.monster.warden.WardenAi;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Warden;
import org.bukkit.entity.Warden.AngerLevel;

public class CraftWarden extends CraftMonster implements Warden {
   public CraftWarden(CraftServer server, net.minecraft.world.entity.monster.warden.Warden entity) {
      super(server, entity);
   }

   public net.minecraft.world.entity.monster.warden.Warden getHandle() {
      return (net.minecraft.world.entity.monster.warden.Warden)this.entity;
   }

   @Override
   public String toString() {
      return "CraftWarden";
   }

   @Override
   public EntityType getType() {
      return EntityType.WARDEN;
   }

   public int getAnger() {
      return this.getHandle().fU().b(this.getHandle().P_());
   }

   public int getAnger(Entity entity) {
      Preconditions.checkArgument(entity != null, "Entity cannot be null");
      return this.getHandle().fU().b(((CraftEntity)entity).getHandle());
   }

   public void increaseAnger(Entity entity, int increase) {
      Preconditions.checkArgument(entity != null, "Entity cannot be null");
      this.getHandle().fU().a(((CraftEntity)entity).getHandle(), increase);
   }

   public void setAnger(Entity entity, int anger) {
      Preconditions.checkArgument(entity != null, "Entity cannot be null");
      this.getHandle().b(((CraftEntity)entity).getHandle());
      this.getHandle().fU().a(((CraftEntity)entity).getHandle(), anger);
   }

   public void clearAnger(Entity entity) {
      Preconditions.checkArgument(entity != null, "Entity cannot be null");
      this.getHandle().b(((CraftEntity)entity).getHandle());
   }

   public LivingEntity getEntityAngryAt() {
      return (LivingEntity)this.getHandle().fT().map(net.minecraft.world.entity.Entity::getBukkitEntity).orElse(null);
   }

   public void setDisturbanceLocation(Location location) {
      Preconditions.checkArgument(location != null, "Location cannot be null");
      WardenAi.a(this.getHandle(), BlockPosition.a(location.getX(), location.getY(), location.getZ()));
   }

   public AngerLevel getAngerLevel() {
      return switch(this.getHandle().fS()) {
         case a -> AngerLevel.CALM;
         case b -> AngerLevel.AGITATED;
         case c -> AngerLevel.ANGRY;
      };
   }
}
