package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.material.FluidTypes;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class SculkBlock extends DropExperienceBlock implements SculkBehaviour {
   public SculkBlock(BlockBase.Info blockbase_info) {
      super(blockbase_info, ConstantInt.a(1));
   }

   @Override
   public int a(
      SculkSpreader.a sculkspreader_a,
      GeneratorAccess generatoraccess,
      BlockPosition blockposition,
      RandomSource randomsource,
      SculkSpreader sculkspreader,
      boolean flag
   ) {
      int i = sculkspreader_a.b();
      if (i != 0 && randomsource.a(sculkspreader.f()) == 0) {
         BlockPosition blockposition1 = sculkspreader_a.a();
         boolean flag1 = blockposition1.a(blockposition, (double)sculkspreader.e());
         if (!flag1 && a(generatoraccess, blockposition1)) {
            int j = sculkspreader.d();
            if (randomsource.a(j) < i) {
               BlockPosition blockposition2 = blockposition1.c();
               IBlockData iblockdata = this.a(generatoraccess, blockposition2, randomsource, sculkspreader.h());
               if (CraftEventFactory.handleBlockSpreadEvent(generatoraccess, blockposition, blockposition2, iblockdata, 3)) {
                  generatoraccess.a(null, blockposition1, iblockdata.t().e(), SoundCategory.e, 1.0F, 1.0F);
               }
            }

            return Math.max(0, i - j);
         } else {
            return randomsource.a(sculkspreader.g()) != 0 ? i : i - (flag1 ? 1 : a(sculkspreader, blockposition1, blockposition, i));
         }
      } else {
         return i;
      }
   }

   private static int a(SculkSpreader sculkspreader, BlockPosition blockposition, BlockPosition blockposition1, int i) {
      int j = sculkspreader.e();
      float f = MathHelper.k((float)Math.sqrt(blockposition.j(blockposition1)) - (float)j);
      int k = MathHelper.h(24 - j);
      float f1 = Math.min(1.0F, f / (float)k);
      return Math.max(1, (int)((float)i * f1 * 0.5F));
   }

   private IBlockData a(GeneratorAccess generatoraccess, BlockPosition blockposition, RandomSource randomsource, boolean flag) {
      IBlockData iblockdata;
      if (randomsource.a(11) == 0) {
         iblockdata = Blocks.qD.o().a(SculkShriekerBlock.c, Boolean.valueOf(flag));
      } else {
         iblockdata = Blocks.qz.o();
      }

      return iblockdata.b(BlockProperties.C) && !generatoraccess.b_(blockposition).c() ? iblockdata.a(BlockProperties.C, Boolean.valueOf(true)) : iblockdata;
   }

   private static boolean a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
      IBlockData iblockdata = generatoraccess.a_(blockposition.c());
      if (iblockdata.h() || iblockdata.a(Blocks.G) && iblockdata.r().b(FluidTypes.c)) {
         int i = 0;

         for(BlockPosition blockposition1 : BlockPosition.a(blockposition.b(-4, 0, -4), blockposition.b(4, 2, 4))) {
            IBlockData iblockdata1 = generatoraccess.a_(blockposition1);
            if (iblockdata1.a(Blocks.qz) || iblockdata1.a(Blocks.qD)) {
               ++i;
            }

            if (i > 2) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean b() {
      return false;
   }
}
