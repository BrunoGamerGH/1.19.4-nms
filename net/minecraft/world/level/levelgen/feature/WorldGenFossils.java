package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.commons.lang3.mutable.MutableInt;

public class WorldGenFossils extends WorldGenerator<FossilFeatureConfiguration> {
   public WorldGenFossils(Codec<FossilFeatureConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<FossilFeatureConfiguration> var0) {
      RandomSource var1 = var0.d();
      GeneratorAccessSeed var2 = var0.b();
      BlockPosition var3 = var0.e();
      EnumBlockRotation var4 = EnumBlockRotation.a(var1);
      FossilFeatureConfiguration var5 = var0.f();
      int var6 = var1.a(var5.b.size());
      StructureTemplateManager var7 = var2.C().n().aV();
      DefinedStructure var8 = var7.a(var5.b.get(var6));
      DefinedStructure var9 = var7.a(var5.c.get(var6));
      ChunkCoordIntPair var10 = new ChunkCoordIntPair(var3);
      StructureBoundingBox var11 = new StructureBoundingBox(var10.d() - 16, var2.v_(), var10.e() - 16, var10.f() + 16, var2.ai(), var10.g() + 16);
      DefinedStructureInfo var12 = new DefinedStructureInfo().a(var4).a(var11).a(var1);
      BaseBlockPosition var13 = var8.a(var4);
      BlockPosition var14 = var3.b(-var13.u() / 2, 0, -var13.w() / 2);
      int var15 = var3.v();

      for(int var16 = 0; var16 < var13.u(); ++var16) {
         for(int var17 = 0; var17 < var13.w(); ++var17) {
            var15 = Math.min(var15, var2.a(HeightMap.Type.c, var14.u() + var16, var14.w() + var17));
         }
      }

      int var16 = Math.max(var15 - 15 - var1.a(10), var2.v_() + 10);
      BlockPosition var17 = var8.a(var14.h(var16), EnumBlockMirror.a, var4);
      if (a(var2, var8.b(var12, var17)) > var5.f) {
         return false;
      } else {
         var12.b();
         var5.d.a().a().forEach(var12::a);
         var8.a(var2, var17, var17, var12, var1, 4);
         var12.b();
         var5.e.a().a().forEach(var12::a);
         var9.a(var2, var17, var17, var12, var1, 4);
         return true;
      }
   }

   private static int a(GeneratorAccessSeed var0, StructureBoundingBox var1) {
      MutableInt var2 = new MutableInt(0);
      var1.a(var2x -> {
         IBlockData var3 = var0.a_(var2x);
         if (var3.h() || var3.a(Blocks.H) || var3.a(Blocks.G)) {
            var2.add(1);
         }
      });
      return var2.getValue();
   }
}
