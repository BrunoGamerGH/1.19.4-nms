package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.World;

public class CriterionTriggerChangedDimension extends CriterionTriggerAbstract<CriterionTriggerChangedDimension.a> {
   static final MinecraftKey a = new MinecraftKey("changed_dimension");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerChangedDimension.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      ResourceKey<World> var3 = var0.has("from") ? ResourceKey.a(Registries.aF, new MinecraftKey(ChatDeserializer.h(var0, "from"))) : null;
      ResourceKey<World> var4 = var0.has("to") ? ResourceKey.a(Registries.aF, new MinecraftKey(ChatDeserializer.h(var0, "to"))) : null;
      return new CriterionTriggerChangedDimension.a(var1, var3, var4);
   }

   public void a(EntityPlayer var0, ResourceKey<World> var1, ResourceKey<World> var2) {
      this.a(var0, var2x -> var2x.b(var1, var2));
   }

   public static class a extends CriterionInstanceAbstract {
      @Nullable
      private final ResourceKey<World> a;
      @Nullable
      private final ResourceKey<World> b;

      public a(CriterionConditionEntity.b var0, @Nullable ResourceKey<World> var1, @Nullable ResourceKey<World> var2) {
         super(CriterionTriggerChangedDimension.a, var0);
         this.a = var1;
         this.b = var2;
      }

      public static CriterionTriggerChangedDimension.a c() {
         return new CriterionTriggerChangedDimension.a(CriterionConditionEntity.b.a, null, null);
      }

      public static CriterionTriggerChangedDimension.a a(ResourceKey<World> var0, ResourceKey<World> var1) {
         return new CriterionTriggerChangedDimension.a(CriterionConditionEntity.b.a, var0, var1);
      }

      public static CriterionTriggerChangedDimension.a a(ResourceKey<World> var0) {
         return new CriterionTriggerChangedDimension.a(CriterionConditionEntity.b.a, null, var0);
      }

      public static CriterionTriggerChangedDimension.a b(ResourceKey<World> var0) {
         return new CriterionTriggerChangedDimension.a(CriterionConditionEntity.b.a, var0, null);
      }

      public boolean b(ResourceKey<World> var0, ResourceKey<World> var1) {
         if (this.a != null && this.a != var0) {
            return false;
         } else {
            return this.b == null || this.b == var1;
         }
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         if (this.a != null) {
            var1.addProperty("from", this.a.a().toString());
         }

         if (this.b != null) {
            var1.addProperty("to", this.b.a().toString());
         }

         return var1;
      }
   }
}
