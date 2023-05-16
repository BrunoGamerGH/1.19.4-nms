package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class LootEnchantFunction extends LootItemFunctionConditional {
   public static final int a = 0;
   final NumberProvider b;
   final int c;

   LootEnchantFunction(LootItemCondition[] alootitemcondition, NumberProvider numberprovider, int i) {
      super(alootitemcondition);
      this.b = numberprovider;
      this.c = i;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.h;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return Sets.union(ImmutableSet.of(LootContextParameters.d), this.b.b());
   }

   boolean c() {
      return this.c > 0;
   }

   @Override
   public ItemStack a(ItemStack itemstack, LootTableInfo loottableinfo) {
      Entity entity = loottableinfo.c(LootContextParameters.d);
      if (entity instanceof EntityLiving) {
         int i = EnchantmentManager.h((EntityLiving)entity);
         if (loottableinfo.a(LootContextParameters.LOOTING_MOD)) {
            i = loottableinfo.c(LootContextParameters.LOOTING_MOD);
         }

         if (i <= 0) {
            return itemstack;
         }

         float f = (float)i * this.b.b(loottableinfo);
         itemstack.g(Math.round(f));
         if (this.c() && itemstack.K() > this.c) {
            itemstack.f(this.c);
         }
      }

      return itemstack;
   }

   public static LootEnchantFunction.a a(NumberProvider numberprovider) {
      return new LootEnchantFunction.a(numberprovider);
   }

   public static class a extends LootItemFunctionConditional.a<LootEnchantFunction.a> {
      private final NumberProvider a;
      private int b = 0;

      public a(NumberProvider numberprovider) {
         this.a = numberprovider;
      }

      protected LootEnchantFunction.a a() {
         return this;
      }

      public LootEnchantFunction.a a(int i) {
         this.b = i;
         return this;
      }

      @Override
      public LootItemFunction b() {
         return new LootEnchantFunction(this.g(), this.a, this.b);
      }
   }

   public static class b extends LootItemFunctionConditional.c<LootEnchantFunction> {
      public void a(JsonObject jsonobject, LootEnchantFunction lootenchantfunction, JsonSerializationContext jsonserializationcontext) {
         super.a(jsonobject, lootenchantfunction, jsonserializationcontext);
         jsonobject.add("count", jsonserializationcontext.serialize(lootenchantfunction.b));
         if (lootenchantfunction.c()) {
            jsonobject.add("limit", jsonserializationcontext.serialize(lootenchantfunction.c));
         }
      }

      public LootEnchantFunction a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext, LootItemCondition[] alootitemcondition) {
         int i = ChatDeserializer.a(jsonobject, "limit", 0);
         return new LootEnchantFunction(alootitemcondition, ChatDeserializer.a(jsonobject, "count", jsondeserializationcontext, NumberProvider.class), i);
      }
   }
}
