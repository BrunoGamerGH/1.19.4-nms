package org.bukkit.craftbukkit.v1_19_R3;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableBiMap.Builder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.stats.ServerStatisticManager;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.Statistic.Type;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.entity.EntityType;

public enum CraftStatistic {
   DAMAGE_DEALT(StatisticList.G),
   DAMAGE_TAKEN(StatisticList.J),
   DEATHS(StatisticList.N),
   MOB_KILLS(StatisticList.O),
   PLAYER_KILLS(StatisticList.Q),
   FISH_CAUGHT(StatisticList.R),
   ANIMALS_BRED(StatisticList.P),
   LEAVE_GAME(StatisticList.j),
   JUMP(StatisticList.E),
   DROP_COUNT(StatisticList.F),
   DROP(new MinecraftKey("dropped")),
   PICKUP(new MinecraftKey("picked_up")),
   PLAY_ONE_MINUTE(StatisticList.k),
   TOTAL_WORLD_TIME(StatisticList.l),
   WALK_ONE_CM(StatisticList.p),
   WALK_ON_WATER_ONE_CM(StatisticList.s),
   FALL_ONE_CM(StatisticList.t),
   SNEAK_TIME(StatisticList.o),
   CLIMB_ONE_CM(StatisticList.u),
   FLY_ONE_CM(StatisticList.v),
   WALK_UNDER_WATER_ONE_CM(StatisticList.w),
   MINECART_ONE_CM(StatisticList.x),
   BOAT_ONE_CM(StatisticList.y),
   PIG_ONE_CM(StatisticList.z),
   HORSE_ONE_CM(StatisticList.A),
   SPRINT_ONE_CM(StatisticList.r),
   CROUCH_ONE_CM(StatisticList.q),
   AVIATE_ONE_CM(StatisticList.B),
   MINE_BLOCK(new MinecraftKey("mined")),
   USE_ITEM(new MinecraftKey("used")),
   BREAK_ITEM(new MinecraftKey("broken")),
   CRAFT_ITEM(new MinecraftKey("crafted")),
   KILL_ENTITY(new MinecraftKey("killed")),
   ENTITY_KILLED_BY(new MinecraftKey("killed_by")),
   TIME_SINCE_DEATH(StatisticList.m),
   TALKED_TO_VILLAGER(StatisticList.S),
   TRADED_WITH_VILLAGER(StatisticList.T),
   CAKE_SLICES_EATEN(StatisticList.U),
   CAULDRON_FILLED(StatisticList.V),
   CAULDRON_USED(StatisticList.W),
   ARMOR_CLEANED(StatisticList.X),
   BANNER_CLEANED(StatisticList.Y),
   BREWINGSTAND_INTERACTION(StatisticList.aa),
   BEACON_INTERACTION(StatisticList.ab),
   DROPPER_INSPECTED(StatisticList.ac),
   HOPPER_INSPECTED(StatisticList.ad),
   DISPENSER_INSPECTED(StatisticList.ae),
   NOTEBLOCK_PLAYED(StatisticList.af),
   NOTEBLOCK_TUNED(StatisticList.ag),
   FLOWER_POTTED(StatisticList.ah),
   TRAPPED_CHEST_TRIGGERED(StatisticList.ai),
   ENDERCHEST_OPENED(StatisticList.aj),
   ITEM_ENCHANTED(StatisticList.ak),
   RECORD_PLAYED(StatisticList.al),
   FURNACE_INTERACTION(StatisticList.am),
   CRAFTING_TABLE_INTERACTION(StatisticList.an),
   CHEST_OPENED(StatisticList.ao),
   SLEEP_IN_BED(StatisticList.ap),
   SHULKER_BOX_OPENED(StatisticList.aq),
   TIME_SINCE_REST(StatisticList.n),
   SWIM_ONE_CM(StatisticList.C),
   DAMAGE_DEALT_ABSORBED(StatisticList.H),
   DAMAGE_DEALT_RESISTED(StatisticList.I),
   DAMAGE_BLOCKED_BY_SHIELD(StatisticList.K),
   DAMAGE_ABSORBED(StatisticList.L),
   DAMAGE_RESISTED(StatisticList.M),
   CLEAN_SHULKER_BOX(StatisticList.Z),
   OPEN_BARREL(StatisticList.ar),
   INTERACT_WITH_BLAST_FURNACE(StatisticList.as),
   INTERACT_WITH_SMOKER(StatisticList.at),
   INTERACT_WITH_LECTERN(StatisticList.au),
   INTERACT_WITH_CAMPFIRE(StatisticList.av),
   INTERACT_WITH_CARTOGRAPHY_TABLE(StatisticList.aw),
   INTERACT_WITH_LOOM(StatisticList.ax),
   INTERACT_WITH_STONECUTTER(StatisticList.ay),
   BELL_RING(StatisticList.az),
   RAID_TRIGGER(StatisticList.aA),
   RAID_WIN(StatisticList.aB),
   INTERACT_WITH_ANVIL(StatisticList.aC),
   INTERACT_WITH_GRINDSTONE(StatisticList.aD),
   TARGET_HIT(StatisticList.aE),
   INTERACT_WITH_SMITHING_TABLE(StatisticList.aF),
   STRIDER_ONE_CM(StatisticList.D);

