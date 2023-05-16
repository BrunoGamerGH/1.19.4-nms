package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.crafting.IRecipe;

public class CriterionTriggerRecipeUnlocked extends CriterionTriggerAbstract<CriterionTriggerRecipeUnlocked.a> {
   static final MinecraftKey a = new MinecraftKey("recipe_unlocked");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerRecipeUnlocked.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      MinecraftKey var3 = new MinecraftKey(ChatDeserializer.h(var0, "recipe"));
      return new CriterionTriggerRecipeUnlocked.a(var1, var3);
   }

   public void a(EntityPlayer var0, IRecipe<?> var1) {
      this.a(var0, var1x -> var1x.a(var1));
   }

   public static CriterionTriggerRecipeUnlocked.a a(MinecraftKey var0) {
      return new CriterionTriggerRecipeUnlocked.a(CriterionConditionEntity.b.a, var0);
   }

   public static class a extends CriterionInstanceAbstract {
      private final MinecraftKey a;

      public a(CriterionConditionEntity.b var0, MinecraftKey var1) {
         super(CriterionTriggerRecipeUnlocked.a, var0);
         this.a = var1;
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.addProperty("recipe", this.a.toString());
         return var1;
      }

      public boolean a(IRecipe<?> var0) {
         return this.a.equals(var0.e());
      }
   }
}
