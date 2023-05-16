package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityEndGateway;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenEndGatewayConfiguration;

public class WorldGenEndGateway extends WorldGenerator<WorldGenEndGatewayConfiguration> {
   public WorldGenEndGateway(Codec<WorldGenEndGatewayConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenEndGatewayConfiguration> var0) {
      BlockPosition var1 = var0.e();
      GeneratorAccessSeed var2 = var0.b();
      WorldGenEndGatewayConfiguration var3 = var0.f();

      for(BlockPosition var5 : BlockPosition.a(var1.b(-1, -2, -1), var1.b(1, 2, 1))) {
         boolean var6 = var5.u() == var1.u();
         boolean var7 = var5.v() == var1.v();
         boolean var8 = var5.w() == var1.w();
         boolean var9 = Math.abs(var5.v() - var1.v()) == 2;
         if (var6 && var7 && var8) {
            BlockPosition var10 = var5.i();
            this.a(var2, var10, Blocks.kC.o());
            var3.b().ifPresent(var3x -> {
               TileEntity var4x = var2.c_(var10);
               if (var4x instanceof TileEntityEndGateway var5x) {
                  var5x.a(var3x, var3.c());
                  var4x.e();
               }
            });
         } else if (var7) {
            this.a(var2, var5, Blocks.a.o());
         } else if (var9 && var6 && var8) {
            this.a(var2, var5, Blocks.F.o());
         } else if ((var6 || var8) && !var9) {
            this.a(var2, var5, Blocks.F.o());
         } else {
            this.a(var2, var5, Blocks.a.o());
         }
      }

      return true;
   }
}
