package net.minecraft.util;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class UtilColor {
   private static final Pattern a = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
   private static final Pattern b = Pattern.compile("\\r\\n|\\v");
   private static final Pattern c = Pattern.compile("(?:\\r\\n|\\v)$");

   public static String a(int var0) {
      int var1 = var0 / 20;
      int var2 = var1 / 60;
      var1 %= 60;
      int var3 = var2 / 60;
      var2 %= 60;
      return var3 > 0 ? String.format(Locale.ROOT, "%02d:%02d:%02d", var3, var2, var1) : String.format(Locale.ROOT, "%02d:%02d", var2, var1);
   }

   public static String a(String var0) {
      return a.matcher(var0).replaceAll("");
   }

   public static boolean b(@Nullable String var0) {
      return StringUtils.isEmpty(var0);
   }

   public static String a(String var0, int var1, boolean var2) {
      if (var0.length() <= var1) {
         return var0;
      } else {
         return var2 && var1 > 3 ? var0.substring(0, var1 - 3) + "..." : var0.substring(0, var1);
      }
   }

   public static int c(String var0) {
      if (var0.isEmpty()) {
         return 0;
      } else {
         Matcher var1 = b.matcher(var0);
         int var2 = 1;

         while(var1.find()) {
            ++var2;
         }

         return var2;
      }
   }

   public static boolean d(String var0) {
      return c.matcher(var0).find();
   }

   public static String e(String var0) {
      return a(var0, 256, false);
   }
}
