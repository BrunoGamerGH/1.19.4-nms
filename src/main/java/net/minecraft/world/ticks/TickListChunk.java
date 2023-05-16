package net.minecraft.world.ticks;

import it.unimi.dsi.fastutil.Hash.Strategy;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.level.ChunkCoordIntPair;

public record TickListChunk<T>(T type, BlockPosition pos, int delay, TickListPriority priority) {
   private final T b;
   private final BlockPosition c;
   private final int d;
   private final TickListPriority e;
   private static final String f = "i";
   private static final String g = "x";
   private static final String h = "y";
   private static final String i = "z";
   private static final String j = "t";
   private static final String k = "p";
   public static final Strategy<TickListChunk<?>> a = new Strategy<TickListChunk<?>>() {
      public int a(TickListChunk<?> var0) {
         return 31 * var0.b().hashCode() + var0.a().hashCode();
      }

      public boolean a(@Nullable TickListChunk<?> var0, @Nullable TickListChunk<?> var1) {
         if (var0 == var1) {
            return true;
         } else if (var0 != null && var1 != null) {
            return var0.a() == var1.a() && var0.b().equals(var1.b());
         } else {
            return false;
         }
      }
   };

   public TickListChunk(T var0, BlockPosition var1, int var2, TickListPriority var3) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
   }

   public static <T> void a(NBTTagList var0, Function<String, Optional<T>> var1, ChunkCoordIntPair var2, Consumer<TickListChunk<T>> var3) {
      long var4 = var2.a();

      for(int var6 = 0; var6 < var0.size(); ++var6) {
         NBTTagCompound var7 = var0.a(var6);
         a(var7, var1).ifPresent(var3x -> {
            if (ChunkCoordIntPair.a(var3x.b()) == var4) {
               var3.accept(var3x);
            }
         });
      }
   }

   public static <T> Optional<TickListChunk<T>> a(NBTTagCompound var0, Function<String, Optional<T>> var1) {
      return var1.apply(var0.l("i")).map(var1x -> {
         BlockPosition var2 = new BlockPosition(var0.h("x"), var0.h("y"), var0.h("z"));
         return new TickListChunk<>(var1x, var2, var0.h("t"), TickListPriority.a(var0.h("p")));
      });
   }

   private static NBTTagCompound a(String var0, BlockPosition var1, int var2, TickListPriority var3) {
      NBTTagCompound var4 = new NBTTagCompound();
      var4.a("i", var0);
      var4.a("x", var1.u());
      var4.a("y", var1.v());
      var4.a("z", var1.w());
      var4.a("t", var2);
      var4.a("p", var3.a());
      return var4;
   }

   public static <T> NBTTagCompound a(NextTickListEntry<T> var0, Function<T, String> var1, long var2) {
      return a(var1.apply(var0.a()), var0.b(), (int)(var0.c() - var2), var0.d());
   }

   public NBTTagCompound a(Function<T, String> var0) {
      return a(var0.apply(this.b), this.c, this.d, this.e);
   }

   public NextTickListEntry<T> a(long var0, long var2) {
      return new NextTickListEntry<>(this.b, this.c, var0 + (long)this.d, this.e, var2);
   }

   public static <T> TickListChunk<T> a(T var0, BlockPosition var1) {
      return new TickListChunk<>(var0, var1, 0, TickListPriority.d);
   }

   public T a() {
      return this.b;
   }

   public BlockPosition b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   public TickListPriority d() {
      return this.e;
   }
}
