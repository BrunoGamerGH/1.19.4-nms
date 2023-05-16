package net.minecraft.world.entity.animal;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFishSchool;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;

public abstract class EntityFishSchool extends EntityFish {
   @Nullable
   private EntityFishSchool b;
   private int c = 1;

   public EntityFishSchool(EntityTypes<? extends EntityFishSchool> var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected void x() {
      super.x();
      this.bN.a(5, new PathfinderGoalFishSchool(this));
   }

   @Override
   public int fy() {
      return this.fU();
   }

   public int fU() {
      return super.fy();
   }

   @Override
   protected boolean fS() {
      return !this.fV();
   }

   public boolean fV() {
      return this.b != null && this.b.bq();
   }

   public EntityFishSchool a(EntityFishSchool var0) {
      this.b = var0;
      var0.gb();
      return var0;
   }

   public void fW() {
      this.b.gc();
      this.b = null;
   }

   private void gb() {
      ++this.c;
   }

   private void gc() {
      --this.c;
   }

   public boolean fX() {
      return this.fY() && this.c < this.fU();
   }

   @Override
   public void l() {
      super.l();
      if (this.fY() && this.H.z.a(200) == 1) {
         List<? extends EntityFish> var0 = this.H.a(this.getClass(), this.cD().c(8.0, 8.0, 8.0));
         if (var0.size() <= 1) {
            this.c = 1;
         }
      }
   }

   public boolean fY() {
      return this.c > 1;
   }

   public boolean fZ() {
      return this.f(this.b) <= 121.0;
   }

   public void ga() {
      if (this.fV()) {
         this.G().a(this.b, 1.0);
      }
   }

   public void a(Stream<? extends EntityFishSchool> var0) {
      var0.limit((long)(this.fU() - this.c)).filter(var0x -> var0x != this).forEach(var0x -> var0x.a(this));
   }

   @Nullable
   @Override
   public GroupDataEntity a(WorldAccess var0, DifficultyDamageScaler var1, EnumMobSpawn var2, @Nullable GroupDataEntity var3, @Nullable NBTTagCompound var4) {
      super.a(var0, var1, var2, var3, var4);
      if (var3 == null) {
         var3 = new EntityFishSchool.a(this);
      } else {
         this.a(((EntityFishSchool.a)var3).a);
      }

      return var3;
   }

   public static class a implements GroupDataEntity {
      public final EntityFishSchool a;

      public a(EntityFishSchool var0) {
         this.a = var0;
      }
   }
}
