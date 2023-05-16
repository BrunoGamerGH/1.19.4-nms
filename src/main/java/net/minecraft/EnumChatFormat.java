package net.minecraft;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.INamable;

public enum EnumChatFormat implements INamable {
   a("BLACK", '0', 0, 0),
   b("DARK_BLUE", '1', 1, 170),
   c("DARK_GREEN", '2', 2, 43520),
   d("DARK_AQUA", '3', 3, 43690),
   e("DARK_RED", '4', 4, 11141120),
   f("DARK_PURPLE", '5', 5, 11141290),
   g("GOLD", '6', 6, 16755200),
   h("GRAY", '7', 7, 11184810),
   i("DARK_GRAY", '8', 8, 5592405),
   j("BLUE", '9', 9, 5592575),
   k("GREEN", 'a', 10, 5635925),
   l("AQUA", 'b', 11, 5636095),
   m("RED", 'c', 12, 16733525),
   n("LIGHT_PURPLE", 'd', 13, 16733695),
   o("YELLOW", 'e', 14, 16777045),
   p("WHITE", 'f', 15, 16777215),
   q("OBFUSCATED", 'k', true),
   r("BOLD", 'l', true),
   s("STRIKETHROUGH", 'm', true),
   t("UNDERLINE", 'n', true),
   u("ITALIC", 'o', true),
   v("RESET", 'r', -1, null);

   public static final Codec<EnumChatFormat> w = INamable.a(EnumChatFormat::values);
   public static final char x = '§';
   private static final Map<String, EnumChatFormat> y = Arrays.stream(values()).collect(Collectors.toMap(var0 -> c(var0.A), var0 -> var0));
   private static final Pattern z = Pattern.compile("(?i)§[0-9A-FK-OR]");
   private final String A;
   public final char B;
   private final boolean C;
   private final String D;
   private final int E;
   @Nullable
   private final Integer F;

   private static String c(String var0) {
      return var0.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
   }

   private EnumChatFormat(String var2, char var3, int var4, Integer var5) {
      this(var2, var3, false, var4, var5);
   }

   private EnumChatFormat(String var2, char var3, boolean var4) {
      this(var2, var3, var4, -1, null);
   }

   private EnumChatFormat(String var2, char var3, boolean var4, int var5, Integer var6) {
      this.A = var2;
      this.B = var3;
      this.C = var4;
      this.E = var5;
      this.F = var6;
      this.D = "§" + var3;
   }

   public char a() {
      return this.B;
   }

   public int b() {
      return this.E;
   }

   public boolean d() {
      return this.C;
   }

   public boolean e() {
      return !this.C && this != v;
   }

   @Nullable
   public Integer f() {
      return this.F;
   }

   public String g() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   @Override
   public String toString() {
      return this.D;
   }

   @Nullable
   public static String a(@Nullable String var0) {
      return var0 == null ? null : z.matcher(var0).replaceAll("");
   }

   @Nullable
   public static EnumChatFormat b(@Nullable String var0) {
      return var0 == null ? null : y.get(c(var0));
   }

   @Nullable
   public static EnumChatFormat a(int var0) {
      if (var0 < 0) {
         return v;
      } else {
         for(EnumChatFormat var4 : values()) {
            if (var4.b() == var0) {
               return var4;
            }
         }

         return null;
      }
   }

   @Nullable
   public static EnumChatFormat a(char var0) {
      char var1 = Character.toString(var0).toLowerCase(Locale.ROOT).charAt(0);

      for(EnumChatFormat var5 : values()) {
         if (var5.B == var1) {
            return var5;
         }
      }

      return null;
   }

   public static Collection<String> a(boolean var0, boolean var1) {
      List<String> var2 = Lists.newArrayList();

      for(EnumChatFormat var6 : values()) {
         if ((!var6.e() || var0) && (!var6.d() || var1)) {
            var2.add(var6.g());
         }
      }

      return var2;
   }

   @Override
   public String c() {
      return this.g();
   }
}
