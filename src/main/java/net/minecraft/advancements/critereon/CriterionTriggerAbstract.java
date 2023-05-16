package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.server.AdvancementDataPlayer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public abstract class CriterionTriggerAbstract<T extends CriterionInstanceAbstract> implements CriterionTrigger<T> {
   private final Map<AdvancementDataPlayer, Set<CriterionTrigger.a<T>>> a = Maps.newIdentityHashMap();

   @Override
   public final void a(AdvancementDataPlayer var0, CriterionTrigger.a<T> var1) {
      this.a.computeIfAbsent(var0, var0x -> Sets.newHashSet()).add(var1);
   }

   @Override
   public final void b(AdvancementDataPlayer var0, CriterionTrigger.a<T> var1) {
      Set<CriterionTrigger.a<T>> var2 = this.a.get(var0);
      if (var2 != null) {
         var2.remove(var1);
         if (var2.isEmpty()) {
            this.a.remove(var0);
         }
      }
   }

   @Override
   public final void a(AdvancementDataPlayer var0) {
      this.a.remove(var0);
   }

   protected abstract T b(JsonObject var1, CriterionConditionEntity.b var2, LootDeserializationContext var3);

   public final T b(JsonObject var0, LootDeserializationContext var1) {
      CriterionConditionEntity.b var2 = CriterionConditionEntity.b.a(var0, "player", var1);
      return this.b(var0, var2, var1);
   }

   protected void a(EntityPlayer var0, Predicate<T> var1) {
      AdvancementDataPlayer var2 = var0.M();
      Set<CriterionTrigger.a<T>> var3 = this.a.get(var2);
      if (var3 != null && !var3.isEmpty()) {
         LootTableInfo var4 = CriterionConditionEntity.b(var0, var0);
         List<CriterionTrigger.a<T>> var5 = null;

         for(CriterionTrigger.a<T> var7 : var3) {
            T var8 = var7.a();
            if (var1.test(var8) && var8.b().a(var4)) {
               if (var5 == null) {
                  var5 = Lists.newArrayList();
               }

               var5.add(var7);
            }
         }

         if (var5 != null) {
            for(CriterionTrigger.a<T> var7 : var5) {
               var7.a(var2);
            }
         }
      }
   }
}
