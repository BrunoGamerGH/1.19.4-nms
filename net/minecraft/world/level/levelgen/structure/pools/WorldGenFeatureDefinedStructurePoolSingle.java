package net.minecraft.world.level.levelgen.structure.pools;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.SystemUtils;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.state.properties.BlockPropertyStructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorBlockIgnore;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorJigsawReplacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureStructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class WorldGenFeatureDefinedStructurePoolSingle extends WorldGenFeatureDefinedStructurePoolStructure {
   private static final Codec<Either<MinecraftKey, DefinedStructure>> a = Codec.of(
      WorldGenFeatureDefinedStructurePoolSingle::a, MinecraftKey.a.map(Either::left)
   );
   public static final Codec<WorldGenFeatureDefinedStructurePoolSingle> b = RecordCodecBuilder.create(
      var0 -> var0.group(c(), b(), d()).apply(var0, WorldGenFeatureDefinedStructurePoolSingle::new)
   );
   protected final Either<MinecraftKey, DefinedStructure> c;
   protected final Holder<ProcessorList> d;

   private static <T> DataResult<T> a(Either<MinecraftKey, DefinedStructure> var0, DynamicOps<T> var1, T var2) {
      Optional<MinecraftKey> var3 = var0.left();
      return !var3.isPresent() ? DataResult.error(() -> "Can not serialize a runtime pool element") : MinecraftKey.a.encode(var3.get(), var1, var2);
   }

   protected static <E extends WorldGenFeatureDefinedStructurePoolSingle> RecordCodecBuilder<E, Holder<ProcessorList>> b() {
      return DefinedStructureStructureProcessorType.n.fieldOf("processors").forGetter(var0 -> var0.d);
   }

   protected static <E extends WorldGenFeatureDefinedStructurePoolSingle> RecordCodecBuilder<E, Either<MinecraftKey, DefinedStructure>> c() {
      return a.fieldOf("location").forGetter(var0 -> var0.c);
   }

   protected WorldGenFeatureDefinedStructurePoolSingle(
      Either<MinecraftKey, DefinedStructure> var0, Holder<ProcessorList> var1, WorldGenFeatureDefinedStructurePoolTemplate.Matching var2
   ) {
      super(var2);
      this.c = var0;
      this.d = var1;
   }

   @Override
   public BaseBlockPosition a(StructureTemplateManager var0, EnumBlockRotation var1) {
      DefinedStructure var2 = this.a(var0);
      return var2.a(var1);
   }

   private DefinedStructure a(StructureTemplateManager var0) {
      return (DefinedStructure)this.c.map(var0::a, Function.identity());
   }

   public List<DefinedStructure.BlockInfo> a(StructureTemplateManager var0, BlockPosition var1, EnumBlockRotation var2, boolean var3) {
      DefinedStructure var4 = this.a(var0);
      List<DefinedStructure.BlockInfo> var5 = var4.a(var1, new DefinedStructureInfo().a(var2), Blocks.oW, var3);
      List<DefinedStructure.BlockInfo> var6 = Lists.newArrayList();

      for(DefinedStructure.BlockInfo var8 : var5) {
         if (var8.c != null) {
            BlockPropertyStructureMode var9 = BlockPropertyStructureMode.valueOf(var8.c.l("mode"));
            if (var9 == BlockPropertyStructureMode.d) {
               var6.add(var8);
            }
         }
      }

      return var6;
   }

   @Override
   public List<DefinedStructure.BlockInfo> a(StructureTemplateManager var0, BlockPosition var1, EnumBlockRotation var2, RandomSource var3) {
      DefinedStructure var4 = this.a(var0);
      ObjectArrayList<DefinedStructure.BlockInfo> var5 = var4.a(var1, new DefinedStructureInfo().a(var2), Blocks.oX, true);
      SystemUtils.b(var5, var3);
      return var5;
   }

   @Override
   public StructureBoundingBox a(StructureTemplateManager var0, BlockPosition var1, EnumBlockRotation var2) {
      DefinedStructure var3 = this.a(var0);
      return var3.b(new DefinedStructureInfo().a(var2), var1);
   }

   @Override
   public boolean a(
      StructureTemplateManager var0,
      GeneratorAccessSeed var1,
      StructureManager var2,
      ChunkGenerator var3,
      BlockPosition var4,
      BlockPosition var5,
      EnumBlockRotation var6,
      StructureBoundingBox var7,
      RandomSource var8,
      boolean var9
   ) {
      DefinedStructure var10 = this.a(var0);
      DefinedStructureInfo var11 = this.a(var6, var7, var9);
      if (!var10.a(var1, var4, var5, var11, var8, 18)) {
         return false;
      } else {
         for(DefinedStructure.BlockInfo var14 : DefinedStructure.a(var1, var4, var5, var11, this.a(var0, var4, var6, false))) {
            this.a(var1, var14, var4, var6, var8, var7);
         }

         return true;
      }
   }

   protected DefinedStructureInfo a(EnumBlockRotation var0, StructureBoundingBox var1, boolean var2) {
      DefinedStructureInfo var3 = new DefinedStructureInfo();
      var3.a(var1);
      var3.a(var0);
      var3.c(true);
      var3.a(false);
      var3.a(DefinedStructureProcessorBlockIgnore.b);
      var3.d(true);
      if (!var2) {
         var3.a(DefinedStructureProcessorJigsawReplacement.b);
      }

      this.d.a().a().forEach(var3::a);
      this.e().b().forEach(var3::a);
      return var3;
   }

   @Override
   public WorldGenFeatureDefinedStructurePools<?> a() {
      return WorldGenFeatureDefinedStructurePools.a;
   }

   @Override
   public String toString() {
      return "Single[" + this.c + "]";
   }
}
