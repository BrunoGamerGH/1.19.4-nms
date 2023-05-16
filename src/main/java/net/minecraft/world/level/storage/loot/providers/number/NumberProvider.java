package net.minecraft.world.level.storage.loot.providers.number;

import net.minecraft.world.level.storage.loot.LootItemUser;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public interface NumberProvider extends LootItemUser {
   float b(LootTableInfo var1);

   default int a(LootTableInfo var0) {
      return Math.round(this.b(var0));
   }

   LootNumberProviderType a();
}
