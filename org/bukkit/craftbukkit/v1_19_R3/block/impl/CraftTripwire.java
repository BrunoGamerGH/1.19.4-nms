package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.BlockTripwire;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Attachable;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Tripwire;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftTripwire extends CraftBlockData implements Tripwire, Attachable, MultipleFacing, Powerable {
   private static final BlockStateBoolean DISARMED = getBoolean(BlockTripwire.class, "disarmed");
   private static final BlockStateBoolean ATTACHED = getBoolean(BlockTripwire.class, "attached");
   private static final BlockStateBoolean[] FACES = new BlockStateBoolean[]{
      getBoolean(BlockTripwire.class, "north", true),
      getBoolean(BlockTripwire.class, "east", true),
      getBoolean(BlockTripwire.class, "south", true),
      getBoolean(BlockTripwire.class, "west", true),
      getBoolean(BlockTripwire.class, "up", true),
      getBoolean(BlockTripwire.class, "down", true)
   };
   private static final BlockStateBoolean POWERED = getBoolean(BlockTripwire.class, "powered");

   public CraftTripwire() {
   }

   public CraftTripwire(IBlockData state) {
      super(state);
   }

   public boolean isDisarmed() {
      return this.get(DISARMED);
   }

   public void setDisarmed(boolean disarmed) {
      this.set(DISARMED, Boolean.valueOf(disarmed));
   }

   public boolean isAttached() {
      return this.get(ATTACHED);
   }

   public void setAttached(boolean attached) {
      this.set(ATTACHED, Boolean.valueOf(attached));
   }

   public boolean hasFace(BlockFace face) {
      BlockStateBoolean state = FACES[face.ordinal()];
      if (state == null) {
         throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
      } else {
         return this.get(state);
      }
   }

   public void setFace(BlockFace face, boolean has) {
      BlockStateBoolean state = FACES[face.ordinal()];
      if (state == null) {
         throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
      } else {
         this.set(state, Boolean.valueOf(has));
      }
   }

   public Set<BlockFace> getFaces() {
      Builder<BlockFace> faces = ImmutableSet.builder();

      for(int i = 0; i < FACES.length; ++i) {
         if (FACES[i] != null && this.get(FACES[i])) {
            faces.add(BlockFace.values()[i]);
         }
      }

      return faces.build();
   }

   public Set<BlockFace> getAllowedFaces() {
      Builder<BlockFace> faces = ImmutableSet.builder();

      for(int i = 0; i < FACES.length; ++i) {
         if (FACES[i] != null) {
            faces.add(BlockFace.values()[i]);
         }
      }

      return faces.build();
   }

   public boolean isPowered() {
      return this.get(POWERED);
   }

   public void setPowered(boolean powered) {
      this.set(POWERED, Boolean.valueOf(powered));
   }
}
