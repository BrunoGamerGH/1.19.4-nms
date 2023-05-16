package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.resources.IResourceManager;
import net.minecraft.server.packs.resources.ResourceDataJson;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.slf4j.Logger;

public class LootPredicateManager extends ResourceDataJson {
   private static final Logger a = LogUtils.getLogger();
   private static final Gson b = LootSerialization.a().create();
   private Map<MinecraftKey, LootItemCondition> c = ImmutableMap.of();

   public LootPredicateManager() {
      super(b, "predicates");
   }

   @Nullable
   public LootItemCondition a(MinecraftKey var0) {
      return this.c.get(var0);
   }

   protected void a(Map<MinecraftKey, JsonElement> var0, IResourceManager var1, GameProfilerFiller var2) {
      Builder<MinecraftKey, LootItemCondition> var3 = ImmutableMap.builder();
      var0.forEach((var1x, var2x) -> {
         try {
            if (var2x.isJsonArray()) {
               LootItemCondition[] var3x = (LootItemCondition[])b.fromJson(var2x, LootItemCondition[].class);
               var3.put(var1x, new LootPredicateManager.a(var3x));
            } else {
               LootItemCondition var5x = (LootItemCondition)b.fromJson(var2x, LootItemCondition.class);
               var3.put(var1x, var5x);
            }
         } catch (Exception var4x) {
            a.error("Couldn't parse loot table {}", var1x, var4x);
         }
      });
      Map<MinecraftKey, LootItemCondition> var4 = var3.build();
      LootCollector var5 = new LootCollector(LootContextParameterSets.l, var4::get, var0x -> null);
      var4.forEach((var1x, var2x) -> var2x.a(var5.b("{" + var1x + "}", var1x)));
      var5.a().forEach((var0x, var1x) -> a.warn("Found validation problem in {}: {}", var0x, var1x));
      this.c = var4;
   }

   public Set<MinecraftKey> a() {
      return Collections.unmodifiableSet(this.c.keySet());
   }

   static class a implements LootItemCondition {
      private final LootItemCondition[] a;
      private final Predicate<LootTableInfo> b;

      a(LootItemCondition[] var0) {
         this.a = var0;
         this.b = LootItemConditions.a(var0);
      }

      public final boolean a(LootTableInfo var0) {
         return this.b.test(var0);
      }

      @Override
      public void a(LootCollector var0) {
         LootItemCondition.super.a(var0);

         for(int var1 = 0; var1 < this.a.length; ++var1) {
            this.a[var1].a(var0.b(".term[" + var1 + "]"));
         }
      }

      @Override
      public LootItemConditionType a() {
         throw new UnsupportedOperationException();
      }
   }
}
