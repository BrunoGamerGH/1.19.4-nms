package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityEnderPortal;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.Location;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class BlockEnderPortal extends BlockTileEntity {
   protected static final VoxelShape a = Block.a(0.0, 6.0, 0.0, 16.0, 12.0, 16.0);

   protected BlockEnderPortal(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      return new TileEntityEnderPortal(blockposition, iblockdata);
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return a;
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      if (world instanceof WorldServer
         && entity.co()
         && VoxelShapes.c(
            VoxelShapes.a(entity.cD().d((double)(-blockposition.u()), (double)(-blockposition.v()), (double)(-blockposition.w()))),
            iblockdata.j(world, blockposition),
            OperatorBoolean.i
         )) {
         ResourceKey<World> resourcekey = world.getTypeKey() == WorldDimension.d ? World.h : World.j;
         WorldServer worldserver = ((WorldServer)world).n().a(resourcekey);
         EntityPortalEnterEvent event = new EntityPortalEnterEvent(
            entity.getBukkitEntity(), new Location(world.getWorld(), (double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w())
         );
         world.getCraftServer().getPluginManager().callEvent(event);
         if (entity instanceof EntityPlayer) {
            ((EntityPlayer)entity).changeDimension(worldserver, TeleportCause.END_PORTAL);
            return;
         }

         entity.b(worldserver);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      double d0 = (double)blockposition.u() + randomsource.j();
      double d1 = (double)blockposition.v() + 0.8;
      double d2 = (double)blockposition.w() + randomsource.j();
      world.a(Particles.ab, d0, d1, d2, 0.0, 0.0, 0.0);
   }

   @Override
   public ItemStack a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
      return ItemStack.b;
   }

   @Override
   public boolean a(IBlockData iblockdata, FluidType fluidtype) {
      return false;
   }
}
