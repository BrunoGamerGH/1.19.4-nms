package net.minecraft.world.entity.monster;

import com.google.common.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTransformEvent.TransformReason;

public class EntitySlime extends EntityInsentient implements IMonster {
   private static final DataWatcherObject<Integer> bS = DataWatcher.a(EntitySlime.class, DataWatcherRegistry.b);
   public static final int b = 1;
   public static final int c = 127;
   public float d;
   public float e;
   public float bR;
   private boolean bT;

   public EntitySlime(EntityTypes<? extends EntitySlime> entitytypes, World world) {
      super(entitytypes, world);
      this.cz();
      this.bK = new EntitySlime.ControllerMoveSlime(this);
   }

   @Override
   protected void x() {
      this.bN.a(1, new EntitySlime.PathfinderGoalSlimeRandomJump(this));
      this.bN.a(2, new EntitySlime.PathfinderGoalSlimeNearestPlayer(this));
      this.bN.a(3, new EntitySlime.PathfinderGoalSlimeRandomDirection(this));
      this.bN.a(5, new EntitySlime.PathfinderGoalSlimeIdle(this));
      this.bO
         .a(
            1,
            new PathfinderGoalNearestAttackableTarget<>(
               this, EntityHuman.class, 10, true, false, entityliving -> Math.abs(entityliving.dn() - this.dn()) <= 4.0
            )
         );
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityIronGolem.class, true));
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bS, 1);
   }

   @VisibleForTesting
   public void a(int i, boolean flag) {
      int j = MathHelper.a(i, 1, 127);
      this.am.b(bS, j);
      this.an();
      this.c_();
      this.a(GenericAttributes.a).a((double)(j * j));
      this.a(GenericAttributes.d).a((double)(0.2F + 0.1F * (float)j));
      this.a(GenericAttributes.f).a((double)j);
      if (flag) {
         this.c(this.eE());
      }

      this.bI = j;
   }

   public int fU() {
      return this.am.a(bS);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Size", this.fU() - 1);
      nbttagcompound.a("wasOnGround", this.bT);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      this.a(nbttagcompound.h("Size") + 1, false);
      super.a(nbttagcompound);
      this.bT = nbttagcompound.q("wasOnGround");
   }

   public boolean fV() {
      return this.fU() <= 1;
   }

   protected ParticleParam r() {
      return Particles.S;
   }

   @Override
   protected boolean R() {
      return this.fU() > 0;
   }

   @Override
   public void l() {
      this.e += (this.d - this.e) * 0.5F;
      this.bR = this.e;
      super.l();
      if (this.N && !this.bT) {
         int i = this.fU();

         for(int j = 0; j < i * 8; ++j) {
            float f = this.af.i() * (float) (Math.PI * 2);
            float f1 = this.af.i() * 0.5F + 0.5F;
            float f2 = MathHelper.a(f) * (float)i * 0.5F * f1;
            float f3 = MathHelper.b(f) * (float)i * 0.5F * f1;
            this.H.a(this.r(), this.dl() + (double)f2, this.dn(), this.dr() + (double)f3, 0.0, 0.0, 0.0);
         }

         this.a(this.fS(), this.eN(), ((this.af.i() - this.af.i()) * 0.2F + 1.0F) / 0.8F);
         this.d = -0.5F;
      } else if (!this.N && this.bT) {
         this.d = 1.0F;
      }

      this.bT = this.N;
      this.fP();
   }

   @Override
   protected void fP() {
      this.d *= 0.6F;
   }

   protected int w() {
      return this.af.a(20) + 10;
   }

   @Override
   public void c_() {
      double d0 = this.dl();
      double d1 = this.dn();
      double d2 = this.dr();
      super.c_();
      this.e(d0, d1, d2);
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (bS.equals(datawatcherobject)) {
         this.c_();
         this.f(this.aV);
         this.aT = this.aV;
         if (this.aT() && this.af.a(20) == 0) {
            this.bb();
         }
      }

      super.a(datawatcherobject);
   }

   @Override
   public EntityTypes<? extends EntitySlime> ae() {
      return super.ae();
   }

   @Override
   public void a(Entity.RemovalReason entity_removalreason) {
      int i = this.fU();
      if (!this.H.B && i > 1 && this.ep()) {
         IChatBaseComponent ichatbasecomponent = this.ab();
         boolean flag = this.fK();
         float f = (float)i / 4.0F;
         int j = i / 2;
         int k = 2 + this.af.a(3);
         SlimeSplitEvent event = new SlimeSplitEvent((Slime)this.getBukkitEntity(), k);
         this.H.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled() || event.getCount() <= 0) {
            super.a(entity_removalreason);
            return;
         }

         k = event.getCount();
         ArrayList slimes = new ArrayList(j);

         for(int living = 0; living < k; ++living) {
            float f1 = ((float)(living % 2) - 0.5F) * f;
            float f2 = ((float)(living / 2) - 0.5F) * f;
            EntitySlime entityslime = this.ae().a(this.H);
            if (entityslime != null) {
               if (this.fB()) {
                  entityslime.fz();
               }

               entityslime.b(ichatbasecomponent);
               entityslime.t(flag);
               entityslime.m(this.cm());
               entityslime.a(j, true);
               entityslime.b(this.dl() + (double)f1, this.dn() + 0.5, this.dr() + (double)f2, this.af.i() * 360.0F, 0.0F);
               slimes.add(entityslime);
            }
         }

         if (CraftEventFactory.callEntityTransformEvent(this, slimes, TransformReason.SPLIT).isCancelled()) {
            super.a(entity_removalreason);
            return;
         }

         for(EntityLiving living : slimes) {
            this.H.addFreshEntity(living, SpawnReason.SLIME_SPLIT);
         }
      }

      super.a(entity_removalreason);
   }

   @Override
   public void g(Entity entity) {
      super.g(entity);
      if (entity instanceof EntityIronGolem && this.fQ()) {
         this.m((EntityLiving)entity);
      }
   }

   @Override
   public void b_(EntityHuman entityhuman) {
      if (this.fQ()) {
         this.m(entityhuman);
      }
   }

   protected void m(EntityLiving entityliving) {
      if (this.bq()) {
         int i = this.fU();
         if (this.f((Entity)entityliving) < 0.6 * (double)i * 0.6 * (double)i
            && this.B(entityliving)
            && entityliving.a(this.dG().b((EntityLiving)this), this.fR())) {
            this.a(SoundEffects.vm, 1.0F, (this.af.i() - this.af.i()) * 0.2F + 1.0F);
            this.a(this, entityliving);
         }
      }
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return 0.625F * entitysize.b;
   }

   protected boolean fQ() {
      return !this.fV() && this.cU();
   }

   protected float fR() {
      return (float)this.b(GenericAttributes.f);
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return this.fV() ? SoundEffects.wd : SoundEffects.vo;
   }

   @Override
   protected SoundEffect x_() {
      return this.fV() ? SoundEffects.wc : SoundEffects.vn;
   }

   protected SoundEffect fS() {
      return this.fV() ? SoundEffects.wf : SoundEffects.vq;
   }

   public static boolean c(
      EntityTypes<EntitySlime> entitytypes, GeneratorAccess generatoraccess, EnumMobSpawn enummobspawn, BlockPosition blockposition, RandomSource randomsource
   ) {
      if (generatoraccess.ah() != EnumDifficulty.a) {
         if (generatoraccess.v(blockposition).a(BiomeTags.ao)
            && blockposition.v() > 50
            && blockposition.v() < 70
            && randomsource.i() < 0.5F
            && randomsource.i() < generatoraccess.am()
            && generatoraccess.C(blockposition) <= randomsource.a(8)) {
            return a(entitytypes, generatoraccess, enummobspawn, blockposition, randomsource);
         }

         if (!(generatoraccess instanceof GeneratorAccessSeed)) {
            return false;
         }

         ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(blockposition);
         boolean flag = SeededRandom.a(
                  chunkcoordintpair.e,
                  chunkcoordintpair.f,
                  ((GeneratorAccessSeed)generatoraccess).A(),
                  (long)generatoraccess.getMinecraftWorld().spigotConfig.slimeSeed
               )
               .a(10)
            == 0;
         if (randomsource.a(10) == 0 && flag && blockposition.v() < 40) {
            return a(entitytypes, generatoraccess, enummobspawn, blockposition, randomsource);
         }
      }

      return false;
   }

   @Override
   protected float eN() {
      return 0.4F * (float)this.fU();
   }

   @Override
   public int V() {
      return 0;
   }

   protected boolean fW() {
      return this.fU() > 0;
   }

   @Override
   protected void eS() {
      Vec3D vec3d = this.dj();
      this.o(vec3d.c, (double)this.eQ(), vec3d.e);
      this.at = true;
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
      int i = randomsource.a(3);
      if (i < 2 && randomsource.i() < 0.5F * difficultydamagescaler.d()) {
         ++i;
      }

      int j = 1 << i;
      this.a(j, true);
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   float q() {
      float f = this.fV() ? 1.4F : 0.8F;
      return ((this.af.i() - this.af.i()) * 0.2F + 1.0F) * f;
   }

   protected SoundEffect fT() {
      return this.fV() ? SoundEffects.we : SoundEffects.vp;
   }

   @Override
   public EntitySize a(EntityPose entitypose) {
      return super.a(entitypose).a(0.255F * (float)this.fU());
   }

   private static class ControllerMoveSlime extends ControllerMove {
      private float l;
      private int m;
      private final EntitySlime n;
      private boolean o;

      public ControllerMoveSlime(EntitySlime entityslime) {
         super(entityslime);
         this.n = entityslime;
         this.l = 180.0F * entityslime.dw() / (float) Math.PI;
      }

      public void a(float f, boolean flag) {
         this.l = f;
         this.o = flag;
      }

      public void a(double d0) {
         this.h = d0;
         this.k = ControllerMove.Operation.b;
      }

      @Override
      public void a() {
         this.d.f(this.a(this.d.dw(), this.l, 90.0F));
         this.d.aV = this.d.dw();
         this.d.aT = this.d.dw();
         if (this.k != ControllerMove.Operation.b) {
            this.d.y(0.0F);
         } else {
            this.k = ControllerMove.Operation.a;
            if (this.d.ax()) {
               this.d.h((float)(this.h * this.d.b(GenericAttributes.d)));
               if (this.m-- <= 0) {
                  this.m = this.n.w();
                  if (this.o) {
                     this.m /= 3;
                  }

                  this.n.E().a();
                  if (this.n.fW()) {
                     this.n.a(this.n.fT(), this.n.eN(), this.n.q());
                  }
               } else {
                  this.n.bj = 0.0F;
                  this.n.bl = 0.0F;
                  this.d.h(0.0F);
               }
            } else {
               this.d.h((float)(this.h * this.d.b(GenericAttributes.d)));
            }
         }
      }
   }

   private static class PathfinderGoalSlimeIdle extends PathfinderGoal {
      private final EntitySlime a;

      public PathfinderGoalSlimeIdle(EntitySlime entityslime) {
         this.a = entityslime;
         this.a(EnumSet.of(PathfinderGoal.Type.c, PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         return !this.a.bL();
      }

      @Override
      public void e() {
         ControllerMove controllermove = this.a.D();
         if (controllermove instanceof EntitySlime.ControllerMoveSlime entityslime_controllermoveslime) {
            entityslime_controllermoveslime.a(1.0);
         }
      }
   }

   private static class PathfinderGoalSlimeNearestPlayer extends PathfinderGoal {
      private final EntitySlime a;
      private int b;

      public PathfinderGoalSlimeNearestPlayer(EntitySlime entityslime) {
         this.a = entityslime;
         this.a(EnumSet.of(PathfinderGoal.Type.b));
      }

      @Override
      public boolean a() {
         EntityLiving entityliving = this.a.P_();
         return entityliving == null ? false : (!this.a.c(entityliving) ? false : this.a.D() instanceof EntitySlime.ControllerMoveSlime);
      }

      @Override
      public void c() {
         this.b = b(300);
         super.c();
      }

      @Override
      public boolean b() {
         EntityLiving entityliving = this.a.P_();
         return entityliving == null ? false : (!this.a.c(entityliving) ? false : --this.b > 0);
      }

      @Override
      public boolean J_() {
         return true;
      }

      @Override
      public void e() {
         EntityLiving entityliving = this.a.P_();
         if (entityliving != null) {
            this.a.a(entityliving, 10.0F, 10.0F);
         }

         ControllerMove controllermove = this.a.D();
         if (controllermove instanceof EntitySlime.ControllerMoveSlime entityslime_controllermoveslime) {
            entityslime_controllermoveslime.a(this.a.dw(), this.a.fQ());
         }
      }
   }

   private static class PathfinderGoalSlimeRandomDirection extends PathfinderGoal {
      private final EntitySlime a;
      private float b;
      private int c;

      public PathfinderGoalSlimeRandomDirection(EntitySlime entityslime) {
         this.a = entityslime;
         this.a(EnumSet.of(PathfinderGoal.Type.b));
      }

      @Override
      public boolean a() {
         return this.a.P_() == null
            && (this.a.N || this.a.aT() || this.a.bg() || this.a.a(MobEffects.y))
            && this.a.D() instanceof EntitySlime.ControllerMoveSlime;
      }

      @Override
      public void e() {
         if (--this.c <= 0) {
            this.c = this.a(40 + this.a.dZ().a(60));
            this.b = (float)this.a.dZ().a(360);
         }

         ControllerMove controllermove = this.a.D();
         if (controllermove instanceof EntitySlime.ControllerMoveSlime entityslime_controllermoveslime) {
            entityslime_controllermoveslime.a(this.b, false);
         }
      }
   }

   private static class PathfinderGoalSlimeRandomJump extends PathfinderGoal {
      private final EntitySlime a;

      public PathfinderGoalSlimeRandomJump(EntitySlime entityslime) {
         this.a = entityslime;
         this.a(EnumSet.of(PathfinderGoal.Type.c, PathfinderGoal.Type.a));
         entityslime.G().a(true);
      }

      @Override
      public boolean a() {
         return (this.a.aT() || this.a.bg()) && this.a.D() instanceof EntitySlime.ControllerMoveSlime;
      }

      @Override
      public boolean J_() {
         return true;
      }

      @Override
      public void e() {
         if (this.a.dZ().i() < 0.8F) {
            this.a.E().a();
         }

         ControllerMove controllermove = this.a.D();
         if (controllermove instanceof EntitySlime.ControllerMoveSlime entityslime_controllermoveslime) {
            entityslime_controllermoveslime.a(1.2);
         }
      }
   }
}
