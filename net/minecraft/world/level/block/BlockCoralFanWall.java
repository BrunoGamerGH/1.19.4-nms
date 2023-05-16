package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.FluidTypes;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockCoralFanWall extends BlockCoralFanWallAbstract {
   private final Block b;

   protected BlockCoralFanWall(Block block, BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.b = block;
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      this.a(iblockdata, (GeneratorAccess)world, blockposition);
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (!e(iblockdata, worldserver, blockposition)) {
         if (CraftEventFactory.callBlockFadeEvent(worldserver, blockposition, this.b.o().a(c, Boolean.valueOf(false)).a(a, iblockdata.c(a))).isCancelled()) {
            return;
         }

         worldserver.a(blockposition, this.b.o().a(c, Boolean.valueOf(false)).a(a, iblockdata.c(a)), 2);
      }
   }

   @Override
   public IBlockData a(
      IBlockData iblockdata,
      EnumDirection enumdirection,
      IBlockData iblockdata1,
      GeneratorAccess generatoraccess,
      BlockPosition blockposition,
      BlockPosition blockposition1
   ) {
      if (enumdirection.g() == iblockdata.c(a) && !iblockdata.a(generatoraccess, blockposition)) {
         return Blocks.a.o();
      } else {
         if (iblockdata.c(c)) {
            generatoraccess.a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
         }

         this.a(iblockdata, generatoraccess, blockposition);
         return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
      }
   }
}
