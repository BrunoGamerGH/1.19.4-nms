package net.minecraft.world.level.levelgen.structure.pools;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.BlockPropertyJigsawOrientation;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.BlockJigsaw;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.TileEntityJigsaw;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class WorldGenFeatureDefinedStructurePoolFeature extends WorldGenFeatureDefinedStructurePoolStructure {
   public static final Codec<WorldGenFeatureDefinedStructurePoolFeature> a = RecordCodecBuilder.create(
      var0 -> var0.group(PlacedFeature.b.fieldOf("feature").forGetter(var0x -> var0x.b), d()).apply(var0, WorldGenFeatureDefinedStructurePoolFeature::new)
   );
   private final Holder<PlacedFeature> b;
   private final NBTTagCompound c;

   protected WorldGenFeatureDefinedStructurePoolFeature(Holder<PlacedFeature> var0, WorldGenFeatureDefinedStructurePoolTemplate.Matching var1) {
      super(var1);
      this.b = var0;
      this.c = this.b();
   }

   private NBTTagCompound b() {
      NBTTagCompound var0 = new NBTTagCompound();
      var0.a("name", "minecraft:bottom");
      var0.a("final_state", "minecraft:air");
      var0.a("pool", "minecraft:empty");
      var0.a("target", "minecraft:empty");
      var0.a("joint", TileEntityJigsaw.JointType.a.c());
      return var0;
   }

   @Override
   public BaseBlockPosition a(StructureTemplateManager var0, EnumBlockRotation var1) {
      return BaseBlockPosition.g;
   }

   @Override
   public List<DefinedStructure.BlockInfo> a(StructureTemplateManager var0, BlockPosition var1, EnumBlockRotation var2, RandomSource var3) {
      List<DefinedStructure.BlockInfo> var4 = Lists.newArrayList();
      var4.add(
         new DefinedStructure.BlockInfo(var1, Blocks.oX.o().a(BlockJigsaw.a, BlockPropertyJigsawOrientation.a(EnumDirection.a, EnumDirection.d)), this.c)
      );
      return var4;
   }

   @Override
   public StructureBoundingBox a(StructureTemplateManager var0, BlockPosition var1, EnumBlockRotation var2) {
      BaseBlockPosition var3 = this.a(var0, var2);
      return new StructureBoundingBox(var1.u(), var1.v(), var1.w(), var1.u() + var3.u(), var1.v() + var3.v(), var1.w() + var3.w());
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
      return this.b.a().a(var1, var3, var8, var4);
   }

   @Override
   public WorldGenFeatureDefinedStructurePools<?> a() {
      return WorldGenFeatureDefinedStructurePools.c;
   }

   @Override
   public String toString() {
      return "Feature[" + this.b + "]";
   }
}
