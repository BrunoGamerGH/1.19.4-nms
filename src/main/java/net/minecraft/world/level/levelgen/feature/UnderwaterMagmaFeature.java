package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.configurations.UnderwaterMagmaConfiguration;
import net.minecraft.world.phys.AxisAlignedBB;

public class UnderwaterMagmaFeature extends WorldGenerator<UnderwaterMagmaConfiguration> {
   public UnderwaterMagmaFeature(Codec<UnderwaterMagmaConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<UnderwaterMagmaConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      UnderwaterMagmaConfiguration var3 = var0.f();
      RandomSource var4 = var0.d();
      OptionalInt var5 = a(var1, var2, var3);
      if (!var5.isPresent()) {
         return false;
      } else {
         BlockPosition var6 = var2.h(var5.getAsInt());
         BaseBlockPosition var7 = new BaseBlockPosition(var3.c, var3.c, var3.c);
         AxisAlignedBB var8 = new AxisAlignedBB(var6.b(var7), var6.a(var7));
         return BlockPosition.a(var8).filter(var2x -> var4.i() < var3.d).filter(var1x -> this.b(var1, var1x)).mapToInt(var1x -> {
            var1.a(var1x, Blocks.kG.o(), 2);
            return 1;
         }).sum() > 0;
      }
   }

   private static OptionalInt a(GeneratorAccessSeed var0, BlockPosition var1, UnderwaterMagmaConfiguration var2) {
      Predicate<IBlockData> var3 = var0x -> var0x.a(Blocks.G);
      Predicate<IBlockData> var4 = var0x -> !var0x.a(Blocks.G);
      Optional<Column> var5 = Column.a(var0, var1, var2.b, var3, var4);
      return var5.<OptionalInt>map(Column::c).orElseGet(OptionalInt::empty);
   }

   private boolean b(GeneratorAccessSeed var0, BlockPosition var1) {
      if (!this.a(var0, var1) && !this.a(var0, var1.d())) {
         for(EnumDirection var3 : EnumDirection.EnumDirectionLimit.a) {
            if (this.a(var0, var1.a(var3))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean a(GeneratorAccess var0, BlockPosition var1) {
      IBlockData var2 = var0.a_(var1);
      return var2.a(Blocks.G) || var2.h();
   }
}
