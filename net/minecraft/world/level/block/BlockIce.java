package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.level.material.Material;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockIce extends BlockHalfTransparent {
   public BlockIce(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   @Override
   public void a(
      World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, ItemStack itemstack
   ) {
      super.a(world, entityhuman, blockposition, iblockdata, tileentity, itemstack);
      if (EnchantmentManager.a(Enchantments.v, itemstack) == 0) {
         if (world.q_().i()) {
            world.a(blockposition, false);
            return;
         }

         Material material = world.a_(blockposition.d()).d();
         if (material.c() || material.a()) {
            world.b(blockposition, Blocks.G.o());
         }
      }
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (worldserver.a(EnumSkyBlock.b, blockposition) > 11 - iblockdata.b(worldserver, blockposition)) {
         this.d(iblockdata, worldserver, blockposition);
      }
   }

   protected void d(IBlockData iblockdata, World world, BlockPosition blockposition) {
      if (!CraftEventFactory.callBlockFadeEvent(world, blockposition, world.q_().i() ? Blocks.a.o() : Blocks.G.o()).isCancelled()) {
         if (world.q_().i()) {
            world.a(blockposition, false);
         } else {
            world.b(blockposition, Blocks.G.o());
            world.a(blockposition, Blocks.G, blockposition);
         }
      }
   }

   @Override
   public EnumPistonReaction d(IBlockData iblockdata) {
      return EnumPistonReaction.a;
   }
}
