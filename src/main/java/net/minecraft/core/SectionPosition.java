package net.minecraft.core;

import it.unimi.dsi.fastutil.longs.LongConsumer;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.entity.EntityAccess;

public class SectionPosition extends BaseBlockPosition {
   public static final int a = 4;
   public static final int b = 16;
   public static final int c = 15;
   public static final int d = 8;
   public static final int e = 15;
   private static final int h = 22;
   private static final int i = 20;
   private static final int j = 22;
   private static final long k = 4194303L;
   private static final long l = 1048575L;
   private static final long m = 4194303L;
   private static final int n = 0;
   private static final int o = 20;
   private static final int p = 42;
   private static final int q = 8;
   private static final int r = 0;
   private static final int s = 4;

   SectionPosition(int var0, int var1, int var2) {
      super(var0, var1, var2);
   }

   public static SectionPosition a(int var0, int var1, int var2) {
      return new SectionPosition(var0, var1, var2);
   }

   public static SectionPosition a(BlockPosition var0) {
      return new SectionPosition(a(var0.u()), a(var0.v()), a(var0.w()));
   }

   public static SectionPosition a(ChunkCoordIntPair var0, int var1) {
      return new SectionPosition(var0.e, var1, var0.f);
   }

   public static SectionPosition a(EntityAccess var0) {
      return a(var0.dg());
   }

   public static SectionPosition a(IPosition var0) {
      return new SectionPosition(b(var0.a()), b(var0.b()), b(var0.c()));
   }

   public static SectionPosition a(long var0) {
      return new SectionPosition(b(var0), c(var0), d(var0));
   }

   public static SectionPosition a(IChunkAccess var0) {
      return a(var0.f(), var0.ak());
   }

   public static long a(long var0, EnumDirection var2) {
      return a(var0, var2.j(), var2.k(), var2.l());
   }

   public static long a(long var0, int var2, int var3, int var4) {
      return b(b(var0) + var2, c(var0) + var3, d(var0) + var4);
   }

   public static int a(double var0) {
      return a(MathHelper.a(var0));
   }

   public static int a(int var0) {
      return var0 >> 4;
   }

   public static int b(double var0) {
      return MathHelper.a(var0) >> 4;
   }

   public static int b(int var0) {
      return var0 & 15;
   }

   public static short b(BlockPosition var0) {
      int var1 = b(var0.u());
      int var2 = b(var0.v());
      int var3 = b(var0.w());
      return (short)(var1 << 8 | var3 << 4 | var2 << 0);
   }

   public static int a(short var0) {
      return var0 >>> 8 & 15;
   }

   public static int b(short var0) {
      return var0 >>> 0 & 15;
   }

   public static int c(short var0) {
      return var0 >>> 4 & 15;
   }

   public int d(short var0) {
      return this.d() + a(var0);
   }

   public int e(short var0) {
      return this.e() + b(var0);
   }

   public int f(short var0) {
      return this.f() + c(var0);
   }

   public BlockPosition g(short var0) {
      return new BlockPosition(this.d(var0), this.e(var0), this.f(var0));
   }

   public static int c(int var0) {
      return var0 << 4;
   }

   public static int a(int var0, int var1) {
      return c(var0) + var1;
   }

   public static int b(long var0) {
      return (int)(var0 << 0 >> 42);
   }

   public static int c(long var0) {
      return (int)(var0 << 44 >> 44);
   }

   public static int d(long var0) {
      return (int)(var0 << 22 >> 42);
   }

   public int a() {
      return this.u();
   }

   public int b() {
      return this.v();
   }

   public int c() {
      return this.w();
   }

   public int d() {
      return c(this.a());
   }

   public int e() {
      return c(this.b());
   }

   public int f() {
      return c(this.c());
   }

   public int g() {
      return a(this.a(), 15);
   }

   public int h() {
      return a(this.b(), 15);
   }

   public int i() {
      return a(this.c(), 15);
   }

   public static long e(long var0) {
      return b(a(BlockPosition.a(var0)), a(BlockPosition.b(var0)), a(BlockPosition.c(var0)));
   }

   public static long f(long var0) {
      return var0 & -1048576L;
   }

   public BlockPosition j() {
      return new BlockPosition(c(this.a()), c(this.b()), c(this.c()));
   }

   public BlockPosition q() {
      int var0 = 8;
      return this.j().b(8, 8, 8);
   }

   public ChunkCoordIntPair r() {
      return new ChunkCoordIntPair(this.a(), this.c());
   }

   public static long c(BlockPosition var0) {
      return b(a(var0.u()), a(var0.v()), a(var0.w()));
   }

   public static long b(int var0, int var1, int var2) {
      long var3 = 0L;
      var3 |= ((long)var0 & 4194303L) << 42;
      var3 |= ((long)var1 & 1048575L) << 0;
      return var3 | ((long)var2 & 4194303L) << 20;
   }

   public long s() {
      return b(this.a(), this.b(), this.c());
   }

   public SectionPosition d(int var0, int var1, int var2) {
      return var0 == 0 && var1 == 0 && var2 == 0 ? this : new SectionPosition(this.a() + var0, this.b() + var1, this.c() + var2);
   }

   public Stream<BlockPosition> t() {
      return BlockPosition.a(this.d(), this.e(), this.f(), this.g(), this.h(), this.i());
   }

   public static Stream<SectionPosition> a(SectionPosition var0, int var1) {
      int var2 = var0.a();
      int var3 = var0.b();
      int var4 = var0.c();
      return a(var2 - var1, var3 - var1, var4 - var1, var2 + var1, var3 + var1, var4 + var1);
   }

   public static Stream<SectionPosition> a(ChunkCoordIntPair var0, int var1, int var2, int var3) {
      int var4 = var0.e;
      int var5 = var0.f;
      return a(var4 - var1, var2, var5 - var1, var4 + var1, var3 - 1, var5 + var1);
   }

   public static Stream<SectionPosition> a(final int var0, final int var1, final int var2, final int var3, final int var4, final int var5) {
      return StreamSupport.stream(new AbstractSpliterator<SectionPosition>((long)((var3 - var0 + 1) * (var4 - var1 + 1) * (var5 - var2 + 1)), 64) {
         final CursorPosition a = new CursorPosition(var0, var1, var2, var3, var4, var5);

         @Override
         public boolean tryAdvance(Consumer<? super SectionPosition> var0x) {
            if (this.a.a()) {
               var0.accept(new SectionPosition(this.a.b(), this.a.c(), this.a.d()));
               return true;
            } else {
               return false;
            }
         }
      }, false);
   }

   public static void a(BlockPosition var0, LongConsumer var1) {
      a(var0.u(), var0.v(), var0.w(), var1);
   }

   public static void a(long var0, LongConsumer var2) {
      a(BlockPosition.a(var0), BlockPosition.b(var0), BlockPosition.c(var0), var2);
   }

   public static void a(int var0, int var1, int var2, LongConsumer var3) {
      int var4 = a(var0 - 1);
      int var5 = a(var0 + 1);
      int var6 = a(var1 - 1);
      int var7 = a(var1 + 1);
      int var8 = a(var2 - 1);
      int var9 = a(var2 + 1);
      if (var4 == var5 && var6 == var7 && var8 == var9) {
         var3.accept(b(var4, var6, var8));
      } else {
         for(int var10 = var4; var10 <= var5; ++var10) {
            for(int var11 = var6; var11 <= var7; ++var11) {
               for(int var12 = var8; var12 <= var9; ++var12) {
                  var3.accept(b(var10, var11, var12));
               }
            }
         }
      }
   }
}
