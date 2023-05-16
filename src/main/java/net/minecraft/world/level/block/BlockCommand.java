package net.minecraft.world.level.block;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.UtilColor;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.CommandBlockListenerAbstract;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityCommand;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.slf4j.Logger;

public class BlockCommand extends BlockTileEntity implements GameMasterBlock {
   private static final Logger c = LogUtils.getLogger();
   public static final BlockStateDirection a = BlockDirectional.a;
   public static final BlockStateBoolean b = BlockProperties.c;
   private final boolean d;

   public BlockCommand(BlockBase.Info blockbase_info, boolean flag) {
      super(blockbase_info);
      this.k(this.D.b().a(a, EnumDirection.c).a(b, Boolean.valueOf(false)));
      this.d = flag;
   }

   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      TileEntityCommand tileentitycommand = new TileEntityCommand(blockposition, iblockdata);
      tileentitycommand.b(this.d);
      return tileentitycommand;
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      if (!world.B) {
         TileEntity tileentity = world.c_(blockposition);
         if (tileentity instanceof TileEntityCommand tileentitycommand) {
            boolean flag1 = world.r(blockposition);
            boolean flag2 = tileentitycommand.d();
            org.bukkit.block.Block bukkitBlock = world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
            int old = flag2 ? 15 : 0;
            int current = flag1 ? 15 : 0;
            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bukkitBlock, old, current);
            world.getCraftServer().getPluginManager().callEvent(eventRedstone);
            flag1 = eventRedstone.getNewCurrent() > 0;
            tileentitycommand.a(flag1);
            if (!flag2 && !tileentitycommand.f() && tileentitycommand.v() != TileEntityCommand.Type.a && flag1) {
               tileentitycommand.j();
               world.a(blockposition, this, 1);
            }
         }
      }
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      TileEntity tileentity = worldserver.c_(blockposition);
      if (tileentity instanceof TileEntityCommand tileentitycommand) {
         CommandBlockListenerAbstract commandblocklistenerabstract = tileentitycommand.c();
         boolean flag = !UtilColor.b(commandblocklistenerabstract.l());
         TileEntityCommand.Type tileentitycommand_type = tileentitycommand.v();
         boolean flag1 = tileentitycommand.i();
         if (tileentitycommand_type == TileEntityCommand.Type.b) {
            tileentitycommand.j();
            if (flag1) {
               this.a(iblockdata, worldserver, blockposition, commandblocklistenerabstract, flag);
            } else if (tileentitycommand.w()) {
               commandblocklistenerabstract.a(0);
            }

            if (tileentitycommand.d() || tileentitycommand.f()) {
               worldserver.a(blockposition, this, 1);
            }
         } else if (tileentitycommand_type == TileEntityCommand.Type.c) {
            if (flag1) {
               this.a(iblockdata, worldserver, blockposition, commandblocklistenerabstract, flag);
            } else if (tileentitycommand.w()) {
               commandblocklistenerabstract.a(0);
            }
         }

         worldserver.c(blockposition, this);
      }
   }

   private void a(IBlockData iblockdata, World world, BlockPosition blockposition, CommandBlockListenerAbstract commandblocklistenerabstract, boolean flag) {
      if (flag) {
         commandblocklistenerabstract.a(world);
      } else {
         commandblocklistenerabstract.a(0);
      }

      a(world, blockposition, iblockdata.c(a));
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
      TileEntity tileentity = world.c_(blockposition);
      if (tileentity instanceof TileEntityCommand && entityhuman.gg()) {
         entityhuman.a((TileEntityCommand)tileentity);
         return EnumInteractionResult.a(world.B);
      } else {
         return EnumInteractionResult.d;
      }
   }

   @Override
   public boolean d_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public int a(IBlockData iblockdata, World world, BlockPosition blockposition) {
      TileEntity tileentity = world.c_(blockposition);
      return tileentity instanceof TileEntityCommand ? ((TileEntityCommand)tileentity).c().j() : 0;
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
      TileEntity tileentity = world.c_(blockposition);
      if (tileentity instanceof TileEntityCommand tileentitycommand) {
         CommandBlockListenerAbstract commandblocklistenerabstract = tileentitycommand.c();
         if (itemstack.z()) {
            commandblocklistenerabstract.b(itemstack.x());
         }

         if (!world.B) {
            if (ItemBlock.a(itemstack) == null) {
               commandblocklistenerabstract.a(world.W().b(GameRules.o));
               tileentitycommand.b(this.d);
            }

            if (tileentitycommand.v() == TileEntityCommand.Type.a) {
               boolean flag = world.r(blockposition);
               tileentitycommand.a(flag);
            }
         }
      }
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

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return this.o().a(a, blockactioncontext.d().g());
   }

   private static void a(World world, BlockPosition blockposition, EnumDirection enumdirection) {
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = blockposition.j();
      GameRules gamerules = world.W();

      IBlockData iblockdata;
      int i;
      for(i = gamerules.c(GameRules.w); i-- > 0; enumdirection = iblockdata.c(a)) {
         blockposition_mutableblockposition.c(enumdirection);
         iblockdata = world.a_(blockposition_mutableblockposition);
         Block block = iblockdata.b();
         if (!iblockdata.a(Blocks.kE)) {
            break;
         }

         TileEntity tileentity = world.c_(blockposition_mutableblockposition);
         if (!(tileentity instanceof TileEntityCommand)) {
            break;
         }

         TileEntityCommand tileentitycommand = (TileEntityCommand)tileentity;
         if (tileentitycommand.v() != TileEntityCommand.Type.a) {
            break;
         }

         if (tileentitycommand.d() || tileentitycommand.f()) {
            CommandBlockListenerAbstract commandblocklistenerabstract = tileentitycommand.c();
            if (tileentitycommand.j()) {
               if (!commandblocklistenerabstract.a(world)) {
                  break;
               }

               world.c(blockposition_mutableblockposition, block);
            } else if (tileentitycommand.w()) {
               commandblocklistenerabstract.a(0);
            }
         }
      }

      if (i <= 0) {
         int j = Math.max(gamerules.c(GameRules.w), 0);
         c.warn("Command Block chain tried to execute more than {} steps!", j);
      }
   }
}
