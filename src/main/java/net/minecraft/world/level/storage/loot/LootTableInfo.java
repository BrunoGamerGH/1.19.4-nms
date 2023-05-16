package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootTableInfo {
   private final RandomSource a;
   private final float b;
   private final WorldServer c;
   private final Function<MinecraftKey, LootTable> d;
   private final Set<LootTable> e = Sets.newLinkedHashSet();
   private final Function<MinecraftKey, LootItemCondition> f;
   private final Set<LootItemCondition> g = Sets.newLinkedHashSet();
   private final Map<LootContextParameter<?>, Object> h;
   private final Map<MinecraftKey, LootTableInfo.b> i;

   LootTableInfo(
      RandomSource var0,
      float var1,
      WorldServer var2,
      Function<MinecraftKey, LootTable> var3,
      Function<MinecraftKey, LootItemCondition> var4,
      Map<LootContextParameter<?>, Object> var5,
      Map<MinecraftKey, LootTableInfo.b> var6
   ) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.f = var4;
      this.h = ImmutableMap.copyOf(var5);
      this.i = ImmutableMap.copyOf(var6);
   }

   public boolean a(LootContextParameter<?> var0) {
      return this.h.containsKey(var0);
   }

   public <T> T b(LootContextParameter<T> var0) {
      T var1 = (T)this.h.get(var0);
      if (var1 == null) {
         throw new NoSuchElementException(var0.a().toString());
      } else {
         return var1;
      }
   }

   public void a(MinecraftKey var0, Consumer<ItemStack> var1) {
      LootTableInfo.b var2 = this.i.get(var0);
      if (var2 != null) {
         var2.add(this, var1);
      }
   }

   @Nullable
   public <T> T c(LootContextParameter<T> var0) {
      return (T)this.h.get(var0);
   }

   public boolean a(LootTable var0) {
      return this.e.add(var0);
   }

   public void b(LootTable var0) {
      this.e.remove(var0);
   }

   public boolean a(LootItemCondition var0) {
      return this.g.add(var0);
   }

   public void b(LootItemCondition var0) {
      this.g.remove(var0);
   }

   public LootTable a(MinecraftKey var0) {
      return this.d.apply(var0);
   }

   @Nullable
   public LootItemCondition b(MinecraftKey var0) {
      return this.f.apply(var0);
   }

   public RandomSource a() {
      return this.a;
   }

   public float b() {
      return this.b;
   }

   public WorldServer c() {
      return this.c;
   }

   public static class Builder {
      private final WorldServer a;
      private final Map<LootContextParameter<?>, Object> b = Maps.newIdentityHashMap();
      private final Map<MinecraftKey, LootTableInfo.b> c = Maps.newHashMap();
      private RandomSource d;
      private float e;

      public Builder(WorldServer var0) {
         this.a = var0;
      }

      public LootTableInfo.Builder a(RandomSource var0) {
         this.d = var0;
         return this;
      }

      public LootTableInfo.Builder a(long var0) {
         if (var0 != 0L) {
            this.d = RandomSource.a(var0);
         }

         return this;
      }

      public LootTableInfo.Builder a(long var0, RandomSource var2) {
         if (var0 == 0L) {
            this.d = var2;
         } else {
            this.d = RandomSource.a(var0);
         }

         return this;
      }

      public LootTableInfo.Builder a(float var0) {
         this.e = var0;
         return this;
      }

      public <T> LootTableInfo.Builder a(LootContextParameter<T> var0, T var1) {
         this.b.put(var0, var1);
         return this;
      }

      public <T> LootTableInfo.Builder b(LootContextParameter<T> var0, @Nullable T var1) {
         if (var1 == null) {
            this.b.remove(var0);
         } else {
            this.b.put(var0, var1);
         }

         return this;
      }

      public LootTableInfo.Builder a(MinecraftKey var0, LootTableInfo.b var1) {
         LootTableInfo.b var2 = this.c.put(var0, var1);
         if (var2 != null) {
            throw new IllegalStateException("Duplicated dynamic drop '" + this.c + "'");
         } else {
            return this;
         }
      }

      public WorldServer a() {
         return this.a;
      }

      public <T> T a(LootContextParameter<T> var0) {
         T var1 = (T)this.b.get(var0);
         if (var1 == null) {
            throw new IllegalArgumentException("No parameter " + var0);
         } else {
            return var1;
         }
      }

      @Nullable
      public <T> T b(LootContextParameter<T> var0) {
         return (T)this.b.get(var0);
      }

      public LootTableInfo a(LootContextParameterSet var0) {
         Set<LootContextParameter<?>> var1 = Sets.difference(this.b.keySet(), var0.b());
         if (!var1.isEmpty()) {
            throw new IllegalArgumentException("Parameters not allowed in this parameter set: " + var1);
         } else {
            Set<LootContextParameter<?>> var2 = Sets.difference(var0.a(), this.b.keySet());
            if (!var2.isEmpty()) {
               throw new IllegalArgumentException("Missing required parameters: " + var2);
            } else {
               RandomSource var3 = this.d;
               if (var3 == null) {
                  var3 = RandomSource.a();
               }

               MinecraftServer var4 = this.a.n();
               return new LootTableInfo(var3, this.e, this.a, var4.aH()::a, var4.aI()::a, this.b, this.c);
            }
         }
      }
   }

   public static enum EntityTarget {
      a("this", LootContextParameters.a),
      b("killer", LootContextParameters.d),
      c("direct_killer", LootContextParameters.e),
      d("killer_player", LootContextParameters.b);

      final String e;
      private final LootContextParameter<? extends Entity> f;

      private EntityTarget(String var2, LootContextParameter var3) {
         this.e = var2;
         this.f = var3;
      }

      public LootContextParameter<? extends Entity> a() {
         return this.f;
      }

      public static LootTableInfo.EntityTarget a(String var0) {
         for(LootTableInfo.EntityTarget var4 : values()) {
            if (var4.e.equals(var0)) {
               return var4;
            }
         }

         throw new IllegalArgumentException("Invalid entity target " + var0);
      }

      public static class a extends TypeAdapter<LootTableInfo.EntityTarget> {
         public void a(JsonWriter var0, LootTableInfo.EntityTarget var1) throws IOException {
            var0.value(var1.e);
         }

         public LootTableInfo.EntityTarget a(JsonReader var0) throws IOException {
            return LootTableInfo.EntityTarget.a(var0.nextString());
         }
      }
   }

   @FunctionalInterface
   public interface b {
      void add(LootTableInfo var1, Consumer<ItemStack> var2);
   }
}
