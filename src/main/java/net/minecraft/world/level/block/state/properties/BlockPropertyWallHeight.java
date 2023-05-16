package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum BlockPropertyWallHeight implements INamable {
   a("none"),
   b("low"),
   c("tall");

   private final String d;

   private BlockPropertyWallHeight(String var2) {
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
}
