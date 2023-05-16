package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.BitSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;

public final class BelowZeroRetrogen {
   private static final BitSet c = new BitSet(0);
   private static final Codec<BitSet> d = Codec.LONG_STREAM.xmap(var0 -> BitSet.valueOf(var0.toArray()), var0 -> LongStream.of(var0.toLongArray()));
   private static final Codec<ChunkStatus> e = BuiltInRegistries.o
      .q()
      .comapFlatMap(var0 -> var0 == ChunkStatus.c ? DataResult.error(() -> "target_status cannot be empty") : DataResult.success(var0), Function.identity());
   public static final Codec<BelowZeroRetrogen> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               e.fieldOf("target_status").forGetter(BelowZeroRetrogen::a),
               d.optionalFieldOf("missing_bedrock").forGetter(var0x -> var0x.h.isEmpty() ? Optional.empty() : Optional.of(var0x.h))
            )
            .apply(var0, BelowZeroRetrogen::new)
   );
   private static final Set<ResourceKey<BiomeBase>> f = Set.of(Biomes.aa, Biomes.Z);
   public static final LevelHeightAccessor b = new LevelHeightAccessor() {
      @Override
      public int w_() {
         return 64;
      }

      @Override
      public int v_() {
         return -64;
      }
   };
   private final ChunkStatus g;
   private final BitSet h;

   private BelowZeroRetrogen(ChunkStatus var0, Optional<BitSet> var1) {
      this.g = var0;
      this.h = var1.orElse(c);
   }

   @Nullable
   public static BelowZeroRetrogen a(NBTTagCompound var0) {
      ChunkStatus var1 = ChunkStatus.a(var0.l("target_status"));
      return var1 == ChunkStatus.c ? null : new BelowZeroRetrogen(var1, Optional.of(BitSet.valueOf(var0.o("missing_bedrock"))));
   }

   public static void a(ProtoChunk var0) {
      int var1 = 4;
      BlockPosition.b(0, 0, 0, 15, 4, 15).forEach(var1x -> {
         if (var0.a_(var1x).a(Blocks.F)) {
            var0.a(var1x, Blocks.rD.o(), false);
         }
      });
   }

   public void b(ProtoChunk var0) {
      LevelHeightAccessor var1 = var0.z();
      int var2 = var1.v_();
      int var3 = var1.ai() - 1;

      for(int var4 = 0; var4 < 16; ++var4) {
         for(int var5 = 0; var5 < 16; ++var5) {
            if (this.a(var4, var5)) {
               BlockPosition.b(var4, var2, var5, var4, var3, var5).forEach(var1x -> var0.a(var1x, Blocks.a.o(), false));
            }
         }
      }
   }

   public ChunkStatus a() {
      return this.g;
   }

   public boolean b() {
      return !this.h.isEmpty();
   }

   public boolean a(int var0, int var1) {
      return this.h.get((var1 & 15) * 16 + (var0 & 15));
   }

   public static BiomeResolver a(BiomeResolver var0, IChunkAccess var1) {
      if (!var1.y()) {
         return var0;
      } else {
         Predicate<ResourceKey<BiomeBase>> var2 = f::contains;
         return (var3, var4, var5, var6) -> {
            Holder<BiomeBase> var7 = var0.getNoiseBiome(var3, var4, var5, var6);
            return var7.a(var2) ? var7 : var1.getNoiseBiome(var3, 0, var5);
         };
      }
   }
}
