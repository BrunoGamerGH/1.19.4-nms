package net.minecraft.data.worldgen;

import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import net.minecraft.world.level.levelgen.WorldGenStage;

public class BiomeSettings {
   public static void a(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Features.a, WorldGenCarvers.a);
      var0.a(WorldGenStage.Features.a, WorldGenCarvers.b);
      var0.a(WorldGenStage.Features.a, WorldGenCarvers.c);
      var0.a(WorldGenStage.Decoration.b, MiscOverworldPlacements.g);
      var0.a(WorldGenStage.Decoration.b, MiscOverworldPlacements.h);
   }

   public static void b(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.d, CavePlacements.a);
      var0.a(WorldGenStage.Decoration.d, CavePlacements.b);
   }

   public static void c(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.g, OrePlacements.i);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.j);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.k);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.l);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.m);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.n);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.o);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.p);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.q);
      var0.a(WorldGenStage.Decoration.j, CavePlacements.i);
   }

   public static void d(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.c, CavePlacements.f);
      var0.a(WorldGenStage.Decoration.h, CavePlacements.e);
      var0.a(WorldGenStage.Decoration.h, CavePlacements.g);
   }

   public static void e(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.h, CavePlacements.t);
      var0.a(WorldGenStage.Decoration.h, CavePlacements.r);
   }

   public static void f(BiomeSettingsGeneration.a var0) {
      a(var0, false);
   }

   public static void a(BiomeSettingsGeneration.a var0, boolean var1) {
      var0.a(WorldGenStage.Decoration.g, OrePlacements.r);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.s);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.t);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.u);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.v);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.x);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.y);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.z);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.A);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.B);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.C);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.D);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.E);
      var0.a(WorldGenStage.Decoration.g, OrePlacements.F);
      var0.a(WorldGenStage.Decoration.g, var1 ? OrePlacements.L : OrePlacements.K);
      var0.a(WorldGenStage.Decoration.g, CavePlacements.h);
   }

   public static void g(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.g, OrePlacements.w);
   }

   public static void h(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.g, OrePlacements.H);
   }

   public static void i(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.h, OrePlacements.G);
   }

   public static void j(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.g, MiscOverworldPlacements.k);
      var0.a(WorldGenStage.Decoration.g, MiscOverworldPlacements.i);
      var0.a(WorldGenStage.Decoration.g, MiscOverworldPlacements.j);
   }

   public static void k(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.g, MiscOverworldPlacements.i);
   }

   public static void l(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.g, MiscOverworldPlacements.l);
      var0.a(WorldGenStage.Decoration.g, MiscOverworldPlacements.i);
   }

   public static void m(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.c, MiscOverworldPlacements.c);
   }

   public static void n(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.y);
   }

   public static void o(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.u);
   }

   public static void p(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.t);
   }

   public static void q(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.a);
   }

   public static void r(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.b);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.au);
   }

   public static void s(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.ad);
   }

   public static void t(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.ae);
   }

   public static void u(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.ao);
   }

   public static void v(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.al);
   }

   public static void w(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.ap);
   }

   public static void x(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.ak);
   }

   public static void y(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.aj);
   }

   public static void z(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.ai);
   }

   public static void A(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, CavePlacements.n);
      var0.a(WorldGenStage.Decoration.j, CavePlacements.k);
      var0.a(WorldGenStage.Decoration.j, CavePlacements.m);
      var0.a(WorldGenStage.Decoration.j, CavePlacements.l);
      var0.a(WorldGenStage.Decoration.j, CavePlacements.j);
      var0.a(WorldGenStage.Decoration.j, CavePlacements.o);
      var0.a(WorldGenStage.Decoration.j, CavePlacements.p);
   }

   public static void B(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.g, OrePlacements.M);
   }

   public static void C(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.an);
   }

   public static void D(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.am);
   }

   public static void E(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.at);
   }

   public static void F(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.aq);
   }

   public static void G(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.af);
   }

   public static void H(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.ag);
   }

   public static void I(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.m);
   }

   public static void J(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.x);
   }

   public static void K(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.j);
   }

   public static void L(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.i);
   }

   public static void M(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.h);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.q);
   }

   public static void N(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.Z);
   }

   public static void O(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.g);
   }

   public static void P(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.ah);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.S);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.j);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.p);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.v);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.N);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.O);
   }

   public static void Q(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.aw);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.j);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.p);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.v);
   }

   public static void R(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.av);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.J);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.K);
   }

   public static void S(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.W);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.T);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.f);
   }

   public static void T(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.o);
   }

   public static void U(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.l);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.p);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.L);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.M);
   }

   public static void V(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.Q);
   }

   public static void W(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.f);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.V);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.ac);
   }

   public static void X(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.f);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.U);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.ab);
   }

   public static void Y(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.P);
   }

   public static void Z(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.h);
   }

   public static void aa(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.k);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.J);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.K);
   }

   public static void ab(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.w);
   }

   public static void ac(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.H);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.I);
   }

   public static void ad(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.E);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.e);
   }

   public static void ae(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.D);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.e);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.A);
   }

   public static void af(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.r);
   }

   public static void ag(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.s);
   }

   public static void ah(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.c);
   }

   public static void ai(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.C);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.e);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.z);
   }

   public static void aj(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.B);
      var0.a(WorldGenStage.Decoration.j, VegetationPlacements.e);
   }

   public static void ak(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.e, MiscOverworldPlacements.o);
   }

   public static void al(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.d, CavePlacements.c);
      var0.a(WorldGenStage.Decoration.d, CavePlacements.d);
   }

   public static void am(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, AquaticPlacements.k);
   }

   public static void an(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, AquaticPlacements.i);
   }

   public static void ao(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.j, AquaticPlacements.l);
   }

   public static void ap(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.i, MiscOverworldPlacements.r);
      var0.a(WorldGenStage.Decoration.i, MiscOverworldPlacements.p);
   }

   public static void aq(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.i, MiscOverworldPlacements.q);
   }

   public static void ar(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.c, MiscOverworldPlacements.d);
      var0.a(WorldGenStage.Decoration.c, MiscOverworldPlacements.e);
   }

   public static void as(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.e, MiscOverworldPlacements.f);
   }

   public static void at(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.k, MiscOverworldPlacements.m);
   }

   public static void au(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.h, OrePlacements.g);
      var0.a(WorldGenStage.Decoration.h, OrePlacements.h);
      var0.a(WorldGenStage.Decoration.h, OrePlacements.e);
      var0.a(WorldGenStage.Decoration.h, OrePlacements.f);
      av(var0);
   }

   public static void av(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.h, OrePlacements.I);
      var0.a(WorldGenStage.Decoration.h, OrePlacements.J);
   }

   public static void aw(BiomeSettingsGeneration.a var0) {
      var0.a(WorldGenStage.Decoration.c, CavePlacements.q);
   }

   public static void a(BiomeSettingsMobs.a var0) {
      var0.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aF, 12, 4, 4));
      var0.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.av, 10, 4, 4));
      var0.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.q, 10, 4, 4));
      var0.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.t, 8, 4, 4));
   }

   public static void b(BiomeSettingsMobs.a var0) {
      var0.a(EnumCreatureType.c, new BiomeSettingsMobs.c(EntityTypes.g, 10, 8, 8));
      var0.a(EnumCreatureType.e, new BiomeSettingsMobs.c(EntityTypes.T, 10, 4, 6));
   }

   public static void c(BiomeSettingsMobs.a var0) {
      b(var0);
      a(var0, 95, 5, 100, false);
   }

   public static void a(BiomeSettingsMobs.a var0, int var1, int var2, int var3) {
      var0.a(EnumCreatureType.f, new BiomeSettingsMobs.c(EntityTypes.aT, var1, 1, var2));
      var0.a(EnumCreatureType.g, new BiomeSettingsMobs.c(EntityTypes.r, var3, 3, 6));
      c(var0);
      var0.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.y, 5, 1, 1));
   }

   public static void a(BiomeSettingsMobs.a var0, int var1, int var2) {
      var0.a(EnumCreatureType.f, new BiomeSettingsMobs.c(EntityTypes.aT, var1, var2, 4));
      var0.a(EnumCreatureType.g, new BiomeSettingsMobs.c(EntityTypes.bc, 25, 8, 8));
      var0.a(EnumCreatureType.f, new BiomeSettingsMobs.c(EntityTypes.v, 2, 1, 2));
      var0.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.y, 5, 1, 1));
      c(var0);
   }

   public static void d(BiomeSettingsMobs.a var0) {
      a(var0);
      var0.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.Y, 5, 2, 6));
      var0.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.w, 1, 1, 3));
      c(var0);
   }

   public static void e(BiomeSettingsMobs.a var0) {
      var0.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aC, 10, 2, 3));
      var0.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.az, 1, 1, 2));
      b(var0);
      a(var0, 95, 5, 20, false);
      var0.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.aU, 80, 4, 4));
   }

   public static void f(BiomeSettingsMobs.a var0) {
      var0.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.aC, 4, 2, 3));
      b(var0);
      a(var0, 19, 1, 100, false);
      var0.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.Z, 80, 4, 4));
   }

   public static void g(BiomeSettingsMobs.a var0) {
      b(var0);
      int var1 = 95;
      a(var0, 95, 5, 100, false);
      var0.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.y, 95, 4, 4));
   }

   public static void a(BiomeSettingsMobs.a var0, int var1, int var2, int var3, boolean var4) {
      var0.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.aS, 100, 4, 4));
      var0.a(EnumCreatureType.a, new BiomeSettingsMobs.c(var4 ? EntityTypes.y : EntityTypes.bp, var1, 4, 4));
      var0.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.br, var2, 1, 1));
      var0.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.aJ, var3, 4, 4));
      var0.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.u, 100, 4, 4));
      var0.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.aL, 100, 4, 4));
      var0.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.E, 10, 1, 4));
      var0.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.bj, 5, 1, 1));
   }

   public static void h(BiomeSettingsMobs.a var0) {
      var0.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.ao, 8, 4, 8));
      b(var0);
   }

   public static void i(BiomeSettingsMobs.a var0) {
      a(var0);
      var0.a(EnumCreatureType.b, new BiomeSettingsMobs.c(EntityTypes.q, 10, 4, 4));
      c(var0);
   }

   public static void j(BiomeSettingsMobs.a var0) {
      var0.a(EnumCreatureType.a, new BiomeSettingsMobs.c(EntityTypes.E, 10, 4, 4));
   }
}
