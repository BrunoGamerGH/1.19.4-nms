package net.minecraft.world.entity.projectile;

import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class EntityFireworks extends IProjectile implements ItemSupplier {
   public static final DataWatcherObject<ItemStack> b = DataWatcher.a(EntityFireworks.class, DataWatcherRegistry.h);
   private static final DataWatcherObject<OptionalInt> c = DataWatcher.a(EntityFireworks.class, DataWatcherRegistry.u);
   public static final DataWatcherObject<Boolean> d = DataWatcher.a(EntityFireworks.class, DataWatcherRegistry.k);
   public int e;
   public int f;
   @Nullable
   public EntityLiving g;

   public EntityFireworks(EntityTypes<? extends EntityFireworks> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityFireworks(World world, double d0, double d1, double d2, ItemStack itemstack) {
      super(EntityTypes.M, world);
      this.e = 0;
      this.e(d0, d1, d2);
      int i = 1;
      if (!itemstack.b() && itemstack.t()) {
         this.am.b(b, itemstack.o());
         i += itemstack.a("Fireworks").f("Flight");
      }

      this.o(this.af.a(0.0, 0.002297), 0.05, this.af.a(0.0, 0.002297));
      this.f = 10 * i + this.af.a(6) + this.af.a(7);
   }

   public EntityFireworks(World world, @Nullable Entity entity, double d0, double d1, double d2, ItemStack itemstack) {
      this(world, d0, d1, d2, itemstack);
      this.b(entity);
   }

   public EntityFireworks(World world, ItemStack itemstack, EntityLiving entityliving) {
      this(world, entityliving, entityliving.dl(), entityliving.dn(), entityliving.dr(), itemstack);
      this.am.b(c, OptionalInt.of(entityliving.af()));
      this.g = entityliving;
   }

   public EntityFireworks(World world, ItemStack itemstack, double d0, double d1, double d2, boolean flag) {
      this(world, d0, d1, d2, itemstack);
      this.am.b(d, flag);
   }

   public EntityFireworks(World world, ItemStack itemstack, Entity entity, double d0, double d1, double d2, boolean flag) {
      this(world, itemstack, d0, d1, d2, flag);
      this.b(entity);
   }

   @Override
   public void inactiveTick() {
      ++this.e;
      if (!this.H.B && this.e > this.f && !CraftEventFactory.callFireworkExplodeEvent(this).isCancelled()) {
         this.k();
      }

      super.inactiveTick();
   }

   @Override
   protected void a_() {
      this.am.a(b, ItemStack.b);
      this.am.a(c, OptionalInt.empty());
      this.am.a(d, false);
   }

   @Override
   public boolean a(double d0) {
      return d0 < 4096.0 && !this.q();
   }

   @Override
   public boolean k(double d0, double d1, double d2) {
      return super.k(d0, d1, d2) && !this.q();
   }

   @Override
   public void l() {
      super.l();
      if (this.q()) {
         if (this.g == null) {
            this.am.a(c).ifPresent(i -> {
               Entity entity = this.H.a(i);
               if (entity instanceof EntityLiving) {
                  this.g = (EntityLiving)entity;
               }
            });
         }

         if (this.g != null) {
            Vec3D vec3d;
            if (this.g.fn()) {
               Vec3D vec3d1 = this.g.bC();
               double d0 = 1.5;
               double d1 = 0.1;
               Vec3D vec3d2 = this.g.dj();
               this.g
                  .f(
                     vec3d2.b(
                        vec3d1.c * 0.1 + (vec3d1.c * 1.5 - vec3d2.c) * 0.5,
                        vec3d1.d * 0.1 + (vec3d1.d * 1.5 - vec3d2.d) * 0.5,
                        vec3d1.e * 0.1 + (vec3d1.e * 1.5 - vec3d2.e) * 0.5
                     )
                  );
               vec3d = this.g.a(Items.tw);
            } else {
               vec3d = Vec3D.b;
            }

            this.e(this.g.dl() + vec3d.c, this.g.dn() + vec3d.d, this.g.dr() + vec3d.e);
            this.f(this.g.dj());
         }
      } else {
         if (!this.j()) {
            double d2 = this.O ? 1.0 : 1.15;
            this.f(this.dj().d(d2, 1.0, d2).b(0.0, 0.04, 0.0));
         }

         Vec3D vec3d = this.dj();
         this.a(EnumMoveType.a, vec3d);
         this.f(vec3d);
      }

      MovingObjectPosition movingobjectposition = ProjectileHelper.a(this, this::a);
      if (!this.ae) {
         this.preOnHit(movingobjectposition);
         this.at = true;
      }

      this.A();
      if (this.e == 0 && !this.aO()) {
         this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.hE, SoundCategory.i, 3.0F, 1.0F);
      }

      ++this.e;
      if (this.H.B && this.e % 2 < 2) {
         this.H.a(Particles.A, this.dl(), this.dn(), this.dr(), this.af.k() * 0.05, -this.dj().d * 0.5, this.af.k() * 0.05);
      }

      if (!this.H.B && this.e > this.f && !CraftEventFactory.callFireworkExplodeEvent(this).isCancelled()) {
         this.k();
      }
   }

   private void k() {
      this.H.a(this, (byte)17);
      this.a(GameEvent.y, this.v());
      this.p();
      this.ai();
   }

   @Override
   protected void a(MovingObjectPositionEntity movingobjectpositionentity) {
      super.a(movingobjectpositionentity);
      if (!this.H.B && !CraftEventFactory.callFireworkExplodeEvent(this).isCancelled()) {
         this.k();
      }
   }

   @Override
   protected void a(MovingObjectPositionBlock movingobjectpositionblock) {
      BlockPosition blockposition = new BlockPosition(movingobjectpositionblock.a());
      this.H.a_(blockposition).a(this.H, blockposition, this);
      if (!this.H.k_() && this.o() && !CraftEventFactory.callFireworkExplodeEvent(this).isCancelled()) {
         this.k();
      }

      super.a(movingobjectpositionblock);
   }

   private boolean o() {
      ItemStack itemstack = this.am.a(b);
      NBTTagCompound nbttagcompound = itemstack.b() ? null : itemstack.b("Fireworks");
      NBTTagList nbttaglist = nbttagcompound != null ? nbttagcompound.c("Explosions", 10) : null;
      return nbttaglist != null && !nbttaglist.isEmpty();
   }

   private void p() {
      float f = 0.0F;
      ItemStack itemstack = this.am.a(b);
      NBTTagCompound nbttagcompound = itemstack.b() ? null : itemstack.b("Fireworks");
      NBTTagList nbttaglist = nbttagcompound != null ? nbttagcompound.c("Explosions", 10) : null;
      if (nbttaglist != null && !nbttaglist.isEmpty()) {
         f = 5.0F + (float)(nbttaglist.size() * 2);
      }

      if (f > 0.0F) {
         if (this.g != null) {
            CraftEventFactory.entityDamage = this;
            this.g.a(this.dG().a(this, this.v()), 5.0F + (float)(nbttaglist.size() * 2));
            CraftEventFactory.entityDamage = null;
         }

         double d0 = 5.0;
         Vec3D vec3d = this.de();

         for(EntityLiving entityliving : this.H.a(EntityLiving.class, this.cD().g(5.0))) {
            if (entityliving != this.g && this.f(entityliving) <= 25.0) {
               boolean flag = false;

               for(int i = 0; i < 2; ++i) {
                  Vec3D vec3d1 = new Vec3D(entityliving.dl(), entityliving.e(0.5 * (double)i), entityliving.dr());
                  MovingObjectPositionBlock movingobjectpositionblock = this.H
                     .a(new RayTrace(vec3d, vec3d1, RayTrace.BlockCollisionOption.a, RayTrace.FluidCollisionOption.a, this));
                  if (movingobjectpositionblock.c() == MovingObjectPosition.EnumMovingObjectType.a) {
                     flag = true;
                     break;
                  }
               }

               if (flag) {
                  float f1 = f * (float)Math.sqrt((5.0 - (double)this.e(entityliving)) / 5.0);
                  CraftEventFactory.entityDamage = this;
                  entityliving.a(this.dG().a(this, this.v()), f1);
                  CraftEventFactory.entityDamage = null;
               }
            }
         }
      }
   }

   private boolean q() {
      return this.am.a(c).isPresent();
   }

   @Override
   public boolean j() {
      return this.am.a(d);
   }

   @Override
   public void b(byte b0) {
      if (b0 == 17 && this.H.B) {
         if (!this.o()) {
            for(int i = 0; i < this.af.a(3) + 2; ++i) {
               this.H.a(Particles.Y, this.dl(), this.dn(), this.dr(), this.af.k() * 0.05, 0.005, this.af.k() * 0.05);
            }
         } else {
            ItemStack itemstack = this.am.a(b);
            NBTTagCompound nbttagcompound = itemstack.b() ? null : itemstack.b("Fireworks");
            Vec3D vec3d = this.dj();
            this.H.a(this.dl(), this.dn(), this.dr(), vec3d.c, vec3d.d, vec3d.e, nbttagcompound);
         }
      }

      super.b(b0);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Life", this.e);
      nbttagcompound.a("LifeTime", this.f);
      ItemStack itemstack = this.am.a(b);
      if (!itemstack.b()) {
         nbttagcompound.a("FireworksItem", itemstack.b(new NBTTagCompound()));
      }

      nbttagcompound.a("ShotAtAngle", this.am.a(d));
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.e = nbttagcompound.h("Life");
      this.f = nbttagcompound.h("LifeTime");
      ItemStack itemstack = ItemStack.a(nbttagcompound.p("FireworksItem"));
      if (!itemstack.b()) {
         this.am.b(b, itemstack);
      }

      if (nbttagcompound.e("ShotAtAngle")) {
         this.am.b(d, nbttagcompound.q("ShotAtAngle"));
      }
   }

   @Override
   public ItemStack i() {
      ItemStack itemstack = this.am.a(b);
      return itemstack.b() ? new ItemStack(Items.tw) : itemstack;
   }

   @Override
   public boolean cl() {
      return false;
   }
}
