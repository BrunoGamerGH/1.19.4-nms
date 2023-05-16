package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;

public class LootItemConditionTableBonus implements LootItemCondition {
   final Enchantment a;
   final float[] b;

   LootItemConditionTableBonus(Enchantment var0, float[] var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.j;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of(LootContextParameters.i);
   }

   public boolean a(LootTableInfo var0) {
      ItemStack var1 = var0.c(LootContextParameters.i);
      int var2 = var1 != null ? EnchantmentManager.a(this.a, var1) : 0;
      float var3 = this.b[Math.min(var2, this.b.length - 1)];
      return var0.a().i() < var3;
   }

   public static LootItemCondition.a a(Enchantment var0, float... var1) {
      return () -> new LootItemConditionTableBonus(var0, var1);
   }

   public static class a implements LootSerializer<LootItemConditionTableBonus> {
      public void a(JsonObject var0, LootItemConditionTableBonus var1, JsonSerializationContext var2) {
         var0.addProperty("enchantment", BuiltInRegistries.g.b(var1.a).toString());
         var0.add("chances", var2.serialize(var1.b));
      }

      public LootItemConditionTableBonus b(JsonObject var0, JsonDeserializationContext var1) {
         MinecraftKey var2 = new MinecraftKey(ChatDeserializer.h(var0, "enchantment"));
         Enchantment var3 = BuiltInRegistries.g.b(var2).orElseThrow(() -> new JsonParseException("Invalid enchantment id: " + var2));
         float[] var4 = (float[])ChatDeserializer.a(var0, "chances", var1, float[].class);
         return new LootItemConditionTableBonus(var3, var4);
      }
   }
}
