package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.function.ToIntFunction;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class CandleBlock extends AbstractCandleBlock implements IBlockWaterlogged {
   public static final int c = 1;
   public static final int d = 4;
   public static final BlockStateInteger e = BlockProperties.az;
   public static final BlockStateBoolean f = AbstractCandleBlock.b;
   public static final BlockStateBoolean g = BlockProperties.C;
   public static final ToIntFunction<IBlockData> h = var0 -> var0.c(f) ? 3 * var0.c(e) : 0;
   private static final Int2ObjectMap<List<Vec3D>> i = SystemUtils.a(() -> {
      Int2ObjectMap<List<Vec3D>> var0 = new Int2ObjectOpenHashMap();
      var0.defaultReturnValue(ImmutableList.of());
      var0.put(1, ImmutableList.of(new Vec3D(0.5, 0.5, 0.5)));
      var0.put(2, ImmutableList.of(new Vec3D(0.375, 0.44, 0.5), new Vec3D(0.625, 0.5, 0.44)));
      var0.put(3, ImmutableList.of(new Vec3D(0.5, 0.313, 0.625), new Vec3D(0.375, 0.44, 0.5), new Vec3D(0.56, 0.5, 0.44)));
      var0.put(4, ImmutableList.of(new Vec3D(0.44, 0.313, 0.56), new Vec3D(0.625, 0.44, 0.56), new Vec3D(0.375, 0.44, 0.375), new Vec3D(0.56, 0.5, 0.375)));
      return Int2ObjectMaps.unmodifiable(var0);
   });
   private static final VoxelShape j = Block.a(7.0, 0.0, 7.0, 9.0, 6.0, 9.0);
   private static final VoxelShape k = Block.a(5.0, 0.0, 6.0, 11.0, 6.0, 9.0);
   private static final VoxelShape l = Block.a(5.0, 0.0, 6.0, 10.0, 6.0, 11.0);
   private static final VoxelShape m = Block.a(5.0, 0.0, 5.0, 11.0, 6.0, 10.0);

   public CandleBlock(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(e, Integer.valueOf(1)).a(f, Boolean.valueOf(false)).a(g, Boolean.valueOf(false)));
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (var3.fK().e && var3.b(var4).b() && var0.c(f)) {
         a(var3, var0, var1, var2);
         return EnumInteractionResult.a(var1.B);
      } else {
         return EnumInteractionResult.d;
      }
   }

   @Override
   public boolean a(IBlockData var0, BlockActionContext var1) {
      return !var1.h() && var1.n().c() == this.k() && var0.c(e) < 4 ? true : super.a(var0, var1);
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockData var1 = var0.q().a_(var0.a());
      if (var1.a(this)) {
         return var1.a(e);
      } else {
         Fluid var2 = var0.q().b_(var0.a());
         boolean var3 = var2.a() == FluidTypes.c;
         return super.a(var0).a(g, Boolean.valueOf(var3));
      }
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(g)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(g) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      switch(var0.c(e)) {
         case 1:
         default:
            return j;
         case 2:
            return k;
         case 3:
            return l;
         case 4:
            return m;
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(e, f, g);
   }

   @Override
   public boolean a(GeneratorAccess var0, BlockPosition var1, IBlockData var2, Fluid var3) {
      if (!var2.c(g) && var3.a() == FluidTypes.c) {
         IBlockData var4 = var2.a(g, Boolean.valueOf(true));
         if (var2.c(f)) {
            a(null, var4, var0, var1);
         } else {
            var0.a(var1, var4, 3);
         }

         var0.a(var1, var3.a(), var3.a().a(var0));
         return true;
      } else {
         return false;
      }
   }

   public static boolean g(IBlockData var0) {
      return var0.a(TagsBlock.ad, var0x -> var0x.b(f) && var0x.b(g)) && !var0.c(f) && !var0.c(g);
   }

   @Override
   protected Iterable<Vec3D> a(IBlockData var0) {
      return (Iterable<Vec3D>)i.get(var0.c(e));
   }

   @Override
   protected boolean c(IBlockData var0) {
      return !var0.c(g) && super.c(var0);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return Block.a(var1, var2.d(), EnumDirection.b);
   }
}
