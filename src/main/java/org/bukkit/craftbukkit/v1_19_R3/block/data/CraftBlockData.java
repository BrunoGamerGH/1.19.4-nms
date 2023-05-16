package org.bukkit.craftbukkit.v1_19_R3.block.data;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.commands.arguments.blocks.ArgumentBlock;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.INamable;
import net.minecraft.world.level.BlockAccessAir;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.BigDripleafBlock;
import net.minecraft.world.level.block.BigDripleafStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockAnvil;
import net.minecraft.world.level.block.BlockBamboo;
import net.minecraft.world.level.block.BlockBanner;
import net.minecraft.world.level.block.BlockBannerWall;
import net.minecraft.world.level.block.BlockBarrel;
import net.minecraft.world.level.block.BlockBed;
import net.minecraft.world.level.block.BlockBeehive;
import net.minecraft.world.level.block.BlockBeetroot;
import net.minecraft.world.level.block.BlockBell;
import net.minecraft.world.level.block.BlockBlastFurnace;
import net.minecraft.world.level.block.BlockBrewingStand;
import net.minecraft.world.level.block.BlockBubbleColumn;
import net.minecraft.world.level.block.BlockButtonAbstract;
import net.minecraft.world.level.block.BlockCactus;
import net.minecraft.world.level.block.BlockCake;
import net.minecraft.world.level.block.BlockCampfire;
import net.minecraft.world.level.block.BlockCarrots;
import net.minecraft.world.level.block.BlockChain;
import net.minecraft.world.level.block.BlockChest;
import net.minecraft.world.level.block.BlockChestTrapped;
import net.minecraft.world.level.block.BlockChorusFlower;
import net.minecraft.world.level.block.BlockChorusFruit;
import net.minecraft.world.level.block.BlockCobbleWall;
import net.minecraft.world.level.block.BlockCocoa;
import net.minecraft.world.level.block.BlockCommand;
import net.minecraft.world.level.block.BlockComposter;
import net.minecraft.world.level.block.BlockConduit;
import net.minecraft.world.level.block.BlockCoralDead;
import net.minecraft.world.level.block.BlockCoralFan;
import net.minecraft.world.level.block.BlockCoralFanAbstract;
import net.minecraft.world.level.block.BlockCoralFanWall;
import net.minecraft.world.level.block.BlockCoralFanWallAbstract;
import net.minecraft.world.level.block.BlockCoralPlant;
import net.minecraft.world.level.block.BlockCrops;
import net.minecraft.world.level.block.BlockDaylightDetector;
import net.minecraft.world.level.block.BlockDirtSnow;
import net.minecraft.world.level.block.BlockDispenser;
import net.minecraft.world.level.block.BlockDoor;
import net.minecraft.world.level.block.BlockDropper;
import net.minecraft.world.level.block.BlockEndRod;
import net.minecraft.world.level.block.BlockEnderChest;
import net.minecraft.world.level.block.BlockEnderPortalFrame;
import net.minecraft.world.level.block.BlockFence;
import net.minecraft.world.level.block.BlockFenceGate;
import net.minecraft.world.level.block.BlockFire;
import net.minecraft.world.level.block.BlockFloorSign;
import net.minecraft.world.level.block.BlockFluids;
import net.minecraft.world.level.block.BlockFurnaceFurace;
import net.minecraft.world.level.block.BlockGlazedTerracotta;
import net.minecraft.world.level.block.BlockGrass;
import net.minecraft.world.level.block.BlockGrindstone;
import net.minecraft.world.level.block.BlockHay;
import net.minecraft.world.level.block.BlockHopper;
import net.minecraft.world.level.block.BlockHugeMushroom;
import net.minecraft.world.level.block.BlockIceFrost;
import net.minecraft.world.level.block.BlockIronBars;
import net.minecraft.world.level.block.BlockJigsaw;
import net.minecraft.world.level.block.BlockJukeBox;
import net.minecraft.world.level.block.BlockKelp;
import net.minecraft.world.level.block.BlockLadder;
import net.minecraft.world.level.block.BlockLantern;
import net.minecraft.world.level.block.BlockLeaves;
import net.minecraft.world.level.block.BlockLectern;
import net.minecraft.world.level.block.BlockLever;
import net.minecraft.world.level.block.BlockLoom;
import net.minecraft.world.level.block.BlockMinecartDetector;
import net.minecraft.world.level.block.BlockMinecartTrack;
import net.minecraft.world.level.block.BlockMycel;
import net.minecraft.world.level.block.BlockNetherWart;
import net.minecraft.world.level.block.BlockNote;
import net.minecraft.world.level.block.BlockObserver;
import net.minecraft.world.level.block.BlockPortal;
import net.minecraft.world.level.block.BlockPotatoes;
import net.minecraft.world.level.block.BlockPoweredRail;
import net.minecraft.world.level.block.BlockPressurePlateBinary;
import net.minecraft.world.level.block.BlockPressurePlateWeighted;
import net.minecraft.world.level.block.BlockPumpkinCarved;
import net.minecraft.world.level.block.BlockRedstoneComparator;
import net.minecraft.world.level.block.BlockRedstoneLamp;
import net.minecraft.world.level.block.BlockRedstoneOre;
import net.minecraft.world.level.block.BlockRedstoneTorch;
import net.minecraft.world.level.block.BlockRedstoneTorchWall;
import net.minecraft.world.level.block.BlockRedstoneWire;
import net.minecraft.world.level.block.BlockReed;
import net.minecraft.world.level.block.BlockRepeater;
import net.minecraft.world.level.block.BlockRespawnAnchor;
import net.minecraft.world.level.block.BlockRotatable;
import net.minecraft.world.level.block.BlockSapling;
import net.minecraft.world.level.block.BlockScaffolding;
import net.minecraft.world.level.block.BlockSeaPickle;
import net.minecraft.world.level.block.BlockShulkerBox;
import net.minecraft.world.level.block.BlockSkull;
import net.minecraft.world.level.block.BlockSkullPlayer;
import net.minecraft.world.level.block.BlockSkullPlayerWall;
import net.minecraft.world.level.block.BlockSkullWall;
import net.minecraft.world.level.block.BlockSmoker;
import net.minecraft.world.level.block.BlockSnow;
import net.minecraft.world.level.block.BlockSoil;
import net.minecraft.world.level.block.BlockStainedGlassPane;
import net.minecraft.world.level.block.BlockStairs;
import net.minecraft.world.level.block.BlockStem;
import net.minecraft.world.level.block.BlockStemAttached;
import net.minecraft.world.level.block.BlockStepAbstract;
import net.minecraft.world.level.block.BlockStonecutter;
import net.minecraft.world.level.block.BlockStructure;
import net.minecraft.world.level.block.BlockSweetBerryBush;
import net.minecraft.world.level.block.BlockTNT;
import net.minecraft.world.level.block.BlockTallPlant;
import net.minecraft.world.level.block.BlockTallPlantFlower;
import net.minecraft.world.level.block.BlockTarget;
import net.minecraft.world.level.block.BlockTorchWall;
import net.minecraft.world.level.block.BlockTrapdoor;
import net.minecraft.world.level.block.BlockTripwire;
import net.minecraft.world.level.block.BlockTripwireHook;
import net.minecraft.world.level.block.BlockTurtleEgg;
import net.minecraft.world.level.block.BlockTwistingVines;
import net.minecraft.world.level.block.BlockVine;
import net.minecraft.world.level.block.BlockWallSign;
import net.minecraft.world.level.block.BlockWeepingVines;
import net.minecraft.world.level.block.BlockWitherSkull;
import net.minecraft.world.level.block.BlockWitherSkullWall;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.CaveVinesPlantBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.CherryLeavesBlock;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.HangingRootsBlock;
import net.minecraft.world.level.block.InfestedRotatedPillarBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.MangroveLeavesBlock;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.MangroveRootsBlock;
import net.minecraft.world.level.block.PiglinWallSkullBlock;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.PowderSnowCauldronBlock;
import net.minecraft.world.level.block.SculkCatalystBlock;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.SculkVeinBlock;
import net.minecraft.world.level.block.SmallDripleafBlock;
import net.minecraft.world.level.block.SuspiciousSandBlock;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WeatheringCopperSlabBlock;
import net.minecraft.world.level.block.WeatheringCopperStairBlock;
import net.minecraft.world.level.block.piston.BlockPiston;
import net.minecraft.world.level.block.piston.BlockPistonExtension;
import net.minecraft.world.level.block.piston.BlockPistonMoving;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.IBlockDataHolder;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.block.state.properties.IBlockState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SoundGroup;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockSupport;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R3.CraftSoundGroup;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockSupport;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftAmethystCluster;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftAnvil;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftBamboo;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftBanner;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftBannerWall;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftBarrel;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftBed;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftBeehive;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftBeetroot;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftBell;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftBigDripleaf;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftBigDripleafStem;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftBlastFurnace;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftBrewingStand;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftBubbleColumn;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftButtonAbstract;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCactus;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCake;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCampfire;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCandle;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCandleCake;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCarrots;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCaveVines;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCaveVinesPlant;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCeilingHangingSign;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftChain;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCherryLeaves;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftChest;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftChestTrapped;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftChiseledBookShelf;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftChorusFlower;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftChorusFruit;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCobbleWall;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCocoa;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCommand;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftComposter;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftConduit;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCoralDead;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCoralFan;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCoralFanAbstract;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCoralFanWall;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCoralFanWallAbstract;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCoralPlant;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftCrops;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftDaylightDetector;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftDecoratedPot;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftDirtSnow;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftDispenser;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftDoor;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftDropper;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftEndRod;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftEnderChest;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftEnderPortalFrame;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftFence;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftFenceGate;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftFire;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftFloorSign;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftFluids;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftFurnaceFurace;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftGlazedTerracotta;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftGlowLichen;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftGrass;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftGrindstone;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftHangingRoots;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftHay;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftHopper;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftHugeMushroom;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftIceFrost;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftInfestedRotatedPillar;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftIronBars;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftJigsaw;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftJukeBox;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftKelp;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftLadder;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftLantern;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftLayeredCauldron;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftLeaves;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftLectern;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftLever;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftLight;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftLightningRod;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftLoom;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftMangroveLeaves;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftMangrovePropagule;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftMangroveRoots;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftMinecartDetector;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftMinecartTrack;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftMycel;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftNetherWart;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftNote;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftObserver;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftPiglinWallSkull;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftPinkPetals;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftPiston;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftPistonExtension;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftPistonMoving;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftPointedDripstone;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftPortal;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftPotatoes;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftPowderSnowCauldron;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftPoweredRail;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftPressurePlateBinary;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftPressurePlateWeighted;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftPumpkinCarved;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftRedstoneComparator;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftRedstoneLamp;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftRedstoneOre;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftRedstoneTorch;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftRedstoneTorchWall;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftRedstoneWire;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftReed;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftRepeater;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftRespawnAnchor;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSapling;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftScaffolding;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSculkCatalyst;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSculkSensor;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSculkShrieker;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSculkVein;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSeaPickle;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftShulkerBox;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSkull;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSkullPlayer;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSkullPlayerWall;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSkullWall;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSmallDripleaf;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSmoker;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSnow;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSoil;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftStainedGlassPane;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftStairs;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftStem;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftStemAttached;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftStepAbstract;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftStonecutter;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftStructure;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSuspiciousSand;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftSweetBerryBush;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftTNT;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftTallPlant;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftTallPlantFlower;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftTallSeagrass;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftTarget;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftTorchWall;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftTorchflowerCrop;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftTrapdoor;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftTripwire;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftTripwireHook;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftTurtleEgg;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftTwistingVines;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftVine;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftWallHangingSign;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftWallSign;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftWeatheringCopperSlab;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftWeatheringCopperStair;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftWeepingVines;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftWitherSkull;
import org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftWitherSkullWall;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;

