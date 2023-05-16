package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;

public class LootItemConditionEntityScore implements LootItemCondition {
   final Map<String, IntRange> a;
   final LootTableInfo.EntityTarget b;

   LootItemConditionEntityScore(Map<String, IntRange> var0, LootTableInfo.EntityTarget var1) {
      this.a = ImmutableMap.copyOf(var0);
      this.b = var1;
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.g;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return Stream.concat(Stream.of(this.b.a()), this.a.values().stream().flatMap(var0 -> var0.a().stream())).collect(ImmutableSet.toImmutableSet());
   }

   public boolean a(LootTableInfo var0) {
      Entity var1 = var0.c(this.b.a());
      if (var1 == null) {
         return false;
      } else {
         Scoreboard var2 = var1.H.H();

         for(Entry<String, IntRange> var4 : this.a.entrySet()) {
            if (!this.a(var0, var1, var2, var4.getKey(), var4.getValue())) {
               return false;
            }
         }

         return true;
      }
   }

   protected boolean a(LootTableInfo var0, Entity var1, Scoreboard var2, String var3, IntRange var4) {
      ScoreboardObjective var5 = var2.d(var3);
      if (var5 == null) {
         return false;
      } else {
         String var6 = var1.cu();
         return !var2.b(var6, var5) ? false : var4.b(var0, var2.c(var6, var5).b());
      }
   }

   public static LootItemConditionEntityScore.a a(LootTableInfo.EntityTarget var0) {
      return new LootItemConditionEntityScore.a(var0);
   }

   public static class a implements LootItemCondition.a {
      private final Map<String, IntRange> a = Maps.newHashMap();
      private final LootTableInfo.EntityTarget b;

      public a(LootTableInfo.EntityTarget var0) {
         this.b = var0;
      }

      public LootItemConditionEntityScore.a a(String var0, IntRange var1) {
         this.a.put(var0, var1);
         return this;
      }

      @Override
      public LootItemCondition build() {
         return new LootItemConditionEntityScore(this.a, this.b);
      }
   }

   public static class b implements LootSerializer<LootItemConditionEntityScore> {
      public void a(JsonObject var0, LootItemConditionEntityScore var1, JsonSerializationContext var2) {
         JsonObject var3 = new JsonObject();

         for(Entry<String, IntRange> var5 : var1.a.entrySet()) {
            var3.add(var5.getKey(), var2.serialize(var5.getValue()));
         }

         var0.add("scores", var3);
         var0.add("entity", var2.serialize(var1.b));
      }

      public LootItemConditionEntityScore b(JsonObject var0, JsonDeserializationContext var1) {
         Set<Entry<String, JsonElement>> var2 = ChatDeserializer.t(var0, "scores").entrySet();
         Map<String, IntRange> var3 = Maps.newLinkedHashMap();

         for(Entry<String, JsonElement> var5 : var2) {
            var3.put(var5.getKey(), ChatDeserializer.a((JsonElement)var5.getValue(), "score", var1, IntRange.class));
         }

         return new LootItemConditionEntityScore(var3, ChatDeserializer.a(var0, "entity", var1, LootTableInfo.EntityTarget.class));
      }
   }
}
