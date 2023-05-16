package org.bukkit.craftbukkit.v1_19_R3.event;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.protocol.game.PacketPlayInCloseWindow;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.stats.Statistic;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Unit;
import net.minecraft.world.EnumHand;
import net.minecraft.world.IInventory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityAreaEffectCloud;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.animal.EntityFish;
import net.minecraft.world.entity.animal.EntityGolem;
import net.minecraft.world.entity.animal.EntityWaterAnimal;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.EntityGhast;
import net.minecraft.world.entity.monster.EntityIllagerWizard;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.EntitySlime;
import net.minecraft.world.entity.monster.EntityStrider;
import net.minecraft.world.entity.monster.piglin.EntityPiglin;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.NPC;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityFireworks;
import net.minecraft.world.entity.projectile.EntityPotion;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerMerchant;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyInstrument;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Note;
import org.bukkit.Server;
import org.bukkit.Statistic.Type;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_19_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_19_R3.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_19_R3.CraftLootTable;
import org.bukkit.craftbukkit.v1_19_R3.CraftRaid;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftStatistic;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockStates;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftRaider;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftSpellcaster;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryCrafting;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftMetaBook;
import org.bukkit.craftbukkit.v1_19_R3.potion.CraftPotionUtil;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Animals;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Raider;
import org.bukkit.entity.Spellcaster;
import org.bukkit.entity.Strider;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageAbortEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockShearEntityEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.FluidLevelChangeEvent;
import org.bukkit.event.block.MoistureChangeEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.ArrowBodyCountChangeEvent;
import org.bukkit.event.entity.BatToggleSleepEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityEnterLoveModeEvent;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntitySpellCastEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.event.entity.PigZapEvent;
import org.bukkit.event.entity.PiglinBarterEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.entity.StriderTemperatureChangeEvent;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.CreeperPowerEvent.PowerCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.entity.EntityExhaustionEvent.ExhaustionReason;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.EntityTransformEvent.TransformReason;
import org.bukkit.event.entity.VillagerCareerChangeEvent.ChangeReason;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.event.inventory.TradeSelectEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEntityEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerBucketFishEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.event.raid.RaidFinishEvent;
import org.bukkit.event.raid.RaidSpawnWaveEvent;
import org.bukkit.event.raid.RaidStopEvent;
import org.bukkit.event.raid.RaidTriggerEvent;
import org.bukkit.event.raid.RaidStopEvent.Reason;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.LightningStrikeEvent.Cause;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.event.world.EntitiesUnloadEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;

public class CraftEventFactory {
   public static Block blockDamage;
   public static net.minecraft.world.entity.Entity entityDamage;
   public static BlockPosition sourceBlockOverride = null;
   private static final Function<? super Double, Double> ZERO = Functions.constant(-0.0);

   private static boolean canBuild(WorldServer world, Player player, int x, int z) {
      int spawnSize = Bukkit.getServer().getSpawnRadius();
      if (world.ab() != World.h) {
         return true;
      } else if (spawnSize <= 0) {
         return true;
      } else if (((CraftServer)Bukkit.getServer()).getHandle().k().c()) {
         return true;
      } else if (player.isOp()) {
         return true;
      } else {
         BlockPosition chunkcoordinates = world.Q();
         int distanceFromSpawn = Math.max(Math.abs(x - chunkcoordinates.u()), Math.abs(z - chunkcoordinates.w()));
         return distanceFromSpawn > spawnSize;
      }
   }

   public static <T extends Event> T callEvent(T event) {
      Bukkit.getServer().getPluginManager().callEvent(event);
      return event;
   }

   public static Either<EntityHuman.EnumBedResult, Unit> callPlayerBedEnterEvent(
      EntityHuman player, BlockPosition bed, Either<EntityHuman.EnumBedResult, Unit> nmsBedResult
   ) {
      BedEnterResult bedEnterResult = (BedEnterResult)nmsBedResult.mapBoth(new Function<EntityHuman.EnumBedResult, BedEnterResult>() {
         public BedEnterResult apply(EntityHuman.EnumBedResult t) {
            switch(t) {
               case a:
                  return BedEnterResult.NOT_POSSIBLE_HERE;
               case b:
                  return BedEnterResult.NOT_POSSIBLE_NOW;
               case c:
                  return BedEnterResult.TOO_FAR_AWAY;
               case d:
               case e:
               default:
                  return BedEnterResult.OTHER_PROBLEM;
               case f:
                  return BedEnterResult.NOT_SAFE;
            }
         }
      }, t -> BedEnterResult.OK).map(java.util.function.Function.identity(), java.util.function.Function.identity());
      PlayerBedEnterEvent event = new PlayerBedEnterEvent((Player)player.getBukkitEntity(), CraftBlock.at(player.H, bed), bedEnterResult);
      Bukkit.getServer().getPluginManager().callEvent(event);
      Result result = event.useBed();
      if (result == Result.ALLOW) {
         return Either.right(Unit.a);
      } else {
         return result == Result.DENY ? Either.left(EntityHuman.EnumBedResult.e) : nmsBedResult;
      }
   }

   public static EntityEnterLoveModeEvent callEntityEnterLoveModeEvent(EntityHuman entityHuman, EntityAnimal entityAnimal, int loveTicks) {
      EntityEnterLoveModeEvent entityEnterLoveModeEvent = new EntityEnterLoveModeEvent(
         (Animals)entityAnimal.getBukkitEntity(), entityHuman != null ? entityHuman.getBukkitEntity() : null, loveTicks
      );
      Bukkit.getPluginManager().callEvent(entityEnterLoveModeEvent);
      return entityEnterLoveModeEvent;
   }

   public static PlayerHarvestBlockEvent callPlayerHarvestBlockEvent(
      World world, BlockPosition blockposition, EntityHuman who, EnumHand enumhand, List<ItemStack> itemsToHarvest
   ) {
      List<org.bukkit.inventory.ItemStack> bukkitItemsToHarvest = new ArrayList(
         itemsToHarvest.stream().map(CraftItemStack::asBukkitCopy).collect(Collectors.toList())
      );
      Player player = (Player)who.getBukkitEntity();
      PlayerHarvestBlockEvent playerHarvestBlockEvent = new PlayerHarvestBlockEvent(
         player, CraftBlock.at(world, blockposition), CraftEquipmentSlot.getHand(enumhand), bukkitItemsToHarvest
      );
      Bukkit.getPluginManager().callEvent(playerHarvestBlockEvent);
      return playerHarvestBlockEvent;
   }

