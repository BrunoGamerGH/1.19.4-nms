package net.minecraft.world.level.storage.loot.functions;

import java.util.function.BiFunction;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.JsonRegistry;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class LootItemFunctions {
   public static final BiFunction<ItemStack, LootTableInfo, ItemStack> a = (var0, var1) -> var0;
   public static final LootItemFunctionType b = a("set_count", new LootItemFunctionSetCount.a());
   public static final LootItemFunctionType c = a("enchant_with_levels", new LootEnchantLevel.b());
   public static final LootItemFunctionType d = a("enchant_randomly", new LootItemFunctionEnchant.b());
   public static final LootItemFunctionType e = a("set_enchantments", new SetEnchantmentsFunction.b());
   public static final LootItemFunctionType f = a("set_nbt", new LootItemFunctionSetTag.a());
   public static final LootItemFunctionType g = a("furnace_smelt", new LootItemFunctionSmelt.a());
   public static final LootItemFunctionType h = a("looting_enchant", new LootEnchantFunction.b());
   public static final LootItemFunctionType i = a("set_damage", new LootItemFunctionSetDamage.a());
   public static final LootItemFunctionType j = a("set_attributes", new LootItemFunctionSetAttribute.d());
   public static final LootItemFunctionType k = a("set_name", new LootItemFunctionSetName.a());
   public static final LootItemFunctionType l = a("exploration_map", new LootItemFunctionExplorationMap.b());
   public static final LootItemFunctionType m = a("set_stew_effect", new LootItemFunctionSetStewEffect.b());
   public static final LootItemFunctionType n = a("copy_name", new LootItemFunctionCopyName.b());
   public static final LootItemFunctionType o = a("set_contents", new LootItemFunctionSetContents.b());
   public static final LootItemFunctionType p = a("limit_count", new LootItemFunctionLimitCount.a());
   public static final LootItemFunctionType q = a("apply_bonus", new LootItemFunctionApplyBonus.e());
   public static final LootItemFunctionType r = a("set_loot_table", new LootItemFunctionSetTable.a());
   public static final LootItemFunctionType s = a("explosion_decay", new LootItemFunctionExplosionDecay.a());
   public static final LootItemFunctionType t = a("set_lore", new LootItemFunctionSetLore.b());
   public static final LootItemFunctionType u = a("fill_player_head", new LootItemFunctionFillPlayerHead.a());
   public static final LootItemFunctionType v = a("copy_nbt", new LootItemFunctionCopyNBT.d());
   public static final LootItemFunctionType w = a("copy_state", new LootItemFunctionCopyState.b());
   public static final LootItemFunctionType x = a("set_banner_pattern", new SetBannerPatternFunction.b());
   public static final LootItemFunctionType y = a("set_potion", new SetPotionFunction.a());
   public static final LootItemFunctionType z = a("set_instrument", new SetInstrumentFunction.a());

   private static LootItemFunctionType a(String var0, LootSerializer<? extends LootItemFunction> var1) {
      return IRegistry.a(BuiltInRegistries.G, new MinecraftKey(var0), new LootItemFunctionType(var1));
   }

   public static Object a() {
      return JsonRegistry.a(BuiltInRegistries.G, "function", "function", LootItemFunction::a).a();
   }

   public static BiFunction<ItemStack, LootTableInfo, ItemStack> a(BiFunction<ItemStack, LootTableInfo, ItemStack>[] var0) {
      switch(var0.length) {
         case 0:
            return a;
         case 1:
            return var0[0];
         case 2:
            BiFunction<ItemStack, LootTableInfo, ItemStack> var1 = var0[0];
            BiFunction<ItemStack, LootTableInfo, ItemStack> var2 = var0[1];
            return (var2x, var3) -> var2.apply(var1.apply(var2x, var3), var3);
         default:
            return (var1x, var2x) -> {
               for(BiFunction<ItemStack, LootTableInfo, ItemStack> var6 : var0) {
                  var1x = var6.apply(var1x, var2x);
               }

               return var1x;
            };
      }
   }
}
