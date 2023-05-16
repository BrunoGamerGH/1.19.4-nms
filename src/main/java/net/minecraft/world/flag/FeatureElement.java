package net.minecraft.world.flag;

import java.util.Set;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

public interface FeatureElement {
   Set<ResourceKey<? extends IRegistry<? extends FeatureElement>>> bv = Set.of(Registries.C, Registries.e, Registries.r, Registries.M);

   FeatureFlagSet m();

   default boolean a(FeatureFlagSet var0) {
      return this.m().a(var0);
   }
}
