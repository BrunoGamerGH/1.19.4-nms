package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.BlockRotatable;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacer;

public class CherryTrunkPlacer extends TrunkPlacer {
   private static final Codec<UniformInt> b = ExtraCodecs.a(
      UniformInt.a,
      (Function<UniformInt, DataResult<UniformInt>>)(var0 -> var0.b() - var0.a() < 1
            ? DataResult.error(() -> "Need at least 2 blocks variation for the branch starts to fit both branches")
            : DataResult.success(var0))
   );
   public static final Codec<CherryTrunkPlacer> a = RecordCodecBuilder.create(
      var0 -> a(var0)
            .and(
               var0.group(
                  IntProvider.b(1, 3).fieldOf("branch_count").forGetter(var0x -> var0x.h),
                  IntProvider.b(2, 16).fieldOf("branch_horizontal_length").forGetter(var0x -> var0x.i),
                  IntProvider.a(-16, 0, b).fieldOf("branch_start_offset_from_top").forGetter(var0x -> var0x.j),
                  IntProvider.b(-16, 16).fieldOf("branch_end_offset_from_top").forGetter(var0x -> var0x.l)
               )
            )
            .apply(var0, CherryTrunkPlacer::new)
   );
   private final IntProvider h;
   private final IntProvider i;
   private final UniformInt j;
   private final UniformInt k;
   private final IntProvider l;

   public CherryTrunkPlacer(int var0, int var1, int var2, IntProvider var3, IntProvider var4, UniformInt var5, IntProvider var6) {
      super(var0, var1, var2);
      this.h = var3;
      this.i = var4;
      this.j = var5;
      this.k = UniformInt.a(var5.a(), var5.b() - 1);
      this.l = var6;
   }

   @Override
   protected TrunkPlacers<?> a() {
      return TrunkPlacers.i;
   }

   @Override
   public List<WorldGenFoilagePlacer.a> a(
      VirtualLevelReadable var0,
      BiConsumer<BlockPosition, IBlockData> var1,
      RandomSource var2,
      int var3,
      BlockPosition var4,
      WorldGenFeatureTreeConfiguration var5
   ) {
      a(var0, var1, var2, var4.d(), var5);
      int var6 = Math.max(0, var3 - 1 + this.j.a(var2));
      int var7 = Math.max(0, var3 - 1 + this.k.a(var2));
      if (var7 >= var6) {
         ++var7;
      }

      int var8 = this.h.a(var2);
      boolean var9 = var8 == 3;
      boolean var10 = var8 >= 2;
      int var11;
      if (var9) {
         var11 = var3;
      } else if (var10) {
         var11 = Math.max(var6, var7) + 1;
      } else {
         var11 = var6 + 1;
      }

      for(int var12 = 0; var12 < var11; ++var12) {
         this.b(var0, var1, var2, var4.b(var12), var5);
      }

      List<WorldGenFoilagePlacer.a> var12 = new ArrayList<>();
      if (var9) {
         var12.add(new WorldGenFoilagePlacer.a(var4.b(var11), 0, false));
      }

      BlockPosition.MutableBlockPosition var13 = new BlockPosition.MutableBlockPosition();
      EnumDirection var14 = EnumDirection.EnumDirectionLimit.a.a(var2);
      Function<IBlockData, IBlockData> var15 = var1x -> var1x.b(BlockRotatable.g, var14.o());
      var12.add(this.a(var0, var1, var2, var3, var4, var5, var15, var14, var6, var6 < var11 - 1, var13));
      if (var10) {
         var12.add(this.a(var0, var1, var2, var3, var4, var5, var15, var14.g(), var7, var7 < var11 - 1, var13));
      }

      return var12;
   }

   private WorldGenFoilagePlacer.a a(
      VirtualLevelReadable var0,
      BiConsumer<BlockPosition, IBlockData> var1,
      RandomSource var2,
      int var3,
      BlockPosition var4,
      WorldGenFeatureTreeConfiguration var5,
      Function<IBlockData, IBlockData> var6,
      EnumDirection var7,
      int var8,
      boolean var9,
      BlockPosition.MutableBlockPosition var10
   ) {
      var10.g(var4).c(EnumDirection.b, var8);
      int var11 = var3 - 1 + this.l.a(var2);
      boolean var12 = var9 || var11 < var8;
      int var13 = this.i.a(var2) + (var12 ? 1 : 0);
      BlockPosition var14 = var4.a(var7, var13).b(var11);
      int var15 = var12 ? 2 : 1;

      for(int var16 = 0; var16 < var15; ++var16) {
         this.a(var0, var1, var2, var10.c(var7), var5, var6);
      }

      EnumDirection var16 = var14.v() > var10.v() ? EnumDirection.b : EnumDirection.a;

      while(true) {
         int var17 = var10.k(var14);
         if (var17 == 0) {
            return new WorldGenFoilagePlacer.a(var14.c(), 0, false);
         }

         float var18 = (float)Math.abs(var14.v() - var10.v()) / (float)var17;
         boolean var19 = var2.i() < var18;
         var10.c(var19 ? var16 : var7);
         this.a(var0, var1, var2, var10, var5, var19 ? Function.identity() : var6);
      }
   }
}
