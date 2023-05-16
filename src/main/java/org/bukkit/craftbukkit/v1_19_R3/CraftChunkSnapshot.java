package org.bukkit.craftbukkit.v1_19_R3;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.DataPaletteBlock;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import net.minecraft.world.level.levelgen.HeightMap;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;

public class CraftChunkSnapshot implements ChunkSnapshot {
   private final int x;
   private final int z;
   private final int minHeight;
   private final int maxHeight;
   private final String worldname;
   private final DataPaletteBlock<IBlockData>[] blockids;
   private final byte[][] skylight;
   private final byte[][] emitlight;
   private final boolean[] empty;
   private final HeightMap hmap;
   private final long captureFulltime;
   private final IRegistry<BiomeBase> biomeRegistry;
   private final PalettedContainerRO<Holder<BiomeBase>>[] biome;

   CraftChunkSnapshot(
      int x,
      int z,
      int minHeight,
      int maxHeight,
      String wname,
      long wtime,
      DataPaletteBlock<IBlockData>[] sectionBlockIDs,
      byte[][] sectionSkyLights,
      byte[][] sectionEmitLights,
      boolean[] sectionEmpty,
      HeightMap hmap,
      IRegistry<BiomeBase> biomeRegistry,
      PalettedContainerRO<Holder<BiomeBase>>[] biome
   ) {
      this.x = x;
      this.z = z;
      this.minHeight = minHeight;
      this.maxHeight = maxHeight;
      this.worldname = wname;
      this.captureFulltime = wtime;
      this.blockids = sectionBlockIDs;
      this.skylight = sectionSkyLights;
      this.emitlight = sectionEmitLights;
      this.empty = sectionEmpty;
      this.hmap = hmap;
      this.biomeRegistry = biomeRegistry;
      this.biome = biome;
   }

   public int getX() {
      return this.x;
   }

   public int getZ() {
      return this.z;
   }

   public String getWorldName() {
      return this.worldname;
   }

   public boolean contains(BlockData block) {
      Preconditions.checkArgument(block != null, "Block cannot be null");
      Predicate<IBlockData> nms = Predicates.equalTo(((CraftBlockData)block).getState());

      for(DataPaletteBlock<IBlockData> palette : this.blockids) {
         if (palette.a(nms)) {
            return true;
         }
      }

      return false;
   }

   public boolean contains(Biome biome) {
      Preconditions.checkArgument(biome != null, "Biome cannot be null");
      Predicate<Holder<BiomeBase>> nms = Predicates.equalTo(CraftBlock.biomeToBiomeBase(this.biomeRegistry, biome));

      for(PalettedContainerRO<Holder<BiomeBase>> palette : this.biome) {
         if (palette.a(nms)) {
            return true;
         }
      }

      return false;
   }

   public Material getBlockType(int x, int y, int z) {
      this.validateChunkCoordinates(x, y, z);
      return CraftMagicNumbers.getMaterial(this.blockids[this.getSectionIndex(y)].a(x, y & 15, z).b());
   }

   public final BlockData getBlockData(int x, int y, int z) {
      this.validateChunkCoordinates(x, y, z);
      return CraftBlockData.fromData(this.blockids[this.getSectionIndex(y)].a(x, y & 15, z));
   }

   public final int getData(int x, int y, int z) {
      this.validateChunkCoordinates(x, y, z);
      return CraftMagicNumbers.toLegacyData(this.blockids[this.getSectionIndex(y)].a(x, y & 15, z));
   }

   public final int getBlockSkyLight(int x, int y, int z) {
      this.validateChunkCoordinates(x, y, z);
      int off = (y & 15) << 7 | z << 3 | x >> 1;
      return this.skylight[this.getSectionIndex(y)][off] >> ((x & 1) << 2) & 15;
   }

   public final int getBlockEmittedLight(int x, int y, int z) {
      this.validateChunkCoordinates(x, y, z);
      int off = (y & 15) << 7 | z << 3 | x >> 1;
      return this.emitlight[this.getSectionIndex(y)][off] >> ((x & 1) << 2) & 15;
   }

   public final int getHighestBlockYAt(int x, int z) {
      Preconditions.checkState(this.hmap != null, "ChunkSnapshot created without height map. Please call getSnapshot with includeMaxblocky=true");
      this.validateChunkCoordinates(x, 0, z);
      return this.hmap.b(x, z);
   }

   public final Biome getBiome(int x, int z) {
      return this.getBiome(x, 0, z);
   }

   public final Biome getBiome(int x, int y, int z) {
      Preconditions.checkState(this.biome != null, "ChunkSnapshot created without biome. Please call getSnapshot with includeBiome=true");
      this.validateChunkCoordinates(x, y, z);
      PalettedContainerRO<Holder<BiomeBase>> biome = this.biome[this.getSectionIndex(y >> 2)];
      return CraftBlock.biomeBaseToBiome(this.biomeRegistry, biome.a(x >> 2, (y & 15) >> 2, z >> 2));
   }

   public final double getRawBiomeTemperature(int x, int z) {
      return this.getRawBiomeTemperature(x, 0, z);
   }

   public final double getRawBiomeTemperature(int x, int y, int z) {
      Preconditions.checkState(this.biome != null, "ChunkSnapshot created without biome. Please call getSnapshot with includeBiome=true");
      this.validateChunkCoordinates(x, y, z);
      PalettedContainerRO<Holder<BiomeBase>> biome = this.biome[this.getSectionIndex(y >> 2)];
      return (double)biome.a(x >> 2, (y & 15) >> 2, z >> 2).a().f(new BlockPosition(this.x << 4 | x, y, this.z << 4 | z));
   }

   public final long getCaptureFullTime() {
      return this.captureFulltime;
   }

   public final boolean isSectionEmpty(int sy) {
      return this.empty[sy];
   }

   private int getSectionIndex(int y) {
      return y - this.minHeight >> 4;
   }

   private void validateChunkCoordinates(int x, int y, int z) {
      CraftChunk.validateChunkCoordinates(this.minHeight, this.maxHeight, x, y, z);
   }
}
