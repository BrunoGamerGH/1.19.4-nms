package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.progress.WorldLoadListener;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.util.thread.IAsyncTaskHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.LocalMobCapCalculator;
import net.minecraft.world.level.SpawnerCreature;
import net.minecraft.world.level.World;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.IChunkProvider;
import net.minecraft.world.level.chunk.storage.ChunkScanAccess;
import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.Convertable;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.WorldPersistentData;
import org.bukkit.entity.SpawnCategory;

public class ChunkProviderServer extends IChunkProvider {
   private static final List<ChunkStatus> b = ChunkStatus.a();
   private final ChunkMapDistance c;
   final WorldServer d;
   final Thread e;
   final LightEngineThreaded f;
   private final ChunkProviderServer.b g;
   public final PlayerChunkMap a;
   private final WorldPersistentData h;
   private long i;
   public boolean j = true;
   public boolean k = true;
   private static final int l = 4;
   private final long[] m = new long[4];
   private final ChunkStatus[] n = new ChunkStatus[4];
   private final IChunkAccess[] o = new IChunkAccess[4];
   @Nullable
   @VisibleForDebug
   private SpawnerCreature.d p;

   public ChunkProviderServer(
      WorldServer worldserver,
      Convertable.ConversionSession convertable_conversionsession,
      DataFixer datafixer,
      StructureTemplateManager structuretemplatemanager,
      Executor executor,
      ChunkGenerator chunkgenerator,
      int i,
      int j,
      boolean flag,
      WorldLoadListener worldloadlistener,
      ChunkStatusUpdateListener chunkstatusupdatelistener,
      Supplier<WorldPersistentData> supplier
   ) {
      this.d = worldserver;
      this.g = new ChunkProviderServer.b(worldserver);
      this.e = Thread.currentThread();
      File file = convertable_conversionsession.a(worldserver.ab()).resolve("data").toFile();
      file.mkdirs();
      this.h = new WorldPersistentData(file, datafixer);
      this.a = new PlayerChunkMap(
         worldserver,
         convertable_conversionsession,
         datafixer,
         structuretemplatemanager,
         executor,
         this.g,
         this,
         chunkgenerator,
         worldloadlistener,
         chunkstatusupdatelistener,
         supplier,
         i,
         flag
      );
      this.f = this.a.e();
      this.c = this.a.j();
      this.c.b(j);
      this.r();
   }

   public boolean isChunkLoaded(int chunkX, int chunkZ) {
      PlayerChunk chunk = this.a.a(ChunkCoordIntPair.c(chunkX, chunkZ));
      if (chunk == null) {
         return false;
      } else {
         return chunk.getFullChunkNow() != null;
      }
   }

   public LightEngineThreaded a() {
      return this.f;
   }

   @Nullable
   private PlayerChunk b(long i) {
      return this.a.b(i);
   }

   public int b() {
      return this.a.h();
   }

   private void a(long i, IChunkAccess ichunkaccess, ChunkStatus chunkstatus) {
      for(int j = 3; j > 0; --j) {
         this.m[j] = this.m[j - 1];
         this.n[j] = this.n[j - 1];
         this.o[j] = this.o[j - 1];
      }

      this.m[0] = i;
      this.n[0] = chunkstatus;
      this.o[0] = ichunkaccess;
   }

   @Nullable
   @Override
   public IChunkAccess a(int i, int j, ChunkStatus chunkstatus, boolean flag) {
      if (Thread.currentThread() != this.e) {
         return CompletableFuture.<IChunkAccess>supplyAsync(() -> this.a(i, j, chunkstatus, flag), this.g).join();
      } else {
         GameProfilerFiller gameprofilerfiller = this.d.ac();
         gameprofilerfiller.d("getChunk");
         long k = ChunkCoordIntPair.c(i, j);

         for(int l = 0; l < 4; ++l) {
            if (k == this.m[l] && chunkstatus == this.n[l]) {
               IChunkAccess ichunkaccess = this.o[l];
               if (ichunkaccess != null) {
                  return ichunkaccess;
               }
            }
         }

         gameprofilerfiller.d("getChunkCacheMiss");
         this.d.timings.syncChunkLoadTimer.startTiming();
         CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = this.c(i, j, chunkstatus, flag);
         ChunkProviderServer.b chunkproviderserver_b = this.g;
         chunkproviderserver_b.c(completablefuture::isDone);
         this.d.timings.syncChunkLoadTimer.stopTiming();
         IChunkAccess ichunkaccess = (IChunkAccess)((Either)completablefuture.join()).map(ichunkaccess1 -> ichunkaccess1, playerchunk_failure -> {
            if (flag) {
               throw (IllegalStateException)SystemUtils.b(new IllegalStateException("Chunk not there when requested: " + playerchunk_failure));
            } else {
               return null;
            }
         });
         this.a(k, ichunkaccess, chunkstatus);
         return ichunkaccess;
      }
   }

