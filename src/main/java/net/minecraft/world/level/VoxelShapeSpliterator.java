package net.minecraft.world.level;

import com.google.common.collect.AbstractIterator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.CursorPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class VoxelShapeSpliterator extends AbstractIterator<VoxelShape> {
   private final AxisAlignedBB a;
   private final VoxelShapeCollision b;
   private final CursorPosition c;
   private final BlockPosition.MutableBlockPosition d;
   private final VoxelShape e;
   private final ICollisionAccess f;
   private final boolean g;
   @Nullable
   private IBlockAccess h;
   private long i;

   public VoxelShapeSpliterator(ICollisionAccess var0, @Nullable Entity var1, AxisAlignedBB var2) {
      this(var0, var1, var2, false);
   }

   public VoxelShapeSpliterator(ICollisionAccess var0, @Nullable Entity var1, AxisAlignedBB var2, boolean var3) {
      this.b = var1 == null ? VoxelShapeCollision.a() : VoxelShapeCollision.a(var1);
      this.d = new BlockPosition.MutableBlockPosition();
      this.e = VoxelShapes.a(var2);
      this.f = var0;
      this.a = var2;
      this.g = var3;
      int var4 = MathHelper.a(var2.a - 1.0E-7) - 1;
      int var5 = MathHelper.a(var2.d + 1.0E-7) + 1;
      int var6 = MathHelper.a(var2.b - 1.0E-7) - 1;
      int var7 = MathHelper.a(var2.e + 1.0E-7) + 1;
      int var8 = MathHelper.a(var2.c - 1.0E-7) - 1;
      int var9 = MathHelper.a(var2.f + 1.0E-7) + 1;
      this.c = new CursorPosition(var4, var6, var8, var5, var7, var9);
   }

   @Nullable
   private IBlockAccess a(int var0, int var1) {
      int var2 = SectionPosition.a(var0);
      int var3 = SectionPosition.a(var1);
      long var4 = ChunkCoordIntPair.c(var2, var3);
      if (this.h != null && this.i == var4) {
         return this.h;
      } else {
         IBlockAccess var6 = this.f.c(var2, var3);
         this.h = var6;
         this.i = var4;
         return var6;
      }
   }

   protected VoxelShape a() {
      while(this.c.a()) {
         int var0 = this.c.b();
         int var1 = this.c.c();
         int var2 = this.c.d();
         int var3 = this.c.e();
         if (var3 != 3) {
            IBlockAccess var4 = this.a(var0, var2);
            if (var4 != null) {
               this.d.d(var0, var1, var2);
               IBlockData var5 = var4.a_(this.d);
               if ((!this.g || var5.o(var4, this.d)) && (var3 != 1 || var5.e()) && (var3 != 2 || var5.a(Blocks.bP))) {
                  VoxelShape var6 = var5.b(this.f, this.d, this.b);
                  if (var6 == VoxelShapes.b()) {
                     if (this.a.a((double)var0, (double)var1, (double)var2, (double)var0 + 1.0, (double)var1 + 1.0, (double)var2 + 1.0)) {
                        return var6.a((double)var0, (double)var1, (double)var2);
                     }
                  } else {
                     VoxelShape var7 = var6.a((double)var0, (double)var1, (double)var2);
                     if (VoxelShapes.c(var7, this.e, OperatorBoolean.i)) {
                        return var7;
                     }
                  }
               }
            }
         }
      }

      return (VoxelShape)this.endOfData();
   }
}
