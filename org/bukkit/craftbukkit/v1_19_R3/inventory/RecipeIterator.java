package org.bukkit.craftbukkit.v1_19_R3.inventory;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.crafting.Recipes;
import org.bukkit.inventory.Recipe;

public class RecipeIterator implements Iterator<Recipe> {
   private final Iterator<Entry<Recipes<?>, Object2ObjectLinkedOpenHashMap<MinecraftKey, IRecipe<?>>>> recipes = MinecraftServer.getServer()
      .aE()
      .c
      .entrySet()
      .iterator();
   private Iterator<IRecipe<?>> current;

   @Override
   public boolean hasNext() {
      if (this.current != null && this.current.hasNext()) {
         return true;
      } else if (this.recipes.hasNext()) {
         this.current = ((Object2ObjectLinkedOpenHashMap)this.recipes.next().getValue()).values().iterator();
         return this.hasNext();
      } else {
         return false;
      }
   }

   public Recipe next() {
      if (this.current != null && this.current.hasNext()) {
         return this.current.next().toBukkitRecipe();
      } else {
         this.current = ((Object2ObjectLinkedOpenHashMap)this.recipes.next().getValue()).values().iterator();
         return this.next();
      }
   }

   @Override
   public void remove() {
      if (this.current == null) {
         throw new IllegalStateException("next() not yet called");
      } else {
         this.current.remove();
      }
   }
}
