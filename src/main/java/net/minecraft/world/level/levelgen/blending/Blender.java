package net.minecraft.world.level.levelgen.blending;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.EnumDirection8;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.server.level.RegionLimitedWorldAccess;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;
import net.minecraft.world.level.material.Fluid;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableObject;

public class Blender {
   private static final Blender a = new Blender(new Long2ObjectOpenHashMap(), new Long2ObjectOpenHashMap()) {
      @Override
      public Blender.a a(int var0, int var1) {
         return new Blender.a(1.0, 0.0);
      }

      @Override
      public double a(DensityFunction.b var0, double var1) {
         return var1;
      }

      @Override
      public BiomeResolver a(BiomeResolver var0) {
         return var0;
      }
   };
   private static final NoiseGeneratorNormal b = NoiseGeneratorNormal.b(new XoroshiroRandomSource(42L), NoiseData.a);
   private static final int c = QuartPos.d(7) - 1;
   private static final int d = QuartPos.e(c + 3);
   private static final int e = 2;
   private static final int f = QuartPos.e(5);
   private static final double g = 8.0;
   private final Long2ObjectOpenHashMap<BlendingData> h;
   private final Long2ObjectOpenHashMap<BlendingData> i;

   public static Blender a() {
      return a;
   }

   public static Blender a(@Nullable RegionLimitedWorldAccess var0) {
      if (var0 == null) {
         return a;
      } else {
         ChunkCoordIntPair var1 = var0.a();
         if (!var0.a(var1, d)) {
            return a;
         } else {
            Long2ObjectOpenHashMap<BlendingData> var2 = new Long2ObjectOpenHashMap();
            Long2ObjectOpenHashMap<BlendingData> var3 = new Long2ObjectOpenHashMap();
            int var4 = MathHelper.h(d + 1);

            for(int var5 = -d; var5 <= d; ++var5) {
               for(int var6 = -d; var6 <= d; ++var6) {
                  if (var5 * var5 + var6 * var6 <= var4) {
                     int var7 = var1.e + var5;
                     int var8 = var1.f + var6;
                     BlendingData var9 = BlendingData.a(var0, var7, var8);
                     if (var9 != null) {
                        var2.put(ChunkCoordIntPair.c(var7, var8), var9);
                        if (var5 >= -f && var5 <= f && var6 >= -f && var6 <= f) {
                           var3.put(ChunkCoordIntPair.c(var7, var8), var9);
                        }
                     }
                  }
               }
            }

            return var2.isEmpty() && var3.isEmpty() ? a : new Blender(var2, var3);
         }
      }
   }

   Blender(Long2ObjectOpenHashMap<BlendingData> var0, Long2ObjectOpenHashMap<BlendingData> var1) {
      this.h = var0;
      this.i = var1;
   }

   public Blender.a a(int var0, int var1) {
      int var2 = QuartPos.a(var0);
      int var3 = QuartPos.a(var1);
      double var4 = this.a(var2, 0, var3, BlendingData::a);
      if (var4 != Double.MAX_VALUE) {
         return new Blender.a(0.0, a(var4));
      } else {
         MutableDouble var6 = new MutableDouble(0.0);
         MutableDouble var7 = new MutableDouble(0.0);
         MutableDouble var8 = new MutableDouble(Double.POSITIVE_INFINITY);
         this.h.forEach((var5x, var6x) -> var6x.a(QuartPos.d(ChunkCoordIntPair.a(var5x)), QuartPos.d(ChunkCoordIntPair.b(var5x)), (var5xx, var6xx, var7x) -> {
               double var9x = MathHelper.f((double)(var2 - var5xx), (double)(var3 - var6xx));
               if (!(var9x > (double)c)) {
                  if (var9x < var8.doubleValue()) {
                     var8.setValue(var9x);
                  }

                  double var11x = 1.0 / (var9x * var9x * var9x * var9x);
                  var7.add(var7x * var11x);
                  var6.add(var11x);
               }
            }));
         if (var8.doubleValue() == Double.POSITIVE_INFINITY) {
            return new Blender.a(1.0, 0.0);
         } else {
            double var9 = var7.doubleValue() / var6.doubleValue();
            double var11 = MathHelper.a(var8.doubleValue() / (double)(c + 1), 0.0, 1.0);
            var11 = 3.0 * var11 * var11 - 2.0 * var11 * var11 * var11;
            return new Blender.a(var11, a(var9));
         }
      }
   }

   private static double a(double var0) {
      double var2 = 1.0;
      double var4 = var0 + 0.5;
      double var6 = MathHelper.c(var4, 8.0);
      return 1.0 * (32.0 * (var4 - 128.0) - 3.0 * (var4 - 120.0) * var6 + 3.0 * var6 * var6) / (128.0 * (32.0 - 3.0 * var6));
   }

