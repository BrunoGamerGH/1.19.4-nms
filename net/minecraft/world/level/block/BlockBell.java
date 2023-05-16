package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBell;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyBellAttach;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockBell extends BlockTileEntity {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   public static final BlockStateEnum<BlockPropertyBellAttach> b = BlockProperties.V;
   public static final BlockStateBoolean c = BlockProperties.w;
   private static final VoxelShape e = Block.a(0.0, 0.0, 4.0, 16.0, 16.0, 12.0);
   private static final VoxelShape f = Block.a(4.0, 0.0, 0.0, 12.0, 16.0, 16.0);
   private static final VoxelShape g = Block.a(5.0, 6.0, 5.0, 11.0, 13.0, 11.0);
   private static final VoxelShape h = Block.a(4.0, 4.0, 4.0, 12.0, 6.0, 12.0);
   private static final VoxelShape i = VoxelShapes.a(h, g);
   private static final VoxelShape j = VoxelShapes.a(i, Block.a(7.0, 13.0, 0.0, 9.0, 15.0, 16.0));
   private static final VoxelShape k = VoxelShapes.a(i, Block.a(0.0, 13.0, 7.0, 16.0, 15.0, 9.0));
   private static final VoxelShape l = VoxelShapes.a(i, Block.a(0.0, 13.0, 7.0, 13.0, 15.0, 9.0));
   private static final VoxelShape m = VoxelShapes.a(i, Block.a(3.0, 13.0, 7.0, 16.0, 15.0, 9.0));
   private static final VoxelShape n = VoxelShapes.a(i, Block.a(7.0, 13.0, 0.0, 9.0, 15.0, 13.0));
   private static final VoxelShape E = VoxelShapes.a(i, Block.a(7.0, 13.0, 3.0, 9.0, 15.0, 16.0));
   private static final VoxelShape F = VoxelShapes.a(i, Block.a(7.0, 13.0, 7.0, 9.0, 16.0, 9.0));
   public static final int d = 1;

   public BlockBell(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, EnumDirection.c).a(b, BlockPropertyBellAttach.a).a(c, Boolean.valueOf(false)));
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, Block var3, BlockPosition var4, boolean var5) {
      boolean var6 = var1.r(var2);
      if (var6 != var0.c(c)) {
         if (var6) {
            this.a(var1, var2, null);
         }

         var1.a(var2, var0.a(c, Boolean.valueOf(var6)), 3);
      }
   }

   @Override
   public void a(World var0, IBlockData var1, MovingObjectPositionBlock var2, IProjectile var3) {
      Entity var4 = var3.v();
      EntityHuman var5 = var4 instanceof EntityHuman ? (EntityHuman)var4 : null;
      this.a(var0, var1, var2, var5, true);
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      return this.a(var1, var0, var5, var3, true) ? EnumInteractionResult.a(var1.B) : EnumInteractionResult.d;
   }

   public boolean a(World var0, IBlockData var1, MovingObjectPositionBlock var2, @Nullable EntityHuman var3, boolean var4) {
      EnumDirection var5 = var2.b();
      BlockPosition var6 = var2.a();
      boolean var7 = !var4 || this.a(var1, var5, var2.e().d - (double)var6.v());
      if (var7) {
         boolean var8 = this.a(var3, var0, var6, var5);
         if (var8 && var3 != null) {
            var3.a(StatisticList.az);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean a(IBlockData var0, EnumDirection var1, double var2) {
      if (var1.o() != EnumDirection.EnumAxis.b && !(var2 > 0.8124F)) {
         EnumDirection var4 = var0.c(a);
         BlockPropertyBellAttach var5 = var0.c(b);
         switch(var5) {
            case a:
               return var4.o() == var1.o();
            case c:
            case d:
               return var4.o() != var1.o();
            case b:
               return true;
            default:
               return false;
         }
      } else {
         return false;
      }
   }

   public boolean a(World var0, BlockPosition var1, @Nullable EnumDirection var2) {
      return this.a(null, var0, var1, var2);
   }

   public boolean a(@Nullable Entity var0, World var1, BlockPosition var2, @Nullable EnumDirection var3) {
      TileEntity var4 = var1.c_(var2);
      if (!var1.B && var4 instanceof TileEntityBell) {
         if (var3 == null) {
            var3 = var1.a_(var2).c(a);
         }

         ((TileEntityBell)var4).a(var3);
         var1.a(null, var2, SoundEffects.bJ, SoundCategory.e, 2.0F, 1.0F);
         var1.a(var0, GameEvent.c, var2);
         return true;
      } else {
         return false;
      }
   }

   private VoxelShape h(IBlockData var0) {
      EnumDirection var1 = var0.c(a);
      BlockPropertyBellAttach var2 = var0.c(b);
      if (var2 == BlockPropertyBellAttach.a) {
         return var1 != EnumDirection.c && var1 != EnumDirection.d ? f : e;
      } else if (var2 == BlockPropertyBellAttach.b) {
         return F;
      } else if (var2 == BlockPropertyBellAttach.d) {
         return var1 != EnumDirection.c && var1 != EnumDirection.d ? k : j;
      } else if (var1 == EnumDirection.c) {
         return n;
      } else if (var1 == EnumDirection.d) {
         return E;
      } else {
         return var1 == EnumDirection.f ? m : l;
      }
   }

   @Override
   public VoxelShape c(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return this.h(var0);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return this.h(var0);
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.c;
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      EnumDirection var2 = var0.k();
      BlockPosition var3 = var0.a();
      World var4 = var0.q();
      EnumDirection.EnumAxis var5 = var2.o();
      if (var5 == EnumDirection.EnumAxis.b) {
         IBlockData var1 = this.o().a(b, var2 == EnumDirection.a ? BlockPropertyBellAttach.b : BlockPropertyBellAttach.a).a(a, var0.g());
         if (var1.a((IWorldReader)var0.q(), var3)) {
            return var1;
         }
      } else {
         boolean var6 = var5 == EnumDirection.EnumAxis.a
               && var4.a_(var3.g()).d(var4, var3.g(), EnumDirection.f)
               && var4.a_(var3.h()).d(var4, var3.h(), EnumDirection.e)
            || var5 == EnumDirection.EnumAxis.c
               && var4.a_(var3.e()).d(var4, var3.e(), EnumDirection.d)
               && var4.a_(var3.f()).d(var4, var3.f(), EnumDirection.c);
         IBlockData var1 = this.o().a(a, var2.g()).a(b, var6 ? BlockPropertyBellAttach.d : BlockPropertyBellAttach.c);
         if (var1.a((IWorldReader)var0.q(), var0.a())) {
            return var1;
         }

         boolean var7 = var4.a_(var3.d()).d(var4, var3.d(), EnumDirection.b);
         var1 = var1.a(b, var7 ? BlockPropertyBellAttach.a : BlockPropertyBellAttach.b);
         if (var1.a((IWorldReader)var0.q(), var0.a())) {
            return var1;
         }
      }

      return null;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      BlockPropertyBellAttach var6 = var0.c(b);
      EnumDirection var7 = n(var0).g();
      if (var7 == var1 && !var0.a(var3, var4) && var6 != BlockPropertyBellAttach.d) {
         return Blocks.a.o();
      } else {
         if (var1.o() == var0.c(a).o()) {
            if (var6 == BlockPropertyBellAttach.d && !var2.d(var3, var5, var1)) {
               return var0.a(b, BlockPropertyBellAttach.c).a(a, var1.g());
            }

            if (var6 == BlockPropertyBellAttach.c && var7.g() == var1 && var2.d(var3, var5, var0.c(a))) {
               return var0.a(b, BlockPropertyBellAttach.d);
            }
         }

         return super.a(var0, var1, var2, var3, var4, var5);
      }
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      EnumDirection var3 = n(var0).g();
      return var3 == EnumDirection.b ? Block.a(var1, var2.c(), EnumDirection.a) : BlockAttachable.b(var1, var2, var3);
   }

   private static EnumDirection n(IBlockData var0) {
      switch((BlockPropertyBellAttach)var0.c(b)) {
         case a:
            return EnumDirection.b;
         case b:
            return EnumDirection.a;
         default:
            return var0.c(a).g();
      }
   }

   @Override
   public EnumPistonReaction d(IBlockData var0) {
      return EnumPistonReaction.b;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, b, c);
   }

   @Nullable
   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntityBell(var0, var1);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World var0, IBlockData var1, TileEntityTypes<T> var2) {
      return a(var2, TileEntityTypes.E, var0.B ? TileEntityBell::a : TileEntityBell::b);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
