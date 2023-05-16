package org.bukkit.craftbukkit.v1_19_R3.generator;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.IRegistry;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkSection;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.material.MaterialData;

@Deprecated
public final class OldCraftChunkData implements ChunkData {
   private final int minHeight;
   private final int maxHeight;
   private final ChunkSection[] sections;
   private final IRegistry<BiomeBase> biomes;
   private Set<BlockPosition> tiles;
   private final Set<BlockPosition> lights = new HashSet<>();

   public OldCraftChunkData(int minHeight, int maxHeight, IRegistry<BiomeBase> biomes) {
      this.minHeight = minHeight;
      this.maxHeight = maxHeight;
      this.biomes = biomes;
      this.sections = new ChunkSection[(maxHeight - 1 >> 4) + 1 - (minHeight >> 4)];
   }

   public int getMinHeight() {
      return this.minHeight;
   }

   public int getMaxHeight() {
      return this.maxHeight;
   }

   public Biome getBiome(int x, int y, int z) {
      throw new UnsupportedOperationException("Unsupported, in older chunk generator api");
   }

   public void setBlock(int x, int y, int z, Material material) {
      this.setBlock(x, y, z, material.createBlockData());
   }

   public void setBlock(int x, int y, int z, MaterialData material) {
      this.setBlock(x, y, z, CraftMagicNumbers.getBlock(material));
   }

   public void setBlock(int x, int y, int z, BlockData blockData) {
      this.setBlock(x, y, z, ((CraftBlockData)blockData).getState());
   }

   public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, Material material) {
      this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, material.createBlockData());
   }

   public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, MaterialData material) {
      this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, CraftMagicNumbers.getBlock(material));
   }

   public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, BlockData blockData) {
      this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, ((CraftBlockData)blockData).getState());
   }

   public Material getType(int x, int y, int z) {
      return CraftMagicNumbers.getMaterial(this.getTypeId(x, y, z).b());
   }

   public MaterialData getTypeAndData(int x, int y, int z) {
      return CraftMagicNumbers.getMaterial(this.getTypeId(x, y, z));
   }

   public BlockData getBlockData(int x, int y, int z) {
      return CraftBlockData.fromData(this.getTypeId(x, y, z));
   }

   public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, IBlockData type) {
      if (xMin <= 15 && yMin < this.maxHeight && zMin <= 15) {
         if (xMin < 0) {
            xMin = 0;
         }

         if (yMin < this.minHeight) {
            yMin = this.minHeight;
         }

         if (zMin < 0) {
            zMin = 0;
         }

         if (xMax > 16) {
            xMax = 16;
         }

         if (yMax > this.maxHeight) {
            yMax = this.maxHeight;
         }

         if (zMax > 16) {
            zMax = 16;
         }

         if (xMin < xMax && yMin < yMax && zMin < zMax) {
            for(int y = yMin; y < yMax; ++y) {
               ChunkSection section = this.getChunkSection(y, true);
               int offsetBase = y & 15;

               for(int x = xMin; x < xMax; ++x) {
                  for(int z = zMin; z < zMax; ++z) {
                     section.a(x, offsetBase, z, type);
                  }
               }
            }
         }
      }
   }

   public IBlockData getTypeId(int x, int y, int z) {
      if (x == (x & 15) && y >= this.minHeight && y < this.maxHeight && z == (z & 15)) {
         ChunkSection section = this.getChunkSection(y, false);
         return section == null ? Blocks.a.o() : section.a(x, y & 15, z);
      } else {
         return Blocks.a.o();
      }
   }

   public byte getData(int x, int y, int z) {
      return CraftMagicNumbers.toLegacyData(this.getTypeId(x, y, z));
   }

   private void setBlock(int x, int y, int z, IBlockData type) {
      if (x == (x & 15) && y >= this.minHeight && y < this.maxHeight && z == (z & 15)) {
         ChunkSection section = this.getChunkSection(y, true);
         section.a(x, y & 15, z, type);
         if (type.g() > 0) {
            this.lights.add(new BlockPosition(x, y, z));
         } else {
            this.lights.remove(new BlockPosition(x, y, z));
         }

         if (type.q()) {
            if (this.tiles == null) {
               this.tiles = new HashSet<>();
            }

            this.tiles.add(new BlockPosition(x, y, z));
         }
      }
   }

   private ChunkSection getChunkSection(int y, boolean create) {
      int offset = y - this.minHeight >> 4;
      ChunkSection section = this.sections[offset];
      if (create && section == null) {
         this.sections[offset] = section = new ChunkSection(offset + (this.minHeight >> 4), this.biomes);
      }

      return section;
   }

   ChunkSection[] getRawChunkData() {
      return this.sections;
   }

   Set<BlockPosition> getTiles() {
      return this.tiles;
   }

   Set<BlockPosition> getLights() {
      return this.lights;
   }
}
