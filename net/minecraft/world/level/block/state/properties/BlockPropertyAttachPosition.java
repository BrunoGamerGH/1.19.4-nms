package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum BlockPropertyAttachPosition implements INamable {
   a("floor"),
   b("wall"),
   c("ceiling");

   private final String d;

   private BlockPropertyAttachPosition(String var2) {
      this.d = var2;
   }

   @Override
   public String c() {
      return this.d;
   }
}
