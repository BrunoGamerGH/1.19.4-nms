package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum BlockPropertyPistonType implements INamable {
   a("normal"),
   b("sticky");

   private final String c;

   private BlockPropertyPistonType(String var2) {
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
