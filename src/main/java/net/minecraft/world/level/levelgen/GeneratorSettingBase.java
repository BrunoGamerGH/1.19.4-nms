package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public record GeneratorSettingBase(
   NoiseSettings noiseSettings,
   IBlockData defaultBlock,
   IBlockData defaultFluid,
   NoiseRouter noiseRouter,
   SurfaceRules.o surfaceRule,
   List<Climate.d> spawnTarget,
   int seaLevel,
   boolean disableMobGeneration,
   boolean aquifersEnabled,
   boolean oreVeinsEnabled,
   boolean useLegacyRandomSource
) {
   private final NoiseSettings j;
   private final IBlockData k;
   private final IBlockData l;
   private final NoiseRouter m;
   private final SurfaceRules.o n;
   private final List<Climate.d> o;
   private final int p;
   private final boolean q;
   private final boolean r;
   private final boolean s;
   private final boolean t;
   public static final Codec<GeneratorSettingBase> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               NoiseSettings.a.fieldOf("noise").forGetter(GeneratorSettingBase::f),
               IBlockData.b.fieldOf("default_block").forGetter(GeneratorSettingBase::g),
               IBlockData.b.fieldOf("default_fluid").forGetter(GeneratorSettingBase::h),
               NoiseRouter.a.fieldOf("noise_router").forGetter(GeneratorSettingBase::i),
               SurfaceRules.o.b.fieldOf("surface_rule").forGetter(GeneratorSettingBase::j),
               Climate.d.a.listOf().fieldOf("spawn_target").forGetter(GeneratorSettingBase::k),
               Codec.INT.fieldOf("sea_level").forGetter(GeneratorSettingBase::l),
               Codec.BOOL.fieldOf("disable_mob_generation").forGetter(GeneratorSettingBase::a),
               Codec.BOOL.fieldOf("aquifers_enabled").forGetter(GeneratorSettingBase::b),
               Codec.BOOL.fieldOf("ore_veins_enabled").forGetter(GeneratorSettingBase::c),
               Codec.BOOL.fieldOf("legacy_random_source").forGetter(GeneratorSettingBase::n)
            )
            .apply(var0, GeneratorSettingBase::new)
   );
   public static final Codec<Holder<GeneratorSettingBase>> b = RegistryFileCodec.a(Registries.au, a);
   public static final ResourceKey<GeneratorSettingBase> c = ResourceKey.a(Registries.au, new MinecraftKey("overworld"));
   public static final ResourceKey<GeneratorSettingBase> d = ResourceKey.a(Registries.au, new MinecraftKey("large_biomes"));
   public static final ResourceKey<GeneratorSettingBase> e = ResourceKey.a(Registries.au, new MinecraftKey("amplified"));
   public static final ResourceKey<GeneratorSettingBase> f = ResourceKey.a(Registries.au, new MinecraftKey("nether"));
   public static final ResourceKey<GeneratorSettingBase> g = ResourceKey.a(Registries.au, new MinecraftKey("end"));
   public static final ResourceKey<GeneratorSettingBase> h = ResourceKey.a(Registries.au, new MinecraftKey("caves"));
   public static final ResourceKey<GeneratorSettingBase> i = ResourceKey.a(Registries.au, new MinecraftKey("floating_islands"));

   public GeneratorSettingBase(
      NoiseSettings var0,
      IBlockData var1,
      IBlockData var2,
      NoiseRouter var3,
      SurfaceRules.o var4,
      List<Climate.d> var5,
      int var6,
      boolean var7,
      boolean var8,
      boolean var9,
      boolean var10
   ) {
      this.j = var0;
      this.k = var1;
      this.l = var2;
      this.m = var3;
      this.n = var4;
      this.o = var5;
      this.p = var6;
      this.q = var7;
      this.r = var8;
      this.s = var9;
      this.t = var10;
   }

   @Deprecated
   public boolean a() {
      return this.q;
   }

   public boolean b() {
      return this.r;
   }

   public boolean c() {
      return this.s;
   }

   public SeededRandom.a d() {
      return this.t ? SeededRandom.a.a : SeededRandom.a.b;
   }

   public static void a(BootstapContext<GeneratorSettingBase> var0) {
      var0.a(c, a(var0, false, false));
      var0.a(d, a(var0, false, true));
      var0.a(e, a(var0, true, false));
      var0.a(f, c(var0));
      var0.a(g, b(var0));
      var0.a(h, d(var0));
      var0.a(i, e(var0));
   }

   private static GeneratorSettingBase b(BootstapContext<?> var0) {
      return new GeneratorSettingBase(
         NoiseSettings.d, Blocks.fy.o(), Blocks.a.o(), NoiseRouterData.a(var0.a(Registries.ar)), SurfaceRuleData.c(), List.of(), 0, true, false, false, true
      );
   }

   private static GeneratorSettingBase c(BootstapContext<?> var0) {
      return new GeneratorSettingBase(
         NoiseSettings.c,
         Blocks.dV.o(),
         Blocks.H.o(),
         NoiseRouterData.a(var0.a(Registries.ar), var0.a(Registries.av)),
         SurfaceRuleData.b(),
         List.of(),
         32,
         false,
         false,
         false,
         true
      );
   }

   private static GeneratorSettingBase a(BootstapContext<?> var0, boolean var1, boolean var2) {
      return new GeneratorSettingBase(
         NoiseSettings.b,
         Blocks.b.o(),
         Blocks.G.o(),
         NoiseRouterData.a(var0.a(Registries.ar), var0.a(Registries.av), var2, var1),
         SurfaceRuleData.a(),
         new OverworldBiomeBuilder().a(),
         63,
         false,
         true,
         true,
         false
      );
   }

   private static GeneratorSettingBase d(BootstapContext<?> var0) {
      return new GeneratorSettingBase(
         NoiseSettings.e,
         Blocks.b.o(),
         Blocks.G.o(),
         NoiseRouterData.b(var0.a(Registries.ar), var0.a(Registries.av)),
         SurfaceRuleData.a(false, true, true),
         List.of(),
         32,
         false,
         false,
         false,
         true
      );
   }

   private static GeneratorSettingBase e(BootstapContext<?> var0) {
      return new GeneratorSettingBase(
         NoiseSettings.f,
         Blocks.b.o(),
         Blocks.G.o(),
         NoiseRouterData.c(var0.a(Registries.ar), var0.a(Registries.av)),
         SurfaceRuleData.a(false, false, false),
         List.of(),
         -64,
         false,
         false,
         false,
         true
      );
   }

   public static GeneratorSettingBase e() {
      return new GeneratorSettingBase(
         NoiseSettings.b, Blocks.b.o(), Blocks.a.o(), NoiseRouterData.a(), SurfaceRuleData.d(), List.of(), 63, true, false, false, false
      );
   }

   public NoiseSettings f() {
      return this.j;
   }

   public IBlockData g() {
      return this.k;
   }

   public IBlockData h() {
      return this.l;
   }

   public NoiseRouter i() {
      return this.m;
   }

   public SurfaceRules.o j() {
      return this.n;
   }

   public List<Climate.d> k() {
      return this.o;
   }

   public int l() {
      return this.p;
   }

   public boolean m() {
      return this.r;
   }

   public boolean n() {
      return this.t;
   }
}
