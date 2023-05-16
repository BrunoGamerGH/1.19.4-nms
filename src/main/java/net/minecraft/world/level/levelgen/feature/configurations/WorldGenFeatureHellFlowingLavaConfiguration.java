package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class WorldGenFeatureHellFlowingLavaConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureHellFlowingLavaConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Fluid.a.fieldOf("state").forGetter(var0x -> var0x.b),
               Codec.BOOL.fieldOf("requires_block_below").orElse(true).forGetter(var0x -> var0x.c),
               Codec.INT.fieldOf("rock_count").orElse(4).forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("hole_count").orElse(1).forGetter(var0x -> var0x.e),
               RegistryCodecs.a(Registries.e).fieldOf("valid_blocks").forGetter(var0x -> var0x.f)
            )
            .apply(var0, WorldGenFeatureHellFlowingLavaConfiguration::new)
   );
   public final Fluid b;
   public final boolean c;
   public final int d;
   public final int e;
   public final HolderSet<Block> f;

   public WorldGenFeatureHellFlowingLavaConfiguration(Fluid var0, boolean var1, int var2, int var3, HolderSet<Block> var4) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
   }
}
