package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class PlayerTrigger extends CriterionTriggerAbstract<PlayerTrigger.a> {
   final MinecraftKey a;

   public PlayerTrigger(MinecraftKey var0) {
      this.a = var0;
   }

   @Override
   public MinecraftKey a() {
      return this.a;
   }

   public PlayerTrigger.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      return new PlayerTrigger.a(this.a, var1);
   }

   public void a(EntityPlayer var0) {
      this.a(var0, var0x -> true);
   }

   public static class a extends CriterionInstanceAbstract {
      public a(MinecraftKey var0, CriterionConditionEntity.b var1) {
         super(var0, var1);
      }

      public static PlayerTrigger.a a(CriterionConditionLocation var0) {
         return new PlayerTrigger.a(CriterionTriggers.p.a, CriterionConditionEntity.b.a(CriterionConditionEntity.a.a().a(var0).b()));
      }

      public static PlayerTrigger.a a(CriterionConditionEntity var0) {
         return new PlayerTrigger.a(CriterionTriggers.p.a, CriterionConditionEntity.b.a(var0));
      }

      public static PlayerTrigger.a c() {
         return new PlayerTrigger.a(CriterionTriggers.q.a, CriterionConditionEntity.b.a);
      }

      public static PlayerTrigger.a d() {
         return new PlayerTrigger.a(CriterionTriggers.H.a, CriterionConditionEntity.b.a);
      }

      public static PlayerTrigger.a e() {
         return new PlayerTrigger.a(CriterionTriggers.Y.a, CriterionConditionEntity.b.a);
      }

      public static PlayerTrigger.a f() {
         return new PlayerTrigger.a(CriterionTriggers.w.a, CriterionConditionEntity.b.a);
      }

      public static PlayerTrigger.a a(Block var0, Item var1) {
         return a(
            CriterionConditionEntity.a.a()
               .a(CriterionConditionEntityEquipment.a.a().d(CriterionConditionItem.a.a().a(var1).b()).b())
               .b(CriterionConditionLocation.a.a().a(CriterionConditionBlock.a.a().a(var0).b()).b())
               .b()
         );
      }
   }
}
