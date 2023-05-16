package net.minecraft.server.level;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.shorts.ShortOpenHashSet;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.network.protocol.game.PacketPlayOutLightUpdate;
import net.minecraft.network.protocol.game.PacketPlayOutMultiBlockChange;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DebugBuffer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.chunk.ChunkSection;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.ProtoChunkExtension;
import net.minecraft.world.level.lighting.LightEngine;

public class PlayerChunk {
   public static final Either<IChunkAccess, PlayerChunk.Failure> a = Either.right(PlayerChunk.Failure.b);
   public static final CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> b = CompletableFuture.completedFuture(a);
   public static final Either<Chunk, PlayerChunk.Failure> c = Either.right(PlayerChunk.Failure.b);
   private static final Either<IChunkAccess, PlayerChunk.Failure> d = Either.right(PlayerChunk.Failure.b);
   private static final CompletableFuture<Either<Chunk, PlayerChunk.Failure>> e = CompletableFuture.completedFuture(c);
   private static final List<ChunkStatus> f = ChunkStatus.a();
   private static final PlayerChunk.State[] g = PlayerChunk.State.values();
   private static final int h = 64;
   private final AtomicReferenceArray<CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>> i = new AtomicReferenceArray<>(f.size());
   private final LevelHeightAccessor j;
   private volatile CompletableFuture<Either<Chunk, PlayerChunk.Failure>> k = e;
   private volatile CompletableFuture<Either<Chunk, PlayerChunk.Failure>> l = e;
   private volatile CompletableFuture<Either<Chunk, PlayerChunk.Failure>> m = e;
   private CompletableFuture<IChunkAccess> n = CompletableFuture.completedFuture(null);
   @Nullable
   private final DebugBuffer<PlayerChunk.b> o = null;
   public int p;
   private int q;
   private int r;
   final ChunkCoordIntPair s;
   private boolean t;
   private final ShortSet[] u;
   private final BitSet v = new BitSet();
   private final BitSet w = new BitSet();
   private final LightEngine x;
   private final PlayerChunk.d y;
   public final PlayerChunk.e z;
   private boolean A;
   private boolean B;
   private CompletableFuture<Void> C = CompletableFuture.completedFuture(null);

   public PlayerChunk(
      ChunkCoordIntPair chunkcoordintpair,
      int i,
      LevelHeightAccessor levelheightaccessor,
      LightEngine lightengine,
      PlayerChunk.d playerchunk_d,
      PlayerChunk.e playerchunk_e
   ) {
      this.s = chunkcoordintpair;
      this.j = levelheightaccessor;
      this.x = lightengine;
      this.y = playerchunk_d;
      this.z = playerchunk_e;
      this.p = PlayerChunkMap.b + 1;
      this.q = this.p;
      this.r = this.p;
      this.a(i);
      this.u = new ShortSet[levelheightaccessor.aj()];
   }

   public Chunk getFullChunkNow() {
      return !c(this.p).a(PlayerChunk.State.b) ? null : this.getFullChunkNowUnchecked();
   }

   public Chunk getFullChunkNowUnchecked() {
      CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> statusFuture = this.a(ChunkStatus.o);
      Either<IChunkAccess, PlayerChunk.Failure> either = (Either)statusFuture.getNow(null);
      return either == null ? null : (Chunk)either.left().orElse(null);
   }

   public CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> a(ChunkStatus chunkstatus) {
      CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = this.i.get(chunkstatus.c());
      return completablefuture == null ? b : completablefuture;
   }

   public CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> b(ChunkStatus chunkstatus) {
      return b(this.q).b(chunkstatus) ? this.a(chunkstatus) : b;
   }

   public CompletableFuture<Either<Chunk, PlayerChunk.Failure>> a() {
      return this.l;
   }

   public CompletableFuture<Either<Chunk, PlayerChunk.Failure>> b() {
      return this.m;
   }

   public CompletableFuture<Either<Chunk, PlayerChunk.Failure>> c() {
      return this.k;
   }

   @Nullable
   public Chunk d() {
      CompletableFuture<Either<Chunk, PlayerChunk.Failure>> completablefuture = this.a();
      Either<Chunk, PlayerChunk.Failure> either = (Either)completablefuture.getNow(null);
      return either == null ? null : (Chunk)either.left().orElse(null);
   }

   @Nullable
   public Chunk e() {
      CompletableFuture<Either<Chunk, PlayerChunk.Failure>> completablefuture = this.c();
      Either<Chunk, PlayerChunk.Failure> either = (Either)completablefuture.getNow(null);
      return either == null ? null : (Chunk)either.left().orElse(null);
   }

