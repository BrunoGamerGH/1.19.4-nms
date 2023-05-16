package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum BlockPropertyBellAttach implements INamable {
   a("floor"),
   b("ceiling"),
   c("single_wall"),
   d("double_wall");

   private final String e;

   private BlockPropertyBellAttach(String var2) {
      this.e = var2;
   }

   @Override
   public String c() {
      return this.e;
   }
}
