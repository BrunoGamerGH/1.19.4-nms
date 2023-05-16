package net.minecraft.world.entity.player;

import java.util.function.IntFunction;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.OptionEnum;

public enum EnumChatVisibility implements OptionEnum {
   a(0, "options.chat.visibility.full"),
   b(1, "options.chat.visibility.system"),
   c(2, "options.chat.visibility.hidden");

   private static final IntFunction<EnumChatVisibility> d = ByIdMap.a(EnumChatVisibility::a, values(), ByIdMap.a.b);
   private final int e;
   private final String f;

   private EnumChatVisibility(int var2, String var3) {
      this.e = var2;
      this.f = var3;
   }

   @Override
   public int a() {
      return this.e;
   }

   @Override
   public String b() {
      return this.f;
   }

   public static EnumChatVisibility a(int var0) {
      return d.apply(var0);
   }
}
