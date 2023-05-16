package org.bukkit.craftbukkit.v1_19_R3.potion;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class CraftPotionUtil {
   private static final BiMap<PotionType, String> regular = ImmutableBiMap.builder()
      .put(PotionType.UNCRAFTABLE, "empty")
      .put(PotionType.WATER, "water")
      .put(PotionType.MUNDANE, "mundane")
      .put(PotionType.THICK, "thick")
      .put(PotionType.AWKWARD, "awkward")
      .put(PotionType.NIGHT_VISION, "night_vision")
      .put(PotionType.INVISIBILITY, "invisibility")
      .put(PotionType.JUMP, "leaping")
      .put(PotionType.FIRE_RESISTANCE, "fire_resistance")
      .put(PotionType.SPEED, "swiftness")
      .put(PotionType.SLOWNESS, "slowness")
      .put(PotionType.WATER_BREATHING, "water_breathing")
      .put(PotionType.INSTANT_HEAL, "healing")
      .put(PotionType.INSTANT_DAMAGE, "harming")
      .put(PotionType.POISON, "poison")
      .put(PotionType.REGEN, "regeneration")
      .put(PotionType.STRENGTH, "strength")
      .put(PotionType.WEAKNESS, "weakness")
      .put(PotionType.LUCK, "luck")
      .put(PotionType.TURTLE_MASTER, "turtle_master")
      .put(PotionType.SLOW_FALLING, "slow_falling")
      .build();
   private static final BiMap<PotionType, String> upgradeable = ImmutableBiMap.builder()
      .put(PotionType.JUMP, "strong_leaping")
      .put(PotionType.SPEED, "strong_swiftness")
      .put(PotionType.INSTANT_HEAL, "strong_healing")
      .put(PotionType.INSTANT_DAMAGE, "strong_harming")
      .put(PotionType.POISON, "strong_poison")
      .put(PotionType.REGEN, "strong_regeneration")
      .put(PotionType.STRENGTH, "strong_strength")
      .put(PotionType.SLOWNESS, "strong_slowness")
      .put(PotionType.TURTLE_MASTER, "strong_turtle_master")
      .build();
   private static final BiMap<PotionType, String> extendable = ImmutableBiMap.builder()
      .put(PotionType.NIGHT_VISION, "long_night_vision")
      .put(PotionType.INVISIBILITY, "long_invisibility")
      .put(PotionType.JUMP, "long_leaping")
      .put(PotionType.FIRE_RESISTANCE, "long_fire_resistance")
      .put(PotionType.SPEED, "long_swiftness")
      .put(PotionType.SLOWNESS, "long_slowness")
      .put(PotionType.WATER_BREATHING, "long_water_breathing")
      .put(PotionType.POISON, "long_poison")
      .put(PotionType.REGEN, "long_regeneration")
      .put(PotionType.STRENGTH, "long_strength")
      .put(PotionType.WEAKNESS, "long_weakness")
      .put(PotionType.TURTLE_MASTER, "long_turtle_master")
      .put(PotionType.SLOW_FALLING, "long_slow_falling")
      .build();

   public static String fromBukkit(PotionData data) {
      String type;
      if (data.isUpgraded()) {
         type = (String)upgradeable.get(data.getType());
      } else if (data.isExtended()) {
         type = (String)extendable.get(data.getType());
      } else {
         type = (String)regular.get(data.getType());
      }

      Preconditions.checkNotNull(type, "Unknown potion type from data " + data);
      return "minecraft:" + type;
   }

   public static PotionData toBukkit(String type) {
      if (type == null) {
         return new PotionData(PotionType.UNCRAFTABLE, false, false);
      } else {
         if (type.startsWith("minecraft:")) {
            type = type.substring(10);
         }

         PotionType potionType = null;
         potionType = (PotionType)extendable.inverse().get(type);
         if (potionType != null) {
            return new PotionData(potionType, true, false);
         } else {
            potionType = (PotionType)upgradeable.inverse().get(type);
            if (potionType != null) {
               return new PotionData(potionType, false, true);
            } else {
               potionType = (PotionType)regular.inverse().get(type);
               return potionType != null ? new PotionData(potionType, false, false) : new PotionData(PotionType.UNCRAFTABLE, false, false);
            }
         }
      }
   }

   public static MobEffect fromBukkit(PotionEffect effect) {
      MobEffectList type = MobEffectList.a(effect.getType().getId());
      return new MobEffect(type, effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), effect.hasParticles());
   }

   public static PotionEffect toBukkit(MobEffect effect) {
      PotionEffectType type = PotionEffectType.getById(MobEffectList.a(effect.c()));
      int amp = effect.e();
      int duration = effect.d();
      boolean ambient = effect.f();
      boolean particles = effect.g();
      return new PotionEffect(type, duration, amp, ambient, particles);
   }

   public static boolean equals(MobEffectList mobEffect, PotionEffectType type) {
      PotionEffectType typeV = PotionEffectType.getById(MobEffectList.a(mobEffect));
      return typeV.equals(type);
   }
}
