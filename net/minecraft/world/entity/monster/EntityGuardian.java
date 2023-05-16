package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerLook;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.navigation.NavigationGuardian;
import net.minecraft.world.entity.animal.EntitySquid;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3D;

public class EntityGuardian extends EntityMonster {
   protected static final int c = 80;
   private static final DataWatcherObject<Boolean> b = DataWatcher.a(EntityGuardian.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Integer> e = DataWatcher.a(EntityGuardian.class, DataWatcherRegistry.b);
   private float bS;
   private float bT;
   private float bU;
   private float bV;
   private float bW;
   @Nullable
   private EntityLiving bX;
   private int bY;
   private boolean bZ;
   @Nullable
   public PathfinderGoalRandomStroll d;

   public EntityGuardian(EntityTypes<? extends EntityGuardian> var0, World var1) {
      super(var0, var1);
      this.bI = 10;
      this.a(PathType.j, 0.0F);
      this.bK = new EntityGuardian.ControllerMoveGuardian(this);
      this.bS = this.af.i();
      this.bT = this.bS;
   }

   @Override
   protected void x() {
      PathfinderGoalMoveTowardsRestriction var0 = new PathfinderGoalMoveTowardsRestriction(this, 1.0);
      this.d = new PathfinderGoalRandomStroll(this, 1.0, 80);
      this.bN.a(4, new EntityGuardian.PathfinderGoalGuardianAttack(this));
      this.bN.a(5, var0);
      this.bN.a(7, this.d);
      this.bN.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.bN.a(8, new PathfinderGoalLookAtPlayer(this, EntityGuardian.class, 12.0F, 0.01F));
      this.bN.a(9, new PathfinderGoalRandomLookaround(this));
      this.d.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      var0.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      this.bO
         .a(
            1,
            new PathfinderGoalNearestAttackableTarget<>(
               this, EntityLiving.class, 10, true, false, new EntityGuardian.EntitySelectorGuardianTargetHumanSquid(this)
            )
         );
   }

   public static AttributeProvider.Builder fS() {
      return EntityMonster.fY().a(GenericAttributes.f, 6.0).a(GenericAttributes.d, 0.5).a(GenericAttributes.b, 16.0).a(GenericAttributes.a, 30.0);
   }

   @Override
   protected NavigationAbstract a(World var0) {
      return new NavigationGuardian(this, var0);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(b, false);
      this.am.a(e, 0);
   }

   @Override
   public boolean dK() {
      return true;
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.e;
   }

   public boolean fT() {
      return this.am.a(b);
   }

   void w(boolean var0) {
      this.am.b(b, var0);
   }

   public int r() {
      return 80;
   }

   public void b(int var0) {
      this.am.b(e, var0);
   }

   public boolean fU() {
      return this.am.a(e) != 0;
   }

   @Nullable
   public EntityLiving fV() {
      if (!this.fU()) {
         return null;
      } else if (this.H.B) {
         if (this.bX != null) {
            return this.bX;
         } else {
            Entity var0 = this.H.a(this.am.a(e));
            if (var0 instanceof EntityLiving) {
               this.bX = (EntityLiving)var0;
               return this.bX;
            } else {
               return null;
            }
         }
      } else {
         return this.P_();
      }
   }

   @Override
   public void a(DataWatcherObject<?> var0) {
      super.a(var0);
      if (e.equals(var0)) {
         this.bY = 0;
         this.bX = null;
      }
   }

   @Override
   public int K() {
      return 160;
   }

   @Override
   protected SoundEffect s() {
      return this.aW() ? SoundEffects.ka : SoundEffects.kb;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return this.aW() ? SoundEffects.kg : SoundEffects.kh;
   }

   @Override
   protected SoundEffect x_() {
      return this.aW() ? SoundEffects.kd : SoundEffects.ke;
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.c;
   }

   @Override
   protected float b(EntityPose var0, EntitySize var1) {
      return var1.b * 0.5F;
   }

   @Override
   public float a(BlockPosition var0, IWorldReader var1) {
      return var1.b_(var0).a(TagsFluid.a) ? 10.0F + var1.y(var0) : super.a(var0, var1);
   }

   @Override
   public void b_() {
      if (this.bq()) {
         if (this.H.B) {
            this.bT = this.bS;
            if (!this.aT()) {
               this.bU = 2.0F;
               Vec3D var0 = this.dj();
               if (var0.d > 0.0 && this.bZ && !this.aO()) {
                  this.H.a(this.dl(), this.dn(), this.dr(), this.w(), this.cX(), 1.0F, 1.0F, false);
               }

               this.bZ = var0.d < 0.0 && this.H.a(this.dg().d(), this);
            } else if (this.fT()) {
               if (this.bU < 0.5F) {
                  this.bU = 4.0F;
               } else {
                  this.bU += (0.5F - this.bU) * 0.1F;
               }
            } else {
               this.bU += (0.125F - this.bU) * 0.2F;
            }

            this.bS += this.bU;
            this.bW = this.bV;
            if (!this.aW()) {
               this.bV = this.af.i();
            } else if (this.fT()) {
               this.bV += (0.0F - this.bV) * 0.25F;
            } else {
               this.bV += (1.0F - this.bV) * 0.06F;
            }

            if (this.fT() && this.aT()) {
               Vec3D var0 = this.j(0.0F);

               for(int var1 = 0; var1 < 2; ++var1) {
                  this.H.a(Particles.e, this.d(0.5) - var0.c * 1.5, this.do() - var0.d * 1.5, this.g(0.5) - var0.e * 1.5, 0.0, 0.0, 0.0);
               }
            }

            if (this.fU()) {
               if (this.bY < this.r()) {
                  ++this.bY;
               }

               EntityLiving var0 = this.fV();
               if (var0 != null) {
                  this.C().a(var0, 90.0F, 90.0F);
                  this.C().a();
                  double var1 = (double)this.E(0.0F);
                  double var3 = var0.dl() - this.dl();
                  double var5 = var0.e(0.5) - this.dp();
                  double var7 = var0.dr() - this.dr();
                  double var9 = Math.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
                  var3 /= var9;
                  var5 /= var9;
                  var7 /= var9;
                  double var11 = this.af.j();

                  while(var11 < var9) {
                     var11 += 1.8 - var1 + this.af.j() * (1.7 - var1);
                     this.H.a(Particles.e, this.dl() + var3 * var11, this.dp() + var5 * var11, this.dr() + var7 * var11, 0.0, 0.0, 0.0);
                  }
               }
            }
         }

         if (this.aW()) {
            this.i(300);
         } else if (this.N) {
            this.f(this.dj().b((double)((this.af.i() * 2.0F - 1.0F) * 0.4F), 0.5, (double)((this.af.i() * 2.0F - 1.0F) * 0.4F)));
            this.f(this.af.i() * 360.0F);
            this.N = false;
            this.at = true;
         }

         if (this.fU()) {
            this.f(this.aV);
         }
      }

      super.b_();
   }

   protected SoundEffect w() {
      return SoundEffects.kf;
   }

   public float C(float var0) {
      return MathHelper.i(var0, this.bT, this.bS);
   }

   public float D(float var0) {
      return MathHelper.i(var0, this.bW, this.bV);
   }

   public float E(float var0) {
      return ((float)this.bY + var0) / (float)this.r();
   }

   public float fW() {
      return (float)this.bY;
   }

   @Override
   public boolean a(IWorldReader var0) {
      return var0.f(this);
   }

   public static boolean b(EntityTypes<? extends EntityGuardian> var0, GeneratorAccess var1, EnumMobSpawn var2, BlockPosition var3, RandomSource var4) {
      return (var4.a(20) == 0 || !var1.x(var3))
         && var1.ah() != EnumDifficulty.a
         && (var2 == EnumMobSpawn.c || var1.b_(var3).a(TagsFluid.a))
         && var1.b_(var3.d()).a(TagsFluid.a);
   }

   @Override
   public boolean a(DamageSource var0, float var1) {
      if (this.H.B) {
         return false;
      } else {
         if (!this.fT() && !var0.a(DamageTypeTags.w) && !var0.a(DamageTypes.L)) {
            Entity var4 = var0.c();
            if (var4 instanceof EntityLiving var2) {
               var2.a(this.dG().d(this), 2.0F);
            }
         }

         if (this.d != null) {
            this.d.i();
         }

         return super.a(var0, var1);
      }
   }

   @Override
   public int V() {
      return 180;
   }

   @Override
   public void h(Vec3D var0) {
      if (this.cT() && this.aT()) {
         this.a(0.1F, var0);
         this.a(EnumMoveType.a, this.dj());
         this.f(this.dj().a(0.9));
         if (!this.fT() && this.P_() == null) {
            this.f(this.dj().b(0.0, -0.005, 0.0));
         }
      } else {
         super.h(var0);
      }
   }

   static class ControllerMoveGuardian extends ControllerMove {
      private final EntityGuardian l;

      public ControllerMoveGuardian(EntityGuardian var0) {
         super(var0);
         this.l = var0;
      }

      @Override
      public void a() {
         if (this.k == ControllerMove.Operation.b && !this.l.G().l()) {
            Vec3D var0 = new Vec3D(this.e - this.l.dl(), this.f - this.l.dn(), this.g - this.l.dr());
            double var1 = var0.f();
            double var3 = var0.c / var1;
            double var5 = var0.d / var1;
            double var7 = var0.e / var1;
            float var9 = (float)(MathHelper.d(var0.e, var0.c) * 180.0F / (float)Math.PI) - 90.0F;
            this.l.f(this.a(this.l.dw(), var9, 90.0F));
            this.l.aT = this.l.dw();
            float var10 = (float)(this.h * this.l.b(GenericAttributes.d));
            float var11 = MathHelper.i(0.125F, this.l.eW(), var10);
            this.l.h(var11);
            double var12 = Math.sin((double)(this.l.ag + this.l.af()) * 0.5) * 0.05;
            double var14 = Math.cos((double)(this.l.dw() * (float) (Math.PI / 180.0)));
            double var16 = Math.sin((double)(this.l.dw() * (float) (Math.PI / 180.0)));
            double var18 = Math.sin((double)(this.l.ag + this.l.af()) * 0.75) * 0.05;
            this.l.f(this.l.dj().b(var12 * var14, var18 * (var16 + var14) * 0.25 + (double)var11 * var5 * 0.1, var12 * var16));
            ControllerLook var20 = this.l.C();
            double var21 = this.l.dl() + var3 * 2.0;
            double var23 = this.l.dp() + var5 / var1;
            double var25 = this.l.dr() + var7 * 2.0;
            double var27 = var20.e();
            double var29 = var20.f();
            double var31 = var20.g();
            if (!var20.d()) {
               var27 = var21;
               var29 = var23;
               var31 = var25;
            }

            this.l.C().a(MathHelper.d(0.125, var27, var21), MathHelper.d(0.125, var29, var23), MathHelper.d(0.125, var31, var25), 10.0F, 40.0F);
            this.l.w(true);
         } else {
            this.l.h(0.0F);
            this.l.w(false);
         }
      }
   }

   static class EntitySelectorGuardianTargetHumanSquid implements Predicate<EntityLiving> {
      private final EntityGuardian a;

      public EntitySelectorGuardianTargetHumanSquid(EntityGuardian var0) {
         this.a = var0;
      }

      public boolean a(@Nullable EntityLiving var0) {
         return (var0 instanceof EntityHuman || var0 instanceof EntitySquid || var0 instanceof Axolotl) && var0.f(this.a) > 9.0;
      }
   }

   public static class PathfinderGoalGuardianAttack extends PathfinderGoal {
      private final EntityGuardian a;
      public int b;
      private final boolean c;

      public PathfinderGoalGuardianAttack(EntityGuardian var0) {
         this.a = var0;
         this.c = var0 instanceof EntityGuardianElder;
         this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      }

      @Override
      public boolean a() {
         EntityLiving var0 = this.a.P_();
         return var0 != null && var0.bq();
      }

      @Override
      public boolean b() {
         return super.b() && (this.c || this.a.P_() != null && this.a.f(this.a.P_()) > 9.0);
      }

      @Override
      public void c() {
         this.b = -10;
         this.a.G().n();
         EntityLiving var0 = this.a.P_();
         if (var0 != null) {
            this.a.C().a(var0, 90.0F, 90.0F);
         }

         this.a.at = true;
      }

      @Override
      public void d() {
         this.a.b(0);
         this.a.i(null);
         this.a.d.i();
      }

      @Override
      public boolean J_() {
         return true;
      }

      @Override
      public void e() {
         EntityLiving var0 = this.a.P_();
         if (var0 != null) {
            this.a.G().n();
            this.a.C().a(var0, 90.0F, 90.0F);
            if (!this.a.B(var0)) {
               this.a.i(null);
            } else {
               ++this.b;
               if (this.b == 0) {
                  this.a.b(var0.af());
                  if (!this.a.aO()) {
                     this.a.H.a(this.a, (byte)21);
                  }
               } else if (this.b >= this.a.r()) {
                  float var1 = 1.0F;
                  if (this.a.H.ah() == EnumDifficulty.d) {
                     var1 += 2.0F;
                  }

                  if (this.c) {
                     var1 += 2.0F;
                  }

                  var0.a(this.a.dG().c(this.a, this.a), var1);
                  var0.a(this.a.dG().b((EntityLiving)this.a), (float)this.a.b(GenericAttributes.f));
                  this.a.i(null);
               }

               super.e();
            }
         }
      }
   }
}