   public static PlayerBucketEntityEvent callPlayerFishBucketEvent(
      EntityLiving fish, EntityHuman entityHuman, ItemStack originalBucket, ItemStack entityBucket, EnumHand enumhand
   ) {
      Player player = (Player)entityHuman.getBukkitEntity();
      EquipmentSlot hand = CraftEquipmentSlot.getHand(enumhand);
      PlayerBucketEntityEvent event;
      if (fish instanceof EntityFish) {
         event = new PlayerBucketFishEvent(
            player, (Fish)fish.getBukkitEntity(), CraftItemStack.asBukkitCopy(originalBucket), CraftItemStack.asBukkitCopy(entityBucket), hand
         );
      } else {
         event = new PlayerBucketEntityEvent(
            player, fish.getBukkitEntity(), CraftItemStack.asBukkitCopy(originalBucket), CraftItemStack.asBukkitCopy(entityBucket), hand
         );
      }

      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static TradeSelectEvent callTradeSelectEvent(EntityPlayer player, int newIndex, ContainerMerchant merchant) {
      TradeSelectEvent tradeSelectEvent = new TradeSelectEvent(merchant.getBukkitView(), newIndex);
      Bukkit.getPluginManager().callEvent(tradeSelectEvent);
      return tradeSelectEvent;
   }

   public static BlockMultiPlaceEvent callBlockMultiPlaceEvent(
      WorldServer world, EntityHuman who, EnumHand hand, List<BlockState> blockStates, int clickedX, int clickedY, int clickedZ
   ) {
      CraftWorld craftWorld = world.getWorld();
      CraftServer craftServer = world.getCraftServer();
      Player player = (Player)who.getBukkitEntity();
      Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
      boolean canBuild = true;

      for(int i = 0; i < blockStates.size(); ++i) {
         if (!canBuild(world, player, ((BlockState)blockStates.get(i)).getX(), ((BlockState)blockStates.get(i)).getZ())) {
            canBuild = false;
            break;
         }
      }

      org.bukkit.inventory.ItemStack item;
      if (hand == EnumHand.a) {
         item = player.getInventory().getItemInMainHand();
      } else {
         item = player.getInventory().getItemInOffHand();
      }

      BlockMultiPlaceEvent event = new BlockMultiPlaceEvent(blockStates, blockClicked, item, player, canBuild);
      craftServer.getPluginManager().callEvent(event);
      return event;
   }

   public static BlockPlaceEvent callBlockPlaceEvent(
      WorldServer world, EntityHuman who, EnumHand hand, BlockState replacedBlockState, int clickedX, int clickedY, int clickedZ
   ) {
      CraftWorld craftWorld = world.getWorld();
      CraftServer craftServer = world.getCraftServer();
      Player player = (Player)who.getBukkitEntity();
      Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
      Block placedBlock = replacedBlockState.getBlock();
      boolean canBuild = canBuild(world, player, placedBlock.getX(), placedBlock.getZ());
      org.bukkit.inventory.ItemStack item;
      EquipmentSlot equipmentSlot;
      if (hand == EnumHand.a) {
         item = player.getInventory().getItemInMainHand();
         equipmentSlot = EquipmentSlot.HAND;
      } else {
         item = player.getInventory().getItemInOffHand();
         equipmentSlot = EquipmentSlot.OFF_HAND;
      }

      BlockPlaceEvent event = new BlockPlaceEvent(placedBlock, replacedBlockState, blockClicked, item, player, canBuild, equipmentSlot);
      craftServer.getPluginManager().callEvent(event);
      return event;
   }

   public static void handleBlockDropItemEvent(Block block, BlockState state, EntityPlayer player, List<EntityItem> items) {
      BlockDropItemEvent event = new BlockDropItemEvent(block, state, player.getBukkitEntity(), Lists.transform(items, itemx -> (Item)itemx.getBukkitEntity()));
      Bukkit.getPluginManager().callEvent(event);
      if (!event.isCancelled()) {
         for(EntityItem item : items) {
            item.H.b(item);
         }
      }
   }

   public static EntityPlaceEvent callEntityPlaceEvent(ItemActionContext itemactioncontext, net.minecraft.world.entity.Entity entity) {
      return callEntityPlaceEvent(itemactioncontext.q(), itemactioncontext.a(), itemactioncontext.k(), itemactioncontext.o(), entity, itemactioncontext.p());
   }

   public static EntityPlaceEvent callEntityPlaceEvent(
      World world, BlockPosition clickPosition, EnumDirection clickedFace, EntityHuman human, net.minecraft.world.entity.Entity entity, EnumHand enumhand
   ) {
      Player who = human == null ? null : (Player)human.getBukkitEntity();
      Block blockClicked = CraftBlock.at(world, clickPosition);
      BlockFace blockFace = CraftBlock.notchToBlockFace(clickedFace);
      EntityPlaceEvent event = new EntityPlaceEvent(entity.getBukkitEntity(), who, blockClicked, blockFace, CraftEquipmentSlot.getHand(enumhand));
      entity.H.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static PlayerBucketEmptyEvent callPlayerBucketEmptyEvent(
      WorldServer world, EntityHuman who, BlockPosition changed, BlockPosition clicked, EnumDirection clickedFace, ItemStack itemInHand, EnumHand enumhand
   ) {
      return (PlayerBucketEmptyEvent)getPlayerBucketEvent(false, world, who, changed, clicked, clickedFace, itemInHand, Items.pG, enumhand);
   }

   public static PlayerBucketFillEvent callPlayerBucketFillEvent(
      WorldServer world,
      EntityHuman who,
      BlockPosition changed,
      BlockPosition clicked,
      EnumDirection clickedFace,
      ItemStack itemInHand,
      net.minecraft.world.item.Item bucket,
      EnumHand enumhand
   ) {
      return (PlayerBucketFillEvent)getPlayerBucketEvent(true, world, who, clicked, changed, clickedFace, itemInHand, bucket, enumhand);
   }

   private static PlayerEvent getPlayerBucketEvent(
      boolean isFilling,
      WorldServer world,
      EntityHuman who,
      BlockPosition changed,
      BlockPosition clicked,
      EnumDirection clickedFace,
      ItemStack itemstack,
      net.minecraft.world.item.Item item,
      EnumHand enumhand
   ) {
      Player player = (Player)who.getBukkitEntity();
      CraftItemStack itemInHand = CraftItemStack.asNewCraftStack(item);
      Material bucket = CraftMagicNumbers.getMaterial(itemstack.c());
      CraftServer craftServer = (CraftServer)player.getServer();
      Block block = CraftBlock.at(world, changed);
      Block blockClicked = CraftBlock.at(world, clicked);
      BlockFace blockFace = CraftBlock.notchToBlockFace(clickedFace);
      EquipmentSlot hand = CraftEquipmentSlot.getHand(enumhand);
      PlayerEvent event;
      if (isFilling) {
         event = new PlayerBucketFillEvent(player, block, blockClicked, blockFace, bucket, itemInHand, hand);
         ((PlayerBucketFillEvent)event).setCancelled(!canBuild(world, player, changed.u(), changed.w()));
      } else {
         event = new PlayerBucketEmptyEvent(player, block, blockClicked, blockFace, bucket, itemInHand, hand);
         ((PlayerBucketEmptyEvent)event).setCancelled(!canBuild(world, player, changed.u(), changed.w()));
      }

      craftServer.getPluginManager().callEvent(event);
      return event;
   }

   public static PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, ItemStack itemstack, EnumHand hand) {
      if (action != Action.LEFT_CLICK_AIR && action != Action.RIGHT_CLICK_AIR) {
         throw new AssertionError(String.format("%s performing %s with %s", who, action, itemstack));
      } else {
         return callPlayerInteractEvent(who, action, null, EnumDirection.d, itemstack, hand);
      }
   }

   public static PlayerInteractEvent callPlayerInteractEvent(
      EntityHuman who, Action action, BlockPosition position, EnumDirection direction, ItemStack itemstack, EnumHand hand
   ) {
      return callPlayerInteractEvent(who, action, position, direction, itemstack, false, hand);
   }

   public static PlayerInteractEvent callPlayerInteractEvent(
      EntityHuman who, Action action, BlockPosition position, EnumDirection direction, ItemStack itemstack, boolean cancelledBlock, EnumHand hand
   ) {
      Player player = who == null ? null : (Player)who.getBukkitEntity();
      CraftItemStack itemInHand = CraftItemStack.asCraftMirror(itemstack);
      CraftWorld craftWorld = (CraftWorld)player.getWorld();
      CraftServer craftServer = (CraftServer)player.getServer();
      Block blockClicked = null;
      if (position != null) {
         blockClicked = craftWorld.getBlockAt(position.u(), position.v(), position.w());
      } else {
         switch(action) {
            case LEFT_CLICK_BLOCK:
               action = Action.LEFT_CLICK_AIR;
               break;
            case RIGHT_CLICK_BLOCK:
               action = Action.RIGHT_CLICK_AIR;
         }
      }

      BlockFace blockFace = CraftBlock.notchToBlockFace(direction);
      if (itemInHand.getType() == Material.AIR || itemInHand.getAmount() == 0) {
         itemInHand = null;
      }

      PlayerInteractEvent event = new PlayerInteractEvent(
         player, action, itemInHand, blockClicked, blockFace, hand == null ? null : (hand == EnumHand.b ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND)
      );
      if (cancelledBlock) {
         event.setUseInteractedBlock(Result.DENY);
      }

      craftServer.getPluginManager().callEvent(event);
      return event;
   }

   public static EntityTransformEvent callEntityTransformEvent(EntityLiving original, EntityLiving coverted, TransformReason transformReason) {
      return callEntityTransformEvent(original, Collections.singletonList(coverted), transformReason);
   }

   public static EntityTransformEvent callEntityTransformEvent(EntityLiving original, List<EntityLiving> convertedList, TransformReason convertType) {
      List<Entity> list = new ArrayList();

      for(EntityLiving entityLiving : convertedList) {
         list.add(entityLiving.getBukkitEntity());
      }

      EntityTransformEvent event = new EntityTransformEvent(original.getBukkitEntity(), list, convertType);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static EntityShootBowEvent callEntityShootBowEvent(
      EntityLiving who,
      ItemStack bow,
      ItemStack consumableItem,
      net.minecraft.world.entity.Entity entityArrow,
      EnumHand hand,
      float force,
      boolean consumeItem
   ) {
      LivingEntity shooter = (LivingEntity)who.getBukkitEntity();
      CraftItemStack itemInHand = CraftItemStack.asCraftMirror(bow);
      CraftItemStack itemConsumable = CraftItemStack.asCraftMirror(consumableItem);
      Entity arrow = entityArrow.getBukkitEntity();
      EquipmentSlot handSlot = hand == EnumHand.a ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
      if (itemInHand != null && (itemInHand.getType() == Material.AIR || itemInHand.getAmount() == 0)) {
         itemInHand = null;
      }

      EntityShootBowEvent event = new EntityShootBowEvent(shooter, itemInHand, itemConsumable, arrow, handSlot, force, consumeItem);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static VillagerCareerChangeEvent callVillagerCareerChangeEvent(EntityVillager vilager, Profession future, ChangeReason reason) {
      VillagerCareerChangeEvent event = new VillagerCareerChangeEvent((Villager)vilager.getBukkitEntity(), future, reason);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static BlockDamageEvent callBlockDamageEvent(EntityPlayer who, BlockPosition pos, ItemStack itemstack, boolean instaBreak) {
      Player player = who.getBukkitEntity();
      CraftItemStack itemInHand = CraftItemStack.asCraftMirror(itemstack);
      Block blockClicked = CraftBlock.at(who.x(), pos);
      BlockDamageEvent event = new BlockDamageEvent(player, blockClicked, itemInHand, instaBreak);
      player.getServer().getPluginManager().callEvent(event);
      return event;
   }

   public static BlockDamageAbortEvent callBlockDamageAbortEvent(EntityPlayer who, BlockPosition pos, ItemStack itemstack) {
      Player player = who.getBukkitEntity();
      CraftItemStack itemInHand = CraftItemStack.asCraftMirror(itemstack);
      Block blockClicked = CraftBlock.at(who.x(), pos);
      BlockDamageAbortEvent event = new BlockDamageAbortEvent(player, blockClicked, itemInHand);
      player.getServer().getPluginManager().callEvent(event);
      return event;
   }

   public static boolean doEntityAddEventCalling(World world, net.minecraft.world.entity.Entity entity, SpawnReason spawnReason) {
      if (entity == null) {
         return false;
      } else {
         Cancellable event = null;
         if (entity instanceof EntityLiving && !(entity instanceof EntityPlayer)) {
            boolean isAnimal = entity instanceof EntityAnimal || entity instanceof EntityWaterAnimal || entity instanceof EntityGolem;
            boolean isMonster = entity instanceof EntityMonster || entity instanceof EntityGhast || entity instanceof EntitySlime;
            boolean isNpc = entity instanceof NPC;
            if (spawnReason != SpawnReason.CUSTOM
               && (
                  isAnimal && !world.getWorld().getAllowAnimals()
                     || isMonster && !world.getWorld().getAllowMonsters()
                     || isNpc && !world.getCraftServer().getServer().X()
               )) {
               entity.ai();
               return false;
            }

            event = callCreatureSpawnEvent((EntityLiving)entity, spawnReason);
         } else if (entity instanceof EntityItem) {
            event = callItemSpawnEvent((EntityItem)entity);
         } else if (entity.getBukkitEntity() instanceof Projectile) {
            event = callProjectileLaunchEvent(entity);
         } else if (entity.getBukkitEntity() instanceof Vehicle) {
            event = callVehicleCreateEvent(entity);
         } else if (entity.getBukkitEntity() instanceof LightningStrike) {
            Cause cause = Cause.UNKNOWN;
            switch(spawnReason) {
               case SPAWNER:
                  cause = Cause.SPAWNER;
                  break;
               case COMMAND:
                  cause = Cause.COMMAND;
                  break;
               case CUSTOM:
                  cause = Cause.CUSTOM;
            }

            if (cause == Cause.UNKNOWN && spawnReason == SpawnReason.DEFAULT) {
               return true;
            }

            event = callLightningStrikeEvent((LightningStrike)entity.getBukkitEntity(), cause);
         } else if (entity instanceof EntityExperienceOrb xp) {
            double radius = world.spigotConfig.expMerge;
            if (radius > 0.0) {
               for(net.minecraft.world.entity.Entity e : world.a_(entity, entity.cD().c(radius, radius, radius))) {
                  if (e instanceof EntityExperienceOrb loopItem && !loopItem.dB()) {
                     xp.i += loopItem.i;
                     loopItem.ai();
                  }
               }
            }
         } else if (!(entity instanceof EntityPlayer)) {
            event = callEntitySpawnEvent(entity);
         }

         if (event != null && (event.isCancelled() || entity.dB())) {
            net.minecraft.world.entity.Entity vehicle = entity.cV();
            if (vehicle != null) {
               vehicle.ai();
            }

            for(net.minecraft.world.entity.Entity passenger : entity.cQ()) {
               passenger.ai();
            }

            entity.ai();
            return false;
         } else {
            return true;
         }
      }
   }

   public static EntitySpawnEvent callEntitySpawnEvent(net.minecraft.world.entity.Entity entity) {
      Entity bukkitEntity = entity.getBukkitEntity();
      EntitySpawnEvent event = new EntitySpawnEvent(bukkitEntity);
      bukkitEntity.getServer().getPluginManager().callEvent(event);
      return event;
   }

   public static CreatureSpawnEvent callCreatureSpawnEvent(EntityLiving entityliving, SpawnReason spawnReason) {
      LivingEntity entity = (LivingEntity)entityliving.getBukkitEntity();
      CraftServer craftServer = (CraftServer)entity.getServer();
      CreatureSpawnEvent event = new CreatureSpawnEvent(entity, spawnReason);
      craftServer.getPluginManager().callEvent(event);
      return event;
   }

   public static EntityTameEvent callEntityTameEvent(EntityInsentient entity, EntityHuman tamer) {
      Entity bukkitEntity = entity.getBukkitEntity();
      AnimalTamer bukkitTamer = tamer != null ? tamer.getBukkitEntity() : null;
      CraftServer craftServer = (CraftServer)bukkitEntity.getServer();
      EntityTameEvent event = new EntityTameEvent((LivingEntity)bukkitEntity, bukkitTamer);
      craftServer.getPluginManager().callEvent(event);
      return event;
   }

   public static ItemSpawnEvent callItemSpawnEvent(EntityItem entityitem) {
      Item entity = (Item)entityitem.getBukkitEntity();
      CraftServer craftServer = (CraftServer)entity.getServer();
      ItemSpawnEvent event = new ItemSpawnEvent(entity);
      craftServer.getPluginManager().callEvent(event);
      return event;
   }

   public static ItemDespawnEvent callItemDespawnEvent(EntityItem entityitem) {
      Item entity = (Item)entityitem.getBukkitEntity();
      ItemDespawnEvent event = new ItemDespawnEvent(entity, entity.getLocation());
      entity.getServer().getPluginManager().callEvent(event);
      return event;
   }

   public static ItemMergeEvent callItemMergeEvent(EntityItem merging, EntityItem mergingWith) {
      Item entityMerging = (Item)merging.getBukkitEntity();
      Item entityMergingWith = (Item)mergingWith.getBukkitEntity();
      ItemMergeEvent event = new ItemMergeEvent(entityMerging, entityMergingWith);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static PotionSplashEvent callPotionSplashEvent(EntityPotion potion, Map<LivingEntity, Double> affectedEntities) {
      ThrownPotion thrownPotion = (ThrownPotion)potion.getBukkitEntity();
      PotionSplashEvent event = new PotionSplashEvent(thrownPotion, affectedEntities);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static LingeringPotionSplashEvent callLingeringPotionSplashEvent(EntityPotion potion, EntityAreaEffectCloud cloud) {
      ThrownPotion thrownPotion = (ThrownPotion)potion.getBukkitEntity();
      AreaEffectCloud effectCloud = (AreaEffectCloud)cloud.getBukkitEntity();
      LingeringPotionSplashEvent event = new LingeringPotionSplashEvent(thrownPotion, effectCloud);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static BlockFadeEvent callBlockFadeEvent(GeneratorAccess world, BlockPosition pos, IBlockData newBlock) {
      CraftBlockState state = CraftBlockStates.getBlockState(world, pos);
      state.setData(newBlock);
      BlockFadeEvent event = new BlockFadeEvent(state.getBlock(), state);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static boolean handleMoistureChangeEvent(World world, BlockPosition pos, IBlockData newBlock, int flag) {
      CraftBlockState state = CraftBlockStates.getBlockState(world, pos, flag);
      state.setData(newBlock);
      MoistureChangeEvent event = new MoistureChangeEvent(state.getBlock(), state);
      Bukkit.getPluginManager().callEvent(event);
      if (!event.isCancelled()) {
         state.update(true);
      }

      return !event.isCancelled();
   }

   public static boolean handleBlockSpreadEvent(World world, BlockPosition source, BlockPosition target, IBlockData block) {
      return handleBlockSpreadEvent(world, source, target, block, 2);
   }

   public static boolean handleBlockSpreadEvent(GeneratorAccess world, BlockPosition source, BlockPosition target, IBlockData block, int flag) {
      if (!(world instanceof World)) {
         world.a(target, block, flag);
         return true;
      } else {
         CraftBlockState state = CraftBlockStates.getBlockState(world, target, flag);
         state.setData(block);
         BlockSpreadEvent event = new BlockSpreadEvent(
            state.getBlock(), CraftBlock.at(world, sourceBlockOverride != null ? sourceBlockOverride : source), state
         );
         Bukkit.getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            state.update(true);
         }

         return !event.isCancelled();
      }
   }

   public static EntityDeathEvent callEntityDeathEvent(EntityLiving victim) {
      return callEntityDeathEvent(victim, new ArrayList(0));
   }

   public static EntityDeathEvent callEntityDeathEvent(EntityLiving victim, List<org.bukkit.inventory.ItemStack> drops) {
      CraftLivingEntity entity = (CraftLivingEntity)victim.getBukkitEntity();
      EntityDeathEvent event = new EntityDeathEvent(entity, drops, victim.getExpReward());
      CraftWorld world = (CraftWorld)entity.getWorld();
      Bukkit.getServer().getPluginManager().callEvent(event);
      victim.expToDrop = event.getDroppedExp();

      for(org.bukkit.inventory.ItemStack stack : event.getDrops()) {
         if (stack != null && stack.getType() != Material.AIR && stack.getAmount() != 0) {
            world.dropItem(entity.getLocation(), stack);
         }
      }

      return event;
   }

   public static PlayerDeathEvent callPlayerDeathEvent(
      EntityPlayer victim, List<org.bukkit.inventory.ItemStack> drops, String deathMessage, boolean keepInventory
   ) {
      CraftPlayer entity = victim.getBukkitEntity();
      PlayerDeathEvent event = new PlayerDeathEvent(entity, drops, victim.getExpReward(), 0, deathMessage);
      event.setKeepInventory(keepInventory);
      event.setKeepLevel(victim.keepLevel);
      org.bukkit.World world = entity.getWorld();
      Bukkit.getServer().getPluginManager().callEvent(event);
      victim.keepLevel = event.getKeepLevel();
      victim.newLevel = event.getNewLevel();
      victim.newTotalExp = event.getNewTotalExp();
      victim.expToDrop = event.getDroppedExp();
      victim.newExp = event.getNewExp();

      for(org.bukkit.inventory.ItemStack stack : event.getDrops()) {
         if (stack != null && stack.getType() != Material.AIR) {
            world.dropItem(entity.getLocation(), stack);
         }
      }

      return event;
   }

   public static ServerListPingEvent callServerListPingEvent(Server craftServer, InetAddress address, String motd, int numPlayers, int maxPlayers) {
      ServerListPingEvent event = new ServerListPingEvent("", address, motd, numPlayers, maxPlayers);
      craftServer.getPluginManager().callEvent(event);
      return event;
   }

   private static EntityDamageEvent handleEntityDamageEvent(
      net.minecraft.world.entity.Entity entity,
      DamageSource source,
      Map<DamageModifier, Double> modifiers,
      Map<DamageModifier, Function<? super Double, Double>> modifierFunctions
   ) {
      return handleEntityDamageEvent(entity, source, modifiers, modifierFunctions, false);
   }

   private static EntityDamageEvent handleEntityDamageEvent(
      net.minecraft.world.entity.Entity entity,
      DamageSource source,
      Map<DamageModifier, Double> modifiers,
      Map<DamageModifier, Function<? super Double, Double>> modifierFunctions,
      boolean cancelled
   ) {
      if (source.a(DamageTypeTags.l)) {
         net.minecraft.world.entity.Entity damager = entityDamage;
         entityDamage = null;
         EntityDamageEvent event;
         if (damager == null) {
            event = new EntityDamageByBlockEvent(null, entity.getBukkitEntity(), DamageCause.BLOCK_EXPLOSION, modifiers, modifierFunctions);
         } else {
            boolean var10000 = entity instanceof EntityEnderDragon;
            DamageCause damageCause;
            if (damager instanceof TNTPrimed) {
               damageCause = DamageCause.BLOCK_EXPLOSION;
            } else {
               damageCause = DamageCause.ENTITY_EXPLOSION;
            }

            event = new EntityDamageByEntityEvent(damager.getBukkitEntity(), entity.getBukkitEntity(), damageCause, modifiers, modifierFunctions);
         }

         event.setCancelled(cancelled);
         callEvent(event);
         if (!event.isCancelled()) {
            event.getEntity().setLastDamageCause(event);
         } else {
            entity.lastDamageCancelled = true;
         }

         return event;
      } else if (source.d() != null || source.c() != null) {
         net.minecraft.world.entity.Entity damager = source.d();
         DamageCause cause = source.isSweep() ? DamageCause.ENTITY_SWEEP_ATTACK : DamageCause.ENTITY_ATTACK;
         if (source.b() && source.c() != null) {
            damager = source.c();
         }

         if (damager instanceof IProjectile) {
            if (damager.getBukkitEntity() instanceof ThrownPotion) {
               cause = DamageCause.MAGIC;
            } else if (damager.getBukkitEntity() instanceof Projectile) {
               cause = DamageCause.PROJECTILE;
            }
         } else if (source.a(DamageTypes.L)) {
            cause = DamageCause.THORNS;
         } else if (source.a(DamageTypes.O)) {
            cause = DamageCause.SONIC_BOOM;
         }

         return callEntityDamageEvent(damager, entity, cause, modifiers, modifierFunctions, cancelled);
      } else if (source.a(DamageTypes.m)) {
         EntityDamageEvent event = new EntityDamageByBlockEvent(null, entity.getBukkitEntity(), DamageCause.VOID, modifiers, modifierFunctions);
         event.setCancelled(cancelled);
         callEvent(event);
         if (!event.isCancelled()) {
            event.getEntity().setLastDamageCause(event);
         } else {
            entity.lastDamageCancelled = true;
         }

         return event;
      } else if (source.a(DamageTypes.d)) {
         EntityDamageEvent event = new EntityDamageByBlockEvent(blockDamage, entity.getBukkitEntity(), DamageCause.LAVA, modifiers, modifierFunctions);
         event.setCancelled(cancelled);
         Block damager = blockDamage;
         blockDamage = null;
         callEvent(event);
         blockDamage = damager;
         if (!event.isCancelled()) {
            event.getEntity().setLastDamageCause(event);
         } else {
            entity.lastDamageCancelled = true;
         }

         return event;
      } else if (blockDamage != null) {
         DamageCause cause = null;
         Block damager = blockDamage;
         if (source.a(DamageTypes.j) || source.a(DamageTypes.s) || source.a(DamageTypes.u) || source.a(DamageTypes.x) || source.a(DamageTypes.w)) {
            cause = DamageCause.CONTACT;
         } else if (source.a(DamageTypes.e)) {
            cause = DamageCause.HOT_FLOOR;
         } else if (source.a(DamageTypes.o)) {
            cause = DamageCause.MAGIC;
         } else {
            if (!source.a(DamageTypes.a)) {
               throw new IllegalStateException(String.format("Unhandled damage of %s by %s from %s", entity, damager, source.e()));
            }

            cause = DamageCause.FIRE;
         }

         EntityDamageEvent event = new EntityDamageByBlockEvent(damager, entity.getBukkitEntity(), cause, modifiers, modifierFunctions);
         event.setCancelled(cancelled);
         blockDamage = null;
         callEvent(event);
         blockDamage = damager;
         if (!event.isCancelled()) {
            event.getEntity().setLastDamageCause(event);
         } else {
            entity.lastDamageCancelled = true;
         }

         return event;
      } else if (entityDamage == null) {
         DamageCause cause = null;
         if (source.a(DamageTypes.a)) {
            cause = DamageCause.FIRE;
         } else if (source.a(DamageTypes.i)) {
            cause = DamageCause.STARVATION;
         } else if (source.a(DamageTypes.p)) {
            cause = DamageCause.WITHER;
         } else if (source.a(DamageTypes.f)) {
            cause = DamageCause.SUFFOCATION;
         } else if (source.a(DamageTypes.h)) {
            cause = DamageCause.DROWNING;
         } else if (source.a(DamageTypes.c)) {
            cause = DamageCause.FIRE_TICK;
         } else if (source.isMelting()) {
            cause = DamageCause.MELTING;
         } else if (source.isPoison()) {
            cause = DamageCause.POISON;
         } else if (source.a(DamageTypes.o)) {
            cause = DamageCause.MAGIC;
         } else if (source.a(DamageTypes.k)) {
            cause = DamageCause.FALL;
         } else if (source.a(DamageTypes.l)) {
            cause = DamageCause.FLY_INTO_WALL;
         } else if (source.a(DamageTypes.g)) {
            cause = DamageCause.CRAMMING;
         } else if (source.a(DamageTypes.r)) {
            cause = DamageCause.DRYOUT;
         } else if (source.a(DamageTypes.t)) {
            cause = DamageCause.FREEZE;
         } else {
            cause = DamageCause.CUSTOM;
         }

         if (cause != null) {
            return callEntityDamageEvent(null, entity, cause, modifiers, modifierFunctions, cancelled);
         } else {
            throw new IllegalStateException(String.format("Unhandled damage of %s from %s", entity, source.e()));
         }
      } else {
         DamageCause cause = null;
         CraftEntity damager = entityDamage.getBukkitEntity();
         entityDamage = null;
         if (source.a(DamageTypes.x) || source.a(DamageTypes.v) || source.a(DamageTypes.w)) {
            cause = DamageCause.FALLING_BLOCK;
         } else if (damager instanceof LightningStrike) {
            cause = DamageCause.LIGHTNING;
         } else if (source.a(DamageTypes.k)) {
            cause = DamageCause.FALL;
         } else if (source.a(DamageTypes.q)) {
            cause = DamageCause.DRAGON_BREATH;
         } else {
            if (!source.a(DamageTypes.o)) {
               throw new IllegalStateException(String.format("Unhandled damage of %s by %s from %s", entity, damager.getHandle(), source.e()));
            }

            cause = DamageCause.MAGIC;
         }

         EntityDamageEvent event = new EntityDamageByEntityEvent(damager, entity.getBukkitEntity(), cause, modifiers, modifierFunctions);
         event.setCancelled(cancelled);
         callEvent(event);
         if (!event.isCancelled()) {
            event.getEntity().setLastDamageCause(event);
         } else {
            entity.lastDamageCancelled = true;
         }

         return event;
      }
   }

   private static EntityDamageEvent callEntityDamageEvent(
      net.minecraft.world.entity.Entity damager,
      net.minecraft.world.entity.Entity damagee,
      DamageCause cause,
      Map<DamageModifier, Double> modifiers,
      Map<DamageModifier, Function<? super Double, Double>> modifierFunctions
   ) {
      return callEntityDamageEvent(damager, damagee, cause, modifiers, modifierFunctions, false);
   }

   private static EntityDamageEvent callEntityDamageEvent(
      net.minecraft.world.entity.Entity damager,
      net.minecraft.world.entity.Entity damagee,
      DamageCause cause,
      Map<DamageModifier, Double> modifiers,
      Map<DamageModifier, Function<? super Double, Double>> modifierFunctions,
      boolean cancelled
   ) {
      EntityDamageEvent event;
      if (damager != null) {
         event = new EntityDamageByEntityEvent(damager.getBukkitEntity(), damagee.getBukkitEntity(), cause, modifiers, modifierFunctions);
      } else {
         event = new EntityDamageEvent(damagee.getBukkitEntity(), cause, modifiers, modifierFunctions);
      }

      event.setCancelled(cancelled);
      callEvent(event);
      if (!event.isCancelled()) {
         event.getEntity().setLastDamageCause(event);
      } else {
         damagee.lastDamageCancelled = true;
      }

      return event;
   }

   public static EntityDamageEvent handleLivingEntityDamageEvent(
      net.minecraft.world.entity.Entity damagee,
      DamageSource source,
      double rawDamage,
      double hardHatModifier,
      double blockingModifier,
      double armorModifier,
      double resistanceModifier,
      double magicModifier,
      double absorptionModifier,
      Function<Double, Double> hardHat,
      Function<Double, Double> blocking,
      Function<Double, Double> armor,
      Function<Double, Double> resistance,
      Function<Double, Double> magic,
      Function<Double, Double> absorption
   ) {
      Map<DamageModifier, Double> modifiers = new EnumMap<>(DamageModifier.class);
      Map<DamageModifier, Function<? super Double, Double>> modifierFunctions = new EnumMap(DamageModifier.class);
      modifiers.put(DamageModifier.BASE, rawDamage);
      modifierFunctions.put(DamageModifier.BASE, ZERO);
      if (source.a(DamageTypes.v) || source.a(DamageTypes.w)) {
         modifiers.put(DamageModifier.HARD_HAT, hardHatModifier);
         modifierFunctions.put(DamageModifier.HARD_HAT, hardHat);
      }

      if (damagee instanceof EntityHuman) {
         modifiers.put(DamageModifier.BLOCKING, blockingModifier);
         modifierFunctions.put(DamageModifier.BLOCKING, blocking);
      }

      modifiers.put(DamageModifier.ARMOR, armorModifier);
      modifierFunctions.put(DamageModifier.ARMOR, armor);
      modifiers.put(DamageModifier.RESISTANCE, resistanceModifier);
      modifierFunctions.put(DamageModifier.RESISTANCE, resistance);
      modifiers.put(DamageModifier.MAGIC, magicModifier);
      modifierFunctions.put(DamageModifier.MAGIC, magic);
      modifiers.put(DamageModifier.ABSORPTION, absorptionModifier);
      modifierFunctions.put(DamageModifier.ABSORPTION, absorption);
      return handleEntityDamageEvent(damagee, source, modifiers, modifierFunctions);
   }

   public static boolean handleNonLivingEntityDamageEvent(net.minecraft.world.entity.Entity entity, DamageSource source, double damage) {
      return handleNonLivingEntityDamageEvent(entity, source, damage, true);
   }

   public static boolean handleNonLivingEntityDamageEvent(
      net.minecraft.world.entity.Entity entity, DamageSource source, double damage, boolean cancelOnZeroDamage
   ) {
      return handleNonLivingEntityDamageEvent(entity, source, damage, cancelOnZeroDamage, false);
   }

   public static EntityDamageEvent callNonLivingEntityDamageEvent(
      net.minecraft.world.entity.Entity entity, DamageSource source, double damage, boolean cancelled
   ) {
      EnumMap<DamageModifier, Double> modifiers = new EnumMap<>(DamageModifier.class);
      EnumMap<DamageModifier, Function<? super Double, Double>> functions = new EnumMap(DamageModifier.class);
      modifiers.put(DamageModifier.BASE, damage);
      functions.put(DamageModifier.BASE, ZERO);
      return handleEntityDamageEvent(entity, source, modifiers, functions, cancelled);
   }

   public static boolean handleNonLivingEntityDamageEvent(
      net.minecraft.world.entity.Entity entity, DamageSource source, double damage, boolean cancelOnZeroDamage, boolean cancelled
   ) {
      EntityDamageEvent event = callNonLivingEntityDamageEvent(entity, source, damage, cancelled);
      if (event == null) {
         return false;
      } else {
         return event.isCancelled() || cancelOnZeroDamage && event.getDamage() == 0.0;
      }
   }

   public static PlayerLevelChangeEvent callPlayerLevelChangeEvent(Player player, int oldLevel, int newLevel) {
      PlayerLevelChangeEvent event = new PlayerLevelChangeEvent(player, oldLevel, newLevel);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static PlayerExpChangeEvent callPlayerExpChangeEvent(EntityHuman entity, int expAmount) {
      Player player = (Player)entity.getBukkitEntity();
      PlayerExpChangeEvent event = new PlayerExpChangeEvent(player, expAmount);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static PlayerItemMendEvent callPlayerItemMendEvent(
      EntityHuman entity, EntityExperienceOrb orb, ItemStack nmsMendedItem, EnumItemSlot slot, int repairAmount
   ) {
      Player player = (Player)entity.getBukkitEntity();
      org.bukkit.inventory.ItemStack bukkitStack = CraftItemStack.asCraftMirror(nmsMendedItem);
      PlayerItemMendEvent event = new PlayerItemMendEvent(
         player, bukkitStack, CraftEquipmentSlot.getSlot(slot), (ExperienceOrb)orb.getBukkitEntity(), repairAmount
      );
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static boolean handleBlockGrowEvent(World world, BlockPosition pos, IBlockData block) {
      return handleBlockGrowEvent(world, pos, block, 3);
   }

   public static boolean handleBlockGrowEvent(World world, BlockPosition pos, IBlockData newData, int flag) {
      Block block = world.getWorld().getBlockAt(pos.u(), pos.v(), pos.w());
      CraftBlockState state = (CraftBlockState)block.getState();
      state.setData(newData);
      BlockGrowEvent event = new BlockGrowEvent(block, state);
      Bukkit.getPluginManager().callEvent(event);
      if (!event.isCancelled()) {
         state.update(true);
      }

      return !event.isCancelled();
   }

   public static FluidLevelChangeEvent callFluidLevelChangeEvent(World world, BlockPosition block, IBlockData newData) {
      FluidLevelChangeEvent event = new FluidLevelChangeEvent(CraftBlock.at(world, block), CraftBlockData.fromData(newData));
      world.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static FoodLevelChangeEvent callFoodLevelChangeEvent(EntityHuman entity, int level) {
      return callFoodLevelChangeEvent(entity, level, null);
   }

   public static FoodLevelChangeEvent callFoodLevelChangeEvent(EntityHuman entity, int level, ItemStack item) {
      FoodLevelChangeEvent event = new FoodLevelChangeEvent(entity.getBukkitEntity(), level, item == null ? null : CraftItemStack.asBukkitCopy(item));
      entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);
      return event;
   }

   public static PigZapEvent callPigZapEvent(
      net.minecraft.world.entity.Entity pig, net.minecraft.world.entity.Entity lightning, net.minecraft.world.entity.Entity pigzombie
   ) {
      PigZapEvent event = new PigZapEvent((Pig)pig.getBukkitEntity(), (LightningStrike)lightning.getBukkitEntity(), (PigZombie)pigzombie.getBukkitEntity());
      pig.getBukkitEntity().getServer().getPluginManager().callEvent(event);
      return event;
   }

   public static HorseJumpEvent callHorseJumpEvent(net.minecraft.world.entity.Entity horse, float power) {
      HorseJumpEvent event = new HorseJumpEvent((AbstractHorse)horse.getBukkitEntity(), power);
      horse.getBukkitEntity().getServer().getPluginManager().callEvent(event);
      return event;
   }

   public static EntityChangeBlockEvent callEntityChangeBlockEvent(net.minecraft.world.entity.Entity entity, BlockPosition position, IBlockData newBlock) {
      return callEntityChangeBlockEvent(entity, position, newBlock, false);
   }

   public static EntityChangeBlockEvent callEntityChangeBlockEvent(
      net.minecraft.world.entity.Entity entity, BlockPosition position, IBlockData newBlock, boolean cancelled
   ) {
      Block block = entity.H.getWorld().getBlockAt(position.u(), position.v(), position.w());
      EntityChangeBlockEvent event = new EntityChangeBlockEvent(entity.getBukkitEntity(), block, CraftBlockData.fromData(newBlock));
      event.setCancelled(cancelled);
      event.getEntity().getServer().getPluginManager().callEvent(event);
      return event;
   }

   public static CreeperPowerEvent callCreeperPowerEvent(
      net.minecraft.world.entity.Entity creeper, net.minecraft.world.entity.Entity lightning, PowerCause cause
   ) {
      CreeperPowerEvent event = new CreeperPowerEvent((Creeper)creeper.getBukkitEntity(), (LightningStrike)lightning.getBukkitEntity(), cause);
      creeper.getBukkitEntity().getServer().getPluginManager().callEvent(event);
      return event;
   }

   public static EntityTargetEvent callEntityTargetEvent(
      net.minecraft.world.entity.Entity entity, net.minecraft.world.entity.Entity target, TargetReason reason
   ) {
      EntityTargetEvent event = new EntityTargetEvent(entity.getBukkitEntity(), target == null ? null : target.getBukkitEntity(), reason);
      entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);
      return event;
   }

   public static EntityTargetLivingEntityEvent callEntityTargetLivingEvent(net.minecraft.world.entity.Entity entity, EntityLiving target, TargetReason reason) {
      EntityTargetLivingEntityEvent event = new EntityTargetLivingEntityEvent(
         entity.getBukkitEntity(), target == null ? null : (LivingEntity)target.getBukkitEntity(), reason
      );
      entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);
      return event;
   }

   public static EntityBreakDoorEvent callEntityBreakDoorEvent(net.minecraft.world.entity.Entity entity, BlockPosition pos) {
      Entity entity1 = entity.getBukkitEntity();
      Block block = CraftBlock.at(entity.H, pos);
      EntityBreakDoorEvent event = new EntityBreakDoorEvent((LivingEntity)entity1, block);
      entity1.getServer().getPluginManager().callEvent(event);
      return event;
   }

   public static Container callInventoryOpenEvent(EntityPlayer player, Container container) {
      return callInventoryOpenEvent(player, container, false);
   }

   public static Container callInventoryOpenEvent(EntityPlayer player, Container container, boolean cancelled) {
      if (player.bP != player.bO) {
         player.b.a(new PacketPlayInCloseWindow(player.bP.j));
      }

      CraftServer server = player.H.getCraftServer();
      CraftPlayer craftPlayer = player.getBukkitEntity();
      player.bP.transferTo(container, craftPlayer);
      InventoryOpenEvent event = new InventoryOpenEvent(container.getBukkitView());
      event.setCancelled(cancelled);
      server.getPluginManager().callEvent(event);
      if (event.isCancelled()) {
         container.transferTo(player.bP, craftPlayer);
         return null;
      } else {
         return container;
      }
   }

   public static ItemStack callPreCraftEvent(IInventory matrix, IInventory resultInventory, ItemStack result, InventoryView lastCraftView, boolean isRepair) {
      CraftInventoryCrafting inventory = new CraftInventoryCrafting(matrix, resultInventory);
      inventory.setResult(CraftItemStack.asCraftMirror(result));
      PrepareItemCraftEvent event = new PrepareItemCraftEvent(inventory, lastCraftView, isRepair);
      Bukkit.getPluginManager().callEvent(event);
      org.bukkit.inventory.ItemStack bitem = event.getInventory().getResult();
      return CraftItemStack.asNMSCopy(bitem);
   }

   public static ProjectileLaunchEvent callProjectileLaunchEvent(net.minecraft.world.entity.Entity entity) {
      Projectile bukkitEntity = (Projectile)entity.getBukkitEntity();
      ProjectileLaunchEvent event = new ProjectileLaunchEvent(bukkitEntity);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static ProjectileHitEvent callProjectileHitEvent(net.minecraft.world.entity.Entity entity, MovingObjectPosition position) {
      if (position.c() == MovingObjectPosition.EnumMovingObjectType.a) {
         return null;
      } else {
         Block hitBlock = null;
         BlockFace hitFace = null;
         if (position.c() == MovingObjectPosition.EnumMovingObjectType.b) {
            MovingObjectPositionBlock positionBlock = (MovingObjectPositionBlock)position;
            hitBlock = CraftBlock.at(entity.H, positionBlock.a());
            hitFace = CraftBlock.notchToBlockFace(positionBlock.b());
         }

         Entity hitEntity = null;
         if (position.c() == MovingObjectPosition.EnumMovingObjectType.c) {
            hitEntity = ((MovingObjectPositionEntity)position).a().getBukkitEntity();
         }

         ProjectileHitEvent event = new ProjectileHitEvent((Projectile)entity.getBukkitEntity(), hitEntity, hitBlock, hitFace);
         entity.H.getCraftServer().getPluginManager().callEvent(event);
         return event;
      }
   }

   public static ExpBottleEvent callExpBottleEvent(net.minecraft.world.entity.Entity entity, int exp) {
      ThrownExpBottle bottle = (ThrownExpBottle)entity.getBukkitEntity();
      ExpBottleEvent event = new ExpBottleEvent(bottle, exp);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static BlockRedstoneEvent callRedstoneChange(World world, BlockPosition pos, int oldCurrent, int newCurrent) {
      BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld().getBlockAt(pos.u(), pos.v(), pos.w()), oldCurrent, newCurrent);
      world.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static NotePlayEvent callNotePlayEvent(World world, BlockPosition pos, BlockPropertyInstrument instrument, int note) {
      NotePlayEvent event = new NotePlayEvent(
         world.getWorld().getBlockAt(pos.u(), pos.v(), pos.w()), Instrument.getByType((byte)instrument.ordinal()), new Note(note)
      );
      world.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static void callPlayerItemBreakEvent(EntityHuman human, ItemStack brokenItem) {
      CraftItemStack item = CraftItemStack.asCraftMirror(brokenItem);
      PlayerItemBreakEvent event = new PlayerItemBreakEvent((Player)human.getBukkitEntity(), item);
      Bukkit.getPluginManager().callEvent(event);
   }

   public static BlockIgniteEvent callBlockIgniteEvent(World world, BlockPosition block, BlockPosition source) {
      org.bukkit.World bukkitWorld = world.getWorld();
      Block igniter = bukkitWorld.getBlockAt(source.u(), source.v(), source.w());

      BlockIgniteEvent event = new BlockIgniteEvent(
         bukkitWorld.getBlockAt(block.u(), block.v(), block.w()), switch($SWITCH_TABLE$org$bukkit$Material()[igniter.getType().ordinal()]) {
            case 644 -> IgniteCause.FLINT_AND_STEEL;
            case 1230 -> IgniteCause.LAVA;
            default -> IgniteCause.SPREAD;
         }, igniter
      );
      world.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static BlockIgniteEvent callBlockIgniteEvent(World world, BlockPosition pos, net.minecraft.world.entity.Entity igniter) {
      org.bukkit.World bukkitWorld = world.getWorld();
      Entity bukkitIgniter = igniter.getBukkitEntity();

      IgniteCause cause = switch(bukkitIgniter.getType()) {
         case ARROW -> IgniteCause.ARROW;
         case FIREBALL, SMALL_FIREBALL -> IgniteCause.FIREBALL;
         case ENDER_CRYSTAL -> IgniteCause.ENDER_CRYSTAL;
         case LIGHTNING -> IgniteCause.LIGHTNING;
         default -> IgniteCause.FLINT_AND_STEEL;
      };
      if (igniter instanceof IProjectile) {
         net.minecraft.world.entity.Entity shooter = ((IProjectile)igniter).v();
         if (shooter != null) {
            bukkitIgniter = shooter.getBukkitEntity();
         }
      }

      BlockIgniteEvent event = new BlockIgniteEvent(bukkitWorld.getBlockAt(pos.u(), pos.v(), pos.w()), cause, bukkitIgniter);
      world.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static BlockIgniteEvent callBlockIgniteEvent(World world, int x, int y, int z, Explosion explosion) {
      org.bukkit.World bukkitWorld = world.getWorld();
      Entity igniter = explosion.j == null ? null : explosion.j.getBukkitEntity();
      BlockIgniteEvent event = new BlockIgniteEvent(bukkitWorld.getBlockAt(x, y, z), IgniteCause.EXPLOSION, igniter);
      world.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static BlockIgniteEvent callBlockIgniteEvent(World world, BlockPosition pos, IgniteCause cause, net.minecraft.world.entity.Entity igniter) {
      BlockIgniteEvent event = new BlockIgniteEvent(world.getWorld().getBlockAt(pos.u(), pos.v(), pos.w()), cause, igniter.getBukkitEntity());
      world.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static void handleInventoryCloseEvent(EntityHuman human) {
      InventoryCloseEvent event = new InventoryCloseEvent(human.bP.getBukkitView());
      human.H.getCraftServer().getPluginManager().callEvent(event);
      human.bP.transferTo(human.bO, human.getBukkitEntity());
   }

   public static ItemStack handleEditBookEvent(EntityPlayer player, int itemInHandIndex, ItemStack itemInHand, ItemStack newBookItem) {
      PlayerEditBookEvent editBookEvent = new PlayerEditBookEvent(
         player.getBukkitEntity(),
         itemInHandIndex >= 0 && itemInHandIndex <= 8 ? itemInHandIndex : -1,
         (BookMeta)CraftItemStack.getItemMeta(itemInHand),
         (BookMeta)CraftItemStack.getItemMeta(newBookItem),
         newBookItem.c() == Items.td
      );
      player.H.getCraftServer().getPluginManager().callEvent(editBookEvent);
      if (itemInHand != null && itemInHand.c() == Items.tc && !editBookEvent.isCancelled()) {
         if (editBookEvent.isSigning()) {
            itemInHand.setItem(Items.td);
         }

         CraftMetaBook meta = (CraftMetaBook)editBookEvent.getNewBookMeta();
         CraftItemStack.setItemMeta(itemInHand, meta);
      }

      return itemInHand;
   }

   public static PlayerUnleashEntityEvent callPlayerUnleashEntityEvent(EntityInsentient entity, EntityHuman player, EnumHand enumhand) {
      PlayerUnleashEntityEvent event = new PlayerUnleashEntityEvent(
         entity.getBukkitEntity(), (Player)player.getBukkitEntity(), CraftEquipmentSlot.getHand(enumhand)
      );
      entity.H.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static PlayerLeashEntityEvent callPlayerLeashEntityEvent(
      EntityInsentient entity, net.minecraft.world.entity.Entity leashHolder, EntityHuman player, EnumHand enumhand
   ) {
      PlayerLeashEntityEvent event = new PlayerLeashEntityEvent(
         entity.getBukkitEntity(), leashHolder.getBukkitEntity(), (Player)player.getBukkitEntity(), CraftEquipmentSlot.getHand(enumhand)
      );
      entity.H.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static BlockShearEntityEvent callBlockShearEntityEvent(net.minecraft.world.entity.Entity animal, Block dispenser, CraftItemStack is) {
      BlockShearEntityEvent bse = new BlockShearEntityEvent(dispenser, animal.getBukkitEntity(), is);
      Bukkit.getPluginManager().callEvent(bse);
      return bse;
   }

   public static boolean handlePlayerShearEntityEvent(EntityHuman player, net.minecraft.world.entity.Entity sheared, ItemStack shears, EnumHand hand) {
      if (!(player instanceof EntityPlayer)) {
         return true;
      } else {
         PlayerShearEntityEvent event = new PlayerShearEntityEvent(
            (Player)player.getBukkitEntity(),
            sheared.getBukkitEntity(),
            CraftItemStack.asCraftMirror(shears),
            hand == EnumHand.b ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND
         );
         Bukkit.getPluginManager().callEvent(event);
         return !event.isCancelled();
      }
   }

   public static Cancellable handleStatisticsIncrease(EntityHuman entityHuman, Statistic<?> statistic, int current, int newValue) {
      Player player = ((EntityPlayer)entityHuman).getBukkitEntity();
      org.bukkit.Statistic stat = CraftStatistic.getBukkitStatistic(statistic);
      if (stat == null) {
         System.err.println("Unhandled statistic: " + statistic);
         return null;
      } else {
         switch(stat) {
            case PLAY_ONE_MINUTE:
            case TOTAL_WORLD_TIME:
            case WALK_ONE_CM:
            case WALK_ON_WATER_ONE_CM:
            case FALL_ONE_CM:
            case SNEAK_TIME:
            case CLIMB_ONE_CM:
            case FLY_ONE_CM:
            case WALK_UNDER_WATER_ONE_CM:
            case MINECART_ONE_CM:
            case BOAT_ONE_CM:
            case PIG_ONE_CM:
            case HORSE_ONE_CM:
            case SPRINT_ONE_CM:
            case CROUCH_ONE_CM:
            case AVIATE_ONE_CM:
            case TIME_SINCE_DEATH:
            case TIME_SINCE_REST:
            case SWIM_ONE_CM:
            case STRIDER_ONE_CM:
               return null;
            default:
               Event event;
               if (stat.getType() == Type.UNTYPED) {
                  event = new PlayerStatisticIncrementEvent(player, stat, current, newValue);
               } else if (stat.getType() == Type.ENTITY) {
                  EntityType entityType = CraftStatistic.getEntityTypeFromStatistic(statistic);
                  event = new PlayerStatisticIncrementEvent(player, stat, current, newValue, entityType);
               } else {
                  Material material = CraftStatistic.getMaterialFromStatistic(statistic);
                  event = new PlayerStatisticIncrementEvent(player, stat, current, newValue, material);
               }

               entityHuman.H.getCraftServer().getPluginManager().callEvent(event);
               return (Cancellable)event;
         }
      }
   }

   public static FireworkExplodeEvent callFireworkExplodeEvent(EntityFireworks firework) {
      FireworkExplodeEvent event = new FireworkExplodeEvent((Firework)firework.getBukkitEntity());
      firework.H.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static PrepareAnvilEvent callPrepareAnvilEvent(InventoryView view, ItemStack item) {
      PrepareAnvilEvent event = new PrepareAnvilEvent(view, CraftItemStack.asCraftMirror(item).clone());
      event.getView().getPlayer().getServer().getPluginManager().callEvent(event);
      event.getInventory().setItem(2, event.getResult());
      return event;
   }

   public static PrepareGrindstoneEvent callPrepareGrindstoneEvent(InventoryView view, ItemStack item) {
      PrepareGrindstoneEvent event = new PrepareGrindstoneEvent(view, CraftItemStack.asCraftMirror(item).clone());
      event.getView().getPlayer().getServer().getPluginManager().callEvent(event);
      event.getInventory().setItem(2, event.getResult());
      return event;
   }

   public static PrepareSmithingEvent callPrepareSmithingEvent(InventoryView view, ItemStack item) {
      PrepareSmithingEvent event = new PrepareSmithingEvent(view, CraftItemStack.asCraftMirror(item).clone());
      event.getView().getPlayer().getServer().getPluginManager().callEvent(event);
      event.getInventory().setResult(event.getResult());
      return event;
   }

   public static SpawnerSpawnEvent callSpawnerSpawnEvent(net.minecraft.world.entity.Entity spawnee, BlockPosition pos) {
      CraftEntity entity = spawnee.getBukkitEntity();
      BlockState state = entity.getWorld().getBlockAt(pos.u(), pos.v(), pos.w()).getState();
      if (!(state instanceof CreatureSpawner)) {
         state = null;
      }

      SpawnerSpawnEvent event = new SpawnerSpawnEvent(entity, (CreatureSpawner)state);
      entity.getServer().getPluginManager().callEvent(event);
      return event;
   }

   public static EntityToggleGlideEvent callToggleGlideEvent(EntityLiving entity, boolean gliding) {
      EntityToggleGlideEvent event = new EntityToggleGlideEvent((LivingEntity)entity.getBukkitEntity(), gliding);
      entity.H.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static EntityToggleSwimEvent callToggleSwimEvent(EntityLiving entity, boolean swimming) {
      EntityToggleSwimEvent event = new EntityToggleSwimEvent((LivingEntity)entity.getBukkitEntity(), swimming);
      entity.H.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static AreaEffectCloudApplyEvent callAreaEffectCloudApplyEvent(EntityAreaEffectCloud cloud, List<LivingEntity> entities) {
      AreaEffectCloudApplyEvent event = new AreaEffectCloudApplyEvent((AreaEffectCloud)cloud.getBukkitEntity(), entities);
      cloud.H.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static VehicleCreateEvent callVehicleCreateEvent(net.minecraft.world.entity.Entity entity) {
      Vehicle bukkitEntity = (Vehicle)entity.getBukkitEntity();
      VehicleCreateEvent event = new VehicleCreateEvent(bukkitEntity);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static EntityBreedEvent callEntityBreedEvent(
      EntityLiving child, EntityLiving mother, EntityLiving father, EntityLiving breeder, ItemStack bredWith, int experience
   ) {
      LivingEntity breederEntity = (LivingEntity)(breeder == null ? null : breeder.getBukkitEntity());
      CraftItemStack bredWithStack = bredWith == null ? null : CraftItemStack.asCraftMirror(bredWith).clone();
      EntityBreedEvent event = new EntityBreedEvent(
         (LivingEntity)child.getBukkitEntity(),
         (LivingEntity)mother.getBukkitEntity(),
         (LivingEntity)father.getBukkitEntity(),
         breederEntity,
         bredWithStack,
         experience
      );
      child.H.getCraftServer().getPluginManager().callEvent(event);
      return event;
   }

   public static BlockPhysicsEvent callBlockPhysicsEvent(GeneratorAccess world, BlockPosition blockposition) {
      Block block = CraftBlock.at(world, blockposition);
      BlockPhysicsEvent event = new BlockPhysicsEvent(block, block.getBlockData());
      if (world instanceof World) {
         ((World)world).n().server.getPluginManager().callEvent(event);
      }

      return event;
   }

   public static boolean handleBlockFormEvent(World world, BlockPosition pos, IBlockData block) {
      return handleBlockFormEvent(world, pos, block, 3);
   }

   public static EntityPotionEffectEvent callEntityPotionEffectChangeEvent(
      EntityLiving entity, @Nullable MobEffect oldEffect, @Nullable MobEffect newEffect, org.bukkit.event.entity.EntityPotionEffectEvent.Cause cause
   ) {
      return callEntityPotionEffectChangeEvent(entity, oldEffect, newEffect, cause, true);
   }

   public static EntityPotionEffectEvent callEntityPotionEffectChangeEvent(
      EntityLiving entity,
      @Nullable MobEffect oldEffect,
      @Nullable MobEffect newEffect,
      org.bukkit.event.entity.EntityPotionEffectEvent.Cause cause,
      org.bukkit.event.entity.EntityPotionEffectEvent.Action action
   ) {
      return callEntityPotionEffectChangeEvent(entity, oldEffect, newEffect, cause, action, true);
   }

   public static EntityPotionEffectEvent callEntityPotionEffectChangeEvent(
      EntityLiving entity,
      @Nullable MobEffect oldEffect,
      @Nullable MobEffect newEffect,
      org.bukkit.event.entity.EntityPotionEffectEvent.Cause cause,
      boolean willOverride
   ) {
      org.bukkit.event.entity.EntityPotionEffectEvent.Action action = org.bukkit.event.entity.EntityPotionEffectEvent.Action.CHANGED;
      if (oldEffect == null) {
         action = org.bukkit.event.entity.EntityPotionEffectEvent.Action.ADDED;
      } else if (newEffect == null) {
         action = org.bukkit.event.entity.EntityPotionEffectEvent.Action.REMOVED;
      }

      return callEntityPotionEffectChangeEvent(entity, oldEffect, newEffect, cause, action, willOverride);
   }

   public static EntityPotionEffectEvent callEntityPotionEffectChangeEvent(
      EntityLiving entity,
      @Nullable MobEffect oldEffect,
      @Nullable MobEffect newEffect,
      org.bukkit.event.entity.EntityPotionEffectEvent.Cause cause,
      org.bukkit.event.entity.EntityPotionEffectEvent.Action action,
      boolean willOverride
   ) {
      PotionEffect bukkitOldEffect = oldEffect == null ? null : CraftPotionUtil.toBukkit(oldEffect);
      PotionEffect bukkitNewEffect = newEffect == null ? null : CraftPotionUtil.toBukkit(newEffect);
      if (bukkitOldEffect == null && bukkitNewEffect == null) {
         throw new IllegalStateException("Old and new potion effect are both null");
      } else {
         EntityPotionEffectEvent event = new EntityPotionEffectEvent(
            (LivingEntity)entity.getBukkitEntity(), bukkitOldEffect, bukkitNewEffect, cause, action, willOverride
         );
         Bukkit.getPluginManager().callEvent(event);
         return event;
      }
   }

   public static boolean handleBlockFormEvent(World world, BlockPosition pos, IBlockData block, @Nullable net.minecraft.world.entity.Entity entity) {
      return handleBlockFormEvent(world, pos, block, 3, entity);
   }

   public static boolean handleBlockFormEvent(World world, BlockPosition pos, IBlockData block, int flag) {
      return handleBlockFormEvent(world, pos, block, flag, null);
   }

   public static boolean handleBlockFormEvent(World world, BlockPosition pos, IBlockData block, int flag, @Nullable net.minecraft.world.entity.Entity entity) {
      CraftBlockState blockState = CraftBlockStates.getBlockState(world, pos, flag);
      blockState.setData(block);
      BlockFormEvent event = (BlockFormEvent)(entity == null
         ? new BlockFormEvent(blockState.getBlock(), blockState)
         : new EntityBlockFormEvent(entity.getBukkitEntity(), blockState.getBlock(), blockState));
      world.getCraftServer().getPluginManager().callEvent(event);
      if (!event.isCancelled()) {
         blockState.update(true);
      }

      return !event.isCancelled();
   }

   public static boolean handleBatToggleSleepEvent(net.minecraft.world.entity.Entity bat, boolean awake) {
      BatToggleSleepEvent event = new BatToggleSleepEvent((Bat)bat.getBukkitEntity(), awake);
      Bukkit.getPluginManager().callEvent(event);
      return !event.isCancelled();
   }

   public static boolean handlePlayerRecipeListUpdateEvent(EntityHuman who, MinecraftKey recipe) {
      PlayerRecipeDiscoverEvent event = new PlayerRecipeDiscoverEvent((Player)who.getBukkitEntity(), CraftNamespacedKey.fromMinecraft(recipe));
      Bukkit.getPluginManager().callEvent(event);
      return !event.isCancelled();
   }

   public static EntityPickupItemEvent callEntityPickupItemEvent(net.minecraft.world.entity.Entity who, EntityItem item, int remaining, boolean cancelled) {
      EntityPickupItemEvent event = new EntityPickupItemEvent((LivingEntity)who.getBukkitEntity(), (Item)item.getBukkitEntity(), remaining);
      event.setCancelled(cancelled);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static LightningStrikeEvent callLightningStrikeEvent(LightningStrike entity, Cause cause) {
      LightningStrikeEvent event = new LightningStrikeEvent(entity.getWorld(), entity, cause);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static boolean callRaidTriggerEvent(Raid raid, EntityPlayer player) {
      RaidTriggerEvent event = new RaidTriggerEvent(new CraftRaid(raid), raid.i().getWorld(), player.getBukkitEntity());
      Bukkit.getPluginManager().callEvent(event);
      return !event.isCancelled();
   }

   public static void callRaidFinishEvent(Raid raid, List<Player> players) {
      RaidFinishEvent event = new RaidFinishEvent(new CraftRaid(raid), raid.i().getWorld(), players);
      Bukkit.getPluginManager().callEvent(event);
   }

   public static void callRaidStopEvent(Raid raid, Reason reason) {
      RaidStopEvent event = new RaidStopEvent(new CraftRaid(raid), raid.i().getWorld(), reason);
      Bukkit.getPluginManager().callEvent(event);
   }

   public static void callRaidSpawnWaveEvent(Raid raid, EntityRaider leader, List<EntityRaider> raiders) {
      Raider craftLeader = (CraftRaider)leader.getBukkitEntity();
      List<Raider> craftRaiders = new ArrayList();

      for(EntityRaider entityRaider : raiders) {
         craftRaiders.add((Raider)entityRaider.getBukkitEntity());
      }

      RaidSpawnWaveEvent event = new RaidSpawnWaveEvent(new CraftRaid(raid), raid.i().getWorld(), craftLeader, craftRaiders);
      Bukkit.getPluginManager().callEvent(event);
   }

   public static LootGenerateEvent callLootGenerateEvent(
      IInventory inventory, LootTable lootTable, LootTableInfo lootInfo, List<ItemStack> loot, boolean plugin
   ) {
      CraftWorld world = lootInfo.c().getWorld();
      net.minecraft.world.entity.Entity entity = lootInfo.c(LootContextParameters.a);
      NamespacedKey key = CraftNamespacedKey.fromMinecraft(world.getHandle().n().aH().lootTableToKey.get(lootTable));
      CraftLootTable craftLootTable = new CraftLootTable(key, lootTable);
      List<org.bukkit.inventory.ItemStack> bukkitLoot = loot.stream().map(CraftItemStack::asCraftMirror).collect(Collectors.toCollection(ArrayList::new));
      LootGenerateEvent event = new LootGenerateEvent(
         world,
         entity != null ? entity.getBukkitEntity() : null,
         inventory.getOwner(),
         craftLootTable,
         CraftLootTable.convertContext(lootInfo),
         bukkitLoot,
         plugin
      );
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static boolean callStriderTemperatureChangeEvent(EntityStrider strider, boolean shivering) {
      StriderTemperatureChangeEvent event = new StriderTemperatureChangeEvent((Strider)strider.getBukkitEntity(), shivering);
      Bukkit.getPluginManager().callEvent(event);
      return !event.isCancelled();
   }

   public static boolean handleEntitySpellCastEvent(EntityIllagerWizard caster, EntityIllagerWizard.Spell spell) {
      EntitySpellCastEvent event = new EntitySpellCastEvent((Spellcaster)caster.getBukkitEntity(), CraftSpellcaster.toBukkitSpell(spell));
      Bukkit.getPluginManager().callEvent(event);
      return !event.isCancelled();
   }

   public static ArrowBodyCountChangeEvent callArrowBodyCountChangeEvent(EntityLiving entity, int oldAmount, int newAmount, boolean isReset) {
      LivingEntity bukkitEntity = (LivingEntity)entity.getBukkitEntity();
      ArrowBodyCountChangeEvent event = new ArrowBodyCountChangeEvent(bukkitEntity, oldAmount, newAmount, isReset);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static EntityExhaustionEvent callPlayerExhaustionEvent(EntityHuman humanEntity, ExhaustionReason exhaustionReason, float exhaustion) {
      EntityExhaustionEvent event = new EntityExhaustionEvent(humanEntity.getBukkitEntity(), exhaustionReason, exhaustion);
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static PiglinBarterEvent callPiglinBarterEvent(EntityPiglin piglin, List<ItemStack> outcome, ItemStack input) {
      PiglinBarterEvent event = new PiglinBarterEvent(
         (Piglin)piglin.getBukkitEntity(), CraftItemStack.asBukkitCopy(input), outcome.stream().map(CraftItemStack::asBukkitCopy).collect(Collectors.toList())
      );
      Bukkit.getPluginManager().callEvent(event);
      return event;
   }

   public static void callEntitiesLoadEvent(World world, ChunkCoordIntPair coords, List<net.minecraft.world.entity.Entity> entities) {
      List<Entity> bukkitEntities = Collections.unmodifiableList(
         entities.stream().map(net.minecraft.world.entity.Entity::getBukkitEntity).collect(Collectors.toList())
      );
      EntitiesLoadEvent event = new EntitiesLoadEvent(new CraftChunk((WorldServer)world, coords.e, coords.f), bukkitEntities);
      Bukkit.getPluginManager().callEvent(event);
   }

   public static void callEntitiesUnloadEvent(World world, ChunkCoordIntPair coords, List<net.minecraft.world.entity.Entity> entities) {
      List<Entity> bukkitEntities = Collections.unmodifiableList(
         entities.stream().map(net.minecraft.world.entity.Entity::getBukkitEntity).collect(Collectors.toList())
      );
      EntitiesUnloadEvent event = new EntitiesUnloadEvent(new CraftChunk((WorldServer)world, coords.e, coords.f), bukkitEntities);
      Bukkit.getPluginManager().callEvent(event);
   }
}
