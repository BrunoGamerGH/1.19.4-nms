package net.minecraft.world.level.levelgen.structure.pools;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public abstract class WorldGenFeatureDefinedStructurePoolStructure {
   public static final Codec<WorldGenFeatureDefinedStructurePoolStructure> e = BuiltInRegistries.ah
      .q()
      .dispatch("element_type", WorldGenFeatureDefinedStructurePoolStructure::a, WorldGenFeatureDefinedStructurePools::codec);
   private static final Holder<ProcessorList> a = Holder.a(new ProcessorList(List.of()));
   @Nullable
   private volatile WorldGenFeatureDefinedStructurePoolTemplate.Matching b;

   protected static <E extends WorldGenFeatureDefinedStructurePoolStructure> RecordCodecBuilder<E, WorldGenFeatureDefinedStructurePoolTemplate.Matching> d() {
      return WorldGenFeatureDefinedStructurePoolTemplate.Matching.c.fieldOf("projection").forGetter(WorldGenFeatureDefinedStructurePoolStructure::e);
   }

   protected WorldGenFeatureDefinedStructurePoolStructure(WorldGenFeatureDefinedStructurePoolTemplate.Matching var0) {
      this.b = var0;
   }

   public abstract BaseBlockPosition a(StructureTemplateManager var1, EnumBlockRotation var2);

   public abstract List<DefinedStructure.BlockInfo> a(StructureTemplateManager var1, BlockPosition var2, EnumBlockRotation var3, RandomSource var4);

   public abstract StructureBoundingBox a(StructureTemplateManager var1, BlockPosition var2, EnumBlockRotation var3);

   public abstract boolean a(
      StructureTemplateManager var1,
      GeneratorAccessSeed var2,
      StructureManager var3,
      ChunkGenerator var4,
      BlockPosition var5,
      BlockPosition var6,
      EnumBlockRotation var7,
      StructureBoundingBox var8,
      RandomSource var9,
      boolean var10
   );

   public abstract WorldGenFeatureDefinedStructurePools<?> a();

   public void a(
      GeneratorAccess var0, DefinedStructure.BlockInfo var1, BlockPosition var2, EnumBlockRotation var3, RandomSource var4, StructureBoundingBox var5
   ) {
   }

   public WorldGenFeatureDefinedStructurePoolStructure a(WorldGenFeatureDefinedStructurePoolTemplate.Matching var0) {
      this.b = var0;
      return this;
   }

   public WorldGenFeatureDefinedStructurePoolTemplate.Matching e() {
      WorldGenFeatureDefinedStructurePoolTemplate.Matching var0 = this.b;
      if (var0 == null) {
         throw new IllegalStateException();
      } else {
         return var0;
      }
   }

   public int f() {
      return 1;
   }

   public static Function<WorldGenFeatureDefinedStructurePoolTemplate.Matching, WorldGenFeatureDefinedStructurePoolEmpty> g() {
      return var0 -> WorldGenFeatureDefinedStructurePoolEmpty.b;
   }

   public static Function<WorldGenFeatureDefinedStructurePoolTemplate.Matching, WorldGenFeatureDefinedStructurePoolLegacySingle> a(String var0) {
      return var1 -> new WorldGenFeatureDefinedStructurePoolLegacySingle(Either.left(new MinecraftKey(var0)), a, var1);
   }

   public static Function<WorldGenFeatureDefinedStructurePoolTemplate.Matching, WorldGenFeatureDefinedStructurePoolLegacySingle> a(
      String var0, Holder<ProcessorList> var1
   ) {
      return var2 -> new WorldGenFeatureDefinedStructurePoolLegacySingle(Either.left(new MinecraftKey(var0)), var1, var2);
   }

   public static Function<WorldGenFeatureDefinedStructurePoolTemplate.Matching, WorldGenFeatureDefinedStructurePoolSingle> b(String var0) {
      return var1 -> new WorldGenFeatureDefinedStructurePoolSingle(Either.left(new MinecraftKey(var0)), a, var1);
   }

   public static Function<WorldGenFeatureDefinedStructurePoolTemplate.Matching, WorldGenFeatureDefinedStructurePoolSingle> b(
      String var0, Holder<ProcessorList> var1
   ) {
      return var2 -> new WorldGenFeatureDefinedStructurePoolSingle(Either.left(new MinecraftKey(var0)), var1, var2);
   }

   public static Function<WorldGenFeatureDefinedStructurePoolTemplate.Matching, WorldGenFeatureDefinedStructurePoolFeature> a(Holder<PlacedFeature> var0) {
      return var1 -> new WorldGenFeatureDefinedStructurePoolFeature(var0, var1);
   }

   public static Function<WorldGenFeatureDefinedStructurePoolTemplate.Matching, WorldGenFeatureDefinedStructurePoolList> a(
      List<Function<WorldGenFeatureDefinedStructurePoolTemplate.Matching, ? extends WorldGenFeatureDefinedStructurePoolStructure>> var0
   ) {
      return var1 -> new WorldGenFeatureDefinedStructurePoolList(var0.stream().map(var1x -> var1x.apply(var1)).collect(Collectors.toList()), var1);
   }
}
