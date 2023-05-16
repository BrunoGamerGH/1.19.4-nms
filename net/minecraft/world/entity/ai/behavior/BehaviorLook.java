package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class BehaviorLook extends Behavior<EntityInsentient> {
   public BehaviorLook(int var0, int var1) {
      super(ImmutableMap.of(MemoryModuleType.n, MemoryStatus.a), var0, var1);
   }

   protected boolean a(WorldServer var0, EntityInsentient var1, long var2) {
      return var1.dH().c(MemoryModuleType.n).filter(var1x -> var1x.a(var1)).isPresent();
   }

   protected void b(WorldServer var0, EntityInsentient var1, long var2) {
      var1.dH().b(MemoryModuleType.n);
   }

   protected void c(WorldServer var0, EntityInsentient var1, long var2) {
      var1.dH().c(MemoryModuleType.n).ifPresent(var1x -> var1.C().a(var1x.a()));
   }
}
