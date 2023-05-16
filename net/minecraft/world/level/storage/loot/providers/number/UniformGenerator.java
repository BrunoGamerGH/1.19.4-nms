package net.minecraft.world.level.storage.loot.providers.number;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;

public class UniformGenerator implements NumberProvider {
   final NumberProvider a;
   final NumberProvider b;

   UniformGenerator(NumberProvider var0, NumberProvider var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public LootNumberProviderType a() {
      return NumberProviders.b;
   }

   public static UniformGenerator a(float var0, float var1) {
      return new UniformGenerator(ConstantValue.a(var0), ConstantValue.a(var1));
   }

   @Override
   public int a(LootTableInfo var0) {
      return MathHelper.a(var0.a(), this.a.a(var0), this.b.a(var0));
   }

   @Override
   public float b(LootTableInfo var0) {
      return MathHelper.a(var0.a(), this.a.b(var0), this.b.b(var0));
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return Sets.union(this.a.b(), this.b.b());
   }

   public static class a implements LootSerializer<UniformGenerator> {
      public UniformGenerator b(JsonObject var0, JsonDeserializationContext var1) {
         NumberProvider var2 = ChatDeserializer.a(var0, "min", var1, NumberProvider.class);
         NumberProvider var3 = ChatDeserializer.a(var0, "max", var1, NumberProvider.class);
         return new UniformGenerator(var2, var3);
      }

      public void a(JsonObject var0, UniformGenerator var1, JsonSerializationContext var2) {
         var0.add("min", var2.serialize(var1.a));
         var0.add("max", var2.serialize(var1.b));
      }
   }
}
