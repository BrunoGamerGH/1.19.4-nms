package net.minecraft.advancements.critereon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.enchantment.Enchantment;

public class CriterionConditionEnchantments {
   public static final CriterionConditionEnchantments a = new CriterionConditionEnchantments();
   public static final CriterionConditionEnchantments[] b = new CriterionConditionEnchantments[0];
   @Nullable
   private final Enchantment c;
   private final CriterionConditionValue.IntegerRange d;

   public CriterionConditionEnchantments() {
      this.c = null;
      this.d = CriterionConditionValue.IntegerRange.e;
   }

   public CriterionConditionEnchantments(@Nullable Enchantment var0, CriterionConditionValue.IntegerRange var1) {
      this.c = var0;
      this.d = var1;
   }

   public boolean a(Map<Enchantment, Integer> var0) {
      if (this.c != null) {
         if (!var0.containsKey(this.c)) {
            return false;
         }

         int var1 = var0.get(this.c);
         if (this.d != CriterionConditionValue.IntegerRange.e && !this.d.d(var1)) {
            return false;
         }
      } else if (this.d != CriterionConditionValue.IntegerRange.e) {
         for(Integer var2 : var0.values()) {
            if (this.d.d(var2)) {
               return true;
            }
         }

         return false;
      }

      return true;
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();
         if (this.c != null) {
            var0.addProperty("enchantment", BuiltInRegistries.g.b(this.c).toString());
         }

         var0.add("levels", this.d.d());
         return var0;
      }
   }

   public static CriterionConditionEnchantments a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "enchantment");
         Enchantment var2 = null;
         if (var1.has("enchantment")) {
            MinecraftKey var3 = new MinecraftKey(ChatDeserializer.h(var1, "enchantment"));
            var2 = BuiltInRegistries.g.b(var3).orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + var3 + "'"));
         }

         CriterionConditionValue.IntegerRange var3 = CriterionConditionValue.IntegerRange.a(var1.get("levels"));
         return new CriterionConditionEnchantments(var2, var3);
      } else {
         return a;
      }
   }

   public static CriterionConditionEnchantments[] b(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonArray var1 = ChatDeserializer.n(var0, "enchantments");
         CriterionConditionEnchantments[] var2 = new CriterionConditionEnchantments[var1.size()];

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3] = a(var1.get(var3));
         }

         return var2;
      } else {
         return b;
      }
   }
}
