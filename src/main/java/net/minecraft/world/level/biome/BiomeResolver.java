package net.minecraft.world.level.biome;

import net.minecraft.core.Holder;

public interface BiomeResolver {
   Holder<BiomeBase> getNoiseBiome(int var1, int var2, int var3, Climate.Sampler var4);
}
