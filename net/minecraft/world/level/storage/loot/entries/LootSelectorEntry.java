package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootCollector;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionUser;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.commons.lang3.ArrayUtils;

public abstract class LootSelectorEntry extends LootEntryAbstract {
   public static final int c = 1;
   public static final int e = 0;
   protected final int f;
   protected final int g;
   protected final LootItemFunction[] h;
   final BiFunction<ItemStack, LootTableInfo, ItemStack> i;
   private final LootEntry j = new LootSelectorEntry.c() {
      @Override
      public void a(Consumer<ItemStack> var0, LootTableInfo var1) {
         LootSelectorEntry.this.a(LootItemFunction.a(LootSelectorEntry.this.i, var0, var1), var1);
      }
   };

   protected LootSelectorEntry(int var0, int var1, LootItemCondition[] var2, LootItemFunction[] var3) {
      super(var2);
      this.f = var0;
      this.g = var1;
      this.h = var3;
      this.i = LootItemFunctions.a(var3);
   }

   @Override
   public void a(LootCollector var0) {
      super.a(var0);

      for(int var1 = 0; var1 < this.h.length; ++var1) {
         this.h[var1].a(var0.b(".functions[" + var1 + "]"));
      }
   }

   protected abstract void a(Consumer<ItemStack> var1, LootTableInfo var2);

   @Override
   public boolean expand(LootTableInfo var0, Consumer<LootEntry> var1) {
      if (this.a(var0)) {
         var1.accept(this.j);
         return true;
      } else {
         return false;
      }
   }

   public static LootSelectorEntry.a<?> a(LootSelectorEntry.d var0) {
      return new LootSelectorEntry.b(var0);
   }

   public abstract static class a<T extends LootSelectorEntry.a<T>> extends LootEntryAbstract.a<T> implements LootItemFunctionUser<T> {
      protected int a = 1;
      protected int b = 0;
      private final List<LootItemFunction> c = Lists.newArrayList();

      public T a(LootItemFunction.a var0) {
         this.c.add(var0.b());
         return this.av_();
      }

      protected LootItemFunction[] a() {
         return this.c.toArray(new LootItemFunction[0]);
      }

      public T a(int var0) {
         this.a = var0;
         return this.av_();
      }

      public T b(int var0) {
         this.b = var0;
         return this.av_();
      }
   }

   static class b extends LootSelectorEntry.a<LootSelectorEntry.b> {
      private final LootSelectorEntry.d c;

      public b(LootSelectorEntry.d var0) {
         this.c = var0;
      }

      protected LootSelectorEntry.b g() {
         return this;
      }

      @Override
      public LootEntryAbstract b() {
         return this.c.build(this.a, this.b, this.f(), this.a());
      }
   }

   protected abstract class c implements LootEntry {
      @Override
      public int a(float var0) {
         return Math.max(MathHelper.d((float)LootSelectorEntry.this.f + (float)LootSelectorEntry.this.g * var0), 0);
      }
   }

   @FunctionalInterface
   protected interface d {
      LootSelectorEntry build(int var1, int var2, LootItemCondition[] var3, LootItemFunction[] var4);
   }

   public abstract static class e<T extends LootSelectorEntry> extends LootEntryAbstract.Serializer<T> {
      public void a(JsonObject var0, T var1, JsonSerializationContext var2) {
         if (var1.f != 1) {
            var0.addProperty("weight", var1.f);
         }

         if (var1.g != 0) {
            var0.addProperty("quality", var1.g);
         }

         if (!ArrayUtils.isEmpty(var1.h)) {
            var0.add("functions", var2.serialize(var1.h));
         }
      }

      public final T a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         int var3 = ChatDeserializer.a(var0, "weight", 1);
         int var4 = ChatDeserializer.a(var0, "quality", 0);
         LootItemFunction[] var5 = (LootItemFunction[])ChatDeserializer.a(var0, "functions", new LootItemFunction[0], var1, LootItemFunction[].class);
         return this.b(var0, var1, var3, var4, var2, var5);
      }

      protected abstract T b(JsonObject var1, JsonDeserializationContext var2, int var3, int var4, LootItemCondition[] var5, LootItemFunction[] var6);
   }
}
