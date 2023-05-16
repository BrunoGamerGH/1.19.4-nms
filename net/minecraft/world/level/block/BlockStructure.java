package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityStructure;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyStructureMode;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class BlockStructure extends BlockTileEntity implements GameMasterBlock {
   public static final BlockStateEnum<BlockPropertyStructureMode> a = BlockProperties.bj;

   protected BlockStructure(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, BlockPropertyStructureMode.b));
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntityStructure(var0, var1);
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      TileEntity var6 = var1.c_(var2);
      if (var6 instanceof TileEntityStructure) {
         return ((TileEntityStructure)var6).a(var3) ? EnumInteractionResult.a(var1.B) : EnumInteractionResult.d;
      } else {
         return EnumInteractionResult.d;
      }
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, @Nullable EntityLiving var3, ItemStack var4) {
      if (!var0.B) {
         if (var3 != null) {
            TileEntity var5 = var0.c_(var1);
            if (var5 instanceof TileEntityStructure) {
               ((TileEntityStructure)var5).a(var3);
            }
         }
      }
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.c;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, Block var3, BlockPosition var4, boolean var5) {
      if (var1 instanceof WorldServer) {
         TileEntity var6 = var1.c_(var2);
         if (var6 instanceof TileEntityStructure) {
            TileEntityStructure var7 = (TileEntityStructure)var6;
            boolean var8 = var1.r(var2);
            boolean var9 = var7.G();
            if (var8 && !var9) {
               var7.c(true);
               this.a((WorldServer)var1, var7);
            } else if (!var8 && var9) {
               var7.c(false);
            }
         }
      }
   }

   private void a(WorldServer var0, TileEntityStructure var1) {
      switch(var1.y()) {
         case a:
            var1.b(false);
            break;
         case b:
            var1.a(var0, false);
            break;
         case c:
            var1.E();
         case d:
      }
   }
}
