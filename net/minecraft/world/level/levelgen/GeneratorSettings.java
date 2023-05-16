package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.Registries;

public record GeneratorSettings(WorldOptions options, WorldDimensions dimensions) {
   private final WorldOptions b;
   private final WorldDimensions c;
   public static final Codec<GeneratorSettings> a = RecordCodecBuilder.create(
      var0 -> var0.group(WorldOptions.a.forGetter(GeneratorSettings::a), WorldDimensions.a.forGetter(GeneratorSettings::b))
            .apply(var0, var0.stable(GeneratorSettings::new))
   );

   public GeneratorSettings(WorldOptions var0, WorldDimensions var1) {
      this.b = var0;
      this.c = var1;
   }

   public static <T> DataResult<T> a(DynamicOps<T> var0, WorldOptions var1, WorldDimensions var2) {
      return a.encodeStart(var0, new GeneratorSettings(var1, var2));
   }

   public static <T> DataResult<T> a(DynamicOps<T> var0, WorldOptions var1, IRegistryCustom var2) {
      return a(var0, var1, new WorldDimensions(var2.d(Registries.aG)));
   }

   public WorldOptions a() {
      return this.b;
   }

   public WorldDimensions b() {
      return this.c;
   }
}
