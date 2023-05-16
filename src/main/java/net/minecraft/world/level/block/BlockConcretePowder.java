package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.entity.item.EntityFallingBlock;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockStates;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.block.BlockFormEvent;

public class BlockConcretePowder extends BlockFalling {
   private final IBlockData a;

   public BlockConcretePowder(Block block, BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.a = block.o();
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, IBlockData iblockdata1, EntityFallingBlock entityfallingblock) {
      if (b(world, blockposition, iblockdata1)) {
         CraftEventFactory.handleBlockFormEvent(world, blockposition, this.a, 3);
      }
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      World world = blockactioncontext.q();
      BlockPosition blockposition = blockactioncontext.a();
      IBlockData iblockdata = world.a_(blockposition);
      if (!b(world, blockposition, iblockdata)) {
         return super.a(blockactioncontext);
      } else {
         CraftBlockState blockState = CraftBlockStates.getBlockState(world, blockposition);
         blockState.setData(this.a);
         BlockFormEvent event = new BlockFormEvent(blockState.getBlock(), blockState);
         world.n().server.getPluginManager().callEvent(event);
         return !event.isCancelled() ? blockState.getHandle() : super.a(blockactioncontext);
      }
   }

   private static boolean b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
      return n(iblockdata) || a(iblockaccess, blockposition);
   }

   private static boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
      boolean flag = false;
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = blockposition.j();

      for(EnumDirection enumdirection : EnumDirection.values()) {
         IBlockData iblockdata = iblockaccess.a_(blockposition_mutableblockposition);
         if (enumdirection != EnumDirection.a || n(iblockdata)) {
            blockposition_mutableblockposition.a(blockposition, enumdirection);
            iblockdata = iblockaccess.a_(blockposition_mutableblockposition);
            if (n(iblockdata) && !iblockdata.d(iblockaccess, blockposition, enumdirection.g())) {
               flag = true;
               break;
            }
         }
      }

      return flag;
   }

   private static boolean n(IBlockData iblockdata) {
      return iblockdata.r().a(TagsFluid.a);
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
      if (a(generatoraccess, blockposition)) {
         if (!(generatoraccess instanceof World)) {
            return this.a;
         }

         CraftBlockState blockState = CraftBlockStates.getBlockState(generatoraccess, blockposition);
         blockState.setData(this.a);
         BlockFormEvent event = new BlockFormEvent(blockState.getBlock(), blockState);
         ((World)generatoraccess).getCraftServer().getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            return blockState.getHandle();
         }
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public int d(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockdata.d(iblockaccess, blockposition).ak;
   }
}
