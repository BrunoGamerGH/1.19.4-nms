package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructureJigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;

public final class JigsawStructure extends Structure {
   public static final int d = 128;
   public static final Codec<JigsawStructure> e = RecordCodecBuilder.mapCodec(
         var0 -> var0.group(
                  a(var0),
                  WorldGenFeatureDefinedStructurePoolTemplate.b.fieldOf("start_pool").forGetter(var0x -> var0x.f),
                  MinecraftKey.a.optionalFieldOf("start_jigsaw_name").forGetter(var0x -> var0x.g),
                  Codec.intRange(0, 7).fieldOf("size").forGetter(var0x -> var0x.h),
                  HeightProvider.c.fieldOf("start_height").forGetter(var0x -> var0x.i),
                  Codec.BOOL.fieldOf("use_expansion_hack").forGetter(var0x -> var0x.j),
                  HeightMap.Type.g.optionalFieldOf("project_start_to_heightmap").forGetter(var0x -> var0x.k),
                  Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(var0x -> var0x.l)
               )
               .apply(var0, JigsawStructure::new)
      )
      .flatXmap(f(), f())
      .codec();
   private final Holder<WorldGenFeatureDefinedStructurePoolTemplate> f;
   private final Optional<MinecraftKey> g;
   private final int h;
   private final HeightProvider i;
   private final boolean j;
   private final Optional<HeightMap.Type> k;
   private final int l;

   private static Function<JigsawStructure, DataResult<JigsawStructure>> f() {
      return var0 -> {
         int var1 = switch(var0.d()) {
            case a -> 0;
            case b, c, d -> 12;
         };
         return var0.l + var1 > 128 ? DataResult.error(() -> "Structure size including terrain adaptation must not exceed 128") : DataResult.success(var0);
      };
   }

   public JigsawStructure(
      Structure.c var0,
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var1,
      Optional<MinecraftKey> var2,
      int var3,
      HeightProvider var4,
      boolean var5,
      Optional<HeightMap.Type> var6,
      int var7
   ) {
      super(var0);
      this.f = var1;
      this.g = var2;
      this.h = var3;
      this.i = var4;
      this.j = var5;
      this.k = var6;
      this.l = var7;
   }

   public JigsawStructure(
      Structure.c var0, Holder<WorldGenFeatureDefinedStructurePoolTemplate> var1, int var2, HeightProvider var3, boolean var4, HeightMap.Type var5
   ) {
      this(var0, var1, Optional.empty(), var2, var3, var4, Optional.of(var5), 80);
   }

   public JigsawStructure(Structure.c var0, Holder<WorldGenFeatureDefinedStructurePoolTemplate> var1, int var2, HeightProvider var3, boolean var4) {
      this(var0, var1, Optional.empty(), var2, var3, var4, Optional.empty(), 80);
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      ChunkCoordIntPair var1 = var0.h();
      int var2 = this.i.a(var0.f(), new WorldGenerationContext(var0.b(), var0.i()));
      BlockPosition var3 = new BlockPosition(var1.d(), var2, var1.e());
      return WorldGenFeatureDefinedStructureJigsawPlacement.a(var0, this.f, this.g, this.h, var3, this.j, this.k, this.l);
   }

   @Override
   public StructureType<?> e() {
      return StructureType.f;
   }
}
