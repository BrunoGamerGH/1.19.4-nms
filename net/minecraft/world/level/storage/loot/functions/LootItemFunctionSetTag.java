package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.MojangsonParser;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootItemFunctionSetTag extends LootItemFunctionConditional {
   final NBTTagCompound a;

   LootItemFunctionSetTag(LootItemCondition[] var0, NBTTagCompound var1) {
      super(var0);
      this.a = var1;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.f;
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      var0.v().a(this.a);
      return var0;
   }

   @Deprecated
   public static LootItemFunctionConditional.a<?> a(NBTTagCompound var0) {
      return a(var1 -> new LootItemFunctionSetTag(var1, var0));
   }

   public static class a extends LootItemFunctionConditional.c<LootItemFunctionSetTag> {
      public void a(JsonObject var0, LootItemFunctionSetTag var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.addProperty("tag", var1.a.toString());
      }

      public LootItemFunctionSetTag a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         try {
            NBTTagCompound var3 = MojangsonParser.a(ChatDeserializer.h(var0, "tag"));
            return new LootItemFunctionSetTag(var2, var3);
         } catch (CommandSyntaxException var5) {
            throw new JsonSyntaxException(var5.getMessage());
         }
      }
   }
}
