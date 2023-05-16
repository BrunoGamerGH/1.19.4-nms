package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;

public class CountPlacement extends RepeatingPlacement {
   public static final Codec<CountPlacement> a = IntProvider.b(0, 256).fieldOf("count").xmap(CountPlacement::new, var0 -> var0.c).codec();
   private final IntProvider c;

   private CountPlacement(IntProvider var0) {
      this.c = var0;
   }

   public static CountPlacement a(IntProvider var0) {
      return new CountPlacement(var0);
   }

   public static CountPlacement a(int var0) {
      return a(ConstantInt.a(var0));
   }

   @Override
   protected int a(RandomSource var0, BlockPosition var1) {
      return this.c.a(var0);
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.f;
   }
}
