package net.minecraft.world.level.biome;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.levelgen.NoiseRouterData;

public class WorldChunkManagerMultiNoise extends WorldChunkManager {
   private static final MapCodec<Holder<BiomeBase>> d = BiomeBase.c.fieldOf("biome");
   public static final MapCodec<Climate.c<Holder<BiomeBase>>> b = Climate.c.a(d).fieldOf("biomes");
   private static final MapCodec<Holder<MultiNoiseBiomeSourceParameterList>> e = MultiNoiseBiomeSourceParameterList.b
      .fieldOf("preset")
      .withLifecycle(Lifecycle.stable());
   public static final Codec<WorldChunkManagerMultiNoise> c = Codec.mapEither(b, e).xmap(WorldChunkManagerMultiNoise::new, var0 -> var0.f).codec();
   private final Either<Climate.c<Holder<BiomeBase>>, Holder<MultiNoiseBiomeSourceParameterList>> f;

   private WorldChunkManagerMultiNoise(Either<Climate.c<Holder<BiomeBase>>, Holder<MultiNoiseBiomeSourceParameterList>> var0) {
      this.f = var0;
   }

   public static WorldChunkManagerMultiNoise a(Climate.c<Holder<BiomeBase>> var0) {
      return new WorldChunkManagerMultiNoise(Either.left(var0));
   }

   public static WorldChunkManagerMultiNoise a(Holder<MultiNoiseBiomeSourceParameterList> var0) {
      return new WorldChunkManagerMultiNoise(Either.right(var0));
   }

   private Climate.c<Holder<BiomeBase>> d() {
      return (Climate.c<Holder<BiomeBase>>)this.f.map(var0 -> var0, var0 -> ((MultiNoiseBiomeSourceParameterList)var0.a()).a());
   }

   @Override
   protected Stream<Holder<BiomeBase>> b() {
      return this.d().a().stream().map(Pair::getSecond);
   }

   @Override
   protected Codec<? extends WorldChunkManager> a() {
      return c;
   }

   public boolean a(ResourceKey<MultiNoiseBiomeSourceParameterList> var0) {
      Optional<Holder<MultiNoiseBiomeSourceParameterList>> var1 = this.f.right();
      return var1.isPresent() && var1.get().a(var0);
   }

   @Override
   public Holder<BiomeBase> getNoiseBiome(int var0, int var1, int var2, Climate.Sampler var3) {
      return this.a(var3.a(var0, var1, var2));
   }

   @VisibleForDebug
   public Holder<BiomeBase> a(Climate.h var0) {
      return this.d().a(var0);
   }

   @Override
   public void a(List<String> var0, BlockPosition var1, Climate.Sampler var2) {
      int var3 = QuartPos.a(var1.u());
      int var4 = QuartPos.a(var1.v());
      int var5 = QuartPos.a(var1.w());
      Climate.h var6 = var2.a(var3, var4, var5);
      float var7 = Climate.a(var6.d());
      float var8 = Climate.a(var6.e());
      float var9 = Climate.a(var6.b());
      float var10 = Climate.a(var6.c());
      float var11 = Climate.a(var6.g());
      double var12 = (double)NoiseRouterData.a(var11);
      OverworldBiomeBuilder var14 = new OverworldBiomeBuilder();
      var0.add(
         "Biome builder PV: "
            + OverworldBiomeBuilder.a(var12)
            + " C: "
            + var14.b((double)var7)
            + " E: "
            + var14.c((double)var8)
            + " T: "
            + var14.d((double)var9)
            + " H: "
            + var14.e((double)var10)
      );
   }
}
