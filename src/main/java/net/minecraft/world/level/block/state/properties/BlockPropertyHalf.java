package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum BlockPropertyHalf implements INamable {
   a("top"),
   b("bottom");

   private final String c;

   private BlockPropertyHalf(String var2) {
      this.c = var2;
   }

   @Override
   public String toString() {
      return this.c;
   }

   @Override
   public String c() {
      return this.c;
   }
}
