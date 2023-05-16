package net.minecraft.world.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.IInventory;
import net.minecraft.world.item.ItemStack;

public abstract class RecipeSingleItem implements IRecipe<IInventory> {
   protected final RecipeItemStack a;
   protected final ItemStack b;
   private final Recipes<?> e;
   private final RecipeSerializer<?> f;
   protected final MinecraftKey c;
   protected final String d;

   public RecipeSingleItem(Recipes<?> var0, RecipeSerializer<?> var1, MinecraftKey var2, String var3, RecipeItemStack var4, ItemStack var5) {
      this.e = var0;
      this.f = var1;
      this.c = var2;
      this.d = var3;
      this.a = var4;
      this.b = var5;
   }

   @Override
   public Recipes<?> f() {
      return this.e;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return this.f;
   }

   @Override
   public MinecraftKey e() {
      return this.c;
   }

   @Override
   public String c() {
      return this.d;
   }

   @Override
   public ItemStack a(IRegistryCustom var0) {
      return this.b;
   }

   @Override
   public NonNullList<RecipeItemStack> a() {
      NonNullList<RecipeItemStack> var0 = NonNullList.a();
      var0.add(this.a);
      return var0;
   }

   @Override
   public boolean a(int var0, int var1) {
      return true;
   }

   @Override
   public ItemStack a(IInventory var0, IRegistryCustom var1) {
      return this.b.o();
   }

   public static class a<T extends RecipeSingleItem> implements RecipeSerializer<T> {
      final RecipeSingleItem.a.a<T> y;

      protected a(RecipeSingleItem.a.a<T> var0) {
         this.y = var0;
      }

      public T a(MinecraftKey var0, JsonObject var1) {
         String var2 = ChatDeserializer.a(var1, "group", "");
         RecipeItemStack var3;
         if (ChatDeserializer.d(var1, "ingredient")) {
            var3 = RecipeItemStack.a(ChatDeserializer.u(var1, "ingredient"));
         } else {
            var3 = RecipeItemStack.a(ChatDeserializer.t(var1, "ingredient"));
         }

         String var4 = ChatDeserializer.h(var1, "result");
         int var5 = ChatDeserializer.n(var1, "count");
         ItemStack var6 = new ItemStack(BuiltInRegistries.i.a(new MinecraftKey(var4)), var5);
         return this.y.create(var0, var2, var3, var6);
      }

      public T a(MinecraftKey var0, PacketDataSerializer var1) {
         String var2 = var1.s();
         RecipeItemStack var3 = RecipeItemStack.b(var1);
         ItemStack var4 = var1.r();
         return this.y.create(var0, var2, var3, var4);
      }

      public void a(PacketDataSerializer var0, T var1) {
         var0.a(var1.d);
         var1.a.a(var0);
         var0.a(var1.b);
      }

      interface a<T extends RecipeSingleItem> {
         T create(MinecraftKey var1, String var2, RecipeItemStack var3, ItemStack var4);
      }
   }
}