public class CraftBlockData implements BlockData {
   private IBlockData state;
   private Map<IBlockState<?>, Comparable<?>> parsedStates;
   private static final Map<Class<? extends Enum<?>>, Enum<?>[]> ENUM_VALUES = new HashMap<>();
   private static final Map<Class<? extends Block>, Function<IBlockData, CraftBlockData>> MAP = new HashMap<>();

   static {
      register(AmethystClusterBlock.class, CraftAmethystCluster::new);
      register(BigDripleafBlock.class, CraftBigDripleaf::new);
      register(BigDripleafStemBlock.class, CraftBigDripleafStem::new);
      register(BlockAnvil.class, CraftAnvil::new);
      register(BlockBamboo.class, CraftBamboo::new);
      register(BlockBanner.class, CraftBanner::new);
      register(BlockBannerWall.class, CraftBannerWall::new);
      register(BlockBarrel.class, CraftBarrel::new);
      register(BlockBed.class, CraftBed::new);
      register(BlockBeehive.class, CraftBeehive::new);
      register(BlockBeetroot.class, CraftBeetroot::new);
      register(BlockBell.class, CraftBell::new);
      register(BlockBlastFurnace.class, CraftBlastFurnace::new);
      register(BlockBrewingStand.class, CraftBrewingStand::new);
      register(BlockBubbleColumn.class, CraftBubbleColumn::new);
      register(BlockButtonAbstract.class, CraftButtonAbstract::new);
      register(BlockCactus.class, CraftCactus::new);
      register(BlockCake.class, CraftCake::new);
      register(BlockCampfire.class, CraftCampfire::new);
      register(BlockCarrots.class, CraftCarrots::new);
      register(BlockChain.class, CraftChain::new);
      register(BlockChest.class, CraftChest::new);
      register(BlockChestTrapped.class, CraftChestTrapped::new);
      register(BlockChorusFlower.class, CraftChorusFlower::new);
      register(BlockChorusFruit.class, CraftChorusFruit::new);
      register(BlockCobbleWall.class, CraftCobbleWall::new);
      register(BlockCocoa.class, CraftCocoa::new);
      register(BlockCommand.class, CraftCommand::new);
      register(BlockComposter.class, CraftComposter::new);
      register(BlockConduit.class, CraftConduit::new);
      register(BlockCoralDead.class, CraftCoralDead::new);
      register(BlockCoralFan.class, CraftCoralFan::new);
      register(BlockCoralFanAbstract.class, CraftCoralFanAbstract::new);
      register(BlockCoralFanWall.class, CraftCoralFanWall::new);
      register(BlockCoralFanWallAbstract.class, CraftCoralFanWallAbstract::new);
      register(BlockCoralPlant.class, CraftCoralPlant::new);
      register(BlockCrops.class, CraftCrops::new);
      register(BlockDaylightDetector.class, CraftDaylightDetector::new);
      register(BlockDirtSnow.class, CraftDirtSnow::new);
      register(BlockDispenser.class, CraftDispenser::new);
      register(BlockDoor.class, CraftDoor::new);
      register(BlockDropper.class, CraftDropper::new);
      register(BlockEndRod.class, CraftEndRod::new);
      register(BlockEnderChest.class, CraftEnderChest::new);
      register(BlockEnderPortalFrame.class, CraftEnderPortalFrame::new);
      register(BlockFence.class, CraftFence::new);
      register(BlockFenceGate.class, CraftFenceGate::new);
      register(BlockFire.class, CraftFire::new);
      register(BlockFloorSign.class, CraftFloorSign::new);
      register(BlockFluids.class, CraftFluids::new);
      register(BlockFurnaceFurace.class, CraftFurnaceFurace::new);
      register(BlockGlazedTerracotta.class, CraftGlazedTerracotta::new);
      register(BlockGrass.class, CraftGrass::new);
      register(BlockGrindstone.class, CraftGrindstone::new);
      register(BlockHay.class, CraftHay::new);
      register(BlockHopper.class, CraftHopper::new);
      register(BlockHugeMushroom.class, CraftHugeMushroom::new);
      register(BlockIceFrost.class, CraftIceFrost::new);
      register(BlockIronBars.class, CraftIronBars::new);
      register(BlockJigsaw.class, CraftJigsaw::new);
      register(BlockJukeBox.class, CraftJukeBox::new);
      register(BlockKelp.class, CraftKelp::new);
      register(BlockLadder.class, CraftLadder::new);
      register(BlockLantern.class, CraftLantern::new);
      register(BlockLeaves.class, CraftLeaves::new);
      register(BlockLectern.class, CraftLectern::new);
      register(BlockLever.class, CraftLever::new);
      register(BlockLoom.class, CraftLoom::new);
      register(BlockMinecartDetector.class, CraftMinecartDetector::new);
      register(BlockMinecartTrack.class, CraftMinecartTrack::new);
      register(BlockMycel.class, CraftMycel::new);
      register(BlockNetherWart.class, CraftNetherWart::new);
      register(BlockNote.class, CraftNote::new);
      register(BlockObserver.class, CraftObserver::new);
      register(BlockPortal.class, CraftPortal::new);
      register(BlockPotatoes.class, CraftPotatoes::new);
      register(BlockPoweredRail.class, CraftPoweredRail::new);
      register(BlockPressurePlateBinary.class, CraftPressurePlateBinary::new);
      register(BlockPressurePlateWeighted.class, CraftPressurePlateWeighted::new);
      register(BlockPumpkinCarved.class, CraftPumpkinCarved::new);
      register(BlockRedstoneComparator.class, CraftRedstoneComparator::new);
      register(BlockRedstoneLamp.class, CraftRedstoneLamp::new);
      register(BlockRedstoneOre.class, CraftRedstoneOre::new);
      register(BlockRedstoneTorch.class, CraftRedstoneTorch::new);
      register(BlockRedstoneTorchWall.class, CraftRedstoneTorchWall::new);
      register(BlockRedstoneWire.class, CraftRedstoneWire::new);
      register(BlockReed.class, CraftReed::new);
      register(BlockRepeater.class, CraftRepeater::new);
      register(BlockRespawnAnchor.class, CraftRespawnAnchor::new);
      register(BlockRotatable.class, org.bukkit.craftbukkit.v1_19_R3.block.impl.CraftRotatable::new);
      register(BlockSapling.class, CraftSapling::new);
      register(BlockScaffolding.class, CraftScaffolding::new);
      register(BlockSeaPickle.class, CraftSeaPickle::new);
      register(BlockShulkerBox.class, CraftShulkerBox::new);
      register(BlockSkull.class, CraftSkull::new);
      register(BlockSkullPlayer.class, CraftSkullPlayer::new);
      register(BlockSkullPlayerWall.class, CraftSkullPlayerWall::new);
      register(BlockSkullWall.class, CraftSkullWall::new);
      register(BlockSmoker.class, CraftSmoker::new);
      register(BlockSnow.class, CraftSnow::new);
      register(BlockSoil.class, CraftSoil::new);
      register(BlockStainedGlassPane.class, CraftStainedGlassPane::new);
      register(BlockStairs.class, CraftStairs::new);
      register(BlockStem.class, CraftStem::new);
      register(BlockStemAttached.class, CraftStemAttached::new);
      register(BlockStepAbstract.class, CraftStepAbstract::new);
      register(BlockStonecutter.class, CraftStonecutter::new);
      register(BlockStructure.class, CraftStructure::new);
      register(BlockSweetBerryBush.class, CraftSweetBerryBush::new);
      register(BlockTNT.class, CraftTNT::new);
      register(BlockTallPlant.class, CraftTallPlant::new);
      register(BlockTallPlantFlower.class, CraftTallPlantFlower::new);
      register(BlockTarget.class, CraftTarget::new);
      register(BlockTorchWall.class, CraftTorchWall::new);
      register(BlockTrapdoor.class, CraftTrapdoor::new);
      register(BlockTripwire.class, CraftTripwire::new);
      register(BlockTripwireHook.class, CraftTripwireHook::new);
      register(BlockTurtleEgg.class, CraftTurtleEgg::new);
      register(BlockTwistingVines.class, CraftTwistingVines::new);
      register(BlockVine.class, CraftVine::new);
      register(BlockWallSign.class, CraftWallSign::new);
      register(BlockWeepingVines.class, CraftWeepingVines::new);
      register(BlockWitherSkull.class, CraftWitherSkull::new);
      register(BlockWitherSkullWall.class, CraftWitherSkullWall::new);
      register(CandleBlock.class, CraftCandle::new);
      register(CandleCakeBlock.class, CraftCandleCake::new);
      register(CaveVinesBlock.class, CraftCaveVines::new);
      register(CaveVinesPlantBlock.class, CraftCaveVinesPlant::new);
      register(CeilingHangingSignBlock.class, CraftCeilingHangingSign::new);
      register(CherryLeavesBlock.class, CraftCherryLeaves::new);
      register(ChiseledBookShelfBlock.class, CraftChiseledBookShelf::new);
      register(DecoratedPotBlock.class, CraftDecoratedPot::new);
      register(GlowLichenBlock.class, CraftGlowLichen::new);
      register(HangingRootsBlock.class, CraftHangingRoots::new);
      register(InfestedRotatedPillarBlock.class, CraftInfestedRotatedPillar::new);
      register(LayeredCauldronBlock.class, CraftLayeredCauldron::new);
      register(LightBlock.class, CraftLight::new);
      register(LightningRodBlock.class, CraftLightningRod::new);
      register(MangroveLeavesBlock.class, CraftMangroveLeaves::new);
      register(MangrovePropaguleBlock.class, CraftMangrovePropagule::new);
      register(MangroveRootsBlock.class, CraftMangroveRoots::new);
      register(PiglinWallSkullBlock.class, CraftPiglinWallSkull::new);
      register(PinkPetalsBlock.class, CraftPinkPetals::new);
      register(PointedDripstoneBlock.class, CraftPointedDripstone::new);
      register(PowderSnowCauldronBlock.class, CraftPowderSnowCauldron::new);
      register(SculkCatalystBlock.class, CraftSculkCatalyst::new);
      register(SculkSensorBlock.class, CraftSculkSensor::new);
      register(SculkShriekerBlock.class, CraftSculkShrieker::new);
      register(SculkVeinBlock.class, CraftSculkVein::new);
      register(SmallDripleafBlock.class, CraftSmallDripleaf::new);
      register(SuspiciousSandBlock.class, CraftSuspiciousSand::new);
      register(TallSeagrassBlock.class, CraftTallSeagrass::new);
      register(TorchflowerCropBlock.class, CraftTorchflowerCrop::new);
      register(WallHangingSignBlock.class, CraftWallHangingSign::new);
      register(WeatheringCopperSlabBlock.class, CraftWeatheringCopperSlab::new);
      register(WeatheringCopperStairBlock.class, CraftWeatheringCopperStair::new);
      register(BlockPiston.class, CraftPiston::new);
      register(BlockPistonExtension.class, CraftPistonExtension::new);
      register(BlockPistonMoving.class, CraftPistonMoving::new);
   }

