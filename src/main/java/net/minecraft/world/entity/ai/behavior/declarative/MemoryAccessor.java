package net.minecraft.world.entity.ai.behavior.declarative;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.K1;
import java.util.Optional;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public final class MemoryAccessor<F extends K1, Value> {
   private final BehaviorController<?> a;
   private final MemoryModuleType<Value> b;
   private final App<F, Value> c;

   public MemoryAccessor(BehaviorController<?> var0, MemoryModuleType<Value> var1, App<F, Value> var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public App<F, Value> a() {
      return this.c;
   }

   public void a(Value var0) {
      this.a.a(this.b, Optional.of(var0));
   }

   public void a(Optional<Value> var0) {
      this.a.a(this.b, var0);
   }

   public void a(Value var0, long var1) {
      this.a.a(this.b, var0, var1);
   }

   public void b() {
      this.a.b(this.b);
   }
}
