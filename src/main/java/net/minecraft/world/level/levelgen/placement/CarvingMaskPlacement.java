package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.levelgen.WorldGenStage;

public class CarvingMaskPlacement extends PlacementModifier {
   public static final Codec<CarvingMaskPlacement> a = WorldGenStage.Features.c.fieldOf("step").xmap(CarvingMaskPlacement::new, var0 -> var0.c).codec();
   private final WorldGenStage.Features c;

   private CarvingMaskPlacement(WorldGenStage.Features var0) {
      this.c = var0;
   }

   public static CarvingMaskPlacement a(WorldGenStage.Features var0) {
      return new CarvingMaskPlacement(var0);
   }

   @Override
   public Stream<BlockPosition> a_(PlacementContext var0, RandomSource var1, BlockPosition var2) {
      ChunkCoordIntPair var3 = new ChunkCoordIntPair(var2);
      return var0.a(var3, this.c).a(var3);
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.o;
   }
}
