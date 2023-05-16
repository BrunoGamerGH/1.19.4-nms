package net.minecraft.world.level.storage.loot.providers.number;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.providers.score.ContextScoreboardNameProvider;
import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProvider;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;

public class ScoreboardValue implements NumberProvider {
   final ScoreboardNameProvider a;
   final String b;
   final float c;

   ScoreboardValue(ScoreboardNameProvider var0, String var1, float var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   @Override
   public LootNumberProviderType a() {
      return NumberProviders.d;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return this.a.b();
   }

   public static ScoreboardValue a(LootTableInfo.EntityTarget var0, String var1) {
      return a(var0, var1, 1.0F);
   }

   public static ScoreboardValue a(LootTableInfo.EntityTarget var0, String var1, float var2) {
      return new ScoreboardValue(ContextScoreboardNameProvider.a(var0), var1, var2);
   }

   @Override
   public float b(LootTableInfo var0) {
      String var1 = this.a.a(var0);
      if (var1 == null) {
         return 0.0F;
      } else {
         Scoreboard var2 = var0.c().f();
         ScoreboardObjective var3 = var2.d(this.b);
         if (var3 == null) {
            return 0.0F;
         } else {
            return !var2.b(var1, var3) ? 0.0F : (float)var2.c(var1, var3).b() * this.c;
         }
      }
   }

   public static class a implements LootSerializer<ScoreboardValue> {
      public ScoreboardValue b(JsonObject var0, JsonDeserializationContext var1) {
         String var2 = ChatDeserializer.h(var0, "score");
         float var3 = ChatDeserializer.a(var0, "scale", 1.0F);
         ScoreboardNameProvider var4 = ChatDeserializer.a(var0, "target", var1, ScoreboardNameProvider.class);
         return new ScoreboardValue(var4, var2, var3);
      }

      public void a(JsonObject var0, ScoreboardValue var1, JsonSerializationContext var2) {
         var0.addProperty("score", var1.b);
         var0.add("target", var2.serialize(var1.a));
         var0.addProperty("scale", var1.c);
      }
   }
}