   private final MinecraftKey minecraftKey;
   private final Statistic bukkit;
   private static final BiMap<MinecraftKey, Statistic> statistics;

   static {
      Builder<MinecraftKey, Statistic> statisticBuilder = ImmutableBiMap.builder();

      CraftStatistic[] var4;
      for(CraftStatistic statistic : var4 = values()) {
         statisticBuilder.put(statistic.minecraftKey, statistic.bukkit);
      }

      statistics = statisticBuilder.build();
   }

   private CraftStatistic(MinecraftKey minecraftKey) {
      this.minecraftKey = minecraftKey;
      this.bukkit = Statistic.valueOf(this.name());
      Preconditions.checkState(this.bukkit != null, "Bukkit statistic %s does not exist", this.name());
   }

   public static Statistic getBukkitStatistic(net.minecraft.stats.Statistic<?> statistic) {
      IRegistry statRegistry = statistic.a().a();
      MinecraftKey nmsKey = BuiltInRegistries.x.b(statistic.a());
      if (statRegistry == BuiltInRegistries.n) {
         nmsKey = (MinecraftKey)statistic.b();
      }

      return (Statistic)statistics.get(nmsKey);
   }

   public static net.minecraft.stats.Statistic getNMSStatistic(Statistic bukkit) {
      Preconditions.checkArgument(bukkit.getType() == Type.UNTYPED, "This method only accepts untyped statistics");
      net.minecraft.stats.Statistic<MinecraftKey> nms = StatisticList.i.b((MinecraftKey)statistics.inverse().get(bukkit));
      Preconditions.checkArgument(nms != null, "NMS Statistic %s does not exist", bukkit);
      return nms;
   }

   public static net.minecraft.stats.Statistic getMaterialStatistic(Statistic stat, Material material) {
      try {
         if (stat == Statistic.MINE_BLOCK) {
            return StatisticList.a.b(CraftMagicNumbers.getBlock(material));
         } else if (stat == Statistic.CRAFT_ITEM) {
            return StatisticList.b.b(CraftMagicNumbers.getItem(material));
         } else if (stat == Statistic.USE_ITEM) {
            return StatisticList.c.b(CraftMagicNumbers.getItem(material));
         } else if (stat == Statistic.BREAK_ITEM) {
            return StatisticList.d.b(CraftMagicNumbers.getItem(material));
         } else if (stat == Statistic.PICKUP) {
            return StatisticList.e.b(CraftMagicNumbers.getItem(material));
         } else {
            return stat == Statistic.DROP ? StatisticList.f.b(CraftMagicNumbers.getItem(material)) : null;
         }
      } catch (ArrayIndexOutOfBoundsException var3) {
         return null;
      }
   }

   public static net.minecraft.stats.Statistic getEntityStatistic(Statistic stat, EntityType entity) {
      if (entity.getName() != null) {
         EntityTypes<?> nmsEntity = BuiltInRegistries.h.a(new MinecraftKey(entity.getName()));
         if (stat == Statistic.KILL_ENTITY) {
            return StatisticList.g.b(nmsEntity);
         }

         if (stat == Statistic.ENTITY_KILLED_BY) {
            return StatisticList.h.b(nmsEntity);
         }
      }

      return null;
   }

   public static EntityType getEntityTypeFromStatistic(net.minecraft.stats.Statistic<EntityTypes<?>> statistic) {
      MinecraftKey name = EntityTypes.a(statistic.b());
      return EntityType.fromName(name.a());
   }

   public static Material getMaterialFromStatistic(net.minecraft.stats.Statistic<?> statistic) {
      if (statistic.b() instanceof Item) {
         return CraftMagicNumbers.getMaterial((Item)statistic.b());
      } else {
         return statistic.b() instanceof Block ? CraftMagicNumbers.getMaterial((Block)statistic.b()) : null;
      }
   }

   public static void incrementStatistic(ServerStatisticManager manager, Statistic statistic) {
      incrementStatistic(manager, statistic, 1);
   }

   public static void decrementStatistic(ServerStatisticManager manager, Statistic statistic) {
      decrementStatistic(manager, statistic, 1);
   }

   public static int getStatistic(ServerStatisticManager manager, Statistic statistic) {
      Validate.notNull(statistic, "Statistic cannot be null");
      Validate.isTrue(statistic.getType() == Type.UNTYPED, "Must supply additional paramater for this statistic");
      return manager.a(getNMSStatistic(statistic));
   }

   public static void incrementStatistic(ServerStatisticManager manager, Statistic statistic, int amount) {
      Validate.isTrue(amount > 0, "Amount must be greater than 0");
      setStatistic(manager, statistic, getStatistic(manager, statistic) + amount);
   }

