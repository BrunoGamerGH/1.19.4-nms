package net.minecraft.data.worldgen;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;
import net.minecraft.world.level.levelgen.structure.structures.BuriedTreasureStructure;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidStructure;
import net.minecraft.world.level.levelgen.structure.structures.EndCityStructure;
import net.minecraft.world.level.levelgen.structure.structures.IglooStructure;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.structures.JungleTempleStructure;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilStructure;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentStructure;
import net.minecraft.world.level.levelgen.structure.structures.OceanRuinStructure;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure;
import net.minecraft.world.level.levelgen.structure.structures.ShipwreckStructure;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdStructure;
import net.minecraft.world.level.levelgen.structure.structures.SwampHutStructure;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionStructure;

public class Structures {
   private static Structure.c a(
      HolderSet<BiomeBase> var0, Map<EnumCreatureType, StructureSpawnOverride> var1, WorldGenStage.Decoration var2, TerrainAdjustment var3
   ) {
      return new Structure.c(var0, var1, var2, var3);
   }

   private static Structure.c a(HolderSet<BiomeBase> var0, WorldGenStage.Decoration var1, TerrainAdjustment var2) {
      return a(var0, Map.of(), var1, var2);
   }

   private static Structure.c a(HolderSet<BiomeBase> var0, TerrainAdjustment var1) {
      return a(var0, Map.of(), WorldGenStage.Decoration.e, var1);
   }

