package net.minecraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.material.FluidType;

public final class TagsFluid {
   public static final TagKey<FluidType> a = a("water");
   public static final TagKey<FluidType> b = a("lava");

   private TagsFluid() {
   }

   private static TagKey<FluidType> a(String var0) {
      return TagKey.a(Registries.v, new MinecraftKey(var0));
   }
}
