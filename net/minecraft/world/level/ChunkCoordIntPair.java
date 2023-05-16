package net.minecraft.world.level;

import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;

public class ChunkCoordIntPair {
   private static final int g = 1056;
   public static final long a = c(1875066, 1875066);
   public static final ChunkCoordIntPair b = new ChunkCoordIntPair(0, 0);
   private static final long h = 32L;
   private static final long i = 4294967295L;
   private static final int j = 5;
   public static final int c = 32;
   private static final int k = 31;
   public static final int d = 31;
   public final int e;
   public final int f;
   private static final int l = 1664525;
   private static final int m = 1013904223;
   private static final int n = -559038737;

   public ChunkCoordIntPair(int var0, int var1) {
      this.e = var0;
      this.f = var1;
   }

   public ChunkCoordIntPair(BlockPosition var0) {
      this.e = SectionPosition.a(var0.u());
      this.f = SectionPosition.a(var0.w());
   }

   public ChunkCoordIntPair(long var0) {
      this.e = (int)var0;
      this.f = (int)(var0 >> 32);
   }

   public static ChunkCoordIntPair a(int var0, int var1) {
      return new ChunkCoordIntPair(var0 << 5, var1 << 5);
   }

   public static ChunkCoordIntPair b(int var0, int var1) {
      return new ChunkCoordIntPair((var0 << 5) + 31, (var1 << 5) + 31);
   }

   public long a() {
      return c(this.e, this.f);
   }

   public static long c(int var0, int var1) {
      return (long)var0 & 4294967295L | ((long)var1 & 4294967295L) << 32;
   }

   public static long a(BlockPosition var0) {
      return c(SectionPosition.a(var0.u()), SectionPosition.a(var0.w()));
   }

   public static int a(long var0) {
      return (int)(var0 & 4294967295L);
   }

   public static int b(long var0) {
      return (int)(var0 >>> 32 & 4294967295L);
   }

   @Override
   public int hashCode() {
      return d(this.e, this.f);
   }

   public static int d(int var0, int var1) {
      int var2 = 1664525 * var0 + 1013904223;
      int var3 = 1664525 * (var1 ^ -559038737) + 1013904223;
      return var2 ^ var3;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof ChunkCoordIntPair)) {
         return false;
      } else {
         ChunkCoordIntPair var1 = (ChunkCoordIntPair)var0;
         return this.e == var1.e && this.f == var1.f;
      }
   }

   public int b() {
      return this.a(8);
   }

   public int c() {
      return this.b(8);
   }

   public int d() {
      return SectionPosition.c(this.e);
   }

   public int e() {
      return SectionPosition.c(this.f);
   }

   public int f() {
      return this.a(15);
   }

   public int g() {
      return this.b(15);
   }

   public int h() {
      return this.e >> 5;
   }

   public int i() {
      return this.f >> 5;
   }

   public int j() {
      return this.e & 31;
   }

   public int k() {
      return this.f & 31;
   }

   public BlockPosition a(int var0, int var1, int var2) {
      return new BlockPosition(this.a(var0), var1, this.b(var2));
   }

   public int a(int var0) {
      return SectionPosition.a(this.e, var0);
   }

   public int b(int var0) {
      return SectionPosition.a(this.f, var0);
   }

   public BlockPosition c(int var0) {
      return new BlockPosition(this.b(), var0, this.c());
   }

   @Override
   public String toString() {
      return "[" + this.e + ", " + this.f + "]";
   }

   public BlockPosition l() {
      return new BlockPosition(this.d(), 0, this.e());
   }

   public int a(ChunkCoordIntPair var0) {
      return Math.max(Math.abs(this.e - var0.e), Math.abs(this.f - var0.f));
   }

   public static Stream<ChunkCoordIntPair> a(ChunkCoordIntPair var0, int var1) {
      return a(new ChunkCoordIntPair(var0.e - var1, var0.f - var1), new ChunkCoordIntPair(var0.e + var1, var0.f + var1));
   }

   public static Stream<ChunkCoordIntPair> a(final ChunkCoordIntPair var0, final ChunkCoordIntPair var1) {
      int var2 = Math.abs(var0.e - var1.e) + 1;
      int var3 = Math.abs(var0.f - var1.f) + 1;
      final int var4 = var0.e < var1.e ? 1 : -1;
      final int var5 = var0.f < var1.f ? 1 : -1;
      return StreamSupport.stream(new AbstractSpliterator<ChunkCoordIntPair>((long)(var2 * var3), 64) {
         @Nullable
         private ChunkCoordIntPair e;

         @Override
         public boolean tryAdvance(Consumer<? super ChunkCoordIntPair> var0x) {
            if (this.e == null) {
               this.e = var0;
            } else {
               int var1 = this.e.e;
               int var2 = this.e.f;
               if (var1 == var1.e) {
                  if (var2 == var1.f) {
                     return false;
                  }

                  this.e = new ChunkCoordIntPair(var0.e, var2 + var5);
               } else {
                  this.e = new ChunkCoordIntPair(var1 + var4, var2);
               }
            }

            var0.accept(this.e);
            return true;
         }
      }, false);
   }
}
