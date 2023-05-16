package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerLook;
import net.minecraft.world.entity.ai.control.EntityAIBodyControl;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityGolem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.EntityShulkerBullet;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Location;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.joml.Vector3f;

public class EntityShulker extends EntityGolem implements VariantHolder<Optional<EnumColor>>, IMonster {
   private static final UUID e = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
   private static final AttributeModifier bS = new AttributeModifier(e, "Covered armor bonus", 20.0, AttributeModifier.Operation.a);
   protected static final DataWatcherObject<EnumDirection> b = DataWatcher.a(EntityShulker.class, DataWatcherRegistry.p);
   protected static final DataWatcherObject<Byte> c = DataWatcher.a(EntityShulker.class, DataWatcherRegistry.a);
   public static final DataWatcherObject<Byte> d = DataWatcher.a(EntityShulker.class, DataWatcherRegistry.a);
   private static final int bT = 6;
   private static final byte bU = 16;
   private static final byte bV = 16;
   private static final int bW = 8;
   private static final int bX = 8;
   private static final int bY = 5;
   private static final float bZ = 0.05F;
   static final Vector3f ca = SystemUtils.a(() -> {
      BaseBlockPosition baseblockposition = EnumDirection.d.q();
      return new Vector3f((float)baseblockposition.u(), (float)baseblockposition.v(), (float)baseblockposition.w());
   });
   private float cb;
   private float cc;
   @Nullable
   private BlockPosition cd;
   private int ce;
   private static final float cf = 1.0F;

   public EntityShulker(EntityTypes<? extends EntityShulker> entitytypes, World world) {
      super(entitytypes, world);
      this.bI = 5;
      this.bJ = new EntityShulker.d(this);
   }

