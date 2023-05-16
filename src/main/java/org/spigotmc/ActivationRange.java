package org.spigotmc;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ambient.EntityAmbient;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.animal.EntitySheep;
import net.minecraft.world.entity.boss.EntityComplexPart;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderCrystal;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.boss.wither.EntityWither;
import net.minecraft.world.entity.item.EntityTNTPrimed;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.EntitySlime;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.EntityFireball;
import net.minecraft.world.entity.projectile.EntityFireworks;
import net.minecraft.world.entity.projectile.EntityProjectile;
import net.minecraft.world.entity.projectile.EntityThrownTrident;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.craftbukkit.v1_19_R3.SpigotTimings;

public class ActivationRange {
   static AxisAlignedBB maxBB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

   public static ActivationRange.ActivationType initializeEntityActivationType(Entity entity) {
      if (entity instanceof EntityRaider) {
         return ActivationRange.ActivationType.RAIDER;
      } else if (entity instanceof EntityMonster || entity instanceof EntitySlime) {
         return ActivationRange.ActivationType.MONSTER;
      } else {
         return !(entity instanceof EntityCreature) && !(entity instanceof EntityAmbient)
            ? ActivationRange.ActivationType.MISC
            : ActivationRange.ActivationType.ANIMAL;
      }
   }

   public static boolean initializeEntityActivationState(Entity entity, SpigotWorldConfig config) {
      return entity.activationType == ActivationRange.ActivationType.MISC && config.miscActivationRange == 0
         || entity.activationType == ActivationRange.ActivationType.RAIDER && config.raiderActivationRange == 0
         || entity.activationType == ActivationRange.ActivationType.ANIMAL && config.animalActivationRange == 0
         || entity.activationType == ActivationRange.ActivationType.MONSTER && config.monsterActivationRange == 0
         || entity instanceof EntityHuman
         || entity instanceof EntityProjectile
         || entity instanceof EntityEnderDragon
         || entity instanceof EntityComplexPart
         || entity instanceof EntityWither
         || entity instanceof EntityFireball
         || entity instanceof EntityLightning
         || entity instanceof EntityTNTPrimed
         || entity instanceof EntityEnderCrystal
         || entity instanceof EntityFireworks
         || entity instanceof EntityThrownTrident;
   }

   public static void activateEntities(World world) {
      SpigotTimings.entityActivationCheckTimer.startTiming();
      int miscActivationRange = world.spigotConfig.miscActivationRange;
      int raiderActivationRange = world.spigotConfig.raiderActivationRange;
      int animalActivationRange = world.spigotConfig.animalActivationRange;
      int monsterActivationRange = world.spigotConfig.monsterActivationRange;
      int maxRange = Math.max(monsterActivationRange, animalActivationRange);
      maxRange = Math.max(maxRange, raiderActivationRange);
      maxRange = Math.max(maxRange, miscActivationRange);
      maxRange = Math.min((world.spigotConfig.simulationDistance << 4) - 8, maxRange);

      for(EntityHuman player : world.v()) {
         player.activatedTick = (long)MinecraftServer.currentTick;
         if (!world.spigotConfig.ignoreSpectatorActivation || !player.F_()) {
            maxBB = player.cD().c((double)maxRange, 256.0, (double)maxRange);
            ActivationRange.ActivationType.MISC.boundingBox = player.cD().c((double)miscActivationRange, 256.0, (double)miscActivationRange);
            ActivationRange.ActivationType.RAIDER.boundingBox = player.cD().c((double)raiderActivationRange, 256.0, (double)raiderActivationRange);
            ActivationRange.ActivationType.ANIMAL.boundingBox = player.cD().c((double)animalActivationRange, 256.0, (double)animalActivationRange);
            ActivationRange.ActivationType.MONSTER.boundingBox = player.cD().c((double)monsterActivationRange, 256.0, (double)monsterActivationRange);
            world.E().a(maxBB, ActivationRange::activateEntity);
         }
      }

      SpigotTimings.entityActivationCheckTimer.stopTiming();
   }

   private static void activateEntity(Entity entity) {
      if ((long)MinecraftServer.currentTick > entity.activatedTick) {
         if (entity.defaultActivationState) {
            entity.activatedTick = (long)MinecraftServer.currentTick;
            return;
         }

         if (entity.activationType.boundingBox.c(entity.cD())) {
            entity.activatedTick = (long)MinecraftServer.currentTick;
         }
      }
   }

   public static boolean checkEntityImmunities(Entity entity) {
      if (!entity.ah && entity.aK <= 0) {
         if (!(entity instanceof EntityArrow)) {
            if (!entity.ax() || !entity.r.isEmpty() || entity.bL()) {
               return true;
            }
         } else if (!((EntityArrow)entity).b) {
            return true;
         }

         if (entity instanceof EntityLiving living) {
            if (living.aJ > 0 || living.bO.size() > 0) {
               return true;
            }

            if (entity instanceof EntityCreature && ((EntityCreature)entity).P_() != null) {
               return true;
            }

            if (entity instanceof EntityVillager && ((EntityVillager)entity).O_()) {
               return true;
            }

            if (entity instanceof EntityAnimal animal) {
               if (animal.y_() || animal.fW()) {
                  return true;
               }

               if (entity instanceof EntitySheep && ((EntitySheep)entity).w()) {
                  return true;
               }
            }

            if (entity instanceof EntityCreeper && ((EntityCreeper)entity).w()) {
               return true;
            }
         }

         return entity instanceof EntityExperienceOrb;
      } else {
         return true;
      }
   }

   public static boolean checkIfActive(Entity entity) {
      SpigotTimings.checkIfActiveTimer.startTiming();
      if (entity instanceof EntityFireworks) {
         SpigotTimings.checkIfActiveTimer.stopTiming();
         return true;
      } else {
         boolean isActive = entity.activatedTick >= (long)MinecraftServer.currentTick || entity.defaultActivationState;
         if (!isActive) {
            if (((long)MinecraftServer.currentTick - entity.activatedTick - 1L) % 20L == 0L) {
               if (checkEntityImmunities(entity)) {
                  entity.activatedTick = (long)(MinecraftServer.currentTick + 20);
               }

               isActive = true;
            }
         } else if (!entity.defaultActivationState && entity.ag % 4 == 0 && !checkEntityImmunities(entity)) {
            isActive = false;
         }

         SpigotTimings.checkIfActiveTimer.stopTiming();
         return isActive;
      }
   }

   public static enum ActivationType {
      MONSTER,
      ANIMAL,
      RAIDER,
      MISC;

      AxisAlignedBB boundingBox = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   }
}
