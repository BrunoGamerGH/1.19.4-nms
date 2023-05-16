package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

public record RuleBasedBlockStateProvider(WorldGenFeatureStateProvider fallback, List<RuleBasedBlockStateProvider.a> rules) {
   private final WorldGenFeatureStateProvider b;
   private final List<RuleBasedBlockStateProvider.a> c;
   public static final Codec<RuleBasedBlockStateProvider> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               WorldGenFeatureStateProvider.a.fieldOf("fallback").forGetter(RuleBasedBlockStateProvider::a),
               RuleBasedBlockStateProvider.a.a.listOf().fieldOf("rules").forGetter(RuleBasedBlockStateProvider::b)
            )
            .apply(var0, RuleBasedBlockStateProvider::new)
   );

   public RuleBasedBlockStateProvider(WorldGenFeatureStateProvider var0, List<RuleBasedBlockStateProvider.a> var1) {
      this.b = var0;
      this.c = var1;
   }

   public static RuleBasedBlockStateProvider a(WorldGenFeatureStateProvider var0) {
      return new RuleBasedBlockStateProvider(var0, List.of());
   }

   public static RuleBasedBlockStateProvider a(Block var0) {
      return a(WorldGenFeatureStateProvider.a(var0));
   }

   public IBlockData a(GeneratorAccessSeed var0, RandomSource var1, BlockPosition var2) {
      for(RuleBasedBlockStateProvider.a var4 : this.c) {
         if (var4.a().test(var0, var2)) {
            return var4.b().a(var1, var2);
         }
      }

      return this.b.a(var1, var2);
   }

   public WorldGenFeatureStateProvider a() {
      return this.b;
   }

   public List<RuleBasedBlockStateProvider.a> b() {
      return this.c;
   }

   public static record a(BlockPredicate ifTrue, WorldGenFeatureStateProvider then) {
      private final BlockPredicate b;
      private final WorldGenFeatureStateProvider c;
      public static final Codec<RuleBasedBlockStateProvider.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  BlockPredicate.b.fieldOf("if_true").forGetter(RuleBasedBlockStateProvider.a::a),
                  WorldGenFeatureStateProvider.a.fieldOf("then").forGetter(RuleBasedBlockStateProvider.a::b)
               )
               .apply(var0, RuleBasedBlockStateProvider.a::new)
      );

      public a(BlockPredicate var0, WorldGenFeatureStateProvider var1) {
         this.b = var0;
         this.c = var1;
      }

      public BlockPredicate a() {
         return this.b;
      }

      public WorldGenFeatureStateProvider b() {
         return this.c;
      }
   }
}
