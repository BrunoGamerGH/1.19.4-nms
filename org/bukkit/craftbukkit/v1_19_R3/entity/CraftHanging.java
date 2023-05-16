package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.core.EnumDirection;
import net.minecraft.world.entity.decoration.EntityHanging;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;

public class CraftHanging extends CraftEntity implements Hanging {
   public CraftHanging(CraftServer server, EntityHanging entity) {
      super(server, entity);
   }

   public BlockFace getAttachedFace() {
      return this.getFacing().getOppositeFace();
   }

   public void setFacingDirection(BlockFace face) {
      this.setFacingDirection(face, false);
   }

   public boolean setFacingDirection(BlockFace face, boolean force) {
      EntityHanging hanging = this.getHandle();
      EnumDirection dir = hanging.cA();
      switch(face) {
         case NORTH:
            this.getHandle().a(EnumDirection.c);
            break;
         case EAST:
            this.getHandle().a(EnumDirection.f);
            break;
         case SOUTH:
            this.getHandle().a(EnumDirection.d);
            break;
         case WEST:
            this.getHandle().a(EnumDirection.e);
            break;
         default:
            throw new IllegalArgumentException(String.format("%s is not a valid facing direction", face));
      }

      if (!force && !this.getHandle().generation && !hanging.s()) {
         hanging.a(dir);
         return false;
      } else {
         return true;
      }
   }

   @Override
   public BlockFace getFacing() {
      EnumDirection direction = this.getHandle().cA();
      return direction == null ? BlockFace.SELF : CraftBlock.notchToBlockFace(direction);
   }

   public EntityHanging getHandle() {
      return (EntityHanging)this.entity;
   }

   @Override
   public String toString() {
      return "CraftHanging";
   }

   public EntityType getType() {
      return EntityType.UNKNOWN;
   }
}
