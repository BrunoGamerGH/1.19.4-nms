package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.phys.Vec3D;

public class LighthingBoltPredicate implements EntitySubPredicate {
   private static final String b = "blocks_set_on_fire";
   private static final String c = "entity_struck";
   private final CriterionConditionValue.IntegerRange d;
   private final CriterionConditionEntity e;

   private LighthingBoltPredicate(CriterionConditionValue.IntegerRange var0, CriterionConditionEntity var1) {
      this.d = var0;
      this.e = var1;
   }

   public static LighthingBoltPredicate a(CriterionConditionValue.IntegerRange var0) {
      return new LighthingBoltPredicate(var0, CriterionConditionEntity.a);
   }

   public static LighthingBoltPredicate a(JsonObject var0) {
      return new LighthingBoltPredicate(
         CriterionConditionValue.IntegerRange.a(var0.get("blocks_set_on_fire")), CriterionConditionEntity.a(var0.get("entity_struck"))
      );
   }

   @Override
   public JsonObject a() {
      JsonObject var0 = new JsonObject();
      var0.add("blocks_set_on_fire", this.d.d());
      var0.add("entity_struck", this.e.a());
      return var0;
   }

   @Override
   public EntitySubPredicate.a c() {
      return EntitySubPredicate.b.b;
   }

   @Override
   public boolean a(Entity var0, WorldServer var1, @Nullable Vec3D var2) {
      if (!(var0 instanceof EntityLightning)) {
         return false;
      } else {
         EntityLightning var3 = (EntityLightning)var0;
         return this.d.d(var3.j()) && (this.e == CriterionConditionEntity.a || var3.k().anyMatch(var2x -> this.e.a(var1, var2, var2x)));
      }
   }
}
