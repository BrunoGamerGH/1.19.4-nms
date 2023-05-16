package net.minecraft.data.worldgen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverAbstract;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverWrapper;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class WorldGenCarvers {
   public static final ResourceKey<WorldGenCarverWrapper<?>> a = a("cave");
   public static final ResourceKey<WorldGenCarverWrapper<?>> b = a("cave_extra_underground");
   public static final ResourceKey<WorldGenCarverWrapper<?>> c = a("canyon");
   public static final ResourceKey<WorldGenCarverWrapper<?>> d = a("nether_cave");

   private static ResourceKey<WorldGenCarverWrapper<?>> a(String var0) {
      return ResourceKey.a(Registries.ap, new MinecraftKey(var0));
   }

   public static void a(BootstapContext<WorldGenCarverWrapper<?>> var0) {
      HolderGetter<Block> var1 = var0.a(Registries.e);
      var0.a(
         a,
         WorldGenCarverAbstract.a
            .a(
               new CaveCarverConfiguration(
                  0.15F,
                  UniformHeight.a(VerticalAnchor.b(8), VerticalAnchor.a(180)),
                  UniformFloat.b(0.1F, 0.9F),
                  VerticalAnchor.b(8),
                  CarverDebugSettings.a(false, Blocks.oO.o()),
                  var1.b(TagsBlock.bf),
                  UniformFloat.b(0.7F, 1.4F),
                  UniformFloat.b(0.8F, 1.3F),
                  UniformFloat.b(-1.0F, -0.4F)
               )
            )
      );
      var0.a(
         b,
         WorldGenCarverAbstract.a
            .a(
               new CaveCarverConfiguration(
                  0.07F,
                  UniformHeight.a(VerticalAnchor.b(8), VerticalAnchor.a(47)),
                  UniformFloat.b(0.1F, 0.9F),
                  VerticalAnchor.b(8),
                  CarverDebugSettings.a(false, Blocks.gu.o()),
                  var1.b(TagsBlock.bf),
                  UniformFloat.b(0.7F, 1.4F),
                  UniformFloat.b(0.8F, 1.3F),
                  UniformFloat.b(-1.0F, -0.4F)
               )
            )
      );
      var0.a(
         c,
         WorldGenCarverAbstract.c
            .a(
               new CanyonCarverConfiguration(
                  0.01F,
                  UniformHeight.a(VerticalAnchor.a(10), VerticalAnchor.a(67)),
                  ConstantFloat.a(3.0F),
                  VerticalAnchor.b(8),
                  CarverDebugSettings.a(false, Blocks.oP.o()),
                  var1.b(TagsBlock.bf),
                  UniformFloat.b(-0.125F, 0.125F),
                  new CanyonCarverConfiguration.a(UniformFloat.b(0.75F, 1.0F), TrapezoidFloat.a(0.0F, 6.0F, 2.0F), 3, UniformFloat.b(0.75F, 1.0F), 1.0F, 0.0F)
               )
            )
      );
      var0.a(
         d,
         WorldGenCarverAbstract.b
            .a(
               new CaveCarverConfiguration(
                  0.2F,
                  UniformHeight.a(VerticalAnchor.a(0), VerticalAnchor.c(1)),
                  ConstantFloat.a(0.5F),
                  VerticalAnchor.b(10),
                  var1.b(TagsBlock.bg),
                  ConstantFloat.a(1.0F),
                  ConstantFloat.a(1.0F),
                  ConstantFloat.a(-0.7F)
               )
            )
      );
   }
}
