package net.minecraft.world.level.chunk;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.Registry;
import net.minecraft.network.PacketDataSerializer;

public class DataPaletteGlobal<T> implements DataPalette<T> {
   private final Registry<T> a;

   public DataPaletteGlobal(Registry<T> var0) {
      this.a = var0;
   }

   public static <A> DataPalette<A> a(int var0, Registry<A> var1, DataPaletteExpandable<A> var2, List<A> var3) {
      return new DataPaletteGlobal<>(var1);
   }

   @Override
   public int a(T var0) {
      int var1 = this.a.a(var0);
      return var1 == -1 ? 0 : var1;
   }

   @Override
   public boolean a(Predicate<T> var0) {
      return true;
   }

   @Override
   public T a(int var0) {
      T var1 = this.a.a(var0);
      if (var1 == null) {
         throw new MissingPaletteEntryException(var0);
      } else {
         return var1;
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
   }

   @Override
   public void b(PacketDataSerializer var0) {
   }

   @Override
   public int a() {
      return PacketDataSerializer.a(0);
   }

   @Override
   public int b() {
      return this.a.b();
   }

   @Override
   public DataPalette<T> c() {
      return this;
   }
}
