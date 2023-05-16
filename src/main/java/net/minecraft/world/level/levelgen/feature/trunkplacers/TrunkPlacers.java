package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public class TrunkPlacers<P extends TrunkPlacer> {
   public static final TrunkPlacers<TrunkPlacerStraight> a = a("straight_trunk_placer", TrunkPlacerStraight.a);
   public static final TrunkPlacers<TrunkPlacerForking> b = a("forking_trunk_placer", TrunkPlacerForking.a);
   public static final TrunkPlacers<TrunkPlacerGiant> c = a("giant_trunk_placer", TrunkPlacerGiant.a);
   public static final TrunkPlacers<TrunkPlacerMegaJungle> d = a("mega_jungle_trunk_placer", TrunkPlacerMegaJungle.b);
   public static final TrunkPlacers<TrunkPlacerDarkOak> e = a("dark_oak_trunk_placer", TrunkPlacerDarkOak.a);
   public static final TrunkPlacers<TrunkPlacerFancy> f = a("fancy_trunk_placer", TrunkPlacerFancy.a);
   public static final TrunkPlacers<BendingTrunkPlacer> g = a("bending_trunk_placer", BendingTrunkPlacer.a);
   public static final TrunkPlacers<UpwardsBranchingTrunkPlacer> h = a("upwards_branching_trunk_placer", UpwardsBranchingTrunkPlacer.a);
   public static final TrunkPlacers<CherryTrunkPlacer> i = a("cherry_trunk_placer", CherryTrunkPlacer.a);
   private final Codec<P> j;

   private static <P extends TrunkPlacer> TrunkPlacers<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.X, var0, new TrunkPlacers<>(var1));
   }

   private TrunkPlacers(Codec<P> var0) {
      this.j = var0;
   }

   public Codec<P> a() {
      return this.j;
   }
}
