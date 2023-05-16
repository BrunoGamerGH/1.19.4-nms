package net.minecraft.world.level.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class BlockSkullPlayerWall extends BlockSkullWall {
   protected BlockSkullPlayerWall(BlockBase.Info var0) {
      super(BlockSkull.Type.c, var0);
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, @Nullable EntityLiving var3, ItemStack var4) {
      Blocks.gJ.a(var0, var1, var2, var3, var4);
   }

   @Override
   public List<ItemStack> a(IBlockData var0, LootTableInfo.Builder var1) {
      return Blocks.gJ.a(var0, var1);
   }
}
