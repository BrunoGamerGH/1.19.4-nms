package net.minecraft.world.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLeapAtTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.navigation.NavigationSpider;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntitySpider extends EntityMonster {
   private static final DataWatcherObject<Byte> b = DataWatcher.a(EntitySpider.class, DataWatcherRegistry.a);
   private static final float c = 0.1F;

   public EntitySpider(EntityTypes<? extends EntitySpider> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   protected void x() {
      this.bN.a(1, new PathfinderGoalFloat(this));
      this.bN.a(3, new PathfinderGoalLeapAtTarget(this, 0.4F));
      this.bN.a(4, new EntitySpider.PathfinderGoalSpiderMeleeAttack(this));
      this.bN.a(5, new PathfinderGoalRandomStrollLand(this, 0.8));
      this.bN.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.bN.a(6, new PathfinderGoalRandomLookaround(this));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this));
      this.bO.a(2, new EntitySpider.PathfinderGoalSpiderNearestAttackableTarget<>(this, EntityHuman.class));
      this.bO.a(3, new EntitySpider.PathfinderGoalSpiderNearestAttackableTarget<>(this, EntityIronGolem.class));
   }

   @Override
   public double bv() {
      return (double)(this.dd() * 0.5F);
   }

   @Override
   protected NavigationAbstract a(World world) {
      return new NavigationSpider(this, world);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(b, (byte)0);
   }

   @Override
   public void l() {
      super.l();
      if (!this.H.B) {
         this.w(this.O);
      }
   }

   public static AttributeProvider.Builder r() {
      return EntityMonster.fY().a(GenericAttributes.a, 16.0).a(GenericAttributes.d, 0.3F);
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.wF;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.wH;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.wG;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.wI, 0.15F, 1.0F);
   }

   @Override
   public boolean z_() {
      return this.w();
   }

   @Override
   public void a(IBlockData iblockdata, Vec3D vec3d) {
      if (!iblockdata.a(Blocks.br)) {
         super.a(iblockdata, vec3d);
      }
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.c;
   }

   @Override
   public boolean c(MobEffect mobeffect) {
      return mobeffect.c() == MobEffects.s ? false : super.c(mobeffect);
   }

   public boolean w() {
      return (this.am.a(b) & 1) != 0;
   }

   public void w(boolean flag) {
      byte b0 = this.am.a(b);
      if (flag) {
         b0 = (byte)(b0 | 1);
      } else {
         b0 = (byte)(b0 & -2);
      }

      this.am.b(b, b0);
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
      Object object = super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
      RandomSource randomsource = worldaccess.r_();
      if (randomsource.a(100) == 0) {
         EntitySkeleton entityskeleton = EntityTypes.aJ.a(this.H);
         if (entityskeleton != null) {
            entityskeleton.b(this.dl(), this.dn(), this.dr(), this.dw(), 0.0F);
            entityskeleton.a(worldaccess, difficultydamagescaler, enummobspawn, null, null);
            entityskeleton.k(this);
         }
      }

      if (object == null) {
         object = new EntitySpider.GroupDataSpider();
         if (worldaccess.ah() == EnumDifficulty.d && randomsource.i() < 0.1F * difficultydamagescaler.d()) {
            ((EntitySpider.GroupDataSpider)object).a(randomsource);
         }
      }

      if (object instanceof EntitySpider.GroupDataSpider entityspider_groupdataspider) {
         MobEffectList mobeffectlist = entityspider_groupdataspider.a;
         if (mobeffectlist != null) {
            this.addEffect(new MobEffect(mobeffectlist, -1), Cause.SPIDER_SPAWN);
         }
      }

      return (GroupDataEntity)object;
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return 0.65F;
   }

   public static class GroupDataSpider implements GroupDataEntity {
      @Nullable
      public MobEffectList a;

      public void a(RandomSource randomsource) {
         int i = randomsource.a(5);
         if (i <= 1) {
            this.a = MobEffects.a;
         } else if (i <= 2) {
            this.a = MobEffects.e;
         } else if (i <= 3) {
            this.a = MobEffects.j;
         } else if (i <= 4) {
            this.a = MobEffects.n;
         }
      }
   }

   private static class PathfinderGoalSpiderMeleeAttack extends PathfinderGoalMeleeAttack {
      public PathfinderGoalSpiderMeleeAttack(EntitySpider entityspider) {
         super(entityspider, 1.0, true);
      }

      @Override
      public boolean a() {
         return super.a() && !this.a.bM();
      }

      @Override
      public boolean b() {
         float f = this.a.bh();
         if (f >= 0.5F && this.a.dZ().a(100) == 0) {
            this.a.i(null);
            return false;
         } else {
            return super.b();
         }
      }

      @Override
      protected double a(EntityLiving entityliving) {
         return (double)(4.0F + entityliving.dc());
      }
   }

   private static class PathfinderGoalSpiderNearestAttackableTarget<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget<T> {
      public PathfinderGoalSpiderNearestAttackableTarget(EntitySpider entityspider, Class<T> oclass) {
         super(entityspider, oclass, true);
      }

      @Override
      public boolean a() {
         float f = this.e.bh();
         return f >= 0.5F ? false : super.a();
      }
   }
}
