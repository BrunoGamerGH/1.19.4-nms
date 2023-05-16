package net.minecraft.world.entity.ai.behavior.warden;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtil;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenAi;

public class Roar extends Behavior<Warden> {
   private static final int c = 25;
   private static final int d = 20;

   public Roar() {
      super(
         ImmutableMap.of(
            MemoryModuleType.ax, MemoryStatus.a, MemoryModuleType.o, MemoryStatus.b, MemoryModuleType.aE, MemoryStatus.c, MemoryModuleType.aC, MemoryStatus.c
         ),
         WardenAi.b
      );
   }

   protected void a(WorldServer var0, Warden var1, long var2) {
      BehaviorController<Warden> var4 = var1.dH();
      var4.a(MemoryModuleType.aC, Unit.a, 25L);
      var4.b(MemoryModuleType.m);
      EntityLiving var5 = var1.dH().c(MemoryModuleType.ax).get();
      BehaviorUtil.a(var1, var5);
      var1.b(EntityPose.l);
      var1.a(var5, 20, false);
   }

   protected boolean b(WorldServer var0, Warden var1, long var2) {
      return true;
   }

   protected void c(WorldServer var0, Warden var1, long var2) {
      if (!var1.dH().a(MemoryModuleType.aC) && !var1.dH().a(MemoryModuleType.aE)) {
         var1.dH().a(MemoryModuleType.aE, Unit.a, (long)(WardenAi.b - 25));
         var1.a(SoundEffects.zk, 3.0F, 1.0F);
      }
   }

   protected void d(WorldServer var0, Warden var1, long var2) {
      if (var1.c(EntityPose.l)) {
         var1.b(EntityPose.a);
      }

      var1.dH().c(MemoryModuleType.ax).ifPresent(var1::m);
      var1.dH().b(MemoryModuleType.ax);
   }
}
