package net.minecraft.world.level.storage.loot.entries;

import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.world.level.storage.loot.LootTableInfo;

@FunctionalInterface
interface LootEntryChildren {
   LootEntryChildren a = (var0, var1) -> false;
   LootEntryChildren b = (var0, var1) -> true;

   boolean expand(LootTableInfo var1, Consumer<LootEntry> var2);

   default LootEntryChildren and(LootEntryChildren var0) {
      Objects.requireNonNull(var0);
      return (var1x, var2) -> this.expand(var1x, var2) && var0.expand(var1x, var2);
   }

   default LootEntryChildren or(LootEntryChildren var0) {
      Objects.requireNonNull(var0);
      return (var1x, var2) -> this.expand(var1x, var2) || var0.expand(var1x, var2);
   }
}
