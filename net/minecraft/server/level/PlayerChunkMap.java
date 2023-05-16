package net.minecraft.server.level;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonElement;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.ReportedException;
import net.minecraft.SystemUtils;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundChunksBiomesPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.protocol.game.PacketPlayOutAttachEntity;
import net.minecraft.network.protocol.game.PacketPlayOutMount;
import net.minecraft.network.protocol.game.PacketPlayOutViewCentre;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.progress.WorldLoadListener;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.util.CSVWriter;
import net.minecraft.util.MathHelper;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.util.thread.IAsyncTaskHandler;
import net.minecraft.util.thread.Mailbox;
import net.minecraft.util.thread.ThreadedMailbox;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.animal.EntityWaterAnimal;
import net.minecraft.world.entity.boss.EntityComplexPart;
import net.minecraft.world.entity.npc.NPC;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.chunk.ChunkConverter;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.ILightAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.ProtoChunkExtension;
import net.minecraft.world.level.chunk.storage.ChunkRegionLoader;
import net.minecraft.world.level.chunk.storage.IChunkLoader;
import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
import net.minecraft.world.level.levelgen.ChunkGeneratorAbstract;
import net.minecraft.world.level.levelgen.GeneratorSettingBase;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.Convertable;
import net.minecraft.world.level.storage.WorldPersistentData;
import net.minecraft.world.phys.Vec3D;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableObject;
import org.bukkit.craftbukkit.v1_19_R3.generator.CustomChunkGenerator;
import org.slf4j.Logger;
import org.spigotmc.AsyncCatcher;
import org.spigotmc.TrackingRange;

public class PlayerChunkMap extends IChunkLoader implements PlayerChunk.e {
   private static final byte f = -1;
   private static final byte g = 0;
   private static final byte h = 1;
   private static final Logger i = LogUtils.getLogger();
   private static final int j = 200;
   private static final int k = 20;
   private static final int l = 10000;
   private static final int m = 3;
   public static final int a = 33;
   public static final int b = 33 + ChunkStatus.b();
   public static final int c = 31;
   public final Long2ObjectLinkedOpenHashMap<PlayerChunk> n = new Long2ObjectLinkedOpenHashMap();
   public volatile Long2ObjectLinkedOpenHashMap<PlayerChunk> o;
   private final Long2ObjectLinkedOpenHashMap<PlayerChunk> p;
   private final LongSet q;
   public final WorldServer r;
   private final LightEngineThreaded s;
   private final IAsyncTaskHandler<Runnable> t;
   public ChunkGenerator u;
   private final RandomState v;
   private final ChunkGeneratorStructureState w;
   private final Supplier<WorldPersistentData> x;
   private final VillagePlace y;
   public final LongSet z;
   private boolean A;
   private final ChunkTaskQueueSorter B;
   private final Mailbox<ChunkTaskQueueSorter.a<Runnable>> C;
   private final Mailbox<ChunkTaskQueueSorter.a<Runnable>> D;
   public final WorldLoadListener E;
   private final ChunkStatusUpdateListener F;
   public final PlayerChunkMap.a G;
   private final AtomicInteger H;
   private final StructureTemplateManager I;
   private final String J;
   private final PlayerMap K;
   public final Int2ObjectMap<PlayerChunkMap.EntityTracker> L;
   private final Long2ByteMap M;
   private final Long2LongMap N;
   private final Queue<Runnable> O;
   int P;
   public final PlayerChunkMap.CallbackExecutor callbackExecutor = new PlayerChunkMap.CallbackExecutor();

   public PlayerChunkMap(
      WorldServer worldserver,
      Convertable.ConversionSession convertable_conversionsession,
      DataFixer datafixer,
      StructureTemplateManager structuretemplatemanager,
      Executor executor,
      IAsyncTaskHandler<Runnable> iasynctaskhandler,
      ILightAccess ilightaccess,
      ChunkGenerator chunkgenerator,
      WorldLoadListener worldloadlistener,
      ChunkStatusUpdateListener chunkstatusupdatelistener,
      Supplier<WorldPersistentData> supplier,
      int i,
      boolean flag
   ) {
      super(convertable_conversionsession.a(worldserver.ab()).resolve("region"), datafixer, flag);
      this.o = this.n.clone();
      this.p = new Long2ObjectLinkedOpenHashMap();
      this.q = new LongOpenHashSet();
      this.z = new LongOpenHashSet();
      this.H = new AtomicInteger();
      this.K = new PlayerMap();
      this.L = new Int2ObjectOpenHashMap();
      this.M = new Long2ByteOpenHashMap();
      this.N = new Long2LongOpenHashMap();
      this.O = Queues.newConcurrentLinkedQueue();
      this.I = structuretemplatemanager;
      Path path = convertable_conversionsession.a(worldserver.ab());
      this.J = path.getFileName().toString();
      this.r = worldserver;
      this.u = chunkgenerator;
      if (chunkgenerator instanceof CustomChunkGenerator) {
         chunkgenerator = ((CustomChunkGenerator)chunkgenerator).getDelegate();
      }

      IRegistryCustom iregistrycustom = worldserver.u_();
      long j = worldserver.A();
      if (chunkgenerator instanceof ChunkGeneratorAbstract chunkgeneratorabstract) {
         this.v = RandomState.a(chunkgeneratorabstract.g().a(), iregistrycustom.b(Registries.av), j);
      } else {
         this.v = RandomState.a(GeneratorSettingBase.e(), iregistrycustom.b(Registries.av), j);
      }

      this.w = chunkgenerator.createState(iregistrycustom.b(Registries.az), this.v, j, worldserver.spigotConfig);
      this.t = iasynctaskhandler;
      ThreadedMailbox<Runnable> threadedmailbox = ThreadedMailbox.a(executor, "worldgen");
      Mailbox<Runnable> mailbox = Mailbox.a("main", iasynctaskhandler::i);
      this.E = worldloadlistener;
      this.F = chunkstatusupdatelistener;
      ThreadedMailbox<Runnable> threadedmailbox1 = ThreadedMailbox.a(executor, "light");
      this.B = new ChunkTaskQueueSorter(ImmutableList.of(threadedmailbox, mailbox, threadedmailbox1), executor, Integer.MAX_VALUE);
      this.C = this.B.a(threadedmailbox, false);
      this.D = this.B.a(mailbox, false);
      this.s = new LightEngineThreaded(ilightaccess, this, this.r.q_().g(), threadedmailbox1, this.B.a(threadedmailbox1, false));
      this.G = new PlayerChunkMap.a(executor, iasynctaskhandler);
      this.x = supplier;
      this.y = new VillagePlace(path.resolve("poi"), datafixer, flag, iregistrycustom, worldserver);
      this.a(i);
   }

   protected ChunkGenerator a() {
      return this.u;
   }

   protected ChunkGeneratorStructureState b() {
      return this.w;
   }

