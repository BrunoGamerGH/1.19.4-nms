package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public class WorldGenFeatureTrees<P extends WorldGenFeatureTree> {
   public static final WorldGenFeatureTrees<WorldGenFeatureTreeVineTrunk> a = a("trunk_vine", WorldGenFeatureTreeVineTrunk.a);
   public static final WorldGenFeatureTrees<WorldGenFeatureTreeVineLeaves> b = a("leave_vine", WorldGenFeatureTreeVineLeaves.a);
   public static final WorldGenFeatureTrees<WorldGenFeatureTreeCocoa> c = a("cocoa", WorldGenFeatureTreeCocoa.a);
   public static final WorldGenFeatureTrees<WorldGenFeatureTreeBeehive> d = a("beehive", WorldGenFeatureTreeBeehive.a);
   public static final WorldGenFeatureTrees<WorldGenFeatureTreeAlterGround> e = a("alter_ground", WorldGenFeatureTreeAlterGround.a);
   public static final WorldGenFeatureTrees<AttachedToLeavesDecorator> f = a("attached_to_leaves", AttachedToLeavesDecorator.a);
   private final Codec<P> g;

   private static <P extends WorldGenFeatureTree> WorldGenFeatureTrees<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.Z, var0, new WorldGenFeatureTrees<>(var1));
   }

   private WorldGenFeatureTrees(Codec<P> var0) {
      this.g = var0;
   }

   public Codec<P> a() {
      return this.g;
   }
}
