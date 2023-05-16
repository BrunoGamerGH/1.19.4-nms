package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import java.util.Map;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockStairs;
import net.minecraft.world.level.block.BlockStepAbstract;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class DefinedStructureProcessorBlackstoneReplace extends DefinedStructureProcessor {
   public static final Codec<DefinedStructureProcessorBlackstoneReplace> a = Codec.unit(() -> DefinedStructureProcessorBlackstoneReplace.b);
   public static final DefinedStructureProcessorBlackstoneReplace b = new DefinedStructureProcessorBlackstoneReplace();
   private final Map<Block, Block> c = SystemUtils.a(Maps.newHashMap(), var0 -> {
      var0.put(Blocks.m, Blocks.pn);
      var0.put(Blocks.cm, Blocks.pn);
      var0.put(Blocks.b, Blocks.pr);
      var0.put(Blocks.eH, Blocks.ps);
      var0.put(Blocks.eI, Blocks.ps);
      var0.put(Blocks.cP, Blocks.po);
      var0.put(Blocks.ne, Blocks.po);
      var0.put(Blocks.ng, Blocks.pz);
      var0.put(Blocks.fi, Blocks.pw);
      var0.put(Blocks.nc, Blocks.pw);
      var0.put(Blocks.jH, Blocks.pq);
      var0.put(Blocks.ns, Blocks.pq);
      var0.put(Blocks.jD, Blocks.pA);
      var0.put(Blocks.jC, Blocks.pA);
      var0.put(Blocks.jJ, Blocks.pv);
      var0.put(Blocks.nq, Blocks.pv);
      var0.put(Blocks.nG, Blocks.px);
      var0.put(Blocks.nE, Blocks.px);
      var0.put(Blocks.fO, Blocks.pp);
      var0.put(Blocks.fP, Blocks.pp);
      var0.put(Blocks.eK, Blocks.pu);
      var0.put(Blocks.eJ, Blocks.pt);
      var0.put(Blocks.eW, Blocks.eX);
   });

   private DefinedStructureProcessorBlackstoneReplace() {
   }

   @Override
   public DefinedStructure.BlockInfo a(
      IWorldReader var0, BlockPosition var1, BlockPosition var2, DefinedStructure.BlockInfo var3, DefinedStructure.BlockInfo var4, DefinedStructureInfo var5
   ) {
      Block var6 = this.c.get(var4.b.b());
      if (var6 == null) {
         return var4;
      } else {
         IBlockData var7 = var4.b;
         IBlockData var8 = var6.o();
         if (var7.b(BlockStairs.a)) {
            var8 = var8.a(BlockStairs.a, var7.c(BlockStairs.a));
         }

         if (var7.b(BlockStairs.b)) {
            var8 = var8.a(BlockStairs.b, var7.c(BlockStairs.b));
         }

         if (var7.b(BlockStepAbstract.a)) {
            var8 = var8.a(BlockStepAbstract.a, var7.c(BlockStepAbstract.a));
         }

         return new DefinedStructure.BlockInfo(var4.a, var8, var4.c);
      }
   }

   @Override
   protected DefinedStructureStructureProcessorType<?> a() {
      return DefinedStructureStructureProcessorType.h;
   }
}
