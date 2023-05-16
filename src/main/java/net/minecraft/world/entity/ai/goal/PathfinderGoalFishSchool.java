package net.minecraft.world.entity.ai.goal;

import com.mojang.datafixers.DataFixUtils;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.animal.EntityFishSchool;

public class PathfinderGoalFishSchool extends PathfinderGoal {
   private static final int a = 200;
   private final EntityFishSchool b;
   private int c;
   private int d;

   public PathfinderGoalFishSchool(EntityFishSchool var0) {
      this.b = var0;
      this.d = this.a(var0);
   }

   protected int a(EntityFishSchool var0) {
      return b(200 + var0.dZ().a(200) % 20);
   }

   @Override
   public boolean a() {
      if (this.b.fY()) {
         return false;
      } else if (this.b.fV()) {
         return true;
      } else if (this.d > 0) {
         --this.d;
         return false;
      } else {
         this.d = this.a(this.b);
         Predicate<EntityFishSchool> var0 = var0x -> var0x.fX() || !var0x.fV();
         List<? extends EntityFishSchool> var1 = this.b.H.a(this.b.getClass(), this.b.cD().c(8.0, 8.0, 8.0), var0);
         EntityFishSchool var2 = (EntityFishSchool)DataFixUtils.orElse(var1.stream().filter(EntityFishSchool::fX).findAny(), this.b);
         var2.a(var1.stream().filter(var0x -> !var0x.fV()));
         return this.b.fV();
      }
   }

   @Override
   public boolean b() {
      return this.b.fV() && this.b.fZ();
   }

   @Override
   public void c() {
      this.c = 0;
   }

   @Override
   public void d() {
      this.b.fW();
   }

   @Override
   public void e() {
      if (--this.c <= 0) {
         this.c = this.a(10);
         this.b.ga();
      }
   }
}