   public double a(DensityFunction.b var0, double var1) {
      int var3 = QuartPos.a(var0.a());
      int var4 = var0.b() / 8;
      int var5 = QuartPos.a(var0.c());
      double var6 = this.a(var3, var4, var5, BlendingData::b);
      if (var6 != Double.MAX_VALUE) {
         return var6;
      } else {
         MutableDouble var8 = new MutableDouble(0.0);
         MutableDouble var9 = new MutableDouble(0.0);
         MutableDouble var10 = new MutableDouble(Double.POSITIVE_INFINITY);
         this.i
            .forEach(
               (var6x, var7x) -> var7x.a(
                     QuartPos.d(ChunkCoordIntPair.a(var6x)), QuartPos.d(ChunkCoordIntPair.b(var6x)), var4 - 1, var4 + 1, (var6xx, var7xx, var8x, var9x) -> {
                        double var11x = MathHelper.f((double)(var3 - var6xx), (double)((var4 - var7xx) * 2), (double)(var5 - var8x));
                        if (!(var11x > 2.0)) {
                           if (var11x < var10.doubleValue()) {
                              var10.setValue(var11x);
                           }
         
                           double var13x = 1.0 / (var11x * var11x * var11x * var11x);
                           var9.add(var9x * var13x);
                           var8.add(var13x);
                        }
                     }
                  )
            );
         if (var10.doubleValue() == Double.POSITIVE_INFINITY) {
            return var1;
         } else {
            double var11 = var9.doubleValue() / var8.doubleValue();
            double var13 = MathHelper.a(var10.doubleValue() / 3.0, 0.0, 1.0);
            return MathHelper.d(var13, var11, var1);
         }
      }
   }

   private double a(int var0, int var1, int var2, Blender.b var3) {
      int var4 = QuartPos.e(var0);
      int var5 = QuartPos.e(var2);
      boolean var6 = (var0 & 3) == 0;
      boolean var7 = (var2 & 3) == 0;
      double var8 = this.a(var3, var4, var5, var0, var1, var2);
      if (var8 == Double.MAX_VALUE) {
         if (var6 && var7) {
            var8 = this.a(var3, var4 - 1, var5 - 1, var0, var1, var2);
         }

         if (var8 == Double.MAX_VALUE) {
            if (var6) {
               var8 = this.a(var3, var4 - 1, var5, var0, var1, var2);
            }

            if (var8 == Double.MAX_VALUE && var7) {
               var8 = this.a(var3, var4, var5 - 1, var0, var1, var2);
            }
         }
      }

      return var8;
   }

   private double a(Blender.b var0, int var1, int var2, int var3, int var4, int var5) {
      BlendingData var6 = (BlendingData)this.h.get(ChunkCoordIntPair.c(var1, var2));
      return var6 != null ? var0.get(var6, var3 - QuartPos.d(var1), var4, var5 - QuartPos.d(var2)) : Double.MAX_VALUE;
   }

   public BiomeResolver a(BiomeResolver var0) {
      return (var1x, var2, var3, var4) -> {
         Holder<BiomeBase> var5 = this.a(var1x, var2, var3);
         return var5 == null ? var0.getNoiseBiome(var1x, var2, var3, var4) : var5;
      };
   }

   @Nullable
   private Holder<BiomeBase> a(int var0, int var1, int var2) {
      MutableDouble var3 = new MutableDouble(Double.POSITIVE_INFINITY);
      MutableObject<Holder<BiomeBase>> var4 = new MutableObject();
      this.h
         .forEach(
            (var5x, var6x) -> var6x.a(QuartPos.d(ChunkCoordIntPair.a(var5x)), var1, QuartPos.d(ChunkCoordIntPair.b(var5x)), (var4xx, var5xx, var6xx) -> {
                  double var7x = MathHelper.f((double)(var0 - var4xx), (double)(var2 - var5xx));
                  if (!(var7x > (double)c)) {
                     if (var7x < var3.doubleValue()) {
                        var4.setValue(var6xx);
                        var3.setValue(var7x);
                     }
                  }
               })
         );
      if (var3.doubleValue() == Double.POSITIVE_INFINITY) {
         return null;
      } else {
         double var5 = b.a((double)var0, 0.0, (double)var2) * 12.0;
         double var7 = MathHelper.a((var3.doubleValue() + var5) / (double)(c + 1), 0.0, 1.0);
         return var7 > 0.5 ? null : (Holder)var4.getValue();
      }
   }