   @Nullable
   public ChunkStatus f() {
      for(int i = f.size() - 1; i >= 0; --i) {
         ChunkStatus chunkstatus = f.get(i);
         CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = this.a(chunkstatus);
         if (((Either)completablefuture.getNow(a)).left().isPresent()) {
            return chunkstatus;
         }
      }

      return null;
   }

   @Nullable
   public IChunkAccess g() {
      for(int i = f.size() - 1; i >= 0; --i) {
         ChunkStatus chunkstatus = f.get(i);
         CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = this.a(chunkstatus);
         if (!completablefuture.isCompletedExceptionally()) {
            Optional<IChunkAccess> optional = ((Either)completablefuture.getNow(a)).left();
            if (optional.isPresent()) {
               return optional.get();
            }
         }
      }

      return null;
   }

   public CompletableFuture<IChunkAccess> h() {
      return this.n;
   }

   public void a(BlockPosition blockposition) {
      Chunk chunk = this.d();
      if (chunk != null) {
         int i = this.j.e(blockposition.v());
         if (i < 0 || i >= this.u.length) {
            return;
         }

         if (this.u[i] == null) {
            this.t = true;
            this.u[i] = new ShortOpenHashSet();
         }

         this.u[i].add(SectionPosition.b(blockposition));
      }
   }

   public void a(EnumSkyBlock enumskyblock, int i) {
      Either<IChunkAccess, PlayerChunk.Failure> either = (Either)this.b(ChunkStatus.k).getNow(null);
      if (either != null) {
         IChunkAccess ichunkaccess = (IChunkAccess)either.left().orElse(null);
         if (ichunkaccess != null) {
            ichunkaccess.a(true);
            Chunk chunk = this.d();
            if (chunk != null) {
               int j = this.x.c();
               int k = this.x.d();
               if (i >= j && i <= k) {
                  int l = i - j;
                  if (enumskyblock == EnumSkyBlock.a) {
                     this.w.set(l);
                  } else {
                     this.v.set(l);
                  }
               }
            }
         }
      }
   }

   public void a(Chunk chunk) {
      if (this.t || !this.w.isEmpty() || !this.v.isEmpty()) {
         World world = chunk.D();
         int i = 0;

         for(int j = 0; j < this.u.length; ++j) {
            i += this.u[j] != null ? this.u[j].size() : 0;
         }

         this.B |= i >= 64;
         if (!this.w.isEmpty() || !this.v.isEmpty()) {
            this.a(new PacketPlayOutLightUpdate(chunk.f(), this.x, this.w, this.v, true), !this.B);
            this.w.clear();
            this.v.clear();
         }

         for(int var10 = 0; var10 < this.u.length; ++var10) {
            ShortSet shortset = this.u[var10];
            if (shortset != null) {
               int k = this.j.g(var10);
               SectionPosition sectionposition = SectionPosition.a(chunk.f(), k);
               if (shortset.size() == 1) {
                  BlockPosition blockposition = sectionposition.g(shortset.iterator().nextShort());
                  IBlockData iblockdata = world.a_(blockposition);
                  this.a(new PacketPlayOutBlockChange(blockposition, iblockdata), false);
                  this.a(world, blockposition, iblockdata);
               } else {
                  ChunkSection chunksection = chunk.b(var10);
                  PacketPlayOutMultiBlockChange packetplayoutmultiblockchange = new PacketPlayOutMultiBlockChange(
                     sectionposition, shortset, chunksection, this.B
                  );
                  this.a(packetplayoutmultiblockchange, false);
                  packetplayoutmultiblockchange.a((blockposition1, iblockdata1) -> this.a(world, blockposition1, iblockdata1));
               }

               this.u[var10] = null;
            }
         }

         this.t = false;
      }
   }

