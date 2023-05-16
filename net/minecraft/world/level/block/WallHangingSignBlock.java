package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntitySign;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyWood;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class WallHangingSignBlock extends BlockSign {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   public static final VoxelShape b = Block.a(0.0, 14.0, 6.0, 16.0, 16.0, 10.0);
   public static final VoxelShape c = Block.a(6.0, 14.0, 0.0, 10.0, 16.0, 16.0);
   public static final VoxelShape d = VoxelShapes.a(b, Block.a(1.0, 0.0, 7.0, 15.0, 10.0, 9.0));
   public static final VoxelShape h = VoxelShapes.a(c, Block.a(7.0, 0.0, 1.0, 9.0, 10.0, 15.0));
   private static final Map<EnumDirection, VoxelShape> i = Maps.newEnumMap(
      ImmutableMap.of(EnumDirection.c, d, EnumDirection.d, d, EnumDirection.f, h, EnumDirection.e, h)
   );

   public WallHangingSignBlock(BlockBase.Info var0, BlockPropertyWood var1) {
      super(var0.a(var1.e()), var1);
      this.k(this.D.b().a(a, EnumDirection.c).a(e, Boolean.valueOf(false)));
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      TileEntity var7 = var1.c_(var2);
      if (var7 instanceof TileEntitySign var6) {
         ItemStack var7x = var3.b(var4);
         if (!var6.a(var3) && var7x.c() instanceof ItemBlock) {
            return EnumInteractionResult.d;
         }
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public String h() {
      return this.k().a();
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return i.get(var0.c(a));
   }

   @Override
   public VoxelShape b_(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return this.a(var0, var1, var2, VoxelShapeCollision.a());
   }

   @Override
   public VoxelShape c(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      switch((EnumDirection)var0.c(a)) {
         case f:
         case e:
            return c;
         default:
            return b;
      }
   }

   public boolean b(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      EnumDirection var3 = var0.c(a).h();
      EnumDirection var4 = var0.c(a).i();
      return this.a(var1, var0, var2.a(var3), var4) || this.a(var1, var0, var2.a(var4), var3);
   }

   public boolean a(IWorldReader var0, IBlockData var1, BlockPosition var2, EnumDirection var3) {
      IBlockData var4 = var0.a_(var2);
      return var4.a(TagsBlock.aw) ? var4.c(a).o().a(var1.c(a)) : var4.a(var0, var2, var3, EnumBlockSupport.a);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockData var1 = this.o();
      Fluid var2 = var0.q().b_(var0.a());
      IWorldReader var3 = var0.q();
      BlockPosition var4 = var0.a();

      for(EnumDirection var8 : var0.f()) {
         if (var8.o().d() && !var8.o().a(var0.k())) {
            EnumDirection var9 = var8.g();
            var1 = var1.a(a, var9);
            if (var1.a(var3, var4) && this.b(var1, var3, var4)) {
               return var1.a(e, Boolean.valueOf(var2.a() == FluidTypes.c));
            }
         }
      }

      return null;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return var1.o() == var0.c(a).h().o() && !var0.a(var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(a, var1.a(var0.c(a)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(var1.a(var0.c(a)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, e);
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new HangingSignBlockEntity(var0, var1);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
