package net.minecraft.world.entity.ai.behavior.warden;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.warden.Warden;

public class Digging<E extends Warden> extends Behavior<E> {
   public Digging(int var0) {
      super(ImmutableMap.of(MemoryModuleType.o, MemoryStatus.b, MemoryModuleType.m, MemoryStatus.b), var0);
   }

   protected boolean a(WorldServer var0, E var1, long var2) {
      return var1.dC() == null;
   }

   protected boolean a(WorldServer var0, E var1) {
      return var1.ax() || var1.aT() || var1.bg();
   }

   protected void b(WorldServer var0, E var1, long var2) {
      if (var1.ax()) {
         var1.b(EntityPose.o);
         var1.a(SoundEffects.zb, 5.0F, 1.0F);
      } else {
         var1.a(SoundEffects.yW, 5.0F, 1.0F);
         this.c(var0, var1, var2);
      }
   }

   protected void c(WorldServer var0, E var1, long var2) {
      if (var1.dC() == null) {
         var1.a(Entity.RemovalReason.b);
      }
   }
}
