package net.minecraft.world.entity.vehicle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.SystemUtils;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockMinecartTrackAbstract;
import net.minecraft.world.level.block.BlockPoweredRail;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyTrackPosition;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Location;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.util.Vector;

public abstract class EntityMinecartAbstract extends Entity {
   private static final DataWatcherObject<Integer> c = DataWatcher.a(EntityMinecartAbstract.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Integer> d = DataWatcher.a(EntityMinecartAbstract.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Float> e = DataWatcher.a(EntityMinecartAbstract.class, DataWatcherRegistry.d);
   private static final DataWatcherObject<Integer> f = DataWatcher.a(EntityMinecartAbstract.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Integer> g = DataWatcher.a(EntityMinecartAbstract.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Boolean> h = DataWatcher.a(EntityMinecartAbstract.class, DataWatcherRegistry.k);
   private static final ImmutableMap<EntityPose, ImmutableList<Integer>> i = ImmutableMap.of(
      EntityPose.a, ImmutableList.of(0, 1, -1), EntityPose.f, ImmutableList.of(0, 1, -1), EntityPose.d, ImmutableList.of(0, 1)
   );
   protected static final float b = 0.95F;
   private boolean j;
   private static final Map<BlockPropertyTrackPosition, Pair<BaseBlockPosition, BaseBlockPosition>> k = SystemUtils.a(
      Maps.newEnumMap(BlockPropertyTrackPosition.class), enummap -> {
         BaseBlockPosition baseblockposition = EnumDirection.e.q();
         BaseBlockPosition baseblockposition1 = EnumDirection.f.q();
         BaseBlockPosition baseblockposition2 = EnumDirection.c.q();
         BaseBlockPosition baseblockposition3 = EnumDirection.d.q();
         BaseBlockPosition baseblockposition4 = baseblockposition.o();
         BaseBlockPosition baseblockposition5 = baseblockposition1.o();
         BaseBlockPosition baseblockposition6 = baseblockposition2.o();
         BaseBlockPosition baseblockposition7 = baseblockposition3.o();
         enummap.put(BlockPropertyTrackPosition.a, Pair.of(baseblockposition2, baseblockposition3));
         enummap.put(BlockPropertyTrackPosition.b, Pair.of(baseblockposition, baseblockposition1));
         enummap.put(BlockPropertyTrackPosition.c, Pair.of(baseblockposition4, baseblockposition1));
         enummap.put(BlockPropertyTrackPosition.d, Pair.of(baseblockposition, baseblockposition5));
         enummap.put(BlockPropertyTrackPosition.e, Pair.of(baseblockposition2, baseblockposition7));
         enummap.put(BlockPropertyTrackPosition.f, Pair.of(baseblockposition6, baseblockposition3));
         enummap.put(BlockPropertyTrackPosition.g, Pair.of(baseblockposition3, baseblockposition1));
         enummap.put(BlockPropertyTrackPosition.h, Pair.of(baseblockposition3, baseblockposition));
         enummap.put(BlockPropertyTrackPosition.i, Pair.of(baseblockposition2, baseblockposition));
         enummap.put(BlockPropertyTrackPosition.j, Pair.of(baseblockposition2, baseblockposition1));
      }
   );
   private int l;
   private double m;
   private double n;
   private double o;
   private double p;
   private double q;
   private double r;
   private double s;
   private double t;
   public boolean slowWhenEmpty = true;
   private double derailedX = 0.5;
   private double derailedY = 0.5;
   private double derailedZ = 0.5;
   private double flyingX = 0.95;
   private double flyingY = 0.95;
   private double flyingZ = 0.95;
   public double maxSpeed = 0.4;

   protected EntityMinecartAbstract(EntityTypes<?> entitytypes, World world) {
      super(entitytypes, world);
      this.F = true;
   }

   protected EntityMinecartAbstract(EntityTypes<?> entitytypes, World world, double d0, double d1, double d2) {
      this(entitytypes, world);
      this.e(d0, d1, d2);
      this.I = d0;
      this.J = d1;
      this.K = d2;
   }

   public static EntityMinecartAbstract a(
      World world, double d0, double d1, double d2, EntityMinecartAbstract.EnumMinecartType entityminecartabstract_enumminecarttype
   ) {
      return (EntityMinecartAbstract)(entityminecartabstract_enumminecarttype == EntityMinecartAbstract.EnumMinecartType.b
         ? new EntityMinecartChest(world, d0, d1, d2)
         : (
            entityminecartabstract_enumminecarttype == EntityMinecartAbstract.EnumMinecartType.c
               ? new EntityMinecartFurnace(world, d0, d1, d2)
               : (
                  entityminecartabstract_enumminecarttype == EntityMinecartAbstract.EnumMinecartType.d
                     ? new EntityMinecartTNT(world, d0, d1, d2)
                     : (
                        entityminecartabstract_enumminecarttype == EntityMinecartAbstract.EnumMinecartType.e
                           ? new EntityMinecartMobSpawner(world, d0, d1, d2)
                           : (
                              entityminecartabstract_enumminecarttype == EntityMinecartAbstract.EnumMinecartType.f
                                 ? new EntityMinecartHopper(world, d0, d1, d2)
                                 : (
                                    entityminecartabstract_enumminecarttype == EntityMinecartAbstract.EnumMinecartType.g
                                       ? new EntityMinecartCommandBlock(world, d0, d1, d2)
                                       : new EntityMinecartRideable(world, d0, d1, d2)
                                 )
                           )
                     )
               )
         ));
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.c;
   }

   @Override
   protected void a_() {
      this.am.a(c, 0);
      this.am.a(d, 1);
      this.am.a(e, 0.0F);
      this.am.a(f, Block.i(Blocks.a.o()));
      this.am.a(g, 6);
      this.am.a(h, false);
   }

   @Override
   public boolean h(Entity entity) {
      return EntityBoat.a(this, entity);
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
      return 0.0;
   }

   @Override
   public Vec3D b(EntityLiving entityliving) {
      EnumDirection enumdirection = this.cB();
      if (enumdirection.o() == EnumDirection.EnumAxis.b) {
         return super.b(entityliving);
      } else {
         int[][] aint = DismountUtil.a(enumdirection);
         BlockPosition blockposition = this.dg();
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
         ImmutableList<EntityPose> immutablelist = entityliving.fr();
         UnmodifiableIterator unmodifiableiterator = immutablelist.iterator();

         while(unmodifiableiterator.hasNext()) {
            EntityPose entitypose = (EntityPose)unmodifiableiterator.next();
            EntitySize entitysize = entityliving.a(entitypose);
            float f = Math.min(entitysize.a, 1.0F) / 2.0F;
            UnmodifiableIterator unmodifiableiterator1 = ((ImmutableList)EntityMinecartAbstract.i.get(entitypose)).iterator();

            while(unmodifiableiterator1.hasNext()) {
               int i = unmodifiableiterator1.next();

               for(int[] aint2 : aint) {
                  blockposition_mutableblockposition.d(blockposition.u() + aint2[0], blockposition.v() + i, blockposition.w() + aint2[1]);
                  double d0 = this.H
                     .a(DismountUtil.a(this.H, blockposition_mutableblockposition), () -> DismountUtil.a(this.H, blockposition_mutableblockposition.d()));
                  if (DismountUtil.a(d0)) {
                     AxisAlignedBB axisalignedbb = new AxisAlignedBB((double)(-f), 0.0, (double)(-f), (double)f, (double)entitysize.b, (double)f);
                     Vec3D vec3d = Vec3D.a(blockposition_mutableblockposition, d0);
                     if (DismountUtil.a(this.H, entityliving, axisalignedbb.c(vec3d))) {
                        entityliving.b(entitypose);
                        return vec3d;
                     }
                  }
               }
            }
         }

         double d1 = this.cD().e;
         blockposition_mutableblockposition.b((double)blockposition.u(), d1, (double)blockposition.w());
         UnmodifiableIterator unmodifiableiterator2 = immutablelist.iterator();

         while(unmodifiableiterator2.hasNext()) {
            EntityPose entitypose1 = (EntityPose)unmodifiableiterator2.next();
            double d2 = (double)entityliving.a(entitypose1).b;
            int l = MathHelper.c(d1 - (double)blockposition_mutableblockposition.v() + d2);
            double d3 = DismountUtil.a(blockposition_mutableblockposition, l, blockposition1 -> this.H.a_(blockposition1).k(this.H, blockposition1));
            if (d1 + d2 <= d3) {
               entityliving.b(entitypose1);
               break;
            }
         }

         return super.b(entityliving);
      }
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.H.B || this.dB()) {
         return true;
      } else if (this.b(damagesource)) {
         return false;
      } else {
         Vehicle vehicle = (Vehicle)this.getBukkitEntity();
         org.bukkit.entity.Entity passenger = damagesource.d() == null ? null : damagesource.d().getBukkitEntity();
         VehicleDamageEvent event = new VehicleDamageEvent(vehicle, passenger, (double)f);
         this.H.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return false;
         } else {
            f = (float)event.getDamage();
            this.d(-this.r());
            this.c(10);
            this.bj();
            this.a(this.p() + f * 10.0F);
            this.a(GameEvent.p, damagesource.d());
            boolean flag = damagesource.d() instanceof EntityHuman && ((EntityHuman)damagesource.d()).fK().d;
            if (flag || this.p() > 40.0F) {
               VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, passenger);
               this.H.getCraftServer().getPluginManager().callEvent(destroyEvent);
               if (destroyEvent.isCancelled()) {
                  this.a(40.0F);
                  return true;
               }

               this.bx();
               if (flag && !this.aa()) {
                  this.ai();
               } else {
                  this.a(damagesource);
               }
            }

            return true;
         }
      }
   }

   @Override
   protected float aF() {
      IBlockData iblockdata = this.H.a_(this.dg());
      return iblockdata.a(TagsBlock.M) ? 1.0F : super.aF();
   }

   public void a(DamageSource damagesource) {
      this.ah();
      if (this.H.W().b(GameRules.h)) {
         ItemStack itemstack = new ItemStack(this.i());
         if (this.aa()) {
            itemstack.a(this.ab());
         }

         this.b(itemstack);
      }
   }

   abstract Item i();

   @Override
   public void q(float f) {
      this.d(-this.r());
      this.c(10);
      this.a(this.p() + this.p() * 10.0F);
   }

   @Override
   public boolean bm() {
      return !this.dB();
   }

   private static Pair<BaseBlockPosition, BaseBlockPosition> a(BlockPropertyTrackPosition blockpropertytrackposition) {
      return (Pair<BaseBlockPosition, BaseBlockPosition>)k.get(blockpropertytrackposition);
   }

   @Override
   public EnumDirection cB() {
      return this.j ? this.cA().g().h() : this.cA().h();
   }

   @Override
   public void l() {
      double prevX = this.dl();
      double prevY = this.dn();
      double prevZ = this.dr();
      float prevYaw = this.dw();
      float prevPitch = this.dy();
      if (this.q() > 0) {
         this.c(this.q() - 1);
      }

      if (this.p() > 0.0F) {
         this.a(this.p() - 1.0F);
      }

      this.ap();
      if (this.H.B) {
         if (this.l > 0) {
            double d0 = this.dl() + (this.m - this.dl()) / (double)this.l;
            double d1 = this.dn() + (this.n - this.dn()) / (double)this.l;
            double d2 = this.dr() + (this.o - this.dr()) / (double)this.l;
            double d3 = MathHelper.d(this.p - (double)this.dw());
            this.f(this.dw() + (float)d3 / (float)this.l);
            this.e(this.dy() + (float)(this.q - (double)this.dy()) / (float)this.l);
            --this.l;
            this.e(d0, d1, d2);
            this.a(this.dw(), this.dy());
         } else {
            this.an();
            this.a(this.dw(), this.dy());
         }
      } else {
         if (!this.aP()) {
            double d0 = this.aT() ? -0.005 : -0.04;
            this.f(this.dj().b(0.0, d0, 0.0));
         }

         int i = MathHelper.a(this.dl());
         int j = MathHelper.a(this.dn());
         int k = MathHelper.a(this.dr());
         if (this.H.a_(new BlockPosition(i, j - 1, k)).a(TagsBlock.M)) {
            --j;
         }

         BlockPosition blockposition = new BlockPosition(i, j, k);
         IBlockData iblockdata = this.H.a_(blockposition);
         if (BlockMinecartTrackAbstract.g(iblockdata)) {
            this.c(blockposition, iblockdata);
            if (iblockdata.a(Blocks.hg)) {
               this.a(i, j, k, iblockdata.c(BlockPoweredRail.e));
            }
         } else {
            this.k();
         }

         this.aL();
         this.e(0.0F);
         double d4 = this.I - this.dl();
         double d5 = this.K - this.dr();
         if (d4 * d4 + d5 * d5 > 0.001) {
            this.f((float)(MathHelper.d(d5, d4) * 180.0 / Math.PI));
            if (this.j) {
               this.f(this.dw() + 180.0F);
            }
         }

         double d6 = (double)MathHelper.g(this.dw() - this.L);
         if (d6 < -170.0 || d6 >= 170.0) {
            this.f(this.dw() + 180.0F);
            this.j = !this.j;
         }

         this.a(this.dw(), this.dy());
         org.bukkit.World bworld = this.H.getWorld();
         Location from = new Location(bworld, prevX, prevY, prevZ, prevYaw, prevPitch);
         Location to = new Location(bworld, this.dl(), this.dn(), this.dr(), this.dw(), this.dy());
         Vehicle vehicle = (Vehicle)this.getBukkitEntity();
         this.H.getCraftServer().getPluginManager().callEvent(new VehicleUpdateEvent(vehicle));
         if (!from.equals(to)) {
            this.H.getCraftServer().getPluginManager().callEvent(new VehicleMoveEvent(vehicle, from, to));
         }

         if (this.s() == EntityMinecartAbstract.EnumMinecartType.a && this.dj().i() > 0.01) {
            List<Entity> list = this.H.a(this, this.cD().c(0.2F, 0.0, 0.2F), IEntitySelector.a(this));
            if (!list.isEmpty()) {
               for(int l = 0; l < list.size(); ++l) {
                  Entity entity = list.get(l);
                  if (!(entity instanceof EntityHuman)
                     && !(entity instanceof EntityIronGolem)
                     && !(entity instanceof EntityMinecartAbstract)
                     && !this.bM()
                     && !entity.bL()) {
                     VehicleEntityCollisionEvent collisionEvent = new VehicleEntityCollisionEvent(vehicle, entity.getBukkitEntity());
                     this.H.getCraftServer().getPluginManager().callEvent(collisionEvent);
                     if (!collisionEvent.isCancelled()) {
                        entity.k(this);
                     }
                  } else {
                     if (!this.v(entity)) {
                        VehicleEntityCollisionEvent collisionEvent = new VehicleEntityCollisionEvent(vehicle, entity.getBukkitEntity());
                        this.H.getCraftServer().getPluginManager().callEvent(collisionEvent);
                        if (collisionEvent.isCancelled()) {
                           continue;
                        }
                     }

                     entity.g(this);
                  }
               }
            }
         } else {
            for(Entity entity1 : this.H.a_(this, this.cD().c(0.2F, 0.0, 0.2F))) {
               if (!this.u(entity1) && entity1.bn() && entity1 instanceof EntityMinecartAbstract) {
                  VehicleEntityCollisionEvent collisionEvent = new VehicleEntityCollisionEvent(vehicle, entity1.getBukkitEntity());
                  this.H.getCraftServer().getPluginManager().callEvent(collisionEvent);
                  if (!collisionEvent.isCancelled()) {
                     entity1.g(this);
                  }
               }
            }
         }

         this.aZ();
         if (this.bg()) {
            this.at();
            this.aa *= 0.5F;
         }

         this.al = false;
      }
   }

   protected double j() {
      return this.aT() ? this.maxSpeed / 2.0 : this.maxSpeed;
   }

   public void a(int i, int j, int k, boolean flag) {
   }

   protected void k() {
      double d0 = this.j();
      Vec3D vec3d = this.dj();
      this.o(MathHelper.a(vec3d.c, -d0, d0), vec3d.d, MathHelper.a(vec3d.e, -d0, d0));
      if (this.N) {
         this.f(new Vec3D(this.dj().c * this.derailedX, this.dj().d * this.derailedY, this.dj().e * this.derailedZ));
      }

      this.a(EnumMoveType.a, this.dj());
      if (!this.N) {
         this.f(new Vec3D(this.dj().c * this.flyingX, this.dj().d * this.flyingY, this.dj().e * this.flyingZ));
      }
   }

   protected void c(BlockPosition blockposition, IBlockData iblockdata) {
      this.n();
      double d0 = this.dl();
      double d1 = this.dn();
      double d2 = this.dr();
      Vec3D vec3d = this.q(d0, d1, d2);
      d1 = (double)blockposition.v();
      boolean flag = false;
      boolean flag1 = false;
      if (iblockdata.a(Blocks.bo)) {
         flag = iblockdata.c(BlockPoweredRail.e);
         flag1 = !flag;
      }

      double d3 = 0.0078125;
      if (this.aT()) {
         d3 *= 0.2;
      }

      Vec3D vec3d1 = this.dj();
      BlockPropertyTrackPosition blockpropertytrackposition = iblockdata.c(((BlockMinecartTrackAbstract)iblockdata.b()).c());
      switch(blockpropertytrackposition) {
         case c:
            this.f(vec3d1.b(-d3, 0.0, 0.0));
            ++d1;
            break;
         case d:
            this.f(vec3d1.b(d3, 0.0, 0.0));
            ++d1;
            break;
         case e:
            this.f(vec3d1.b(0.0, 0.0, d3));
            ++d1;
            break;
         case f:
            this.f(vec3d1.b(0.0, 0.0, -d3));
            ++d1;
      }

      vec3d1 = this.dj();
      Pair<BaseBlockPosition, BaseBlockPosition> pair = a(blockpropertytrackposition);
      BaseBlockPosition baseblockposition = (BaseBlockPosition)pair.getFirst();
      BaseBlockPosition baseblockposition1 = (BaseBlockPosition)pair.getSecond();
      double d4 = (double)(baseblockposition1.u() - baseblockposition.u());
      double d5 = (double)(baseblockposition1.w() - baseblockposition.w());
      double d6 = Math.sqrt(d4 * d4 + d5 * d5);
      double d7 = vec3d1.c * d4 + vec3d1.e * d5;
      if (d7 < 0.0) {
         d4 = -d4;
         d5 = -d5;
      }

      double d8 = Math.min(2.0, vec3d1.h());
      vec3d1 = new Vec3D(d8 * d4 / d6, vec3d1.d, d8 * d5 / d6);
      this.f(vec3d1);
      Entity entity = this.cN();
      if (entity instanceof EntityHuman) {
         Vec3D vec3d2 = entity.dj();
         double d9 = vec3d2.i();
         double d10 = this.dj().i();
         if (d9 > 1.0E-4 && d10 < 0.01) {
            this.f(this.dj().b(vec3d2.c * 0.1, 0.0, vec3d2.e * 0.1));
            flag1 = false;
         }
      }

      if (flag1) {
         double d11 = this.dj().h();
         if (d11 < 0.03) {
            this.f(Vec3D.b);
         } else {
            this.f(this.dj().d(0.5, 0.0, 0.5));
         }
      }

      double d11 = (double)blockposition.u() + 0.5 + (double)baseblockposition.u() * 0.5;
      double d12 = (double)blockposition.w() + 0.5 + (double)baseblockposition.w() * 0.5;
      double d13 = (double)blockposition.u() + 0.5 + (double)baseblockposition1.u() * 0.5;
      double d14 = (double)blockposition.w() + 0.5 + (double)baseblockposition1.w() * 0.5;
      d4 = d13 - d11;
      d5 = d14 - d12;
      double d15;
      if (d4 == 0.0) {
         d15 = d2 - (double)blockposition.w();
      } else if (d5 == 0.0) {
         d15 = d0 - (double)blockposition.u();
      } else {
         double d16 = d0 - d11;
         double d17 = d2 - d12;
         d15 = (d16 * d4 + d17 * d5) * 2.0;
      }

      d0 = d11 + d4 * d15;
      d2 = d12 + d5 * d15;
      this.e(d0, d1, d2);
      double d16 = this.bM() ? 0.75 : 1.0;
      double d17 = this.j();
      vec3d1 = this.dj();
      this.a(EnumMoveType.a, new Vec3D(MathHelper.a(d16 * vec3d1.c, -d17, d17), 0.0, MathHelper.a(d16 * vec3d1.e, -d17, d17)));
      if (baseblockposition.v() != 0
         && MathHelper.a(this.dl()) - blockposition.u() == baseblockposition.u()
         && MathHelper.a(this.dr()) - blockposition.w() == baseblockposition.w()) {
         this.e(this.dl(), this.dn() + (double)baseblockposition.v(), this.dr());
      } else if (baseblockposition1.v() != 0
         && MathHelper.a(this.dl()) - blockposition.u() == baseblockposition1.u()
         && MathHelper.a(this.dr()) - blockposition.w() == baseblockposition1.w()) {
         this.e(this.dl(), this.dn() + (double)baseblockposition1.v(), this.dr());
      }

      this.o();
      Vec3D vec3d3 = this.q(this.dl(), this.dn(), this.dr());
      if (vec3d3 != null && vec3d != null) {
         double d19 = (vec3d.d - vec3d3.d) * 0.05;
         Vec3D vec3d4 = this.dj();
         double d18 = vec3d4.h();
         if (d18 > 0.0) {
            this.f(vec3d4.d((d18 + d19) / d18, 1.0, (d18 + d19) / d18));
         }

         this.e(this.dl(), vec3d3.d, this.dr());
      }

      int i = MathHelper.a(this.dl());
      int j = MathHelper.a(this.dr());
      if (i != blockposition.u() || j != blockposition.w()) {
         Vec3D vec3d4 = this.dj();
         double d18 = vec3d4.h();
         this.o(d18 * (double)(i - blockposition.u()), vec3d4.d, d18 * (double)(j - blockposition.w()));
      }

      if (flag) {
         Vec3D vec3d4 = this.dj();
         double d18 = vec3d4.h();
         if (d18 > 0.01) {
            double d20 = 0.06;
            this.f(vec3d4.b(vec3d4.c / d18 * 0.06, 0.0, vec3d4.e / d18 * 0.06));
         } else {
            Vec3D vec3d5 = this.dj();
            double d21 = vec3d5.c;
            double d22 = vec3d5.e;
            if (blockpropertytrackposition == BlockPropertyTrackPosition.b) {
               if (this.a(blockposition.g())) {
                  d21 = 0.02;
               } else if (this.a(blockposition.h())) {
                  d21 = -0.02;
               }
            } else {
               if (blockpropertytrackposition != BlockPropertyTrackPosition.a) {
                  return;
               }

               if (this.a(blockposition.e())) {
                  d22 = 0.02;
               } else if (this.a(blockposition.f())) {
                  d22 = -0.02;
               }
            }

            this.o(d21, vec3d5.d, d22);
         }
      }
   }

   private boolean a(BlockPosition blockposition) {
      return this.H.a_(blockposition).g(this.H, blockposition);
   }

   @Override
   protected void o() {
      double d0 = !this.bM() && this.slowWhenEmpty ? 0.96 : 0.997;
      Vec3D vec3d = this.dj();
      vec3d = vec3d.d(d0, 0.0, d0);
      if (this.aT()) {
         vec3d = vec3d.a(0.95F);
      }

      this.f(vec3d);
   }

   @Nullable
   public Vec3D a(double d0, double d1, double d2, double d3) {
      int i = MathHelper.a(d0);
      int j = MathHelper.a(d1);
      int k = MathHelper.a(d2);
      if (this.H.a_(new BlockPosition(i, j - 1, k)).a(TagsBlock.M)) {
         --j;
      }

      IBlockData iblockdata = this.H.a_(new BlockPosition(i, j, k));
      if (BlockMinecartTrackAbstract.g(iblockdata)) {
         BlockPropertyTrackPosition blockpropertytrackposition = iblockdata.c(((BlockMinecartTrackAbstract)iblockdata.b()).c());
         d1 = (double)j;
         if (blockpropertytrackposition.b()) {
            d1 = (double)(j + 1);
         }

         Pair<BaseBlockPosition, BaseBlockPosition> pair = a(blockpropertytrackposition);
         BaseBlockPosition baseblockposition = (BaseBlockPosition)pair.getFirst();
         BaseBlockPosition baseblockposition1 = (BaseBlockPosition)pair.getSecond();
         double d4 = (double)(baseblockposition1.u() - baseblockposition.u());
         double d5 = (double)(baseblockposition1.w() - baseblockposition.w());
         double d6 = Math.sqrt(d4 * d4 + d5 * d5);
         d4 /= d6;
         d5 /= d6;
         d0 += d4 * d3;
         d2 += d5 * d3;
         if (baseblockposition.v() != 0 && MathHelper.a(d0) - i == baseblockposition.u() && MathHelper.a(d2) - k == baseblockposition.w()) {
            d1 += (double)baseblockposition.v();
         } else if (baseblockposition1.v() != 0 && MathHelper.a(d0) - i == baseblockposition1.u() && MathHelper.a(d2) - k == baseblockposition1.w()) {
            d1 += (double)baseblockposition1.v();
         }

         return this.q(d0, d1, d2);
      } else {
         return null;
      }
   }

   @Nullable
   public Vec3D q(double d0, double d1, double d2) {
      int i = MathHelper.a(d0);
      int j = MathHelper.a(d1);
      int k = MathHelper.a(d2);
      if (this.H.a_(new BlockPosition(i, j - 1, k)).a(TagsBlock.M)) {
         --j;
      }

      IBlockData iblockdata = this.H.a_(new BlockPosition(i, j, k));
      if (BlockMinecartTrackAbstract.g(iblockdata)) {
         BlockPropertyTrackPosition blockpropertytrackposition = iblockdata.c(((BlockMinecartTrackAbstract)iblockdata.b()).c());
         Pair<BaseBlockPosition, BaseBlockPosition> pair = a(blockpropertytrackposition);
         BaseBlockPosition baseblockposition = (BaseBlockPosition)pair.getFirst();
         BaseBlockPosition baseblockposition1 = (BaseBlockPosition)pair.getSecond();
         double d3 = (double)i + 0.5 + (double)baseblockposition.u() * 0.5;
         double d4 = (double)j + 0.0625 + (double)baseblockposition.v() * 0.5;
         double d5 = (double)k + 0.5 + (double)baseblockposition.w() * 0.5;
         double d6 = (double)i + 0.5 + (double)baseblockposition1.u() * 0.5;
         double d7 = (double)j + 0.0625 + (double)baseblockposition1.v() * 0.5;
         double d8 = (double)k + 0.5 + (double)baseblockposition1.w() * 0.5;
         double d9 = d6 - d3;
         double d10 = (d7 - d4) * 2.0;
         double d11 = d8 - d5;
         double d12;
         if (d9 == 0.0) {
            d12 = d2 - (double)k;
         } else if (d11 == 0.0) {
            d12 = d0 - (double)i;
         } else {
            double d13 = d0 - d3;
            double d14 = d2 - d5;
            d12 = (d13 * d9 + d14 * d11) * 2.0;
         }

         d0 = d3 + d9 * d12;
         d1 = d4 + d10 * d12;
         d2 = d5 + d11 * d12;
         if (d10 < 0.0) {
            ++d1;
         } else if (d10 > 0.0) {
            d1 += 0.5;
         }

         return new Vec3D(d0, d1, d2);
      } else {
         return null;
      }
   }

   @Override
   public AxisAlignedBB A_() {
      AxisAlignedBB axisalignedbb = this.cD();
      return this.y() ? axisalignedbb.g((double)Math.abs(this.w()) / 16.0) : axisalignedbb;
   }

   @Override
   protected void a(NBTTagCompound nbttagcompound) {
      if (nbttagcompound.q("CustomDisplayTile")) {
         this.b(GameProfileSerializer.a(this.H.a(Registries.e), nbttagcompound.p("DisplayState")));
         this.l(nbttagcompound.h("DisplayOffset"));
      }
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      if (this.y()) {
         nbttagcompound.a("CustomDisplayTile", true);
         nbttagcompound.a("DisplayState", GameProfileSerializer.a(this.t()));
         nbttagcompound.a("DisplayOffset", this.w());
      }
   }

   @Override
   public void g(Entity entity) {
      if (!this.H.B && !entity.ae && !this.ae && !this.u(entity)) {
         VehicleEntityCollisionEvent collisionEvent = new VehicleEntityCollisionEvent((Vehicle)this.getBukkitEntity(), entity.getBukkitEntity());
         this.H.getCraftServer().getPluginManager().callEvent(collisionEvent);
         if (collisionEvent.isCancelled()) {
            return;
         }

         double d0 = entity.dl() - this.dl();
         double d1 = entity.dr() - this.dr();
         double d2 = d0 * d0 + d1 * d1;
         if (d2 >= 1.0E-4F) {
            d2 = Math.sqrt(d2);
            d0 /= d2;
            d1 /= d2;
            double d3 = 1.0 / d2;
            if (d3 > 1.0) {
               d3 = 1.0;
            }

            d0 *= d3;
            d1 *= d3;
            d0 *= 0.1F;
            d1 *= 0.1F;
            d0 *= 0.5;
            d1 *= 0.5;
            if (entity instanceof EntityMinecartAbstract) {
               double d4 = entity.dl() - this.dl();
               double d5 = entity.dr() - this.dr();
               Vec3D vec3d = new Vec3D(d4, 0.0, d5).d();
               Vec3D vec3d1 = new Vec3D(
                     (double)MathHelper.b(this.dw() * (float) (Math.PI / 180.0)), 0.0, (double)MathHelper.a(this.dw() * (float) (Math.PI / 180.0))
                  )
                  .d();
               double d6 = Math.abs(vec3d.b(vec3d1));
               if (d6 < 0.8F) {
                  return;
               }

               Vec3D vec3d2 = this.dj();
               Vec3D vec3d3 = entity.dj();
               if (((EntityMinecartAbstract)entity).s() == EntityMinecartAbstract.EnumMinecartType.c && this.s() != EntityMinecartAbstract.EnumMinecartType.c) {
                  this.f(vec3d2.d(0.2, 1.0, 0.2));
                  this.j(vec3d3.c - d0, 0.0, vec3d3.e - d1);
                  entity.f(vec3d3.d(0.95, 1.0, 0.95));
               } else if (((EntityMinecartAbstract)entity).s() != EntityMinecartAbstract.EnumMinecartType.c
                  && this.s() == EntityMinecartAbstract.EnumMinecartType.c) {
                  entity.f(vec3d3.d(0.2, 1.0, 0.2));
                  entity.j(vec3d2.c + d0, 0.0, vec3d2.e + d1);
                  this.f(vec3d2.d(0.95, 1.0, 0.95));
               } else {
                  double d7 = (vec3d3.c + vec3d2.c) / 2.0;
                  double d8 = (vec3d3.e + vec3d2.e) / 2.0;
                  this.f(vec3d2.d(0.2, 1.0, 0.2));
                  this.j(d7 - d0, 0.0, d8 - d1);
                  entity.f(vec3d3.d(0.2, 1.0, 0.2));
                  entity.j(d7 + d0, 0.0, d8 + d1);
               }
            } else {
               this.j(-d0, 0.0, -d1);
               entity.j(d0 / 4.0, 0.0, d1 / 4.0);
            }
         }
      }
   }

   @Override
   public void a(double d0, double d1, double d2, float f, float f1, int i, boolean flag) {
      this.m = d0;
      this.n = d1;
      this.o = d2;
      this.p = (double)f;
      this.q = (double)f1;
      this.l = i + 2;
      this.o(this.r, this.s, this.t);
   }

   @Override
   public void l(double d0, double d1, double d2) {
      this.r = d0;
      this.s = d1;
      this.t = d2;
      this.o(this.r, this.s, this.t);
   }

   public void a(float f) {
      this.am.b(e, f);
   }

   public float p() {
      return this.am.a(e);
   }

   public void c(int i) {
      this.am.b(c, i);
   }

   public int q() {
      return this.am.a(c);
   }

   public void d(int i) {
      this.am.b(d, i);
   }

   public int r() {
      return this.am.a(d);
   }

   public abstract EntityMinecartAbstract.EnumMinecartType s();

   public IBlockData t() {
      return !this.y() ? this.v() : Block.a(this.aj().a(f));
   }

   public IBlockData v() {
      return Blocks.a.o();
   }

   public int w() {
      return !this.y() ? this.x() : this.aj().a(g);
   }

   public int x() {
      return 6;
   }

   @Override
   public void b(IBlockData iblockdata) {
      this.aj().b(f, Block.i(iblockdata));
      this.a(true);
   }

   public void l(int i) {
      this.aj().b(g, i);
      this.a(true);
   }

   public boolean y() {
      return this.aj().a(h);
   }

   public void a(boolean flag) {
      this.aj().b(h, flag);
   }

   @Override
   public ItemStack dt() {
      return new ItemStack(switch(this.s()) {
         case b -> Items.mX;
         case c -> Items.mY;
         case d -> Items.mZ;
         default -> Items.mW;
         case f -> Items.na;
         case g -> Items.tO;
      });
   }

   public Vector getFlyingVelocityMod() {
      return new Vector(this.flyingX, this.flyingY, this.flyingZ);
   }

   public void setFlyingVelocityMod(Vector flying) {
      this.flyingX = flying.getX();
      this.flyingY = flying.getY();
      this.flyingZ = flying.getZ();
   }

   public Vector getDerailedVelocityMod() {
      return new Vector(this.derailedX, this.derailedY, this.derailedZ);
   }

   public void setDerailedVelocityMod(Vector derailed) {
      this.derailedX = derailed.getX();
      this.derailedY = derailed.getY();
      this.derailedZ = derailed.getZ();
   }

   public static enum EnumMinecartType {
      a,
      b,
      c,
      d,
      e,
      f,
      g;
   }
}
