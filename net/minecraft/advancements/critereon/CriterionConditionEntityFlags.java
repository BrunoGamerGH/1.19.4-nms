package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;

public class CriterionConditionEntityFlags {
   public static final CriterionConditionEntityFlags a = new CriterionConditionEntityFlags.a().b();
   @Nullable
   private final Boolean b;
   @Nullable
   private final Boolean c;
   @Nullable
   private final Boolean d;
   @Nullable
   private final Boolean e;
   @Nullable
   private final Boolean f;

   public CriterionConditionEntityFlags(@Nullable Boolean var0, @Nullable Boolean var1, @Nullable Boolean var2, @Nullable Boolean var3, @Nullable Boolean var4) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
   }

   public boolean a(Entity var0) {
      if (this.b != null && var0.bK() != this.b) {
         return false;
      } else if (this.c != null && var0.bT() != this.c) {
         return false;
      } else if (this.d != null && var0.bU() != this.d) {
         return false;
      } else if (this.e != null && var0.bV() != this.e) {
         return false;
      } else {
         return this.f == null || !(var0 instanceof EntityLiving) || ((EntityLiving)var0).y_() == this.f;
      }
   }

   @Nullable
   private static Boolean a(JsonObject var0, String var1) {
      return var0.has(var1) ? ChatDeserializer.j(var0, var1) : null;
   }

   public static CriterionConditionEntityFlags a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "entity flags");
         Boolean var2 = a(var1, "is_on_fire");
         Boolean var3 = a(var1, "is_sneaking");
         Boolean var4 = a(var1, "is_sprinting");
         Boolean var5 = a(var1, "is_swimming");
         Boolean var6 = a(var1, "is_baby");
         return new CriterionConditionEntityFlags(var2, var3, var4, var5, var6);
      } else {
         return a;
      }
   }

   private void a(JsonObject var0, String var1, @Nullable Boolean var2) {
      if (var2 != null) {
         var0.addProperty(var1, var2);
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();
         this.a(var0, "is_on_fire", this.b);
         this.a(var0, "is_sneaking", this.c);
         this.a(var0, "is_sprinting", this.d);
         this.a(var0, "is_swimming", this.e);
         this.a(var0, "is_baby", this.f);
         return var0;
      }
   }

   public static class a {
      @Nullable
      private Boolean a;
      @Nullable
      private Boolean b;
      @Nullable
      private Boolean c;
      @Nullable
      private Boolean d;
      @Nullable
      private Boolean e;

      public static CriterionConditionEntityFlags.a a() {
         return new CriterionConditionEntityFlags.a();
      }

      public CriterionConditionEntityFlags.a a(@Nullable Boolean var0) {
         this.a = var0;
         return this;
      }

      public CriterionConditionEntityFlags.a b(@Nullable Boolean var0) {
         this.b = var0;
         return this;
      }

      public CriterionConditionEntityFlags.a c(@Nullable Boolean var0) {
         this.c = var0;
         return this;
      }

      public CriterionConditionEntityFlags.a d(@Nullable Boolean var0) {
         this.d = var0;
         return this;
      }

      public CriterionConditionEntityFlags.a e(@Nullable Boolean var0) {
         this.e = var0;
         return this;
      }

      public CriterionConditionEntityFlags b() {
         return new CriterionConditionEntityFlags(this.a, this.b, this.c, this.d, this.e);
      }
   }
}