   public static void a(RegionLimitedWorldAccess var0, IChunkAccess var1) {
      ChunkCoordIntPair var2 = var1.f();
      boolean var3 = var1.s();
      BlockPosition.MutableBlockPosition var4 = new BlockPosition.MutableBlockPosition();
      BlockPosition var5 = new BlockPosition(var2.d(), 0, var2.e());
      BlendingData var6 = var1.t();
      if (var6 != null) {
         int var7 = var6.a().v_();
         int var8 = var6.a().ai() - 1;
         if (var3) {
            for(int var9 = 0; var9 < 16; ++var9) {
               for(int var10 = 0; var10 < 16; ++var10) {
                  a(var1, var4.a(var5, var9, var7 - 1, var10));
                  a(var1, var4.a(var5, var9, var7, var10));
                  a(var1, var4.a(var5, var9, var8, var10));
                  a(var1, var4.a(var5, var9, var8 + 1, var10));
               }
            }
         }

         for(EnumDirection var10 : EnumDirection.EnumDirectionLimit.a) {
            if (var0.a(var2.e + var10.j(), var2.f + var10.l()).s() != var3) {
               int var11 = var10 == EnumDirection.f ? 15 : 0;
               int var12 = var10 == EnumDirection.e ? 0 : 15;
               int var13 = var10 == EnumDirection.d ? 15 : 0;
               int var14 = var10 == EnumDirection.c ? 0 : 15;

               for(int var15 = var11; var15 <= var12; ++var15) {
                  for(int var16 = var13; var16 <= var14; ++var16) {
                     int var17 = Math.min(var8, var1.a(HeightMap.Type.e, var15, var16)) + 1;

                     for(int var18 = var7; var18 < var17; ++var18) {
                        a(var1, var4.a(var5, var15, var18, var16));
                     }
                  }
               }
            }
         }
      }
   }

   private static void a(IChunkAccess var0, BlockPosition var1) {
      IBlockData var2 = var0.a_(var1);
      if (var2.a(TagsBlock.N)) {
         var0.e(var1);
      }

      Fluid var3 = var0.b_(var1);
      if (!var3.c()) {
         var0.e(var1);
      }
   }

   public static void a(GeneratorAccessSeed var0, ProtoChunk var1) {
      ChunkCoordIntPair var2 = var1.f();
      Builder<EnumDirection8, BlendingData> var3 = ImmutableMap.builder();

      for(EnumDirection8 var7 : EnumDirection8.values()) {
         int var8 = var2.e + var7.b();
         int var9 = var2.f + var7.c();
         BlendingData var10 = var0.a(var8, var9).t();
         if (var10 != null) {
            var3.put(var7, var10);
         }
      }

      ImmutableMap<EnumDirection8, BlendingData> var4 = var3.build();
      if (var1.s() || !var4.isEmpty()) {
         Blender.c var5 = a(var1.t(), var4);
         CarvingMask.a var6 = (var1x, var2x, var3x) -> {
            double var4 = (double)var1x + 0.5 + b.a((double)var1x, (double)var2x, (double)var3x) * 4.0;
            double var6 = (double)var2x + 0.5 + b.a((double)var2x, (double)var3x, (double)var1x) * 4.0;
            double var8x = (double)var3x + 0.5 + b.a((double)var3x, (double)var1x, (double)var2x) * 4.0;
            return var5.getDistance(var4, var6, var8x) < 4.0;
         };
         Stream.of(WorldGenStage.Features.values()).map(var1::b).forEach(var1x -> var1x.a(var6));
      }
   }

   public static Blender.c a(@Nullable BlendingData var0, Map<EnumDirection8, BlendingData> var1) {
      List<Blender.c> var2 = Lists.newArrayList();
      if (var0 != null) {
         var2.add(a(null, var0));
      }

      var1.forEach((var1x, var2x) -> var2.add(a(var1x, var2x)));
      return (var1x, var3, var5) -> {
         double var7 = Double.POSITIVE_INFINITY;

         for(Blender.c var10 : var2) {
            double var11 = var10.getDistance(var1x, var3, var5);
            if (var11 < var7) {
               var7 = var11;
            }
         }

         return var7;
      };
   }

   private static Blender.c a(@Nullable EnumDirection8 var0, BlendingData var1) {
      double var2 = 0.0;
      double var4 = 0.0;
      if (var0 != null) {
         for(EnumDirection var7 : var0.a()) {
            var2 += (double)(var7.j() * 16);
            var4 += (double)(var7.l() * 16);
         }
      }

      double var6 = var2;
      double var8 = var4;
      double var10 = (double)var1.a().w_() / 2.0;
      double var12 = (double)var1.a().v_() + var10;
      return (var8x, var10x, var12x) -> a(var8x - 8.0 - var6, var10x - var12, var12x - 8.0 - var8, 8.0, var10, 8.0);
   }

   private static double a(double var0, double var2, double var4, double var6, double var8, double var10) {
      double var12 = Math.abs(var0) - var6;
      double var14 = Math.abs(var2) - var8;
      double var16 = Math.abs(var4) - var10;
      return MathHelper.f(Math.max(0.0, var12), Math.max(0.0, var14), Math.max(0.0, var16));
   }

   public static record a(double alpha, double blendingOffset) {
      private final double a;
      private final double b;

      public a(double var0, double var2) {
         this.a = var0;
         this.b = var2;
      }
   }

   interface b {
      double get(BlendingData var1, int var2, int var3, int var4);
   }

   public interface c {
      double getDistance(double var1, double var3, double var5);
   }
}
