package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import java.util.stream.Collectors;
import net.minecraft.world.entity.vehicle.EntityBoat;
import org.bukkit.TreeSpecies;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Boat.Status;
import org.bukkit.entity.Boat.Type;

public class CraftBoat extends CraftVehicle implements Boat {
   public CraftBoat(CraftServer server, EntityBoat entity) {
      super(server, entity);
   }

   public TreeSpecies getWoodType() {
      return getTreeSpecies(this.getHandle().t());
   }

   public void setWoodType(TreeSpecies species) {
      this.getHandle().a(getBoatType(species));
   }

   public Type getBoatType() {
      return boatTypeFromNms(this.getHandle().t());
   }

   public void setBoatType(Type type) {
      Preconditions.checkArgument(type != null, "Boat.Type cannot be null");
      this.getHandle().a(boatTypeToNms(type));
   }

   public double getMaxSpeed() {
      return this.getHandle().maxSpeed;
   }

   public void setMaxSpeed(double speed) {
      if (speed >= 0.0) {
         this.getHandle().maxSpeed = speed;
      }
   }

   public double getOccupiedDeceleration() {
      return this.getHandle().occupiedDeceleration;
   }

   public void setOccupiedDeceleration(double speed) {
      if (speed >= 0.0) {
         this.getHandle().occupiedDeceleration = speed;
      }
   }

   public double getUnoccupiedDeceleration() {
      return this.getHandle().unoccupiedDeceleration;
   }

   public void setUnoccupiedDeceleration(double speed) {
      this.getHandle().unoccupiedDeceleration = speed;
   }

   public boolean getWorkOnLand() {
      return this.getHandle().landBoats;
   }

   public void setWorkOnLand(boolean workOnLand) {
      this.getHandle().landBoats = workOnLand;
   }

   public Status getStatus() {
      return boatStatusFromNms(this.getHandle().aL);
   }

   public EntityBoat getHandle() {
      return (EntityBoat)this.entity;
   }

   @Override
   public String toString() {
      return "CraftBoat{boatType="
         + this.getBoatType()
         + ",status="
         + this.getStatus()
         + ",passengers="
         + (String)this.getPassengers().stream().map(Object::toString).collect(Collectors.joining("-", "{", "}"))
         + "}";
   }

   public EntityType getType() {
      return EntityType.BOAT;
   }

   public static Type boatTypeFromNms(EntityBoat.EnumBoatType boatType) {
      return switch(boatType) {
         case a -> Type.OAK;
         case b -> Type.SPRUCE;
         case c -> Type.BIRCH;
         case d -> Type.JUNGLE;
         case e -> Type.ACACIA;
         case f -> Type.CHERRY;
         case g -> Type.DARK_OAK;
         case h -> Type.MANGROVE;
         case i -> Type.BAMBOO;
         default -> throw new EnumConstantNotPresentException(Type.class, boatType.name());
      };
   }

   public static EntityBoat.EnumBoatType boatTypeToNms(Type type) {
      return switch(type) {
         case OAK -> EntityBoat.EnumBoatType.a;
         case SPRUCE -> EntityBoat.EnumBoatType.b;
         case BIRCH -> EntityBoat.EnumBoatType.c;
         case JUNGLE -> EntityBoat.EnumBoatType.d;
         case ACACIA -> EntityBoat.EnumBoatType.e;
         case CHERRY -> EntityBoat.EnumBoatType.f;
         case DARK_OAK -> EntityBoat.EnumBoatType.g;
         case MANGROVE -> EntityBoat.EnumBoatType.h;
         case BAMBOO -> EntityBoat.EnumBoatType.i;
         default -> throw new EnumConstantNotPresentException(EntityBoat.EnumBoatType.class, type.name());
      };
   }

   public static Status boatStatusFromNms(EntityBoat.EnumStatus enumStatus) {
      return switch(enumStatus) {
         case a -> Status.IN_WATER;
         case b -> Status.UNDER_WATER;
         case c -> Status.UNDER_FLOWING_WATER;
         case d -> Status.ON_LAND;
         case e -> Status.IN_AIR;
         default -> throw new EnumConstantNotPresentException(Status.class, enumStatus.name());
      };
   }

   @Deprecated
   public static TreeSpecies getTreeSpecies(EntityBoat.EnumBoatType boatType) {
      switch(boatType) {
         case a:
         case f:
         default:
            return TreeSpecies.GENERIC;
         case b:
            return TreeSpecies.REDWOOD;
         case c:
            return TreeSpecies.BIRCH;
         case d:
            return TreeSpecies.JUNGLE;
         case e:
            return TreeSpecies.ACACIA;
         case g:
            return TreeSpecies.DARK_OAK;
      }
   }

   @Deprecated
   public static EntityBoat.EnumBoatType getBoatType(TreeSpecies species) {
      switch(species) {
         case GENERIC:
         default:
            return EntityBoat.EnumBoatType.a;
         case REDWOOD:
            return EntityBoat.EnumBoatType.b;
         case BIRCH:
            return EntityBoat.EnumBoatType.c;
         case JUNGLE:
            return EntityBoat.EnumBoatType.d;
         case ACACIA:
            return EntityBoat.EnumBoatType.e;
         case DARK_OAK:
            return EntityBoat.EnumBoatType.g;
      }
   }
}
