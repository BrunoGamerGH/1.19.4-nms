package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.ChatDeserializer;

public class CriterionConditionRange {
   public static final CriterionConditionRange a = new CriterionConditionRange(null, null);
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.range.ints"));
   @Nullable
   private final Float c;
   @Nullable
   private final Float d;

   public CriterionConditionRange(@Nullable Float var0, @Nullable Float var1) {
      this.c = var0;
      this.d = var1;
   }

   public static CriterionConditionRange a(float var0) {
      return new CriterionConditionRange(var0, var0);
   }

   public static CriterionConditionRange a(float var0, float var1) {
      return new CriterionConditionRange(var0, var1);
   }

   public static CriterionConditionRange b(float var0) {
      return new CriterionConditionRange(var0, null);
   }

   public static CriterionConditionRange c(float var0) {
      return new CriterionConditionRange(null, var0);
   }

   public boolean d(float var0) {
      if (this.c != null && this.d != null && this.c > this.d && this.c > var0 && this.d < var0) {
         return false;
      } else if (this.c != null && this.c > var0) {
         return false;
      } else {
         return this.d == null || !(this.d < var0);
      }
   }

   public boolean a(double var0) {
      if (this.c != null && this.d != null && this.c > this.d && (double)(this.c * this.c) > var0 && (double)(this.d * this.d) < var0) {
         return false;
      } else if (this.c != null && (double)(this.c * this.c) > var0) {
         return false;
      } else {
         return this.d == null || !((double)(this.d * this.d) < var0);
      }
   }

   @Nullable
   public Float a() {
      return this.c;
   }

   @Nullable
   public Float b() {
      return this.d;
   }

   public JsonElement c() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else if (this.c != null && this.d != null && this.c.equals(this.d)) {
         return new JsonPrimitive(this.c);
      } else {
         JsonObject var0 = new JsonObject();
         if (this.c != null) {
            var0.addProperty("min", this.c);
         }

         if (this.d != null) {
            var0.addProperty("max", this.c);
         }

         return var0;
      }
   }

   public static CriterionConditionRange a(@Nullable JsonElement var0) {
      if (var0 == null || var0.isJsonNull()) {
         return a;
      } else if (ChatDeserializer.b(var0)) {
         float var1 = ChatDeserializer.e(var0, "value");
         return new CriterionConditionRange(var1, var1);
      } else {
         JsonObject var1 = ChatDeserializer.m(var0, "value");
         Float var2 = var1.has("min") ? ChatDeserializer.l(var1, "min") : null;
         Float var3 = var1.has("max") ? ChatDeserializer.l(var1, "max") : null;
         return new CriterionConditionRange(var2, var3);
      }
   }

   public static CriterionConditionRange a(StringReader var0, boolean var1) throws CommandSyntaxException {
      return a(var0, var1, var0x -> var0x);
   }

   public static CriterionConditionRange a(StringReader var0, boolean var1, Function<Float, Float> var2) throws CommandSyntaxException {
      if (!var0.canRead()) {
         throw CriterionConditionValue.a.createWithContext(var0);
      } else {
         int var3 = var0.getCursor();
         Float var4 = a(b(var0, var1), var2);
         Float var5;
         if (var0.canRead(2) && var0.peek() == '.' && var0.peek(1) == '.') {
            var0.skip();
            var0.skip();
            var5 = a(b(var0, var1), var2);
            if (var4 == null && var5 == null) {
               var0.setCursor(var3);
               throw CriterionConditionValue.a.createWithContext(var0);
            }
         } else {
            if (!var1 && var0.canRead() && var0.peek() == '.') {
               var0.setCursor(var3);
               throw b.createWithContext(var0);
            }

            var5 = var4;
         }

         if (var4 == null && var5 == null) {
            var0.setCursor(var3);
            throw CriterionConditionValue.a.createWithContext(var0);
         } else {
            return new CriterionConditionRange(var4, var5);
         }
      }
   }

   @Nullable
   private static Float b(StringReader var0, boolean var1) throws CommandSyntaxException {
      int var2 = var0.getCursor();

      while(var0.canRead() && c(var0, var1)) {
         var0.skip();
      }

      String var3 = var0.getString().substring(var2, var0.getCursor());
      if (var3.isEmpty()) {
         return null;
      } else {
         try {
            return Float.parseFloat(var3);
         } catch (NumberFormatException var5) {
            if (var1) {
               throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidDouble().createWithContext(var0, var3);
            } else {
               throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().createWithContext(var0, var3);
            }
         }
      }
   }

   private static boolean c(StringReader var0, boolean var1) {
      char var2 = var0.peek();
      if ((var2 < '0' || var2 > '9') && var2 != '-') {
         if (var1 && var2 == '.') {
            return !var0.canRead(2) || var0.peek(1) != '.';
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   @Nullable
   private static Float a(@Nullable Float var0, Function<Float, Float> var1) {
      return var0 == null ? null : var1.apply(var0);
   }
}
