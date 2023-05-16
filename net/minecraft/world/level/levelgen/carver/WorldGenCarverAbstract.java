package net.minecraft.world.level.levelgen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import org.apache.commons.lang3.mutable.MutableBoolean;

public abstract class WorldGenCarverAbstract<C extends WorldGenCarverConfiguration> {
   public static final WorldGenCarverAbstract<CaveCarverConfiguration> a = a("cave", new WorldGenCaves(CaveCarverConfiguration.a));
   public static final WorldGenCarverAbstract<CaveCarverConfiguration> b = a("nether_cave", new WorldGenCavesHell(CaveCarverConfiguration.a));
   public static final WorldGenCarverAbstract<CanyonCarverConfiguration> c = a("canyon", new WorldGenCanyon(CanyonCarverConfiguration.a));
   protected static final IBlockData d = Blocks.a.o();
   protected static final IBlockData e = Blocks.mY.o();
   protected static final Fluid f = FluidTypes.c.g();
   protected static final Fluid g = FluidTypes.e.g();
   protected Set<FluidType> h = ImmutableSet.of(FluidTypes.c);
   private final Codec<WorldGenCarverWrapper<C>> i;

   private static <C extends WorldGenCarverConfiguration, F extends WorldGenCarverAbstract<C>> F a(String var0, F var1) {
      return IRegistry.a(BuiltInRegistries.P, var0, var1);
   }

   public WorldGenCarverAbstract(Codec<C> var0) {
      this.i = var0.fieldOf("config").xmap(this::a, WorldGenCarverWrapper::b).codec();
   }

   public WorldGenCarverWrapper<C> a(C var0) {
      return new WorldGenCarverWrapper<>(this, var0);
   }

   public Codec<WorldGenCarverWrapper<C>> c() {
      return this.i;
   }

   public int d() {
      return 4;
   }

   protected boolean a(
      CarvingContext var0,
      C var1,
      IChunkAccess var2,
      Function<BlockPosition, Holder<BiomeBase>> var3,
      Aquifer var4,
      double var5,
      double var7,
      double var9,
      double var11,
      double var13,
      CarvingMask var15,
      WorldGenCarverAbstract.a var16
   ) {
      ChunkCoordIntPair var17 = var2.f();
      double var18 = (double)var17.b();
      double var20 = (double)var17.c();
      double var22 = 16.0 + var11 * 2.0;
      if (!(Math.abs(var5 - var18) > var22) && !(Math.abs(var9 - var20) > var22)) {
         int var24 = var17.d();
         int var25 = var17.e();
         int var26 = Math.max(MathHelper.a(var5 - var11) - var24 - 1, 0);
         int var27 = Math.min(MathHelper.a(var5 + var11) - var24, 15);
         int var28 = Math.max(MathHelper.a(var7 - var13) - 1, var0.a() + 1);
         int var29 = var2.y() ? 0 : 7;
         int var30 = Math.min(MathHelper.a(var7 + var13) + 1, var0.a() + var0.b() - 1 - var29);
         int var31 = Math.max(MathHelper.a(var9 - var11) - var25 - 1, 0);
         int var32 = Math.min(MathHelper.a(var9 + var11) - var25, 15);
         boolean var33 = false;
         BlockPosition.MutableBlockPosition var34 = new BlockPosition.MutableBlockPosition();
         BlockPosition.MutableBlockPosition var35 = new BlockPosition.MutableBlockPosition();

         for(int var36 = var26; var36 <= var27; ++var36) {
            int var37 = var17.a(var36);
            double var38 = ((double)var37 + 0.5 - var5) / var11;

            for(int var40 = var31; var40 <= var32; ++var40) {
               int var41 = var17.b(var40);
               double var42 = ((double)var41 + 0.5 - var9) / var11;
               if (!(var38 * var38 + var42 * var42 >= 1.0)) {
                  MutableBoolean var44 = new MutableBoolean(false);

                  for(int var45 = var30; var45 > var28; --var45) {
                     double var46 = ((double)var45 - 0.5 - var7) / var13;
                     if (!var16.shouldSkip(var0, var38, var46, var42, var45) && (!var15.b(var36, var45, var40) || b(var1))) {
                        var15.a(var36, var45, var40);
                        var34.d(var37, var45, var41);
                        var33 |= this.a(var0, var1, var2, var3, var15, var34, var35, var4, var44);
                     }
                  }
               }
            }
         }

         return var33;
      } else {
         return false;
      }
   }

