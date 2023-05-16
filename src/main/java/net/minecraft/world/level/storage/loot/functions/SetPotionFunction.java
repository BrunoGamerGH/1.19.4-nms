package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionRegistry;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetPotionFunction extends LootItemFunctionConditional {
   final PotionRegistry a;

   SetPotionFunction(LootItemCondition[] var0, PotionRegistry var1) {
      super(var0);
      this.a = var1;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.y;
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      PotionUtil.a(var0, this.a);
      return var0;
   }

   public static LootItemFunctionConditional.a<?> a(PotionRegistry var0) {
      return a(var1 -> new SetPotionFunction(var1, var0));
   }

   public static class a extends LootItemFunctionConditional.c<SetPotionFunction> {
      public void a(JsonObject var0, SetPotionFunction var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.addProperty("id", BuiltInRegistries.j.b(var1.a).toString());
      }

      public SetPotionFunction a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         String var3 = ChatDeserializer.h(var0, "id");
         PotionRegistry var4 = BuiltInRegistries.j.b(MinecraftKey.a(var3)).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + var3 + "'"));
         return new SetPotionFunction(var2, var4);
      }
   }
}
