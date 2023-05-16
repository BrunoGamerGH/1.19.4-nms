package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;

public class WorldGenFoilagePlacerPine extends WorldGenFoilagePlacer {
   public static final Codec<WorldGenFoilagePlacerPine> a = RecordCodecBuilder.create(
      var0 -> b(var0).and(IntProvider.b(0, 24).fieldOf("height").forGetter(var0x -> var0x.b)).apply(var0, WorldGenFoilagePlacerPine::new)
   );
   private final IntProvider b;

   public WorldGenFoilagePlacerPine(IntProvider var0, IntProvider var1, IntProvider var2) {
      super(var0, var1);
      this.b = var2;
   }

   @Override
   protected WorldGenFoilagePlacers<?> a() {
      return WorldGenFoilagePlacers.c;
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
      int var9 = 0;

      for(int var10 = var8; var10 >= var8 - var6; --var10) {
         this.a(var0, var1, var2, var3, var5.a(), var9, var10, var5.c());
         if (var9 >= 1 && var10 == var8 - var6 + 1) {
            --var9;
         } else if (var9 < var7 + var5.b()) {
            ++var9;
         }
      }
   }

   @Override
   public int a(RandomSource var0, int var1) {
      return super.a(var0, var1) + var0.a(Math.max(var1 + 1, 1));
   }

   @Override
   public int a(RandomSource var0, int var1, WorldGenFeatureTreeConfiguration var2) {
      return this.b.a(var0);
   }

   @Override
   protected boolean a(RandomSource var0, int var1, int var2, int var3, int var4, boolean var5) {
      return var1 == var4 && var3 == var4 && var4 > 0;
   }
}
