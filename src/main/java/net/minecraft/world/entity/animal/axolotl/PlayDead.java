package net.minecraft.world.entity.animal.axolotl;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class PlayDead extends Behavior<Axolotl> {
   public PlayDead() {
      super(ImmutableMap.of(MemoryModuleType.M, MemoryStatus.a, MemoryModuleType.y, MemoryStatus.a), 200);
   }

   protected boolean a(WorldServer var0, Axolotl var1) {
      return var1.aW();
   }

   protected boolean a(WorldServer var0, Axolotl var1, long var2) {
      return var1.aW() && var1.dH().a(MemoryModuleType.M);
   }

   protected void b(WorldServer var0, Axolotl var1, long var2) {
      BehaviorController<Axolotl> var4 = var1.dH();
      var4.b(MemoryModuleType.m);
      var4.b(MemoryModuleType.n);
      var1.b(new MobEffect(MobEffects.j, 200, 0));
   }
}