   @Nullable
   @Override
   public Chunk a(int i, int j) {
      if (Thread.currentThread() != this.e) {
         return null;
      } else {
         this.d.ac().d("getChunkNow");
         long k = ChunkCoordIntPair.c(i, j);

         for(int l = 0; l < 4; ++l) {
            if (k == this.m[l] && this.n[l] == ChunkStatus.o) {
               IChunkAccess ichunkaccess = this.o[l];
               return ichunkaccess instanceof Chunk ? (Chunk)ichunkaccess : null;
            }
         }

         PlayerChunk playerchunk = this.b(k);
         if (playerchunk == null) {
            return null;
         } else {
            Either<IChunkAccess, PlayerChunk.Failure> either = (Either)playerchunk.b(ChunkStatus.o).getNow(null);
            if (either == null) {
               return null;
            } else {
               IChunkAccess ichunkaccess1 = (IChunkAccess)either.left().orElse(null);
               if (ichunkaccess1 != null) {
                  this.a(k, ichunkaccess1, ChunkStatus.o);
                  if (ichunkaccess1 instanceof Chunk) {
                     return (Chunk)ichunkaccess1;
                  }
               }

               return null;
            }
         }
      }
   }

   private void r() {
      Arrays.fill(this.m, ChunkCoordIntPair.a);
      Arrays.fill(this.n, null);
      Arrays.fill(this.o, null);
   }

   public CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> b(int i, int j, ChunkStatus chunkstatus, boolean flag) {
      boolean flag1 = Thread.currentThread() == this.e;
      CompletableFuture completablefuture;
      if (flag1) {
         completablefuture = this.c(i, j, chunkstatus, flag);
         ChunkProviderServer.b chunkproviderserver_b = this.g;
         chunkproviderserver_b.c(completablefuture::isDone);
      } else {
         completablefuture = CompletableFuture.<CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>>supplyAsync(
               () -> this.c(i, j, chunkstatus, flag), this.g
            )
            .thenCompose(completablefuture1 -> completablefuture1);
      }

      return completablefuture;
   }

   private CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> c(int i, int j, ChunkStatus chunkstatus, boolean flag) {
      ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
      long k = chunkcoordintpair.a();
      int l = 33 + ChunkStatus.a(chunkstatus);
      PlayerChunk playerchunk = this.b(k);
      boolean currentlyUnloading = false;
      if (playerchunk != null) {
         PlayerChunk.State oldChunkState = PlayerChunk.c(playerchunk.p);
         PlayerChunk.State currentChunkState = PlayerChunk.c(playerchunk.k());
         currentlyUnloading = oldChunkState.a(PlayerChunk.State.b) && !currentChunkState.a(PlayerChunk.State.b);
      }

      if (flag && !currentlyUnloading) {
         this.c.a(TicketType.h, chunkcoordintpair, l, chunkcoordintpair);
         if (this.a(playerchunk, l)) {
            GameProfilerFiller gameprofilerfiller = this.d.ac();
            gameprofilerfiller.a("chunkLoad");
            this.s();
            playerchunk = this.b(k);
            gameprofilerfiller.c();
            if (this.a(playerchunk, l)) {
               throw (IllegalStateException)SystemUtils.b(new IllegalStateException("No chunk holder after ticket has been added"));
            }
         }
      }

      return this.a(playerchunk, l) ? PlayerChunk.b : playerchunk.a(chunkstatus, this.a);
   }

   private boolean a(@Nullable PlayerChunk playerchunk, int i) {
      return playerchunk == null || playerchunk.p > i;
   }

   @Override
   public boolean b(int i, int j) {
      PlayerChunk playerchunk = this.b(new ChunkCoordIntPair(i, j).a());
      int k = 33 + ChunkStatus.a(ChunkStatus.o);
      return !this.a(playerchunk, k);
   }

   @Override
   public IBlockAccess c(int i, int j) {
      long k = ChunkCoordIntPair.c(i, j);
      PlayerChunk playerchunk = this.b(k);
      if (playerchunk == null) {
         return null;
      } else {
         int l = b.size() - 1;

         while(true) {
            ChunkStatus chunkstatus = b.get(l);
            Optional<IChunkAccess> optional = ((Either)playerchunk.a(chunkstatus).getNow(PlayerChunk.a)).left();
            if (optional.isPresent()) {
               return optional.get();
            }

            if (chunkstatus == ChunkStatus.l.e()) {
               return null;
            }

            --l;
         }
      }
   }

