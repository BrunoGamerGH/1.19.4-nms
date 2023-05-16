package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javax.annotation.Nullable;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.EntityFishingHook;
import net.minecraft.world.phys.Vec3D;

public class CriterionConditionInOpenWater implements EntitySubPredicate {
   public static final CriterionConditionInOpenWater b = new CriterionConditionInOpenWater(false);
   private static final String c = "in_open_water";
   private final boolean d;

   private CriterionConditionInOpenWater(boolean var0) {
      this.d = var0;
   }

   public static CriterionConditionInOpenWater a(boolean var0) {
      return new CriterionConditionInOpenWater(var0);
   }

   public static CriterionConditionInOpenWater a(JsonObject var0) {
      JsonElement var1 = var0.get("in_open_water");
      return var1 != null ? new CriterionConditionInOpenWater(ChatDeserializer.c(var1, "in_open_water")) : b;
   }

   @Override
   public JsonObject a() {
      if (this == b) {
         return new JsonObject();
      } else {
         JsonObject var0 = new JsonObject();
         var0.add("in_open_water", new JsonPrimitive(this.d));
         return var0;
      }
   }

   @Override
   public EntitySubPredicate.a c() {
      return EntitySubPredicate.b.c;
   }

   @Override
   public boolean a(Entity var0, WorldServer var1, @Nullable Vec3D var2) {
      if (this == b) {
         return true;
      } else if (!(var0 instanceof EntityFishingHook)) {
         return false;
      } else {
         EntityFishingHook var3 = (EntityFishingHook)var0;
         return this.d == var3.i();
      }
   }
}
