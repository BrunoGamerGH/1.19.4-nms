package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public class WorldGenFoilagePlacers<P extends WorldGenFoilagePlacer> {
   public static final WorldGenFoilagePlacers<WorldGenFoilagePlacerBlob> a = a("blob_foliage_placer", WorldGenFoilagePlacerBlob.a);
   public static final WorldGenFoilagePlacers<WorldGenFoilagePlacerSpruce> b = a("spruce_foliage_placer", WorldGenFoilagePlacerSpruce.a);
   public static final WorldGenFoilagePlacers<WorldGenFoilagePlacerPine> c = a("pine_foliage_placer", WorldGenFoilagePlacerPine.a);
   public static final WorldGenFoilagePlacers<WorldGenFoilagePlacerAcacia> d = a("acacia_foliage_placer", WorldGenFoilagePlacerAcacia.a);
   public static final WorldGenFoilagePlacers<WorldGenFoilagePlacerBush> e = a("bush_foliage_placer", WorldGenFoilagePlacerBush.c);
   public static final WorldGenFoilagePlacers<WorldGenFoilagePlacerFancy> f = a("fancy_foliage_placer", WorldGenFoilagePlacerFancy.c);
   public static final WorldGenFoilagePlacers<WorldGenFoilagePlacerJungle> g = a("jungle_foliage_placer", WorldGenFoilagePlacerJungle.a);
   public static final WorldGenFoilagePlacers<WorldGenFoilagePlacerMegaPine> h = a("mega_pine_foliage_placer", WorldGenFoilagePlacerMegaPine.a);
   public static final WorldGenFoilagePlacers<WorldGenFoilagePlacerDarkOak> i = a("dark_oak_foliage_placer", WorldGenFoilagePlacerDarkOak.a);
   public static final WorldGenFoilagePlacers<RandomSpreadFoliagePlacer> j = a("random_spread_foliage_placer", RandomSpreadFoliagePlacer.a);
   public static final WorldGenFoilagePlacers<CherryFoliagePlacer> k = a("cherry_foliage_placer", CherryFoliagePlacer.a);
   private final Codec<P> l;

   private static <P extends WorldGenFoilagePlacer> WorldGenFoilagePlacers<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.W, var0, new WorldGenFoilagePlacers<>(var1));
   }

   private WorldGenFoilagePlacers(Codec<P> var0) {
      this.l = var0;
   }

   public Codec<P> a() {
      return this.l;
   }
}
