package org.bukkit.craftbukkit.v1_19_R3;

import com.google.common.base.Preconditions;
import java.util.concurrent.TimeUnit;
import net.minecraft.core.BlockPosition;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class CraftWorldBorder implements WorldBorder {
   private final World world;
   private final net.minecraft.world.level.border.WorldBorder handle;

   public CraftWorldBorder(CraftWorld world) {
      this.world = world;
      this.handle = world.getHandle().p_();
   }

   public CraftWorldBorder(net.minecraft.world.level.border.WorldBorder handle) {
      this.world = null;
      this.handle = handle;
   }

   public World getWorld() {
      return this.world;
   }

   public void reset() {
      this.getHandle().a(net.minecraft.world.level.border.WorldBorder.e);
   }

   public double getSize() {
      return this.handle.i();
   }

   public void setSize(double newSize) {
      this.setSize(newSize, 0L);
   }

   public void setSize(double newSize, long time) {
      this.setSize(Math.min(this.getMaxSize(), Math.max(1.0, newSize)), TimeUnit.SECONDS, Math.min(9223372036854775L, Math.max(0L, time)));
   }

   public void setSize(double newSize, TimeUnit unit, long time) {
      Preconditions.checkArgument(unit != null, "TimeUnit cannot be null.");
      Preconditions.checkArgument(time >= 0L, "time cannot be lower than 0");
      Preconditions.checkArgument(newSize >= 1.0 && newSize <= this.getMaxSize(), "newSize must be between 1.0D and %s", this.getMaxSize());
      if (time > 0L) {
         this.handle.a(this.handle.i(), newSize, unit.toMillis(time));
      } else {
         this.handle.a(newSize);
      }
   }

   public Location getCenter() {
      double x = this.handle.a();
      double z = this.handle.b();
      return new Location(this.world, x, 0.0, z);
   }

   public void setCenter(double x, double z) {
      Preconditions.checkArgument(Math.abs(x) <= this.getMaxCenterCoordinate(), "x coordinate cannot be outside +- %s", this.getMaxCenterCoordinate());
      Preconditions.checkArgument(Math.abs(z) <= this.getMaxCenterCoordinate(), "z coordinate cannot be outside +- %s", this.getMaxCenterCoordinate());
      this.handle.c(x, z);
   }

   public void setCenter(Location location) {
      this.setCenter(location.getX(), location.getZ());
   }

   public double getDamageBuffer() {
      return this.handle.n();
   }

   public void setDamageBuffer(double blocks) {
      this.handle.b(blocks);
   }

   public double getDamageAmount() {
      return this.handle.o();
   }

   public void setDamageAmount(double damage) {
      this.handle.c(damage);
   }

   public int getWarningTime() {
      return this.handle.q();
   }

   public void setWarningTime(int time) {
      this.handle.b(time);
   }

   public int getWarningDistance() {
      return this.handle.r();
   }

   public void setWarningDistance(int distance) {
      this.handle.c(distance);
   }

   public boolean isInside(Location location) {
      Preconditions.checkArgument(location != null, "location cannot be null");
      return (this.world == null || location.getWorld().equals(this.world))
         && this.handle.a(BlockPosition.a(location.getX(), location.getY(), location.getZ()));
   }

   public double getMaxSize() {
      return 5.999997E7F;
   }

   public double getMaxCenterCoordinate() {
      return 2.9999984E7;
   }

   public net.minecraft.world.level.border.WorldBorder getHandle() {
      return this.handle;
   }

   public boolean isVirtual() {
      return this.world == null;
   }
}
