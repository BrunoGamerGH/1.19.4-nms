package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.player.EntityHuman;

public class BehaviorInteractPlayer extends Behavior<EntityVillager> {
   private final float c;

   public BehaviorInteractPlayer(float var0) {
      super(ImmutableMap.of(MemoryModuleType.m, MemoryStatus.c, MemoryModuleType.n, MemoryStatus.c), Integer.MAX_VALUE);
      this.c = var0;
   }

   protected boolean a(WorldServer var0, EntityVillager var1) {
      EntityHuman var2 = var1.fS();
      return var1.bq() && var2 != null && !var1.aT() && !var1.S && var1.f(var2) <= 16.0 && var2.bP != null;
   }

   protected boolean a(WorldServer var0, EntityVillager var1, long var2) {
      return this.a(var0, var1);
   }

   protected void b(WorldServer var0, EntityVillager var1, long var2) {
      this.a(var1);
   }

   protected void c(WorldServer var0, EntityVillager var1, long var2) {
      BehaviorController<?> var4 = var1.dH();
      var4.b(MemoryModuleType.m);
      var4.b(MemoryModuleType.n);
   }

   protected void d(WorldServer var0, EntityVillager var1, long var2) {
      this.a(var1);
   }

   @Override
   protected boolean a(long var0) {
      return false;
   }

   private void a(EntityVillager var0) {
      BehaviorController<?> var1 = var0.dH();
      var1.a(MemoryModuleType.m, new MemoryTarget(new BehaviorPositionEntity(var0.fS(), false), this.c, 2));
      var1.a(MemoryModuleType.n, new BehaviorPositionEntity(var0.fS(), true));
   }
}
