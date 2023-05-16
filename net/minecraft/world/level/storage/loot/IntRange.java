package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class IntRange {
   @Nullable
   final NumberProvider a;
   @Nullable
   final NumberProvider b;
   private final IntRange.b c;
   private final IntRange.a d;

   public Set<LootContextParameter<?>> a() {
      Builder<LootContextParameter<?>> var0 = ImmutableSet.builder();
      if (this.a != null) {
         var0.addAll(this.a.b());
      }

      if (this.b != null) {
         var0.addAll(this.b.b());
      }

      return var0.build();
   }

   IntRange(@Nullable NumberProvider var0, @Nullable NumberProvider var1) {
      this.a = var0;
      this.b = var1;
      if (var0 == null) {
         if (var1 == null) {
            this.c = (var0x, var1x) -> var1x;
            this.d = (var0x, var1x) -> true;
         } else {
            this.c = (var1x, var2x) -> Math.min(var1.a(var1x), var2x);
            this.d = (var1x, var2x) -> var2x <= var1.a(var1x);
         }
      } else if (var1 == null) {
         this.c = (var1x, var2x) -> Math.max(var0.a(var1x), var2x);
         this.d = (var1x, var2x) -> var2x >= var0.a(var1x);
      } else {
         this.c = (var2x, var3) -> MathHelper.a(var3, var0.a(var2x), var1.a(var2x));
         this.d = (var2x, var3) -> var3 >= var0.a(var2x) && var3 <= var1.a(var2x);
      }
   }

   public static IntRange a(int var0) {
      ConstantValue var1 = ConstantValue.a((float)var0);
      return new IntRange(var1, var1);
   }

   public static IntRange a(int var0, int var1) {
      return new IntRange(ConstantValue.a((float)var0), ConstantValue.a((float)var1));
   }

   public static IntRange b(int var0) {
      return new IntRange(ConstantValue.a((float)var0), null);
   }

   public static IntRange c(int var0) {
      return new IntRange(null, ConstantValue.a((float)var0));
   }

   public int a(LootTableInfo var0, int var1) {
      return this.c.apply(var0, var1);
   }

   public boolean b(LootTableInfo var0, int var1) {
      return this.d.test(var0, var1);
   }

   @FunctionalInterface
   interface a {
      boolean test(LootTableInfo var1, int var2);
   }

   @FunctionalInterface
   interface b {
      int apply(LootTableInfo var1, int var2);
   }

   public static class c implements JsonDeserializer<IntRange>, JsonSerializer<IntRange> {
      public IntRange a(JsonElement var0, Type var1, JsonDeserializationContext var2) {
         if (var0.isJsonPrimitive()) {
            return IntRange.a(var0.getAsInt());
         } else {
            JsonObject var3 = ChatDeserializer.m(var0, "value");
            NumberProvider var4 = var3.has("min") ? ChatDeserializer.a(var3, "min", var2, NumberProvider.class) : null;
            NumberProvider var5 = var3.has("max") ? ChatDeserializer.a(var3, "max", var2, NumberProvider.class) : null;
            return new IntRange(var4, var5);
         }
      }

      public JsonElement a(IntRange var0, Type var1, JsonSerializationContext var2) {
         JsonObject var3 = new JsonObject();
         if (Objects.equals(var0.b, var0.a)) {
            return var2.serialize(var0.a);
         } else {
            if (var0.b != null) {
               var3.add("max", var2.serialize(var0.b));
            }

            if (var0.a != null) {
               var3.add("min", var2.serialize(var0.a));
            }

            return var3;
         }
      }
   }
}
