package net.minecraft.data.worldgen.features;

import java.util.List;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureOreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureRuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureTestBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureTestTag;

public class OreFeatures {
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> a = FeatureUtils.a("ore_magma");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> b = FeatureUtils.a("ore_soul_sand");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> c = FeatureUtils.a("ore_nether_gold");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> d = FeatureUtils.a("ore_quartz");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> e = FeatureUtils.a("ore_gravel_nether");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> f = FeatureUtils.a("ore_blackstone");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> g = FeatureUtils.a("ore_dirt");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> h = FeatureUtils.a("ore_gravel");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> i = FeatureUtils.a("ore_granite");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> j = FeatureUtils.a("ore_diorite");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> k = FeatureUtils.a("ore_andesite");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> l = FeatureUtils.a("ore_tuff");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> m = FeatureUtils.a("ore_coal");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> n = FeatureUtils.a("ore_coal_buried");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> o = FeatureUtils.a("ore_iron");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> p = FeatureUtils.a("ore_iron_small");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> q = FeatureUtils.a("ore_gold");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> r = FeatureUtils.a("ore_gold_buried");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> s = FeatureUtils.a("ore_redstone");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> t = FeatureUtils.a("ore_diamond_small");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> u = FeatureUtils.a("ore_diamond_large");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> v = FeatureUtils.a("ore_diamond_buried");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> w = FeatureUtils.a("ore_lapis");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> x = FeatureUtils.a("ore_lapis_buried");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> y = FeatureUtils.a("ore_infested");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> z = FeatureUtils.a("ore_emerald");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> A = FeatureUtils.a("ore_ancient_debris_large");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> B = FeatureUtils.a("ore_ancient_debris_small");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> C = FeatureUtils.a("ore_copper_small");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> D = FeatureUtils.a("ore_copper_large");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> E = FeatureUtils.a("ore_clay");

   public static void a(BootstapContext<WorldGenFeatureConfigured<?, ?>> var0) {
      DefinedStructureRuleTest var1 = new DefinedStructureTestTag(TagsBlock.bb);
      DefinedStructureRuleTest var2 = new DefinedStructureTestTag(TagsBlock.bc);
      DefinedStructureRuleTest var3 = new DefinedStructureTestTag(TagsBlock.bd);
      DefinedStructureRuleTest var4 = new DefinedStructureTestBlock(Blocks.dV);
      DefinedStructureRuleTest var5 = new DefinedStructureTestTag(TagsBlock.be);
      List<WorldGenFeatureOreConfiguration.a> var6 = List.of(
         WorldGenFeatureOreConfiguration.a(var2, Blocks.O.o()), WorldGenFeatureOreConfiguration.a(var3, Blocks.P.o())
      );
      List<WorldGenFeatureOreConfiguration.a> var7 = List.of(
         WorldGenFeatureOreConfiguration.a(var2, Blocks.M.o()), WorldGenFeatureOreConfiguration.a(var3, Blocks.N.o())
      );
      List<WorldGenFeatureOreConfiguration.a> var8 = List.of(
         WorldGenFeatureOreConfiguration.a(var2, Blocks.cw.o()), WorldGenFeatureOreConfiguration.a(var3, Blocks.cx.o())
      );
      List<WorldGenFeatureOreConfiguration.a> var9 = List.of(
         WorldGenFeatureOreConfiguration.a(var2, Blocks.aQ.o()), WorldGenFeatureOreConfiguration.a(var3, Blocks.aR.o())
      );
      List<WorldGenFeatureOreConfiguration.a> var10 = List.of(
         WorldGenFeatureOreConfiguration.a(var2, Blocks.qI.o()), WorldGenFeatureOreConfiguration.a(var3, Blocks.qJ.o())
      );
      List<WorldGenFeatureOreConfiguration.a> var11 = List.of(
         WorldGenFeatureOreConfiguration.a(var2, Blocks.Q.o()), WorldGenFeatureOreConfiguration.a(var3, Blocks.R.o())
      );
      FeatureUtils.a(var0, a, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var4, Blocks.kG.o(), 33));
      FeatureUtils.a(var0, b, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var4, Blocks.dW.o(), 12));
      FeatureUtils.a(var0, c, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var4, Blocks.S.o(), 10));
      FeatureUtils.a(var0, d, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var4, Blocks.ha.o(), 14));
      FeatureUtils.a(var0, e, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var4, Blocks.L.o(), 33));
      FeatureUtils.a(var0, f, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var4, Blocks.pn.o(), 33));
      FeatureUtils.a(var0, g, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var1, Blocks.j.o(), 33));
      FeatureUtils.a(var0, h, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var1, Blocks.L.o(), 33));
      FeatureUtils.a(var0, i, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var1, Blocks.c.o(), 64));
      FeatureUtils.a(var0, j, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var1, Blocks.e.o(), 64));
      FeatureUtils.a(var0, k, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var1, Blocks.g.o(), 64));
      FeatureUtils.a(var0, l, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var1, Blocks.qv.o(), 64));
      FeatureUtils.a(var0, m, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var11, 17));
      FeatureUtils.a(var0, n, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var11, 17, 0.5F));
      FeatureUtils.a(var0, o, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var6, 9));
      FeatureUtils.a(var0, p, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var6, 4));
      FeatureUtils.a(var0, q, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var7, 9));
      FeatureUtils.a(var0, r, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var7, 9, 0.5F));
      FeatureUtils.a(
         var0,
         s,
         WorldGenerator.J,
         new WorldGenFeatureOreConfiguration(
            List.of(WorldGenFeatureOreConfiguration.a(var2, Blocks.dH.o()), WorldGenFeatureOreConfiguration.a(var3, Blocks.dI.o())), 8
         )
      );
      FeatureUtils.a(var0, t, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var8, 4, 0.5F));
      FeatureUtils.a(var0, u, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var8, 12, 0.7F));
      FeatureUtils.a(var0, v, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var8, 8, 1.0F));
      FeatureUtils.a(var0, w, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var9, 7));
      FeatureUtils.a(var0, x, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var9, 7, 1.0F));
      FeatureUtils.a(
         var0,
         y,
         WorldGenerator.J,
         new WorldGenFeatureOreConfiguration(
            List.of(WorldGenFeatureOreConfiguration.a(var2, Blocks.eN.o()), WorldGenFeatureOreConfiguration.a(var3, Blocks.rX.o())), 9
         )
      );
      FeatureUtils.a(
         var0,
         z,
         WorldGenerator.J,
         new WorldGenFeatureOreConfiguration(
            List.of(WorldGenFeatureOreConfiguration.a(var2, Blocks.fD.o()), WorldGenFeatureOreConfiguration.a(var3, Blocks.fE.o())), 3
         )
      );
      FeatureUtils.a(var0, A, WorldGenerator.af, new WorldGenFeatureOreConfiguration(var5, Blocks.pf.o(), 3, 1.0F));
      FeatureUtils.a(var0, B, WorldGenerator.af, new WorldGenFeatureOreConfiguration(var5, Blocks.pf.o(), 2, 1.0F));
      FeatureUtils.a(var0, C, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var10, 10));
      FeatureUtils.a(var0, D, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var10, 20));
      FeatureUtils.a(var0, E, WorldGenerator.J, new WorldGenFeatureOreConfiguration(var1, Blocks.dQ.o(), 33));
   }
}
