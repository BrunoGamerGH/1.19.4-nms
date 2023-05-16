package net.minecraft.network.chat;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ChatClickable {
   private final ChatClickable.EnumClickAction a;
   private final String b;

   public ChatClickable(ChatClickable.EnumClickAction var0, String var1) {
      this.a = var0;
      this.b = var1;
   }

   public ChatClickable.EnumClickAction a() {
      return this.a;
   }

   public String b() {
      return this.b;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (var0 != null && this.getClass() == var0.getClass()) {
         ChatClickable var1 = (ChatClickable)var0;
         if (this.a != var1.a) {
            return false;
         } else {
            return this.b != null ? this.b.equals(var1.b) : var1.b == null;
         }
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return "ClickEvent{action=" + this.a + ", value='" + this.b + "'}";
   }

   @Override
   public int hashCode() {
      int var0 = this.a.hashCode();
      return 31 * var0 + (this.b != null ? this.b.hashCode() : 0);
   }

   public static enum EnumClickAction {
      a("open_url", true),
      b("open_file", false),
      c("run_command", true),
      d("suggest_command", true),
      e("change_page", true),
      f("copy_to_clipboard", true);

      private static final Map<String, ChatClickable.EnumClickAction> g = Arrays.stream(values())
         .collect(Collectors.toMap(ChatClickable.EnumClickAction::b, var0 -> var0));
      private final boolean h;
      private final String i;

      private EnumClickAction(String var2, boolean var3) {
         this.i = var2;
         this.h = var3;
      }

      public boolean a() {
         return this.h;
      }

      public String b() {
         return this.i;
      }

      public static ChatClickable.EnumClickAction a(String var0) {
         return g.get(var0);
      }
   }
}
