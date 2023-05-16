package net.minecraft.core.dispenser;

import net.minecraft.core.ISourceBlock;

public abstract class DispenseBehaviorMaybe extends DispenseBehaviorItem {
   private boolean c = true;

   public boolean a() {
      return this.c;
   }

   public void a(boolean var0) {
      this.c = var0;
   }

   @Override
   protected void a(ISourceBlock var0) {
      var0.g().c(this.a() ? 1000 : 1001, var0.d(), 0);
   }
}
