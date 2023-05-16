package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootItemFunctionExplosionDecay extends LootItemFunctionConditional {
   LootItemFunctionExplosionDecay(LootItemCondition[] var0) {
      super(var0);
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.s;
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      Float var2 = var1.c(LootContextParameters.j);
      if (var2 != null) {
         RandomSource var3 = var1.a();
         float var4 = 1.0F / var2;
         int var5 = var0.K();
         int var6 = 0;

         for(int var7 = 0; var7 < var5; ++var7) {
            if (var3.i() <= var4) {
               ++var6;
            }
         }

         var0.f(var6);
      }

      return var0;
   }

   public static LootItemFunctionConditional.a<?> c() {
      return a(LootItemFunctionExplosionDecay::new);
   }

   public static class a extends LootItemFunctionConditional.c<LootItemFunctionExplosionDecay> {
      public LootItemFunctionExplosionDecay a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         return new LootItemFunctionExplosionDecay(var2);
      }
   }
}
