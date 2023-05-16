package net.minecraft.world.level.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;

public class MultiNoiseBiomeSourceParameterLists {
   public static final ResourceKey<MultiNoiseBiomeSourceParameterList> a = a("nether");
   public static final ResourceKey<MultiNoiseBiomeSourceParameterList> b = a("overworld");

   public static void a(BootstapContext<MultiNoiseBiomeSourceParameterList> var0) {
      HolderGetter<BiomeBase> var1 = var0.a(Registries.an);
      var0.a(a, new MultiNoiseBiomeSourceParameterList(MultiNoiseBiomeSourceParameterList.a.a, var1));
      var0.a(b, new MultiNoiseBiomeSourceParameterList(MultiNoiseBiomeSourceParameterList.a.b, var1));
   }

   public static void b(BootstapContext<MultiNoiseBiomeSourceParameterList> var0) {
      HolderGetter<BiomeBase> var1 = var0.a(Registries.an);
      var0.a(b, new MultiNoiseBiomeSourceParameterList(MultiNoiseBiomeSourceParameterList.a.c, var1));
   }

   private static ResourceKey<MultiNoiseBiomeSourceParameterList> a(String var0) {
      return ResourceKey.a(Registries.aE, new MinecraftKey(var0));
   }
}
