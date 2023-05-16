package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;

public class RarityFilter extends PlacementFilter {
   public static final Codec<RarityFilter> a = ExtraCodecs.i.fieldOf("chance").xmap(RarityFilter::new, var0 -> var0.c).codec();
   private final int c;

   private RarityFilter(int var0) {
      this.c = var0;
   }

   public static RarityFilter a(int var0) {
      return new RarityFilter(var0);
   }

   @Override
   protected boolean a(PlacementContext var0, RandomSource var1, BlockPosition var2) {
      return var1.i() < 1.0F / (float)this.c;
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.b;
   }
}
