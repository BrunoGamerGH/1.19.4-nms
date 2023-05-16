package net.minecraft.world.entity.ai.behavior.declarative;

import com.mojang.datafixers.kinds.Const;
import com.mojang.datafixers.kinds.IdF;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.OptionalBox;
import com.mojang.datafixers.kinds.Const.Mu;
import com.mojang.datafixers.util.Unit;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public interface MemoryCondition<F extends K1, Value> {
   MemoryModuleType<Value> a();

   MemoryStatus b();

   @Nullable
   MemoryAccessor<F, Value> a(BehaviorController<?> var1, Optional<Value> var2);

   public static record a<Value>(MemoryModuleType<Value> memory) implements MemoryCondition<Mu<Unit>, Value> {
      private final MemoryModuleType<Value> a;

      public a(MemoryModuleType<Value> var0) {
         this.a = var0;
      }

      @Override
      public MemoryStatus b() {
         return MemoryStatus.b;
      }

      @Override
      public MemoryAccessor<Mu<Unit>, Value> a(BehaviorController<?> var0, Optional<Value> var1) {
         return var1.isPresent() ? null : new MemoryAccessor<>(var0, this.a, Const.create(Unit.INSTANCE));
      }
   }

   public static record b<Value>(MemoryModuleType<Value> memory) implements MemoryCondition<com.mojang.datafixers.kinds.IdF.Mu, Value> {
      private final MemoryModuleType<Value> a;

      public b(MemoryModuleType<Value> var0) {
         this.a = var0;
      }

      @Override
      public MemoryStatus b() {
         return MemoryStatus.a;
      }

      @Override
      public MemoryAccessor<com.mojang.datafixers.kinds.IdF.Mu, Value> a(BehaviorController<?> var0, Optional<Value> var1) {
         return var1.isEmpty() ? null : new MemoryAccessor<>(var0, this.a, IdF.create(var1.get()));
      }
   }

   public static record c<Value>(MemoryModuleType<Value> memory) implements MemoryCondition<com.mojang.datafixers.kinds.OptionalBox.Mu, Value> {
      private final MemoryModuleType<Value> a;

      public c(MemoryModuleType<Value> var0) {
         this.a = var0;
      }

      @Override
      public MemoryStatus b() {
         return MemoryStatus.c;
      }

      @Override
      public MemoryAccessor<com.mojang.datafixers.kinds.OptionalBox.Mu, Value> a(BehaviorController<?> var0, Optional<Value> var1) {
         return new MemoryAccessor<>(var0, this.a, OptionalBox.create(var1));
      }
   }
}
