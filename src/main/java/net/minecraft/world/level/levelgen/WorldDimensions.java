package net.minecraft.world.level.levelgen;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.IRegistryWritable;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.RegistryMaterials;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.biome.WorldChunkManagerMultiNoise;
import net.minecraft.world.level.biome.WorldChunkManagerTheEnd;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.storage.WorldDataServer;

public record WorldDimensions(IRegistry<WorldDimension> dimensions) {
   private final IRegistry<WorldDimension> b;
   public static final MapCodec<WorldDimensions> a = RecordCodecBuilder.mapCodec(
      var0 -> var0.group(RegistryCodecs.b(Registries.aG, Lifecycle.stable(), WorldDimension.a).fieldOf("dimensions").forGetter(WorldDimensions::d))
            .apply(var0, var0.stable(WorldDimensions::new))
   );
   private static final Set<ResourceKey<WorldDimension>> c = ImmutableSet.of(WorldDimension.b, WorldDimension.c, WorldDimension.d);
   private static final int d = c.size();

   public WorldDimensions(IRegistry<WorldDimension> var0) {
      WorldDimension var1 = var0.a(WorldDimension.b);
      if (var1 == null) {
         throw new IllegalStateException("Overworld settings missing");
      } else {
         this.b = var0;
      }
   }

   public static Stream<ResourceKey<WorldDimension>> a(Stream<ResourceKey<WorldDimension>> var0) {
      return Stream.concat(c.stream(), var0.filter(var0x -> !c.contains(var0x)));
   }

   public WorldDimensions a(IRegistryCustom var0, ChunkGenerator var1) {
      IRegistry<DimensionManager> var2 = var0.d(Registries.as);
      IRegistry<WorldDimension> var3 = a(var2, this.b, var1);
      return new WorldDimensions(var3);
   }

   public static IRegistry<WorldDimension> a(IRegistry<DimensionManager> var0, IRegistry<WorldDimension> var1, ChunkGenerator var2) {
      WorldDimension var3 = var1.a(WorldDimension.b);
      Holder<DimensionManager> var4 = (Holder<DimensionManager>)(var3 == null ? var0.f(BuiltinDimensionTypes.a) : var3.a());
      return a(var1, var4, var2);
   }

   public static IRegistry<WorldDimension> a(IRegistry<WorldDimension> var0, Holder<DimensionManager> var1, ChunkGenerator var2) {
      IRegistryWritable<WorldDimension> var3 = new RegistryMaterials<>(Registries.aG, Lifecycle.experimental());
      var3.a(WorldDimension.b, new WorldDimension(var1, var2), Lifecycle.stable());

      for(Entry<ResourceKey<WorldDimension>, WorldDimension> var5 : var0.g()) {
         ResourceKey<WorldDimension> var6 = var5.getKey();
         if (var6 != WorldDimension.b) {
            var3.a(var6, var5.getValue(), var0.e(var5.getValue()));
         }
      }

      return var3.l();
   }

   public ChunkGenerator a() {
      WorldDimension var0 = this.b.a(WorldDimension.b);
      if (var0 == null) {
         throw new IllegalStateException("Overworld settings missing");
      } else {
         return var0.b();
      }
   }

   public Optional<WorldDimension> a(ResourceKey<WorldDimension> var0) {
      return this.b.d(var0);
   }

   public ImmutableSet<ResourceKey<World>> b() {
      return this.d().g().stream().map(Entry::getKey).map(Registries::a).collect(ImmutableSet.toImmutableSet());
   }

   public boolean c() {
      return this.a() instanceof ChunkProviderDebug;
   }

   private static WorldDataServer.a b(IRegistry<WorldDimension> var0) {
      return var0.d(WorldDimension.b).map(var0x -> {
         ChunkGenerator var1 = var0x.b();
         if (var1 instanceof ChunkProviderDebug) {
            return WorldDataServer.a.c;
         } else {
            return var1 instanceof ChunkProviderFlat ? WorldDataServer.a.b : WorldDataServer.a.a;
         }
      }).orElse(WorldDataServer.a.a);
   }

