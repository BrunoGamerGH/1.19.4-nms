package net.minecraft.world.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.monster.piglin.EntityPiglinAbstract;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.pathfinder.PathType;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntitySkeletonWither extends EntitySkeletonAbstract {
   public EntitySkeletonWither(EntityTypes<? extends EntitySkeletonWither> entitytypes, World world) {
      super(entitytypes, world);
      this.a(PathType.i, 8.0F);
   }

   @Override
   protected void x() {
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityPiglinAbstract.class, true));
      super.x();
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.zJ;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.zL;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.zK;
   }

   @Override
   SoundEffect r() {
      return SoundEffects.zM;
   }

   @Override
   protected void a(DamageSource damagesource, int i, boolean flag) {
      super.a(damagesource, i, flag);
      Entity entity = damagesource.d();
      if (entity instanceof EntityCreeper entitycreeper && entitycreeper.fT()) {
         entitycreeper.fU();
         this.a((IMaterial)Items.to);
      }
   }

   @Override
   protected void a(RandomSource randomsource, DifficultyDamageScaler difficultydamagescaler) {
      this.a(EnumItemSlot.a, new ItemStack(Items.nY));
   }

   @Override
   protected void b(RandomSource randomsource, DifficultyDamageScaler difficultydamagescaler) {
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
      GroupDataEntity groupdataentity1 = super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
      this.a(GenericAttributes.f).a(4.0);
      this.w();
      return groupdataentity1;
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return 2.1F;
   }

   @Override
   public boolean z(Entity entity) {
      if (!super.z(entity)) {
         return false;
      } else {
         if (entity instanceof EntityLiving) {
            ((EntityLiving)entity).addEffect(new MobEffect(MobEffects.t, 200), this, Cause.ATTACK);
         }

         return true;
      }
   }

   @Override
   protected EntityArrow b(ItemStack itemstack, float f) {
      EntityArrow entityarrow = super.b(itemstack, f);
      entityarrow.f(100);
      return entityarrow;
   }

   @Override
   public boolean c(MobEffect mobeffect) {
      return mobeffect.c() == MobEffects.t ? false : super.c(mobeffect);
   }
}
