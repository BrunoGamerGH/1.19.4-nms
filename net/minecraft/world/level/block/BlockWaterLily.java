package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockWaterLily extends BlockPlant {
   protected static final VoxelShape a = Block.a(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

   protected BlockWaterLily(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      super.a(iblockdata, world, blockposition, entity);
      if (world instanceof WorldServer
         && entity instanceof EntityBoat
         && !CraftEventFactory.callEntityChangeBlockEvent(entity, blockposition, Blocks.a.o()).isCancelled()) {
         world.a(new BlockPosition(blockposition), true, entity);
      }
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return a;
   }

   @Override
   protected boolean d(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      Fluid fluid = iblockaccess.b_(blockposition);
      Fluid fluid1 = iblockaccess.b_(blockposition.c());
      return (fluid.a() == FluidTypes.c || iblockdata.d() == Material.H) && fluid1.a() == FluidTypes.a;
   }
}
