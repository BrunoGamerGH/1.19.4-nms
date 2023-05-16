package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.frog.Frog;

public class Croak extends Behavior<Frog> {
   private static final int c = 60;
   private static final int d = 100;
   private int e;

   public Croak() {
      super(ImmutableMap.of(MemoryModuleType.m, MemoryStatus.b), 100);
   }

   protected boolean a(WorldServer var0, Frog var1) {
      return var1.al() == EntityPose.a;
   }

   protected boolean a(WorldServer var0, Frog var1, long var2) {
      return this.e < 60;
   }

   protected void b(WorldServer var0, Frog var1, long var2) {
      if (!var1.aW() && !var1.bg()) {
         var1.b(EntityPose.i);
         this.e = 0;
      }
   }

   protected void c(WorldServer var0, Frog var1, long var2) {
      var1.b(EntityPose.a);
   }

   protected void d(WorldServer var0, Frog var1, long var2) {
      ++this.e;
   }
}
