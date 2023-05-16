package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.datafixers.Products.P3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;

public class WorldGenFoilagePlacerBlob extends WorldGenFoilagePlacer {
   public static final Codec<WorldGenFoilagePlacerBlob> a = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, WorldGenFoilagePlacerBlob::new));
   protected final int b;

   protected static <P extends WorldGenFoilagePlacerBlob> P3<Mu<P>, IntProvider, IntProvider, Integer> a(Instance<P> var0) {
      return b(var0).and(Codec.intRange(0, 16).fieldOf("height").forGetter(var0x -> var0x.b));
   }

   public WorldGenFoilagePlacerBlob(IntProvider var0, IntProvider var1, int var2) {
      super(var0, var1);
      this.b = var2;
   }

   @Override
   protected WorldGenFoilagePlacers<?> a() {
      return WorldGenFoilagePlacers.a;
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
         int var10 = Math.max(var7 + var5.b() - 1 - var9 / 2, 0);
         this.a(var0, var1, var2, var3, var5.a(), var10, var9, var5.c());
      }
   }

   @Override
   public int a(RandomSource var0, int var1, WorldGenFeatureTreeConfiguration var2) {
      return this.b;
   }

   @Override
   protected boolean a(RandomSource var0, int var1, int var2, int var3, int var4, boolean var5) {
      return var1 == var4 && var3 == var4 && (var0.a(2) == 0 || var2 == 0);
   }
}
