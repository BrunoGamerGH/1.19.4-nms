package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class CountDownCooldownTicks extends Behavior<EntityLiving> {
   private final MemoryModuleType<Integer> c;

   public CountDownCooldownTicks(MemoryModuleType<Integer> var0) {
      super(ImmutableMap.of(var0, MemoryStatus.a));
      this.c = var0;
   }

   private Optional<Integer> b(EntityLiving var0) {
      return var0.dH().c(this.c);
   }

   @Override
   protected boolean a(long var0) {
      return false;
   }

   @Override
   protected boolean a(WorldServer var0, EntityLiving var1, long var2) {
      Optional<Integer> var4 = this.b(var1);
      return var4.isPresent() && var4.get() > 0;
   }

   @Override
   protected void c(WorldServer var0, EntityLiving var1, long var2) {
      Optional<Integer> var4 = this.b(var1);
      var1.dH().a(this.c, var4.get() - 1);
   }

   @Override
   protected void b(WorldServer var0, EntityLiving var1, long var2) {
      var1.dH().b(this.c);
   }
}
