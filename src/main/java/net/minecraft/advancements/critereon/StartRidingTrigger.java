package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;

public class StartRidingTrigger extends CriterionTriggerAbstract<StartRidingTrigger.a> {
   static final MinecraftKey a = new MinecraftKey("started_riding");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public StartRidingTrigger.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      return new StartRidingTrigger.a(var1);
   }

   public void a(EntityPlayer var0) {
      this.a(var0, var0x -> true);
   }

   public static class a extends CriterionInstanceAbstract {
      public a(CriterionConditionEntity.b var0) {
         super(StartRidingTrigger.a, var0);
      }

      public static StartRidingTrigger.a a(CriterionConditionEntity.a var0) {
         return new StartRidingTrigger.a(CriterionConditionEntity.b.a(var0.b()));
      }
   }
}
