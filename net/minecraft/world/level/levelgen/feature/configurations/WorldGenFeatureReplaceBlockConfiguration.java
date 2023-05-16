package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureTestBlockState;

public class WorldGenFeatureReplaceBlockConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureReplaceBlockConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(Codec.list(WorldGenFeatureOreConfiguration.a.a).fieldOf("targets").forGetter(var0x -> var0x.b))
            .apply(var0, WorldGenFeatureReplaceBlockConfiguration::new)
   );
   public final List<WorldGenFeatureOreConfiguration.a> b;

   public WorldGenFeatureReplaceBlockConfiguration(IBlockData var0, IBlockData var1) {
      this(ImmutableList.of(WorldGenFeatureOreConfiguration.a(new DefinedStructureTestBlockState(var0), var1)));
   }

   public WorldGenFeatureReplaceBlockConfiguration(List<WorldGenFeatureOreConfiguration.a> var0) {
      this.b = var0;
   }
}
