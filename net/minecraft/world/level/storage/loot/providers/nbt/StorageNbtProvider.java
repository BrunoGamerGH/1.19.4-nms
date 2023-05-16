package net.minecraft.world.level.storage.loot.providers.nbt;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;

public class StorageNbtProvider implements NbtProvider {
   final MinecraftKey a;

   StorageNbtProvider(MinecraftKey var0) {
      this.a = var0;
   }

   @Override
   public LootNbtProviderType a() {
      return NbtProviders.a;
   }

   @Nullable
   @Override
   public NBTBase a(LootTableInfo var0) {
      return var0.c().n().aG().a(this.a);
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of();
   }

   public static class a implements LootSerializer<StorageNbtProvider> {
      public void a(JsonObject var0, StorageNbtProvider var1, JsonSerializationContext var2) {
         var0.addProperty("source", var1.a.toString());
      }

      public StorageNbtProvider b(JsonObject var0, JsonDeserializationContext var1) {
         String var2 = ChatDeserializer.h(var0, "source");
         return new StorageNbtProvider(new MinecraftKey(var2));
      }
   }
}
