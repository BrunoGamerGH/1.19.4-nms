package net.minecraft.world.level.biome;

import com.google.common.hash.Hashing;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.util.LinearCongruentialGenerator;
import net.minecraft.util.MathHelper;

public class BiomeManager {
   public static final int a = QuartPos.a(8);
   private static final int b = 2;
   private static final int c = 4;
   private static final int d = 3;
   private final BiomeManager.Provider e;
   private final long f;

   public BiomeManager(BiomeManager.Provider var0, long var1) {
      this.e = var0;
      this.f = var1;
   }

   public static long a(long var0) {
      return Hashing.sha256().hashLong(var0).asLong();
   }

   public BiomeManager a(BiomeManager.Provider var0) {
      return new BiomeManager(var0, this.f);
   }

   public Holder<BiomeBase> a(BlockPosition var0) {
      int var1 = var0.u() - 2;
      int var2 = var0.v() - 2;
      int var3 = var0.w() - 2;
      int var4 = var1 >> 2;
      int var5 = var2 >> 2;
      int var6 = var3 >> 2;
      double var7 = (double)(var1 & 3) / 4.0;
      double var9 = (double)(var2 & 3) / 4.0;
      double var11 = (double)(var3 & 3) / 4.0;
      int var13 = 0;
      double var14 = Double.POSITIVE_INFINITY;

      for(int var16 = 0; var16 < 8; ++var16) {
         boolean var17 = (var16 & 4) == 0;
         boolean var18 = (var16 & 2) == 0;
         boolean var19 = (var16 & 1) == 0;
         int var20 = var17 ? var4 : var4 + 1;
         int var21 = var18 ? var5 : var5 + 1;
         int var22 = var19 ? var6 : var6 + 1;
         double var23 = var17 ? var7 : var7 - 1.0;
         double var25 = var18 ? var9 : var9 - 1.0;
         double var27 = var19 ? var11 : var11 - 1.0;
         double var29 = a(this.f, var20, var21, var22, var23, var25, var27);
         if (var14 > var29) {
            var13 = var16;
            var14 = var29;
         }
      }

      int var16 = (var13 & 4) == 0 ? var4 : var4 + 1;
      int var17 = (var13 & 2) == 0 ? var5 : var5 + 1;
      int var18 = (var13 & 1) == 0 ? var6 : var6 + 1;
      return this.e.getNoiseBiome(var16, var17, var18);
   }

   public Holder<BiomeBase> a(double var0, double var2, double var4) {
      int var6 = QuartPos.a(MathHelper.a(var0));
      int var7 = QuartPos.a(MathHelper.a(var2));
      int var8 = QuartPos.a(MathHelper.a(var4));
      return this.a(var6, var7, var8);
   }

   public Holder<BiomeBase> b(BlockPosition var0) {
      int var1 = QuartPos.a(var0.u());
      int var2 = QuartPos.a(var0.v());
      int var3 = QuartPos.a(var0.w());
      return this.a(var1, var2, var3);
   }

   public Holder<BiomeBase> a(int var0, int var1, int var2) {
      return this.e.getNoiseBiome(var0, var1, var2);
   }

   private static double a(long var0, int var2, int var3, int var4, double var5, double var7, double var9) {
      long var11 = LinearCongruentialGenerator.a(var0, (long)var2);
      var11 = LinearCongruentialGenerator.a(var11, (long)var3);
      var11 = LinearCongruentialGenerator.a(var11, (long)var4);
      var11 = LinearCongruentialGenerator.a(var11, (long)var2);
      var11 = LinearCongruentialGenerator.a(var11, (long)var3);
      var11 = LinearCongruentialGenerator.a(var11, (long)var4);
      double var13 = b(var11);
      var11 = LinearCongruentialGenerator.a(var11, var0);
      double var15 = b(var11);
      var11 = LinearCongruentialGenerator.a(var11, var0);
      double var17 = b(var11);
      return MathHelper.k(var9 + var17) + MathHelper.k(var7 + var15) + MathHelper.k(var5 + var13);
   }

   private static double b(long var0) {
      double var2 = (double)Math.floorMod(var0 >> 24, 1024) / 1024.0;
      return (var2 - 0.5) * 0.9;
   }

   public interface Provider {
      Holder<BiomeBase> getNoiseBiome(int var1, int var2, int var3);
   }
}
