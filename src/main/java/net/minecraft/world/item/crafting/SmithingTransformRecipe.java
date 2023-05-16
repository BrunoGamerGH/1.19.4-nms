package net.minecraft.world.item.crafting;

import com.google.gson.JsonObject;
import java.util.stream.Stream;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.IInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftSmithingTransformRecipe;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.Recipe;

public class SmithingTransformRecipe implements SmithingRecipe {
   private final MinecraftKey a;
   final RecipeItemStack b;
   final RecipeItemStack c;
   final RecipeItemStack d;
   final ItemStack e;

   public SmithingTransformRecipe(
      MinecraftKey minecraftkey, RecipeItemStack recipeitemstack, RecipeItemStack recipeitemstack1, RecipeItemStack recipeitemstack2, ItemStack itemstack
   ) {
      this.a = minecraftkey;
      this.b = recipeitemstack;
      this.c = recipeitemstack1;
      this.d = recipeitemstack2;
      this.e = itemstack;
   }

   @Override
   public boolean a(IInventory iinventory, World world) {
      return this.b.a(iinventory.a(0)) && this.c.a(iinventory.a(1)) && this.d.a(iinventory.a(2));
   }

   @Override
   public ItemStack a(IInventory iinventory, IRegistryCustom iregistrycustom) {
      ItemStack itemstack = this.e.o();
      NBTTagCompound nbttagcompound = iinventory.a(1).u();
      if (nbttagcompound != null) {
         itemstack.c(nbttagcompound.h());
      }

      return itemstack;
   }

   @Override
   public ItemStack a(IRegistryCustom iregistrycustom) {
      return this.e;
   }

   @Override
   public boolean a(ItemStack itemstack) {
      return this.b.a(itemstack);
   }

   @Override
   public boolean b(ItemStack itemstack) {
      return this.c.a(itemstack);
   }

   @Override
   public boolean c(ItemStack itemstack) {
      return this.d.a(itemstack);
   }

   @Override
   public MinecraftKey e() {
      return this.a;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.v;
   }

   @Override
   public boolean aj_() {
      return Stream.of(this.b, this.c, this.d).anyMatch(RecipeItemStack::d);
   }

   @Override
   public Recipe toBukkitRecipe() {
      CraftItemStack result = CraftItemStack.asCraftMirror(this.e);
      return new CraftSmithingTransformRecipe(
         CraftNamespacedKey.fromMinecraft(this.a), result, CraftRecipe.toBukkit(this.b), CraftRecipe.toBukkit(this.c), CraftRecipe.toBukkit(this.d)
      );
   }

   public static class a implements RecipeSerializer<SmithingTransformRecipe> {
      public SmithingTransformRecipe a(MinecraftKey minecraftkey, JsonObject jsonobject) {
         RecipeItemStack recipeitemstack = RecipeItemStack.a(ChatDeserializer.t(jsonobject, "template"));
         RecipeItemStack recipeitemstack1 = RecipeItemStack.a(ChatDeserializer.t(jsonobject, "base"));
         RecipeItemStack recipeitemstack2 = RecipeItemStack.a(ChatDeserializer.t(jsonobject, "addition"));
         ItemStack itemstack = ShapedRecipes.a(ChatDeserializer.t(jsonobject, "result"));
         return new SmithingTransformRecipe(minecraftkey, recipeitemstack, recipeitemstack1, recipeitemstack2, itemstack);
      }

      public SmithingTransformRecipe a(MinecraftKey minecraftkey, PacketDataSerializer packetdataserializer) {
         RecipeItemStack recipeitemstack = RecipeItemStack.b(packetdataserializer);
         RecipeItemStack recipeitemstack1 = RecipeItemStack.b(packetdataserializer);
         RecipeItemStack recipeitemstack2 = RecipeItemStack.b(packetdataserializer);
         ItemStack itemstack = packetdataserializer.r();
         return new SmithingTransformRecipe(minecraftkey, recipeitemstack, recipeitemstack1, recipeitemstack2, itemstack);
      }

      public void a(PacketDataSerializer packetdataserializer, SmithingTransformRecipe smithingtransformrecipe) {
         smithingtransformrecipe.b.a(packetdataserializer);
         smithingtransformrecipe.c.a(packetdataserializer);
         smithingtransformrecipe.d.a(packetdataserializer);
         packetdataserializer.a(smithingtransformrecipe.e);
      }
   }
}