   @Override
   protected void x() {
      this.bN.a(1, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F, 0.02F, true));
      this.bN.a(4, new EntityShulker.a());
      this.bN.a(7, new EntityShulker.f());
      this.bN.a(8, new PathfinderGoalRandomLookaround(this));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this, this.getClass()).a());
      this.bO.a(2, new EntityShulker.e(this));
      this.bO.a(3, new EntityShulker.c(this));
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.a;
   }

   @Override
   public SoundCategory cX() {
      return SoundCategory.f;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.uI;
   }

   @Override
   public void L() {
      if (!this.fX()) {
         super.L();
      }
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.uO;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return this.fX() ? SoundEffects.uQ : SoundEffects.uP;
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(b, EnumDirection.a);
      this.am.a(c, (byte)0);
      this.am.a(d, (byte)16);
   }

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y().a(GenericAttributes.a, 30.0);
   }

   @Override
   protected EntityAIBodyControl A() {
      return new EntityShulker.b(this);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.a(EnumDirection.a(nbttagcompound.f("AttachFace")));
      this.am.b(c, nbttagcompound.f("Peek"));
      if (nbttagcompound.b("Color", 99)) {
         this.am.b(d, nbttagcompound.f("Color"));
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("AttachFace", (byte)this.w().d());
      nbttagcompound.a("Peek", this.am.a(c));
      nbttagcompound.a("Color", this.am.a(d));
   }

   @Override
   public void l() {
      super.l();
      if (!this.H.B && !this.bL() && !this.a(this.dg(), this.w())) {
         this.fU();
      }

      if (this.fV()) {
         this.fW();
      }

      if (this.H.B) {
         if (this.ce > 0) {
            --this.ce;
         } else {
            this.cd = null;
         }
      }
   }

   private void fU() {
      EnumDirection enumdirection = this.g(this.dg());
      if (enumdirection != null) {
         this.a(enumdirection);
      } else {
         this.r();
      }
   }

   @Override
   protected AxisAlignedBB am() {
      float f = E(this.cc);
      EnumDirection enumdirection = this.w().g();
      float f1 = this.ae().k() / 2.0F;
      return a(enumdirection, f).d(this.dl() - (double)f1, this.dn(), this.dr() - (double)f1);
   }

   private static float E(float f) {
      return 0.5F - MathHelper.a((0.5F + f) * (float) Math.PI) * 0.5F;
   }

   private boolean fV() {
      this.cb = this.cc;
      float f = (float)this.fZ() * 0.01F;
      if (this.cc == f) {
         return false;
      } else {
         if (this.cc > f) {
            this.cc = MathHelper.a(this.cc - 0.05F, f, 1.0F);
         } else {
            this.cc = MathHelper.a(this.cc + 0.05F, 0.0F, f);
         }

         return true;
      }
   }

   private void fW() {
      this.an();
      float f = E(this.cc);
      float f1 = E(this.cb);
      EnumDirection enumdirection = this.w().g();
      float f2 = f - f1;
      if (f2 > 0.0F) {
         for(Entity entity : this.H
            .a(this, a(enumdirection, f1, f).d(this.dl() - 0.5, this.dn(), this.dr() - 0.5), IEntitySelector.f.and(entityx -> !entityx.v(this)))) {
            if (!(entity instanceof EntityShulker) && !entity.ae) {
               entity.a(
                  EnumMoveType.e,
                  new Vec3D((double)(f2 * (float)enumdirection.j()), (double)(f2 * (float)enumdirection.k()), (double)(f2 * (float)enumdirection.l()))
               );
            }
         }
      }
   }

   public static AxisAlignedBB a(EnumDirection enumdirection, float f) {
      return a(enumdirection, -1.0F, f);
   }

   public static AxisAlignedBB a(EnumDirection enumdirection, float f, float f1) {
      double d0 = (double)Math.max(f, f1);
      double d1 = (double)Math.min(f, f1);
      return new AxisAlignedBB(BlockPosition.b)
         .b((double)enumdirection.j() * d0, (double)enumdirection.k() * d0, (double)enumdirection.l() * d0)
         .a((double)(-enumdirection.j()) * (1.0 + d1), (double)(-enumdirection.k()) * (1.0 + d1), (double)(-enumdirection.l()) * (1.0 + d1));
   }

   @Override
   public double bu() {
      EntityTypes<?> entitytypes = this.cV().ae();
      return !(this.cV() instanceof EntityBoat) && entitytypes != EntityTypes.an ? super.bu() : 0.1875 - this.cV().bv();
   }

   @Override
   public boolean a(Entity entity, boolean flag) {
      if (this.H.k_()) {
         this.cd = null;
         this.ce = 0;
      }

      this.a(EnumDirection.a);
      return super.a(entity, flag);
   }

   @Override
   public void bz() {
      super.bz();
      if (this.H.B) {
         this.cd = this.dg();
      }

      this.aU = 0.0F;
      this.aT = 0.0F;
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
      this.f(0.0F);
      this.aV = this.dw();
      this.bi();
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   @Override
   public void a(EnumMoveType enummovetype, Vec3D vec3d) {
      if (enummovetype == EnumMoveType.d) {
         this.r();
      } else {
         super.a(enummovetype, vec3d);
      }
   }

   @Override
   public Vec3D dj() {
      return Vec3D.b;
   }

   @Override
   public void f(Vec3D vec3d) {
   }

   @Override
   public void e(double d0, double d1, double d2) {
      BlockPosition blockposition = this.dg();
      if (this.bL()) {
         super.e(d0, d1, d2);
      } else {
         super.e((double)MathHelper.a(d0) + 0.5, (double)MathHelper.a(d1 + 0.5), (double)MathHelper.a(d2) + 0.5);
      }

      if (this.ag != 0) {
         BlockPosition blockposition1 = this.dg();
         if (!blockposition1.equals(blockposition)) {
            this.am.b(c, (byte)0);
            this.at = true;
            if (this.H.B && !this.bL() && !blockposition1.equals(this.cd)) {
               this.cd = blockposition;
               this.ce = 6;
               this.ab = this.dl();
               this.ac = this.dn();
               this.ad = this.dr();
            }
         }
      }
   }

   @Nullable
   protected EnumDirection g(BlockPosition blockposition) {
      for(EnumDirection enumdirection : EnumDirection.values()) {
         if (this.a(blockposition, enumdirection)) {
            return enumdirection;
         }
      }

      return null;
   }

   boolean a(BlockPosition blockposition, EnumDirection enumdirection) {
      if (this.h(blockposition)) {
         return false;
      } else {
         EnumDirection enumdirection1 = enumdirection.g();
         if (!this.H.a(blockposition.a(enumdirection), this, enumdirection1)) {
            return false;
         } else {
            AxisAlignedBB axisalignedbb = a(enumdirection1, 1.0F).a(blockposition).h(1.0E-6);
            return this.H.a(this, axisalignedbb);
         }
      }
   }

   private boolean h(BlockPosition blockposition) {
      IBlockData iblockdata = this.H.a_(blockposition);
      if (iblockdata.h()) {
         return false;
      } else {
         boolean flag = iblockdata.a(Blocks.bP) && blockposition.equals(this.dg());
         return !flag;
      }
   }

   protected boolean r() {
      if (!this.fK() && this.bq()) {
         BlockPosition blockposition = this.dg();

         for(int i = 0; i < 5; ++i) {
            BlockPosition blockposition1 = blockposition.b(MathHelper.b(this.af, -8, 8), MathHelper.b(this.af, -8, 8), MathHelper.b(this.af, -8, 8));
            if (blockposition1.v() > this.H.v_()
               && this.H.w(blockposition1)
               && this.H.p_().a(blockposition1)
               && this.H.a(this, new AxisAlignedBB(blockposition1).h(1.0E-6))) {
               EnumDirection enumdirection = this.g(blockposition1);
               if (enumdirection != null) {
                  EntityTeleportEvent teleport = new EntityTeleportEvent(
                     this.getBukkitEntity(),
                     this.getBukkitEntity().getLocation(),
                     new Location(this.H.getWorld(), (double)blockposition1.u(), (double)blockposition1.v(), (double)blockposition1.w())
                  );
                  this.H.getCraftServer().getPluginManager().callEvent(teleport);
                  if (!teleport.isCancelled()) {
                     Location to = teleport.getTo();
                     blockposition1 = BlockPosition.a(to.getX(), to.getY(), to.getZ());
                     this.ac();
                     this.a(enumdirection);
                     this.a(SoundEffects.uT, 1.0F, 1.0F);
                     this.e((double)blockposition1.u() + 0.5, (double)blockposition1.v(), (double)blockposition1.w() + 0.5);
                     this.H.a(GameEvent.V, blockposition, GameEvent.a.a(this));
                     this.am.b(c, (byte)0);
                     this.i(null);
                     return true;
                  }

                  return false;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Override
   public void a(double d0, double d1, double d2, float f, float f1, int i, boolean flag) {
      this.bm = 0;
      this.e(d0, d1, d2);
      this.a(f, f1);
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.fX()) {
         Entity entity = damagesource.c();
         if (entity instanceof EntityArrow) {
            return false;
         }
      }

      if (!super.a(damagesource, f)) {
         return false;
      } else {
         if ((double)this.eo() < (double)this.eE() * 0.5 && this.af.a(4) == 0) {
            this.r();
         } else if (damagesource.a(DamageTypeTags.j)) {
            Entity entity = damagesource.c();
            if (entity != null && entity.ae() == EntityTypes.aH) {
               this.fY();
            }
         }

         return true;
      }
   }

   private boolean fX() {
      return this.fZ() == 0;
   }

   private void fY() {
      Vec3D vec3d = this.de();
      AxisAlignedBB axisalignedbb = this.cD();
      if (!this.fX() && this.r()) {
         int i = this.H.a(EntityTypes.aG, axisalignedbb.g(8.0), Entity::bq).size();
         float f = (float)(i - 1) / 5.0F;
         if (this.H.z.i() >= f) {
            EntityShulker entityshulker = EntityTypes.aG.a(this.H);
            if (entityshulker != null) {
               entityshulker.a(this.fS());
               entityshulker.d(vec3d);
               this.H.addFreshEntity(entityshulker, SpawnReason.BREEDING);
            }
         }
      }
   }

   @Override
   public boolean bs() {
      return this.bq();
   }

   public EnumDirection w() {
      return this.am.a(b);
   }

   public void a(EnumDirection enumdirection) {
      this.am.b(b, enumdirection);
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (b.equals(datawatcherobject)) {
         this.a(this.am());
      }

      super.a(datawatcherobject);
   }

   public int fZ() {
      return this.am.a(c);
   }

   public void b(int i) {
      if (!this.H.B) {
         this.a(GenericAttributes.i).d(bS);
         if (i == 0) {
            this.a(GenericAttributes.i).c(bS);
            this.a(SoundEffects.uN, 1.0F, 1.0F);
            this.a(GameEvent.j);
         } else {
            this.a(SoundEffects.uR, 1.0F, 1.0F);
            this.a(GameEvent.k);
         }
      }

      this.am.b(c, (byte)i);
   }

   public float C(float f) {
      return MathHelper.i(f, this.cb, this.cc);
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return 0.5F;
   }

   @Override
   public void a(PacketPlayOutSpawnEntity packetplayoutspawnentity) {
      super.a(packetplayoutspawnentity);
      this.aT = 0.0F;
      this.aU = 0.0F;
   }

   @Override
   public int V() {
      return 180;
   }

   @Override
   public int W() {
      return 180;
   }

   @Override
   public void g(Entity entity) {
   }

   @Override
   public float bB() {
      return 0.0F;
   }

   public Optional<Vec3D> D(float f) {
      if (this.cd != null && this.ce > 0) {
         double d0 = (double)((float)this.ce - f) / 6.0;
         d0 *= d0;
         BlockPosition blockposition = this.dg();
         double d1 = (double)(blockposition.u() - this.cd.u()) * d0;
         double d2 = (double)(blockposition.v() - this.cd.v()) * d0;
         double d3 = (double)(blockposition.w() - this.cd.w()) * d0;
         return Optional.of(new Vec3D(-d1, -d2, -d3));
      } else {
         return Optional.empty();
      }
   }

   public void a(Optional<EnumColor> optional) {
      this.am.b(d, optional.<Byte>map(enumcolor -> (byte)enumcolor.a()).orElse((byte)16));
   }

   public Optional<EnumColor> fS() {
      return Optional.ofNullable(this.fT());
   }

   @Nullable
   public EnumColor fT() {
      byte b0 = this.am.a(d);
      return b0 != 16 && b0 <= 15 ? EnumColor.a(b0) : null;
   }

   private class a extends PathfinderGoal {
      private int b;

      public a() {
         this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      }

      @Override
      public boolean a() {
         EntityLiving entityliving = EntityShulker.this.P_();
         return entityliving != null && entityliving.bq() ? EntityShulker.this.H.ah() != EnumDifficulty.a : false;
      }

      @Override
      public void c() {
         this.b = 20;
         EntityShulker.this.b(100);
      }

      @Override
      public void d() {
         EntityShulker.this.b(0);
      }

      @Override
      public boolean J_() {
         return true;
      }

      @Override
      public void e() {
         if (EntityShulker.this.H.ah() != EnumDifficulty.a) {
            --this.b;
            EntityLiving entityliving = EntityShulker.this.P_();
            if (entityliving != null) {
               EntityShulker.this.C().a(entityliving, 180.0F, 180.0F);
               double d0 = EntityShulker.this.f(entityliving);
               if (d0 < 400.0) {
                  if (this.b <= 0) {
                     this.b = 20 + EntityShulker.this.af.a(10) * 20 / 2;
                     EntityShulker.this.H.b(new EntityShulkerBullet(EntityShulker.this.H, EntityShulker.this, entityliving, EntityShulker.this.w().o()));
                     EntityShulker.this.a(SoundEffects.uS, 2.0F, (EntityShulker.this.af.i() - EntityShulker.this.af.i()) * 0.2F + 1.0F);
                  }
               } else {
                  EntityShulker.this.i(null);
               }

               super.e();
            }
         }
      }
   }

   private static class b extends EntityAIBodyControl {
      public b(EntityInsentient entityinsentient) {
         super(entityinsentient);
      }

      @Override
      public void a() {
      }
   }

   private static class c extends PathfinderGoalNearestAttackableTarget<EntityLiving> {
      public c(EntityShulker entityshulker) {
         super(entityshulker, EntityLiving.class, 10, true, false, entityliving -> entityliving instanceof IMonster);
      }

      @Override
      public boolean a() {
         return this.e.cb() == null ? false : super.a();
      }

      @Override
      protected AxisAlignedBB a(double d0) {
         EnumDirection enumdirection = ((EntityShulker)this.e).w();
         return enumdirection.o() == EnumDirection.EnumAxis.a
            ? this.e.cD().c(4.0, d0, d0)
            : (enumdirection.o() == EnumDirection.EnumAxis.c ? this.e.cD().c(d0, d0, 4.0) : this.e.cD().c(d0, 4.0, d0));
      }
   }

   private class d extends ControllerLook {
      public d(EntityInsentient entityinsentient) {
         super(entityinsentient);
      }

      @Override
      protected void b() {
      }

      @Override
      protected Optional<Float> i() {
         EnumDirection enumdirection = EntityShulker.this.w().g();
         Vector3f vector3f = enumdirection.b().transform(new Vector3f(EntityShulker.ca));
         BaseBlockPosition baseblockposition = enumdirection.q();
         Vector3f vector3f1 = new Vector3f((float)baseblockposition.u(), (float)baseblockposition.v(), (float)baseblockposition.w());
         vector3f1.cross(vector3f);
         double d0 = this.e - this.a.dl();
         double d1 = this.f - this.a.dp();
         double d2 = this.g - this.a.dr();
         Vector3f vector3f2 = new Vector3f((float)d0, (float)d1, (float)d2);
         float f = vector3f1.dot(vector3f2);
         float f1 = vector3f.dot(vector3f2);
         return Math.abs(f) <= 1.0E-5F && Math.abs(f1) <= 1.0E-5F
            ? Optional.empty()
            : Optional.of((float)(MathHelper.d((double)(-f), (double)f1) * 180.0F / (float)Math.PI));
      }

      @Override
      protected Optional<Float> h() {
         return Optional.of(0.0F);
      }
   }

   private class e extends PathfinderGoalNearestAttackableTarget<EntityHuman> {
      public e(EntityShulker entityshulker) {
         super(entityshulker, EntityHuman.class, true);
      }

      @Override
      public boolean a() {
         return EntityShulker.this.H.ah() == EnumDifficulty.a ? false : super.a();
      }

      @Override
      protected AxisAlignedBB a(double d0) {
         EnumDirection enumdirection = ((EntityShulker)this.e).w();
         return enumdirection.o() == EnumDirection.EnumAxis.a
            ? this.e.cD().c(4.0, d0, d0)
            : (enumdirection.o() == EnumDirection.EnumAxis.c ? this.e.cD().c(d0, d0, 4.0) : this.e.cD().c(d0, 4.0, d0));
      }
   }

   private class f extends PathfinderGoal {
      private int b;

      f() {
      }

      @Override
      public boolean a() {
         return EntityShulker.this.P_() == null
            && EntityShulker.this.af.a(b(40)) == 0
            && EntityShulker.this.a(EntityShulker.this.dg(), EntityShulker.this.w());
      }

      @Override
      public boolean b() {
         return EntityShulker.this.P_() == null && this.b > 0;
      }

      @Override
      public void c() {
         this.b = this.a(20 * (1 + EntityShulker.this.af.a(3)));
         EntityShulker.this.b(30);
      }

      @Override
      public void d() {
         if (EntityShulker.this.P_() == null) {
            EntityShulker.this.b(0);
         }
      }

      @Override
      public void e() {
         --this.b;
      }
   }
}
