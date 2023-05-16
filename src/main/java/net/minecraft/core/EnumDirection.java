package net.minecraft.core;

import com.google.common.collect.Iterators;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.INamable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector4f;

public enum EnumDirection implements INamable {
   a(0, 1, -1, "down", EnumDirection.EnumAxisDirection.b, EnumDirection.EnumAxis.b, new BaseBlockPosition(0, -1, 0)),
   b(1, 0, -1, "up", EnumDirection.EnumAxisDirection.a, EnumDirection.EnumAxis.b, new BaseBlockPosition(0, 1, 0)),
   c(2, 3, 2, "north", EnumDirection.EnumAxisDirection.b, EnumDirection.EnumAxis.c, new BaseBlockPosition(0, 0, -1)),
   d(3, 2, 0, "south", EnumDirection.EnumAxisDirection.a, EnumDirection.EnumAxis.c, new BaseBlockPosition(0, 0, 1)),
   e(4, 5, 1, "west", EnumDirection.EnumAxisDirection.b, EnumDirection.EnumAxis.a, new BaseBlockPosition(-1, 0, 0)),
   f(5, 4, 3, "east", EnumDirection.EnumAxisDirection.a, EnumDirection.EnumAxis.a, new BaseBlockPosition(1, 0, 0));

   public static final INamable.a<EnumDirection> g = INamable.a(EnumDirection::values);
   public static final Codec<EnumDirection> h = ExtraCodecs.a(g, EnumDirection::a);
   private final int i;
   private final int j;
   private final int k;
   private final String l;
   private final EnumDirection.EnumAxis m;
   private final EnumDirection.EnumAxisDirection n;
   private final BaseBlockPosition o;
   private static final EnumDirection[] p = values();
   private static final EnumDirection[] q = Arrays.stream(p).sorted(Comparator.comparingInt(var0 -> var0.i)).toArray(var0 -> new EnumDirection[var0]);
   private static final EnumDirection[] r = Arrays.stream(p)
      .filter(var0 -> var0.o().d())
      .sorted(Comparator.comparingInt(var0 -> var0.k))
      .toArray(var0 -> new EnumDirection[var0]);
   private static final Long2ObjectMap<EnumDirection> s = Arrays.stream(p)
      .collect(Collectors.toMap(var0 -> new BlockPosition(var0.q()).a(), var0 -> var0, (var0, var1) -> {
         throw new IllegalArgumentException("Duplicate keys");
      }, Long2ObjectOpenHashMap::new));

   private EnumDirection(int var2, int var3, int var4, String var5, EnumDirection.EnumAxisDirection var6, EnumDirection.EnumAxis var7, BaseBlockPosition var8) {
      this.i = var2;
      this.k = var4;
      this.j = var3;
      this.l = var5;
      this.m = var7;
      this.n = var6;
      this.o = var8;
   }

   public static EnumDirection[] a(Entity var0) {
      float var1 = var0.k(1.0F) * (float) (Math.PI / 180.0);
      float var2 = -var0.l(1.0F) * (float) (Math.PI / 180.0);
      float var3 = MathHelper.a(var1);
      float var4 = MathHelper.b(var1);
      float var5 = MathHelper.a(var2);
      float var6 = MathHelper.b(var2);
      boolean var7 = var5 > 0.0F;
      boolean var8 = var3 < 0.0F;
      boolean var9 = var6 > 0.0F;
      float var10 = var7 ? var5 : -var5;
      float var11 = var8 ? -var3 : var3;
      float var12 = var9 ? var6 : -var6;
      float var13 = var10 * var4;
      float var14 = var12 * var4;
      EnumDirection var15 = var7 ? f : e;
      EnumDirection var16 = var8 ? b : a;
      EnumDirection var17 = var9 ? d : c;
      if (var10 > var12) {
         if (var11 > var13) {
            return a(var16, var15, var17);
         } else {
            return var14 > var11 ? a(var15, var17, var16) : a(var15, var16, var17);
         }
      } else if (var11 > var14) {
         return a(var16, var17, var15);
      } else {
         return var13 > var11 ? a(var17, var15, var16) : a(var17, var16, var15);
      }
   }

   private static EnumDirection[] a(EnumDirection var0, EnumDirection var1, EnumDirection var2) {
      return new EnumDirection[]{var0, var1, var2, var2.g(), var1.g(), var0.g()};
   }

   public static EnumDirection a(Matrix4f var0, EnumDirection var1) {
      BaseBlockPosition var2 = var1.q();
      Vector4f var3 = var0.transform(new Vector4f((float)var2.u(), (float)var2.v(), (float)var2.w(), 0.0F));
      return a(var3.x(), var3.y(), var3.z());
   }

   public static Collection<EnumDirection> a(RandomSource var0) {
      return SystemUtils.b(values(), var0);
   }

   public static Stream<EnumDirection> a() {
      return Stream.of(p);
   }

