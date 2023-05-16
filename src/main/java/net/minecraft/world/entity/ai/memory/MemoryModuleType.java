package net.minecraft.world.entity.ai.memory;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.IRegistry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.BehaviorPosition;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.hoglin.EntityHoglin;
import net.minecraft.world.entity.monster.piglin.EntityPiglinAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.phys.Vec3D;

public class MemoryModuleType<U> {
   public static final MemoryModuleType<Void> a = a("dummy");
   public static final MemoryModuleType<GlobalPos> b = a("home", GlobalPos.a);
   public static final MemoryModuleType<GlobalPos> c = a("job_site", GlobalPos.a);
   public static final MemoryModuleType<GlobalPos> d = a("potential_job_site", GlobalPos.a);
   public static final MemoryModuleType<GlobalPos> e = a("meeting_point", GlobalPos.a);
   public static final MemoryModuleType<List<GlobalPos>> f = a("secondary_job_site");
   public static final MemoryModuleType<List<EntityLiving>> g = a("mobs");
   public static final MemoryModuleType<NearestVisibleLivingEntities> h = a("visible_mobs");
   public static final MemoryModuleType<List<EntityLiving>> i = a("visible_villager_babies");
   public static final MemoryModuleType<List<EntityHuman>> j = a("nearest_players");
   public static final MemoryModuleType<EntityHuman> k = a("nearest_visible_player");
   public static final MemoryModuleType<EntityHuman> l = a("nearest_visible_targetable_player");
   public static final MemoryModuleType<MemoryTarget> m = a("walk_target");
   public static final MemoryModuleType<BehaviorPosition> n = a("look_target");
   public static final MemoryModuleType<EntityLiving> o = a("attack_target");
   public static final MemoryModuleType<Boolean> p = a("attack_cooling_down");
   public static final MemoryModuleType<EntityLiving> q = a("interaction_target");
   public static final MemoryModuleType<EntityAgeable> r = a("breed_target");
   public static final MemoryModuleType<Entity> s = a("ride_target");
   public static final MemoryModuleType<PathEntity> t = a("path");
   public static final MemoryModuleType<List<GlobalPos>> u = a("interactable_doors");
   public static final MemoryModuleType<Set<GlobalPos>> v = a("doors_to_close");
   public static final MemoryModuleType<BlockPosition> w = a("nearest_bed");
   public static final MemoryModuleType<DamageSource> x = a("hurt_by");
   public static final MemoryModuleType<EntityLiving> y = a("hurt_by_entity");
   public static final MemoryModuleType<EntityLiving> z = a("avoid_target");
   public static final MemoryModuleType<EntityLiving> A = a("nearest_hostile");
   public static final MemoryModuleType<EntityLiving> B = a("nearest_attackable");
   public static final MemoryModuleType<GlobalPos> C = a("hiding_place");
   public static final MemoryModuleType<Long> D = a("heard_bell_time");
   public static final MemoryModuleType<Long> E = a("cant_reach_walk_target_since");
   public static final MemoryModuleType<Boolean> F = a("golem_detected_recently", Codec.BOOL);
   public static final MemoryModuleType<Long> G = a("last_slept", Codec.LONG);
   public static final MemoryModuleType<Long> H = a("last_woken", Codec.LONG);
   public static final MemoryModuleType<Long> I = a("last_worked_at_poi", Codec.LONG);
   public static final MemoryModuleType<EntityAgeable> J = a("nearest_visible_adult");
   public static final MemoryModuleType<EntityItem> K = a("nearest_visible_wanted_item");
   public static final MemoryModuleType<EntityInsentient> L = a("nearest_visible_nemesis");
   public static final MemoryModuleType<Integer> M = a("play_dead_ticks", Codec.INT);
   public static final MemoryModuleType<EntityHuman> N = a("tempting_player");
   public static final MemoryModuleType<Integer> O = a("temptation_cooldown_ticks", Codec.INT);
   public static final MemoryModuleType<Integer> P = a("gaze_cooldown_ticks", Codec.INT);
   public static final MemoryModuleType<Boolean> Q = a("is_tempted", Codec.BOOL);
   public static final MemoryModuleType<Integer> R = a("long_jump_cooling_down", Codec.INT);
   public static final MemoryModuleType<Boolean> S = a("long_jump_mid_jump");
   public static final MemoryModuleType<Boolean> T = a("has_hunting_cooldown", Codec.BOOL);
   public static final MemoryModuleType<Integer> U = a("ram_cooldown_ticks", Codec.INT);
   public static final MemoryModuleType<Vec3D> V = a("ram_target");
   public static final MemoryModuleType<Unit> W = a("is_in_water", Codec.unit(Unit.a));
   public static final MemoryModuleType<Unit> X = a("is_pregnant", Codec.unit(Unit.a));
   public static final MemoryModuleType<Boolean> Y = a("is_panicking", Codec.BOOL);
   public static final MemoryModuleType<List<UUID>> Z = a("unreachable_tongue_targets");
   public static final MemoryModuleType<UUID> aa = a("angry_at", UUIDUtil.a);
   public static final MemoryModuleType<Boolean> ab = a("universal_anger", Codec.BOOL);
   public static final MemoryModuleType<Boolean> ac = a("admiring_item", Codec.BOOL);
   public static final MemoryModuleType<Integer> ad = a("time_trying_to_reach_admire_item");
   public static final MemoryModuleType<Boolean> ae = a("disable_walk_to_admire_item");
   public static final MemoryModuleType<Boolean> af = a("admiring_disabled", Codec.BOOL);
   public static final MemoryModuleType<Boolean> ag = a("hunted_recently", Codec.BOOL);
   public static final MemoryModuleType<BlockPosition> ah = a("celebrate_location");
   public static final MemoryModuleType<Boolean> ai = a("dancing");
   public static final MemoryModuleType<EntityHoglin> aj = a("nearest_visible_huntable_hoglin");
   public static final MemoryModuleType<EntityHoglin> ak = a("nearest_visible_baby_hoglin");
   public static final MemoryModuleType<EntityHuman> al = a("nearest_targetable_player_not_wearing_gold");
   public static final MemoryModuleType<List<EntityPiglinAbstract>> am = a("nearby_adult_piglins");
   public static final MemoryModuleType<List<EntityPiglinAbstract>> an = a("nearest_visible_adult_piglins");
   public static final MemoryModuleType<List<EntityHoglin>> ao = a("nearest_visible_adult_hoglins");
   public static final MemoryModuleType<EntityPiglinAbstract> ap = a("nearest_visible_adult_piglin");
   public static final MemoryModuleType<EntityLiving> aq = a("nearest_visible_zombified");
   public static final MemoryModuleType<Integer> ar = a("visible_adult_piglin_count");
   public static final MemoryModuleType<Integer> as = a("visible_adult_hoglin_count");
   public static final MemoryModuleType<EntityHuman> at = a("nearest_player_holding_wanted_item");
   public static final MemoryModuleType<Boolean> au = a("ate_recently");
   public static final MemoryModuleType<BlockPosition> av = a("nearest_repellent");
   public static final MemoryModuleType<Boolean> aw = a("pacified");
   public static final MemoryModuleType<EntityLiving> ax = a("roar_target");
   public static final MemoryModuleType<BlockPosition> ay = a("disturbance_location");
   public static final MemoryModuleType<Unit> az = a("recent_projectile", Codec.unit(Unit.a));
   public static final MemoryModuleType<Unit> aA = a("is_sniffing", Codec.unit(Unit.a));
   public static final MemoryModuleType<Unit> aB = a("is_emerging", Codec.unit(Unit.a));
   public static final MemoryModuleType<Unit> aC = a("roar_sound_delay", Codec.unit(Unit.a));
   public static final MemoryModuleType<Unit> aD = a("dig_cooldown", Codec.unit(Unit.a));
   public static final MemoryModuleType<Unit> aE = a("roar_sound_cooldown", Codec.unit(Unit.a));
   public static final MemoryModuleType<Unit> aF = a("sniff_cooldown", Codec.unit(Unit.a));
   public static final MemoryModuleType<Unit> aG = a("touch_cooldown", Codec.unit(Unit.a));
   public static final MemoryModuleType<Unit> aH = a("vibration_cooldown", Codec.unit(Unit.a));
   public static final MemoryModuleType<Unit> aI = a("sonic_boom_cooldown", Codec.unit(Unit.a));
   public static final MemoryModuleType<Unit> aJ = a("sonic_boom_sound_cooldown", Codec.unit(Unit.a));
   public static final MemoryModuleType<Unit> aK = a("sonic_boom_sound_delay", Codec.unit(Unit.a));
   public static final MemoryModuleType<UUID> aL = a("liked_player", UUIDUtil.a);
   public static final MemoryModuleType<GlobalPos> aM = a("liked_noteblock", GlobalPos.a);
   public static final MemoryModuleType<Integer> aN = a("liked_noteblock_cooldown_ticks", Codec.INT);
   public static final MemoryModuleType<Integer> aO = a("item_pickup_cooldown_ticks", Codec.INT);
   public static final MemoryModuleType<List<BlockPosition>> aP = a("sniffer_explored_positions", Codec.list(BlockPosition.a));
   public static final MemoryModuleType<BlockPosition> aQ = a("sniffer_sniffing_target");
   public static final MemoryModuleType<Boolean> aR = a("sniffer_digging");
   public static final MemoryModuleType<Boolean> aS = a("sniffer_happy");
   private final Optional<Codec<ExpirableMemory<U>>> aT;

   @VisibleForTesting
   public MemoryModuleType(Optional<Codec<U>> var0) {
      this.aT = var0.map(ExpirableMemory::a);
   }

   @Override
   public String toString() {
      return BuiltInRegistries.B.b(this).toString();
   }

   public Optional<Codec<ExpirableMemory<U>>> a() {
      return this.aT;
   }

   private static <U> MemoryModuleType<U> a(String var0, Codec<U> var1) {
      return IRegistry.a(BuiltInRegistries.B, new MinecraftKey(var0), new MemoryModuleType<>(Optional.of(var1)));
   }

   private static <U> MemoryModuleType<U> a(String var0) {
      return IRegistry.a(BuiltInRegistries.B, new MinecraftKey(var0), new MemoryModuleType<>(Optional.empty()));
   }
}
