package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.decoration.EntityLeash;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LeashHitch;

public class CraftLeash extends CraftHanging implements LeashHitch {
   public CraftLeash(CraftServer server, EntityLeash entity) {
      super(server, entity);
   }

   @Override
   public boolean setFacingDirection(BlockFace face, boolean force) {
      Preconditions.checkArgument(face == BlockFace.SELF, "%s is not a valid facing direction", face);
      return force || this.getHandle().generation || this.getHandle().s();
   }

   @Override
   public BlockFace getFacing() {
      return BlockFace.SELF;
   }

   public EntityLeash getHandle() {
      return (EntityLeash)this.entity;
   }

   @Override
   public String toString() {
      return "CraftLeash";
   }

   @Override
   public EntityType getType() {
      return EntityType.LEASH_HITCH;
   }
}
