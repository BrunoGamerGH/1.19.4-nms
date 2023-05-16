package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.GlowLichen;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftGlowLichen extends CraftBlockData implements GlowLichen, MultipleFacing, Waterlogged {
   private static final BlockStateBoolean[] FACES = new BlockStateBoolean[]{
      getBoolean(GlowLichenBlock.class, "north", true),
      getBoolean(GlowLichenBlock.class, "east", true),
      getBoolean(GlowLichenBlock.class, "south", true),
      getBoolean(GlowLichenBlock.class, "west", true),
      getBoolean(GlowLichenBlock.class, "up", true),
      getBoolean(GlowLichenBlock.class, "down", true)
   };
   private static final BlockStateBoolean WATERLOGGED = getBoolean(GlowLichenBlock.class, "waterlogged");

   public CraftGlowLichen() {
   }

   public CraftGlowLichen(IBlockData state) {
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

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
