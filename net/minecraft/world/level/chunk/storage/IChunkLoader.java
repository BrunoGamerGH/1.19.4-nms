package net.minecraft.world.level.chunk.storage;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Codec;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ChunkProviderServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.levelgen.structure.PersistentStructureLegacy;
import net.minecraft.world.level.storage.WorldPersistentData;
import org.bukkit.Bukkit;
import org.spigotmc.SpigotConfig;

public class IChunkLoader implements AutoCloseable {
   public static final int d = 1493;
   private final IOWorker a;
   protected final DataFixer e;
   @Nullable
   private volatile PersistentStructureLegacy b;

   public IChunkLoader(Path path, DataFixer datafixer, boolean flag) {
      this.e = datafixer;
      this.a = new IOWorker(path, flag, "chunk");
   }

   public boolean a(ChunkCoordIntPair chunkcoordintpair, int i) {
      return this.a.a(chunkcoordintpair, i);
   }

   private boolean check(ChunkProviderServer cps, int x, int z) {
      ChunkCoordIntPair pos = new ChunkCoordIntPair(x, z);
      if (cps != null) {
         Preconditions.checkState(Bukkit.isPrimaryThread(), "primary thread");
         if (cps.b(x, z)) {
            return true;
         }
      }

      NBTTagCompound nbt;
      try {
         nbt = this.f(pos).get().orElse(null);
      } catch (ExecutionException | InterruptedException var8) {
         throw new RuntimeException(var8);
      }

      if (nbt != null) {
         NBTTagCompound level = nbt.p("Level");
         if (level.q("TerrainPopulated")) {
            return true;
         }

         ChunkStatus status = ChunkStatus.a(level.l("Status"));
         if (status != null && status.b(ChunkStatus.k)) {
            return true;
         }
      }

      return false;
   }

   public NBTTagCompound upgradeChunkTag(
      ResourceKey<WorldDimension> resourcekey,
      Supplier<WorldPersistentData> supplier,
      NBTTagCompound nbttagcompound,
      Optional<ResourceKey<Codec<? extends ChunkGenerator>>> optional,
      ChunkCoordIntPair pos,
      @Nullable GeneratorAccess generatoraccess
   ) {
      int i = a(nbttagcompound);
      if (i < 1466) {
         NBTTagCompound level = nbttagcompound.p("Level");
         if (level.q("TerrainPopulated") && !level.q("LightPopulated")) {
            ChunkProviderServer cps = generatoraccess == null ? null : ((WorldServer)generatoraccess).k();
            if (this.check(cps, pos.e - 1, pos.f) && this.check(cps, pos.e - 1, pos.f - 1) && this.check(cps, pos.e, pos.f - 1)) {
               level.a("LightPopulated", true);
            }
         }
      }

      if (i < 1493) {
         nbttagcompound = DataFixTypes.c.a(this.e, nbttagcompound, i, 1493);
         if (nbttagcompound.p("Level").q("hasLegacyStructureData")) {
            PersistentStructureLegacy persistentstructurelegacy = this.a(resourcekey, supplier);
            nbttagcompound = persistentstructurelegacy.a(nbttagcompound);
         }
      }

      boolean stopBelowZero = false;
      boolean belowZeroGenerationInExistingChunks = generatoraccess != null
         ? ((WorldServer)generatoraccess).spigotConfig.belowZeroGenerationInExistingChunks
         : SpigotConfig.belowZeroGenerationInExistingChunks;
      if (i <= 2730 && !belowZeroGenerationInExistingChunks) {
         stopBelowZero = ChunkStatus.o.d().equals(nbttagcompound.p("Level").l("Status"));
      }

      a(nbttagcompound, resourcekey, optional);
      nbttagcompound = DataFixTypes.c.a(this.e, nbttagcompound, Math.max(1493, i));
      if (i < SharedConstants.b().d().c()) {
         GameProfileSerializer.g(nbttagcompound);
      }

      if (stopBelowZero) {
         nbttagcompound.a("Status", ChunkStatus.n.d());
      }

      nbttagcompound.r("__context");
      return nbttagcompound;
   }

   private PersistentStructureLegacy a(ResourceKey<WorldDimension> resourcekey, Supplier<WorldPersistentData> supplier) {
      PersistentStructureLegacy persistentstructurelegacy = this.b;
      if (persistentstructurelegacy == null) {
         synchronized(this) {
            persistentstructurelegacy = this.b;
            if (persistentstructurelegacy == null) {
               this.b = persistentstructurelegacy = PersistentStructureLegacy.a(resourcekey, supplier.get());
            }
         }
      }

      return persistentstructurelegacy;
   }

   public static void a(
      NBTTagCompound nbttagcompound, ResourceKey<WorldDimension> resourcekey, Optional<ResourceKey<Codec<? extends ChunkGenerator>>> optional
   ) {
      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      nbttagcompound1.a("dimension", resourcekey.a().toString());
      optional.ifPresent(resourcekey1 -> nbttagcompound1.a("generator", resourcekey1.a().toString()));
      nbttagcompound.a("__context", nbttagcompound1);
   }

   public static int a(NBTTagCompound nbttagcompound) {
      return GameProfileSerializer.b(nbttagcompound, -1);
   }

   public CompletableFuture<Optional<NBTTagCompound>> f(ChunkCoordIntPair chunkcoordintpair) {
      return this.a.a(chunkcoordintpair);
   }

   public void a(ChunkCoordIntPair chunkcoordintpair, NBTTagCompound nbttagcompound) {
      this.a.a(chunkcoordintpair, nbttagcompound);
      if (this.b != null) {
         this.b.a(chunkcoordintpair.a());
      }
   }

   public void o() {
      this.a.a(true).join();
   }

   @Override
   public void close() throws IOException {
      this.a.close();
   }

   public ChunkScanAccess p() {
      return this.a;
   }
}
