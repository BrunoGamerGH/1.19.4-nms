package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockCoral extends Block {
   private final Block a;

   public BlockCoral(Block block, BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.a = block;
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (!this.a(worldserver, blockposition)) {
         if (CraftEventFactory.callBlockFadeEvent(worldserver, blockposition, this.a.o()).isCancelled()) {
            return;
         }

         worldserver.a(blockposition, this.a.o(), 2);
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
      if (!this.a(generatoraccess, blockposition)) {
         generatoraccess.a(blockposition, this, 60 + generatoraccess.r_().a(40));
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   protected boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
      for(EnumDirection enumdirection : EnumDirection.values()) {
         Fluid fluid = iblockaccess.b_(blockposition.a(enumdirection));
         if (fluid.a(TagsFluid.a)) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      if (!this.a(blockactioncontext.q(), blockactioncontext.a())) {
         blockactioncontext.q().a(blockactioncontext.a(), this, 60 + blockactioncontext.q().r_().a(40));
      }

      return this.o();
   }
}
