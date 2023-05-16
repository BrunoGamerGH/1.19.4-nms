package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;

public class WorldGenFoilagePlacerMegaPine extends WorldGenFoilagePlacer {
   public static final Codec<WorldGenFoilagePlacerMegaPine> a = RecordCodecBuilder.create(
      var0 -> b(var0).and(IntProvider.b(0, 24).fieldOf("crown_height").forGetter(var0x -> var0x.b)).apply(var0, WorldGenFoilagePlacerMegaPine::new)
   );
   private final IntProvider b;

   public WorldGenFoilagePlacerMegaPine(IntProvider var0, IntProvider var1, IntProvider var2) {
      super(var0, var1);
      this.b = var2;
   }

   @Override
   protected WorldGenFoilagePlacers<?> a() {
      return WorldGenFoilagePlacers.h;
   }

   @Override
   protected void a(
      VirtualLevelReadable var0,
      WorldGenFoilagePlacer.b var1,
      RandomSource var2,
      WorldGenFeatureTreeConfiguration var3,
      int var4,
      WorldGenFoilagePlacer.a var5,
      int var6,
      int var7,
      int var8
   ) {
      BlockPosition var9 = var5.a();
      int var10 = 0;

      for(int var11 = var9.v() - var6 + var8; var11 <= var9.v() + var8; ++var11) {
         int var12 = var9.v() - var11;
         int var13 = var7 + var5.b() + MathHelper.d((float)var12 / (float)var6 * 3.5F);
         int var14;
         if (var12 > 0 && var13 == var10 && (var11 & 1) == 0) {
            var14 = var13 + 1;
         } else {
            var14 = var13;
         }

         this.a(var0, var1, var2, var3, new BlockPosition(var9.u(), var11, var9.w()), var14, 0, var5.c());
         var10 = var13;
      }
   }

   @Override
   public int a(RandomSource var0, int var1, WorldGenFeatureTreeConfiguration var2) {
      return this.b.a(var0);
   }

   @Override
   protected boolean a(RandomSource var0, int var1, int var2, int var3, int var4, boolean var5) {
      if (var1 + var3 >= 7) {
         return true;
      } else {
         return var1 * var1 + var3 * var3 > var4 * var4;
      }
   }
}
