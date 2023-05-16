package net.minecraft.world.level.chunk;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.Registry;
import net.minecraft.network.PacketDataSerializer;

public interface DataPalette<T> {
   int a(T var1);

   boolean a(Predicate<T> var1);

   T a(int var1);

   void a(PacketDataSerializer var1);

   void b(PacketDataSerializer var1);

   int a();

   int b();

   DataPalette<T> c();

   public interface a {
      <A> DataPalette<A> create(int var1, Registry<A> var2, DataPaletteExpandable<A> var3, List<A> var4);
   }
}
