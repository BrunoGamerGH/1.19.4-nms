package net.minecraft.world.entity.animal;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagsFluid;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreath;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowBoat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomSwim;
import net.minecraft.world.entity.ai.goal.PathfinderGoalWater;
import net.minecraft.world.entity.ai.goal.PathfinderGoalWaterJump;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.navigation.NavigationGuardian;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.EntityGuardian;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntityDolphin extends EntityWaterAnimal {
   private static final DataWatcherObject<BlockPosition> d = DataWatcher.a(EntityDolphin.class, DataWatcherRegistry.n);
   private static final DataWatcherObject<Boolean> e = DataWatcher.a(EntityDolphin.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Integer> bS = DataWatcher.a(EntityDolphin.class, DataWatcherRegistry.b);
   static final PathfinderTargetCondition bT = PathfinderTargetCondition.b().a(10.0).d();
   public static final int b = 4800;
   private static final int bU = 2400;
   public static final Predicate<EntityItem> c = entityitem -> !entityitem.q() && entityitem.bq() && entityitem.aT();

   @Override
   public int getDefaultMaxAirSupply() {
      return 4800;
   }

   public EntityDolphin(EntityTypes<? extends EntityDolphin> entitytypes, World world) {
      super(entitytypes, world);
      this.bK = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
      this.bJ = new SmoothSwimmingLookControl(this, 10);
      this.s(true);
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
      this.i(this.cc());
      this.e(0.0F);
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   @Override
   public boolean dK() {
      return false;
   }

   @Override
   protected void b(int i) {
   }

   public void g(BlockPosition blockposition) {
      this.am.b(d, blockposition);
   }

   public BlockPosition q() {
      return this.am.a(d);
   }

   public boolean r() {
      return this.am.a(e);
   }

   public void w(boolean flag) {
      this.am.b(e, flag);
   }

   public int w() {
      return this.am.a(bS);
   }

   public void c(int i) {
      this.am.b(bS, i);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(d, BlockPosition.b);
      this.am.a(e, false);
      this.am.a(bS, 2400);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("TreasurePosX", this.q().u());
      nbttagcompound.a("TreasurePosY", this.q().v());
      nbttagcompound.a("TreasurePosZ", this.q().w());
      nbttagcompound.a("GotFish", this.r());
      nbttagcompound.a("Moistness", this.w());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      int i = nbttagcompound.h("TreasurePosX");
      int j = nbttagcompound.h("TreasurePosY");
      int k = nbttagcompound.h("TreasurePosZ");
      this.g(new BlockPosition(i, j, k));
      super.a(nbttagcompound);
      this.w(nbttagcompound.q("GotFish"));
      this.c(nbttagcompound.h("Moistness"));
   }

   @Override
   protected void x() {
      this.bN.a(0, new PathfinderGoalBreath(this));
      this.bN.a(0, new PathfinderGoalWater(this));
      this.bN.a(1, new EntityDolphin.a(this));
      this.bN.a(2, new EntityDolphin.b(this, 4.0));
      this.bN.a(4, new PathfinderGoalRandomSwim(this, 1.0, 10));
      this.bN.a(4, new PathfinderGoalRandomLookaround(this));
      this.bN.a(5, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
      this.bN.a(5, new PathfinderGoalWaterJump(this, 10));
      this.bN.a(6, new PathfinderGoalMeleeAttack(this, 1.2F, true));
      this.bN.a(8, new EntityDolphin.c());
      this.bN.a(8, new PathfinderGoalFollowBoat(this));
      this.bN.a(9, new PathfinderGoalAvoidTarget<>(this, EntityGuardian.class, 8.0F, 1.0, 1.0));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this, EntityGuardian.class).a());
   }

   public static AttributeProvider.Builder fS() {
      return EntityInsentient.y().a(GenericAttributes.a, 10.0).a(GenericAttributes.d, 1.2F).a(GenericAttributes.f, 3.0);
   }

   @Override
   protected NavigationAbstract a(World world) {
      return new NavigationGuardian(this, world);
   }

   @Override
   public boolean z(Entity entity) {
      boolean flag = entity.a(this.dG().b((EntityLiving)this), (float)((int)this.b(GenericAttributes.f)));
      if (flag) {
         this.a(this, entity);
         this.a(SoundEffects.fN, 1.0F, 1.0F);
      }

      return flag;
   }

   @Override
   public int cc() {
      return this.maxAirTicks;
   }

   @Override
   protected int m(int i) {
      return this.cc();
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return 0.3F;
   }

   @Override
   public int V() {
      return 1;
   }

   @Override
   public int W() {
      return 1;
   }

   @Override
   protected boolean l(Entity entity) {
      return true;
   }

   @Override
   public boolean f(ItemStack itemstack) {
      EnumItemSlot enumitemslot = EntityInsentient.h(itemstack);
      return !this.c(enumitemslot).b() ? false : enumitemslot == EnumItemSlot.a && super.f(itemstack);
   }

   @Override
   protected void b(EntityItem entityitem) {
      if (this.c(EnumItemSlot.a).b()) {
         ItemStack itemstack = entityitem.i();
         if (this.j(itemstack)) {
            if (CraftEventFactory.callEntityPickupItemEvent(this, entityitem, 0, false).isCancelled()) {
               return;
            }

            itemstack = entityitem.i();
            this.a(entityitem);
            this.a(EnumItemSlot.a, itemstack);
            this.e(EnumItemSlot.a);
            this.a(entityitem, itemstack.K());
            entityitem.ai();
         }
      }
   }

   @Override
   public void l() {
      super.l();
      if (this.fK()) {
         this.i(this.cc());
      } else {
         if (this.aV()) {
            this.c(2400);
         } else {
            this.c(this.w() - 1);
            if (this.w() <= 0) {
               this.a(this.dG().r(), 1.0F);
            }

            if (this.N) {
               this.f(this.dj().b((double)((this.af.i() * 2.0F - 1.0F) * 0.2F), 0.5, (double)((this.af.i() * 2.0F - 1.0F) * 0.2F)));
               this.f(this.af.i() * 360.0F);
               this.N = false;
               this.at = true;
            }
         }

         if (this.H.B && this.aT() && this.dj().g() > 0.03) {
            Vec3D vec3d = this.j(0.0F);
            float f = MathHelper.b(this.dw() * (float) (Math.PI / 180.0)) * 0.3F;
            float f1 = MathHelper.a(this.dw() * (float) (Math.PI / 180.0)) * 0.3F;
            float f2 = 1.2F - this.af.i() * 0.7F;

            for(int i = 0; i < 2; ++i) {
               this.H
                  .a(
                     Particles.ao,
                     this.dl() - vec3d.c * (double)f2 + (double)f,
                     this.dn() - vec3d.d,
                     this.dr() - vec3d.e * (double)f2 + (double)f1,
                     0.0,
                     0.0,
                     0.0
                  );
               this.H
                  .a(
                     Particles.ao,
                     this.dl() - vec3d.c * (double)f2 - (double)f,
                     this.dn() - vec3d.d,
                     this.dr() - vec3d.e * (double)f2 - (double)f1,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }
   }

   @Override
   public void b(byte b0) {
      if (b0 == 38) {
         this.a(Particles.M);
      } else {
         super.b(b0);
      }
   }

   private void a(ParticleParam particleparam) {
      for(int i = 0; i < 7; ++i) {
         double d0 = this.af.k() * 0.01;
         double d1 = this.af.k() * 0.01;
         double d2 = this.af.k() * 0.01;
         this.H.a(particleparam, this.d(1.0), this.do() + 0.2, this.g(1.0), d0, d1, d2);
      }
   }

   @Override
   protected EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (!itemstack.b() && itemstack.a(TagsItem.an)) {
         if (!this.H.B) {
            this.a(SoundEffects.fP, 1.0F, 1.0F);
         }

         this.w(true);
         if (!entityhuman.fK().d) {
            itemstack.h(1);
         }

         return EnumInteractionResult.a(this.H.B);
      } else {
         return super.b(entityhuman, enumhand);
      }
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.fQ;
   }

   @Nullable
   @Override
   protected SoundEffect x_() {
      return SoundEffects.fO;
   }

   @Nullable
   @Override
   protected SoundEffect s() {
      return this.aT() ? SoundEffects.fM : SoundEffects.fL;
   }

   @Override
   protected SoundEffect aJ() {
      return SoundEffects.fT;
   }

   @Override
   protected SoundEffect aI() {
      return SoundEffects.fU;
   }

   protected boolean fT() {
      BlockPosition blockposition = this.G().h();
      return blockposition != null ? blockposition.a(this.de(), 12.0) : false;
   }

   @Override
   public void h(Vec3D vec3d) {
      if (this.cU() && this.aT()) {
         this.a(this.eW(), vec3d);
         this.a(EnumMoveType.a, this.dj());
         this.f(this.dj().a(0.9));
         if (this.P_() == null) {
            this.f(this.dj().b(0.0, -0.005, 0.0));
         }
      } else {
         super.h(vec3d);
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return true;
   }

   private static class a extends PathfinderGoal {
      private final EntityDolphin a;
      private boolean b;

      a(EntityDolphin entitydolphin) {
         this.a = entitydolphin;
         this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      }

      @Override
      public boolean I_() {
         return false;
      }

      @Override
      public boolean a() {
         return this.a.r() && this.a.cd() >= 100 && this.a.H.getWorld().canGenerateStructures();
      }

      @Override
      public boolean b() {
         BlockPosition blockposition = this.a.q();
         return !BlockPosition.a((double)blockposition.u(), this.a.dn(), (double)blockposition.w()).a(this.a.de(), 4.0) && !this.b && this.a.cd() >= 100;
      }

      @Override
      public void c() {
         if (this.a.H instanceof WorldServer worldserver) {
            this.b = false;
            this.a.G().n();
            BlockPosition blockposition = this.a.dg();
            BlockPosition blockposition1 = worldserver.a(StructureTags.b, blockposition, 50, false);
            if (blockposition1 != null) {
               this.a.g(blockposition1);
               worldserver.a(this.a, (byte)38);
            } else {
               this.b = true;
            }
         }
      }

      @Override
      public void d() {
         BlockPosition blockposition = this.a.q();
         if (BlockPosition.a((double)blockposition.u(), this.a.dn(), (double)blockposition.w()).a(this.a.de(), 4.0) || this.b) {
            this.a.w(false);
         }
      }

      @Override
      public void e() {
         World world = this.a.H;
         if (this.a.fT() || this.a.G().l()) {
            Vec3D vec3d = Vec3D.b(this.a.q());
            Vec3D vec3d1 = DefaultRandomPos.a(this.a, 16, 1, vec3d, (float) (Math.PI / 8));
            if (vec3d1 == null) {
               vec3d1 = DefaultRandomPos.a(this.a, 8, 4, vec3d, (float) (Math.PI / 2));
            }

            if (vec3d1 != null) {
               BlockPosition blockposition = BlockPosition.a(vec3d1);
               if (!world.b_(blockposition).a(TagsFluid.a) || !world.a_(blockposition).a(world, blockposition, PathMode.b)) {
                  vec3d1 = DefaultRandomPos.a(this.a, 8, 5, vec3d, (float) (Math.PI / 2));
               }
            }

            if (vec3d1 == null) {
               this.b = true;
               return;
            }

            this.a.C().a(vec3d1.c, vec3d1.d, vec3d1.e, (float)(this.a.W() + 20), (float)this.a.V());
            this.a.G().a(vec3d1.c, vec3d1.d, vec3d1.e, 1.3);
            if (world.z.a(this.a(80)) == 0) {
               world.a(this.a, (byte)38);
            }
         }
      }
   }

   private static class b extends PathfinderGoal {
      private final EntityDolphin a;
      private final double b;
      @Nullable
      private EntityHuman c;

      b(EntityDolphin entitydolphin, double d0) {
         this.a = entitydolphin;
         this.b = d0;
         this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      }

      @Override
      public boolean a() {
         this.c = this.a.H.a(EntityDolphin.bT, this.a);
         return this.c == null ? false : this.c.bV() && this.a.P_() != this.c;
      }

      @Override
      public boolean b() {
         return this.c != null && this.c.bV() && this.a.f(this.c) < 256.0;
      }

      @Override
      public void c() {
         this.c.addEffect(new MobEffect(MobEffects.D, 100), this.a, Cause.DOLPHIN);
      }

      @Override
      public void d() {
         this.c = null;
         this.a.G().n();
      }

      @Override
      public void e() {
         this.a.C().a(this.c, (float)(this.a.W() + 20), (float)this.a.V());
         if (this.a.f(this.c) < 6.25) {
            this.a.G().n();
         } else {
            this.a.G().a(this.c, this.b);
         }

         if (this.c.bV() && this.c.H.z.a(6) == 0) {
            this.c.addEffect(new MobEffect(MobEffects.D, 100), this.a, Cause.DOLPHIN);
         }
      }
   }

   private class c extends PathfinderGoal {
      private int b;

      c() {
      }

      @Override
      public boolean a() {
         if (this.b > EntityDolphin.this.ag) {
            return false;
         } else {
            List<EntityItem> list = EntityDolphin.this.H.a(EntityItem.class, EntityDolphin.this.cD().c(8.0, 8.0, 8.0), EntityDolphin.c);
            return !list.isEmpty() || !EntityDolphin.this.c(EnumItemSlot.a).b();
         }
      }

      @Override
      public void c() {
         List<EntityItem> list = EntityDolphin.this.H.a(EntityItem.class, EntityDolphin.this.cD().c(8.0, 8.0, 8.0), EntityDolphin.c);
         if (!list.isEmpty()) {
            EntityDolphin.this.G().a(list.get(0), 1.2F);
            EntityDolphin.this.a(SoundEffects.fS, 1.0F, 1.0F);
         }

         this.b = 0;
      }

      @Override
      public void d() {
         ItemStack itemstack = EntityDolphin.this.c(EnumItemSlot.a);
         if (!itemstack.b()) {
            this.a(itemstack);
            EntityDolphin.this.a(EnumItemSlot.a, ItemStack.b);
            this.b = EntityDolphin.this.ag + EntityDolphin.this.af.a(100);
         }
      }

      @Override
      public void e() {
         List<EntityItem> list = EntityDolphin.this.H.a(EntityItem.class, EntityDolphin.this.cD().c(8.0, 8.0, 8.0), EntityDolphin.c);
         ItemStack itemstack = EntityDolphin.this.c(EnumItemSlot.a);
         if (!itemstack.b()) {
            this.a(itemstack);
            EntityDolphin.this.a(EnumItemSlot.a, ItemStack.b);
         } else if (!list.isEmpty()) {
            EntityDolphin.this.G().a(list.get(0), 1.2F);
         }
      }

      private void a(ItemStack itemstack) {
         if (!itemstack.b()) {
            double d0 = EntityDolphin.this.dp() - 0.3F;
            EntityItem entityitem = new EntityItem(EntityDolphin.this.H, EntityDolphin.this.dl(), d0, EntityDolphin.this.dr(), itemstack);
            entityitem.b(40);
            entityitem.c(EntityDolphin.this.cs());
            float f = 0.3F;
            float f1 = EntityDolphin.this.af.i() * (float) (Math.PI * 2);
            float f2 = 0.02F * EntityDolphin.this.af.i();
            entityitem.o(
               (double)(
                  0.3F
                        * -MathHelper.a(EntityDolphin.this.dw() * (float) (Math.PI / 180.0))
                        * MathHelper.b(EntityDolphin.this.dy() * (float) (Math.PI / 180.0))
                     + MathHelper.b(f1) * f2
               ),
               (double)(0.3F * MathHelper.a(EntityDolphin.this.dy() * (float) (Math.PI / 180.0)) * 1.5F),
               (double)(
                  0.3F * MathHelper.b(EntityDolphin.this.dw() * (float) (Math.PI / 180.0)) * MathHelper.b(EntityDolphin.this.dy() * (float) (Math.PI / 180.0))
                     + MathHelper.a(f1) * f2
               )
            );
            EntityDolphin.this.H.b(entityitem);
         }
      }
   }
}
