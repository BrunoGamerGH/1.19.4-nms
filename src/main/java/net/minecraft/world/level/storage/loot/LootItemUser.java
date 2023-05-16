package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;

public interface LootItemUser {
   default Set<LootContextParameter<?>> b() {
      return ImmutableSet.of();
   }

   default void a(LootCollector var0) {
      var0.a(this);
   }
}