   protected CraftBlockData() {
      throw new AssertionError("Template Constructor");
   }

   protected CraftBlockData(IBlockData state) {
      this.state = state;
   }

   public Material getMaterial() {
      return CraftMagicNumbers.getMaterial(this.state.b());
   }

   public IBlockData getState() {
      return this.state;
   }

   protected <B extends Enum<B>> B get(BlockStateEnum<?> nms, Class<B> bukkit) {
      return toBukkit(this.state.c(nms), bukkit);
   }

   protected <B extends Enum<B>> Set<B> getValues(BlockStateEnum<?> nms, Class<B> bukkit) {
      Builder<B> values = ImmutableSet.builder();

      for(Enum<?> e : nms.a()) {
         values.add(toBukkit(e, bukkit));
      }

      return values.build();
   }

   protected <B extends Enum<B>, N extends Enum<N> & INamable> void set(BlockStateEnum<N> nms, Enum<B> bukkit) {
      this.parsedStates = null;
      this.state = this.state.a(nms, toNMS(bukkit, nms.g()));
   }

   public BlockData merge(BlockData data) {
      CraftBlockData craft = (CraftBlockData)data;
      Preconditions.checkArgument(craft.parsedStates != null, "Data not created via string parsing");
      Preconditions.checkArgument(this.state.b() == craft.state.b(), "States have different types (got %s, expected %s)", data, this);
      CraftBlockData clone = (CraftBlockData)this.clone();
      clone.parsedStates = null;

      for(IBlockState parsed : craft.parsedStates.keySet()) {
         clone.state = clone.state.a(parsed, craft.state.c(parsed));
      }

      return clone;
   }

