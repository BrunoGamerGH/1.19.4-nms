package net.minecraft.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;

public class ChatDeserializer {
   private static final Gson a = new GsonBuilder().create();

   public static boolean a(JsonObject var0, String var1) {
      return !f(var0, var1) ? false : var0.getAsJsonPrimitive(var1).isString();
   }

   public static boolean a(JsonElement var0) {
      return !var0.isJsonPrimitive() ? false : var0.getAsJsonPrimitive().isString();
   }

   public static boolean b(JsonObject var0, String var1) {
      return !f(var0, var1) ? false : var0.getAsJsonPrimitive(var1).isNumber();
   }

   public static boolean b(JsonElement var0) {
      return !var0.isJsonPrimitive() ? false : var0.getAsJsonPrimitive().isNumber();
   }

   public static boolean c(JsonObject var0, String var1) {
      return !f(var0, var1) ? false : var0.getAsJsonPrimitive(var1).isBoolean();
   }

   public static boolean c(JsonElement var0) {
      return !var0.isJsonPrimitive() ? false : var0.getAsJsonPrimitive().isBoolean();
   }

   public static boolean d(JsonObject var0, String var1) {
      return !g(var0, var1) ? false : var0.get(var1).isJsonArray();
   }

   public static boolean e(JsonObject var0, String var1) {
      return !g(var0, var1) ? false : var0.get(var1).isJsonObject();
   }

   public static boolean f(JsonObject var0, String var1) {
      return !g(var0, var1) ? false : var0.get(var1).isJsonPrimitive();
   }

   public static boolean g(JsonObject var0, String var1) {
      if (var0 == null) {
         return false;
      } else {
         return var0.get(var1) != null;
      }
   }

