package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleParamRedstone;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockRedstoneOre extends Block {
   public static final BlockStateBoolean a = BlockRedstoneTorch.a;

   public BlockRedstoneOre(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.o().a(a, Boolean.valueOf(false)));
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman) {
      interact(iblockdata, world, blockposition, entityhuman);
      super.a(iblockdata, world, blockposition, entityhuman);
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
      if (!entity.bP()) {
         if (entity instanceof EntityHuman) {
            PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, blockposition, null, null, null);
            if (!event.isCancelled()) {
               interact(world.a_(blockposition), world, blockposition, entity);
            }
         } else {
            EntityInteractEvent event = new EntityInteractEvent(
               entity.getBukkitEntity(), world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w())
            );
            world.getCraftServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
               interact(world.a_(blockposition), world, blockposition, entity);
            }
         }
      }

      super.a(world, blockposition, iblockdata, entity);
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
      if (world.B) {
         a(world, blockposition);
      } else {
         interact(iblockdata, world, blockposition, entityhuman);
      }

      ItemStack itemstack = entityhuman.b(enumhand);
      return itemstack.c() instanceof ItemBlock && new BlockActionContext(entityhuman, enumhand, itemstack, movingobjectpositionblock).b()
         ? EnumInteractionResult.d
         : EnumInteractionResult.a;
   }

   private static void interact(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      a(world, blockposition);
      if (!iblockdata.c(a)) {
         if (CraftEventFactory.callEntityChangeBlockEvent(entity, blockposition, iblockdata.a(a, Boolean.valueOf(true))).isCancelled()) {
            return;
         }

         world.a(blockposition, iblockdata.a(a, Boolean.valueOf(true)), 3);
      }
   }

   @Override
   public boolean e_(IBlockData iblockdata) {
      return iblockdata.c(a);
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(a)) {
         if (CraftEventFactory.callBlockFadeEvent(worldserver, blockposition, iblockdata.a(a, Boolean.valueOf(false))).isCancelled()) {
            return;
         }

         worldserver.a(blockposition, iblockdata.a(a, Boolean.valueOf(false)), 3);
      }
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
      super.a(iblockdata, worldserver, blockposition, itemstack, flag);
   }

   @Override
   public int getExpDrop(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
      return flag && EnchantmentManager.a(Enchantments.v, itemstack) == 0 ? 1 + worldserver.z.a(5) : 0;
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(a)) {
         a(world, blockposition);
      }
   }

   private static void a(World world, BlockPosition blockposition) {
      double d0 = 0.5625;
      RandomSource randomsource = world.z;

      for(EnumDirection enumdirection : EnumDirection.values()) {
         BlockPosition blockposition1 = blockposition.a(enumdirection);
         if (!world.a_(blockposition1).i(world, blockposition1)) {
            EnumDirection.EnumAxis enumdirection_enumaxis = enumdirection.o();
            double d1 = enumdirection_enumaxis == EnumDirection.EnumAxis.a ? 0.5 + 0.5625 * (double)enumdirection.j() : (double)randomsource.i();
            double d2 = enumdirection_enumaxis == EnumDirection.EnumAxis.b ? 0.5 + 0.5625 * (double)enumdirection.k() : (double)randomsource.i();
            double d3 = enumdirection_enumaxis == EnumDirection.EnumAxis.c ? 0.5 + 0.5625 * (double)enumdirection.l() : (double)randomsource.i();
            world.a(ParticleParamRedstone.b, (double)blockposition.u() + d1, (double)blockposition.v() + d2, (double)blockposition.w() + d3, 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a);
   }
}
