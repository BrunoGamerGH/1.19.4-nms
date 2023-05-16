package net.minecraft.world.level.storage.loot.entries;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.storage.loot.JsonRegistry;
import net.minecraft.world.level.storage.loot.LootSerializer;

public class LootEntries {
   public static final LootEntryType a = a("empty", new LootSelectorEmpty.a());
   public static final LootEntryType b = a("item", new LootItem.a());
   public static final LootEntryType c = a("loot_table", new LootSelectorLootTable.a());
   public static final LootEntryType d = a("dynamic", new LootSelectorDynamic.a());
   public static final LootEntryType e = a("tag", new LootSelectorTag.a());
   public static final LootEntryType f = a("alternatives", LootEntryChildrenAbstract.a(LootEntryAlternatives::new));
   public static final LootEntryType g = a("sequence", LootEntryChildrenAbstract.a(LootEntrySequence::new));
   public static final LootEntryType h = a("group", LootEntryChildrenAbstract.a(LootEntryGroup::new));

   private static LootEntryType a(String var0, LootSerializer<? extends LootEntryAbstract> var1) {
      return IRegistry.a(BuiltInRegistries.F, new MinecraftKey(var0), new LootEntryType(var1));
   }

   public static Object a() {
      return JsonRegistry.a(BuiltInRegistries.F, "entry", "type", LootEntryAbstract::a).a();
   }
}
