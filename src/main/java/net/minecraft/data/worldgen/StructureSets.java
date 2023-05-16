package net.minecraft.data.worldgen;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

public interface StructureSets {
   static void a(BootstapContext<StructureSet> var0) {
      HolderGetter<Structure> var1 = var0.a(Registries.ax);
      HolderGetter<BiomeBase> var2 = var0.a(Registries.an);
      Holder.c<StructureSet> var3 = var0.a(
         BuiltinStructureSets.a,
         new StructureSet(
            List.of(
               StructureSet.a(var1.b(BuiltinStructures.t)),
               StructureSet.a(var1.b(BuiltinStructures.u)),
               StructureSet.a(var1.b(BuiltinStructures.v)),
               StructureSet.a(var1.b(BuiltinStructures.w)),
               StructureSet.a(var1.b(BuiltinStructures.x))
            ),
            new RandomSpreadStructurePlacement(34, 8, RandomSpreadType.a, 10387312)
         )
      );
      var0.a(BuiltinStructureSets.b, new StructureSet(var1.b(BuiltinStructures.f), new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.a, 14357617)));
      var0.a(BuiltinStructureSets.c, new StructureSet(var1.b(BuiltinStructures.g), new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.a, 14357618)));
      var0.a(BuiltinStructureSets.d, new StructureSet(var1.b(BuiltinStructures.e), new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.a, 14357619)));
      var0.a(BuiltinStructureSets.e, new StructureSet(var1.b(BuiltinStructures.j), new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.a, 14357620)));
      var0.a(
         BuiltinStructureSets.f,
         new StructureSet(
            var1.b(BuiltinStructures.a),
            new RandomSpreadStructurePlacement(
               BaseBlockPosition.g, StructurePlacement.c.b, 0.2F, 165745296, Optional.of(new StructurePlacement.a(var3, 10)), 32, 8, RandomSpreadType.a
            )
         )
      );
      var0.a(BuiltinStructureSets.q, new StructureSet(var1.b(BuiltinStructures.F), new RandomSpreadStructurePlacement(24, 8, RandomSpreadType.a, 20083232)));
      var0.a(BuiltinStructureSets.g, new StructureSet(var1.b(BuiltinStructures.l), new RandomSpreadStructurePlacement(32, 5, RandomSpreadType.b, 10387313)));
      var0.a(BuiltinStructureSets.h, new StructureSet(var1.b(BuiltinStructures.d), new RandomSpreadStructurePlacement(80, 20, RandomSpreadType.b, 10387319)));
      var0.a(
         BuiltinStructureSets.i,
         new StructureSet(
            var1.b(BuiltinStructures.r),
            new RandomSpreadStructurePlacement(new BaseBlockPosition(9, 0, 9), StructurePlacement.c.c, 0.01F, 0, Optional.empty(), 1, 0, RandomSpreadType.a)
         )
      );
      var0.a(
         BuiltinStructureSets.j,
         new StructureSet(
            List.of(StructureSet.a(var1.b(BuiltinStructures.b)), StructureSet.a(var1.b(BuiltinStructures.c))),
            new RandomSpreadStructurePlacement(BaseBlockPosition.g, StructurePlacement.c.d, 0.004F, 0, Optional.empty(), 1, 0, RandomSpreadType.a)
         )
      );
      var0.a(
         BuiltinStructureSets.k,
         new StructureSet(
            List.of(
               StructureSet.a(var1.b(BuiltinStructures.y)),
               StructureSet.a(var1.b(BuiltinStructures.z)),
               StructureSet.a(var1.b(BuiltinStructures.A)),
               StructureSet.a(var1.b(BuiltinStructures.B)),
               StructureSet.a(var1.b(BuiltinStructures.C)),
               StructureSet.a(var1.b(BuiltinStructures.D)),
               StructureSet.a(var1.b(BuiltinStructures.E))
            ),
            new RandomSpreadStructurePlacement(40, 15, RandomSpreadType.a, 34222645)
         )
      );
      var0.a(
         BuiltinStructureSets.l,
         new StructureSet(
            List.of(StructureSet.a(var1.b(BuiltinStructures.h)), StructureSet.a(var1.b(BuiltinStructures.i))),
            new RandomSpreadStructurePlacement(24, 4, RandomSpreadType.a, 165745295)
         )
      );
      var0.a(
         BuiltinStructureSets.m,
         new StructureSet(
            List.of(StructureSet.a(var1.b(BuiltinStructures.m)), StructureSet.a(var1.b(BuiltinStructures.n))),
            new RandomSpreadStructurePlacement(20, 8, RandomSpreadType.a, 14357621)
         )
      );
      var0.a(
         BuiltinStructureSets.n,
         new StructureSet(
            List.of(StructureSet.a(var1.b(BuiltinStructures.o), 2), StructureSet.a(var1.b(BuiltinStructures.s), 3)),
            new RandomSpreadStructurePlacement(27, 4, RandomSpreadType.a, 30084232)
         )
      );
      var0.a(BuiltinStructureSets.o, new StructureSet(var1.b(BuiltinStructures.p), new RandomSpreadStructurePlacement(2, 1, RandomSpreadType.a, 14357921)));
      var0.a(BuiltinStructureSets.p, new StructureSet(var1.b(BuiltinStructures.q), new RandomSpreadStructurePlacement(20, 11, RandomSpreadType.b, 10387313)));
      var0.a(BuiltinStructureSets.r, new StructureSet(var1.b(BuiltinStructures.k), new ConcentricRingsStructurePlacement(32, 3, 128, var2.b(BiomeTags.o))));
   }
}
