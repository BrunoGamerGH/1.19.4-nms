package net.minecraft.world.entity.ai.behavior;

import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public abstract class Behavior<E extends EntityLiving> implements BehaviorControl<E> {
   public static final int a = 60;
   protected final Map<MemoryModuleType<?>, MemoryStatus> b;
   private Behavior.Status c = Behavior.Status.a;
   private long d;
   private final int e;
   private final int f;

   public Behavior(Map<MemoryModuleType<?>, MemoryStatus> var0) {
      this(var0, 60);
   }

   public Behavior(Map<MemoryModuleType<?>, MemoryStatus> var0, int var1) {
      this(var0, var1, var1);
   }

   public Behavior(Map<MemoryModuleType<?>, MemoryStatus> var0, int var1, int var2) {
      this.e = var1;
      this.f = var2;
      this.b = var0;
   }

   @Override
   public Behavior.Status a() {
      return this.c;
   }

   @Override
   public final boolean e(WorldServer var0, E var1, long var2) {
      if (this.a(var1) && this.a(var0, var1)) {
         this.c = Behavior.Status.b;
         int var4 = this.e + var0.r_().a(this.f + 1 - this.e);
         this.d = var2 + (long)var4;
         this.d(var0, var1, var2);
         return true;
      } else {
         return false;
      }
   }

   protected void d(WorldServer var0, E var1, long var2) {
   }

   @Override
   public final void f(WorldServer var0, E var1, long var2) {
      if (!this.a(var2) && this.a(var0, var1, var2)) {
         this.c(var0, var1, var2);
      } else {
         this.g(var0, var1, var2);
      }
   }

   protected void c(WorldServer var0, E var1, long var2) {
   }

   @Override
   public final void g(WorldServer var0, E var1, long var2) {
      this.c = Behavior.Status.a;
      this.b(var0, var1, var2);
   }

   protected void b(WorldServer var0, E var1, long var2) {
   }

   protected boolean a(WorldServer var0, E var1, long var2) {
      return false;
   }

   protected boolean a(long var0) {
      return var0 > this.d;
   }

   protected boolean a(WorldServer var0, E var1) {
      return true;
   }

   @Override
   public String b() {
      return this.getClass().getSimpleName();
   }

   protected boolean a(E var0) {
      for(Entry<MemoryModuleType<?>, MemoryStatus> var2 : this.b.entrySet()) {
         MemoryModuleType<?> var3 = var2.getKey();
         MemoryStatus var4 = var2.getValue();
         if (!var0.dH().a(var3, var4)) {
            return false;
         }
      }

      return true;
   }

   public static enum Status {
      a,
      b;
   }
}
