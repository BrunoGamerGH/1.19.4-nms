package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.HeightMap;

public class SurfaceWaterDepthFilter extends PlacementFilter {
   public static final Codec<SurfaceWaterDepthFilter> a = RecordCodecBuilder.create(
      var0 -> var0.group(Codec.INT.fieldOf("max_water_depth").forGetter(var0x -> var0x.c)).apply(var0, SurfaceWaterDepthFilter::new)
   );
   private final int c;

   private SurfaceWaterDepthFilter(int var0) {
      this.c = var0;
   }

   public static SurfaceWaterDepthFilter a(int var0) {
      return new SurfaceWaterDepthFilter(var0);
   }

   @Override
   protected boolean a(PlacementContext var0, RandomSource var1, BlockPosition var2) {
      int var3 = var0.a(HeightMap.Type.d, var2.u(), var2.w());
      int var4 = var0.a(HeightMap.Type.b, var2.u(), var2.w());
      return var4 - var3 <= this.c;
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.d;
   }
}
