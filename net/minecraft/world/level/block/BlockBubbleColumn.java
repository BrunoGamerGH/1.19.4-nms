package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockBubbleColumn extends Block implements IFluidSource {
   public static final BlockStateBoolean a = BlockProperties.e;
   private static final int b = 5;

   public BlockBubbleColumn(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, Boolean.valueOf(true)));
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, Entity var3) {
      IBlockData var4 = var1.a_(var2.c());
      if (var4.h()) {
         var3.k(var0.c(a));
         if (!var1.B) {
            WorldServer var5 = (WorldServer)var1;

            for(int var6 = 0; var6 < 2; ++var6) {
               var5.a(Particles.ai, (double)var2.u() + var1.z.j(), (double)(var2.v() + 1), (double)var2.w() + var1.z.j(), 1, 0.0, 0.0, 0.0, 1.0);
               var5.a(Particles.e, (double)var2.u() + var1.z.j(), (double)(var2.v() + 1), (double)var2.w() + var1.z.j(), 1, 0.0, 0.01, 0.0, 0.2);
            }
         }
      } else {
         var3.l(var0.c(a));
      }
   }

   @Override
   public void a(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      a(var1, var2, var0, var1.a_(var2.d()));
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return FluidTypes.c.a(false);
   }

   public static void b(GeneratorAccess var0, BlockPosition var1, IBlockData var2) {
      a(var0, var1, var0.a_(var1), var2);
   }

   public static void a(GeneratorAccess var0, BlockPosition var1, IBlockData var2, IBlockData var3) {
      if (h(var2)) {
         IBlockData var4 = n(var3);
         var0.a(var1, var4, 2);
         BlockPosition.MutableBlockPosition var5 = var1.j().c(EnumDirection.b);

         while(h(var0.a_(var5))) {
            if (!var0.a(var5, var4, 2)) {
               return;
            }

            var5.c(EnumDirection.b);
         }
      }
   }

   private static boolean h(IBlockData var0) {
      return var0.a(Blocks.mZ) || var0.a(Blocks.G) && var0.r().e() >= 8 && var0.r().b();
   }

   private static IBlockData n(IBlockData var0) {
      if (var0.a(Blocks.mZ)) {
         return var0;
      } else if (var0.a(Blocks.dW)) {
         return Blocks.mZ.o().a(a, Boolean.valueOf(false));
      } else {
         return var0.a(Blocks.kG) ? Blocks.mZ.o().a(a, Boolean.valueOf(true)) : Blocks.G.o();
      }
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      double var4 = (double)var2.u();
      double var6 = (double)var2.v();
      double var8 = (double)var2.w();
      if (var0.c(a)) {
         var1.b(Particles.al, var4 + 0.5, var6 + 0.8, var8, 0.0, 0.0, 0.0);
         if (var3.a(200) == 0) {
            var1.a(var4, var6, var8, SoundEffects.cp, SoundCategory.e, 0.2F + var3.i() * 0.2F, 0.9F + var3.i() * 0.15F, false);
         }
      } else {
         var1.b(Particles.am, var4 + 0.5, var6, var8 + 0.5, 0.0, 0.04, 0.0);
         var1.b(Particles.am, var4 + (double)var3.i(), var6 + (double)var3.i(), var8 + (double)var3.i(), 0.0, 0.04, 0.0);
         if (var3.a(200) == 0) {
            var1.a(var4, var6, var8, SoundEffects.cn, SoundCategory.e, 0.2F + var3.i() * 0.2F, 0.9F + var3.i() * 0.15F, false);
         }
      }
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      if (!var0.a(var3, var4) || var1 == EnumDirection.a || var1 == EnumDirection.b && !var2.a(Blocks.mZ) && h(var2)) {
         var3.a(var4, this, 5);
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      IBlockData var3 = var1.a_(var2.d());
      return var3.a(Blocks.mZ) || var3.a(Blocks.kG) || var3.a(Blocks.dW);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return VoxelShapes.a();
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.a;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a);
   }

   @Override
   public ItemStack c(GeneratorAccess var0, BlockPosition var1, IBlockData var2) {
      var0.a(var1, Blocks.a.o(), 11);
      return new ItemStack(Items.pH);
   }

   @Override
   public Optional<SoundEffect> an_() {
      return FluidTypes.c.j();
   }
}
