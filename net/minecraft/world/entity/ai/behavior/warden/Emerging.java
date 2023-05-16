package net.minecraft.world.entity.ai.behavior.warden;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.warden.Warden;

public class Emerging<E extends Warden> extends Behavior<E> {
   public Emerging(int var0) {
      super(ImmutableMap.of(MemoryModuleType.aB, MemoryStatus.a, MemoryModuleType.m, MemoryStatus.b, MemoryModuleType.n, MemoryStatus.c), var0);
   }

   protected boolean a(WorldServer var0, E var1, long var2) {
      return true;
   }

   protected void b(WorldServer var0, E var1, long var2) {
      var1.b(EntityPose.n);
      var1.a(SoundEffects.zc, 5.0F, 1.0F);
   }

   protected void c(WorldServer var0, E var1, long var2) {
      if (var1.c(EntityPose.n)) {
         var1.b(EntityPose.a);
      }
   }
}
