package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockCampfire;
import net.minecraft.world.level.block.BlockIronBars;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorPredicates;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorRotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureTestBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureTestBlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureTestRandomBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureTestTag;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureTestTrue;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestAxisAlignedLinear;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;

public class ProcessorLists {
   private static final ResourceKey<ProcessorList> J = a("empty");
   public static final ResourceKey<ProcessorList> a = a("zombie_plains");
   public static final ResourceKey<ProcessorList> b = a("zombie_savanna");
   public static final ResourceKey<ProcessorList> c = a("zombie_snowy");
   public static final ResourceKey<ProcessorList> d = a("zombie_taiga");
   public static final ResourceKey<ProcessorList> e = a("zombie_desert");
   public static final ResourceKey<ProcessorList> f = a("mossify_10_percent");
   public static final ResourceKey<ProcessorList> g = a("mossify_20_percent");
   public static final ResourceKey<ProcessorList> h = a("mossify_70_percent");
   public static final ResourceKey<ProcessorList> i = a("street_plains");
   public static final ResourceKey<ProcessorList> j = a("street_savanna");
   public static final ResourceKey<ProcessorList> k = a("street_snowy_or_taiga");
   public static final ResourceKey<ProcessorList> l = a("farm_plains");
   public static final ResourceKey<ProcessorList> m = a("farm_savanna");
   public static final ResourceKey<ProcessorList> n = a("farm_snowy");
   public static final ResourceKey<ProcessorList> o = a("farm_taiga");
   public static final ResourceKey<ProcessorList> p = a("farm_desert");
   public static final ResourceKey<ProcessorList> q = a("outpost_rot");
   public static final ResourceKey<ProcessorList> r = a("bottom_rampart");
   public static final ResourceKey<ProcessorList> s = a("treasure_rooms");
   public static final ResourceKey<ProcessorList> t = a("housing");
   public static final ResourceKey<ProcessorList> u = a("side_wall_degradation");
   public static final ResourceKey<ProcessorList> v = a("stable_degradation");
   public static final ResourceKey<ProcessorList> w = a("bastion_generic_degradation");
   public static final ResourceKey<ProcessorList> x = a("rampart_degradation");
   public static final ResourceKey<ProcessorList> y = a("entrance_replacement");
   public static final ResourceKey<ProcessorList> z = a("bridge");
   public static final ResourceKey<ProcessorList> A = a("roof");
   public static final ResourceKey<ProcessorList> B = a("high_wall");
   public static final ResourceKey<ProcessorList> C = a("high_rampart");
   public static final ResourceKey<ProcessorList> D = a("fossil_rot");
   public static final ResourceKey<ProcessorList> E = a("fossil_coal");
   public static final ResourceKey<ProcessorList> F = a("fossil_diamonds");
   public static final ResourceKey<ProcessorList> G = a("ancient_city_start_degradation");
   public static final ResourceKey<ProcessorList> H = a("ancient_city_generic_degradation");
   public static final ResourceKey<ProcessorList> I = a("ancient_city_walls_degradation");

   private static ResourceKey<ProcessorList> a(String var0) {
      return ResourceKey.a(Registries.ay, new MinecraftKey(var0));
   }

   private static void a(BootstapContext<ProcessorList> var0, ResourceKey<ProcessorList> var1, List<DefinedStructureProcessor> var2) {
      var0.a(var1, new ProcessorList(var2));
   }

