package net.minecraft.advancements.critereon;

import com.google.common.base.Joiner;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.EntityTypes;

public abstract class CriterionConditionEntityType {
   public static final CriterionConditionEntityType a = new CriterionConditionEntityType() {
      @Override
      public boolean a(EntityTypes<?> var0) {
         return true;
      }

      @Override
      public JsonElement a() {
         return JsonNull.INSTANCE;
      }
   };
   private static final Joiner b = Joiner.on(", ");

   public abstract boolean a(EntityTypes<?> var1);

   public abstract JsonElement a();

   public static CriterionConditionEntityType a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         String var1 = ChatDeserializer.a(var0, "type");
         if (var1.startsWith("#")) {
            MinecraftKey var2 = new MinecraftKey(var1.substring(1));
            return new CriterionConditionEntityType.a(TagKey.a(Registries.r, var2));
         } else {
            MinecraftKey var2 = new MinecraftKey(var1);
            EntityTypes<?> var3 = BuiltInRegistries.h
               .b(var2)
               .orElseThrow(() -> new JsonSyntaxException("Unknown entity type '" + var2 + "', valid types are: " + b.join(BuiltInRegistries.h.e())));
            return new CriterionConditionEntityType.b(var3);
         }
      } else {
         return a;
      }
   }

   public static CriterionConditionEntityType b(EntityTypes<?> var0) {
      return new CriterionConditionEntityType.b(var0);
   }

   public static CriterionConditionEntityType a(TagKey<EntityTypes<?>> var0) {
      return new CriterionConditionEntityType.a(var0);
   }

   static class a extends CriterionConditionEntityType {
      private final TagKey<EntityTypes<?>> b;

      public a(TagKey<EntityTypes<?>> var0) {
         this.b = var0;
      }

      @Override
      public boolean a(EntityTypes<?> var0) {
         return var0.a(this.b);
      }

      @Override
      public JsonElement a() {
         return new JsonPrimitive("#" + this.b.b());
      }
   }

   static class b extends CriterionConditionEntityType {
      private final EntityTypes<?> b;

      public b(EntityTypes<?> var0) {
         this.b = var0;
      }

      @Override
      public boolean a(EntityTypes<?> var0) {
         return this.b == var0;
      }

      @Override
      public JsonElement a() {
         return new JsonPrimitive(BuiltInRegistries.h.b(this.b).toString());
      }
   }
}
