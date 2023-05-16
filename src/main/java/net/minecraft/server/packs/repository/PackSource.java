package net.minecraft.server.packs.repository;

import java.util.function.UnaryOperator;
import net.minecraft.EnumChatFormat;
import net.minecraft.network.chat.IChatBaseComponent;

public interface PackSource {
   UnaryOperator<IChatBaseComponent> a = UnaryOperator.identity();
   PackSource b = a(a, true);
   PackSource c = a(a("pack.source.builtin"), true);
   PackSource d = a(a("pack.source.feature"), false);
   PackSource e = a(a("pack.source.world"), true);
   PackSource f = a(a("pack.source.server"), true);

   IChatBaseComponent a(IChatBaseComponent var1);

   boolean a();

   static PackSource a(final UnaryOperator<IChatBaseComponent> var0, final boolean var1) {
      return new PackSource() {
         @Override
         public IChatBaseComponent a(IChatBaseComponent var0x) {
            return var0.apply(var0);
         }

         @Override
         public boolean a() {
            return var1;
         }
      };
   }

   private static UnaryOperator<IChatBaseComponent> a(String var0) {
      IChatBaseComponent var1 = IChatBaseComponent.c(var0);
      return var1x -> IChatBaseComponent.a("pack.nameAndSource", var1x, var1).a(EnumChatFormat.h);
   }
}
