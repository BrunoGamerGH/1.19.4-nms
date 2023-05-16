package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;

public record WorldGenCarverWrapper<WC extends WorldGenCarverConfiguration>(WorldGenCarverAbstract<WC> worldCarver, WC config) {
   private final WorldGenCarverAbstract<WC> d;
   private final WC e;
   public static final Codec<WorldGenCarverWrapper<?>> a = BuiltInRegistries.P.q().dispatch(var0 -> var0.d, WorldGenCarverAbstract::c);
   public static final Codec<Holder<WorldGenCarverWrapper<?>>> b = RegistryFileCodec.a(Registries.ap, a);
   public static final Codec<HolderSet<WorldGenCarverWrapper<?>>> c = RegistryCodecs.a(Registries.ap, a);

   public WorldGenCarverWrapper(WorldGenCarverAbstract<WC> var0, WC var1) {
      this.d = var0;
      this.e = var1;
   }

   public boolean a(RandomSource var0) {
      return this.d.a(this.e, var0);
   }

   public boolean a(
      CarvingContext var0,
      IChunkAccess var1,
      Function<BlockPosition, Holder<BiomeBase>> var2,
      RandomSource var3,
      Aquifer var4,
      ChunkCoordIntPair var5,
      CarvingMask var6
   ) {
      return SharedConstants.a(var1.f()) ? false : this.d.a(var0, this.e, var1, var2, var3, var4, var5, var6);
   }

   public WorldGenCarverAbstract<WC> a() {
      return this.d;
   }

   public WC b() {
      return this.e;
   }
}
