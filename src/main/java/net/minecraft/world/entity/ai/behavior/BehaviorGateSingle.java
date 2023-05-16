package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class BehaviorGateSingle<E extends EntityLiving> extends BehaviorGate<E> {
   public BehaviorGateSingle(List<Pair<? extends BehaviorControl<? super E>, Integer>> var0) {
      this(ImmutableMap.of(), var0);
   }

   public BehaviorGateSingle(Map<MemoryModuleType<?>, MemoryStatus> var0, List<Pair<? extends BehaviorControl<? super E>, Integer>> var1) {
      super(var0, ImmutableSet.of(), BehaviorGate.Order.b, BehaviorGate.Execution.a, var1);
   }
}
