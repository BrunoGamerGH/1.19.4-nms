package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.ChatDeserializer;

public class CriterionConditionLight {
   public static final CriterionConditionLight a = new CriterionConditionLight(CriterionConditionValue.IntegerRange.e);
   private final CriterionConditionValue.IntegerRange b;

   CriterionConditionLight(CriterionConditionValue.IntegerRange var0) {
      this.b = var0;
   }

   public boolean a(WorldServer var0, BlockPosition var1) {
      if (this == a) {
         return true;
      } else if (!var0.o(var1)) {
         return false;
      } else {
         return this.b.d(var0.C(var1));
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();
         var0.add("light", this.b.d());
         return var0;
      }
   }

   public static CriterionConditionLight a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "light");
         CriterionConditionValue.IntegerRange var2 = CriterionConditionValue.IntegerRange.a(var1.get("light"));
         return new CriterionConditionLight(var2);
      } else {
         return a;
      }
   }

   public static class a {
      private CriterionConditionValue.IntegerRange a = CriterionConditionValue.IntegerRange.e;

      public static CriterionConditionLight.a a() {
         return new CriterionConditionLight.a();
      }

      public CriterionConditionLight.a a(CriterionConditionValue.IntegerRange var0) {
         this.a = var0;
         return this;
      }

      public CriterionConditionLight b() {
         return new CriterionConditionLight(this.a);
      }
   }
}
