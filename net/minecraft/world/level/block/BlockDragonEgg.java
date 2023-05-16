package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockDragonEgg extends BlockFalling {
   protected static final VoxelShape a = Block.a(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public BlockDragonEgg(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return a;
   }

   @Override
   public EnumInteractionResult a(
      IBlockData iblockdata,
      World world,
      BlockPosition blockposition,
      EntityHuman entityhuman,
      EnumHand enumhand,
      MovingObjectPositionBlock movingobjectpositionblock
   ) {
      this.d(iblockdata, world, blockposition);
      return EnumInteractionResult.a(world.B);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman) {
      this.d(iblockdata, world, blockposition);
   }

   private void d(IBlockData iblockdata, World world, BlockPosition blockposition) {
      WorldBorder worldborder = world.p_();

      for(int i = 0; i < 1000; ++i) {
         BlockPosition blockposition1 = blockposition.b(world.z.a(16) - world.z.a(16), world.z.a(8) - world.z.a(8), world.z.a(16) - world.z.a(16));
         if (world.a_(blockposition1).h() && worldborder.a(blockposition1)) {
            org.bukkit.block.Block from = world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
            org.bukkit.block.Block to = world.getWorld().getBlockAt(blockposition1.u(), blockposition1.v(), blockposition1.w());
            BlockFromToEvent event = new BlockFromToEvent(from, to);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               return;
            }

            blockposition1 = new BlockPosition(event.getToBlock().getX(), event.getToBlock().getY(), event.getToBlock().getZ());
            if (world.B) {
               for(int j = 0; j < 128; ++j) {
                  double d0 = world.z.j();
                  float f = (world.z.i() - 0.5F) * 0.2F;
                  float f1 = (world.z.i() - 0.5F) * 0.2F;
                  float f2 = (world.z.i() - 0.5F) * 0.2F;
                  double d1 = MathHelper.d(d0, (double)blockposition1.u(), (double)blockposition.u()) + (world.z.j() - 0.5) + 0.5;
                  double d2 = MathHelper.d(d0, (double)blockposition1.v(), (double)blockposition.v()) + world.z.j() - 0.5;
                  double d3 = MathHelper.d(d0, (double)blockposition1.w(), (double)blockposition.w()) + (world.z.j() - 0.5) + 0.5;
                  world.a(Particles.Z, d1, d2, d3, (double)f, (double)f1, (double)f2);
               }
            } else {
               world.a(blockposition1, iblockdata, 2);
               world.a(blockposition, false);
            }

            return;
         }
      }
   }

   @Override
   protected int b() {
      return 5;
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }
}
