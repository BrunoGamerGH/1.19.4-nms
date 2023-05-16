package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EntitySlime;
import net.minecraft.world.phys.Vec3D;

public class SlimePredicate implements EntitySubPredicate {
   private final CriterionConditionValue.IntegerRange b;

   private SlimePredicate(CriterionConditionValue.IntegerRange var0) {
      this.b = var0;
   }

   public static SlimePredicate a(CriterionConditionValue.IntegerRange var0) {
      return new SlimePredicate(var0);
   }

   public static SlimePredicate a(JsonObject var0) {
      CriterionConditionValue.IntegerRange var1 = CriterionConditionValue.IntegerRange.a(var0.get("size"));
      return new SlimePredicate(var1);
   }

   @Override
   public JsonObject a() {
      JsonObject var0 = new JsonObject();
      var0.add("size", this.b.d());
      return var0;
   }

   @Override
   public boolean a(Entity var0, WorldServer var1, @Nullable Vec3D var2) {
      return var0 instanceof EntitySlime var3 ? this.b.d(var3.fU()) : false;
   }

   @Override
   public EntitySubPredicate.a c() {
      return EntitySubPredicate.b.e;
   }
}
