package net.minecraft.advancements.critereon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionEntityProperty;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.scores.ScoreboardTeamBase;

public class CriterionConditionEntity {
   public static final CriterionConditionEntity a = new CriterionConditionEntity(
      CriterionConditionEntityType.a,
      CriterionConditionDistance.a,
      CriterionConditionLocation.a,
      CriterionConditionLocation.a,
      CriterionConditionMobEffect.a,
      CriterionConditionNBT.a,
      CriterionConditionEntityFlags.a,
      CriterionConditionEntityEquipment.a,
      EntitySubPredicate.a,
      null
   );
   private final CriterionConditionEntityType b;
   private final CriterionConditionDistance c;
   private final CriterionConditionLocation d;
   private final CriterionConditionLocation e;
   private final CriterionConditionMobEffect f;
   private final CriterionConditionNBT g;
   private final CriterionConditionEntityFlags h;
   private final CriterionConditionEntityEquipment i;
   private final EntitySubPredicate j;
   private final CriterionConditionEntity k;
   private final CriterionConditionEntity l;
   private final CriterionConditionEntity m;
   @Nullable
   private final String n;

   private CriterionConditionEntity(
      CriterionConditionEntityType var0,
      CriterionConditionDistance var1,
      CriterionConditionLocation var2,
      CriterionConditionLocation var3,
      CriterionConditionMobEffect var4,
      CriterionConditionNBT var5,
      CriterionConditionEntityFlags var6,
      CriterionConditionEntityEquipment var7,
      EntitySubPredicate var8,
      @Nullable String var9
   ) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
      this.h = var6;
      this.i = var7;
      this.j = var8;
      this.l = this;
      this.k = this;
      this.m = this;
      this.n = var9;
   }

   CriterionConditionEntity(
      CriterionConditionEntityType var0,
      CriterionConditionDistance var1,
      CriterionConditionLocation var2,
      CriterionConditionLocation var3,
      CriterionConditionMobEffect var4,
      CriterionConditionNBT var5,
      CriterionConditionEntityFlags var6,
      CriterionConditionEntityEquipment var7,
      EntitySubPredicate var8,
      CriterionConditionEntity var9,
      CriterionConditionEntity var10,
      CriterionConditionEntity var11,
      @Nullable String var12
   ) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
      this.h = var6;
      this.i = var7;
      this.j = var8;
      this.k = var9;
      this.l = var10;
      this.m = var11;
      this.n = var12;
   }

   public boolean a(EntityPlayer var0, @Nullable Entity var1) {
      return this.a(var0.x(), var0.de(), var1);
   }

   public boolean a(WorldServer var0, @Nullable Vec3D var1, @Nullable Entity var2) {
      if (this == a) {
         return true;
      } else if (var2 == null) {
         return false;
      } else if (!this.b.a(var2.ae())) {
         return false;
      } else {
         if (var1 == null) {
            if (this.c != CriterionConditionDistance.a) {
               return false;
            }
         } else if (!this.c.a(var1.c, var1.d, var1.e, var2.dl(), var2.dn(), var2.dr())) {
            return false;
         }

         if (!this.d.a(var0, var2.dl(), var2.dn(), var2.dr())) {
            return false;
         } else {
            if (this.e != CriterionConditionLocation.a) {
               Vec3D var3 = Vec3D.b(var2.aC());
               if (!this.e.a(var0, var3.a(), var3.b(), var3.c())) {
                  return false;
               }
            }

            if (!this.f.a(var2)) {
               return false;
            } else if (!this.g.a(var2)) {
               return false;
            } else if (!this.h.a(var2)) {
               return false;
            } else if (!this.i.a(var2)) {
               return false;
            } else if (!this.j.a(var2, var0, var1)) {
               return false;
            } else if (!this.k.a(var0, var1, var2.cV())) {
               return false;
            } else if (this.l != a && var2.cM().stream().noneMatch(var2x -> this.l.a(var0, var1, var2x))) {
               return false;
            } else if (!this.m.a(var0, var1, var2 instanceof EntityInsentient ? ((EntityInsentient)var2).P_() : null)) {
               return false;
            } else {
               if (this.n != null) {
                  ScoreboardTeamBase var3 = var2.cb();
                  if (var3 == null || !this.n.equals(var3.b())) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   public static CriterionConditionEntity a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "entity");
         CriterionConditionEntityType var2 = CriterionConditionEntityType.a(var1.get("type"));
         CriterionConditionDistance var3 = CriterionConditionDistance.a(var1.get("distance"));
         CriterionConditionLocation var4 = CriterionConditionLocation.a(var1.get("location"));
         CriterionConditionLocation var5 = CriterionConditionLocation.a(var1.get("stepping_on"));
         CriterionConditionMobEffect var6 = CriterionConditionMobEffect.a(var1.get("effects"));
         CriterionConditionNBT var7 = CriterionConditionNBT.a(var1.get("nbt"));
         CriterionConditionEntityFlags var8 = CriterionConditionEntityFlags.a(var1.get("flags"));
         CriterionConditionEntityEquipment var9 = CriterionConditionEntityEquipment.a(var1.get("equipment"));
         EntitySubPredicate var10 = EntitySubPredicate.a(var1.get("type_specific"));
         CriterionConditionEntity var11 = a(var1.get("vehicle"));
         CriterionConditionEntity var12 = a(var1.get("passenger"));
         CriterionConditionEntity var13 = a(var1.get("targeted_entity"));
         String var14 = ChatDeserializer.a(var1, "team", null);
         return new CriterionConditionEntity.a()
            .a(var2)
            .a(var3)
            .a(var4)
            .b(var5)
            .a(var6)
            .a(var7)
            .a(var8)
            .a(var9)
            .a(var10)
            .a(var14)
            .a(var11)
            .b(var12)
            .c(var13)
            .b();
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();
         var0.add("type", this.b.a());
         var0.add("distance", this.c.a());
         var0.add("location", this.d.a());
         var0.add("stepping_on", this.e.a());
         var0.add("effects", this.f.b());
         var0.add("nbt", this.g.a());
         var0.add("flags", this.h.a());
         var0.add("equipment", this.i.a());
         var0.add("type_specific", this.j.b());
         var0.add("vehicle", this.k.a());
         var0.add("passenger", this.l.a());
         var0.add("targeted_entity", this.m.a());
         var0.addProperty("team", this.n);
         return var0;
      }
   }

   public static LootTableInfo b(EntityPlayer var0, Entity var1) {
      return new LootTableInfo.Builder(var0.x())
         .a(LootContextParameters.a, var1)
         .a(LootContextParameters.f, var0.de())
         .a(var0.dZ())
         .a(LootContextParameterSets.k);
   }

   public static class a {
      private CriterionConditionEntityType a;
      private CriterionConditionDistance b;
      private CriterionConditionLocation c;
      private CriterionConditionLocation d;
      private CriterionConditionMobEffect e;
      private CriterionConditionNBT f;
      private CriterionConditionEntityFlags g;
      private CriterionConditionEntityEquipment h;
      private EntitySubPredicate i;
      private CriterionConditionEntity j;
      private CriterionConditionEntity k;
      private CriterionConditionEntity l;
      @Nullable
      private String m;

      public a() {
         this.a = CriterionConditionEntityType.a;
         this.b = CriterionConditionDistance.a;
         this.c = CriterionConditionLocation.a;
         this.d = CriterionConditionLocation.a;
         this.e = CriterionConditionMobEffect.a;
         this.f = CriterionConditionNBT.a;
         this.g = CriterionConditionEntityFlags.a;
         this.h = CriterionConditionEntityEquipment.a;
         this.i = EntitySubPredicate.a;
         this.j = CriterionConditionEntity.a;
         this.k = CriterionConditionEntity.a;
         this.l = CriterionConditionEntity.a;
      }

      public static CriterionConditionEntity.a a() {
         return new CriterionConditionEntity.a();
      }

      public CriterionConditionEntity.a a(EntityTypes<?> var0) {
         this.a = CriterionConditionEntityType.b(var0);
         return this;
      }

      public CriterionConditionEntity.a a(TagKey<EntityTypes<?>> var0) {
         this.a = CriterionConditionEntityType.a(var0);
         return this;
      }

      public CriterionConditionEntity.a a(CriterionConditionEntityType var0) {
         this.a = var0;
         return this;
      }

      public CriterionConditionEntity.a a(CriterionConditionDistance var0) {
         this.b = var0;
         return this;
      }

      public CriterionConditionEntity.a a(CriterionConditionLocation var0) {
         this.c = var0;
         return this;
      }

      public CriterionConditionEntity.a b(CriterionConditionLocation var0) {
         this.d = var0;
         return this;
      }

      public CriterionConditionEntity.a a(CriterionConditionMobEffect var0) {
         this.e = var0;
         return this;
      }

      public CriterionConditionEntity.a a(CriterionConditionNBT var0) {
         this.f = var0;
         return this;
      }

      public CriterionConditionEntity.a a(CriterionConditionEntityFlags var0) {
         this.g = var0;
         return this;
      }

      public CriterionConditionEntity.a a(CriterionConditionEntityEquipment var0) {
         this.h = var0;
         return this;
      }

      public CriterionConditionEntity.a a(EntitySubPredicate var0) {
         this.i = var0;
         return this;
      }

      public CriterionConditionEntity.a a(CriterionConditionEntity var0) {
         this.j = var0;
         return this;
      }

      public CriterionConditionEntity.a b(CriterionConditionEntity var0) {
         this.k = var0;
         return this;
      }

      public CriterionConditionEntity.a c(CriterionConditionEntity var0) {
         this.l = var0;
         return this;
      }

      public CriterionConditionEntity.a a(@Nullable String var0) {
         this.m = var0;
         return this;
      }

      public CriterionConditionEntity b() {
         return new CriterionConditionEntity(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m);
      }
   }

   public static class b {
      public static final CriterionConditionEntity.b a = new CriterionConditionEntity.b(new LootItemCondition[0]);
      private final LootItemCondition[] b;
      private final Predicate<LootTableInfo> c;

      private b(LootItemCondition[] var0) {
         this.b = var0;
         this.c = LootItemConditions.a(var0);
      }

      public static CriterionConditionEntity.b a(LootItemCondition... var0) {
         return new CriterionConditionEntity.b(var0);
      }

      public static CriterionConditionEntity.b a(JsonObject var0, String var1, LootDeserializationContext var2) {
         JsonElement var3 = var0.get(var1);
         return a(var1, var2, var3);
      }

      public static CriterionConditionEntity.b[] b(JsonObject var0, String var1, LootDeserializationContext var2) {
         JsonElement var3 = var0.get(var1);
         if (var3 != null && !var3.isJsonNull()) {
            JsonArray var4 = ChatDeserializer.n(var3, var1);
            CriterionConditionEntity.b[] var5 = new CriterionConditionEntity.b[var4.size()];

            for(int var6 = 0; var6 < var4.size(); ++var6) {
               var5[var6] = a(var1 + "[" + var6 + "]", var2, var4.get(var6));
            }

            return var5;
         } else {
            return new CriterionConditionEntity.b[0];
         }
      }

      private static CriterionConditionEntity.b a(String var0, LootDeserializationContext var1, @Nullable JsonElement var2) {
         if (var2 != null && var2.isJsonArray()) {
            LootItemCondition[] var3 = var1.a(var2.getAsJsonArray(), var1.a() + "/" + var0, LootContextParameterSets.k);
            return new CriterionConditionEntity.b(var3);
         } else {
            CriterionConditionEntity var3 = CriterionConditionEntity.a(var2);
            return a(var3);
         }
      }

      public static CriterionConditionEntity.b a(CriterionConditionEntity var0) {
         if (var0 == CriterionConditionEntity.a) {
            return a;
         } else {
            LootItemCondition var1 = LootItemConditionEntityProperty.a(LootTableInfo.EntityTarget.a, var0).build();
            return new CriterionConditionEntity.b(new LootItemCondition[]{var1});
         }
      }

      public boolean a(LootTableInfo var0) {
         return this.c.test(var0);
      }

      public JsonElement a(LootSerializationContext var0) {
         return (JsonElement)(this.b.length == 0 ? JsonNull.INSTANCE : var0.a(this.b));
      }

      public static JsonElement a(CriterionConditionEntity.b[] var0, LootSerializationContext var1) {
         if (var0.length == 0) {
            return JsonNull.INSTANCE;
         } else {
            JsonArray var2 = new JsonArray();

            for(CriterionConditionEntity.b var6 : var0) {
               var2.add(var6.a(var1));
            }

            return var2;
         }
      }
   }
}
