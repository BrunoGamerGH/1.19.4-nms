package net.minecraft.gametest.framework;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2LongMap.Entry;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.TileEntityStructure;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.phys.AxisAlignedBB;

public class GameTestHarnessInfo {
   private final GameTestHarnessTestFunction a;
   @Nullable
   private BlockPosition b;
   private final WorldServer c;
   private final Collection<GameTestHarnessListener> d = Lists.newArrayList();
   private final int e;
   private final Collection<GameTestHarnessSequence> f = Lists.newCopyOnWriteArrayList();
   private final Object2LongMap<Runnable> g = new Object2LongOpenHashMap();
   private long h;
   private long i;
   private boolean j;
   private final Stopwatch k = Stopwatch.createUnstarted();
   private boolean l;
   private final EnumBlockRotation m;
   @Nullable
   private Throwable n;
   @Nullable
   private TileEntityStructure o;

   public GameTestHarnessInfo(GameTestHarnessTestFunction var0, EnumBlockRotation var1, WorldServer var2) {
      this.a = var0;
      this.c = var2;
      this.e = var0.c();
      this.m = var0.g().a(var1);
   }

   void a(BlockPosition var0) {
      this.b = var0;
   }

   void a() {
      this.h = this.c.U() + 1L + this.a.f();
      this.k.start();
   }

   public void b() {
      if (!this.k()) {
         this.A();
         if (this.k()) {
            if (this.n != null) {
               this.d.forEach(var0 -> var0.c(this));
            } else {
               this.d.forEach(var0 -> var0.b(this));
            }
         }
      }
   }

   private void A() {
      this.i = this.c.U() - this.h;
      if (this.i >= 0L) {
         if (this.i == 0L) {
            this.B();
         }

         ObjectIterator<Entry<Runnable>> var0 = this.g.object2LongEntrySet().iterator();

         while(var0.hasNext()) {
            Entry<Runnable> var1 = (Entry)var0.next();
            if (var1.getLongValue() <= this.i) {
               try {
                  ((Runnable)var1.getKey()).run();
               } catch (Exception var4) {
                  this.a(var4);
               }

               var0.remove();
            }
         }

         if (this.i > (long)this.e) {
            if (this.f.isEmpty()) {
               this.a(new GameTestHarnessTimeout("Didn't succeed or fail within " + this.a.c() + " ticks"));
            } else {
               this.f.forEach(var0x -> var0x.b(this.i));
               if (this.n == null) {
                  this.a(new GameTestHarnessTimeout("No sequences finished"));
               }
            }
         } else {
            this.f.forEach(var0x -> var0x.a(this.i));
         }
      }
   }

   private void B() {
      if (this.j) {
         throw new IllegalStateException("Test already started");
      } else {
         this.j = true;

         try {
            this.a.a(new GameTestHarnessHelper(this));
         } catch (Exception var2) {
            this.a(var2);
         }
      }
   }

   public void a(long var0, Runnable var2) {
      this.g.put(var2, var0);
   }

   public String c() {
      return this.a.a();
   }

   public BlockPosition d() {
      return this.b;
   }

   @Nullable
   public BaseBlockPosition e() {
      TileEntityStructure var0 = this.C();
      return var0 == null ? null : var0.j();
   }

   @Nullable
   public AxisAlignedBB f() {
      TileEntityStructure var0 = this.C();
      return var0 == null ? null : GameTestHarnessStructures.a(var0);
   }

   @Nullable
   private TileEntityStructure C() {
      return (TileEntityStructure)this.c.c_(this.b);
   }

   public WorldServer g() {
      return this.c;
   }

   public boolean h() {
      return this.l && this.n == null;
   }

   public boolean i() {
      return this.n != null;
   }

   public boolean j() {
      return this.j;
   }

   public boolean k() {
      return this.l;
   }

   public long l() {
      return this.k.elapsed(TimeUnit.MILLISECONDS);
   }

   private void D() {
      if (!this.l) {
         this.l = true;
         this.k.stop();
      }
   }

   public void m() {
      if (this.n == null) {
         this.D();
      }
   }

   public void a(Throwable var0) {
      this.n = var0;
      this.D();
   }

   @Nullable
   public Throwable n() {
      return this.n;
   }

   @Override
   public String toString() {
      return this.c();
   }

   public void a(GameTestHarnessListener var0) {
      this.d.add(var0);
   }

   public void a(BlockPosition var0, int var1) {
      this.o = GameTestHarnessStructures.a(this.t(), var0, this.u(), var1, this.c, false);
      this.b = this.o.p();
      this.o.a(this.c());
      GameTestHarnessStructures.a(this.b, new BlockPosition(1, 0, -1), this.u(), this.c);
      this.d.forEach(var0x -> var0x.a(this));
   }

   public void o() {
      if (this.o == null) {
         throw new IllegalStateException("Expected structure to be initialized, but it was null");
      } else {
         StructureBoundingBox var0 = GameTestHarnessStructures.b(this.o);
         GameTestHarnessStructures.a(var0, this.b.v(), this.c);
      }
   }

   long p() {
      return this.i;
   }

   GameTestHarnessSequence q() {
      GameTestHarnessSequence var0 = new GameTestHarnessSequence(this);
      this.f.add(var0);
      return var0;
   }

   public boolean r() {
      return this.a.d();
   }

   public boolean s() {
      return !this.a.d();
   }

   public String t() {
      return this.a.b();
   }

   public EnumBlockRotation u() {
      return this.m;
   }

   public GameTestHarnessTestFunction v() {
      return this.a;
   }

   public int w() {
      return this.e;
   }

   public boolean x() {
      return this.a.h();
   }

   public int y() {
      return this.a.i();
   }

   public int z() {
      return this.a.j();
   }
}
