package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class CriterionTriggerChanneledLightning extends CriterionTriggerAbstract<CriterionTriggerChanneledLightning.a> {
   static final MinecraftKey a = new MinecraftKey("channeled_lightning");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerChanneledLightning.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionEntity.b[] var3 = CriterionConditionEntity.b.b(var0, "victims", var2);
      return new CriterionTriggerChanneledLightning.a(var1, var3);
   }

   public void a(EntityPlayer var0, Collection<? extends Entity> var1) {
      List<LootTableInfo> var2 = var1.stream().map(var1x -> CriterionConditionEntity.b(var0, var1x)).collect(Collectors.toList());
      this.a(var0, var1x -> var1x.a(var2));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionEntity.b[] a;

      public a(CriterionConditionEntity.b var0, CriterionConditionEntity.b[] var1) {
         super(CriterionTriggerChanneledLightning.a, var0);
         this.a = var1;
      }

      public static CriterionTriggerChanneledLightning.a a(CriterionConditionEntity... var0) {
         return new CriterionTriggerChanneledLightning.a(
            CriterionConditionEntity.b.a, Stream.of(var0).map(CriterionConditionEntity.b::a).toArray(var0x -> new CriterionConditionEntity.b[var0x])
         );
      }

      public boolean a(Collection<? extends LootTableInfo> var0) {
         for(CriterionConditionEntity.b var4 : this.a) {
            boolean var5 = false;

            for(LootTableInfo var7 : var0) {
               if (var4.a(var7)) {
                  var5 = true;
                  break;
               }
            }

            if (!var5) {
               return false;
            }
         }

         return true;
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("victims", CriterionConditionEntity.b.a(this.a, var0));
         return var1;
      }
   }
}
