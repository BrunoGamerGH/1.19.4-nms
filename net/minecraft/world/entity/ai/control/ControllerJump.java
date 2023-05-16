package net.minecraft.world.entity.ai.control;

import net.minecraft.world.entity.EntityInsentient;

public class ControllerJump implements Control {
   private final EntityInsentient b;
   protected boolean a;

   public ControllerJump(EntityInsentient var0) {
      this.b = var0;
   }

   public void a() {
      this.a = true;
   }

   public void b() {
      this.b.r(this.a);
      this.a = false;
   }
}
