package net.minecraft.world.level.storage.loot.providers.number;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;

public final class BinomialDistributionGenerator implements NumberProvider {
   final NumberProvider a;
   final NumberProvider b;

   BinomialDistributionGenerator(NumberProvider var0, NumberProvider var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public LootNumberProviderType a() {
      return NumberProviders.c;
   }

   @Override
   public int a(LootTableInfo var0) {
      int var1 = this.a.a(var0);
      float var2 = this.b.b(var0);
      RandomSource var3 = var0.a();
      int var4 = 0;

      for(int var5 = 0; var5 < var1; ++var5) {
         if (var3.i() < var2) {
            ++var4;
         }
      }

      return var4;
   }

   @Override
   public float b(LootTableInfo var0) {
      return (float)this.a(var0);
   }

   public static BinomialDistributionGenerator a(int var0, float var1) {
      return new BinomialDistributionGenerator(ConstantValue.a((float)var0), ConstantValue.a(var1));
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return Sets.union(this.a.b(), this.b.b());
   }

   public static class a implements LootSerializer<BinomialDistributionGenerator> {
      public BinomialDistributionGenerator b(JsonObject var0, JsonDeserializationContext var1) {
         NumberProvider var2 = ChatDeserializer.a(var0, "n", var1, NumberProvider.class);
         NumberProvider var3 = ChatDeserializer.a(var0, "p", var1, NumberProvider.class);
         return new BinomialDistributionGenerator(var2, var3);
      }

      public void a(JsonObject var0, BinomialDistributionGenerator var1, JsonSerializationContext var2) {
         var0.add("n", var2.serialize(var1.a));
         var0.add("p", var2.serialize(var1.b));
      }
   }
}
