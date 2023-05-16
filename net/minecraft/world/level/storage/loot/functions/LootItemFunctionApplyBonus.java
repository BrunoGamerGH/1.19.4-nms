package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootItemFunctionApplyBonus extends LootItemFunctionConditional {
   static final Map<MinecraftKey, LootItemFunctionApplyBonus.c> a = Maps.newHashMap();
   final Enchantment b;
   final LootItemFunctionApplyBonus.b c;

   LootItemFunctionApplyBonus(LootItemCondition[] var0, Enchantment var1, LootItemFunctionApplyBonus.b var2) {
      super(var0);
      this.b = var1;
      this.c = var2;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.q;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of(LootContextParameters.i);
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      ItemStack var2 = var1.c(LootContextParameters.i);
      if (var2 != null) {
         int var3 = EnchantmentManager.a(this.b, var2);
         int var4 = this.c.a(var1.a(), var0.K(), var3);
         var0.f(var4);
      }

      return var0;
   }

   public static LootItemFunctionConditional.a<?> a(Enchantment var0, float var1, int var2) {
      return a(var3 -> new LootItemFunctionApplyBonus(var3, var0, new LootItemFunctionApplyBonus.a(var2, var1)));
   }

   public static LootItemFunctionConditional.a<?> a(Enchantment var0) {
      return a(var1 -> new LootItemFunctionApplyBonus(var1, var0, new LootItemFunctionApplyBonus.d()));
   }

   public static LootItemFunctionConditional.a<?> b(Enchantment var0) {
      return a(var1 -> new LootItemFunctionApplyBonus(var1, var0, new LootItemFunctionApplyBonus.f(1)));
   }

   public static LootItemFunctionConditional.a<?> a(Enchantment var0, int var1) {
      return a(var2 -> new LootItemFunctionApplyBonus(var2, var0, new LootItemFunctionApplyBonus.f(var1)));
   }

   static {
      a.put(LootItemFunctionApplyBonus.a.a, LootItemFunctionApplyBonus.a::a);
      a.put(LootItemFunctionApplyBonus.d.a, LootItemFunctionApplyBonus.d::a);
      a.put(LootItemFunctionApplyBonus.f.a, LootItemFunctionApplyBonus.f::a);
   }

   static final class a implements LootItemFunctionApplyBonus.b {
      public static final MinecraftKey a = new MinecraftKey("binomial_with_bonus_count");
      private final int b;
      private final float c;

      public a(int var0, float var1) {
         this.b = var0;
         this.c = var1;
      }

      @Override
      public int a(RandomSource var0, int var1, int var2) {
         for(int var3 = 0; var3 < var2 + this.b; ++var3) {
            if (var0.i() < this.c) {
               ++var1;
            }
         }

         return var1;
      }

      @Override
      public void a(JsonObject var0, JsonSerializationContext var1) {
         var0.addProperty("extra", this.b);
         var0.addProperty("probability", this.c);
      }

      public static LootItemFunctionApplyBonus.b a(JsonObject var0, JsonDeserializationContext var1) {
         int var2 = ChatDeserializer.n(var0, "extra");
         float var3 = ChatDeserializer.l(var0, "probability");
         return new LootItemFunctionApplyBonus.a(var2, var3);
      }

      @Override
      public MinecraftKey a() {
         return a;
      }
   }

   interface b {
      int a(RandomSource var1, int var2, int var3);

      void a(JsonObject var1, JsonSerializationContext var2);

      MinecraftKey a();
   }

   interface c {
      LootItemFunctionApplyBonus.b deserialize(JsonObject var1, JsonDeserializationContext var2);
   }

   static final class d implements LootItemFunctionApplyBonus.b {
      public static final MinecraftKey a = new MinecraftKey("ore_drops");

      @Override
      public int a(RandomSource var0, int var1, int var2) {
         if (var2 > 0) {
            int var3 = var0.a(var2 + 2) - 1;
            if (var3 < 0) {
               var3 = 0;
            }

            return var1 * (var3 + 1);
         } else {
            return var1;
         }
      }

      @Override
      public void a(JsonObject var0, JsonSerializationContext var1) {
      }

      public static LootItemFunctionApplyBonus.b a(JsonObject var0, JsonDeserializationContext var1) {
         return new LootItemFunctionApplyBonus.d();
      }

      @Override
      public MinecraftKey a() {
         return a;
      }
   }

   public static class e extends LootItemFunctionConditional.c<LootItemFunctionApplyBonus> {
      public void a(JsonObject var0, LootItemFunctionApplyBonus var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.addProperty("enchantment", BuiltInRegistries.g.b(var1.b).toString());
         var0.addProperty("formula", var1.c.a().toString());
         JsonObject var3 = new JsonObject();
         var1.c.a(var3, var2);
         if (var3.size() > 0) {
            var0.add("parameters", var3);
         }
      }

      public LootItemFunctionApplyBonus a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         MinecraftKey var3 = new MinecraftKey(ChatDeserializer.h(var0, "enchantment"));
         Enchantment var4 = BuiltInRegistries.g.b(var3).orElseThrow(() -> new JsonParseException("Invalid enchantment id: " + var3));
         MinecraftKey var5 = new MinecraftKey(ChatDeserializer.h(var0, "formula"));
         LootItemFunctionApplyBonus.c var6 = LootItemFunctionApplyBonus.a.get(var5);
         if (var6 == null) {
            throw new JsonParseException("Invalid formula id: " + var5);
         } else {
            LootItemFunctionApplyBonus.b var7;
            if (var0.has("parameters")) {
               var7 = var6.deserialize(ChatDeserializer.t(var0, "parameters"), var1);
            } else {
               var7 = var6.deserialize(new JsonObject(), var1);
            }

            return new LootItemFunctionApplyBonus(var2, var4, var7);
         }
      }
   }

   static final class f implements LootItemFunctionApplyBonus.b {
      public static final MinecraftKey a = new MinecraftKey("uniform_bonus_count");
      private final int b;

      public f(int var0) {
         this.b = var0;
      }

      @Override
      public int a(RandomSource var0, int var1, int var2) {
         return var1 + var0.a(this.b * var2 + 1);
      }

      @Override
      public void a(JsonObject var0, JsonSerializationContext var1) {
         var0.addProperty("bonusMultiplier", this.b);
      }

      public static LootItemFunctionApplyBonus.b a(JsonObject var0, JsonDeserializationContext var1) {
         int var2 = ChatDeserializer.n(var0, "bonusMultiplier");
         return new LootItemFunctionApplyBonus.f(var2);
      }

      @Override
      public MinecraftKey a() {
         return a;
      }
   }
}
