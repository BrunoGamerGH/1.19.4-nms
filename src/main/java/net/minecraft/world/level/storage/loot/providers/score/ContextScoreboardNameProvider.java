package net.minecraft.world.level.storage.loot.providers.score;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.JsonRegistry;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;

public class ContextScoreboardNameProvider implements ScoreboardNameProvider {
   final LootTableInfo.EntityTarget a;

   ContextScoreboardNameProvider(LootTableInfo.EntityTarget var0) {
      this.a = var0;
   }

   public static ScoreboardNameProvider a(LootTableInfo.EntityTarget var0) {
      return new ContextScoreboardNameProvider(var0);
   }

   @Override
   public LootScoreProviderType a() {
      return ScoreboardNameProviders.b;
   }

   @Nullable
   @Override
   public String a(LootTableInfo var0) {
      Entity var1 = var0.c(this.a.a());
      return var1 != null ? var1.cu() : null;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of(this.a.a());
   }

   public static class a implements JsonRegistry.b<ContextScoreboardNameProvider> {
      public JsonElement a(ContextScoreboardNameProvider var0, JsonSerializationContext var1) {
         return var1.serialize(var0.a);
      }

      public ContextScoreboardNameProvider b(JsonElement var0, JsonDeserializationContext var1) {
         LootTableInfo.EntityTarget var2 = (LootTableInfo.EntityTarget)var1.deserialize(var0, LootTableInfo.EntityTarget.class);
         return new ContextScoreboardNameProvider(var2);
      }
   }

   public static class b implements LootSerializer<ContextScoreboardNameProvider> {
      public void a(JsonObject var0, ContextScoreboardNameProvider var1, JsonSerializationContext var2) {
         var0.addProperty("target", var1.a.name());
      }

      public ContextScoreboardNameProvider b(JsonObject var0, JsonDeserializationContext var1) {
         LootTableInfo.EntityTarget var2 = ChatDeserializer.a(var0, "target", var1, LootTableInfo.EntityTarget.class);
         return new ContextScoreboardNameProvider(var2);
      }
   }
}