   public boolean matches(BlockData data) {
      if (data == null) {
         return false;
      } else if (!(data instanceof CraftBlockData)) {
         return false;
      } else {
         CraftBlockData craft = (CraftBlockData)data;
         if (this.state.b() != craft.state.b()) {
            return false;
         } else {
            boolean exactMatch = this.equals(data);
            return !exactMatch && craft.parsedStates != null ? this.merge(data).equals(this) : exactMatch;
         }
      }
   }

   private static <B extends Enum<B>> B toBukkit(Enum<?> nms, Class<B> bukkit) {
      return (B)(nms instanceof EnumDirection
         ? CraftBlock.notchToBlockFace((EnumDirection)nms)
         : ENUM_VALUES.computeIfAbsent(bukkit, Class::getEnumConstants)[nms.ordinal()]);
   }

   private static <N extends Enum<N> & INamable> N toNMS(Enum<?> bukkit, Class<N> nms) {
      return (N)(bukkit instanceof BlockFace
         ? CraftBlock.blockFaceToNotch((BlockFace)bukkit)
         : ENUM_VALUES.computeIfAbsent(nms, Class::getEnumConstants)[bukkit.ordinal()]);
   }

   protected <T extends Comparable<T>> T get(IBlockState<T> ibs) {
      return this.state.c(ibs);
   }

