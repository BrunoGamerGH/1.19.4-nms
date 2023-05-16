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
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftSmithingRecipe;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.Recipe;

@Deprecated(
   forRemoval = true
)
public class LegacyUpgradeRecipe implements SmithingRecipe {
   final RecipeItemStack a;
   final RecipeItemStack b;
   final ItemStack c;
   private final MinecraftKey d;

   public LegacyUpgradeRecipe(MinecraftKey minecraftkey, RecipeItemStack recipeitemstack, RecipeItemStack recipeitemstack1, ItemStack itemstack) {
      this.d = minecraftkey;
      this.a = recipeitemstack;
      this.b = recipeitemstack1;
      this.c = itemstack;
   }

   @Override
   public boolean a(IInventory iinventory, World world) {
      return this.a.a(iinventory.a(0)) && this.b.a(iinventory.a(1));
   }

   @Override
   public ItemStack a(IInventory iinventory, IRegistryCustom iregistrycustom) {
      ItemStack itemstack = this.c.o();
      NBTTagCompound nbttagcompound = iinventory.a(0).u();
      if (nbttagcompound != null) {
         itemstack.c(nbttagcompound.h());
      }

      return itemstack;
   }

   @Override
   public boolean a(int i, int j) {
      return i * j >= 2;
   }

   @Override
   public ItemStack a(IRegistryCustom iregistrycustom) {
      return this.c;
   }

   @Override
   public boolean a(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean b(ItemStack itemstack) {
      return this.a.a(itemstack);
   }

   @Override
   public boolean c(ItemStack itemstack) {
      return this.b.a(itemstack);
   }

   @Override
   public MinecraftKey e() {
      return this.d;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.u;
   }

   @Override
   public boolean aj_() {
      return Stream.of(this.a, this.b).anyMatch(recipeitemstack -> recipeitemstack.a().length == 0);
   }

   @Override
   public Recipe toBukkitRecipe() {
      CraftItemStack result = CraftItemStack.asCraftMirror(this.c);
      return new CraftSmithingRecipe(CraftNamespacedKey.fromMinecraft(this.d), result, CraftRecipe.toBukkit(this.a), CraftRecipe.toBukkit(this.b));
   }

   public static class a implements RecipeSerializer<LegacyUpgradeRecipe> {
      public LegacyUpgradeRecipe a(MinecraftKey minecraftkey, JsonObject jsonobject) {
         RecipeItemStack recipeitemstack = RecipeItemStack.a(ChatDeserializer.t(jsonobject, "base"));
         RecipeItemStack recipeitemstack1 = RecipeItemStack.a(ChatDeserializer.t(jsonobject, "addition"));
         ItemStack itemstack = ShapedRecipes.a(ChatDeserializer.t(jsonobject, "result"));
         return new LegacyUpgradeRecipe(minecraftkey, recipeitemstack, recipeitemstack1, itemstack);
      }

      public LegacyUpgradeRecipe a(MinecraftKey minecraftkey, PacketDataSerializer packetdataserializer) {
         RecipeItemStack recipeitemstack = RecipeItemStack.b(packetdataserializer);
         RecipeItemStack recipeitemstack1 = RecipeItemStack.b(packetdataserializer);
         ItemStack itemstack = packetdataserializer.r();
         return new LegacyUpgradeRecipe(minecraftkey, recipeitemstack, recipeitemstack1, itemstack);
      }

      public void a(PacketDataSerializer packetdataserializer, LegacyUpgradeRecipe legacyupgraderecipe) {
         legacyupgraderecipe.a.a(packetdataserializer);
         legacyupgraderecipe.b.a(packetdataserializer);
         packetdataserializer.a(legacyupgraderecipe.c);
      }
   }
}
