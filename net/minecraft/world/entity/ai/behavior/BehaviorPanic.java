package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.schedule.Activity;

public class BehaviorPanic extends Behavior<EntityVillager> {
   public BehaviorPanic() {
      super(ImmutableMap.of());
   }

   protected boolean a(WorldServer var0, EntityVillager var1, long var2) {
      return c(var1) || b(var1);
   }

   protected void b(WorldServer var0, EntityVillager var1, long var2) {
      if (c(var1) || b(var1)) {
         BehaviorController<?> var4 = var1.dH();
         if (!var4.c(Activity.g)) {
            var4.b(MemoryModuleType.t);
            var4.b(MemoryModuleType.m);
            var4.b(MemoryModuleType.n);
            var4.b(MemoryModuleType.r);
            var4.b(MemoryModuleType.q);
         }

         var4.a(Activity.g);
      }
   }

   protected void c(WorldServer var0, EntityVillager var1, long var2) {
      if (var2 % 100L == 0L) {
         var1.a(var0, var2, 3);
      }
   }

   public static boolean b(EntityLiving var0) {
      return var0.dH().a(MemoryModuleType.A);
   }

   public static boolean c(EntityLiving var0) {
      return var0.dH().a(MemoryModuleType.x);
   }
}
