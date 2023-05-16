package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalTarget;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityVex extends EntityMonster implements TraceableEntity {
   public static final float b = 45.836624F;
   public static final int c = MathHelper.f((float) (Math.PI * 5.0 / 4.0));
   protected static final DataWatcherObject<Byte> d = DataWatcher.a(EntityVex.class, DataWatcherRegistry.a);
   private static final int e = 1;
   private static final double bS = 0.4;
   @Nullable
   EntityInsentient bT;
   @Nullable
   private BlockPosition bU;
   public boolean bV;
   public int bW;

   public EntityVex(EntityTypes<? extends EntityVex> entitytypes, World world) {
      super(entitytypes, world);
      this.bK = new EntityVex.c(this);
      this.bI = 3;
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return entitysize.b - 0.28125F;
   }

   @Override
   public boolean aN() {
      return this.ag % c == 0;
   }

   @Override
   public void a(EnumMoveType enummovetype, Vec3D vec3d) {
      super.a(enummovetype, vec3d);
      this.aL();
   }

   @Override
   public void l() {
      this.ae = true;
      super.l();
      this.ae = false;
      this.e(true);
      if (this.bV && --this.bW <= 0) {
         this.bW = 20;
         this.a(this.dG().i(), 1.0F);
      }
   }

   @Override
   protected void x() {
      super.x();
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(4, new EntityVex.a());
      this.bN.a(8, new EntityVex.d());
      this.bN.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 3.0F, 1.0F));
      this.bN.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this, EntityRaider.class).a());
      this.bO.a(2, new EntityVex.b(this));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
   }

   public static AttributeProvider.Builder q() {
      return EntityMonster.fY().a(GenericAttributes.a, 14.0).a(GenericAttributes.f, 4.0);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(d, (byte)0);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.e("BoundX")) {
         this.bU = new BlockPosition(nbttagcompound.h("BoundX"), nbttagcompound.h("BoundY"), nbttagcompound.h("BoundZ"));
      }

      if (nbttagcompound.e("LifeTicks")) {
         this.b(nbttagcompound.h("LifeTicks"));
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (this.bU != null) {
         nbttagcompound.a("BoundX", this.bU.u());
         nbttagcompound.a("BoundY", this.bU.v());
         nbttagcompound.a("BoundZ", this.bU.w());
      }

      if (this.bV) {
         nbttagcompound.a("LifeTicks", this.bW);
      }
   }

   @Nullable
   public EntityInsentient r() {
      return this.bT;
   }

   @Nullable
   public BlockPosition w() {
      return this.bU;
   }

   public void g(@Nullable BlockPosition blockposition) {
      this.bU = blockposition;
   }

   private boolean c(int i) {
      byte b0 = this.am.a(d);
      return (b0 & i) != 0;
   }

   private void a(int i, boolean flag) {
      byte b0 = this.am.a(d);
      int j;
      if (flag) {
         j = b0 | i;
      } else {
         j = b0 & ~i;
      }

      this.am.b(d, (byte)(j & 0xFF));
   }

   public boolean fS() {
      return this.c(1);
   }

   public void w(boolean flag) {
      this.a(1, flag);
   }

   public void a(EntityInsentient entityinsentient) {
      this.bT = entityinsentient;
   }

   public void b(int i) {
      this.bV = true;
      this.bW = i;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.ye;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.yg;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.yh;
   }

   @Override
   public float bh() {
      return 1.0F;
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
      RandomSource randomsource = worldaccess.r_();
      this.a(randomsource, difficultydamagescaler);
      this.b(randomsource, difficultydamagescaler);
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   @Override
   protected void a(RandomSource randomsource, DifficultyDamageScaler difficultydamagescaler) {
      this.a(EnumItemSlot.a, new ItemStack(Items.oi));
      this.a(EnumItemSlot.a, 0.0F);
   }

   @Override
   public double bu() {
      return 0.4;
   }

   private class a extends PathfinderGoal {
      public a() {
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         EntityLiving entityliving = EntityVex.this.P_();
         return entityliving != null && entityliving.bq() && !EntityVex.this.D().b() && EntityVex.this.af.a(b(7)) == 0
            ? EntityVex.this.f(entityliving) > 4.0
            : false;
      }

      @Override
      public boolean b() {
         return EntityVex.this.D().b() && EntityVex.this.fS() && EntityVex.this.P_() != null && EntityVex.this.P_().bq();
      }

      @Override
      public void c() {
         EntityLiving entityliving = EntityVex.this.P_();
         if (entityliving != null) {
            Vec3D vec3d = entityliving.bk();
            EntityVex.this.bK.a(vec3d.c, vec3d.d, vec3d.e, 1.0);
         }

         EntityVex.this.w(true);
         EntityVex.this.a(SoundEffects.yf, 1.0F, 1.0F);
      }

      @Override
      public void d() {
         EntityVex.this.w(false);
      }

      @Override
      public boolean J_() {
         return true;
      }

      @Override
      public void e() {
         EntityLiving entityliving = EntityVex.this.P_();
         if (entityliving != null) {
            if (EntityVex.this.cD().c(entityliving.cD())) {
               EntityVex.this.z(entityliving);
               EntityVex.this.w(false);
            } else {
               double d0 = EntityVex.this.f(entityliving);
               if (d0 < 9.0) {
                  Vec3D vec3d = entityliving.bk();
                  EntityVex.this.bK.a(vec3d.c, vec3d.d, vec3d.e, 1.0);
               }
            }
         }
      }
   }

   private class b extends PathfinderGoalTarget {
      private final PathfinderTargetCondition b = PathfinderTargetCondition.b().d().e();

      public b(EntityCreature entitycreature) {
         super(entitycreature, false);
      }

      @Override
      public boolean a() {
         return EntityVex.this.bT != null && EntityVex.this.bT.P_() != null && this.a(EntityVex.this.bT.P_(), this.b);
      }

      @Override
      public void c() {
         EntityVex.this.setTarget(EntityVex.this.bT.P_(), TargetReason.OWNER_ATTACKED_TARGET, true);
         super.c();
      }
   }

   private class c extends ControllerMove {
      public c(EntityVex entityvex) {
         super(entityvex);
      }

      @Override
      public void a() {
         if (this.k == ControllerMove.Operation.b) {
            Vec3D vec3d = new Vec3D(this.e - EntityVex.this.dl(), this.f - EntityVex.this.dn(), this.g - EntityVex.this.dr());
            double d0 = vec3d.f();
            if (d0 < EntityVex.this.cD().a()) {
               this.k = ControllerMove.Operation.a;
               EntityVex.this.f(EntityVex.this.dj().a(0.5));
            } else {
               EntityVex.this.f(EntityVex.this.dj().e(vec3d.a(this.h * 0.05 / d0)));
               if (EntityVex.this.P_() == null) {
                  Vec3D vec3d1 = EntityVex.this.dj();
                  EntityVex.this.f(-((float)MathHelper.d(vec3d1.c, vec3d1.e)) * (180.0F / (float)Math.PI));
                  EntityVex.this.aT = EntityVex.this.dw();
               } else {
                  double d1 = EntityVex.this.P_().dl() - EntityVex.this.dl();
                  double d2 = EntityVex.this.P_().dr() - EntityVex.this.dr();
                  EntityVex.this.f(-((float)MathHelper.d(d1, d2)) * (180.0F / (float)Math.PI));
                  EntityVex.this.aT = EntityVex.this.dw();
               }
            }
         }
      }
   }

   private class d extends PathfinderGoal {
      public d() {
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         return !EntityVex.this.D().b() && EntityVex.this.af.a(b(7)) == 0;
      }

      @Override
      public boolean b() {
         return false;
      }

      @Override
      public void e() {
         BlockPosition blockposition = EntityVex.this.w();
         if (blockposition == null) {
            blockposition = EntityVex.this.dg();
         }

         for(int i = 0; i < 3; ++i) {
            BlockPosition blockposition1 = blockposition.b(EntityVex.this.af.a(15) - 7, EntityVex.this.af.a(11) - 5, EntityVex.this.af.a(15) - 7);
            if (EntityVex.this.H.w(blockposition1)) {
               EntityVex.this.bK.a((double)blockposition1.u() + 0.5, (double)blockposition1.v() + 0.5, (double)blockposition1.w() + 0.5, 0.25);
               if (EntityVex.this.P_() == null) {
                  EntityVex.this.C().a((double)blockposition1.u() + 0.5, (double)blockposition1.v() + 0.5, (double)blockposition1.w() + 0.5, 180.0F, 20.0F);
               }
               break;
            }
         }
      }
   }
}
