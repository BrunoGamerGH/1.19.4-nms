package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootCollector;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionUser;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.apache.commons.lang3.ArrayUtils;

public abstract class LootItemFunctionConditional implements LootItemFunction {
   protected final LootItemCondition[] g;
   private final Predicate<LootTableInfo> a;

   protected LootItemFunctionConditional(LootItemCondition[] var0) {
      this.g = var0;
      this.a = LootItemConditions.a(var0);
   }

   public final ItemStack b(ItemStack var0, LootTableInfo var1) {
      return this.a.test(var1) ? this.a(var0, var1) : var0;
   }

   protected abstract ItemStack a(ItemStack var1, LootTableInfo var2);

   @Override
   public void a(LootCollector var0) {
      LootItemFunction.super.a(var0);

      for(int var1 = 0; var1 < this.g.length; ++var1) {
         this.g[var1].a(var0.b(".conditions[" + var1 + "]"));
      }
   }

   protected static LootItemFunctionConditional.a<?> a(Function<LootItemCondition[], LootItemFunction> var0) {
      return new LootItemFunctionConditional.b(var0);
   }

   public abstract static class a<T extends LootItemFunctionConditional.a<T>> implements LootItemFunction.a, LootItemConditionUser<T> {
      private final List<LootItemCondition> a = Lists.newArrayList();

      public T a(LootItemCondition.a var0) {
         this.a.add(var0.build());
         return this.c();
      }

      public final T f() {
         return this.c();
      }

      protected abstract T c();

      protected LootItemCondition[] g() {
         return this.a.toArray(new LootItemCondition[0]);
      }
   }

   static final class b extends LootItemFunctionConditional.a<LootItemFunctionConditional.b> {
      private final Function<LootItemCondition[], LootItemFunction> a;

      public b(Function<LootItemCondition[], LootItemFunction> var0) {
         this.a = var0;
      }

      protected LootItemFunctionConditional.b a() {
         return this;
      }

      @Override
      public LootItemFunction b() {
         return this.a.apply(this.g());
      }
   }

   public abstract static class c<T extends LootItemFunctionConditional> implements LootSerializer<T> {
      public void a(JsonObject var0, T var1, JsonSerializationContext var2) {
         if (!ArrayUtils.isEmpty(var1.g)) {
            var0.add("conditions", var2.serialize(var1.g));
         }
      }

      public final T b(JsonObject var0, JsonDeserializationContext var1) {
         LootItemCondition[] var2 = (LootItemCondition[])ChatDeserializer.a(var0, "conditions", new LootItemCondition[0], var1, LootItemCondition[].class);
         return this.b(var0, var1, var2);
      }

      public abstract T b(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3);
   }
}
