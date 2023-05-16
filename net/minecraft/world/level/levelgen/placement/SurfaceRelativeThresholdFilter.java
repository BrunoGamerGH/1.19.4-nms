package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.HeightMap;

public class SurfaceRelativeThresholdFilter extends PlacementFilter {
   public static final Codec<SurfaceRelativeThresholdFilter> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               HeightMap.Type.g.fieldOf("heightmap").forGetter(var0x -> var0x.c),
               Codec.INT.optionalFieldOf("min_inclusive", Integer.MIN_VALUE).forGetter(var0x -> var0x.d),
               Codec.INT.optionalFieldOf("max_inclusive", Integer.MAX_VALUE).forGetter(var0x -> var0x.e)
            )
            .apply(var0, SurfaceRelativeThresholdFilter::new)
   );
   private final HeightMap.Type c;
   private final int d;
   private final int e;

   private SurfaceRelativeThresholdFilter(HeightMap.Type var0, int var1, int var2) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
   }

   public static SurfaceRelativeThresholdFilter a(HeightMap.Type var0, int var1, int var2) {
      return new SurfaceRelativeThresholdFilter(var0, var1, var2);
   }

   @Override
   protected boolean a(PlacementContext var0, RandomSource var1, BlockPosition var2) {
      long var3 = (long)var0.a(this.c, var2.u(), var2.w());
      long var5 = var3 + (long)this.d;
      long var7 = var3 + (long)this.e;
      return var5 <= (long)var2.v() && (long)var2.v() <= var7;
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.c;
   }
}
