package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum BlockPropertyComparatorMode implements INamable {
   a("compare"),
   b("subtract");

   private final String c;

   private BlockPropertyComparatorMode(String var2) {
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
