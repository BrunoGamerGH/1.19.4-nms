package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.resources.IResourceManager;
import net.minecraft.server.packs.resources.ResourceDataJson;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.slf4j.Logger;

public class LootTableRegistry extends ResourceDataJson {
   private static final Logger a = LogUtils.getLogger();
   private static final Gson b = LootSerialization.c().create();
   private Map<MinecraftKey, LootTable> c = ImmutableMap.of();
   public Map<LootTable, MinecraftKey> lootTableToKey = ImmutableMap.of();
   private final LootPredicateManager d;

   public LootTableRegistry(LootPredicateManager lootpredicatemanager) {
      super(b, "loot_tables");
      this.d = lootpredicatemanager;
   }

   public LootTable a(MinecraftKey minecraftkey) {
      return this.c.getOrDefault(minecraftkey, LootTable.a);
   }

   protected void a(Map<MinecraftKey, JsonElement> map, IResourceManager iresourcemanager, GameProfilerFiller gameprofilerfiller) {
      Builder<MinecraftKey, LootTable> builder = ImmutableMap.builder();
      JsonElement jsonelement = (JsonElement)map.remove(LootTables.a);
      if (jsonelement != null) {
         a.warn("Datapack tried to redefine {} loot table, ignoring", LootTables.a);
      }

      map.forEach((minecraftkey, jsonelement1) -> {
         try {
            LootTable loottable = (LootTable)b.fromJson(jsonelement1, LootTable.class);
            builder.put(minecraftkey, loottable);
         } catch (Exception var4x) {
            a.error("Couldn't parse loot table {}", minecraftkey, var4x);
         }
      });
      builder.put(LootTables.a, LootTable.a);
      ImmutableMap<MinecraftKey, LootTable> immutablemap = builder.build();
      LootContextParameterSet lootcontextparameterset = LootContextParameterSets.l;
      LootPredicateManager lootpredicatemanager = this.d;
      Function<MinecraftKey, LootItemCondition> function = lootpredicatemanager::a;
      LootCollector lootcollector = new LootCollector(lootcontextparameterset, function, immutablemap::get);
      immutablemap.forEach((minecraftkey, loottable) -> a(lootcollector, minecraftkey, loottable));
      lootcollector.a().forEach((s, s1) -> a.warn("Found validation problem in {}: {}", s, s1));
      this.c = immutablemap;
      Builder<LootTable, MinecraftKey> lootTableToKeyBuilder = ImmutableMap.builder();
      this.c.forEach((lootTable, key) -> lootTableToKeyBuilder.put(key, lootTable));
      this.lootTableToKey = lootTableToKeyBuilder.build();
   }

   public static void a(LootCollector lootcollector, MinecraftKey minecraftkey, LootTable loottable) {
      loottable.a(lootcollector.a(loottable.a()).a("{" + minecraftkey + "}", minecraftkey));
   }

   public static JsonElement a(LootTable loottable) {
      return b.toJsonTree(loottable);
   }

   public Set<MinecraftKey> a() {
      return this.c.keySet();
   }
}
