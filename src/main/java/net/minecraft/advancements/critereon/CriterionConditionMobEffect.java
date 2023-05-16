package net.minecraft.advancements.critereon;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;

public class CriterionConditionMobEffect {
   public static final CriterionConditionMobEffect a = new CriterionConditionMobEffect(Collections.emptyMap());
   private final Map<MobEffectList, CriterionConditionMobEffect.a> b;

   public CriterionConditionMobEffect(Map<MobEffectList, CriterionConditionMobEffect.a> var0) {
      this.b = var0;
   }

   public static CriterionConditionMobEffect a() {
      return new CriterionConditionMobEffect(Maps.newLinkedHashMap());
   }

   public CriterionConditionMobEffect a(MobEffectList var0) {
      this.b.put(var0, new CriterionConditionMobEffect.a());
      return this;
   }

   public CriterionConditionMobEffect a(MobEffectList var0, CriterionConditionMobEffect.a var1) {
      this.b.put(var0, var1);
      return this;
   }

   public boolean a(Entity var0) {
      if (this == a) {
         return true;
      } else {
         return var0 instanceof EntityLiving ? this.a(((EntityLiving)var0).em()) : false;
      }
   }

   public boolean a(EntityLiving var0) {
      return this == a ? true : this.a(var0.em());
   }

   public boolean a(Map<MobEffectList, MobEffect> var0) {
      if (this == a) {
         return true;
      } else {
         for(Entry<MobEffectList, CriterionConditionMobEffect.a> var2 : this.b.entrySet()) {
            MobEffect var3 = var0.get(var2.getKey());
            if (!var2.getValue().a(var3)) {
               return false;
            }
         }

         return true;
      }
   }

   public static CriterionConditionMobEffect a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "effects");
         Map<MobEffectList, CriterionConditionMobEffect.a> var2 = Maps.newLinkedHashMap();

         for(Entry<String, JsonElement> var4 : var1.entrySet()) {
            MinecraftKey var5 = new MinecraftKey(var4.getKey());
            MobEffectList var6 = BuiltInRegistries.e.b(var5).orElseThrow(() -> new JsonSyntaxException("Unknown effect '" + var5 + "'"));
            CriterionConditionMobEffect.a var7 = CriterionConditionMobEffect.a.a(ChatDeserializer.m((JsonElement)var4.getValue(), var4.getKey()));
            var2.put(var6, var7);
         }

         return new CriterionConditionMobEffect(var2);
      } else {
         return a;
      }
   }

   public JsonElement b() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();

         for(Entry<MobEffectList, CriterionConditionMobEffect.a> var2 : this.b.entrySet()) {
            var0.add(BuiltInRegistries.e.b(var2.getKey()).toString(), var2.getValue().a());
         }

         return var0;
      }
   }

   public static class a {
      private final CriterionConditionValue.IntegerRange a;
      private final CriterionConditionValue.IntegerRange b;
      @Nullable
      private final Boolean c;
      @Nullable
      private final Boolean d;

      public a(CriterionConditionValue.IntegerRange var0, CriterionConditionValue.IntegerRange var1, @Nullable Boolean var2, @Nullable Boolean var3) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
      }

      public a() {
         this(CriterionConditionValue.IntegerRange.e, CriterionConditionValue.IntegerRange.e, null, null);
      }

      public boolean a(@Nullable MobEffect var0) {
         if (var0 == null) {
            return false;
         } else if (!this.a.d(var0.e())) {
            return false;
         } else if (!this.b.d(var0.d())) {
            return false;
         } else if (this.c != null && this.c != var0.f()) {
            return false;
         } else {
            return this.d == null || this.d == var0.g();
         }
      }

      public JsonElement a() {
         JsonObject var0 = new JsonObject();
         var0.add("amplifier", this.a.d());
         var0.add("duration", this.b.d());
         var0.addProperty("ambient", this.c);
         var0.addProperty("visible", this.d);
         return var0;
      }

      public static CriterionConditionMobEffect.a a(JsonObject var0) {
         CriterionConditionValue.IntegerRange var1 = CriterionConditionValue.IntegerRange.a(var0.get("amplifier"));
         CriterionConditionValue.IntegerRange var2 = CriterionConditionValue.IntegerRange.a(var0.get("duration"));
         Boolean var3 = var0.has("ambient") ? ChatDeserializer.j(var0, "ambient") : null;
         Boolean var4 = var0.has("visible") ? ChatDeserializer.j(var0, "visible") : null;
         return new CriterionConditionMobEffect.a(var1, var2, var3, var4);
      }
   }
}
