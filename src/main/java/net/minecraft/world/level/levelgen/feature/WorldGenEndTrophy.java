package net.minecraft.world.level.levelgen.feature;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.BlockTorchWall;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class WorldGenEndTrophy extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   public static final int a = 4;
   public static final int b = 4;
   public static final int c = 1;
   public static final float d = 0.5F;
   public static final BlockPosition e = BlockPosition.b;
   private final boolean ao;

   public WorldGenEndTrophy(boolean var0) {
      super(WorldGenFeatureEmptyConfiguration.a);
      this.ao = var0;
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      BlockPosition var1 = var0.e();
      GeneratorAccessSeed var2 = var0.b();

      for(BlockPosition var4 : BlockPosition.a(
         new BlockPosition(var1.u() - 4, var1.v() - 1, var1.w() - 4), new BlockPosition(var1.u() + 4, var1.v() + 32, var1.w() + 4)
      )) {
         boolean var5 = var4.a(var1, 2.5);
         if (var5 || var4.a(var1, 3.5)) {
            if (var4.v() < var1.v()) {
               if (var5) {
                  this.a(var2, var4, Blocks.F.o());
               } else if (var4.v() < var1.v()) {
                  this.a(var2, var4, Blocks.fy.o());
               }
            } else if (var4.v() > var1.v()) {
               this.a(var2, var4, Blocks.a.o());
            } else if (!var5) {
               this.a(var2, var4, Blocks.F.o());
            } else if (this.ao) {
               this.a(var2, new BlockPosition(var4), Blocks.fw.o());
            } else {
               this.a(var2, new BlockPosition(var4), Blocks.a.o());
            }
         }
      }

      for(int var3 = 0; var3 < 4; ++var3) {
         this.a(var2, var1.b(var3), Blocks.F.o());
      }

      BlockPosition var3 = var1.b(2);

      for(EnumDirection var5 : EnumDirection.EnumDirectionLimit.a) {
         this.a(var2, var3.a(var5), Blocks.cp.o().a(BlockTorchWall.a, var5));
      }

      return true;
   }
}
