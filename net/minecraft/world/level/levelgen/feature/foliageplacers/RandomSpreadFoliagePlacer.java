package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;

public class RandomSpreadFoliagePlacer extends WorldGenFoilagePlacer {
   public static final Codec<RandomSpreadFoliagePlacer> a = RecordCodecBuilder.create(
      var0 -> b(var0)
            .and(
               var0.group(
                  IntProvider.b(1, 512).fieldOf("foliage_height").forGetter(var0x -> var0x.b),
                  Codec.intRange(0, 256).fieldOf("leaf_placement_attempts").forGetter(var0x -> var0x.c)
               )
            )
            .apply(var0, RandomSpreadFoliagePlacer::new)
   );
   private final IntProvider b;
   private final int c;

   public RandomSpreadFoliagePlacer(IntProvider var0, IntProvider var1, IntProvider var2, int var3) {
      super(var0, var1);
      this.b = var2;
      this.c = var3;
   }

   @Override
   protected WorldGenFoilagePlacers<?> a() {
      return WorldGenFoilagePlacers.j;
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
      BlockPosition.MutableBlockPosition var10 = var9.j();

      for(int var11 = 0; var11 < this.c; ++var11) {
         var10.a(var9, var2.a(var7) - var2.a(var7), var2.a(var6) - var2.a(var6), var2.a(var7) - var2.a(var7));
         a(var0, var1, var2, var3, var10);
      }
   }

   @Override
   public int a(RandomSource var0, int var1, WorldGenFeatureTreeConfiguration var2) {
      return this.b.a(var0);
   }

   @Override
   protected boolean a(RandomSource var0, int var1, int var2, int var3, int var4, boolean var5) {
      return false;
   }
}
