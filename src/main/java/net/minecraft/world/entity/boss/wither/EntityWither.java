package net.minecraft.world.entity.boss.wither;

import com.google.common.collect.ImmutableList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutWorldEvent;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.BossBattleServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.BossBattle;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerMoveFlying;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalArrowAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomFly;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.navigation.NavigationFlying;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.IRangedEntity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.EntityWitherSkull;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityWither extends EntityMonster implements PowerableMob, IRangedEntity {
   private static final DataWatcherObject<Integer> b = DataWatcher.a(EntityWither.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Integer> c = DataWatcher.a(EntityWither.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Integer> d = DataWatcher.a(EntityWither.class, DataWatcherRegistry.b);
   private static final List<DataWatcherObject<Integer>> e = ImmutableList.of(b, c, d);
   private static final DataWatcherObject<Integer> bS = DataWatcher.a(EntityWither.class, DataWatcherRegistry.b);
   private static final int bT = 220;
   private final float[] bU = new float[2];
   private final float[] bV = new float[2];
   private final float[] bW = new float[2];
   private final float[] bX = new float[2];
   private final int[] bY = new int[2];
   private final int[] bZ = new int[2];
   private int ca;
   public final BossBattleServer cb = (BossBattleServer)new BossBattleServer(this.G_(), BossBattle.BarColor.f, BossBattle.BarStyle.a).a(true);
   private static final Predicate<EntityLiving> cc = entityliving -> entityliving.eJ() != EnumMonsterType.b && entityliving.fq();
   private static final PathfinderTargetCondition cd = PathfinderTargetCondition.a().a(20.0).a(cc);

   public EntityWither(EntityTypes<? extends EntityWither> entitytypes, World world) {
      super(entitytypes, world);
      this.bK = new ControllerMoveFlying(this, 10, false);
      this.c(this.eE());
      this.bI = 50;
   }

   @Override
   protected NavigationAbstract a(World world) {
      NavigationFlying navigationflying = new NavigationFlying(this, world);
      navigationflying.b(false);
      navigationflying.a(true);
      navigationflying.c(true);
      return navigationflying;
   }

   @Override
   protected void x() {
      this.bN.a(0, new EntityWither.a());
      this.bN.a(2, new PathfinderGoalArrowAttack(this, 1.0, 40, 20.0F));
      this.bN.a(5, new PathfinderGoalRandomFly(this, 1.0));
      this.bN.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.bN.a(7, new PathfinderGoalRandomLookaround(this));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this));
      this.bO.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityLiving.class, 0, false, false, cc));
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(b, 0);
      this.am.a(c, 0);
      this.am.a(d, 0);
      this.am.a(bS, 0);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Invul", this.w());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.r(nbttagcompound.h("Invul"));
      if (this.aa()) {
         this.cb.a(this.G_());
      }
   }

   @Override
   public void b(@Nullable IChatBaseComponent ichatbasecomponent) {
      super.b(ichatbasecomponent);
      this.cb.a(this.G_());
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.zE;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.zH;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.zG;
   }

   @Override
   public void b_() {
      Vec3D vec3d = this.dj().d(1.0, 0.6, 1.0);
      if (!this.H.B && this.s(0) > 0) {
         Entity entity = this.H.a(this.s(0));
         if (entity != null) {
            double d0 = vec3d.d;
            if (this.dn() < entity.dn() || !this.a() && this.dn() < entity.dn() + 5.0) {
               d0 = Math.max(0.0, d0);
               d0 += 0.3 - d0 * 0.6F;
            }

            vec3d = new Vec3D(vec3d.c, d0, vec3d.e);
            Vec3D vec3d1 = new Vec3D(entity.dl() - this.dl(), 0.0, entity.dr() - this.dr());
            if (vec3d1.i() > 9.0) {
               Vec3D vec3d2 = vec3d1.d();
               vec3d = vec3d.b(vec3d2.c * 0.3 - vec3d.c * 0.6, 0.0, vec3d2.e * 0.3 - vec3d.e * 0.6);
            }
         }
      }

      this.f(vec3d);
      if (vec3d.i() > 0.05) {
         this.f((float)MathHelper.d(vec3d.e, vec3d.c) * (180.0F / (float)Math.PI) - 90.0F);
      }

      super.b_();

      for(int i = 0; i < 2; ++i) {
         this.bX[i] = this.bV[i];
         this.bW[i] = this.bU[i];
      }

      for(int var22 = 0; var22 < 2; ++var22) {
         int j = this.s(var22 + 1);
         Entity entity1 = null;
         if (j > 0) {
            entity1 = this.H.a(j);
         }

         if (entity1 != null) {
            double d1 = this.t(var22 + 1);
            double d2 = this.u(var22 + 1);
            double d3 = this.v(var22 + 1);
            double d4 = entity1.dl() - d1;
            double d5 = entity1.dp() - d2;
            double d6 = entity1.dr() - d3;
            double d7 = Math.sqrt(d4 * d4 + d6 * d6);
            float f = (float)(MathHelper.d(d6, d4) * 180.0F / (float)Math.PI) - 90.0F;
            float f1 = (float)(-(MathHelper.d(d5, d7) * 180.0F / (float)Math.PI));
            this.bU[var22] = this.a(this.bU[var22], f1, 40.0F);
            this.bV[var22] = this.a(this.bV[var22], f, 10.0F);
         } else {
            this.bV[var22] = this.a(this.bV[var22], this.aT, 10.0F);
         }
      }

      boolean flag = this.a();

      for(int j = 0; j < 3; ++j) {
         double d8 = this.t(j);
         double d9 = this.u(j);
         double d10 = this.v(j);
         this.H.a(Particles.ab, d8 + this.af.k() * 0.3F, d9 + this.af.k() * 0.3F, d10 + this.af.k() * 0.3F, 0.0, 0.0, 0.0);
         if (flag && this.H.z.a(4) == 0) {
            this.H.a(Particles.v, d8 + this.af.k() * 0.3F, d9 + this.af.k() * 0.3F, d10 + this.af.k() * 0.3F, 0.7F, 0.7F, 0.5);
         }
      }

      if (this.w() > 0) {
         for(int var26 = 0; var26 < 3; ++var26) {
            this.H.a(Particles.v, this.dl() + this.af.k(), this.dn() + (double)(this.af.i() * 3.3F), this.dr() + this.af.k(), 0.7F, 0.7F, 0.9F);
         }
      }
   }

   @Override
   protected void U() {
      if (this.w() > 0) {
         int i = this.w() - 1;
         this.cb.a(1.0F - (float)i / 220.0F);
         if (i <= 0) {
            ExplosionPrimeEvent event = new ExplosionPrimeEvent(this.getBukkitEntity(), 7.0F, false);
            this.H.getCraftServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
               this.H.a(this, this.dl(), this.dp(), this.dr(), event.getRadius(), event.getFire(), World.a.c);
            }

            if (!this.aO()) {
               int viewDistance = ((WorldServer)this.H).getCraftServer().getViewDistance() * 16;

               for(EntityPlayer player : MinecraftServer.getServer().ac().k) {
                  double deltaX = this.dl() - player.dl();
                  double deltaZ = this.dr() - player.dr();
                  double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;
                  if (this.H.spigotConfig.witherSpawnSoundRadius <= 0
                     || !(distanceSquared > (double)(this.H.spigotConfig.witherSpawnSoundRadius * this.H.spigotConfig.witherSpawnSoundRadius))) {
                     if (distanceSquared > (double)(viewDistance * viewDistance)) {
                        double deltaLength = Math.sqrt(distanceSquared);
                        double relativeX = player.dl() + deltaX / deltaLength * (double)viewDistance;
                        double relativeZ = player.dr() + deltaZ / deltaLength * (double)viewDistance;
                        player.b.a(new PacketPlayOutWorldEvent(1023, new BlockPosition((int)relativeX, (int)this.dn(), (int)relativeZ), 0, true));
                     } else {
                        player.b.a(new PacketPlayOutWorldEvent(1023, this.dg(), 0, true));
                     }
                  }
               }
            }
         }

         this.r(i);
         if (this.ag % 10 == 0) {
            this.heal(10.0F, RegainReason.WITHER_SPAWN);
         }
      } else {
         super.U();

         for(int i = 1; i < 3; ++i) {
            if (this.ag >= this.bY[i - 1]) {
               this.bY[i - 1] = this.ag + 10 + this.af.a(10);
               if (this.H.ah() == EnumDifficulty.c || this.H.ah() == EnumDifficulty.d) {
                  int k = i - 1;
                  int l = this.bZ[i - 1];
                  this.bZ[k] = this.bZ[i - 1] + 1;
                  if (l > 15) {
                     float f = 10.0F;
                     float f1 = 5.0F;
                     double d0 = MathHelper.a(this.af, this.dl() - 10.0, this.dl() + 10.0);
                     double d1 = MathHelper.a(this.af, this.dn() - 5.0, this.dn() + 5.0);
                     double d2 = MathHelper.a(this.af, this.dr() - 10.0, this.dr() + 10.0);
                     this.a(i + 1, d0, d1, d2, true);
                     this.bZ[i - 1] = 0;
                  }
               }

               int j = this.s(i);
               if (j > 0) {
                  EntityLiving entityliving = (EntityLiving)this.H.a(j);
                  if (entityliving != null && this.c(entityliving) && this.f((Entity)entityliving) <= 900.0 && this.B(entityliving)) {
                     this.a(i + 1, entityliving);
                     this.bY[i - 1] = this.ag + 40 + this.af.a(20);
                     this.bZ[i - 1] = 0;
                  } else {
                     this.a(i, 0);
                  }
               } else {
                  List<EntityLiving> list = this.H.a(EntityLiving.class, cd, this, this.cD().c(20.0, 8.0, 20.0));
                  if (!list.isEmpty()) {
                     EntityLiving entityliving1 = list.get(this.af.a(list.size()));
                     if (!CraftEventFactory.callEntityTargetLivingEvent(this, entityliving1, TargetReason.CLOSEST_ENTITY).isCancelled()) {
                        this.a(i, entityliving1.af());
                     }
                  }
               }
            }
         }

         if (this.P_() != null) {
            this.a(0, this.P_().af());
         } else {
            this.a(0, 0);
         }

         if (this.ca > 0) {
            --this.ca;
            if (this.ca == 0 && this.H.W().b(GameRules.c)) {
               int var19 = MathHelper.a(this.dn());
               int j = MathHelper.a(this.dl());
               int i1 = MathHelper.a(this.dr());
               boolean flag = false;

               for(int j1 = -1; j1 <= 1; ++j1) {
                  for(int k1 = -1; k1 <= 1; ++k1) {
                     for(int l1 = 0; l1 <= 3; ++l1) {
                        int i2 = j + j1;
                        int j2 = var19 + l1;
                        int k2 = i1 + k1;
                        BlockPosition blockposition = new BlockPosition(i2, j2, k2);
                        IBlockData iblockdata = this.H.a_(blockposition);
                        if (c(iblockdata) && !CraftEventFactory.callEntityChangeBlockEvent(this, blockposition, Blocks.a.o()).isCancelled()) {
                           flag = this.H.a(blockposition, true, this) || flag;
                        }
                     }
                  }
               }

               if (flag) {
                  this.H.a(null, 1022, this.dg(), 0);
               }
            }
         }

         if (this.ag % 20 == 0) {
            this.heal(1.0F, RegainReason.REGEN);
         }

         this.cb.a(this.eo() / this.eE());
      }
   }

   public static boolean c(IBlockData iblockdata) {
      return !iblockdata.h() && !iblockdata.a(TagsBlock.aB);
   }

   @Override
   public void q() {
      this.r(220);
      this.cb.a(0.0F);
      this.c(this.eE() / 3.0F);
   }

   @Override
   public void a(IBlockData iblockdata, Vec3D vec3d) {
   }

   @Override
   public void c(EntityPlayer entityplayer) {
      super.c(entityplayer);
      this.cb.a(entityplayer);
   }

   @Override
   public void d(EntityPlayer entityplayer) {
      super.d(entityplayer);
      this.cb.b(entityplayer);
   }

   private double t(int i) {
      if (i <= 0) {
         return this.dl();
      } else {
         float f = (this.aT + (float)(180 * (i - 1))) * (float) (Math.PI / 180.0);
         float f1 = MathHelper.b(f);
         return this.dl() + (double)f1 * 1.3;
      }
   }

   private double u(int i) {
      return i <= 0 ? this.dn() + 3.0 : this.dn() + 2.2;
   }

   private double v(int i) {
      if (i <= 0) {
         return this.dr();
      } else {
         float f = (this.aT + (float)(180 * (i - 1))) * (float) (Math.PI / 180.0);
         float f1 = MathHelper.a(f);
         return this.dr() + (double)f1 * 1.3;
      }
   }

   private float a(float f, float f1, float f2) {
      float f3 = MathHelper.g(f1 - f);
      if (f3 > f2) {
         f3 = f2;
      }

      if (f3 < -f2) {
         f3 = -f2;
      }

      return f + f3;
   }

   private void a(int i, EntityLiving entityliving) {
      this.a(i, entityliving.dl(), entityliving.dn() + (double)entityliving.cE() * 0.5, entityliving.dr(), i == 0 && this.af.i() < 0.001F);
   }

   private void a(int i, double d0, double d1, double d2, boolean flag) {
      if (!this.aO()) {
         this.H.a(null, 1024, this.dg(), 0);
      }

      double d3 = this.t(i);
      double d4 = this.u(i);
      double d5 = this.v(i);
      double d6 = d0 - d3;
      double d7 = d1 - d4;
      double d8 = d2 - d5;
      EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.H, this, d6, d7, d8);
      entitywitherskull.b(this);
      if (flag) {
         entitywitherskull.a(true);
      }

      entitywitherskull.p(d3, d4, d5);
      this.H.b(entitywitherskull);
   }

   @Override
   public void a(EntityLiving entityliving, float f) {
      this.a(0, entityliving);
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else if (damagesource.a(DamageTypeTags.t) || damagesource.d() instanceof EntityWither) {
         return false;
      } else if (this.w() > 0 && !damagesource.a(DamageTypeTags.d)) {
         return false;
      } else {
         if (this.a()) {
            Entity entity = damagesource.c();
            if (entity instanceof EntityArrow) {
               return false;
            }
         }

         Entity entity = damagesource.d();
         if (entity != null && !(entity instanceof EntityHuman) && entity instanceof EntityLiving && ((EntityLiving)entity).eJ() == this.eJ()) {
            return false;
         } else {
            if (this.ca <= 0) {
               this.ca = 20;
            }

            for(int i = 0; i < this.bZ.length; ++i) {
               this.bZ[i] += 3;
            }

            return super.a(damagesource, f);
         }
      }
   }

   @Override
   protected void a(DamageSource damagesource, int i, boolean flag) {
      super.a(damagesource, i, flag);
      EntityItem entityitem = this.a(Items.tu);
      if (entityitem != null) {
         entityitem.s();
      }
   }

   @Override
   public void ds() {
      if (this.H.ah() == EnumDifficulty.a && this.R()) {
         this.ai();
      } else {
         this.ba = 0;
      }
   }

   @Override
   public boolean b(MobEffect mobeffect, @Nullable Entity entity) {
      return false;
   }

   public static AttributeProvider.Builder r() {
      return EntityMonster.fY()
         .a(GenericAttributes.a, 300.0)
         .a(GenericAttributes.d, 0.6F)
         .a(GenericAttributes.e, 0.6F)
         .a(GenericAttributes.b, 40.0)
         .a(GenericAttributes.i, 4.0);
   }

   public float b(int i) {
      return this.bV[i];
   }

   public float c(int i) {
      return this.bU[i];
   }

   public int w() {
      return this.am.a(bS);
   }

   public void r(int i) {
      this.am.b(bS, i);
   }

   public int s(int i) {
      return this.am.a(e.get(i));
   }

   public void a(int i, int j) {
      this.am.b(e.get(i), j);
   }

   @Override
   public boolean a() {
      return this.eo() <= this.eE() / 2.0F;
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.b;
   }

   @Override
   protected boolean l(Entity entity) {
      return false;
   }

   @Override
   public boolean co() {
      return false;
   }

   @Override
   public boolean c(MobEffect mobeffect) {
      return mobeffect.c() == MobEffects.t ? false : super.c(mobeffect);
   }

   private class a extends PathfinderGoal {
      public a() {
         this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.c, PathfinderGoal.Type.b));
      }

      @Override
      public boolean a() {
         return EntityWither.this.w() > 0;
      }
   }
}
