package net.minecraft.world.level.block.state.properties;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.INamable;

public enum BlockPropertyStructureMode implements INamable {
   a("save"),
   b("load"),
   c("corner"),
   d("data");

   private final String e;
   private final IChatBaseComponent f;

   private BlockPropertyStructureMode(String var2) {
      this.e = var2;
      this.f = IChatBaseComponent.c("structure_block.mode_info." + var2);
   }

   @Override
   public String c() {
      return this.e;
   }

   public IChatBaseComponent a() {
      return this.f;
   }
}
