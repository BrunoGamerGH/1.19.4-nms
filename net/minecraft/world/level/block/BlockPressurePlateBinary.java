package net.minecraft.world.level.block;

import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.plugin.PluginManager;

public class BlockPressurePlateBinary extends BlockPressurePlateAbstract {
   public static final BlockStateBoolean d = BlockProperties.w;
   private final BlockPressurePlateBinary.EnumMobType e;

   protected BlockPressurePlateBinary(
      BlockPressurePlateBinary.EnumMobType blockpressureplatebinary_enummobtype, BlockBase.Info blockbase_info, BlockSetType blocksettype
   ) {
      super(blockbase_info, blocksettype);
      this.k(this.D.b().a(d, Boolean.valueOf(false)));
      this.e = blockpressureplatebinary_enummobtype;
   }

   @Override
   protected int g(IBlockData iblockdata) {
      return iblockdata.c(d) ? 15 : 0;
   }

   @Override
   protected IBlockData a(IBlockData iblockdata, int i) {
      return iblockdata.a(d, Boolean.valueOf(i > 0));
   }

   @Override
   protected int b(World world, BlockPosition blockposition) {
      AxisAlignedBB axisalignedbb = c.a(blockposition);
      List list;
      switch(this.e) {
         case a:
            list = world.a_(null, axisalignedbb);
            break;
         case b:
            list = world.a(EntityLiving.class, axisalignedbb);
            break;
         default:
            return 0;
      }

      if (!list.isEmpty()) {
         for(Entity entity : list) {
            if (this.g(world.a_(blockposition)) == 0) {
               org.bukkit.World bworld = world.getWorld();
               PluginManager manager = world.getCraftServer().getPluginManager();
               Cancellable cancellable;
               if (entity instanceof EntityHuman) {
                  cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, blockposition, null, null, null);
               } else {
                  cancellable = new EntityInteractEvent(entity.getBukkitEntity(), bworld.getBlockAt(blockposition.u(), blockposition.v(), blockposition.w()));
                  manager.callEvent((EntityInteractEvent)cancellable);
               }

               if (cancellable.isCancelled()) {
                  continue;
               }
            }

            if (!entity.cq()) {
               return 15;
            }
         }
      }

      return 0;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(d);
   }

   public static enum EnumMobType {
      a,
      b;
   }
}
