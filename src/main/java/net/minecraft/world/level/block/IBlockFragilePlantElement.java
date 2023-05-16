package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;

public interface IBlockFragilePlantElement {
   boolean a(IWorldReader var1, BlockPosition var2, IBlockData var3, boolean var4);

   boolean a(World var1, RandomSource var2, BlockPosition var3, IBlockData var4);

   void a(WorldServer var1, RandomSource var2, BlockPosition var3, IBlockData var4);
}
