package net.minecraft.world.level.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;

public class BuiltinDimensionTypes {
   public static final ResourceKey<DimensionManager> a = a("overworld");
   public static final ResourceKey<DimensionManager> b = a("the_nether");
   public static final ResourceKey<DimensionManager> c = a("the_end");
   public static final ResourceKey<DimensionManager> d = a("overworld_caves");
   public static final MinecraftKey e = new MinecraftKey("overworld");
   public static final MinecraftKey f = new MinecraftKey("the_nether");
   public static final MinecraftKey g = new MinecraftKey("the_end");

   private static ResourceKey<DimensionManager> a(String var0) {
      return ResourceKey.a(Registries.as, new MinecraftKey(var0));
   }
}
