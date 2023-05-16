package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.World;

public class RecipeRepair extends IRecipeComplex {
   public RecipeRepair(MinecraftKey var0, CraftingBookCategory var1) {
      super(var0, var1);
   }

   public boolean a(InventoryCrafting var0, World var1) {
      List<ItemStack> var2 = Lists.newArrayList();

      for(int var3 = 0; var3 < var0.b(); ++var3) {
         ItemStack var4 = var0.a(var3);
         if (!var4.b()) {
            var2.add(var4);
            if (var2.size() > 1) {
               ItemStack var5 = var2.get(0);
               if (!var4.a(var5.c()) || var5.K() != 1 || var4.K() != 1 || !var5.c().o()) {
                  return false;
               }
            }
         }
      }

      return var2.size() == 2;
   }

   public ItemStack a(InventoryCrafting var0, IRegistryCustom var1) {
      List<ItemStack> var2 = Lists.newArrayList();

      for(int var3 = 0; var3 < var0.b(); ++var3) {
         ItemStack var4 = var0.a(var3);
         if (!var4.b()) {
            var2.add(var4);
            if (var2.size() > 1) {
               ItemStack var5 = var2.get(0);
               if (!var4.a(var5.c()) || var5.K() != 1 || var4.K() != 1 || !var5.c().o()) {
                  return ItemStack.b;
               }
            }
         }
      }

      if (var2.size() == 2) {
         ItemStack var3 = var2.get(0);
         ItemStack var4 = var2.get(1);
         if (var3.a(var4.c()) && var3.K() == 1 && var4.K() == 1 && var3.c().o()) {
            Item var5 = var3.c();
            int var6 = var5.n() - var3.j();
            int var7 = var5.n() - var4.j();
            int var8 = var6 + var7 + var5.n() * 5 / 100;
            int var9 = var5.n() - var8;
            if (var9 < 0) {
               var9 = 0;
            }

            ItemStack var10 = new ItemStack(var3.c());
            var10.b(var9);
            Map<Enchantment, Integer> var11 = Maps.newHashMap();
            Map<Enchantment, Integer> var12 = EnchantmentManager.a(var3);
            Map<Enchantment, Integer> var13 = EnchantmentManager.a(var4);
            BuiltInRegistries.g.s().filter(Enchantment::c).forEach(var3x -> {
               int var4x = Math.max(var12.getOrDefault(var3x, 0), var13.getOrDefault(var3x, 0));
               if (var4x > 0) {
                  var11.put(var3x, var4x);
               }
            });
            if (!var11.isEmpty()) {
               EnchantmentManager.a(var11, var10);
            }

            return var10;
         }
      }

      return ItemStack.b;
   }

   @Override
   public boolean a(int var0, int var1) {
      return var0 * var1 >= 2;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.o;
   }
}
