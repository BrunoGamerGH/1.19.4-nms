package net.minecraft.world.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntityCaveSpider extends EntitySpider {
   public EntityCaveSpider(EntityTypes<? extends EntityCaveSpider> entitytypes, World world) {
      super(entitytypes, world);
   }

   public static AttributeProvider.Builder q() {
      return EntitySpider.r().a(GenericAttributes.a, 12.0);
   }

   @Override
   public boolean z(Entity entity) {
      if (super.z(entity)) {
         if (entity instanceof EntityLiving) {
            byte b0 = 0;
            if (this.H.ah() == EnumDifficulty.c) {
               b0 = 7;
            } else if (this.H.ah() == EnumDifficulty.d) {
               b0 = 15;
            }

            if (b0 > 0) {
               ((EntityLiving)entity).addEffect(new MobEffect(MobEffects.s, b0 * 20, 0), this, Cause.ATTACK);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Nullable
   @Override
   public GroupDataEntity a(
      WorldAccess worldaccess,
      DifficultyDamageScaler difficultydamagescaler,
      EnumMobSpawn enummobspawn,
      @Nullable GroupDataEntity groupdataentity,
      @Nullable NBTTagCompound nbttagcompound
   ) {
      return groupdataentity;
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return 0.45F;
   }
}
