package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;

public class WorldGenFoilagePlacerBush extends WorldGenFoilagePlacerBlob {
   public static final Codec<WorldGenFoilagePlacerBush> c = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, WorldGenFoilagePlacerBush::new));

   public WorldGenFoilagePlacerBush(IntProvider var0, IntProvider var1, int var2) {
      super(var0, var1, var2);
   }

   @Override
   protected WorldGenFoilagePlacers<?> a() {
      return WorldGenFoilagePlacers.e;
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
         int var10 = var7 + var5.b() - 1 - var9;
         this.a(var0, var1, var2, var3, var5.a(), var10, var9, var5.c());
      }
   }

   @Override
   protected boolean a(RandomSource var0, int var1, int var2, int var3, int var4, boolean var5) {
      return var1 == var4 && var3 == var4 && var0.a(2) == 0;
   }
}
