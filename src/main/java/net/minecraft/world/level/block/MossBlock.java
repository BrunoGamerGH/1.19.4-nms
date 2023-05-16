package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class MossBlock extends Block implements IBlockFragilePlantElement {
   public MossBlock(BlockBase.Info var0) {
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
      var0.u_().c(Registries.aq).flatMap(var0x -> var0x.b(CaveFeatures.n)).ifPresent(var3x -> var3x.a().a(var0, var0.k().g(), var1, var2.c()));
   }
}