   public static void a(BootstapContext<ProcessorList> var0) {
      HolderGetter<Block> var1 = var0.a(Registries.e);
      DefinedStructureProcessorPredicates var2 = new DefinedStructureProcessorPredicates(
         new DefinedStructureTestRandomBlock(Blocks.pn, 0.01F), DefinedStructureTestTrue.b, Blocks.py.o()
      );
      DefinedStructureProcessorPredicates var3 = new DefinedStructureProcessorPredicates(
         new DefinedStructureTestRandomBlock(Blocks.py, 0.5F), DefinedStructureTestTrue.b, Blocks.pn.o()
      );
      a(var0, J, ImmutableList.of());
      a(
         var0,
         a,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.m, 0.8F), DefinedStructureTestTrue.b, Blocks.cm.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestTag(TagsBlock.o), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.co), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.cp), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.m, 0.07F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cm, 0.07F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.hi, 0.07F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.T, 0.05F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.n, 0.1F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ct, 0.1F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ak, 0.02F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.eY, 0.5F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates[]{
                     new DefinedStructureProcessorPredicates(
                        new DefinedStructureTestBlockState(Blocks.eY.o().a(BlockIronBars.a, Boolean.valueOf(true)).a(BlockIronBars.c, Boolean.valueOf(true))),
                        DefinedStructureTestTrue.b,
                        Blocks.hK.o().a(BlockIronBars.a, Boolean.valueOf(true)).a(BlockIronBars.c, Boolean.valueOf(true))
                     ),
                     new DefinedStructureProcessorPredicates(
                        new DefinedStructureTestBlockState(Blocks.eY.o().a(BlockIronBars.b, Boolean.valueOf(true)).a(BlockIronBars.d, Boolean.valueOf(true))),
                        DefinedStructureTestTrue.b,
                        Blocks.hK.o().a(BlockIronBars.b, Boolean.valueOf(true)).a(BlockIronBars.d, Boolean.valueOf(true))
                     ),
                     new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.3F), DefinedStructureTestTrue.b, Blocks.gs.o()),
                     new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.2F), DefinedStructureTestTrue.b, Blocks.gt.o()),
                     new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.1F), DefinedStructureTestTrue.b, Blocks.kA.o())
                  }
               )
            )
         )
      );
      a(
         var0,
         b,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestTag(TagsBlock.o), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.co), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.cp), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.r, 0.2F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.hO, 0.2F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.X, 0.05F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ar, 0.05F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.hj, 0.05F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.hm, 0.05F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.hw, 0.05F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.eY, 0.5F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(
                     new DefinedStructureTestBlockState(Blocks.eY.o().a(BlockIronBars.a, Boolean.valueOf(true)).a(BlockIronBars.c, Boolean.valueOf(true))),
                     DefinedStructureTestTrue.b,
                     Blocks.hK.o().a(BlockIronBars.a, Boolean.valueOf(true)).a(BlockIronBars.c, Boolean.valueOf(true))
                  ),
                  new DefinedStructureProcessorPredicates[]{
                     new DefinedStructureProcessorPredicates(
                        new DefinedStructureTestBlockState(Blocks.eY.o().a(BlockIronBars.b, Boolean.valueOf(true)).a(BlockIronBars.d, Boolean.valueOf(true))),
                        DefinedStructureTestTrue.b,
                        Blocks.hK.o().a(BlockIronBars.b, Boolean.valueOf(true)).a(BlockIronBars.d, Boolean.valueOf(true))
                     ),
                     new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.1F), DefinedStructureTestTrue.b, Blocks.fd.o())
                  }
               )
            )
         )
      );
      a(
         var0,
         c,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestTag(TagsBlock.o), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.co), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.cp), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.oa), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.o, 0.2F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.jt, 0.4F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ae, 0.05F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.aw, 0.05F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.eY, 0.5F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(
                     new DefinedStructureTestBlockState(Blocks.eY.o().a(BlockIronBars.a, Boolean.valueOf(true)).a(BlockIronBars.c, Boolean.valueOf(true))),
                     DefinedStructureTestTrue.b,
                     Blocks.hK.o().a(BlockIronBars.a, Boolean.valueOf(true)).a(BlockIronBars.c, Boolean.valueOf(true))
                  ),
                  new DefinedStructureProcessorPredicates(
                     new DefinedStructureTestBlockState(Blocks.eY.o().a(BlockIronBars.b, Boolean.valueOf(true)).a(BlockIronBars.d, Boolean.valueOf(true))),
                     DefinedStructureTestTrue.b,
                     Blocks.hK.o().a(BlockIronBars.b, Boolean.valueOf(true)).a(BlockIronBars.d, Boolean.valueOf(true))
                  ),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.1F), DefinedStructureTestTrue.b, Blocks.gs.o()),
                  new DefinedStructureProcessorPredicates[]{
                     new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.8F), DefinedStructureTestTrue.b, Blocks.gt.o())
                  }
               )
            )
         )
      );
      a(
         var0,
         d,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.m, 0.8F), DefinedStructureTestTrue.b, Blocks.cm.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestTag(TagsBlock.o), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.co), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.cp), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(
                     new DefinedStructureTestBlock(Blocks.oc), DefinedStructureTestTrue.b, Blocks.oc.o().a(BlockCampfire.b, Boolean.valueOf(false))
                  ),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.m, 0.08F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.U, 0.08F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.eY, 0.5F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(
                     new DefinedStructureTestBlockState(Blocks.eY.o().a(BlockIronBars.a, Boolean.valueOf(true)).a(BlockIronBars.c, Boolean.valueOf(true))),
                     DefinedStructureTestTrue.b,
                     Blocks.hK.o().a(BlockIronBars.a, Boolean.valueOf(true)).a(BlockIronBars.c, Boolean.valueOf(true))
                  ),
                  new DefinedStructureProcessorPredicates(
                     new DefinedStructureTestBlockState(Blocks.eY.o().a(BlockIronBars.b, Boolean.valueOf(true)).a(BlockIronBars.d, Boolean.valueOf(true))),
                     DefinedStructureTestTrue.b,
                     Blocks.hK.o().a(BlockIronBars.b, Boolean.valueOf(true)).a(BlockIronBars.d, Boolean.valueOf(true))
                  ),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.3F), DefinedStructureTestTrue.b, Blocks.fc.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.2F), DefinedStructureTestTrue.b, Blocks.gt.o()),
                  new DefinedStructureProcessorPredicates[0]
               )
            )
         )
      );
      a(
         var0,
         e,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestTag(TagsBlock.o), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.co), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.cp), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.jR, 0.08F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.aW, 0.1F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.iz, 0.08F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.nh, 0.08F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.nu, 0.08F), DefinedStructureTestTrue.b, Blocks.br.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.2F), DefinedStructureTestTrue.b, Blocks.kA.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.1F), DefinedStructureTestTrue.b, Blocks.fd.o())
               )
            )
         )
      );
      a(
         var0,
         f,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.m, 0.1F), DefinedStructureTestTrue.b, Blocks.cm.o())
               )
            )
         )
      );
      a(
         var0,
         g,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.m, 0.2F), DefinedStructureTestTrue.b, Blocks.cm.o())
               )
            )
         )
      );
      a(
         var0,
         h,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.m, 0.7F), DefinedStructureTestTrue.b, Blocks.cm.o())
               )
            )
         )
      );
      a(
         var0,
         i,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.kB), new DefinedStructureTestBlock(Blocks.G), Blocks.n.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.kB, 0.1F), DefinedStructureTestTrue.b, Blocks.i.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.i), new DefinedStructureTestBlock(Blocks.G), Blocks.G.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.j), new DefinedStructureTestBlock(Blocks.G), Blocks.G.o())
               )
            )
         )
      );
      a(
         var0,
         j,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.kB), new DefinedStructureTestBlock(Blocks.G), Blocks.r.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.kB, 0.2F), DefinedStructureTestTrue.b, Blocks.i.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.i), new DefinedStructureTestBlock(Blocks.G), Blocks.G.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.j), new DefinedStructureTestBlock(Blocks.G), Blocks.G.o())
               )
            )
         )
      );
      a(
         var0,
         k,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.kB), new DefinedStructureTestBlock(Blocks.G), Blocks.o.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.kB), new DefinedStructureTestBlock(Blocks.dN), Blocks.o.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.kB, 0.2F), DefinedStructureTestTrue.b, Blocks.i.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.i), new DefinedStructureTestBlock(Blocks.G), Blocks.G.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.j), new DefinedStructureTestBlock(Blocks.G), Blocks.G.o())
               )
            )
         )
      );
      a(
         var0,
         l,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.3F), DefinedStructureTestTrue.b, Blocks.gs.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.2F), DefinedStructureTestTrue.b, Blocks.gt.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.1F), DefinedStructureTestTrue.b, Blocks.kA.o())
               )
            )
         )
      );
      a(
         var0,
         m,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.1F), DefinedStructureTestTrue.b, Blocks.fd.o())
               )
            )
         )
      );
      a(
         var0,
         n,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.1F), DefinedStructureTestTrue.b, Blocks.gs.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.8F), DefinedStructureTestTrue.b, Blocks.gt.o())
               )
            )
         )
      );
      a(
         var0,
         o,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.3F), DefinedStructureTestTrue.b, Blocks.fc.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.2F), DefinedStructureTestTrue.b, Blocks.gt.o())
               )
            )
         )
      );
      a(
         var0,
         p,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.2F), DefinedStructureTestTrue.b, Blocks.kA.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cA, 0.1F), DefinedStructureTestTrue.b, Blocks.fd.o())
               )
            )
         )
      );
      a(var0, q, ImmutableList.of(new DefinedStructureProcessorRotation(0.05F)));
      a(
         var0,
         r,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.kG, 0.75F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.pt, 0.15F), DefinedStructureTestTrue.b, Blocks.ps.o()),
                  var3,
                  var2
               )
            )
         )
      );
      a(
         var0,
         s,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ps, 0.35F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.pu, 0.1F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  var3,
                  var2
               )
            )
         )
      );
      a(
         var0,
         t,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ps, 0.3F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.pn, 1.0E-4F), DefinedStructureTestTrue.b, Blocks.a.o()),
                  var3,
                  var2
               )
            )
         )
      );
      a(
         var0,
         u,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.pu, 0.5F), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cg, 0.1F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  var3,
                  var2
               )
            )
         )
      );
      a(
         var0,
         v,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ps, 0.1F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.pn, 1.0E-4F), DefinedStructureTestTrue.b, Blocks.a.o()),
                  var3,
                  var2
               )
            )
         )
      );
      a(
         var0,
         w,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ps, 0.3F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.pn, 1.0E-4F), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cg, 0.3F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  var3,
                  var2
               )
            )
         )
      );
      a(
         var0,
         x,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ps, 0.4F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.pn, 0.01F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ps, 1.0E-4F), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.pn, 1.0E-4F), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cg, 0.3F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  var3,
                  var2
               )
            )
         )
      );
      a(
         var0,
         y,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.pu, 0.5F), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cg, 0.6F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  var3,
                  var2
               )
            )
         )
      );
      a(
         var0,
         z,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ps, 0.3F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.pn, 1.0E-4F), DefinedStructureTestTrue.b, Blocks.a.o())
               )
            )
         )
      );
      a(
         var0,
         A,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ps, 0.3F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ps, 0.15F), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ps, 0.3F), DefinedStructureTestTrue.b, Blocks.pn.o())
               )
            )
         )
      );
      a(
         var0,
         B,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ps, 0.01F), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ps, 0.5F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ps, 0.3F), DefinedStructureTestTrue.b, Blocks.pn.o()),
                  var3
               )
            )
         )
      );
      a(
         var0,
         C,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.cg, 0.3F), DefinedStructureTestTrue.b, Blocks.pt.o()),
                  new DefinedStructureProcessorPredicates(
                     DefinedStructureTestTrue.b,
                     DefinedStructureTestTrue.b,
                     new PosRuleTestAxisAlignedLinear(0.0F, 0.05F, 0, 100, EnumDirection.EnumAxis.b),
                     Blocks.a.o()
                  ),
                  var3
               )
            )
         )
      );
      a(var0, D, ImmutableList.of(new DefinedStructureProcessorRotation(0.9F), new ProtectedBlockProcessor(TagsBlock.bC)));
      a(var0, E, ImmutableList.of(new DefinedStructureProcessorRotation(0.1F), new ProtectedBlockProcessor(TagsBlock.bC)));
      a(
         var0,
         F,
         ImmutableList.of(
            new DefinedStructureProcessorRotation(0.1F),
            new DefinedStructureProcessorRule(
               ImmutableList.of(new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(Blocks.Q), DefinedStructureTestTrue.b, Blocks.cx.o()))
            ),
            new ProtectedBlockProcessor(TagsBlock.bC)
         )
      );
      a(
         var0,
         G,
         ImmutableList.of(
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.rQ, 0.3F), DefinedStructureTestTrue.b, Blocks.rV.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.rM, 0.3F), DefinedStructureTestTrue.b, Blocks.rW.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ob, 0.05F), DefinedStructureTestTrue.b, Blocks.a.o())
               )
            ),
            new ProtectedBlockProcessor(TagsBlock.bC)
         )
      );
      a(
         var0,
         H,
         ImmutableList.of(
            new DefinedStructureProcessorRotation(var1.b(TagsBlock.bI), 0.95F),
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.rQ, 0.3F), DefinedStructureTestTrue.b, Blocks.rV.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.rM, 0.3F), DefinedStructureTestTrue.b, Blocks.rW.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ob, 0.05F), DefinedStructureTestTrue.b, Blocks.a.o())
               )
            ),
            new ProtectedBlockProcessor(TagsBlock.bC)
         )
      );
      a(
         var0,
         I,
         ImmutableList.of(
            new DefinedStructureProcessorRotation(var1.b(TagsBlock.bI), 0.95F),
            new DefinedStructureProcessorRule(
               ImmutableList.of(
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.rQ, 0.3F), DefinedStructureTestTrue.b, Blocks.rV.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.rM, 0.3F), DefinedStructureTestTrue.b, Blocks.rW.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.rO, 0.3F), DefinedStructureTestTrue.b, Blocks.a.o()),
                  new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(Blocks.ob, 0.05F), DefinedStructureTestTrue.b, Blocks.a.o())
               )
            ),
            new ProtectedBlockProcessor(TagsBlock.bC)
         )
      );
   }
}
