package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionInstance;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.AdvancementDataPlayer;

public class CriterionTriggerImpossible implements CriterionTrigger<CriterionTriggerImpossible.a> {
   static final MinecraftKey a = new MinecraftKey("impossible");

   @Override
   public MinecraftKey a() {
      return a;
   }

   @Override
   public void a(AdvancementDataPlayer var0, CriterionTrigger.a<CriterionTriggerImpossible.a> var1) {
   }

   @Override
   public void b(AdvancementDataPlayer var0, CriterionTrigger.a<CriterionTriggerImpossible.a> var1) {
   }

   @Override
   public void a(AdvancementDataPlayer var0) {
   }

   public CriterionTriggerImpossible.a b(JsonObject var0, LootDeserializationContext var1) {
      return new CriterionTriggerImpossible.a();
   }

   public static class a implements CriterionInstance {
      @Override
      public MinecraftKey a() {
         return CriterionTriggerImpossible.a;
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         return new JsonObject();
      }
   }
}
