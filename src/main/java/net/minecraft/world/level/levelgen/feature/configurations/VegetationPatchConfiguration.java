package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class VegetationPatchConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<VegetationPatchConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               TagKey.b(Registries.e).fieldOf("replaceable").forGetter(var0x -> var0x.b),
               WorldGenFeatureStateProvider.a.fieldOf("ground_state").forGetter(var0x -> var0x.c),
               PlacedFeature.b.fieldOf("vegetation_feature").forGetter(var0x -> var0x.d),
               CaveSurface.c.fieldOf("surface").forGetter(var0x -> var0x.e),
               IntProvider.b(1, 128).fieldOf("depth").forGetter(var0x -> var0x.f),
               Codec.floatRange(0.0F, 1.0F).fieldOf("extra_bottom_block_chance").forGetter(var0x -> var0x.g),
               Codec.intRange(1, 256).fieldOf("vertical_range").forGetter(var0x -> var0x.h),
               Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(var0x -> var0x.i),
               IntProvider.c.fieldOf("xz_radius").forGetter(var0x -> var0x.j),
               Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter(var0x -> var0x.k)
            )
            .apply(var0, VegetationPatchConfiguration::new)
   );
   public final TagKey<Block> b;
   public final WorldGenFeatureStateProvider c;
   public final Holder<PlacedFeature> d;
   public final CaveSurface e;
   public final IntProvider f;
   public final float g;
   public final int h;
   public final float i;
   public final IntProvider j;
   public final float k;

   public VegetationPatchConfiguration(
      TagKey<Block> var0,
      WorldGenFeatureStateProvider var1,
      Holder<PlacedFeature> var2,
      CaveSurface var3,
      IntProvider var4,
      float var5,
      int var6,
      float var7,
      IntProvider var8,
      float var9
   ) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
      this.h = var6;
      this.i = var7;
      this.j = var8;
      this.k = var9;
   }
}
