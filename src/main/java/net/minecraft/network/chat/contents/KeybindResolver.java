package net.minecraft.network.chat.contents;

import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.chat.IChatBaseComponent;

public class KeybindResolver {
   static Function<String, Supplier<IChatBaseComponent>> a = var0 -> () -> IChatBaseComponent.b(var0);

   public static void a(Function<String, Supplier<IChatBaseComponent>> var0) {
      a = var0;
   }
}
