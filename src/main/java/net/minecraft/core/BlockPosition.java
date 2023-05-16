package net.minecraft.core;

import com.google.common.collect.AbstractIterator;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.concurrent.Immutable;
import net.minecraft.SystemUtils;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

@Immutable
public class BlockPosition extends BaseBlockPosition {
   public static final Codec<BlockPosition> a = Codec.INT_STREAM
      .comapFlatMap(
         var0 -> SystemUtils.a(var0, 3).map(var0x -> new BlockPosition(var0x[0], var0x[1], var0x[2])), var0 -> IntStream.of(var0.u(), var0.v(), var0.w())
      )
      .stable();
   private static final Logger d = LogUtils.getLogger();
   public static final BlockPosition b = new BlockPosition(0, 0, 0);
   private static final int e = 1 + MathHelper.f(MathHelper.c(30000000));
   private static final int h = e;
   public static final int c = 64 - e - h;
   private static final long i = (1L << e) - 1L;
   private static final long j = (1L << c) - 1L;
   private static final long k = (1L << h) - 1L;
   private static final int l = 0;
   private static final int m = c;
   private static final int n = c + h;

   public BlockPosition(int var0, int var1, int var2) {
      super(var0, var1, var2);
   }

   public BlockPosition(BaseBlockPosition var0) {
      this(var0.u(), var0.v(), var0.w());
   }

   public static long a(long var0, EnumDirection var2) {
      return a(var0, var2.j(), var2.k(), var2.l());
   }

   public static long a(long var0, int var2, int var3, int var4) {
      return a(a(var0) + var2, b(var0) + var3, c(var0) + var4);
   }

   public static int a(long var0) {
      return (int)(var0 << 64 - n - e >> 64 - e);
   }

   public static int b(long var0) {
      return (int)(var0 << 64 - c >> 64 - c);
   }

   public static int c(long var0) {
      return (int)(var0 << 64 - m - h >> 64 - h);
   }

   public static BlockPosition d(long var0) {
      return new BlockPosition(a(var0), b(var0), c(var0));
   }

   public static BlockPosition a(double var0, double var2, double var4) {
      return new BlockPosition(MathHelper.a(var0), MathHelper.a(var2), MathHelper.a(var4));
   }

   public static BlockPosition a(IPosition var0) {
      return a(var0.a(), var0.b(), var0.c());
   }

   public long a() {
      return a(this.u(), this.v(), this.w());
   }

   public static long a(int var0, int var1, int var2) {
      long var3 = 0L;
      var3 |= ((long)var0 & i) << n;
      var3 |= ((long)var1 & j) << 0;
      return var3 | ((long)var2 & k) << m;
   }

   public static long e(long var0) {
      return var0 & -16L;
   }

   public BlockPosition b(int var0, int var1, int var2) {
      return var0 == 0 && var1 == 0 && var2 == 0 ? this : new BlockPosition(this.u() + var0, this.v() + var1, this.w() + var2);
   }

   public Vec3D b() {
      return Vec3D.b(this);
   }

   public BlockPosition a(BaseBlockPosition var0) {
      return this.b(var0.u(), var0.v(), var0.w());
   }

   public BlockPosition b(BaseBlockPosition var0) {
      return this.b(-var0.u(), -var0.v(), -var0.w());
   }

   public BlockPosition a(int var0) {
      if (var0 == 1) {
         return this;
      } else {
         return var0 == 0 ? b : new BlockPosition(this.u() * var0, this.v() * var0, this.w() * var0);
      }
   }

   public BlockPosition c() {
      return this.a(EnumDirection.b);
   }

   public BlockPosition b(int var0) {
      return this.a(EnumDirection.b, var0);
   }

   public BlockPosition d() {
      return this.a(EnumDirection.a);
   }

   public BlockPosition c(int var0) {
      return this.a(EnumDirection.a, var0);
   }

   public BlockPosition e() {
      return this.a(EnumDirection.c);
   }

   public BlockPosition d(int var0) {
      return this.a(EnumDirection.c, var0);
   }

   public BlockPosition f() {
      return this.a(EnumDirection.d);
   }

   public BlockPosition e(int var0) {
      return this.a(EnumDirection.d, var0);
   }

   public BlockPosition g() {
      return this.a(EnumDirection.e);
   }

   public BlockPosition f(int var0) {
      return this.a(EnumDirection.e, var0);
   }

