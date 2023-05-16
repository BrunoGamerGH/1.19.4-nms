package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.IPosition;
import net.minecraft.core.ISourceBlock;
import net.minecraft.core.Position;
import net.minecraft.core.SourceBlock;
import net.minecraft.core.dispenser.DispenseBehaviorItem;
import net.minecraft.core.dispenser.IDispenseBehavior;
import net.minecraft.server.level.WorldServer;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InventoryUtils;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityDispenser;
import net.minecraft.world.level.block.entity.TileEntityDropper;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class BlockDispenser extends BlockTileEntity {
   public static final BlockStateDirection a = BlockDirectional.a;
   public static final BlockStateBoolean b = BlockProperties.A;
   public static final Map<Item, IDispenseBehavior> c = SystemUtils.a(
      new Object2ObjectOpenHashMap(), object2objectopenhashmap -> object2objectopenhashmap.defaultReturnValue(new DispenseBehaviorItem())
   );
   private static final int d = 4;
   public static boolean eventFired = false;

   public static void a(IMaterial imaterial, IDispenseBehavior idispensebehavior) {
      c.put(imaterial.k(), idispensebehavior);
   }

   protected BlockDispenser(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, EnumDirection.c).a(b, Boolean.valueOf(false)));
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
         return EnumInteractionResult.a;
      } else {
         TileEntity tileentity = world.c_(blockposition);
         if (tileentity instanceof TileEntityDispenser) {
            entityhuman.a((TileEntityDispenser)tileentity);
            if (tileentity instanceof TileEntityDropper) {
               entityhuman.a(StatisticList.ac);
            } else {
               entityhuman.a(StatisticList.ae);
            }
         }

         return EnumInteractionResult.b;
      }
   }

   public void a(WorldServer worldserver, BlockPosition blockposition) {
      SourceBlock sourceblock = new SourceBlock(worldserver, blockposition);
      TileEntityDispenser tileentitydispenser = sourceblock.f();
      int i = tileentitydispenser.a(worldserver.z);
      if (i < 0) {
         worldserver.c(1001, blockposition, 0);
         worldserver.a(null, GameEvent.l, blockposition);
      } else {
         ItemStack itemstack = tileentitydispenser.a(i);
         IDispenseBehavior idispensebehavior = this.a(itemstack);
         if (idispensebehavior != IDispenseBehavior.b) {
            eventFired = false;
            tileentitydispenser.a(i, idispensebehavior.dispense(sourceblock, itemstack));
         }
      }
   }

   protected IDispenseBehavior a(ItemStack itemstack) {
      return c.get(itemstack.c());
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      boolean flag1 = world.r(blockposition) || world.r(blockposition.c());
      boolean flag2 = iblockdata.c(b);
      if (flag1 && !flag2) {
         world.a(blockposition, this, 4);
         world.a(blockposition, iblockdata.a(b, Boolean.valueOf(true)), 4);
      } else if (!flag1 && flag2) {
         world.a(blockposition, iblockdata.a(b, Boolean.valueOf(false)), 4);
      }
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      this.a(worldserver, blockposition);
   }

   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      return new TileEntityDispenser(blockposition, iblockdata);
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return this.o().a(a, blockactioncontext.d().g());
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
      if (itemstack.z()) {
         TileEntity tileentity = world.c_(blockposition);
         if (tileentity instanceof TileEntityDispenser) {
            ((TileEntityDispenser)tileentity).a(itemstack.x());
         }
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata.a(iblockdata1.b())) {
         TileEntity tileentity = world.c_(blockposition);
         if (tileentity instanceof TileEntityDispenser) {
            InventoryUtils.a(world, blockposition, (TileEntityDispenser)tileentity);
            world.c(blockposition, this);
         }

         super.a(iblockdata, world, blockposition, iblockdata1, flag);
      }
   }

   public static IPosition a(ISourceBlock isourceblock) {
      EnumDirection enumdirection = isourceblock.e().c(a);
      double d0 = isourceblock.a() + 0.7 * (double)enumdirection.j();
      double d1 = isourceblock.b() + 0.7 * (double)enumdirection.k();
      double d2 = isourceblock.c() + 0.7 * (double)enumdirection.l();
      return new Position(d0, d1, d2);
   }

   @Override
   public boolean d_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public int a(IBlockData iblockdata, World world, BlockPosition blockposition) {
      return Container.a(world.c_(blockposition));
   }

   @Override
   public EnumRenderType b_(IBlockData iblockdata) {
      return EnumRenderType.c;
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
      return iblockdata.a(a, enumblockrotation.a(iblockdata.c(a)));
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
      return iblockdata.a(enumblockmirror.a(iblockdata.c(a)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a, b);
   }
}
