package net.minecraft.world.level.levelgen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.material.FluidTypes;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class WorldGenCavesHell extends WorldGenCaves {
   public WorldGenCavesHell(Codec<CaveCarverConfiguration> var0) {
      super(var0);
      this.h = ImmutableSet.of(FluidTypes.e, FluidTypes.c);
   }

   @Override
   protected int a() {
      return 10;
   }

   @Override
   protected float a(RandomSource var0) {
      return (var0.i() * 2.0F + var0.i()) * 2.0F;
   }

   @Override
   protected double b() {
      return 5.0;
   }

   protected boolean a(
      CarvingContext var0,
      CaveCarverConfiguration var1,
      IChunkAccess var2,
      Function<BlockPosition, Holder<BiomeBase>> var3,
      CarvingMask var4,
      BlockPosition.MutableBlockPosition var5,
      BlockPosition.MutableBlockPosition var6,
      Aquifer var7,
      MutableBoolean var8
   ) {
      if (this.a(var1, var2.a_(var5))) {
         IBlockData var9;
         if (var5.v() <= var0.a() + 31) {
            var9 = g.g();
         } else {
            var9 = e;
         }

         var2.a(var5, var9, false);
         return true;
      } else {
         return false;
      }
   }
}
