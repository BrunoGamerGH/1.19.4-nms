package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.HeightMap;

public class HeightmapPlacement extends PlacementModifier {
   public static final Codec<HeightmapPlacement> a = RecordCodecBuilder.create(
      var0 -> var0.group(HeightMap.Type.g.fieldOf("heightmap").forGetter(var0x -> var0x.c)).apply(var0, HeightmapPlacement::new)
   );
   private final HeightMap.Type c;

   private HeightmapPlacement(HeightMap.Type var0) {
      this.c = var0;
   }

   public static HeightmapPlacement a(HeightMap.Type var0) {
      return new HeightmapPlacement(var0);
   }

   @Override
   public Stream<BlockPosition> a_(PlacementContext var0, RandomSource var1, BlockPosition var2) {
      int var3 = var2.u();
      int var4 = var2.w();
      int var5 = var0.a(this.c, var3, var4);
      return var5 > var0.c() ? Stream.of(new BlockPosition(var3, var5, var4)) : Stream.of();
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.k;
   }
}
