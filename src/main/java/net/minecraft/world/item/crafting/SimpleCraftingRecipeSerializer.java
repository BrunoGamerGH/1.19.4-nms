package net.minecraft.world.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;

public class SimpleCraftingRecipeSerializer<T extends RecipeCrafting> implements RecipeSerializer<T> {
   private final SimpleCraftingRecipeSerializer.a<T> y;

   public SimpleCraftingRecipeSerializer(SimpleCraftingRecipeSerializer.a<T> var0) {
      this.y = var0;
   }

   public T a(MinecraftKey var0, JsonObject var1) {
      CraftingBookCategory var2 = CraftingBookCategory.e.a(ChatDeserializer.a(var1, "category", null), CraftingBookCategory.d);
      return this.y.create(var0, var2);
   }

   public T a(MinecraftKey var0, PacketDataSerializer var1) {
      CraftingBookCategory var2 = var1.b(CraftingBookCategory.class);
      return this.y.create(var0, var2);
   }

   public void a(PacketDataSerializer var0, T var1) {
      var0.a(var1.d());
   }

   @FunctionalInterface
   public interface a<T extends RecipeCrafting> {
      T create(MinecraftKey var1, CraftingBookCategory var2);
   }
}
