package net.minecraft.world.flag;

import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.resources.MinecraftKey;
import org.slf4j.Logger;

public class FeatureFlagRegistry {
   private static final Logger a = LogUtils.getLogger();
   private final FeatureFlagUniverse b;
   private final Map<MinecraftKey, FeatureFlag> c;
   private final FeatureFlagSet d;

   FeatureFlagRegistry(FeatureFlagUniverse var0, FeatureFlagSet var1, Map<MinecraftKey, FeatureFlag> var2) {
      this.b = var0;
      this.c = var2;
      this.d = var1;
   }

   public boolean a(FeatureFlagSet var0) {
      return var0.a(this.d);
   }

   public FeatureFlagSet a() {
      return this.d;
   }

   public FeatureFlagSet a(Iterable<MinecraftKey> var0) {
      return this.a(var0, var0x -> a.warn("Unknown feature flag: {}", var0x));
   }

   public FeatureFlagSet a(FeatureFlag... var0) {
      return FeatureFlagSet.a(this.b, Arrays.asList(var0));
   }

   public FeatureFlagSet a(Iterable<MinecraftKey> var0, Consumer<MinecraftKey> var1) {
      Set<FeatureFlag> var2 = Sets.newIdentityHashSet();

      for(MinecraftKey var4 : var0) {
         FeatureFlag var5 = this.c.get(var4);
         if (var5 == null) {
            var1.accept(var4);
         } else {
            var2.add(var5);
         }
      }

      return FeatureFlagSet.a(this.b, var2);
   }

   public Set<MinecraftKey> b(FeatureFlagSet var0) {
      Set<MinecraftKey> var1 = new HashSet<>();
      this.c.forEach((var2x, var3) -> {
         if (var0.b(var3)) {
            var1.add(var2x);
         }
      });
      return var1;
   }

   public Codec<FeatureFlagSet> b() {
      return MinecraftKey.a.listOf().comapFlatMap(var0 -> {
         Set<MinecraftKey> var1 = new HashSet<>();
         FeatureFlagSet var2 = this.a(var0, var1::add);
         return !var1.isEmpty() ? DataResult.error(() -> "Unknown feature ids: " + var1, var2) : DataResult.success(var2);
      }, var0 -> List.copyOf(this.b(var0)));
   }

   public static class a {
      private final FeatureFlagUniverse a;
      private int b;
      private final Map<MinecraftKey, FeatureFlag> c = new LinkedHashMap<>();

      public a(String var0) {
         this.a = new FeatureFlagUniverse(var0);
      }

      public FeatureFlag a(String var0) {
         return this.a(new MinecraftKey("minecraft", var0));
      }

      public FeatureFlag a(MinecraftKey var0) {
         if (this.b >= 64) {
            throw new IllegalStateException("Too many feature flags");
         } else {
            FeatureFlag var1 = new FeatureFlag(this.a, this.b++);
            FeatureFlag var2 = this.c.put(var0, var1);
            if (var2 != null) {
               throw new IllegalStateException("Duplicate feature flag " + var0);
            } else {
               return var1;
            }
         }
      }

      public FeatureFlagRegistry a() {
         FeatureFlagSet var0 = FeatureFlagSet.a(this.a, this.c.values());
         return new FeatureFlagRegistry(this.a, var0, Map.copyOf(this.c));
      }
   }
}
