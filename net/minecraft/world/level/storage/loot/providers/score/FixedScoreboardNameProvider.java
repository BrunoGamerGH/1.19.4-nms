package net.minecraft.world.level.storage.loot.providers.score;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;

public class FixedScoreboardNameProvider implements ScoreboardNameProvider {
   final String a;

   FixedScoreboardNameProvider(String var0) {
      this.a = var0;
   }

   public static ScoreboardNameProvider a(String var0) {
      return new FixedScoreboardNameProvider(var0);
   }

   @Override
   public LootScoreProviderType a() {
      return ScoreboardNameProviders.a;
   }

   public String c() {
      return this.a;
   }

   @Nullable
   @Override
   public String a(LootTableInfo var0) {
      return this.a;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of();
   }

   public static class a implements LootSerializer<FixedScoreboardNameProvider> {
      public void a(JsonObject var0, FixedScoreboardNameProvider var1, JsonSerializationContext var2) {
         var0.addProperty("name", var1.a);
      }

      public FixedScoreboardNameProvider b(JsonObject var0, JsonDeserializationContext var1) {
         String var2 = ChatDeserializer.h(var0, "name");
         return new FixedScoreboardNameProvider(var2);
      }
   }
}