   public static String a(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive()) {
         return var0.getAsString();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a string, was " + d(var0));
      }
   }

   public static String h(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return a(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a string");
      }
   }

   @Nullable
   @Contract("_,_,!null->!null;_,_,null->_")
   public static String a(JsonObject var0, String var1, @Nullable String var2) {
      return var0.has(var1) ? a(var0.get(var1), var1) : var2;
   }

   public static Item b(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive()) {
         String var2 = var0.getAsString();
         return BuiltInRegistries.i
            .b(new MinecraftKey(var2))
            .orElseThrow(() -> new JsonSyntaxException("Expected " + var1 + " to be an item, was unknown string '" + var2 + "'"));
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be an item, was " + d(var0));
      }
   }

   public static Item i(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return b(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find an item");
      }
   }

   @Nullable
   @Contract("_,_,!null->!null;_,_,null->_")
   public static Item a(JsonObject var0, String var1, @Nullable Item var2) {
      return var0.has(var1) ? b(var0.get(var1), var1) : var2;
   }

   public static boolean c(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive()) {
         return var0.getAsBoolean();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a Boolean, was " + d(var0));
      }
   }

   public static boolean j(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return c(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a Boolean");
      }
   }

   public static boolean a(JsonObject var0, String var1, boolean var2) {
      return var0.has(var1) ? c(var0.get(var1), var1) : var2;
   }

   public static double d(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isNumber()) {
         return var0.getAsDouble();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a Double, was " + d(var0));
      }
   }

   public static double k(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return d(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a Double");
      }
   }

   public static double a(JsonObject var0, String var1, double var2) {
      return var0.has(var1) ? d(var0.get(var1), var1) : var2;
   }

   public static float e(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isNumber()) {
         return var0.getAsFloat();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a Float, was " + d(var0));
      }
   }

   public static float l(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return e(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a Float");
      }
   }

   public static float a(JsonObject var0, String var1, float var2) {
      return var0.has(var1) ? e(var0.get(var1), var1) : var2;
   }

   public static long f(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isNumber()) {
         return var0.getAsLong();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a Long, was " + d(var0));
      }
   }

   public static long m(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return f(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a Long");
      }
   }

   public static long a(JsonObject var0, String var1, long var2) {
      return var0.has(var1) ? f(var0.get(var1), var1) : var2;
   }

   public static int g(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isNumber()) {
         return var0.getAsInt();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a Int, was " + d(var0));
      }
   }

   public static int n(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return g(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a Int");
      }
   }

   public static int a(JsonObject var0, String var1, int var2) {
      return var0.has(var1) ? g(var0.get(var1), var1) : var2;
   }

   public static byte h(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isNumber()) {
         return var0.getAsByte();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a Byte, was " + d(var0));
      }
   }

   public static byte o(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return h(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a Byte");
      }
   }

   public static byte a(JsonObject var0, String var1, byte var2) {
      return var0.has(var1) ? h(var0.get(var1), var1) : var2;
   }

   public static char i(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isNumber()) {
         return var0.getAsCharacter();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a Character, was " + d(var0));
      }
   }

   public static char p(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return i(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a Character");
      }
   }

   public static char a(JsonObject var0, String var1, char var2) {
      return var0.has(var1) ? i(var0.get(var1), var1) : var2;
   }

   public static BigDecimal j(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isNumber()) {
         return var0.getAsBigDecimal();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a BigDecimal, was " + d(var0));
      }
   }

   public static BigDecimal q(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return j(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a BigDecimal");
      }
   }

   public static BigDecimal a(JsonObject var0, String var1, BigDecimal var2) {
      return var0.has(var1) ? j(var0.get(var1), var1) : var2;
   }

   public static BigInteger k(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isNumber()) {
         return var0.getAsBigInteger();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a BigInteger, was " + d(var0));
      }
   }

   public static BigInteger r(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return k(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a BigInteger");
      }
   }

   public static BigInteger a(JsonObject var0, String var1, BigInteger var2) {
      return var0.has(var1) ? k(var0.get(var1), var1) : var2;
   }

   public static short l(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isNumber()) {
         return var0.getAsShort();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a Short, was " + d(var0));
      }
   }

   public static short s(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return l(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a Short");
      }
   }

   public static short a(JsonObject var0, String var1, short var2) {
      return var0.has(var1) ? l(var0.get(var1), var1) : var2;
   }

   public static JsonObject m(JsonElement var0, String var1) {
      if (var0.isJsonObject()) {
         return var0.getAsJsonObject();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a JsonObject, was " + d(var0));
      }
   }

   public static JsonObject t(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return m(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a JsonObject");
      }
   }

   @Nullable
   @Contract("_,_,!null->!null;_,_,null->_")
   public static JsonObject a(JsonObject var0, String var1, @Nullable JsonObject var2) {
      return var0.has(var1) ? m(var0.get(var1), var1) : var2;
   }

   public static JsonArray n(JsonElement var0, String var1) {
      if (var0.isJsonArray()) {
         return var0.getAsJsonArray();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a JsonArray, was " + d(var0));
      }
   }

   public static JsonArray u(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return n(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a JsonArray");
      }
   }

   @Nullable
   @Contract("_,_,!null->!null;_,_,null->_")
   public static JsonArray a(JsonObject var0, String var1, @Nullable JsonArray var2) {
      return var0.has(var1) ? n(var0.get(var1), var1) : var2;
   }

   public static <T> T a(@Nullable JsonElement var0, String var1, JsonDeserializationContext var2, Class<? extends T> var3) {
      if (var0 != null) {
         return (T)var2.deserialize(var0, var3);
      } else {
         throw new JsonSyntaxException("Missing " + var1);
      }
   }

   public static <T> T a(JsonObject var0, String var1, JsonDeserializationContext var2, Class<? extends T> var3) {
      if (var0.has(var1)) {
         return a(var0.get(var1), var1, var2, var3);
      } else {
         throw new JsonSyntaxException("Missing " + var1);
      }
   }

   @Nullable
   @Contract("_,_,!null,_,_->!null;_,_,null,_,_->_")
   public static <T> T a(JsonObject var0, String var1, @Nullable T var2, JsonDeserializationContext var3, Class<? extends T> var4) {
      return (T)(var0.has(var1) ? a(var0.get(var1), var1, var3, var4) : var2);
   }

   public static String d(@Nullable JsonElement var0) {
      String var1 = StringUtils.abbreviateMiddle(String.valueOf(var0), "...", 10);
      if (var0 == null) {
         return "null (missing)";
      } else if (var0.isJsonNull()) {
         return "null (json)";
      } else if (var0.isJsonArray()) {
         return "an array (" + var1 + ")";
      } else if (var0.isJsonObject()) {
         return "an object (" + var1 + ")";
      } else {
         if (var0.isJsonPrimitive()) {
            JsonPrimitive var2 = var0.getAsJsonPrimitive();
            if (var2.isNumber()) {
               return "a number (" + var1 + ")";
            }

            if (var2.isBoolean()) {
               return "a boolean (" + var1 + ")";
            }
         }

         return var1;
      }
   }

   @Nullable
   public static <T> T a(Gson var0, Reader var1, Class<T> var2, boolean var3) {
      try {
         JsonReader var4 = new JsonReader(var1);
         var4.setLenient(var3);
         return (T)var0.getAdapter(var2).read(var4);
      } catch (IOException var5) {
         throw new JsonParseException(var5);
      }
   }

   public static <T> T b(Gson var0, Reader var1, Class<T> var2, boolean var3) {
      T var4 = a(var0, var1, var2, var3);
      if (var4 == null) {
         throw new JsonParseException("JSON data was null or empty");
      } else {
         return var4;
      }
   }

   @Nullable
   public static <T> T a(Gson var0, Reader var1, TypeToken<T> var2, boolean var3) {
      try {
         JsonReader var4 = new JsonReader(var1);
         var4.setLenient(var3);
         return (T)var0.getAdapter(var2).read(var4);
      } catch (IOException var5) {
         throw new JsonParseException(var5);
      }
   }

   public static <T> T b(Gson var0, Reader var1, TypeToken<T> var2, boolean var3) {
      T var4 = a(var0, var1, var2, var3);
      if (var4 == null) {
         throw new JsonParseException("JSON data was null or empty");
      } else {
         return var4;
      }
   }

   @Nullable
   public static <T> T a(Gson var0, String var1, TypeToken<T> var2, boolean var3) {
      return a(var0, new StringReader(var1), var2, var3);
   }

   public static <T> T a(Gson var0, String var1, Class<T> var2, boolean var3) {
      return b(var0, new StringReader(var1), var2, var3);
   }

   @Nullable
   public static <T> T b(Gson var0, String var1, Class<T> var2, boolean var3) {
      return a(var0, new StringReader(var1), var2, var3);
   }

   public static <T> T a(Gson var0, Reader var1, TypeToken<T> var2) {
      return b(var0, var1, var2, false);
   }

   @Nullable
   public static <T> T a(Gson var0, String var1, TypeToken<T> var2) {
      return a(var0, var1, var2, false);
   }

   public static <T> T a(Gson var0, Reader var1, Class<T> var2) {
      return b(var0, var1, var2, false);
   }

   public static <T> T a(Gson var0, String var1, Class<T> var2) {
      return a(var0, var1, var2, false);
   }

   public static JsonObject a(String var0, boolean var1) {
      return a(new StringReader(var0), var1);
   }

   public static JsonObject a(Reader var0, boolean var1) {
      return b(a, var0, JsonObject.class, var1);
   }

   public static JsonObject a(String var0) {
      return a(var0, false);
   }

   public static JsonObject a(Reader var0) {
      return a(var0, false);
   }

   public static JsonArray b(String var0) {
      return b(new StringReader(var0));
   }

   public static JsonArray b(Reader var0) {
      return b(a, var0, JsonArray.class, false);
   }

   public static String e(JsonElement var0) {
      StringWriter var1 = new StringWriter();
      JsonWriter var2 = new JsonWriter(var1);

      try {
         a(var2, var0, Comparator.naturalOrder());
      } catch (IOException var4) {
         throw new AssertionError(var4);
      }

      return var1.toString();
   }

   public static void a(JsonWriter var0, @Nullable JsonElement var1, @Nullable Comparator<String> var2) throws IOException {
      if (var1 == null || var1.isJsonNull()) {
         var0.nullValue();
      } else if (var1.isJsonPrimitive()) {
         JsonPrimitive var3 = var1.getAsJsonPrimitive();
         if (var3.isNumber()) {
            var0.value(var3.getAsNumber());
         } else if (var3.isBoolean()) {
            var0.value(var3.getAsBoolean());
         } else {
            var0.value(var3.getAsString());
         }
      } else if (var1.isJsonArray()) {
         var0.beginArray();

         for(JsonElement var4 : var1.getAsJsonArray()) {
            a(var0, var4, var2);
         }

         var0.endArray();
      } else {
         if (!var1.isJsonObject()) {
            throw new IllegalArgumentException("Couldn't write " + var1.getClass());
         }

         var0.beginObject();

         for(Entry<String, JsonElement> var4 : a(var1.getAsJsonObject().entrySet(), var2)) {
            var0.name(var4.getKey());
            a(var0, (JsonElement)var4.getValue(), var2);
         }

         var0.endObject();
      }
   }

   private static Collection<Entry<String, JsonElement>> a(Collection<Entry<String, JsonElement>> var0, @Nullable Comparator<String> var1) {
      if (var1 == null) {
         return var0;
      } else {
         List<Entry<String, JsonElement>> var2 = new ArrayList<>(var0);
         var2.sort(Entry.comparingByKey(var1));
         return var2;
      }
   }
}
