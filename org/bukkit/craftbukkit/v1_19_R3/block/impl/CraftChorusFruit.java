package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.BlockChorusFruit;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftChorusFruit extends CraftBlockData implements MultipleFacing {
   private static final BlockStateBoolean[] FACES = new BlockStateBoolean[]{
      getBoolean(BlockChorusFruit.class, "north", true),
      getBoolean(BlockChorusFruit.class, "east", true),
      getBoolean(BlockChorusFruit.class, "south", true),
      getBoolean(BlockChorusFruit.class, "west", true),
      getBoolean(BlockChorusFruit.class, "up", true),
      getBoolean(BlockChorusFruit.class, "down", true)
   };

   public CraftChorusFruit() {
   }

   public CraftChorusFruit(IBlockData state) {
      super(state);
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
}
