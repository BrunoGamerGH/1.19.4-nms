package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum BlockPropertyBedPart implements INamable {
   a("head"),
   b("foot");

   private final String c;

   private BlockPropertyBedPart(String var2) {
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