   public <T extends Comparable<T>, V extends T> void set(IBlockState<T> ibs, V v) {
      this.parsedStates = null;
      this.state = this.state.a(ibs, v);
   }

   public String getAsString() {
      return this.toString(this.state.y());
   }

   public String getAsString(boolean hideUnspecified) {
      return hideUnspecified && this.parsedStates != null ? this.toString(this.parsedStates) : this.getAsString();
   }

   public BlockData clone() {
      try {
         return (BlockData)super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError("Clone not supported", var2);
      }
   }

   @Override
   public String toString() {
      return "CraftBlockData{" + this.getAsString() + "}";
   }

   public String toString(Map<IBlockState<?>, Comparable<?>> states) {
      StringBuilder stateString = new StringBuilder(BuiltInRegistries.f.b(this.state.b()).toString());
      if (!states.isEmpty()) {
         stateString.append('[');
         stateString.append(states.entrySet().stream().map(IBlockDataHolder.a).collect(Collectors.joining(",")));
         stateString.append(']');
      }

      return stateString.toString();
   }

   public NBTTagCompound toStates() {
      NBTTagCompound compound = new NBTTagCompound();

      for(Entry<IBlockState<?>, Comparable<?>> entry : this.state.y().entrySet()) {
         IBlockState iblockstate = entry.getKey();
         compound.a(iblockstate.f(), iblockstate.a(entry.getValue()));
      }

      return compound;
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof CraftBlockData && this.state.equals(((CraftBlockData)obj).state);
   }

