package net.minecraft.network.chat;

import java.util.Arrays;
import java.util.Collection;

public class CommonComponents {
   public static final IChatBaseComponent a = IChatBaseComponent.h();
   public static final IChatBaseComponent b = IChatBaseComponent.c("options.on");
   public static final IChatBaseComponent c = IChatBaseComponent.c("options.off");
   public static final IChatBaseComponent d = IChatBaseComponent.c("gui.done");
   public static final IChatBaseComponent e = IChatBaseComponent.c("gui.cancel");
   public static final IChatBaseComponent f = IChatBaseComponent.c("gui.yes");
   public static final IChatBaseComponent g = IChatBaseComponent.c("gui.no");
   public static final IChatBaseComponent h = IChatBaseComponent.c("gui.proceed");
   public static final IChatBaseComponent i = IChatBaseComponent.c("gui.continue");
   public static final IChatBaseComponent j = IChatBaseComponent.c("gui.back");
   public static final IChatBaseComponent k = IChatBaseComponent.c("gui.toTitle");
   public static final IChatBaseComponent l = IChatBaseComponent.c("gui.acknowledge");
   public static final IChatBaseComponent m = IChatBaseComponent.c("connect.failed");
   public static final IChatBaseComponent n = IChatBaseComponent.b("\n");
   public static final IChatBaseComponent o = IChatBaseComponent.b(". ");
   public static final IChatBaseComponent p = IChatBaseComponent.b("...");
   public static final IChatBaseComponent q = a();

   public static IChatMutableComponent a() {
      return IChatBaseComponent.b(" ");
   }

   public static IChatMutableComponent a(long var0) {
      return IChatBaseComponent.a("gui.days", var0);
   }

   public static IChatMutableComponent b(long var0) {
      return IChatBaseComponent.a("gui.hours", var0);
   }

   public static IChatMutableComponent c(long var0) {
      return IChatBaseComponent.a("gui.minutes", var0);
   }

   public static IChatBaseComponent a(boolean var0) {
      return var0 ? b : c;
   }

   public static IChatMutableComponent a(IChatBaseComponent var0, boolean var1) {
      return IChatBaseComponent.a(var1 ? "options.on.composed" : "options.off.composed", var0);
   }

   public static IChatMutableComponent a(IChatBaseComponent var0, IChatBaseComponent var1) {
      return IChatBaseComponent.a("options.generic_value", var0, var1);
   }

   public static IChatMutableComponent a(IChatBaseComponent... var0) {
      IChatMutableComponent var1 = IChatBaseComponent.h();

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1.b(var0[var2]);
         if (var2 != var0.length - 1) {
            var1.b(o);
         }
      }

      return var1;
   }

   public static IChatBaseComponent b(IChatBaseComponent... var0) {
      return a(Arrays.asList(var0));
   }

   public static IChatBaseComponent a(Collection<? extends IChatBaseComponent> var0) {
      return ChatComponentUtils.a(var0, n);
   }
}
