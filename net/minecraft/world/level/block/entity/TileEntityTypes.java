package net.minecraft.world.level.block.entity;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.types.Type;
import com.mojang.logging.LogUtils;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.datafix.fixes.DataConverterTypes;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.TileEntityPiston;
import net.minecraft.world.level.block.state.IBlockData;
import org.slf4j.Logger;

public class TileEntityTypes<T extends TileEntity> {
   private static final Logger O = LogUtils.getLogger();
   public static final TileEntityTypes<TileEntityFurnaceFurnace> a = a("furnace", TileEntityTypes.b.a(TileEntityFurnaceFurnace::new, Blocks.cC));
   public static final TileEntityTypes<TileEntityChest> b = a("chest", TileEntityTypes.b.a(TileEntityChest::new, Blocks.cu));
   public static final TileEntityTypes<TileEntityChestTrapped> c = a("trapped_chest", TileEntityTypes.b.a(TileEntityChestTrapped::new, Blocks.gU));
   public static final TileEntityTypes<TileEntityEnderChest> d = a("ender_chest", TileEntityTypes.b.a(TileEntityEnderChest::new, Blocks.fF));
   public static final TileEntityTypes<TileEntityJukeBox> e = a("jukebox", TileEntityTypes.b.a(TileEntityJukeBox::new, Blocks.dS));
   public static final TileEntityTypes<TileEntityDispenser> f = a("dispenser", TileEntityTypes.b.a(TileEntityDispenser::new, Blocks.aT));
   public static final TileEntityTypes<TileEntityDropper> g = a("dropper", TileEntityTypes.b.a(TileEntityDropper::new, Blocks.hh));
   public static final TileEntityTypes<TileEntitySign> h = a(
      "sign",
      TileEntityTypes.b.a(
         TileEntitySign::new,
         Blocks.cD,
         Blocks.cE,
         Blocks.cF,
         Blocks.cG,
         Blocks.cH,
         Blocks.cI,
         Blocks.cJ,
         Blocks.cQ,
         Blocks.cR,
         Blocks.cS,
         Blocks.cT,
         Blocks.cU,
         Blocks.cV,
         Blocks.cW,
         Blocks.oS,
         Blocks.oU,
         Blocks.oT,
         Blocks.oV,
         Blocks.cK,
         Blocks.cX,
         Blocks.cL,
         Blocks.cY
      )
   );
   public static final TileEntityTypes<HangingSignBlockEntity> i = a(
      "hanging_sign",
      TileEntityTypes.b.a(
         HangingSignBlockEntity::new,
         Blocks.cZ,
         Blocks.da,
         Blocks.db,
         Blocks.dc,
         Blocks.dd,
         Blocks.de,
         Blocks.df,
         Blocks.dg,
         Blocks.dh,
         Blocks.di,
         Blocks.dj,
         Blocks.dk,
         Blocks.dl,
         Blocks.dm,
         Blocks.dn,
         Blocks.do,
         Blocks.dp,
         Blocks.dq,
         Blocks.ds,
         Blocks.dt,
         Blocks.dr,
         Blocks.du
      )
   );
   public static final TileEntityTypes<TileEntityMobSpawner> j = a("mob_spawner", TileEntityTypes.b.a(TileEntityMobSpawner::new, Blocks.cs));
   public static final TileEntityTypes<TileEntityPiston> k = a("piston", TileEntityTypes.b.a(TileEntityPiston::new, Blocks.bP));
   public static final TileEntityTypes<TileEntityBrewingStand> l = a("brewing_stand", TileEntityTypes.b.a(TileEntityBrewingStand::new, Blocks.fr));
   public static final TileEntityTypes<TileEntityEnchantTable> m = a("enchanting_table", TileEntityTypes.b.a(TileEntityEnchantTable::new, Blocks.fq));
   public static final TileEntityTypes<TileEntityEnderPortal> n = a("end_portal", TileEntityTypes.b.a(TileEntityEnderPortal::new, Blocks.fw));
   public static final TileEntityTypes<TileEntityBeacon> o = a("beacon", TileEntityTypes.b.a(TileEntityBeacon::new, Blocks.fN));
   public static final TileEntityTypes<TileEntitySkull> p = a(
      "skull",
      TileEntityTypes.b.a(
         TileEntitySkull::new,
         Blocks.gD,
         Blocks.gE,
         Blocks.gL,
         Blocks.gM,
         Blocks.gN,
         Blocks.gO,
         Blocks.gH,
         Blocks.gI,
         Blocks.gF,
         Blocks.gG,
         Blocks.gJ,
         Blocks.gK,
         Blocks.gP,
         Blocks.gQ
      )
   );
   public static final TileEntityTypes<TileEntityLightDetector> q = a("daylight_detector", TileEntityTypes.b.a(TileEntityLightDetector::new, Blocks.gY));
   public static final TileEntityTypes<TileEntityHopper> r = a("hopper", TileEntityTypes.b.a(TileEntityHopper::new, Blocks.hb));
   public static final TileEntityTypes<TileEntityComparator> s = a("comparator", TileEntityTypes.b.a(TileEntityComparator::new, Blocks.gX));
   public static final TileEntityTypes<TileEntityBanner> t = a(
      "banner",
      TileEntityTypes.b.a(
         TileEntityBanner::new,
         Blocks.iI,
         Blocks.iJ,
         Blocks.iK,
         Blocks.iL,
         Blocks.iM,
         Blocks.iN,
         Blocks.iO,
         Blocks.iP,
         Blocks.iQ,
         Blocks.iR,
         Blocks.iS,
         Blocks.iT,
         Blocks.iU,
         Blocks.iV,
         Blocks.iW,
         Blocks.iX,
         Blocks.iY,
         Blocks.iZ,
         Blocks.ja,
         Blocks.jb,
         Blocks.jc,
         Blocks.jd,
         Blocks.je,
         Blocks.jf,
         Blocks.jg,
         Blocks.jh,
         Blocks.ji,
         Blocks.jj,
         Blocks.jk,
         Blocks.jl,
         Blocks.jm,
         Blocks.jn
      )
   );
   public static final TileEntityTypes<TileEntityStructure> u = a("structure_block", TileEntityTypes.b.a(TileEntityStructure::new, Blocks.oW));
   public static final TileEntityTypes<TileEntityEndGateway> v = a("end_gateway", TileEntityTypes.b.a(TileEntityEndGateway::new, Blocks.kC));
   public static final TileEntityTypes<TileEntityCommand> w = a("command_block", TileEntityTypes.b.a(TileEntityCommand::new, Blocks.fM, Blocks.kE, Blocks.kD));
   public static final TileEntityTypes<TileEntityShulkerBox> x = a(
      "shulker_box",
      TileEntityTypes.b.a(
         TileEntityShulkerBox::new,
         Blocks.kM,
         Blocks.lc,
         Blocks.kY,
         Blocks.kZ,
         Blocks.kW,
         Blocks.kU,
         Blocks.la,
         Blocks.kQ,
         Blocks.kV,
         Blocks.kS,
         Blocks.kP,
         Blocks.kO,
         Blocks.kT,
         Blocks.kX,
         Blocks.lb,
         Blocks.kN,
         Blocks.kR
      )
   );
   public static final TileEntityTypes<TileEntityBed> y = a(
      "bed",
      TileEntityTypes.b.a(
         TileEntityBed::new,
         Blocks.bm,
         Blocks.bn,
         Blocks.bj,
         Blocks.bk,
         Blocks.bh,
         Blocks.bf,
         Blocks.bl,
         Blocks.bb,
         Blocks.bg,
         Blocks.bd,
         Blocks.ba,
         Blocks.aZ,
         Blocks.be,
         Blocks.bi,
         Blocks.aY,
         Blocks.bc
      )
   );
   public static final TileEntityTypes<TileEntityConduit> z = a("conduit", TileEntityTypes.b.a(TileEntityConduit::new, Blocks.mT));
   public static final TileEntityTypes<TileEntityBarrel> A = a("barrel", TileEntityTypes.b.a(TileEntityBarrel::new, Blocks.nQ));
   public static final TileEntityTypes<TileEntitySmoker> B = a("smoker", TileEntityTypes.b.a(TileEntitySmoker::new, Blocks.nR));
   public static final TileEntityTypes<TileEntityBlastFurnace> C = a("blast_furnace", TileEntityTypes.b.a(TileEntityBlastFurnace::new, Blocks.nS));
   public static final TileEntityTypes<TileEntityLectern> D = a("lectern", TileEntityTypes.b.a(TileEntityLectern::new, Blocks.nW));
   public static final TileEntityTypes<TileEntityBell> E = a("bell", TileEntityTypes.b.a(TileEntityBell::new, Blocks.nZ));
   public static final TileEntityTypes<TileEntityJigsaw> F = a("jigsaw", TileEntityTypes.b.a(TileEntityJigsaw::new, Blocks.oX));
   public static final TileEntityTypes<TileEntityCampfire> G = a("campfire", TileEntityTypes.b.a(TileEntityCampfire::new, Blocks.oc, Blocks.od));
   public static final TileEntityTypes<TileEntityBeehive> H = a("beehive", TileEntityTypes.b.a(TileEntityBeehive::new, Blocks.pa, Blocks.pb));
   public static final TileEntityTypes<SculkSensorBlockEntity> I = a("sculk_sensor", TileEntityTypes.b.a(SculkSensorBlockEntity::new, Blocks.qz));
   public static final TileEntityTypes<SculkCatalystBlockEntity> J = a("sculk_catalyst", TileEntityTypes.b.a(SculkCatalystBlockEntity::new, Blocks.qC));
   public static final TileEntityTypes<SculkShriekerBlockEntity> K = a("sculk_shrieker", TileEntityTypes.b.a(SculkShriekerBlockEntity::new, Blocks.qD));
   public static final TileEntityTypes<ChiseledBookShelfBlockEntity> L = a(
      "chiseled_bookshelf", TileEntityTypes.b.a(ChiseledBookShelfBlockEntity::new, Blocks.cl)
   );
   public static final TileEntityTypes<SuspiciousSandBlockEntity> M = a("suspicious_sand", TileEntityTypes.b.a(SuspiciousSandBlockEntity::new, Blocks.J));
   public static final TileEntityTypes<DecoratedPotBlockEntity> N = a("decorated_pot", TileEntityTypes.b.a(DecoratedPotBlockEntity::new, Blocks.sj));
   private final TileEntityTypes.a<? extends T> P;
   private final Set<Block> Q;
   private final Type<?> R;

