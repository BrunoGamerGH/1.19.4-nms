package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.IWorldWriter;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacer;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTree;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.VoxelShapeBitSet;
import net.minecraft.world.phys.shapes.VoxelShapeDiscrete;

public class WorldGenTrees extends WorldGenerator<WorldGenFeatureTreeConfiguration> {
   private static final int a = 19;

   public WorldGenTrees(Codec<WorldGenFeatureTreeConfiguration> var0) {
      super(var0);
   }

   private static boolean e(VirtualLevelReadable var0, BlockPosition var1) {
      return var0.a(var1, var0x -> var0x.a(Blocks.fe));
   }

   public static boolean b(VirtualLevelReadable var0, BlockPosition var1) {
      return var0.a(var1, var0x -> var0x.a(Blocks.G));
   }

   public static boolean c(VirtualLevelReadable var0, BlockPosition var1) {
      return var0.a(var1, var0x -> var0x.h() || var0x.a(TagsBlock.N));
   }

   private static boolean f(VirtualLevelReadable var0, BlockPosition var1) {
      return var0.a(var1, var0x -> {
         Material var1x = var0x.d();
         return var1x == Material.g || var1x == Material.i || var1x == Material.h;
      });
   }

   private static void b(IWorldWriter var0, BlockPosition var1, IBlockData var2) {
      var0.a(var1, var2, 19);
   }

   public static boolean d(VirtualLevelReadable var0, BlockPosition var1) {
      return c(var0, var1) || f(var0, var1) || b(var0, var1);
   }

