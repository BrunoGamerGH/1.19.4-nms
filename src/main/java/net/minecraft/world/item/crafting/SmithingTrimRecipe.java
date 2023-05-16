package net.minecraft.world.item.crafting;

import com.google.gson.JsonObject;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.IInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.armortrim.TrimPatterns;
import net.minecraft.world.level.World;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftSmithingTrimRecipe;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.Recipe;

public class SmithingTrimRecipe implements SmithingRecipe {
   private final MinecraftKey a;
   final RecipeItemStack b;
   final RecipeItemStack c;
   final RecipeItemStack d;

   public SmithingTrimRecipe(MinecraftKey minecraftkey, RecipeItemStack recipeitemstack, RecipeItemStack recipeitemstack1, RecipeItemStack recipeitemstack2) {
      this.a = minecraftkey;
      this.b = recipeitemstack;
      this.c = recipeitemstack1;
      this.d = recipeitemstack2;
   }

   @Override
   public boolean a(IInventory iinventory, World world) {
      return this.b.a(iinventory.a(0)) && this.c.a(iinventory.a(1)) && this.d.a(iinventory.a(2));
   }

   @Override
   public ItemStack a(IInventory iinventory, IRegistryCustom iregistrycustom) {
      ItemStack itemstack = iinventory.a(1);
      if (this.c.a(itemstack)) {
         Optional<Holder.c<TrimMaterial>> optional = TrimMaterials.a(iregistrycustom, iinventory.a(2));
         Optional<Holder.c<TrimPattern>> optional1 = TrimPatterns.a(iregistrycustom, iinventory.a(0));
         if (optional.isPresent() && optional1.isPresent()) {
            Optional<ArmorTrim> optional2 = ArmorTrim.a(iregistrycustom, itemstack);
            if (optional2.isPresent() && optional2.get().a(optional1.get(), optional.get())) {
               return ItemStack.b;
            }

            ItemStack itemstack1 = itemstack.o();
            itemstack1.f(1);
            ArmorTrim armortrim = new ArmorTrim(optional.get(), optional1.get());
            if (ArmorTrim.a(iregistrycustom, itemstack1, armortrim)) {
               return itemstack1;
            }
         }
      }

      return ItemStack.b;
   }

   @Override
   public ItemStack a(IRegistryCustom iregistrycustom) {
      ItemStack itemstack = new ItemStack(Items.oP);
      Optional<Holder.c<TrimPattern>> optional = iregistrycustom.d(Registries.aC).h().findFirst();
      if (optional.isPresent()) {
         Optional<Holder.c<TrimMaterial>> optional1 = iregistrycustom.d(Registries.aB).b(TrimMaterials.d);
         if (optional1.isPresent()) {
            ArmorTrim armortrim = new ArmorTrim(optional1.get(), optional.get());
            ArmorTrim.a(iregistrycustom, itemstack, armortrim);
         }
      }

      return itemstack;
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
      return RecipeSerializer.w;
   }

   @Override
   public boolean aj_() {
      return Stream.of(this.b, this.c, this.d).anyMatch(RecipeItemStack::d);
   }

   @Override
   public Recipe toBukkitRecipe() {
      return new CraftSmithingTrimRecipe(
         CraftNamespacedKey.fromMinecraft(this.a), CraftRecipe.toBukkit(this.b), CraftRecipe.toBukkit(this.c), CraftRecipe.toBukkit(this.d)
      );
   }

   public static class a implements RecipeSerializer<SmithingTrimRecipe> {
      public SmithingTrimRecipe a(MinecraftKey minecraftkey, JsonObject jsonobject) {
         RecipeItemStack recipeitemstack = RecipeItemStack.a(ChatDeserializer.t(jsonobject, "template"));
         RecipeItemStack recipeitemstack1 = RecipeItemStack.a(ChatDeserializer.t(jsonobject, "base"));
         RecipeItemStack recipeitemstack2 = RecipeItemStack.a(ChatDeserializer.t(jsonobject, "addition"));
         return new SmithingTrimRecipe(minecraftkey, recipeitemstack, recipeitemstack1, recipeitemstack2);
      }

      public SmithingTrimRecipe a(MinecraftKey minecraftkey, PacketDataSerializer packetdataserializer) {
         RecipeItemStack recipeitemstack = RecipeItemStack.b(packetdataserializer);
         RecipeItemStack recipeitemstack1 = RecipeItemStack.b(packetdataserializer);
         RecipeItemStack recipeitemstack2 = RecipeItemStack.b(packetdataserializer);
         return new SmithingTrimRecipe(minecraftkey, recipeitemstack, recipeitemstack1, recipeitemstack2);
      }

      public void a(PacketDataSerializer packetdataserializer, SmithingTrimRecipe smithingtrimrecipe) {
         smithingtrimrecipe.b.a(packetdataserializer);
         smithingtrimrecipe.c.a(packetdataserializer);
         smithingtrimrecipe.d.a(packetdataserializer);
      }
   }
}
