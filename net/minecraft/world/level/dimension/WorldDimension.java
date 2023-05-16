package net.minecraft.world.level.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.chunk.ChunkGenerator;

public record WorldDimension(Holder<DimensionManager> type, ChunkGenerator generator) {
   private final Holder<DimensionManager> e;
   private final ChunkGenerator f;
   public static final Codec<WorldDimension> a = RecordCodecBuilder.create(
      var0 -> var0.group(DimensionManager.j.fieldOf("type").forGetter(WorldDimension::a), ChunkGenerator.a.fieldOf("generator").forGetter(WorldDimension::b))
            .apply(var0, var0.stable(WorldDimension::new))
   );
   public static final ResourceKey<WorldDimension> b = ResourceKey.a(Registries.aG, new MinecraftKey("overworld"));
   public static final ResourceKey<WorldDimension> c = ResourceKey.a(Registries.aG, new MinecraftKey("the_nether"));
   public static final ResourceKey<WorldDimension> d = ResourceKey.a(Registries.aG, new MinecraftKey("the_end"));

   public WorldDimension(Holder<DimensionManager> var0, ChunkGenerator var1) {
      this.e = var0;
      this.f = var1;
   }

   public Holder<DimensionManager> a() {
      return this.e;
   }

   public ChunkGenerator b() {
      return this.f;
   }
}
