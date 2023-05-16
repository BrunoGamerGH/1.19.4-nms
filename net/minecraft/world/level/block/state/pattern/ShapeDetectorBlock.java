package net.minecraft.world.level.block.state.pattern;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;

public class ShapeDetectorBlock {
   private final IWorldReader a;
   private final BlockPosition b;
   private final boolean c;
   @Nullable
   private IBlockData d;
   @Nullable
   private TileEntity e;
   private boolean f;

   public ShapeDetectorBlock(IWorldReader var0, BlockPosition var1, boolean var2) {
      this.a = var0;
      this.b = var1.i();
      this.c = var2;
   }

   public IBlockData a() {
      if (this.d == null && (this.c || this.a.D(this.b))) {
         this.d = this.a.a_(this.b);
      }

      return this.d;
   }

   @Nullable
   public TileEntity b() {
      if (this.e == null && !this.f) {
         this.e = this.a.c_(this.b);
         this.f = true;
      }

      return this.e;
   }

   public IWorldReader c() {
      return this.a;
   }

   public BlockPosition d() {
      return this.b;
   }

   public static Predicate<ShapeDetectorBlock> a(Predicate<IBlockData> var0) {
      return var1 -> var1 != null && var0.test(var1.a());
   }
}
