package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class LootEnchantLevel extends LootItemFunctionConditional {
   final NumberProvider a;
   final boolean b;

   LootEnchantLevel(LootItemCondition[] var0, NumberProvider var1, boolean var2) {
      super(var0);
      this.a = var1;
      this.b = var2;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.c;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return this.a.b();
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      RandomSource var2 = var1.a();
      return EnchantmentManager.a(var2, var0, this.a.a(var1), this.b);
   }

   public static LootEnchantLevel.a a(NumberProvider var0) {
      return new LootEnchantLevel.a(var0);
   }

   public static class a extends LootItemFunctionConditional.a<LootEnchantLevel.a> {
      private final NumberProvider a;
      private boolean b;

      public a(NumberProvider var0) {
         this.a = var0;
      }

      protected LootEnchantLevel.a a() {
         return this;
      }

      public LootEnchantLevel.a e() {
         this.b = true;
         return this;
      }

      @Override
      public LootItemFunction b() {
         return new LootEnchantLevel(this.g(), this.a, this.b);
      }
   }

   public static class b extends LootItemFunctionConditional.c<LootEnchantLevel> {
      public void a(JsonObject var0, LootEnchantLevel var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.add("levels", var2.serialize(var1.a));
         var0.addProperty("treasure", var1.b);
      }

      public LootEnchantLevel a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         NumberProvider var3 = ChatDeserializer.a(var0, "levels", var1, NumberProvider.class);
         boolean var4 = ChatDeserializer.a(var0, "treasure", false);
         return new LootEnchantLevel(var2, var3, var4);
      }
   }
}
