package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum BlockPropertyBambooSize implements INamable {
   a("none"),
   b("small"),
   c("large");

   private final String d;

   private BlockPropertyBambooSize(String var2) {
      this.d = var2;
   }

   @Override
   public String toString() {
      return this.d;
   }

   @Override
   public String c() {
      return this.d;
   }
}
