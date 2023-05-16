package net.minecraft.data.worldgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.Particles;
import net.minecraft.data.worldgen.BiomeSettings;
import net.minecraft.data.worldgen.WorldGenCarvers;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeFog;
import net.minecraft.world.level.biome.BiomeParticles;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import net.minecraft.world.level.biome.CaveSound;
import net.minecraft.world.level.biome.CaveSoundSettings;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverWrapper;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class NetherBiomes {
   public static BiomeBase a(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs var2 = new BiomeSettingsMobs.a()
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.Q, 50, 4, 4))
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.bs, 100, 4, 4))
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.al, 2, 4, 4))
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.E, 1, 4, 4))
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.aw, 15, 4, 4))
         .a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aV, 60, 1, 2))
         .a();
      BiomeSettingsGeneration.a var3 = new BiomeSettingsGeneration.a(var0, var1)
         .a(WorldGenStage.Features.a, WorldGenCarvers.d)
         .a(WorldGenStage.Decoration.j, MiscOverworldPlacements.p);
      BiomeSettings.ac(var3);
      var3.a(WorldGenStage.Decoration.h, NetherPlacements.r)
         .a(WorldGenStage.Decoration.h, NetherPlacements.t)
         .a(WorldGenStage.Decoration.h, NetherPlacements.s)
         .a(WorldGenStage.Decoration.h, NetherPlacements.f)
         .a(WorldGenStage.Decoration.h, NetherPlacements.g)
         .a(WorldGenStage.Decoration.h, VegetationPlacements.F)
         .a(WorldGenStage.Decoration.h, VegetationPlacements.G)
         .a(WorldGenStage.Decoration.h, OrePlacements.a)
         .a(WorldGenStage.Decoration.h, NetherPlacements.p);
      BiomeSettings.au(var3);
      return new BiomeBase.a()
         .a(false)
         .a(2.0F)
         .b(0.0F)
         .a(
            new BiomeFog.a()
               .b(4159204)
               .c(329011)
               .a(3344392)
               .d(OverworldBiomes.a(2.0F))
               .a(SoundEffects.p)
               .a(new CaveSoundSettings(SoundEffects.q, 6000, 8, 2.0))
               .a(new CaveSound(SoundEffects.o, 0.0111))
               .a(Musics.a(SoundEffects.ow))
               .a()
         )
         .a(var2)
         .a(var3.a())
         .a();
   }

   public static BiomeBase b(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      double var2 = 0.7;
      double var4 = 0.15;
      BiomeSettingsMobs var6 = new BiomeSettingsMobs.a()
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.aJ, 20, 5, 5))
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.Q, 50, 4, 4))
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.E, 1, 4, 4))
         .a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aV, 60, 1, 2))
         .a(EntityTypes.aJ, 0.7, 0.15)
         .a(EntityTypes.Q, 0.7, 0.15)
         .a(EntityTypes.E, 0.7, 0.15)
         .a(EntityTypes.aV, 0.7, 0.15)
         .a();
      BiomeSettingsGeneration.a var7 = new BiomeSettingsGeneration.a(var0, var1)
         .a(WorldGenStage.Features.a, WorldGenCarvers.d)
         .a(WorldGenStage.Decoration.j, MiscOverworldPlacements.p)
         .a(WorldGenStage.Decoration.c, NetherPlacements.n)
         .a(WorldGenStage.Decoration.h, NetherPlacements.r)
         .a(WorldGenStage.Decoration.h, NetherPlacements.t)
         .a(WorldGenStage.Decoration.h, NetherPlacements.s)
         .a(WorldGenStage.Decoration.h, NetherPlacements.f)
         .a(WorldGenStage.Decoration.h, NetherPlacements.g)
         .a(WorldGenStage.Decoration.h, NetherPlacements.m)
         .a(WorldGenStage.Decoration.h, OrePlacements.a)
         .a(WorldGenStage.Decoration.h, NetherPlacements.p)
         .a(WorldGenStage.Decoration.h, OrePlacements.b);
      BiomeSettings.au(var7);
      return new BiomeBase.a()
         .a(false)
         .a(2.0F)
         .b(0.0F)
         .a(
            new BiomeFog.a()
               .b(4159204)
               .c(329011)
               .a(1787717)
               .d(OverworldBiomes.a(2.0F))
               .a(new BiomeParticles(Particles.aw, 0.00625F))
               .a(SoundEffects.s)
               .a(new CaveSoundSettings(SoundEffects.t, 6000, 8, 2.0))
               .a(new CaveSound(SoundEffects.r, 0.0111))
               .a(Musics.a(SoundEffects.oz))
               .a()
         )
         .a(var6)
         .a(var7.a())
         .a();
   }

   public static BiomeBase c(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs var2 = new BiomeSettingsMobs.a()
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.Q, 40, 1, 1))
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.al, 100, 2, 5))
         .a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aV, 60, 1, 2))
         .a();
      BiomeSettingsGeneration.a var3 = new BiomeSettingsGeneration.a(var0, var1)
         .a(WorldGenStage.Features.a, WorldGenCarvers.d)
         .a(WorldGenStage.Decoration.e, NetherPlacements.a)
         .a(WorldGenStage.Decoration.e, NetherPlacements.b)
         .a(WorldGenStage.Decoration.e, NetherPlacements.c)
         .a(WorldGenStage.Decoration.h, NetherPlacements.d)
         .a(WorldGenStage.Decoration.h, NetherPlacements.e)
         .a(WorldGenStage.Decoration.h, NetherPlacements.o)
         .a(WorldGenStage.Decoration.h, NetherPlacements.t)
         .a(WorldGenStage.Decoration.h, NetherPlacements.s)
         .a(WorldGenStage.Decoration.h, NetherPlacements.f)
         .a(WorldGenStage.Decoration.h, NetherPlacements.g)
         .a(WorldGenStage.Decoration.h, VegetationPlacements.F)
         .a(WorldGenStage.Decoration.h, VegetationPlacements.G)
         .a(WorldGenStage.Decoration.h, OrePlacements.a)
         .a(WorldGenStage.Decoration.h, NetherPlacements.q)
         .a(WorldGenStage.Decoration.h, OrePlacements.c)
         .a(WorldGenStage.Decoration.h, OrePlacements.d);
      BiomeSettings.av(var3);
      return new BiomeBase.a()
         .a(false)
         .a(2.0F)
         .b(0.0F)
         .a(
            new BiomeFog.a()
               .b(4159204)
               .c(329011)
               .a(6840176)
               .d(OverworldBiomes.a(2.0F))
               .a(new BiomeParticles(Particles.aE, 0.118093334F))
               .a(SoundEffects.j)
               .a(new CaveSoundSettings(SoundEffects.k, 6000, 8, 2.0))
               .a(new CaveSound(SoundEffects.i, 0.0111))
               .a(Musics.a(SoundEffects.ok))
               .a()
         )
         .a(var2)
         .a(var3.a())
         .a();
   }

   public static BiomeBase d(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs var2 = new BiomeSettingsMobs.a()
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.bs, 1, 2, 4))
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.W, 9, 3, 4))
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.aw, 5, 3, 4))
         .a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aV, 60, 1, 2))
         .a();
      BiomeSettingsGeneration.a var3 = new BiomeSettingsGeneration.a(var0, var1)
         .a(WorldGenStage.Features.a, WorldGenCarvers.d)
         .a(WorldGenStage.Decoration.j, MiscOverworldPlacements.p);
      BiomeSettings.ac(var3);
      var3.a(WorldGenStage.Decoration.h, NetherPlacements.r)
         .a(WorldGenStage.Decoration.h, NetherPlacements.t)
         .a(WorldGenStage.Decoration.h, NetherPlacements.f)
         .a(WorldGenStage.Decoration.h, NetherPlacements.g)
         .a(WorldGenStage.Decoration.h, OrePlacements.a)
         .a(WorldGenStage.Decoration.h, NetherPlacements.p)
         .a(WorldGenStage.Decoration.j, NetherPlacements.l)
         .a(WorldGenStage.Decoration.j, TreePlacements.a)
         .a(WorldGenStage.Decoration.j, NetherPlacements.h);
      BiomeSettings.au(var3);
      return new BiomeBase.a()
         .a(false)
         .a(2.0F)
         .b(0.0F)
         .a(
            new BiomeFog.a()
               .b(4159204)
               .c(329011)
               .a(3343107)
               .d(OverworldBiomes.a(2.0F))
               .a(new BiomeParticles(Particles.ax, 0.025F))
               .a(SoundEffects.m)
               .a(new CaveSoundSettings(SoundEffects.n, 6000, 8, 2.0))
               .a(new CaveSound(SoundEffects.l, 0.0111))
               .a(Musics.a(SoundEffects.ol))
               .a()
         )
         .a(var2)
         .a(var3.a())
         .a();
   }

   public static BiomeBase e(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs var2 = new BiomeSettingsMobs.a()
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.E, 1, 4, 4))
         .a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aV, 60, 1, 2))
         .a(EntityTypes.E, 1.0, 0.12)
         .a();
      BiomeSettingsGeneration.a var3 = new BiomeSettingsGeneration.a(var0, var1)
         .a(WorldGenStage.Features.a, WorldGenCarvers.d)
         .a(WorldGenStage.Decoration.j, MiscOverworldPlacements.p);
      BiomeSettings.ac(var3);
      var3.a(WorldGenStage.Decoration.h, NetherPlacements.r)
         .a(WorldGenStage.Decoration.h, NetherPlacements.t)
         .a(WorldGenStage.Decoration.h, NetherPlacements.s)
         .a(WorldGenStage.Decoration.h, NetherPlacements.f)
         .a(WorldGenStage.Decoration.h, NetherPlacements.g)
         .a(WorldGenStage.Decoration.h, OrePlacements.a)
         .a(WorldGenStage.Decoration.h, NetherPlacements.p)
         .a(WorldGenStage.Decoration.j, TreePlacements.b)
         .a(WorldGenStage.Decoration.j, NetherPlacements.i)
         .a(WorldGenStage.Decoration.j, NetherPlacements.j)
         .a(WorldGenStage.Decoration.j, NetherPlacements.k);
      BiomeSettings.au(var3);
      return new BiomeBase.a()
         .a(false)
         .a(2.0F)
         .b(0.0F)
         .a(
            new BiomeFog.a()
               .b(4159204)
               .c(329011)
               .a(1705242)
               .d(OverworldBiomes.a(2.0F))
               .a(new BiomeParticles(Particles.ay, 0.01428F))
               .a(SoundEffects.v)
               .a(new CaveSoundSettings(SoundEffects.w, 6000, 8, 2.0))
               .a(new CaveSound(SoundEffects.u, 0.0111))
               .a(Musics.a(SoundEffects.oB))
               .a()
         )
         .a(var2)
         .a(var3.a())
         .a();
   }
}