   public Quaternionf b() {
      return switch(this) {
         case a -> new Quaternionf().rotationX((float) Math.PI);
         case b -> new Quaternionf();
         case c -> new Quaternionf().rotationXYZ((float) (Math.PI / 2), 0.0F, (float) Math.PI);
         case d -> new Quaternionf().rotationX((float) (Math.PI / 2));
         case e -> new Quaternionf().rotationXYZ((float) (Math.PI / 2), 0.0F, (float) (Math.PI / 2));
         case f -> new Quaternionf().rotationXYZ((float) (Math.PI / 2), 0.0F, (float) (-Math.PI / 2));
      };
   }

   public int d() {
      return this.i;
   }

   public int e() {
      return this.k;
   }

   public EnumDirection.EnumAxisDirection f() {
      return this.n;
   }

   public static EnumDirection a(Entity var0, EnumDirection.EnumAxis var1) {
      return switch(var1) {
         case a -> f.a(var0.l(1.0F)) ? f : e;
         case c -> d.a(var0.l(1.0F)) ? d : c;
         case b -> var0.k(1.0F) < 0.0F ? b : a;
      };
   }

   public EnumDirection g() {
      return a(this.j);
   }

   public EnumDirection a(EnumDirection.EnumAxis var0) {
      return switch(var0) {
         case a -> this != e && this != f ? this.r() : this;
         case c -> this != c && this != d ? this.t() : this;
         case b -> this != b && this != a ? this.h() : this;
      };
   }

   public EnumDirection b(EnumDirection.EnumAxis var0) {
      return switch(var0) {
         case a -> this != e && this != f ? this.s() : this;
         case c -> this != c && this != d ? this.u() : this;
         case b -> this != b && this != a ? this.i() : this;
      };
   }

   public EnumDirection h() {
      return switch(this) {
         case c -> f;
         case d -> e;
         case e -> c;
         case f -> d;
         default -> throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
      };
   }

   private EnumDirection r() {
      return switch(this) {
         case a -> d;
         case b -> c;
         case c -> a;
         case d -> b;
         default -> throw new IllegalStateException("Unable to get X-rotated facing of " + this);
      };
   }

   private EnumDirection s() {
      return switch(this) {
         case a -> c;
         case b -> d;
         case c -> b;
         case d -> a;
         default -> throw new IllegalStateException("Unable to get X-rotated facing of " + this);
      };
   }

   private EnumDirection t() {
      return switch(this) {
         case a -> e;
         case b -> f;
         default -> throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
         case e -> b;
         case f -> a;
      };
   }

   private EnumDirection u() {
      return switch(this) {
         case a -> f;
         case b -> e;
         default -> throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
         case e -> a;
         case f -> b;
      };
   }

   public EnumDirection i() {
      return switch(this) {
         case c -> e;
         case d -> f;
         case e -> d;
         case f -> c;
         default -> throw new IllegalStateException("Unable to get CCW facing of " + this);
      };
   }

   public int j() {
      return this.o.u();
   }

   public int k() {
      return this.o.v();
   }

   public int l() {
      return this.o.w();
   }

   public org.joml.Vector3f m() {
      return new org.joml.Vector3f((float)this.j(), (float)this.k(), (float)this.l());
   }

   public String n() {
      return this.l;
   }

   public EnumDirection.EnumAxis o() {
      return this.m;
   }

   @Nullable
   public static EnumDirection a(@Nullable String var0) {
      return g.a(var0);
   }

   public static EnumDirection a(int var0) {
      return q[MathHelper.a(var0 % q.length)];
   }

   public static EnumDirection b(int var0) {
      return r[MathHelper.a(var0 % r.length)];
   }

   @Nullable
   public static EnumDirection a(BlockPosition var0) {
      return (EnumDirection)s.get(var0.a());
   }

   @Nullable
   public static EnumDirection a(int var0, int var1, int var2) {
      return (EnumDirection)s.get(BlockPosition.a(var0, var1, var2));
   }

   public static EnumDirection a(double var0) {
      return b(MathHelper.a(var0 / 90.0 + 0.5) & 3);
   }

   public static EnumDirection a(EnumDirection.EnumAxis var0, EnumDirection.EnumAxisDirection var1) {
      return switch(var0) {
         case a -> var1 == EnumDirection.EnumAxisDirection.a ? f : e;
         case c -> var1 == EnumDirection.EnumAxisDirection.a ? d : c;
         case b -> var1 == EnumDirection.EnumAxisDirection.a ? b : a;
      };
   }

   public float p() {
      return (float)((this.k & 3) * 90);
   }

   public static EnumDirection b(RandomSource var0) {
      return SystemUtils.a(p, var0);
   }

   public static EnumDirection a(double var0, double var2, double var4) {
      return a((float)var0, (float)var2, (float)var4);
   }

   public static EnumDirection a(float var0, float var1, float var2) {
      EnumDirection var3 = c;
      float var4 = Float.MIN_VALUE;

      for(EnumDirection var8 : p) {
         float var9 = var0 * (float)var8.o.u() + var1 * (float)var8.o.v() + var2 * (float)var8.o.w();
         if (var9 > var4) {
            var4 = var9;
            var3 = var8;
         }
      }

      return var3;
   }