   protected RandomState c() {
      return this.v;
   }

   public void d() {
      DataResult<JsonElement> dataresult = ChunkGenerator.a.encodeStart(JsonOps.INSTANCE, this.u);
      DataResult<ChunkGenerator> dataresult1 = dataresult.flatMap(jsonelement -> ChunkGenerator.a.parse(JsonOps.INSTANCE, jsonelement));
      dataresult1.result().ifPresent(chunkgenerator -> this.u = chunkgenerator);
   }

   private static double a(ChunkCoordIntPair chunkcoordintpair, Entity entity) {
      double d0 = (double)SectionPosition.a(chunkcoordintpair.e, 8);
      double d1 = (double)SectionPosition.a(chunkcoordintpair.f, 8);
      double d2 = d0 - entity.dl();
      double d3 = d1 - entity.dr();
      return d2 * d2 + d3 * d3;
   }

   public static boolean a(int i, int j, int k, int l, int i1) {
      int j1 = Math.max(0, Math.abs(i - k) - 1);
      int k1 = Math.max(0, Math.abs(j - l) - 1);
      long l1 = (long)Math.max(0, Math.max(j1, k1) - 1);
      long i2 = (long)Math.min(j1, k1);
      long j2 = i2 * i2 + l1 * l1;
      int k2 = i1 - 1;
      int l2 = k2 * k2;
      return j2 <= (long)l2;
   }

   private static boolean b(int i, int j, int k, int l, int i1) {
      return !a(i, j, k, l, i1)
         ? false
         : (!a(i + 1, j, k, l, i1) ? true : (!a(i, j + 1, k, l, i1) ? true : (!a(i - 1, j, k, l, i1) ? true : !a(i, j - 1, k, l, i1))));
   }

   protected LightEngineThreaded e() {
      return this.s;
   }

   @Nullable
   protected PlayerChunk a(long i) {
      return (PlayerChunk)this.n.get(i);
   }

   @Nullable
   protected PlayerChunk b(long i) {
      return (PlayerChunk)this.o.get(i);
   }

   protected IntSupplier c(long i) {
      return () -> {
         PlayerChunk playerchunk = this.b(i);
         return playerchunk == null ? ChunkTaskQueue.a - 1 : Math.min(playerchunk.l(), ChunkTaskQueue.a - 1);
      };
   }

   public String a(ChunkCoordIntPair chunkcoordintpair) {
      PlayerChunk playerchunk = this.b(chunkcoordintpair.a());
      if (playerchunk == null) {
         return "null";
      } else {
         String s = playerchunk.k() + "\n";
         ChunkStatus chunkstatus = playerchunk.f();
         IChunkAccess ichunkaccess = playerchunk.g();
         if (chunkstatus != null) {
            s = s + "St: §" + chunkstatus.c() + chunkstatus + "§r\n";
         }

         if (ichunkaccess != null) {
            s = s + "Ch: §" + ichunkaccess.j().c() + ichunkaccess.j() + "§r\n";
         }

         PlayerChunk.State playerchunk_state = playerchunk.i();
         s = s + "§" + playerchunk_state.ordinal() + playerchunk_state;
         return s + "§r";
      }
   }

   private CompletableFuture<Either<List<IChunkAccess>, PlayerChunk.Failure>> a(
      ChunkCoordIntPair chunkcoordintpair, int i, IntFunction<ChunkStatus> intfunction
   ) {
      List<CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>> list = new ArrayList<>();
      List<PlayerChunk> list1 = new ArrayList<>();
      int j = chunkcoordintpair.e;
      int k = chunkcoordintpair.f;

      for(int l = -i; l <= i; ++l) {
         for(int i1 = -i; i1 <= i; ++i1) {
            int j1 = Math.max(Math.abs(i1), Math.abs(l));
            final ChunkCoordIntPair chunkcoordintpair1 = new ChunkCoordIntPair(j + i1, k + l);
            long k1 = chunkcoordintpair1.a();
            PlayerChunk playerchunk = this.a(k1);
            if (playerchunk == null) {
               return CompletableFuture.completedFuture(Either.right(new PlayerChunk.Failure() {
                  @Override
                  public String toString() {
                     return "Unloaded " + chunkcoordintpair1;
                  }
               }));
            }

            ChunkStatus chunkstatus = intfunction.apply(j1);
            CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = playerchunk.a(chunkstatus, this);
            list1.add(playerchunk);
            list.add(completablefuture);
         }
      }

      CompletableFuture<List<Either<IChunkAccess, PlayerChunk.Failure>>> completablefuture1 = SystemUtils.b(list);
      CompletableFuture<Either<List<IChunkAccess>, PlayerChunk.Failure>> completablefuture2 = completablefuture1.thenApply(list2 -> {
         List<IChunkAccess> list3 = Lists.newArrayList();
         final int cnt = 0;

         for(final Either<IChunkAccess, PlayerChunk.Failure> either : list2) {
            if (either == null) {
               throw this.a(new IllegalStateException("At least one of the chunk futures were null"), "n/a");
            }

            Optional<IChunkAccess> optional = either.left();
            if (!optional.isPresent()) {
               return Either.right(new PlayerChunk.Failure() {
                  @Override
                  public String toString() {
                     ChunkCoordIntPair chunkcoordintpair2 = new ChunkCoordIntPair(j + cnt % (i * 2 + 1), k + cnt / (i * 2 + 1));
                     return "Unloaded " + chunkcoordintpair2 + " " + either.right().get();
                  }
               });
            }

            list3.add(optional.get());
            ++cnt;
         }

         return Either.left(list3);
      });

      for(PlayerChunk playerchunk1 : list1) {
         playerchunk1.a("getChunkRangeFuture " + chunkcoordintpair + " " + i, completablefuture2);
      }

      return completablefuture2;
   }

   public ReportedException a(IllegalStateException illegalstateexception, String s) {
      StringBuilder stringbuilder = new StringBuilder();
      Consumer<PlayerChunk> consumer = playerchunk -> playerchunk.o()
            .forEach(
               pair -> {
                  ChunkStatus chunkstatus = (ChunkStatus)pair.getFirst();
                  CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = (CompletableFuture)pair.getSecond();
                  if (completablefuture != null && completablefuture.isDone() && completablefuture.join() == null) {
                     stringbuilder.append(playerchunk.j())
                        .append(" - status: ")
                        .append(chunkstatus)
                        .append(" future: ")
                        .append(completablefuture)
                        .append(System.lineSeparator());
                  }
               }
            );
      stringbuilder.append("Updating:").append(System.lineSeparator());
      this.n.values().forEach(consumer);
      stringbuilder.append("Visible:").append(System.lineSeparator());
      this.o.values().forEach(consumer);
      CrashReport crashreport = CrashReport.a(illegalstateexception, "Chunk loading");
      CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Chunk loading");
      crashreportsystemdetails.a("Details", s);
      crashreportsystemdetails.a("Futures", stringbuilder);
      return new ReportedException(crashreport);
   }

