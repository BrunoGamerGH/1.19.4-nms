package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.EntityAnimal;

public class BehaviorMakeLoveAnimal extends Behavior<EntityAnimal> {
   private static final int c = 3;
   private static final int d = 60;
   private static final int e = 110;
   private final EntityTypes<? extends EntityAnimal> f;
   private final float g;
   private long h;

   public BehaviorMakeLoveAnimal(EntityTypes<? extends EntityAnimal> var0, float var1) {
      super(
         ImmutableMap.of(
            MemoryModuleType.h, MemoryStatus.a, MemoryModuleType.r, MemoryStatus.b, MemoryModuleType.m, MemoryStatus.c, MemoryModuleType.n, MemoryStatus.c
         ),
         110
      );
      this.f = var0;
      this.g = var1;
   }

   protected boolean a(WorldServer var0, EntityAnimal var1) {
      return var1.fW() && this.c(var1).isPresent();
   }

   protected void a(WorldServer var0, EntityAnimal var1, long var2) {
      EntityAnimal var4 = this.c(var1).get();
      var1.dH().a(MemoryModuleType.r, var4);
      var4.dH().a(MemoryModuleType.r, var1);
      BehaviorUtil.a(var1, var4, this.g);
      int var5 = 60 + var1.dZ().a(50);
      this.h = var2 + (long)var5;
   }

   protected boolean b(WorldServer var0, EntityAnimal var1, long var2) {
      if (!this.b(var1)) {
         return false;
      } else {
         EntityAnimal var4 = this.a(var1);
         return var4.bq() && var1.a(var4) && BehaviorUtil.a(var1.dH(), var4) && var2 <= this.h;
      }
   }

   protected void c(WorldServer var0, EntityAnimal var1, long var2) {
      EntityAnimal var4 = this.a(var1);
      BehaviorUtil.a(var1, var4, this.g);
      if (var1.a(var4, 3.0)) {
         if (var2 >= this.h) {
            var1.a(var0, var4);
            var1.dH().b(MemoryModuleType.r);
            var4.dH().b(MemoryModuleType.r);
         }
      }
   }

   protected void d(WorldServer var0, EntityAnimal var1, long var2) {
      var1.dH().b(MemoryModuleType.r);
      var1.dH().b(MemoryModuleType.m);
      var1.dH().b(MemoryModuleType.n);
      this.h = 0L;
   }

   private EntityAnimal a(EntityAnimal var0) {
      return (EntityAnimal)var0.dH().c(MemoryModuleType.r).get();
   }

   private boolean b(EntityAnimal var0) {
      BehaviorController<?> var1 = var0.dH();
      return var1.a(MemoryModuleType.r) && var1.c(MemoryModuleType.r).get().ae() == this.f;
   }

   private Optional<? extends EntityAnimal> c(EntityAnimal var0) {
      return var0.dH().c(MemoryModuleType.h).get().a(var1x -> {
         if (var1x.ae() == this.f && var1x instanceof EntityAnimal var2 && var0.a(var2)) {
            return true;
         }

         return false;
      }).map(EntityAnimal.class::cast);
   }
}
