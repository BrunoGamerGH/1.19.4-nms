package net.minecraft.world.entity.vehicle;

import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayInBoatMove;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.INamable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.animal.EntityWaterAnimal;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockWaterLily;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;

public class EntityBoat extends Entity implements VariantHolder<EntityBoat.EnumBoatType> {
   private static final DataWatcherObject<Integer> f = DataWatcher.a(EntityBoat.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Integer> g = DataWatcher.a(EntityBoat.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Float> h = DataWatcher.a(EntityBoat.class, DataWatcherRegistry.d);
   private static final DataWatcherObject<Integer> i = DataWatcher.a(EntityBoat.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Boolean> j = DataWatcher.a(EntityBoat.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Boolean> k = DataWatcher.a(EntityBoat.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Integer> l = DataWatcher.a(EntityBoat.class, DataWatcherRegistry.b);
   public static final int b = 0;
   public static final int c = 1;
   private static final int m = 60;
   private static final float n = (float) (Math.PI / 8);
   public static final double d = (float) (Math.PI / 4);
   public static final int e = 60;
   private final float[] o;
   private float p;
   private float q;
   private float r;
   private int s;
   private double t;
   private double u;
   private double aC;
   private double aD;
   private double aE;
   private boolean aF;
   private boolean aG;
   private boolean aH;
   private boolean aI;
   private double aJ;
   private float aK;
   public EntityBoat.EnumStatus aL;
   private EntityBoat.EnumStatus aM;
   private double aN;
   private boolean aO;
   private boolean aP;
   private float aQ;
   private float aR;
   private float aS;
   public double maxSpeed = 0.4;
   public double occupiedDeceleration = 0.2;
   public double unoccupiedDeceleration = -1.0;
   public boolean landBoats = false;
   private Location lastLocation;

   public EntityBoat(EntityTypes<? extends EntityBoat> entitytypes, World world) {
      super(entitytypes, world);
      this.o = new float[2];
      this.F = true;
   }

   public EntityBoat(World world, double d0, double d1, double d2) {
      this(EntityTypes.k, world);
      this.e(d0, d1, d2);
      this.I = d0;
      this.J = d1;
      this.K = d2;
   }

   @Override
   protected float a(EntityPose entitypose, EntitySize entitysize) {
      return entitysize.b;
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.c;
   }

   @Override
   protected void a_() {
      this.am.a(f, 0);
      this.am.a(g, 1);
      this.am.a(h, 0.0F);
      this.am.a(i, EntityBoat.EnumBoatType.a.ordinal());
      this.am.a(j, false);
      this.am.a(k, false);
      this.am.a(l, 0);
   }

   @Override
   public boolean h(Entity entity) {
      return a(this, entity);
   }

   public static boolean a(Entity entity, Entity entity1) {
      return (entity1.bs() || entity1.bn()) && !entity.v(entity1);
   }

   @Override
   public boolean bs() {
      return true;
   }

   @Override
   public boolean bn() {
      return true;
   }

   @Override
   protected Vec3D a(EnumDirection.EnumAxis enumdirection_enumaxis, BlockUtil.Rectangle blockutil_rectangle) {
      return EntityLiving.i(super.a(enumdirection_enumaxis, blockutil_rectangle));
   }

   @Override
   public double bv() {
      return this.t() == EntityBoat.EnumBoatType.i ? 0.3 : -0.1;
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else if (!this.H.B && !this.dB()) {
         Vehicle vehicle = (Vehicle)this.getBukkitEntity();
         org.bukkit.entity.Entity attacker = damagesource.d() == null ? null : damagesource.d().getBukkitEntity();
         VehicleDamageEvent event = new VehicleDamageEvent(vehicle, attacker, (double)f);
         this.H.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return false;
         } else {
            this.l(-this.s());
            this.d(10);
            this.a(this.q() + f * 10.0F);
            this.bj();
            this.a(GameEvent.p, damagesource.d());
            boolean flag = damagesource.d() instanceof EntityHuman && ((EntityHuman)damagesource.d()).fK().d;
            if (flag || this.q() > 40.0F) {
               VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, attacker);
               this.H.getCraftServer().getPluginManager().callEvent(destroyEvent);
               if (destroyEvent.isCancelled()) {
                  this.a(40.0F);
                  return true;
               }

               if (!flag && this.H.W().b(GameRules.h)) {
                  this.a(damagesource);
               }

               this.ai();
            }

            return true;
         }
      } else {
         return true;
      }
   }

   protected void a(DamageSource damagesource) {
      this.a(this.i());
   }

   @Override
   public void k(boolean flag) {
      if (!this.H.B) {
         this.aO = true;
         this.aP = flag;
         if (this.E() == 0) {
            this.b(60);
         }
      }

      this.H.a(Particles.ai, this.dl() + (double)this.af.i(), this.dn() + 0.7, this.dr() + (double)this.af.i(), 0.0, 0.0, 0.0);
      if (this.af.a(20) == 0) {
         this.H.a(this.dl(), this.dn(), this.dr(), this.aJ(), this.cX(), 1.0F, 0.8F + 0.4F * this.af.i(), false);
         this.a(GameEvent.S, this.cK());
      }
   }

   @Override
   public void g(Entity entity) {
      if (entity instanceof EntityBoat) {
         if (entity.cD().b < this.cD().e) {
            if (!this.v(entity)) {
               VehicleEntityCollisionEvent event = new VehicleEntityCollisionEvent((Vehicle)this.getBukkitEntity(), entity.getBukkitEntity());
               this.H.getCraftServer().getPluginManager().callEvent(event);
               if (event.isCancelled()) {
                  return;
               }
            }

            super.g(entity);
         }
      } else if (entity.cD().b <= this.cD().b) {
         if (!this.v(entity)) {
            VehicleEntityCollisionEvent event = new VehicleEntityCollisionEvent((Vehicle)this.getBukkitEntity(), entity.getBukkitEntity());
            this.H.getCraftServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               return;
            }
         }

         super.g(entity);
      }
   }

   public Item i() {
      return switch(this.t()) {
         case b -> Items.ng;
         case c -> Items.ni;
         case d -> Items.nk;
         case e -> Items.nm;
         case f -> Items.no;
         case g -> Items.nq;
         case h -> Items.ns;
         case i -> Items.nu;
         default -> Items.ne;
      };
   }

   @Override
   public void q(float f) {
      this.l(-this.s());
      this.d(10);
      this.a(this.q() * 11.0F);
   }

   @Override
   public boolean bm() {
      return !this.dB();
   }

   @Override
   public void a(double d0, double d1, double d2, float f, float f1, int i, boolean flag) {
      this.t = d0;
      this.u = d1;
      this.aC = d2;
      this.aD = (double)f;
      this.aE = (double)f1;
      this.s = 10;
   }

   @Override
   public EnumDirection cB() {
      return this.cA().h();
   }

   @Override
   public void l() {
      this.aM = this.aL;
      this.aL = this.y();
      if (this.aL != EntityBoat.EnumStatus.b && this.aL != EntityBoat.EnumStatus.c) {
         this.q = 0.0F;
      } else {
         ++this.q;
      }

      if (!this.H.B && this.q >= 60.0F) {
         this.bx();
      }

      if (this.r() > 0) {
         this.d(this.r() - 1);
      }

      if (this.q() > 0.0F) {
         this.a(this.q() - 1.0F);
      }

      super.l();
      this.x();
      if (this.cT()) {
         if (!(this.cN() instanceof EntityHuman)) {
            this.a(false, false);
         }

         this.C();
         if (this.H.B) {
            this.D();
            this.H.a(new PacketPlayInBoatMove(this.c(0), this.c(1)));
         }

         this.a(EnumMoveType.a, this.dj());
      } else {
         this.f(Vec3D.b);
      }

      Server server = this.H.getCraftServer();
      org.bukkit.World bworld = this.H.getWorld();
      Location to = new Location(bworld, this.dl(), this.dn(), this.dr(), this.dw(), this.dy());
      Vehicle vehicle = (Vehicle)this.getBukkitEntity();
      server.getPluginManager().callEvent(new VehicleUpdateEvent(vehicle));
      if (this.lastLocation != null && !this.lastLocation.equals(to)) {
         VehicleMoveEvent event = new VehicleMoveEvent(vehicle, this.lastLocation, to);
         server.getPluginManager().callEvent(event);
      }

      this.lastLocation = vehicle.getLocation();
      this.w();

      for(int i = 0; i <= 1; ++i) {
         if (this.c(i)) {
            if (!this.aO()
               && (double)(this.o[i] % (float) (Math.PI * 2)) <= (float) (Math.PI / 4)
               && (double)((this.o[i] + (float) (Math.PI / 8)) % (float) (Math.PI * 2)) >= (float) (Math.PI / 4)) {
               SoundEffect soundeffect = this.j();
               if (soundeffect != null) {
                  Vec3D vec3d = this.j(1.0F);
                  double d0 = i == 1 ? -vec3d.e : vec3d.e;
                  double d1 = i == 1 ? vec3d.c : -vec3d.c;
                  this.H.a(null, this.dl() + d0, this.dn(), this.dr() + d1, soundeffect, this.cX(), 1.0F, 0.8F + 0.4F * this.af.i());
               }
            }

            this.o[i] += (float) (Math.PI / 8);
         } else {
            this.o[i] = 0.0F;
         }
      }

      this.aL();
      List<Entity> list = this.H.a(this, this.cD().c(0.2F, -0.01F, 0.2F), IEntitySelector.a(this));
      if (!list.isEmpty()) {
         boolean flag = !this.H.B && !(this.cK() instanceof EntityHuman);

         for(int j = 0; j < list.size(); ++j) {
            Entity entity = list.get(j);
            if (!entity.u(this)) {
               if (flag
                  && this.cM().size() < this.v()
                  && !entity.bL()
                  && this.a(entity)
                  && entity instanceof EntityLiving
                  && !(entity instanceof EntityWaterAnimal)
                  && !(entity instanceof EntityHuman)) {
                  entity.k(this);
               } else {
                  this.g(entity);
               }
            }
         }
      }
   }

   private void w() {
      if (this.H.B) {
         int i = this.E();
         if (i > 0) {
            this.aQ += 0.05F;
         } else {
            this.aQ -= 0.1F;
         }

         this.aQ = MathHelper.a(this.aQ, 0.0F, 1.0F);
         this.aS = this.aR;
         this.aR = 10.0F * (float)Math.sin((double)(0.5F * (float)this.H.U())) * this.aQ;
      } else {
         if (!this.aO) {
            this.b(0);
         }

         int i = this.E();
         if (i > 0) {
            this.b(--i);
            int j = 60 - i - 1;
            if (j > 0 && i == 0) {
               this.b(0);
               Vec3D vec3d = this.dj();
               if (this.aP) {
                  this.f(vec3d.b(0.0, -0.7, 0.0));
                  this.bx();
               } else {
                  this.o(vec3d.c, this.a(entity -> entity instanceof EntityHuman) ? 2.7 : 0.6, vec3d.e);
               }
            }

            this.aO = false;
         }
      }
   }

   @Nullable
   protected SoundEffect j() {
      switch(this.y()) {
         case a:
         case b:
         case c:
            return SoundEffects.bW;
         case d:
            return SoundEffects.bV;
         case e:
         default:
            return null;
      }
   }

   private void x() {
      if (this.cT()) {
         this.s = 0;
         this.f(this.dl(), this.dn(), this.dr());
      }

      if (this.s > 0) {
         double d0 = this.dl() + (this.t - this.dl()) / (double)this.s;
         double d1 = this.dn() + (this.u - this.dn()) / (double)this.s;
         double d2 = this.dr() + (this.aC - this.dr()) / (double)this.s;
         double d3 = MathHelper.d(this.aD - (double)this.dw());
         this.f(this.dw() + (float)d3 / (float)this.s);
         this.e(this.dy() + (float)(this.aE - (double)this.dy()) / (float)this.s);
         --this.s;
         this.e(d0, d1, d2);
         this.a(this.dw(), this.dy());
      }
   }

   public void a(boolean flag, boolean flag1) {
      this.am.b(j, flag);
      this.am.b(k, flag1);
   }

   public float a(int i, float f) {
      return this.c(i) ? MathHelper.b(this.o[i] - (float) (Math.PI / 8), this.o[i], f) : 0.0F;
   }

   private EntityBoat.EnumStatus y() {
      EntityBoat.EnumStatus entityboat_enumstatus = this.A();
      if (entityboat_enumstatus != null) {
         this.aJ = this.cD().e;
         return entityboat_enumstatus;
      } else if (this.z()) {
         return EntityBoat.EnumStatus.a;
      } else {
         float f = this.o();
         if (f > 0.0F) {
            this.aK = f;
            return EntityBoat.EnumStatus.d;
         } else {
            return EntityBoat.EnumStatus.e;
         }
      }
   }

   public float k() {
      AxisAlignedBB axisalignedbb = this.cD();
      int i = MathHelper.a(axisalignedbb.a);
      int j = MathHelper.c(axisalignedbb.d);
      int k = MathHelper.a(axisalignedbb.e);
      int l = MathHelper.c(axisalignedbb.e - this.aN);
      int i1 = MathHelper.a(axisalignedbb.c);
      int j1 = MathHelper.c(axisalignedbb.f);
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

      label39:
      for(int k1 = k; k1 < l; ++k1) {
         float f = 0.0F;

         for(int l1 = i; l1 < j; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               blockposition_mutableblockposition.d(l1, k1, i2);
               Fluid fluid = this.H.b_(blockposition_mutableblockposition);
               if (fluid.a(TagsFluid.a)) {
                  f = Math.max(f, fluid.a((IBlockAccess)this.H, blockposition_mutableblockposition));
               }

               if (f >= 1.0F) {
                  continue label39;
               }
            }
         }

         if (f < 1.0F) {
            return (float)blockposition_mutableblockposition.v() + f;
         }
      }

      return (float)(l + 1);
   }

   public float o() {
      AxisAlignedBB axisalignedbb = this.cD();
      AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(
         axisalignedbb.a, axisalignedbb.b - 0.001, axisalignedbb.c, axisalignedbb.d, axisalignedbb.b, axisalignedbb.f
      );
      int i = MathHelper.a(axisalignedbb1.a) - 1;
      int j = MathHelper.c(axisalignedbb1.d) + 1;
      int k = MathHelper.a(axisalignedbb1.b) - 1;
      int l = MathHelper.c(axisalignedbb1.e) + 1;
      int i1 = MathHelper.a(axisalignedbb1.c) - 1;
      int j1 = MathHelper.c(axisalignedbb1.f) + 1;
      VoxelShape voxelshape = VoxelShapes.a(axisalignedbb1);
      float f = 0.0F;
      int k1 = 0;
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int l1 = i; l1 < j; ++l1) {
         for(int i2 = i1; i2 < j1; ++i2) {
            int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
            if (j2 != 2) {
               for(int k2 = k; k2 < l; ++k2) {
                  if (j2 <= 0 || k2 != k && k2 != l - 1) {
                     blockposition_mutableblockposition.d(l1, k2, i2);
                     IBlockData iblockdata = this.H.a_(blockposition_mutableblockposition);
                     if (!(iblockdata.b() instanceof BlockWaterLily)
                        && VoxelShapes.c(
                           iblockdata.k(this.H, blockposition_mutableblockposition).a((double)l1, (double)k2, (double)i2), voxelshape, OperatorBoolean.i
                        )) {
                        f += iblockdata.b().i();
                        ++k1;
                     }
                  }
               }
            }
         }
      }

      return f / (float)k1;
   }

