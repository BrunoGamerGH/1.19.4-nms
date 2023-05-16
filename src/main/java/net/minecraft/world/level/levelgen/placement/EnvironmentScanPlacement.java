package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

public class EnvironmentScanPlacement extends PlacementModifier {
   private final EnumDirection c;
   private final BlockPredicate d;
   private final BlockPredicate e;
   private final int f;
   public static final Codec<EnvironmentScanPlacement> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               EnumDirection.h.fieldOf("direction_of_search").forGetter(var0x -> var0x.c),
               BlockPredicate.b.fieldOf("target_condition").forGetter(var0x -> var0x.d),
               BlockPredicate.b.optionalFieldOf("allowed_search_condition", BlockPredicate.e()).forGetter(var0x -> var0x.e),
               Codec.intRange(1, 32).fieldOf("max_steps").forGetter(var0x -> var0x.f)
            )
            .apply(var0, EnvironmentScanPlacement::new)
   );

   private EnvironmentScanPlacement(EnumDirection var0, BlockPredicate var1, BlockPredicate var2, int var3) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
   }

   public static EnvironmentScanPlacement a(EnumDirection var0, BlockPredicate var1, BlockPredicate var2, int var3) {
      return new EnvironmentScanPlacement(var0, var1, var2, var3);
   }

   public static EnvironmentScanPlacement a(EnumDirection var0, BlockPredicate var1, int var2) {
      return a(var0, var1, BlockPredicate.e(), var2);
   }

   @Override
   public Stream<BlockPosition> a_(PlacementContext var0, RandomSource var1, BlockPosition var2) {
      BlockPosition.MutableBlockPosition var3 = var2.j();
      GeneratorAccessSeed var4 = var0.d();
      if (!this.e.test(var4, var3)) {
         return Stream.of();
      } else {
         for(int var5 = 0; var5 < this.f; ++var5) {
            if (this.d.test(var4, var3)) {
               return Stream.of(var3);
            }

            var3.c(this.c);
            if (var4.d(var3.v())) {
               return Stream.of();
            }

            if (!this.e.test(var4, var3)) {
               break;
            }
         }

         return this.d.test(var4, var3) ? Stream.of(var3) : Stream.of();
      }
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.j;
   }
}
