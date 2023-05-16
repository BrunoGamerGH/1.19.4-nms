package net.minecraft.network.syncher;

import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.network.PacketDataSerializer;

public interface DataWatcherSerializer<T> {
   void a(PacketDataSerializer var1, T var2);

   T a(PacketDataSerializer var1);

   default DataWatcherObject<T> a(int var0) {
      return new DataWatcherObject<>(var0, this);
   }

   T a(T var1);

   static <T> DataWatcherSerializer<T> a(final PacketDataSerializer.b<T> var0, final PacketDataSerializer.a<T> var1) {
      return new DataWatcherSerializer.a<T>() {
         @Override
         public void a(PacketDataSerializer var0x, T var1x) {
            var0.accept((T)var0, var1);
         }

         @Override
         public T a(PacketDataSerializer var0x) {
            return var1.apply((T)var0);
         }
      };
   }

   static <T> DataWatcherSerializer<Optional<T>> b(PacketDataSerializer.b<T> var0, PacketDataSerializer.a<T> var1) {
      return a(var0.asOptional(), var1.asOptional());
   }

   static <T extends Enum<T>> DataWatcherSerializer<T> a(Class<T> var0) {
      return a(PacketDataSerializer::a, var1 -> var1.b(var0));
   }

   static <T> DataWatcherSerializer<T> a(Registry<T> var0) {
      return a((var1, var2) -> var1.a(var0, (T)var2), var1 -> var1.<T>a(var0));
   }

   public interface a<T> extends DataWatcherSerializer<T> {
      @Override
      default T a(T var0) {
         return var0;
      }
   }
}
