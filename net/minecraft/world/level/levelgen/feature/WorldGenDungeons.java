package net.minecraft.world.level.levelgen.feature;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityLootable;
import net.minecraft.world.level.block.entity.TileEntityMobSpawner;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootTables;
import org.slf4j.Logger;

public class WorldGenDungeons extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   private static final Logger a = LogUtils.getLogger();
   private static final EntityTypes<?>[] b = new EntityTypes[]{EntityTypes.aJ, EntityTypes.bp, EntityTypes.bp, EntityTypes.aS};
   private static final IBlockData c = Blocks.mY.o();

   public WorldGenDungeons(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      Predicate<IBlockData> var1 = WorldGenerator.a(TagsBlock.bC);
      BlockPosition var2 = var0.e();
      RandomSource var3 = var0.d();
      GeneratorAccessSeed var4 = var0.b();
      int var5 = 3;
      int var6 = var3.a(2) + 2;
      int var7 = -var6 - 1;
      int var8 = var6 + 1;
      int var9 = -1;
      int var10 = 4;
      int var11 = var3.a(2) + 2;
      int var12 = -var11 - 1;
      int var13 = var11 + 1;
      int var14 = 0;

      for(int var15 = var7; var15 <= var8; ++var15) {
         for(int var16 = -1; var16 <= 4; ++var16) {
            for(int var17 = var12; var17 <= var13; ++var17) {
               BlockPosition var18 = var2.b(var15, var16, var17);
               Material var19 = var4.a_(var18).d();
               boolean var20 = var19.b();
               if (var16 == -1 && !var20) {
                  return false;
               }

               if (var16 == 4 && !var20) {
                  return false;
               }

               if ((var15 == var7 || var15 == var8 || var17 == var12 || var17 == var13) && var16 == 0 && var4.w(var18) && var4.w(var18.c())) {
                  ++var14;
               }
            }
         }
      }

      if (var14 >= 1 && var14 <= 5) {
         for(int var15 = var7; var15 <= var8; ++var15) {
            for(int var16 = 3; var16 >= -1; --var16) {
               for(int var17 = var12; var17 <= var13; ++var17) {
                  BlockPosition var18 = var2.b(var15, var16, var17);
                  IBlockData var19 = var4.a_(var18);
                  if (var15 == var7 || var16 == -1 || var17 == var12 || var15 == var8 || var16 == 4 || var17 == var13) {
                     if (var18.v() >= var4.v_() && !var4.a_(var18.d()).d().b()) {
                        var4.a(var18, c, 2);
                     } else if (var19.d().b() && !var19.a(Blocks.cu)) {
                        if (var16 == -1 && var3.a(4) != 0) {
                           this.a(var4, var18, Blocks.cm.o(), var1);
                        } else {
                           this.a(var4, var18, Blocks.m.o(), var1);
                        }
                     }
                  } else if (!var19.a(Blocks.cu) && !var19.a(Blocks.cs)) {
                     this.a(var4, var18, c, var1);
                  }
               }
            }
         }

         for(int var15 = 0; var15 < 2; ++var15) {
            for(int var16 = 0; var16 < 3; ++var16) {
               int var17 = var2.u() + var3.a(var6 * 2 + 1) - var6;
               int var18 = var2.v();
               int var19 = var2.w() + var3.a(var11 * 2 + 1) - var11;
               BlockPosition var20 = new BlockPosition(var17, var18, var19);
               if (var4.w(var20)) {
                  int var21 = 0;

                  for(EnumDirection var23 : EnumDirection.EnumDirectionLimit.a) {
                     if (var4.a_(var20.a(var23)).d().b()) {
                        ++var21;
                     }
                  }

                  if (var21 == 1) {
                     this.a(var4, var20, StructurePiece.a(var4, var20, Blocks.cu.o()), var1);
                     TileEntityLootable.a(var4, var3, var20, LootTables.d);
                     break;
                  }
               }
            }
         }

         this.a(var4, var2, Blocks.cs.o(), var1);
         TileEntity var15 = var4.c_(var2);
         if (var15 instanceof TileEntityMobSpawner var16) {
            var16.a(this.a(var3), var3);
         } else {
            a.error("Failed to fetch mob spawner entity at ({}, {}, {})", new Object[]{var2.u(), var2.v(), var2.w()});
         }

         return true;
      } else {
         return false;
      }
   }

   private EntityTypes<?> a(RandomSource var0) {
      return SystemUtils.a(b, var0);
   }
}
