package net.minecraft.world.level.levelgen.structure;

import com.google.common.base.MoreObjects;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import org.slf4j.Logger;

public class StructureBoundingBox {
   private static final Logger b = LogUtils.getLogger();
   public static final Codec<StructureBoundingBox> a = Codec.INT_STREAM
      .comapFlatMap(
         var0 -> SystemUtils.a(var0, 6).map(var0x -> new StructureBoundingBox(var0x[0], var0x[1], var0x[2], var0x[3], var0x[4], var0x[5])),
         var0 -> IntStream.of(var0.c, var0.d, var0.e, var0.f, var0.g, var0.h)
      )
      .stable();
   private int c;
   private int d;
   private int e;
   private int f;
   private int g;
   private int h;

   public StructureBoundingBox(BlockPosition var0) {
      this(var0.u(), var0.v(), var0.w(), var0.u(), var0.v(), var0.w());
   }

   public StructureBoundingBox(int var0, int var1, int var2, int var3, int var4, int var5) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
      this.g = var4;
      this.h = var5;
      if (var3 < var0 || var4 < var1 || var5 < var2) {
         String var6 = "Invalid bounding box data, inverted bounds for: " + this;
         if (SharedConstants.aO) {
            throw new IllegalStateException(var6);
         }

         b.error(var6);
         this.c = Math.min(var0, var3);
         this.d = Math.min(var1, var4);
         this.e = Math.min(var2, var5);
         this.f = Math.max(var0, var3);
         this.g = Math.max(var1, var4);
         this.h = Math.max(var2, var5);
      }
   }

   public static StructureBoundingBox a(BaseBlockPosition var0, BaseBlockPosition var1) {
      return new StructureBoundingBox(
         Math.min(var0.u(), var1.u()),
         Math.min(var0.v(), var1.v()),
         Math.min(var0.w(), var1.w()),
         Math.max(var0.u(), var1.u()),
         Math.max(var0.v(), var1.v()),
         Math.max(var0.w(), var1.w())
      );
   }

   public static StructureBoundingBox a() {
      return new StructureBoundingBox(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
   }

   public static StructureBoundingBox a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, EnumDirection var9) {
      switch(var9) {
         case d:
         default:
            return new StructureBoundingBox(var0 + var3, var1 + var4, var2 + var5, var0 + var6 - 1 + var3, var1 + var7 - 1 + var4, var2 + var8 - 1 + var5);
         case c:
            return new StructureBoundingBox(var0 + var3, var1 + var4, var2 - var8 + 1 + var5, var0 + var6 - 1 + var3, var1 + var7 - 1 + var4, var2 + var5);
         case e:
            return new StructureBoundingBox(var0 - var8 + 1 + var5, var1 + var4, var2 + var3, var0 + var5, var1 + var7 - 1 + var4, var2 + var6 - 1 + var3);
         case f:
            return new StructureBoundingBox(var0 + var5, var1 + var4, var2 + var3, var0 + var8 - 1 + var5, var1 + var7 - 1 + var4, var2 + var6 - 1 + var3);
      }
   }

   public boolean a(StructureBoundingBox var0) {
      return this.f >= var0.c && this.c <= var0.f && this.h >= var0.e && this.e <= var0.h && this.g >= var0.d && this.d <= var0.g;
   }

   public boolean a(int var0, int var1, int var2, int var3) {
      return this.f >= var0 && this.c <= var2 && this.h >= var1 && this.e <= var3;
   }

   public static Optional<StructureBoundingBox> a(Iterable<BlockPosition> var0) {
      Iterator<BlockPosition> var1 = var0.iterator();
      if (!var1.hasNext()) {
         return Optional.empty();
      } else {
         StructureBoundingBox var2 = new StructureBoundingBox(var1.next());
         var1.forEachRemaining(var2::a);
         return Optional.of(var2);
      }
   }

   public static Optional<StructureBoundingBox> b(Iterable<StructureBoundingBox> var0) {
      Iterator<StructureBoundingBox> var1 = var0.iterator();
      if (!var1.hasNext()) {
         return Optional.empty();
      } else {
         StructureBoundingBox var2 = var1.next();
         StructureBoundingBox var3 = new StructureBoundingBox(var2.c, var2.d, var2.e, var2.f, var2.g, var2.h);
         var1.forEachRemaining(var3::b);
         return Optional.of(var3);
      }
   }

   @Deprecated
   public StructureBoundingBox b(StructureBoundingBox var0) {
      this.c = Math.min(this.c, var0.c);
      this.d = Math.min(this.d, var0.d);
      this.e = Math.min(this.e, var0.e);
      this.f = Math.max(this.f, var0.f);
      this.g = Math.max(this.g, var0.g);
      this.h = Math.max(this.h, var0.h);
      return this;
   }

   @Deprecated
   public StructureBoundingBox a(BlockPosition var0) {
      this.c = Math.min(this.c, var0.u());
      this.d = Math.min(this.d, var0.v());
      this.e = Math.min(this.e, var0.w());
      this.f = Math.max(this.f, var0.u());
      this.g = Math.max(this.g, var0.v());
      this.h = Math.max(this.h, var0.w());
      return this;
   }

   @Deprecated
   public StructureBoundingBox a(int var0, int var1, int var2) {
      this.c += var0;
      this.d += var1;
      this.e += var2;
      this.f += var0;
      this.g += var1;
      this.h += var2;
      return this;
   }

   @Deprecated
   public StructureBoundingBox a(BaseBlockPosition var0) {
      return this.a(var0.u(), var0.v(), var0.w());
   }

   public StructureBoundingBox b(int var0, int var1, int var2) {
      return new StructureBoundingBox(this.c + var0, this.d + var1, this.e + var2, this.f + var0, this.g + var1, this.h + var2);
   }

   public StructureBoundingBox a(int var0) {
      return new StructureBoundingBox(this.g() - var0, this.h() - var0, this.i() - var0, this.j() + var0, this.k() + var0, this.l() + var0);
   }

   public boolean b(BaseBlockPosition var0) {
      return this.c(var0.u(), var0.v(), var0.w());
   }

   public boolean c(int var0, int var1, int var2) {
      return var0 >= this.c && var0 <= this.f && var2 >= this.e && var2 <= this.h && var1 >= this.d && var1 <= this.g;
   }

   public BaseBlockPosition b() {
      return new BaseBlockPosition(this.f - this.c, this.g - this.d, this.h - this.e);
   }

   public int c() {
      return this.f - this.c + 1;
   }

   public int d() {
      return this.g - this.d + 1;
   }

   public int e() {
      return this.h - this.e + 1;
   }

   public BlockPosition f() {
      return new BlockPosition(this.c + (this.f - this.c + 1) / 2, this.d + (this.g - this.d + 1) / 2, this.e + (this.h - this.e + 1) / 2);
   }

   public void a(Consumer<BlockPosition> var0) {
      BlockPosition.MutableBlockPosition var1 = new BlockPosition.MutableBlockPosition();
      var0.accept(var1.d(this.f, this.g, this.h));
      var0.accept(var1.d(this.c, this.g, this.h));
      var0.accept(var1.d(this.f, this.d, this.h));
      var0.accept(var1.d(this.c, this.d, this.h));
      var0.accept(var1.d(this.f, this.g, this.e));
      var0.accept(var1.d(this.c, this.g, this.e));
      var0.accept(var1.d(this.f, this.d, this.e));
      var0.accept(var1.d(this.c, this.d, this.e));
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
         .add("minX", this.c)
         .add("minY", this.d)
         .add("minZ", this.e)
         .add("maxX", this.f)
         .add("maxY", this.g)
         .add("maxZ", this.h)
         .toString();
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof StructureBoundingBox)) {
         return false;
      } else {
         StructureBoundingBox var1 = (StructureBoundingBox)var0;
         return this.c == var1.c && this.d == var1.d && this.e == var1.e && this.f == var1.f && this.g == var1.g && this.h == var1.h;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.c, this.d, this.e, this.f, this.g, this.h);
   }

   public int g() {
      return this.c;
   }

   public int h() {
      return this.d;
   }

   public int i() {
      return this.e;
   }

   public int j() {
      return this.f;
   }

   public int k() {
      return this.g;
   }

   public int l() {
      return this.h;
   }
}
