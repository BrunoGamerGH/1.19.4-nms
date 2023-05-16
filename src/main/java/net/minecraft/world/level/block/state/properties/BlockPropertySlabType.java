package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum BlockPropertySlabType implements INamable {
   a("top"),
   b("bottom"),
   c("double");

   private final String d;

   private BlockPropertySlabType(String var2) {
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
