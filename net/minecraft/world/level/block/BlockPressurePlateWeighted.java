package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;

public class BlockPressurePlateWeighted extends BlockPressurePlateAbstract {
   public static final BlockStateInteger d = BlockProperties.aT;
   private final int e;

   protected BlockPressurePlateWeighted(int i, BlockBase.Info blockbase_info, BlockSetType blocksettype) {
      super(blockbase_info, blocksettype);
      this.k(this.D.b().a(d, Integer.valueOf(0)));
      this.e = i;
   }

   @Override
   protected int b(World world, BlockPosition blockposition) {
      int i = 0;

      for(Entity entity : world.a(Entity.class, c.a(blockposition))) {
         Cancellable cancellable;
         if (entity instanceof EntityHuman) {
            cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, blockposition, null, null, null);
         } else {
            cancellable = new EntityInteractEvent(
               entity.getBukkitEntity(), world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w())
            );
            world.getCraftServer().getPluginManager().callEvent((EntityInteractEvent)cancellable);
         }

         if (!cancellable.isCancelled()) {
            ++i;
         }
      }

      i = Math.min(i, this.e);
      if (i > 0) {
         float f = (float)Math.min(this.e, i) / (float)this.e;
         return MathHelper.f(f * 15.0F);
      } else {
         return 0;
      }
   }

   @Override
   protected int g(IBlockData iblockdata) {
      return iblockdata.c(d);
   }

   @Override
   protected IBlockData a(IBlockData iblockdata, int i) {
      return iblockdata.a(d, Integer.valueOf(i));
   }

   @Override
   protected int b() {
      return 10;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(d);
   }
}
