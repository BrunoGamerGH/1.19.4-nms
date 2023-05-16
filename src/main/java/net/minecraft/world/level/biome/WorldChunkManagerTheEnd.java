package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPosition;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.levelgen.DensityFunction;

public class WorldChunkManagerTheEnd extends WorldChunkManager {
   public static final Codec<WorldChunkManagerTheEnd> b = RecordCodecBuilder.create(
      var0 -> var0.group(RegistryOps.d(Biomes.ah), RegistryOps.d(Biomes.ai), RegistryOps.d(Biomes.aj), RegistryOps.d(Biomes.ak), RegistryOps.d(Biomes.al))
            .apply(var0, var0.stable(WorldChunkManagerTheEnd::new))
   );
   private final Holder<BiomeBase> c;
   private final Holder<BiomeBase> d;
   private final Holder<BiomeBase> e;
   private final Holder<BiomeBase> f;
   private final Holder<BiomeBase> g;

   public static WorldChunkManagerTheEnd a(HolderGetter<BiomeBase> var0) {
      return new WorldChunkManagerTheEnd(var0.b(Biomes.ah), var0.b(Biomes.ai), var0.b(Biomes.aj), var0.b(Biomes.ak), var0.b(Biomes.al));
   }

   private WorldChunkManagerTheEnd(Holder<BiomeBase> var0, Holder<BiomeBase> var1, Holder<BiomeBase> var2, Holder<BiomeBase> var3, Holder<BiomeBase> var4) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
      this.g = var4;
   }

   @Override
   protected Stream<Holder<BiomeBase>> b() {
      return Stream.of(this.c, this.d, this.e, this.f, this.g);
   }

   @Override
   protected Codec<? extends WorldChunkManager> a() {
      return b;
   }

   @Override
   public Holder<BiomeBase> getNoiseBiome(int var0, int var1, int var2, Climate.Sampler var3) {
      int var4 = QuartPos.c(var0);
      int var5 = QuartPos.c(var1);
      int var6 = QuartPos.c(var2);
      int var7 = SectionPosition.a(var4);
      int var8 = SectionPosition.a(var6);
      if ((long)var7 * (long)var7 + (long)var8 * (long)var8 <= 4096L) {
         return this.c;
      } else {
         int var9 = (SectionPosition.a(var4) * 2 + 1) * 8;
         int var10 = (SectionPosition.a(var6) * 2 + 1) * 8;
         double var11 = var3.e().a(new DensityFunction.e(var9, var5, var10));
         if (var11 > 0.25) {
            return this.d;
         } else if (var11 >= -0.0625) {
            return this.e;
         } else {
            return var11 < -0.21875 ? this.f : this.g;
         }
      }
   }
}
