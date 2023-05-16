package org.bukkit.craftbukkit.v1_19_R3.generator;

import com.google.common.base.Preconditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R3.CraftRegionAccessor;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Consumer;

public class CraftLimitedRegion extends CraftRegionAccessor implements LimitedRegion {
   private final WeakReference<GeneratorAccessSeed> weakAccess;
   private final int centerChunkX;
   private final int centerChunkZ;
   private final int buffer = 16;
   private final BoundingBox region;
   boolean entitiesLoaded = false;
   private final List<Entity> entities = new ArrayList<>();
   private final List<Entity> outsideEntities = new ArrayList<>();

   public CraftLimitedRegion(GeneratorAccessSeed access, ChunkCoordIntPair center) {
      this.weakAccess = new WeakReference<>(access);
      this.centerChunkX = center.e;
      this.centerChunkZ = center.f;
      World world = access.getMinecraftWorld().getWorld();
      int xCenter = this.centerChunkX << 4;
      int zCenter = this.centerChunkZ << 4;
      int xMin = xCenter - this.getBuffer();
      int zMin = zCenter - this.getBuffer();
      int xMax = xCenter + this.getBuffer() + 16;
      int zMax = zCenter + this.getBuffer() + 16;
      this.region = new BoundingBox((double)xMin, (double)world.getMinHeight(), (double)zMin, (double)xMax, (double)world.getMaxHeight(), (double)zMax);
   }

   @Override
   public GeneratorAccessSeed getHandle() {
      GeneratorAccessSeed handle = this.weakAccess.get();
      if (handle == null) {
         throw new IllegalStateException("GeneratorAccessSeed no longer present, are you using it in a different tick?");
      } else {
         return handle;
      }
   }

   public void loadEntities() {
      if (!this.entitiesLoaded) {
         GeneratorAccessSeed access = this.getHandle();

         for(int x = -1; x <= 1; ++x) {
            for(int z = -1; z <= 1; ++z) {
               ProtoChunk chunk = (ProtoChunk)access.a(this.centerChunkX + x, this.centerChunkZ + z);

               for(NBTTagCompound compound : chunk.D()) {
                  EntityTypes.a(compound, access.getMinecraftWorld(), entity -> {
                     if (this.region.contains(entity.dl(), entity.dn(), entity.dr())) {
                        entity.generation = true;
                        this.entities.add(entity);
                     } else {
                        this.outsideEntities.add(entity);
                     }

                     return entity;
                  });
               }
            }
         }

         this.entitiesLoaded = true;
      }
   }

   public void saveEntities() {
      GeneratorAccessSeed access = this.getHandle();
      if (this.entitiesLoaded) {
         for(int x = -1; x <= 1; ++x) {
            for(int z = -1; z <= 1; ++z) {
               ProtoChunk chunk = (ProtoChunk)access.a(this.centerChunkX + x, this.centerChunkZ + z);
               chunk.D().clear();
            }
         }
      }

      for(Entity entity : this.entities) {
         if (entity.bq()) {
            Preconditions.checkState(this.region.contains(entity.dl(), entity.dn(), entity.dr()), "Entity %s is not in the region", entity);
            access.b(entity);
         }
      }

      for(Entity entity : this.outsideEntities) {
         access.b(entity);
      }
   }

   public void breakLink() {
      this.weakAccess.clear();
   }

   public int getBuffer() {
      return 16;
   }

   public boolean isInRegion(Location location) {
      return this.region.contains(location.getX(), location.getY(), location.getZ());
   }

   public boolean isInRegion(int x, int y, int z) {
      return this.region.contains((double)x, (double)y, (double)z);
   }

   public List<BlockState> getTileEntities() {
      List<BlockState> blockStates = new ArrayList();

      for(int x = -1; x <= 1; ++x) {
         for(int z = -1; z <= 1; ++z) {
            ProtoChunk chunk = (ProtoChunk)this.getHandle().a(this.centerChunkX + x, this.centerChunkZ + z);

            for(BlockPosition position : chunk.c()) {
               blockStates.add(this.getBlockState(position.u(), position.v(), position.w()));
            }
         }
      }

      return blockStates;
   }

   @Override
   public Biome getBiome(int x, int y, int z) {
      Preconditions.checkArgument(this.isInRegion(x, y, z), "Coordinates %s, %s, %s are not in the region", x, y, z);
      return super.getBiome(x, y, z);
   }

   @Override
   public void setBiome(int x, int y, int z, Holder<BiomeBase> biomeBase) {
      Preconditions.checkArgument(this.isInRegion(x, y, z), "Coordinates %s, %s, %s are not in the region", x, y, z);
      IChunkAccess chunk = this.getHandle().a(x >> 4, z >> 4, ChunkStatus.c);
      chunk.setBiome(x >> 2, y >> 2, z >> 2, biomeBase);
   }

   @Override
   public BlockState getBlockState(int x, int y, int z) {
      Preconditions.checkArgument(this.isInRegion(x, y, z), "Coordinates %s, %s, %s are not in the region", x, y, z);
      return super.getBlockState(x, y, z);
   }

   @Override
   public BlockData getBlockData(int x, int y, int z) {
      Preconditions.checkArgument(this.isInRegion(x, y, z), "Coordinates %s, %s, %s are not in the region", x, y, z);
      return super.getBlockData(x, y, z);
   }

   @Override
   public Material getType(int x, int y, int z) {
      Preconditions.checkArgument(this.isInRegion(x, y, z), "Coordinates %s, %s, %s are not in the region", x, y, z);
      return super.getType(x, y, z);
   }

   @Override
   public void setBlockData(int x, int y, int z, BlockData blockData) {
      Preconditions.checkArgument(this.isInRegion(x, y, z), "Coordinates %s, %s, %s are not in the region", x, y, z);
      super.setBlockData(x, y, z, blockData);
   }

   @Override
   public boolean generateTree(Location location, Random random, TreeType treeType) {
      Preconditions.checkArgument(
         this.isInRegion(location), "Coordinates %s, %s, %s are not in the region", location.getBlockX(), location.getBlockY(), location.getBlockZ()
      );
      return super.generateTree(location, random, treeType);
   }

   @Override
   public boolean generateTree(Location location, Random random, TreeType treeType, Consumer<BlockState> consumer) {
      Preconditions.checkArgument(
         this.isInRegion(location), "Coordinates %s, %s, %s are not in the region", location.getBlockX(), location.getBlockY(), location.getBlockZ()
      );
      return super.generateTree(location, random, treeType, consumer);
   }

   public Collection<Entity> getNMSEntities() {
      this.loadEntities();
      return new ArrayList<>(this.entities);
   }

   @Override
   public <T extends org.bukkit.entity.Entity> T spawn(Location location, Class<T> clazz, Consumer<T> function, SpawnReason reason) throws IllegalArgumentException {
      Preconditions.checkArgument(
         this.isInRegion(location), "Coordinates %s, %s, %s are not in the region", location.getBlockX(), location.getBlockY(), location.getBlockZ()
      );
      return super.spawn(location, clazz, function, reason);
   }

   @Override
   public void addEntityToWorld(Entity entity, SpawnReason reason) {
      this.entities.add(entity);
   }
}
