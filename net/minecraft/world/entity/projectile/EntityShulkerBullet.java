package net.minecraft.world.entity.projectile;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntityShulkerBullet extends IProjectile {
   private static final double b = 0.15;
   @Nullable
   private Entity c;
   @Nullable
   private EnumDirection d;
   private int e;
   private double f;
   private double g;
   private double h;
   @Nullable
   private UUID i;

   public EntityShulkerBullet(EntityTypes<? extends EntityShulkerBullet> entitytypes, World world) {
      super(entitytypes, world);
      this.ae = true;
   }

   public EntityShulkerBullet(World world, EntityLiving entityliving, Entity entity, EnumDirection.EnumAxis enumdirection_enumaxis) {
      this(EntityTypes.aH, world);
      this.b(entityliving);
      BlockPosition blockposition = entityliving.dg();
      double d0 = (double)blockposition.u() + 0.5;
      double d1 = (double)blockposition.v() + 0.5;
      double d2 = (double)blockposition.w() + 0.5;
      this.b(d0, d1, d2, this.dw(), this.dy());
      this.c = entity;
      this.d = EnumDirection.b;
      this.a(enumdirection_enumaxis);
      this.projectileSource = (LivingEntity)entityliving.getBukkitEntity();
   }

   public Entity getTarget() {
      return this.c;
   }

   public void setTarget(Entity e) {
      this.c = e;
      this.d = EnumDirection.b;
      this.a(EnumDirection.EnumAxis.a);
   }

   @Override
   public SoundCategory cX() {
      return SoundCategory.f;
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (this.c != null) {
         nbttagcompound.a("Target", this.c.cs());
      }

      if (this.d != null) {
         nbttagcompound.a("Dir", this.d.d());
      }

      nbttagcompound.a("Steps", this.e);
      nbttagcompound.a("TXD", this.f);
      nbttagcompound.a("TYD", this.g);
      nbttagcompound.a("TZD", this.h);
   }

   @Override
   protected void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.e = nbttagcompound.h("Steps");
      this.f = nbttagcompound.k("TXD");
      this.g = nbttagcompound.k("TYD");
      this.h = nbttagcompound.k("TZD");
      if (nbttagcompound.b("Dir", 99)) {
         this.d = EnumDirection.a(nbttagcompound.h("Dir"));
      }

      if (nbttagcompound.b("Target")) {
         this.i = nbttagcompound.a("Target");
      }
   }

   @Override
   protected void a_() {
   }

   @Nullable
   private EnumDirection i() {
      return this.d;
   }

   private void a(@Nullable EnumDirection enumdirection) {
      this.d = enumdirection;
   }

   private void a(@Nullable EnumDirection.EnumAxis enumdirection_enumaxis) {
      double d0 = 0.5;
      BlockPosition blockposition;
      if (this.c == null) {
         blockposition = this.dg().d();
      } else {
         d0 = (double)this.c.dd() * 0.5;
         blockposition = BlockPosition.a(this.c.dl(), this.c.dn() + d0, this.c.dr());
      }

      double d1 = (double)blockposition.u() + 0.5;
      double d2 = (double)blockposition.v() + d0;
      double d3 = (double)blockposition.w() + 0.5;
      EnumDirection enumdirection = null;
      if (!blockposition.a(this.de(), 2.0)) {
         BlockPosition blockposition1 = this.dg();
         List<EnumDirection> list = Lists.newArrayList();
         if (enumdirection_enumaxis != EnumDirection.EnumAxis.a) {
            if (blockposition1.u() < blockposition.u() && this.H.w(blockposition1.h())) {
               list.add(EnumDirection.f);
            } else if (blockposition1.u() > blockposition.u() && this.H.w(blockposition1.g())) {
               list.add(EnumDirection.e);
            }
         }

         if (enumdirection_enumaxis != EnumDirection.EnumAxis.b) {
            if (blockposition1.v() < blockposition.v() && this.H.w(blockposition1.c())) {
               list.add(EnumDirection.b);
            } else if (blockposition1.v() > blockposition.v() && this.H.w(blockposition1.d())) {
               list.add(EnumDirection.a);
            }
         }

         if (enumdirection_enumaxis != EnumDirection.EnumAxis.c) {
            if (blockposition1.w() < blockposition.w() && this.H.w(blockposition1.f())) {
               list.add(EnumDirection.d);
            } else if (blockposition1.w() > blockposition.w() && this.H.w(blockposition1.e())) {
               list.add(EnumDirection.c);
            }
         }

         enumdirection = EnumDirection.b(this.af);
         if (list.isEmpty()) {
            for(int i = 5; !this.H.w(blockposition1.a(enumdirection)) && i > 0; --i) {
               enumdirection = EnumDirection.b(this.af);
            }
         } else {
            enumdirection = list.get(this.af.a(list.size()));
         }

         d1 = this.dl() + (double)enumdirection.j();
         d2 = this.dn() + (double)enumdirection.k();
         d3 = this.dr() + (double)enumdirection.l();
      }

      this.a(enumdirection);
      double d4 = d1 - this.dl();
      double d5 = d2 - this.dn();
      double d6 = d3 - this.dr();
      double d7 = Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
      if (d7 == 0.0) {
         this.f = 0.0;
         this.g = 0.0;
         this.h = 0.0;
      } else {
         this.f = d4 / d7 * 0.15;
         this.g = d5 / d7 * 0.15;
         this.h = d6 / d7 * 0.15;
      }

      this.at = true;
      this.e = 10 + this.af.a(5) * 10;
   }

   @Override
   public void ds() {
      if (this.H.ah() == EnumDifficulty.a) {
         this.ai();
      }
   }

   @Override
   public void l() {
      super.l();
      if (!this.H.B) {
         if (this.c == null && this.i != null) {
            this.c = ((WorldServer)this.H).a(this.i);
            if (this.c == null) {
               this.i = null;
            }
         }

         if (this.c == null || !this.c.bq() || this.c instanceof EntityHuman && this.c.F_()) {
            if (!this.aP()) {
               this.f(this.dj().b(0.0, -0.04, 0.0));
            }
         } else {
            this.f = MathHelper.a(this.f * 1.025, -1.0, 1.0);
            this.g = MathHelper.a(this.g * 1.025, -1.0, 1.0);
            this.h = MathHelper.a(this.h * 1.025, -1.0, 1.0);
            Vec3D vec3d = this.dj();
            this.f(vec3d.b((this.f - vec3d.c) * 0.2, (this.g - vec3d.d) * 0.2, (this.h - vec3d.e) * 0.2));
         }

         MovingObjectPosition movingobjectposition = ProjectileHelper.a(this, this::a);
         if (movingobjectposition.c() != MovingObjectPosition.EnumMovingObjectType.a) {
            this.preOnHit(movingobjectposition);
         }
      }

      this.aL();
      Vec3D vec3d = this.dj();
      this.e(this.dl() + vec3d.c, this.dn() + vec3d.d, this.dr() + vec3d.e);
      ProjectileHelper.a(this, 0.5F);
      if (this.H.B) {
         this.H.a(Particles.u, this.dl() - vec3d.c, this.dn() - vec3d.d + 0.15, this.dr() - vec3d.e, 0.0, 0.0, 0.0);
      } else if (this.c != null && !this.c.dB()) {
         if (this.e > 0) {
            --this.e;
            if (this.e == 0) {
               this.a(this.d == null ? null : this.d.o());
            }
         }

         if (this.d != null) {
            BlockPosition blockposition = this.dg();
            EnumDirection.EnumAxis enumdirection_enumaxis = this.d.o();
            if (this.H.a(blockposition.a(this.d), this)) {
               this.a(enumdirection_enumaxis);
            } else {
               BlockPosition blockposition1 = this.c.dg();
               if (enumdirection_enumaxis == EnumDirection.EnumAxis.a && blockposition.u() == blockposition1.u()
                  || enumdirection_enumaxis == EnumDirection.EnumAxis.c && blockposition.w() == blockposition1.w()
                  || enumdirection_enumaxis == EnumDirection.EnumAxis.b && blockposition.v() == blockposition1.v()) {
                  this.a(enumdirection_enumaxis);
               }
            }
         }
      }
   }

   @Override
   protected boolean a(Entity entity) {
      return super.a(entity) && !entity.ae;
   }

   @Override
   public boolean bK() {
      return false;
   }

   @Override
   public boolean a(double d0) {
      return d0 < 16384.0;
   }

   @Override
   public float bh() {
      return 1.0F;
   }

   @Override
   protected void a(MovingObjectPositionEntity movingobjectpositionentity) {
      super.a(movingobjectpositionentity);
      Entity entity = movingobjectpositionentity.a();
      Entity entity1 = this.v();
      EntityLiving entityliving = entity1 instanceof EntityLiving ? (EntityLiving)entity1 : null;
      boolean flag = entity.a(this.dG().a(this, entityliving), 4.0F);
      if (flag) {
         this.a(entityliving, entity);
         if (entity instanceof EntityLiving entityliving1) {
            entityliving1.addEffect(new MobEffect(MobEffects.y, 200), (Entity)MoreObjects.firstNonNull(entity1, this), Cause.ATTACK);
         }
      }
   }

   @Override
   protected void a(MovingObjectPositionBlock movingobjectpositionblock) {
      super.a(movingobjectpositionblock);
      ((WorldServer)this.H).a(Particles.x, this.dl(), this.dn(), this.dr(), 2, 0.2, 0.2, 0.2, 0.0);
      this.a(SoundEffects.uL, 1.0F, 1.0F);
   }

   private void j() {
      this.ai();
      this.H.a(GameEvent.p, this.de(), GameEvent.a.a(this));
   }

   @Override
   protected void a(MovingObjectPosition movingobjectposition) {
      super.a(movingobjectposition);
      this.j();
   }

   @Override
   public boolean bm() {
      return true;
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, (double)f, false)) {
         return false;
      } else {
         if (!this.H.B) {
            this.a(SoundEffects.uM, 1.0F, 1.0F);
            ((WorldServer)this.H).a(Particles.g, this.dl(), this.dn(), this.dr(), 15, 0.2, 0.2, 0.2, 0.0);
            this.j();
         }

         return true;
      }
   }

   @Override
   public void a(PacketPlayOutSpawnEntity packetplayoutspawnentity) {
      super.a(packetplayoutspawnentity);
      double d0 = packetplayoutspawnentity.h();
      double d1 = packetplayoutspawnentity.i();
      double d2 = packetplayoutspawnentity.j();
      this.o(d0, d1, d2);
   }
}
