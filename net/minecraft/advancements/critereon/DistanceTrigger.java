package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.phys.Vec3D;

public class DistanceTrigger extends CriterionTriggerAbstract<DistanceTrigger.a> {
   final MinecraftKey a;

   public DistanceTrigger(MinecraftKey var0) {
      this.a = var0;
   }

   @Override
   public MinecraftKey a() {
      return this.a;
   }

   public DistanceTrigger.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionLocation var3 = CriterionConditionLocation.a(var0.get("start_position"));
      CriterionConditionDistance var4 = CriterionConditionDistance.a(var0.get("distance"));
      return new DistanceTrigger.a(this.a, var1, var3, var4);
   }

   public void a(EntityPlayer var0, Vec3D var1) {
      Vec3D var2 = var0.de();
      this.a(var0, var3x -> var3x.a(var0.x(), var1, var2));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionLocation a;
      private final CriterionConditionDistance b;

      public a(MinecraftKey var0, CriterionConditionEntity.b var1, CriterionConditionLocation var2, CriterionConditionDistance var3) {
         super(var0, var1);
         this.a = var2;
         this.b = var3;
      }

      public static DistanceTrigger.a a(CriterionConditionEntity.a var0, CriterionConditionDistance var1, CriterionConditionLocation var2) {
         return new DistanceTrigger.a(CriterionTriggers.U.a, CriterionConditionEntity.b.a(var0.b()), var2, var1);
      }

      public static DistanceTrigger.a a(CriterionConditionEntity.a var0, CriterionConditionDistance var1) {
         return new DistanceTrigger.a(CriterionTriggers.V.a, CriterionConditionEntity.b.a(var0.b()), CriterionConditionLocation.a, var1);
      }

      public static DistanceTrigger.a a(CriterionConditionDistance var0) {
         return new DistanceTrigger.a(CriterionTriggers.C.a, CriterionConditionEntity.b.a, CriterionConditionLocation.a, var0);
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("start_position", this.a.a());
         var1.add("distance", this.b.a());
         return var1;
      }

      public boolean a(WorldServer var0, Vec3D var1, Vec3D var2) {
         if (!this.a.a(var0, var1.c, var1.d, var1.e)) {
            return false;
         } else {
            return this.b.a(var1.c, var1.d, var1.e, var2.c, var2.d, var2.e);
         }
      }
   }
}
