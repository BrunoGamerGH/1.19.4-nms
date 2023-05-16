package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacer;

public class UpwardsBranchingTrunkPlacer extends TrunkPlacer {
   public static final Codec<UpwardsBranchingTrunkPlacer> a = RecordCodecBuilder.create(
      var0 -> a(var0)
            .and(
               var0.group(
                  IntProvider.e.fieldOf("extra_branch_steps").forGetter(var0x -> var0x.b),
                  Codec.floatRange(0.0F, 1.0F).fieldOf("place_branch_per_log_probability").forGetter(var0x -> var0x.h),
                  IntProvider.d.fieldOf("extra_branch_length").forGetter(var0x -> var0x.i),
                  RegistryCodecs.a(Registries.e).fieldOf("can_grow_through").forGetter(var0x -> var0x.j)
               )
            )
            .apply(var0, UpwardsBranchingTrunkPlacer::new)
   );
   private final IntProvider b;
   private final float h;
   private final IntProvider i;
   private final HolderSet<Block> j;

   public UpwardsBranchingTrunkPlacer(int var0, int var1, int var2, IntProvider var3, float var4, IntProvider var5, HolderSet<Block> var6) {
      super(var0, var1, var2);
      this.b = var3;
      this.h = var4;
      this.i = var5;
      this.j = var6;
   }

   @Override
   protected TrunkPlacers<?> a() {
      return TrunkPlacers.h;
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
      List<WorldGenFoilagePlacer.a> var6 = Lists.newArrayList();
      BlockPosition.MutableBlockPosition var7 = new BlockPosition.MutableBlockPosition();

      for(int var8 = 0; var8 < var3; ++var8) {
         int var9 = var4.v() + var8;
         if (this.b(var0, var1, var2, var7.d(var4.u(), var9, var4.w()), var5) && var8 < var3 - 1 && var2.i() < this.h) {
            EnumDirection var10 = EnumDirection.EnumDirectionLimit.a.a(var2);
            int var11 = this.i.a(var2);
            int var12 = Math.max(0, var11 - this.i.a(var2) - 1);
            int var13 = this.b.a(var2);
            this.a(var0, var1, var2, var3, var5, var6, var7, var9, var10, var12, var13);
         }

         if (var8 == var3 - 1) {
            var6.add(new WorldGenFoilagePlacer.a(var7.d(var4.u(), var9 + 1, var4.w()), 0, false));
         }
      }

      return var6;
   }

   private void a(
      VirtualLevelReadable var0,
      BiConsumer<BlockPosition, IBlockData> var1,
      RandomSource var2,
      int var3,
      WorldGenFeatureTreeConfiguration var4,
      List<WorldGenFoilagePlacer.a> var5,
      BlockPosition.MutableBlockPosition var6,
      int var7,
      EnumDirection var8,
      int var9,
      int var10
   ) {
      int var11 = var7 + var9;
      int var12 = var6.u();
      int var13 = var6.w();

      for(int var14 = var9; var14 < var3 && var10 > 0; --var10) {
         if (var14 >= 1) {
            int var15 = var7 + var14;
            var12 += var8.j();
            var13 += var8.l();
            var11 = var15;
            if (this.b(var0, var1, var2, var6.d(var12, var15, var13), var4)) {
               var11 = var15 + 1;
            }

            var5.add(new WorldGenFoilagePlacer.a(var6.i(), 0, false));
         }

         ++var14;
      }

      if (var11 - var7 > 1) {
         BlockPosition var14 = new BlockPosition(var12, var11, var13);
         var5.add(new WorldGenFoilagePlacer.a(var14, 0, false));
         var5.add(new WorldGenFoilagePlacer.a(var14.c(2), 0, false));
      }
   }

   @Override
   protected boolean a(VirtualLevelReadable var0, BlockPosition var1) {
      return super.a(var0, var1) || var0.a(var1, var0x -> var0x.a(this.j));
   }
}
