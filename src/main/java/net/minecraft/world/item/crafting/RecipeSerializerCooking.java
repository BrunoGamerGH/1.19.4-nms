package net.minecraft.world.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;

public class RecipeSerializerCooking<T extends RecipeCooking> implements RecipeSerializer<T> {
   private final int y;
   private final RecipeSerializerCooking.a<T> z;

   public RecipeSerializerCooking(RecipeSerializerCooking.a<T> var0, int var1) {
      this.y = var1;
      this.z = var0;
   }

   public T a(MinecraftKey var0, JsonObject var1) {
      String var2 = ChatDeserializer.a(var1, "group", "");
      CookingBookCategory var3 = CookingBookCategory.d.a(ChatDeserializer.a(var1, "category", null), CookingBookCategory.c);
      JsonElement var4 = (JsonElement)(ChatDeserializer.d(var1, "ingredient")
         ? ChatDeserializer.u(var1, "ingredient")
         : ChatDeserializer.t(var1, "ingredient"));
      RecipeItemStack var5 = RecipeItemStack.a(var4);
      String var6 = ChatDeserializer.h(var1, "result");
      MinecraftKey var7 = new MinecraftKey(var6);
      ItemStack var8 = new ItemStack(BuiltInRegistries.i.b(var7).orElseThrow(() -> new IllegalStateException("Item: " + var6 + " does not exist")));
      float var9 = ChatDeserializer.a(var1, "experience", 0.0F);
      int var10 = ChatDeserializer.a(var1, "cookingtime", this.y);
      return this.z.create(var0, var2, var3, var5, var8, var9, var10);
   }

   public T a(MinecraftKey var0, PacketDataSerializer var1) {
      String var2 = var1.s();
      CookingBookCategory var3 = var1.b(CookingBookCategory.class);
      RecipeItemStack var4 = RecipeItemStack.b(var1);
      ItemStack var5 = var1.r();
      float var6 = var1.readFloat();
      int var7 = var1.m();
      return this.z.create(var0, var2, var3, var4, var5, var6, var7);
   }

   public void a(PacketDataSerializer var0, T var1) {
      var0.a(var1.c);
      var0.a(var1.g());
      var1.d.a(var0);
      var0.a(var1.e);
      var0.writeFloat(var1.f);
      var0.d(var1.g);
   }

   interface a<T extends RecipeCooking> {
      T create(MinecraftKey var1, String var2, CookingBookCategory var3, RecipeItemStack var4, ItemStack var5, float var6, int var7);
   }
}