   private boolean a(
      GeneratorAccessSeed var0,
      RandomSource var1,
      BlockPosition var2,
      BiConsumer<BlockPosition, IBlockData> var3,
      BiConsumer<BlockPosition, IBlockData> var4,
      WorldGenFoilagePlacer.b var5,
      WorldGenFeatureTreeConfiguration var6
   ) {
      int var7 = var6.d.a(var1);
      int var8 = var6.f.a(var1, var7, var6);
      int var9 = var7 - var8;
      int var10 = var6.f.a(var1, var9);
      BlockPosition var11 = var6.g.<BlockPosition>map(var2x -> var2x.a(var2, var1)).orElse(var2);
      int var12 = Math.min(var2.v(), var11.v());
      int var13 = Math.max(var2.v(), var11.v()) + var7 + 1;
      if (var12 >= var0.v_() + 1 && var13 <= var0.ai()) {
         OptionalInt var14 = var6.h.c();
         int var15 = this.a(var0, var7, var11, var6);
         if (var15 >= var7 || !var14.isEmpty() && var15 >= var14.getAsInt()) {
            if (var6.g.isPresent() && !var6.g.get().a(var0, var3, var1, var2, var11, var6)) {
               return false;
            } else {
               List<WorldGenFoilagePlacer.a> var16 = var6.d.a(var0, var4, var1, var15, var11, var6);
               var16.forEach(var7x -> var6.f.a(var0, var5, var1, var6, var15, var7x, var8, var10));
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private int a(VirtualLevelReadable var0, int var1, BlockPosition var2, WorldGenFeatureTreeConfiguration var3) {
      BlockPosition.MutableBlockPosition var4 = new BlockPosition.MutableBlockPosition();

      for(int var5 = 0; var5 <= var1 + 1; ++var5) {
         int var6 = var3.h.a(var1, var5);

         for(int var7 = -var6; var7 <= var6; ++var7) {
            for(int var8 = -var6; var8 <= var6; ++var8) {
               var4.a(var2, var7, var5, var8);
               if (!var3.d.b(var0, var4) || !var3.j && e(var0, var4)) {
                  return var5 - 2;
               }
            }
         }
      }

      return var1;
   }

   @Override
   protected void a(IWorldWriter var0, BlockPosition var1, IBlockData var2) {
      b(var0, var1, var2);
   }

   @Override
   public final boolean a(FeaturePlaceContext<WorldGenFeatureTreeConfiguration> var0) {
      final GeneratorAccessSeed var1 = var0.b();
      RandomSource var2 = var0.d();
      BlockPosition var3 = var0.e();
      WorldGenFeatureTreeConfiguration var4 = var0.f();
      Set<BlockPosition> var5 = Sets.newHashSet();
      Set<BlockPosition> var6 = Sets.newHashSet();
      final Set<BlockPosition> var7 = Sets.newHashSet();
      Set<BlockPosition> var8 = Sets.newHashSet();
      BiConsumer<BlockPosition, IBlockData> var9 = (var2x, var3x) -> {
         var5.add(var2x.i());
         var1.a(var2x, var3x, 19);
      };
      BiConsumer<BlockPosition, IBlockData> var10 = (var2x, var3x) -> {
         var6.add(var2x.i());
         var1.a(var2x, var3x, 19);
      };
      WorldGenFoilagePlacer.b var11 = new WorldGenFoilagePlacer.b() {
         @Override
         public void a(BlockPosition var0, IBlockData var1x) {
            var7.add(var0.i());
            var1.a(var0, var1, 19);
         }

         @Override
         public boolean a(BlockPosition var0) {
            return var7.contains(var0);
         }
      };
      BiConsumer<BlockPosition, IBlockData> var12 = (var2x, var3x) -> {
         var8.add(var2x.i());
         var1.a(var2x, var3x, 19);
      };
      boolean var13 = this.a(var1, var2, var3, var9, var10, var11, var4);
      if (var13 && (!var6.isEmpty() || !var7.isEmpty())) {
         if (!var4.i.isEmpty()) {
            WorldGenFeatureTree.a var14 = new WorldGenFeatureTree.a(var1, var12, var2, var6, var7, var5);
            var4.i.forEach(var1x -> var1x.a(var14));
         }

         return StructureBoundingBox.a(Iterables.concat(var5, var6, var7, var8)).map(var4x -> {
            VoxelShapeDiscrete var5x = a(var1, var4x, var6, var8, var5);
            DefinedStructure.a(var1, 3, var5x, var4x.g(), var4x.h(), var4x.i());
            return true;
         }).orElse(false);
      } else {
         return false;
      }
   }

   private static VoxelShapeDiscrete a(
      GeneratorAccess var0, StructureBoundingBox var1, Set<BlockPosition> var2, Set<BlockPosition> var3, Set<BlockPosition> var4
   ) {
      List<Set<BlockPosition>> var5 = Lists.newArrayList();
      VoxelShapeDiscrete var6 = new VoxelShapeBitSet(var1.c(), var1.d(), var1.e());
      int var7 = 6;

      for(int var8 = 0; var8 < 6; ++var8) {
         var5.add(Sets.newHashSet());
      }

      BlockPosition.MutableBlockPosition var8 = new BlockPosition.MutableBlockPosition();

      for(BlockPosition var10 : Lists.newArrayList(Sets.union(var3, var4))) {
         if (var1.b(var10)) {
            var6.c(var10.u() - var1.g(), var10.v() - var1.h(), var10.w() - var1.i());
         }
      }

      for(BlockPosition var10 : Lists.newArrayList(var2)) {
         if (var1.b(var10)) {
            var6.c(var10.u() - var1.g(), var10.v() - var1.h(), var10.w() - var1.i());
         }

         for(EnumDirection var14 : EnumDirection.values()) {
            var8.a(var10, var14);
            if (!var2.contains(var8)) {
               IBlockData var15 = var0.a_(var8);
               if (var15.b(BlockProperties.aC)) {
                  var5.get(0).add(var8.i());
                  b(var0, var8, var15.a(BlockProperties.aC, Integer.valueOf(1)));
                  if (var1.b(var8)) {
                     var6.c(var8.u() - var1.g(), var8.v() - var1.h(), var8.w() - var1.i());
                  }
               }
            }
         }
      }

      for(int var9 = 1; var9 < 6; ++var9) {
         Set<BlockPosition> var10 = var5.get(var9 - 1);
         Set<BlockPosition> var11 = var5.get(var9);

         for(BlockPosition var13 : var10) {
            if (var1.b(var13)) {
               var6.c(var13.u() - var1.g(), var13.v() - var1.h(), var13.w() - var1.i());
            }

            for(EnumDirection var17 : EnumDirection.values()) {
               var8.a(var13, var17);
               if (!var10.contains(var8) && !var11.contains(var8)) {
                  IBlockData var18 = var0.a_(var8);
                  if (var18.b(BlockProperties.aC)) {
                     int var19 = var18.c(BlockProperties.aC);
                     if (var19 > var9 + 1) {
                        IBlockData var20 = var18.a(BlockProperties.aC, Integer.valueOf(var9 + 1));
                        b(var0, var8, var20);
                        if (var1.b(var8)) {
                           var6.c(var8.u() - var1.g(), var8.v() - var1.h(), var8.w() - var1.i());
                        }

                        var11.add(var8.i());
                     }
                  }
               }
            }
         }
      }

      return var6;
   }
}
