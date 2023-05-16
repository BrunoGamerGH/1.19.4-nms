package net.minecraft.world.level.block.state.properties;

import net.minecraft.core.BlockPropertyJigsawOrientation;
import net.minecraft.core.EnumDirection;

public class BlockProperties {
   public static final BlockStateBoolean a = BlockStateBoolean.a("attached");
   public static final BlockStateBoolean b = BlockStateBoolean.a("bottom");
   public static final BlockStateBoolean c = BlockStateBoolean.a("conditional");
   public static final BlockStateBoolean d = BlockStateBoolean.a("disarmed");
   public static final BlockStateBoolean e = BlockStateBoolean.a("drag");
   public static final BlockStateBoolean f = BlockStateBoolean.a("enabled");
   public static final BlockStateBoolean g = BlockStateBoolean.a("extended");
   public static final BlockStateBoolean h = BlockStateBoolean.a("eye");
   public static final BlockStateBoolean i = BlockStateBoolean.a("falling");
   public static final BlockStateBoolean j = BlockStateBoolean.a("hanging");
   public static final BlockStateBoolean k = BlockStateBoolean.a("has_bottle_0");
   public static final BlockStateBoolean l = BlockStateBoolean.a("has_bottle_1");
   public static final BlockStateBoolean m = BlockStateBoolean.a("has_bottle_2");
   public static final BlockStateBoolean n = BlockStateBoolean.a("has_record");
   public static final BlockStateBoolean o = BlockStateBoolean.a("has_book");
   public static final BlockStateBoolean p = BlockStateBoolean.a("inverted");
   public static final BlockStateBoolean q = BlockStateBoolean.a("in_wall");
   public static final BlockStateBoolean r = BlockStateBoolean.a("lit");
   public static final BlockStateBoolean s = BlockStateBoolean.a("locked");
   public static final BlockStateBoolean t = BlockStateBoolean.a("occupied");
   public static final BlockStateBoolean u = BlockStateBoolean.a("open");
   public static final BlockStateBoolean v = BlockStateBoolean.a("persistent");
   public static final BlockStateBoolean w = BlockStateBoolean.a("powered");
   public static final BlockStateBoolean x = BlockStateBoolean.a("short");
   public static final BlockStateBoolean y = BlockStateBoolean.a("signal_fire");
   public static final BlockStateBoolean z = BlockStateBoolean.a("snowy");
   public static final BlockStateBoolean A = BlockStateBoolean.a("triggered");
   public static final BlockStateBoolean B = BlockStateBoolean.a("unstable");
   public static final BlockStateBoolean C = BlockStateBoolean.a("waterlogged");
   public static final BlockStateBoolean D = BlockStateBoolean.a("berries");
   public static final BlockStateBoolean E = BlockStateBoolean.a("bloom");
   public static final BlockStateBoolean F = BlockStateBoolean.a("shrieking");
   public static final BlockStateBoolean G = BlockStateBoolean.a("can_summon");
   public static final BlockStateEnum<EnumDirection.EnumAxis> H = BlockStateEnum.a(
      "axis", EnumDirection.EnumAxis.class, EnumDirection.EnumAxis.a, EnumDirection.EnumAxis.c
   );
   public static final BlockStateEnum<EnumDirection.EnumAxis> I = BlockStateEnum.a("axis", EnumDirection.EnumAxis.class);
   public static final BlockStateBoolean J = BlockStateBoolean.a("up");
   public static final BlockStateBoolean K = BlockStateBoolean.a("down");
   public static final BlockStateBoolean L = BlockStateBoolean.a("north");
   public static final BlockStateBoolean M = BlockStateBoolean.a("east");
   public static final BlockStateBoolean N = BlockStateBoolean.a("south");
   public static final BlockStateBoolean O = BlockStateBoolean.a("west");
   public static final BlockStateDirection P = BlockStateDirection.a(
      "facing", EnumDirection.c, EnumDirection.f, EnumDirection.d, EnumDirection.e, EnumDirection.b, EnumDirection.a
   );
   public static final BlockStateDirection Q = BlockStateDirection.a("facing", var0 -> var0 != EnumDirection.b);
   public static final BlockStateDirection R = BlockStateDirection.a("facing", EnumDirection.EnumDirectionLimit.a);
   public static final BlockStateInteger S = BlockStateInteger.a("flower_amount", 1, 4);
   public static final BlockStateEnum<BlockPropertyJigsawOrientation> T = BlockStateEnum.a("orientation", BlockPropertyJigsawOrientation.class);
   public static final BlockStateEnum<BlockPropertyAttachPosition> U = BlockStateEnum.a("face", BlockPropertyAttachPosition.class);
   public static final BlockStateEnum<BlockPropertyBellAttach> V = BlockStateEnum.a("attachment", BlockPropertyBellAttach.class);
   public static final BlockStateEnum<BlockPropertyWallHeight> W = BlockStateEnum.a("east", BlockPropertyWallHeight.class);
   public static final BlockStateEnum<BlockPropertyWallHeight> X = BlockStateEnum.a("north", BlockPropertyWallHeight.class);
   public static final BlockStateEnum<BlockPropertyWallHeight> Y = BlockStateEnum.a("south", BlockPropertyWallHeight.class);
   public static final BlockStateEnum<BlockPropertyWallHeight> Z = BlockStateEnum.a("west", BlockPropertyWallHeight.class);
   public static final BlockStateEnum<BlockPropertyRedstoneSide> aa = BlockStateEnum.a("east", BlockPropertyRedstoneSide.class);
   public static final BlockStateEnum<BlockPropertyRedstoneSide> ab = BlockStateEnum.a("north", BlockPropertyRedstoneSide.class);
   public static final BlockStateEnum<BlockPropertyRedstoneSide> ac = BlockStateEnum.a("south", BlockPropertyRedstoneSide.class);
   public static final BlockStateEnum<BlockPropertyRedstoneSide> ad = BlockStateEnum.a("west", BlockPropertyRedstoneSide.class);
   public static final BlockStateEnum<BlockPropertyDoubleBlockHalf> ae = BlockStateEnum.a("half", BlockPropertyDoubleBlockHalf.class);
   public static final BlockStateEnum<BlockPropertyHalf> af = BlockStateEnum.a("half", BlockPropertyHalf.class);
   public static final BlockStateEnum<BlockPropertyTrackPosition> ag = BlockStateEnum.a("shape", BlockPropertyTrackPosition.class);
   public static final BlockStateEnum<BlockPropertyTrackPosition> ah = BlockStateEnum.a(
      "shape",
      BlockPropertyTrackPosition.class,
      var0 -> var0 != BlockPropertyTrackPosition.j
            && var0 != BlockPropertyTrackPosition.i
            && var0 != BlockPropertyTrackPosition.g
            && var0 != BlockPropertyTrackPosition.h
   );
   public static final int ai = 1;
   public static final int aj = 2;
   public static final int ak = 3;
   public static final int al = 4;
   public static final int am = 5;
   public static final int an = 7;
   public static final int ao = 15;
   public static final int ap = 25;
   public static final BlockStateInteger aq = BlockStateInteger.a("age", 0, 1);
   public static final BlockStateInteger ar = BlockStateInteger.a("age", 0, 2);
   public static final BlockStateInteger as = BlockStateInteger.a("age", 0, 3);
   public static final BlockStateInteger at = BlockStateInteger.a("age", 0, 4);
   public static final BlockStateInteger au = BlockStateInteger.a("age", 0, 5);
   public static final BlockStateInteger av = BlockStateInteger.a("age", 0, 7);
   public static final BlockStateInteger aw = BlockStateInteger.a("age", 0, 15);
   public static final BlockStateInteger ax = BlockStateInteger.a("age", 0, 25);
   public static final BlockStateInteger ay = BlockStateInteger.a("bites", 0, 6);
   public static final BlockStateInteger az = BlockStateInteger.a("candles", 1, 4);
   public static final BlockStateInteger aA = BlockStateInteger.a("delay", 1, 4);
   public static final int aB = 7;
   public static final BlockStateInteger aC = BlockStateInteger.a("distance", 1, 7);
   public static final BlockStateInteger aD = BlockStateInteger.a("eggs", 1, 4);
   public static final BlockStateInteger aE = BlockStateInteger.a("hatch", 0, 2);
   public static final BlockStateInteger aF = BlockStateInteger.a("layers", 1, 8);
   public static final int aG = 0;
   public static final int aH = 1;
   public static final int aI = 3;
   public static final int aJ = 8;
   public static final BlockStateInteger aK = BlockStateInteger.a("level", 1, 3);
   public static final BlockStateInteger aL = BlockStateInteger.a("level", 0, 8);
   public static final BlockStateInteger aM = BlockStateInteger.a("level", 1, 8);
   public static final BlockStateInteger aN = BlockStateInteger.a("honey_level", 0, 5);
   public static final int aO = 15;
   public static final BlockStateInteger aP = BlockStateInteger.a("level", 0, 15);
   public static final BlockStateInteger aQ = BlockStateInteger.a("moisture", 0, 7);
   public static final BlockStateInteger aR = BlockStateInteger.a("note", 0, 24);
   public static final BlockStateInteger aS = BlockStateInteger.a("pickles", 1, 4);
   public static final BlockStateInteger aT = BlockStateInteger.a("power", 0, 15);
   public static final BlockStateInteger aU = BlockStateInteger.a("stage", 0, 1);
   public static final int aV = 7;
   public static final BlockStateInteger aW = BlockStateInteger.a("distance", 0, 7);
   public static final int aX = 0;
   public static final int aY = 4;
   public static final BlockStateInteger aZ = BlockStateInteger.a("charges", 0, 4);
   public static final BlockStateInteger ba = BlockStateInteger.a("rotation", 0, RotationSegment.a());
   public static final BlockStateEnum<BlockPropertyBedPart> bb = BlockStateEnum.a("part", BlockPropertyBedPart.class);
   public static final BlockStateEnum<BlockPropertyChestType> bc = BlockStateEnum.a("type", BlockPropertyChestType.class);
   public static final BlockStateEnum<BlockPropertyComparatorMode> bd = BlockStateEnum.a("mode", BlockPropertyComparatorMode.class);
   public static final BlockStateEnum<BlockPropertyDoorHinge> be = BlockStateEnum.a("hinge", BlockPropertyDoorHinge.class);
   public static final BlockStateEnum<BlockPropertyInstrument> bf = BlockStateEnum.a("instrument", BlockPropertyInstrument.class);
   public static final BlockStateEnum<BlockPropertyPistonType> bg = BlockStateEnum.a("type", BlockPropertyPistonType.class);
   public static final BlockStateEnum<BlockPropertySlabType> bh = BlockStateEnum.a("type", BlockPropertySlabType.class);
   public static final BlockStateEnum<BlockPropertyStairsShape> bi = BlockStateEnum.a("shape", BlockPropertyStairsShape.class);
   public static final BlockStateEnum<BlockPropertyStructureMode> bj = BlockStateEnum.a("mode", BlockPropertyStructureMode.class);
   public static final BlockStateEnum<BlockPropertyBambooSize> bk = BlockStateEnum.a("leaves", BlockPropertyBambooSize.class);
   public static final BlockStateEnum<Tilt> bl = BlockStateEnum.a("tilt", Tilt.class);
   public static final BlockStateDirection bm = BlockStateDirection.a("vertical_direction", EnumDirection.b, EnumDirection.a);
   public static final BlockStateEnum<DripstoneThickness> bn = BlockStateEnum.a("thickness", DripstoneThickness.class);
   public static final BlockStateEnum<SculkSensorPhase> bo = BlockStateEnum.a("sculk_sensor_phase", SculkSensorPhase.class);
   public static final BlockStateBoolean bp = BlockStateBoolean.a("slot_0_occupied");
   public static final BlockStateBoolean bq = BlockStateBoolean.a("slot_1_occupied");
   public static final BlockStateBoolean br = BlockStateBoolean.a("slot_2_occupied");
   public static final BlockStateBoolean bs = BlockStateBoolean.a("slot_3_occupied");
   public static final BlockStateBoolean bt = BlockStateBoolean.a("slot_4_occupied");
   public static final BlockStateBoolean bu = BlockStateBoolean.a("slot_5_occupied");
   public static final BlockStateInteger bv = BlockStateInteger.a("dusted", 0, 3);
}
