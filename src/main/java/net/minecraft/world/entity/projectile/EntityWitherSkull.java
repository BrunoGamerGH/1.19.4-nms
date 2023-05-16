package net.minecraft.world.entity.projectile;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.boss.wither.EntityWither;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class EntityWitherSkull extends EntityFireball {
   private static final DataWatcherObject<Boolean> e = DataWatcher.a(EntityWitherSkull.class, DataWatcherRegistry.k);

   public EntityWitherSkull(EntityTypes<? extends EntityWitherSkull> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityWitherSkull(World world, EntityLiving entityliving, double d0, double d1, double d2) {
      super(EntityTypes.bm, entityliving, d0, d1, d2, world);
   }

   @Override
   protected float k() {
      return this.o() ? 0.73F : super.k();
   }

   @Override
   public boolean bK() {
      return false;
   }

   @Override
   public float a(Explosion explosion, IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, Fluid fluid, float f) {
      return this.o() && EntityWither.c(iblockdata) ? Math.min(0.8F, f) : f;
   }

   @Override
   protected void a(MovingObjectPositionEntity movingobjectpositionentity) {
      super.a(movingobjectpositionentity);
      if (!this.H.B) {
         Entity entity = movingobjectpositionentity.a();
         Entity entity1 = this.v();
         boolean flag;
         if (entity1 instanceof EntityLiving entityliving) {
            flag = entity.a(this.dG().a(this, entityliving), 8.0F);
            if (flag) {
               if (entity.bq()) {
                  this.a(entityliving, entity);
               } else {
                  entityliving.heal(5.0F, RegainReason.WITHER);
               }
            }
         } else {
            flag = entity.a(this.dG().o(), 5.0F);
         }

         if (flag && entity instanceof EntityLiving entityliving) {
            byte b0 = 0;
            if (this.H.ah() == EnumDifficulty.c) {
               b0 = 10;
            } else if (this.H.ah() == EnumDifficulty.d) {
               b0 = 40;
            }

            if (b0 > 0) {
               entityliving.addEffect(new MobEffect(MobEffects.t, 20 * b0, 1), this.z(), Cause.ATTACK);
            }
         }
      }
   }

   @Override
   protected void a(MovingObjectPosition movingobjectposition) {
      super.a(movingobjectposition);
      if (!this.H.B) {
         ExplosionPrimeEvent event = new ExplosionPrimeEvent(this.getBukkitEntity(), 1.0F, false);
         this.H.getCraftServer().getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            this.H.a(this, this.dl(), this.dn(), this.dr(), event.getRadius(), event.getFire(), World.a.c);
         }

         this.ai();
      }
   }

   @Override
   public boolean bm() {
      return false;
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      return false;
   }

   @Override
   protected void a_() {
      this.am.a(e, false);
   }

   public boolean o() {
      return this.am.a(e);
   }

   public void a(boolean flag) {
      this.am.b(e, flag);
   }

   @Override
   protected boolean Z_() {
      return false;
   }
}