   private boolean z() {
      AxisAlignedBB axisalignedbb = this.cD();
      int i = MathHelper.a(axisalignedbb.a);
      int j = MathHelper.c(axisalignedbb.d);
      int k = MathHelper.a(axisalignedbb.b);
      int l = MathHelper.c(axisalignedbb.b + 0.001);
      int i1 = MathHelper.a(axisalignedbb.c);
      int j1 = MathHelper.c(axisalignedbb.f);
      boolean flag = false;
      this.aJ = -Double.MAX_VALUE;
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int k1 = i; k1 < j; ++k1) {
         for(int l1 = k; l1 < l; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               blockposition_mutableblockposition.d(k1, l1, i2);
               Fluid fluid = this.H.b_(blockposition_mutableblockposition);
               if (fluid.a(TagsFluid.a)) {
                  float f = (float)l1 + fluid.a((IBlockAccess)this.H, blockposition_mutableblockposition);
                  this.aJ = Math.max((double)f, this.aJ);
                  flag |= axisalignedbb.b < (double)f;
               }
            }
         }
      }

      return flag;
   }

   @Nullable
   private EntityBoat.EnumStatus A() {
      AxisAlignedBB axisalignedbb = this.cD();
      double d0 = axisalignedbb.e + 0.001;
      int i = MathHelper.a(axisalignedbb.a);
      int j = MathHelper.c(axisalignedbb.d);
      int k = MathHelper.a(axisalignedbb.e);
      int l = MathHelper.c(d0);
      int i1 = MathHelper.a(axisalignedbb.c);
      int j1 = MathHelper.c(axisalignedbb.f);
      boolean flag = false;
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int k1 = i; k1 < j; ++k1) {
         for(int l1 = k; l1 < l; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               blockposition_mutableblockposition.d(k1, l1, i2);
               Fluid fluid = this.H.b_(blockposition_mutableblockposition);
               if (fluid.a(TagsFluid.a)
                  && d0 < (double)((float)blockposition_mutableblockposition.v() + fluid.a((IBlockAccess)this.H, blockposition_mutableblockposition))) {
                  if (!fluid.b()) {
                     return EntityBoat.EnumStatus.c;
                  }

                  flag = true;
               }
            }
         }
      }

      return flag ? EntityBoat.EnumStatus.b : null;
   }

   private void C() {
      double d0 = -0.04F;
      double d1 = this.aP() ? 0.0 : -0.04F;
      double d2 = 0.0;
      this.p = 0.05F;
      if (this.aM == EntityBoat.EnumStatus.e && this.aL != EntityBoat.EnumStatus.e && this.aL != EntityBoat.EnumStatus.d) {
         this.aJ = this.e(1.0);
         this.e(this.dl(), (double)(this.k() - this.dd()) + 0.101, this.dr());
         this.f(this.dj().d(1.0, 0.0, 1.0));
         this.aN = 0.0;
         this.aL = EntityBoat.EnumStatus.a;
      } else {
         if (this.aL == EntityBoat.EnumStatus.a) {
            d2 = (this.aJ - this.dn()) / (double)this.dd();
            this.p = 0.9F;
         } else if (this.aL == EntityBoat.EnumStatus.c) {
            d1 = -7.0E-4;
            this.p = 0.9F;
         } else if (this.aL == EntityBoat.EnumStatus.b) {
            d2 = 0.01F;
            this.p = 0.45F;
         } else if (this.aL == EntityBoat.EnumStatus.e) {
            this.p = 0.9F;
         } else if (this.aL == EntityBoat.EnumStatus.d) {
            this.p = this.aK;
            if (this.cK() instanceof EntityHuman) {
               this.aK /= 2.0F;
            }
         }

         Vec3D vec3d = this.dj();
         this.o(vec3d.c * (double)this.p, vec3d.d + d1, vec3d.e * (double)this.p);
         this.r *= this.p;
         if (d2 > 0.0) {
            Vec3D vec3d1 = this.dj();
            this.o(vec3d1.c, (vec3d1.d + d2 * 0.06153846016296973) * 0.75, vec3d1.e);
         }
      }
   }

   private void D() {
      if (this.bM()) {
         float f = 0.0F;
         if (this.aF) {
            --this.r;
         }

         if (this.aG) {
            ++this.r;
         }

         if (this.aG != this.aF && !this.aH && !this.aI) {
            f += 0.005F;
         }

         this.f(this.dw() + this.r);
         if (this.aH) {
            f += 0.04F;
         }

         if (this.aI) {
            f -= 0.005F;
         }

         this.f(
            this.dj()
               .b((double)(MathHelper.a(-this.dw() * (float) (Math.PI / 180.0)) * f), 0.0, (double)(MathHelper.b(this.dw() * (float) (Math.PI / 180.0)) * f))
         );
         this.a(this.aG && !this.aF || this.aH, this.aF && !this.aG || this.aH);
      }
   }

   protected float p() {
      return 0.0F;
   }

   public boolean a(Entity entity) {
      return entity.dc() < this.dc();
   }

   @Override
   public void i(Entity entity) {
      if (this.u(entity)) {
         float f = this.p();
         float f1 = (float)((this.dB() ? 0.01F : this.bv()) + entity.bu());
         if (this.cM().size() > 1) {
            int i = this.cM().indexOf(entity);
            if (i == 0) {
               f = 0.2F;
            } else {
               f = -0.6F;
            }

            if (entity instanceof EntityAnimal) {
               f += 0.2F;
            }
         }

         Vec3D vec3d = new Vec3D((double)f, 0.0, 0.0).b(-this.dw() * (float) (Math.PI / 180.0) - (float) (Math.PI / 2));
         entity.e(this.dl() + vec3d.c, this.dn() + (double)f1, this.dr() + vec3d.e);
         entity.f(entity.dw() + this.r);
         entity.r(entity.ck() + this.r);
         this.b(entity);
         if (entity instanceof EntityAnimal && this.cM().size() == this.v()) {
            int j = entity.af() % 2 == 0 ? 90 : 270;
            entity.s(((EntityAnimal)entity).aT + (float)j);
            entity.r(entity.ck() + (float)j);
         }
      }
   }

   @Override
   public Vec3D b(EntityLiving entityliving) {
      Vec3D vec3d = a((double)(this.dc() * MathHelper.g), (double)entityliving.dc(), entityliving.dw());
      double d0 = this.dl() + vec3d.c;
      double d1 = this.dr() + vec3d.e;
      BlockPosition blockposition = BlockPosition.a(d0, this.cD().e, d1);
      BlockPosition blockposition1 = blockposition.d();
      if (!this.H.B(blockposition1)) {
         List<Vec3D> list = Lists.newArrayList();
         double d2 = this.H.i(blockposition);
         if (DismountUtil.a(d2)) {
            list.add(new Vec3D(d0, (double)blockposition.v() + d2, d1));
         }

         double d3 = this.H.i(blockposition1);
         if (DismountUtil.a(d3)) {
            list.add(new Vec3D(d0, (double)blockposition1.v() + d3, d1));
         }

         UnmodifiableIterator unmodifiableiterator = entityliving.fr().iterator();

         while(unmodifiableiterator.hasNext()) {
            EntityPose entitypose = (EntityPose)unmodifiableiterator.next();

            for(Vec3D vec3d1 : list) {
               if (DismountUtil.a(this.H, vec3d1, entityliving, entitypose)) {
                  entityliving.b(entitypose);
                  return vec3d1;
               }
            }
         }
      }

      return super.b(entityliving);
   }

   protected void b(Entity entity) {
      entity.s(this.dw());
      float f = MathHelper.g(entity.dw() - this.dw());
      float f1 = MathHelper.a(f, -105.0F, 105.0F);
      entity.L += f1 - f;
      entity.f(entity.dw() + f1 - f);
      entity.r(entity.dw());
   }

   @Override
   public void j(Entity entity) {
      this.b(entity);
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("Type", this.t().c());
   }

   @Override
   protected void a(NBTTagCompound nbttagcompound) {
      if (nbttagcompound.b("Type", 8)) {
         this.a(EntityBoat.EnumBoatType.a(nbttagcompound.l("Type")));
      }
   }

   @Override
   public EnumInteractionResult a(EntityHuman entityhuman, EnumHand enumhand) {
      return entityhuman.fz()
         ? EnumInteractionResult.d
         : (
            this.q < 60.0F
               ? (!this.H.B ? (entityhuman.k(this) ? EnumInteractionResult.b : EnumInteractionResult.d) : EnumInteractionResult.a)
               : EnumInteractionResult.d
         );
   }

   @Override
   protected void a(double d0, boolean flag, IBlockData iblockdata, BlockPosition blockposition) {
      this.aN = this.dj().d;
      if (!this.bL()) {
         if (flag) {
            if (this.aa > 3.0F) {
               if (this.aL != EntityBoat.EnumStatus.d) {
                  this.n();
                  return;
               }

               this.a(this.aa, 1.0F, this.dG().k());
               if (!this.H.B && !this.dB()) {
                  Vehicle vehicle = (Vehicle)this.getBukkitEntity();
                  VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, null);
                  this.H.getCraftServer().getPluginManager().callEvent(destroyEvent);
                  if (!destroyEvent.isCancelled()) {
                     this.ah();
                     if (this.H.W().b(GameRules.h)) {
                        for(int i = 0; i < 3; ++i) {
                           this.a(this.t().b());
                        }

                        for(int var9 = 0; var9 < 2; ++var9) {
                           this.a(Items.ox);
                        }
                     }
                  }
               }
            }

            this.n();
         } else if (!this.H.b_(this.dg().d()).a(TagsFluid.a) && d0 < 0.0) {
            this.aa -= (float)d0;
         }
      }
   }

   public boolean c(int i) {
      return this.am.<Boolean>a(i == 0 ? j : k) && this.cK() != null;
   }

   public void a(float f) {
      this.am.b(h, f);
   }

   public float q() {
      return this.am.a(h);
   }

   public void d(int i) {
      this.am.b(f, i);
   }

   public int r() {
      return this.am.a(f);
   }

   private void b(int i) {
      this.am.b(l, i);
   }

   private int E() {
      return this.am.a(l);
   }

   public float b(float f) {
      return MathHelper.i(f, this.aS, this.aR);
   }

   public void l(int i) {
      this.am.b(g, i);
   }

   public int s() {
      return this.am.a(g);
   }

   public void a(EntityBoat.EnumBoatType entityboat_enumboattype) {
      this.am.b(i, entityboat_enumboattype.ordinal());
   }

   public EntityBoat.EnumBoatType t() {
      return EntityBoat.EnumBoatType.a(this.am.a(i));
   }

   @Override
   protected boolean o(Entity entity) {
      return this.cM().size() < this.v() && !this.a(TagsFluid.a);
   }

   protected int v() {
      return 2;
   }

   @Nullable
   @Override
   public EntityLiving cK() {
      Entity entity = this.cN();
      EntityLiving entityliving;
      if (entity instanceof EntityLiving entityliving1) {
         entityliving = entityliving1;
      } else {
         entityliving = null;
      }

      return entityliving;
   }

   public void a(boolean flag, boolean flag1, boolean flag2, boolean flag3) {
      this.aF = flag;
      this.aG = flag1;
      this.aH = flag2;
      this.aI = flag3;
   }

   @Override
   public boolean aX() {
      return this.aL == EntityBoat.EnumStatus.b || this.aL == EntityBoat.EnumStatus.c;
   }

   @Override
   public ItemStack dt() {
      return new ItemStack(this.i());
   }

   public static enum EnumBoatType implements INamable {
      a(Blocks.n, "oak"),
      b(Blocks.o, "spruce"),
      c(Blocks.p, "birch"),
      d(Blocks.q, "jungle"),
      e(Blocks.r, "acacia"),
      f(Blocks.s, "cherry"),
      g(Blocks.t, "dark_oak"),
      h(Blocks.u, "mangrove"),
      i(Blocks.v, "bamboo");

      private final String k;
      private final Block l;
      public static final INamable.a<EntityBoat.EnumBoatType> j = INamable.a(EntityBoat.EnumBoatType::values);
      private static final IntFunction<EntityBoat.EnumBoatType> m = ByIdMap.a(Enum::ordinal, values(), ByIdMap.a.a);

      private EnumBoatType(Block block, String s) {
         this.k = s;
         this.l = block;
      }

      @Override
      public String c() {
         return this.k;
      }

      public String a() {
         return this.k;
      }

      public Block b() {
         return this.l;
      }

      @Override
      public String toString() {
         return this.k;
      }

      public static EntityBoat.EnumBoatType a(int i) {
         return m.apply(i);
      }

      public static EntityBoat.EnumBoatType a(String s) {
         return j.a(s, a);
      }
   }

   public static enum EnumStatus {
      a,
      b,
      c,
      d,
      e;
   }
}
