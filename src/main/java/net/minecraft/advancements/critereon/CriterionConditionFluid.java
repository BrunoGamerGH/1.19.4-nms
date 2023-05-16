package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;

public class CriterionConditionFluid {
   public static final CriterionConditionFluid a = new CriterionConditionFluid(null, null, CriterionTriggerProperties.a);
   @Nullable
   private final TagKey<FluidType> b;
   @Nullable
   private final FluidType c;
   private final CriterionTriggerProperties d;

   public CriterionConditionFluid(@Nullable TagKey<FluidType> var0, @Nullable FluidType var1, CriterionTriggerProperties var2) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
   }

   public boolean a(WorldServer var0, BlockPosition var1) {
      if (this == a) {
         return true;
      } else if (!var0.o(var1)) {
         return false;
      } else {
         Fluid var2 = var0.b_(var1);
         if (this.b != null && !var2.a(this.b)) {
            return false;
         } else if (this.c != null && !var2.b(this.c)) {
            return false;
         } else {
            return this.d.a(var2);
         }
      }
   }

   public static CriterionConditionFluid a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "fluid");
         FluidType var2 = null;
         if (var1.has("fluid")) {
            MinecraftKey var3 = new MinecraftKey(ChatDeserializer.h(var1, "fluid"));
            var2 = BuiltInRegistries.d.a(var3);
         }

         TagKey<FluidType> var3 = null;
         if (var1.has("tag")) {
            MinecraftKey var4 = new MinecraftKey(ChatDeserializer.h(var1, "tag"));
            var3 = TagKey.a(Registries.v, var4);
         }

         CriterionTriggerProperties var4 = CriterionTriggerProperties.a(var1.get("state"));
         return new CriterionConditionFluid(var3, var2, var4);
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();
         if (this.c != null) {
            var0.addProperty("fluid", BuiltInRegistries.d.b(this.c).toString());
         }

         if (this.b != null) {
            var0.addProperty("tag", this.b.b().toString());
         }

         var0.add("state", this.d.a());
         return var0;
      }
   }

   public static class a {
      @Nullable
      private FluidType a;
      @Nullable
      private TagKey<FluidType> b;
      private CriterionTriggerProperties c = CriterionTriggerProperties.a;

      private a() {
      }

      public static CriterionConditionFluid.a a() {
         return new CriterionConditionFluid.a();
      }

      public CriterionConditionFluid.a a(FluidType var0) {
         this.a = var0;
         return this;
      }

      public CriterionConditionFluid.a a(TagKey<FluidType> var0) {
         this.b = var0;
         return this;
      }

      public CriterionConditionFluid.a a(CriterionTriggerProperties var0) {
         this.c = var0;
         return this;
      }

      public CriterionConditionFluid b() {
         return new CriterionConditionFluid(this.b, this.a, this.c);
      }
   }
}
