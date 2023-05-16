package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;

public class BiomeSources {
   public static Codec<? extends WorldChunkManager> a(IRegistry<Codec<? extends WorldChunkManager>> var0) {
      IRegistry.a(var0, "fixed", WorldChunkManagerHell.b);
      IRegistry.a(var0, "multi_noise", WorldChunkManagerMultiNoise.c);
      IRegistry.a(var0, "checkerboard", WorldChunkManagerCheckerBoard.b);
      return IRegistry.a(var0, "the_end", WorldChunkManagerTheEnd.b);
   }
}
