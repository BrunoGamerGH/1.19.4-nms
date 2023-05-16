package net.minecraft.world.level.chunk;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.Registry;
import net.minecraft.network.PacketDataSerializer;
import org.apache.commons.lang3.Validate;

public class DataPaletteLinear<T> implements DataPalette<T> {
   private final Registry<T> a;
   private final T[] b;
   private final DataPaletteExpandable<T> c;
   private final int d;
   private int e;

   private DataPaletteLinear(Registry<T> var0, int var1, DataPaletteExpandable<T> var2, List<T> var3) {
      this.a = var0;
      this.b = (T[])(new Object[1 << var1]);
      this.d = var1;
      this.c = var2;
      Validate.isTrue(var3.size() <= this.b.length, "Can't initialize LinearPalette of size %d with %d entries", new Object[]{this.b.length, var3.size()});

      for(int var4 = 0; var4 < var3.size(); ++var4) {
         this.b[var4] = var3.get(var4);
      }

      this.e = var3.size();
   }

   private DataPaletteLinear(Registry<T> var0, T[] var1, DataPaletteExpandable<T> var2, int var3, int var4) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
   }

   public static <A> DataPalette<A> a(int var0, Registry<A> var1, DataPaletteExpandable<A> var2, List<A> var3) {
      return new DataPaletteLinear<>(var1, var0, var2, var3);
   }

   @Override
   public int a(T var0) {
      for(int var1 = 0; var1 < this.e; ++var1) {
         if (this.b[var1] == var0) {
            return var1;
         }
      }

      int var1 = this.e;
      if (var1 < this.b.length) {
         this.b[var1] = var0;
         ++this.e;
         return var1;
      } else {
         return this.c.onResize(this.d + 1, var0);
      }
   }

   @Override
   public boolean a(Predicate<T> var0) {
      for(int var1 = 0; var1 < this.e; ++var1) {
         if (var0.test(this.b[var1])) {
            return true;
         }
      }

      return false;
   }

   @Override
   public T a(int var0) {
      if (var0 >= 0 && var0 < this.e) {
         return this.b[var0];
      } else {
         throw new MissingPaletteEntryException(var0);
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      this.e = var0.m();

      for(int var1 = 0; var1 < this.e; ++var1) {
         this.b[var1] = this.a.b(var0.m());
      }
   }

   @Override
   public void b(PacketDataSerializer var0) {
      var0.d(this.e);

      for(int var1 = 0; var1 < this.e; ++var1) {
         var0.d(this.a.a(this.b[var1]));
      }
   }

   @Override
   public int a() {
      int var0 = PacketDataSerializer.a(this.b());

      for(int var1 = 0; var1 < this.b(); ++var1) {
         var0 += PacketDataSerializer.a(this.a.a(this.b[var1]));
      }

      return var0;
   }

   @Override
   public int b() {
      return this.e;
   }

   @Override
   public DataPalette<T> c() {
      return new DataPaletteLinear<>(this.a, (T[])((Object[])this.b.clone()), this.c, this.d, this.e);
   }
}
