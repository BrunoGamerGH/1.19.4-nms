package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class BehaviorGate<E extends EntityLiving> implements BehaviorControl<E> {
   private final Map<MemoryModuleType<?>, MemoryStatus> a;
   private final Set<MemoryModuleType<?>> b;
   private final BehaviorGate.Order c;
   private final BehaviorGate.Execution d;
   private final ShufflingList<BehaviorControl<? super E>> e = new ShufflingList<>();
   private Behavior.Status f = Behavior.Status.a;

   public BehaviorGate(
      Map<MemoryModuleType<?>, MemoryStatus> var0,
      Set<MemoryModuleType<?>> var1,
      BehaviorGate.Order var2,
      BehaviorGate.Execution var3,
      List<Pair<? extends BehaviorControl<? super E>, Integer>> var4
   ) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      var4.forEach(var0x -> this.e.a((BehaviorControl<? super E>)var0x.getFirst(), var0x.getSecond()));
   }

   @Override
   public Behavior.Status a() {
      return this.f;
   }

   private boolean a(E var0) {
      for(Entry<MemoryModuleType<?>, MemoryStatus> var2 : this.a.entrySet()) {
         MemoryModuleType<?> var3 = var2.getKey();
         MemoryStatus var4 = var2.getValue();
         if (!var0.dH().a(var3, var4)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public final boolean e(WorldServer var0, E var1, long var2) {
      if (this.a(var1)) {
         this.f = Behavior.Status.b;
         this.c.a(this.e);
         this.d.a(this.e.b(), var0, var1, var2);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public final void f(WorldServer var0, E var1, long var2) {
      this.e.b().filter(var0x -> var0x.a() == Behavior.Status.b).forEach(var4 -> var4.f(var0, var1, var2));
      if (this.e.b().noneMatch(var0x -> var0x.a() == Behavior.Status.b)) {
         this.g(var0, var1, var2);
      }
   }

   @Override
   public final void g(WorldServer var0, E var1, long var2) {
      this.f = Behavior.Status.a;
      this.e.b().filter(var0x -> var0x.a() == Behavior.Status.b).forEach(var4 -> var4.g(var0, var1, var2));
      this.b.forEach(var1.dH()::b);
   }

   @Override
   public String b() {
      return this.getClass().getSimpleName();
   }

   @Override
   public String toString() {
      Set<? extends BehaviorControl<? super E>> var0 = this.e.b().filter(var0x -> var0x.a() == Behavior.Status.b).collect(Collectors.toSet());
      return "(" + this.getClass().getSimpleName() + "): " + var0;
   }

   public static enum Execution {
      a {
         @Override
         public <E extends EntityLiving> void a(Stream<BehaviorControl<? super E>> var0, WorldServer var1, E var2, long var3) {
            var0.filter(var0x -> var0x.a() == Behavior.Status.a).filter(var4x -> var4x.e(var1, var2, var3)).findFirst();
         }
      },
      b {
         @Override
         public <E extends EntityLiving> void a(Stream<BehaviorControl<? super E>> var0, WorldServer var1, E var2, long var3) {
            var0.filter(var0x -> var0x.a() == Behavior.Status.a).forEach(var4x -> var4x.e(var1, var2, var3));
         }
      };

      public abstract <E extends EntityLiving> void a(Stream<BehaviorControl<? super E>> var1, WorldServer var2, E var3, long var4);
   }

   public static enum Order {
      a(var0 -> {
      }),
      b(ShufflingList::a);

      private final Consumer<ShufflingList<?>> c;

      private Order(Consumer var2) {
         this.c = var2;
      }

      public void a(ShufflingList<?> var0) {
         this.c.accept(var0);
      }
   }
}
