package net.minecraft.data.worldgen.biome;

import javax.annotation.Nullable;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeSettings;
import net.minecraft.data.worldgen.WorldGenCarvers;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeFog;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import net.minecraft.world.level.biome.CaveSoundSettings;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverWrapper;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class OverworldBiomes {
   protected static final int a = 4159204;
   protected static final int b = 329011;
   private static final int c = 12638463;
   @Nullable
   private static final Music d = null;

   protected static int a(float var0) {
      float var1 = var0 / 3.0F;
      var1 = MathHelper.a(var1, -1.0F, 1.0F);
      return MathHelper.h(0.62222224F - var1 * 0.05F, 0.5F + var1 * 0.1F, 1.0F);
   }

   private static BiomeBase a(boolean var0, float var1, float var2, BiomeSettingsMobs.a var3, BiomeSettingsGeneration.a var4, @Nullable Music var5) {
      return a(var0, var1, var2, 4159204, 329011, null, null, var3, var4, var5);
   }

   private static BiomeBase a(
      boolean var0,
      float var1,
      float var2,
      int var3,
      int var4,
      @Nullable Integer var5,
      @Nullable Integer var6,
      BiomeSettingsMobs.a var7,
      BiomeSettingsGeneration.a var8,
      @Nullable Music var9
   ) {
      BiomeFog.a var10 = new BiomeFog.a().b(var3).c(var4).a(12638463).d(a(var1)).a(CaveSoundSettings.b).a(var9);
      if (var5 != null) {
         var10.f(var5);
      }

      if (var6 != null) {
         var10.e(var6);
      }

      return new BiomeBase.a().a(var0).a(var1).b(var2).a(var10.a()).a(var7.a()).a(var8.a()).a();
   }

   private static void a(BiomeSettingsGeneration.a var0) {
      BiomeSettings.a(var0);
      BiomeSettings.aw(var0);
      BiomeSettings.b(var0);
      BiomeSettings.c(var0);
      BiomeSettings.ap(var0);
      BiomeSettings.at(var0);
   }

   public static BiomeBase a(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1, boolean var2) {
      BiomeSettingsMobs.a var3 = new BiomeSettingsMobs.a();
      BiomeSettings.a(var3);
      var3.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.bn, 8, 4, 4));
      var3.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aC, 4, 2, 3));
      var3.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.N, 8, 2, 4));
      if (var2) {
         BiomeSettings.c(var3);
      } else {
         BiomeSettings.b(var3);
         BiomeSettings.a(var3, 100, 25, 100, false);
      }

      BiomeSettingsGeneration.a var4 = new BiomeSettingsGeneration.a(var0, var1);
      a(var4);
      BiomeSettings.m(var4);
      BiomeSettings.n(var4);
      BiomeSettings.f(var4);
      BiomeSettings.j(var4);
      var4.a(WorldGenStage.Decoration.j, var2 ? VegetationPlacements.ar : VegetationPlacements.as);
      BiomeSettings.V(var4);
      BiomeSettings.U(var4);
      BiomeSettings.ac(var4);
      BiomeSettings.ad(var4);
      BiomeSettings.p(var4);
      Music var5 = Musics.a(SoundEffects.ot);
      return a(true, var2 ? 0.25F : 0.3F, 0.8F, var3, var4, var5);
   }

   public static BiomeBase a(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs.a var2 = new BiomeSettingsMobs.a();
      BiomeSettings.i(var2);
      return a(var0, var1, 0.8F, false, true, false, var2);
   }

   public static BiomeBase b(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs.a var2 = new BiomeSettingsMobs.a();
      BiomeSettings.i(var2);
      var2.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.at, 40, 1, 2))
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.aq, 2, 1, 3))
         .a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.as, 1, 1, 2));
      return a(var0, var1, 0.9F, false, false, true, var2);
   }

   public static BiomeBase c(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs.a var2 = new BiomeSettingsMobs.a();
      BiomeSettings.i(var2);
      var2.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.at, 40, 1, 2))
         .a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.as, 80, 1, 2))
         .a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.aq, 2, 1, 1));
      return a(var0, var1, 0.9F, true, false, true, var2);
   }

   private static BiomeBase a(
      HolderGetter<PlacedFeature> var0,
      HolderGetter<WorldGenCarverWrapper<?>> var1,
      float var2,
      boolean var3,
      boolean var4,
      boolean var5,
      BiomeSettingsMobs.a var6
   ) {
      BiomeSettingsGeneration.a var7 = new BiomeSettingsGeneration.a(var0, var1);
      a(var7);
      BiomeSettings.f(var7);
      BiomeSettings.j(var7);
      if (var3) {
         BiomeSettings.r(var7);
      } else {
         if (var5) {
            BiomeSettings.q(var7);
         }

         if (var4) {
            BiomeSettings.F(var7);
         } else {
            BiomeSettings.E(var7);
         }
      }

      BiomeSettings.Y(var7);
      BiomeSettings.I(var7);
      BiomeSettings.ac(var7);
      BiomeSettings.ad(var7);
      BiomeSettings.ah(var7);
      if (var4) {
         BiomeSettings.ag(var7);
      } else {
         BiomeSettings.af(var7);
      }

      Music var8 = Musics.a(SoundEffects.os);
      return a(true, 0.95F, var2, var6, var7, var8);
   }

   public static BiomeBase b(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1, boolean var2) {
      BiomeSettingsMobs.a var3 = new BiomeSettingsMobs.a();
      BiomeSettings.a(var3);
      var3.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aj, 5, 4, 6));
      BiomeSettings.c(var3);
      BiomeSettingsGeneration.a var4 = new BiomeSettingsGeneration.a(var0, var1);
      a(var4);
      BiomeSettings.f(var4);
      BiomeSettings.j(var4);
      if (var2) {
         BiomeSettings.D(var4);
      } else {
         BiomeSettings.C(var4);
      }

      BiomeSettings.V(var4);
      BiomeSettings.Z(var4);
      BiomeSettings.ac(var4);
      BiomeSettings.ad(var4);
      BiomeSettings.h(var4);
      BiomeSettings.i(var4);
      return a(true, 0.2F, 0.3F, var3, var4, d);
   }

   public static BiomeBase d(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs.a var2 = new BiomeSettingsMobs.a();
      BiomeSettings.f(var2);
      BiomeSettingsGeneration.a var3 = new BiomeSettingsGeneration.a(var0, var1);
      BiomeSettings.al(var3);
      a(var3);
      BiomeSettings.f(var3);
      BiomeSettings.j(var3);
      BiomeSettings.V(var3);
      BiomeSettings.Z(var3);
      BiomeSettings.T(var3);
      BiomeSettings.ac(var3);
      BiomeSettings.ai(var3);
      BiomeSettings.ak(var3);
      return a(false, 2.0F, 0.0F, var2, var3, d);
   }

   public static BiomeBase a(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1, boolean var2, boolean var3, boolean var4) {
      BiomeSettingsMobs.a var5 = new BiomeSettingsMobs.a();
      BiomeSettingsGeneration.a var6 = new BiomeSettingsGeneration.a(var0, var1);
      a(var6);
      if (var3) {
         var5.a(0.07F);
         BiomeSettings.e(var5);
         if (var4) {
            var6.a(WorldGenStage.Decoration.e, MiscOverworldPlacements.a);
            var6.a(WorldGenStage.Decoration.e, MiscOverworldPlacements.b);
         }
      } else {
         BiomeSettings.d(var5);
         BiomeSettings.ab(var6);
         if (var2) {
            var6.a(WorldGenStage.Decoration.j, VegetationPlacements.d);
         }
      }

      BiomeSettings.f(var6);
      BiomeSettings.j(var6);
      if (var3) {
         BiomeSettings.H(var6);
         BiomeSettings.V(var6);
         BiomeSettings.Z(var6);
      } else {
         BiomeSettings.S(var6);
      }

      BiomeSettings.ac(var6);
      if (var2) {
         var6.a(WorldGenStage.Decoration.j, VegetationPlacements.E);
         var6.a(WorldGenStage.Decoration.j, VegetationPlacements.e);
      } else {
         BiomeSettings.ad(var6);
      }

      float var7 = var3 ? 0.0F : 0.8F;
      return a(true, var7, var3 ? 0.5F : 0.4F, var5, var6, d);
   }

   public static BiomeBase e(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs.a var2 = new BiomeSettingsMobs.a();
      BiomeSettings.h(var2);
      BiomeSettingsGeneration.a var3 = new BiomeSettingsGeneration.a(var0, var1);
      a(var3);
      BiomeSettings.f(var3);
      BiomeSettings.j(var3);
      BiomeSettings.R(var3);
      BiomeSettings.ad(var3);
      return a(true, 0.9F, 1.0F, var2, var3, d);
   }

   public static BiomeBase a(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1, boolean var2, boolean var3) {
      BiomeSettingsGeneration.a var4 = new BiomeSettingsGeneration.a(var0, var1);
      a(var4);
      if (!var2) {
         BiomeSettings.J(var4);
      }

      BiomeSettings.f(var4);
      BiomeSettings.j(var4);
      if (var2) {
         BiomeSettings.z(var4);
         BiomeSettings.V(var4);
         BiomeSettings.K(var4);
      } else {
         BiomeSettings.y(var4);
         BiomeSettings.Y(var4);
         BiomeSettings.L(var4);
      }

      BiomeSettings.ac(var4);
      BiomeSettings.ad(var4);
      BiomeSettingsMobs.a var5 = new BiomeSettingsMobs.a();
      BiomeSettings.a(var5);
      var5.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.Y, 1, 2, 6)).a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.w, 1, 1, 1));
      BiomeSettings.c(var5);
      if (var3) {
         var5.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aj, 8, 4, 4));
      }

      return a(false, 2.0F, 0.0F, var5, var4, d);
   }

   public static BiomeBase c(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1, boolean var2) {
      BiomeSettingsMobs.a var3 = new BiomeSettingsMobs.a();
      BiomeSettings.c(var3);
      BiomeSettingsGeneration.a var4 = new BiomeSettingsGeneration.a(var0, var1);
      a(var4);
      BiomeSettings.f(var4);
      BiomeSettings.g(var4);
      BiomeSettings.j(var4);
      if (var2) {
         BiomeSettings.G(var4);
      }

      BiomeSettings.M(var4);
      BiomeSettings.ac(var4);
      BiomeSettings.ae(var4);
      return new BiomeBase.a()
         .a(false)
         .a(2.0F)
         .b(0.0F)
         .a(new BiomeFog.a().b(4159204).c(329011).a(12638463).d(a(2.0F)).e(10387789).f(9470285).a(CaveSoundSettings.b).a())
         .a(var3.a())
         .a(var4.a())
         .a();
   }

   private static BiomeBase a(BiomeSettingsMobs.a var0, int var1, int var2, BiomeSettingsGeneration.a var3) {
      return a(true, 0.5F, 0.5F, var1, var2, null, null, var0, var3, d);
   }

   private static BiomeSettingsGeneration.a s(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsGeneration.a var2 = new BiomeSettingsGeneration.a(var0, var1);
      a(var2);
      BiomeSettings.f(var2);
      BiomeSettings.j(var2);
      BiomeSettings.u(var2);
      BiomeSettings.V(var2);
      BiomeSettings.Z(var2);
      BiomeSettings.ac(var2);
      BiomeSettings.ad(var2);
      return var2;
   }

   public static BiomeBase d(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1, boolean var2) {
      BiomeSettingsMobs.a var3 = new BiomeSettingsMobs.a();
      BiomeSettings.a(var3, 3, 4, 15);
      var3.a(EnumCreatureType.g, new BiomeSettingsMobs.c(EntityTypes.aE, 15, 1, 5));
      BiomeSettingsGeneration.a var4 = s(var0, var1);
      var4.a(WorldGenStage.Decoration.j, var2 ? AquaticPlacements.h : AquaticPlacements.c);
      BiomeSettings.an(var4);
      BiomeSettings.am(var4);
      return a(var3, 4020182, 329011, var4);
   }

   public static BiomeBase e(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1, boolean var2) {
      BiomeSettingsMobs.a var3 = new BiomeSettingsMobs.a();
      BiomeSettings.a(var3, 1, 4, 10);
      var3.a(EnumCreatureType.f, new BiomeSettingsMobs.c(EntityTypes.v, 1, 1, 2));
      BiomeSettingsGeneration.a var4 = s(var0, var1);
      var4.a(WorldGenStage.Decoration.j, var2 ? AquaticPlacements.g : AquaticPlacements.b);
      BiomeSettings.an(var4);
      BiomeSettings.am(var4);
      return a(var3, 4159204, 329011, var4);
   }

   public static BiomeBase f(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1, boolean var2) {
      BiomeSettingsMobs.a var3 = new BiomeSettingsMobs.a();
      if (var2) {
         BiomeSettings.a(var3, 8, 4, 8);
      } else {
         BiomeSettings.a(var3, 10, 2, 15);
      }

      var3.a(EnumCreatureType.g, new BiomeSettingsMobs.c(EntityTypes.aB, 5, 1, 3))
         .a(EnumCreatureType.g, new BiomeSettingsMobs.c(EntityTypes.bc, 25, 8, 8))
         .a(EnumCreatureType.f, new BiomeSettingsMobs.c(EntityTypes.v, 2, 1, 2));
      BiomeSettingsGeneration.a var4 = s(var0, var1);
      var4.a(WorldGenStage.Decoration.j, var2 ? AquaticPlacements.f : AquaticPlacements.a);
      if (var2) {
         BiomeSettings.an(var4);
      }

      BiomeSettings.ao(var4);
      return a(var3, 4566514, 267827, var4);
   }

   public static BiomeBase f(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs.a var2 = new BiomeSettingsMobs.a().a(EnumCreatureType.g, new BiomeSettingsMobs.c(EntityTypes.aB, 15, 1, 3));
      BiomeSettings.a(var2, 10, 4);
      BiomeSettingsGeneration.a var3 = s(var0, var1)
         .a(WorldGenStage.Decoration.j, AquaticPlacements.m)
         .a(WorldGenStage.Decoration.j, AquaticPlacements.a)
         .a(WorldGenStage.Decoration.j, AquaticPlacements.j);
      return a(var2, 4445678, 270131, var3);
   }

   public static BiomeBase g(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1, boolean var2) {
      BiomeSettingsMobs.a var3 = new BiomeSettingsMobs.a()
         .a(EnumCreatureType.f, new BiomeSettingsMobs.c(EntityTypes.aT, 1, 1, 4))
         .a(EnumCreatureType.g, new BiomeSettingsMobs.c(EntityTypes.aE, 15, 1, 5))
         .a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.az, 1, 1, 2));
      BiomeSettings.c(var3);
      var3.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.y, 5, 1, 1));
      float var4 = var2 ? 0.5F : 0.0F;
      BiomeSettingsGeneration.a var5 = new BiomeSettingsGeneration.a(var0, var1);
      BiomeSettings.ar(var5);
      a(var5);
      BiomeSettings.as(var5);
      BiomeSettings.f(var5);
      BiomeSettings.j(var5);
      BiomeSettings.u(var5);
      BiomeSettings.V(var5);
      BiomeSettings.Z(var5);
      BiomeSettings.ac(var5);
      BiomeSettings.ad(var5);
      return new BiomeBase.a()
         .a(true)
         .a(var4)
         .a(BiomeBase.TemperatureModifier.b)
         .b(0.5F)
         .a(new BiomeFog.a().b(3750089).c(329011).a(12638463).d(a(var4)).a(CaveSoundSettings.b).a())
         .a(var3.a())
         .a(var5.a())
         .a();
   }

   public static BiomeBase b(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1, boolean var2, boolean var3, boolean var4) {
      BiomeSettingsGeneration.a var5 = new BiomeSettingsGeneration.a(var0, var1);
      a(var5);
      if (var4) {
         var5.a(WorldGenStage.Decoration.j, VegetationPlacements.Y);
      } else {
         BiomeSettings.N(var5);
      }

      BiomeSettings.f(var5);
      BiomeSettings.j(var5);
      if (var4) {
         var5.a(WorldGenStage.Decoration.j, VegetationPlacements.aa);
         var5.a(WorldGenStage.Decoration.j, VegetationPlacements.R);
         BiomeSettings.Z(var5);
      } else {
         if (var2) {
            if (var3) {
               BiomeSettings.x(var5);
            } else {
               BiomeSettings.v(var5);
            }
         } else {
            BiomeSettings.w(var5);
         }

         BiomeSettings.V(var5);
         BiomeSettings.O(var5);
      }

      BiomeSettings.ac(var5);
      BiomeSettings.ad(var5);
      BiomeSettingsMobs.a var6 = new BiomeSettingsMobs.a();
      BiomeSettings.a(var6);
      BiomeSettings.c(var6);
      if (var4) {
         var6.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aC, 4, 2, 3));
      } else if (!var2) {
         var6.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.bn, 5, 4, 4));
      }

      float var7 = var2 ? 0.6F : 0.7F;
      Music var8 = Musics.a(SoundEffects.os);
      return a(true, var7, var2 ? 0.6F : 0.8F, var6, var5, var8);
   }

   public static BiomeBase h(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1, boolean var2) {
      BiomeSettingsMobs.a var3 = new BiomeSettingsMobs.a();
      BiomeSettings.a(var3);
      var3.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.bn, 8, 4, 4))
         .a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aC, 4, 2, 3))
         .a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.N, 8, 2, 4));
      BiomeSettings.c(var3);
      float var4 = var2 ? -0.5F : 0.25F;
      BiomeSettingsGeneration.a var5 = new BiomeSettingsGeneration.a(var0, var1);
      a(var5);
      BiomeSettings.n(var5);
      BiomeSettings.f(var5);
      BiomeSettings.j(var5);
      BiomeSettings.s(var5);
      BiomeSettings.V(var5);
      BiomeSettings.aa(var5);
      BiomeSettings.ad(var5);
      if (var2) {
         BiomeSettings.o(var5);
      } else {
         BiomeSettings.p(var5);
      }

      return a(true, var4, var2 ? 0.4F : 0.8F, var2 ? 4020182 : 4159204, 329011, null, null, var3, var5, d);
   }

   public static BiomeBase g(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs.a var2 = new BiomeSettingsMobs.a();
      BiomeSettings.a(var2);
      BiomeSettings.c(var2);
      BiomeSettingsGeneration.a var3 = new BiomeSettingsGeneration.a(var0, var1);
      a(var3);
      var3.a(WorldGenStage.Decoration.j, VegetationPlacements.X);
      BiomeSettings.N(var3);
      BiomeSettings.f(var3);
      BiomeSettings.j(var3);
      BiomeSettings.V(var3);
      BiomeSettings.O(var3);
      BiomeSettings.ac(var3);
      BiomeSettings.ad(var3);
      Music var4 = Musics.a(SoundEffects.os);
      return new BiomeBase.a()
         .a(true)
         .a(0.7F)
         .b(0.8F)
         .a(new BiomeFog.a().b(4159204).c(329011).a(12638463).d(a(0.7F)).a(BiomeFog.GrassColor.b).a(CaveSoundSettings.b).a(var4).a())
         .a(var2.a())
         .a(var3.a())
         .a();
   }

   public static BiomeBase h(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs.a var2 = new BiomeSettingsMobs.a();
      BiomeSettings.a(var2);
      BiomeSettings.c(var2);
      var2.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.aL, 1, 1, 1));
      var2.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.O, 10, 2, 5));
      BiomeSettingsGeneration.a var3 = new BiomeSettingsGeneration.a(var0, var1);
      BiomeSettings.al(var3);
      a(var3);
      BiomeSettings.f(var3);
      BiomeSettings.k(var3);
      BiomeSettings.P(var3);
      BiomeSettings.ac(var3);
      BiomeSettings.aj(var3);
      var3.a(WorldGenStage.Decoration.j, AquaticPlacements.e);
      Music var4 = Musics.a(SoundEffects.or);
      return new BiomeBase.a()
         .a(true)
         .a(0.8F)
         .b(0.9F)
         .a(new BiomeFog.a().b(6388580).c(2302743).a(12638463).d(a(0.8F)).e(6975545).a(BiomeFog.GrassColor.c).a(CaveSoundSettings.b).a(var4).a())
         .a(var2.a())
         .a(var3.a())
         .a();
   }

   public static BiomeBase i(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs.a var2 = new BiomeSettingsMobs.a();
      BiomeSettings.c(var2);
      var2.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.aL, 1, 1, 1));
      var2.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.O, 10, 2, 5));
      var2.a(EnumCreatureType.g, new BiomeSettingsMobs.c(EntityTypes.bc, 25, 8, 8));
      BiomeSettingsGeneration.a var3 = new BiomeSettingsGeneration.a(var0, var1);
      BiomeSettings.al(var3);
      a(var3);
      BiomeSettings.f(var3);
      BiomeSettings.l(var3);
      BiomeSettings.Q(var3);
      var3.a(WorldGenStage.Decoration.j, AquaticPlacements.e);
      Music var4 = Musics.a(SoundEffects.or);
      return new BiomeBase.a()
         .a(true)
         .a(0.8F)
         .b(0.9F)
         .a(new BiomeFog.a().b(3832426).c(5077600).a(12638463).d(a(0.8F)).e(9285927).a(BiomeFog.GrassColor.c).a(CaveSoundSettings.b).a(var4).a())
         .a(var2.a())
         .a(var3.a())
         .a();
   }

   public static BiomeBase i(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1, boolean var2) {
      BiomeSettingsMobs.a var3 = new BiomeSettingsMobs.a()
         .a(EnumCreatureType.f, new BiomeSettingsMobs.c(EntityTypes.aT, 2, 1, 4))
         .a(EnumCreatureType.g, new BiomeSettingsMobs.c(EntityTypes.aE, 5, 1, 5));
      BiomeSettings.c(var3);
      var3.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.y, var2 ? 1 : 100, 1, 1));
      BiomeSettingsGeneration.a var4 = new BiomeSettingsGeneration.a(var0, var1);
      a(var4);
      BiomeSettings.f(var4);
      BiomeSettings.j(var4);
      BiomeSettings.u(var4);
      BiomeSettings.V(var4);
      BiomeSettings.Z(var4);
      BiomeSettings.ac(var4);
      BiomeSettings.ad(var4);
      if (!var2) {
         var4.a(WorldGenStage.Decoration.j, AquaticPlacements.d);
      }

      float var5 = var2 ? 0.0F : 0.5F;
      return a(true, var5, 0.5F, var2 ? 3750089 : 4159204, 329011, null, null, var3, var4, d);
   }

   public static BiomeBase b(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1, boolean var2, boolean var3) {
      BiomeSettingsMobs.a var4 = new BiomeSettingsMobs.a();
      boolean var5 = !var3 && !var2;
      if (var5) {
         var4.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.bd, 5, 2, 5));
      }

      BiomeSettings.c(var4);
      BiomeSettingsGeneration.a var6 = new BiomeSettingsGeneration.a(var0, var1);
      a(var6);
      BiomeSettings.f(var6);
      BiomeSettings.j(var6);
      BiomeSettings.V(var6);
      BiomeSettings.Z(var6);
      BiomeSettings.ac(var6);
      BiomeSettings.ad(var6);
      float var7;
      if (var2) {
         var7 = 0.05F;
      } else if (var3) {
         var7 = 0.2F;
      } else {
         var7 = 0.8F;
      }

      return a(true, var7, var5 ? 0.4F : 0.3F, var2 ? 4020182 : 4159204, 329011, null, null, var4, var6, d);
   }

   public static BiomeBase j(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsGeneration.a var2 = new BiomeSettingsGeneration.a(var0, var1);
      var2.a(WorldGenStage.Decoration.k, MiscOverworldPlacements.n);
      return a(false, 0.5F, 0.5F, new BiomeSettingsMobs.a(), var2, d);
   }

   public static BiomeBase j(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1, boolean var2) {
      BiomeSettingsGeneration.a var3 = new BiomeSettingsGeneration.a(var0, var1);
      BiomeSettingsMobs.a var4 = new BiomeSettingsMobs.a();
      var4.a(EnumCreatureType.b, new BiomeSettingsMobs.c(var2 ? EntityTypes.av : EntityTypes.w, 1, 1, 2))
         .a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aC, 2, 2, 6))
         .a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aF, 2, 2, 4));
      BiomeSettings.c(var4);
      a(var3);
      BiomeSettings.ab(var3);
      BiomeSettings.f(var3);
      BiomeSettings.j(var3);
      if (var2) {
         BiomeSettings.W(var3);
      } else {
         BiomeSettings.X(var3);
      }

      BiomeSettings.h(var3);
      BiomeSettings.i(var3);
      Music var5 = Musics.a(var2 ? SoundEffects.ov : SoundEffects.ou);
      return var2
         ? a(true, 0.5F, 0.8F, 6141935, 6141935, 11983713, 11983713, var4, var3, var5)
         : a(true, 0.5F, 0.8F, 937679, 329011, null, null, var4, var3, var5);
   }

   public static BiomeBase k(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsGeneration.a var2 = new BiomeSettingsGeneration.a(var0, var1);
      BiomeSettingsMobs.a var3 = new BiomeSettingsMobs.a();
      var3.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.U, 5, 1, 3));
      BiomeSettings.c(var3);
      a(var2);
      BiomeSettings.aq(var2);
      BiomeSettings.f(var2);
      BiomeSettings.j(var2);
      BiomeSettings.h(var2);
      BiomeSettings.i(var2);
      Music var4 = Musics.a(SoundEffects.ox);
      return a(true, -0.7F, 0.9F, var3, var2, var4);
   }

   public static BiomeBase l(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsGeneration.a var2 = new BiomeSettingsGeneration.a(var0, var1);
      BiomeSettingsMobs.a var3 = new BiomeSettingsMobs.a();
      var3.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.U, 5, 1, 3));
      BiomeSettings.c(var3);
      a(var2);
      BiomeSettings.aq(var2);
      BiomeSettings.f(var2);
      BiomeSettings.j(var2);
      BiomeSettings.h(var2);
      BiomeSettings.i(var2);
      Music var4 = Musics.a(SoundEffects.op);
      return a(true, -0.7F, 0.9F, var3, var2, var4);
   }

   public static BiomeBase m(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsGeneration.a var2 = new BiomeSettingsGeneration.a(var0, var1);
      BiomeSettingsMobs.a var3 = new BiomeSettingsMobs.a();
      BiomeSettings.c(var3);
      a(var2);
      BiomeSettings.f(var2);
      BiomeSettings.j(var2);
      BiomeSettings.h(var2);
      BiomeSettings.i(var2);
      Music var4 = Musics.a(SoundEffects.oA);
      return a(true, 1.0F, 0.3F, var3, var2, var4);
   }

   public static BiomeBase n(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsGeneration.a var2 = new BiomeSettingsGeneration.a(var0, var1);
      BiomeSettingsMobs.a var3 = new BiomeSettingsMobs.a();
      var3.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aC, 4, 2, 3)).a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.U, 5, 1, 3));
      BiomeSettings.c(var3);
      a(var2);
      BiomeSettings.aq(var2);
      BiomeSettings.f(var2);
      BiomeSettings.j(var2);
      BiomeSettings.ad(var2);
      BiomeSettings.h(var2);
      BiomeSettings.i(var2);
      Music var4 = Musics.a(SoundEffects.oy);
      return a(true, -0.3F, 0.9F, var3, var2, var4);
   }

   public static BiomeBase o(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsGeneration.a var2 = new BiomeSettingsGeneration.a(var0, var1);
      BiomeSettingsMobs.a var3 = new BiomeSettingsMobs.a();
      BiomeSettings.a(var3);
      var3.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.bn, 8, 4, 4))
         .a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aC, 4, 2, 3))
         .a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.N, 8, 2, 4));
      BiomeSettings.c(var3);
      a(var2);
      BiomeSettings.aq(var2);
      BiomeSettings.f(var2);
      BiomeSettings.j(var2);
      BiomeSettings.t(var2);
      BiomeSettings.ad(var2);
      BiomeSettings.h(var2);
      BiomeSettings.i(var2);
      Music var4 = Musics.a(SoundEffects.oo);
      return a(true, -0.2F, 0.8F, var3, var2, var4);
   }

   public static BiomeBase p(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs.a var2 = new BiomeSettingsMobs.a();
      var2.a(EnumCreatureType.d, new BiomeSettingsMobs.c(EntityTypes.f, 10, 4, 6));
      var2.a(EnumCreatureType.g, new BiomeSettingsMobs.c(EntityTypes.bc, 25, 8, 8));
      BiomeSettings.c(var2);
      BiomeSettingsGeneration.a var3 = new BiomeSettingsGeneration.a(var0, var1);
      a(var3);
      BiomeSettings.ab(var3);
      BiomeSettings.f(var3);
      BiomeSettings.B(var3);
      BiomeSettings.j(var3);
      BiomeSettings.A(var3);
      Music var4 = Musics.a(SoundEffects.oq);
      return a(true, 0.5F, 0.5F, var2, var3, var4);
   }

   public static BiomeBase q(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs.a var2 = new BiomeSettingsMobs.a();
      BiomeSettings.g(var2);
      BiomeSettingsGeneration.a var3 = new BiomeSettingsGeneration.a(var0, var1);
      a(var3);
      BiomeSettings.ab(var3);
      BiomeSettings.a(var3, true);
      BiomeSettings.j(var3);
      BiomeSettings.S(var3);
      BiomeSettings.ac(var3);
      BiomeSettings.ad(var3);
      BiomeSettings.d(var3);
      Music var4 = Musics.a(SoundEffects.on);
      return a(true, 0.8F, 0.4F, var2, var3, var4);
   }

   public static BiomeBase r(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsMobs.a var2 = new BiomeSettingsMobs.a();
      BiomeSettingsGeneration.a var3 = new BiomeSettingsGeneration.a(var0, var1);
      var3.a(WorldGenStage.Features.a, WorldGenCarvers.a);
      var3.a(WorldGenStage.Features.a, WorldGenCarvers.b);
      var3.a(WorldGenStage.Features.a, WorldGenCarvers.c);
      BiomeSettings.aw(var3);
      BiomeSettings.b(var3);
      BiomeSettings.c(var3);
      BiomeSettings.at(var3);
      BiomeSettings.ab(var3);
      BiomeSettings.f(var3);
      BiomeSettings.j(var3);
      BiomeSettings.S(var3);
      BiomeSettings.ac(var3);
      BiomeSettings.ad(var3);
      BiomeSettings.e(var3);
      Music var4 = Musics.a(SoundEffects.om);
      return a(true, 0.8F, 0.4F, var2, var3, var4);
   }
}
