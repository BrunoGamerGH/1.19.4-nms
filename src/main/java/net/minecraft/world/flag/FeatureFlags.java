package net.minecraft.world.flag;

import com.mojang.serialization.Codec;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.resources.MinecraftKey;

public class FeatureFlags {
   public static final FeatureFlag a;
   public static final FeatureFlag b;
   public static final FeatureFlag c;
   public static final FeatureFlagRegistry d;
   public static final Codec<FeatureFlagSet> e = d.b();
   public static final FeatureFlagSet f = FeatureFlagSet.a(a);
   public static final FeatureFlagSet g = f;

   public static String a(FeatureFlagSet var0, FeatureFlagSet var1) {
      return a(d, var0, var1);
   }

   public static String a(FeatureFlagRegistry var0, FeatureFlagSet var1, FeatureFlagSet var2) {
      Set<MinecraftKey> var3 = var0.b(var2);
      Set<MinecraftKey> var4 = var0.b(var1);
      return var3.stream().filter(var1x -> !var4.contains(var1x)).map(MinecraftKey::toString).collect(Collectors.joining(", "));
   }

   public static boolean a(FeatureFlagSet var0) {
      return !var0.a(f);
   }

   static {
      FeatureFlagRegistry.a var0 = new FeatureFlagRegistry.a("main");
      a = var0.a("vanilla");
      b = var0.a("bundle");
      c = var0.a("update_1_20");
      d = var0.a();
   }
}
