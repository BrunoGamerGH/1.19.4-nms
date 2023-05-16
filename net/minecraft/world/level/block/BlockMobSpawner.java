package net.minecraft.world.level.block;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityMobSpawner;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockMobSpawner extends BlockTileEntity {
   protected BlockMobSpawner(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      return new TileEntityMobSpawner(blockposition, iblockdata);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World world, IBlockData iblockdata, TileEntityTypes<T> tileentitytypes) {
      return a(tileentitytypes, TileEntityTypes.j, world.B ? TileEntityMobSpawner::a : TileEntityMobSpawner::b);
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
      super.a(iblockdata, worldserver, blockposition, itemstack, flag);
   }

   @Override
   public int getExpDrop(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
      return flag ? 15 + worldserver.z.a(15) + worldserver.z.a(15) : 0;
   }

   @Override
   public EnumRenderType b_(IBlockData iblockdata) {
      return EnumRenderType.c;
   }

   @Override
   public void a(ItemStack itemstack, @Nullable IBlockAccess iblockaccess, List<IChatBaseComponent> list, TooltipFlag tooltipflag) {
      super.a(itemstack, iblockaccess, list, tooltipflag);
      Optional<IChatBaseComponent> optional = this.a(itemstack);
      if (optional.isPresent()) {
         list.add(optional.get());
      } else {
         list.add(CommonComponents.a);
         list.add(IChatBaseComponent.c("block.minecraft.spawner.desc1").a(EnumChatFormat.h));
         list.add(CommonComponents.a().b(IChatBaseComponent.c("block.minecraft.spawner.desc2").a(EnumChatFormat.j)));
      }
   }

   private Optional<IChatBaseComponent> a(ItemStack itemstack) {
      NBTTagCompound nbttagcompound = ItemBlock.a(itemstack);
      if (nbttagcompound != null && nbttagcompound.b("SpawnData", 10)) {
         String s = nbttagcompound.p("SpawnData").p("entity").l("id");
         MinecraftKey minecraftkey = MinecraftKey.a(s);
         if (minecraftkey != null) {
            return BuiltInRegistries.h.b(minecraftkey).map(entitytypes -> IChatBaseComponent.c(entitytypes.g()).a(EnumChatFormat.h));
         }
      }

      return Optional.empty();
   }
}
