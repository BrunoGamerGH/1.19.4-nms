package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;

public class WorldChunkManagerCheckerBoard extends WorldChunkManager {
   public static final Codec<WorldChunkManagerCheckerBoard> b = RecordCodecBuilder.create(
      var0 -> var0.group(
               BiomeBase.d.fieldOf("biomes").forGetter(var0x -> var0x.c), Codec.intRange(0, 62).fieldOf("scale").orElse(2).forGetter(var0x -> var0x.e)
            )
            .apply(var0, WorldChunkManagerCheckerBoard::new)
   );
   private final HolderSet<BiomeBase> c;
   private final int d;
   private final int e;

   public WorldChunkManagerCheckerBoard(HolderSet<BiomeBase> var0, int var1) {
      this.c = var0;
      this.d = var1 + 2;
      this.e = var1;
   }

   @Override
   protected Stream<Holder<BiomeBase>> b() {
      return this.c.a();
   }

   @Override
   protected Codec<? extends WorldChunkManager> a() {
      return b;
   }

   @Override
   public Holder<BiomeBase> getNoiseBiome(int var0, int var1, int var2, Climate.Sampler var3) {
      return this.c.a(Math.floorMod((var0 >> this.d) + (var2 >> this.d), this.c.b()));
   }
}
