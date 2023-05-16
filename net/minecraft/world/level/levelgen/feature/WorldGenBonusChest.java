package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.stream.IntStream;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntityLootable;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;
import net.minecraft.world.level.storage.loot.LootTables;

public class WorldGenBonusChest extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   public WorldGenBonusChest(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      RandomSource var1 = var0.d();
      GeneratorAccessSeed var2 = var0.b();
      ChunkCoordIntPair var3 = new ChunkCoordIntPair(var0.e());
      IntArrayList var4 = SystemUtils.a(IntStream.rangeClosed(var3.d(), var3.f()), var1);
      IntArrayList var5 = SystemUtils.a(IntStream.rangeClosed(var3.e(), var3.g()), var1);
      BlockPosition.MutableBlockPosition var6 = new BlockPosition.MutableBlockPosition();
      IntListIterator var8 = var4.iterator();

      while(var8.hasNext()) {
         Integer var8x = (Integer)var8.next();
         IntListIterator var10 = var5.iterator();

         while(var10.hasNext()) {
            Integer var10x = (Integer)var10.next();
            var6.d(var8x, 0, var10x);
            BlockPosition var11 = var2.a(HeightMap.Type.f, var6);
            if (var2.w(var11) || var2.a_(var11).k(var2, var11).b()) {
               var2.a(var11, Blocks.cu.o(), 2);
               TileEntityLootable.a(var2, var1, var11, LootTables.b);
               IBlockData var12 = Blocks.co.o();

               for(EnumDirection var14 : EnumDirection.EnumDirectionLimit.a) {
                  BlockPosition var15 = var11.a(var14);
                  if (var12.a(var2, var15)) {
                     var2.a(var15, var12, 2);
                  }
               }

               return true;
            }
         }
      }

      return false;
   }
}
