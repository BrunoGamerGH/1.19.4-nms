package net.minecraft.world.level.block;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BlockGrass extends BlockDirtSnowSpreadable implements IBlockFragilePlantElement {
   public BlockGrass(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public boolean a(IWorldReader var0, BlockPosition var1, IBlockData var2, boolean var3) {
      return var0.a_(var1.c()).h();
   }

   @Override
   public boolean a(World var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      return true;
   }

   @Override
   public void a(WorldServer var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      BlockPosition var4 = var2.c();
      IBlockData var5 = Blocks.bs.o();
      Optional<Holder.c<PlacedFeature>> var6 = var0.u_().d(Registries.aw).b(VegetationPlacements.n);

      label49:
      for(int var7 = 0; var7 < 128; ++var7) {
         BlockPosition var8 = var4;

         for(int var9 = 0; var9 < var7 / 16; ++var9) {
            var8 = var8.b(var1.a(3) - 1, (var1.a(3) - 1) * var1.a(3) / 2, var1.a(3) - 1);
            if (!var0.a_(var8.d()).a(this) || var0.a_(var8).r(var0, var8)) {
               continue label49;
            }
         }

         IBlockData var9 = var0.a_(var8);
         if (var9.a(var5.b()) && var1.a(10) == 0) {
            ((IBlockFragilePlantElement)var5.b()).a(var0, var1, var8, var9);
         }

         if (var9.h()) {
            Holder<PlacedFeature> var10;
            if (var1.a(8) == 0) {
               List<WorldGenFeatureConfigured<?, ?>> var11 = var0.v(var8).a().d().a();
               if (var11.isEmpty()) {
                  continue;
               }

               var10 = ((WorldGenFeatureRandomPatchConfiguration)var11.get(0).c()).d();
            } else {
               if (!var6.isPresent()) {
                  continue;
               }

               var10 = var6.get();
            }

            var10.a().a(var0, var0.k().g(), var1, var8);
         }
      }
   }
}
