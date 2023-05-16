package net.minecraft.world.level.storage.loot.providers.nbt;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.storage.loot.JsonRegistry;
import net.minecraft.world.level.storage.loot.LootSerializer;

public class NbtProviders {
   public static final LootNbtProviderType a = a("storage", new StorageNbtProvider.a());
   public static final LootNbtProviderType b = a("context", new ContextNbtProvider.c());

   private static LootNbtProviderType a(String var0, LootSerializer<? extends NbtProvider> var1) {
      return IRegistry.a(BuiltInRegistries.J, new MinecraftKey(var0), new LootNbtProviderType(var1));
   }

   public static Object a() {
      return JsonRegistry.<NbtProvider, LootNbtProviderType>a(BuiltInRegistries.J, "provider", "type", NbtProvider::a).a(b, new ContextNbtProvider.b()).a();
   }
}
