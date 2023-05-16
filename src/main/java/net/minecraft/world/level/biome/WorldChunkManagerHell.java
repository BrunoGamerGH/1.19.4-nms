package net.minecraft.world.level.biome;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IWorldReader;

public class WorldChunkManagerHell extends WorldChunkManager implements BiomeManager.Provider {
   public static final Codec<WorldChunkManagerHell> b = BiomeBase.c.fieldOf("biome").xmap(WorldChunkManagerHell::new, var0 -> var0.c).stable().codec();
   private final Holder<BiomeBase> c;

   public WorldChunkManagerHell(Holder<BiomeBase> var0) {
      this.c = var0;
   }

   @Override
   protected Stream<Holder<BiomeBase>> b() {
      return Stream.of(this.c);
   }

   @Override
   protected Codec<? extends WorldChunkManager> a() {
      return b;
   }

   @Override
   public Holder<BiomeBase> getNoiseBiome(int var0, int var1, int var2, Climate.Sampler var3) {
      return this.c;
   }

   @Override
   public Holder<BiomeBase> getNoiseBiome(int var0, int var1, int var2) {
      return this.c;
   }

   @Nullable
   @Override
   public Pair<BlockPosition, Holder<BiomeBase>> a(
      int var0, int var1, int var2, int var3, int var4, Predicate<Holder<BiomeBase>> var5, RandomSource var6, boolean var7, Climate.Sampler var8
   ) {
      if (var5.test(this.c)) {
         return var7
            ? Pair.of(new BlockPosition(var0, var1, var2), this.c)
            : Pair.of(new BlockPosition(var0 - var3 + var6.a(var3 * 2 + 1), var1, var2 - var3 + var6.a(var3 * 2 + 1)), this.c);
      } else {
         return null;
      }
   }

   @Nullable
   @Override
   public Pair<BlockPosition, Holder<BiomeBase>> a(
      BlockPosition var0, int var1, int var2, int var3, Predicate<Holder<BiomeBase>> var4, Climate.Sampler var5, IWorldReader var6
   ) {
      return var4.test(this.c) ? Pair.of(var0, this.c) : null;
   }

   @Override
   public Set<Holder<BiomeBase>> a(int var0, int var1, int var2, int var3, Climate.Sampler var4) {
      return Sets.newHashSet(Set.of(this.c));
   }
}
