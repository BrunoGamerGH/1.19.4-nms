package net.minecraft.world.entity;

import javax.annotation.Nullable;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;

public abstract class EntityAgeable extends EntityCreature {
   private static final DataWatcherObject<Boolean> bS = DataWatcher.a(EntityAgeable.class, DataWatcherRegistry.k);
   public static final int b = -24000;
   private static final int bT = 40;
   protected int c;
   protected int d;
   protected int e;
   public boolean ageLocked;

   protected EntityAgeable(EntityTypes<? extends EntityAgeable> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   public void inactiveTick() {
      super.inactiveTick();
      if (!this.H.B && !this.ageLocked) {
         int i = this.h();
         if (i < 0) {
            this.c_(++i);
         } else if (i > 0) {
            this.c_(--i);
         }
      } else {
         this.c_();
      }
   }

   @Override
   public GroupDataEntity a(
      WorldAccess worldaccess,
      DifficultyDamageScaler difficultydamagescaler,
      EnumMobSpawn enummobspawn,
      @Nullable GroupDataEntity groupdataentity,
      @Nullable NBTTagCompound nbttagcompound
   ) {
      if (groupdataentity == null) {
         groupdataentity = new EntityAgeable.a(true);
      }

      EntityAgeable.a entityageable_a = (EntityAgeable.a)groupdataentity;
      if (entityageable_a.c() && entityageable_a.a() > 0 && worldaccess.r_().i() <= entityageable_a.d()) {
         this.c_(-24000);
      }

      entityageable_a.b();
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   @Nullable
   public abstract EntityAgeable a(WorldServer var1, EntityAgeable var2);

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bS, false);
   }

   public boolean O_() {
      return false;
   }

   public int h() {
      return this.H.B ? (this.am.a(bS) ? -1 : 1) : this.c;
   }

   public void a(int i, boolean flag) {
      int j = this.h();
      j += i * 20;
      if (j > 0) {
         j = 0;
      }

      int l = j - j;
      this.c_(j);
      if (flag) {
         this.d += l;
         if (this.e == 0) {
            this.e = 40;
         }
      }

      if (this.h() == 0) {
         this.c_(this.d);
      }
   }

   public void b_(int i) {
      this.a(i, false);
   }

   public void c_(int i) {
      int j = this.h();
      this.c = i;
      if (j < 0 && i >= 0 || j >= 0 && i < 0) {
         this.am.b(bS, i < 0);
         this.m();
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Age", this.h());
      nbttagcompound.a("ForcedAge", this.d);
      nbttagcompound.a("AgeLocked", this.ageLocked);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.c_(nbttagcompound.h("Age"));
      this.d = nbttagcompound.h("ForcedAge");
      this.ageLocked = nbttagcompound.q("AgeLocked");
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (bS.equals(datawatcherobject)) {
         this.c_();
      }

      super.a(datawatcherobject);
   }

   @Override
   public void b_() {
      super.b_();
      if (!this.H.B && !this.ageLocked) {
         if (this.bq()) {
            int i = this.h();
            if (i < 0) {
               this.c_(++i);
            } else if (i > 0) {
               this.c_(--i);
            }
         }
      } else if (this.e > 0) {
         if (this.e % 4 == 0) {
            this.H.a(Particles.M, this.d(1.0), this.do() + 0.5, this.g(1.0), 0.0, 0.0, 0.0);
         }

         --this.e;
      }
   }

   protected void m() {
      if (!this.y_() && this.bL()) {
         Entity entity = this.cV();
         if (entity instanceof EntityBoat entityboat && !entityboat.a((Entity)this)) {
            this.bz();
         }
      }
   }

   @Override
   public boolean y_() {
      return this.h() < 0;
   }

   @Override
   public void a(boolean flag) {
      this.c_(flag ? -24000 : 0);
   }

   public static int d_(int i) {
      return (int)((float)(i / 20) * 0.1F);
   }

   public static class a implements GroupDataEntity {
      private int a;
      private final boolean b;
      private final float c;

      private a(boolean flag, float f) {
         this.b = flag;
         this.c = f;
      }

      public a(boolean flag) {
         this(flag, 0.05F);
      }

      public a(float f) {
         this(true, f);
      }

      public int a() {
         return this.a;
      }

      public void b() {
         ++this.a;
      }

      public boolean c() {
         return this.b;
      }

      public float d() {
         return this.c;
      }
   }
}
