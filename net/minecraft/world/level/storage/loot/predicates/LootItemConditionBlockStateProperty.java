package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Set;
import net.minecraft.advancements.critereon.CriterionTriggerProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;

public class LootItemConditionBlockStateProperty implements LootItemCondition {
   final Block a;
   final CriterionTriggerProperties b;

   LootItemConditionBlockStateProperty(Block var0, CriterionTriggerProperties var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.h;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of(LootContextParameters.g);
   }

   public boolean a(LootTableInfo var0) {
      IBlockData var1 = var0.c(LootContextParameters.g);
      return var1 != null && var1.a(this.a) && this.b.a(var1);
   }

   public static LootItemConditionBlockStateProperty.a a(Block var0) {
      return new LootItemConditionBlockStateProperty.a(var0);
   }

   public static class a implements LootItemCondition.a {
      private final Block a;
      private CriterionTriggerProperties b = CriterionTriggerProperties.a;

      public a(Block var0) {
         this.a = var0;
      }

      public LootItemConditionBlockStateProperty.a a(CriterionTriggerProperties.a var0) {
         this.b = var0.b();
         return this;
      }

      @Override
      public LootItemCondition build() {
         return new LootItemConditionBlockStateProperty(this.a, this.b);
      }
   }

   public static class b implements LootSerializer<LootItemConditionBlockStateProperty> {
      public void a(JsonObject var0, LootItemConditionBlockStateProperty var1, JsonSerializationContext var2) {
         var0.addProperty("block", BuiltInRegistries.f.b(var1.a).toString());
         var0.add("properties", var1.b.a());
      }

      public LootItemConditionBlockStateProperty b(JsonObject var0, JsonDeserializationContext var1) {
         MinecraftKey var2 = new MinecraftKey(ChatDeserializer.h(var0, "block"));
         Block var3 = BuiltInRegistries.f.b(var2).orElseThrow(() -> new IllegalArgumentException("Can't find block " + var2));
         CriterionTriggerProperties var4 = CriterionTriggerProperties.a(var0.get("properties"));
         var4.a(var3.n(), var1x -> {
            throw new JsonSyntaxException("Block " + var3 + " has no property " + var1x);
         });
         return new LootItemConditionBlockStateProperty(var3, var4);
      }
   }
}
