package net.minecraft.world.level;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class DataPackConfiguration {
   public static final DataPackConfiguration a = new DataPackConfiguration(ImmutableList.of("vanilla"), ImmutableList.of());
   public static final Codec<DataPackConfiguration> b = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.STRING.listOf().fieldOf("Enabled").forGetter(var0x -> var0x.c), Codec.STRING.listOf().fieldOf("Disabled").forGetter(var0x -> var0x.d)
            )
            .apply(var0, DataPackConfiguration::new)
   );
   private final List<String> c;
   private final List<String> d;

   public DataPackConfiguration(List<String> var0, List<String> var1) {
      this.c = ImmutableList.copyOf(var0);
      this.d = ImmutableList.copyOf(var1);
   }

   public List<String> a() {
      return this.c;
   }

   public List<String> b() {
      return this.d;
   }
}
