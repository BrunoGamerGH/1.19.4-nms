package net.minecraft.world.entity.monster;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityFlying;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerLook;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.control.EntityAIBodyControl;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityPhantom extends EntityFlying implements IMonster {
   public static final float b = 7.448451F;
   public static final int c = MathHelper.f(24.166098F);
   private static final DataWatcherObject<Integer> d = DataWatcher.a(EntityPhantom.class, DataWatcherRegistry.b);
   Vec3D e = Vec3D.b;
   BlockPosition bR = BlockPosition.b;
   EntityPhantom.AttackPhase bS = EntityPhantom.AttackPhase.a;

   public EntityPhantom(EntityTypes<? extends EntityPhantom> entitytypes, World world) {
      super(entitytypes, world);
      this.bI = 5;
      this.bK = new EntityPhantom.g(this);
      this.bJ = new EntityPhantom.f(this);
   }

   @Override
   public boolean aN() {
      return (this.r() + this.ag) % c == 0;
   }

   @Override
   protected EntityAIBodyControl A() {
      return new EntityPhantom.d(this);
   }

   @Override
   protected void x() {
      this.bN.a(1, new EntityPhantom.c());
      this.bN.a(2, new EntityPhantom.i());
      this.bN.a(3, new EntityPhantom.e());
      this.bO.a(1, new EntityPhantom.b());
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(d, 0);
   }

   public void b(int i) {
      this.am.b(d, MathHelper.a(i, 0, 64));
   }

   private void w() {
      this.c_();
      this.a(GenericAttributes.f).a((double)(6 + this.q()));
   }

   public int q() {
      return this.am.a(d);
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return entitysize.b * 0.35F;
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (d.equals(datawatcherobject)) {
         this.w();
      }

      super.a(datawatcherobject);
   }

   public int r() {
      return this.af() * 3;
   }

   @Override
   protected boolean R() {
      return true;
   }

   @Override
   public void l() {
      super.l();
      if (this.H.B) {
         float f = MathHelper.b((float)(this.r() + this.ag) * 7.448451F * (float) (Math.PI / 180.0) + (float) Math.PI);
         float f1 = MathHelper.b((float)(this.r() + this.ag + 1) * 7.448451F * (float) (Math.PI / 180.0) + (float) Math.PI);
         if (f > 0.0F && f1 <= 0.0F) {
            this.H.a(this.dl(), this.dn(), this.dr(), SoundEffects.rv, this.cX(), 0.95F + this.af.i() * 0.05F, 0.95F + this.af.i() * 0.05F, false);
         }

         int i = this.q();
         float f2 = MathHelper.b(this.dw() * (float) (Math.PI / 180.0)) * (1.3F + 0.21F * (float)i);
         float f3 = MathHelper.a(this.dw() * (float) (Math.PI / 180.0)) * (1.3F + 0.21F * (float)i);
         float f4 = (0.3F + f * 0.45F) * ((float)i * 0.2F + 1.0F);
         this.H.a(Particles.W, this.dl() + (double)f2, this.dn() + (double)f4, this.dr() + (double)f3, 0.0, 0.0, 0.0);
         this.H.a(Particles.W, this.dl() - (double)f2, this.dn() + (double)f4, this.dr() - (double)f3, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public void b_() {
      if (this.bq() && this.fN()) {
         this.f(8);
      }

      super.b_();
   }

   @Override
   protected void U() {
      super.U();
   }

   @Override
   public GroupDataEntity a(
      WorldAccess worldaccess,
      DifficultyDamageScaler difficultydamagescaler,
      EnumMobSpawn enummobspawn,
      @Nullable GroupDataEntity groupdataentity,
      @Nullable NBTTagCompound nbttagcompound
   ) {
      this.bR = this.dg().b(5);
      this.b(0);
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.e("AX")) {
         this.bR = new BlockPosition(nbttagcompound.h("AX"), nbttagcompound.h("AY"), nbttagcompound.h("AZ"));
      }

      this.b(nbttagcompound.h("Size"));
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("AX", this.bR.u());
      nbttagcompound.a("AY", this.bR.v());
      nbttagcompound.a("AZ", this.bR.w());
      nbttagcompound.a("Size", this.q());
   }

   @Override
   public boolean a(double d0) {
      return true;
   }

   @Override
   public SoundCategory cX() {
      return SoundCategory.f;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.rs;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.rw;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.ru;
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.b;
   }

   @Override
   protected float eN() {
      return 1.0F;
   }

   @Override
   public boolean a(EntityTypes<?> entitytypes) {
      return true;
   }

   @Override
   public EntitySize a(EntityPose entitypose) {
      int i = this.q();
      EntitySize entitysize = super.a(entitypose);
      float f = (entitysize.a + 0.2F * (float)i) / entitysize.a;
      return entitysize.a(f);
   }

   @Override
   public double bv() {
      return (double)this.cE();
   }

   private static enum AttackPhase {
      a,
      b;
   }

   private class b extends PathfinderGoal {
      private final PathfinderTargetCondition b = PathfinderTargetCondition.a().a(64.0);
      private int c = b(20);

      b() {
      }

      @Override
      public boolean a() {
         if (this.c > 0) {
            --this.c;
            return false;
         } else {
            this.c = b(60);
            List<EntityHuman> list = EntityPhantom.this.H.a(this.b, EntityPhantom.this, EntityPhantom.this.cD().c(16.0, 64.0, 16.0));
            if (!list.isEmpty()) {
               list.sort(Comparator.<EntityHuman, Double>comparing(e -> e.dn()).reversed());

               for(EntityHuman entityhuman : list) {
                  if (EntityPhantom.this.a(entityhuman, PathfinderTargetCondition.a)) {
                     EntityPhantom.this.setTarget(entityhuman, TargetReason.CLOSEST_PLAYER, true);
                     return true;
                  }
               }
            }

            return false;
         }
      }

      @Override
      public boolean b() {
         EntityLiving entityliving = EntityPhantom.this.P_();
         return entityliving != null ? EntityPhantom.this.a(entityliving, PathfinderTargetCondition.a) : false;
      }
   }

   private class c extends PathfinderGoal {
      private int b;

      c() {
      }

      @Override
      public boolean a() {
         EntityLiving entityliving = EntityPhantom.this.P_();
         return entityliving != null ? EntityPhantom.this.a(entityliving, PathfinderTargetCondition.a) : false;
      }

      @Override
      public void c() {
         this.b = this.a(10);
         EntityPhantom.this.bS = EntityPhantom.AttackPhase.a;
         this.h();
      }

      @Override
      public void d() {
         EntityPhantom.this.bR = EntityPhantom.this.H.a(HeightMap.Type.e, EntityPhantom.this.bR).b(10 + EntityPhantom.this.af.a(20));
      }

      @Override
      public void e() {
         if (EntityPhantom.this.bS == EntityPhantom.AttackPhase.a) {
            --this.b;
            if (this.b <= 0) {
               EntityPhantom.this.bS = EntityPhantom.AttackPhase.b;
               this.h();
               this.b = this.a((8 + EntityPhantom.this.af.a(4)) * 20);
               EntityPhantom.this.a(SoundEffects.rx, 10.0F, 0.95F + EntityPhantom.this.af.i() * 0.1F);
            }
         }
      }

      private void h() {
         EntityPhantom.this.bR = EntityPhantom.this.P_().dg().b(20 + EntityPhantom.this.af.a(20));
         if (EntityPhantom.this.bR.v() < EntityPhantom.this.H.m_()) {
            EntityPhantom.this.bR = new BlockPosition(EntityPhantom.this.bR.u(), EntityPhantom.this.H.m_() + 1, EntityPhantom.this.bR.w());
         }
      }
   }

   private class d extends EntityAIBodyControl {
      public d(EntityInsentient entityinsentient) {
         super(entityinsentient);
      }

      @Override
      public void a() {
         EntityPhantom.this.aV = EntityPhantom.this.aT;
         EntityPhantom.this.aT = EntityPhantom.this.dw();
      }
   }

   private class e extends EntityPhantom.h {
      private float c;
      private float d;
      private float e;
      private float f;

      e() {
      }

      @Override
      public boolean a() {
         return EntityPhantom.this.P_() == null || EntityPhantom.this.bS == EntityPhantom.AttackPhase.a;
      }

      @Override
      public void c() {
         this.d = 5.0F + EntityPhantom.this.af.i() * 10.0F;
         this.e = -4.0F + EntityPhantom.this.af.i() * 9.0F;
         this.f = EntityPhantom.this.af.h() ? 1.0F : -1.0F;
         this.i();
      }

      @Override
      public void e() {
         if (EntityPhantom.this.af.a(this.a(350)) == 0) {
            this.e = -4.0F + EntityPhantom.this.af.i() * 9.0F;
         }

         if (EntityPhantom.this.af.a(this.a(250)) == 0) {
            ++this.d;
            if (this.d > 15.0F) {
               this.d = 5.0F;
               this.f = -this.f;
            }
         }

         if (EntityPhantom.this.af.a(this.a(450)) == 0) {
            this.c = EntityPhantom.this.af.i() * 2.0F * (float) Math.PI;
            this.i();
         }

         if (this.h()) {
            this.i();
         }

         if (EntityPhantom.this.e.d < EntityPhantom.this.dn() && !EntityPhantom.this.H.w(EntityPhantom.this.dg().c(1))) {
            this.e = Math.max(1.0F, this.e);
            this.i();
         }

         if (EntityPhantom.this.e.d > EntityPhantom.this.dn() && !EntityPhantom.this.H.w(EntityPhantom.this.dg().b(1))) {
            this.e = Math.min(-1.0F, this.e);
            this.i();
         }
      }

      private void i() {
         if (BlockPosition.b.equals(EntityPhantom.this.bR)) {
            EntityPhantom.this.bR = EntityPhantom.this.dg();
         }

         this.c += this.f * 15.0F * (float) (Math.PI / 180.0);
         EntityPhantom.this.e = Vec3D.a(EntityPhantom.this.bR)
            .b((double)(this.d * MathHelper.b(this.c)), (double)(-4.0F + this.e), (double)(this.d * MathHelper.a(this.c)));
      }
   }

   private class f extends ControllerLook {
      public f(EntityInsentient entityinsentient) {
         super(entityinsentient);
      }

      @Override
      public void a() {
      }
   }

   private class g extends ControllerMove {
      private float m = 0.1F;

      public g(EntityInsentient entityinsentient) {
         super(entityinsentient);
      }

      @Override
      public void a() {
         if (EntityPhantom.this.O) {
            EntityPhantom.this.f(EntityPhantom.this.dw() + 180.0F);
            this.m = 0.1F;
         }

         double d0 = EntityPhantom.this.e.c - EntityPhantom.this.dl();
         double d1 = EntityPhantom.this.e.d - EntityPhantom.this.dn();
         double d2 = EntityPhantom.this.e.e - EntityPhantom.this.dr();
         double d3 = Math.sqrt(d0 * d0 + d2 * d2);
         if (Math.abs(d3) > 1.0E-5F) {
            double d4 = 1.0 - Math.abs(d1 * 0.7F) / d3;
            d0 *= d4;
            d2 *= d4;
            d3 = Math.sqrt(d0 * d0 + d2 * d2);
            double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
            float f = EntityPhantom.this.dw();
            float f1 = (float)MathHelper.d(d2, d0);
            float f2 = MathHelper.g(EntityPhantom.this.dw() + 90.0F);
            float f3 = MathHelper.g(f1 * (180.0F / (float)Math.PI));
            EntityPhantom.this.f(MathHelper.e(f2, f3, 4.0F) - 90.0F);
            EntityPhantom.this.aT = EntityPhantom.this.dw();
            if (MathHelper.d(f, EntityPhantom.this.dw()) < 3.0F) {
               this.m = MathHelper.d(this.m, 1.8F, 0.005F * (1.8F / this.m));
            } else {
               this.m = MathHelper.d(this.m, 0.2F, 0.025F);
            }

            float f4 = (float)(-(MathHelper.d(-d1, d3) * 180.0F / (float)Math.PI));
            EntityPhantom.this.e(f4);
            float f5 = EntityPhantom.this.dw() + 90.0F;
            double d6 = (double)(this.m * MathHelper.b(f5 * (float) (Math.PI / 180.0))) * Math.abs(d0 / d5);
            double d7 = (double)(this.m * MathHelper.a(f5 * (float) (Math.PI / 180.0))) * Math.abs(d2 / d5);
            double d8 = (double)(this.m * MathHelper.a(f4 * (float) (Math.PI / 180.0))) * Math.abs(d1 / d5);
            Vec3D vec3d = EntityPhantom.this.dj();
            EntityPhantom.this.f(vec3d.e(new Vec3D(d6, d8, d7).d(vec3d).a(0.2)));
         }
      }
   }

   private abstract class h extends PathfinderGoal {
      public h() {
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      protected boolean h() {
         return EntityPhantom.this.e.c(EntityPhantom.this.dl(), EntityPhantom.this.dn(), EntityPhantom.this.dr()) < 4.0;
      }
   }

   private class i extends EntityPhantom.h {
      private static final int c = 20;
      private boolean d;
      private int e;

      i() {
      }

      @Override
      public boolean a() {
         return EntityPhantom.this.P_() != null && EntityPhantom.this.bS == EntityPhantom.AttackPhase.b;
      }

      @Override
      public boolean b() {
         EntityLiving entityliving = EntityPhantom.this.P_();
         if (entityliving == null) {
            return false;
         } else if (!entityliving.bq()) {
            return false;
         } else {
            if (entityliving instanceof EntityHuman entityhuman && (entityliving.F_() || entityhuman.f())) {
               return false;
            }

            if (!this.a()) {
               return false;
            } else {
               if (EntityPhantom.this.ag > this.e) {
                  this.e = EntityPhantom.this.ag + 20;
                  List<EntityCat> list = EntityPhantom.this.H.a(EntityCat.class, EntityPhantom.this.cD().g(16.0), IEntitySelector.a);

                  for(EntityCat entitycat : list) {
                     entitycat.gd();
                  }

                  this.d = !list.isEmpty();
               }

               return !this.d;
            }
         }
      }

      @Override
      public void c() {
      }

      @Override
      public void d() {
         EntityPhantom.this.i(null);
         EntityPhantom.this.bS = EntityPhantom.AttackPhase.a;
      }

      @Override
      public void e() {
         EntityLiving entityliving = EntityPhantom.this.P_();
         if (entityliving != null) {
            EntityPhantom.this.e = new Vec3D(entityliving.dl(), entityliving.e(0.5), entityliving.dr());
            if (EntityPhantom.this.cD().g(0.2F).c(entityliving.cD())) {
               EntityPhantom.this.z(entityliving);
               EntityPhantom.this.bS = EntityPhantom.AttackPhase.a;
               if (!EntityPhantom.this.aO()) {
                  EntityPhantom.this.H.c(1039, EntityPhantom.this.dg(), 0);
               }
            } else if (EntityPhantom.this.O || EntityPhantom.this.aJ > 0) {
               EntityPhantom.this.bS = EntityPhantom.AttackPhase.a;
            }
         }
      }
   }
}
