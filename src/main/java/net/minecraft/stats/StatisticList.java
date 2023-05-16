package net.minecraft.stats;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class StatisticList {
   public static final StatisticWrapper<Block> a = a("mined", BuiltInRegistries.f);
   public static final StatisticWrapper<Item> b = a("crafted", BuiltInRegistries.i);
   public static final StatisticWrapper<Item> c = a("used", BuiltInRegistries.i);
   public static final StatisticWrapper<Item> d = a("broken", BuiltInRegistries.i);
   public static final StatisticWrapper<Item> e = a("picked_up", BuiltInRegistries.i);
   public static final StatisticWrapper<Item> f = a("dropped", BuiltInRegistries.i);
   public static final StatisticWrapper<EntityTypes<?>> g = a("killed", BuiltInRegistries.h);
   public static final StatisticWrapper<EntityTypes<?>> h = a("killed_by", BuiltInRegistries.h);
   public static final StatisticWrapper<MinecraftKey> i = a("custom", BuiltInRegistries.n);
   public static final MinecraftKey j = a("leave_game", Counter.b);
   public static final MinecraftKey k = a("play_time", Counter.e);
   public static final MinecraftKey l = a("total_world_time", Counter.e);
   public static final MinecraftKey m = a("time_since_death", Counter.e);
   public static final MinecraftKey n = a("time_since_rest", Counter.e);
   public static final MinecraftKey o = a("sneak_time", Counter.e);
   public static final MinecraftKey p = a("walk_one_cm", Counter.d);
   public static final MinecraftKey q = a("crouch_one_cm", Counter.d);
   public static final MinecraftKey r = a("sprint_one_cm", Counter.d);
   public static final MinecraftKey s = a("walk_on_water_one_cm", Counter.d);
   public static final MinecraftKey t = a("fall_one_cm", Counter.d);
   public static final MinecraftKey u = a("climb_one_cm", Counter.d);
   public static final MinecraftKey v = a("fly_one_cm", Counter.d);
   public static final MinecraftKey w = a("walk_under_water_one_cm", Counter.d);
   public static final MinecraftKey x = a("minecart_one_cm", Counter.d);
   public static final MinecraftKey y = a("boat_one_cm", Counter.d);
   public static final MinecraftKey z = a("pig_one_cm", Counter.d);
   public static final MinecraftKey A = a("horse_one_cm", Counter.d);
   public static final MinecraftKey B = a("aviate_one_cm", Counter.d);
   public static final MinecraftKey C = a("swim_one_cm", Counter.d);
   public static final MinecraftKey D = a("strider_one_cm", Counter.d);
   public static final MinecraftKey E = a("jump", Counter.b);
   public static final MinecraftKey F = a("drop", Counter.b);
   public static final MinecraftKey G = a("damage_dealt", Counter.c);
   public static final MinecraftKey H = a("damage_dealt_absorbed", Counter.c);
   public static final MinecraftKey I = a("damage_dealt_resisted", Counter.c);
   public static final MinecraftKey J = a("damage_taken", Counter.c);
   public static final MinecraftKey K = a("damage_blocked_by_shield", Counter.c);
   public static final MinecraftKey L = a("damage_absorbed", Counter.c);
   public static final MinecraftKey M = a("damage_resisted", Counter.c);
   public static final MinecraftKey N = a("deaths", Counter.b);
   public static final MinecraftKey O = a("mob_kills", Counter.b);
   public static final MinecraftKey P = a("animals_bred", Counter.b);
   public static final MinecraftKey Q = a("player_kills", Counter.b);
   public static final MinecraftKey R = a("fish_caught", Counter.b);
   public static final MinecraftKey S = a("talked_to_villager", Counter.b);
   public static final MinecraftKey T = a("traded_with_villager", Counter.b);
   public static final MinecraftKey U = a("eat_cake_slice", Counter.b);
   public static final MinecraftKey V = a("fill_cauldron", Counter.b);
   public static final MinecraftKey W = a("use_cauldron", Counter.b);
   public static final MinecraftKey X = a("clean_armor", Counter.b);
   public static final MinecraftKey Y = a("clean_banner", Counter.b);
   public static final MinecraftKey Z = a("clean_shulker_box", Counter.b);
   public static final MinecraftKey aa = a("interact_with_brewingstand", Counter.b);
   public static final MinecraftKey ab = a("interact_with_beacon", Counter.b);
   public static final MinecraftKey ac = a("inspect_dropper", Counter.b);
   public static final MinecraftKey ad = a("inspect_hopper", Counter.b);
   public static final MinecraftKey ae = a("inspect_dispenser", Counter.b);
   public static final MinecraftKey af = a("play_noteblock", Counter.b);
   public static final MinecraftKey ag = a("tune_noteblock", Counter.b);
   public static final MinecraftKey ah = a("pot_flower", Counter.b);
   public static final MinecraftKey ai = a("trigger_trapped_chest", Counter.b);
   public static final MinecraftKey aj = a("open_enderchest", Counter.b);
   public static final MinecraftKey ak = a("enchant_item", Counter.b);
   public static final MinecraftKey al = a("play_record", Counter.b);
   public static final MinecraftKey am = a("interact_with_furnace", Counter.b);
   public static final MinecraftKey an = a("interact_with_crafting_table", Counter.b);
   public static final MinecraftKey ao = a("open_chest", Counter.b);
   public static final MinecraftKey ap = a("sleep_in_bed", Counter.b);
   public static final MinecraftKey aq = a("open_shulker_box", Counter.b);
   public static final MinecraftKey ar = a("open_barrel", Counter.b);
   public static final MinecraftKey as = a("interact_with_blast_furnace", Counter.b);
   public static final MinecraftKey at = a("interact_with_smoker", Counter.b);
   public static final MinecraftKey au = a("interact_with_lectern", Counter.b);
   public static final MinecraftKey av = a("interact_with_campfire", Counter.b);
   public static final MinecraftKey aw = a("interact_with_cartography_table", Counter.b);
   public static final MinecraftKey ax = a("interact_with_loom", Counter.b);
   public static final MinecraftKey ay = a("interact_with_stonecutter", Counter.b);
   public static final MinecraftKey az = a("bell_ring", Counter.b);
   public static final MinecraftKey aA = a("raid_trigger", Counter.b);
   public static final MinecraftKey aB = a("raid_win", Counter.b);
   public static final MinecraftKey aC = a("interact_with_anvil", Counter.b);
   public static final MinecraftKey aD = a("interact_with_grindstone", Counter.b);
   public static final MinecraftKey aE = a("target_hit", Counter.b);
   public static final MinecraftKey aF = a("interact_with_smithing_table", Counter.b);

   private static MinecraftKey a(String var0, Counter var1) {
      MinecraftKey var2 = new MinecraftKey(var0);
      IRegistry.a(BuiltInRegistries.n, var0, var2);
      i.a(var2, var1);
      return var2;
   }

   private static <T> StatisticWrapper<T> a(String var0, IRegistry<T> var1) {
      return IRegistry.a(BuiltInRegistries.x, var0, new StatisticWrapper<>(var1));
   }
}
