package net.minecraft.server.level;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.util.thread.Mailbox;
import net.minecraft.util.thread.ThreadedMailbox;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.chunk.ChunkSection;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.ILightAccess;
import net.minecraft.world.level.chunk.NibbleArray;
import net.minecraft.world.level.lighting.LightEngine;
import org.slf4j.Logger;

public class LightEngineThreaded extends LightEngine implements AutoCloseable {
   private static final Logger d = LogUtils.getLogger();
   private final ThreadedMailbox<Runnable> e;
   private final ObjectList<Pair<LightEngineThreaded.Update, Runnable>> f = new ObjectArrayList();
   private final PlayerChunkMap g;
   private final Mailbox<ChunkTaskQueueSorter.a<Runnable>> h;
   private volatile int i = 5;
   private final AtomicBoolean j = new AtomicBoolean();

   public LightEngineThreaded(
      ILightAccess var0, PlayerChunkMap var1, boolean var2, ThreadedMailbox<Runnable> var3, Mailbox<ChunkTaskQueueSorter.a<Runnable>> var4
   ) {
      super(var0, true, var2);
      this.g = var1;
      this.h = var4;
      this.e = var3;
   }

   @Override
   public void close() {
   }

   @Override
   public int a(int var0, boolean var1, boolean var2) {
      throw (UnsupportedOperationException)SystemUtils.b(new UnsupportedOperationException("Ran automatically on a different thread!"));
   }

   @Override
   public void a(BlockPosition var0, int var1) {
      throw (UnsupportedOperationException)SystemUtils.b(new UnsupportedOperationException("Ran automatically on a different thread!"));
   }

   @Override
   public void a(BlockPosition var0) {
      BlockPosition var1 = var0.i();
      this.a(
         SectionPosition.a(var0.u()),
         SectionPosition.a(var0.w()),
         LightEngineThreaded.Update.b,
         SystemUtils.a(() -> super.a(var1), () -> "checkBlock " + var1)
      );
   }

   protected void a(ChunkCoordIntPair var0) {
      this.a(var0.e, var0.f, () -> 0, LightEngineThreaded.Update.a, SystemUtils.a(() -> {
         super.b(var0, false);
         super.a(var0, false);

         for(int var1x = this.c(); var1x < this.d(); ++var1x) {
            super.a(EnumSkyBlock.b, SectionPosition.a(var0, var1x), null, true);
            super.a(EnumSkyBlock.a, SectionPosition.a(var0, var1x), null, true);
         }

         for(int var1 = this.c.ak(); var1 < this.c.al(); ++var1) {
            super.a(SectionPosition.a(var0, var1), true);
         }
      }, () -> "updateChunkStatus " + var0 + " true"));
   }

   @Override
   public void a(SectionPosition var0, boolean var1) {
      this.a(
         var0.a(), var0.c(), () -> 0, LightEngineThreaded.Update.a, SystemUtils.a(() -> super.a(var0, var1), () -> "updateSectionStatus " + var0 + " " + var1)
      );
   }

   @Override
   public void a(ChunkCoordIntPair var0, boolean var1) {
      this.a(var0.e, var0.f, LightEngineThreaded.Update.a, SystemUtils.a(() -> super.a(var0, var1), () -> "enableLight " + var0 + " " + var1));
   }

   @Override
   public void a(EnumSkyBlock var0, SectionPosition var1, @Nullable NibbleArray var2, boolean var3) {
      this.a(var1.a(), var1.c(), () -> 0, LightEngineThreaded.Update.a, SystemUtils.a(() -> super.a(var0, var1, var2, var3), () -> "queueData " + var1));
   }

   private void a(int var0, int var1, LightEngineThreaded.Update var2, Runnable var3) {
      this.a(var0, var1, this.g.c(ChunkCoordIntPair.c(var0, var1)), var2, var3);
   }

   private void a(int var0, int var1, IntSupplier var2, LightEngineThreaded.Update var3, Runnable var4) {
      this.h.a(ChunkTaskQueueSorter.a(() -> {
         this.f.add(Pair.of(var3, var4));
         if (this.f.size() >= this.i) {
            this.e();
         }
      }, ChunkCoordIntPair.c(var0, var1), var2));
   }

   @Override
   public void b(ChunkCoordIntPair var0, boolean var1) {
      this.a(var0.e, var0.f, () -> 0, LightEngineThreaded.Update.a, SystemUtils.a(() -> super.b(var0, var1), () -> "retainData " + var0));
   }

   public CompletableFuture<IChunkAccess> a(IChunkAccess var0) {
      ChunkCoordIntPair var1 = var0.f();
      return CompletableFuture.supplyAsync(SystemUtils.a(() -> {
         super.b(var1, true);
         return var0;
      }, () -> "retainData: " + var1), var1x -> this.a(var1.e, var1.f, LightEngineThreaded.Update.a, var1x));
   }

   public CompletableFuture<IChunkAccess> a(IChunkAccess var0, boolean var1) {
      ChunkCoordIntPair var2 = var0.f();
      var0.b(false);
      this.a(var2.e, var2.f, LightEngineThreaded.Update.a, SystemUtils.a(() -> {
         ChunkSection[] var3x = var0.d();

         for(int var4 = 0; var4 < var0.aj(); ++var4) {
            ChunkSection var5 = var3x[var4];
            if (!var5.c()) {
               int var6 = this.c.g(var4);
               super.a(SectionPosition.a(var2, var6), false);
            }
         }

         super.a(var2, true);
         if (!var1) {
            var0.n().forEach(var1xx -> super.a(var1xx, var0.h(var1xx)));
         }
      }, () -> "lightChunk " + var2 + " " + var1));
      return CompletableFuture.supplyAsync(() -> {
         var0.b(true);
         super.b(var2, false);
         this.g.c(var2);
         return var0;
      }, var1x -> this.a(var2.e, var2.f, LightEngineThreaded.Update.b, var1x));
   }

   public void a() {
      if ((!this.f.isEmpty() || super.D_()) && this.j.compareAndSet(false, true)) {
         this.e.a(() -> {
            this.e();
            this.j.set(false);
         });
      }
   }

   private void e() {
      int var0 = Math.min(this.f.size(), this.i);
      ObjectListIterator<Pair<LightEngineThreaded.Update, Runnable>> var1 = this.f.iterator();

      int var2;
      for(var2 = 0; var1.hasNext() && var2 < var0; ++var2) {
         Pair<LightEngineThreaded.Update, Runnable> var3 = (Pair)var1.next();
         if (var3.getFirst() == LightEngineThreaded.Update.a) {
            ((Runnable)var3.getSecond()).run();
         }
      }

      var1.back(var2);
      super.a(Integer.MAX_VALUE, true, true);

      for(int var5 = 0; var1.hasNext() && var5 < var0; ++var5) {
         Pair<LightEngineThreaded.Update, Runnable> var3 = (Pair)var1.next();
         if (var3.getFirst() == LightEngineThreaded.Update.b) {
            ((Runnable)var3.getSecond()).run();
         }

         var1.remove();
      }
   }

   public void a(int var0) {
      this.i = var0;
   }

   static enum Update {
      a,
      b;
   }
}
