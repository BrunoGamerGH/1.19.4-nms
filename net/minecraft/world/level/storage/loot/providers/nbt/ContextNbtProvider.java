package net.minecraft.world.level.storage.loot.providers.nbt;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.CriterionConditionNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.storage.loot.JsonRegistry;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;

public class ContextNbtProvider implements NbtProvider {
   private static final String b = "block_entity";
   private static final ContextNbtProvider.a c = new ContextNbtProvider.a() {
      @Override
      public NBTBase a(LootTableInfo var0) {
         TileEntity var1 = var0.c(LootContextParameters.h);
         return var1 != null ? var1.m() : null;
      }

      @Override
      public String a() {
         return "block_entity";
      }

      @Override
      public Set<LootContextParameter<?>> b() {
         return ImmutableSet.of(LootContextParameters.h);
      }
   };
   public static final ContextNbtProvider a = new ContextNbtProvider(c);
   final ContextNbtProvider.a d;

   private static ContextNbtProvider.a b(final LootTableInfo.EntityTarget var0) {
      return new ContextNbtProvider.a() {
         @Nullable
         @Override
         public NBTBase a(LootTableInfo var0x) {
            Entity var1 = var0.c(var0.a());
            return var1 != null ? CriterionConditionNBT.b(var1) : null;
         }

         @Override
         public String a() {
            return var0.name();
         }

         @Override
         public Set<LootContextParameter<?>> b() {
            return ImmutableSet.of(var0.a());
         }
      };
   }

   private ContextNbtProvider(ContextNbtProvider.a var0) {
      this.d = var0;
   }

   @Override
   public LootNbtProviderType a() {
      return NbtProviders.b;
   }

   @Nullable
   @Override
   public NBTBase a(LootTableInfo var0) {
      return this.d.a(var0);
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return this.d.b();
   }

   public static NbtProvider a(LootTableInfo.EntityTarget var0) {
      return new ContextNbtProvider(b(var0));
   }

   static ContextNbtProvider a(String var0) {
      if (var0.equals("block_entity")) {
         return new ContextNbtProvider(c);
      } else {
         LootTableInfo.EntityTarget var1 = LootTableInfo.EntityTarget.a(var0);
         return new ContextNbtProvider(b(var1));
      }
   }

   interface a {
      @Nullable
      NBTBase a(LootTableInfo var1);

      String a();

      Set<LootContextParameter<?>> b();
   }

   public static class b implements JsonRegistry.b<ContextNbtProvider> {
      public JsonElement a(ContextNbtProvider var0, JsonSerializationContext var1) {
         return new JsonPrimitive(var0.d.a());
      }

      public ContextNbtProvider b(JsonElement var0, JsonDeserializationContext var1) {
         String var2 = var0.getAsString();
         return ContextNbtProvider.a(var2);
      }
   }

   public static class c implements LootSerializer<ContextNbtProvider> {
      public void a(JsonObject var0, ContextNbtProvider var1, JsonSerializationContext var2) {
         var0.addProperty("target", var1.d.a());
      }

      public ContextNbtProvider b(JsonObject var0, JsonDeserializationContext var1) {
         String var2 = ChatDeserializer.h(var0, "target");
         return ContextNbtProvider.a(var2);
      }
   }
}
