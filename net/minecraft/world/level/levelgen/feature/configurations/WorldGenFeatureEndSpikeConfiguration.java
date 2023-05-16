package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.levelgen.feature.WorldGenEnder;

public class WorldGenFeatureEndSpikeConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureEndSpikeConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.BOOL.fieldOf("crystal_invulnerable").orElse(false).forGetter(var0x -> var0x.b),
               WorldGenEnder.Spike.a.listOf().fieldOf("spikes").forGetter(var0x -> var0x.c),
               BlockPosition.a.optionalFieldOf("crystal_beam_target").forGetter(var0x -> Optional.ofNullable(var0x.d))
            )
            .apply(var0, WorldGenFeatureEndSpikeConfiguration::new)
   );
   private final boolean b;
   private final List<WorldGenEnder.Spike> c;
   @Nullable
   private final BlockPosition d;

   public WorldGenFeatureEndSpikeConfiguration(boolean var0, List<WorldGenEnder.Spike> var1, @Nullable BlockPosition var2) {
      this(var0, var1, Optional.ofNullable(var2));
   }

   private WorldGenFeatureEndSpikeConfiguration(boolean var0, List<WorldGenEnder.Spike> var1, Optional<BlockPosition> var2) {
      this.b = var0;
      this.c = var1;
      this.d = var2.orElse(null);
   }

   public boolean a() {
      return this.b;
   }

   public List<WorldGenEnder.Spike> b() {
      return this.c;
   }

   @Nullable
   public BlockPosition c() {
      return this.d;
   }
}
