package net.minecraft.world.level.storage.loot.providers.number;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.storage.loot.JsonRegistry;
import net.minecraft.world.level.storage.loot.LootSerializer;

public class NumberProviders {
   public static final LootNumberProviderType a = a("constant", new ConstantValue.b());
   public static final LootNumberProviderType b = a("uniform", new UniformGenerator.a());
   public static final LootNumberProviderType c = a("binomial", new BinomialDistributionGenerator.a());
   public static final LootNumberProviderType d = a("score", new ScoreboardValue.a());

   private static LootNumberProviderType a(String var0, LootSerializer<? extends NumberProvider> var1) {
      return IRegistry.a(BuiltInRegistries.I, new MinecraftKey(var0), new LootNumberProviderType(var1));
   }

   public static Object a() {
      return JsonRegistry.<NumberProvider, LootNumberProviderType>a(BuiltInRegistries.I, "provider", "type", NumberProvider::a)
         .a(a, new ConstantValue.a())
         .a(b)
         .a();
   }
}
