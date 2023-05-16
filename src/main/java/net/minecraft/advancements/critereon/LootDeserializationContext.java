package net.minecraft.advancements.critereon;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.storage.loot.LootCollector;
import net.minecraft.world.level.storage.loot.LootPredicateManager;
import net.minecraft.world.level.storage.loot.LootSerialization;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSet;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.slf4j.Logger;

public class LootDeserializationContext {
   private static final Logger a = LogUtils.getLogger();
   private final MinecraftKey b;
   private final LootPredicateManager c;
   private final Gson d = LootSerialization.a().create();

   public LootDeserializationContext(MinecraftKey var0, LootPredicateManager var1) {
      this.b = var0;
      this.c = var1;
   }

   public final LootItemCondition[] a(JsonArray var0, String var1, LootContextParameterSet var2) {
      LootItemCondition[] var3 = (LootItemCondition[])this.d.fromJson(var0, LootItemCondition[].class);
      LootCollector var4 = new LootCollector(var2, this.c::a, var0x -> null);

      for(LootItemCondition var8 : var3) {
         var8.a(var4);
         var4.a().forEach((var1x, var2x) -> a.warn("Found validation problem in advancement trigger {}/{}: {}", new Object[]{var1, var1x, var2x}));
      }

      return var3;
   }

   public MinecraftKey a() {
      return this.b;
   }
}
