package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.stream.Stream;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.data.loot.packs.UpdateOneTwentyBuiltInLootTables;
import net.minecraft.util.RandomSource;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;
import org.apache.commons.lang3.mutable.MutableInt;

public class WorldGenDesertWell extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   private static final BlockStatePredicate a = BlockStatePredicate.a(Blocks.I);
   private final IBlockData b = Blocks.jE.o();
   private final IBlockData c = Blocks.aU.o();
   private final IBlockData d = Blocks.G.o();

   public WorldGenDesertWell(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      var2 = var2.c();

      while(var1.w(var2) && var2.v() > var1.v_() + 2) {
         var2 = var2.d();
      }

      if (!a.a(var1.a_(var2))) {
         return false;
      } else {
         for(int var3 = -2; var3 <= 2; ++var3) {
            for(int var4 = -2; var4 <= 2; ++var4) {
               if (var1.w(var2.b(var3, -1, var4)) && var1.w(var2.b(var3, -2, var4))) {
                  return false;
               }
            }
         }

         for(int var3 = -1; var3 <= 0; ++var3) {
            for(int var4 = -2; var4 <= 2; ++var4) {
               for(int var5 = -2; var5 <= 2; ++var5) {
                  var1.a(var2.b(var4, var3, var5), this.c, 2);
               }
            }
         }

         if (var1.G().b(FeatureFlags.c)) {
            a(var1, var2, var0.d());
         }

         var1.a(var2, this.d, 2);

         for(EnumDirection var4 : EnumDirection.EnumDirectionLimit.a) {
            var1.a(var2.a(var4), this.d, 2);
         }

         for(int var3 = -2; var3 <= 2; ++var3) {
            for(int var4 = -2; var4 <= 2; ++var4) {
               if (var3 == -2 || var3 == 2 || var4 == -2 || var4 == 2) {
                  var1.a(var2.b(var3, 1, var4), this.c, 2);
               }
            }
         }

         var1.a(var2.b(2, 1, 0), this.b, 2);
         var1.a(var2.b(-2, 1, 0), this.b, 2);
         var1.a(var2.b(0, 1, 2), this.b, 2);
         var1.a(var2.b(0, 1, -2), this.b, 2);

         for(int var3 = -1; var3 <= 1; ++var3) {
            for(int var4 = -1; var4 <= 1; ++var4) {
               if (var3 == 0 && var4 == 0) {
                  var1.a(var2.b(var3, 4, var4), this.c, 2);
               } else {
                  var1.a(var2.b(var3, 4, var4), this.b, 2);
               }
            }
         }

         for(int var3 = 1; var3 <= 3; ++var3) {
            var1.a(var2.b(-1, var3, -1), this.c, 2);
            var1.a(var2.b(-1, var3, 1), this.c, 2);
            var1.a(var2.b(1, var3, -1), this.c, 2);
            var1.a(var2.b(1, var3, 1), this.c, 2);
         }

         return true;
      }
   }

   private static void a(GeneratorAccessSeed var0, BlockPosition var1, RandomSource var2) {
      BlockPosition var3 = var1.b(0, -1, 0);
      ObjectArrayList<BlockPosition> var4 = SystemUtils.a(new ObjectArrayList(), var1x -> {
         var1x.add(var3.h());
         var1x.add(var3.f());
         var1x.add(var3.g());
         var1x.add(var3.e());
      });
      SystemUtils.b(var4, var2);
      MutableInt var5 = new MutableInt(var2.b(2, 4));
      Stream.concat(Stream.of(var3), var4.stream()).forEach(var2x -> {
         if (var5.getAndDecrement() > 0) {
            var0.a(var2x, Blocks.J.o(), 3);
            var0.a(var2x, TileEntityTypes.M).ifPresent(var1xx -> var1xx.a(UpdateOneTwentyBuiltInLootTables.a, var2x.a()));
         } else {
            var0.a(var2x, Blocks.I.o(), 3);
         }
      });
   }
}
