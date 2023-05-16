package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.animal.EntityBee;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Bee;
import org.bukkit.entity.EntityType;

public class CraftBee extends CraftAnimals implements Bee {
   public CraftBee(CraftServer server, EntityBee entity) {
      super(server, entity);
   }

   public EntityBee getHandle() {
      return (EntityBee)this.entity;
   }

   @Override
   public String toString() {
      return "CraftBee";
   }

   @Override
   public EntityType getType() {
      return EntityType.BEE;
   }

   public Location getHive() {
      BlockPosition hive = this.getHandle().ga();
      return hive == null ? null : new Location(this.getWorld(), (double)hive.u(), (double)hive.v(), (double)hive.w());
   }

   public void setHive(Location location) {
      Preconditions.checkArgument(location == null || this.getWorld().equals(location.getWorld()), "Hive must be in same world");
      this.getHandle().cF = location == null ? null : new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
   }

   public Location getFlower() {
      BlockPosition flower = this.getHandle().q();
      return flower == null ? null : new Location(this.getWorld(), (double)flower.u(), (double)flower.v(), (double)flower.w());
   }

   public void setFlower(Location location) {
      Preconditions.checkArgument(location == null || this.getWorld().equals(location.getWorld()), "Flower must be in same world");
      this.getHandle().g(location == null ? null : new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
   }

   public boolean hasNectar() {
      return this.getHandle().gc();
   }

   public void setHasNectar(boolean nectar) {
      this.getHandle().w(nectar);
   }

   public boolean hasStung() {
      return this.getHandle().gd();
   }

   public void setHasStung(boolean stung) {
      this.getHandle().x(stung);
   }

   public int getAnger() {
      return this.getHandle().a();
   }

   public void setAnger(int anger) {
      this.getHandle().a(anger);
   }

   public int getCannotEnterHiveTicks() {
      return this.getHandle().cy;
   }

   public void setCannotEnterHiveTicks(int ticks) {
      this.getHandle().s(ticks);
   }
}
