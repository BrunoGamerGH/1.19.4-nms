package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;

public record BlockColumnConfiguration(List<BlockColumnConfiguration.a> layers, EnumDirection direction, BlockPredicate allowedPlacement, boolean prioritizeTip)
   implements WorldGenFeatureConfiguration {
   private final List<BlockColumnConfiguration.a> b;
   private final EnumDirection c;
   private final BlockPredicate d;
   private final boolean e;
   public static final Codec<BlockColumnConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               BlockColumnConfiguration.a.a.listOf().fieldOf("layers").forGetter(BlockColumnConfiguration::a),
               EnumDirection.g.fieldOf("direction").forGetter(BlockColumnConfiguration::b),
               BlockPredicate.b.fieldOf("allowed_placement").forGetter(BlockColumnConfiguration::c),
               Codec.BOOL.fieldOf("prioritize_tip").forGetter(BlockColumnConfiguration::d)
            )
            .apply(var0, BlockColumnConfiguration::new)
   );

   public BlockColumnConfiguration(List<BlockColumnConfiguration.a> var0, EnumDirection var1, BlockPredicate var2, boolean var3) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
   }

   public static BlockColumnConfiguration.a a(IntProvider var0, WorldGenFeatureStateProvider var1) {
      return new BlockColumnConfiguration.a(var0, var1);
   }

   public static BlockColumnConfiguration b(IntProvider var0, WorldGenFeatureStateProvider var1) {
      return new BlockColumnConfiguration(List.of(a(var0, var1)), EnumDirection.b, BlockPredicate.c, false);
   }

   public List<BlockColumnConfiguration.a> a() {
      return this.b;
   }

   public EnumDirection b() {
      return this.c;
   }

   public BlockPredicate c() {
      return this.d;
   }

   public boolean d() {
      return this.e;
   }

   public static record a(IntProvider height, WorldGenFeatureStateProvider state) {
      private final IntProvider b;
      private final WorldGenFeatureStateProvider c;
      public static final Codec<BlockColumnConfiguration.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  IntProvider.d.fieldOf("height").forGetter(BlockColumnConfiguration.a::a),
                  WorldGenFeatureStateProvider.a.fieldOf("provider").forGetter(BlockColumnConfiguration.a::b)
               )
               .apply(var0, BlockColumnConfiguration.a::new)
      );

      public a(IntProvider var0, WorldGenFeatureStateProvider var1) {
         this.b = var0;
         this.c = var1;
      }

      public IntProvider a() {
         return this.b;
      }

      public WorldGenFeatureStateProvider b() {
         return this.c;
      }
   }
}