   public BlockPosition h() {
      return this.a(EnumDirection.f);
   }

   public BlockPosition g(int var0) {
      return this.a(EnumDirection.f, var0);
   }

   public BlockPosition a(EnumDirection var0) {
      return new BlockPosition(this.u() + var0.j(), this.v() + var0.k(), this.w() + var0.l());
   }

   public BlockPosition a(EnumDirection var0, int var1) {
      return var1 == 0 ? this : new BlockPosition(this.u() + var0.j() * var1, this.v() + var0.k() * var1, this.w() + var0.l() * var1);
   }

   public BlockPosition a(EnumDirection.EnumAxis var0, int var1) {
      if (var1 == 0) {
         return this;
      } else {
         int var2 = var0 == EnumDirection.EnumAxis.a ? var1 : 0;
         int var3 = var0 == EnumDirection.EnumAxis.b ? var1 : 0;
         int var4 = var0 == EnumDirection.EnumAxis.c ? var1 : 0;
         return new BlockPosition(this.u() + var2, this.v() + var3, this.w() + var4);
      }
   }

   public BlockPosition a(EnumBlockRotation var0) {
      switch(var0) {
         case a:
         default:
            return this;
         case b:
            return new BlockPosition(-this.w(), this.v(), this.u());
         case c:
            return new BlockPosition(-this.u(), this.v(), -this.w());
         case d:
            return new BlockPosition(this.w(), this.v(), -this.u());
      }
   }

   public BlockPosition c(BaseBlockPosition var0) {
      return new BlockPosition(this.v() * var0.w() - this.w() * var0.v(), this.w() * var0.u() - this.u() * var0.w(), this.u() * var0.v() - this.v() * var0.u());
   }

   public BlockPosition h(int var0) {
      return new BlockPosition(this.u(), var0, this.w());
   }

   public BlockPosition i() {
      return this;
   }

   public BlockPosition.MutableBlockPosition j() {
      return new BlockPosition.MutableBlockPosition(this.u(), this.v(), this.w());
   }

   public static Iterable<BlockPosition> a(RandomSource var0, int var1, BlockPosition var2, int var3) {
      return a(var0, var1, var2.u() - var3, var2.v() - var3, var2.w() - var3, var2.u() + var3, var2.v() + var3, var2.w() + var3);
   }

   public static Iterable<BlockPosition> a(RandomSource var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = var5 - var2 + 1;
      int var9 = var6 - var3 + 1;
      int var10 = var7 - var4 + 1;
      return () -> new AbstractIterator<BlockPosition>() {
            final BlockPosition.MutableBlockPosition a = new BlockPosition.MutableBlockPosition();
            int b = var1;

            protected BlockPosition a() {
               if (this.b <= 0) {
                  return (BlockPosition)this.endOfData();
               } else {
                  BlockPosition var0 = this.a.d(var2 + var0.a(var8), var3 + var0.a(var9), var4 + var0.a(var10));
                  --this.b;
                  return var0;
               }
            }
         };
   }

   public static Iterable<BlockPosition> a(BlockPosition var0, int var1, int var2, int var3) {
      int var4 = var1 + var2 + var3;
      int var5 = var0.u();
      int var6 = var0.v();
      int var7 = var0.w();
      return () -> new AbstractIterator<BlockPosition>() {
            private final BlockPosition.MutableBlockPosition h = new BlockPosition.MutableBlockPosition();
            private int i;
            private int j;
            private int k;
            private int l;
            private int m;
            private boolean n;

            protected BlockPosition a() {
               if (this.n) {
                  this.n = false;
                  this.h.r(var7 - (this.h.w() - var7));
                  return this.h;
               } else {
                  BlockPosition var0;
                  for(var0 = null; var0 == null; ++this.m) {
                     if (this.m > this.k) {
                        ++this.l;
                        if (this.l > this.j) {
                           ++this.i;
                           if (this.i > var4) {
                              return (BlockPosition)this.endOfData();
                           }

                           this.j = Math.min(var1, this.i);
                           this.l = -this.j;
                        }

                        this.k = Math.min(var2, this.i - Math.abs(this.l));
                        this.m = -this.k;
                     }

                     int var1 = this.l;
                     int var2 = this.m;
                     int var3 = this.i - Math.abs(var1) - Math.abs(var2);
                     if (var3 <= var3) {
                        this.n = var3 != 0;
                        var0 = this.h.d(var5 + var1, var6 + var2, var7 + var3);
                     }
                  }

                  return var0;
               }
            }
         };
   }

