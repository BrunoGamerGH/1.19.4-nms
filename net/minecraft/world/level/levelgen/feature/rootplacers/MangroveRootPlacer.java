package net.minecraft.world.level.levelgen.feature.rootplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;

public class MangroveRootPlacer extends RootPlacer {
   public static final int a = 8;
   public static final int b = 15;
   public static final Codec<MangroveRootPlacer> c = RecordCodecBuilder.create(
      var0 -> a(var0).and(MangroveRootPlacement.a.fieldOf("mangrove_root_placement").forGetter(var0x -> var0x.h)).apply(var0, MangroveRootPlacer::new)
   );
   private final MangroveRootPlacement h;

   public MangroveRootPlacer(IntProvider var0, WorldGenFeatureStateProvider var1, Optional<AboveRootPlacement> var2, MangroveRootPlacement var3) {
      super(var0, var1, var2);
      this.h = var3;
   }

   @Override
   public boolean a(
      VirtualLevelReadable var0,
      BiConsumer<BlockPosition, IBlockData> var1,
      RandomSource var2,
      BlockPosition var3,
      BlockPosition var4,
      WorldGenFeatureTreeConfiguration var5
   ) {
      List<BlockPosition> var6 = Lists.newArrayList();
      BlockPosition.MutableBlockPosition var7 = var3.j();

      while(var7.v() < var4.v()) {
         if (!this.a(var0, var7)) {
            return false;
         }

         var7.c(EnumDirection.b);
      }

      var6.add(var4.d());

      for(EnumDirection var9 : EnumDirection.EnumDirectionLimit.a) {
         BlockPosition var10 = var4.a(var9);
         List<BlockPosition> var11 = Lists.newArrayList();
         if (!this.a(var0, var2, var10, var9, var4, var11, 0)) {
            return false;
         }

         var6.addAll(var11);
         var6.add(var4.a(var9));
      }

      for(BlockPosition var9 : var6) {
         this.a(var0, var1, var2, var9, var5);
      }

      return true;
   }

   private boolean a(
      VirtualLevelReadable var0, RandomSource var1, BlockPosition var2, EnumDirection var3, BlockPosition var4, List<BlockPosition> var5, int var6
   ) {
      int var7 = this.h.e();
      if (var6 != var7 && var5.size() <= var7) {
         for(BlockPosition var10 : this.a(var2, var3, var1, var4)) {
            if (this.a(var0, var10)) {
               var5.add(var10);
               if (!this.a(var0, var1, var10, var3, var4, var5, var6 + 1)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   protected List<BlockPosition> a(BlockPosition var0, EnumDirection var1, RandomSource var2, BlockPosition var3) {
      BlockPosition var4 = var0.d();
      BlockPosition var5 = var0.a(var1);
      int var6 = var0.k(var3);
      int var7 = this.h.d();
      float var8 = this.h.f();
      if (var6 > var7 - 3 && var6 <= var7) {
         return var2.i() < var8 ? List.of(var4, var5.d()) : List.of(var4);
      } else if (var6 > var7) {
         return List.of(var4);
      } else if (var2.i() < var8) {
         return List.of(var4);
      } else {
         return var2.h() ? List.of(var5) : List.of(var4);
      }
   }

   @Override
   protected boolean a(VirtualLevelReadable var0, BlockPosition var1) {
      return super.a(var0, var1) || var0.a(var1, var0x -> var0x.a(this.h.a()));
   }

   @Override
   protected void a(
      VirtualLevelReadable var0, BiConsumer<BlockPosition, IBlockData> var1, RandomSource var2, BlockPosition var3, WorldGenFeatureTreeConfiguration var4
   ) {
      if (var0.a(var3, var0x -> var0x.a(this.h.b()))) {
         IBlockData var5 = this.h.c().a(var2, var3);
         var1.accept(var3, this.a(var0, var3, var5));
      } else {
         super.a(var0, var1, var2, var3, var4);
      }
   }

   @Override
   protected RootPlacerType<?> a() {
      return RootPlacerType.a;
   }
}
