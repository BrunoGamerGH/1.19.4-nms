package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;

public class DripstoneClusterFeature extends WorldGenerator<DripstoneClusterConfiguration> {
   public DripstoneClusterFeature(Codec<DripstoneClusterConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<DripstoneClusterConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      DripstoneClusterConfiguration var3 = var0.f();
      RandomSource var4 = var0.d();
      if (!DripstoneUtils.a(var1, var2)) {
         return false;
      } else {
         int var5 = var3.c.a(var4);
         float var6 = var3.i.a(var4);
         float var7 = var3.h.a(var4);
         int var8 = var3.d.a(var4);
         int var9 = var3.d.a(var4);

         for(int var10 = -var8; var10 <= var8; ++var10) {
            for(int var11 = -var9; var11 <= var9; ++var11) {
               double var12 = this.a(var8, var9, var10, var11, var3);
               BlockPosition var14 = var2.b(var10, 0, var11);
               this.a(var1, var4, var14, var10, var11, var6, var12, var5, var7, var3);
            }
         }

         return true;
      }
   }

   private void a(
      GeneratorAccessSeed var0,
      RandomSource var1,
      BlockPosition var2,
      int var3,
      int var4,
      float var5,
      double var6,
      int var8,
      float var9,
      DripstoneClusterConfiguration var10
   ) {
      Optional<Column> var11 = Column.a(var0, var2, var10.b, DripstoneUtils::c, DripstoneUtils::d);
      if (var11.isPresent()) {
         OptionalInt var12 = var11.get().b();
         OptionalInt var13 = var11.get().c();
         if (var12.isPresent() || var13.isPresent()) {
            boolean var14 = var1.i() < var5;
            Column var15;
            if (var14 && var13.isPresent() && this.b(var0, var2.h(var13.getAsInt()))) {
               int var16 = var13.getAsInt();
               var15 = var11.get().a(OptionalInt.of(var16 - 1));
               var0.a(var2.h(var16), Blocks.G.o(), 2);
            } else {
               var15 = var11.get();
            }

            OptionalInt var16 = var15.c();
            boolean var18 = var1.j() < var6;
            int var17;
            if (var12.isPresent() && var18 && !this.a((IWorldReader)var0, var2.h(var12.getAsInt()))) {
               int var19 = var10.g.a(var1);
               this.a(var0, var2.h(var12.getAsInt()), var19, EnumDirection.b);
               int var20;
               if (var16.isPresent()) {
                  var20 = Math.min(var8, var12.getAsInt() - var16.getAsInt());
               } else {
                  var20 = var8;
               }

               var17 = this.a(var1, var3, var4, var9, var20, var10);
            } else {
               var17 = 0;
            }

            boolean var20 = var1.j() < var6;
            int var19;
            if (var16.isPresent() && var20 && !this.a((IWorldReader)var0, var2.h(var16.getAsInt()))) {
               int var21 = var10.g.a(var1);
               this.a(var0, var2.h(var16.getAsInt()), var21, EnumDirection.a);
               if (var12.isPresent()) {
                  var19 = Math.max(0, var17 + MathHelper.b(var1, -var10.e, var10.e));
               } else {
                  var19 = this.a(var1, var3, var4, var9, var8, var10);
               }
            } else {
               var19 = 0;
            }

            int var22;
            int var21;
            if (var12.isPresent() && var16.isPresent() && var12.getAsInt() - var17 <= var16.getAsInt() + var19) {
               int var23 = var16.getAsInt();
               int var24 = var12.getAsInt();
               int var25 = Math.max(var24 - var17, var23 + 1);
               int var26 = Math.min(var23 + var19, var24 - 1);
               int var27 = MathHelper.b(var1, var25, var26 + 1);
               int var28 = var27 - 1;
               var21 = var24 - var27;
               var22 = var28 - var23;
            } else {
               var21 = var17;
               var22 = var19;
            }

            boolean var23 = var1.h() && var21 > 0 && var22 > 0 && var15.d().isPresent() && var21 + var22 == var15.d().getAsInt();
            if (var12.isPresent()) {
               DripstoneUtils.a(var0, var2.h(var12.getAsInt() - 1), EnumDirection.a, var21, var23);
            }

            if (var16.isPresent()) {
               DripstoneUtils.a(var0, var2.h(var16.getAsInt() + 1), EnumDirection.b, var22, var23);
            }
         }
      }
   }

   private boolean a(IWorldReader var0, BlockPosition var1) {
      return var0.a_(var1).a(Blocks.H);
   }

   private int a(RandomSource var0, int var1, int var2, float var3, int var4, DripstoneClusterConfiguration var5) {
      if (var0.i() > var3) {
         return 0;
      } else {
         int var6 = Math.abs(var1) + Math.abs(var2);
         float var7 = (float)MathHelper.a((double)var6, 0.0, (double)var5.l, (double)var4 / 2.0, 0.0);
         return (int)a(var0, 0.0F, (float)var4, var7, (float)var5.f);
      }
   }

   private boolean b(GeneratorAccessSeed var0, BlockPosition var1) {
      IBlockData var2 = var0.a_(var1);
      if (!var2.a(Blocks.G) && !var2.a(Blocks.ro) && !var2.a(Blocks.rn)) {
         if (var0.a_(var1.c()).r().a(TagsFluid.a)) {
            return false;
         } else {
            for(EnumDirection var4 : EnumDirection.EnumDirectionLimit.a) {
               if (!this.a((GeneratorAccess)var0, var1.a(var4))) {
                  return false;
               }
            }

            return this.a((GeneratorAccess)var0, var1.d());
         }
      } else {
         return false;
      }
   }

   private boolean a(GeneratorAccess var0, BlockPosition var1) {
      IBlockData var2 = var0.a_(var1);
      return var2.a(TagsBlock.bb) || var2.r().a(TagsFluid.a);
   }

   private void a(GeneratorAccessSeed var0, BlockPosition var1, int var2, EnumDirection var3) {
      BlockPosition.MutableBlockPosition var4 = var1.j();

      for(int var5 = 0; var5 < var2; ++var5) {
         if (!DripstoneUtils.c(var0, var4)) {
            return;
         }

         var4.c(var3);
      }
   }

   private double a(int var0, int var1, int var2, int var3, DripstoneClusterConfiguration var4) {
      int var5 = var0 - Math.abs(var2);
      int var6 = var1 - Math.abs(var3);
      int var7 = Math.min(var5, var6);
      return (double)MathHelper.b((float)var7, 0.0F, (float)var4.k, var4.j, 1.0F);
   }

   private static float a(RandomSource var0, float var1, float var2, float var3, float var4) {
      return ClampedNormalFloat.a(var0, var3, var4, var1, var2);
   }
}
