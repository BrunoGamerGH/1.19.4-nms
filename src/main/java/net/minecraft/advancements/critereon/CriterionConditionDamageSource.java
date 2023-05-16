package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.phys.Vec3D;

public class CriterionConditionDamageSource {
   public static final CriterionConditionDamageSource a = CriterionConditionDamageSource.a.a().b();
   private final List<TagPredicate<DamageType>> b;
   private final CriterionConditionEntity c;
   private final CriterionConditionEntity d;

   public CriterionConditionDamageSource(List<TagPredicate<DamageType>> var0, CriterionConditionEntity var1, CriterionConditionEntity var2) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
   }

   public boolean a(EntityPlayer var0, DamageSource var1) {
      return this.a(var0.x(), var0.de(), var1);
   }

   public boolean a(WorldServer var0, Vec3D var1, DamageSource var2) {
      if (this == a) {
         return true;
      } else {
         for(TagPredicate<DamageType> var4 : this.b) {
            if (!var4.a(var2.k())) {
               return false;
            }
         }

         if (!this.c.a(var0, var1, var2.c())) {
            return false;
         } else {
            return this.d.a(var0, var1, var2.d());
         }
      }
   }

   public static CriterionConditionDamageSource a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "damage type");
         JsonArray var2 = ChatDeserializer.a(var1, "tags", null);
         List<TagPredicate<DamageType>> var3;
         if (var2 != null) {
            var3 = new ArrayList<>(var2.size());

            for(JsonElement var5 : var2) {
               var3.add(TagPredicate.a(var5, Registries.o));
            }
         } else {
            var3 = List.of();
         }

         CriterionConditionEntity var4 = CriterionConditionEntity.a(var1.get("direct_entity"));
         CriterionConditionEntity var5 = CriterionConditionEntity.a(var1.get("source_entity"));
         return new CriterionConditionDamageSource(var3, var4, var5);
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();
         if (!this.b.isEmpty()) {
            JsonArray var1 = new JsonArray(this.b.size());

            for(int var2 = 0; var2 < this.b.size(); ++var2) {
               var1.add(this.b.get(var2).a());
            }

            var0.add("tags", var1);
         }

         var0.add("direct_entity", this.c.a());
         var0.add("source_entity", this.d.a());
         return var0;
      }
   }

   public static class a {
      private final Builder<TagPredicate<DamageType>> a = ImmutableList.builder();
      private CriterionConditionEntity b = CriterionConditionEntity.a;
      private CriterionConditionEntity c = CriterionConditionEntity.a;

      public static CriterionConditionDamageSource.a a() {
         return new CriterionConditionDamageSource.a();
      }

      public CriterionConditionDamageSource.a a(TagPredicate<DamageType> var0) {
         this.a.add(var0);
         return this;
      }

      public CriterionConditionDamageSource.a a(CriterionConditionEntity var0) {
         this.b = var0;
         return this;
      }

      public CriterionConditionDamageSource.a a(CriterionConditionEntity.a var0) {
         this.b = var0.b();
         return this;
      }

      public CriterionConditionDamageSource.a b(CriterionConditionEntity var0) {
         this.c = var0;
         return this;
      }

      public CriterionConditionDamageSource.a b(CriterionConditionEntity.a var0) {
         this.c = var0.b();
         return this;
      }

      public CriterionConditionDamageSource b() {
         return new CriterionConditionDamageSource(this.a.build(), this.b, this.c);
      }
   }
}
