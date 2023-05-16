package net.minecraft.world.entity.projectile;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftItem;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;

public abstract class EntityArrow extends IProjectile {
   private static final double f = 2.0;
   private static final DataWatcherObject<Byte> g = DataWatcher.a(EntityArrow.class, DataWatcherRegistry.a);
   private static final DataWatcherObject<Byte> h = DataWatcher.a(EntityArrow.class, DataWatcherRegistry.a);
   private static final int i = 1;
   private static final int j = 2;
   private static final int k = 4;
   @Nullable
   private IBlockData l;
   public boolean b;
   protected int c;
   public EntityArrow.PickupStatus d = EntityArrow.PickupStatus.a;
   public int e;
   public int m;
   private double n = 2.0;
   public int o;
   private SoundEffect p = this.j();
   @Nullable
   private IntOpenHashSet q;
   @Nullable
   private List<Entity> r;

   @Override
   public void inactiveTick() {
      if (this.b) {
         ++this.m;
      }

      super.inactiveTick();
   }

   protected EntityArrow(EntityTypes<? extends EntityArrow> entitytypes, World world) {
      super(entitytypes, world);
   }

   protected EntityArrow(EntityTypes<? extends EntityArrow> entitytypes, double d0, double d1, double d2, World world) {
      this(entitytypes, world);
      this.e(d0, d1, d2);
   }

   protected EntityArrow(EntityTypes<? extends EntityArrow> entitytypes, EntityLiving entityliving, World world) {
      this(entitytypes, entityliving.dl(), entityliving.dp() - 0.1F, entityliving.dr(), world);
      this.b(entityliving);
      if (entityliving instanceof EntityHuman) {
         this.d = EntityArrow.PickupStatus.b;
      }
   }

   public void b(SoundEffect soundeffect) {
      this.p = soundeffect;
   }

   @Override
   public boolean a(double d0) {
      double d1 = this.cD().a() * 10.0;
      if (Double.isNaN(d1)) {
         d1 = 1.0;
      }

      d1 *= 64.0 * cw();
      return d0 < d1 * d1;
   }

   @Override
   protected void a_() {
      this.am.a(g, (byte)0);
      this.am.a(h, (byte)0);
   }

   @Override
   public void c(double d0, double d1, double d2, float f, float f1) {
      super.c(d0, d1, d2, f, f1);
      this.m = 0;
   }

   @Override
   public void a(double d0, double d1, double d2, float f, float f1, int i, boolean flag) {
      this.e(d0, d1, d2);
      this.a(f, f1);
   }

   @Override
   public void l(double d0, double d1, double d2) {
      super.l(d0, d1, d2);
      this.m = 0;
   }