   public World c() {
      return this.d;
   }

   public boolean d() {
      return this.g.x();
   }

   boolean s() {
      boolean flag = this.c.a(this.a);
      boolean flag1 = this.a.g();
      if (!flag && !flag1) {
         return false;
      } else {
         this.r();
         return true;
      }
   }

   public boolean a(long i) {
      PlayerChunk playerchunk = this.b(i);
      if (playerchunk == null) {
         return false;
      } else if (!this.d.a(i)) {
         return false;
      } else {
         Either<Chunk, PlayerChunk.Failure> either = (Either)playerchunk.a().getNow(null);
         return either != null && either.left().isPresent();
      }
   }

   public void a(boolean flag) {
      this.s();
      this.a.a(flag);
   }

   @Override
   public void close() throws IOException {
      this.close(true);
   }

   public void close(boolean save) throws IOException {
      if (save) {
         this.a(true);
      }

      this.f.close();
      this.a.close();
   }

   public void purgeUnload() {
      this.d.ac().a("purge");
      this.c.a();
      this.s();
      this.d.ac().b("unload");
      this.a.a(() -> true);
      this.d.ac().c();
      this.r();
   }

   @Override
   public void a(BooleanSupplier booleansupplier, boolean flag) {
      this.d.ac().a("purge");
      this.d.timings.doChunkMap.startTiming();
      this.c.a();
      this.s();
      this.d.timings.doChunkMap.stopTiming();
      this.d.ac().b("chunks");
      if (flag) {
         this.t();
      }

      this.d.timings.doChunkUnload.startTiming();
      this.d.ac().b("unload");
      this.a.a(booleansupplier);
      this.d.timings.doChunkUnload.stopTiming();
      this.d.ac().c();
      this.r();
   }

   private void t() {
      long i = this.d.U();
      long j = i - this.i;
      this.i = i;
      boolean flag = this.d.ae();
      if (flag) {
         this.a.l();
      } else {
         WorldData worlddata = this.d.n_();
         GameProfilerFiller gameprofilerfiller = this.d.ac();
         gameprofilerfiller.a("pollingChunks");
         int k = this.d.W().c(GameRules.n);
         boolean flag1 = this.d.ticksPerSpawnCategory.getLong(SpawnCategory.ANIMAL) != 0L
            && worlddata.e() % this.d.ticksPerSpawnCategory.getLong(SpawnCategory.ANIMAL) == 0L;
         gameprofilerfiller.a("naturalSpawnCount");
         int l = this.c.b();
         SpawnerCreature.d spawnercreature_d = SpawnerCreature.a(l, this.d.y(), this::a, new LocalMobCapCalculator(this.a));
         this.p = spawnercreature_d;
         gameprofilerfiller.b("filteringLoadedChunks");
         List<ChunkProviderServer.a> list = Lists.newArrayListWithCapacity(l);

         for(PlayerChunk playerchunk : this.a.k()) {
            Chunk chunk = playerchunk.d();
            if (chunk != null) {
               list.add(new ChunkProviderServer.a(chunk, playerchunk));
            }
         }

         gameprofilerfiller.b("spawnAndTick");
         boolean flag2 = this.d.W().b(GameRules.e) && !this.d.v().isEmpty();
         Collections.shuffle(list);

         for(ChunkProviderServer.a chunkproviderserver_a : list) {
            Chunk chunk1 = chunkproviderserver_a.a;
            ChunkCoordIntPair chunkcoordintpair = chunk1.f();
            if (this.d.a(chunkcoordintpair) && this.a.d(chunkcoordintpair)) {
               chunk1.a(j);
               if (flag2 && (this.j || this.k) && this.d.p_().a(chunkcoordintpair) && this.a.anyPlayerCloseEnoughForSpawning(chunkcoordintpair, true)) {
                  SpawnerCreature.a(this.d, chunk1, spawnercreature_d, this.k, this.j, flag1);
               }

               if (this.d.a(chunkcoordintpair.a())) {
                  this.d.timings.doTickTiles.startTiming();
                  this.d.a(chunk1, k);
                  this.d.timings.doTickTiles.stopTiming();
               }
            }
         }

         gameprofilerfiller.b("customSpawners");
         if (flag2) {
            this.d.a(this.j, this.k);
         }

         gameprofilerfiller.b("broadcast");
         list.forEach(chunkproviderserver_a1 -> chunkproviderserver_a1.b.a(chunkproviderserver_a1.a));
         gameprofilerfiller.c();
         gameprofilerfiller.c();
         this.d.timings.tracker.startTiming();
         this.a.l();
         this.d.timings.tracker.stopTiming();
      }
   }

