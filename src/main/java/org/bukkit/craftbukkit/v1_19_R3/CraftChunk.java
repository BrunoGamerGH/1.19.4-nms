package org.bukkit.craftbukkit.v1_19_R3;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.mojang.serialization.Codec;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.thread.ThreadedMailbox;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkSection;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.DataPaletteBlock;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.NibbleArray;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import net.minecraft.world.level.chunk.storage.ChunkRegionLoader;
import net.minecraft.world.level.chunk.storage.EntityStorage;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.lighting.LightEngine;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

public class CraftChunk implements Chunk {
   private WeakReference<net.minecraft.world.level.chunk.Chunk> weakChunk;
   private final WorldServer worldServer;
   private final int x;
   private final int z;
   private static final DataPaletteBlock<IBlockData> emptyBlockIDs = new DataPaletteBlock<>(Block.o, Blocks.a.o(), DataPaletteBlock.d.d);
   private static final byte[] emptyLight = new byte[2048];

   static {
      Arrays.fill(emptyLight, (byte)-1);
   }

   public CraftChunk(net.minecraft.world.level.chunk.Chunk chunk) {
      this.weakChunk = new WeakReference<>(chunk);
      this.worldServer = this.getHandle().q;
      this.x = this.getHandle().f().e;
      this.z = this.getHandle().f().f;
   }

   public CraftChunk(WorldServer worldServer, int x, int z) {
      this.weakChunk = new WeakReference<>(null);
      this.worldServer = worldServer;
      this.x = x;
      this.z = z;
   }

   public World getWorld() {
      return this.worldServer.getWorld();
   }

   public CraftWorld getCraftWorld() {
      return (CraftWorld)this.getWorld();
   }

   public net.minecraft.world.level.chunk.Chunk getHandle() {
      net.minecraft.world.level.chunk.Chunk c = this.weakChunk.get();
      if (c == null) {
         c = this.worldServer.d(this.x, this.z);
         this.weakChunk = new WeakReference<>(c);
      }

      return c;
   }

   void breakLink() {
      this.weakChunk.clear();
   }

   public int getX() {
      return this.x;
   }

   public int getZ() {
      return this.z;
   }

   @Override
   public String toString() {
      return "CraftChunk{x=" + this.getX() + "z=" + this.getZ() + 125;
   }

   public org.bukkit.block.Block getBlock(int x, int y, int z) {
      validateChunkCoordinates(this.getHandle().v_(), this.getHandle().ai(), x, y, z);
      return new CraftBlock(this.worldServer, new BlockPosition(this.x << 4 | x, y, this.z << 4 | z));
   }

   public boolean isEntitiesLoaded() {
      return this.getCraftWorld().getHandle().L.a(ChunkCoordIntPair.c(this.x, this.z));
   }

   public org.bukkit.entity.Entity[] getEntities() {
      if (!this.isLoaded()) {
         this.getWorld().getChunkAt(this.x, this.z);
      }

      PersistentEntitySectionManager<Entity> entityManager = this.getCraftWorld().getHandle().L;
      long pair = ChunkCoordIntPair.c(this.x, this.z);
      if (entityManager.a(pair)) {
         return entityManager.getEntities(new ChunkCoordIntPair(this.x, this.z))
            .stream()
            .map(Entity::getBukkitEntity)
            .filter(Objects::nonNull)
            .toArray(var0 -> new org.bukkit.entity.Entity[var0]);
      } else {
         entityManager.b(pair);
         ThreadedMailbox<Runnable> mailbox = ((EntityStorage)entityManager.d).h;
         BooleanSupplier supplier = () -> {
            if (entityManager.a(pair)) {
               return true;
            } else {
               if (!entityManager.isPending(pair)) {
                  entityManager.b(pair);
               }

               entityManager.a();
               return entityManager.a(pair);
            }
         };

         while(!supplier.getAsBoolean()) {
            if (mailbox.b() != 0) {
               mailbox.run();
            } else {
               Thread.yield();
               LockSupport.parkNanos("waiting for entity loading", 100000L);
            }
         }

         return entityManager.getEntities(new ChunkCoordIntPair(this.x, this.z))
            .stream()
            .map(Entity::getBukkitEntity)
            .filter(Objects::nonNull)
            .toArray(var0 -> new org.bukkit.entity.Entity[var0]);
      }
   }

