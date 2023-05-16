package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.slf4j.Logger;

public class LootItemFunctionSetName extends LootItemFunctionConditional {
   private static final Logger a = LogUtils.getLogger();
   final IChatBaseComponent b;
   @Nullable
   final LootTableInfo.EntityTarget c;

   LootItemFunctionSetName(LootItemCondition[] var0, @Nullable IChatBaseComponent var1, @Nullable LootTableInfo.EntityTarget var2) {
      super(var0);
      this.b = var1;
      this.c = var2;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.k;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return this.c != null ? ImmutableSet.of(this.c.a()) : ImmutableSet.of();
   }

   public static UnaryOperator<IChatBaseComponent> a(LootTableInfo var0, @Nullable LootTableInfo.EntityTarget var1) {
      if (var1 != null) {
         Entity var2 = var0.c(var1.a());
         if (var2 != null) {
            CommandListenerWrapper var3 = var2.cZ().a(2);
            return var2x -> {
               try {
                  return ChatComponentUtils.a(var3, var2x, var2, 0);
               } catch (CommandSyntaxException var4) {
                  a.warn("Failed to resolve text component", var4);
                  return var2x;
               }
            };
         }
      }

      return var0x -> var0x;
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      if (this.b != null) {
         var0.a(a(var1, this.c).apply(this.b));
      }

      return var0;
   }

   public static LootItemFunctionConditional.a<?> a(IChatBaseComponent var0) {
      return a(var1 -> new LootItemFunctionSetName(var1, var0, null));
   }

   public static LootItemFunctionConditional.a<?> a(IChatBaseComponent var0, LootTableInfo.EntityTarget var1) {
      return a(var2 -> new LootItemFunctionSetName(var2, var0, var1));
   }

   public static class a extends LootItemFunctionConditional.c<LootItemFunctionSetName> {
      public void a(JsonObject var0, LootItemFunctionSetName var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         if (var1.b != null) {
            var0.add("name", IChatBaseComponent.ChatSerializer.c(var1.b));
         }

         if (var1.c != null) {
            var0.add("entity", var2.serialize(var1.c));
         }
      }

      public LootItemFunctionSetName a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         IChatBaseComponent var3 = IChatBaseComponent.ChatSerializer.a(var0.get("name"));
         LootTableInfo.EntityTarget var4 = ChatDeserializer.a(var0, "entity", null, var1, LootTableInfo.EntityTarget.class);
         return new LootItemFunctionSetName(var2, var3, var4);
      }
   }
}
