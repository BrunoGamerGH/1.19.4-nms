package org.bukkit.craftbukkit.v1_19_R3.generator;

import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.chunk.ChunkGenerator;

public abstract class InternalChunkGenerator extends ChunkGenerator {
   public InternalChunkGenerator(WorldChunkManager worldchunkmanager, Function<Holder<BiomeBase>, BiomeSettingsGeneration> function) {
      super(worldchunkmanager, function);
   }
}