   @Override
   public void l() {
      super.l();
      boolean flag = this.x();
      Vec3D vec3d = this.dj();
      if (this.M == 0.0F && this.L == 0.0F) {
         double d0 = vec3d.h();
         this.f((float)(MathHelper.d(vec3d.c, vec3d.e) * 180.0F / (float)Math.PI));
         this.e((float)(MathHelper.d(vec3d.d, d0) * 180.0F / (float)Math.PI));
         this.L = this.dw();
         this.M = this.dy();
      }

      BlockPosition blockposition = this.dg();
      IBlockData iblockdata = this.H.a_(blockposition);
      if (!iblockdata.h() && !flag) {
         VoxelShape voxelshape = iblockdata.k(this.H, blockposition);
         if (!voxelshape.b()) {
            Vec3D vec3d1 = this.de();

            for(AxisAlignedBB axisalignedbb : voxelshape.d()) {
               if (axisalignedbb.a(blockposition).d(vec3d1)) {
                  this.b = true;
                  break;
               }
            }
         }
      }

      if (this.e > 0) {
         --this.e;
      }

      if (this.aU() || iblockdata.a(Blocks.qy)) {
         this.av();
      }

      if (this.b && !flag) {
         if (this.l != iblockdata && this.y()) {
            this.C();
         } else if (!this.H.B) {
            this.i();
         }

         ++this.c;
      } else {
         this.c = 0;
         Vec3D vec3d2 = this.de();
         Vec3D vec3d1 = vec3d2.e(vec3d);
         Object object = this.H.a(new RayTrace(vec3d2, vec3d1, RayTrace.BlockCollisionOption.a, RayTrace.FluidCollisionOption.a, this));
         if (((MovingObjectPosition)object).c() != MovingObjectPosition.EnumMovingObjectType.a) {
            vec3d1 = ((MovingObjectPosition)object).e();
         }

         while(!this.dB()) {
            MovingObjectPositionEntity movingobjectpositionentity = this.a(vec3d2, vec3d1);
            if (movingobjectpositionentity != null) {
               object = movingobjectpositionentity;
            }

            if (object != null && ((MovingObjectPosition)object).c() == MovingObjectPosition.EnumMovingObjectType.c) {
               Entity entity = ((MovingObjectPositionEntity)object).a();
               Entity entity1 = this.v();
               if (entity instanceof EntityHuman && entity1 instanceof EntityHuman && !((EntityHuman)entity1).a((EntityHuman)entity)) {
                  object = null;
                  movingobjectpositionentity = null;
               }
            }

            if (object != null && !flag) {
               this.preOnHit((MovingObjectPosition)object);
               this.at = true;
            }

            if (movingobjectpositionentity == null || this.t() <= 0) {
               break;
            }

            object = null;
         }

         vec3d = this.dj();
         double d1 = vec3d.c;
         double d2 = vec3d.d;
         double d3 = vec3d.e;
         if (this.r()) {
            for(int i = 0; i < 4; ++i) {
               this.H
                  .a(Particles.g, this.dl() + d1 * (double)i / 4.0, this.dn() + d2 * (double)i / 4.0, this.dr() + d3 * (double)i / 4.0, -d1, -d2 + 0.2, -d3);
            }
         }

         double d4 = this.dl() + d1;
         double d5 = this.dn() + d2;
         double d6 = this.dr() + d3;
         double d7 = vec3d.h();
         if (flag) {
            this.f((float)(MathHelper.d(-d1, -d3) * 180.0F / (float)Math.PI));
         } else {
            this.f((float)(MathHelper.d(d1, d3) * 180.0F / (float)Math.PI));
         }

         this.e((float)(MathHelper.d(d2, d7) * 180.0F / (float)Math.PI));
         this.e(d(this.M, this.dy()));
         this.f(d(this.L, this.dw()));
         float f = 0.99F;
         float f1 = 0.05F;
         if (this.aT()) {
            for(int j = 0; j < 4; ++j) {
               float f2 = 0.25F;
               this.H.a(Particles.e, d4 - d1 * 0.25, d5 - d2 * 0.25, d6 - d3 * 0.25, d1, d2, d3);
            }

            f = this.w();
         }

         this.f(vec3d.a((double)f));
         if (!this.aP() && !flag) {
            Vec3D vec3d3 = this.dj();
            this.o(vec3d3.c, vec3d3.d - 0.05F, vec3d3.e);
         }

         this.e(d4, d5, d6);
         this.aL();
      }
   }

   private boolean y() {
      return this.b && this.H.b(new AxisAlignedBB(this.de(), this.de()).g(0.06));
   }

   private void C() {
      this.b = false;
      Vec3D vec3d = this.dj();
      this.f(vec3d.d((double)(this.af.i() * 0.2F), (double)(this.af.i() * 0.2F), (double)(this.af.i() * 0.2F)));
      this.m = 0;
   }

   @Override
   public void a(EnumMoveType enummovetype, Vec3D vec3d) {
      super.a(enummovetype, vec3d);
      if (enummovetype != EnumMoveType.a && this.y()) {
         this.C();
      }
   }

   protected void i() {
      ++this.m;
      if (this.m >= (this instanceof EntityThrownTrident ? this.H.spigotConfig.tridentDespawnRate : this.H.spigotConfig.arrowDespawnRate)) {
         this.ai();
      }
   }

   private void D() {
      if (this.r != null) {
         this.r.clear();
      }

      if (this.q != null) {
         this.q.clear();
      }
   }

