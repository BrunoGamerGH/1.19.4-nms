package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sniffer;
import org.bukkit.entity.Sniffer.State;

public class CraftSniffer extends CraftAnimals implements Sniffer {
   public CraftSniffer(CraftServer server, net.minecraft.world.entity.animal.sniffer.Sniffer entity) {
      super(server, entity);
   }

   public net.minecraft.world.entity.animal.sniffer.Sniffer getHandle() {
      return (net.minecraft.world.entity.animal.sniffer.Sniffer)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftSniffer";
   }

   @Override
   public EntityType getType() {
      return EntityType.SNIFFER;
   }

   public Collection<Location> getExploredLocations() {
      return this.getHandle()
         .gh()
         .map(blockPosition -> new Location(this.getLocation().getWorld(), (double)blockPosition.u(), (double)blockPosition.v(), (double)blockPosition.w()))
         .collect(Collectors.toList());
   }

   public void removeExploredLocation(Location location) {
      Preconditions.checkArgument(location != null, "location cannot be null");
      if (location.getWorld() == this.getWorld()) {
         BlockPosition blockPosition = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
         this.getHandle()
            .dH()
            .a(
               MemoryModuleType.aP,
               this.getHandle().gh().filter(blockPositionExplored -> !blockPositionExplored.equals(blockPosition)).collect(Collectors.toList())
            );
      }
   }

   public void addExploredLocation(Location location) {
      Preconditions.checkArgument(location != null, "location cannot be null");
      if (location.getWorld() == this.getWorld()) {
         this.getHandle().h(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
      }
   }

   public State getState() {
      return this.stateToBukkit(this.getHandle().gd());
   }

   public void setState(State state) {
      Preconditions.checkArgument(state != null, "state cannot be null");
      this.getHandle().a(this.stateToNMS(state));
   }

   public Location findPossibleDigLocation() {
      return (Location)this.getHandle()
         .fS()
         .map(blockPosition -> new Location(this.getLocation().getWorld(), (double)blockPosition.u(), (double)blockPosition.v(), (double)blockPosition.w()))
         .orElse(null);
   }

   public boolean canDig() {
      return this.getHandle().fY();
   }

   private net.minecraft.world.entity.animal.sniffer.Sniffer.a stateToNMS(State state) {
      return switch(state) {
         case IDLING -> net.minecraft.world.entity.animal.sniffer.Sniffer.a.a;
         case FEELING_HAPPY -> net.minecraft.world.entity.animal.sniffer.Sniffer.a.b;
         case SCENTING -> net.minecraft.world.entity.animal.sniffer.Sniffer.a.c;
         case SNIFFING -> net.minecraft.world.entity.animal.sniffer.Sniffer.a.d;
         case SEARCHING -> net.minecraft.world.entity.animal.sniffer.Sniffer.a.e;
         case DIGGING -> net.minecraft.world.entity.animal.sniffer.Sniffer.a.f;
         case RISING -> net.minecraft.world.entity.animal.sniffer.Sniffer.a.g;
         default -> throw new IncompatibleClassChangeError();
      };
   }

   private State stateToBukkit(net.minecraft.world.entity.animal.sniffer.Sniffer.a state) {
      return switch(state) {
         case a -> State.IDLING;
         case b -> State.FEELING_HAPPY;
         case c -> State.SCENTING;
         case d -> State.SNIFFING;
         case e -> State.SEARCHING;
         case f -> State.DIGGING;
         case g -> State.RISING;
      };
   }
}