   public CompletableFuture<Either<Chunk, PlayerChunk.Failure>> b(ChunkCoordIntPair chunkcoordintpair) {
      return this.a(chunkcoordintpair, 2, i -> ChunkStatus.o).thenApplyAsync(either -> either.mapLeft(list -> (Chunk)list.get(list.size() / 2)), this.t);
   }

   @Nullable
   PlayerChunk a(long i, int j, @Nullable PlayerChunk playerchunk, int k) {
      if (k > b && j > b) {
         return playerchunk;
      } else {
         if (playerchunk != null) {
            playerchunk.a(j);
         }

         if (playerchunk != null) {
            if (j > b) {
               this.z.add(i);
            } else {
               this.z.remove(i);
            }
         }

         if (j <= b && playerchunk == null) {
            playerchunk = (PlayerChunk)this.p.remove(i);
            if (playerchunk != null) {
               playerchunk.a(j);
            } else {
               playerchunk = new PlayerChunk(new ChunkCoordIntPair(i), j, this.r, this.s, this.B, this);
            }

            this.n.put(i, playerchunk);
            this.A = true;
         }

         return playerchunk;
      }
   }

   @Override
   public void close() throws IOException {
      try {
         this.B.close();
         this.y.close();
      } finally {
         super.close();
      }
   }

   protected void a(boolean flag) {
      if (flag) {
         List<PlayerChunk> list = this.o.values().stream().filter(PlayerChunk::m).peek(PlayerChunk::n).collect(Collectors.toList());
         MutableBoolean mutableboolean = new MutableBoolean();

         do {
            mutableboolean.setFalse();
            list.stream()
               .map(playerchunk -> {
                  CompletableFuture completablefuture;
                  do {
                     completablefuture = playerchunk.h();
                     IAsyncTaskHandler iasynctaskhandler = this.t;
                     iasynctaskhandler.c(completablefuture::isDone);
                  } while(completablefuture != playerchunk.h());
   
                  return (IChunkAccess)completablefuture.join();
               })
               .filter(ichunkaccess -> ichunkaccess instanceof ProtoChunkExtension || ichunkaccess instanceof Chunk)
               .filter(this::a)
               .forEach(ichunkaccess -> mutableboolean.setTrue());
         } while(mutableboolean.isTrue());

         this.b(() -> true);
         this.o();
      } else {
         this.o.values().forEach(this::d);
      }
   }

   protected void a(BooleanSupplier booleansupplier) {
      GameProfilerFiller gameprofilerfiller = this.r.ac();
      gameprofilerfiller.a("poi");
      this.y.a(booleansupplier);
      gameprofilerfiller.b("chunk_unload");
      if (!this.r.r()) {
         this.b(booleansupplier);
      }

      gameprofilerfiller.c();
   }

   public boolean f() {
      return this.s.D_() || !this.p.isEmpty() || !this.n.isEmpty() || this.y.a() || !this.z.isEmpty() || !this.O.isEmpty() || this.B.a() || this.G.f();
   }

   private void b(BooleanSupplier booleansupplier) {
      LongIterator longiterator = this.z.iterator();

      long j;
      for(int i = 0; longiterator.hasNext() && (booleansupplier.getAsBoolean() || i < 200 || this.z.size() > 2000); longiterator.remove()) {
         j = longiterator.nextLong();
         PlayerChunk playerchunk = (PlayerChunk)this.n.remove(j);
         if (playerchunk != null) {
            this.p.put(j, playerchunk);
            this.A = true;
            ++i;
            this.a(j, playerchunk);
         }
      }

      int k = Math.max(0, this.O.size() - 2000);

      while((booleansupplier.getAsBoolean() || k > 0) && (j = (long)this.O.poll()) != null) {
         --k;
         j.run();
      }

      int l = 0;
      ObjectIterator objectiterator = this.o.values().iterator();

      while(l < 20 && booleansupplier.getAsBoolean() && objectiterator.hasNext()) {
         if (this.d((PlayerChunk)objectiterator.next())) {
            ++l;
         }
      }
   }

   private void a(long i, PlayerChunk playerchunk) {
      CompletableFuture<IChunkAccess> completablefuture = playerchunk.h();
      Consumer<IChunkAccess> consumer = ichunkaccess -> {
         CompletableFuture<IChunkAccess> completablefuture1 = playerchunk.h();
         if (completablefuture1 != completablefuture) {
            this.a(i, playerchunk);
         } else if (this.p.remove(i, playerchunk) && ichunkaccess != null) {
            if (ichunkaccess instanceof Chunk) {
               ((Chunk)ichunkaccess).c(false);
            }

            this.a(ichunkaccess);
            if (this.q.remove(i) && ichunkaccess instanceof Chunk chunk) {
               this.r.a(chunk);
            }

            this.s.a(ichunkaccess.f());
            this.s.a();
            this.E.a(ichunkaccess.f(), null);
            this.N.remove(ichunkaccess.f().a());
         }
      };
      Queue queue = this.O;
      completablefuture.thenAcceptAsync(consumer, queue::add).whenComplete((ovoid, throwable) -> {
         if (throwable != null) {
            i.error("Failed to save chunk {}", playerchunk.j(), throwable);
         }
      });
   }

   protected boolean g() {
      if (!this.A) {
         return false;
      } else {
         this.o = this.n.clone();
         this.A = false;
         return true;
      }
   }

   public CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> a(PlayerChunk playerchunk, ChunkStatus chunkstatus) {
      ChunkCoordIntPair chunkcoordintpair = playerchunk.j();
      if (chunkstatus == ChunkStatus.c) {
         return this.g(chunkcoordintpair);
      } else {
         if (chunkstatus == ChunkStatus.l) {
            this.G.a(TicketType.e, chunkcoordintpair, 33 + ChunkStatus.a(ChunkStatus.l), chunkcoordintpair);
         }

         Optional<IChunkAccess> optional = ((Either)playerchunk.a(chunkstatus.e(), this).getNow(PlayerChunk.a)).left();
         if (optional.isPresent() && optional.get().j().b(chunkstatus)) {
            CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = chunkstatus.a(
               this.r, this.I, this.s, ichunkaccess -> this.c(playerchunk), optional.get()
            );
            this.E.a(chunkcoordintpair, chunkstatus);
            return completablefuture;
         } else {
            return this.b(playerchunk, chunkstatus);
         }
      }
   }

