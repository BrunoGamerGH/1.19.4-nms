package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;

public class WorldGenFoilagePlacerFancy extends WorldGenFoilagePlacerBlob {
   public static final Codec<WorldGenFoilagePlacerFancy> c = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, WorldGenFoilagePlacerFancy::new));

   public WorldGenFoilagePlacerFancy(IntProvider var0, IntProvider var1, int var2) {
      super(var0, var1, var2);
   }

   @Override
   protected WorldGenFoilagePlacers<?> a() {
      return WorldGenFoilagePlacers.f;
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
      for(int var9 = var8; var9 >= var8 - var6; --var9) {
         int var10 = var7 + (var9 != var8 && var9 != var8 - var6 ? 1 : 0);
         this.a(var0, var1, var2, var3, var5.a(), var10, var9, var5.c());
      }
   }

   @Override
   protected boolean a(RandomSource var0, int var1, int var2, int var3, int var4, boolean var5) {
      return MathHelper.k((float)var1 + 0.5F) + MathHelper.k((float)var3 + 0.5F) > (float)(var4 * var4);
   }
}
