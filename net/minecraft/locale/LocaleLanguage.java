package net.minecraft.locale;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.IChatFormatted;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.FormattedString;
import net.minecraft.util.StringDecomposer;
import org.slf4j.Logger;

public abstract class LocaleLanguage {
   private static final Logger b = LogUtils.getLogger();
   private static final Gson c = new Gson();
   private static final Pattern d = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
   public static final String a = "en_us";
   private static volatile LocaleLanguage e = c();

   private static LocaleLanguage c() {
      Builder<String, String> var0 = ImmutableMap.builder();
      BiConsumer<String, String> var1 = var0::put;
      String var2 = "/assets/minecraft/lang/en_us.json";

      try (InputStream var3 = LocaleLanguage.class.getResourceAsStream("/assets/minecraft/lang/en_us.json")) {
         a(var3, var1);
      } catch (JsonParseException | IOException var8) {
         b.error("Couldn't read strings from {}", "/assets/minecraft/lang/en_us.json", var8);
      }

      final Map<String, String> var3 = var0.build();
      return new LocaleLanguage() {
         @Override
         public String a(String var0, String var1) {
            return var3.getOrDefault(var0, var1);
         }

         @Override
         public boolean b(String var0) {
            return var3.containsKey(var0);
         }

         @Override
         public boolean b() {
            return false;
         }

         @Override
         public FormattedString a(IChatFormatted var0) {
            return var1x -> var0.a((var1xx, var2) -> StringDecomposer.c(var2, var1xx, var1x) ? Optional.empty() : IChatFormatted.a, ChatModifier.a)
                  .isPresent();
         }
      };
   }

   public static void a(InputStream var0, BiConsumer<String, String> var1) {
      JsonObject var2 = (JsonObject)c.fromJson(new InputStreamReader(var0, StandardCharsets.UTF_8), JsonObject.class);

      for(Entry<String, JsonElement> var4 : var2.entrySet()) {
         String var5 = d.matcher(ChatDeserializer.a((JsonElement)var4.getValue(), var4.getKey())).replaceAll("%$1s");
         var1.accept(var4.getKey(), var5);
      }
   }

   public static LocaleLanguage a() {
      return e;
   }

   public static void a(LocaleLanguage var0) {
      e = var0;
   }

   public String a(String var0) {
      return this.a(var0, var0);
   }

   public abstract String a(String var1, String var2);

   public abstract boolean b(String var1);

   public abstract boolean b();

   public abstract FormattedString a(IChatFormatted var1);

   public List<FormattedString> a(List<IChatFormatted> var0) {
      return var0.stream().map(this::a).collect(ImmutableList.toImmutableList());
   }
}