   @Override
   public int hashCode() {
      return this.state.hashCode();
   }

   protected static BlockStateBoolean getBoolean(String name) {
      throw new AssertionError("Template Method");
   }

   protected static BlockStateBoolean getBoolean(String name, boolean optional) {
      throw new AssertionError("Template Method");
   }

   protected static BlockStateEnum<?> getEnum(String name) {
      throw new AssertionError("Template Method");
   }

   protected static BlockStateInteger getInteger(String name) {
      throw new AssertionError("Template Method");
   }

   protected static BlockStateBoolean getBoolean(Class<? extends Block> block, String name) {
      return (BlockStateBoolean)getState(block, name, false);
   }

   protected static BlockStateBoolean getBoolean(Class<? extends Block> block, String name, boolean optional) {
      return (BlockStateBoolean)getState(block, name, optional);
   }

   protected static BlockStateEnum<?> getEnum(Class<? extends Block> block, String name) {
      return (BlockStateEnum<?>)getState(block, name, false);
   }

   protected static BlockStateInteger getInteger(Class<? extends Block> block, String name) {
      return (BlockStateInteger)getState(block, name, false);
   }

   private static IBlockState<?> getState(Class<? extends Block> block, String name, boolean optional) {
      IBlockState<?> state = null;

      for(Block instance : BuiltInRegistries.f) {
         if (instance.getClass() == block) {
            if (state == null) {
               state = instance.n().a(name);
            } else {
               IBlockState<?> newState = instance.n().a(name);
               Preconditions.checkState(state == newState, "State mistmatch %s,%s", state, newState);
            }
         }
      }

      Preconditions.checkState(optional || state != null, "Null state for %s,%s", block, name);
      return state;
   }

