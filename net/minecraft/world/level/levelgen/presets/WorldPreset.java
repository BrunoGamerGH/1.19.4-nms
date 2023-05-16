package net.minecraft.world.level.levelgen.presets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryWritable;
import net.minecraft.core.RegistryMaterials;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.levelgen.WorldDimensions;

public class WorldPreset {
   public static final Codec<WorldPreset> a = ExtraCodecs.a(
      RecordCodecBuilder.create(
         var0 -> var0.group(Codec.unboundedMap(ResourceKey.a(Registries.aG), WorldDimension.a).fieldOf("dimensions").forGetter(var0x -> var0x.c))
               .apply(var0, WorldPreset::new)
      ),
      WorldPreset::a
   );
   public static final Codec<Holder<WorldPreset>> b = RegistryFileCodec.a(Registries.aD, a);
   private final Map<ResourceKey<WorldDimension>, WorldDimension> c;

   public WorldPreset(Map<ResourceKey<WorldDimension>, WorldDimension> var0) {
      this.c = var0;
   }

   private IRegistry<WorldDimension> c() {
      IRegistryWritable<WorldDimension> var0 = new RegistryMaterials<>(Registries.aG, Lifecycle.experimental());
      WorldDimensions.a(this.c.keySet().stream()).forEach(var1x -> {
         WorldDimension var2 = this.c.get(var1x);
         if (var2 != null) {
            var0.a(var1x, var2, Lifecycle.stable());
         }
      });
      return var0.l();
   }

   public WorldDimensions a() {
      return new WorldDimensions(this.c());
   }

   public Optional<WorldDimension> b() {
      return Optional.ofNullable(this.c.get(WorldDimension.b));
   }

   private static DataResult<WorldPreset> a(WorldPreset var0) {
      return var0.b().isEmpty() ? DataResult.error(() -> "Missing overworld dimension") : DataResult.success(var0, Lifecycle.stable());
   }
}
