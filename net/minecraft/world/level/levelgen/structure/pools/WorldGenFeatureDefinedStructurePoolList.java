package net.minecraft.world.level.levelgen.structure.pools;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class WorldGenFeatureDefinedStructurePoolList extends WorldGenFeatureDefinedStructurePoolStructure {
   public static final Codec<WorldGenFeatureDefinedStructurePoolList> a = RecordCodecBuilder.create(
      var0 -> var0.group(WorldGenFeatureDefinedStructurePoolStructure.e.listOf().fieldOf("elements").forGetter(var0x -> var0x.b), d())
            .apply(var0, WorldGenFeatureDefinedStructurePoolList::new)
   );
   private final List<WorldGenFeatureDefinedStructurePoolStructure> b;

   public WorldGenFeatureDefinedStructurePoolList(
      List<WorldGenFeatureDefinedStructurePoolStructure> var0, WorldGenFeatureDefinedStructurePoolTemplate.Matching var1
   ) {
      super(var1);
      if (var0.isEmpty()) {
         throw new IllegalArgumentException("Elements are empty");
      } else {
         this.b = var0;
         this.b(var1);
      }
   }

   @Override
   public BaseBlockPosition a(StructureTemplateManager var0, EnumBlockRotation var1) {
      int var2 = 0;
      int var3 = 0;
      int var4 = 0;

      for(WorldGenFeatureDefinedStructurePoolStructure var6 : this.b) {
         BaseBlockPosition var7 = var6.a(var0, var1);
         var2 = Math.max(var2, var7.u());
         var3 = Math.max(var3, var7.v());
         var4 = Math.max(var4, var7.w());
      }

      return new BaseBlockPosition(var2, var3, var4);
   }

   @Override
   public List<DefinedStructure.BlockInfo> a(StructureTemplateManager var0, BlockPosition var1, EnumBlockRotation var2, RandomSource var3) {
      return this.b.get(0).a(var0, var1, var2, var3);
   }

   @Override
   public StructureBoundingBox a(StructureTemplateManager var0, BlockPosition var1, EnumBlockRotation var2) {
      Stream<StructureBoundingBox> var3 = this.b
         .stream()
         .filter(var0x -> var0x != WorldGenFeatureDefinedStructurePoolEmpty.b)
         .map(var3x -> var3x.a(var0, var1, var2));
      return StructureBoundingBox.b(var3::iterator).orElseThrow(() -> new IllegalStateException("Unable to calculate boundingbox for ListPoolElement"));
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
      for(WorldGenFeatureDefinedStructurePoolStructure var11 : this.b) {
         if (!var11.a(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public WorldGenFeatureDefinedStructurePools<?> a() {
      return WorldGenFeatureDefinedStructurePools.b;
   }

   @Override
   public WorldGenFeatureDefinedStructurePoolStructure a(WorldGenFeatureDefinedStructurePoolTemplate.Matching var0) {
      super.a(var0);
      this.b(var0);
      return this;
   }

   @Override
   public String toString() {
      return "List[" + (String)this.b.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
   }

   private void b(WorldGenFeatureDefinedStructurePoolTemplate.Matching var0) {
      this.b.forEach(var1x -> var1x.a(var0));
   }
}
