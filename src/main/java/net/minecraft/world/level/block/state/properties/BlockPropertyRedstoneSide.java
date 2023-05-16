package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum BlockPropertyRedstoneSide implements INamable {
   a("up"),
   b("side"),
   c("none");

   private final String d;

   private BlockPropertyRedstoneSide(String var2) {
      this.d = var2;
   }

   @Override
   public String toString() {
      return this.c();
   }

   @Override
   public String c() {
      return this.d;
   }

   public boolean a() {
      return this != c;
   }
}
