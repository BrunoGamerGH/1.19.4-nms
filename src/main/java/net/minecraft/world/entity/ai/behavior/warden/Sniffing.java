package net.minecraft.world.entity.ai.behavior.warden;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenAi;

public class Sniffing<E extends Warden> extends Behavior<E> {
   private static final double c = 6.0;
   private static final double d = 20.0;

   public Sniffing(int var0) {
      super(
         ImmutableMap.of(
            MemoryModuleType.aA,
            MemoryStatus.a,
            MemoryModuleType.o,
            MemoryStatus.b,
            MemoryModuleType.m,
            MemoryStatus.b,
            MemoryModuleType.n,
            MemoryStatus.c,
            MemoryModuleType.B,
            MemoryStatus.c,
            MemoryModuleType.ay,
            MemoryStatus.c,
            MemoryModuleType.aF,
            MemoryStatus.c
         ),
         var0
      );
   }

   protected boolean a(WorldServer var0, E var1, long var2) {
      return true;
   }

   protected void b(WorldServer var0, E var1, long var2) {
      var1.a(SoundEffects.zl, 5.0F, 1.0F);
   }

   protected void c(WorldServer var0, E var1, long var2) {
      if (var1.c(EntityPose.m)) {
         var1.b(EntityPose.a);
      }

      var1.dH().b(MemoryModuleType.aA);
      var1.dH().c(MemoryModuleType.B).filter(var1::a).ifPresent(var1x -> {
         if (var1.a(var1x, 6.0, 20.0)) {
            var1.c(var1x);
         }

         if (!var1.dH().a(MemoryModuleType.ay)) {
            WardenAi.a(var1, var1x.dg());
         }
      });
   }
}
