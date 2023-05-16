package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.logging.LogUtils;
import java.util.Set;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.slf4j.Logger;

public class LootItemFunctionSetDamage extends LootItemFunctionConditional {
   private static final Logger a = LogUtils.getLogger();
   final NumberProvider b;
   final boolean c;

   LootItemFunctionSetDamage(LootItemCondition[] var0, NumberProvider var1, boolean var2) {
      super(var0);
      this.b = var1;
      this.c = var2;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.i;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return this.b.b();
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      if (var0.h()) {
         int var2 = var0.k();
         float var3 = this.c ? 1.0F - (float)var0.j() / (float)var2 : 0.0F;
         float var4 = 1.0F - MathHelper.a(this.b.b(var1) + var3, 0.0F, 1.0F);
         var0.b(MathHelper.d(var4 * (float)var2));
      } else {
         a.warn("Couldn't set damage of loot item {}", var0);
      }

      return var0;
   }

   public static LootItemFunctionConditional.a<?> a(NumberProvider var0) {
      return a(var1 -> new LootItemFunctionSetDamage(var1, var0, false));
   }

   public static LootItemFunctionConditional.a<?> a(NumberProvider var0, boolean var1) {
      return a(var2 -> new LootItemFunctionSetDamage(var2, var0, var1));
   }

   public static class a extends LootItemFunctionConditional.c<LootItemFunctionSetDamage> {
      public void a(JsonObject var0, LootItemFunctionSetDamage var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.add("damage", var2.serialize(var1.b));
         var0.addProperty("add", var1.c);
      }

      public LootItemFunctionSetDamage a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         NumberProvider var3 = ChatDeserializer.a(var0, "damage", var1, NumberProvider.class);
         boolean var4 = ChatDeserializer.a(var0, "add", false);
         return new LootItemFunctionSetDamage(var2, var3, var4);
      }
   }
}
