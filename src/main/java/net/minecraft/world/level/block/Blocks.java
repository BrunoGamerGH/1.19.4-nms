package net.minecraft.world.level.block;

import com.google.common.collect.UnmodifiableIterator;
import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.IRegistry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.particles.Particles;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityShulkerBox;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.grower.CherryTreeGrower;
import net.minecraft.world.level.block.grower.WorldGenMegaTreeProviderDarkOak;
import net.minecraft.world.level.block.grower.WorldGenMegaTreeProviderJungle;
import net.minecraft.world.level.block.grower.WorldGenTreeProviderAcacia;
import net.minecraft.world.level.block.grower.WorldGenTreeProviderBirch;
import net.minecraft.world.level.block.grower.WorldGenTreeProviderOak;
import net.minecraft.world.level.block.grower.WorldGenTreeProviderSpruce;
import net.minecraft.world.level.block.piston.BlockPiston;
import net.minecraft.world.level.block.piston.BlockPistonExtension;
import net.minecraft.world.level.block.piston.BlockPistonMoving;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyBedPart;
import net.minecraft.world.level.block.state.properties.BlockPropertyWood;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialMapColor;

public class Blocks {
   public static final Block a = a("air", new BlockAir(BlockBase.Info.a(Material.a).a().f().g()));
   public static final Block b = a("stone", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.l).h().a(1.5F, 6.0F)));
   public static final Block c = a("granite", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.k).h().a(1.5F, 6.0F)));
   public static final Block d = a("polished_granite", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.k).h().a(1.5F, 6.0F)));
   public static final Block e = a("diorite", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.o).h().a(1.5F, 6.0F)));
   public static final Block f = a("polished_diorite", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.o).h().a(1.5F, 6.0F)));
   public static final Block g = a("andesite", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.l).h().a(1.5F, 6.0F)));
   public static final Block h = a("polished_andesite", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.l).h().a(1.5F, 6.0F)));
   public static final Block i = a("grass_block", new BlockGrass(BlockBase.Info.a(Material.u).d().d(0.6F).a(SoundEffectType.c)));
   public static final Block j = a("dirt", new Block(BlockBase.Info.a(Material.t, MaterialMapColor.k).d(0.5F).a(SoundEffectType.b)));
   public static final Block k = a("coarse_dirt", new Block(BlockBase.Info.a(Material.t, MaterialMapColor.k).d(0.5F).a(SoundEffectType.b)));
   public static final Block l = a("podzol", new BlockDirtSnow(BlockBase.Info.a(Material.t, MaterialMapColor.I).d(0.5F).a(SoundEffectType.b)));
   public static final Block m = a("cobblestone", new Block(BlockBase.Info.a(Material.J).h().a(2.0F, 6.0F)));
   public static final Block n = a("oak_planks", new Block(BlockBase.Info.a(Material.z, MaterialMapColor.n).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block o = a("spruce_planks", new Block(BlockBase.Info.a(Material.z, MaterialMapColor.I).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block p = a("birch_planks", new Block(BlockBase.Info.a(Material.z, MaterialMapColor.c).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block q = a("jungle_planks", new Block(BlockBase.Info.a(Material.z, MaterialMapColor.k).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block r = a("acacia_planks", new Block(BlockBase.Info.a(Material.z, MaterialMapColor.p).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block s = a(
      "cherry_planks", new Block(BlockBase.Info.a(Material.z, MaterialMapColor.K).a(2.0F, 3.0F).a(SoundEffectType.aP).a(FeatureFlags.c))
   );
   public static final Block t = a("dark_oak_planks", new Block(BlockBase.Info.a(Material.z, MaterialMapColor.A).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block u = a("mangrove_planks", new Block(BlockBase.Info.a(Material.z, MaterialMapColor.C).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block v = a(
      "bamboo_planks", new Block(BlockBase.Info.a(Material.z, MaterialMapColor.s).a(2.0F, 3.0F).a(SoundEffectType.aN).a(FeatureFlags.c))
   );
   public static final Block w = a(
      "bamboo_mosaic", new Block(BlockBase.Info.a(Material.z, MaterialMapColor.s).a(2.0F, 3.0F).a(SoundEffectType.aN).a(FeatureFlags.c))
   );
   public static final Block x = a(
      "oak_sapling", new BlockSapling(new WorldGenTreeProviderOak(), BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.c))
   );
   public static final Block y = a(
      "spruce_sapling", new BlockSapling(new WorldGenTreeProviderSpruce(), BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.c))
   );
   public static final Block z = a(
      "birch_sapling", new BlockSapling(new WorldGenTreeProviderBirch(), BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.c))
   );
   public static final Block A = a(
      "jungle_sapling", new BlockSapling(new WorldGenMegaTreeProviderJungle(), BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.c))
   );
   public static final Block B = a(
      "acacia_sapling", new BlockSapling(new WorldGenTreeProviderAcacia(), BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.c))
   );
   public static final Block C = a(
      "cherry_sapling",
      new BlockSapling(new CherryTreeGrower(), BlockBase.Info.a(Material.e, MaterialMapColor.u).a().d().c().a(SoundEffectType.aQ).a(FeatureFlags.c))
   );
   public static final Block D = a(
      "dark_oak_sapling", new BlockSapling(new WorldGenMegaTreeProviderDarkOak(), BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.c))
   );
   public static final Block E = a(
      "mangrove_propagule", new MangrovePropaguleBlock(BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block F = a("bedrock", new Block(BlockBase.Info.a(Material.J).a(-1.0F, 3600000.0F).f().a(Blocks::a)));
   public static final Block G = a("water", new BlockFluids(FluidTypes.c, BlockBase.Info.a(Material.j).a().d(100.0F).f()));
   public static final Block H = a("lava", new BlockFluids(FluidTypes.e, BlockBase.Info.a(Material.l).a().d().d(100.0F).a(var0 -> 15).f()));
   public static final Block I = a("sand", new BlockSand(14406560, BlockBase.Info.a(Material.w, MaterialMapColor.c).d(0.5F).a(SoundEffectType.i)));
   public static final Block J = a(
      "suspicious_sand", new SuspiciousSandBlock(BlockBase.Info.a(Material.w, MaterialMapColor.c).d(0.25F).a(SoundEffectType.aU).a(FeatureFlags.c))
   );
   public static final Block K = a("red_sand", new BlockSand(11098145, BlockBase.Info.a(Material.w, MaterialMapColor.p).d(0.5F).a(SoundEffectType.i)));
   public static final Block L = a("gravel", new BlockGravel(BlockBase.Info.a(Material.w, MaterialMapColor.l).d(0.6F).a(SoundEffectType.b)));
   public static final Block M = a("gold_ore", new DropExperienceBlock(BlockBase.Info.a(Material.J).h().a(3.0F, 3.0F)));
   public static final Block N = a(
      "deepslate_gold_ore", new DropExperienceBlock(BlockBase.Info.a((BlockBase)M).a(MaterialMapColor.ah).a(4.5F, 3.0F).a(SoundEffectType.az))
   );
   public static final Block O = a("iron_ore", new DropExperienceBlock(BlockBase.Info.a(Material.J).h().a(3.0F, 3.0F)));
   public static final Block P = a(
      "deepslate_iron_ore", new DropExperienceBlock(BlockBase.Info.a((BlockBase)O).a(MaterialMapColor.ah).a(4.5F, 3.0F).a(SoundEffectType.az))
   );
   public static final Block Q = a("coal_ore", new DropExperienceBlock(BlockBase.Info.a(Material.J).h().a(3.0F, 3.0F), UniformInt.a(0, 2)));
   public static final Block R = a(
      "deepslate_coal_ore",
      new DropExperienceBlock(BlockBase.Info.a((BlockBase)Q).a(MaterialMapColor.ah).a(4.5F, 3.0F).a(SoundEffectType.az), UniformInt.a(0, 2))
   );
   public static final Block S = a(
      "nether_gold_ore", new DropExperienceBlock(BlockBase.Info.a(Material.J, MaterialMapColor.J).h().a(3.0F, 3.0F).a(SoundEffectType.U), UniformInt.a(0, 1))
   );
   public static final Block T = a("oak_log", a(MaterialMapColor.n, MaterialMapColor.I));
   public static final Block U = a("spruce_log", a(MaterialMapColor.I, MaterialMapColor.A));
   public static final Block V = a("birch_log", a(MaterialMapColor.c, MaterialMapColor.o));
   public static final Block W = a("jungle_log", a(MaterialMapColor.k, MaterialMapColor.I));
   public static final Block X = a("acacia_log", a(MaterialMapColor.p, MaterialMapColor.l));
   public static final Block Y = a("cherry_log", a(MaterialMapColor.K, MaterialMapColor.R, SoundEffectType.aP, FeatureFlags.c));
   public static final Block Z = a("dark_oak_log", a(MaterialMapColor.A, MaterialMapColor.A));
   public static final Block aa = a("mangrove_log", a(MaterialMapColor.C, MaterialMapColor.I));
   public static final Block ab = a(
      "mangrove_roots",
      new MangroveRootsBlock(BlockBase.Info.a(Material.z, MaterialMapColor.I).d(0.7F).d().a(SoundEffectType.aF).b().b(Blocks::b).c(Blocks::b).b())
   );
   public static final Block ac = a("muddy_mangrove_roots", new BlockRotatable(BlockBase.Info.a(Material.t, MaterialMapColor.I).d(0.7F).a(SoundEffectType.aG)));
   public static final Block ad = a("bamboo_block", a(MaterialMapColor.s, MaterialMapColor.h, SoundEffectType.aN, FeatureFlags.c));
   public static final Block ae = a("stripped_spruce_log", a(MaterialMapColor.I, MaterialMapColor.I));
   public static final Block af = a("stripped_birch_log", a(MaterialMapColor.c, MaterialMapColor.c));
   public static final Block ag = a("stripped_jungle_log", a(MaterialMapColor.k, MaterialMapColor.k));
   public static final Block ah = a("stripped_acacia_log", a(MaterialMapColor.p, MaterialMapColor.p));
   public static final Block ai = a("stripped_cherry_log", a(MaterialMapColor.K, MaterialMapColor.Q, SoundEffectType.aP, FeatureFlags.c));
   public static final Block aj = a("stripped_dark_oak_log", a(MaterialMapColor.A, MaterialMapColor.A));
   public static final Block ak = a("stripped_oak_log", a(MaterialMapColor.n, MaterialMapColor.n));
   public static final Block al = a("stripped_mangrove_log", a(MaterialMapColor.C, MaterialMapColor.C));
   public static final Block am = a("stripped_bamboo_block", a(MaterialMapColor.s, MaterialMapColor.s, SoundEffectType.aN, FeatureFlags.c));
   public static final Block an = a("oak_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.n).d(2.0F).a(SoundEffectType.a)));
   public static final Block ao = a("spruce_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.I).d(2.0F).a(SoundEffectType.a)));
   public static final Block ap = a("birch_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.c).d(2.0F).a(SoundEffectType.a)));
   public static final Block aq = a("jungle_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.k).d(2.0F).a(SoundEffectType.a)));
   public static final Block ar = a("acacia_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.v).d(2.0F).a(SoundEffectType.a)));
   public static final Block as = a(
      "cherry_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.R).d(2.0F).a(SoundEffectType.aP).a(FeatureFlags.c))
   );
   public static final Block at = a("dark_oak_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.A).d(2.0F).a(SoundEffectType.a)));
   public static final Block au = a("mangrove_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.C).d(2.0F).a(SoundEffectType.a)));
   public static final Block av = a("stripped_oak_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.n).d(2.0F).a(SoundEffectType.a)));
   public static final Block aw = a("stripped_spruce_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.I).d(2.0F).a(SoundEffectType.a)));
   public static final Block ax = a("stripped_birch_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.c).d(2.0F).a(SoundEffectType.a)));
   public static final Block ay = a("stripped_jungle_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.k).d(2.0F).a(SoundEffectType.a)));
   public static final Block az = a("stripped_acacia_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.p).d(2.0F).a(SoundEffectType.a)));
   public static final Block aA = a(
      "stripped_cherry_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.Q).d(2.0F).a(SoundEffectType.aP).a(FeatureFlags.c))
   );
   public static final Block aB = a(
      "stripped_dark_oak_wood", new BlockRotatable(BlockBase.Info.a(Material.z, MaterialMapColor.A).d(2.0F).a(SoundEffectType.a))
   );
   public static final Block aC = a("stripped_mangrove_wood", a(MaterialMapColor.C, MaterialMapColor.C));
   public static final Block aD = a("oak_leaves", a(SoundEffectType.c));
   public static final Block aE = a("spruce_leaves", a(SoundEffectType.c));
   public static final Block aF = a("birch_leaves", a(SoundEffectType.c));
   public static final Block aG = a("jungle_leaves", a(SoundEffectType.c));
   public static final Block aH = a("acacia_leaves", a(SoundEffectType.c));
   public static final Block aI = a(
      "cherry_leaves",
      new CherryLeavesBlock(
         BlockBase.Info.a(Material.F, MaterialMapColor.u).d(0.2F).d().a(SoundEffectType.aR).b().a(Blocks::c).b(Blocks::b).c(Blocks::b).a(FeatureFlags.c)
      )
   );
   public static final Block aJ = a("dark_oak_leaves", a(SoundEffectType.c));
   public static final Block aK = a(
      "mangrove_leaves", new MangroveLeavesBlock(BlockBase.Info.a(Material.F).d(0.2F).d().a(SoundEffectType.c).b().a(Blocks::c).b(Blocks::b).c(Blocks::b))
   );
   public static final Block aL = a("azalea_leaves", a(SoundEffectType.as));
   public static final Block aM = a("flowering_azalea_leaves", a(SoundEffectType.as));
   public static final Block aN = a("sponge", new BlockSponge(BlockBase.Info.a(Material.x).d(0.6F).a(SoundEffectType.c)));
   public static final Block aO = a("wet_sponge", new BlockWetSponge(BlockBase.Info.a(Material.x).d(0.6F).a(SoundEffectType.c)));
   public static final Block aP = a(
      "glass", new BlockGlass(BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b().a(Blocks::a).a(Blocks::b).b(Blocks::b).c(Blocks::b))
   );
   public static final Block aQ = a("lapis_ore", new DropExperienceBlock(BlockBase.Info.a(Material.J).h().a(3.0F, 3.0F), UniformInt.a(2, 5)));
   public static final Block aR = a(
      "deepslate_lapis_ore",
      new DropExperienceBlock(BlockBase.Info.a((BlockBase)aQ).a(MaterialMapColor.ah).a(4.5F, 3.0F).a(SoundEffectType.az), UniformInt.a(2, 5))
   );
   public static final Block aS = a("lapis_block", new Block(BlockBase.Info.a(Material.K, MaterialMapColor.G).h().a(3.0F, 3.0F)));
   public static final Block aT = a("dispenser", new BlockDispenser(BlockBase.Info.a(Material.J).h().d(3.5F)));
   public static final Block aU = a("sandstone", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.c).h().d(0.8F)));
   public static final Block aV = a("chiseled_sandstone", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.c).h().d(0.8F)));
   public static final Block aW = a("cut_sandstone", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.c).h().d(0.8F)));
   public static final Block aX = a("note_block", new BlockNote(BlockBase.Info.a(Material.z).a(SoundEffectType.a).d(0.8F)));
   public static final Block aY = a("white_bed", a(EnumColor.a));
   public static final Block aZ = a("orange_bed", a(EnumColor.b));
   public static final Block ba = a("magenta_bed", a(EnumColor.c));
   public static final Block bb = a("light_blue_bed", a(EnumColor.d));
   public static final Block bc = a("yellow_bed", a(EnumColor.e));
   public static final Block bd = a("lime_bed", a(EnumColor.f));
   public static final Block be = a("pink_bed", a(EnumColor.g));
   public static final Block bf = a("gray_bed", a(EnumColor.h));
   public static final Block bg = a("light_gray_bed", a(EnumColor.i));
   public static final Block bh = a("cyan_bed", a(EnumColor.j));
   public static final Block bi = a("purple_bed", a(EnumColor.k));
   public static final Block bj = a("blue_bed", a(EnumColor.l));
   public static final Block bk = a("brown_bed", a(EnumColor.m));
   public static final Block bl = a("green_bed", a(EnumColor.n));
   public static final Block bm = a("red_bed", a(EnumColor.o));
   public static final Block bn = a("black_bed", a(EnumColor.p));
   public static final Block bo = a("powered_rail", new BlockPoweredRail(BlockBase.Info.a(Material.o).a().d(0.7F).a(SoundEffectType.f)));
   public static final Block bp = a("detector_rail", new BlockMinecartDetector(BlockBase.Info.a(Material.o).a().d(0.7F).a(SoundEffectType.f)));
   public static final Block bq = a("sticky_piston", a(true));
   public static final Block br = a("cobweb", new BlockWeb(BlockBase.Info.a(Material.p).a().h().d(4.0F)));
   public static final Block bs = a("grass", new BlockLongGrass(BlockBase.Info.a(Material.g).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.c)));
   public static final Block bt = a("fern", new BlockLongGrass(BlockBase.Info.a(Material.g).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.c)));
   public static final Block bu = a("dead_bush", new BlockDeadBush(BlockBase.Info.a(Material.g, MaterialMapColor.n).a().c().a(SoundEffectType.c)));
   public static final Block bv = a("seagrass", new SeagrassBlock(BlockBase.Info.a(Material.i).a().c().a(SoundEffectType.p)));
   public static final Block bw = a(
      "tall_seagrass", new TallSeagrassBlock(BlockBase.Info.a(Material.i).a().c().a(SoundEffectType.p).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block bx = a("piston", a(false));
   public static final Block by = a("piston_head", new BlockPistonExtension(BlockBase.Info.a(Material.O).d(1.5F).f()));
   public static final Block bz = a("white_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.i).d(0.8F).a(SoundEffectType.h)));
   public static final Block bA = a("orange_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.p).d(0.8F).a(SoundEffectType.h)));
   public static final Block bB = a("magenta_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.q).d(0.8F).a(SoundEffectType.h)));
   public static final Block bC = a("light_blue_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.r).d(0.8F).a(SoundEffectType.h)));
   public static final Block bD = a("yellow_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.s).d(0.8F).a(SoundEffectType.h)));
   public static final Block bE = a("lime_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.t).d(0.8F).a(SoundEffectType.h)));
   public static final Block bF = a("pink_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.u).d(0.8F).a(SoundEffectType.h)));
   public static final Block bG = a("gray_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.v).d(0.8F).a(SoundEffectType.h)));
   public static final Block bH = a("light_gray_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.w).d(0.8F).a(SoundEffectType.h)));
   public static final Block bI = a("cyan_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.x).d(0.8F).a(SoundEffectType.h)));
   public static final Block bJ = a("purple_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.y).d(0.8F).a(SoundEffectType.h)));
   public static final Block bK = a("blue_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.z).d(0.8F).a(SoundEffectType.h)));
   public static final Block bL = a("brown_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.A).d(0.8F).a(SoundEffectType.h)));
   public static final Block bM = a("green_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.B).d(0.8F).a(SoundEffectType.h)));
   public static final Block bN = a("red_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.C).d(0.8F).a(SoundEffectType.h)));
   public static final Block bO = a("black_wool", new Block(BlockBase.Info.a(Material.D, MaterialMapColor.D).d(0.8F).a(SoundEffectType.h)));
   public static final Block bP = a(
      "moving_piston", new BlockPistonMoving(BlockBase.Info.a(Material.O).d(-1.0F).e().f().b().a(Blocks::b).b(Blocks::b).c(Blocks::b))
   );
   public static final Block bQ = a(
      "dandelion", new BlockFlowers(MobEffects.w, 7, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block bR = a(
      "torchflower",
      new BlockFlowers(MobEffects.p, 5, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b).a(FeatureFlags.c))
   );
   public static final Block bS = a(
      "poppy", new BlockFlowers(MobEffects.p, 5, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block bT = a(
      "blue_orchid", new BlockFlowers(MobEffects.w, 7, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block bU = a(
      "allium", new BlockFlowers(MobEffects.l, 4, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block bV = a(
      "azure_bluet", new BlockFlowers(MobEffects.o, 8, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block bW = a(
      "red_tulip", new BlockFlowers(MobEffects.r, 9, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block bX = a(
      "orange_tulip", new BlockFlowers(MobEffects.r, 9, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block bY = a(
      "white_tulip", new BlockFlowers(MobEffects.r, 9, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block bZ = a(
      "pink_tulip", new BlockFlowers(MobEffects.r, 9, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block ca = a(
      "oxeye_daisy", new BlockFlowers(MobEffects.j, 8, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block cb = a(
      "cornflower", new BlockFlowers(MobEffects.h, 6, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block cc = a(
      "wither_rose", new BlockWitherRose(MobEffects.t, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block cd = a(
      "lily_of_the_valley", new BlockFlowers(MobEffects.s, 12, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block ce = a(
      "brown_mushroom",
      new BlockMushroom(BlockBase.Info.a(Material.e, MaterialMapColor.A).a().d().c().a(SoundEffectType.c).a(var0 -> 1).d(Blocks::a), TreeFeatures.e)
   );
   public static final Block cf = a(
      "red_mushroom", new BlockMushroom(BlockBase.Info.a(Material.e, MaterialMapColor.C).a().d().c().a(SoundEffectType.c).d(Blocks::a), TreeFeatures.f)
   );
   public static final Block cg = a("gold_block", new Block(BlockBase.Info.a(Material.K, MaterialMapColor.E).h().a(3.0F, 6.0F).a(SoundEffectType.f)));
   public static final Block ch = a("iron_block", new Block(BlockBase.Info.a(Material.K, MaterialMapColor.g).h().a(5.0F, 6.0F).a(SoundEffectType.f)));
   public static final Block ci = a("bricks", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.C).h().a(2.0F, 6.0F)));
   public static final Block cj = a("tnt", new BlockTNT(BlockBase.Info.a(Material.E).c().a(SoundEffectType.c)));
   public static final Block ck = a("bookshelf", new Block(BlockBase.Info.a(Material.z).d(1.5F).a(SoundEffectType.a)));
   public static final Block cl = a(
      "chiseled_bookshelf", new ChiseledBookShelfBlock(BlockBase.Info.a(Material.z).d(1.5F).a(SoundEffectType.aT).a(FeatureFlags.c))
   );
   public static final Block cm = a("mossy_cobblestone", new Block(BlockBase.Info.a(Material.J).h().a(2.0F, 6.0F)));
   public static final Block cn = a("obsidian", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.D).h().a(50.0F, 1200.0F)));
   public static final Block co = a("torch", new BlockTorch(BlockBase.Info.a(Material.o).a().c().a(var0 -> 14).a(SoundEffectType.a), Particles.C));
   public static final Block cp = a(
      "wall_torch", new BlockTorchWall(BlockBase.Info.a(Material.o).a().c().a(var0 -> 14).a(SoundEffectType.a).a(co), Particles.C)
   );
   public static final Block cq = a("fire", new BlockFire(BlockBase.Info.a(Material.n, MaterialMapColor.e).a().c().a(var0 -> 15).a(SoundEffectType.h)));
   public static final Block cr = a(
      "soul_fire", new BlockSoulFire(BlockBase.Info.a(Material.n, MaterialMapColor.r).a().c().a(var0 -> 10).a(SoundEffectType.h))
   );
   public static final Block cs = a("spawner", new BlockMobSpawner(BlockBase.Info.a(Material.J).h().d(5.0F).a(SoundEffectType.f).b()));
   public static final Block ct = a("oak_stairs", new BlockStairs(n.o(), BlockBase.Info.a((BlockBase)n)));
   public static final Block cu = a("chest", new BlockChest(BlockBase.Info.a(Material.z).d(2.5F).a(SoundEffectType.a), () -> TileEntityTypes.b));
   public static final Block cv = a("redstone_wire", new BlockRedstoneWire(BlockBase.Info.a(Material.o).a().c()));
   public static final Block cw = a("diamond_ore", new DropExperienceBlock(BlockBase.Info.a(Material.J).h().a(3.0F, 3.0F), UniformInt.a(3, 7)));
   public static final Block cx = a(
      "deepslate_diamond_ore",
      new DropExperienceBlock(BlockBase.Info.a((BlockBase)cw).a(MaterialMapColor.ah).a(4.5F, 3.0F).a(SoundEffectType.az), UniformInt.a(3, 7))
   );
   public static final Block cy = a("diamond_block", new Block(BlockBase.Info.a(Material.K, MaterialMapColor.F).h().a(5.0F, 6.0F).a(SoundEffectType.f)));
   public static final Block cz = a("crafting_table", new BlockWorkbench(BlockBase.Info.a(Material.z).d(2.5F).a(SoundEffectType.a)));
   public static final Block cA = a("wheat", new BlockCrops(BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.v)));
   public static final Block cB = a("farmland", new BlockSoil(BlockBase.Info.a(Material.t).d().d(0.6F).a(SoundEffectType.b).c(Blocks::a).b(Blocks::a)));
   public static final Block cC = a("furnace", new BlockFurnaceFurace(BlockBase.Info.a(Material.J).h().d(3.5F).a(a(13))));
   public static final Block cD = a("oak_sign", new BlockFloorSign(BlockBase.Info.a(Material.z).a().d(1.0F), BlockPropertyWood.a));
   public static final Block cE = a("spruce_sign", new BlockFloorSign(BlockBase.Info.a(Material.z, U.t()).a().d(1.0F), BlockPropertyWood.b));
   public static final Block cF = a("birch_sign", new BlockFloorSign(BlockBase.Info.a(Material.z, MaterialMapColor.c).a().d(1.0F), BlockPropertyWood.c));
   public static final Block cG = a("acacia_sign", new BlockFloorSign(BlockBase.Info.a(Material.z, MaterialMapColor.p).a().d(1.0F), BlockPropertyWood.d));
   public static final Block cH = a("cherry_sign", new BlockFloorSign(BlockBase.Info.a(Material.z, s.t()).a().d(1.0F).a(FeatureFlags.c), BlockPropertyWood.e));
   public static final Block cI = a("jungle_sign", new BlockFloorSign(BlockBase.Info.a(Material.z, W.t()).a().d(1.0F), BlockPropertyWood.f));
   public static final Block cJ = a("dark_oak_sign", new BlockFloorSign(BlockBase.Info.a(Material.z, Z.t()).a().d(1.0F), BlockPropertyWood.g));
   public static final Block cK = a("mangrove_sign", new BlockFloorSign(BlockBase.Info.a(Material.z, aa.t()).a().d(1.0F), BlockPropertyWood.j));
   public static final Block cL = a("bamboo_sign", new BlockFloorSign(BlockBase.Info.a(Material.z, v.t()).a().d(1.0F).a(FeatureFlags.c), BlockPropertyWood.k));
   public static final Block cM = a("oak_door", new BlockDoor(BlockBase.Info.a(Material.z, n.t()).d(3.0F).b(), BlockSetType.e));
   public static final Block cN = a("ladder", new BlockLadder(BlockBase.Info.a(Material.o).d(0.4F).a(SoundEffectType.l).b()));
   public static final Block cO = a("rail", new BlockMinecartTrack(BlockBase.Info.a(Material.o).a().d(0.7F).a(SoundEffectType.f)));
   public static final Block cP = a("cobblestone_stairs", new BlockStairs(m.o(), BlockBase.Info.a((BlockBase)m)));
   public static final Block cQ = a("oak_wall_sign", new BlockWallSign(BlockBase.Info.a(Material.z).a().d(1.0F).a(cD), BlockPropertyWood.a));
   public static final Block cR = a("spruce_wall_sign", new BlockWallSign(BlockBase.Info.a(Material.z, U.t()).a().d(1.0F).a(cE), BlockPropertyWood.b));
   public static final Block cS = a(
      "birch_wall_sign", new BlockWallSign(BlockBase.Info.a(Material.z, MaterialMapColor.c).a().d(1.0F).a(cF), BlockPropertyWood.c)
   );
   public static final Block cT = a(
      "acacia_wall_sign", new BlockWallSign(BlockBase.Info.a(Material.z, MaterialMapColor.p).a().d(1.0F).a(cG), BlockPropertyWood.d)
   );
   public static final Block cU = a(
      "cherry_wall_sign", new BlockWallSign(BlockBase.Info.a(Material.z, Y.t()).a().d(1.0F).a(cH).a(FeatureFlags.c), BlockPropertyWood.e)
   );
   public static final Block cV = a("jungle_wall_sign", new BlockWallSign(BlockBase.Info.a(Material.z, W.t()).a().d(1.0F).a(cI), BlockPropertyWood.f));
   public static final Block cW = a("dark_oak_wall_sign", new BlockWallSign(BlockBase.Info.a(Material.z, Z.t()).a().d(1.0F).a(cJ), BlockPropertyWood.g));
   public static final Block cX = a("mangrove_wall_sign", new BlockWallSign(BlockBase.Info.a(Material.z, aa.t()).a().d(1.0F).a(cK), BlockPropertyWood.j));
   public static final Block cY = a(
      "bamboo_wall_sign", new BlockWallSign(BlockBase.Info.a(Material.z, v.t()).a().d(1.0F).a(FeatureFlags.c).a(cL), BlockPropertyWood.k)
   );
   public static final Block cZ = a(
      "oak_hanging_sign", new CeilingHangingSignBlock(BlockBase.Info.a(Material.z, T.t()).a().d(1.0F).a(FeatureFlags.c), BlockPropertyWood.a)
   );
   public static final Block da = a(
      "spruce_hanging_sign", new CeilingHangingSignBlock(BlockBase.Info.a(Material.z, U.t()).a().d(1.0F).a(FeatureFlags.c), BlockPropertyWood.b)
   );
   public static final Block db = a(
      "birch_hanging_sign", new CeilingHangingSignBlock(BlockBase.Info.a(Material.z, MaterialMapColor.c).a().d(1.0F).a(FeatureFlags.c), BlockPropertyWood.c)
   );
   public static final Block dc = a(
      "acacia_hanging_sign", new CeilingHangingSignBlock(BlockBase.Info.a(Material.z, MaterialMapColor.p).a().d(1.0F).a(FeatureFlags.c), BlockPropertyWood.d)
   );
   public static final Block dd = a(
      "cherry_hanging_sign", new CeilingHangingSignBlock(BlockBase.Info.a(Material.z, MaterialMapColor.Q).a().d(1.0F).a(FeatureFlags.c), BlockPropertyWood.e)
   );
   public static final Block de = a(
      "jungle_hanging_sign", new CeilingHangingSignBlock(BlockBase.Info.a(Material.z, W.t()).a().d(1.0F).a(FeatureFlags.c), BlockPropertyWood.f)
   );
   public static final Block df = a(
      "dark_oak_hanging_sign", new CeilingHangingSignBlock(BlockBase.Info.a(Material.z, Z.t()).a().d(1.0F).a(FeatureFlags.c), BlockPropertyWood.g)
   );
   public static final Block dg = a(
      "crimson_hanging_sign",
      new CeilingHangingSignBlock(BlockBase.Info.a(Material.z, MaterialMapColor.ab).a().d(1.0F).a(FeatureFlags.c), BlockPropertyWood.h)
   );
   public static final Block dh = a(
      "warped_hanging_sign", new CeilingHangingSignBlock(BlockBase.Info.a(Material.z, MaterialMapColor.ae).a().d(1.0F).a(FeatureFlags.c), BlockPropertyWood.i)
   );
   public static final Block di = a(
      "mangrove_hanging_sign", new CeilingHangingSignBlock(BlockBase.Info.a(Material.z, aa.t()).a().d(1.0F).a(FeatureFlags.c), BlockPropertyWood.j)
   );
   public static final Block dj = a(
      "bamboo_hanging_sign", new CeilingHangingSignBlock(BlockBase.Info.a(Material.z, MaterialMapColor.s).a().d(1.0F).a(FeatureFlags.c), BlockPropertyWood.k)
   );
   public static final Block dk = a(
      "oak_wall_hanging_sign", new WallHangingSignBlock(BlockBase.Info.a(Material.z, T.t()).a().d(1.0F).a(FeatureFlags.c).a(cZ), BlockPropertyWood.a)
   );
   public static final Block dl = a(
      "spruce_wall_hanging_sign", new WallHangingSignBlock(BlockBase.Info.a(Material.z).a().d(1.0F).a(da).a(FeatureFlags.c), BlockPropertyWood.b)
   );
   public static final Block dm = a(
      "birch_wall_hanging_sign", new WallHangingSignBlock(BlockBase.Info.a(Material.w).a().d(1.0F).a(db).a(FeatureFlags.c), BlockPropertyWood.c)
   );
   public static final Block dn = a(
      "acacia_wall_hanging_sign",
      new WallHangingSignBlock(BlockBase.Info.a(Material.z, MaterialMapColor.p).a().d(1.0F).a(dc).a(FeatureFlags.c), BlockPropertyWood.d)
   );
   public static final Block do = a(
      "cherry_wall_hanging_sign",
      new WallHangingSignBlock(BlockBase.Info.a(Material.z, MaterialMapColor.Q).a().d(1.0F).a(dd).a(FeatureFlags.c), BlockPropertyWood.e)
   );
   public static final Block dp = a(
      "jungle_wall_hanging_sign", new WallHangingSignBlock(BlockBase.Info.a(Material.z, W.t()).a().d(1.0F).a(de).a(FeatureFlags.c), BlockPropertyWood.f)
   );
   public static final Block dq = a(
      "dark_oak_wall_hanging_sign", new WallHangingSignBlock(BlockBase.Info.a(Material.z, Z.t()).a().d(1.0F).a(df).a(FeatureFlags.c), BlockPropertyWood.g)
   );
   public static final Block dr = a(
      "mangrove_wall_hanging_sign", new WallHangingSignBlock(BlockBase.Info.a(Material.z, aa.t()).a().d(1.0F).a(di).a(FeatureFlags.c), BlockPropertyWood.j)
   );
   public static final Block ds = a(
      "crimson_wall_hanging_sign",
      new WallHangingSignBlock(BlockBase.Info.a(Material.z, MaterialMapColor.ab).a().d(1.0F).a(dg).a(FeatureFlags.c), BlockPropertyWood.h)
   );
   public static final Block dt = a(
      "warped_wall_hanging_sign",
      new WallHangingSignBlock(BlockBase.Info.a(Material.z, MaterialMapColor.ae).a().d(1.0F).a(dh).a(FeatureFlags.c), BlockPropertyWood.i)
   );
   public static final Block du = a(
      "bamboo_wall_hanging_sign",
      new WallHangingSignBlock(BlockBase.Info.a(Material.z, MaterialMapColor.s).a().d(1.0F).a(dj).a(FeatureFlags.c), BlockPropertyWood.k)
   );
   public static final Block dv = a("lever", new BlockLever(BlockBase.Info.a(Material.o).a().d(0.5F).a(SoundEffectType.a)));
   public static final Block dw = a(
      "stone_pressure_plate",
      new BlockPressurePlateBinary(BlockPressurePlateBinary.EnumMobType.b, BlockBase.Info.a(Material.J).h().a().d(0.5F), BlockSetType.c)
   );
   public static final Block dx = a("iron_door", new BlockDoor(BlockBase.Info.a(Material.K, MaterialMapColor.g).h().d(5.0F).b(), BlockSetType.a));
   public static final Block dy = a(
      "oak_pressure_plate",
      new BlockPressurePlateBinary(BlockPressurePlateBinary.EnumMobType.a, BlockBase.Info.a(Material.z, n.t()).a().d(0.5F), BlockSetType.e)
   );
   public static final Block dz = a(
      "spruce_pressure_plate",
      new BlockPressurePlateBinary(BlockPressurePlateBinary.EnumMobType.a, BlockBase.Info.a(Material.z, o.t()).a().d(0.5F), BlockSetType.f)
   );
   public static final Block dA = a(
      "birch_pressure_plate",
      new BlockPressurePlateBinary(BlockPressurePlateBinary.EnumMobType.a, BlockBase.Info.a(Material.z, p.t()).a().d(0.5F), BlockSetType.g)
   );
   public static final Block dB = a(
      "jungle_pressure_plate",
      new BlockPressurePlateBinary(BlockPressurePlateBinary.EnumMobType.a, BlockBase.Info.a(Material.z, q.t()).a().d(0.5F), BlockSetType.j)
   );
   public static final Block dC = a(
      "acacia_pressure_plate",
      new BlockPressurePlateBinary(BlockPressurePlateBinary.EnumMobType.a, BlockBase.Info.a(Material.z, r.t()).a().d(0.5F), BlockSetType.h)
   );
   public static final Block dD = a(
      "cherry_pressure_plate",
      new BlockPressurePlateBinary(BlockPressurePlateBinary.EnumMobType.a, BlockBase.Info.a(Material.z, s.t()).a().d(0.5F).a(FeatureFlags.c), BlockSetType.i)
   );
   public static final Block dE = a(
      "dark_oak_pressure_plate",
      new BlockPressurePlateBinary(BlockPressurePlateBinary.EnumMobType.a, BlockBase.Info.a(Material.z, t.t()).a().d(0.5F), BlockSetType.k)
   );
   public static final Block dF = a(
      "mangrove_pressure_plate",
      new BlockPressurePlateBinary(BlockPressurePlateBinary.EnumMobType.a, BlockBase.Info.a(Material.z, u.t()).a().d(0.5F), BlockSetType.n)
   );
   public static final Block dG = a(
      "bamboo_pressure_plate",
      new BlockPressurePlateBinary(BlockPressurePlateBinary.EnumMobType.a, BlockBase.Info.a(Material.z, v.t()).a().d(0.5F).a(FeatureFlags.c), BlockSetType.o)
   );
   public static final Block dH = a("redstone_ore", new BlockRedstoneOre(BlockBase.Info.a(Material.J).h().d().a(a(9)).a(3.0F, 3.0F)));
   public static final Block dI = a(
      "deepslate_redstone_ore", new BlockRedstoneOre(BlockBase.Info.a((BlockBase)dH).a(MaterialMapColor.ah).a(4.5F, 3.0F).a(SoundEffectType.az))
   );
   public static final Block dJ = a("redstone_torch", new BlockRedstoneTorch(BlockBase.Info.a(Material.o).a().c().a(a(7)).a(SoundEffectType.a)));
   public static final Block dK = a("redstone_wall_torch", new BlockRedstoneTorchWall(BlockBase.Info.a(Material.o).a().c().a(a(7)).a(SoundEffectType.a).a(dJ)));
   public static final Block dL = a("stone_button", b());
   public static final Block dM = a(
      "snow", new BlockSnow(BlockBase.Info.a(Material.m).d().d(0.1F).h().a(SoundEffectType.j).c((var0, var1x, var2x) -> var0.c(BlockSnow.b) >= 8))
   );
   public static final Block dN = a(
      "ice",
      new BlockIce(BlockBase.Info.a(Material.H).a(0.98F).d().d(0.5F).a(SoundEffectType.g).b().a((var0, var1x, var2x, var3x) -> var3x == EntityTypes.az))
   );
   public static final Block dO = a("snow_block", new Block(BlockBase.Info.a(Material.L).h().d(0.2F).a(SoundEffectType.j)));
   public static final Block dP = a("cactus", new BlockCactus(BlockBase.Info.a(Material.I).d().d(0.4F).a(SoundEffectType.h)));
   public static final Block dQ = a("clay", new Block(BlockBase.Info.a(Material.s).d(0.6F).a(SoundEffectType.b)));
   public static final Block dR = a("sugar_cane", new BlockReed(BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.c)));
   public static final Block dS = a("jukebox", new BlockJukeBox(BlockBase.Info.a(Material.z, MaterialMapColor.k).a(2.0F, 6.0F)));
   public static final Block dT = a("oak_fence", new BlockFence(BlockBase.Info.a(Material.z, n.t()).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block dU = a("pumpkin", new BlockPumpkin(BlockBase.Info.a(Material.Q, MaterialMapColor.p).d(1.0F).a(SoundEffectType.a)));
   public static final Block dV = a("netherrack", new BlockNetherrack(BlockBase.Info.a(Material.J, MaterialMapColor.J).h().d(0.4F).a(SoundEffectType.L)));
   public static final Block dW = a(
      "soul_sand",
      new BlockSlowSand(
         BlockBase.Info.a(Material.w, MaterialMapColor.A).d(0.5F).b(0.4F).a(SoundEffectType.H).a(Blocks::b).a(Blocks::a).c(Blocks::a).b(Blocks::a)
      )
   );
   public static final Block dX = a("soul_soil", new Block(BlockBase.Info.a(Material.t, MaterialMapColor.A).d(0.5F).a(SoundEffectType.I)));
   public static final Block dY = a("basalt", new BlockRotatable(BlockBase.Info.a(Material.J, MaterialMapColor.D).h().a(1.25F, 4.2F).a(SoundEffectType.J)));
   public static final Block dZ = a(
      "polished_basalt", new BlockRotatable(BlockBase.Info.a(Material.J, MaterialMapColor.D).h().a(1.25F, 4.2F).a(SoundEffectType.J))
   );
   public static final Block ea = a("soul_torch", new BlockTorch(BlockBase.Info.a(Material.o).a().c().a(var0 -> 10).a(SoundEffectType.a), Particles.J));
   public static final Block eb = a(
      "soul_wall_torch", new BlockTorchWall(BlockBase.Info.a(Material.o).a().c().a(var0 -> 10).a(SoundEffectType.a).a(ea), Particles.J)
   );
   public static final Block ec = a("glowstone", new Block(BlockBase.Info.a(Material.G, MaterialMapColor.c).d(0.3F).a(SoundEffectType.g).a(var0 -> 15)));
   public static final Block ed = a("nether_portal", new BlockPortal(BlockBase.Info.a(Material.c).a().d().d(-1.0F).a(SoundEffectType.g).a(var0 -> 11)));
   public static final Block ee = a(
      "carved_pumpkin", new BlockPumpkinCarved(BlockBase.Info.a(Material.Q, MaterialMapColor.p).d(1.0F).a(SoundEffectType.a).a(Blocks::b))
   );
   public static final Block ef = a(
      "jack_o_lantern", new BlockPumpkinCarved(BlockBase.Info.a(Material.Q, MaterialMapColor.p).d(1.0F).a(SoundEffectType.a).a(var0 -> 15).a(Blocks::b))
   );
   public static final Block eg = a("cake", new BlockCake(BlockBase.Info.a(Material.S).d(0.5F).a(SoundEffectType.h)));
   public static final Block eh = a("repeater", new BlockRepeater(BlockBase.Info.a(Material.o).c().a(SoundEffectType.a)));
   public static final Block ei = a("white_stained_glass", b(EnumColor.a));
   public static final Block ej = a("orange_stained_glass", b(EnumColor.b));
   public static final Block ek = a("magenta_stained_glass", b(EnumColor.c));
   public static final Block el = a("light_blue_stained_glass", b(EnumColor.d));
   public static final Block em = a("yellow_stained_glass", b(EnumColor.e));
   public static final Block en = a("lime_stained_glass", b(EnumColor.f));
   public static final Block eo = a("pink_stained_glass", b(EnumColor.g));
   public static final Block ep = a("gray_stained_glass", b(EnumColor.h));
   public static final Block eq = a("light_gray_stained_glass", b(EnumColor.i));
   public static final Block er = a("cyan_stained_glass", b(EnumColor.j));
   public static final Block es = a("purple_stained_glass", b(EnumColor.k));
   public static final Block et = a("blue_stained_glass", b(EnumColor.l));
   public static final Block eu = a("brown_stained_glass", b(EnumColor.m));
   public static final Block ev = a("green_stained_glass", b(EnumColor.n));
   public static final Block ew = a("red_stained_glass", b(EnumColor.o));
   public static final Block ex = a("black_stained_glass", b(EnumColor.p));
   public static final Block ey = a(
      "oak_trapdoor", new BlockTrapdoor(BlockBase.Info.a(Material.z, MaterialMapColor.n).d(3.0F).b().a(Blocks::a), BlockSetType.e)
   );
   public static final Block ez = a(
      "spruce_trapdoor", new BlockTrapdoor(BlockBase.Info.a(Material.z, MaterialMapColor.I).d(3.0F).b().a(Blocks::a), BlockSetType.f)
   );
   public static final Block eA = a(
      "birch_trapdoor", new BlockTrapdoor(BlockBase.Info.a(Material.z, MaterialMapColor.c).d(3.0F).b().a(Blocks::a), BlockSetType.g)
   );
   public static final Block eB = a(
      "jungle_trapdoor", new BlockTrapdoor(BlockBase.Info.a(Material.z, MaterialMapColor.k).d(3.0F).b().a(Blocks::a), BlockSetType.j)
   );
   public static final Block eC = a(
      "acacia_trapdoor", new BlockTrapdoor(BlockBase.Info.a(Material.z, MaterialMapColor.p).d(3.0F).b().a(Blocks::a), BlockSetType.h)
   );
   public static final Block eD = a(
      "cherry_trapdoor", new BlockTrapdoor(BlockBase.Info.a(Material.z, MaterialMapColor.K).d(3.0F).b().a(Blocks::a).a(FeatureFlags.c), BlockSetType.i)
   );
   public static final Block eE = a(
      "dark_oak_trapdoor", new BlockTrapdoor(BlockBase.Info.a(Material.z, MaterialMapColor.A).d(3.0F).b().a(Blocks::a), BlockSetType.k)
   );
   public static final Block eF = a(
      "mangrove_trapdoor", new BlockTrapdoor(BlockBase.Info.a(Material.z, MaterialMapColor.C).d(3.0F).b().a(Blocks::a), BlockSetType.n)
   );
   public static final Block eG = a(
      "bamboo_trapdoor", new BlockTrapdoor(BlockBase.Info.a(Material.z, MaterialMapColor.s).d(3.0F).b().a(Blocks::a).a(FeatureFlags.c), BlockSetType.o)
   );
   public static final Block eH = a("stone_bricks", new Block(BlockBase.Info.a(Material.J).h().a(1.5F, 6.0F)));
   public static final Block eI = a("mossy_stone_bricks", new Block(BlockBase.Info.a(Material.J).h().a(1.5F, 6.0F)));
   public static final Block eJ = a("cracked_stone_bricks", new Block(BlockBase.Info.a(Material.J).h().a(1.5F, 6.0F)));
   public static final Block eK = a("chiseled_stone_bricks", new Block(BlockBase.Info.a(Material.J).h().a(1.5F, 6.0F)));
   public static final Block eL = a("packed_mud", new Block(BlockBase.Info.a((BlockBase)j).a(1.0F, 3.0F).a(SoundEffectType.aJ)));
   public static final Block eM = a("mud_bricks", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.S).h().a(1.5F, 3.0F).a(SoundEffectType.aI)));
   public static final Block eN = a("infested_stone", new BlockMonsterEggs(b, BlockBase.Info.a(Material.s)));
   public static final Block eO = a("infested_cobblestone", new BlockMonsterEggs(m, BlockBase.Info.a(Material.s)));
   public static final Block eP = a("infested_stone_bricks", new BlockMonsterEggs(eH, BlockBase.Info.a(Material.s)));
   public static final Block eQ = a("infested_mossy_stone_bricks", new BlockMonsterEggs(eI, BlockBase.Info.a(Material.s)));
   public static final Block eR = a("infested_cracked_stone_bricks", new BlockMonsterEggs(eJ, BlockBase.Info.a(Material.s)));
   public static final Block eS = a("infested_chiseled_stone_bricks", new BlockMonsterEggs(eK, BlockBase.Info.a(Material.s)));
   public static final Block eT = a(
      "brown_mushroom_block", new BlockHugeMushroom(BlockBase.Info.a(Material.z, MaterialMapColor.k).d(0.2F).a(SoundEffectType.a))
   );
   public static final Block eU = a("red_mushroom_block", new BlockHugeMushroom(BlockBase.Info.a(Material.z, MaterialMapColor.C).d(0.2F).a(SoundEffectType.a)));
   public static final Block eV = a("mushroom_stem", new BlockHugeMushroom(BlockBase.Info.a(Material.z, MaterialMapColor.d).d(0.2F).a(SoundEffectType.a)));
   public static final Block eW = a(
      "iron_bars", new BlockIronBars(BlockBase.Info.a(Material.K, MaterialMapColor.a).h().a(5.0F, 6.0F).a(SoundEffectType.f).b())
   );
   public static final Block eX = a("chain", new BlockChain(BlockBase.Info.a(Material.K, MaterialMapColor.a).h().a(5.0F, 6.0F).a(SoundEffectType.T).b()));
   public static final Block eY = a("glass_pane", new BlockIronBars(BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b()));
   public static final Block eZ = a("melon", new BlockMelon(BlockBase.Info.a(Material.Q, MaterialMapColor.t).d(1.0F).a(SoundEffectType.a)));
   public static final Block fa = a(
      "attached_pumpkin_stem", new BlockStemAttached((BlockStemmed)dU, () -> Items.rf, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.a))
   );
   public static final Block fb = a(
      "attached_melon_stem", new BlockStemAttached((BlockStemmed)eZ, () -> Items.rg, BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.a))
   );
   public static final Block fc = a(
      "pumpkin_stem", new BlockStem((BlockStemmed)dU, () -> Items.rf, BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.w))
   );
   public static final Block fd = a(
      "melon_stem", new BlockStem((BlockStemmed)eZ, () -> Items.rg, BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.w))
   );
   public static final Block fe = a("vine", new BlockVine(BlockBase.Info.a(Material.g).a().d().d(0.2F).a(SoundEffectType.x)));
   public static final Block ff = a(
      "glow_lichen", new GlowLichenBlock(BlockBase.Info.a(Material.g, MaterialMapColor.aj).a().d(0.2F).a(SoundEffectType.ay).a(GlowLichenBlock.b(7)))
   );
   public static final Block fg = a("oak_fence_gate", new BlockFenceGate(BlockBase.Info.a(Material.z, n.t()).a(2.0F, 3.0F), BlockPropertyWood.a));
   public static final Block fh = a("brick_stairs", new BlockStairs(ci.o(), BlockBase.Info.a((BlockBase)ci)));
   public static final Block fi = a("stone_brick_stairs", new BlockStairs(eH.o(), BlockBase.Info.a((BlockBase)eH)));
   public static final Block fj = a("mud_brick_stairs", new BlockStairs(eM.o(), BlockBase.Info.a((BlockBase)eM)));
   public static final Block fk = a("mycelium", new BlockMycel(BlockBase.Info.a(Material.u, MaterialMapColor.y).d().d(0.6F).a(SoundEffectType.c)));
   public static final Block fl = a("lily_pad", new BlockWaterLily(BlockBase.Info.a(Material.e).c().a(SoundEffectType.d).b()));
   public static final Block fm = a("nether_bricks", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.J).h().a(2.0F, 6.0F).a(SoundEffectType.M)));
   public static final Block fn = a(
      "nether_brick_fence", new BlockFence(BlockBase.Info.a(Material.J, MaterialMapColor.J).h().a(2.0F, 6.0F).a(SoundEffectType.M))
   );
   public static final Block fo = a("nether_brick_stairs", new BlockStairs(fm.o(), BlockBase.Info.a((BlockBase)fm)));
   public static final Block fp = a("nether_wart", new BlockNetherWart(BlockBase.Info.a(Material.e, MaterialMapColor.C).a().d().a(SoundEffectType.y)));
   public static final Block fq = a(
      "enchanting_table", new BlockEnchantmentTable(BlockBase.Info.a(Material.J, MaterialMapColor.C).h().a(var0 -> 7).a(5.0F, 1200.0F))
   );
   public static final Block fr = a("brewing_stand", new BlockBrewingStand(BlockBase.Info.a(Material.K).h().d(0.5F).a(var0 -> 1).b()));
   public static final Block fs = a("cauldron", new BlockCauldron(BlockBase.Info.a(Material.K, MaterialMapColor.l).h().d(2.0F).b()));
   public static final Block ft = a("water_cauldron", new LayeredCauldronBlock(BlockBase.Info.a((BlockBase)fs), LayeredCauldronBlock.f, CauldronInteraction.b));
   public static final Block fu = a("lava_cauldron", new LavaCauldronBlock(BlockBase.Info.a((BlockBase)fs).a(var0 -> 15)));
   public static final Block fv = a(
      "powder_snow_cauldron", new PowderSnowCauldronBlock(BlockBase.Info.a((BlockBase)fs), LayeredCauldronBlock.g, CauldronInteraction.d)
   );
   public static final Block fw = a(
      "end_portal", new BlockEnderPortal(BlockBase.Info.a(Material.c, MaterialMapColor.D).a().a(var0 -> 15).a(-1.0F, 3600000.0F).f())
   );
   public static final Block fx = a(
      "end_portal_frame",
      new BlockEnderPortalFrame(BlockBase.Info.a(Material.J, MaterialMapColor.B).a(SoundEffectType.g).a(var0 -> 1).a(-1.0F, 3600000.0F).f())
   );
   public static final Block fy = a("end_stone", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.c).h().a(3.0F, 9.0F)));
   public static final Block fz = a("dragon_egg", new BlockDragonEgg(BlockBase.Info.a(Material.R, MaterialMapColor.D).a(3.0F, 9.0F).a(var0 -> 1).b()));
   public static final Block fA = a("redstone_lamp", new BlockRedstoneLamp(BlockBase.Info.a(Material.r).a(a(15)).d(0.3F).a(SoundEffectType.g).a(Blocks::b)));
   public static final Block fB = a("cocoa", new BlockCocoa(BlockBase.Info.a(Material.e).d().a(0.2F, 3.0F).a(SoundEffectType.a).b()));
   public static final Block fC = a("sandstone_stairs", new BlockStairs(aU.o(), BlockBase.Info.a((BlockBase)aU)));
   public static final Block fD = a("emerald_ore", new DropExperienceBlock(BlockBase.Info.a(Material.J).h().a(3.0F, 3.0F), UniformInt.a(3, 7)));
   public static final Block fE = a(
      "deepslate_emerald_ore",
      new DropExperienceBlock(BlockBase.Info.a((BlockBase)fD).a(MaterialMapColor.ah).a(4.5F, 3.0F).a(SoundEffectType.az), UniformInt.a(3, 7))
   );
   public static final Block fF = a("ender_chest", new BlockEnderChest(BlockBase.Info.a(Material.J).h().a(22.5F, 600.0F).a(var0 -> 7)));
   public static final Block fG = a("tripwire_hook", new BlockTripwireHook(BlockBase.Info.a(Material.o).a()));
   public static final Block fH = a("tripwire", new BlockTripwire((BlockTripwireHook)fG, BlockBase.Info.a(Material.o).a()));
   public static final Block fI = a("emerald_block", new Block(BlockBase.Info.a(Material.K, MaterialMapColor.H).h().a(5.0F, 6.0F).a(SoundEffectType.f)));
   public static final Block fJ = a("spruce_stairs", new BlockStairs(o.o(), BlockBase.Info.a((BlockBase)o)));
   public static final Block fK = a("birch_stairs", new BlockStairs(p.o(), BlockBase.Info.a((BlockBase)p)));
   public static final Block fL = a("jungle_stairs", new BlockStairs(q.o(), BlockBase.Info.a((BlockBase)q)));
   public static final Block fM = a("command_block", new BlockCommand(BlockBase.Info.a(Material.K, MaterialMapColor.A).h().a(-1.0F, 3600000.0F).f(), false));
   public static final Block fN = a("beacon", new BlockBeacon(BlockBase.Info.a(Material.G, MaterialMapColor.F).d(3.0F).a(var0 -> 15).b().a(Blocks::b)));
   public static final Block fO = a("cobblestone_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)m)));
   public static final Block fP = a("mossy_cobblestone_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)m)));
   public static final Block fQ = a("flower_pot", new BlockFlowerPot(a, BlockBase.Info.a(Material.o).c().b()));
   public static final Block fR = a("potted_torchflower", new BlockFlowerPot(bR, BlockBase.Info.a(Material.o).c().b().a(FeatureFlags.c)));
   public static final Block fS = a("potted_oak_sapling", new BlockFlowerPot(x, BlockBase.Info.a(Material.o).c().b()));
   public static final Block fT = a("potted_spruce_sapling", new BlockFlowerPot(y, BlockBase.Info.a(Material.o).c().b()));
   public static final Block fU = a("potted_birch_sapling", new BlockFlowerPot(z, BlockBase.Info.a(Material.o).c().b()));
   public static final Block fV = a("potted_jungle_sapling", new BlockFlowerPot(A, BlockBase.Info.a(Material.o).c().b()));
   public static final Block fW = a("potted_acacia_sapling", new BlockFlowerPot(B, BlockBase.Info.a(Material.o).c().b()));
   public static final Block fX = a("potted_cherry_sapling", new BlockFlowerPot(C, BlockBase.Info.a(Material.o).c().b().a(FeatureFlags.c)));
   public static final Block fY = a("potted_dark_oak_sapling", new BlockFlowerPot(D, BlockBase.Info.a(Material.o).c().b()));
   public static final Block fZ = a("potted_mangrove_propagule", new BlockFlowerPot(E, BlockBase.Info.a(Material.o).c().b()));
   public static final Block ga = a("potted_fern", new BlockFlowerPot(bt, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gb = a("potted_dandelion", new BlockFlowerPot(bQ, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gc = a("potted_poppy", new BlockFlowerPot(bS, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gd = a("potted_blue_orchid", new BlockFlowerPot(bT, BlockBase.Info.a(Material.o).c().b()));
   public static final Block ge = a("potted_allium", new BlockFlowerPot(bU, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gf = a("potted_azure_bluet", new BlockFlowerPot(bV, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gg = a("potted_red_tulip", new BlockFlowerPot(bW, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gh = a("potted_orange_tulip", new BlockFlowerPot(bX, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gi = a("potted_white_tulip", new BlockFlowerPot(bY, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gj = a("potted_pink_tulip", new BlockFlowerPot(bZ, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gk = a("potted_oxeye_daisy", new BlockFlowerPot(ca, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gl = a("potted_cornflower", new BlockFlowerPot(cb, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gm = a("potted_lily_of_the_valley", new BlockFlowerPot(cd, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gn = a("potted_wither_rose", new BlockFlowerPot(cc, BlockBase.Info.a(Material.o).c().b()));
   public static final Block go = a("potted_red_mushroom", new BlockFlowerPot(cf, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gp = a("potted_brown_mushroom", new BlockFlowerPot(ce, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gq = a("potted_dead_bush", new BlockFlowerPot(bu, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gr = a("potted_cactus", new BlockFlowerPot(dP, BlockBase.Info.a(Material.o).c().b()));
   public static final Block gs = a("carrots", new BlockCarrots(BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.v)));
   public static final Block gt = a("potatoes", new BlockPotatoes(BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.v)));
   public static final Block gu = a("oak_button", a(BlockSetType.e));
   public static final Block gv = a("spruce_button", a(BlockSetType.f));
   public static final Block gw = a("birch_button", a(BlockSetType.g));
   public static final Block gx = a("jungle_button", a(BlockSetType.j));
   public static final Block gy = a("acacia_button", a(BlockSetType.h));
   public static final Block gz = a(
      "cherry_button", new BlockButtonAbstract(BlockBase.Info.a(Material.o).a().d(0.5F).a(SoundEffectType.aP).a(FeatureFlags.c), BlockSetType.i, 30, true)
   );
   public static final Block gA = a("dark_oak_button", a(BlockSetType.k));
   public static final Block gB = a("mangrove_button", a(BlockSetType.n));
   public static final Block gC = a(
      "bamboo_button", new BlockButtonAbstract(BlockBase.Info.a(Material.o).a().d(0.5F).a(SoundEffectType.aN).a(FeatureFlags.c), BlockSetType.o, 30, true)
   );
   public static final Block gD = a("skeleton_skull", new BlockSkull(BlockSkull.Type.a, BlockBase.Info.a(Material.o).d(1.0F)));
   public static final Block gE = a("skeleton_wall_skull", new BlockSkullWall(BlockSkull.Type.a, BlockBase.Info.a(Material.o).d(1.0F).a(gD)));
   public static final Block gF = a("wither_skeleton_skull", new BlockWitherSkull(BlockBase.Info.a(Material.o).d(1.0F)));
   public static final Block gG = a("wither_skeleton_wall_skull", new BlockWitherSkullWall(BlockBase.Info.a(Material.o).d(1.0F).a(gF)));
   public static final Block gH = a("zombie_head", new BlockSkull(BlockSkull.Type.d, BlockBase.Info.a(Material.o).d(1.0F)));
   public static final Block gI = a("zombie_wall_head", new BlockSkullWall(BlockSkull.Type.d, BlockBase.Info.a(Material.o).d(1.0F).a(gH)));
   public static final Block gJ = a("player_head", new BlockSkullPlayer(BlockBase.Info.a(Material.o).d(1.0F)));
   public static final Block gK = a("player_wall_head", new BlockSkullPlayerWall(BlockBase.Info.a(Material.o).d(1.0F).a(gJ)));
   public static final Block gL = a("creeper_head", new BlockSkull(BlockSkull.Type.e, BlockBase.Info.a(Material.o).d(1.0F)));
   public static final Block gM = a("creeper_wall_head", new BlockSkullWall(BlockSkull.Type.e, BlockBase.Info.a(Material.o).d(1.0F).a(gL)));
   public static final Block gN = a("dragon_head", new BlockSkull(BlockSkull.Type.g, BlockBase.Info.a(Material.o).d(1.0F)));
   public static final Block gO = a("dragon_wall_head", new BlockSkullWall(BlockSkull.Type.g, BlockBase.Info.a(Material.o).d(1.0F).a(gN)));
   public static final Block gP = a("piglin_head", new BlockSkull(BlockSkull.Type.f, BlockBase.Info.a(Material.o).d(1.0F).a(FeatureFlags.c)));
   public static final Block gQ = a("piglin_wall_head", new PiglinWallSkullBlock(BlockBase.Info.a(Material.o).d(1.0F).a(gP).a(FeatureFlags.c)));
   public static final Block gR = a("anvil", new BlockAnvil(BlockBase.Info.a(Material.M, MaterialMapColor.g).h().a(5.0F, 1200.0F).a(SoundEffectType.m)));
   public static final Block gS = a(
      "chipped_anvil", new BlockAnvil(BlockBase.Info.a(Material.M, MaterialMapColor.g).h().a(5.0F, 1200.0F).a(SoundEffectType.m))
   );
   public static final Block gT = a(
      "damaged_anvil", new BlockAnvil(BlockBase.Info.a(Material.M, MaterialMapColor.g).h().a(5.0F, 1200.0F).a(SoundEffectType.m))
   );
   public static final Block gU = a("trapped_chest", new BlockChestTrapped(BlockBase.Info.a(Material.z).d(2.5F).a(SoundEffectType.a)));
   public static final Block gV = a(
      "light_weighted_pressure_plate", new BlockPressurePlateWeighted(15, BlockBase.Info.a(Material.K, MaterialMapColor.E).h().a().d(0.5F), BlockSetType.b)
   );
   public static final Block gW = a(
      "heavy_weighted_pressure_plate", new BlockPressurePlateWeighted(150, BlockBase.Info.a(Material.K).h().a().d(0.5F), BlockSetType.a)
   );
   public static final Block gX = a("comparator", new BlockRedstoneComparator(BlockBase.Info.a(Material.o).c().a(SoundEffectType.a)));
   public static final Block gY = a("daylight_detector", new BlockDaylightDetector(BlockBase.Info.a(Material.z).d(0.2F).a(SoundEffectType.a)));
   public static final Block gZ = a(
      "redstone_block", new BlockPowered(BlockBase.Info.a(Material.K, MaterialMapColor.e).h().a(5.0F, 6.0F).a(SoundEffectType.f).a(Blocks::b))
   );
   public static final Block ha = a(
      "nether_quartz_ore",
      new DropExperienceBlock(BlockBase.Info.a(Material.J, MaterialMapColor.J).h().a(3.0F, 3.0F).a(SoundEffectType.O), UniformInt.a(2, 5))
   );
   public static final Block hb = a("hopper", new BlockHopper(BlockBase.Info.a(Material.K, MaterialMapColor.l).h().a(3.0F, 4.8F).a(SoundEffectType.f).b()));
   public static final Block hc = a("quartz_block", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.o).h().d(0.8F)));
   public static final Block hd = a("chiseled_quartz_block", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.o).h().d(0.8F)));
   public static final Block he = a("quartz_pillar", new BlockRotatable(BlockBase.Info.a(Material.J, MaterialMapColor.o).h().d(0.8F)));
   public static final Block hf = a("quartz_stairs", new BlockStairs(hc.o(), BlockBase.Info.a((BlockBase)hc)));
   public static final Block hg = a("activator_rail", new BlockPoweredRail(BlockBase.Info.a(Material.o).a().d(0.7F).a(SoundEffectType.f)));
   public static final Block hh = a("dropper", new BlockDropper(BlockBase.Info.a(Material.J).h().d(3.5F)));
   public static final Block hi = a("white_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.K).h().a(1.25F, 4.2F)));
   public static final Block hj = a("orange_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.L).h().a(1.25F, 4.2F)));
   public static final Block hk = a("magenta_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.M).h().a(1.25F, 4.2F)));
   public static final Block hl = a("light_blue_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.N).h().a(1.25F, 4.2F)));
   public static final Block hm = a("yellow_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.O).h().a(1.25F, 4.2F)));
   public static final Block hn = a("lime_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.P).h().a(1.25F, 4.2F)));
   public static final Block ho = a("pink_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.Q).h().a(1.25F, 4.2F)));
   public static final Block hp = a("gray_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.R).h().a(1.25F, 4.2F)));
   public static final Block hq = a("light_gray_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.S).h().a(1.25F, 4.2F)));
   public static final Block hr = a("cyan_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.T).h().a(1.25F, 4.2F)));
   public static final Block hs = a("purple_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.U).h().a(1.25F, 4.2F)));
   public static final Block ht = a("blue_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.V).h().a(1.25F, 4.2F)));
   public static final Block hu = a("brown_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.W).h().a(1.25F, 4.2F)));
   public static final Block hv = a("green_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.X).h().a(1.25F, 4.2F)));
   public static final Block hw = a("red_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.Y).h().a(1.25F, 4.2F)));
   public static final Block hx = a("black_terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.Z).h().a(1.25F, 4.2F)));
   public static final Block hy = a(
      "white_stained_glass_pane", new BlockStainedGlassPane(EnumColor.a, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hz = a(
      "orange_stained_glass_pane", new BlockStainedGlassPane(EnumColor.b, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hA = a(
      "magenta_stained_glass_pane", new BlockStainedGlassPane(EnumColor.c, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hB = a(
      "light_blue_stained_glass_pane", new BlockStainedGlassPane(EnumColor.d, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hC = a(
      "yellow_stained_glass_pane", new BlockStainedGlassPane(EnumColor.e, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hD = a(
      "lime_stained_glass_pane", new BlockStainedGlassPane(EnumColor.f, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hE = a(
      "pink_stained_glass_pane", new BlockStainedGlassPane(EnumColor.g, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hF = a(
      "gray_stained_glass_pane", new BlockStainedGlassPane(EnumColor.h, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hG = a(
      "light_gray_stained_glass_pane", new BlockStainedGlassPane(EnumColor.i, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hH = a(
      "cyan_stained_glass_pane", new BlockStainedGlassPane(EnumColor.j, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hI = a(
      "purple_stained_glass_pane", new BlockStainedGlassPane(EnumColor.k, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hJ = a(
      "blue_stained_glass_pane", new BlockStainedGlassPane(EnumColor.l, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hK = a(
      "brown_stained_glass_pane", new BlockStainedGlassPane(EnumColor.m, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hL = a(
      "green_stained_glass_pane", new BlockStainedGlassPane(EnumColor.n, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hM = a(
      "red_stained_glass_pane", new BlockStainedGlassPane(EnumColor.o, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hN = a(
      "black_stained_glass_pane", new BlockStainedGlassPane(EnumColor.p, BlockBase.Info.a(Material.G).d(0.3F).a(SoundEffectType.g).b())
   );
   public static final Block hO = a("acacia_stairs", new BlockStairs(r.o(), BlockBase.Info.a((BlockBase)r)));
   public static final Block hP = a("cherry_stairs", new BlockStairs(s.o(), BlockBase.Info.a((BlockBase)s)));
   public static final Block hQ = a("dark_oak_stairs", new BlockStairs(t.o(), BlockBase.Info.a((BlockBase)t)));
   public static final Block hR = a("mangrove_stairs", new BlockStairs(u.o(), BlockBase.Info.a((BlockBase)u)));
   public static final Block hS = a("bamboo_stairs", new BlockStairs(v.o(), BlockBase.Info.a((BlockBase)v)));
   public static final Block hT = a("bamboo_mosaic_stairs", new BlockStairs(w.o(), BlockBase.Info.a((BlockBase)w)));
   public static final Block hU = a("slime_block", new BlockSlime(BlockBase.Info.a(Material.s, MaterialMapColor.b).a(0.8F).a(SoundEffectType.n).b()));
   public static final Block hV = a("barrier", new BlockBarrier(BlockBase.Info.a(Material.N).a(-1.0F, 3600000.8F).f().b().a(Blocks::a).i()));
   public static final Block hW = a("light", new LightBlock(BlockBase.Info.a(Material.a).a(-1.0F, 3600000.8F).f().b().a(LightBlock.d)));
   public static final Block hX = a("iron_trapdoor", new BlockTrapdoor(BlockBase.Info.a(Material.K).h().d(5.0F).b().a(Blocks::a), BlockSetType.a));
   public static final Block hY = a("prismarine", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.x).h().a(1.5F, 6.0F)));
   public static final Block hZ = a("prismarine_bricks", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.F).h().a(1.5F, 6.0F)));
   public static final Block ia = a("dark_prismarine", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.F).h().a(1.5F, 6.0F)));
   public static final Block ib = a("prismarine_stairs", new BlockStairs(hY.o(), BlockBase.Info.a((BlockBase)hY)));
   public static final Block ic = a("prismarine_brick_stairs", new BlockStairs(hZ.o(), BlockBase.Info.a((BlockBase)hZ)));
   public static final Block id = a("dark_prismarine_stairs", new BlockStairs(ia.o(), BlockBase.Info.a((BlockBase)ia)));
   public static final Block ie = a("prismarine_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.x).h().a(1.5F, 6.0F)));
   public static final Block if = a("prismarine_brick_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.F).h().a(1.5F, 6.0F)));
   public static final Block ig = a("dark_prismarine_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.F).h().a(1.5F, 6.0F)));
   public static final Block ih = a("sea_lantern", new Block(BlockBase.Info.a(Material.G, MaterialMapColor.o).d(0.3F).a(SoundEffectType.g).a(var0 -> 15)));
   public static final Block ii = a("hay_block", new BlockHay(BlockBase.Info.a(Material.u, MaterialMapColor.s).d(0.5F).a(SoundEffectType.c)));
   public static final Block ij = a(
      "white_carpet", new BlockCarpet(EnumColor.a, BlockBase.Info.a(Material.d, MaterialMapColor.i).d(0.1F).a(SoundEffectType.h))
   );
   public static final Block ik = a(
      "orange_carpet", new BlockCarpet(EnumColor.b, BlockBase.Info.a(Material.d, MaterialMapColor.p).d(0.1F).a(SoundEffectType.h))
   );
   public static final Block il = a(
      "magenta_carpet", new BlockCarpet(EnumColor.c, BlockBase.Info.a(Material.d, MaterialMapColor.q).d(0.1F).a(SoundEffectType.h))
   );
   public static final Block im = a(
      "light_blue_carpet", new BlockCarpet(EnumColor.d, BlockBase.Info.a(Material.d, MaterialMapColor.r).d(0.1F).a(SoundEffectType.h))
   );
   public static final Block in = a(
      "yellow_carpet", new BlockCarpet(EnumColor.e, BlockBase.Info.a(Material.d, MaterialMapColor.s).d(0.1F).a(SoundEffectType.h))
   );
   public static final Block io = a("lime_carpet", new BlockCarpet(EnumColor.f, BlockBase.Info.a(Material.d, MaterialMapColor.t).d(0.1F).a(SoundEffectType.h)));
   public static final Block ip = a("pink_carpet", new BlockCarpet(EnumColor.g, BlockBase.Info.a(Material.d, MaterialMapColor.u).d(0.1F).a(SoundEffectType.h)));
   public static final Block iq = a("gray_carpet", new BlockCarpet(EnumColor.h, BlockBase.Info.a(Material.d, MaterialMapColor.v).d(0.1F).a(SoundEffectType.h)));
   public static final Block ir = a(
      "light_gray_carpet", new BlockCarpet(EnumColor.i, BlockBase.Info.a(Material.d, MaterialMapColor.w).d(0.1F).a(SoundEffectType.h))
   );
   public static final Block is = a("cyan_carpet", new BlockCarpet(EnumColor.j, BlockBase.Info.a(Material.d, MaterialMapColor.x).d(0.1F).a(SoundEffectType.h)));
   public static final Block it = a(
      "purple_carpet", new BlockCarpet(EnumColor.k, BlockBase.Info.a(Material.d, MaterialMapColor.y).d(0.1F).a(SoundEffectType.h))
   );
   public static final Block iu = a("blue_carpet", new BlockCarpet(EnumColor.l, BlockBase.Info.a(Material.d, MaterialMapColor.z).d(0.1F).a(SoundEffectType.h)));
   public static final Block iv = a(
      "brown_carpet", new BlockCarpet(EnumColor.m, BlockBase.Info.a(Material.d, MaterialMapColor.A).d(0.1F).a(SoundEffectType.h))
   );
   public static final Block iw = a(
      "green_carpet", new BlockCarpet(EnumColor.n, BlockBase.Info.a(Material.d, MaterialMapColor.B).d(0.1F).a(SoundEffectType.h))
   );
   public static final Block ix = a("red_carpet", new BlockCarpet(EnumColor.o, BlockBase.Info.a(Material.d, MaterialMapColor.C).d(0.1F).a(SoundEffectType.h)));
   public static final Block iy = a(
      "black_carpet", new BlockCarpet(EnumColor.p, BlockBase.Info.a(Material.d, MaterialMapColor.D).d(0.1F).a(SoundEffectType.h))
   );
   public static final Block iz = a("terracotta", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.p).h().a(1.25F, 4.2F)));
   public static final Block iA = a("coal_block", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.D).h().a(5.0F, 6.0F)));
   public static final Block iB = a("packed_ice", new Block(BlockBase.Info.a(Material.v).a(0.98F).d(0.5F).a(SoundEffectType.g)));
   public static final Block iC = a(
      "sunflower", new BlockTallPlantFlower(BlockBase.Info.a(Material.g).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block iD = a(
      "lilac", new BlockTallPlantFlower(BlockBase.Info.a(Material.g).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block iE = a(
      "rose_bush", new BlockTallPlantFlower(BlockBase.Info.a(Material.g).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block iF = a(
      "peony", new BlockTallPlantFlower(BlockBase.Info.a(Material.g).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block iG = a(
      "tall_grass", new BlockTallPlant(BlockBase.Info.a(Material.g).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block iH = a(
      "large_fern", new BlockTallPlant(BlockBase.Info.a(Material.g).a().c().a(SoundEffectType.c).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block iI = a("white_banner", new BlockBanner(EnumColor.a, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iJ = a("orange_banner", new BlockBanner(EnumColor.b, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iK = a("magenta_banner", new BlockBanner(EnumColor.c, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iL = a("light_blue_banner", new BlockBanner(EnumColor.d, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iM = a("yellow_banner", new BlockBanner(EnumColor.e, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iN = a("lime_banner", new BlockBanner(EnumColor.f, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iO = a("pink_banner", new BlockBanner(EnumColor.g, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iP = a("gray_banner", new BlockBanner(EnumColor.h, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iQ = a("light_gray_banner", new BlockBanner(EnumColor.i, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iR = a("cyan_banner", new BlockBanner(EnumColor.j, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iS = a("purple_banner", new BlockBanner(EnumColor.k, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iT = a("blue_banner", new BlockBanner(EnumColor.l, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iU = a("brown_banner", new BlockBanner(EnumColor.m, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iV = a("green_banner", new BlockBanner(EnumColor.n, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iW = a("red_banner", new BlockBanner(EnumColor.o, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iX = a("black_banner", new BlockBanner(EnumColor.p, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a)));
   public static final Block iY = a("white_wall_banner", new BlockBannerWall(EnumColor.a, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iI)));
   public static final Block iZ = a(
      "orange_wall_banner", new BlockBannerWall(EnumColor.b, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iJ))
   );
   public static final Block ja = a(
      "magenta_wall_banner", new BlockBannerWall(EnumColor.c, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iK))
   );
   public static final Block jb = a(
      "light_blue_wall_banner", new BlockBannerWall(EnumColor.d, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iL))
   );
   public static final Block jc = a(
      "yellow_wall_banner", new BlockBannerWall(EnumColor.e, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iM))
   );
   public static final Block jd = a("lime_wall_banner", new BlockBannerWall(EnumColor.f, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iN)));
   public static final Block je = a("pink_wall_banner", new BlockBannerWall(EnumColor.g, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iO)));
   public static final Block jf = a("gray_wall_banner", new BlockBannerWall(EnumColor.h, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iP)));
   public static final Block jg = a(
      "light_gray_wall_banner", new BlockBannerWall(EnumColor.i, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iQ))
   );
   public static final Block jh = a("cyan_wall_banner", new BlockBannerWall(EnumColor.j, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iR)));
   public static final Block ji = a(
      "purple_wall_banner", new BlockBannerWall(EnumColor.k, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iS))
   );
   public static final Block jj = a("blue_wall_banner", new BlockBannerWall(EnumColor.l, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iT)));
   public static final Block jk = a("brown_wall_banner", new BlockBannerWall(EnumColor.m, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iU)));
   public static final Block jl = a("green_wall_banner", new BlockBannerWall(EnumColor.n, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iV)));
   public static final Block jm = a("red_wall_banner", new BlockBannerWall(EnumColor.o, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iW)));
   public static final Block jn = a("black_wall_banner", new BlockBannerWall(EnumColor.p, BlockBase.Info.a(Material.z).a().d(1.0F).a(SoundEffectType.a).a(iX)));
   public static final Block jo = a("red_sandstone", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.p).h().d(0.8F)));
   public static final Block jp = a("chiseled_red_sandstone", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.p).h().d(0.8F)));
   public static final Block jq = a("cut_red_sandstone", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.p).h().d(0.8F)));
   public static final Block jr = a("red_sandstone_stairs", new BlockStairs(jo.o(), BlockBase.Info.a((BlockBase)jo)));
   public static final Block js = a("oak_slab", new BlockStepAbstract(BlockBase.Info.a(Material.z, MaterialMapColor.n).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block jt = a("spruce_slab", new BlockStepAbstract(BlockBase.Info.a(Material.z, MaterialMapColor.I).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block ju = a("birch_slab", new BlockStepAbstract(BlockBase.Info.a(Material.z, MaterialMapColor.c).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block jv = a("jungle_slab", new BlockStepAbstract(BlockBase.Info.a(Material.z, MaterialMapColor.k).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block jw = a("acacia_slab", new BlockStepAbstract(BlockBase.Info.a(Material.z, MaterialMapColor.p).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block jx = a(
      "cherry_slab", new BlockStepAbstract(BlockBase.Info.a(Material.z, MaterialMapColor.K).a(2.0F, 3.0F).a(SoundEffectType.aP).a(FeatureFlags.c))
   );
   public static final Block jy = a(
      "dark_oak_slab", new BlockStepAbstract(BlockBase.Info.a(Material.z, MaterialMapColor.A).a(2.0F, 3.0F).a(SoundEffectType.a))
   );
   public static final Block jz = a(
      "mangrove_slab", new BlockStepAbstract(BlockBase.Info.a(Material.z, MaterialMapColor.C).a(2.0F, 3.0F).a(SoundEffectType.a))
   );
   public static final Block jA = a(
      "bamboo_slab", new BlockStepAbstract(BlockBase.Info.a(Material.z, MaterialMapColor.s).a(2.0F, 3.0F).a(SoundEffectType.aN).a(FeatureFlags.c))
   );
   public static final Block jB = a(
      "bamboo_mosaic_slab", new BlockStepAbstract(BlockBase.Info.a(Material.z, MaterialMapColor.s).a(2.0F, 3.0F).a(SoundEffectType.aN).a(FeatureFlags.c))
   );
   public static final Block jC = a("stone_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.l).h().a(2.0F, 6.0F)));
   public static final Block jD = a("smooth_stone_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.l).h().a(2.0F, 6.0F)));
   public static final Block jE = a("sandstone_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.c).h().a(2.0F, 6.0F)));
   public static final Block jF = a("cut_sandstone_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.c).h().a(2.0F, 6.0F)));
   public static final Block jG = a("petrified_oak_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.n).h().a(2.0F, 6.0F)));
   public static final Block jH = a("cobblestone_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.l).h().a(2.0F, 6.0F)));
   public static final Block jI = a("brick_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.C).h().a(2.0F, 6.0F)));
   public static final Block jJ = a("stone_brick_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.l).h().a(2.0F, 6.0F)));
   public static final Block jK = a(
      "mud_brick_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.S).h().a(1.5F, 3.0F).a(SoundEffectType.aI))
   );
   public static final Block jL = a(
      "nether_brick_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.J).h().a(2.0F, 6.0F).a(SoundEffectType.M))
   );
   public static final Block jM = a("quartz_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.o).h().a(2.0F, 6.0F)));
   public static final Block jN = a("red_sandstone_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.p).h().a(2.0F, 6.0F)));
   public static final Block jO = a("cut_red_sandstone_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.p).h().a(2.0F, 6.0F)));
   public static final Block jP = a("purpur_slab", new BlockStepAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.q).h().a(2.0F, 6.0F)));
   public static final Block jQ = a("smooth_stone", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.l).h().a(2.0F, 6.0F)));
   public static final Block jR = a("smooth_sandstone", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.c).h().a(2.0F, 6.0F)));
   public static final Block jS = a("smooth_quartz", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.o).h().a(2.0F, 6.0F)));
   public static final Block jT = a("smooth_red_sandstone", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.p).h().a(2.0F, 6.0F)));
   public static final Block jU = a("spruce_fence_gate", new BlockFenceGate(BlockBase.Info.a(Material.z, o.t()).a(2.0F, 3.0F), BlockPropertyWood.b));
   public static final Block jV = a("birch_fence_gate", new BlockFenceGate(BlockBase.Info.a(Material.z, p.t()).a(2.0F, 3.0F), BlockPropertyWood.c));
   public static final Block jW = a("jungle_fence_gate", new BlockFenceGate(BlockBase.Info.a(Material.z, q.t()).a(2.0F, 3.0F), BlockPropertyWood.f));
   public static final Block jX = a("acacia_fence_gate", new BlockFenceGate(BlockBase.Info.a(Material.z, r.t()).a(2.0F, 3.0F), BlockPropertyWood.d));
   public static final Block jY = a(
      "cherry_fence_gate", new BlockFenceGate(BlockBase.Info.a(Material.z, s.t()).a(2.0F, 3.0F).a(FeatureFlags.c), BlockPropertyWood.e)
   );
   public static final Block jZ = a("dark_oak_fence_gate", new BlockFenceGate(BlockBase.Info.a(Material.z, t.t()).a(2.0F, 3.0F), BlockPropertyWood.g));
   public static final Block ka = a("mangrove_fence_gate", new BlockFenceGate(BlockBase.Info.a(Material.z, u.t()).a(2.0F, 3.0F), BlockPropertyWood.j));
   public static final Block kb = a(
      "bamboo_fence_gate", new BlockFenceGate(BlockBase.Info.a(Material.z, v.t()).a(2.0F, 3.0F).a(FeatureFlags.c), BlockPropertyWood.k)
   );
   public static final Block kc = a("spruce_fence", new BlockFence(BlockBase.Info.a(Material.z, o.t()).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block kd = a("birch_fence", new BlockFence(BlockBase.Info.a(Material.z, p.t()).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block ke = a("jungle_fence", new BlockFence(BlockBase.Info.a(Material.z, q.t()).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block kf = a("acacia_fence", new BlockFence(BlockBase.Info.a(Material.z, r.t()).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block kg = a("cherry_fence", new BlockFence(BlockBase.Info.a(Material.z, s.t()).a(2.0F, 3.0F).a(SoundEffectType.aP).a(FeatureFlags.c)));
   public static final Block kh = a("dark_oak_fence", new BlockFence(BlockBase.Info.a(Material.z, t.t()).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block ki = a("mangrove_fence", new BlockFence(BlockBase.Info.a(Material.z, u.t()).a(2.0F, 3.0F).a(SoundEffectType.a)));
   public static final Block kj = a("bamboo_fence", new BlockFence(BlockBase.Info.a(Material.z, v.t()).a(2.0F, 3.0F).a(SoundEffectType.aN).a(FeatureFlags.c)));
   public static final Block kk = a("spruce_door", new BlockDoor(BlockBase.Info.a(Material.z, o.t()).d(3.0F).b(), BlockSetType.f));
   public static final Block kl = a("birch_door", new BlockDoor(BlockBase.Info.a(Material.z, p.t()).d(3.0F).b(), BlockSetType.g));
   public static final Block km = a("jungle_door", new BlockDoor(BlockBase.Info.a(Material.z, q.t()).d(3.0F).b(), BlockSetType.j));
   public static final Block kn = a("acacia_door", new BlockDoor(BlockBase.Info.a(Material.z, r.t()).d(3.0F).b(), BlockSetType.h));
   public static final Block ko = a("cherry_door", new BlockDoor(BlockBase.Info.a(Material.z, s.t()).d(3.0F).b().a(FeatureFlags.c), BlockSetType.i));
   public static final Block kp = a("dark_oak_door", new BlockDoor(BlockBase.Info.a(Material.z, t.t()).d(3.0F).b(), BlockSetType.k));
   public static final Block kq = a("mangrove_door", new BlockDoor(BlockBase.Info.a(Material.z, u.t()).d(3.0F).b(), BlockSetType.n));
   public static final Block kr = a("bamboo_door", new BlockDoor(BlockBase.Info.a(Material.z, v.t()).d(3.0F).b().a(FeatureFlags.c), BlockSetType.o));
   public static final Block ks = a("end_rod", new BlockEndRod(BlockBase.Info.a(Material.o).c().a(var0 -> 14).a(SoundEffectType.a).b()));
   public static final Block kt = a("chorus_plant", new BlockChorusFruit(BlockBase.Info.a(Material.e, MaterialMapColor.y).d(0.4F).a(SoundEffectType.a).b()));
   public static final Block ku = a(
      "chorus_flower",
      new BlockChorusFlower((BlockChorusFruit)kt, BlockBase.Info.a(Material.e, MaterialMapColor.y).d().d(0.4F).a(SoundEffectType.a).b().a(Blocks::a))
   );
   public static final Block kv = a("purpur_block", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.q).h().a(1.5F, 6.0F)));
   public static final Block kw = a("purpur_pillar", new BlockRotatable(BlockBase.Info.a(Material.J, MaterialMapColor.q).h().a(1.5F, 6.0F)));
   public static final Block kx = a("purpur_stairs", new BlockStairs(kv.o(), BlockBase.Info.a((BlockBase)kv)));
   public static final Block ky = a("end_stone_bricks", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.c).h().a(3.0F, 9.0F)));
   public static final Block kz = a(
      "torchflower_crop", new TorchflowerCropBlock(BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.v).a(FeatureFlags.c))
   );
   public static final Block kA = a("beetroots", new BlockBeetroot(BlockBase.Info.a(Material.e).a().d().c().a(SoundEffectType.v)));
   public static final Block kB = a("dirt_path", new BlockGrassPath(BlockBase.Info.a(Material.t).d(0.65F).a(SoundEffectType.c).c(Blocks::a).b(Blocks::a)));
   public static final Block kC = a(
      "end_gateway", new BlockEndGateway(BlockBase.Info.a(Material.c, MaterialMapColor.D).a().a(var0 -> 15).a(-1.0F, 3600000.0F).f())
   );
   public static final Block kD = a(
      "repeating_command_block", new BlockCommand(BlockBase.Info.a(Material.K, MaterialMapColor.y).h().a(-1.0F, 3600000.0F).f(), false)
   );
   public static final Block kE = a(
      "chain_command_block", new BlockCommand(BlockBase.Info.a(Material.K, MaterialMapColor.B).h().a(-1.0F, 3600000.0F).f(), true)
   );
   public static final Block kF = a(
      "frosted_ice",
      new BlockIceFrost(BlockBase.Info.a(Material.H).a(0.98F).d().d(0.5F).a(SoundEffectType.g).b().a((var0, var1x, var2x, var3x) -> var3x == EntityTypes.az))
   );
   public static final Block kG = a(
      "magma_block",
      new BlockMagma(
         BlockBase.Info.a(Material.J, MaterialMapColor.J).h().a(var0 -> 3).d().d(0.5F).a((var0, var1x, var2x, var3x) -> var3x.d()).d(Blocks::a).e(Blocks::a)
      )
   );
   public static final Block kH = a("nether_wart_block", new Block(BlockBase.Info.a(Material.u, MaterialMapColor.C).d(1.0F).a(SoundEffectType.K)));
   public static final Block kI = a("red_nether_bricks", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.J).h().a(2.0F, 6.0F).a(SoundEffectType.M)));
   public static final Block kJ = a("bone_block", new BlockRotatable(BlockBase.Info.a(Material.J, MaterialMapColor.c).h().d(2.0F).a(SoundEffectType.P)));
   public static final Block kK = a("structure_void", new BlockStructureVoid(BlockBase.Info.a(Material.b).a().f().i()));
   public static final Block kL = a("observer", new BlockObserver(BlockBase.Info.a(Material.J).d(3.0F).h().a(Blocks::b)));
   public static final Block kM = a("shulker_box", a(null, BlockBase.Info.a(Material.y)));
   public static final Block kN = a("white_shulker_box", a(EnumColor.a, BlockBase.Info.a(Material.y, MaterialMapColor.i)));
   public static final Block kO = a("orange_shulker_box", a(EnumColor.b, BlockBase.Info.a(Material.y, MaterialMapColor.p)));
   public static final Block kP = a("magenta_shulker_box", a(EnumColor.c, BlockBase.Info.a(Material.y, MaterialMapColor.q)));
   public static final Block kQ = a("light_blue_shulker_box", a(EnumColor.d, BlockBase.Info.a(Material.y, MaterialMapColor.r)));
   public static final Block kR = a("yellow_shulker_box", a(EnumColor.e, BlockBase.Info.a(Material.y, MaterialMapColor.s)));
   public static final Block kS = a("lime_shulker_box", a(EnumColor.f, BlockBase.Info.a(Material.y, MaterialMapColor.t)));
   public static final Block kT = a("pink_shulker_box", a(EnumColor.g, BlockBase.Info.a(Material.y, MaterialMapColor.u)));
   public static final Block kU = a("gray_shulker_box", a(EnumColor.h, BlockBase.Info.a(Material.y, MaterialMapColor.v)));
   public static final Block kV = a("light_gray_shulker_box", a(EnumColor.i, BlockBase.Info.a(Material.y, MaterialMapColor.w)));
   public static final Block kW = a("cyan_shulker_box", a(EnumColor.j, BlockBase.Info.a(Material.y, MaterialMapColor.x)));
   public static final Block kX = a("purple_shulker_box", a(EnumColor.k, BlockBase.Info.a(Material.y, MaterialMapColor.U)));
   public static final Block kY = a("blue_shulker_box", a(EnumColor.l, BlockBase.Info.a(Material.y, MaterialMapColor.z)));
   public static final Block kZ = a("brown_shulker_box", a(EnumColor.m, BlockBase.Info.a(Material.y, MaterialMapColor.A)));
   public static final Block la = a("green_shulker_box", a(EnumColor.n, BlockBase.Info.a(Material.y, MaterialMapColor.B)));
   public static final Block lb = a("red_shulker_box", a(EnumColor.o, BlockBase.Info.a(Material.y, MaterialMapColor.C)));
   public static final Block lc = a("black_shulker_box", a(EnumColor.p, BlockBase.Info.a(Material.y, MaterialMapColor.D)));
   public static final Block ld = a("white_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.a).h().d(1.4F)));
   public static final Block le = a("orange_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.b).h().d(1.4F)));
   public static final Block lf = a("magenta_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.c).h().d(1.4F)));
   public static final Block lg = a("light_blue_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.d).h().d(1.4F)));
   public static final Block lh = a("yellow_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.e).h().d(1.4F)));
   public static final Block li = a("lime_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.f).h().d(1.4F)));
   public static final Block lj = a("pink_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.g).h().d(1.4F)));
   public static final Block lk = a("gray_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.h).h().d(1.4F)));
   public static final Block ll = a("light_gray_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.i).h().d(1.4F)));
   public static final Block lm = a("cyan_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.j).h().d(1.4F)));
   public static final Block ln = a("purple_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.k).h().d(1.4F)));
   public static final Block lo = a("blue_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.l).h().d(1.4F)));
   public static final Block lp = a("brown_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.m).h().d(1.4F)));
   public static final Block lq = a("green_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.n).h().d(1.4F)));
   public static final Block lr = a("red_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.o).h().d(1.4F)));
   public static final Block ls = a("black_glazed_terracotta", new BlockGlazedTerracotta(BlockBase.Info.a(Material.J, EnumColor.p).h().d(1.4F)));
   public static final Block lt = a("white_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.a).h().d(1.8F)));
   public static final Block lu = a("orange_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.b).h().d(1.8F)));
   public static final Block lv = a("magenta_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.c).h().d(1.8F)));
   public static final Block lw = a("light_blue_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.d).h().d(1.8F)));
   public static final Block lx = a("yellow_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.e).h().d(1.8F)));
   public static final Block ly = a("lime_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.f).h().d(1.8F)));
   public static final Block lz = a("pink_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.g).h().d(1.8F)));
   public static final Block lA = a("gray_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.h).h().d(1.8F)));
   public static final Block lB = a("light_gray_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.i).h().d(1.8F)));
   public static final Block lC = a("cyan_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.j).h().d(1.8F)));
   public static final Block lD = a("purple_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.k).h().d(1.8F)));
   public static final Block lE = a("blue_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.l).h().d(1.8F)));
   public static final Block lF = a("brown_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.m).h().d(1.8F)));
   public static final Block lG = a("green_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.n).h().d(1.8F)));
   public static final Block lH = a("red_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.o).h().d(1.8F)));
   public static final Block lI = a("black_concrete", new Block(BlockBase.Info.a(Material.J, EnumColor.p).h().d(1.8F)));
   public static final Block lJ = a(
      "white_concrete_powder", new BlockConcretePowder(lt, BlockBase.Info.a(Material.w, EnumColor.a).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lK = a(
      "orange_concrete_powder", new BlockConcretePowder(lu, BlockBase.Info.a(Material.w, EnumColor.b).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lL = a(
      "magenta_concrete_powder", new BlockConcretePowder(lv, BlockBase.Info.a(Material.w, EnumColor.c).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lM = a(
      "light_blue_concrete_powder", new BlockConcretePowder(lw, BlockBase.Info.a(Material.w, EnumColor.d).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lN = a(
      "yellow_concrete_powder", new BlockConcretePowder(lx, BlockBase.Info.a(Material.w, EnumColor.e).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lO = a(
      "lime_concrete_powder", new BlockConcretePowder(ly, BlockBase.Info.a(Material.w, EnumColor.f).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lP = a(
      "pink_concrete_powder", new BlockConcretePowder(lz, BlockBase.Info.a(Material.w, EnumColor.g).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lQ = a(
      "gray_concrete_powder", new BlockConcretePowder(lA, BlockBase.Info.a(Material.w, EnumColor.h).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lR = a(
      "light_gray_concrete_powder", new BlockConcretePowder(lB, BlockBase.Info.a(Material.w, EnumColor.i).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lS = a(
      "cyan_concrete_powder", new BlockConcretePowder(lC, BlockBase.Info.a(Material.w, EnumColor.j).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lT = a(
      "purple_concrete_powder", new BlockConcretePowder(lD, BlockBase.Info.a(Material.w, EnumColor.k).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lU = a(
      "blue_concrete_powder", new BlockConcretePowder(lE, BlockBase.Info.a(Material.w, EnumColor.l).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lV = a(
      "brown_concrete_powder", new BlockConcretePowder(lF, BlockBase.Info.a(Material.w, EnumColor.m).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lW = a(
      "green_concrete_powder", new BlockConcretePowder(lG, BlockBase.Info.a(Material.w, EnumColor.n).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lX = a("red_concrete_powder", new BlockConcretePowder(lH, BlockBase.Info.a(Material.w, EnumColor.o).d(0.5F).a(SoundEffectType.i)));
   public static final Block lY = a(
      "black_concrete_powder", new BlockConcretePowder(lI, BlockBase.Info.a(Material.w, EnumColor.p).d(0.5F).a(SoundEffectType.i))
   );
   public static final Block lZ = a("kelp", new BlockKelp(BlockBase.Info.a(Material.f).a().d().c().a(SoundEffectType.p)));
   public static final Block ma = a("kelp_plant", new BlockKelpPlant(BlockBase.Info.a(Material.f).a().c().a(SoundEffectType.p)));
   public static final Block mb = a("dried_kelp_block", new Block(BlockBase.Info.a(Material.u, MaterialMapColor.B).a(0.5F, 2.5F).a(SoundEffectType.c)));
   public static final Block mc = a("turtle_egg", new BlockTurtleEgg(BlockBase.Info.a(Material.R, MaterialMapColor.c).d(0.5F).a(SoundEffectType.f).d().b()));
   public static final Block md = a("dead_tube_coral_block", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a(1.5F, 6.0F)));
   public static final Block me = a("dead_brain_coral_block", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a(1.5F, 6.0F)));
   public static final Block mf = a("dead_bubble_coral_block", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a(1.5F, 6.0F)));
   public static final Block mg = a("dead_fire_coral_block", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a(1.5F, 6.0F)));
   public static final Block mh = a("dead_horn_coral_block", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a(1.5F, 6.0F)));
   public static final Block mi = a(
      "tube_coral_block", new BlockCoral(md, BlockBase.Info.a(Material.J, MaterialMapColor.z).h().a(1.5F, 6.0F).a(SoundEffectType.q))
   );
   public static final Block mj = a(
      "brain_coral_block", new BlockCoral(me, BlockBase.Info.a(Material.J, MaterialMapColor.u).h().a(1.5F, 6.0F).a(SoundEffectType.q))
   );
   public static final Block mk = a(
      "bubble_coral_block", new BlockCoral(mf, BlockBase.Info.a(Material.J, MaterialMapColor.y).h().a(1.5F, 6.0F).a(SoundEffectType.q))
   );
   public static final Block ml = a(
      "fire_coral_block", new BlockCoral(mg, BlockBase.Info.a(Material.J, MaterialMapColor.C).h().a(1.5F, 6.0F).a(SoundEffectType.q))
   );
   public static final Block mm = a(
      "horn_coral_block", new BlockCoral(mh, BlockBase.Info.a(Material.J, MaterialMapColor.s).h().a(1.5F, 6.0F).a(SoundEffectType.q))
   );
   public static final Block mn = a("dead_tube_coral", new BlockCoralDead(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c()));
   public static final Block mo = a("dead_brain_coral", new BlockCoralDead(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c()));
   public static final Block mp = a("dead_bubble_coral", new BlockCoralDead(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c()));
   public static final Block mq = a("dead_fire_coral", new BlockCoralDead(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c()));
   public static final Block mr = a("dead_horn_coral", new BlockCoralDead(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c()));
   public static final Block ms = a("tube_coral", new BlockCoralPlant(mn, BlockBase.Info.a(Material.f, MaterialMapColor.z).a().c().a(SoundEffectType.p)));
   public static final Block mt = a("brain_coral", new BlockCoralPlant(mo, BlockBase.Info.a(Material.f, MaterialMapColor.u).a().c().a(SoundEffectType.p)));
   public static final Block mu = a("bubble_coral", new BlockCoralPlant(mp, BlockBase.Info.a(Material.f, MaterialMapColor.y).a().c().a(SoundEffectType.p)));
   public static final Block mv = a("fire_coral", new BlockCoralPlant(mq, BlockBase.Info.a(Material.f, MaterialMapColor.C).a().c().a(SoundEffectType.p)));
   public static final Block mw = a("horn_coral", new BlockCoralPlant(mr, BlockBase.Info.a(Material.f, MaterialMapColor.s).a().c().a(SoundEffectType.p)));
   public static final Block mx = a("dead_tube_coral_fan", new BlockCoralFanAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c()));
   public static final Block my = a("dead_brain_coral_fan", new BlockCoralFanAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c()));
   public static final Block mz = a("dead_bubble_coral_fan", new BlockCoralFanAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c()));
   public static final Block mA = a("dead_fire_coral_fan", new BlockCoralFanAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c()));
   public static final Block mB = a("dead_horn_coral_fan", new BlockCoralFanAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c()));
   public static final Block mC = a("tube_coral_fan", new BlockCoralFan(mx, BlockBase.Info.a(Material.f, MaterialMapColor.z).a().c().a(SoundEffectType.p)));
   public static final Block mD = a("brain_coral_fan", new BlockCoralFan(my, BlockBase.Info.a(Material.f, MaterialMapColor.u).a().c().a(SoundEffectType.p)));
   public static final Block mE = a("bubble_coral_fan", new BlockCoralFan(mz, BlockBase.Info.a(Material.f, MaterialMapColor.y).a().c().a(SoundEffectType.p)));
   public static final Block mF = a("fire_coral_fan", new BlockCoralFan(mA, BlockBase.Info.a(Material.f, MaterialMapColor.C).a().c().a(SoundEffectType.p)));
   public static final Block mG = a("horn_coral_fan", new BlockCoralFan(mB, BlockBase.Info.a(Material.f, MaterialMapColor.s).a().c().a(SoundEffectType.p)));
   public static final Block mH = a(
      "dead_tube_coral_wall_fan", new BlockCoralFanWallAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c().a(mx))
   );
   public static final Block mI = a(
      "dead_brain_coral_wall_fan", new BlockCoralFanWallAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c().a(my))
   );
   public static final Block mJ = a(
      "dead_bubble_coral_wall_fan", new BlockCoralFanWallAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c().a(mz))
   );
   public static final Block mK = a(
      "dead_fire_coral_wall_fan", new BlockCoralFanWallAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c().a(mA))
   );
   public static final Block mL = a(
      "dead_horn_coral_wall_fan", new BlockCoralFanWallAbstract(BlockBase.Info.a(Material.J, MaterialMapColor.v).h().a().c().a(mB))
   );
   public static final Block mM = a(
      "tube_coral_wall_fan", new BlockCoralFanWall(mH, BlockBase.Info.a(Material.f, MaterialMapColor.z).a().c().a(SoundEffectType.p).a(mC))
   );
   public static final Block mN = a(
      "brain_coral_wall_fan", new BlockCoralFanWall(mI, BlockBase.Info.a(Material.f, MaterialMapColor.u).a().c().a(SoundEffectType.p).a(mD))
   );
   public static final Block mO = a(
      "bubble_coral_wall_fan", new BlockCoralFanWall(mJ, BlockBase.Info.a(Material.f, MaterialMapColor.y).a().c().a(SoundEffectType.p).a(mE))
   );
   public static final Block mP = a(
      "fire_coral_wall_fan", new BlockCoralFanWall(mK, BlockBase.Info.a(Material.f, MaterialMapColor.C).a().c().a(SoundEffectType.p).a(mF))
   );
   public static final Block mQ = a(
      "horn_coral_wall_fan", new BlockCoralFanWall(mL, BlockBase.Info.a(Material.f, MaterialMapColor.s).a().c().a(SoundEffectType.p).a(mG))
   );
   public static final Block mR = a(
      "sea_pickle",
      new BlockSeaPickle(
         BlockBase.Info.a(Material.f, MaterialMapColor.B).a(var0 -> BlockSeaPickle.h(var0) ? 0 : 3 + 3 * var0.c(BlockSeaPickle.b)).a(SoundEffectType.n).b()
      )
   );
   public static final Block mS = a("blue_ice", new BlockHalfTransparent(BlockBase.Info.a(Material.v).d(2.8F).a(0.989F).a(SoundEffectType.g)));
   public static final Block mT = a("conduit", new BlockConduit(BlockBase.Info.a(Material.G, MaterialMapColor.F).d(3.0F).a(var0 -> 15).b()));
   public static final Block mU = a(
      "bamboo_sapling", new BlockBambooSapling(BlockBase.Info.a(Material.B).d().c().a().d(1.0F).a(SoundEffectType.s).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block mV = a(
      "bamboo", new BlockBamboo(BlockBase.Info.a(Material.C, MaterialMapColor.h).d().c().d(1.0F).a(SoundEffectType.r).b().e().a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block mW = a("potted_bamboo", new BlockFlowerPot(mV, BlockBase.Info.a(Material.o).c().b()));
   public static final Block mX = a("void_air", new BlockAir(BlockBase.Info.a(Material.a).a().f().g()));
   public static final Block mY = a("cave_air", new BlockAir(BlockBase.Info.a(Material.a).a().f().g()));
   public static final Block mZ = a("bubble_column", new BlockBubbleColumn(BlockBase.Info.a(Material.k).a().f()));
   public static final Block na = a("polished_granite_stairs", new BlockStairs(d.o(), BlockBase.Info.a((BlockBase)d)));
   public static final Block nb = a("smooth_red_sandstone_stairs", new BlockStairs(jT.o(), BlockBase.Info.a((BlockBase)jT)));
   public static final Block nc = a("mossy_stone_brick_stairs", new BlockStairs(eI.o(), BlockBase.Info.a((BlockBase)eI)));
   public static final Block nd = a("polished_diorite_stairs", new BlockStairs(f.o(), BlockBase.Info.a((BlockBase)f)));
   public static final Block ne = a("mossy_cobblestone_stairs", new BlockStairs(cm.o(), BlockBase.Info.a((BlockBase)cm)));
   public static final Block nf = a("end_stone_brick_stairs", new BlockStairs(ky.o(), BlockBase.Info.a((BlockBase)ky)));
   public static final Block ng = a("stone_stairs", new BlockStairs(b.o(), BlockBase.Info.a((BlockBase)b)));
   public static final Block nh = a("smooth_sandstone_stairs", new BlockStairs(jR.o(), BlockBase.Info.a((BlockBase)jR)));
   public static final Block ni = a("smooth_quartz_stairs", new BlockStairs(jS.o(), BlockBase.Info.a((BlockBase)jS)));
   public static final Block nj = a("granite_stairs", new BlockStairs(c.o(), BlockBase.Info.a((BlockBase)c)));
   public static final Block nk = a("andesite_stairs", new BlockStairs(g.o(), BlockBase.Info.a((BlockBase)g)));
   public static final Block nl = a("red_nether_brick_stairs", new BlockStairs(kI.o(), BlockBase.Info.a((BlockBase)kI)));
   public static final Block nm = a("polished_andesite_stairs", new BlockStairs(h.o(), BlockBase.Info.a((BlockBase)h)));
   public static final Block nn = a("diorite_stairs", new BlockStairs(e.o(), BlockBase.Info.a((BlockBase)e)));
   public static final Block no = a("polished_granite_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)d)));
   public static final Block np = a("smooth_red_sandstone_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)jT)));
   public static final Block nq = a("mossy_stone_brick_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)eI)));
   public static final Block nr = a("polished_diorite_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)f)));
   public static final Block ns = a("mossy_cobblestone_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)cm)));
   public static final Block nt = a("end_stone_brick_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)ky)));
   public static final Block nu = a("smooth_sandstone_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)jR)));
   public static final Block nv = a("smooth_quartz_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)jS)));
   public static final Block nw = a("granite_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)c)));
   public static final Block nx = a("andesite_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)g)));
   public static final Block ny = a("red_nether_brick_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)kI)));
   public static final Block nz = a("polished_andesite_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)h)));
   public static final Block nA = a("diorite_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)e)));
   public static final Block nB = a("brick_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)ci)));
   public static final Block nC = a("prismarine_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)hY)));
   public static final Block nD = a("red_sandstone_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)jo)));
   public static final Block nE = a("mossy_stone_brick_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)eI)));
   public static final Block nF = a("granite_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)c)));
   public static final Block nG = a("stone_brick_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)eH)));
   public static final Block nH = a("mud_brick_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)eM)));
   public static final Block nI = a("nether_brick_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)fm)));
   public static final Block nJ = a("andesite_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)g)));
   public static final Block nK = a("red_nether_brick_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)kI)));
   public static final Block nL = a("sandstone_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)aU)));
   public static final Block nM = a("end_stone_brick_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)ky)));
   public static final Block nN = a("diorite_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)e)));
   public static final Block nO = a(
      "scaffolding", new BlockScaffolding(BlockBase.Info.a(Material.o, MaterialMapColor.c).a().a(SoundEffectType.t).e().a(Blocks::a))
   );
   public static final Block nP = a("loom", new BlockLoom(BlockBase.Info.a(Material.z).d(2.5F).a(SoundEffectType.a)));
   public static final Block nQ = a("barrel", new BlockBarrel(BlockBase.Info.a(Material.z).d(2.5F).a(SoundEffectType.a)));
   public static final Block nR = a("smoker", new BlockSmoker(BlockBase.Info.a(Material.J).h().d(3.5F).a(a(13))));
   public static final Block nS = a("blast_furnace", new BlockBlastFurnace(BlockBase.Info.a(Material.J).h().d(3.5F).a(a(13))));
   public static final Block nT = a("cartography_table", new BlockCartographyTable(BlockBase.Info.a(Material.z).d(2.5F).a(SoundEffectType.a)));
   public static final Block nU = a("fletching_table", new BlockFletchingTable(BlockBase.Info.a(Material.z).d(2.5F).a(SoundEffectType.a)));
   public static final Block nV = a("grindstone", new BlockGrindstone(BlockBase.Info.a(Material.M, MaterialMapColor.g).h().a(2.0F, 6.0F).a(SoundEffectType.e)));
   public static final Block nW = a("lectern", new BlockLectern(BlockBase.Info.a(Material.z).d(2.5F).a(SoundEffectType.a)));
   public static final Block nX = a("smithing_table", new BlockSmithingTable(BlockBase.Info.a(Material.z).d(2.5F).a(SoundEffectType.a)));
   public static final Block nY = a("stonecutter", new BlockStonecutter(BlockBase.Info.a(Material.J).h().d(3.5F)));
   public static final Block nZ = a("bell", new BlockBell(BlockBase.Info.a(Material.K, MaterialMapColor.E).h().d(5.0F).a(SoundEffectType.m)));
   public static final Block oa = a("lantern", new BlockLantern(BlockBase.Info.a(Material.K).h().d(3.5F).a(SoundEffectType.z).a(var0 -> 15).b()));
   public static final Block ob = a("soul_lantern", new BlockLantern(BlockBase.Info.a(Material.K).h().d(3.5F).a(SoundEffectType.z).a(var0 -> 10).b()));
   public static final Block oc = a(
      "campfire", new BlockCampfire(true, 1, BlockBase.Info.a(Material.z, MaterialMapColor.I).d(2.0F).a(SoundEffectType.a).a(a(15)).b())
   );
   public static final Block od = a(
      "soul_campfire", new BlockCampfire(false, 2, BlockBase.Info.a(Material.z, MaterialMapColor.I).d(2.0F).a(SoundEffectType.a).a(a(10)).b())
   );
   public static final Block oe = a("sweet_berry_bush", new BlockSweetBerryBush(BlockBase.Info.a(Material.e).d().a().a(SoundEffectType.u)));
   public static final Block of = a("warped_stem", a(MaterialMapColor.ae));
   public static final Block og = a("stripped_warped_stem", a(MaterialMapColor.ae));
   public static final Block oh = a("warped_hyphae", new BlockRotatable(BlockBase.Info.a(Material.A, MaterialMapColor.af).d(2.0F).a(SoundEffectType.A)));
   public static final Block oi = a(
      "stripped_warped_hyphae", new BlockRotatable(BlockBase.Info.a(Material.A, MaterialMapColor.af).d(2.0F).a(SoundEffectType.A))
   );
   public static final Block oj = a("warped_nylium", new BlockNylium(BlockBase.Info.a(Material.J, MaterialMapColor.ad).h().d(0.4F).a(SoundEffectType.B).d()));
   public static final Block ok = a(
      "warped_fungus", new BlockFungi(BlockBase.Info.a(Material.e, MaterialMapColor.x).c().a().a(SoundEffectType.C), TreeFeatures.d, oj)
   );
   public static final Block ol = a("warped_wart_block", new Block(BlockBase.Info.a(Material.u, MaterialMapColor.ag).d(1.0F).a(SoundEffectType.K)));
   public static final Block om = a(
      "warped_roots", new BlockRoots(BlockBase.Info.a(Material.h, MaterialMapColor.x).a().c().a(SoundEffectType.D).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block on = a(
      "nether_sprouts", new BlockNetherSprouts(BlockBase.Info.a(Material.h, MaterialMapColor.x).a().c().a(SoundEffectType.N).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block oo = a("crimson_stem", a(MaterialMapColor.ab));
   public static final Block op = a("stripped_crimson_stem", a(MaterialMapColor.ab));
   public static final Block oq = a("crimson_hyphae", new BlockRotatable(BlockBase.Info.a(Material.A, MaterialMapColor.ac).d(2.0F).a(SoundEffectType.A)));
   public static final Block or = a(
      "stripped_crimson_hyphae", new BlockRotatable(BlockBase.Info.a(Material.A, MaterialMapColor.ac).d(2.0F).a(SoundEffectType.A))
   );
   public static final Block os = a("crimson_nylium", new BlockNylium(BlockBase.Info.a(Material.J, MaterialMapColor.aa).h().d(0.4F).a(SoundEffectType.B).d()));
   public static final Block ot = a(
      "crimson_fungus", new BlockFungi(BlockBase.Info.a(Material.e, MaterialMapColor.J).c().a().a(SoundEffectType.C), TreeFeatures.b, os)
   );
   public static final Block ou = a("shroomlight", new Block(BlockBase.Info.a(Material.u, MaterialMapColor.C).d(1.0F).a(SoundEffectType.E).a(var0 -> 15)));
   public static final Block ov = a("weeping_vines", new BlockWeepingVines(BlockBase.Info.a(Material.e, MaterialMapColor.J).d().a().c().a(SoundEffectType.F)));
   public static final Block ow = a(
      "weeping_vines_plant", new BlockWeepingVinesPlant(BlockBase.Info.a(Material.e, MaterialMapColor.J).a().c().a(SoundEffectType.F))
   );
   public static final Block ox = a(
      "twisting_vines", new BlockTwistingVines(BlockBase.Info.a(Material.e, MaterialMapColor.x).d().a().c().a(SoundEffectType.F))
   );
   public static final Block oy = a(
      "twisting_vines_plant", new BlockTwistingVinesPlant(BlockBase.Info.a(Material.e, MaterialMapColor.x).a().c().a(SoundEffectType.F))
   );
   public static final Block oz = a(
      "crimson_roots", new BlockRoots(BlockBase.Info.a(Material.h, MaterialMapColor.J).a().c().a(SoundEffectType.D).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block oA = a("crimson_planks", new Block(BlockBase.Info.a(Material.A, MaterialMapColor.ab).a(2.0F, 3.0F).a(SoundEffectType.aO)));
   public static final Block oB = a("warped_planks", new Block(BlockBase.Info.a(Material.A, MaterialMapColor.ae).a(2.0F, 3.0F).a(SoundEffectType.aO)));
   public static final Block oC = a("crimson_slab", new BlockStepAbstract(BlockBase.Info.a(Material.A, oA.t()).a(2.0F, 3.0F).a(SoundEffectType.aO)));
   public static final Block oD = a("warped_slab", new BlockStepAbstract(BlockBase.Info.a(Material.A, oB.t()).a(2.0F, 3.0F).a(SoundEffectType.aO)));
   public static final Block oE = a(
      "crimson_pressure_plate",
      new BlockPressurePlateBinary(BlockPressurePlateBinary.EnumMobType.a, BlockBase.Info.a(Material.A, oA.t()).a().d(0.5F), BlockSetType.l)
   );
   public static final Block oF = a(
      "warped_pressure_plate",
      new BlockPressurePlateBinary(BlockPressurePlateBinary.EnumMobType.a, BlockBase.Info.a(Material.A, oB.t()).a().d(0.5F), BlockSetType.m)
   );
   public static final Block oG = a("crimson_fence", new BlockFence(BlockBase.Info.a(Material.A, oA.t()).a(2.0F, 3.0F).a(SoundEffectType.aO)));
   public static final Block oH = a("warped_fence", new BlockFence(BlockBase.Info.a(Material.A, oB.t()).a(2.0F, 3.0F).a(SoundEffectType.aO)));
   public static final Block oI = a("crimson_trapdoor", new BlockTrapdoor(BlockBase.Info.a(Material.A, oA.t()).d(3.0F).b().a(Blocks::a), BlockSetType.l));
   public static final Block oJ = a("warped_trapdoor", new BlockTrapdoor(BlockBase.Info.a(Material.A, oB.t()).d(3.0F).b().a(Blocks::a), BlockSetType.m));
   public static final Block oK = a("crimson_fence_gate", new BlockFenceGate(BlockBase.Info.a(Material.A, oA.t()).a(2.0F, 3.0F), BlockPropertyWood.h));
   public static final Block oL = a("warped_fence_gate", new BlockFenceGate(BlockBase.Info.a(Material.A, oB.t()).a(2.0F, 3.0F), BlockPropertyWood.i));
   public static final Block oM = a("crimson_stairs", new BlockStairs(oA.o(), BlockBase.Info.a((BlockBase)oA)));
   public static final Block oN = a("warped_stairs", new BlockStairs(oB.o(), BlockBase.Info.a((BlockBase)oB)));
   public static final Block oO = a("crimson_button", a(BlockSetType.l));
   public static final Block oP = a("warped_button", a(BlockSetType.m));
   public static final Block oQ = a("crimson_door", new BlockDoor(BlockBase.Info.a(Material.A, oA.t()).d(3.0F).b(), BlockSetType.l));
   public static final Block oR = a("warped_door", new BlockDoor(BlockBase.Info.a(Material.A, oB.t()).d(3.0F).b(), BlockSetType.m));
   public static final Block oS = a("crimson_sign", new BlockFloorSign(BlockBase.Info.a(Material.A, oA.t()).a().d(1.0F), BlockPropertyWood.h));
   public static final Block oT = a("warped_sign", new BlockFloorSign(BlockBase.Info.a(Material.A, oB.t()).a().d(1.0F), BlockPropertyWood.i));
   public static final Block oU = a("crimson_wall_sign", new BlockWallSign(BlockBase.Info.a(Material.A, oA.t()).a().d(1.0F).a(oS), BlockPropertyWood.h));
   public static final Block oV = a("warped_wall_sign", new BlockWallSign(BlockBase.Info.a(Material.A, oB.t()).a().d(1.0F).a(oT), BlockPropertyWood.i));
   public static final Block oW = a("structure_block", new BlockStructure(BlockBase.Info.a(Material.K, MaterialMapColor.w).h().a(-1.0F, 3600000.0F).f()));
   public static final Block oX = a("jigsaw", new BlockJigsaw(BlockBase.Info.a(Material.K, MaterialMapColor.w).h().a(-1.0F, 3600000.0F).f()));
   public static final Block oY = a("composter", new BlockComposter(BlockBase.Info.a(Material.z).d(0.6F).a(SoundEffectType.a)));
   public static final Block oZ = a("target", new BlockTarget(BlockBase.Info.a(Material.u, MaterialMapColor.o).d(0.5F).a(SoundEffectType.c)));
   public static final Block pa = a("bee_nest", new BlockBeehive(BlockBase.Info.a(Material.z, MaterialMapColor.s).d(0.3F).a(SoundEffectType.a)));
   public static final Block pb = a("beehive", new BlockBeehive(BlockBase.Info.a(Material.z).d(0.6F).a(SoundEffectType.a)));
   public static final Block pc = a("honey_block", new BlockHoney(BlockBase.Info.a(Material.s, MaterialMapColor.p).b(0.4F).c(0.5F).b().a(SoundEffectType.o)));
   public static final Block pd = a("honeycomb_block", new Block(BlockBase.Info.a(Material.s, MaterialMapColor.p).d(0.6F).a(SoundEffectType.q)));
   public static final Block pe = a("netherite_block", new Block(BlockBase.Info.a(Material.K, MaterialMapColor.D).h().a(50.0F, 1200.0F).a(SoundEffectType.Q)));
   public static final Block pf = a("ancient_debris", new Block(BlockBase.Info.a(Material.K, MaterialMapColor.D).h().a(30.0F, 1200.0F).a(SoundEffectType.R)));
   public static final Block pg = a(
      "crying_obsidian", new BlockCryingObsidian(BlockBase.Info.a(Material.J, MaterialMapColor.D).h().a(50.0F, 1200.0F).a(var0 -> 10))
   );
   public static final Block ph = a(
      "respawn_anchor",
      new BlockRespawnAnchor(BlockBase.Info.a(Material.J, MaterialMapColor.D).h().a(50.0F, 1200.0F).a(var0 -> BlockRespawnAnchor.a(var0, 15)))
   );
   public static final Block pi = a("potted_crimson_fungus", new BlockFlowerPot(ot, BlockBase.Info.a(Material.o).c().b()));
   public static final Block pj = a("potted_warped_fungus", new BlockFlowerPot(ok, BlockBase.Info.a(Material.o).c().b()));
   public static final Block pk = a("potted_crimson_roots", new BlockFlowerPot(oz, BlockBase.Info.a(Material.o).c().b()));
   public static final Block pl = a("potted_warped_roots", new BlockFlowerPot(om, BlockBase.Info.a(Material.o).c().b()));
   public static final Block pm = a("lodestone", new Block(BlockBase.Info.a(Material.M).h().d(3.5F).a(SoundEffectType.S)));
   public static final Block pn = a("blackstone", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.D).h().a(1.5F, 6.0F)));
   public static final Block po = a("blackstone_stairs", new BlockStairs(pn.o(), BlockBase.Info.a((BlockBase)pn)));
   public static final Block pp = a("blackstone_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)pn)));
   public static final Block pq = a("blackstone_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)pn).a(2.0F, 6.0F)));
   public static final Block pr = a("polished_blackstone", new Block(BlockBase.Info.a((BlockBase)pn).a(2.0F, 6.0F)));
   public static final Block ps = a("polished_blackstone_bricks", new Block(BlockBase.Info.a((BlockBase)pr).a(1.5F, 6.0F)));
   public static final Block pt = a("cracked_polished_blackstone_bricks", new Block(BlockBase.Info.a((BlockBase)ps)));
   public static final Block pu = a("chiseled_polished_blackstone", new Block(BlockBase.Info.a((BlockBase)pr).a(1.5F, 6.0F)));
   public static final Block pv = a("polished_blackstone_brick_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)ps).a(2.0F, 6.0F)));
   public static final Block pw = a("polished_blackstone_brick_stairs", new BlockStairs(ps.o(), BlockBase.Info.a((BlockBase)ps)));
   public static final Block px = a("polished_blackstone_brick_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)ps)));
   public static final Block py = a("gilded_blackstone", new Block(BlockBase.Info.a((BlockBase)pn).a(SoundEffectType.V)));
   public static final Block pz = a("polished_blackstone_stairs", new BlockStairs(pr.o(), BlockBase.Info.a((BlockBase)pr)));
   public static final Block pA = a("polished_blackstone_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)pr)));
   public static final Block pB = a(
      "polished_blackstone_pressure_plate",
      new BlockPressurePlateBinary(BlockPressurePlateBinary.EnumMobType.b, BlockBase.Info.a(Material.J, MaterialMapColor.D).h().a().d(0.5F), BlockSetType.d)
   );
   public static final Block pC = a("polished_blackstone_button", b());
   public static final Block pD = a("polished_blackstone_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)pr)));
   public static final Block pE = a(
      "chiseled_nether_bricks", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.J).h().a(2.0F, 6.0F).a(SoundEffectType.M))
   );
   public static final Block pF = a(
      "cracked_nether_bricks", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.J).h().a(2.0F, 6.0F).a(SoundEffectType.M))
   );
   public static final Block pG = a("quartz_bricks", new Block(BlockBase.Info.a((BlockBase)hc)));
   public static final Block pH = a(
      "candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.c).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pI = a(
      "white_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.d).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pJ = a(
      "orange_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.p).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pK = a(
      "magenta_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.q).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pL = a(
      "light_blue_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.r).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pM = a(
      "yellow_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.s).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pN = a(
      "lime_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.t).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pO = a(
      "pink_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.u).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pP = a(
      "gray_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.v).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pQ = a(
      "light_gray_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.w).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pR = a(
      "cyan_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.x).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pS = a(
      "purple_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.y).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pT = a(
      "blue_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.z).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pU = a(
      "brown_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.A).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pV = a(
      "green_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.B).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pW = a(
      "red_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.C).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pX = a(
      "black_candle", new CandleBlock(BlockBase.Info.a(Material.o, MaterialMapColor.D).b().d(0.1F).a(SoundEffectType.W).a(CandleBlock.h))
   );
   public static final Block pY = a("candle_cake", new CandleCakeBlock(pH, BlockBase.Info.a((BlockBase)eg).a(a(3))));
   public static final Block pZ = a("white_candle_cake", new CandleCakeBlock(pI, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qa = a("orange_candle_cake", new CandleCakeBlock(pJ, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qb = a("magenta_candle_cake", new CandleCakeBlock(pK, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qc = a("light_blue_candle_cake", new CandleCakeBlock(pL, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qd = a("yellow_candle_cake", new CandleCakeBlock(pM, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qe = a("lime_candle_cake", new CandleCakeBlock(pN, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qf = a("pink_candle_cake", new CandleCakeBlock(pO, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qg = a("gray_candle_cake", new CandleCakeBlock(pP, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qh = a("light_gray_candle_cake", new CandleCakeBlock(pQ, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qi = a("cyan_candle_cake", new CandleCakeBlock(pR, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qj = a("purple_candle_cake", new CandleCakeBlock(pS, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qk = a("blue_candle_cake", new CandleCakeBlock(pT, BlockBase.Info.a((BlockBase)pY)));
   public static final Block ql = a("brown_candle_cake", new CandleCakeBlock(pU, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qm = a("green_candle_cake", new CandleCakeBlock(pV, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qn = a("red_candle_cake", new CandleCakeBlock(pW, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qo = a("black_candle_cake", new CandleCakeBlock(pX, BlockBase.Info.a((BlockBase)pY)));
   public static final Block qp = a("amethyst_block", new AmethystBlock(BlockBase.Info.a(Material.T, MaterialMapColor.y).d(1.5F).a(SoundEffectType.X).h()));
   public static final Block qq = a("budding_amethyst", new BuddingAmethystBlock(BlockBase.Info.a(Material.T).d().d(1.5F).a(SoundEffectType.X).h()));
   public static final Block qr = a(
      "amethyst_cluster", new AmethystClusterBlock(7, 3, BlockBase.Info.a(Material.T).b().d().a(SoundEffectType.Y).d(1.5F).a(var0 -> 5))
   );
   public static final Block qs = a("large_amethyst_bud", new AmethystClusterBlock(5, 3, BlockBase.Info.a((BlockBase)qr).a(SoundEffectType.aa).a(var0 -> 4)));
   public static final Block qt = a("medium_amethyst_bud", new AmethystClusterBlock(4, 3, BlockBase.Info.a((BlockBase)qr).a(SoundEffectType.ab).a(var0 -> 2)));
   public static final Block qu = a("small_amethyst_bud", new AmethystClusterBlock(3, 4, BlockBase.Info.a((BlockBase)qr).a(SoundEffectType.Z).a(var0 -> 1)));
   public static final Block qv = a("tuff", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.R).a(SoundEffectType.ac).h().a(1.5F, 6.0F)));
   public static final Block qw = a("calcite", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.K).a(SoundEffectType.ad).h().d(0.75F)));
   public static final Block qx = a(
      "tinted_glass", new TintedGlassBlock(BlockBase.Info.a((BlockBase)aP).a(MaterialMapColor.v).b().a(Blocks::a).a(Blocks::b).b(Blocks::b).c(Blocks::b))
   );
   public static final Block qy = a("powder_snow", new PowderSnowBlock(BlockBase.Info.a(Material.U).d(0.25F).a(SoundEffectType.k).e()));
   public static final Block qz = a(
      "sculk_sensor",
      new SculkSensorBlock(
         BlockBase.Info.a(Material.q, MaterialMapColor.x)
            .d(1.5F)
            .a(SoundEffectType.at)
            .a(var0 -> 1)
            .e((var0, var1x, var2x) -> SculkSensorBlock.h(var0) == SculkSensorPhase.b),
         8
      )
   );
   public static final Block qA = a("sculk", new SculkBlock(BlockBase.Info.a(Material.q).d(0.2F).a(SoundEffectType.av)));
   public static final Block qB = a("sculk_vein", new SculkVeinBlock(BlockBase.Info.a(Material.q).a().d(0.2F).a(SoundEffectType.aw)));
   public static final Block qC = a("sculk_catalyst", new SculkCatalystBlock(BlockBase.Info.a(Material.q).a(3.0F, 3.0F).a(SoundEffectType.au).a(var0 -> 6)));
   public static final Block qD = a(
      "sculk_shrieker", new SculkShriekerBlock(BlockBase.Info.a(Material.q, MaterialMapColor.D).a(3.0F, 3.0F).a(SoundEffectType.ax))
   );
   public static final Block qE = a(
      "oxidized_copper",
      new WeatheringCopperFullBlock(WeatheringCopper.a.d, BlockBase.Info.a(Material.K, MaterialMapColor.ad).h().a(3.0F, 6.0F).a(SoundEffectType.ag))
   );
   public static final Block qF = a(
      "weathered_copper",
      new WeatheringCopperFullBlock(WeatheringCopper.a.c, BlockBase.Info.a(Material.K, MaterialMapColor.ae).h().a(3.0F, 6.0F).a(SoundEffectType.ag))
   );
   public static final Block qG = a(
      "exposed_copper",
      new WeatheringCopperFullBlock(WeatheringCopper.a.b, BlockBase.Info.a(Material.K, MaterialMapColor.S).h().a(3.0F, 6.0F).a(SoundEffectType.ag))
   );
   public static final Block qH = a(
      "copper_block",
      new WeatheringCopperFullBlock(WeatheringCopper.a.a, BlockBase.Info.a(Material.K, MaterialMapColor.p).h().a(3.0F, 6.0F).a(SoundEffectType.ag))
   );
   public static final Block qI = a("copper_ore", new DropExperienceBlock(BlockBase.Info.a((BlockBase)O)));
   public static final Block qJ = a(
      "deepslate_copper_ore", new DropExperienceBlock(BlockBase.Info.a((BlockBase)qI).a(MaterialMapColor.ah).a(4.5F, 3.0F).a(SoundEffectType.az))
   );
   public static final Block qK = a("oxidized_cut_copper", new WeatheringCopperFullBlock(WeatheringCopper.a.d, BlockBase.Info.a((BlockBase)qE)));
   public static final Block qL = a("weathered_cut_copper", new WeatheringCopperFullBlock(WeatheringCopper.a.c, BlockBase.Info.a((BlockBase)qF)));
   public static final Block qM = a("exposed_cut_copper", new WeatheringCopperFullBlock(WeatheringCopper.a.b, BlockBase.Info.a((BlockBase)qG)));
   public static final Block qN = a("cut_copper", new WeatheringCopperFullBlock(WeatheringCopper.a.a, BlockBase.Info.a((BlockBase)qH)));
   public static final Block qO = a(
      "oxidized_cut_copper_stairs", new WeatheringCopperStairBlock(WeatheringCopper.a.d, qK.o(), BlockBase.Info.a((BlockBase)qK))
   );
   public static final Block qP = a(
      "weathered_cut_copper_stairs", new WeatheringCopperStairBlock(WeatheringCopper.a.c, qL.o(), BlockBase.Info.a((BlockBase)qF))
   );
   public static final Block qQ = a("exposed_cut_copper_stairs", new WeatheringCopperStairBlock(WeatheringCopper.a.b, qM.o(), BlockBase.Info.a((BlockBase)qG)));
   public static final Block qR = a("cut_copper_stairs", new WeatheringCopperStairBlock(WeatheringCopper.a.a, qN.o(), BlockBase.Info.a((BlockBase)qH)));
   public static final Block qS = a("oxidized_cut_copper_slab", new WeatheringCopperSlabBlock(WeatheringCopper.a.d, BlockBase.Info.a((BlockBase)qK).h()));
   public static final Block qT = a("weathered_cut_copper_slab", new WeatheringCopperSlabBlock(WeatheringCopper.a.c, BlockBase.Info.a((BlockBase)qL).h()));
   public static final Block qU = a("exposed_cut_copper_slab", new WeatheringCopperSlabBlock(WeatheringCopper.a.b, BlockBase.Info.a((BlockBase)qM).h()));
   public static final Block qV = a("cut_copper_slab", new WeatheringCopperSlabBlock(WeatheringCopper.a.a, BlockBase.Info.a((BlockBase)qN).h()));
   public static final Block qW = a("waxed_copper_block", new Block(BlockBase.Info.a((BlockBase)qH)));
   public static final Block qX = a("waxed_weathered_copper", new Block(BlockBase.Info.a((BlockBase)qF)));
   public static final Block qY = a("waxed_exposed_copper", new Block(BlockBase.Info.a((BlockBase)qG)));
   public static final Block qZ = a("waxed_oxidized_copper", new Block(BlockBase.Info.a((BlockBase)qE)));
   public static final Block ra = a("waxed_oxidized_cut_copper", new Block(BlockBase.Info.a((BlockBase)qE)));
   public static final Block rb = a("waxed_weathered_cut_copper", new Block(BlockBase.Info.a((BlockBase)qF)));
   public static final Block rc = a("waxed_exposed_cut_copper", new Block(BlockBase.Info.a((BlockBase)qG)));
   public static final Block rd = a("waxed_cut_copper", new Block(BlockBase.Info.a((BlockBase)qH)));
   public static final Block re = a("waxed_oxidized_cut_copper_stairs", new BlockStairs(ra.o(), BlockBase.Info.a((BlockBase)qE)));
   public static final Block rf = a("waxed_weathered_cut_copper_stairs", new BlockStairs(rb.o(), BlockBase.Info.a((BlockBase)qF)));
   public static final Block rg = a("waxed_exposed_cut_copper_stairs", new BlockStairs(rc.o(), BlockBase.Info.a((BlockBase)qG)));
   public static final Block rh = a("waxed_cut_copper_stairs", new BlockStairs(rd.o(), BlockBase.Info.a((BlockBase)qH)));
   public static final Block ri = a("waxed_oxidized_cut_copper_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)ra).h()));
   public static final Block rj = a("waxed_weathered_cut_copper_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)rb).h()));
   public static final Block rk = a("waxed_exposed_cut_copper_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)rc).h()));
   public static final Block rl = a("waxed_cut_copper_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)rd).h()));
   public static final Block rm = a(
      "lightning_rod", new LightningRodBlock(BlockBase.Info.a(Material.K, MaterialMapColor.p).h().a(3.0F, 6.0F).a(SoundEffectType.ag).b())
   );
   public static final Block rn = a(
      "pointed_dripstone",
      new PointedDripstoneBlock(
         BlockBase.Info.a(Material.J, MaterialMapColor.W).b().a(SoundEffectType.af).d().a(1.5F, 3.0F).e().a(BlockBase.EnumRandomOffset.b)
      )
   );
   public static final Block ro = a("dripstone_block", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.W).a(SoundEffectType.ae).h().a(1.5F, 1.0F)));
   public static final Block rp = a("cave_vines", new CaveVinesBlock(BlockBase.Info.a(Material.e).d().a().a(CaveVines.h_(14)).c().a(SoundEffectType.ah)));
   public static final Block rq = a(
      "cave_vines_plant", new CaveVinesPlantBlock(BlockBase.Info.a(Material.e).a().a(CaveVines.h_(14)).c().a(SoundEffectType.ah))
   );
   public static final Block rr = a("spore_blossom", new SporeBlossomBlock(BlockBase.Info.a(Material.e).c().a().a(SoundEffectType.ai)));
   public static final Block rs = a("azalea", new AzaleaBlock(BlockBase.Info.a(Material.e).c().a(SoundEffectType.aj).b()));
   public static final Block rt = a("flowering_azalea", new AzaleaBlock(BlockBase.Info.a(Material.e).c().a(SoundEffectType.ak).b()));
   public static final Block ru = a("moss_carpet", new CarpetBlock(BlockBase.Info.a(Material.e, MaterialMapColor.B).d(0.1F).a(SoundEffectType.al)));
   public static final Block rv = a("pink_petals", new PinkPetalsBlock(BlockBase.Info.a(Material.e).a().a(SoundEffectType.am).a(FeatureFlags.c)));
   public static final Block rw = a("moss_block", new MossBlock(BlockBase.Info.a(Material.P, MaterialMapColor.B).d(0.1F).a(SoundEffectType.an)));
   public static final Block rx = a("big_dripleaf", new BigDripleafBlock(BlockBase.Info.a(Material.e).d(0.1F).a(SoundEffectType.ao)));
   public static final Block ry = a("big_dripleaf_stem", new BigDripleafStemBlock(BlockBase.Info.a(Material.e).a().d(0.1F).a(SoundEffectType.ao)));
   public static final Block rz = a(
      "small_dripleaf", new SmallDripleafBlock(BlockBase.Info.a(Material.e).a().c().a(SoundEffectType.ap).a(BlockBase.EnumRandomOffset.c))
   );
   public static final Block rA = a(
      "hanging_roots", new HangingRootsBlock(BlockBase.Info.a(Material.g, MaterialMapColor.k).a().c().a(SoundEffectType.ar).a(BlockBase.EnumRandomOffset.b))
   );
   public static final Block rB = a("rooted_dirt", new RootedDirtBlock(BlockBase.Info.a(Material.t, MaterialMapColor.k).d(0.5F).a(SoundEffectType.aq)));
   public static final Block rC = a(
      "mud", new MudBlock(BlockBase.Info.a((BlockBase)j).a(MaterialMapColor.T).a(Blocks::b).a(Blocks::a).c(Blocks::a).b(Blocks::a).a(SoundEffectType.aH))
   );
   public static final Block rD = a("deepslate", new BlockRotatable(BlockBase.Info.a(Material.J, MaterialMapColor.ah).h().a(3.0F, 6.0F).a(SoundEffectType.az)));
   public static final Block rE = a("cobbled_deepslate", new Block(BlockBase.Info.a((BlockBase)rD).a(3.5F, 6.0F)));
   public static final Block rF = a("cobbled_deepslate_stairs", new BlockStairs(rE.o(), BlockBase.Info.a((BlockBase)rE)));
   public static final Block rG = a("cobbled_deepslate_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)rE)));
   public static final Block rH = a("cobbled_deepslate_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)rE)));
   public static final Block rI = a("polished_deepslate", new Block(BlockBase.Info.a((BlockBase)rE).a(SoundEffectType.aC)));
   public static final Block rJ = a("polished_deepslate_stairs", new BlockStairs(rI.o(), BlockBase.Info.a((BlockBase)rI)));
   public static final Block rK = a("polished_deepslate_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)rI)));
   public static final Block rL = a("polished_deepslate_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)rI)));
   public static final Block rM = a("deepslate_tiles", new Block(BlockBase.Info.a((BlockBase)rE).a(SoundEffectType.aB)));
   public static final Block rN = a("deepslate_tile_stairs", new BlockStairs(rM.o(), BlockBase.Info.a((BlockBase)rM)));
   public static final Block rO = a("deepslate_tile_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)rM)));
   public static final Block rP = a("deepslate_tile_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)rM)));
   public static final Block rQ = a("deepslate_bricks", new Block(BlockBase.Info.a((BlockBase)rE).a(SoundEffectType.aA)));
   public static final Block rR = a("deepslate_brick_stairs", new BlockStairs(rQ.o(), BlockBase.Info.a((BlockBase)rQ)));
   public static final Block rS = a("deepslate_brick_slab", new BlockStepAbstract(BlockBase.Info.a((BlockBase)rQ)));
   public static final Block rT = a("deepslate_brick_wall", new BlockCobbleWall(BlockBase.Info.a((BlockBase)rQ)));
   public static final Block rU = a("chiseled_deepslate", new Block(BlockBase.Info.a((BlockBase)rE).a(SoundEffectType.aA)));
   public static final Block rV = a("cracked_deepslate_bricks", new Block(BlockBase.Info.a((BlockBase)rQ)));
   public static final Block rW = a("cracked_deepslate_tiles", new Block(BlockBase.Info.a((BlockBase)rM)));
   public static final Block rX = a(
      "infested_deepslate", new InfestedRotatedPillarBlock(rD, BlockBase.Info.a(Material.s, MaterialMapColor.ah).a(SoundEffectType.az))
   );
   public static final Block rY = a("smooth_basalt", new Block(BlockBase.Info.a((BlockBase)dY)));
   public static final Block rZ = a("raw_iron_block", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.ai).h().a(5.0F, 6.0F)));
   public static final Block sa = a("raw_copper_block", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.p).h().a(5.0F, 6.0F)));
   public static final Block sb = a("raw_gold_block", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.E).h().a(5.0F, 6.0F)));
   public static final Block sc = a("potted_azalea_bush", new BlockFlowerPot(rs, BlockBase.Info.a(Material.o).c().b()));
   public static final Block sd = a("potted_flowering_azalea_bush", new BlockFlowerPot(rt, BlockBase.Info.a(Material.o).c().b()));
   public static final Block se = a(
      "ochre_froglight", new BlockRotatable(BlockBase.Info.a(Material.W, MaterialMapColor.c).d(0.3F).a(var0 -> 15).a(SoundEffectType.aD))
   );
   public static final Block sf = a(
      "verdant_froglight", new BlockRotatable(BlockBase.Info.a(Material.W, MaterialMapColor.aj).d(0.3F).a(var0 -> 15).a(SoundEffectType.aD))
   );
   public static final Block sg = a(
      "pearlescent_froglight", new BlockRotatable(BlockBase.Info.a(Material.W, MaterialMapColor.u).d(0.3F).a(var0 -> 15).a(SoundEffectType.aD))
   );
   public static final Block sh = a("frogspawn", new FrogspawnBlock(BlockBase.Info.a(Material.V).c().b().a().a(SoundEffectType.aE)));
   public static final Block si = a(
      "reinforced_deepslate", new Block(BlockBase.Info.a(Material.J, MaterialMapColor.ah).a(SoundEffectType.az).a(55.0F, 1200.0F))
   );
   public static final Block sj = a(
      "decorated_pot", new DecoratedPotBlock(BlockBase.Info.a(Material.X).a(0.0F, 0.0F).a(SoundEffectType.aV).a(FeatureFlags.c).b())
   );

   private static ToIntFunction<IBlockData> a(int var0) {
      return var1 -> var1.c(BlockProperties.r) ? var0 : 0;
   }

   private static Boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, EntityTypes<?> var3) {
      return false;
   }

   private static Boolean b(IBlockData var0, IBlockAccess var1, BlockPosition var2, EntityTypes<?> var3) {
      return true;
   }

   private static Boolean c(IBlockData var0, IBlockAccess var1, BlockPosition var2, EntityTypes<?> var3) {
      return var3 == EntityTypes.aq || var3 == EntityTypes.at;
   }

   private static BlockBed a(EnumColor var0) {
      return new BlockBed(
         var0,
         BlockBase.Info.a(Material.D, var1 -> var1.c(BlockBed.a) == BlockPropertyBedPart.b ? var0.e() : MaterialMapColor.d).a(SoundEffectType.a).d(0.2F).b()
      );
   }

   private static BlockRotatable a(MaterialMapColor var0, MaterialMapColor var1) {
      return new BlockRotatable(
         BlockBase.Info.a(Material.z, var2 -> var2.c(BlockRotatable.g) == EnumDirection.EnumAxis.b ? var0 : var1).d(2.0F).a(SoundEffectType.a)
      );
   }

   private static BlockRotatable a(MaterialMapColor var0, MaterialMapColor var1, SoundEffectType var2, FeatureFlag... var3) {
      return new BlockRotatable(
         BlockBase.Info.a(Material.z, var2x -> var2x.c(BlockRotatable.g) == EnumDirection.EnumAxis.b ? var0 : var1).d(2.0F).a(var2).a(var3)
      );
   }

   private static Block a(MaterialMapColor var0) {
      return new BlockRotatable(BlockBase.Info.a(Material.A, var1 -> var0).d(2.0F).a(SoundEffectType.A));
   }

   private static boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return true;
   }

   private static boolean b(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return false;
   }

   private static BlockStainedGlass b(EnumColor var0) {
      return new BlockStainedGlass(
         var0, BlockBase.Info.a(Material.G, var0).d(0.3F).a(SoundEffectType.g).b().a(Blocks::a).a(Blocks::b).b(Blocks::b).c(Blocks::b)
      );
   }

   private static BlockLeaves a(SoundEffectType var0) {
      return new BlockLeaves(BlockBase.Info.a(Material.F).d(0.2F).d().a(var0).b().a(Blocks::c).b(Blocks::b).c(Blocks::b));
   }

   private static BlockShulkerBox a(EnumColor var0, BlockBase.Info var1) {
      BlockBase.f var2 = (var0x, var1x, var2x) -> {
         TileEntity var3 = var1x.c_(var2x);
         if (!(var3 instanceof TileEntityShulkerBox)) {
            return true;
         } else {
            TileEntityShulkerBox var4 = (TileEntityShulkerBox)var3;
            return var4.v();
         }
      };
      return new BlockShulkerBox(var0, var1.d(2.0F).e().b().b(var2).c(var2));
   }

   private static BlockPiston a(boolean var0) {
      BlockBase.f var1 = (var0x, var1x, var2) -> !var0x.c(BlockPiston.b);
      return new BlockPiston(var0, BlockBase.Info.a(Material.O).d(1.5F).a(Blocks::b).b(var1).c(var1));
   }

   private static BlockButtonAbstract a(BlockSetType var0) {
      return new BlockButtonAbstract(BlockBase.Info.a(Material.o).a().d(0.5F), var0, 30, true);
   }

   private static BlockButtonAbstract b() {
      return new BlockButtonAbstract(BlockBase.Info.a(Material.o).a().d(0.5F), BlockSetType.c, 20, false);
   }

   private static Block a(String var0, Block var1) {
      return IRegistry.a(BuiltInRegistries.f, var0, var1);
   }

   public static void a() {
      Block.o.forEach(BlockBase.BlockData::a);
   }

   static {
      for(Block var1 : BuiltInRegistries.f) {
         UnmodifiableIterator var2 = var1.n().a().iterator();

         while(var2.hasNext()) {
            IBlockData var3 = (IBlockData)var2.next();
            Block.o.b(var3);
            var3.a();
         }

         var1.s();
      }
   }
}
