package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import java.util.Optional;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.FurnaceRecipe;
import net.minecraft.world.item.crafting.Recipes;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.slf4j.Logger;

public class LootItemFunctionSmelt extends LootItemFunctionConditional {
   private static final Logger a = LogUtils.getLogger();

   LootItemFunctionSmelt(LootItemCondition[] var0) {
      super(var0);
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.g;
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      if (var0.b()) {
         return var0;
      } else {
         Optional<FurnaceRecipe> var2 = var1.c().q().a(Recipes.b, new InventorySubcontainer(var0), var1.c());
         if (var2.isPresent()) {
            ItemStack var3 = var2.get().a(var1.c().u_());
            if (!var3.b()) {
               ItemStack var4 = var3.o();
               var4.f(var0.K());
               return var4;
            }
         }

         a.warn("Couldn't smelt {} because there is no smelting recipe", var0);
         return var0;
      }
   }

   public static LootItemFunctionConditional.a<?> c() {
      return a(LootItemFunctionSmelt::new);
   }

   public static class a extends LootItemFunctionConditional.c<LootItemFunctionSmelt> {
      public LootItemFunctionSmelt a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         return new LootItemFunctionSmelt(var2);
      }
   }
}
