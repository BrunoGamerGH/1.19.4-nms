package net.minecraft.world.level.storage.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.entries.LootEntry;
import net.minecraft.world.level.storage.loot.entries.LootEntryAbstract;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionUser;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionUser;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.mutable.MutableInt;

public class LootSelector {
   final LootEntryAbstract[] a;
   final LootItemCondition[] b;
   private final Predicate<LootTableInfo> c;
   final LootItemFunction[] d;
   private final BiFunction<ItemStack, LootTableInfo, ItemStack> e;
   final NumberProvider f;
   final NumberProvider g;

   LootSelector(LootEntryAbstract[] var0, LootItemCondition[] var1, LootItemFunction[] var2, NumberProvider var3, NumberProvider var4) {
      this.a = var0;
      this.b = var1;
      this.c = LootItemConditions.a(var1);
      this.d = var2;
      this.e = LootItemFunctions.a(var2);
      this.f = var3;
      this.g = var4;
   }

   private void b(Consumer<ItemStack> var0, LootTableInfo var1) {
      RandomSource var2 = var1.a();
      List<LootEntry> var3 = Lists.newArrayList();
      MutableInt var4 = new MutableInt();

      for(LootEntryAbstract var8 : this.a) {
         var8.expand(var1, var3x -> {
            int var4x = var3x.a(var1.b());
            if (var4x > 0) {
               var3.add(var3x);
               var4.add(var4x);
            }
         });
      }

      int var5 = var3.size();
      if (var4.intValue() != 0 && var5 != 0) {
         if (var5 == 1) {
            var3.get(0).a(var0, var1);
         } else {
            int var6 = var2.a(var4.intValue());

            for(LootEntry var8 : var3) {
               var6 -= var8.a(var1.b());
               if (var6 < 0) {
                  var8.a(var0, var1);
                  return;
               }
            }
         }
      }
   }

   public void a(Consumer<ItemStack> var0, LootTableInfo var1) {
      if (this.c.test(var1)) {
         Consumer<ItemStack> var2 = LootItemFunction.a(this.e, var0, var1);
         int var3 = this.f.a(var1) + MathHelper.d(this.g.b(var1) * var1.b());

         for(int var4 = 0; var4 < var3; ++var4) {
            this.b(var2, var1);
         }
      }
   }

   public void a(LootCollector var0) {
      for(int var1 = 0; var1 < this.b.length; ++var1) {
         this.b[var1].a(var0.b(".condition[" + var1 + "]"));
      }

      for(int var1 = 0; var1 < this.d.length; ++var1) {
         this.d[var1].a(var0.b(".functions[" + var1 + "]"));
      }

      for(int var1 = 0; var1 < this.a.length; ++var1) {
         this.a[var1].a(var0.b(".entries[" + var1 + "]"));
      }

      this.f.a(var0.b(".rolls"));
      this.g.a(var0.b(".bonusRolls"));
   }

   public static LootSelector.a a() {
      return new LootSelector.a();
   }

   public static class a implements LootItemFunctionUser<LootSelector.a>, LootItemConditionUser<LootSelector.a> {
      private final List<LootEntryAbstract> a = Lists.newArrayList();
      private final List<LootItemCondition> b = Lists.newArrayList();
      private final List<LootItemFunction> c = Lists.newArrayList();
      private NumberProvider d = ConstantValue.a(1.0F);
      private NumberProvider e = ConstantValue.a(0.0F);

      public LootSelector.a a(NumberProvider var0) {
         this.d = var0;
         return this;
      }

      public LootSelector.a a() {
         return this;
      }

      public LootSelector.a b(NumberProvider var0) {
         this.e = var0;
         return this;
      }

      public LootSelector.a a(LootEntryAbstract.a<?> var0) {
         this.a.add(var0.b());
         return this;
      }

      public LootSelector.a a(LootItemCondition.a var0) {
         this.b.add(var0.build());
         return this;
      }

      public LootSelector.a a(LootItemFunction.a var0) {
         this.c.add(var0.b());
         return this;
      }

      public LootSelector b() {
         if (this.d == null) {
            throw new IllegalArgumentException("Rolls not set");
         } else {
            return new LootSelector(
               this.a.toArray(new LootEntryAbstract[0]), this.b.toArray(new LootItemCondition[0]), this.c.toArray(new LootItemFunction[0]), this.d, this.e
            );
         }
      }
   }

   public static class b implements JsonDeserializer<LootSelector>, JsonSerializer<LootSelector> {
      public LootSelector a(JsonElement var0, Type var1, JsonDeserializationContext var2) throws JsonParseException {
         JsonObject var3 = ChatDeserializer.m(var0, "loot pool");
         LootEntryAbstract[] var4 = (LootEntryAbstract[])ChatDeserializer.a(var3, "entries", var2, LootEntryAbstract[].class);
         LootItemCondition[] var5 = (LootItemCondition[])ChatDeserializer.a(var3, "conditions", new LootItemCondition[0], var2, LootItemCondition[].class);
         LootItemFunction[] var6 = (LootItemFunction[])ChatDeserializer.a(var3, "functions", new LootItemFunction[0], var2, LootItemFunction[].class);
         NumberProvider var7 = ChatDeserializer.a(var3, "rolls", var2, NumberProvider.class);
         NumberProvider var8 = ChatDeserializer.a(var3, "bonus_rolls", ConstantValue.a(0.0F), var2, NumberProvider.class);
         return new LootSelector(var4, var5, var6, var7, var8);
      }

      public JsonElement a(LootSelector var0, Type var1, JsonSerializationContext var2) {
         JsonObject var3 = new JsonObject();
         var3.add("rolls", var2.serialize(var0.f));
         var3.add("bonus_rolls", var2.serialize(var0.g));
         var3.add("entries", var2.serialize(var0.a));
         if (!ArrayUtils.isEmpty(var0.b)) {
            var3.add("conditions", var2.serialize(var0.b));
         }

         if (!ArrayUtils.isEmpty(var0.d)) {
            var3.add("functions", var2.serialize(var0.d));
         }

         return var3;
      }
   }
}
