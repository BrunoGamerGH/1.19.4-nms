package net.minecraft.world.level;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface IBlockAccess extends LevelHeightAccessor {
   @Nullable
   TileEntity c_(BlockPosition var1);

   default <T extends TileEntity> Optional<T> a(BlockPosition blockposition, TileEntityTypes<T> tileentitytypes) {
      TileEntity tileentity = this.c_(blockposition);
      return tileentity != null && tileentity.u() == tileentitytypes ? Optional.of((T)tileentity) : Optional.empty();
   }

   IBlockData a_(BlockPosition var1);

   Fluid b_(BlockPosition var1);

   default int h(BlockPosition blockposition) {
      return this.a_(blockposition).g();
   }

   default int L() {
      return 15;
   }

   default Stream<IBlockData> a(AxisAlignedBB axisalignedbb) {
      return BlockPosition.a(axisalignedbb).map(this::a_);
   }

   default MovingObjectPositionBlock a(ClipBlockStateContext clipblockstatecontext) {
      return a(
         clipblockstatecontext.b(),
         clipblockstatecontext.a(),
         clipblockstatecontext,
         (clipblockstatecontext1, blockposition) -> {
            IBlockData iblockdata = this.a_(blockposition);
            Vec3D vec3d = clipblockstatecontext1.b().d(clipblockstatecontext1.a());
            return clipblockstatecontext1.c().test(iblockdata)
               ? new MovingObjectPositionBlock(
                  clipblockstatecontext1.a(), EnumDirection.a(vec3d.c, vec3d.d, vec3d.e), BlockPosition.a(clipblockstatecontext1.a()), false
               )
               : null;
         },
         clipblockstatecontext1 -> {
            Vec3D vec3d = clipblockstatecontext1.b().d(clipblockstatecontext1.a());
            return MovingObjectPositionBlock.a(
               clipblockstatecontext1.a(), EnumDirection.a(vec3d.c, vec3d.d, vec3d.e), BlockPosition.a(clipblockstatecontext1.a())
            );
         }
      );
   }

   default MovingObjectPositionBlock clip(RayTrace raytrace1, BlockPosition blockposition) {
      IBlockData iblockdata = this.a_(blockposition);
      Fluid fluid = this.b_(blockposition);
      Vec3D vec3d = raytrace1.b();
      Vec3D vec3d1 = raytrace1.a();
      VoxelShape voxelshape = raytrace1.a(iblockdata, this, blockposition);
      MovingObjectPositionBlock movingobjectpositionblock = this.a(vec3d, vec3d1, blockposition, voxelshape, iblockdata);
      VoxelShape voxelshape1 = raytrace1.a(fluid, this, blockposition);
      MovingObjectPositionBlock movingobjectpositionblock1 = voxelshape1.a(vec3d, vec3d1, blockposition);
      double d0 = movingobjectpositionblock == null ? Double.MAX_VALUE : raytrace1.b().g(movingobjectpositionblock.e());
      double d1 = movingobjectpositionblock1 == null ? Double.MAX_VALUE : raytrace1.b().g(movingobjectpositionblock1.e());
      return d0 <= d1 ? movingobjectpositionblock : movingobjectpositionblock1;
   }

   default MovingObjectPositionBlock a(RayTrace raytrace) {
      return a(raytrace.b(), raytrace.a(), raytrace, (raytrace1, blockposition) -> this.clip(raytrace1, blockposition), raytrace1 -> {
         Vec3D vec3d = raytrace1.b().d(raytrace1.a());
         return MovingObjectPositionBlock.a(raytrace1.a(), EnumDirection.a(vec3d.c, vec3d.d, vec3d.e), BlockPosition.a(raytrace1.a()));
      });
   }

   @Nullable
   default MovingObjectPositionBlock a(Vec3D vec3d, Vec3D vec3d1, BlockPosition blockposition, VoxelShape voxelshape, IBlockData iblockdata) {
      MovingObjectPositionBlock movingobjectpositionblock = voxelshape.a(vec3d, vec3d1, blockposition);
      if (movingobjectpositionblock != null) {
         MovingObjectPositionBlock movingobjectpositionblock1 = iblockdata.m(this, blockposition).a(vec3d, vec3d1, blockposition);
         if (movingobjectpositionblock1 != null && movingobjectpositionblock1.e().d(vec3d).g() < movingobjectpositionblock.e().d(vec3d).g()) {
            return movingobjectpositionblock.a(movingobjectpositionblock1.b());
         }
      }

      return movingobjectpositionblock;
   }

   default double a(VoxelShape voxelshape, Supplier<VoxelShape> supplier) {
      if (!voxelshape.b()) {
         return voxelshape.c(EnumDirection.EnumAxis.b);
      } else {
         double d0 = supplier.get().c(EnumDirection.EnumAxis.b);
         return d0 >= 1.0 ? d0 - 1.0 : Double.NEGATIVE_INFINITY;
      }
   }

   default double i(BlockPosition blockposition) {
      return this.a(this.a_(blockposition).k(this, blockposition), () -> {
         BlockPosition blockposition1 = blockposition.d();
         return this.a_(blockposition1).k(this, blockposition1);
      });
   }

   static <T, C> T a(Vec3D vec3d, Vec3D vec3d1, C c0, BiFunction<C, BlockPosition, T> bifunction, Function<C, T> function) {
      if (vec3d.equals(vec3d1)) {
         return function.apply(c0);
      } else {
         double d0 = MathHelper.d(-1.0E-7, vec3d1.c, vec3d.c);
         double d1 = MathHelper.d(-1.0E-7, vec3d1.d, vec3d.d);
         double d2 = MathHelper.d(-1.0E-7, vec3d1.e, vec3d.e);
         double d3 = MathHelper.d(-1.0E-7, vec3d.c, vec3d1.c);
         double d4 = MathHelper.d(-1.0E-7, vec3d.d, vec3d1.d);
         double d5 = MathHelper.d(-1.0E-7, vec3d.e, vec3d1.e);
         int i = MathHelper.a(d3);
         int j = MathHelper.a(d4);
         int k = MathHelper.a(d5);
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition(i, j, k);
         T t0 = bifunction.apply(c0, blockposition_mutableblockposition);
         if (t0 != null) {
            return t0;
         } else {
            double d6 = d0 - d3;
            double d7 = d1 - d4;
            double d8 = d2 - d5;
            int l = MathHelper.j(d6);
            int i1 = MathHelper.j(d7);
            int j1 = MathHelper.j(d8);
            double d9 = l == 0 ? Double.MAX_VALUE : (double)l / d6;
            double d10 = i1 == 0 ? Double.MAX_VALUE : (double)i1 / d7;
            double d11 = j1 == 0 ? Double.MAX_VALUE : (double)j1 / d8;
            double d12 = d9 * (l > 0 ? 1.0 - MathHelper.e(d3) : MathHelper.e(d3));
            double d13 = d10 * (i1 > 0 ? 1.0 - MathHelper.e(d4) : MathHelper.e(d4));
            double d14 = d11 * (j1 > 0 ? 1.0 - MathHelper.e(d5) : MathHelper.e(d5));

            while(!(d12 > 1.0) || !(d13 > 1.0) || !(d14 > 1.0)) {
               if (d12 < d13) {
                  if (d12 < d14) {
                     i += l;
                     d12 += d9;
                  } else {
                     k += j1;
                     d14 += d11;
                  }
               } else if (d13 < d14) {
                  j += i1;
                  d13 += d10;
               } else {
                  k += j1;
                  d14 += d11;
               }

               T object = bifunction.apply(c0, blockposition_mutableblockposition.d(i, j, k));
               if (object != null) {
                  return object;
               }
            }

            return function.apply(c0);
         }
      }
   }
}
