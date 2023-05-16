package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class CriterionTriggerKilledByCrossbow extends CriterionTriggerAbstract<CriterionTriggerKilledByCrossbow.a> {
   static final MinecraftKey a = new MinecraftKey("killed_by_crossbow");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerKilledByCrossbow.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionEntity.b[] var3 = CriterionConditionEntity.b.b(var0, "victims", var2);
      CriterionConditionValue.IntegerRange var4 = CriterionConditionValue.IntegerRange.a(var0.get("unique_entity_types"));
      return new CriterionTriggerKilledByCrossbow.a(var1, var3, var4);
   }

   public void a(EntityPlayer var0, Collection<Entity> var1) {
      List<LootTableInfo> var2 = Lists.newArrayList();
      Set<EntityTypes<?>> var3 = Sets.newHashSet();

      for(Entity var5 : var1) {
         var3.add(var5.ae());
         var2.add(CriterionConditionEntity.b(var0, var5));
      }

      this.a(var0, var2x -> var2x.a(var2, var3.size()));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionEntity.b[] a;
      private final CriterionConditionValue.IntegerRange b;

      public a(CriterionConditionEntity.b var0, CriterionConditionEntity.b[] var1, CriterionConditionValue.IntegerRange var2) {
         super(CriterionTriggerKilledByCrossbow.a, var0);
         this.a = var1;
         this.b = var2;
      }

      public static CriterionTriggerKilledByCrossbow.a a(CriterionConditionEntity.a... var0) {
         CriterionConditionEntity.b[] var1 = new CriterionConditionEntity.b[var0.length];

         for(int var2 = 0; var2 < var0.length; ++var2) {
            CriterionConditionEntity.a var3 = var0[var2];
            var1[var2] = CriterionConditionEntity.b.a(var3.b());
         }

         return new CriterionTriggerKilledByCrossbow.a(CriterionConditionEntity.b.a, var1, CriterionConditionValue.IntegerRange.e);
      }

      public static CriterionTriggerKilledByCrossbow.a a(CriterionConditionValue.IntegerRange var0) {
         CriterionConditionEntity.b[] var1 = new CriterionConditionEntity.b[0];
         return new CriterionTriggerKilledByCrossbow.a(CriterionConditionEntity.b.a, var1, var0);
      }

      public boolean a(Collection<LootTableInfo> var0, int var1) {
         if (this.a.length > 0) {
            List<LootTableInfo> var2 = Lists.newArrayList(var0);

            for(CriterionConditionEntity.b var6 : this.a) {
               boolean var7 = false;
               Iterator<LootTableInfo> var8 = var2.iterator();

               while(var8.hasNext()) {
                  LootTableInfo var9 = var8.next();
                  if (var6.a(var9)) {
                     var8.remove();
                     var7 = true;
                     break;
                  }
               }

               if (!var7) {
                  return false;
               }
            }
         }

         return this.b.d(var1);
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("victims", CriterionConditionEntity.b.a(this.a, var0));
         var1.add("unique_entity_types", this.b.d());
         return var1;
      }
   }
}
