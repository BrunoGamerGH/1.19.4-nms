package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.authlib.GameProfile;
import java.util.Set;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootItemFunctionFillPlayerHead extends LootItemFunctionConditional {
   final LootTableInfo.EntityTarget a;

   public LootItemFunctionFillPlayerHead(LootItemCondition[] var0, LootTableInfo.EntityTarget var1) {
      super(var0);
      this.a = var1;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.u;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of(this.a.a());
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      if (var0.a(Items.tp)) {
         Entity var2 = var1.c(this.a.a());
         if (var2 instanceof EntityHuman) {
            GameProfile var3 = ((EntityHuman)var2).fI();
            var0.v().a("SkullOwner", GameProfileSerializer.a(new NBTTagCompound(), var3));
         }
      }

      return var0;
   }

   public static LootItemFunctionConditional.a<?> a(LootTableInfo.EntityTarget var0) {
      return a(var1 -> new LootItemFunctionFillPlayerHead(var1, var0));
   }

   public static class a extends LootItemFunctionConditional.c<LootItemFunctionFillPlayerHead> {
      public void a(JsonObject var0, LootItemFunctionFillPlayerHead var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.add("entity", var2.serialize(var1.a));
      }

      public LootItemFunctionFillPlayerHead a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         LootTableInfo.EntityTarget var3 = ChatDeserializer.a(var0, "entity", var1, LootTableInfo.EntityTarget.class);
         return new LootItemFunctionFillPlayerHead(var2, var3);
      }
   }
}
