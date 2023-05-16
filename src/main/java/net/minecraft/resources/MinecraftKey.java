package net.minecraft.resources;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.reflect.Type;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.ResourceKeyInvalidException;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.ChatDeserializer;
import org.apache.commons.lang3.StringUtils;

public class MinecraftKey implements Comparable<MinecraftKey> {
   public static final Codec<MinecraftKey> a = Codec.STRING.comapFlatMap(MinecraftKey::b, MinecraftKey::toString).stable();
   private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.id.invalid"));
   public static final char b = ':';
   public static final String c = "minecraft";
   public static final String d = "realms";
   private final String f;
   private final String g;

   protected MinecraftKey(String var0, String var1, @Nullable MinecraftKey.a var2) {
      this.f = var0;
      this.g = var1;
   }

   public MinecraftKey(String var0, String var1) {
      this(c(var0, var1), d(var0, var1), null);
   }

   private MinecraftKey(String[] var0) {
      this(var0[0], var0[1]);
   }

   public MinecraftKey(String var0) {
      this(b(var0, ':'));
   }

   public static MinecraftKey a(String var0, char var1) {
      return new MinecraftKey(b(var0, var1));
   }

   @Nullable
   public static MinecraftKey a(String var0) {
      try {
         return new MinecraftKey(var0);
      } catch (ResourceKeyInvalidException var2) {
         return null;
      }
   }

   @Nullable
   public static MinecraftKey a(String var0, String var1) {
      try {
         return new MinecraftKey(var0, var1);
      } catch (ResourceKeyInvalidException var3) {
         return null;
      }
   }

   protected static String[] b(String var0, char var1) {
      String[] var2 = new String[]{"minecraft", var0};
      int var3 = var0.indexOf(var1);
      if (var3 >= 0) {
         var2[1] = var0.substring(var3 + 1);
         if (var3 >= 1) {
            var2[0] = var0.substring(0, var3);
         }
      }

      return var2;
   }

   public static DataResult<MinecraftKey> b(String var0) {
      try {
         return DataResult.success(new MinecraftKey(var0));
      } catch (ResourceKeyInvalidException var2) {
         return DataResult.error(() -> "Not a valid resource location: " + var0 + " " + var2.getMessage());
      }
   }

   public String a() {
      return this.g;
   }

   public String b() {
      return this.f;
   }

   public MinecraftKey c(String var0) {
      return new MinecraftKey(this.f, d(this.f, var0), null);
   }

   public MinecraftKey a(UnaryOperator<String> var0) {
      return this.c(var0.apply(this.g));
   }

   public MinecraftKey d(String var0) {
      return this.c(var0 + this.g);
   }

   public MinecraftKey e(String var0) {
      return this.c(this.g + var0);
   }

   @Override
   public String toString() {
      return this.f + ":" + this.g;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof MinecraftKey)) {
         return false;
      } else {
         MinecraftKey var1 = (MinecraftKey)var0;
         return this.f.equals(var1.f) && this.g.equals(var1.g);
      }
   }

   @Override
   public int hashCode() {
      return 31 * this.f.hashCode() + this.g.hashCode();
   }

   public int a(MinecraftKey var0) {
      int var1 = this.g.compareTo(var0.g);
      if (var1 == 0) {
         var1 = this.f.compareTo(var0.f);
      }

      return var1;
   }

   public String c() {
      return this.toString().replace('/', '_').replace(':', '_');
   }

   public String d() {
      return this.f + "." + this.g;
   }

   public String e() {
      return this.f.equals("minecraft") ? this.g : this.d();
   }

   public String f(String var0) {
      return var0 + "." + this.d();
   }

   public String b(String var0, String var1) {
      return var0 + "." + this.d() + "." + var1;
   }

   public static MinecraftKey a(StringReader var0) throws CommandSyntaxException {
      int var1 = var0.getCursor();

      while(var0.canRead() && a(var0.peek())) {
         var0.skip();
      }

      String var2 = var0.getString().substring(var1, var0.getCursor());

      try {
         return new MinecraftKey(var2);
      } catch (ResourceKeyInvalidException var4) {
         var0.setCursor(var1);
         throw e.createWithContext(var0);
      }
   }

   public static boolean a(char var0) {
      return var0 >= '0' && var0 <= '9' || var0 >= 'a' && var0 <= 'z' || var0 == '_' || var0 == ':' || var0 == '/' || var0 == '.' || var0 == '-';
   }

   private static boolean h(String var0) {
      for(int var1 = 0; var1 < var0.length(); ++var1) {
         if (!b(var0.charAt(var1))) {
            return false;
         }
      }

      return true;
   }

   private static boolean i(String var0) {
      for(int var1 = 0; var1 < var0.length(); ++var1) {
         if (!c(var0.charAt(var1))) {
            return false;
         }
      }

      return true;
   }

   private static String c(String var0, String var1) {
      if (!i(var0)) {
         throw new ResourceKeyInvalidException("Non [a-z0-9_.-] character in namespace of location: " + var0 + ":" + var1);
      } else {
         return var0;
      }
   }

   public static boolean b(char var0) {
      return var0 == '_' || var0 == '-' || var0 >= 'a' && var0 <= 'z' || var0 >= '0' && var0 <= '9' || var0 == '/' || var0 == '.';
   }

   private static boolean c(char var0) {
      return var0 == '_' || var0 == '-' || var0 >= 'a' && var0 <= 'z' || var0 >= '0' && var0 <= '9' || var0 == '.';
   }

   public static boolean g(String var0) {
      String[] var1 = b(var0, ':');
      return i(StringUtils.isEmpty(var1[0]) ? "minecraft" : var1[0]) && h(var1[1]);
   }

   private static String d(String var0, String var1) {
      if (!h(var1)) {
         throw new ResourceKeyInvalidException("Non [a-z0-9/._-] character in path of location: " + var0 + ":" + var1);
      } else {
         return var1;
      }
   }

   protected interface a {
   }

   public static class b implements JsonDeserializer<MinecraftKey>, JsonSerializer<MinecraftKey> {
      public MinecraftKey a(JsonElement var0, Type var1, JsonDeserializationContext var2) throws JsonParseException {
         return new MinecraftKey(ChatDeserializer.a(var0, "location"));
      }

      public JsonElement a(MinecraftKey var0, Type var1, JsonSerializationContext var2) {
         return new JsonPrimitive(var0.toString());
      }
   }
}
