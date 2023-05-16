package net.minecraft.world.entity.ai.behavior;

import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;

public abstract class OneShot<E extends EntityLiving> implements BehaviorControl<E>, Trigger<E> {
   private Behavior.Status a;

   public OneShot() {
      this.a = Behavior.Status.a;
   }

   @Override
   public final Behavior.Status a() {
      return this.a;
   }

   @Override
   public final boolean e(WorldServer var0, E var1, long var2) {
      if (this.trigger(var0, var1, var2)) {
         this.a = Behavior.Status.b;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public final void f(WorldServer var0, E var1, long var2) {
      this.g(var0, var1, var2);
   }

   @Override
   public final void g(WorldServer var0, E var1, long var2) {
      this.a = Behavior.Status.a;
   }

   @Override
   public String b() {
      return this.getClass().getSimpleName();
   }
}
