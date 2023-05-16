package net.minecraft.world.level.levelgen.blending;

import com.google.common.primitives.Doubles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.EnumDirection8;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPosition;
import net.minecraft.server.level.RegionLimitedWorldAccess;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.HeightMap;

public class BlendingData {
   private static final double f = 0.1;
   protected static final int a = 4;
   protected static final int b = 8;
   protected static final int c = 2;
   private static final double g = 1.0;
   private static final double h = -1.0;
   private static final int i = 2;
   private static final int j = QuartPos.a(16);
   private static final int k = j - 1;
   private static final int l = j;
   private static final int m = 2 * k + 1;
   private static final int n = 2 * l + 1;
   private static final int o = m + n;
   private final LevelHeightAccessor p;
   private static final List<Block> q = List.of(
      Blocks.l, Blocks.L, Blocks.i, Blocks.b, Blocks.k, Blocks.I, Blocks.K, Blocks.fk, Blocks.dO, Blocks.iz, Blocks.j
   );
   protected static final double d = Double.MAX_VALUE;
   private boolean r;
   private final double[] s;
   private final List<List<Holder<BiomeBase>>> t;
   private final transient double[][] u;
   private static final Codec<double[]> v = Codec.DOUBLE.listOf().xmap(Doubles::toArray, Doubles::asList);
   public static final Codec<BlendingData> e = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.INT.fieldOf("min_section").forGetter(var0x -> var0x.p.ak()),
                  Codec.INT.fieldOf("max_section").forGetter(var0x -> var0x.p.al()),
                  v.optionalFieldOf("heights")
                     .forGetter(var0x -> DoubleStream.of(var0x.s).anyMatch(var0xx -> var0xx != Double.MAX_VALUE) ? Optional.of(var0x.s) : Optional.empty())
               )
               .apply(var0, BlendingData::new)
      )
      .comapFlatMap(BlendingData::a, Function.identity());

   private static DataResult<BlendingData> a(BlendingData var0) {
      return var0.s.length != o ? DataResult.error(() -> "heights has to be of length " + o) : DataResult.success(var0);
   }

   private BlendingData(int var0, int var1, Optional<double[]> var2) {
      this.s = var2.orElse(SystemUtils.a(new double[o], var0x -> Arrays.fill(var0x, Double.MAX_VALUE)));
      this.u = new double[o][];
      ObjectArrayList<List<Holder<BiomeBase>>> var3 = new ObjectArrayList(o);
      var3.size(o);
      this.t = var3;
      int var4 = SectionPosition.c(var0);
      int var5 = SectionPosition.c(var1) - var4;
      this.p = LevelHeightAccessor.e(var4, var5);
   }

   @Nullable
   public static BlendingData a(RegionLimitedWorldAccess var0, int var1, int var2) {
      IChunkAccess var3 = var0.a(var1, var2);
      BlendingData var4 = var3.t();
      if (var4 == null) {
         return null;
      } else {
         var4.a(var3, a(var0, var1, var2, false));
         return var4;
      }
   }

   public static Set<EnumDirection8> a(GeneratorAccessSeed var0, int var1, int var2, boolean var3) {
      Set<EnumDirection8> var4 = EnumSet.noneOf(EnumDirection8.class);

      for(EnumDirection8 var8 : EnumDirection8.values()) {
         int var9 = var1 + var8.b();
         int var10 = var2 + var8.c();
         if (var0.a(var9, var10).s() == var3) {
            var4.add(var8);
         }
      }

      return var4;
   }

   private void a(IChunkAccess var0, Set<EnumDirection8> var1) {
      if (!this.r) {
         if (var1.contains(EnumDirection8.a) || var1.contains(EnumDirection8.g) || var1.contains(EnumDirection8.h)) {
            this.a(a(0, 0), var0, 0, 0);
         }

         if (var1.contains(EnumDirection8.a)) {
            for(int var2 = 1; var2 < j; ++var2) {
               this.a(a(var2, 0), var0, 4 * var2, 0);
            }
         }

         if (var1.contains(EnumDirection8.g)) {
            for(int var2 = 1; var2 < j; ++var2) {
               this.a(a(0, var2), var0, 0, 4 * var2);
            }
         }

         if (var1.contains(EnumDirection8.c)) {
            for(int var2 = 1; var2 < j; ++var2) {
               this.a(b(l, var2), var0, 15, 4 * var2);
            }
         }

         if (var1.contains(EnumDirection8.e)) {
            for(int var2 = 0; var2 < j; ++var2) {
               this.a(b(var2, l), var0, 4 * var2, 15);
            }
         }

         if (var1.contains(EnumDirection8.c) && var1.contains(EnumDirection8.b)) {
            this.a(b(l, 0), var0, 15, 0);
         }

         if (var1.contains(EnumDirection8.c) && var1.contains(EnumDirection8.e) && var1.contains(EnumDirection8.d)) {
            this.a(b(l, l), var0, 15, 15);
         }

         this.r = true;
      }
   }

   private void a(int var0, IChunkAccess var1, int var2, int var3) {
      if (this.s[var0] == Double.MAX_VALUE) {
         this.s[var0] = (double)this.a(var1, var2, var3);
      }

      this.u[var0] = this.a(var1, var2, var3, MathHelper.a(this.s[var0]));
      this.t.set(var0, this.b(var1, var2, var3));
   }

   private int a(IChunkAccess var0, int var1, int var2) {
      int var3;
      if (var0.b(HeightMap.Type.a)) {
         var3 = Math.min(var0.a(HeightMap.Type.a, var1, var2) + 1, this.p.ai());
      } else {
         var3 = this.p.ai();
      }

      int var4 = this.p.v_();
      BlockPosition.MutableBlockPosition var5 = new BlockPosition.MutableBlockPosition(var1, var3, var2);

      while(var5.v() > var4) {
         var5.c(EnumDirection.a);
         if (q.contains(var0.a_(var5).b())) {
            return var5.v();
         }
      }

      return var4;
   }

   private static double a(IChunkAccess var0, BlockPosition.MutableBlockPosition var1) {
      return a(var0, (BlockPosition)var1.c(EnumDirection.a)) ? 1.0 : -1.0;
   }

   private static double b(IChunkAccess var0, BlockPosition.MutableBlockPosition var1) {
      double var2 = 0.0;

      for(int var4 = 0; var4 < 7; ++var4) {
         var2 += a(var0, var1);
      }

      return var2;
   }

   private double[] a(IChunkAccess var0, int var1, int var2, int var3) {
      double[] var4 = new double[this.b()];
      Arrays.fill(var4, -1.0);
      BlockPosition.MutableBlockPosition var5 = new BlockPosition.MutableBlockPosition(var1, this.p.ai(), var2);
      double var6 = b(var0, var5);

      for(int var8 = var4.length - 2; var8 >= 0; --var8) {
         double var9 = a(var0, var5);
         double var11 = b(var0, var5);
         var4[var8] = (var6 + var9 + var11) / 15.0;
         var6 = var11;
      }

      int var8 = this.a(MathHelper.a(var3, 8));
      if (var8 >= 0 && var8 < var4.length - 1) {
         double var9 = ((double)var3 + 0.5) % 8.0 / 8.0;
         double var11 = (1.0 - var9) / var9;
         double var13 = Math.max(var11, 1.0) * 0.25;
         var4[var8 + 1] = -var11 / var13;
         var4[var8] = 1.0 / var13;
      }

      return var4;
   }

   private List<Holder<BiomeBase>> b(IChunkAccess var0, int var1, int var2) {
      ObjectArrayList<Holder<BiomeBase>> var3 = new ObjectArrayList(this.c());
      var3.size(this.c());

      for(int var4 = 0; var4 < var3.size(); ++var4) {
         int var5 = var4 + QuartPos.a(this.p.v_());
         var3.set(var4, var0.getNoiseBiome(QuartPos.a(var1), var5, QuartPos.a(var2)));
      }

      return var3;
   }

   private static boolean a(IChunkAccess var0, BlockPosition var1) {
      IBlockData var2 = var0.a_(var1);
      if (var2.h()) {
         return false;
      } else if (var2.a(TagsBlock.N)) {
         return false;
      } else if (var2.a(TagsBlock.s)) {
         return false;
      } else if (var2.a(Blocks.eT) || var2.a(Blocks.eU)) {
         return false;
      } else {
         return !var2.k(var0, var1).b();
      }
   }

   protected double a(int var0, int var1, int var2) {
      if (var0 == l || var2 == l) {
         return this.s[b(var0, var2)];
      } else {
         return var0 != 0 && var2 != 0 ? Double.MAX_VALUE : this.s[a(var0, var2)];
      }
   }

   private double a(@Nullable double[] var0, int var1) {
      if (var0 == null) {
         return Double.MAX_VALUE;
      } else {
         int var2 = this.a(var1);
         return var2 >= 0 && var2 < var0.length ? var0[var2] * 0.1 : Double.MAX_VALUE;
      }
   }

   protected double b(int var0, int var1, int var2) {
      if (var1 == this.e()) {
         return 0.1;
      } else if (var0 == l || var2 == l) {
         return this.a(this.u[b(var0, var2)], var1);
      } else {
         return var0 != 0 && var2 != 0 ? Double.MAX_VALUE : this.a(this.u[a(var0, var2)], var1);
      }
   }

   protected void a(int var0, int var1, int var2, BlendingData.a var3) {
      if (var1 >= QuartPos.a(this.p.v_()) && var1 < QuartPos.a(this.p.ai())) {
         int var4 = var1 - QuartPos.a(this.p.v_());

         for(int var5 = 0; var5 < this.t.size(); ++var5) {
            if (this.t.get(var5) != null) {
               Holder<BiomeBase> var6 = this.t.get(var5).get(var4);
               if (var6 != null) {
                  var3.consume(var0 + b(var5), var2 + c(var5), var6);
               }
            }
         }
      }
   }

   protected void a(int var0, int var1, BlendingData.c var2) {
      for(int var3 = 0; var3 < this.s.length; ++var3) {
         double var4 = this.s[var3];
         if (var4 != Double.MAX_VALUE) {
            var2.consume(var0 + b(var3), var1 + c(var3), var4);
         }
      }
   }

   protected void a(int var0, int var1, int var2, int var3, BlendingData.b var4) {
      int var5 = this.d();
      int var6 = Math.max(0, var2 - var5);
      int var7 = Math.min(this.b(), var3 - var5);

      for(int var8 = 0; var8 < this.u.length; ++var8) {
         double[] var9 = this.u[var8];
         if (var9 != null) {
            int var10 = var0 + b(var8);
            int var11 = var1 + c(var8);

            for(int var12 = var6; var12 < var7; ++var12) {
               var4.consume(var10, var12 + var5, var11, var9[var12] * 0.1);
            }
         }
      }
   }

   private int b() {
      return this.p.aj() * 2;
   }

   private int c() {
      return QuartPos.d(this.p.aj());
   }

   private int d() {
      return this.e() + 1;
   }

   private int e() {
      return this.p.ak() * 2;
   }

   private int a(int var0) {
      return var0 - this.d();
   }

   private static int a(int var0, int var1) {
      return k - var0 + var1;
   }

   private static int b(int var0, int var1) {
      return m + var0 + l - var1;
   }

   private static int b(int var0) {
      if (var0 < m) {
         return d(k - var0);
      } else {
         int var1 = var0 - m;
         return l - d(l - var1);
      }
   }

   private static int c(int var0) {
      if (var0 < m) {
         return d(var0 - k);
      } else {
         int var1 = var0 - m;
         return l - d(var1 - l);
      }
   }

   private static int d(int var0) {
      return var0 & ~(var0 >> 31);
   }

   public LevelHeightAccessor a() {
      return this.p;
   }

   protected interface a {
      void consume(int var1, int var2, Holder<BiomeBase> var3);
   }

   protected interface b {
      void consume(int var1, int var2, int var3, double var4);
   }

   protected interface c {
      void consume(int var1, int var2, double var3);
   }
}
