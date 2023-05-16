package net.minecraft.util;

import java.util.Optional;
import net.minecraft.EnumChatFormat;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.IChatFormatted;

public class StringDecomposer {
   private static final char a = '�';
   private static final Optional<Object> b = Optional.of(Unit.a);

   private static boolean a(ChatModifier var0, FormattedStringEmpty var1, int var2, char var3) {
      return Character.isSurrogate(var3) ? var1.accept(var2, var0, 65533) : var1.accept(var2, var0, var3);
   }

   public static boolean a(String var0, ChatModifier var1, FormattedStringEmpty var2) {
      int var3 = var0.length();

      for(int var4 = 0; var4 < var3; ++var4) {
         char var5 = var0.charAt(var4);
         if (Character.isHighSurrogate(var5)) {
            if (var4 + 1 >= var3) {
               if (!var2.accept(var4, var1, 65533)) {
                  return false;
               }
               break;
            }

            char var6 = var0.charAt(var4 + 1);
            if (Character.isLowSurrogate(var6)) {
               if (!var2.accept(var4, var1, Character.toCodePoint(var5, var6))) {
                  return false;
               }

               ++var4;
            } else if (!var2.accept(var4, var1, 65533)) {
               return false;
            }
         } else if (!a(var1, var2, var4, var5)) {
            return false;
         }
      }

      return true;
   }

   public static boolean b(String var0, ChatModifier var1, FormattedStringEmpty var2) {
      int var3 = var0.length();

      for(int var4 = var3 - 1; var4 >= 0; --var4) {
         char var5 = var0.charAt(var4);
         if (Character.isLowSurrogate(var5)) {
            if (var4 - 1 < 0) {
               if (!var2.accept(0, var1, 65533)) {
                  return false;
               }
               break;
            }

            char var6 = var0.charAt(var4 - 1);
            if (Character.isHighSurrogate(var6)) {
               if (!var2.accept(--var4, var1, Character.toCodePoint(var6, var5))) {
                  return false;
               }
            } else if (!var2.accept(var4, var1, 65533)) {
               return false;
            }
         } else if (!a(var1, var2, var4, var5)) {
            return false;
         }
      }

      return true;
   }

   public static boolean c(String var0, ChatModifier var1, FormattedStringEmpty var2) {
      return a(var0, 0, var1, var2);
   }

   public static boolean a(String var0, int var1, ChatModifier var2, FormattedStringEmpty var3) {
      return a(var0, var1, var2, var2, var3);
   }

   public static boolean a(String var0, int var1, ChatModifier var2, ChatModifier var3, FormattedStringEmpty var4) {
      int var5 = var0.length();
      ChatModifier var6 = var2;

      for(int var7 = var1; var7 < var5; ++var7) {
         char var8 = var0.charAt(var7);
         if (var8 == 167) {
            if (var7 + 1 >= var5) {
               break;
            }

            char var9 = var0.charAt(var7 + 1);
            EnumChatFormat var10 = EnumChatFormat.a(var9);
            if (var10 != null) {
               var6 = var10 == EnumChatFormat.v ? var3 : var6.c(var10);
            }

            ++var7;
         } else if (Character.isHighSurrogate(var8)) {
            if (var7 + 1 >= var5) {
               if (!var4.accept(var7, var6, 65533)) {
                  return false;
               }
               break;
            }

            char var9 = var0.charAt(var7 + 1);
            if (Character.isLowSurrogate(var9)) {
               if (!var4.accept(var7, var6, Character.toCodePoint(var8, var9))) {
                  return false;
               }

               ++var7;
            } else if (!var4.accept(var7, var6, 65533)) {
               return false;
            }
         } else if (!a(var6, var4, var7, var8)) {
            return false;
         }
      }

      return true;
   }

   public static boolean a(IChatFormatted var0, ChatModifier var1, FormattedStringEmpty var2) {
      return !var0.a((var1x, var2x) -> a(var2x, 0, var1x, var2) ? Optional.empty() : b, var1).isPresent();
   }

   public static String a(String var0) {
      StringBuilder var1 = new StringBuilder();
      a(var0, ChatModifier.a, (var1x, var2, var3) -> {
         var1.appendCodePoint(var3);
         return true;
      });
      return var1.toString();
   }

   public static String a(IChatFormatted var0) {
      StringBuilder var1 = new StringBuilder();
      a(var0, ChatModifier.a, (var1x, var2, var3) -> {
         var1.appendCodePoint(var3);
         return true;
      });
      return var1.toString();
   }
}
