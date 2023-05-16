package net.minecraft.world.level.chunk;

import com.mojang.serialization.DataResult;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import net.minecraft.core.Registry;
import net.minecraft.network.PacketDataSerializer;

public interface PalettedContainerRO<T> {
   T a(int var1, int var2, int var3);

   void a(Consumer<T> var1);

   void b(PacketDataSerializer var1);

   int c();

   boolean a(Predicate<T> var1);

   void a(DataPaletteBlock.b<T> var1);

   DataPaletteBlock<T> e();

   PalettedContainerRO.a<T> a(Registry<T> var1, DataPaletteBlock.d var2);

   public static record a<T>(List<T> paletteEntries, Optional<LongStream> storage) {
      private final List<T> a;
      private final Optional<LongStream> b;

      public a(List<T> var0, Optional<LongStream> var1) {
         this.a = var0;
         this.b = var1;
      }
   }

   public interface b<T, C extends PalettedContainerRO<T>> {
      DataResult<C> read(Registry<T> var1, DataPaletteBlock.d var2, PalettedContainerRO.a<T> var3);
   }
}
