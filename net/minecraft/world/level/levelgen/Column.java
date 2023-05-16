package net.minecraft.world.level.levelgen;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.state.IBlockData;

public abstract class Column {
   public static Column.b a(int var0, int var1) {
      return new Column.b(var0 - 1, var1 + 1);
   }

   public static Column.b b(int var0, int var1) {
      return new Column.b(var0, var1);
   }

   public static Column a(int var0) {
      return new Column.c(var0, false);
   }

   public static Column b(int var0) {
      return new Column.c(var0 + 1, false);
   }

   public static Column c(int var0) {
      return new Column.c(var0, true);
   }

   public static Column d(int var0) {
      return new Column.c(var0 - 1, true);
   }

   public static Column a() {
      return Column.a.a;
   }

   public static Column a(OptionalInt var0, OptionalInt var1) {
      if (var0.isPresent() && var1.isPresent()) {
         return b(var0.getAsInt(), var1.getAsInt());
      } else if (var0.isPresent()) {
         return c(var0.getAsInt());
      } else {
         return var1.isPresent() ? a(var1.getAsInt()) : a();
      }
   }

   public abstract OptionalInt b();

   public abstract OptionalInt c();

   public abstract OptionalInt d();

   public Column a(OptionalInt var0) {
      return a(var0, this.b());
   }

   public Column b(OptionalInt var0) {
      return a(this.c(), var0);
   }

   public static Optional<Column> a(VirtualLevelReadable var0, BlockPosition var1, int var2, Predicate<IBlockData> var3, Predicate<IBlockData> var4) {
      BlockPosition.MutableBlockPosition var5 = var1.j();
      if (!var0.a(var1, var3)) {
         return Optional.empty();
      } else {
         int var6 = var1.v();
         OptionalInt var7 = a(var0, var2, var3, var4, var5, var6, EnumDirection.b);
         OptionalInt var8 = a(var0, var2, var3, var4, var5, var6, EnumDirection.a);
         return Optional.of(a(var8, var7));
      }
   }

   private static OptionalInt a(
      VirtualLevelReadable var0,
      int var1,
      Predicate<IBlockData> var2,
      Predicate<IBlockData> var3,
      BlockPosition.MutableBlockPosition var4,
      int var5,
      EnumDirection var6
   ) {
      var4.q(var5);

      for(int var7 = 1; var7 < var1 && var0.a(var4, var2); ++var7) {
         var4.c(var6);
      }

      return var0.a(var4, var3) ? OptionalInt.of(var4.v()) : OptionalInt.empty();
   }

   public static final class a extends Column {
      static final Column.a a = new Column.a();

      private a() {
      }

      @Override
      public OptionalInt b() {
         return OptionalInt.empty();
      }

      @Override
      public OptionalInt c() {
         return OptionalInt.empty();
      }

      @Override
      public OptionalInt d() {
         return OptionalInt.empty();
      }

      @Override
      public String toString() {
         return "C(-)";
      }
   }

   public static final class b extends Column {
      private final int a;
      private final int b;

      protected b(int var0, int var1) {
         this.a = var0;
         this.b = var1;
         if (this.g() < 0) {
            throw new IllegalArgumentException("Column of negative height: " + this);
         }
      }

      @Override
      public OptionalInt b() {
         return OptionalInt.of(this.b);
      }

      @Override
      public OptionalInt c() {
         return OptionalInt.of(this.a);
      }

      @Override
      public OptionalInt d() {
         return OptionalInt.of(this.g());
      }

      public int e() {
         return this.b;
      }

      public int f() {
         return this.a;
      }

      public int g() {
         return this.b - this.a - 1;
      }

      @Override
      public String toString() {
         return "C(" + this.b + "-" + this.a + ")";
      }
   }

   public static final class c extends Column {
      private final int a;
      private final boolean b;

      public c(int var0, boolean var1) {
         this.a = var0;
         this.b = var1;
      }

      @Override
      public OptionalInt b() {
         return this.b ? OptionalInt.empty() : OptionalInt.of(this.a);
      }

      @Override
      public OptionalInt c() {
         return this.b ? OptionalInt.of(this.a) : OptionalInt.empty();
      }

      @Override
      public OptionalInt d() {
         return OptionalInt.empty();
      }

      @Override
      public String toString() {
         return this.b ? "C(" + this.a + "-)" : "C(-" + this.a + ")";
      }
   }
}
