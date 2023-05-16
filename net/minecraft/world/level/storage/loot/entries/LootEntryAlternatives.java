package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import net.minecraft.world.level.storage.loot.LootCollector;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.commons.lang3.ArrayUtils;

public class LootEntryAlternatives extends LootEntryChildrenAbstract {
   LootEntryAlternatives(LootEntryAbstract[] var0, LootItemCondition[] var1) {
      super(var0, var1);
   }

   @Override
   public LootEntryType a() {
      return LootEntries.f;
   }

   @Override
   protected LootEntryChildren a(LootEntryChildren[] var0) {
      switch(var0.length) {
         case 0:
            return a;
         case 1:
            return var0[0];
         case 2:
            return var0[0].or(var0[1]);
         default:
            return (var1x, var2) -> {
               for(LootEntryChildren var6 : var0) {
                  if (var6.expand(var1x, var2)) {
                     return true;
                  }
               }

               return false;
            };
      }
   }

   @Override
   public void a(LootCollector var0) {
      super.a(var0);

      for(int var1 = 0; var1 < this.c.length - 1; ++var1) {
         if (ArrayUtils.isEmpty(this.c[var1].d)) {
            var0.a("Unreachable entry!");
         }
      }
   }

   public static LootEntryAlternatives.a a(LootEntryAbstract.a<?>... var0) {
      return new LootEntryAlternatives.a(var0);
   }

   public static <E> LootEntryAlternatives.a a(Collection<E> var0, Function<E, LootEntryAbstract.a<?>> var1) {
      return new LootEntryAlternatives.a(var0.stream().map(var1::apply).toArray(var0x -> new LootEntryAbstract.a[var0x]));
   }

   public static class a extends LootEntryAbstract.a<LootEntryAlternatives.a> {
      private final List<LootEntryAbstract> a = Lists.newArrayList();

      public a(LootEntryAbstract.a<?>... var0) {
         for(LootEntryAbstract.a<?> var4 : var0) {
            this.a.add(var4.b());
         }
      }

      protected LootEntryAlternatives.a a() {
         return this;
      }

      @Override
      public LootEntryAlternatives.a a(LootEntryAbstract.a<?> var0) {
         this.a.add(var0.b());
         return this;
      }

      @Override
      public LootEntryAbstract b() {
         return new LootEntryAlternatives(this.a.toArray(new LootEntryAbstract[0]), this.f());
      }
   }
}
