package net.minecraft.world.level;

import com.google.common.collect.Iterables;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public interface ICollisionAccess extends IBlockAccess {
   WorldBorder p_();

   @Nullable
   IBlockAccess c(int var1, int var2);

   default boolean a(@Nullable Entity var0, VoxelShape var1) {
      return true;
   }

   default boolean a(IBlockData var0, BlockPosition var1, VoxelShapeCollision var2) {
      VoxelShape var3 = var0.b(this, var1, var2);
      return var3.b() || this.a(null, var3.a((double)var1.u(), (double)var1.v(), (double)var1.w()));
   }

   default boolean f(Entity var0) {
      return this.a(var0, VoxelShapes.a(var0.cD()));
   }

   default boolean b(AxisAlignedBB var0) {
      return this.a(null, var0);
   }

   default boolean g(Entity var0) {
      return this.a(var0, var0.cD());
   }

   default boolean a(@Nullable Entity var0, AxisAlignedBB var1) {
      for(VoxelShape var3 : this.d(var0, var1)) {
         if (!var3.b()) {
            return false;
         }
      }

      if (!this.b(var0, var1).isEmpty()) {
         return false;
      } else if (var0 == null) {
         return true;
      } else {
         VoxelShape var2 = this.f(var0, var1);
         return var2 == null || !VoxelShapes.c(var2, VoxelShapes.a(var1), OperatorBoolean.i);
      }
   }

   List<VoxelShape> b(@Nullable Entity var1, AxisAlignedBB var2);

   default Iterable<VoxelShape> c(@Nullable Entity var0, AxisAlignedBB var1) {
      List<VoxelShape> var2 = this.b(var0, var1);
      Iterable<VoxelShape> var3 = this.d(var0, var1);
      return var2.isEmpty() ? var3 : Iterables.concat(var2, var3);
   }

   default Iterable<VoxelShape> d(@Nullable Entity var0, AxisAlignedBB var1) {
      return () -> new VoxelShapeSpliterator(this, var0, var1);
   }

   @Nullable
   private VoxelShape f(Entity var0, AxisAlignedBB var1) {
      WorldBorder var2 = this.p_();
      return var2.a(var0, var1) ? var2.c() : null;
   }

   default boolean e(@Nullable Entity var0, AxisAlignedBB var1) {
      VoxelShapeSpliterator var2 = new VoxelShapeSpliterator(this, var0, var1, true);

      while(var2.hasNext()) {
         if (!((VoxelShape)var2.next()).b()) {
            return true;
         }
      }

      return false;
   }

   default Optional<Vec3D> a(@Nullable Entity var0, VoxelShape var1, Vec3D var2, double var3, double var5, double var7) {
      if (var1.b()) {
         return Optional.empty();
      } else {
         AxisAlignedBB var9 = var1.a().c(var3, var5, var7);
         VoxelShape var10 = StreamSupport.stream(this.d(var0, var9).spliterator(), false)
            .filter(var0x -> this.p_() == null || this.p_().a(var0x.a()))
            .flatMap(var0x -> var0x.d().stream())
            .map(var6x -> var6x.c(var3 / 2.0, var5 / 2.0, var7 / 2.0))
            .map(VoxelShapes::a)
            .reduce(VoxelShapes.a(), VoxelShapes::a);
         VoxelShape var11 = VoxelShapes.a(var1, var10, OperatorBoolean.e);
         return var11.a(var2);
      }
   }
}
