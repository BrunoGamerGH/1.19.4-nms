package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.ChatDeserializer;

public class CriterionTriggerPlayerGeneratesContainerLoot extends CriterionTriggerAbstract<CriterionTriggerPlayerGeneratesContainerLoot.a> {
   static final MinecraftKey a = new MinecraftKey("player_generates_container_loot");

   @Override
   public MinecraftKey a() {
      return a;
   }

   protected CriterionTriggerPlayerGeneratesContainerLoot.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      MinecraftKey var3 = new MinecraftKey(ChatDeserializer.h(var0, "loot_table"));
      return new CriterionTriggerPlayerGeneratesContainerLoot.a(var1, var3);
   }

   public void a(EntityPlayer var0, MinecraftKey var1) {
      this.a(var0, var1x -> var1x.b(var1));
   }

   public static class a extends CriterionInstanceAbstract {
      private final MinecraftKey a;

      public a(CriterionConditionEntity.b var0, MinecraftKey var1) {
         super(CriterionTriggerPlayerGeneratesContainerLoot.a, var0);
         this.a = var1;
      }

      public static CriterionTriggerPlayerGeneratesContainerLoot.a a(MinecraftKey var0) {
         return new CriterionTriggerPlayerGeneratesContainerLoot.a(CriterionConditionEntity.b.a, var0);
      }

      public boolean b(MinecraftKey var0) {
         return this.a.equals(var0);
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.addProperty("loot_table", this.a.toString());
         return var1;
      }
   }
}