   public static Optional<BlockPosition> a(BlockPosition var0, int var1, int var2, Predicate<BlockPosition> var3) {
      for(BlockPosition var5 : a(var0, var1, var2, var1)) {
         if (var3.test(var5)) {
            return Optional.of(var5);
         }
      }

      return Optional.empty();
   }

   public static Stream<BlockPosition> b(BlockPosition var0, int var1, int var2, int var3) {
      return StreamSupport.stream(a(var0, var1, var2, var3).spliterator(), false);
   }

   public static Iterable<BlockPosition> a(BlockPosition var0, BlockPosition var1) {
      return b(
         Math.min(var0.u(), var1.u()),
         Math.min(var0.v(), var1.v()),
         Math.min(var0.w(), var1.w()),
         Math.max(var0.u(), var1.u()),
         Math.max(var0.v(), var1.v()),
         Math.max(var0.w(), var1.w())
      );
   }

   public static Stream<BlockPosition> b(BlockPosition var0, BlockPosition var1) {
      return StreamSupport.stream(a(var0, var1).spliterator(), false);
   }

   public static Stream<BlockPosition> a(StructureBoundingBox var0) {
      return a(
         Math.min(var0.g(), var0.j()),
         Math.min(var0.h(), var0.k()),
         Math.min(var0.i(), var0.l()),
         Math.max(var0.g(), var0.j()),
         Math.max(var0.h(), var0.k()),
         Math.max(var0.i(), var0.l())
      );
   }

   public static Stream<BlockPosition> a(AxisAlignedBB var0) {
      return a(MathHelper.a(var0.a), MathHelper.a(var0.b), MathHelper.a(var0.c), MathHelper.a(var0.d), MathHelper.a(var0.e), MathHelper.a(var0.f));
   }

   public static Stream<BlockPosition> a(int var0, int var1, int var2, int var3, int var4, int var5) {
      return StreamSupport.stream(b(var0, var1, var2, var3, var4, var5).spliterator(), false);
   }

   public static Iterable<BlockPosition> b(int var0, int var1, int var2, int var3, int var4, int var5) {
      int var6 = var3 - var0 + 1;
      int var7 = var4 - var1 + 1;
      int var8 = var5 - var2 + 1;
      int var9 = var6 * var7 * var8;
      return () -> new AbstractIterator<BlockPosition>() {
            private final BlockPosition.MutableBlockPosition g = new BlockPosition.MutableBlockPosition();
            private int h;

            protected BlockPosition a() {
               if (this.h == var9) {
                  return (BlockPosition)this.endOfData();
               } else {
                  int var0 = this.h % var6;
                  int var1 = this.h / var6;
                  int var2 = var1 % var7;
                  int var3 = var1 / var7;
                  ++this.h;
                  return this.g.d(var0 + var0, var1 + var2, var2 + var3);
               }
            }
         };
   }

   public static Iterable<BlockPosition.MutableBlockPosition> a(BlockPosition var0, int var1, EnumDirection var2, EnumDirection var3) {
      Validate.validState(var2.o() != var3.o(), "The two directions cannot be on the same axis", new Object[0]);
      return () -> new AbstractIterator<BlockPosition.MutableBlockPosition>() {
            private final EnumDirection[] e = new EnumDirection[]{var2, var3, var2.g(), var3.g()};
            private final BlockPosition.MutableBlockPosition f = var0.j().c(var3);
            private final int g = 4 * var1;
            private int h = -1;
            private int i;
            private int j;
            private int k = this.f.u();
            private int l = this.f.v();
            private int m = this.f.w();

            protected BlockPosition.MutableBlockPosition a() {
               this.f.d(this.k, this.l, this.m).c(this.e[(this.h + 4) % 4]);
               this.k = this.f.u();
               this.l = this.f.v();
               this.m = this.f.w();
               if (this.j >= this.i) {
                  if (this.h >= this.g) {
                     return (BlockPosition.MutableBlockPosition)this.endOfData();
                  }

                  ++this.h;
                  this.j = 0;
                  this.i = this.h / 2 + 1;
               }

               ++this.j;
               return this.f;
            }
         };
   }

