package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum SculkSensorPhase implements INamable {
   a("inactive"),
   b("active"),
   c("cooldown");

   private final String d;

   private SculkSensorPhase(String var2) {
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
