package net.minecraft.world.level;

import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class RayTrace {
   private final Vec3D a;
   private final Vec3D b;
   private final RayTrace.BlockCollisionOption c;
   private final RayTrace.FluidCollisionOption d;
   private final VoxelShapeCollision e;

   public RayTrace(
      Vec3D vec3d,
      Vec3D vec3d1,
      RayTrace.BlockCollisionOption raytrace_blockcollisionoption,
      RayTrace.FluidCollisionOption raytrace_fluidcollisionoption,
      Entity entity
   ) {
      this.a = vec3d;
      this.b = vec3d1;
      this.c = raytrace_blockcollisionoption;
      this.d = raytrace_fluidcollisionoption;
      this.e = entity == null ? VoxelShapeCollision.a() : VoxelShapeCollision.a(entity);
   }

   public Vec3D a() {
      return this.b;
   }

   public Vec3D b() {
      return this.a;
   }

   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return this.c.get(iblockdata, iblockaccess, blockposition, this.e);
   }

   public VoxelShape a(Fluid fluid, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return this.d.a(fluid) ? fluid.d(iblockaccess, blockposition) : VoxelShapes.a();
   }

   public static enum BlockCollisionOption implements RayTrace.c {
      a(BlockBase.BlockData::b),
      b(BlockBase.BlockData::a),
      c(BlockBase.BlockData::c),
      d((iblockdata, iblockaccess, blockposition, voxelshapecollision) -> iblockdata.a(TagsBlock.aN) ? VoxelShapes.b() : VoxelShapes.a());

      private final RayTrace.c e;

      private BlockCollisionOption(RayTrace.c raytrace_c) {
         this.e = raytrace_c;
      }

      @Override
      public VoxelShape get(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
         return this.e.get(iblockdata, iblockaccess, blockposition, voxelshapecollision);
      }
   }

   public static enum FluidCollisionOption {
      a(fluid -> false),
      b(Fluid::b),
      c(fluid -> !fluid.c()),
      d(fluid -> fluid.a(TagsFluid.a));

      private final Predicate<Fluid> e;

      private FluidCollisionOption(Predicate<Fluid> predicate) {
         this.e = predicate;
      }

      public boolean a(Fluid fluid) {
         return this.e.test(fluid);
      }
   }

   public interface c {
      VoxelShape get(IBlockData var1, IBlockAccess var2, BlockPosition var3, VoxelShapeCollision var4);
   }
}