   @Nullable
   public static MinecraftKey a(TileEntityTypes<?> var0) {
      return BuiltInRegistries.l.b(var0);
   }

   private static <T extends TileEntity> TileEntityTypes<T> a(String var0, TileEntityTypes.b<T> var1) {
      if (var1.b.isEmpty()) {
         O.warn("Block entity type {} requires at least one valid block to be defined!", var0);
      }

      Type<?> var2 = SystemUtils.a(DataConverterTypes.l, var0);
      return IRegistry.a(BuiltInRegistries.l, var0, var1.a(var2));
   }

   public TileEntityTypes(TileEntityTypes.a<? extends T> var0, Set<Block> var1, Type<?> var2) {
      this.P = var0;
      this.Q = var1;
      this.R = var2;
   }

   @Nullable
   public T a(BlockPosition var0, IBlockData var1) {
      return this.P.create(var0, var1);
   }

   public boolean a(IBlockData var0) {
      return this.Q.contains(var0.b());
   }

   @Nullable
   public T a(IBlockAccess var0, BlockPosition var1) {
      TileEntity var2 = var0.c_(var1);
      return (T)(var2 != null && var2.u() == this ? var2 : null);
   }

   @FunctionalInterface
   interface a<T extends TileEntity> {
      T create(BlockPosition var1, IBlockData var2);
   }

   public static final class b<T extends TileEntity> {
      private final TileEntityTypes.a<? extends T> a;
      final Set<Block> b;

      private b(TileEntityTypes.a<? extends T> var0, Set<Block> var1) {
         this.a = var0;
         this.b = var1;
      }

      public static <T extends TileEntity> TileEntityTypes.b<T> a(TileEntityTypes.a<? extends T> var0, Block... var1) {
         return new TileEntityTypes.b<>(var0, ImmutableSet.copyOf(var1));
      }

      public TileEntityTypes<T> a(Type<?> var0) {
         return new TileEntityTypes<>(this.a, this.b, var0);
      }
   }
}
