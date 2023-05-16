package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionInstance;
import net.minecraft.resources.MinecraftKey;

public abstract class CriterionInstanceAbstract implements CriterionInstance {
   private final MinecraftKey a;
   private final CriterionConditionEntity.b b;

   public CriterionInstanceAbstract(MinecraftKey var0, CriterionConditionEntity.b var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public MinecraftKey a() {
      return this.a;
   }

   protected CriterionConditionEntity.b b() {
      return this.b;
   }

   @Override
   public JsonObject a(LootSerializationContext var0) {
      JsonObject var1 = new JsonObject();
      var1.add("player", this.b.a(var0));
      return var1;
   }

   @Override
   public String toString() {
      return "AbstractCriterionInstance{criterion=" + this.a + "}";
   }
}
