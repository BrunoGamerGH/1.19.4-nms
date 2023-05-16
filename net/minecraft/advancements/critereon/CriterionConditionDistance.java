package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.MathHelper;

public class CriterionConditionDistance {
   public static final CriterionConditionDistance a = new CriterionConditionDistance(
      CriterionConditionValue.DoubleRange.e,
      CriterionConditionValue.DoubleRange.e,
      CriterionConditionValue.DoubleRange.e,
      CriterionConditionValue.DoubleRange.e,
      CriterionConditionValue.DoubleRange.e
   );
   private final CriterionConditionValue.DoubleRange b;
   private final CriterionConditionValue.DoubleRange c;
   private final CriterionConditionValue.DoubleRange d;
   private final CriterionConditionValue.DoubleRange e;
   private final CriterionConditionValue.DoubleRange f;

   public CriterionConditionDistance(
      CriterionConditionValue.DoubleRange var0,
      CriterionConditionValue.DoubleRange var1,
      CriterionConditionValue.DoubleRange var2,
      CriterionConditionValue.DoubleRange var3,
      CriterionConditionValue.DoubleRange var4
   ) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
   }

   public static CriterionConditionDistance a(CriterionConditionValue.DoubleRange var0) {
      return new CriterionConditionDistance(
         CriterionConditionValue.DoubleRange.e,
         CriterionConditionValue.DoubleRange.e,
         CriterionConditionValue.DoubleRange.e,
         var0,
         CriterionConditionValue.DoubleRange.e
      );
   }

   public static CriterionConditionDistance b(CriterionConditionValue.DoubleRange var0) {
      return new CriterionConditionDistance(
         CriterionConditionValue.DoubleRange.e,
         var0,
         CriterionConditionValue.DoubleRange.e,
         CriterionConditionValue.DoubleRange.e,
         CriterionConditionValue.DoubleRange.e
      );
   }

   public static CriterionConditionDistance c(CriterionConditionValue.DoubleRange var0) {
      return new CriterionConditionDistance(
         CriterionConditionValue.DoubleRange.e,
         CriterionConditionValue.DoubleRange.e,
         CriterionConditionValue.DoubleRange.e,
         CriterionConditionValue.DoubleRange.e,
         var0
      );
   }

   public boolean a(double var0, double var2, double var4, double var6, double var8, double var10) {
      float var12 = (float)(var0 - var6);
      float var13 = (float)(var2 - var8);
      float var14 = (float)(var4 - var10);
      if (!this.b.d((double)MathHelper.e(var12)) || !this.c.d((double)MathHelper.e(var13)) || !this.d.d((double)MathHelper.e(var14))) {
         return false;
      } else if (!this.e.e((double)(var12 * var12 + var14 * var14))) {
         return false;
      } else {
         return this.f.e((double)(var12 * var12 + var13 * var13 + var14 * var14));
      }
   }

   public static CriterionConditionDistance a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "distance");
         CriterionConditionValue.DoubleRange var2 = CriterionConditionValue.DoubleRange.a(var1.get("x"));
         CriterionConditionValue.DoubleRange var3 = CriterionConditionValue.DoubleRange.a(var1.get("y"));
         CriterionConditionValue.DoubleRange var4 = CriterionConditionValue.DoubleRange.a(var1.get("z"));
         CriterionConditionValue.DoubleRange var5 = CriterionConditionValue.DoubleRange.a(var1.get("horizontal"));
         CriterionConditionValue.DoubleRange var6 = CriterionConditionValue.DoubleRange.a(var1.get("absolute"));
         return new CriterionConditionDistance(var2, var3, var4, var5, var6);
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();
         var0.add("x", this.b.d());
         var0.add("y", this.c.d());
         var0.add("z", this.d.d());
         var0.add("horizontal", this.e.d());
         var0.add("absolute", this.f.d());
         return var0;
      }
   }
}
