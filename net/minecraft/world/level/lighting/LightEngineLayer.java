package net.minecraft.world.level.lighting;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.SectionPosition;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ILightAccess;
import net.minecraft.world.level.chunk.NibbleArray;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.apache.commons.lang3.mutable.MutableInt;

public abstract class LightEngineLayer<M extends LightEngineStorageArray<M>, S extends LightEngineStorage<M>>
   extends LightEngineGraph
   implements LightEngineLayerEventListener {
   public static final long a = Long.MAX_VALUE;
   private static final EnumDirection[] f = EnumDirection.values();
   protected final ILightAccess b;
   protected final EnumSkyBlock c;
   protected final S d;
   private boolean g;
   protected final BlockPosition.MutableBlockPosition e = new BlockPosition.MutableBlockPosition();
   private static final int h = 2;
   private final long[] i = new long[2];
   private final IBlockAccess[] j = new IBlockAccess[2];

   public LightEngineLayer(ILightAccess var0, EnumSkyBlock var1, S var2) {
      super(16, 256, 8192);
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.d();
   }

   @Override
   protected void f(long var0) {
      this.d.d();
      if (this.d.g(SectionPosition.e(var0))) {
         super.f(var0);
      }
   }

   @Nullable
   private IBlockAccess a(int var0, int var1) {
      long var2 = ChunkCoordIntPair.c(var0, var1);

      for(int var4 = 0; var4 < 2; ++var4) {
         if (var2 == this.i[var4]) {
            return this.j[var4];
         }
      }

      IBlockAccess var4 = this.b.c(var0, var1);

      for(int var5 = 1; var5 > 0; --var5) {
         this.i[var5] = this.i[var5 - 1];
         this.j[var5] = this.j[var5 - 1];
      }

      this.i[0] = var2;
      this.j[0] = var4;
      return var4;
   }

   private void d() {
      Arrays.fill(this.i, ChunkCoordIntPair.a);
      Arrays.fill(this.j, null);
   }

   protected IBlockData a(long var0, @Nullable MutableInt var2) {
      if (var0 == Long.MAX_VALUE) {
         if (var2 != null) {
            var2.setValue(0);
         }

         return Blocks.a.o();
      } else {
         int var3 = SectionPosition.a(BlockPosition.a(var0));
         int var4 = SectionPosition.a(BlockPosition.c(var0));
         IBlockAccess var5 = this.a(var3, var4);
         if (var5 == null) {
            if (var2 != null) {
               var2.setValue(16);
            }

            return Blocks.F.o();
         } else {
            this.e.f(var0);
            IBlockData var6 = var5.a_(this.e);
            boolean var7 = var6.m() && var6.f();
            if (var2 != null) {
               var2.setValue(var6.b(this.b.q(), this.e));
            }

            return var7 ? var6 : Blocks.a.o();
         }
      }
   }

   protected VoxelShape a(IBlockData var0, long var1, EnumDirection var3) {
      return var0.m() ? var0.a(this.b.q(), this.e.f(var1), var3) : VoxelShapes.a();
   }

   public static int a(IBlockAccess var0, IBlockData var1, BlockPosition var2, IBlockData var3, BlockPosition var4, EnumDirection var5, int var6) {
      boolean var7 = var1.m() && var1.f();
      boolean var8 = var3.m() && var3.f();
      if (!var7 && !var8) {
         return var6;
      } else {
         VoxelShape var9 = var7 ? var1.c(var0, var2) : VoxelShapes.a();
         VoxelShape var10 = var8 ? var3.c(var0, var4) : VoxelShapes.a();
         return VoxelShapes.b(var9, var10, var5) ? 16 : var6;
      }
   }

   @Override
   protected boolean a(long var0) {
      return var0 == Long.MAX_VALUE;
   }

   @Override
   protected int a(long var0, long var2, int var4) {
      return 0;
   }

   @Override
   protected int c(long var0) {
      return var0 == Long.MAX_VALUE ? 0 : 15 - this.d.i(var0);
   }

   protected int a(NibbleArray var0, long var1) {
      return 15 - var0.a(SectionPosition.b(BlockPosition.a(var1)), SectionPosition.b(BlockPosition.b(var1)), SectionPosition.b(BlockPosition.c(var1)));
   }

   @Override
   protected void a(long var0, int var2) {
      this.d.b(var0, Math.min(15, 15 - var2));
   }

   @Override
   protected int b(long var0, long var2, int var4) {
      return 0;
   }

   @Override
   public boolean D_() {
      return this.b() || this.d.b() || this.d.a();
   }

   @Override
   public int a(int var0, boolean var1, boolean var2) {
      if (!this.g) {
         if (this.d.b()) {
            var0 = this.d.b(var0);
            if (var0 == 0) {
               return var0;
            }
         }

         this.d.a(this, var1, var2);
      }

      this.g = true;
      if (this.b()) {
         var0 = this.b(var0);
         this.d();
         if (var0 == 0) {
            return var0;
         }
      }

      this.g = false;
      this.d.e();
      return var0;
   }

   protected void a(long var0, @Nullable NibbleArray var2, boolean var3) {
      this.d.a(var0, var2, var3);
   }

   @Nullable
   @Override
   public NibbleArray a(SectionPosition var0) {
      return this.d.h(var0.s());
   }

   @Override
   public int b(BlockPosition var0) {
      return this.d.d(var0.a());
   }

   public String b(long var0) {
      return this.d.c(var0) + "";
   }

   @Override
   public void a(BlockPosition var0) {
      long var1 = var0.a();
      this.f(var1);

      for(EnumDirection var6 : f) {
         this.f(BlockPosition.a(var1, var6));
      }
   }

   @Override
   public void a(BlockPosition var0, int var1) {
   }

   @Override
   public void a(SectionPosition var0, boolean var1) {
      this.d.d(var0.s(), var1);
   }

   @Override
   public void a(ChunkCoordIntPair var0, boolean var1) {
      long var2 = SectionPosition.f(SectionPosition.b(var0.e, 0, var0.f));
      this.d.b(var2, var1);
   }

   public void b(ChunkCoordIntPair var0, boolean var1) {
      long var2 = SectionPosition.f(SectionPosition.b(var0.e, 0, var0.f));
      this.d.c(var2, var1);
   }
}
