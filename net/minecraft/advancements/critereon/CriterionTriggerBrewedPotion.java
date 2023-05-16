package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.alchemy.PotionRegistry;

public class CriterionTriggerBrewedPotion extends CriterionTriggerAbstract<CriterionTriggerBrewedPotion.a> {
   static final MinecraftKey a = new MinecraftKey("brewed_potion");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerBrewedPotion.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      PotionRegistry var3 = null;
      if (var0.has("potion")) {
         MinecraftKey var4 = new MinecraftKey(ChatDeserializer.h(var0, "potion"));
         var3 = BuiltInRegistries.j.b(var4).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + var4 + "'"));
      }

      return new CriterionTriggerBrewedPotion.a(var1, var3);
   }

   public void a(EntityPlayer var0, PotionRegistry var1) {
      this.a(var0, var1x -> var1x.a(var1));
   }

   public static class a extends CriterionInstanceAbstract {
      @Nullable
      private final PotionRegistry a;

      public a(CriterionConditionEntity.b var0, @Nullable PotionRegistry var1) {
         super(CriterionTriggerBrewedPotion.a, var0);
         this.a = var1;
      }

      public static CriterionTriggerBrewedPotion.a c() {
         return new CriterionTriggerBrewedPotion.a(CriterionConditionEntity.b.a, null);
      }

      public boolean a(PotionRegistry var0) {
         return this.a == null || this.a == var0;
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         if (this.a != null) {
            var1.addProperty("potion", BuiltInRegistries.j.b(this.a).toString());
         }

         return var1;
      }
   }
}
