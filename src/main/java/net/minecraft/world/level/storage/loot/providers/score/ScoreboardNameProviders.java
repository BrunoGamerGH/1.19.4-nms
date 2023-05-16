package net.minecraft.world.level.storage.loot.providers.score;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.storage.loot.JsonRegistry;
import net.minecraft.world.level.storage.loot.LootSerializer;

public class ScoreboardNameProviders {
   public static final LootScoreProviderType a = a("fixed", new FixedScoreboardNameProvider.a());
   public static final LootScoreProviderType b = a("context", new ContextScoreboardNameProvider.b());

   private static LootScoreProviderType a(String var0, LootSerializer<? extends ScoreboardNameProvider> var1) {
      return IRegistry.a(BuiltInRegistries.K, new MinecraftKey(var0), new LootScoreProviderType(var1));
   }

   public static Object a() {
      return JsonRegistry.<ScoreboardNameProvider, LootScoreProviderType>a(BuiltInRegistries.K, "provider", "type", ScoreboardNameProvider::a)
         .a(b, new ContextScoreboardNameProvider.a())
         .a();
   }
}
