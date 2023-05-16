package net.minecraft.data.worldgen;

import java.util.OptionalLong;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionManager;

public class DimensionTypes {
   public static void a(BootstapContext<DimensionManager> var0) {
      var0.a(
         BuiltinDimensionTypes.a,
         new DimensionManager(
            OptionalLong.empty(),
            true,
            false,
            false,
            true,
            1.0,
            true,
            false,
            -64,
            384,
            384,
            TagsBlock.aY,
            BuiltinDimensionTypes.e,
            0.0F,
            new DimensionManager.a(false, true, UniformInt.a(0, 7), 0)
         )
      );
      var0.a(
         BuiltinDimensionTypes.b,
         new DimensionManager(
            OptionalLong.of(18000L),
            false,
            true,
            true,
            false,
            8.0,
            false,
            true,
            0,
            256,
            128,
            TagsBlock.aZ,
            BuiltinDimensionTypes.f,
            0.1F,
            new DimensionManager.a(true, false, ConstantInt.a(7), 15)
         )
      );
      var0.a(
         BuiltinDimensionTypes.c,
         new DimensionManager(
            OptionalLong.of(6000L),
            false,
            false,
            false,
            false,
            1.0,
            false,
            false,
            0,
            256,
            256,
            TagsBlock.ba,
            BuiltinDimensionTypes.g,
            0.0F,
            new DimensionManager.a(false, true, UniformInt.a(0, 7), 0)
         )
      );
      var0.a(
         BuiltinDimensionTypes.d,
         new DimensionManager(
            OptionalLong.empty(),
            true,
            true,
            false,
            true,
            1.0,
            true,
            false,
            -64,
            384,
            384,
            TagsBlock.aY,
            BuiltinDimensionTypes.e,
            0.0F,
            new DimensionManager.a(false, true, UniformInt.a(0, 7), 0)
         )
      );
   }
}
