package net.minecraft.world.entity.raid;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRaid;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.entity.animal.EntityWolf;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.EntityIllagerAbstract;
import net.minecraft.world.entity.monster.EntityMonsterPatrolling;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public abstract class EntityRaider extends EntityMonsterPatrolling {
   protected static final DataWatcherObject<Boolean> c = DataWatcher.a(EntityRaider.class, DataWatcherRegistry.k);
   static final Predicate<EntityItem> b = entityitem -> !entityitem.q() && entityitem.bq() && ItemStack.b(entityitem.i(), Raid.s());
   @Nullable
   protected Raid d;
   private int e;
   private boolean bS;
   private int bT;

   protected EntityRaider(EntityTypes<? extends EntityRaider> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   protected void x() {
      super.x();
      this.bN.a(1, new EntityRaider.b<>(this));
      this.bN.a(3, new PathfinderGoalRaid<>(this));
      this.bN.a(4, new EntityRaider.d(this, 1.05F, 1));
      this.bN.a(5, new EntityRaider.c(this));
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(c, false);
   }

   public abstract void a(int var1, boolean var2);

   public boolean gf() {
      return this.bS;
   }

   public void z(boolean flag) {
      this.bS = flag;
   }

   @Override
   public void b_() {
      if (this.H instanceof WorldServer && this.bq()) {
         Raid raid = this.gg();
         if (this.gf()) {
            if (raid == null) {
               if (this.H.U() % 20L == 0L) {
                  Raid raid1 = ((WorldServer)this.H).c(this.dg());
                  if (raid1 != null && PersistentRaid.a(this, raid1)) {
                     raid1.a(raid1.k(), this, null, true);
                  }
               }
            } else {
               EntityLiving entityliving = this.P_();
               if (entityliving != null && (entityliving.ae() == EntityTypes.bt || entityliving.ae() == EntityTypes.ac)) {
                  this.ba = 0;
               }
            }
         }
      }

      super.b_();
   }

   @Override
   protected void fX() {
      this.ba += 2;
   }

   @Override
   public void a(DamageSource damagesource) {
      if (this.H instanceof WorldServer) {
         Entity entity = damagesource.d();
         Raid raid = this.gg();
         if (raid != null) {
            if (this.fW()) {
               raid.c(this.gi());
            }

            if (entity != null && entity.ae() == EntityTypes.bt) {
               raid.a(entity);
            }

            raid.a(this, false);
         }

         if (this.fW() && raid == null && ((WorldServer)this.H).c(this.dg()) == null) {
            ItemStack itemstack = this.c(EnumItemSlot.f);
            EntityHuman entityhuman = null;
            if (entity instanceof EntityHuman) {
               entityhuman = (EntityHuman)entity;
            } else if (entity instanceof EntityWolf entitywolf) {
               EntityLiving entityliving = entitywolf.H_();
               if (entitywolf.q() && entityliving instanceof EntityHuman) {
                  entityhuman = (EntityHuman)entityliving;
               }
            }

            if (!itemstack.b() && ItemStack.b(itemstack, Raid.s()) && entityhuman != null) {
               MobEffect mobeffect = entityhuman.b(MobEffects.E);
               byte b0 = 1;
               int i;
               if (mobeffect != null) {
                  i = b0 + mobeffect.e();
                  entityhuman.c(MobEffects.E);
               } else {
                  i = b0 - 1;
               }

               i = MathHelper.a(i, 0, 4);
               MobEffect mobeffect1 = new MobEffect(MobEffects.E, 120000, i, false, false, true);
               if (!this.H.W().b(GameRules.z)) {
                  entityhuman.addEffect(mobeffect1, Cause.PATROL_CAPTAIN);
               }
            }
         }
      }

      super.a(damagesource);
   }

   @Override
   public boolean fZ() {
      return !this.gh();
   }

   public void a(@Nullable Raid raid) {
      this.d = raid;
   }

   @Nullable
   public Raid gg() {
      return this.d;
   }

   public boolean gh() {
      return this.gg() != null && this.gg().v();
   }

   public void b(int i) {
      this.e = i;
   }

   public int gi() {
      return this.e;
   }

   public boolean gj() {
      return this.am.a(c);
   }

   public void A(boolean flag) {
      this.am.b(c, flag);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Wave", this.e);
      nbttagcompound.a("CanJoinRaid", this.bS);
      if (this.d != null) {
         nbttagcompound.a("RaidId", this.d.u());
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.e = nbttagcompound.h("Wave");
      this.bS = nbttagcompound.q("CanJoinRaid");
      if (nbttagcompound.b("RaidId", 3)) {
         if (this.H instanceof WorldServer) {
            this.d = ((WorldServer)this.H).x().a(nbttagcompound.h("RaidId"));
         }

         if (this.d != null) {
            this.d.a(this.e, this, false);
            if (this.fW()) {
               this.d.a(this.e, this);
            }
         }
      }
   }

   @Override
   protected void b(EntityItem entityitem) {
      ItemStack itemstack = entityitem.i();
      boolean flag = this.gh() && this.gg().b(this.gi()) != null;
      if (this.gh() && !flag && ItemStack.b(itemstack, Raid.s())) {
         EnumItemSlot enumitemslot = EnumItemSlot.f;
         ItemStack itemstack1 = this.c(enumitemslot);
         double d0 = (double)this.f(enumitemslot);
         if (!itemstack1.b() && (double)Math.max(this.af.i() - 0.1F, 0.0F) < d0) {
            this.b(itemstack1);
         }

         this.a(entityitem);
         this.a(enumitemslot, itemstack);
         this.a(entityitem, itemstack.K());
         entityitem.ai();
         this.gg().a(this.gi(), this);
         this.w(true);
      } else {
         super.b(entityitem);
      }
   }

   @Override
   public boolean h(double d0) {
      return this.gg() == null ? super.h(d0) : false;
   }

   @Override
   public boolean Q() {
      return super.Q() || this.gg() != null;
   }

   public int gk() {
      return this.bT;
   }

   public void c(int i) {
      this.bT = i;
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.gh()) {
         this.gg().p();
      }

      return super.a(damagesource, f);
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
      this.z(this.ae() != EntityTypes.bj || enummobspawn != EnumMobSpawn.a);
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   public abstract SoundEffect X_();

   protected class a extends PathfinderGoal {
      private final EntityRaider c;
      private final float d;
      public final PathfinderTargetCondition a = PathfinderTargetCondition.b().a(8.0).d().e();

      public a(EntityIllagerAbstract entityillagerabstract, float f) {
         this.c = entityillagerabstract;
         this.d = f * f;
         this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      }

      @Override
      public boolean a() {
         EntityLiving entityliving = this.c.ea();
         return this.c.gg() == null && this.c.gb() && this.c.P_() != null && !this.c.fM() && (entityliving == null || entityliving.ae() != EntityTypes.bt);
      }

      @Override
      public void c() {
         super.c();
         this.c.G().n();

         for(EntityRaider entityraider : this.c.H.a(EntityRaider.class, this.a, this.c, this.c.cD().c(8.0, 8.0, 8.0))) {
            entityraider.setTarget(this.c.P_(), TargetReason.FOLLOW_LEADER, true);
         }
      }

      @Override
      public void d() {
         super.d();
         EntityLiving entityliving = this.c.P_();
         if (entityliving != null) {
            for(EntityRaider entityraider : this.c.H.a(EntityRaider.class, this.a, this.c, this.c.cD().c(8.0, 8.0, 8.0))) {
               entityraider.setTarget(this.c.P_(), TargetReason.FOLLOW_LEADER, true);
               entityraider.v(true);
            }

            this.c.v(true);
         }
      }

      @Override
      public boolean J_() {
         return true;
      }

      @Override
      public void e() {
         EntityLiving entityliving = this.c.P_();
         if (entityliving != null) {
            if (this.c.f(entityliving) > (double)this.d) {
               this.c.C().a(entityliving, 30.0F, 30.0F);
               if (this.c.af.a(50) == 0) {
                  this.c.L();
               }
            } else {
               this.c.v(true);
            }

            super.e();
         }
      }
   }

   public class b<T extends EntityRaider> extends PathfinderGoal {
      private final T b;

      public b(T entityraider) {
         this.b = entityraider;
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         Raid raid = this.b.gg();
         if (this.b.gh() && !this.b.gg().a() && this.b.fT() && !ItemStack.b(this.b.c(EnumItemSlot.f), Raid.s())) {
            EntityRaider entityraider = raid.b(this.b.gi());
            if (entityraider == null || !entityraider.bq()) {
               List<EntityItem> list = this.b.H.a(EntityItem.class, this.b.cD().c(16.0, 8.0, 16.0), EntityRaider.b);
               if (!list.isEmpty()) {
                  return this.b.G().a(list.get(0), 1.15F);
               }
            }

            return false;
         } else {
            return false;
         }
      }

      @Override
      public void e() {
         if (this.b.G().h().a(this.b.de(), 1.414)) {
            List<EntityItem> list = this.b.H.a(EntityItem.class, this.b.cD().c(4.0, 4.0, 4.0), EntityRaider.b);
            if (!list.isEmpty()) {
               this.b.b(list.get(0));
            }
         }
      }
   }

   public class c extends PathfinderGoal {
      private final EntityRaider b;

      c(EntityRaider entityraider) {
         this.b = entityraider;
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         Raid raid = this.b.gg();
         return this.b.bq() && this.b.P_() == null && raid != null && raid.f();
      }

      @Override
      public void c() {
         this.b.A(true);
         super.c();
      }

      @Override
      public void d() {
         this.b.A(false);
         super.d();
      }

      @Override
      public void e() {
         if (!this.b.aO() && this.b.af.a(this.a(100)) == 0) {
            EntityRaider.this.a(EntityRaider.this.X_(), EntityRaider.this.eN(), EntityRaider.this.eO());
         }

         if (!this.b.bL() && this.b.af.a(this.a(50)) == 0) {
            this.b.E().a();
         }

         super.e();
      }
   }

   private static class d extends PathfinderGoal {
      private final EntityRaider a;
      private final double b;
      private BlockPosition c;
      private final List<BlockPosition> d = Lists.newArrayList();
      private final int e;
      private boolean f;

      public d(EntityRaider entityraider, double d0, int i) {
         this.a = entityraider;
         this.b = d0;
         this.e = i;
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         this.k();
         return this.h() && this.i() && this.a.P_() == null;
      }

      private boolean h() {
         return this.a.gh() && !this.a.gg().a();
      }

      private boolean i() {
         WorldServer worldserver = (WorldServer)this.a.H;
         BlockPosition blockposition = this.a.dg();
         Optional<BlockPosition> optional = worldserver.w().a(holder -> holder.a(PoiTypes.n), this::a, VillagePlace.Occupancy.c, blockposition, 48, this.a.af);
         if (!optional.isPresent()) {
            return false;
         } else {
            this.c = optional.get().i();
            return true;
         }
      }

      @Override
      public boolean b() {
         return this.a.G().l() ? false : this.a.P_() == null && !this.c.a(this.a.de(), (double)(this.a.dc() + (float)this.e)) && !this.f;
      }

      @Override
      public void d() {
         if (this.c.a(this.a.de(), (double)this.e)) {
            this.d.add(this.c);
         }
      }

      @Override
      public void c() {
         super.c();
         this.a.n(0);
         this.a.G().a((double)this.c.u(), (double)this.c.v(), (double)this.c.w(), this.b);
         this.f = false;
      }

      @Override
      public void e() {
         if (this.a.G().l()) {
            Vec3D vec3d = Vec3D.c(this.c);
            Vec3D vec3d1 = DefaultRandomPos.a(this.a, 16, 7, vec3d, (float) (Math.PI / 10));
            if (vec3d1 == null) {
               vec3d1 = DefaultRandomPos.a(this.a, 8, 7, vec3d, (float) (Math.PI / 2));
            }

            if (vec3d1 == null) {
               this.f = true;
               return;
            }

            this.a.G().a(vec3d1.c, vec3d1.d, vec3d1.e, this.b);
         }
      }

      private boolean a(BlockPosition blockposition) {
         for(BlockPosition blockposition1 : this.d) {
            if (Objects.equals(blockposition, blockposition1)) {
               return false;
            }
         }

         return true;
      }

      private void k() {
         if (this.d.size() > 2) {
            this.d.remove(0);
         }
      }
   }
}
