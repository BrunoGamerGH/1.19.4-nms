package net.minecraft.world.item;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Optional;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.crafting.CraftingManager;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.level.World;
import org.slf4j.Logger;

public class ItemKnowledgeBook extends Item {
   private static final String a = "Recipes";
   private static final Logger b = LogUtils.getLogger();

   public ItemKnowledgeBook(Item.Info var0) {
      super(var0);
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      ItemStack var3 = var1.b(var2);
      NBTTagCompound var4 = var3.u();
      if (!var1.fK().d) {
         var1.a(var2, ItemStack.b);
      }

      if (var4 != null && var4.b("Recipes", 9)) {
         if (!var0.B) {
            NBTTagList var5 = var4.c("Recipes", 8);
            List<IRecipe<?>> var6 = Lists.newArrayList();
            CraftingManager var7 = var0.n().aE();

            for(int var8 = 0; var8 < var5.size(); ++var8) {
               String var9 = var5.j(var8);
               Optional<? extends IRecipe<?>> var10 = var7.a(new MinecraftKey(var9));
               if (!var10.isPresent()) {
                  b.error("Invalid recipe: {}", var9);
                  return InteractionResultWrapper.d(var3);
               }

               var6.add(var10.get());
            }

            var1.a(var6);
            var1.b(StatisticList.c.b(this));
         }

         return InteractionResultWrapper.a(var3, var0.k_());
      } else {
         b.error("Tag not valid: {}", var4);
         return InteractionResultWrapper.d(var3);
      }
   }
}
