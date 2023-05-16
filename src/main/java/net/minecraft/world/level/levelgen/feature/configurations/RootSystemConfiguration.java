package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class RootSystemConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<RootSystemConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               PlacedFeature.b.fieldOf("feature").forGetter(var0x -> var0x.b),
               Codec.intRange(1, 64).fieldOf("required_vertical_space_for_tree").forGetter(var0x -> var0x.c),
               Codec.intRange(1, 64).fieldOf("root_radius").forGetter(var0x -> var0x.d),
               TagKey.b(Registries.e).fieldOf("root_replaceable").forGetter(var0x -> var0x.e),
               WorldGenFeatureStateProvider.a.fieldOf("root_state_provider").forGetter(var0x -> var0x.f),
               Codec.intRange(1, 256).fieldOf("root_placement_attempts").forGetter(var0x -> var0x.g),
               Codec.intRange(1, 4096).fieldOf("root_column_max_height").forGetter(var0x -> var0x.h),
               Codec.intRange(1, 64).fieldOf("hanging_root_radius").forGetter(var0x -> var0x.i),
               Codec.intRange(0, 16).fieldOf("hanging_roots_vertical_span").forGetter(var0x -> var0x.j),
               WorldGenFeatureStateProvider.a.fieldOf("hanging_root_state_provider").forGetter(var0x -> var0x.k),
               Codec.intRange(1, 256).fieldOf("hanging_root_placement_attempts").forGetter(var0x -> var0x.l),
               Codec.intRange(1, 64).fieldOf("allowed_vertical_water_for_tree").forGetter(var0x -> var0x.n),
               BlockPredicate.b.fieldOf("allowed_tree_position").forGetter(var0x -> var0x.o)
            )
            .apply(var0, RootSystemConfiguration::new)
   );
   public final Holder<PlacedFeature> b;
   public final int c;
   public final int d;
   public final TagKey<Block> e;
   public final WorldGenFeatureStateProvider f;
   public final int g;
   public final int h;
   public final int i;
   public final int j;
   public final WorldGenFeatureStateProvider k;
   public final int l;
   public final int n;
   public final BlockPredicate o;

   public RootSystemConfiguration(
      Holder<PlacedFeature> var0,
      int var1,
      int var2,
      TagKey<Block> var3,
      WorldGenFeatureStateProvider var4,
      int var5,
      int var6,
      int var7,
      int var8,
      WorldGenFeatureStateProvider var9,
      int var10,
      int var11,
      BlockPredicate var12
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
      this.l = var10;
      this.n = var11;
      this.o = var12;
   }
}
