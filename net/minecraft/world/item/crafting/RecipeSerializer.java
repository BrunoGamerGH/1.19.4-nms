package net.minecraft.world.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;

public interface RecipeSerializer<T extends IRecipe<?>> {
   RecipeSerializer<ShapedRecipes> a = a("crafting_shaped", new ShapedRecipes.a());
   RecipeSerializer<ShapelessRecipes> b = a("crafting_shapeless", new ShapelessRecipes.a());
   RecipeSerializer<RecipeArmorDye> c = a("crafting_special_armordye", new SimpleCraftingRecipeSerializer<>(RecipeArmorDye::new));
   RecipeSerializer<RecipeBookClone> d = a("crafting_special_bookcloning", new SimpleCraftingRecipeSerializer<>(RecipeBookClone::new));
   RecipeSerializer<RecipeMapClone> e = a("crafting_special_mapcloning", new SimpleCraftingRecipeSerializer<>(RecipeMapClone::new));
   RecipeSerializer<RecipeMapExtend> f = a("crafting_special_mapextending", new SimpleCraftingRecipeSerializer<>(RecipeMapExtend::new));
   RecipeSerializer<RecipeFireworks> g = a("crafting_special_firework_rocket", new SimpleCraftingRecipeSerializer<>(RecipeFireworks::new));
   RecipeSerializer<RecipeFireworksStar> h = a("crafting_special_firework_star", new SimpleCraftingRecipeSerializer<>(RecipeFireworksStar::new));
   RecipeSerializer<RecipeFireworksFade> i = a("crafting_special_firework_star_fade", new SimpleCraftingRecipeSerializer<>(RecipeFireworksFade::new));
   RecipeSerializer<RecipeTippedArrow> j = a("crafting_special_tippedarrow", new SimpleCraftingRecipeSerializer<>(RecipeTippedArrow::new));
   RecipeSerializer<RecipeBannerDuplicate> k = a("crafting_special_bannerduplicate", new SimpleCraftingRecipeSerializer<>(RecipeBannerDuplicate::new));
   RecipeSerializer<RecipiesShield> l = a("crafting_special_shielddecoration", new SimpleCraftingRecipeSerializer<>(RecipiesShield::new));
   RecipeSerializer<RecipeShulkerBox> m = a("crafting_special_shulkerboxcoloring", new SimpleCraftingRecipeSerializer<>(RecipeShulkerBox::new));
   RecipeSerializer<RecipeSuspiciousStew> n = a("crafting_special_suspiciousstew", new SimpleCraftingRecipeSerializer<>(RecipeSuspiciousStew::new));
   RecipeSerializer<RecipeRepair> o = a("crafting_special_repairitem", new SimpleCraftingRecipeSerializer<>(RecipeRepair::new));
   RecipeSerializer<FurnaceRecipe> p = a("smelting", new RecipeSerializerCooking<>(FurnaceRecipe::new, 200));
   RecipeSerializer<RecipeBlasting> q = a("blasting", new RecipeSerializerCooking<>(RecipeBlasting::new, 100));
   RecipeSerializer<RecipeSmoking> r = a("smoking", new RecipeSerializerCooking<>(RecipeSmoking::new, 100));
   RecipeSerializer<RecipeCampfire> s = a("campfire_cooking", new RecipeSerializerCooking<>(RecipeCampfire::new, 100));
   RecipeSerializer<RecipeStonecutting> t = a("stonecutting", new RecipeSingleItem.a<>(RecipeStonecutting::new));
   RecipeSerializer<LegacyUpgradeRecipe> u = a("smithing", new LegacyUpgradeRecipe.a());
   RecipeSerializer<SmithingTransformRecipe> v = a("smithing_transform", new SmithingTransformRecipe.a());
   RecipeSerializer<SmithingTrimRecipe> w = a("smithing_trim", new SmithingTrimRecipe.a());
   RecipeSerializer<DecoratedPotRecipe> x = a("crafting_decorated_pot", new SimpleCraftingRecipeSerializer<>(DecoratedPotRecipe::new));

   T b(MinecraftKey var1, JsonObject var2);

   T b(MinecraftKey var1, PacketDataSerializer var2);

   void a(PacketDataSerializer var1, T var2);

   static <S extends RecipeSerializer<T>, T extends IRecipe<?>> S a(String var0, S var1) {
      return IRegistry.a(BuiltInRegistries.t, var0, var1);
   }
}
