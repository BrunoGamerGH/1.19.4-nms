package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;

public class WorldGenFoilagePlacerJungle extends WorldGenFoilagePlacer {
   public static final Codec<WorldGenFoilagePlacerJungle> a = RecordCodecBuilder.create(
      var0 -> b(var0).and(Codec.intRange(0, 16).fieldOf("height").forGetter(var0x -> var0x.b)).apply(var0, WorldGenFoilagePlacerJungle::new)
   );
   protected final int b;

   public WorldGenFoilagePlacerJungle(IntProvider var0, IntProvider var1, int var2) {
      super(var0, var1);
      this.b = var2;
   }

   @Override
   protected WorldGenFoilagePlacers<?> a() {
      return WorldGenFoilagePlacers.g;
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
      int var9 = var5.c() ? var6 : 1 + var2.a(2);

      for(int var10 = var8; var10 >= var8 - var9; --var10) {
         int var11 = var7 + var5.b() + 1 - var10;
         this.a(var0, var1, var2, var3, var5.a(), var11, var10, var5.c());
      }
   }

   @Override
   public int a(RandomSource var0, int var1, WorldGenFeatureTreeConfiguration var2) {
      return this.b;
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
