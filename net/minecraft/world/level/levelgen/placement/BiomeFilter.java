package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.BiomeBase;

public class BiomeFilter extends PlacementFilter {
   private static final BiomeFilter c = new BiomeFilter();
   public static Codec<BiomeFilter> a = Codec.unit(() -> c);

   private BiomeFilter() {
   }

   public static BiomeFilter a() {
      return c;
   }

   @Override
   protected boolean a(PlacementContext var0, RandomSource var1, BlockPosition var2) {
      PlacedFeature var3 = var0.e()
         .orElseThrow(() -> new IllegalStateException("Tried to biome check an unregistered feature, or a feature that should not restrict the biome"));
      Holder<BiomeBase> var4 = var0.d().v(var2);
      return var0.f().a(var4).a(var3);
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.e;
   }
}
