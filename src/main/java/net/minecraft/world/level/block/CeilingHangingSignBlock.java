package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
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
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyWood;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class CeilingHangingSignBlock extends BlockSign {
   public static final BlockStateInteger a = BlockProperties.ba;
   public static final BlockStateBoolean b = BlockProperties.a;
   protected static final float c = 5.0F;
   protected static final VoxelShape d = Block.a(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
   private static final Map<Integer, VoxelShape> h = Maps.newHashMap(
      ImmutableMap.of(
         0,
         Block.a(1.0, 0.0, 7.0, 15.0, 10.0, 9.0),
         4,
         Block.a(7.0, 0.0, 1.0, 9.0, 10.0, 15.0),
         8,
         Block.a(1.0, 0.0, 7.0, 15.0, 10.0, 9.0),
         12,
         Block.a(7.0, 0.0, 1.0, 9.0, 10.0, 15.0)
      )
   );

   public CeilingHangingSignBlock(BlockBase.Info var0, BlockPropertyWood var1) {
      super(var0.a(var1.e()), var1);
      this.k(this.D.b().a(a, Integer.valueOf(0)).a(b, Boolean.valueOf(false)).a(e, Boolean.valueOf(false)));
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
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return var1.a_(var2.c()).a(var1, var2.c(), EnumDirection.a, EnumBlockSupport.b);
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      World var1 = var0.q();
      Fluid var2 = var1.b_(var0.a());
      BlockPosition var3 = var0.a().c();
      IBlockData var4 = var1.a_(var3);
      boolean var5 = var4.a(TagsBlock.ax);
      EnumDirection var6 = EnumDirection.a((double)var0.i());
      boolean var7 = !Block.a(var4.k(var1, var3), EnumDirection.a) || var0.h();
      if (var5 && !var0.h()) {
         if (var4.b(WallHangingSignBlock.a)) {
            EnumDirection var8 = var4.c(WallHangingSignBlock.a);
            if (var8.o().a(var6)) {
               var7 = false;
            }
         } else if (var4.b(a)) {
            Optional<EnumDirection> var8 = RotationSegment.a(var4.c(a));
            if (var8.isPresent() && var8.get().o().a(var6)) {
               var7 = false;
            }
         }
      }

      int var8 = !var7 ? RotationSegment.a(var6.g()) : RotationSegment.a(var0.i() + 180.0F);
      return this.o().a(b, Boolean.valueOf(var7)).a(a, Integer.valueOf(var8)).a(e, Boolean.valueOf(var2.a() == FluidTypes.c));
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      VoxelShape var4 = h.get(var0.c(a));
      return var4 == null ? d : var4;
   }

   @Override
   public VoxelShape b_(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return this.a(var0, var1, var2, VoxelShapeCollision.a());
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return var1 == EnumDirection.b && !this.a(var0, var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(a, Integer.valueOf(var1.a(var0.c(a), 16)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(a, Integer.valueOf(var1.a(var0.c(a), 16)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, b, e);
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new HangingSignBlockEntity(var0, var1);
   }
}
