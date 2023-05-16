package net.minecraft.world.item.crafting;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.resources.IResourceManager;
import net.minecraft.server.packs.resources.ResourceDataJson;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.world.IInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import org.slf4j.Logger;
import org.spigotmc.AsyncCatcher;

public class CraftingManager extends ResourceDataJson {
   private static final Gson a = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private static final Logger b = LogUtils.getLogger();
   public Map<Recipes<?>, Object2ObjectLinkedOpenHashMap<MinecraftKey, IRecipe<?>>> c = ImmutableMap.of();
   private Map<MinecraftKey, IRecipe<?>> d = ImmutableMap.of();
   private boolean e;

   public CraftingManager() {
      super(a, "recipes");
   }

   protected void a(Map<MinecraftKey, JsonElement> map, IResourceManager iresourcemanager, GameProfilerFiller gameprofilerfiller) {
      this.e = false;
      Map<Recipes<?>, Object2ObjectLinkedOpenHashMap<MinecraftKey, IRecipe<?>>> map1 = Maps.newHashMap();

      for(Recipes<?> recipeType : BuiltInRegistries.s) {
         map1.put(recipeType, new Object2ObjectLinkedOpenHashMap());
      }

      Builder<MinecraftKey, IRecipe<?>> builder = ImmutableMap.builder();

      for(Entry<MinecraftKey, JsonElement> entry : map.entrySet()) {
         MinecraftKey minecraftkey = entry.getKey();

         try {
            IRecipe<?> irecipe = a(minecraftkey, ChatDeserializer.m((JsonElement)entry.getValue(), "top element"));
            ((Object2ObjectLinkedOpenHashMap)map1.computeIfAbsent(irecipe.f(), recipes -> new Object2ObjectLinkedOpenHashMap())).put(minecraftkey, irecipe);
            builder.put(minecraftkey, irecipe);
         } catch (JsonParseException | IllegalArgumentException var10) {
            b.error("Parsing error loading recipe {}", minecraftkey, var10);
         }
      }

      this.c = map1.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, entry1 -> (Object2ObjectLinkedOpenHashMap)entry1.getValue()));
      this.d = Maps.newHashMap(builder.build());
      b.info("Loaded {} recipes", map1.size());
   }

   public void addRecipe(IRecipe<?> irecipe) {
      AsyncCatcher.catchOp("Recipe Add");
      Object2ObjectLinkedOpenHashMap<MinecraftKey, IRecipe<?>> map = (Object2ObjectLinkedOpenHashMap)this.c.get(irecipe.f());
      if (!this.d.containsKey(irecipe.e()) && !map.containsKey(irecipe.e())) {
         map.putAndMoveToFirst(irecipe.e(), irecipe);
         this.d.put(irecipe.e(), irecipe);
      } else {
         throw new IllegalStateException("Duplicate recipe ignored with ID " + irecipe.e());
      }
   }

   public boolean a() {
      return this.e;
   }

   public <C extends IInventory, T extends IRecipe<C>> Optional<T> a(Recipes<T> recipes, C c0, World world) {
      Optional<T> recipe = this.c(recipes).values().stream().filter(irecipe -> irecipe.a(c0, world)).findFirst();
      c0.setCurrentRecipe(recipe.orElse((T)null));
      return recipe;
   }

   public <C extends IInventory, T extends IRecipe<C>> Optional<Pair<MinecraftKey, T>> a(
      Recipes<T> recipes, C c0, World world, @Nullable MinecraftKey minecraftkey
   ) {
      Map<MinecraftKey, T> map = this.c(recipes);
      if (minecraftkey != null) {
         T t0 = map.get(minecraftkey);
         if (t0 != null && t0.a(c0, world)) {
            return Optional.of((T)Pair.of(minecraftkey, t0));
         }
      }

      return map.entrySet()
         .stream()
         .filter(entry -> entry.getValue().a(c0, world))
         .findFirst()
         .map(entry -> Pair.of((MinecraftKey)entry.getKey(), (IRecipe)entry.getValue()));
   }

   public <C extends IInventory, T extends IRecipe<C>> List<T> a(Recipes<T> recipes) {
      return List.copyOf(this.c(recipes).values());
   }

   public <C extends IInventory, T extends IRecipe<C>> List<T> b(Recipes<T> recipes, C c0, World world) {
      return this.c(recipes)
         .values()
         .stream()
         .filter(irecipe -> irecipe.a(c0, world))
         .sorted(Comparator.comparing(irecipe -> irecipe.a(world.u_()).p()))
         .collect(Collectors.toList());
   }

   private <C extends IInventory, T extends IRecipe<C>> Map<MinecraftKey, T> c(Recipes<T> recipes) {
      return (Map<MinecraftKey, T>)this.c.getOrDefault(recipes, new Object2ObjectLinkedOpenHashMap());
   }

   public <C extends IInventory, T extends IRecipe<C>> NonNullList<ItemStack> c(Recipes<T> recipes, C c0, World world) {
      Optional<T> optional = this.a(recipes, c0, world);
      if (optional.isPresent()) {
         return optional.get().a(c0);
      } else {
         NonNullList<ItemStack> nonnulllist = NonNullList.a(c0.b(), ItemStack.b);

         for(int i = 0; i < nonnulllist.size(); ++i) {
            nonnulllist.set(i, c0.a(i));
         }

         return nonnulllist;
      }
   }

   public Optional<? extends IRecipe<?>> a(MinecraftKey minecraftkey) {
      return Optional.ofNullable(this.d.get(minecraftkey));
   }

   public Collection<IRecipe<?>> b() {
      return this.c.values().stream().flatMap(map -> map.values().stream()).collect(Collectors.toSet());
   }

   public Stream<MinecraftKey> d() {
      return this.c.values().stream().flatMap(map -> map.keySet().stream());
   }

   public static IRecipe<?> a(MinecraftKey minecraftkey, JsonObject jsonobject) {
      String s = ChatDeserializer.h(jsonobject, "type");
      return BuiltInRegistries.t
         .b(new MinecraftKey(s))
         .orElseThrow(() -> new JsonSyntaxException("Invalid or unsupported recipe type '" + s + "'"))
         .b(minecraftkey, jsonobject);
   }

   public void a(Iterable<IRecipe<?>> iterable) {
      this.e = false;
      Map<Recipes<?>, Object2ObjectLinkedOpenHashMap<MinecraftKey, IRecipe<?>>> map = Maps.newHashMap();
      Builder<MinecraftKey, IRecipe<?>> builder = ImmutableMap.builder();
      iterable.forEach(irecipe -> {
         Map<MinecraftKey, IRecipe<?>> map1 = (Map)map.computeIfAbsent(irecipe.f(), recipes -> new Object2ObjectLinkedOpenHashMap());
         MinecraftKey minecraftkey = irecipe.e();
         IRecipe<?> irecipe1 = map1.put(minecraftkey, irecipe);
         builder.put(minecraftkey, irecipe);
         if (irecipe1 != null) {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + minecraftkey);
         }
      });
      this.c = ImmutableMap.copyOf(map);
      this.d = Maps.newHashMap(builder.build());
   }

   public boolean removeRecipe(MinecraftKey mcKey) {
      for(Object2ObjectLinkedOpenHashMap<MinecraftKey, IRecipe<?>> recipes : this.c.values()) {
         recipes.remove(mcKey);
      }

      return this.d.remove(mcKey) != null;
   }

   public void clearRecipes() {
      this.c = Maps.newHashMap();

      for(Recipes<?> recipeType : BuiltInRegistries.s) {
         this.c.put(recipeType, new Object2ObjectLinkedOpenHashMap());
      }

      this.d = Maps.newHashMap();
   }

   public static <C extends IInventory, T extends IRecipe<C>> CraftingManager.a<C, T> b(final Recipes<T> recipes) {
      return new CraftingManager.a<C, T>() {
         @Nullable
         private MinecraftKey b;

         @Override
         public Optional<T> a(C c0, World world) {
            CraftingManager craftingmanager = world.q();
            Optional<Pair<MinecraftKey, T>> optional = craftingmanager.a(recipes, c0, world, this.b);
            if (optional.isPresent()) {
               Pair<MinecraftKey, T> pair = (Pair)optional.get();
               this.b = (MinecraftKey)pair.getFirst();
               return Optional.of((T)pair.getSecond());
            } else {
               return Optional.empty();
            }
         }
      };
   }

   public interface a<C extends IInventory, T extends IRecipe<C>> {
      Optional<T> a(C var1, World var2);
   }
}
