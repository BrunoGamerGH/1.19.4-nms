package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.resources.IResourceManager;
import net.minecraft.server.packs.resources.ResourceDataJson;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import org.slf4j.Logger;

public class ItemModifierManager extends ResourceDataJson {
   private static final Logger a = LogUtils.getLogger();
   private static final Gson b = LootSerialization.b().create();
   private final LootPredicateManager c;
   private final LootTableRegistry d;
   private Map<MinecraftKey, LootItemFunction> e = ImmutableMap.of();

   public ItemModifierManager(LootPredicateManager var0, LootTableRegistry var1) {
      super(b, "item_modifiers");
      this.c = var0;
      this.d = var1;
   }

   @Nullable
   public LootItemFunction a(MinecraftKey var0) {
      return this.e.get(var0);
   }

   public LootItemFunction a(MinecraftKey var0, LootItemFunction var1) {
      return this.e.getOrDefault(var0, var1);
   }

   protected void a(Map<MinecraftKey, JsonElement> var0, IResourceManager var1, GameProfilerFiller var2) {
      Builder<MinecraftKey, LootItemFunction> var3 = ImmutableMap.builder();
      var0.forEach((var1x, var2x) -> {
         try {
            if (var2x.isJsonArray()) {
               LootItemFunction[] var3x = (LootItemFunction[])b.fromJson(var2x, LootItemFunction[].class);
               var3.put(var1x, new ItemModifierManager.a(var3x));
            } else {
               LootItemFunction var5x = (LootItemFunction)b.fromJson(var2x, LootItemFunction.class);
               var3.put(var1x, var5x);
            }
         } catch (Exception var4x) {
            a.error("Couldn't parse item modifier {}", var1x, var4x);
         }
      });
      Map<MinecraftKey, LootItemFunction> var4 = var3.build();
      LootCollector var5 = new LootCollector(LootContextParameterSets.l, this.c::a, this.d::a);
      var4.forEach((var1x, var2x) -> var2x.a(var5));
      var5.a().forEach((var0x, var1x) -> a.warn("Found item modifier validation problem in {}: {}", var0x, var1x));
      this.e = var4;
   }

   public Set<MinecraftKey> a() {
      return Collections.unmodifiableSet(this.e.keySet());
   }

   static class a implements LootItemFunction {
      protected final LootItemFunction[] a;
      private final BiFunction<ItemStack, LootTableInfo, ItemStack> b;

      public a(LootItemFunction[] var0) {
         this.a = var0;
         this.b = LootItemFunctions.a(var0);
      }

      public ItemStack a(ItemStack var0, LootTableInfo var1) {
         return this.b.apply(var0, var1);
      }

      @Override
      public LootItemFunctionType a() {
         throw new UnsupportedOperationException();
      }
   }
}
