package net.minecraft.world.ticks;

import it.unimi.dsi.fastutil.Hash.Strategy;
import java.util.Comparator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;

public record NextTickListEntry<T>(T type, BlockPosition pos, long triggerTick, TickListPriority priority, long subTickOrder) {
   private final T d;
   private final BlockPosition e;
   private final long f;
   private final TickListPriority g;
   private final long h;
   public static final Comparator<NextTickListEntry<?>> a = (var0, var1) -> {
      int var2 = Long.compare(var0.f, var1.f);
      if (var2 != 0) {
         return var2;
      } else {
         var2 = var0.g.compareTo(var1.g);
         return var2 != 0 ? var2 : Long.compare(var0.h, var1.h);
      }
   };
   public static final Comparator<NextTickListEntry<?>> b = (var0, var1) -> {
      int var2 = var0.g.compareTo(var1.g);
      return var2 != 0 ? var2 : Long.compare(var0.h, var1.h);
   };
   public static final Strategy<NextTickListEntry<?>> c = new Strategy<NextTickListEntry<?>>() {
      public int a(NextTickListEntry<?> var0) {
         return 31 * var0.b().hashCode() + var0.a().hashCode();
      }

      public boolean a(@Nullable NextTickListEntry<?> var0, @Nullable NextTickListEntry<?> var1) {
         if (var0 == var1) {
            return true;
         } else if (var0 != null && var1 != null) {
            return var0.a() == var1.a() && var0.b().equals(var1.b());
         } else {
            return false;
         }
      }
   };

   public NextTickListEntry(T var0, BlockPosition var1, long var2, long var4) {
      this(var0, var1, var2, TickListPriority.d, var4);
   }

   public NextTickListEntry(T var0, BlockPosition var1, long var2, TickListPriority var4, long var5) {
      var1 = var1.i();
      this.d = var0;
      this.e = var1;
      this.f = var2;
      this.g = var4;
      this.h = var5;
   }

   public static <T> NextTickListEntry<T> a(T var0, BlockPosition var1) {
      return new NextTickListEntry<>(var0, var1, 0L, TickListPriority.d, 0L);
   }

   public T a() {
      return this.d;
   }

   public BlockPosition b() {
      return this.e;
   }

   public long c() {
      return this.f;
   }

   public TickListPriority d() {
      return this.g;
   }

   public long e() {
      return this.h;
   }
}
