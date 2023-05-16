package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.player.AutoRecipeStackManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IMaterial;

public final class RecipeItemStack implements Predicate<ItemStack> {
   public static final RecipeItemStack a = new RecipeItemStack(Stream.empty());
   private final RecipeItemStack.Provider[] b;
   @Nullable
   public ItemStack[] c;
   @Nullable
   private IntList d;
   public boolean exact;

   public RecipeItemStack(Stream<? extends RecipeItemStack.Provider> stream) {
      this.b = stream.toArray(i -> new RecipeItemStack.Provider[i]);
   }

   public ItemStack[] a() {
      if (this.c == null) {
         this.c = Arrays.stream(this.b).flatMap(recipeitemstack_provider -> recipeitemstack_provider.a().stream()).distinct().toArray(i -> new ItemStack[i]);
      }

      return this.c;
   }

   public boolean a(@Nullable ItemStack itemstack) {
      if (itemstack == null) {
         return false;
      } else if (this.d()) {
         return itemstack.b();
      } else {
         for(ItemStack itemstack1 : this.a()) {
            if (this.exact) {
               if (itemstack1.c() == itemstack.c() && ItemStack.a(itemstack, itemstack1)) {
                  return true;
               }
            } else if (itemstack1.a(itemstack.c())) {
               return true;
            }
         }

         return false;
      }
   }

   public IntList b() {
      if (this.d == null) {
         ItemStack[] aitemstack = this.a();
         this.d = new IntArrayList(aitemstack.length);

         for(ItemStack itemstack : aitemstack) {
            this.d.add(AutoRecipeStackManager.c(itemstack));
         }

         this.d.sort(IntComparators.NATURAL_COMPARATOR);
      }

      return this.d;
   }

   public void a(PacketDataSerializer packetdataserializer) {
      packetdataserializer.a(Arrays.asList(this.a()), PacketDataSerializer::a);
   }

   public JsonElement c() {
      if (this.b.length == 1) {
         return this.b[0].b();
      } else {
         JsonArray jsonarray = new JsonArray();

         for(RecipeItemStack.Provider recipeitemstack_provider : this.b) {
            jsonarray.add(recipeitemstack_provider.b());
         }

         return jsonarray;
      }
   }

   public boolean d() {
      return this.b.length == 0;
   }

   private static RecipeItemStack b(Stream<? extends RecipeItemStack.Provider> stream) {
      RecipeItemStack recipeitemstack = new RecipeItemStack(stream);
      return recipeitemstack.d() ? a : recipeitemstack;
   }

   public static RecipeItemStack e() {
      return a;
   }

   public static RecipeItemStack a(IMaterial... aimaterial) {
      return a(Arrays.stream(aimaterial).map(ItemStack::new));
   }

   public static RecipeItemStack a(ItemStack... aitemstack) {
      return a(Arrays.stream(aitemstack));
   }

   public static RecipeItemStack a(Stream<ItemStack> stream) {
      return b(stream.filter(itemstack -> !itemstack.b()).map(RecipeItemStack.StackProvider::new));
   }

   public static RecipeItemStack a(TagKey<Item> tagkey) {
      return b(Stream.of(new RecipeItemStack.b(tagkey)));
   }

   public static RecipeItemStack b(PacketDataSerializer packetdataserializer) {
      return b(packetdataserializer.<ItemStack>a(PacketDataSerializer::r).stream().map(RecipeItemStack.StackProvider::new));
   }

   public static RecipeItemStack a(@Nullable JsonElement jsonelement) {
      if (jsonelement == null || jsonelement.isJsonNull()) {
         throw new JsonSyntaxException("Item cannot be null");
      } else if (jsonelement.isJsonObject()) {
         return b(Stream.of(a(jsonelement.getAsJsonObject())));
      } else if (jsonelement.isJsonArray()) {
         JsonArray jsonarray = jsonelement.getAsJsonArray();
         if (jsonarray.size() == 0) {
            throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
         } else {
            return b(StreamSupport.stream(jsonarray.spliterator(), false).map(jsonelement1 -> a(ChatDeserializer.m(jsonelement1, "item"))));
         }
      } else {
         throw new JsonSyntaxException("Expected item to be object or array of objects");
      }
   }

   private static RecipeItemStack.Provider a(JsonObject jsonobject) {
      if (jsonobject.has("item") && jsonobject.has("tag")) {
         throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
      } else if (jsonobject.has("item")) {
         Item item = ShapedRecipes.b(jsonobject);
         return new RecipeItemStack.StackProvider(new ItemStack(item));
      } else if (jsonobject.has("tag")) {
         MinecraftKey minecraftkey = new MinecraftKey(ChatDeserializer.h(jsonobject, "tag"));
         TagKey<Item> tagkey = TagKey.a(Registries.C, minecraftkey);
         return new RecipeItemStack.b(tagkey);
      } else {
         throw new JsonParseException("An ingredient entry needs either a tag or an item");
      }
   }

   public interface Provider {
      Collection<ItemStack> a();

      JsonObject b();
   }

   public static class StackProvider implements RecipeItemStack.Provider {
      private final ItemStack a;

      public StackProvider(ItemStack itemstack) {
         this.a = itemstack;
      }

      @Override
      public Collection<ItemStack> a() {
         return Collections.singleton(this.a);
      }

      @Override
      public JsonObject b() {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("item", BuiltInRegistries.i.b(this.a.c()).toString());
         return jsonobject;
      }
   }

   private static class b implements RecipeItemStack.Provider {
      private final TagKey<Item> a;

      b(TagKey<Item> tagkey) {
         this.a = tagkey;
      }

      @Override
      public Collection<ItemStack> a() {
         List<ItemStack> list = Lists.newArrayList();

         for(Holder<Item> holder : BuiltInRegistries.i.c(this.a)) {
            list.add(new ItemStack(holder));
         }

         return list;
      }

      @Override
      public JsonObject b() {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("tag", this.a.b().toString());
         return jsonobject;
      }
   }
}
