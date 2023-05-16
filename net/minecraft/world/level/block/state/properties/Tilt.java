package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum Tilt implements INamable {
   a("none", true),
   b("unstable", false),
   c("partial", true),
   d("full", true);

   private final String e;
   private final boolean f;

   private Tilt(String var2, boolean var3) {
      this.e = var2;
      this.f = var3;
   }

   @Override
   public String c() {
      return this.e;
   }

   public boolean a() {
      return this.f;
   }
}
