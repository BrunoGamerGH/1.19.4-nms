package net.minecraft.world.level.chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.Registry;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.util.RegistryID;

public class DataPaletteHash<T> implements DataPalette<T> {
   private final Registry<T> a;
   private final RegistryID<T> b;
   private final DataPaletteExpandable<T> c;
   private final int d;

   public DataPaletteHash(Registry<T> var0, int var1, DataPaletteExpandable<T> var2, List<T> var3) {
      this(var0, var1, var2);
      var3.forEach(this.b::c);
   }

   public DataPaletteHash(Registry<T> var0, int var1, DataPaletteExpandable<T> var2) {
      this(var0, var1, var2, RegistryID.c(1 << var1));
   }

   private DataPaletteHash(Registry<T> var0, int var1, DataPaletteExpandable<T> var2, RegistryID<T> var3) {
      this.a = var0;
      this.d = var1;
      this.c = var2;
      this.b = var3;
   }

   public static <A> DataPalette<A> a(int var0, Registry<A> var1, DataPaletteExpandable<A> var2, List<A> var3) {
      return new DataPaletteHash<>(var1, var0, var2, var3);
   }

   @Override
   public int a(T var0) {
      int var1 = this.b.a(var0);
      if (var1 == -1) {
         var1 = this.b.c(var0);
         if (var1 >= 1 << this.d) {
            var1 = this.c.onResize(this.d + 1, var0);
         }
      }

      return var1;
   }

   @Override
   public boolean a(Predicate<T> var0) {
      for(int var1 = 0; var1 < this.b(); ++var1) {
         if (var0.test(this.b.a(var1))) {
            return true;
         }
      }

      return false;
   }

   @Override
   public T a(int var0) {
      T var1 = this.b.a(var0);
      if (var1 == null) {
         throw new MissingPaletteEntryException(var0);
      } else {
         return var1;
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      this.b.a();
      int var1 = var0.m();

      for(int var2 = 0; var2 < var1; ++var2) {
         this.b.c(this.a.b(var0.m()));
      }
   }

   @Override
   public void b(PacketDataSerializer var0) {
      int var1 = this.b();
      var0.d(var1);

      for(int var2 = 0; var2 < var1; ++var2) {
         var0.d(this.a.a(this.b.a(var2)));
      }
   }

   @Override
   public int a() {
      int var0 = PacketDataSerializer.a(this.b());

      for(int var1 = 0; var1 < this.b(); ++var1) {
         var0 += PacketDataSerializer.a(this.a.a(this.b.a(var1)));
      }

      return var0;
   }

   public List<T> d() {
      ArrayList<T> var0 = new ArrayList<>();
      this.b.iterator().forEachRemaining(var0::add);
      return var0;
   }

   @Override
   public int b() {
      return this.b.b();
   }

   @Override
   public DataPalette<T> c() {
      return new DataPaletteHash<>(this.a, this.d, this.c, this.b.c());
   }
}
