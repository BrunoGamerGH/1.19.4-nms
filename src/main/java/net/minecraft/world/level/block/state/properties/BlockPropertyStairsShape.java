package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum BlockPropertyStairsShape implements INamable {
   a("straight"),
   b("inner_left"),
   c("inner_right"),
   d("outer_left"),
   e("outer_right");

   private final String f;

   private BlockPropertyStairsShape(String var2) {
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
