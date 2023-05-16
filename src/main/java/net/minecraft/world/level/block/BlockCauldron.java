package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import org.bukkit.event.block.CauldronLevelChangeEvent.ChangeReason;

public class BlockCauldron extends AbstractCauldronBlock {
   private static final float c = 0.05F;
   private static final float d = 0.1F;

   public BlockCauldron(BlockBase.Info blockbase_info) {
      super(blockbase_info, CauldronInteraction.a);
   }

   @Override
   public boolean c(IBlockData iblockdata) {
      return false;
   }

   protected static boolean a(World world, BiomeBase.Precipitation biomebase_precipitation) {
      return biomebase_precipitation == BiomeBase.Precipitation.b
         ? world.r_().i() < 0.05F
         : (biomebase_precipitation == BiomeBase.Precipitation.c ? world.r_().i() < 0.1F : false);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, BiomeBase.Precipitation biomebase_precipitation) {
      if (a(world, biomebase_precipitation)) {
         if (biomebase_precipitation == BiomeBase.Precipitation.b) {
            world.b(blockposition, Blocks.ft.o());
            world.a(null, GameEvent.c, blockposition);
         } else if (biomebase_precipitation == BiomeBase.Precipitation.c) {
            world.b(blockposition, Blocks.fv.o());
            world.a(null, GameEvent.c, blockposition);
         }
      }
   }

   @Override
   protected boolean a(FluidType fluidtype) {
      return true;
   }

   @Override
   protected void a(IBlockData iblockdata, World world, BlockPosition blockposition, FluidType fluidtype) {
      if (fluidtype == FluidTypes.c) {
         IBlockData iblockdata1 = Blocks.ft.o();
         LayeredCauldronBlock.changeLevel(iblockdata, world, blockposition, iblockdata1, null, ChangeReason.NATURAL_FILL);
         world.c(1047, blockposition, 0);
      } else if (fluidtype == FluidTypes.e) {
         IBlockData iblockdata1 = Blocks.fu.o();
         LayeredCauldronBlock.changeLevel(iblockdata, world, blockposition, iblockdata1, null, ChangeReason.NATURAL_FILL);
         world.c(1046, blockposition, 0);
      }
   }
}