   @Override
   protected void a(MovingObjectPositionEntity movingobjectpositionentity) {
      super.a(movingobjectpositionentity);
      Entity entity = movingobjectpositionentity.a();
      float f = (float)this.dj().f();
      int i = MathHelper.c(MathHelper.a((double)f * this.n, 0.0, 2.147483647E9));
      if (this.t() > 0) {
         if (this.q == null) {
            this.q = new IntOpenHashSet(5);
         }

         if (this.r == null) {
            this.r = Lists.newArrayListWithCapacity(5);
         }

         if (this.q.size() >= this.t() + 1) {
            this.ai();
            return;
         }

         this.q.add(entity.af());
      }

      if (this.r()) {
         long j = (long)this.af.a(i / 2 + 2);
         i = (int)Math.min(j + (long)i, 2147483647L);
      }

      Entity entity1 = this.v();
      DamageSource damagesource;
      if (entity1 == null) {
         damagesource = this.dG().a(this, this);
      } else {
         damagesource = this.dG().a(this, entity1);
         if (entity1 instanceof EntityLiving) {
            ((EntityLiving)entity1).x(entity);
         }
      }

      boolean flag = entity.ae() == EntityTypes.E;
      int k = entity.au();
      if (this.bK() && !flag) {
         EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(this.getBukkitEntity(), entity.getBukkitEntity(), 5);
         Bukkit.getPluginManager().callEvent(combustEvent);
         if (!combustEvent.isCancelled()) {
            entity.setSecondsOnFire(combustEvent.getDuration(), false);
         }
      }

      if (entity.a(damagesource, (float)i)) {
         if (flag) {
            return;
         }

         if (entity instanceof EntityLiving entityliving) {
            if (!this.H.B && this.t() <= 0) {
               entityliving.o(entityliving.eF() + 1);
            }

            if (this.o > 0) {
               double d0 = Math.max(0.0, 1.0 - entityliving.b(GenericAttributes.c));
               Vec3D vec3d = this.dj().d(1.0, 0.0, 1.0).d().a((double)this.o * 0.6 * d0);
               if (vec3d.g() > 0.0) {
                  entityliving.j(vec3d.c, 0.1, vec3d.e);
               }
            }

            if (!this.H.B && entity1 instanceof EntityLiving) {
               EnchantmentManager.a(entityliving, entity1);
               EnchantmentManager.b((EntityLiving)entity1, entityliving);
            }

            this.a(entityliving);
            if (entity1 != null && entityliving != entity1 && entityliving instanceof EntityHuman && entity1 instanceof EntityPlayer && !this.aO()) {
               ((EntityPlayer)entity1).b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.g, 0.0F));
            }

            if (!entity.bq() && this.r != null) {
               this.r.add(entityliving);
            }

            if (!this.H.B && entity1 instanceof EntityPlayer entityplayer) {
               if (this.r != null && this.s()) {
                  CriterionTriggers.G.a(entityplayer, this.r);
               } else if (!entity.bq() && this.s()) {
                  CriterionTriggers.G.a(entityplayer, Arrays.asList(entity));
               }
            }
         }

