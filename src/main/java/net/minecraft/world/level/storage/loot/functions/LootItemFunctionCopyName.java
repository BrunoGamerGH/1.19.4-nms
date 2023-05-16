package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.INamableTileEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootItemFunctionCopyName extends LootItemFunctionConditional {
   final LootItemFunctionCopyName.Source a;

   LootItemFunctionCopyName(LootItemCondition[] var0, LootItemFunctionCopyName.Source var1) {
      super(var0);
      this.a = var1;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.n;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of(this.a.f);
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      Object var2 = var1.c(this.a.f);
      if (var2 instanceof INamableTileEntity var3 && var3.aa()) {
         var0.a(var3.G_());
      }

      return var0;
   }

   public static LootItemFunctionConditional.a<?> a(LootItemFunctionCopyName.Source var0) {
      return a(var1 -> new LootItemFunctionCopyName(var1, var0));
   }

   public static enum Source {
      a("this", LootContextParameters.a),
      b("killer", LootContextParameters.d),
      c("killer_player", LootContextParameters.b),
      d("block_entity", LootContextParameters.h);

      public final String e;
      public final LootContextParameter<?> f;

      private Source(String var2, LootContextParameter var3) {
         this.e = var2;
         this.f = var3;
      }

      public static LootItemFunctionCopyName.Source a(String var0) {
         for(LootItemFunctionCopyName.Source var4 : values()) {
            if (var4.e.equals(var0)) {
               return var4;
            }
         }

         throw new IllegalArgumentException("Invalid name source " + var0);
      }
   }

   public static class b extends LootItemFunctionConditional.c<LootItemFunctionCopyName> {
      public void a(JsonObject var0, LootItemFunctionCopyName var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.addProperty("source", var1.a.e);
      }

      public LootItemFunctionCopyName a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         LootItemFunctionCopyName.Source var3 = LootItemFunctionCopyName.Source.a(ChatDeserializer.h(var0, "source"));
         return new LootItemFunctionCopyName(var2, var3);
      }
   }
}
