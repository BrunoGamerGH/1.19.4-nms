package net.minecraft.world.level.chunk;

import java.util.BitSet;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.ChunkCoordIntPair;

public class CarvingMask {
   private final int a;
   private final BitSet b;
   private CarvingMask.a c = (var0x, var1x, var2x) -> false;

   public CarvingMask(int var0, int var1) {
      this.a = var1;
      this.b = new BitSet(256 * var0);
   }

   public void a(CarvingMask.a var0) {
      this.c = var0;
   }

   public CarvingMask(long[] var0, int var1) {
      this.a = var1;
      this.b = BitSet.valueOf(var0);
   }

   private int c(int var0, int var1, int var2) {
      return var0 & 15 | (var2 & 15) << 4 | var1 - this.a << 8;
   }

   public void a(int var0, int var1, int var2) {
      this.b.set(this.c(var0, var1, var2));
   }

   public boolean b(int var0, int var1, int var2) {
      return this.c.test(var0, var1, var2) || this.b.get(this.c(var0, var1, var2));
   }

   public Stream<BlockPosition> a(ChunkCoordIntPair var0) {
      return this.b.stream().mapToObj(var1x -> {
         int var2 = var1x & 15;
         int var3 = var1x >> 4 & 15;
         int var4 = var1x >> 8;
         return var0.a(var2, var4 + this.a, var3);
      });
   }

   public long[] a() {
      return this.b.toLongArray();
   }

   public interface a {
      boolean test(int var1, int var2, int var3);
   }
}
