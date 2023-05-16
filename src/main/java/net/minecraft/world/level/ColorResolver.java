package net.minecraft.world.level;

import net.minecraft.world.level.biome.BiomeBase;

@FunctionalInterface
public interface ColorResolver {
   int getColor(BiomeBase var1, double var2, double var4);
}
