package net.minecraft.world.entity.ai.behavior;

import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;

public class BehaviorNop implements BehaviorControl<EntityLiving> {
   private final int a;
   private final int b;
   private Behavior.Status c = Behavior.Status.a;
   private long d;

   public BehaviorNop(int var0, int var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public Behavior.Status a() {
      return this.c;
   }

   @Override
   public final boolean e(WorldServer var0, EntityLiving var1, long var2) {
      this.c = Behavior.Status.b;
      int var4 = this.a + var0.r_().a(this.b + 1 - this.a);
      this.d = var2 + (long)var4;
      return true;
   }

   @Override
   public final void f(WorldServer var0, EntityLiving var1, long var2) {
      if (var2 > this.d) {
         this.g(var0, var1, var2);
      }
   }

   @Override
   public final void g(WorldServer var0, EntityLiving var1, long var2) {
      this.c = Behavior.Status.a;
   }

   @Override
   public String b() {
      return this.getClass().getSimpleName();
   }
}
