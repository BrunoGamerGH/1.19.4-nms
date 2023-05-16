package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootEntrySequence extends LootEntryChildrenAbstract {
   LootEntrySequence(LootEntryAbstract[] var0, LootItemCondition[] var1) {
      super(var0, var1);
   }

   @Override
   public LootEntryType a() {
      return LootEntries.g;
   }

   @Override
   protected LootEntryChildren a(LootEntryChildren[] var0) {
      switch(var0.length) {
         case 0:
            return b;
         case 1:
            return var0[0];
         case 2:
            return var0[0].and(var0[1]);
         default:
            return (var1x, var2) -> {
               for(LootEntryChildren var6 : var0) {
                  if (!var6.expand(var1x, var2)) {
                     return false;
                  }
               }

               return true;
            };
      }
   }

   public static LootEntrySequence.a a(LootEntryAbstract.a<?>... var0) {
      return new LootEntrySequence.a(var0);
   }

   public static class a extends LootEntryAbstract.a<LootEntrySequence.a> {
      private final List<LootEntryAbstract> a = Lists.newArrayList();

      public a(LootEntryAbstract.a<?>... var0) {
         for(LootEntryAbstract.a<?> var4 : var0) {
            this.a.add(var4.b());
         }
      }

      protected LootEntrySequence.a a() {
         return this;
      }

      @Override
      public LootEntrySequence.a c(LootEntryAbstract.a<?> var0) {
         this.a.add(var0.b());
         return this;
      }

      @Override
      public LootEntryAbstract b() {
         return new LootEntrySequence(this.a.toArray(new LootEntryAbstract[0]), this.f());
      }
   }
}
