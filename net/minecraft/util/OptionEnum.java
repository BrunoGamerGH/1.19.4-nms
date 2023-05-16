package net.minecraft.util;

import net.minecraft.network.chat.IChatBaseComponent;

public interface OptionEnum {
   int a();

   String b();

   default IChatBaseComponent c() {
      return IChatBaseComponent.c(this.b());
   }
}
