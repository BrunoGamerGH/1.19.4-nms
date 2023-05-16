package net.minecraft.world.item.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.NonNullList;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.player.AutoRecipeStackManager;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftShapelessRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class ShapelessRecipes implements RecipeCrafting {
   private final MinecraftKey a;
   final String b;
   final CraftingBookCategory c;
   final ItemStack d;
   final NonNullList<RecipeItemStack> e;

   public ShapelessRecipes(
      MinecraftKey minecraftkey, String s, CraftingBookCategory craftingbookcategory, ItemStack itemstack, NonNullList<RecipeItemStack> nonnulllist
   ) {
      this.a = minecraftkey;
      this.b = s;
      this.c = craftingbookcategory;
      this.d = itemstack;
      this.e = nonnulllist;
   }

   public ShapelessRecipe toBukkitRecipe() {
      CraftItemStack result = CraftItemStack.asCraftMirror(this.d);
      CraftShapelessRecipe recipe = new CraftShapelessRecipe(result, this);
      recipe.setGroup(this.b);
      recipe.setCategory(CraftRecipe.getCategory(this.d()));

      for(RecipeItemStack list : this.e) {
         recipe.addIngredient(CraftRecipe.toBukkit(list));
      }

      return recipe;
   }

   @Override
   public MinecraftKey e() {
      return this.a;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.b;
   }

   @Override
   public String c() {
      return this.b;
   }

   @Override
   public CraftingBookCategory d() {
      return this.c;
   }

   @Override
   public ItemStack a(IRegistryCustom iregistrycustom) {
      return this.d;
   }

   @Override
   public NonNullList<RecipeItemStack> a() {
      return this.e;
   }

   public boolean a(InventoryCrafting inventorycrafting, World world) {
      AutoRecipeStackManager autorecipestackmanager = new AutoRecipeStackManager();
      int i = 0;

      for(int j = 0; j < inventorycrafting.b(); ++j) {
         ItemStack itemstack = inventorycrafting.a(j);
         if (!itemstack.b()) {
            ++i;
            autorecipestackmanager.a(itemstack, 1);
         }
      }

      return i == this.e.size() && autorecipestackmanager.a(this, null);
   }

   public ItemStack a(InventoryCrafting inventorycrafting, IRegistryCustom iregistrycustom) {
      return this.d.o();
   }

   @Override
   public boolean a(int i, int j) {
      return i * j >= this.e.size();
   }

   public static class a implements RecipeSerializer<ShapelessRecipes> {
      public ShapelessRecipes a(MinecraftKey minecraftkey, JsonObject jsonobject) {
         String s = ChatDeserializer.a(jsonobject, "group", "");
         CraftingBookCategory craftingbookcategory = CraftingBookCategory.e.a(ChatDeserializer.a(jsonobject, "category", null), CraftingBookCategory.d);
         NonNullList<RecipeItemStack> nonnulllist = a(ChatDeserializer.u(jsonobject, "ingredients"));
         if (nonnulllist.isEmpty()) {
            throw new JsonParseException("No ingredients for shapeless recipe");
         } else if (nonnulllist.size() > 9) {
            throw new JsonParseException("Too many ingredients for shapeless recipe");
         } else {
            ItemStack itemstack = ShapedRecipes.a(ChatDeserializer.t(jsonobject, "result"));
            return new ShapelessRecipes(minecraftkey, s, craftingbookcategory, itemstack, nonnulllist);
         }
      }

      private static NonNullList<RecipeItemStack> a(JsonArray jsonarray) {
         NonNullList<RecipeItemStack> nonnulllist = NonNullList.a();

         for(int i = 0; i < jsonarray.size(); ++i) {
            RecipeItemStack recipeitemstack = RecipeItemStack.a(jsonarray.get(i));
            if (!recipeitemstack.d()) {
               nonnulllist.add(recipeitemstack);
            }
         }

         return nonnulllist;
      }

      public ShapelessRecipes a(MinecraftKey minecraftkey, PacketDataSerializer packetdataserializer) {
         String s = packetdataserializer.s();
         CraftingBookCategory craftingbookcategory = packetdataserializer.b(CraftingBookCategory.class);
         int i = packetdataserializer.m();
         NonNullList<RecipeItemStack> nonnulllist = NonNullList.a(i, RecipeItemStack.a);

         for(int j = 0; j < nonnulllist.size(); ++j) {
            nonnulllist.set(j, RecipeItemStack.b(packetdataserializer));
         }

         ItemStack itemstack = packetdataserializer.r();
         return new ShapelessRecipes(minecraftkey, s, craftingbookcategory, itemstack, nonnulllist);
      }

      public void a(PacketDataSerializer packetdataserializer, ShapelessRecipes shapelessrecipes) {
         packetdataserializer.a(shapelessrecipes.b);
         packetdataserializer.a(shapelessrecipes.c);
         packetdataserializer.d(shapelessrecipes.e.size());

         for(RecipeItemStack recipeitemstack : shapelessrecipes.e) {
            recipeitemstack.a(packetdataserializer);
         }

         packetdataserializer.a(shapelessrecipes.d);
      }
   }
}
