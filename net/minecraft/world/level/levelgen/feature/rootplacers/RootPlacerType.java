package net.minecraft.world.level.levelgen.feature.rootplacers;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public class RootPlacerType<P extends RootPlacer> {
   public static final RootPlacerType<MangroveRootPlacer> a = a("mangrove_root_placer", MangroveRootPlacer.c);
   private final Codec<P> b;

   private static <P extends RootPlacer> RootPlacerType<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.Y, var0, new RootPlacerType<>(var1));
   }

   private RootPlacerType(Codec<P> var0) {
      this.b = var0;
   }

   public Codec<P> a() {
      return this.b;
   }
}
