package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.WeightedRandomEnchant;
import net.minecraft.world.level.World;

public class ItemEnchantedBook extends Item {
   public static final String a = "StoredEnchantments";

   public ItemEnchantedBook(Item.Info var0) {
      super(var0);
   }

   @Override
   public boolean i(ItemStack var0) {
      return true;
   }

   @Override
   public boolean d_(ItemStack var0) {
      return false;
   }

   public static NBTTagList d(ItemStack var0) {
      NBTTagCompound var1 = var0.u();
      return var1 != null ? var1.c("StoredEnchantments", 10) : new NBTTagList();
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      super.a(var0, var1, var2, var3);
      ItemStack.a(var2, d(var0));
   }

   public static void a(ItemStack var0, WeightedRandomEnchant var1) {
      NBTTagList var2 = d(var0);
      boolean var3 = true;
      MinecraftKey var4 = EnchantmentManager.a(var1.a);

      for(int var5 = 0; var5 < var2.size(); ++var5) {
         NBTTagCompound var6 = var2.a(var5);
         MinecraftKey var7 = EnchantmentManager.b(var6);
         if (var7 != null && var7.equals(var4)) {
            if (EnchantmentManager.a(var6) < var1.b) {
               EnchantmentManager.a(var6, var1.b);
            }

            var3 = false;
            break;
         }
      }

      if (var3) {
         var2.add(EnchantmentManager.a(var4, var1.b));
      }

      var0.v().a("StoredEnchantments", var2);
   }

   public static ItemStack a(WeightedRandomEnchant var0) {
      ItemStack var1 = new ItemStack(Items.ty);
      a(var1, var0);
      return var1;
   }
}
