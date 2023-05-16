package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.phys.Vec3D;

public class LargeDripstoneFeature extends WorldGenerator<LargeDripstoneConfiguration> {
   public LargeDripstoneFeature(Codec<LargeDripstoneConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<LargeDripstoneConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      LargeDripstoneConfiguration var3 = var0.f();
      RandomSource var4 = var0.d();
      if (!DripstoneUtils.a(var1, var2)) {
         return false;
      } else {
         Optional<Column> var5 = Column.a(var1, var2, var3.b, DripstoneUtils::c, DripstoneUtils::a);
         if (var5.isPresent() && var5.get() instanceof Column.b var6) {
            if (var6.g() < 4) {
               return false;
            } else {
               int var7 = (int)((float)var6.g() * var3.e);
               int var8 = MathHelper.a(var7, var3.c.a(), var3.c.b());
               int var9 = MathHelper.b(var4, var3.c.a(), var8);
               LargeDripstoneFeature.a var10 = a(var2.h(var6.e() - 1), false, var4, var9, var3.f, var3.d);
               LargeDripstoneFeature.a var11 = a(var2.h(var6.f() + 1), true, var4, var9, var3.g, var3.d);
               LargeDripstoneFeature.b var12;
               if (var10.a(var3) && var11.a(var3)) {
                  var12 = new LargeDripstoneFeature.b(var2.v(), var4, var3.h);
               } else {
                  var12 = LargeDripstoneFeature.b.a();
               }

               boolean var13 = var10.a(var1, var12);
               boolean var14 = var11.a(var1, var12);
               if (var13) {
                  var10.a(var1, var4, var12);
               }

               if (var14) {
                  var11.a(var1, var4, var12);
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }

   private static LargeDripstoneFeature.a a(BlockPosition var0, boolean var1, RandomSource var2, int var3, FloatProvider var4, FloatProvider var5) {
      return new LargeDripstoneFeature.a(var0, var1, var3, (double)var4.a(var2), (double)var5.a(var2));
   }

   private void a(GeneratorAccessSeed var0, BlockPosition var1, Column.b var2, LargeDripstoneFeature.b var3) {
      var0.a(var3.a(var1.h(var2.e() - 1)), Blocks.cy.o(), 2);
      var0.a(var3.a(var1.h(var2.f() + 1)), Blocks.cg.o(), 2);

      for(BlockPosition.MutableBlockPosition var4 = var1.h(var2.f() + 2).j(); var4.v() < var2.e() - 1; var4.c(EnumDirection.b)) {
         BlockPosition var5 = var3.a(var4);
         if (DripstoneUtils.a(var0, var5) || var0.a_(var5).a(Blocks.ro)) {
            var0.a(var5, Blocks.gL.o(), 2);
         }
      }
   }

   static final class a {
      private BlockPosition a;
      private final boolean b;
      private int c;
      private final double d;
      private final double e;

      a(BlockPosition var0, boolean var1, int var2, double var3, double var5) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var5;
      }

      private int a() {
         return this.a(0.0F);
      }

      private int b() {
         return this.b ? this.a.v() : this.a.v() - this.a();
      }

      private int c() {
         return !this.b ? this.a.v() : this.a.v() + this.a();
      }

      boolean a(GeneratorAccessSeed var0, LargeDripstoneFeature.b var1) {
         while(this.c > 1) {
            BlockPosition.MutableBlockPosition var2 = this.a.j();
            int var3 = Math.min(10, this.a());

            for(int var4 = 0; var4 < var3; ++var4) {
               if (var0.a_(var2).a(Blocks.H)) {
                  return false;
               }

               if (DripstoneUtils.a(var0, var1.a(var2), this.c)) {
                  this.a = var2;
                  return true;
               }

               var2.c(this.b ? EnumDirection.a : EnumDirection.b);
            }

            this.c /= 2;
         }

         return false;
      }

      private int a(float var0) {
         return (int)DripstoneUtils.a((double)var0, (double)this.c, this.e, this.d);
      }

      void a(GeneratorAccessSeed var0, RandomSource var1, LargeDripstoneFeature.b var2) {
         for(int var3 = -this.c; var3 <= this.c; ++var3) {
            for(int var4 = -this.c; var4 <= this.c; ++var4) {
               float var5 = MathHelper.c((float)(var3 * var3 + var4 * var4));
               if (!(var5 > (float)this.c)) {
                  int var6 = this.a(var5);
                  if (var6 > 0) {
                     if ((double)var1.i() < 0.2) {
                        var6 = (int)((float)var6 * MathHelper.b(var1, 0.8F, 1.0F));
                     }

                     BlockPosition.MutableBlockPosition var7 = this.a.b(var3, 0, var4).j();
                     boolean var8 = false;
                     int var9 = this.b ? var0.a(HeightMap.Type.a, var7.u(), var7.w()) : Integer.MAX_VALUE;

                     for(int var10 = 0; var10 < var6 && var7.v() < var9; ++var10) {
                        BlockPosition var11 = var2.a(var7);
                        if (DripstoneUtils.b(var0, var11)) {
                           var8 = true;
                           Block var12 = Blocks.ro;
                           var0.a(var11, var12.o(), 2);
                        } else if (var8 && var0.a_(var11).a(TagsBlock.bb)) {
                           break;
                        }

                        var7.c(this.b ? EnumDirection.b : EnumDirection.a);
                     }
                  }
               }
            }
         }
      }

      boolean a(LargeDripstoneConfiguration var0) {
         return this.c >= var0.i && this.d >= (double)var0.j;
      }
   }

   static final class b {
      private final int a;
      @Nullable
      private final Vec3D b;

      b(int var0, RandomSource var1, FloatProvider var2) {
         this.a = var0;
         float var3 = var2.a(var1);
         float var4 = MathHelper.b(var1, 0.0F, (float) Math.PI);
         this.b = new Vec3D((double)(MathHelper.b(var4) * var3), 0.0, (double)(MathHelper.a(var4) * var3));
      }

      private b() {
         this.a = 0;
         this.b = null;
      }

      static LargeDripstoneFeature.b a() {
         return new LargeDripstoneFeature.b();
      }

      BlockPosition a(BlockPosition var0) {
         if (this.b == null) {
            return var0;
         } else {
            int var1 = this.a - var0.v();
            Vec3D var2 = this.b.a((double)var1);
            return var0.b(MathHelper.a(var2.c), 0, MathHelper.a(var2.e));
         }
      }
   }
}
