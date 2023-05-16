package net.minecraft.server.level;

import net.minecraft.core.SectionPosition;
import net.minecraft.world.level.ChunkCoordIntPair;

public record BlockPosition2D(int x, int z) {
   private final int a;
   private final int b;
   private static final long c = 32L;
   private static final long d = 4294967295L;

   public BlockPosition2D(int var0, int var1) {
      this.a = var0;
      this.b = var1;
   }

   public ChunkCoordIntPair a() {
      return new ChunkCoordIntPair(SectionPosition.a(this.a), SectionPosition.a(this.b));
   }

   public long b() {
      return a(this.a, this.b);
   }

   public static long a(int var0, int var1) {
      return (long)var0 & 4294967295L | ((long)var1 & 4294967295L) << 32;
   }

   public static int a(long var0) {
      return (int)(var0 & 4294967295L);
   }

   public static int b(long var0) {
      return (int)(var0 >>> 32 & 4294967295L);
   }

   @Override
   public String toString() {
      return "[" + this.a + ", " + this.b + "]";
   }

   @Override
   public int hashCode() {
      return ChunkCoordIntPair.d(this.a, this.b);
   }

   public int c() {
      return this.a;
   }

   public int d() {
      return this.b;
   }
}
