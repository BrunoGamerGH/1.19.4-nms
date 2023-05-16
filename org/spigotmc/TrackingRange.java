package org.spigotmc;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.decoration.EntityItemFrame;
import net.minecraft.world.entity.decoration.EntityPainting;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.EntityGhast;

public class TrackingRange {
   public static int getEntityTrackingRange(Entity entity, int defaultRange) {
      if (defaultRange == 0) {
         return defaultRange;
      } else {
         SpigotWorldConfig config = entity.H.spigotConfig;
         if (entity instanceof EntityPlayer) {
            return config.playerTrackingRange;
         } else if (entity.activationType == ActivationRange.ActivationType.MONSTER || entity.activationType == ActivationRange.ActivationType.RAIDER) {
            return config.monsterTrackingRange;
         } else if (entity instanceof EntityGhast) {
            return config.monsterTrackingRange > config.monsterActivationRange ? config.monsterTrackingRange : config.monsterActivationRange;
         } else if (entity.activationType == ActivationRange.ActivationType.ANIMAL) {
            return config.animalTrackingRange;
         } else {
            return !(entity instanceof EntityItemFrame)
                  && !(entity instanceof EntityPainting)
                  && !(entity instanceof EntityItem)
                  && !(entity instanceof EntityExperienceOrb)
               ? config.otherTrackingRange
               : config.miscTrackingRange;
         }
      }
   }
}
