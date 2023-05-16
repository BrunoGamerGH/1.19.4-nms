package net.minecraft.world.item.crafting;

import java.util.List;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.tags.TagsItem;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;

public class DecoratedPotRecipe extends IRecipeComplex {
   public DecoratedPotRecipe(MinecraftKey var0, CraftingBookCategory var1) {
      super(var0, var1);
   }

   public boolean a(InventoryCrafting var0, World var1) {
      if (!this.a(var0.g(), var0.f())) {
         return false;
      } else {
         for(int var2 = 0; var2 < var0.b(); ++var2) {
            ItemStack var3 = var0.a(var2);
            switch(var2) {
               case 1:
               case 3:
               case 5:
               case 7:
                  if (!var3.a(TagsItem.aK)) {
                     return false;
                  }
                  break;
               case 2:
               case 4:
               case 6:
               default:
                  if (!var3.a(Items.a)) {
                     return false;
                  }
            }
         }

         return true;
      }
   }

   public ItemStack a(InventoryCrafting var0, IRegistryCustom var1) {
      ItemStack var2 = Items.ee.ad_();
      NBTTagCompound var3 = new NBTTagCompound();
      DecoratedPotBlockEntity.a(List.of(var0.a(1).c(), var0.a(3).c(), var0.a(5).c(), var0.a(7).c()), var3);
      ItemBlock.a(var2, TileEntityTypes.N, var3);
      return var2;
   }

   @Override
   public boolean a(int var0, int var1) {
      return var0 == 3 && var1 == 3;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.x;
   }
}
