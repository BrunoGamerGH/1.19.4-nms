package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum DripstoneThickness implements INamable {
   a("tip_merge"),
   b("tip"),
   c("frustum"),
   d("middle"),
   e("base");

   private final String f;

   private DripstoneThickness(String var2) {
      this.f = var2;
   }

   @Override
   public String toString() {
      return this.f;
   }

   @Override
   public String c() {
      return this.f;
   }
}
