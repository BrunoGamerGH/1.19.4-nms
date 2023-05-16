package org.bukkit.craftbukkit.v1_19_R3.util;

import net.minecraft.world.entity.EnumCreatureType;
import org.bukkit.entity.SpawnCategory;

public class CraftSpawnCategory {
   public static boolean isValidForLimits(SpawnCategory spawnCategory) {
      return spawnCategory != null && spawnCategory != SpawnCategory.MISC;
   }

   public static String getConfigNameSpawnLimit(SpawnCategory spawnCategory) {
      return switch(spawnCategory) {
         case MONSTER -> "spawn-limits.monsters";
         case ANIMAL -> "spawn-limits.animals";
         case WATER_ANIMAL -> "spawn-limits.water-animals";
         case WATER_AMBIENT -> "spawn-limits.water-ambient";
         case WATER_UNDERGROUND_CREATURE -> "spawn-limits.water-underground-creature";
         case AMBIENT -> "spawn-limits.ambient";
         case AXOLOTL -> "spawn-limits.axolotls";
         default -> throw new UnsupportedOperationException("Unknown Config value " + spawnCategory + " for spawn-limits");
      };
   }

   public static String getConfigNameTicksPerSpawn(SpawnCategory spawnCategory) {
      return switch(spawnCategory) {
         case MONSTER -> "ticks-per.monster-spawns";
         case ANIMAL -> "ticks-per.animal-spawns";
         case WATER_ANIMAL -> "ticks-per.water-spawns";
         case WATER_AMBIENT -> "ticks-per.water-ambient-spawns";
         case WATER_UNDERGROUND_CREATURE -> "ticks-per.water-underground-creature-spawns";
         case AMBIENT -> "ticks-per.ambient-spawns";
         case AXOLOTL -> "ticks-per.axolotl-spawns";
         default -> throw new UnsupportedOperationException("Unknown Config value " + spawnCategory + " for ticks-per");
      };
   }

   public static long getDefaultTicksPerSpawn(SpawnCategory spawnCategory) {
      return switch(spawnCategory) {
         case MONSTER, WATER_ANIMAL, WATER_AMBIENT, WATER_UNDERGROUND_CREATURE, AMBIENT, AXOLOTL -> 1L;
         case ANIMAL -> 400L;
         default -> throw new UnsupportedOperationException("Unknown Config value " + spawnCategory + " for ticks-per");
      };
   }

   public static SpawnCategory toBukkit(EnumCreatureType enumCreatureType) {
      return switch(enumCreatureType) {
         case a -> SpawnCategory.MONSTER;
         case b -> SpawnCategory.ANIMAL;
         case c -> SpawnCategory.AMBIENT;
         case d -> SpawnCategory.AXOLOTL;
         case e -> SpawnCategory.WATER_UNDERGROUND_CREATURE;
         case f -> SpawnCategory.WATER_ANIMAL;
         case g -> SpawnCategory.WATER_AMBIENT;
         case h -> SpawnCategory.MISC;
         default -> throw new UnsupportedOperationException("Unknown EnumCreatureType " + enumCreatureType + " for SpawnCategory");
      };
   }

   public static EnumCreatureType toNMS(SpawnCategory spawnCategory) {
      return switch(spawnCategory) {
         case MONSTER -> EnumCreatureType.a;
         case ANIMAL -> EnumCreatureType.b;
         case WATER_ANIMAL -> EnumCreatureType.f;
         case WATER_AMBIENT -> EnumCreatureType.g;
         case WATER_UNDERGROUND_CREATURE -> EnumCreatureType.e;
         case AMBIENT -> EnumCreatureType.c;
         case AXOLOTL -> EnumCreatureType.d;
         case MISC -> EnumCreatureType.h;
         default -> throw new UnsupportedOperationException("Unknown SpawnCategory " + spawnCategory + " for EnumCreatureType");
      };
   }
}
