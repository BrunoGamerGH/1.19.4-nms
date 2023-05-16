package net.minecraft.world.level.storage.loot.predicates;

import java.util.function.Predicate;
import net.minecraft.world.level.storage.loot.LootItemUser;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public interface LootItemCondition extends LootItemUser, Predicate<LootTableInfo> {
   LootItemConditionType a();

   @FunctionalInterface
   public interface a {
      LootItemCondition build();

      default LootItemCondition.a invert() {
         return LootItemConditionInverted.a(this);
      }

      default LootItemConditionAlternative.a or(LootItemCondition.a var0) {
         return LootItemConditionAlternative.a(this, var0);
      }
   }
}