   private CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> g(ChunkCoordIntPair chunkcoordintpair) {
      return this.k(chunkcoordintpair).thenApply(optional -> optional.filter(nbttagcompound -> {
            boolean flag = b(nbttagcompound);
            if (!flag) {
               i.error("Chunk file at {} is missing level data, skipping", chunkcoordintpair);
            }

            return flag;
         })).thenApplyAsync(optional -> {
         this.r.ac().d("chunkLoad");
         if (optional.isPresent()) {
            ProtoChunk protochunk = ChunkRegionLoader.a(this.r, this.y, chunkcoordintpair, (NBTTagCompound)optional.get());
            this.a(chunkcoordintpair, protochunk.j().g());
            return Either.left(protochunk);
         } else {
            return Either.left(this.h(chunkcoordintpair));
         }
      }, this.t).exceptionallyAsync(throwable -> this.a(throwable, chunkcoordintpair), this.t);
   }

   private static boolean b(NBTTagCompound nbttagcompound) {
      return nbttagcompound.b("Status", 8);
   }

   private Either<IChunkAccess, PlayerChunk.Failure> a(Throwable throwable, ChunkCoordIntPair chunkcoordintpair) {
      if (throwable instanceof ReportedException reportedexception) {
         Throwable throwable1 = reportedexception.getCause();
         if (!(throwable1 instanceof IOException)) {
            this.i(chunkcoordintpair);
            throw reportedexception;
         }

         i.error("Couldn't load chunk {}", chunkcoordintpair, throwable1);
      } else if (throwable instanceof IOException) {
         i.error("Couldn't load chunk {}", chunkcoordintpair, throwable);
      }

      return Either.left(this.h(chunkcoordintpair));
   }

   private IChunkAccess h(ChunkCoordIntPair chunkcoordintpair) {
      this.i(chunkcoordintpair);
      return new ProtoChunk(chunkcoordintpair, ChunkConverter.a, this.r, this.r.u_().d(Registries.an), null);
   }

   private void i(ChunkCoordIntPair chunkcoordintpair) {
      this.M.put(chunkcoordintpair.a(), (byte)-1);
   }

   private byte a(ChunkCoordIntPair chunkcoordintpair, ChunkStatus.Type chunkstatus_type) {
      return this.M.put(chunkcoordintpair.a(), (byte)(chunkstatus_type == ChunkStatus.Type.a ? -1 : 1));
   }

   private CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> b(PlayerChunk playerchunk, ChunkStatus chunkstatus) {
      ChunkCoordIntPair chunkcoordintpair = playerchunk.j();
      CompletableFuture<Either<List<IChunkAccess>, PlayerChunk.Failure>> completablefuture = this.a(
         chunkcoordintpair, chunkstatus.f(), i -> this.a(chunkstatus, i)
      );
      this.r.ac().c(() -> "chunkGenerate " + chunkstatus.d());
      Executor executor = runnable -> this.C.a(ChunkTaskQueueSorter.a(playerchunk, runnable));
      return completablefuture.thenComposeAsync(
         either -> (CompletionStage)either.map(
               list -> {
                  try {
                     CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture1 = chunkstatus.a(
                        executor, this.r, this.u, this.I, this.s, ichunkaccess -> this.c(playerchunk), list, false
                     );
                     this.E.a(chunkcoordintpair, chunkstatus);
                     return completablefuture1;
                  } catch (Exception var9) {
                     var9.getStackTrace();
                     CrashReport crashreport = CrashReport.a(var9, "Exception generating new chunk");
                     CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Chunk to be generated");
                     crashreportsystemdetails.a("Location", String.format(Locale.ROOT, "%d,%d", chunkcoordintpair.e, chunkcoordintpair.f));
                     crashreportsystemdetails.a("Position hash", ChunkCoordIntPair.c(chunkcoordintpair.e, chunkcoordintpair.f));
                     crashreportsystemdetails.a("Generator", this.u);
                     this.t.execute(() -> {
                        throw new ReportedException(crashreport);
                     });
                     throw new ReportedException(crashreport);
                  }
               },
               playerchunk_failure -> {
                  this.c(chunkcoordintpair);
                  return CompletableFuture.completedFuture(Either.right(playerchunk_failure));
               }
            ),
         executor
      );
   }

   protected void c(ChunkCoordIntPair chunkcoordintpair) {
      this.t
         .i(
            SystemUtils.a(
               () -> this.G.b(TicketType.e, chunkcoordintpair, 33 + ChunkStatus.a(ChunkStatus.l), chunkcoordintpair),
               () -> "release light ticket " + chunkcoordintpair
            )
         );
   }

   private ChunkStatus a(ChunkStatus chunkstatus, int i) {
      ChunkStatus chunkstatus1;
      if (i == 0) {
         chunkstatus1 = chunkstatus.e();
      } else {
         chunkstatus1 = ChunkStatus.a(ChunkStatus.a(chunkstatus) + i);
      }

      return chunkstatus1;
   }

   private static void a(WorldServer worldserver, List<NBTTagCompound> list) {
      if (!list.isEmpty()) {
         worldserver.b(EntityTypes.a(list, worldserver).filter(entity -> {
            boolean needsRemoval = false;
            DedicatedServer server = worldserver.getCraftServer().getServer();
            if (!server.X() && entity instanceof NPC) {
               entity.ai();
               needsRemoval = true;
            }

            if (!server.W() && (entity instanceof EntityAnimal || entity instanceof EntityWaterAnimal)) {
               entity.ai();
               needsRemoval = true;
            }

            return !needsRemoval;
         }));
      }
   }

   private CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> c(PlayerChunk playerchunk) {
      CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = playerchunk.a(ChunkStatus.o.e());
      return completablefuture.thenApplyAsync(either -> {
         ChunkStatus chunkstatus = PlayerChunk.b(playerchunk.k());
         return !chunkstatus.b(ChunkStatus.o) ? PlayerChunk.a : either.mapLeft(ichunkaccess -> {
            ChunkCoordIntPair chunkcoordintpair = playerchunk.j();
            ProtoChunk protochunk = (ProtoChunk)ichunkaccess;
            Chunk chunk;
            if (protochunk instanceof ProtoChunkExtension) {
               chunk = ((ProtoChunkExtension)protochunk).A();
            } else {
               chunk = new Chunk(this.r, protochunk, chunk1 -> a(this.r, protochunk.D()));
               playerchunk.a(new ProtoChunkExtension(chunk, false));
            }

            chunk.b(() -> PlayerChunk.c(playerchunk.k()));
            chunk.C();
            if (this.q.add(chunkcoordintpair.a())) {
               chunk.c(true);
               chunk.H();
               chunk.a(this.r);
            }

            return chunk;
         });
      }, runnable -> {
         Mailbox mailbox = this.D;
         long i = playerchunk.j().a();
         mailbox.a(ChunkTaskQueueSorter.a(runnable, i, playerchunk::k));
      });
   }

