package net.minecraft.world.flag;

public class FeatureFlag {
   final FeatureFlagUniverse a;
   final long b;

   FeatureFlag(FeatureFlagUniverse var0, int var1) {
      this.a = var0;
      this.b = 1L << var1;
   }
}
