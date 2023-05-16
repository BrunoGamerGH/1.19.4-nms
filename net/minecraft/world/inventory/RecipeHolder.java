package net.minecraft.world.inventory;

import java.util.Collections;
import javax.annotation.Nullable;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;

public interface RecipeHolder {
   void a(@Nullable IRecipe<?> var1);

   @Nullable
   IRecipe<?> d();

   default void b(EntityHuman var0) {
      IRecipe<?> var1 = this.d();
      if (var1 != null && !var1.ah_()) {
         var0.a(Collections.singleton(var1));
         this.a(null);
      }
   }

   default boolean a(World var0, EntityPlayer var1, IRecipe<?> var2) {
      if (!var2.ah_() && var0.W().b(GameRules.v) && !var1.E().b(var2)) {
         return false;
      } else {
         this.a(var2);
         return true;
      }
   }
}
