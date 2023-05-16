package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfigurationChance;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class WorldGenCarverConfiguration extends WorldGenFeatureConfigurationChance {
   public static final MapCodec<WorldGenCarverConfiguration> d = RecordCodecBuilder.mapCodec(
      var0 -> var0.group(
               Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(var0x -> var0x.l),
               HeightProvider.c.fieldOf("y").forGetter(var0x -> var0x.e),
               FloatProvider.c.fieldOf("yScale").forGetter(var0x -> var0x.f),
               VerticalAnchor.a.fieldOf("lava_level").forGetter(var0x -> var0x.g),
               CarverDebugSettings.b.optionalFieldOf("debug_settings", CarverDebugSettings.a).forGetter(var0x -> var0x.h),
               RegistryCodecs.a(Registries.e).fieldOf("replaceable").forGetter(var0x -> var0x.i)
            )
            .apply(var0, WorldGenCarverConfiguration::new)
   );
   public final HeightProvider e;
   public final FloatProvider f;
   public final VerticalAnchor g;
   public final CarverDebugSettings h;
   public final HolderSet<Block> i;

   public WorldGenCarverConfiguration(
      float var0, HeightProvider var1, FloatProvider var2, VerticalAnchor var3, CarverDebugSettings var4, HolderSet<Block> var5
   ) {
      super(var0);
      this.e = var1;
      this.f = var2;
      this.g = var3;
      this.h = var4;
      this.i = var5;
   }
}
