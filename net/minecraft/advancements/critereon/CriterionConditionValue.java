package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.ChatDeserializer;

public abstract class CriterionConditionValue<T extends Number> {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.range.empty"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.range.swapped"));
   @Nullable
   protected final T c;
   @Nullable
   protected final T d;

   protected CriterionConditionValue(@Nullable T var0, @Nullable T var1) {
      this.c = var0;
      this.d = var1;
   }

   @Nullable
   public T a() {
      return this.c;
   }

   @Nullable
   public T b() {
      return this.d;
   }

   public boolean c() {
      return this.c == null && this.d == null;
   }

   public JsonElement d() {
      if (this.c()) {
         return JsonNull.INSTANCE;
      } else if (this.c != null && this.c.equals(this.d)) {
         return new JsonPrimitive(this.c);
      } else {
         JsonObject var0 = new JsonObject();
         if (this.c != null) {
            var0.addProperty("min", this.c);
         }

         if (this.d != null) {
            var0.addProperty("max", this.d);
         }

         return var0;
      }
   }

   protected static <T extends Number, R extends CriterionConditionValue<T>> R a(
      @Nullable JsonElement var0, R var1, BiFunction<JsonElement, String, T> var2, CriterionConditionValue.a<T, R> var3
   ) {
      if (var0 == null || var0.isJsonNull()) {
         return var1;
      } else if (ChatDeserializer.b(var0)) {
         T var4 = var2.apply((T)var0, "value");
         return var3.create(var4, var4);
      } else {
         JsonObject var4 = ChatDeserializer.m(var0, "value");
         T var5 = var4.has("min") ? var2.apply((T)var4.get("min"), "min") : null;
         T var6 = var4.has("max") ? var2.apply((T)var4.get("max"), "max") : null;
         return var3.create(var5, var6);
      }
   }

   protected static <T extends Number, R extends CriterionConditionValue<T>> R a(
      StringReader var0, CriterionConditionValue.b<T, R> var1, Function<String, T> var2, Supplier<DynamicCommandExceptionType> var3, Function<T, T> var4
   ) throws CommandSyntaxException {
      if (!var0.canRead()) {
         throw a.createWithContext(var0);
      } else {
         int var5 = var0.getCursor();

         try {
            T var6 = a(a(var0, var2, var3), var4);
            T var7;
            if (var0.canRead(2) && var0.peek() == '.' && var0.peek(1) == '.') {
               var0.skip();
               var0.skip();
               var7 = a(a(var0, var2, var3), var4);
               if (var6 == null && var7 == null) {
                  throw a.createWithContext(var0);
               }
            } else {
               var7 = var6;
            }

            if (var6 == null && var7 == null) {
               throw a.createWithContext(var0);
            } else {
               return var1.create(var0, var6, var7);
            }
         } catch (CommandSyntaxException var8) {
            var0.setCursor(var5);
            throw new CommandSyntaxException(var8.getType(), var8.getRawMessage(), var8.getInput(), var5);
         }
      }
   }

   @Nullable
   private static <T extends Number> T a(StringReader var0, Function<String, T> var1, Supplier<DynamicCommandExceptionType> var2) throws CommandSyntaxException {
      int var3 = var0.getCursor();

      while(var0.canRead() && a(var0)) {
         var0.skip();
      }

      String var4 = var0.getString().substring(var3, var0.getCursor());
      if (var4.isEmpty()) {
         return null;
      } else {
         try {
            return var1.apply(var4);
         } catch (NumberFormatException var6) {
            throw ((DynamicCommandExceptionType)var2.get()).createWithContext(var0, var4);
         }
      }
   }

   private static boolean a(StringReader var0) {
      char var1 = var0.peek();
      if ((var1 < '0' || var1 > '9') && var1 != '-') {
         if (var1 != '.') {
            return false;
         } else {
            return !var0.canRead(2) || var0.peek(1) != '.';
         }
      } else {
         return true;
      }
   }

   @Nullable
   private static <T> T a(@Nullable T var0, Function<T, T> var1) {
      return var0 == null ? null : var1.apply(var0);
   }

   public static class DoubleRange extends CriterionConditionValue<Double> {
      public static final CriterionConditionValue.DoubleRange e = new CriterionConditionValue.DoubleRange(null, null);
      @Nullable
      private final Double f;
      @Nullable
      private final Double g;

      private static CriterionConditionValue.DoubleRange a(StringReader var0, @Nullable Double var1, @Nullable Double var2) throws CommandSyntaxException {
         if (var1 != null && var2 != null && var1 > var2) {
            throw b.createWithContext(var0);
         } else {
            return new CriterionConditionValue.DoubleRange(var1, var2);
         }
      }

      @Nullable
      private static Double a(@Nullable Double var0) {
         return var0 == null ? null : var0 * var0;
      }

      private DoubleRange(@Nullable Double var0, @Nullable Double var1) {
         super(var0, var1);
         this.f = a(var0);
         this.g = a(var1);
      }

      public static CriterionConditionValue.DoubleRange a(double var0) {
         return new CriterionConditionValue.DoubleRange(var0, var0);
      }

      public static CriterionConditionValue.DoubleRange a(double var0, double var2) {
         return new CriterionConditionValue.DoubleRange(var0, var2);
      }

      public static CriterionConditionValue.DoubleRange b(double var0) {
         return new CriterionConditionValue.DoubleRange(var0, null);
      }

      public static CriterionConditionValue.DoubleRange c(double var0) {
         return new CriterionConditionValue.DoubleRange(null, var0);
      }

      public boolean d(double var0) {
         if (this.c != null && this.c > var0) {
            return false;
         } else {
            return this.d == null || !(this.d < var0);
         }
      }

      public boolean e(double var0) {
         if (this.f != null && this.f > var0) {
            return false;
         } else {
            return this.g == null || !(this.g < var0);
         }
      }

      public static CriterionConditionValue.DoubleRange a(@Nullable JsonElement var0) {
         return a(var0, e, ChatDeserializer::d, CriterionConditionValue.DoubleRange::new);
      }

      public static CriterionConditionValue.DoubleRange a(StringReader var0) throws CommandSyntaxException {
         return a(var0, var0x -> var0x);
      }

      public static CriterionConditionValue.DoubleRange a(StringReader var0, Function<Double, Double> var1) throws CommandSyntaxException {
         return a(var0, CriterionConditionValue.DoubleRange::a, Double::parseDouble, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidDouble, var1);
      }
   }

   public static class IntegerRange extends CriterionConditionValue<Integer> {
      public static final CriterionConditionValue.IntegerRange e = new CriterionConditionValue.IntegerRange(null, null);
      @Nullable
      private final Long f;
      @Nullable
      private final Long g;

      private static CriterionConditionValue.IntegerRange a(StringReader var0, @Nullable Integer var1, @Nullable Integer var2) throws CommandSyntaxException {
         if (var1 != null && var2 != null && var1 > var2) {
            throw b.createWithContext(var0);
         } else {
            return new CriterionConditionValue.IntegerRange(var1, var2);
         }
      }

      @Nullable
      private static Long a(@Nullable Integer var0) {
         return var0 == null ? null : var0.longValue() * var0.longValue();
      }

      private IntegerRange(@Nullable Integer var0, @Nullable Integer var1) {
         super(var0, var1);
         this.f = a(var0);
         this.g = a(var1);
      }

      public static CriterionConditionValue.IntegerRange a(int var0) {
         return new CriterionConditionValue.IntegerRange(var0, var0);
      }

      public static CriterionConditionValue.IntegerRange a(int var0, int var1) {
         return new CriterionConditionValue.IntegerRange(var0, var1);
      }

      public static CriterionConditionValue.IntegerRange b(int var0) {
         return new CriterionConditionValue.IntegerRange(var0, null);
      }

      public static CriterionConditionValue.IntegerRange c(int var0) {
         return new CriterionConditionValue.IntegerRange(null, var0);
      }

      public boolean d(int var0) {
         if (this.c != null && this.c > var0) {
            return false;
         } else {
            return this.d == null || this.d >= var0;
         }
      }

      public boolean a(long var0) {
         if (this.f != null && this.f > var0) {
            return false;
         } else {
            return this.g == null || this.g >= var0;
         }
      }

      public static CriterionConditionValue.IntegerRange a(@Nullable JsonElement var0) {
         return a(var0, e, ChatDeserializer::g, CriterionConditionValue.IntegerRange::new);
      }

      public static CriterionConditionValue.IntegerRange a(StringReader var0) throws CommandSyntaxException {
         return a(var0, var0x -> var0x);
      }

      public static CriterionConditionValue.IntegerRange a(StringReader var0, Function<Integer, Integer> var1) throws CommandSyntaxException {
         return a(var0, CriterionConditionValue.IntegerRange::a, Integer::parseInt, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidInt, var1);
      }
   }

   @FunctionalInterface
   protected interface a<T extends Number, R extends CriterionConditionValue<T>> {
      R create(@Nullable T var1, @Nullable T var2);
   }

   @FunctionalInterface
   protected interface b<T extends Number, R extends CriterionConditionValue<T>> {
      R create(StringReader var1, @Nullable T var2, @Nullable T var3) throws CommandSyntaxException;
   }
}