   private void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      if (iblockdata.q()) {
         this.a(world, blockposition);
      }
   }

   private void a(World world, BlockPosition blockposition) {
      TileEntity tileentity = world.c_(blockposition);
      if (tileentity != null) {
         Packet<?> packet = tileentity.h();
         if (packet != null) {
            this.a(packet, false);
         }
      }
   }

   private void a(Packet<?> packet, boolean flag) {
      this.z.a(this.s, flag).forEach(entityplayer -> entityplayer.b.a(packet));
   }

   public CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> a(ChunkStatus chunkstatus, PlayerChunkMap playerchunkmap) {
      int i = chunkstatus.c();
      CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = this.i.get(i);
      if (completablefuture != null) {
         Either<IChunkAccess, PlayerChunk.Failure> either = (Either)completablefuture.getNow(d);
         if (either == null) {
            String s = "value in future for status: " + chunkstatus + " was incorrectly set to null at chunk: " + this.s;
            throw playerchunkmap.a(new IllegalStateException("null value previously set for chunk status"), s);
         }

         if (either == d || either.right().isEmpty()) {
            return completablefuture;
         }
      }

      if (b(this.q).b(chunkstatus)) {
         CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture1 = playerchunkmap.a(this, chunkstatus);
         this.a(completablefuture1, "schedule " + chunkstatus);
         this.i.set(i, completablefuture1);
         return completablefuture1;
      } else {
         return completablefuture == null ? b : completablefuture;
      }
   }

   protected void a(String s, CompletableFuture<?> completablefuture) {
      if (this.o != null) {
         this.o.a(new PlayerChunk.b(Thread.currentThread(), completablefuture, s));
      }

      this.n = this.n.thenCombine(completablefuture, (ichunkaccess, object) -> ichunkaccess);
   }

   private void a(CompletableFuture<? extends Either<? extends IChunkAccess, PlayerChunk.Failure>> completablefuture, String s) {
      if (this.o != null) {
         this.o.a(new PlayerChunk.b(Thread.currentThread(), completablefuture, s));
      }

      this.n = this.n
         .thenCombine(
            completablefuture, (ichunkaccess, either) -> (IChunkAccess)either.map(ichunkaccess1 -> ichunkaccess1, playerchunk_failure -> ichunkaccess)
         );
   }

   public PlayerChunk.State i() {
      return c(this.q);
   }

   public ChunkCoordIntPair j() {
      return this.s;
   }

   public int k() {
      return this.q;
   }

   public int l() {
      return this.r;
   }

   private void d(int i) {
      this.r = i;
   }

   public void a(int i) {
      this.q = i;
   }

   private void a(
      PlayerChunkMap playerchunkmap,
      CompletableFuture<Either<Chunk, PlayerChunk.Failure>> completablefuture,
      Executor executor,
      PlayerChunk.State playerchunk_state
   ) {
      this.C.cancel(false);
      CompletableFuture<Void> completablefuture1 = new CompletableFuture<>();
      completablefuture1.thenRunAsync(() -> playerchunkmap.a(this.s, playerchunk_state), executor);
      this.C = completablefuture1;
      completablefuture.thenAccept(either -> either.ifLeft(chunk -> completablefuture1.complete(null)));
   }

   private void a(PlayerChunkMap playerchunkmap, PlayerChunk.State playerchunk_state) {
      this.C.cancel(false);
      playerchunkmap.a(this.s, playerchunk_state);
   }

   protected void a(PlayerChunkMap playerchunkmap, Executor executor) {
      ChunkStatus chunkstatus = b(this.p);
      ChunkStatus chunkstatus1 = b(this.q);
      boolean flag = this.p <= PlayerChunkMap.b;
      boolean flag1 = this.q <= PlayerChunkMap.b;
      PlayerChunk.State playerchunk_state = c(this.p);
      PlayerChunk.State playerchunk_state1 = c(this.q);
      if (playerchunk_state.a(PlayerChunk.State.b) && !playerchunk_state1.a(PlayerChunk.State.b)) {
         this.a(ChunkStatus.o).thenAccept(either -> {
            Chunk chunk = (Chunk)either.left().orElse(null);
            if (chunk != null) {
               playerchunkmap.callbackExecutor.execute(() -> {
                  chunk.a(true);
                  chunk.unloadCallback();
               });
            }
         }).exceptionally(throwable -> {
            MinecraftServer.n.error("Failed to schedule unload callback for chunk " + this.s, throwable);
            return null;
         });
         playerchunkmap.callbackExecutor.run();
      }

      if (flag) {
         Either<IChunkAccess, PlayerChunk.Failure> either = Either.right(new PlayerChunk.Failure() {
            @Override
            public String toString() {
               return "Unloaded ticket level " + PlayerChunk.this.s;
            }
         });

         for(int i = flag1 ? chunkstatus1.c() + 1 : 0; i <= chunkstatus.c(); ++i) {
            CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = this.i.get(i);
            if (completablefuture == null) {
               this.i.set(i, CompletableFuture.completedFuture(either));
            }
         }
      }

      boolean flag2 = playerchunk_state.a(PlayerChunk.State.b);
      boolean flag3 = playerchunk_state1.a(PlayerChunk.State.b);
      this.A |= flag3;
      if (!flag2 && flag3) {
         this.k = playerchunkmap.b(this);
         this.a(playerchunkmap, this.k, executor, PlayerChunk.State.b);
         this.a(this.k, "full");
      }

      if (flag2 && !flag3) {
         this.k.complete(c);
         this.k = e;
      }

      boolean flag4 = playerchunk_state.a(PlayerChunk.State.c);
      boolean flag5 = playerchunk_state1.a(PlayerChunk.State.c);
      if (!flag4 && flag5) {
         this.l = playerchunkmap.a(this);
         this.a(playerchunkmap, this.l, executor, PlayerChunk.State.c);
         this.a(this.l, "ticking");
      }

      if (flag4 && !flag5) {
         this.l.complete(c);
         this.l = e;
      }

      boolean flag6 = playerchunk_state.a(PlayerChunk.State.d);
      boolean flag7 = playerchunk_state1.a(PlayerChunk.State.d);
      if (!flag6 && flag7) {
         if (this.m != e) {
            throw (IllegalStateException)SystemUtils.b(new IllegalStateException());
         }

         this.m = playerchunkmap.b(this.s);
         this.a(playerchunkmap, this.m, executor, PlayerChunk.State.d);
         this.a(this.m, "entity ticking");
      }

      if (flag6 && !flag7) {
         this.m.complete(c);
         this.m = e;
      }

      if (!playerchunk_state1.a(playerchunk_state)) {
         this.a(playerchunkmap, playerchunk_state1);
      }

      this.y.onLevelChange(this.s, this::l, this.q, this::d);
      this.p = this.q;
      if (!playerchunk_state.a(PlayerChunk.State.b) && playerchunk_state1.a(PlayerChunk.State.b)) {
         this.a(ChunkStatus.o).thenAccept(either -> {
            Chunk chunk = (Chunk)either.left().orElse(null);
            if (chunk != null) {
               playerchunkmap.callbackExecutor.execute(() -> chunk.loadCallback());
            }
         }).exceptionally(throwable -> {
            MinecraftServer.n.error("Failed to schedule load callback for chunk " + this.s, throwable);
            return null;
         });
         playerchunkmap.callbackExecutor.run();
      }
   }

   public static ChunkStatus b(int i) {
      return i < 33 ? ChunkStatus.o : ChunkStatus.a(i - 33);
   }

   public static PlayerChunk.State c(int i) {
      return g[MathHelper.a(33 - i + 1, 0, g.length - 1)];
   }

   public boolean m() {
      return this.A;
   }

   public void n() {
      this.A = c(this.q).a(PlayerChunk.State.b);
   }

   public void a(ProtoChunkExtension protochunkextension) {
      for(int i = 0; i < this.i.length(); ++i) {
         CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = this.i.get(i);
         if (completablefuture != null) {
            Optional<IChunkAccess> optional = ((Either)completablefuture.getNow(a)).left();
            if (!optional.isEmpty() && optional.get() instanceof ProtoChunk) {
               this.i.set(i, CompletableFuture.completedFuture(Either.left(protochunkextension)));
            }
         }
      }

      this.a(CompletableFuture.completedFuture(Either.left(protochunkextension.A())), "replaceProto");
   }

   public List<Pair<ChunkStatus, CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>>> o() {
      List<Pair<ChunkStatus, CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>>> list = new ArrayList();

      for(int i = 0; i < f.size(); ++i) {
         list.add(Pair.of(f.get(i), this.i.get(i)));
      }

      return list;
   }

   public interface Failure {
      PlayerChunk.Failure b = new PlayerChunk.Failure() {
         @Override
         public String toString() {
            return "UNLOADED";
         }
      };
   }

   public static enum State {
      a,
      b,
      c,
      d;

      public boolean a(PlayerChunk.State playerchunk_state) {
         return this.ordinal() >= playerchunk_state.ordinal();
      }
   }

   private static final class b {
      private final Thread a;
      private final CompletableFuture<?> b;
      private final String c;

      b(Thread thread, CompletableFuture<?> completablefuture, String s) {
         this.a = thread;
         this.b = completablefuture;
         this.c = s;
      }
   }

   @FunctionalInterface
   public interface d {
      void onLevelChange(ChunkCoordIntPair var1, IntSupplier var2, int var3, IntConsumer var4);
   }

   public interface e {
      List<EntityPlayer> a(ChunkCoordIntPair var1, boolean var2);
   }
}
