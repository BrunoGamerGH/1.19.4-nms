package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemEnchantedBook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.WeightedRandomEnchant;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SetEnchantmentsFunction extends LootItemFunctionConditional {
   final Map<Enchantment, NumberProvider> a;
   final boolean b;

   SetEnchantmentsFunction(LootItemCondition[] var0, Map<Enchantment, NumberProvider> var1, boolean var2) {
      super(var0);
      this.a = ImmutableMap.copyOf(var1);
      this.b = var2;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.e;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return this.a.values().stream().flatMap(var0 -> var0.b().stream()).collect(ImmutableSet.toImmutableSet());
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      Object2IntMap<Enchantment> var2 = new Object2IntOpenHashMap();
      this.a.forEach((var2x, var3x) -> var2.put(var2x, var3x.a(var1)));
      if (var0.c() == Items.pX) {
         ItemStack var3 = new ItemStack(Items.ty);
         var2.forEach((var1x, var2x) -> ItemEnchantedBook.a(var3, new WeightedRandomEnchant(var1x, var2x)));
         return var3;
      } else {
         Map<Enchantment, Integer> var3 = EnchantmentManager.a(var0);
         if (this.b) {
            var2.forEach((var1x, var2x) -> a(var3, var1x, Math.max(var3.getOrDefault(var1x, 0) + var2x, 0)));
         } else {
            var2.forEach((var1x, var2x) -> a(var3, var1x, Math.max(var2x, 0)));
         }

         EnchantmentManager.a(var3, var0);
         return var0;
      }
   }

   private static void a(Map<Enchantment, Integer> var0, Enchantment var1, int var2) {
      if (var2 == 0) {
         var0.remove(var1);
      } else {
         var0.put(var1, var2);
      }
   }

   public static class a extends LootItemFunctionConditional.a<SetEnchantmentsFunction.a> {
      private final Map<Enchantment, NumberProvider> a = Maps.newHashMap();
      private final boolean b;

      public a() {
         this(false);
      }

      public a(boolean var0) {
         this.b = var0;
      }

      protected SetEnchantmentsFunction.a a() {
         return this;
      }

      public SetEnchantmentsFunction.a a(Enchantment var0, NumberProvider var1) {
         this.a.put(var0, var1);
         return this;
      }

      @Override
      public LootItemFunction b() {
         return new SetEnchantmentsFunction(this.g(), this.a, this.b);
      }
   }

   public static class b extends LootItemFunctionConditional.c<SetEnchantmentsFunction> {
      public void a(JsonObject var0, SetEnchantmentsFunction var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         JsonObject var3 = new JsonObject();
         var1.a.forEach((var2x, var3x) -> {
            MinecraftKey var4x = BuiltInRegistries.g.b(var2x);
            if (var4x == null) {
               throw new IllegalArgumentException("Don't know how to serialize enchantment " + var2x);
            } else {
               var3.add(var4x.toString(), var2.serialize(var3x));
            }
         });
         var0.add("enchantments", var3);
         var0.addProperty("add", var1.b);
      }

      public SetEnchantmentsFunction a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         Map<Enchantment, NumberProvider> var3 = Maps.newHashMap();
         if (var0.has("enchantments")) {
            JsonObject var4 = ChatDeserializer.t(var0, "enchantments");

            for(Entry<String, JsonElement> var6 : var4.entrySet()) {
               String var7 = var6.getKey();
               JsonElement var8 = (JsonElement)var6.getValue();
               Enchantment var9 = BuiltInRegistries.g
                  .b(new MinecraftKey(var7))
                  .orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + var7 + "'"));
               NumberProvider var10 = (NumberProvider)var1.deserialize(var8, NumberProvider.class);
               var3.put(var9, var10);
            }
         }

         boolean var4 = ChatDeserializer.a(var0, "add", false);
         return new SetEnchantmentsFunction(var2, var3, var4);
      }
   }
}