   public static void a(BootstapContext<Structure> var0) {
      HolderGetter<BiomeBase> var1 = var0.a(Registries.an);
      HolderGetter<WorldGenFeatureDefinedStructurePoolTemplate> var2 = var0.a(Registries.aA);
      var0.a(
         BuiltinStructures.a,
         new JigsawStructure(
            a(
               var1.b(BiomeTags.y),
               Map.of(
                  EnumCreatureType.a,
                  new StructureSpawnOverride(StructureSpawnOverride.a.b, WeightedRandomList.a(new BiomeSettingsMobs.c(EntityTypes.ay, 1, 1, 1)))
               ),
               WorldGenStage.Decoration.e,
               TerrainAdjustment.c
            ),
            var2.b(WorldGenFeaturePillagerOutpostPieces.a),
            7,
            ConstantHeight.a(VerticalAnchor.a(0)),
            true,
            HeightMap.Type.a
         )
      );
      var0.a(BuiltinStructures.b, new MineshaftStructure(a(var1.b(BiomeTags.t), WorldGenStage.Decoration.d, TerrainAdjustment.a), MineshaftStructure.a.a));
      var0.a(BuiltinStructures.c, new MineshaftStructure(a(var1.b(BiomeTags.u), WorldGenStage.Decoration.d, TerrainAdjustment.a), MineshaftStructure.a.b));
      var0.a(BuiltinStructures.d, new WoodlandMansionStructure(a(var1.b(BiomeTags.O), TerrainAdjustment.a)));
      var0.a(BuiltinStructures.e, new JungleTempleStructure(a(var1.b(BiomeTags.s), TerrainAdjustment.a)));
      var0.a(BuiltinStructures.f, new DesertPyramidStructure(a(var1.b(BiomeTags.q), TerrainAdjustment.a)));
      var0.a(BuiltinStructures.g, new IglooStructure(a(var1.b(BiomeTags.r), TerrainAdjustment.a)));
      var0.a(BuiltinStructures.h, new ShipwreckStructure(a(var1.b(BiomeTags.G), TerrainAdjustment.a), false));
      var0.a(BuiltinStructures.i, new ShipwreckStructure(a(var1.b(BiomeTags.F), TerrainAdjustment.a), true));
      var0.a(
         BuiltinStructures.j,
         new SwampHutStructure(
            a(
               var1.b(BiomeTags.I),
               Map.of(
                  EnumCreatureType.a,
                  new StructureSpawnOverride(StructureSpawnOverride.a.a, WeightedRandomList.a(new BiomeSettingsMobs.c(EntityTypes.bj, 1, 1, 1))),
                  EnumCreatureType.b,
                  new StructureSpawnOverride(StructureSpawnOverride.a.a, WeightedRandomList.a(new BiomeSettingsMobs.c(EntityTypes.m, 1, 1, 1)))
               ),
               WorldGenStage.Decoration.e,
               TerrainAdjustment.a
            )
         )
      );
      var0.a(BuiltinStructures.k, new StrongholdStructure(a(var1.b(BiomeTags.H), TerrainAdjustment.b)));
      var0.a(
         BuiltinStructures.l,
         new OceanMonumentStructure(
            a(
               var1.b(BiomeTags.v),
               Map.of(
                  EnumCreatureType.a,
                  new StructureSpawnOverride(StructureSpawnOverride.a.b, WeightedRandomList.a(new BiomeSettingsMobs.c(EntityTypes.V, 1, 2, 4))),
                  EnumCreatureType.e,
                  new StructureSpawnOverride(StructureSpawnOverride.a.b, BiomeSettingsMobs.a),
                  EnumCreatureType.d,
                  new StructureSpawnOverride(StructureSpawnOverride.a.b, BiomeSettingsMobs.a)
               ),
               WorldGenStage.Decoration.e,
               TerrainAdjustment.a
            )
         )
      );
      var0.a(BuiltinStructures.m, new OceanRuinStructure(a(var1.b(BiomeTags.w), TerrainAdjustment.a), OceanRuinStructure.a.b, 0.3F, 0.9F));
      var0.a(BuiltinStructures.n, new OceanRuinStructure(a(var1.b(BiomeTags.x), TerrainAdjustment.a), OceanRuinStructure.a.a, 0.3F, 0.9F));
      var0.a(
         BuiltinStructures.o,
         new NetherFortressStructure(
            a(
               var1.b(BiomeTags.P),
               Map.of(EnumCreatureType.a, new StructureSpawnOverride(StructureSpawnOverride.a.a, NetherFortressStructure.d)),
               WorldGenStage.Decoration.h,
               TerrainAdjustment.a
            )
         )
      );
      var0.a(
         BuiltinStructures.p,
         new NetherFossilStructure(
            a(var1.b(BiomeTags.Q), WorldGenStage.Decoration.h, TerrainAdjustment.c), UniformHeight.a(VerticalAnchor.a(32), VerticalAnchor.c(2))
         )
      );
      var0.a(BuiltinStructures.q, new EndCityStructure(a(var1.b(BiomeTags.U), TerrainAdjustment.a)));
      var0.a(BuiltinStructures.r, new BuriedTreasureStructure(a(var1.b(BiomeTags.p), WorldGenStage.Decoration.d, TerrainAdjustment.a)));
      var0.a(
         BuiltinStructures.s,
         new JigsawStructure(
            a(var1.b(BiomeTags.R), TerrainAdjustment.a), var2.b(WorldGenFeatureBastionPieces.a), 6, ConstantHeight.a(VerticalAnchor.a(33)), false
         )
      );
      var0.a(
         BuiltinStructures.t,
         new JigsawStructure(
            a(var1.b(BiomeTags.K), TerrainAdjustment.c),
            var2.b(WorldGenFeatureVillagePlain.a),
            6,
            ConstantHeight.a(VerticalAnchor.a(0)),
            true,
            HeightMap.Type.a
         )
      );
      var0.a(
         BuiltinStructures.u,
         new JigsawStructure(
            a(var1.b(BiomeTags.J), TerrainAdjustment.c),
            var2.b(WorldGenFeatureDesertVillage.a),
            6,
            ConstantHeight.a(VerticalAnchor.a(0)),
            true,
            HeightMap.Type.a
         )
      );
      var0.a(
         BuiltinStructures.v,
         new JigsawStructure(
            a(var1.b(BiomeTags.L), TerrainAdjustment.c),
            var2.b(WorldGenFeatureVillageSavanna.a),
            6,
            ConstantHeight.a(VerticalAnchor.a(0)),
            true,
            HeightMap.Type.a
         )
      );
      var0.a(
         BuiltinStructures.w,
         new JigsawStructure(
            a(var1.b(BiomeTags.M), TerrainAdjustment.c),
            var2.b(WorldGenFeatureVillageSnowy.a),
            6,
            ConstantHeight.a(VerticalAnchor.a(0)),
            true,
            HeightMap.Type.a
         )
      );
      var0.a(
         BuiltinStructures.x,
         new JigsawStructure(
            a(var1.b(BiomeTags.N), TerrainAdjustment.c),
            var2.b(WorldGenFeatureVillageTaiga.a),
            6,
            ConstantHeight.a(VerticalAnchor.a(0)),
            true,
            HeightMap.Type.a
         )
      );
      var0.a(
         BuiltinStructures.y,
         new RuinedPortalStructure(
            a(var1.b(BiomeTags.E), TerrainAdjustment.a),
            List.of(
               new RuinedPortalStructure.a(RuinedPortalPiece.b.e, 1.0F, 0.2F, false, false, true, false, 0.5F),
               new RuinedPortalStructure.a(RuinedPortalPiece.b.a, 0.5F, 0.2F, false, false, true, false, 0.5F)
            )
         )
      );
      var0.a(
         BuiltinStructures.z,
         new RuinedPortalStructure(
            a(var1.b(BiomeTags.z), TerrainAdjustment.a), new RuinedPortalStructure.a(RuinedPortalPiece.b.b, 0.0F, 0.0F, false, false, false, false, 1.0F)
         )
      );
      var0.a(
         BuiltinStructures.A,
         new RuinedPortalStructure(
            a(var1.b(BiomeTags.A), TerrainAdjustment.a), new RuinedPortalStructure.a(RuinedPortalPiece.b.a, 0.5F, 0.8F, true, true, false, false, 1.0F)
         )
      );
      var0.a(
         BuiltinStructures.B,
         new RuinedPortalStructure(
            a(var1.b(BiomeTags.C), TerrainAdjustment.a), new RuinedPortalStructure.a(RuinedPortalPiece.b.c, 0.0F, 0.5F, false, true, false, false, 1.0F)
         )
      );
      var0.a(
         BuiltinStructures.C,
         new RuinedPortalStructure(
            a(var1.b(BiomeTags.D), TerrainAdjustment.a),
            List.of(
               new RuinedPortalStructure.a(RuinedPortalPiece.b.d, 1.0F, 0.2F, false, false, true, false, 0.5F),
               new RuinedPortalStructure.a(RuinedPortalPiece.b.a, 0.5F, 0.2F, false, false, true, false, 0.5F)
            )
         )
      );
      var0.a(
         BuiltinStructures.D,
         new RuinedPortalStructure(
            a(var1.b(BiomeTags.B), TerrainAdjustment.a), new RuinedPortalStructure.a(RuinedPortalPiece.b.c, 0.0F, 0.8F, false, false, true, false, 1.0F)
         )
      );
      var0.a(
         BuiltinStructures.E,
         new RuinedPortalStructure(
            a(var1.b(BiomeTags.T), TerrainAdjustment.a), new RuinedPortalStructure.a(RuinedPortalPiece.b.f, 0.5F, 0.0F, false, false, false, true, 1.0F)
         )
      );
      var0.a(
         BuiltinStructures.F,
         new JigsawStructure(
            a(
               var1.b(BiomeTags.S),
               Arrays.stream(EnumCreatureType.values())
                  .collect(Collectors.toMap(var0x -> var0x, var0x -> new StructureSpawnOverride(StructureSpawnOverride.a.b, WeightedRandomList.c()))),
               WorldGenStage.Decoration.h,
               TerrainAdjustment.d
            ),
            var2.b(AncientCityStructurePieces.a),
            Optional.of(new MinecraftKey("city_anchor")),
            7,
            ConstantHeight.a(VerticalAnchor.a(-27)),
            false,
            Optional.empty(),
            116
         )
      );
   }
}
