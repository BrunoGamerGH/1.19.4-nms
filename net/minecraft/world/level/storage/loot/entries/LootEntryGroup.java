package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootEntryGroup extends LootEntryChildrenAbstract {
   LootEntryGroup(LootEntryAbstract[] var0, LootItemCondition[] var1) {
      super(var0, var1);
   }

   @Override
   public LootEntryType a() {
      return LootEntries.h;
   }

   @Override
   protected LootEntryChildren a(LootEntryChildren[] var0) {
      switch(var0.length) {
         case 0:
            return b;
         case 1:
            return var0[0];
         case 2:
            LootEntryChildren var1 = var0[0];
            LootEntryChildren var2 = var0[1];
            return (var2x, var3x) -> {
               var1.expand(var2x, var3x);
               var2.expand(var2x, var3x);
               return true;
            };
         default:
            return (var1x, var2x) -> {
               for(LootEntryChildren var6 : var0) {
                  var6.expand(var1x, var2x);
               }

               return true;
            };
      }
   }

   public static LootEntryGroup.a a(LootEntryAbstract.a<?>... var0) {
      return new LootEntryGroup.a(var0);
   }

   public static class a extends LootEntryAbstract.a<LootEntryGroup.a> {
      private final List<LootEntryAbstract> a = Lists.newArrayList();

      public a(LootEntryAbstract.a<?>... var0) {
         for(LootEntryAbstract.a<?> var4 : var0) {
            this.a.add(var4.b());
         }
      }

      protected LootEntryGroup.a a() {
         return this;
      }

      @Override
      public LootEntryGroup.a b(LootEntryAbstract.a<?> var0) {
         this.a.add(var0.b());
         return this;
      }

      @Override
      public LootEntryAbstract b() {
         return new LootEntryGroup(this.a.toArray(new LootEntryAbstract[0]), this.f());
      }
   }
}
