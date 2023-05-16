package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public abstract class BlockGrowingTop extends BlockGrowingAbstract implements IBlockFragilePlantElement {
   public static final BlockStateInteger d = BlockProperties.ax;
   public static final int e = 25;
   private final double f;

   protected BlockGrowingTop(BlockBase.Info blockbase_info, EnumDirection enumdirection, VoxelShape voxelshape, boolean flag, double d0) {
      super(blockbase_info, enumdirection, voxelshape, flag);
      this.f = d0;
      this.k(this.D.b().a(d, Integer.valueOf(0)));
   }

   @Override
   public IBlockData a(GeneratorAccess generatoraccess) {
      return this.o().a(d, Integer.valueOf(generatoraccess.r_().a(25)));
   }

   @Override
   public boolean e_(IBlockData iblockdata) {
      return iblockdata.c(d) < 25;
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      int modifier;
      if (this == Blocks.lZ) {
         modifier = worldserver.spigotConfig.kelpModifier;
      } else if (this == Blocks.ox) {
         modifier = worldserver.spigotConfig.twistingVinesModifier;
      } else if (this == Blocks.ov) {
         modifier = worldserver.spigotConfig.weepingVinesModifier;
      } else {
         modifier = worldserver.spigotConfig.caveVinesModifier;
      }

      if (iblockdata.c(d) < 25 && randomsource.j() < (double)modifier / 100.0 * this.f) {
         BlockPosition blockposition1 = blockposition.a(this.a);
         if (this.g(worldserver.a_(blockposition1))) {
            CraftEventFactory.handleBlockSpreadEvent(worldserver, blockposition, blockposition1, this.a(iblockdata, worldserver.z));
         }
      }
   }

   protected IBlockData a(IBlockData iblockdata, RandomSource randomsource) {
      return iblockdata.a(d);
   }

   public IBlockData n(IBlockData iblockdata) {
      return iblockdata.a(d, Integer.valueOf(25));
   }

   public boolean o(IBlockData iblockdata) {
      return iblockdata.c(d) == 25;
   }

   protected IBlockData a(IBlockData iblockdata, IBlockData iblockdata1) {
      return iblockdata1;
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
      if (enumdirection == this.a.g() && !iblockdata.a(generatoraccess, blockposition)) {
         generatoraccess.a(blockposition, this, 1);
      }

      if (enumdirection != this.a || !iblockdata1.a(this) && !iblockdata1.a(this.b())) {
         if (this.b) {
            generatoraccess.a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
         }

         return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
      } else {
         return this.a(iblockdata, this.b().o());
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(d);
   }

   @Override
   public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      return this.g(iworldreader.a_(blockposition.a(this.a)));
   }

   @Override
   public boolean a(World world, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      return true;
   }

   @Override
   public void a(WorldServer worldserver, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      BlockPosition blockposition1 = blockposition.a(this.a);
      int i = Math.min(iblockdata.c(d) + 1, 25);
      int j = this.a(randomsource);

      for(int k = 0; k < j && this.g(worldserver.a_(blockposition1)); ++k) {
         worldserver.b(blockposition1, iblockdata.a(d, Integer.valueOf(i)));
         blockposition1 = blockposition1.a(this.a);
         i = Math.min(i + 1, 25);
      }
   }

   protected abstract int a(RandomSource var1);

   protected abstract boolean g(IBlockData var1);

   @Override
   protected BlockGrowingTop c() {
      return this;
   }
}