   public BlockState[] getTileEntities() {
      if (!this.isLoaded()) {
         this.getWorld().getChunkAt(this.x, this.z);
      }

      int index = 0;
      net.minecraft.world.level.chunk.Chunk chunk = this.getHandle();
      BlockState[] entities = new BlockState[chunk.i.size()];

      Object[] var7;
      for(Object obj : var7 = chunk.i.keySet().toArray()) {
         if (obj instanceof BlockPosition position) {
            entities[index++] = this.worldServer.getWorld().getBlockAt(position.u(), position.v(), position.w()).getState();
         }
      }

      return entities;
   }

   public boolean isLoaded() {
      return this.getWorld().isChunkLoaded(this);
   }

   public boolean load() {
      return this.getWorld().loadChunk(this.getX(), this.getZ(), true);
   }

   public boolean load(boolean generate) {
      return this.getWorld().loadChunk(this.getX(), this.getZ(), generate);
   }

   public boolean unload() {
      return this.getWorld().unloadChunk(this.getX(), this.getZ());
   }

   public boolean isSlimeChunk() {
      return SeededRandom.a(this.getX(), this.getZ(), this.getWorld().getSeed(), (long)this.worldServer.spigotConfig.slimeSeed).a(10) == 0;
   }

   public boolean unload(boolean save) {
      return this.getWorld().unloadChunk(this.getX(), this.getZ(), save);
   }

   public boolean isForceLoaded() {
      return this.getWorld().isChunkForceLoaded(this.getX(), this.getZ());
   }

   public void setForceLoaded(boolean forced) {
      this.getWorld().setChunkForceLoaded(this.getX(), this.getZ(), forced);
   }

   public boolean addPluginChunkTicket(Plugin plugin) {
      return this.getWorld().addPluginChunkTicket(this.getX(), this.getZ(), plugin);
   }

   public boolean removePluginChunkTicket(Plugin plugin) {
      return this.getWorld().removePluginChunkTicket(this.getX(), this.getZ(), plugin);
   }

   public Collection<Plugin> getPluginChunkTickets() {
      return this.getWorld().getPluginChunkTickets(this.getX(), this.getZ());
   }

   public long getInhabitedTime() {
      return this.getHandle().u();
   }

   public void setInhabitedTime(long ticks) {
      Preconditions.checkArgument(ticks >= 0L, "ticks cannot be negative");
      this.getHandle().b(ticks);
   }

   public boolean contains(BlockData block) {
      Preconditions.checkArgument(block != null, "Block cannot be null");
      Predicate<IBlockData> nms = Predicates.equalTo(((CraftBlockData)block).getState());

      ChunkSection[] var6;
      for(ChunkSection section : var6 = this.getHandle().d()) {
         if (section != null && section.i().a(nms)) {
            return true;
         }
      }

      return false;
   }

   public boolean contains(Biome biome) {
      Preconditions.checkArgument(biome != null, "Biome cannot be null");
      Predicate<Holder<BiomeBase>> nms = Predicates.equalTo(CraftBlock.biomeToBiomeBase(this.getHandle().biomeRegistry, biome));

      ChunkSection[] var6;
      for(ChunkSection section : var6 = this.getHandle().d()) {
         if (section != null && section.j().a(nms)) {
            return true;
         }
      }

      return false;
   }

   public ChunkSnapshot getChunkSnapshot() {
      return this.getChunkSnapshot(true, false, false);
   }

