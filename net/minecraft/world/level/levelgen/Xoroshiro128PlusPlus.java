package net.minecraft.world.level.levelgen;

public class Xoroshiro128PlusPlus {
   private long a;
   private long b;

   public Xoroshiro128PlusPlus(RandomSupport.a var0) {
      this(var0.a(), var0.b());
   }

   public Xoroshiro128PlusPlus(long var0, long var2) {
      this.a = var0;
      this.b = var2;
      if ((this.a | this.b) == 0L) {
         this.a = -7046029254386353131L;
         this.b = 7640891576956012809L;
      }
   }

   public long a() {
      long var0 = this.a;
      long var2 = this.b;
      long var4 = Long.rotateLeft(var0 + var2, 17) + var0;
      var2 ^= var0;
      this.a = Long.rotateLeft(var0, 49) ^ var2 ^ var2 << 21;
      this.b = Long.rotateLeft(var2, 28);
      return var4;
   }
}