   private void a(long i, Consumer<Chunk> consumer) {
      PlayerChunk playerchunk = this.b(i);
      if (playerchunk != null) {
         ((Either)playerchunk.c().getNow(PlayerChunk.c)).left().ifPresent(consumer);
      }
   }

   @Override
   public String e() {
      return Integer.toString(this.j());
   }

   @VisibleForTesting
   public int f() {
      return this.g.bo();
   }

   public ChunkGenerator g() {
      return this.a.a();
   }

   public ChunkGeneratorStructureState h() {
      return this.a.b();
   }

   public RandomState i() {
      return this.a.c();
   }

   @Override
   public int j() {
      return this.a.i();
   }

   public void a(BlockPosition blockposition) {
      int i = SectionPosition.a(blockposition.u());
      int j = SectionPosition.a(blockposition.w());
      PlayerChunk playerchunk = this.b(ChunkCoordIntPair.c(i, j));
      if (playerchunk != null) {
         playerchunk.a(blockposition);
      }
   }

   @Override
   public void a(EnumSkyBlock enumskyblock, SectionPosition sectionposition) {
      this.g.execute(() -> {
         PlayerChunk playerchunk = this.b(sectionposition.r().a());
         if (playerchunk != null) {
            playerchunk.a(enumskyblock, sectionposition.b());
         }
      });
   }

   public <T> void a(TicketType<T> tickettype, ChunkCoordIntPair chunkcoordintpair, int i, T t0) {
      this.c.c(tickettype, chunkcoordintpair, i, t0);
   }

   public <T> void b(TicketType<T> tickettype, ChunkCoordIntPair chunkcoordintpair, int i, T t0) {
      this.c.d(tickettype, chunkcoordintpair, i, t0);
   }

   @Override
   public void a(ChunkCoordIntPair chunkcoordintpair, boolean flag) {
      this.c.a(chunkcoordintpair, flag);
   }

   public void a(EntityPlayer entityplayer) {
      if (!entityplayer.dB()) {
         this.a.a(entityplayer);
      }
   }

   public void a(Entity entity) {
      this.a.b(entity);
   }

   public void b(Entity entity) {
      this.a.a(entity);
   }

   public void a(Entity entity, Packet<?> packet) {
      this.a.b(entity, packet);
   }

   public void b(Entity entity, Packet<?> packet) {
      this.a.a(entity, packet);
   }

   public void a(int i) {
      this.a.a(i);
   }

   public void b(int i) {
      this.c.b(i);
   }

   @Override
   public void a(boolean flag, boolean flag1) {
      this.j = flag;
      this.k = flag1;
   }

   public String a(ChunkCoordIntPair chunkcoordintpair) {
      return this.a.a(chunkcoordintpair);
   }

   public WorldPersistentData k() {
      return this.h;
   }

   public VillagePlace l() {
      return this.a.m();
   }

   public ChunkScanAccess m() {
      return this.a.p();
   }

   @Nullable
   @VisibleForDebug
   public SpawnerCreature.d n() {
      return this.p;
   }

   public void o() {
      this.c.e();
   }

   private static record a(Chunk chunk, PlayerChunk holder) {
      private final Chunk a;
      private final PlayerChunk b;

      private a(Chunk chunk, PlayerChunk holder) {
         this.a = chunk;
         this.b = holder;
      }
   }

   private final class b extends IAsyncTaskHandler<Runnable> {
      b(World world) {
         super("Chunk source main thread executor for " + world.ab().a());
      }

      @Override
      protected Runnable f(Runnable runnable) {
         return runnable;
      }

      @Override
      protected boolean e(Runnable runnable) {
         return true;
      }

      @Override
      protected boolean at() {
         return true;
      }

      @Override
      protected Thread au() {
         return ChunkProviderServer.this.e;
      }

      @Override
      protected void d(Runnable runnable) {
         ChunkProviderServer.this.d.ac().d("runTask");
         super.d(runnable);
      }

      @Override
      public boolean x() {
         try {
            if (!ChunkProviderServer.this.s()) {
               ChunkProviderServer.this.f.a();
               return super.x();
            }
         } finally {
            ChunkProviderServer.this.a.callbackExecutor.run();
         }

         return true;
      }
   }
}
