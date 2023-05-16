package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPosition;

public class WorldGenEndGatewayConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenEndGatewayConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(BlockPosition.a.optionalFieldOf("exit").forGetter(var0x -> var0x.b), Codec.BOOL.fieldOf("exact").forGetter(var0x -> var0x.c))
            .apply(var0, WorldGenEndGatewayConfiguration::new)
   );
   private final Optional<BlockPosition> b;
   private final boolean c;

   private WorldGenEndGatewayConfiguration(Optional<BlockPosition> var0, boolean var1) {
      this.b = var0;
      this.c = var1;
   }

   public static WorldGenEndGatewayConfiguration a(BlockPosition var0, boolean var1) {
      return new WorldGenEndGatewayConfiguration(Optional.of(var0), var1);
   }

   public static WorldGenEndGatewayConfiguration a() {
      return new WorldGenEndGatewayConfiguration(Optional.empty(), false);
   }

   public Optional<BlockPosition> b() {
      return this.b;
   }

   public boolean c() {
      return this.c;
   }
}