         this.a(this.p, 1.0F, 1.2F / (this.af.i() * 0.2F + 0.9F));
         if (this.t() <= 0) {
            this.ai();
         }
      } else {
         entity.g(k);
         this.f(this.dj().a(-0.1));
         this.f(this.dw() + 180.0F);
         this.L += 180.0F;
         if (!this.H.B && this.dj().g() < 1.0E-7) {
            if (this.d == EntityArrow.PickupStatus.b) {
               this.a(this.o(), 0.1F);
            }

            this.ai();
         }
      }
   }

   @Override
   protected void a(MovingObjectPositionBlock movingobjectpositionblock) {
      this.l = this.H.a_(movingobjectpositionblock.a());
      super.a(movingobjectpositionblock);
      Vec3D vec3d = movingobjectpositionblock.e().a(this.dl(), this.dn(), this.dr());
      this.f(vec3d);
      Vec3D vec3d1 = vec3d.d().a(0.05F);
      this.p(this.dl() - vec3d1.c, this.dn() - vec3d1.d, this.dr() - vec3d1.e);
      this.a(this.k(), 1.0F, 1.2F / (this.af.i() * 0.2F + 0.9F));
      this.b = true;
      this.e = 7;
      this.a(false);
      this.a((byte)0);
      this.b(SoundEffects.ao);
      this.q(false);
      this.D();
   }

   protected SoundEffect j() {
      return SoundEffects.ao;
   }

   protected final SoundEffect k() {
      return this.p;
   }

   protected void a(EntityLiving entityliving) {
   }

   @Nullable
   protected MovingObjectPositionEntity a(Vec3D vec3d, Vec3D vec3d1) {
      return ProjectileHelper.a(this.H, this, vec3d, vec3d1, this.cD().b(this.dj()).g(1.0), this::a);
   }

   @Override
   protected boolean a(Entity entity) {
      return super.a(entity) && (this.q == null || !this.q.contains(entity.af()));
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("life", (short)this.m);
      if (this.l != null) {
         nbttagcompound.a("inBlockState", GameProfileSerializer.a(this.l));
      }

      nbttagcompound.a("shake", (byte)this.e);
      nbttagcompound.a("inGround", this.b);
      nbttagcompound.a("pickup", (byte)this.d.ordinal());
      nbttagcompound.a("damage", this.n);
      nbttagcompound.a("crit", this.r());
      nbttagcompound.a("PierceLevel", this.t());
      nbttagcompound.a("SoundEvent", BuiltInRegistries.c.b(this.p).toString());
      nbttagcompound.a("ShotFromCrossbow", this.s());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.m = nbttagcompound.g("life");
      if (nbttagcompound.b("inBlockState", 10)) {
         this.l = GameProfileSerializer.a(this.H.a(Registries.e), nbttagcompound.p("inBlockState"));
      }

      this.e = nbttagcompound.f("shake") & 255;
      this.b = nbttagcompound.q("inGround");
      if (nbttagcompound.b("damage", 99)) {
         this.n = nbttagcompound.k("damage");
      }

      this.d = EntityArrow.PickupStatus.a(nbttagcompound.f("pickup"));
      this.a(nbttagcompound.q("crit"));
      this.a(nbttagcompound.f("PierceLevel"));
      if (nbttagcompound.b("SoundEvent", 8)) {
         this.p = BuiltInRegistries.c.b(new MinecraftKey(nbttagcompound.l("SoundEvent"))).orElse(this.j());
      }

      this.q(nbttagcompound.q("ShotFromCrossbow"));
   }

   @Override
   public void b(@Nullable Entity entity) {
      super.b(entity);
      if (entity instanceof EntityHuman) {
         this.d = ((EntityHuman)entity).fK().d ? EntityArrow.PickupStatus.c : EntityArrow.PickupStatus.b;
      }
   }

   @Override
   public void b_(EntityHuman entityhuman) {
      if (!this.H.B && (this.b || this.x()) && this.e <= 0) {
         ItemStack itemstack = this.o();
         if (this.d == EntityArrow.PickupStatus.b && !itemstack.b() && entityhuman.fJ().canHold(itemstack) > 0) {
            EntityItem item = new EntityItem(this.H, this.dl(), this.dn(), this.dr(), itemstack);
            PlayerPickupArrowEvent event = new PlayerPickupArrowEvent(
               (Player)entityhuman.getBukkitEntity(), new CraftItem(this.H.getCraftServer(), this, item), (AbstractArrow)this.getBukkitEntity()
            );
            this.H.getCraftServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               return;
            }

            itemstack = item.i();
         }

         if (this.d == EntityArrow.PickupStatus.b && entityhuman.fJ().e(itemstack) || this.d == EntityArrow.PickupStatus.c && entityhuman.fK().d) {
            entityhuman.a(this, 1);
            this.ai();
         }
      }
   }

   protected boolean a(EntityHuman entityhuman) {
      switch(this.d) {
         case b:
            return entityhuman.fJ().e(this.o());
         case c:
            return entityhuman.fK().d;
         default:
            return false;
      }
   }

   protected abstract ItemStack o();

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.a;
   }

   public void h(double d0) {
      this.n = d0;
   }

   public double p() {
      return this.n;
   }

   public void b(int i) {
      this.o = i;
   }

   public int q() {
      return this.o;
   }

   @Override
   public boolean cl() {
      return false;
   }

   @Override
   protected float a(EntityPose entitypose, EntitySize entitysize) {
      return 0.13F;
   }

   public void a(boolean flag) {
      this.a(1, flag);
   }

   public void a(byte b0) {
      this.am.b(h, b0);
   }

   private void a(int i, boolean flag) {
      byte b0 = this.am.a(g);
      if (flag) {
         this.am.b(g, (byte)(b0 | i));
      } else {
         this.am.b(g, (byte)(b0 & ~i));
      }
   }

   public boolean r() {
      byte b0 = this.am.a(g);
      return (b0 & 1) != 0;
   }

   public boolean s() {
      byte b0 = this.am.a(g);
      return (b0 & 4) != 0;
   }

   public byte t() {
      return this.am.a(h);
   }

   public void a(EntityLiving entityliving, float f) {
      int i = EnchantmentManager.a(Enchantments.y, entityliving);
      int j = EnchantmentManager.a(Enchantments.z, entityliving);
      this.h((double)(f * 2.0F) + this.af.a((double)this.H.ah().a() * 0.11, 0.57425));
      if (i > 0) {
         this.h(this.p() + (double)i * 0.5 + 0.5);
      }

      if (j > 0) {
         this.b(j);
      }

      if (EnchantmentManager.a(Enchantments.A, entityliving) > 0) {
         this.f(100);
      }
   }

   protected float w() {
      return 0.6F;
   }

   public void p(boolean flag) {
      this.ae = flag;
      this.a(2, flag);
   }

   public boolean x() {
      return !this.H.B ? this.ae : (this.am.a(g) & 2) != 0;
   }

   public void q(boolean flag) {
      this.a(4, flag);
   }

   public static enum PickupStatus {
      a,
      b,
      c;

      public static EntityArrow.PickupStatus a(int i) {
         if (i < 0 || i > values().length) {
            i = 0;
         }

         return values()[i];
      }
   }
}
