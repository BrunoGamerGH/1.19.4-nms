package net.minecraft.world.item;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockCanBuildEvent;

public class ItemBlockWallable extends ItemBlock {
   public final Block d;
   private final EnumDirection c;

   public ItemBlockWallable(Block block, Block block1, Item.Info item_info, EnumDirection enumdirection) {
      super(block, item_info);
      this.d = block1;
      this.c = enumdirection;
   }

   protected boolean a(IWorldReader iworldreader, IBlockData iblockdata, BlockPosition blockposition) {
      return iblockdata.a(iworldreader, blockposition);
   }

   @Nullable
   @Override
   protected IBlockData c(BlockActionContext blockactioncontext) {
      IBlockData iblockdata = this.d.a(blockactioncontext);
      IBlockData iblockdata1 = null;
      World world = blockactioncontext.q();
      BlockPosition blockposition = blockactioncontext.a();

      for(EnumDirection enumdirection : blockactioncontext.f()) {
         if (enumdirection != this.c.g()) {
            IBlockData iblockdata2 = enumdirection == this.c ? this.e().a(blockactioncontext) : iblockdata;
            if (iblockdata2 != null && this.a(world, iblockdata2, blockposition)) {
               iblockdata1 = iblockdata2;
               break;
            }
         }
      }

      if (iblockdata1 != null) {
         boolean defaultReturn = world.a(iblockdata1, blockposition, VoxelShapeCollision.a());
         Player player = blockactioncontext.o() instanceof EntityPlayer ? (Player)blockactioncontext.o().getBukkitEntity() : null;
         BlockCanBuildEvent event = new BlockCanBuildEvent(CraftBlock.at(world, blockposition), player, CraftBlockData.fromData(iblockdata1), defaultReturn);
         blockactioncontext.q().getCraftServer().getPluginManager().callEvent(event);
         return event.isBuildable() ? iblockdata1 : null;
      } else {
         return null;
      }
   }

   @Override
   public void a(Map<Block, Item> map, Item item) {
      super.a(map, item);
      map.put(this.d, item);
   }
}
