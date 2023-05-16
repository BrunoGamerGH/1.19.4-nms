package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum BlockPropertyChestType implements INamable {
   a("single"),
   b("left"),
   c("right");

   private final String d;

   private BlockPropertyChestType(String var2) {
      this.d = var2;
   }

   @Override
   public String c() {
      return this.d;
   }

   public BlockPropertyChestType a() {
      return switch(this) {
         case a -> a;
         case b -> c;
         case c -> b;
      };
   }
}
