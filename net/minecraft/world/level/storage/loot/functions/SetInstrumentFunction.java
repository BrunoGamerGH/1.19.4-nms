package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetInstrumentFunction extends LootItemFunctionConditional {
   final TagKey<Instrument> a;

   SetInstrumentFunction(LootItemCondition[] var0, TagKey<Instrument> var1) {
      super(var0);
      this.a = var1;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.z;
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      InstrumentItem.a(var0, this.a, var1.a());
      return var0;
   }

   public static LootItemFunctionConditional.a<?> a(TagKey<Instrument> var0) {
      return a(var1 -> new SetInstrumentFunction(var1, var0));
   }

   public static class a extends LootItemFunctionConditional.c<SetInstrumentFunction> {
      public void a(JsonObject var0, SetInstrumentFunction var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.addProperty("options", "#" + var1.a.b());
      }

      public SetInstrumentFunction a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         String var3 = ChatDeserializer.h(var0, "options");
         if (!var3.startsWith("#")) {
            throw new JsonSyntaxException("Inline tag value not supported: " + var3);
         } else {
            return new SetInstrumentFunction(var2, TagKey.a(Registries.A, new MinecraftKey(var3.substring(1))));
         }
      }
   }
}
