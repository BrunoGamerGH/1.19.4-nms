package net.minecraft.world.item.crafting;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftShapedRecipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class ShapedRecipes implements RecipeCrafting {
   final int a;
   final int b;
   final NonNullList<RecipeItemStack> c;
   final ItemStack d;
   private final MinecraftKey e;
   final String f;
   final CraftingBookCategory g;
   final boolean h;

   public ShapedRecipes(
      MinecraftKey minecraftkey,
      String s,
      CraftingBookCategory craftingbookcategory,
      int i,
      int j,
      NonNullList<RecipeItemStack> nonnulllist,
      ItemStack itemstack,
      boolean flag
   ) {
      this.e = minecraftkey;
      this.f = s;
      this.g = craftingbookcategory;
      this.a = i;
      this.b = j;
      this.c = nonnulllist;
      this.d = itemstack;
      this.h = flag;
   }

   public ShapedRecipes(
      MinecraftKey minecraftkey,
      String s,
      CraftingBookCategory craftingbookcategory,
      int i,
      int j,
      NonNullList<RecipeItemStack> nonnulllist,
      ItemStack itemstack
   ) {
      this(minecraftkey, s, craftingbookcategory, i, j, nonnulllist, itemstack, true);
   }

   public ShapedRecipe toBukkitRecipe() {
      CraftShapedRecipe recipe;
      CraftItemStack result = CraftItemStack.asCraftMirror(this.d);
      recipe = new CraftShapedRecipe(result, this);
      recipe.setGroup(this.f);
      recipe.setCategory(CraftRecipe.getCategory(this.d()));
      label37:
      switch(this.b) {
         case 1:
            switch(this.a) {
               case 1:
                  recipe.shape(new String[]{"a"});
                  break label37;
               case 2:
                  recipe.shape(new String[]{"ab"});
                  break label37;
               case 3:
                  recipe.shape(new String[]{"abc"});
               default:
                  break label37;
            }
         case 2:
            switch(this.a) {
               case 1:
                  recipe.shape(new String[]{"a", "b"});
                  break label37;
               case 2:
                  recipe.shape(new String[]{"ab", "cd"});
                  break label37;
               case 3:
                  recipe.shape(new String[]{"abc", "def"});
               default:
                  break label37;
            }
         case 3:
            switch(this.a) {
               case 1:
                  recipe.shape(new String[]{"a", "b", "c"});
                  break;
               case 2:
                  recipe.shape(new String[]{"ab", "cd", "ef"});
                  break;
               case 3:
                  recipe.shape(new String[]{"abc", "def", "ghi"});
            }
      }

      char c = 'a';

      for(RecipeItemStack list : this.c) {
         RecipeChoice choice = CraftRecipe.toBukkit(list);
         if (choice != null) {
            recipe.setIngredient(c, choice);
         }

         ++c;
      }

      return recipe;
   }

   @Override
   public MinecraftKey e() {
      return this.e;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.a;
   }

   @Override
   public String c() {
      return this.f;
   }

   @Override
   public CraftingBookCategory d() {
      return this.g;
   }

   @Override
   public ItemStack a(IRegistryCustom iregistrycustom) {
      return this.d;
   }

   @Override
   public NonNullList<RecipeItemStack> a() {
      return this.c;
   }

   @Override
   public boolean i() {
      return this.h;
   }

   @Override
   public boolean a(int i, int j) {
      return i >= this.a && j >= this.b;
   }

   public boolean a(InventoryCrafting inventorycrafting, World world) {
      for(int i = 0; i <= inventorycrafting.g() - this.a; ++i) {
         for(int j = 0; j <= inventorycrafting.f() - this.b; ++j) {
            if (this.a(inventorycrafting, i, j, true)) {
               return true;
            }

            if (this.a(inventorycrafting, i, j, false)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean a(InventoryCrafting inventorycrafting, int i, int j, boolean flag) {
      for(int k = 0; k < inventorycrafting.g(); ++k) {
         for(int l = 0; l < inventorycrafting.f(); ++l) {
            int i1 = k - i;
            int j1 = l - j;
            RecipeItemStack recipeitemstack = RecipeItemStack.a;
            if (i1 >= 0 && j1 >= 0 && i1 < this.a && j1 < this.b) {
               if (flag) {
                  recipeitemstack = this.c.get(this.a - i1 - 1 + j1 * this.a);
               } else {
                  recipeitemstack = this.c.get(i1 + j1 * this.a);
               }
            }

            if (!recipeitemstack.a(inventorycrafting.a(k + l * inventorycrafting.g()))) {
               return false;
            }
         }
      }

      return true;
   }

   public ItemStack a(InventoryCrafting inventorycrafting, IRegistryCustom iregistrycustom) {
      return this.a(iregistrycustom).o();
   }

   public int j() {
      return this.a;
   }

   public int k() {
      return this.b;
   }

   static NonNullList<RecipeItemStack> a(String[] astring, Map<String, RecipeItemStack> map, int i, int j) {
      NonNullList<RecipeItemStack> nonnulllist = NonNullList.a(i * j, RecipeItemStack.a);
      Set<String> set = Sets.newHashSet(map.keySet());
      set.remove(" ");

      for(int k = 0; k < astring.length; ++k) {
         for(int l = 0; l < astring[k].length(); ++l) {
            String s = astring[k].substring(l, l + 1);
            RecipeItemStack recipeitemstack = map.get(s);
            if (recipeitemstack == null) {
               throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
            }

            set.remove(s);
            nonnulllist.set(l + i * k, recipeitemstack);
         }
      }

      if (!set.isEmpty()) {
         throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
      } else {
         return nonnulllist;
      }
   }

   @VisibleForTesting
   static String[] a(String... astring) {
      int i = Integer.MAX_VALUE;
      int j = 0;
      int k = 0;
      int l = 0;

      for(int i1 = 0; i1 < astring.length; ++i1) {
         String s = astring[i1];
         i = Math.min(i, a(s));
         int j1 = b(s);
         j = Math.max(j, j1);
         if (j1 < 0) {
            if (k == i1) {
               ++k;
            }

            ++l;
         } else {
            l = 0;
         }
      }

      if (astring.length == l) {
         return new String[0];
      } else {
         String[] astring1 = new String[astring.length - l - k];

         for(int k1 = 0; k1 < astring1.length; ++k1) {
            astring1[k1] = astring[k1 + k].substring(i, j + 1);
         }

         return astring1;
      }
   }

   @Override
   public boolean aj_() {
      NonNullList<RecipeItemStack> nonnulllist = this.a();
      return nonnulllist.isEmpty()
         || nonnulllist.stream().filter(recipeitemstack -> !recipeitemstack.d()).anyMatch(recipeitemstack -> recipeitemstack.a().length == 0);
   }

   private static int a(String s) {
      int i = 0;

      while(i < s.length() && s.charAt(i) == ' ') {
         ++i;
      }

      return i;
   }

   private static int b(String s) {
      int i = s.length() - 1;

      while(i >= 0 && s.charAt(i) == ' ') {
         --i;
      }

      return i;
   }

   static String[] a(JsonArray jsonarray) {
      String[] astring = new String[jsonarray.size()];
      if (astring.length > 3) {
         throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
      } else if (astring.length == 0) {
         throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
      } else {
         for(int i = 0; i < astring.length; ++i) {
            String s = ChatDeserializer.a(jsonarray.get(i), "pattern[" + i + "]");
            if (s.length() > 3) {
               throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
            }

            if (i > 0 && astring[0].length() != s.length()) {
               throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
            }

            astring[i] = s;
         }

         return astring;
      }
   }

   static Map<String, RecipeItemStack> c(JsonObject jsonobject) {
      Map<String, RecipeItemStack> map = Maps.newHashMap();

      for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
         if (entry.getKey().length() != 1) {
            throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
         }

         if (" ".equals(entry.getKey())) {
            throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
         }

         map.put(entry.getKey(), RecipeItemStack.a((JsonElement)entry.getValue()));
      }

      map.put(" ", RecipeItemStack.a);
      return map;
   }

   public static ItemStack a(JsonObject jsonobject) {
      Item item = b(jsonobject);
      if (jsonobject.has("data")) {
         throw new JsonParseException("Disallowed data tag found");
      } else {
         int i = ChatDeserializer.a(jsonobject, "count", 1);
         if (i < 1) {
            throw new JsonSyntaxException("Invalid output count: " + i);
         } else {
            return new ItemStack(item, i);
         }
      }
   }

   public static Item b(JsonObject jsonobject) {
      String s = ChatDeserializer.h(jsonobject, "item");
      Item item = BuiltInRegistries.i.b(new MinecraftKey(s)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + s + "'"));
      if (item == Items.a) {
         throw new JsonSyntaxException("Invalid item: " + s);
      } else {
         return item;
      }
   }

   public static class a implements RecipeSerializer<ShapedRecipes> {
      public ShapedRecipes a(MinecraftKey minecraftkey, JsonObject jsonobject) {
         String s = ChatDeserializer.a(jsonobject, "group", "");
         CraftingBookCategory craftingbookcategory = CraftingBookCategory.e.a(ChatDeserializer.a(jsonobject, "category", null), CraftingBookCategory.d);
         Map<String, RecipeItemStack> map = ShapedRecipes.c(ChatDeserializer.t(jsonobject, "key"));
         String[] astring = ShapedRecipes.a(ShapedRecipes.a(ChatDeserializer.u(jsonobject, "pattern")));
         int i = astring[0].length();
         int j = astring.length;
         NonNullList<RecipeItemStack> nonnulllist = ShapedRecipes.a(astring, map, i, j);
         ItemStack itemstack = ShapedRecipes.a(ChatDeserializer.t(jsonobject, "result"));
         boolean flag = ChatDeserializer.a(jsonobject, "show_notification", true);
         return new ShapedRecipes(minecraftkey, s, craftingbookcategory, i, j, nonnulllist, itemstack, flag);
      }

      public ShapedRecipes a(MinecraftKey minecraftkey, PacketDataSerializer packetdataserializer) {
         int i = packetdataserializer.m();
         int j = packetdataserializer.m();
         String s = packetdataserializer.s();
         CraftingBookCategory craftingbookcategory = packetdataserializer.b(CraftingBookCategory.class);
         NonNullList<RecipeItemStack> nonnulllist = NonNullList.a(i * j, RecipeItemStack.a);

         for(int k = 0; k < nonnulllist.size(); ++k) {
            nonnulllist.set(k, RecipeItemStack.b(packetdataserializer));
         }

         ItemStack itemstack = packetdataserializer.r();
         boolean flag = packetdataserializer.readBoolean();
         return new ShapedRecipes(minecraftkey, s, craftingbookcategory, i, j, nonnulllist, itemstack, flag);
      }

      public void a(PacketDataSerializer packetdataserializer, ShapedRecipes shapedrecipes) {
         packetdataserializer.d(shapedrecipes.a);
         packetdataserializer.d(shapedrecipes.b);
         packetdataserializer.a(shapedrecipes.f);
         packetdataserializer.a(shapedrecipes.g);

         for(RecipeItemStack recipeitemstack : shapedrecipes.c) {
            recipeitemstack.a(packetdataserializer);
         }

         packetdataserializer.a(shapedrecipes.d);
         packetdataserializer.writeBoolean(shapedrecipes.h);
      }
   }
}