   protected static int getMin(BlockStateInteger state) {
      return state.b;
   }

   protected static int getMax(BlockStateInteger state) {
      return state.c;
   }

   private static void register(Class<? extends Block> nms, Function<IBlockData, CraftBlockData> bukkit) {
      Preconditions.checkState(MAP.put(nms, bukkit) == null, "Duplicate mapping %s->%s", nms, bukkit);
   }

   public static CraftBlockData newData(Material material, String data) {
      Preconditions.checkArgument(material == null || material.isBlock(), "Cannot get data for not block %s", material);
      Block block = CraftMagicNumbers.getBlock(material);
      Map<IBlockState<?>, Comparable<?>> parsed = null;
      IBlockData blockData;
      if (data != null) {
         try {
            if (block != null) {
               data = BuiltInRegistries.f.b(block) + data;
            }

            StringReader reader = new StringReader(data);
            ArgumentBlock.a arg = ArgumentBlock.a(BuiltInRegistries.f.p(), reader, false);
            Preconditions.checkArgument(!reader.canRead(), "Spurious trailing data: " + data);
            blockData = arg.a();
            parsed = arg.b();
         } catch (CommandSyntaxException var7) {
            throw new IllegalArgumentException("Could not parse data: " + data, var7);
         }
      } else {
         blockData = block.o();
      }

      CraftBlockData craft = fromData(blockData);
      craft.parsedStates = parsed;
      return craft;
   }

