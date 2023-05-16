package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.damagesource.DamageSource;

public class CriterionTriggerEntityHurtPlayer extends CriterionTriggerAbstract<CriterionTriggerEntityHurtPlayer.a> {
   static final MinecraftKey a = new MinecraftKey("entity_hurt_player");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerEntityHurtPlayer.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionDamage var3 = CriterionConditionDamage.a(var0.get("damage"));
      return new CriterionTriggerEntityHurtPlayer.a(var1, var3);
   }

   public void a(EntityPlayer var0, DamageSource var1, float var2, float var3, boolean var4) {
      this.a(var0, var5x -> var5x.a(var0, var1, var2, var3, var4));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionDamage a;

      public a(CriterionConditionEntity.b var0, CriterionConditionDamage var1) {
         super(CriterionTriggerEntityHurtPlayer.a, var0);
         this.a = var1;
      }

      public static CriterionTriggerEntityHurtPlayer.a c() {
         return new CriterionTriggerEntityHurtPlayer.a(CriterionConditionEntity.b.a, CriterionConditionDamage.a);
      }

      public static CriterionTriggerEntityHurtPlayer.a a(CriterionConditionDamage var0) {
         return new CriterionTriggerEntityHurtPlayer.a(CriterionConditionEntity.b.a, var0);
      }

      public static CriterionTriggerEntityHurtPlayer.a a(CriterionConditionDamage.a var0) {
         return new CriterionTriggerEntityHurtPlayer.a(CriterionConditionEntity.b.a, var0.b());
      }

      public boolean a(EntityPlayer var0, DamageSource var1, float var2, float var3, boolean var4) {
         return this.a.a(var0, var1, var2, var3, var4);
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("damage", this.a.a());
         return var1;
      }
   }
}
