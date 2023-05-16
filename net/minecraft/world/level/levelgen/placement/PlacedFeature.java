package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import org.apache.commons.lang3.mutable.MutableBoolean;

public record PlacedFeature(Holder<WorldGenFeatureConfigured<?, ?>> feature, List<PlacementModifier> placement) {
   private final Holder<WorldGenFeatureConfigured<?, ?>> e;
   private final List<PlacementModifier> f;
   public static final Codec<PlacedFeature> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               WorldGenFeatureConfigured.b.fieldOf("feature").forGetter(var0x -> var0x.e),
               PlacementModifier.b.listOf().fieldOf("placement").forGetter(var0x -> var0x.f)
            )
            .apply(var0, PlacedFeature::new)
   );
   public static final Codec<Holder<PlacedFeature>> b = RegistryFileCodec.a(Registries.aw, a);
   public static final Codec<HolderSet<PlacedFeature>> c = RegistryCodecs.a(Registries.aw, a);
   public static final Codec<List<HolderSet<PlacedFeature>>> d = RegistryCodecs.a(Registries.aw, a, true).listOf();

   public PlacedFeature(Holder<WorldGenFeatureConfigured<?, ?>> var0, List<PlacementModifier> var1) {
      this.e = var0;
      this.f = var1;
   }

   public boolean a(GeneratorAccessSeed var0, ChunkGenerator var1, RandomSource var2, BlockPosition var3) {
      return this.a(new PlacementContext(var0, var1, Optional.empty()), var2, var3);
   }

   public boolean b(GeneratorAccessSeed var0, ChunkGenerator var1, RandomSource var2, BlockPosition var3) {
      return this.a(new PlacementContext(var0, var1, Optional.of(this)), var2, var3);
   }

   private boolean a(PlacementContext var0, RandomSource var1, BlockPosition var2) {
      Stream<BlockPosition> var3 = Stream.of(var2);

      for(PlacementModifier var5 : this.f) {
         var3 = var3.flatMap(var3x -> var5.a_(var0, var1, var3x));
      }

      WorldGenFeatureConfigured<?, ?> var4 = this.e.a();
      MutableBoolean var5 = new MutableBoolean();
      var3.forEach(var4x -> {
         if (var4.a(var0.d(), var0.f(), var1, var4x)) {
            var5.setTrue();
         }
      });
      return var5.isTrue();
   }

   public Stream<WorldGenFeatureConfigured<?, ?>> a() {
      return this.e.a().a();
   }

   @Override
   public String toString() {
      return "Placed " + this.e;
   }

   public Holder<WorldGenFeatureConfigured<?, ?>> b() {
      return this.e;
   }

   public List<PlacementModifier> c() {
      return this.f;
   }
}
