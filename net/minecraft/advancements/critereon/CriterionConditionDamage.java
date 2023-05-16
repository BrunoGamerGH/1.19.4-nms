package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.damagesource.DamageSource;

public class CriterionConditionDamage {
   public static final CriterionConditionDamage a = CriterionConditionDamage.a.a().b();
   private final CriterionConditionValue.DoubleRange b;
   private final CriterionConditionValue.DoubleRange c;
   private final CriterionConditionEntity d;
   @Nullable
   private final Boolean e;
   private final CriterionConditionDamageSource f;

   public CriterionConditionDamage() {
      this.b = CriterionConditionValue.DoubleRange.e;
      this.c = CriterionConditionValue.DoubleRange.e;
      this.d = CriterionConditionEntity.a;
      this.e = null;
      this.f = CriterionConditionDamageSource.a;
   }

   public CriterionConditionDamage(
      CriterionConditionValue.DoubleRange var0,
      CriterionConditionValue.DoubleRange var1,
      CriterionConditionEntity var2,
      @Nullable Boolean var3,
      CriterionConditionDamageSource var4
   ) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
   }

   public boolean a(EntityPlayer var0, DamageSource var1, float var2, float var3, boolean var4) {
      if (this == a) {
         return true;
      } else if (!this.b.d((double)var2)) {
         return false;
      } else if (!this.c.d((double)var3)) {
         return false;
      } else if (!this.d.a(var0, var1.d())) {
         return false;
      } else if (this.e != null && this.e != var4) {
         return false;
      } else {
         return this.f.a(var0, var1);
      }
   }

   public static CriterionConditionDamage a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "damage");
         CriterionConditionValue.DoubleRange var2 = CriterionConditionValue.DoubleRange.a(var1.get("dealt"));
         CriterionConditionValue.DoubleRange var3 = CriterionConditionValue.DoubleRange.a(var1.get("taken"));
         Boolean var4 = var1.has("blocked") ? ChatDeserializer.j(var1, "blocked") : null;
         CriterionConditionEntity var5 = CriterionConditionEntity.a(var1.get("source_entity"));
         CriterionConditionDamageSource var6 = CriterionConditionDamageSource.a(var1.get("type"));
         return new CriterionConditionDamage(var2, var3, var5, var4, var6);
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();
         var0.add("dealt", this.b.d());
         var0.add("taken", this.c.d());
         var0.add("source_entity", this.d.a());
         var0.add("type", this.f.a());
         if (this.e != null) {
            var0.addProperty("blocked", this.e);
         }

         return var0;
      }
   }

   public static class a {
      private CriterionConditionValue.DoubleRange a = CriterionConditionValue.DoubleRange.e;
      private CriterionConditionValue.DoubleRange b = CriterionConditionValue.DoubleRange.e;
      private CriterionConditionEntity c = CriterionConditionEntity.a;
      @Nullable
      private Boolean d;
      private CriterionConditionDamageSource e = CriterionConditionDamageSource.a;

      public static CriterionConditionDamage.a a() {
         return new CriterionConditionDamage.a();
      }

      public CriterionConditionDamage.a a(CriterionConditionValue.DoubleRange var0) {
         this.a = var0;
         return this;
      }

      public CriterionConditionDamage.a b(CriterionConditionValue.DoubleRange var0) {
         this.b = var0;
         return this;
      }

      public CriterionConditionDamage.a a(CriterionConditionEntity var0) {
         this.c = var0;
         return this;
      }

      public CriterionConditionDamage.a a(Boolean var0) {
         this.d = var0;
         return this;
      }

      public CriterionConditionDamage.a a(CriterionConditionDamageSource var0) {
         this.e = var0;
         return this;
      }

      public CriterionConditionDamage.a a(CriterionConditionDamageSource.a var0) {
         this.e = var0.b();
         return this;
      }

      public CriterionConditionDamage b() {
         return new CriterionConditionDamage(this.a, this.b, this.c, this.d, this.e);
      }
   }
}
