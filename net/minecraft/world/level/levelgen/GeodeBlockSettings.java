package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;

public class GeodeBlockSettings {
   public final WorldGenFeatureStateProvider a;
   public final WorldGenFeatureStateProvider b;
   public final WorldGenFeatureStateProvider c;
   public final WorldGenFeatureStateProvider d;
   public final WorldGenFeatureStateProvider e;
   public final List<IBlockData> f;
   public final TagKey<Block> g;
   public final TagKey<Block> h;
   public static final Codec<GeodeBlockSettings> i = RecordCodecBuilder.create(
      var0 -> var0.group(
               WorldGenFeatureStateProvider.a.fieldOf("filling_provider").forGetter(var0x -> var0x.a),
               WorldGenFeatureStateProvider.a.fieldOf("inner_layer_provider").forGetter(var0x -> var0x.b),
               WorldGenFeatureStateProvider.a.fieldOf("alternate_inner_layer_provider").forGetter(var0x -> var0x.c),
               WorldGenFeatureStateProvider.a.fieldOf("middle_layer_provider").forGetter(var0x -> var0x.d),
               WorldGenFeatureStateProvider.a.fieldOf("outer_layer_provider").forGetter(var0x -> var0x.e),
               ExtraCodecs.a(IBlockData.b.listOf()).fieldOf("inner_placements").forGetter(var0x -> var0x.f),
               TagKey.b(Registries.e).fieldOf("cannot_replace").forGetter(var0x -> var0x.g),
               TagKey.b(Registries.e).fieldOf("invalid_blocks").forGetter(var0x -> var0x.h)
            )
            .apply(var0, GeodeBlockSettings::new)
   );

   public GeodeBlockSettings(
      WorldGenFeatureStateProvider var0,
      WorldGenFeatureStateProvider var1,
      WorldGenFeatureStateProvider var2,
      WorldGenFeatureStateProvider var3,
      WorldGenFeatureStateProvider var4,
      List<IBlockData> var5,
      TagKey<Block> var6,
      TagKey<Block> var7
   ) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
      this.g = var6;
      this.h = var7;
   }
}
