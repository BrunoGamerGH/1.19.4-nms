package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.loot.LootCollector;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionUser;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.apache.commons.lang3.ArrayUtils;

public abstract class LootEntryAbstract implements LootEntryChildren {
   protected final LootItemCondition[] d;
   private final Predicate<LootTableInfo> c;

   protected LootEntryAbstract(LootItemCondition[] var0) {
      this.d = var0;
      this.c = LootItemConditions.a(var0);
   }

   public void a(LootCollector var0) {
      for(int var1 = 0; var1 < this.d.length; ++var1) {
         this.d[var1].a(var0.b(".condition[" + var1 + "]"));
      }
   }

   protected final boolean a(LootTableInfo var0) {
      return this.c.test(var0);
   }

   public abstract LootEntryType a();

   public abstract static class Serializer<T extends LootEntryAbstract> implements LootSerializer<T> {
      public final void b(JsonObject var0, T var1, JsonSerializationContext var2) {
         if (!ArrayUtils.isEmpty(var1.d)) {
            var0.add("conditions", var2.serialize(var1.d));
         }

         this.a(var0, var1, var2);
      }

      public final T b(JsonObject var0, JsonDeserializationContext var1) {
         LootItemCondition[] var2 = (LootItemCondition[])ChatDeserializer.a(var0, "conditions", new LootItemCondition[0], var1, LootItemCondition[].class);
         return this.b(var0, var1, var2);
      }

      public abstract void a(JsonObject var1, T var2, JsonSerializationContext var3);

      public abstract T b(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3);
   }

   public abstract static class a<T extends LootEntryAbstract.a<T>> implements LootItemConditionUser<T> {
      private final List<LootItemCondition> a = Lists.newArrayList();

      protected abstract T av_();

      public T a(LootItemCondition.a var0) {
         this.a.add(var0.build());
         return this.av_();
      }

      public final T e() {
         return this.av_();
      }

      protected LootItemCondition[] f() {
         return this.a.toArray(new LootItemCondition[0]);
      }

      public LootEntryAlternatives.a a(LootEntryAbstract.a<?> var0) {
         return new LootEntryAlternatives.a(this, var0);
      }

      public LootEntryGroup.a b(LootEntryAbstract.a<?> var0) {
         return new LootEntryGroup.a(this, var0);
      }

      public LootEntrySequence.a c(LootEntryAbstract.a<?> var0) {
         return new LootEntrySequence.a(this, var0);
      }

      public abstract LootEntryAbstract b();
   }
}
