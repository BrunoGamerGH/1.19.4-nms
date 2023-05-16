package net.minecraft.world.entity.monster;

import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntityZombieHusk extends EntityZombie {
   public EntityZombieHusk(EntityTypes<? extends EntityZombieHusk> entitytypes, World world) {
      super(entitytypes, world);
   }

   public static boolean a(
      EntityTypes<EntityZombieHusk> entitytypes, WorldAccess worldaccess, EnumMobSpawn enummobspawn, BlockPosition blockposition, RandomSource randomsource
   ) {
      return b(entitytypes, worldaccess, enummobspawn, blockposition, randomsource) && (enummobspawn == EnumMobSpawn.c || worldaccess.g(blockposition));
   }

   @Override
   protected boolean W_() {
      return false;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.lo;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.lr;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.lq;
   }

   @Override
   protected SoundEffect w() {
      return SoundEffects.ls;
   }

   @Override
   public boolean z(Entity entity) {
      boolean flag = super.z(entity);
      if (flag && this.eK().b() && entity instanceof EntityLiving) {
         float f = this.H.d_(this.dg()).b();
         ((EntityLiving)entity).addEffect(new MobEffect(MobEffects.q, 140 * (int)f), this, Cause.ATTACK);
      }

      return flag;
   }

   @Override
   protected boolean fT() {
      return true;
   }

   @Override
   protected void fV() {
      this.b(EntityTypes.bp);
      if (!this.aO()) {
         this.H.a(null, 1041, this.dg(), 0);
      }
   }

   @Override
   protected ItemStack fS() {
      return ItemStack.b;
   }
}
