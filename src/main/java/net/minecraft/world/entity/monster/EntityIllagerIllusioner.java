package net.minecraft.world.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBowShoot;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.ProjectileHelper;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntityIllagerIllusioner extends EntityIllagerWizard implements IRangedEntity {
   private static final int e = 4;
   private static final int bS = 3;
   private static final int bT = 3;
   private int bU;
   private final Vec3D[][] bV;

   public EntityIllagerIllusioner(EntityTypes<? extends EntityIllagerIllusioner> entitytypes, World world) {
      super(entitytypes, world);
      this.bI = 5;
      this.bV = new Vec3D[2][4];

      for(int i = 0; i < 4; ++i) {
         this.bV[0][i] = Vec3D.b;
         this.bV[1][i] = Vec3D.b;
      }
   }

   @Override
   protected void x() {
      super.x();
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(1, new EntityIllagerWizard.b());
      this.bN.a(4, new EntityIllagerIllusioner.b());
      this.bN.a(5, new EntityIllagerIllusioner.a());
      this.bN.a(6, new PathfinderGoalBowShoot<>(this, 0.5, 20, 15.0F));
      this.bN.a(8, new PathfinderGoalRandomStroll(this, 0.6));
      this.bN.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 3.0F, 1.0F));
      this.bN.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this, EntityRaider.class).a());
      this.bO.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true).c(300));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityVillagerAbstract.class, false).c(300));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityIronGolem.class, false).c(300));
   }

   public static AttributeProvider.Builder r() {
      return EntityMonster.fY().a(GenericAttributes.d, 0.5).a(GenericAttributes.b, 18.0).a(GenericAttributes.a, 32.0);
   }

   @Override
   public GroupDataEntity a(
      WorldAccess worldaccess,
      DifficultyDamageScaler difficultydamagescaler,
      EnumMobSpawn enummobspawn,
      @Nullable GroupDataEntity groupdataentity,
      @Nullable NBTTagCompound nbttagcompound
   ) {
      this.a(EnumItemSlot.a, new ItemStack(Items.nC));
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   @Override
   protected void a_() {
      super.a_();
   }

   @Override
   public AxisAlignedBB A_() {
      return this.cD().c(3.0, 0.0, 3.0);
   }

   @Override
   public void b_() {
      super.b_();
      if (this.H.B && this.ca()) {
         --this.bU;
         if (this.bU < 0) {
            this.bU = 0;
         }

         if (this.aJ == 1 || this.ag % 1200 == 0) {
            this.bU = 3;
            float f = -6.0F;
            boolean flag = true;

            for(int j = 0; j < 4; ++j) {
               this.bV[0][j] = this.bV[1][j];
               this.bV[1][j] = new Vec3D(
                  (double)(-6.0F + (float)this.af.a(13)) * 0.5, (double)Math.max(0, this.af.a(6) - 4), (double)(-6.0F + (float)this.af.a(13)) * 0.5
               );
            }

            for(int var5 = 0; var5 < 16; ++var5) {
               this.H.a(Particles.f, this.d(0.5), this.do(), this.f(0.5), 0.0, 0.0, 0.0);
            }

            this.H.a(this.dl(), this.dn(), this.dr(), SoundEffects.lx, this.cX(), 1.0F, 1.0F, false);
         } else if (this.aJ == this.aK - 1) {
            this.bU = 3;

            for(int i = 0; i < 4; ++i) {
               this.bV[0][i] = this.bV[1][i];
               this.bV[1][i] = new Vec3D(0.0, 0.0, 0.0);
            }
         }
      }
   }

   @Override
   public SoundEffect X_() {
      return SoundEffects.lt;
   }

   public Vec3D[] C(float f) {
      if (this.bU <= 0) {
         return this.bV[1];
      } else {
         double d0 = (double)(((float)this.bU - f) / 3.0F);
         d0 = Math.pow(d0, 0.25);
         Vec3D[] avec3d = new Vec3D[4];

         for(int i = 0; i < 4; ++i) {
            avec3d[i] = this.bV[1][i].a(1.0 - d0).e(this.bV[0][i].a(d0));
         }

         return avec3d;
      }
   }

   @Override
   public boolean p(Entity entity) {
      return super.p(entity)
         ? true
         : (entity instanceof EntityLiving && ((EntityLiving)entity).eJ() == EnumMonsterType.d ? this.cb() == null && entity.cb() == null : false);
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.lt;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.lv;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.lw;
   }

   @Override
   protected SoundEffect fS() {
      return SoundEffects.lu;
   }

   @Override
   public void a(int i, boolean flag) {
   }

   @Override
   public void a(EntityLiving entityliving, float f) {
      ItemStack itemstack = this.g(this.b(ProjectileHelper.a(this, Items.nC)));
      EntityArrow entityarrow = ProjectileHelper.a(this, itemstack, f);
      double d0 = entityliving.dl() - this.dl();
      double d1 = entityliving.e(0.3333333333333333) - entityarrow.dn();
      double d2 = entityliving.dr() - this.dr();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      entityarrow.c(d0, d1 + d3 * 0.2F, d2, 1.6F, (float)(14 - this.H.ah().a() * 4));
      this.a(SoundEffects.vk, 1.0F, 1.0F / (this.dZ().i() * 0.4F + 0.8F));
      this.H.b(entityarrow);
   }

   @Override
   public EntityIllagerAbstract.a q() {
      return this.gc() ? EntityIllagerAbstract.a.c : (this.fM() ? EntityIllagerAbstract.a.d : EntityIllagerAbstract.a.a);
   }

   private class a extends EntityIllagerWizard.PathfinderGoalCastSpell {
      private int e;

      a() {
      }

      @Override
      public boolean a() {
         return !super.a()
            ? false
            : (
               EntityIllagerIllusioner.this.P_() == null
                  ? false
                  : (
                     EntityIllagerIllusioner.this.P_().af() == this.e
                        ? false
                        : EntityIllagerIllusioner.this.H.d_(EntityIllagerIllusioner.this.dg()).a((float)EnumDifficulty.c.ordinal())
                  )
            );
      }

      @Override
      public void c() {
         super.c();
         EntityLiving entityliving = EntityIllagerIllusioner.this.P_();
         if (entityliving != null) {
            this.e = entityliving.af();
         }
      }

      @Override
      protected int h() {
         return 20;
      }

      @Override
      protected int i() {
         return 180;
      }

      @Override
      protected void k() {
         EntityIllagerIllusioner.this.P_().addEffect(new MobEffect(MobEffects.o, 400), EntityIllagerIllusioner.this, Cause.ATTACK);
      }

      @Override
      protected SoundEffect l() {
         return SoundEffects.ly;
      }

      @Override
      protected EntityIllagerWizard.Spell m() {
         return EntityIllagerWizard.Spell.f;
      }
   }

   private class b extends EntityIllagerWizard.PathfinderGoalCastSpell {
      b() {
      }

      @Override
      public boolean a() {
         return !super.a() ? false : !EntityIllagerIllusioner.this.a(MobEffects.n);
      }

      @Override
      protected int h() {
         return 20;
      }

      @Override
      protected int i() {
         return 340;
      }

      @Override
      protected void k() {
         EntityIllagerIllusioner.this.addEffect(new MobEffect(MobEffects.n, 1200), Cause.ILLUSION);
      }

      @Nullable
      @Override
      protected SoundEffect l() {
         return SoundEffects.lz;
      }

      @Override
      protected EntityIllagerWizard.Spell m() {
         return EntityIllagerWizard.Spell.e;
      }
   }
}
