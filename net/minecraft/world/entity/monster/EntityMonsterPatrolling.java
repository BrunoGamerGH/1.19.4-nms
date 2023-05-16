package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.phys.Vec3D;

public abstract class EntityMonsterPatrolling extends EntityMonster {
   @Nullable
   private BlockPosition b;
   private boolean c;
   private boolean d;

   protected EntityMonsterPatrolling(EntityTypes<? extends EntityMonsterPatrolling> var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected void x() {
      super.x();
      this.bN.a(4, new EntityMonsterPatrolling.a<>(this, 0.7, 0.595));
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      if (this.b != null) {
         var0.a("PatrolTarget", GameProfileSerializer.a(this.b));
      }

      var0.a("PatrolLeader", this.c);
      var0.a("Patrolling", this.d);
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      if (var0.e("PatrolTarget")) {
         this.b = GameProfileSerializer.b(var0.p("PatrolTarget"));
      }

      this.c = var0.q("PatrolLeader");
      this.d = var0.q("Patrolling");
   }

   @Override
   public double bu() {
      return -0.45;
   }

   public boolean fT() {
      return true;
   }

   @Nullable
   @Override
   public GroupDataEntity a(WorldAccess var0, DifficultyDamageScaler var1, EnumMobSpawn var2, @Nullable GroupDataEntity var3, @Nullable NBTTagCompound var4) {
      if (var2 != EnumMobSpawn.p && var2 != EnumMobSpawn.h && var2 != EnumMobSpawn.d && var0.r_().i() < 0.06F && this.fT()) {
         this.c = true;
      }

      if (this.fW()) {
         this.a(EnumItemSlot.f, Raid.s());
         this.a(EnumItemSlot.f, 2.0F);
      }

      if (var2 == EnumMobSpawn.p) {
         this.d = true;
      }

      return super.a(var0, var1, var2, var3, var4);
   }

   public static boolean b(EntityTypes<? extends EntityMonsterPatrolling> var0, GeneratorAccess var1, EnumMobSpawn var2, BlockPosition var3, RandomSource var4) {
      return var1.a(EnumSkyBlock.b, var3) > 8 ? false : c(var0, var1, var2, var3, var4);
   }

   @Override
   public boolean h(double var0) {
      return !this.d || var0 > 16384.0;
   }

   public void g(BlockPosition var0) {
      this.b = var0;
      this.d = true;
   }

   public BlockPosition fU() {
      return this.b;
   }

   public boolean fV() {
      return this.b != null;
   }

   public void w(boolean var0) {
      this.c = var0;
      this.d = true;
   }

   public boolean fW() {
      return this.c;
   }

   public boolean fZ() {
      return true;
   }

   public void ga() {
      this.b = this.dg().b(-500 + this.af.a(1000), 0, -500 + this.af.a(1000));
      this.d = true;
   }

   protected boolean gb() {
      return this.d;
   }

   protected void x(boolean var0) {
      this.d = var0;
   }

   public static class a<T extends EntityMonsterPatrolling> extends PathfinderGoal {
      private static final int a = 200;
      private final T b;
      private final double c;
      private final double d;
      private long e;

      public a(T var0, double var1, double var3) {
         this.b = var0;
         this.c = var1;
         this.d = var3;
         this.e = -1L;
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         boolean var0 = this.b.H.U() < this.e;
         return this.b.gb() && this.b.P_() == null && !this.b.bM() && this.b.fV() && !var0;
      }

      @Override
      public void c() {
      }

      @Override
      public void d() {
      }

      @Override
      public void e() {
         boolean var0 = this.b.fW();
         NavigationAbstract var1 = this.b.G();
         if (var1.l()) {
            List<EntityMonsterPatrolling> var2 = this.h();
            if (this.b.gb() && var2.isEmpty()) {
               this.b.x(false);
            } else if (var0 && this.b.fU().a(this.b.de(), 10.0)) {
               this.b.ga();
            } else {
               Vec3D var3 = Vec3D.c(this.b.fU());
               Vec3D var4 = this.b.de();
               Vec3D var5 = var4.d(var3);
               var3 = var5.b(90.0F).a(0.4).e(var3);
               Vec3D var6 = var3.d(var4).d().a(10.0).e(var4);
               BlockPosition var7 = BlockPosition.a(var6);
               var7 = this.b.H.a(HeightMap.Type.f, var7);
               if (!var1.a((double)var7.u(), (double)var7.v(), (double)var7.w(), var0 ? this.d : this.c)) {
                  this.i();
                  this.e = this.b.H.U() + 200L;
               } else if (var0) {
                  for(EntityMonsterPatrolling var9 : var2) {
                     var9.g(var7);
                  }
               }
            }
         }
      }

      private List<EntityMonsterPatrolling> h() {
         return this.b.H.a(EntityMonsterPatrolling.class, this.b.cD().g(16.0), var0 -> var0.fZ() && !var0.q(this.b));
      }

      private boolean i() {
         RandomSource var0 = this.b.dZ();
         BlockPosition var1 = this.b.H.a(HeightMap.Type.f, this.b.dg().b(-8 + var0.a(16), 0, -8 + var0.a(16)));
         return this.b.G().a((double)var1.u(), (double)var1.v(), (double)var1.w(), this.c);
      }
   }
}