   public ChunkSnapshot getChunkSnapshot(boolean includeMaxBlockY, boolean includeBiome, boolean includeBiomeTempRain) {
      net.minecraft.world.level.chunk.Chunk chunk = this.getHandle();
      ChunkSection[] cs = chunk.d();
      DataPaletteBlock[] sectionBlockIDs = new DataPaletteBlock[cs.length];
      byte[][] sectionSkyLights = new byte[cs.length][];
      byte[][] sectionEmitLights = new byte[cs.length][];
      boolean[] sectionEmpty = new boolean[cs.length];
      PalettedContainerRO[] biome = !includeBiome && !includeBiomeTempRain ? null : new DataPaletteBlock[cs.length];
      IRegistry<BiomeBase> iregistry = this.worldServer.u_().d(Registries.an);
      Codec<PalettedContainerRO<Holder<BiomeBase>>> biomeCodec = DataPaletteBlock.b(iregistry.t(), iregistry.r(), DataPaletteBlock.d.e, iregistry.f(Biomes.b));

      for(int i = 0; i < cs.length; ++i) {
         NBTTagCompound data = new NBTTagCompound();
         data.a("block_states", (NBTBase)ChunkRegionLoader.h.encodeStart(DynamicOpsNBT.a, cs[i].i()).get().left().get());
         sectionBlockIDs[i] = (DataPaletteBlock)ChunkRegionLoader.h.parse(DynamicOpsNBT.a, data.p("block_states")).get().left().get();
         LightEngine lightengine = chunk.q.l_();
         NibbleArray skyLightArray = lightengine.a(EnumSkyBlock.a).a(SectionPosition.a(this.x, i, this.z));
         if (skyLightArray == null) {
            sectionSkyLights[i] = emptyLight;
         } else {
            sectionSkyLights[i] = new byte[2048];
            System.arraycopy(skyLightArray.a(), 0, sectionSkyLights[i], 0, 2048);
         }

         NibbleArray emitLightArray = lightengine.a(EnumSkyBlock.b).a(SectionPosition.a(this.x, i, this.z));
         if (emitLightArray == null) {
            sectionEmitLights[i] = emptyLight;
         } else {
            sectionEmitLights[i] = new byte[2048];
            System.arraycopy(emitLightArray.a(), 0, sectionEmitLights[i], 0, 2048);
         }

         if (biome != null) {
            data.a("biomes", (NBTBase)biomeCodec.encodeStart(DynamicOpsNBT.a, cs[i].j()).get().left().get());
            biome[i] = (PalettedContainerRO)biomeCodec.parse(DynamicOpsNBT.a, data.p("biomes")).get().left().get();
         }
      }

      HeightMap hmap = null;
      if (includeMaxBlockY) {
         hmap = new HeightMap(chunk, HeightMap.Type.e);
         hmap.a(chunk, HeightMap.Type.e, chunk.g.get(HeightMap.Type.e).a());
      }

      World world = this.getWorld();
      return new CraftChunkSnapshot(
         this.getX(),
         this.getZ(),
         chunk.v_(),
         chunk.ai(),
         world.getName(),
         world.getFullTime(),
         sectionBlockIDs,
         sectionSkyLights,
         sectionEmitLights,
         sectionEmpty,
         hmap,
         iregistry,
         biome
      );
   }

   public PersistentDataContainer getPersistentDataContainer() {
      return this.getHandle().persistentDataContainer;
   }

   public static ChunkSnapshot getEmptyChunkSnapshot(int x, int z, CraftWorld world, boolean includeBiome, boolean includeBiomeTempRain) {
      IChunkAccess actual = world.getHandle().a(x, z, !includeBiome && !includeBiomeTempRain ? ChunkStatus.c : ChunkStatus.f);
      int hSection = actual.aj();
      DataPaletteBlock[] blockIDs = new DataPaletteBlock[hSection];
      byte[][] skyLight = new byte[hSection][];
      byte[][] emitLight = new byte[hSection][];
      boolean[] empty = new boolean[hSection];
      IRegistry<BiomeBase> iregistry = world.getHandle().u_().d(Registries.an);
      DataPaletteBlock[] biome = !includeBiome && !includeBiomeTempRain ? null : new DataPaletteBlock[hSection];
      Codec<PalettedContainerRO<Holder<BiomeBase>>> biomeCodec = DataPaletteBlock.b(iregistry.t(), iregistry.r(), DataPaletteBlock.d.e, iregistry.f(Biomes.b));

      for(int i = 0; i < hSection; ++i) {
         blockIDs[i] = emptyBlockIDs;
         skyLight[i] = emptyLight;
         emitLight[i] = emptyLight;
         empty[i] = true;
         if (biome != null) {
            biome[i] = (DataPaletteBlock)biomeCodec.parse(
                  DynamicOpsNBT.a, (NBTBase)biomeCodec.encodeStart(DynamicOpsNBT.a, actual.b(i).j()).get().left().get()
               )
               .get()
               .left()
               .get();
         }
      }

      return new CraftChunkSnapshot(
         x,
         z,
         world.getMinHeight(),
         world.getMaxHeight(),
         world.getName(),
         world.getFullTime(),
         blockIDs,
         skyLight,
         emitLight,
         empty,
         new HeightMap(actual, HeightMap.Type.e),
         iregistry,
         biome
      );
   }

   static void validateChunkCoordinates(int minY, int maxY, int x, int y, int z) {
      Preconditions.checkArgument(x >= 0 && x <= 15, "x out of range (expected 0-15, got %s)", x);
      Preconditions.checkArgument(minY <= y && y <= maxY, "y out of range (expected %s-%s, got %s)", minY, maxY, y);
      Preconditions.checkArgument(z >= 0 && z <= 15, "z out of range (expected 0-15, got %s)", z);
   }
}