   public static void decrementStatistic(ServerStatisticManager manager, Statistic statistic, int amount) {
      Validate.isTrue(amount > 0, "Amount must be greater than 0");
      setStatistic(manager, statistic, getStatistic(manager, statistic) - amount);
   }

   public static void setStatistic(ServerStatisticManager manager, Statistic statistic, int newValue) {
      Validate.notNull(statistic, "Statistic cannot be null");
      Validate.isTrue(statistic.getType() == Type.UNTYPED, "Must supply additional paramater for this statistic");
      Validate.isTrue(newValue >= 0, "Value must be greater than or equal to 0");
      net.minecraft.stats.Statistic nmsStatistic = getNMSStatistic(statistic);
      manager.a(null, nmsStatistic, newValue);
   }

   public static void incrementStatistic(ServerStatisticManager manager, Statistic statistic, Material material) {
      incrementStatistic(manager, statistic, material, 1);
   }

   public static void decrementStatistic(ServerStatisticManager manager, Statistic statistic, Material material) {
      decrementStatistic(manager, statistic, material, 1);
   }

   public static int getStatistic(ServerStatisticManager manager, Statistic statistic, Material material) {
      Validate.notNull(statistic, "Statistic cannot be null");
      Validate.notNull(material, "Material cannot be null");
      Validate.isTrue(statistic.getType() == Type.BLOCK || statistic.getType() == Type.ITEM, "This statistic does not take a Material parameter");
      net.minecraft.stats.Statistic nmsStatistic = getMaterialStatistic(statistic, material);
      Validate.notNull(nmsStatistic, "The supplied Material does not have a corresponding statistic");
      return manager.a(nmsStatistic);
   }

   public static void incrementStatistic(ServerStatisticManager manager, Statistic statistic, Material material, int amount) {
      Validate.isTrue(amount > 0, "Amount must be greater than 0");
      setStatistic(manager, statistic, material, getStatistic(manager, statistic, material) + amount);
   }

   public static void decrementStatistic(ServerStatisticManager manager, Statistic statistic, Material material, int amount) {
      Validate.isTrue(amount > 0, "Amount must be greater than 0");
      setStatistic(manager, statistic, material, getStatistic(manager, statistic, material) - amount);
   }

   public static void setStatistic(ServerStatisticManager manager, Statistic statistic, Material material, int newValue) {
      Validate.notNull(statistic, "Statistic cannot be null");
      Validate.notNull(material, "Material cannot be null");
      Validate.isTrue(newValue >= 0, "Value must be greater than or equal to 0");
      Validate.isTrue(statistic.getType() == Type.BLOCK || statistic.getType() == Type.ITEM, "This statistic does not take a Material parameter");
      net.minecraft.stats.Statistic nmsStatistic = getMaterialStatistic(statistic, material);
      Validate.notNull(nmsStatistic, "The supplied Material does not have a corresponding statistic");
      manager.a(null, nmsStatistic, newValue);
   }

   public static void incrementStatistic(ServerStatisticManager manager, Statistic statistic, EntityType entityType) {
      incrementStatistic(manager, statistic, entityType, 1);
   }

   public static void decrementStatistic(ServerStatisticManager manager, Statistic statistic, EntityType entityType) {
      decrementStatistic(manager, statistic, entityType, 1);
   }

   public static int getStatistic(ServerStatisticManager manager, Statistic statistic, EntityType entityType) {
      Validate.notNull(statistic, "Statistic cannot be null");
      Validate.notNull(entityType, "EntityType cannot be null");
      Validate.isTrue(statistic.getType() == Type.ENTITY, "This statistic does not take an EntityType parameter");
      net.minecraft.stats.Statistic nmsStatistic = getEntityStatistic(statistic, entityType);
      Validate.notNull(nmsStatistic, "The supplied EntityType does not have a corresponding statistic");
      return manager.a(nmsStatistic);
   }

   public static void incrementStatistic(ServerStatisticManager manager, Statistic statistic, EntityType entityType, int amount) {
      Validate.isTrue(amount > 0, "Amount must be greater than 0");
      setStatistic(manager, statistic, entityType, getStatistic(manager, statistic, entityType) + amount);
   }

   public static void decrementStatistic(ServerStatisticManager manager, Statistic statistic, EntityType entityType, int amount) {
      Validate.isTrue(amount > 0, "Amount must be greater than 0");
      setStatistic(manager, statistic, entityType, getStatistic(manager, statistic, entityType) - amount);
   }

   public static void setStatistic(ServerStatisticManager manager, Statistic statistic, EntityType entityType, int newValue) {
      Validate.notNull(statistic, "Statistic cannot be null");
      Validate.notNull(entityType, "EntityType cannot be null");
      Validate.isTrue(newValue >= 0, "Value must be greater than or equal to 0");
      Validate.isTrue(statistic.getType() == Type.ENTITY, "This statistic does not take an EntityType parameter");
      net.minecraft.stats.Statistic nmsStatistic = getEntityStatistic(statistic, entityType);
      Validate.notNull(nmsStatistic, "The supplied EntityType does not have a corresponding statistic");
      manager.a(null, nmsStatistic, newValue);
   }
}
