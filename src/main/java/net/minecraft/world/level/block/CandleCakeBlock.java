package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class CandleCakeBlock extends AbstractCandleBlock {
   public static final BlockStateBoolean c = AbstractCandleBlock.b;
   protected static final float d = 1.0F;
   protected static final VoxelShape e = Block.a(1.0, 0.0, 1.0, 15.0, 8.0, 15.0);
   protected static final VoxelShape f = Block.a(7.0, 8.0, 7.0, 9.0, 14.0, 9.0);
   protected static final VoxelShape g = VoxelShapes.a(e, f);
   private static final Map<Block, CandleCakeBlock> h = Maps.newHashMap();
   private static final Iterable<Vec3D> i = ImmutableList.of(new Vec3D(0.5, 1.0, 0.5));

   protected CandleCakeBlock(Block var0, BlockBase.Info var1) {
      super(var1);
      this.k(this.D.b().a(c, Boolean.valueOf(false)));
      h.put(var0, this);
   }

   @Override
   protected Iterable<Vec3D> a(IBlockData var0) {
      return i;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return g;
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      ItemStack var6 = var3.b(var4);
      if (var6.a(Items.nA) || var6.a(Items.tb)) {
         return EnumInteractionResult.d;
      } else if (a(var5) && var3.b(var4).b() && var0.c(c)) {
         a(var3, var0, var1, var2);
         return EnumInteractionResult.a(var1.B);
      } else {
         EnumInteractionResult var7 = BlockCake.a(var1, var2, Blocks.eg.o(), var3);
         if (var7.a()) {
            c(var0, var1, var2);
         }

         return var7;
      }
   }

   private static boolean a(MovingObjectPositionBlock var0) {
      return var0.e().d - (double)var0.a().v() > 0.5;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(c);
   }

   @Override
   public ItemStack a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      return new ItemStack(Blocks.eg);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return var1 == EnumDirection.a && !var0.a(var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return var1.a_(var2.d()).d().b();
   }

   @Override
   public int a(IBlockData var0, World var1, BlockPosition var2) {
      return BlockCake.c;
   }

   @Override
   public boolean d_(IBlockData var0) {
      return true;
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }

   public static IBlockData a(Block var0) {
      return h.get(var0).o();
   }

   public static boolean g(IBlockData var0) {
      return var0.a(TagsBlock.bh, var1 -> var1.b(c) && !var0.c(c));
   }
}
