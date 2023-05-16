package net.minecraft.world.level.levelgen.feature;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderCrystal;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.BlockIronBars;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEndSpikeConfiguration;
import net.minecraft.world.phys.AxisAlignedBB;

public class WorldGenEnder extends WorldGenerator<WorldGenFeatureEndSpikeConfiguration> {
   public static final int a = 10;
   private static final int b = 42;
   private static final LoadingCache<Long, List<WorldGenEnder.Spike>> c = CacheBuilder.newBuilder()
      .expireAfterWrite(5L, TimeUnit.MINUTES)
      .build(new WorldGenEnder.b());

   public WorldGenEnder(Codec<WorldGenFeatureEndSpikeConfiguration> var0) {
      super(var0);
   }

   public static List<WorldGenEnder.Spike> a(GeneratorAccessSeed var0) {
      RandomSource var1 = RandomSource.a(var0.A());
      long var2 = var1.g() & 65535L;
      return (List<WorldGenEnder.Spike>)c.getUnchecked(var2);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEndSpikeConfiguration> var0) {
      WorldGenFeatureEndSpikeConfiguration var1 = var0.f();
      GeneratorAccessSeed var2 = var0.b();
      RandomSource var3 = var0.d();
      BlockPosition var4 = var0.e();
      List<WorldGenEnder.Spike> var5 = var1.b();
      if (var5.isEmpty()) {
         var5 = a(var2);
      }

      for(WorldGenEnder.Spike var7 : var5) {
         if (var7.a(var4)) {
            this.a(var2, var3, var1, var7);
         }
      }

      return true;
   }

   private void a(WorldAccess var0, RandomSource var1, WorldGenFeatureEndSpikeConfiguration var2, WorldGenEnder.Spike var3) {
      int var4 = var3.c();

      for(BlockPosition var6 : BlockPosition.a(
         new BlockPosition(var3.a() - var4, var0.v_(), var3.b() - var4), new BlockPosition(var3.a() + var4, var3.d() + 10, var3.b() + var4)
      )) {
         if (var6.d((double)var3.a(), (double)var6.v(), (double)var3.b()) <= (double)(var4 * var4 + 1) && var6.v() < var3.d()) {
            this.a(var0, var6, Blocks.cn.o());
         } else if (var6.v() > 65) {
            this.a(var0, var6, Blocks.a.o());
         }
      }

      if (var3.e()) {
         int var5 = -2;
         int var6 = 2;
         int var7 = 3;
         BlockPosition.MutableBlockPosition var8 = new BlockPosition.MutableBlockPosition();

         for(int var9 = -2; var9 <= 2; ++var9) {
            for(int var10 = -2; var10 <= 2; ++var10) {
               for(int var11 = 0; var11 <= 3; ++var11) {
                  boolean var12 = MathHelper.a(var9) == 2;
                  boolean var13 = MathHelper.a(var10) == 2;
                  boolean var14 = var11 == 3;
                  if (var12 || var13 || var14) {
                     boolean var15 = var9 == -2 || var9 == 2 || var14;
                     boolean var16 = var10 == -2 || var10 == 2 || var14;
                     IBlockData var17 = Blocks.eW
                        .o()
                        .a(BlockIronBars.a, Boolean.valueOf(var15 && var10 != -2))
                        .a(BlockIronBars.c, Boolean.valueOf(var15 && var10 != 2))
                        .a(BlockIronBars.d, Boolean.valueOf(var16 && var9 != -2))
                        .a(BlockIronBars.b, Boolean.valueOf(var16 && var9 != 2));
                     this.a(var0, var8.d(var3.a() + var9, var3.d() + var11, var3.b() + var10), var17);
                  }
               }
            }
         }
      }

      EntityEnderCrystal var5 = EntityTypes.B.a((World)var0.C());
      if (var5 != null) {
         var5.a(var2.c());
         var5.m(var2.a());
         var5.b((double)var3.a() + 0.5, (double)(var3.d() + 1), (double)var3.b() + 0.5, var1.i() * 360.0F, 0.0F);
         var0.b(var5);
         this.a(var0, new BlockPosition(var3.a(), var3.d(), var3.b()), Blocks.F.o());
      }
   }

   public static class Spike {
      public static final Codec<WorldGenEnder.Spike> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.INT.fieldOf("centerX").orElse(0).forGetter(var0x -> var0x.b),
                  Codec.INT.fieldOf("centerZ").orElse(0).forGetter(var0x -> var0x.c),
                  Codec.INT.fieldOf("radius").orElse(0).forGetter(var0x -> var0x.d),
                  Codec.INT.fieldOf("height").orElse(0).forGetter(var0x -> var0x.e),
                  Codec.BOOL.fieldOf("guarded").orElse(false).forGetter(var0x -> var0x.f)
               )
               .apply(var0, WorldGenEnder.Spike::new)
      );
      private final int b;
      private final int c;
      private final int d;
      private final int e;
      private final boolean f;
      private final AxisAlignedBB g;

      public Spike(int var0, int var1, int var2, int var3, boolean var4) {
         this.b = var0;
         this.c = var1;
         this.d = var2;
         this.e = var3;
         this.f = var4;
         this.g = new AxisAlignedBB(
            (double)(var0 - var2), (double)DimensionManager.e, (double)(var1 - var2), (double)(var0 + var2), (double)DimensionManager.d, (double)(var1 + var2)
         );
      }

      public boolean a(BlockPosition var0) {
         return SectionPosition.a(var0.u()) == SectionPosition.a(this.b) && SectionPosition.a(var0.w()) == SectionPosition.a(this.c);
      }

      public int a() {
         return this.b;
      }

      public int b() {
         return this.c;
      }

      public int c() {
         return this.d;
      }

      public int d() {
         return this.e;
      }

      public boolean e() {
         return this.f;
      }

      public AxisAlignedBB f() {
         return this.g;
      }
   }

   static class b extends CacheLoader<Long, List<WorldGenEnder.Spike>> {
      public List<WorldGenEnder.Spike> a(Long var0) {
         IntArrayList var1 = SystemUtils.a(IntStream.range(0, 10), RandomSource.a(var0));
         List<WorldGenEnder.Spike> var2 = Lists.newArrayList();

         for(int var3 = 0; var3 < 10; ++var3) {
            int var4 = MathHelper.a(42.0 * Math.cos(2.0 * (-Math.PI + (Math.PI / 10) * (double)var3)));
            int var5 = MathHelper.a(42.0 * Math.sin(2.0 * (-Math.PI + (Math.PI / 10) * (double)var3)));
            int var6 = var1.get(var3);
            int var7 = 2 + var6 / 3;
            int var8 = 76 + var6 * 3;
            boolean var9 = var6 == 1 || var6 == 2;
            var2.add(new WorldGenEnder.Spike(var4, var5, var7, var8, var9));
         }

         return var2;
      }
   }
}
