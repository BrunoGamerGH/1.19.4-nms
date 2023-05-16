package net.minecraft.world.level.biome;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IWorldReader;

public abstract class WorldChunkManager implements BiomeResolver {
   public static final Codec<WorldChunkManager> a = BuiltInRegistries.ab.q().dispatchStable(WorldChunkManager::a, Function.identity());
   private final Supplier<Set<Holder<BiomeBase>>> b = Suppliers.memoize(() -> this.b().distinct().collect(ImmutableSet.toImmutableSet()));

   protected WorldChunkManager() {
   }

   protected abstract Codec<? extends WorldChunkManager> a();

   protected abstract Stream<Holder<BiomeBase>> b();

   public Set<Holder<BiomeBase>> c() {
      return this.b.get();
   }

   public Set<Holder<BiomeBase>> a(int var0, int var1, int var2, int var3, Climate.Sampler var4) {
      int var5 = QuartPos.a(var0 - var3);
      int var6 = QuartPos.a(var1 - var3);
      int var7 = QuartPos.a(var2 - var3);
      int var8 = QuartPos.a(var0 + var3);
      int var9 = QuartPos.a(var1 + var3);
      int var10 = QuartPos.a(var2 + var3);
      int var11 = var8 - var5 + 1;
      int var12 = var9 - var6 + 1;
      int var13 = var10 - var7 + 1;
      Set<Holder<BiomeBase>> var14 = Sets.newHashSet();

      for(int var15 = 0; var15 < var13; ++var15) {
         for(int var16 = 0; var16 < var11; ++var16) {
            for(int var17 = 0; var17 < var12; ++var17) {
               int var18 = var5 + var16;
               int var19 = var6 + var17;
               int var20 = var7 + var15;
               var14.add(this.getNoiseBiome(var18, var19, var20, var4));
            }
         }
      }

      return var14;
   }

   @Nullable
   public Pair<BlockPosition, Holder<BiomeBase>> a(
      int var0, int var1, int var2, int var3, Predicate<Holder<BiomeBase>> var4, RandomSource var5, Climate.Sampler var6
   ) {
      return this.a(var0, var1, var2, var3, 1, var4, var5, false, var6);
   }

   @Nullable
   public Pair<BlockPosition, Holder<BiomeBase>> a(
      BlockPosition var0, int var1, int var2, int var3, Predicate<Holder<BiomeBase>> var4, Climate.Sampler var5, IWorldReader var6
   ) {
      Set<Holder<BiomeBase>> var7 = this.c().stream().filter(var4).collect(Collectors.toUnmodifiableSet());
      if (var7.isEmpty()) {
         return null;
      } else {
         int var8 = Math.floorDiv(var1, var2);
         int[] var9 = MathHelper.a(var0.v(), var6.v_() + 1, var6.ai(), var3).toArray();

         for(BlockPosition.MutableBlockPosition var11 : BlockPosition.a(BlockPosition.b, var8, EnumDirection.f, EnumDirection.d)) {
            int var12 = var0.u() + var11.u() * var2;
            int var13 = var0.w() + var11.w() * var2;
            int var14 = QuartPos.a(var12);
            int var15 = QuartPos.a(var13);

            for(int var19 : var9) {
               int var20 = QuartPos.a(var19);
               Holder<BiomeBase> var21 = this.getNoiseBiome(var14, var20, var15, var5);
               if (var7.contains(var21)) {
                  return Pair.of(new BlockPosition(var12, var19, var13), var21);
               }
            }
         }

         return null;
      }
   }

   @Nullable
   public Pair<BlockPosition, Holder<BiomeBase>> a(
      int var0, int var1, int var2, int var3, int var4, Predicate<Holder<BiomeBase>> var5, RandomSource var6, boolean var7, Climate.Sampler var8
   ) {
      int var9 = QuartPos.a(var0);
      int var10 = QuartPos.a(var2);
      int var11 = QuartPos.a(var3);
      int var12 = QuartPos.a(var1);
      Pair<BlockPosition, Holder<BiomeBase>> var13 = null;
      int var14 = 0;
      int var15 = var7 ? 0 : var11;

      for(int var16 = var15; var16 <= var11; var16 += var4) {
         for(int var17 = SharedConstants.an ? 0 : -var16; var17 <= var16; var17 += var4) {
            boolean var18 = Math.abs(var17) == var16;

            for(int var19 = -var16; var19 <= var16; var19 += var4) {
               if (var7) {
                  boolean var20 = Math.abs(var19) == var16;
                  if (!var20 && !var18) {
                     continue;
                  }
               }

               int var20 = var9 + var19;
               int var21 = var10 + var17;
               Holder<BiomeBase> var22 = this.getNoiseBiome(var20, var12, var21, var8);
               if (var5.test(var22)) {
                  if (var13 == null || var6.a(var14 + 1) == 0) {
                     BlockPosition var23 = new BlockPosition(QuartPos.c(var20), var1, QuartPos.c(var21));
                     if (var7) {
                        return Pair.of(var23, var22);
                     }

                     var13 = Pair.of(var23, var22);
                  }

                  ++var14;
               }
            }
         }
      }

      return var13;
   }

   @Override
   public abstract Holder<BiomeBase> getNoiseBiome(int var1, int var2, int var3, Climate.Sampler var4);

   public void a(List<String> var0, BlockPosition var1, Climate.Sampler var2) {
   }
}