   @Override
   public String toString() {
      return this.l;
   }

   @Override
   public String c() {
      return this.l;
   }

   private static DataResult<EnumDirection> a(EnumDirection var0) {
      return var0.o().b() ? DataResult.success(var0) : DataResult.error(() -> "Expected a vertical direction");
   }

   public static EnumDirection a(EnumDirection.EnumAxisDirection var0, EnumDirection.EnumAxis var1) {
      for(EnumDirection var5 : p) {
         if (var5.f() == var0 && var5.o() == var1) {
            return var5;
         }
      }

      throw new IllegalArgumentException("No such direction: " + var0 + " " + var1);
   }

   public BaseBlockPosition q() {
      return this.o;
   }

   public boolean a(float var0) {
      float var1 = var0 * (float) (Math.PI / 180.0);
      float var2 = -MathHelper.a(var1);
      float var3 = MathHelper.b(var1);
      return (float)this.o.u() * var2 + (float)this.o.w() * var3 > 0.0F;
   }

   public static enum EnumAxis implements INamable, Predicate<EnumDirection> {
      a("x") {
         @Override
         public int a(int var0, int var1, int var2) {
            return var0;
         }

         @Override
         public double a(double var0, double var2, double var4) {
            return var0;
         }
      },
      b("y") {
         @Override
         public int a(int var0, int var1, int var2) {
            return var1;
         }

         @Override
         public double a(double var0, double var2, double var4) {
            return var2;
         }
      },
      c("z") {
         @Override
         public int a(int var0, int var1, int var2) {
            return var2;
         }

         @Override
         public double a(double var0, double var2, double var4) {
            return var4;
         }
      };

      public static final EnumDirection.EnumAxis[] d = values();
      public static final INamable.a<EnumDirection.EnumAxis> e = INamable.a(EnumDirection.EnumAxis::values);
      private final String f;

      EnumAxis(String var2) {
         this.f = var2;
      }

      @Nullable
      public static EnumDirection.EnumAxis a(String var0) {
         return e.a(var0);
      }

      public String a() {
         return this.f;
      }

      public boolean b() {
         return this == b;
      }

      public boolean d() {
         return this == a || this == c;
      }

      @Override
      public String toString() {
         return this.f;
      }

      public static EnumDirection.EnumAxis a(RandomSource var0) {
         return SystemUtils.a(d, var0);
      }

      public boolean a(@Nullable EnumDirection var0) {
         return var0 != null && var0.o() == this;
      }

      public EnumDirection.EnumDirectionLimit e() {
         return switch(this) {
            case a, c -> EnumDirection.EnumDirectionLimit.a;
            case b -> EnumDirection.EnumDirectionLimit.b;
         };
      }

      @Override
      public String c() {
         return this.f;
      }

      public abstract int a(int var1, int var2, int var3);

      public abstract double a(double var1, double var3, double var5);
   }

   public static enum EnumAxisDirection {
      a(1, "Towards positive"),
      b(-1, "Towards negative");

      private final int c;
      private final String d;

      private EnumAxisDirection(int var2, String var3) {
         this.c = var2;
         this.d = var3;
      }

      public int a() {
         return this.c;
      }

      public String b() {
         return this.d;
      }

      @Override
      public String toString() {
         return this.d;
      }

      public EnumDirection.EnumAxisDirection c() {
         return this == a ? b : a;
      }
   }

   public static enum EnumDirectionLimit implements Iterable<EnumDirection>, Predicate<EnumDirection> {
      a(
         new EnumDirection[]{EnumDirection.c, EnumDirection.f, EnumDirection.d, EnumDirection.e},
         new EnumDirection.EnumAxis[]{EnumDirection.EnumAxis.a, EnumDirection.EnumAxis.c}
      ),
      b(new EnumDirection[]{EnumDirection.b, EnumDirection.a}, new EnumDirection.EnumAxis[]{EnumDirection.EnumAxis.b});

      private final EnumDirection[] c;
      private final EnumDirection.EnumAxis[] d;

      private EnumDirectionLimit(EnumDirection[] var2, EnumDirection.EnumAxis[] var3) {
         this.c = var2;
         this.d = var3;
      }

      public EnumDirection a(RandomSource var0) {
         return SystemUtils.a(this.c, var0);
      }

      public EnumDirection.EnumAxis b(RandomSource var0) {
         return SystemUtils.a(this.d, var0);
      }

      public boolean a(@Nullable EnumDirection var0) {
         return var0 != null && var0.o().e() == this;
      }

      @Override
      public Iterator<EnumDirection> iterator() {
         return Iterators.forArray(this.c);
      }

      public Stream<EnumDirection> a() {
         return Arrays.stream(this.c);
      }

      public List<EnumDirection> c(RandomSource var0) {
         return SystemUtils.b(this.c, var0);
      }
   }
}
