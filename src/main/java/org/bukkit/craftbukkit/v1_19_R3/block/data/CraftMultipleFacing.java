package org.bukkit.craftbukkit.v1_19_R3.block.data;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;

public abstract class CraftMultipleFacing extends CraftBlockData implements MultipleFacing {
   private static final BlockStateBoolean[] FACES = new BlockStateBoolean[]{
      getBoolean("north", true),
      getBoolean("east", true),
      getBoolean("south", true),
      getBoolean("west", true),
      getBoolean("up", true),
      getBoolean("down", true)
   };

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
}