   public static class MutableBlockPosition extends BlockPosition {
      public MutableBlockPosition() {
         this(0, 0, 0);
      }

      public MutableBlockPosition(int var0, int var1, int var2) {
         super(var0, var1, var2);
      }

      public MutableBlockPosition(double var0, double var2, double var4) {
         this(MathHelper.a(var0), MathHelper.a(var2), MathHelper.a(var4));
      }

      @Override
      public BlockPosition b(int var0, int var1, int var2) {
         return super.b(var0, var1, var2).i();
      }

      @Override
      public BlockPosition a(int var0) {
         return super.a(var0).i();
      }

      @Override
      public BlockPosition a(EnumDirection var0, int var1) {
         return super.a(var0, var1).i();
      }

      @Override
      public BlockPosition a(EnumDirection.EnumAxis var0, int var1) {
         return super.a(var0, var1).i();
      }

      @Override
      public BlockPosition a(EnumBlockRotation var0) {
         return super.a(var0).i();
      }

      public BlockPosition.MutableBlockPosition d(int var0, int var1, int var2) {
         this.p(var0);
         this.q(var1);
         this.r(var2);
         return this;
      }

      public BlockPosition.MutableBlockPosition b(double var0, double var2, double var4) {
         return this.d(MathHelper.a(var0), MathHelper.a(var2), MathHelper.a(var4));
      }

      public BlockPosition.MutableBlockPosition g(BaseBlockPosition var0) {
         return this.d(var0.u(), var0.v(), var0.w());
      }

      public BlockPosition.MutableBlockPosition f(long var0) {
         return this.d(a(var0), b(var0), c(var0));
      }

      public BlockPosition.MutableBlockPosition a(EnumAxisCycle var0, int var1, int var2, int var3) {
         return this.d(
            var0.a(var1, var2, var3, EnumDirection.EnumAxis.a),
            var0.a(var1, var2, var3, EnumDirection.EnumAxis.b),
            var0.a(var1, var2, var3, EnumDirection.EnumAxis.c)
         );
      }

      public BlockPosition.MutableBlockPosition a(BaseBlockPosition var0, EnumDirection var1) {
         return this.d(var0.u() + var1.j(), var0.v() + var1.k(), var0.w() + var1.l());
      }

      public BlockPosition.MutableBlockPosition a(BaseBlockPosition var0, int var1, int var2, int var3) {
         return this.d(var0.u() + var1, var0.v() + var2, var0.w() + var3);
      }

      public BlockPosition.MutableBlockPosition a(BaseBlockPosition var0, BaseBlockPosition var1) {
         return this.d(var0.u() + var1.u(), var0.v() + var1.v(), var0.w() + var1.w());
      }

      public BlockPosition.MutableBlockPosition c(EnumDirection var0) {
         return this.c(var0, 1);
      }

      public BlockPosition.MutableBlockPosition c(EnumDirection var0, int var1) {
         return this.d(this.u() + var0.j() * var1, this.v() + var0.k() * var1, this.w() + var0.l() * var1);
      }

      public BlockPosition.MutableBlockPosition e(int var0, int var1, int var2) {
         return this.d(this.u() + var0, this.v() + var1, this.w() + var2);
      }

      public BlockPosition.MutableBlockPosition h(BaseBlockPosition var0) {
         return this.d(this.u() + var0.u(), this.v() + var0.v(), this.w() + var0.w());
      }

      public BlockPosition.MutableBlockPosition a(EnumDirection.EnumAxis var0, int var1, int var2) {
         switch(var0) {
            case a:
               return this.d(MathHelper.a(this.u(), var1, var2), this.v(), this.w());
            case b:
               return this.d(this.u(), MathHelper.a(this.v(), var1, var2), this.w());
            case c:
               return this.d(this.u(), this.v(), MathHelper.a(this.w(), var1, var2));
            default:
               throw new IllegalStateException("Unable to clamp axis " + var0);
         }
      }

      public BlockPosition.MutableBlockPosition p(int var0) {
         super.u(var0);
         return this;
      }

      public BlockPosition.MutableBlockPosition q(int var0) {
         super.t(var0);
         return this;
      }

      public BlockPosition.MutableBlockPosition r(int var0) {
         super.s(var0);
         return this;
      }

      @Override
      public BlockPosition i() {
         return new BlockPosition(this);
      }
   }
}