   public CompletableFuture<Either<Chunk, PlayerChunk.Failure>> a(PlayerChunk playerchunk) {
      ChunkCoordIntPair chunkcoordintpair = playerchunk.j();
      CompletableFuture<Either<List<IChunkAccess>, PlayerChunk.Failure>> completablefuture = this.a(chunkcoordintpair, 1, i -> ChunkStatus.o);
      CompletableFuture<Either<Chunk, PlayerChunk.Failure>> completablefuture1 = completablefuture.thenApplyAsync(
            either -> either.mapLeft(list -> (Chunk)list.get(list.size() / 2)), runnable -> this.D.a(ChunkTaskQueueSorter.a(playerchunk, runnable))
         )
         .thenApplyAsync(either -> either.ifLeft(chunk -> {
               chunk.F();
               this.r.b(chunk);
            }), this.t);
      completablefuture1.thenAcceptAsync(either -> either.ifLeft(chunk -> {
            this.H.getAndIncrement();
            MutableObject<ClientboundLevelChunkWithLightPacket> mutableobject = new MutableObject();
            this.a(chunkcoordintpair, false).forEach(entityplayer -> this.a(entityplayer, mutableobject, chunk));
         }), runnable -> this.D.a(ChunkTaskQueueSorter.a(playerchunk, runnable)));
      return completablefuture1;
   }

   public CompletableFuture<Either<Chunk, PlayerChunk.Failure>> b(PlayerChunk playerchunk) {
      return this.a(playerchunk.j(), 1, ChunkStatus::a)
         .thenApplyAsync(
            either -> either.mapLeft(list -> (Chunk)list.get(list.size() / 2)), runnable -> this.D.a(ChunkTaskQueueSorter.a(playerchunk, runnable))
         );
   }

   public int h() {
      return this.H.get();
   }

   private boolean d(PlayerChunk playerchunk) {
      if (!playerchunk.m()) {
         return false;
      } else {
         IChunkAccess ichunkaccess = playerchunk.h().getNow(null);
         if (!(ichunkaccess instanceof ProtoChunkExtension) && !(ichunkaccess instanceof Chunk)) {
            return false;
         } else {
            long i = ichunkaccess.f().a();
            long j = this.N.getOrDefault(i, -1L);
            long k = System.currentTimeMillis();
            if (k < j) {
               return false;
            } else {
               boolean flag = this.a(ichunkaccess);
               playerchunk.n();
               if (flag) {
                  this.N.put(i, k + 10000L);
               }

               return flag;
            }
         }
      }
   }

   public boolean a(IChunkAccess ichunkaccess) {
      this.y.a(ichunkaccess.f());
      if (!ichunkaccess.i()) {
         return false;
      } else {
         ichunkaccess.a(false);
         ChunkCoordIntPair chunkcoordintpair = ichunkaccess.f();

         try {
            ChunkStatus chunkstatus = ichunkaccess.j();
            if (chunkstatus.g() != ChunkStatus.Type.b) {
               if (this.j(chunkcoordintpair)) {
                  return false;
               }

               if (chunkstatus == ChunkStatus.c && ichunkaccess.g().values().stream().noneMatch(StructureStart::b)) {
                  return false;
               }
            }

            this.r.ac().d("chunkSave");
            NBTTagCompound nbttagcompound = ChunkRegionLoader.a(this.r, ichunkaccess);
            this.a(chunkcoordintpair, nbttagcompound);
            this.a(chunkcoordintpair, chunkstatus.g());
            return true;
         } catch (Exception var5) {
            i.error("Failed to save chunk {},{}", new Object[]{chunkcoordintpair.e, chunkcoordintpair.f, var5});
            return false;
         }
      }
   }

   private boolean j(ChunkCoordIntPair chunkcoordintpair) {
      byte b0 = this.M.get(chunkcoordintpair.a());
      if (b0 != 0) {
         return b0 == 1;
      } else {
         NBTTagCompound nbttagcompound;
         try {
            nbttagcompound = this.k(chunkcoordintpair).join().orElse(null);
            if (nbttagcompound == null) {
               this.i(chunkcoordintpair);
               return false;
            }
         } catch (Exception var5) {
            i.error("Failed to read chunk {}", chunkcoordintpair, var5);
            this.i(chunkcoordintpair);
            return false;
         }

         ChunkStatus.Type chunkstatus_type = ChunkRegionLoader.a(nbttagcompound);
         return this.a(chunkcoordintpair, chunkstatus_type) == 1;
      }
   }

   protected void a(int i) {
      int j = MathHelper.a(i + 1, 3, 33);
      if (j != this.P) {
         int k = this.P;
         this.P = j;
         this.G.a(this.P + 1);
         ObjectIterator objectiterator = this.n.values().iterator();

         while(objectiterator.hasNext()) {
            PlayerChunk playerchunk = (PlayerChunk)objectiterator.next();
            ChunkCoordIntPair chunkcoordintpair = playerchunk.j();
            MutableObject<ClientboundLevelChunkWithLightPacket> mutableobject = new MutableObject();
            this.a(chunkcoordintpair, false).forEach(entityplayer -> {
               SectionPosition sectionposition = entityplayer.R();
               boolean flag = a(chunkcoordintpair.e, chunkcoordintpair.f, sectionposition.a(), sectionposition.c(), k);
               boolean flag1 = a(chunkcoordintpair.e, chunkcoordintpair.f, sectionposition.a(), sectionposition.c(), this.P);
               this.a(entityplayer, chunkcoordintpair, mutableobject, flag, flag1);
            });
         }
      }
   }

   protected void a(
      EntityPlayer entityplayer,
      ChunkCoordIntPair chunkcoordintpair,
      MutableObject<ClientboundLevelChunkWithLightPacket> mutableobject,
      boolean flag,
      boolean flag1
   ) {
      if (entityplayer.H == this.r) {
         if (flag1 && !flag) {
            PlayerChunk playerchunk = this.b(chunkcoordintpair.a());
            if (playerchunk != null) {
               Chunk chunk = playerchunk.d();
               if (chunk != null) {
                  this.a(entityplayer, mutableobject, chunk);
               }

               PacketDebug.a(this.r, chunkcoordintpair);
            }
         }

         if (!flag1 && flag) {
            entityplayer.a(chunkcoordintpair);
         }
      }
   }

   public int i() {
      return this.o.size();
   }

   public ChunkMapDistance j() {
      return this.G;
   }

   protected Iterable<PlayerChunk> k() {
      return Iterables.unmodifiableIterable(this.o.values());
   }

