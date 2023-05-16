package net.minecraft.recipebook;

import java.util.Iterator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.crafting.ShapedRecipes;

public interface AutoRecipeAbstract<T> {
   default void a(int var0, int var1, int var2, IRecipe<?> var3, Iterator<T> var4, int var5) {
      int var6 = var0;
      int var7 = var1;
      if (var3 instanceof ShapedRecipes var8) {
         var6 = var8.j();
         var7 = var8.k();
      }

      int var8 = 0;

      for(int var9 = 0; var9 < var1; ++var9) {
         if (var8 == var2) {
            ++var8;
         }

         boolean var10 = (float)var7 < (float)var1 / 2.0F;
         int var11 = MathHelper.d((float)var1 / 2.0F - (float)var7 / 2.0F);
         if (var10 && var11 > var9) {
            var8 += var0;
            ++var9;
         }

         for(int var12 = 0; var12 < var0; ++var12) {
            if (!var4.hasNext()) {
               return;
            }

            var10 = (float)var6 < (float)var0 / 2.0F;
            var11 = MathHelper.d((float)var0 / 2.0F - (float)var6 / 2.0F);
            int var13 = var6;
            boolean var14 = var12 < var6;
            if (var10) {
               var13 = var11 + var6;
               var14 = var11 <= var12 && var12 < var11 + var6;
            }

            if (var14) {
               this.a(var4, var8, var5, var9, var12);
            } else if (var13 == var12) {
               var8 += var0 - var12;
               break;
            }

            ++var8;
         }
      }
   }

   void a(Iterator<T> var1, int var2, int var3, int var4, int var5);
}