   static Lifecycle a(ResourceKey<WorldDimension> var0, WorldDimension var1) {
      return b(var0, var1) ? Lifecycle.stable() : Lifecycle.experimental();
   }

   private static boolean b(ResourceKey<WorldDimension> var0, WorldDimension var1) {
      if (var0 == WorldDimension.b) {
         return a(var1);
      } else if (var0 == WorldDimension.c) {
         return b(var1);
      } else {
         return var0 == WorldDimension.d ? c(var1) : false;
      }
   }

   private static boolean a(WorldDimension var0) {
      Holder<DimensionManager> var1 = var0.a();
      if (!var1.a(BuiltinDimensionTypes.a) && !var1.a(BuiltinDimensionTypes.d)) {
         return false;
      } else {
         WorldChunkManager var3 = var0.b().c();
         if (var3 instanceof WorldChunkManagerMultiNoise var2 && !var2.a(MultiNoiseBiomeSourceParameterLists.b)) {
            return false;
         }

         return true;
      }
   }

   private static boolean b(WorldDimension var0) {
      if (var0.a().a(BuiltinDimensionTypes.b)) {
         ChunkGenerator var3 = var0.b();
         if (var3 instanceof ChunkGeneratorAbstract var2 && var2.a(GeneratorSettingBase.f)) {
            WorldChunkManager var4 = var2.c();
            if (var4 instanceof WorldChunkManagerMultiNoise var1 && var1.a(MultiNoiseBiomeSourceParameterLists.a)) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean c(WorldDimension var0) {
      if (var0.a().a(BuiltinDimensionTypes.c)) {
         ChunkGenerator var2 = var0.b();
         if (var2 instanceof ChunkGeneratorAbstract var1 && var1.a(GeneratorSettingBase.g) && var1.c() instanceof WorldChunkManagerTheEnd) {
            return true;
         }
      }

      return false;
   }

   public WorldDimensions.b a(IRegistry<WorldDimension> var0) {
      Stream<ResourceKey<WorldDimension>> var1 = Stream.concat(var0.f().stream(), this.b.f().stream()).distinct();

      record a(ResourceKey<WorldDimension> key, WorldDimension value) {
         final ResourceKey<WorldDimension> a;
         final WorldDimension b;

         a(ResourceKey<WorldDimension> var0, WorldDimension var1) {
            this.a = var0;
            this.b = var1;
         }

         Lifecycle c() {
            return WorldDimensions.a(this.a, this.b);
         }
      }

      List<a> var2 = new ArrayList<>();
      a(var1).forEach(var2x -> var0.d(var2x).or(() -> this.b.d(var2x)).ifPresent(var2xx -> var2.add(new a(var2x, var2xx))));
      Lifecycle var3 = var2.size() == d ? Lifecycle.stable() : Lifecycle.experimental();
      IRegistryWritable<WorldDimension> var4 = new RegistryMaterials<>(Registries.aG, var3);
      var2.forEach(var1x -> var4.a(var1x.a, var1x.b, var1x.c()));
      IRegistry<WorldDimension> var5 = var4.l();
      WorldDataServer.a var6 = b(var5);
      return new WorldDimensions.b(var5.l(), var6);
   }

   public IRegistry<WorldDimension> d() {
      return this.b;
   }

   public static record b(IRegistry<WorldDimension> dimensions, WorldDataServer.a specialWorldProperty) {
      private final IRegistry<WorldDimension> a;
      private final WorldDataServer.a b;

      public b(IRegistry<WorldDimension> var0, WorldDataServer.a var1) {
         this.a = var0;
         this.b = var1;
      }

      public Lifecycle a() {
         return this.a.d();
      }

      public IRegistryCustom.Dimension b() {
         return new IRegistryCustom.c(List.of(this.a)).c();
      }

      public IRegistry<WorldDimension> c() {
         return this.a;
      }

      public WorldDataServer.a d() {
         return this.b;
      }
   }
}