   protected boolean a(
      CarvingContext var0,
      C var1,
      IChunkAccess var2,
      Function<BlockPosition, Holder<BiomeBase>> var3,
      CarvingMask var4,
      BlockPosition.MutableBlockPosition var5,
      BlockPosition.MutableBlockPosition var6,
      Aquifer var7,
      MutableBoolean var8
   ) {
      IBlockData var9 = var2.a_(var5);
      if (var9.a(Blocks.i) || var9.a(Blocks.fk)) {
         var8.setTrue();
      }

      if (!this.a(var1, var9) && !b(var1)) {
         return false;
      } else {
         IBlockData var10 = this.a(var0, var1, var5, var7);
         if (var10 == null) {
            return false;
         } else {
            var2.a(var5, var10, false);
            if (var7.a() && !var10.r().c()) {
               var2.e(var5);
            }

            if (var8.isTrue()) {
               var6.a(var5, EnumDirection.a);
               if (var2.a_(var6).a(Blocks.j)) {
                  var0.a(var3, var2, var6, !var10.r().c()).ifPresent(var2x -> {
                     var2.a(var6, var2x, false);
                     if (!var2x.r().c()) {
                        var2.e(var6);
                     }
                  });
               }
            }

            return true;
         }
      }
   }

   @Nullable
   private IBlockData a(CarvingContext var0, C var1, BlockPosition var2, Aquifer var3) {
      if (var2.v() <= var1.g.a(var0)) {
         return g.g();
      } else {
         IBlockData var4 = var3.a(new DensityFunction.e(var2.u(), var2.v(), var2.w()), 0.0);
         if (var4 == null) {
            return b(var1) ? var1.h.e() : null;
         } else {
            return b(var1) ? b(var1, var4) : var4;
         }
      }
   }

   private static IBlockData b(WorldGenCarverConfiguration var0, IBlockData var1) {
      if (var1.a(Blocks.a)) {
         return var0.h.b();
      } else if (var1.a(Blocks.G)) {
         IBlockData var2 = var0.h.c();
         return var2.b(BlockProperties.C) ? var2.a(BlockProperties.C, Boolean.valueOf(true)) : var2;
      } else {
         return var1.a(Blocks.H) ? var0.h.d() : var1;
      }
   }

   public abstract boolean a(
      CarvingContext var1,
      C var2,
      IChunkAccess var3,
      Function<BlockPosition, Holder<BiomeBase>> var4,
      RandomSource var5,
      Aquifer var6,
      ChunkCoordIntPair var7,
      CarvingMask var8
   );

   public abstract boolean a(C var1, RandomSource var2);

   protected boolean a(C var0, IBlockData var1) {
      return var1.a(var0.i);
   }

   protected static boolean a(ChunkCoordIntPair var0, double var1, double var3, int var5, int var6, float var7) {
      double var8 = (double)var0.b();
      double var10 = (double)var0.c();
      double var12 = var1 - var8;
      double var14 = var3 - var10;
      double var16 = (double)(var6 - var5);
      double var18 = (double)(var7 + 2.0F + 16.0F);
      return var12 * var12 + var14 * var14 - var16 * var16 <= var18 * var18;
   }

   private static boolean b(WorldGenCarverConfiguration var0) {
      return var0.h.a();
   }

   public interface a {
      boolean shouldSkip(CarvingContext var1, double var2, double var4, double var6, int var8);
   }
}
