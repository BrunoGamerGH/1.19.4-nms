package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;

public class WorldGenFoilagePlacerSpruce extends WorldGenFoilagePlacer {
   public static final Codec<WorldGenFoilagePlacerSpruce> a = RecordCodecBuilder.create(
      var0 -> b(var0).and(IntProvider.b(0, 24).fieldOf("trunk_height").forGetter(var0x -> var0x.b)).apply(var0, WorldGenFoilagePlacerSpruce::new)
   );
   private final IntProvider b;

   public WorldGenFoilagePlacerSpruce(IntProvider var0, IntProvider var1, IntProvider var2) {
      super(var0, var1);
      this.b = var2;
   }

   @Override
   protected WorldGenFoilagePlacers<?> a() {
      return WorldGenFoilagePlacers.b;
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
      int var10 = var2.a(2);
      int var11 = 1;
      int var12 = 0;

      for(int var13 = var8; var13 >= -var6; --var13) {
         this.a(var0, var1, var2, var3, var9, var10, var13, var5.c());
         if (var10 >= var11) {
            var10 = var12;
            var12 = 1;
            var11 = Math.min(var11 + 1, var7 + var5.b());
         } else {
            ++var10;
         }
      }
   }

   @Override
   public int a(RandomSource var0, int var1, WorldGenFeatureTreeConfiguration var2) {
      return Math.max(4, var1 - this.b.a(var0));
   }

   @Override
   protected boolean a(RandomSource var0, int var1, int var2, int var3, int var4, boolean var5) {
      return var1 == var4 && var3 == var4 && var4 > 0;
   }
}