   public static CraftBlockData fromData(IBlockData data) {
      return (CraftBlockData)((Function)MAP.getOrDefault(data.b().getClass(), CraftBlockData::new)).apply(data);
   }

   public SoundGroup getSoundGroup() {
      return CraftSoundGroup.getSoundGroup(this.state.t());
   }

   public int getLightEmission() {
      return this.state.g();
   }

   public boolean isOccluding() {
      return this.state.m();
   }

   public boolean requiresCorrectToolForDrops() {
      return this.state.v();
   }

   public boolean isPreferredTool(ItemStack tool) {
      Preconditions.checkArgument(tool != null, "tool must not be null");
      net.minecraft.world.item.ItemStack nms = CraftItemStack.asNMSCopy(tool);
      return isPreferredTool(this.state, nms);
   }

   public static boolean isPreferredTool(IBlockData iblockdata, net.minecraft.world.item.ItemStack nmsItem) {
      return !iblockdata.v() || nmsItem.b(iblockdata);
   }

   public PistonMoveReaction getPistonMoveReaction() {
      return PistonMoveReaction.getById(this.state.l().ordinal());
   }

   public boolean isSupported(org.bukkit.block.Block block) {
      Preconditions.checkArgument(block != null, "block must not be null");
      CraftBlock craftBlock = (CraftBlock)block;
      return this.state.a(craftBlock.getCraftWorld().getHandle(), craftBlock.getPosition());
   }

   public boolean isSupported(Location location) {
      Preconditions.checkArgument(location != null, "location must not be null");
      CraftWorld world = (CraftWorld)location.getWorld();
      Preconditions.checkArgument(world != null, "location must not have a null world");
      BlockPosition position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
      return this.state.a(world.getHandle(), position);
   }

   public boolean isFaceSturdy(BlockFace face, BlockSupport support) {
      Preconditions.checkArgument(face != null, "face must not be null");
      Preconditions.checkArgument(support != null, "support must not be null");
      return this.state.a(BlockAccessAir.a, BlockPosition.b, CraftBlock.blockFaceToNotch(face), CraftBlockSupport.toNMS(support));
   }

   public Material getPlacementMaterial() {
      return CraftMagicNumbers.getMaterial(this.state.b().k());
   }
}