   void a(Writer writer) throws IOException {
      CSVWriter csvwriter = CSVWriter.a()
         .a("x")
         .a("z")
         .a("level")
         .a("in_memory")
         .a("status")
         .a("full_status")
         .a("accessible_ready")
         .a("ticking_ready")
         .a("entity_ticking_ready")
         .a("ticket")
         .a("spawning")
         .a("block_entity_count")
         .a("ticking_ticket")
         .a("ticking_level")
         .a("block_ticks")
         .a("fluid_ticks")
         .a(writer);
      TickingTracker tickingtracker = this.G.d();
      ObjectBidirectionalIterator objectbidirectionaliterator = this.o.long2ObjectEntrySet().iterator();

      while(objectbidirectionaliterator.hasNext()) {
         Entry<PlayerChunk> entry = (Entry)objectbidirectionaliterator.next();
         long i = entry.getLongKey();
         ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i);
         PlayerChunk playerchunk = (PlayerChunk)entry.getValue();
         Optional<IChunkAccess> optional = Optional.ofNullable(playerchunk.g());
         Optional<Chunk> optional1 = optional.flatMap(ichunkaccess -> ichunkaccess instanceof Chunk ? Optional.of((Chunk)ichunkaccess) : Optional.empty());
         csvwriter.a(
            chunkcoordintpair.e,
            chunkcoordintpair.f,
            playerchunk.k(),
            optional.isPresent(),
            optional.map(IChunkAccess::j).orElse(null),
            optional1.map(Chunk::B).orElse(null),
            a(playerchunk.c()),
            a(playerchunk.a()),
            a(playerchunk.b()),
            this.G.e(i),
            this.d(chunkcoordintpair),
            optional1.<Integer>map(chunk -> chunk.E().size()).orElse(0),
            tickingtracker.d(i),
            tickingtracker.c(i),
            optional1.<Integer>map(chunk -> chunk.o().a()).orElse(0),
            optional1.<Integer>map(chunk -> chunk.p().a()).orElse(0)
         );
      }
   }

   private static String a(CompletableFuture<Either<Chunk, PlayerChunk.Failure>> completablefuture) {
      try {
         Either<Chunk, PlayerChunk.Failure> either = (Either)completablefuture.getNow(null);
         return either != null ? (String)either.map(chunk -> "done", playerchunk_failure -> "unloaded") : "not completed";
      } catch (CompletionException var2) {
         return "failed " + var2.getCause().getMessage();
      } catch (CancellationException var3) {
         return "cancelled";
      }
   }

   private CompletableFuture<Optional<NBTTagCompound>> k(ChunkCoordIntPair chunkcoordintpair) {
      return this.f(chunkcoordintpair)
         .thenApplyAsync(optional -> optional.map(nbttagcompound -> this.upgradeChunkTag(nbttagcompound, chunkcoordintpair)), SystemUtils.f());
   }

   private NBTTagCompound upgradeChunkTag(NBTTagCompound nbttagcompound, ChunkCoordIntPair chunkcoordintpair) {
      return this.upgradeChunkTag(this.r.getTypeKey(), this.x, nbttagcompound, this.u.b(), chunkcoordintpair, this.r);
   }

   boolean d(ChunkCoordIntPair chunkcoordintpair) {
      return this.anyPlayerCloseEnoughForSpawning(chunkcoordintpair, false);
   }

   boolean anyPlayerCloseEnoughForSpawning(ChunkCoordIntPair chunkcoordintpair, boolean reducedRange) {
      int chunkRange = this.r.spigotConfig.mobSpawnRange;
      chunkRange = chunkRange > this.r.spigotConfig.viewDistance ? (byte)this.r.spigotConfig.viewDistance : chunkRange;
      chunkRange = chunkRange > 8 ? 8 : chunkRange;
      double blockRange = reducedRange ? Math.pow((double)(chunkRange << 4), 2.0) : 16384.0;
      long i = chunkcoordintpair.a();
      if (!this.G.f(i)) {
         return false;
      } else {
         for(EntityPlayer entityplayer : this.K.a(i)) {
            if (this.playerIsCloseEnoughForSpawning(entityplayer, chunkcoordintpair, blockRange)) {
               return true;
            }
         }

         return false;
      }
   }

   public List<EntityPlayer> e(ChunkCoordIntPair chunkcoordintpair) {
      long i = chunkcoordintpair.a();
      if (!this.G.f(i)) {
         return List.of();
      } else {
         Builder<EntityPlayer> builder = ImmutableList.builder();

         for(EntityPlayer entityplayer : this.K.a(i)) {
            if (this.playerIsCloseEnoughForSpawning(entityplayer, chunkcoordintpair, 16384.0)) {
               builder.add(entityplayer);
            }
         }

         return builder.build();
      }
   }

   private boolean playerIsCloseEnoughForSpawning(EntityPlayer entityplayer, ChunkCoordIntPair chunkcoordintpair, double range) {
      if (entityplayer.F_()) {
         return false;
      } else {
         double d0 = a(chunkcoordintpair, entityplayer);
         return d0 < range;
      }
   }

   private boolean b(EntityPlayer entityplayer) {
      return entityplayer.F_() && !this.r.W().b(GameRules.q);
   }

   void a(EntityPlayer entityplayer, boolean flag) {
      boolean flag1 = this.b(entityplayer);
      boolean flag2 = this.K.c(entityplayer);
      int i = SectionPosition.a(entityplayer.dk());
      int j = SectionPosition.a(entityplayer.dq());
      if (flag) {
         this.K.a(ChunkCoordIntPair.c(i, j), entityplayer, flag1);
         this.c(entityplayer);
         if (!flag1) {
            this.G.a(SectionPosition.a(entityplayer), entityplayer);
         }
      } else {
         SectionPosition sectionposition = entityplayer.R();
         this.K.a(sectionposition.r().a(), entityplayer);
         if (!flag2) {
            this.G.b(sectionposition, entityplayer);
         }
      }

      for(int k = i - this.P - 1; k <= i + this.P + 1; ++k) {
         for(int l = j - this.P - 1; l <= j + this.P + 1; ++l) {
            if (a(k, l, i, j, this.P)) {
               ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(k, l);
               this.a(entityplayer, chunkcoordintpair, new MutableObject(), !flag, flag);
            }
         }
      }
   }

   private SectionPosition c(EntityPlayer entityplayer) {
      SectionPosition sectionposition = SectionPosition.a(entityplayer);
      entityplayer.a(sectionposition);
      entityplayer.b.a(new PacketPlayOutViewCentre(sectionposition.a(), sectionposition.c()));
      return sectionposition;
   }

   public void a(EntityPlayer entityplayer) {
      ObjectIterator objectiterator = this.L.values().iterator();

      while(objectiterator.hasNext()) {
         PlayerChunkMap.EntityTracker playerchunkmap_entitytracker = (PlayerChunkMap.EntityTracker)objectiterator.next();
         if (playerchunkmap_entitytracker.c == entityplayer) {
            playerchunkmap_entitytracker.a(this.r.v());
         } else {
            playerchunkmap_entitytracker.b(entityplayer);
         }
      }

      int i = SectionPosition.a(entityplayer.dk());
      int j = SectionPosition.a(entityplayer.dq());
      SectionPosition sectionposition = entityplayer.R();
      SectionPosition sectionposition1 = SectionPosition.a(entityplayer);
      long k = sectionposition.r().a();
      long l = sectionposition1.r().a();
      boolean flag = this.K.d(entityplayer);
      boolean flag1 = this.b(entityplayer);
      boolean flag2 = sectionposition.s() != sectionposition1.s();
      if (flag2 || flag != flag1) {
         this.c(entityplayer);
         if (!flag) {
            this.G.b(sectionposition, entityplayer);
         }

         if (!flag1) {
            this.G.a(sectionposition1, entityplayer);
         }

         if (!flag && flag1) {
            this.K.a(entityplayer);
         }

         if (flag && !flag1) {
            this.K.b(entityplayer);
         }

         if (k != l) {
            this.K.a(k, l, entityplayer);
         }
      }

      int i1 = sectionposition.a();
      int j1 = sectionposition.c();
      if (Math.abs(i1 - i) <= this.P * 2 && Math.abs(j1 - j) <= this.P * 2) {
         int k1 = Math.min(i, i1) - this.P - 1;
         int l1 = Math.min(j, j1) - this.P - 1;
         int i2 = Math.max(i, i1) + this.P + 1;
         int j2 = Math.max(j, j1) + this.P + 1;

         for(int k2 = k1; k2 <= i2; ++k2) {
            for(int l2 = l1; l2 <= j2; ++l2) {
               boolean flag3 = a(k2, l2, i1, j1, this.P);
               boolean flag4 = a(k2, l2, i, j, this.P);
               this.a(entityplayer, new ChunkCoordIntPair(k2, l2), new MutableObject(), flag3, flag4);
            }
         }
      } else {
         for(int k1 = i1 - this.P - 1; k1 <= i1 + this.P + 1; ++k1) {
            for(int l1 = j1 - this.P - 1; l1 <= j1 + this.P + 1; ++l1) {
               if (a(k1, l1, i1, j1, this.P)) {
                  boolean flag5 = true;
                  boolean flag6 = false;
                  this.a(entityplayer, new ChunkCoordIntPair(k1, l1), new MutableObject(), true, false);
               }
            }
         }

         for(int var25 = i - this.P - 1; var25 <= i + this.P + 1; ++var25) {
            for(int l1 = j - this.P - 1; l1 <= j + this.P + 1; ++l1) {
               if (a(var25, l1, i, j, this.P)) {
                  boolean flag5 = false;
                  boolean flag6 = true;
                  this.a(entityplayer, new ChunkCoordIntPair(var25, l1), new MutableObject(), false, true);
               }
            }
         }
      }
   }

   @Override
   public List<EntityPlayer> a(ChunkCoordIntPair chunkcoordintpair, boolean flag) {
      Set<EntityPlayer> set = this.K.a(chunkcoordintpair.a());
      Builder<EntityPlayer> builder = ImmutableList.builder();

      for(EntityPlayer entityplayer : set) {
         SectionPosition sectionposition = entityplayer.R();
         if (flag && b(chunkcoordintpair.e, chunkcoordintpair.f, sectionposition.a(), sectionposition.c(), this.P)
            || !flag && a(chunkcoordintpair.e, chunkcoordintpair.f, sectionposition.a(), sectionposition.c(), this.P)) {
            builder.add(entityplayer);
         }
      }

      return builder.build();
   }

   protected void a(Entity entity) {
      AsyncCatcher.catchOp("entity track");
      if (!(entity instanceof EntityComplexPart)) {
         EntityTypes<?> entitytypes = entity.ae();
         int i = entitytypes.o() * 16;
         i = TrackingRange.getEntityTrackingRange(entity, i);
         if (i != 0) {
            int j = entitytypes.p();
            if (this.L.containsKey(entity.af())) {
               throw (IllegalStateException)SystemUtils.b(new IllegalStateException("Entity is already tracked!"));
            }

            PlayerChunkMap.EntityTracker playerchunkmap_entitytracker = new PlayerChunkMap.EntityTracker(entity, i, j, entitytypes.q());
            this.L.put(entity.af(), playerchunkmap_entitytracker);
            playerchunkmap_entitytracker.a(this.r.v());
            if (entity instanceof EntityPlayer entityplayer) {
               this.a(entityplayer, true);
               ObjectIterator objectiterator = this.L.values().iterator();

               while(objectiterator.hasNext()) {
                  PlayerChunkMap.EntityTracker playerchunkmap_entitytracker1 = (PlayerChunkMap.EntityTracker)objectiterator.next();
                  if (playerchunkmap_entitytracker1.c != entityplayer) {
                     playerchunkmap_entitytracker1.b(entityplayer);
                  }
               }
            }
         }
      }
   }

   protected void b(Entity entity) {
      AsyncCatcher.catchOp("entity untrack");
      if (entity instanceof EntityPlayer entityplayer) {
         this.a(entityplayer, false);
         ObjectIterator objectiterator = this.L.values().iterator();

         while(objectiterator.hasNext()) {
            PlayerChunkMap.EntityTracker playerchunkmap_entitytracker = (PlayerChunkMap.EntityTracker)objectiterator.next();
            playerchunkmap_entitytracker.a(entityplayer);
         }
      }

      PlayerChunkMap.EntityTracker playerchunkmap_entitytracker1 = (PlayerChunkMap.EntityTracker)this.L.remove(entity.af());
      if (playerchunkmap_entitytracker1 != null) {
         playerchunkmap_entitytracker1.a();
      }
   }

   protected void l() {
      List<EntityPlayer> list = Lists.newArrayList();
      List<EntityPlayer> list1 = this.r.v();
      ObjectIterator objectiterator = this.L.values().iterator();

      while(objectiterator.hasNext()) {
         PlayerChunkMap.EntityTracker playerchunkmap_entitytracker = (PlayerChunkMap.EntityTracker)objectiterator.next();
         SectionPosition sectionposition = playerchunkmap_entitytracker.e;
         SectionPosition sectionposition1 = SectionPosition.a(playerchunkmap_entitytracker.c);
         boolean flag = !Objects.equals(sectionposition, sectionposition1);
         if (flag) {
            playerchunkmap_entitytracker.a(list1);
            Entity entity = playerchunkmap_entitytracker.c;
            if (entity instanceof EntityPlayer) {
               list.add((EntityPlayer)entity);
            }

            playerchunkmap_entitytracker.e = sectionposition1;
         }

         if (flag || this.G.c(sectionposition1.r().a())) {
            playerchunkmap_entitytracker.b.a();
         }
      }

      if (!list.isEmpty()) {
         objectiterator = this.L.values().iterator();

         while(objectiterator.hasNext()) {
            PlayerChunkMap.EntityTracker playerchunkmap_entitytracker = (PlayerChunkMap.EntityTracker)objectiterator.next();
            playerchunkmap_entitytracker.a(list);
         }
      }
   }

   public void a(Entity entity, Packet<?> packet) {
      PlayerChunkMap.EntityTracker playerchunkmap_entitytracker = (PlayerChunkMap.EntityTracker)this.L.get(entity.af());
      if (playerchunkmap_entitytracker != null) {
         playerchunkmap_entitytracker.a(packet);
      }
   }

   protected void b(Entity entity, Packet<?> packet) {
      PlayerChunkMap.EntityTracker playerchunkmap_entitytracker = (PlayerChunkMap.EntityTracker)this.L.get(entity.af());
      if (playerchunkmap_entitytracker != null) {
         playerchunkmap_entitytracker.b(packet);
      }
   }

   public void a(List<IChunkAccess> list) {
      Map<EntityPlayer, List<Chunk>> map = new HashMap<>();

      for(IChunkAccess ichunkaccess : list) {
         ChunkCoordIntPair chunkcoordintpair = ichunkaccess.f();
         Chunk chunk;
         if (ichunkaccess instanceof Chunk chunk1) {
            chunk = chunk1;
         } else {
            chunk = this.r.d(chunkcoordintpair.e, chunkcoordintpair.f);
         }

         for(EntityPlayer entityplayer : this.a(chunkcoordintpair, false)) {
            map.computeIfAbsent(entityplayer, entityplayer1 -> new ArrayList()).add(chunk);
         }
      }

      map.forEach((entityplayer1, list1) -> entityplayer1.b.a(ClientboundChunksBiomesPacket.a(list1)));
   }

   private void a(EntityPlayer entityplayer, MutableObject<ClientboundLevelChunkWithLightPacket> mutableobject, Chunk chunk) {
      if (mutableobject.getValue() == null) {
         mutableobject.setValue(new ClientboundLevelChunkWithLightPacket(chunk, this.s, null, null, true));
      }

      entityplayer.a(chunk.f(), (Packet<?>)mutableobject.getValue());
      PacketDebug.a(this.r, chunk.f());
      List<Entity> list = Lists.newArrayList();
      List<Entity> list1 = Lists.newArrayList();
      ObjectIterator objectiterator = this.L.values().iterator();

      while(objectiterator.hasNext()) {
         PlayerChunkMap.EntityTracker playerchunkmap_entitytracker = (PlayerChunkMap.EntityTracker)objectiterator.next();
         Entity entity = playerchunkmap_entitytracker.c;
         if (entity != entityplayer && entity.di().equals(chunk.f())) {
            playerchunkmap_entitytracker.b(entityplayer);
            if (entity instanceof EntityInsentient && ((EntityInsentient)entity).fJ() != null) {
               list.add(entity);
            }

            if (!entity.cM().isEmpty()) {
               list1.add(entity);
            }
         }
      }

      if (!list.isEmpty()) {
         for(Entity entity1 : list) {
            entityplayer.b.a(new PacketPlayOutAttachEntity(entity1, ((EntityInsentient)entity1).fJ()));
         }
      }

      if (!list1.isEmpty()) {
         for(Entity entity1 : list1) {
            entityplayer.b.a(new PacketPlayOutMount(entity1));
         }
      }
   }

   protected VillagePlace m() {
      return this.y;
   }

   public String n() {
      return this.J;
   }

   void a(ChunkCoordIntPair chunkcoordintpair, PlayerChunk.State playerchunk_state) {
      this.F.onChunkStatusChange(chunkcoordintpair, playerchunk_state);
   }

   public static final class CallbackExecutor implements Executor, Runnable {
      private final Queue<Runnable> queue = new ArrayDeque<>();

      @Override
      public void execute(Runnable runnable) {
         this.queue.add(runnable);
      }

      @Override
      public void run() {
         Runnable task;
         while((task = this.queue.poll()) != null) {
            task.run();
         }
      }
   }

   public class EntityTracker {
      final EntityTrackerEntry b;
      final Entity c;
      private final int d;
      SectionPosition e;
      public final Set<ServerPlayerConnection> f = Sets.newIdentityHashSet();

      public EntityTracker(Entity entity, int i, int j, boolean flag) {
         this.b = new EntityTrackerEntry(PlayerChunkMap.this.r, entity, j, flag, this::a, this.f);
         this.c = entity;
         this.d = i;
         this.e = SectionPosition.a(entity);
      }

      @Override
      public boolean equals(Object object) {
         return object instanceof PlayerChunkMap.EntityTracker ? ((PlayerChunkMap.EntityTracker)object).c.af() == this.c.af() : false;
      }

      @Override
      public int hashCode() {
         return this.c.af();
      }

      public void a(Packet<?> packet) {
         for(ServerPlayerConnection serverplayerconnection : this.f) {
            serverplayerconnection.a(packet);
         }
      }

      public void b(Packet<?> packet) {
         this.a(packet);
         if (this.c instanceof EntityPlayer) {
            ((EntityPlayer)this.c).b.a(packet);
         }
      }

      public void a() {
         for(ServerPlayerConnection serverplayerconnection : this.f) {
            this.b.a(serverplayerconnection.f());
         }
      }

      public void a(EntityPlayer entityplayer) {
         AsyncCatcher.catchOp("player tracker clear");
         if (this.f.remove(entityplayer.b)) {
            this.b.a(entityplayer);
         }
      }

      public void b(EntityPlayer entityplayer) {
         AsyncCatcher.catchOp("player tracker update");
         if (entityplayer != this.c) {
            Vec3D vec3d = entityplayer.de().d(this.c.de());
            double d0 = (double)Math.min(this.b(), (PlayerChunkMap.this.P - 1) * 16);
            double d1 = vec3d.c * vec3d.c + vec3d.e * vec3d.e;
            double d2 = d0 * d0;
            boolean flag = d1 <= d2 && this.c.a(entityplayer);
            if (!entityplayer.getBukkitEntity().canSee(this.c.getBukkitEntity())) {
               flag = false;
            }

            if (flag) {
               if (this.f.add(entityplayer.b)) {
                  this.b.b(entityplayer);
               }
            } else if (this.f.remove(entityplayer.b)) {
               this.b.a(entityplayer);
            }
         }
      }

      private int a(int i) {
         return PlayerChunkMap.this.r.n().b(i);
      }

      private int b() {
         int i = this.d;

         for(Entity entity : this.c.cQ()) {
            int j = entity.ae().o() * 16;
            if (j > i) {
               i = j;
            }
         }

         return this.a(i);
      }

      public void a(List<EntityPlayer> list) {
         for(EntityPlayer entityplayer : list) {
            this.b(entityplayer);
         }
      }
   }

   private class a extends ChunkMapDistance {
      protected a(Executor executor, Executor executor1) {
         super(executor, executor1);
      }

      @Override
      protected boolean a(long i) {
         return PlayerChunkMap.this.z.contains(i);
      }

      @Nullable
      @Override
      protected PlayerChunk b(long i) {
         return PlayerChunkMap.this.a(i);
      }

      @Nullable
      @Override
      protected PlayerChunk a(long i, int j, @Nullable PlayerChunk playerchunk, int k) {
         return PlayerChunkMap.this.a(i, j, playerchunk, k);
      }
   }
}
