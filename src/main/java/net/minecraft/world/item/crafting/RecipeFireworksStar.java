package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.SystemUtils;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDye;
import net.minecraft.world.item.ItemFireworks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;

public class RecipeFireworksStar extends IRecipeComplex {
   private static final RecipeItemStack a = RecipeItemStack.a(
      Items.tb, Items.oB, Items.rp, Items.tn, Items.to, Items.tr, Items.tp, Items.ts, Items.tq, Items.tt
   );
   private static final RecipeItemStack b = RecipeItemStack.a(Items.nG);
   private static final RecipeItemStack c = RecipeItemStack.a(Items.qg);
   private static final Map<Item, ItemFireworks.EffectType> d = SystemUtils.a(Maps.newHashMap(), var0 -> {
      var0.put(Items.tb, ItemFireworks.EffectType.b);
      var0.put(Items.oB, ItemFireworks.EffectType.e);
      var0.put(Items.rp, ItemFireworks.EffectType.c);
      var0.put(Items.tn, ItemFireworks.EffectType.d);
      var0.put(Items.to, ItemFireworks.EffectType.d);
      var0.put(Items.tr, ItemFireworks.EffectType.d);
      var0.put(Items.tp, ItemFireworks.EffectType.d);
      var0.put(Items.ts, ItemFireworks.EffectType.d);
      var0.put(Items.tq, ItemFireworks.EffectType.d);
      var0.put(Items.tt, ItemFireworks.EffectType.d);
   });
   private static final RecipeItemStack e = RecipeItemStack.a(Items.oC);

   public RecipeFireworksStar(MinecraftKey var0, CraftingBookCategory var1) {
      super(var0, var1);
   }

   public boolean a(InventoryCrafting var0, World var1) {
      boolean var2 = false;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = false;

      for(int var7 = 0; var7 < var0.b(); ++var7) {
         ItemStack var8 = var0.a(var7);
         if (!var8.b()) {
            if (a.a(var8)) {
               if (var4) {
                  return false;
               }

               var4 = true;
            } else if (c.a(var8)) {
               if (var6) {
                  return false;
               }

               var6 = true;
            } else if (b.a(var8)) {
               if (var5) {
                  return false;
               }

               var5 = true;
            } else if (e.a(var8)) {
               if (var2) {
                  return false;
               }

               var2 = true;
            } else {
               if (!(var8.c() instanceof ItemDye)) {
                  return false;
               }

               var3 = true;
            }
         }
      }

      return var2 && var3;
   }

   public ItemStack a(InventoryCrafting var0, IRegistryCustom var1) {
      ItemStack var2 = new ItemStack(Items.tx);
      NBTTagCompound var3 = var2.a("Explosion");
      ItemFireworks.EffectType var4 = ItemFireworks.EffectType.a;
      List<Integer> var5 = Lists.newArrayList();

      for(int var6 = 0; var6 < var0.b(); ++var6) {
         ItemStack var7 = var0.a(var6);
         if (!var7.b()) {
            if (a.a(var7)) {
               var4 = d.get(var7.c());
            } else if (c.a(var7)) {
               var3.a("Flicker", true);
            } else if (b.a(var7)) {
               var3.a("Trail", true);
            } else if (var7.c() instanceof ItemDye) {
               var5.add(((ItemDye)var7.c()).d().f());
            }
         }
      }

      var3.b("Colors", var5);
      var3.a("Type", (byte)var4.a());
      return var2;
   }

   @Override
   public boolean a(int var0, int var1) {
      return var0 * var1 >= 2;
   }

   @Override
   public ItemStack a(IRegistryCustom var0) {
      return new ItemStack(Items.tx);
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.h;
   }
}
