package net.minecraft.world.level.chunk;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.world.level.levelgen.ChunkGeneratorAbstract;
import net.minecraft.world.level.levelgen.ChunkProviderDebug;
import net.minecraft.world.level.levelgen.ChunkProviderFlat;

public class ChunkGenerators {
   public static Codec<? extends ChunkGenerator> a(IRegistry<Codec<? extends ChunkGenerator>> var0) {
      IRegistry.a(var0, "noise", ChunkGeneratorAbstract.c);
      IRegistry.a(var0, "flat", ChunkProviderFlat.c);
      return IRegistry.a(var0, "debug", ChunkProviderDebug.c);
   }
}
