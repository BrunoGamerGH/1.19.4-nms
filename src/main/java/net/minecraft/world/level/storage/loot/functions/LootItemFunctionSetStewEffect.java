package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemSuspiciousStew;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class LootItemFunctionSetStewEffect extends LootItemFunctionConditional {
   final Map<MobEffectList, NumberProvider> a;

   LootItemFunctionSetStewEffect(LootItemCondition[] var0, Map<MobEffectList, NumberProvider> var1) {
      super(var0);
      this.a = ImmutableMap.copyOf(var1);
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.m;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return this.a.values().stream().flatMap(var0 -> var0.b().stream()).collect(ImmutableSet.toImmutableSet());
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      if (var0.a(Items.uU) && !this.a.isEmpty()) {
         RandomSource var2 = var1.a();
         int var3 = var2.a(this.a.size());
         Entry<MobEffectList, NumberProvider> var4 = (Entry)Iterables.get(this.a.entrySet(), var3);
         MobEffectList var5 = var4.getKey();
         int var6 = var4.getValue().a(var1);
         if (!var5.a()) {
            var6 *= 20;
         }

         ItemSuspiciousStew.a(var0, var5, var6);
         return var0;
      } else {
         return var0;
      }
   }

   public static LootItemFunctionSetStewEffect.a c() {
      return new LootItemFunctionSetStewEffect.a();
   }

   public static class a extends LootItemFunctionConditional.a<LootItemFunctionSetStewEffect.a> {
      private final Map<MobEffectList, NumberProvider> a = Maps.newLinkedHashMap();

      protected LootItemFunctionSetStewEffect.a a() {
         return this;
      }

      public LootItemFunctionSetStewEffect.a a(MobEffectList var0, NumberProvider var1) {
         this.a.put(var0, var1);
         return this;
      }

      @Override
      public LootItemFunction b() {
         return new LootItemFunctionSetStewEffect(this.g(), this.a);
      }
   }

   public static class b extends LootItemFunctionConditional.c<LootItemFunctionSetStewEffect> {
      public void a(JsonObject var0, LootItemFunctionSetStewEffect var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         if (!var1.a.isEmpty()) {
            JsonArray var3 = new JsonArray();

            for(MobEffectList var5 : var1.a.keySet()) {
               JsonObject var6 = new JsonObject();
               MinecraftKey var7 = BuiltInRegistries.e.b(var5);
               if (var7 == null) {
                  throw new IllegalArgumentException("Don't know how to serialize mob effect " + var5);
               }

               var6.add("type", new JsonPrimitive(var7.toString()));
               var6.add("duration", var2.serialize(var1.a.get(var5)));
               var3.add(var6);
            }

            var0.add("effects", var3);
         }
      }

      public LootItemFunctionSetStewEffect a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         Map<MobEffectList, NumberProvider> var3 = Maps.newLinkedHashMap();
         if (var0.has("effects")) {
            for(JsonElement var6 : ChatDeserializer.u(var0, "effects")) {
               String var7 = ChatDeserializer.h(var6.getAsJsonObject(), "type");
               MobEffectList var8 = BuiltInRegistries.e
                  .b(new MinecraftKey(var7))
                  .orElseThrow(() -> new JsonSyntaxException("Unknown mob effect '" + var7 + "'"));
               NumberProvider var9 = ChatDeserializer.a(var6.getAsJsonObject(), "duration", var1, NumberProvider.class);
               var3.put(var8, var9);
            }
         }

         return new LootItemFunctionSetStewEffect(var2, var3);
      }
   }
}
