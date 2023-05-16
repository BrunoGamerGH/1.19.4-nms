package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;

public class WorldGenFoilagePlacerAcacia extends WorldGenFoilagePlacer {
   public static final Codec<WorldGenFoilagePlacerAcacia> a = RecordCodecBuilder.create(var0 -> b(var0).apply(var0, WorldGenFoilagePlacerAcacia::new));

   public WorldGenFoilagePlacerAcacia(IntProvider var0, IntProvider var1) {
      super(var0, var1);
   }

   @Override
   protected WorldGenFoilagePlacers<?> a() {
      return WorldGenFoilagePlacers.d;
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
      boolean var9 = var5.c();
      BlockPosition var10 = var5.a().b(var8);
      this.a(var0, var1, var2, var3, var10, var7 + var5.b(), -1 - var6, var9);
      this.a(var0, var1, var2, var3, var10, var7 - 1, -var6, var9);
      this.a(var0, var1, var2, var3, var10, var7 + var5.b() - 1, 0, var9);
   }

   @Override
   public int a(RandomSource var0, int var1, WorldGenFeatureTreeConfiguration var2) {
      return 0;
   }

   @Override
   protected boolean a(RandomSource var0, int var1, int var2, int var3, int var4, boolean var5) {
      if (var2 == 0) {
         return (var1 > 1 || var3 > 1) && var1 != 0 && var3 != 0;
      } else {
         return var1 == var4 && var3 == var4 && var4 > 0;
      }
   }
}
