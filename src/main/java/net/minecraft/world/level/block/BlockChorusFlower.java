package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockChorusFlower extends Block {
   public static final int a = 5;
   public static final BlockStateInteger b = BlockProperties.au;
   private final BlockChorusFruit c;

   protected BlockChorusFlower(BlockChorusFruit blockchorusfruit, BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.c = blockchorusfruit;
      this.k(this.D.b().a(b, Integer.valueOf(0)));
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (!iblockdata.a(worldserver, blockposition)) {
         worldserver.b(blockposition, true);
      }
   }

   @Override
   public boolean e_(IBlockData iblockdata) {
      return iblockdata.c(b) < 5;
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      BlockPosition blockposition1 = blockposition.c();
      if (worldserver.w(blockposition1) && blockposition1.v() < worldserver.ai()) {
         int i = iblockdata.c(b);
         if (i < 5) {
            boolean flag = false;
            boolean flag1 = false;
            IBlockData iblockdata1 = worldserver.a_(blockposition.d());
            if (iblockdata1.a(Blocks.fy)) {
               flag = true;
            } else if (!iblockdata1.a(this.c)) {
               if (iblockdata1.h()) {
                  flag = true;
               }
            } else {
               int j = 1;

               for(int k = 0; k < 4; ++k) {
                  IBlockData iblockdata2 = worldserver.a_(blockposition.c(j + 1));
                  if (!iblockdata2.a(this.c)) {
                     if (iblockdata2.a(Blocks.fy)) {
                        flag1 = true;
                     }
                     break;
                  }

                  ++j;
               }

               if (j < 2 || j <= randomsource.a(flag1 ? 5 : 4)) {
                  flag = true;
               }
            }

            if (flag && b(worldserver, blockposition1, null) && worldserver.w(blockposition.b(2))) {
               if (CraftEventFactory.handleBlockSpreadEvent(worldserver, blockposition, blockposition1, this.o().a(b, Integer.valueOf(i)), 2)) {
                  worldserver.a(blockposition, this.c.a(worldserver, blockposition), 2);
                  this.a(worldserver, blockposition1, i);
               }
            } else if (i < 4) {
               int j = randomsource.a(4);
               if (flag1) {
                  ++j;
               }

               boolean flag2 = false;

               for(int l = 0; l < j; ++l) {
                  EnumDirection enumdirection = EnumDirection.EnumDirectionLimit.a.a(randomsource);
                  BlockPosition blockposition2 = blockposition.a(enumdirection);
                  if (worldserver.w(blockposition2)
                     && worldserver.w(blockposition2.d())
                     && b(worldserver, blockposition2, enumdirection.g())
                     && CraftEventFactory.handleBlockSpreadEvent(worldserver, blockposition, blockposition2, this.o().a(b, Integer.valueOf(i + 1)), 2)) {
                     this.a(worldserver, blockposition2, i + 1);
                     flag2 = true;
                  }
               }

               if (flag2) {
                  worldserver.a(blockposition, this.c.a(worldserver, blockposition), 2);
               } else if (CraftEventFactory.handleBlockGrowEvent(worldserver, blockposition, this.o().a(b, Integer.valueOf(5)), 2)) {
                  this.a(worldserver, blockposition);
               }
            } else if (CraftEventFactory.handleBlockGrowEvent(worldserver, blockposition, this.o().a(b, Integer.valueOf(5)), 2)) {
               this.a(worldserver, blockposition);
            }
         }
      }
   }

   private void a(World world, BlockPosition blockposition, int i) {
      world.a(blockposition, this.o().a(b, Integer.valueOf(i)), 2);
      world.c(1033, blockposition, 0);
   }

   private void a(World world, BlockPosition blockposition) {
      world.a(blockposition, this.o().a(b, Integer.valueOf(5)), 2);
      world.c(1034, blockposition, 0);
   }

   private static boolean b(IWorldReader iworldreader, BlockPosition blockposition, @Nullable EnumDirection enumdirection) {
      for(EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.a) {
         if (enumdirection1 != enumdirection && !iworldreader.w(blockposition.a(enumdirection1))) {
            return false;
         }
      }

      return true;
   }

   @Override
   public IBlockData a(
      IBlockData iblockdata,
      EnumDirection enumdirection,
      IBlockData iblockdata1,
      GeneratorAccess generatoraccess,
      BlockPosition blockposition,
      BlockPosition blockposition1
   ) {
      if (enumdirection != EnumDirection.b && !iblockdata.a(generatoraccess, blockposition)) {
         generatoraccess.a(blockposition, this, 1);
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      IBlockData iblockdata1 = iworldreader.a_(blockposition.d());
      if (!iblockdata1.a(this.c) && !iblockdata1.a(Blocks.fy)) {
         if (!iblockdata1.h()) {
            return false;
         } else {
            boolean flag = false;

            for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
               IBlockData iblockdata2 = iworldreader.a_(blockposition.a(enumdirection));
               if (iblockdata2.a(this.c)) {
                  if (flag) {
                     return false;
                  }

                  flag = true;
               } else if (!iblockdata2.h()) {
                  return false;
               }
            }

            return flag;
         }
      } else {
         return true;
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(b);
   }

   public static void a(GeneratorAccess generatoraccess, BlockPosition blockposition, RandomSource randomsource, int i) {
      generatoraccess.a(blockposition, ((BlockChorusFruit)Blocks.kt).a(generatoraccess, blockposition), 2);
      a(generatoraccess, blockposition, randomsource, blockposition, i, 0);
   }

   private static void a(GeneratorAccess generatoraccess, BlockPosition blockposition, RandomSource randomsource, BlockPosition blockposition1, int i, int j) {
      BlockChorusFruit blockchorusfruit = (BlockChorusFruit)Blocks.kt;
      int k = randomsource.a(4) + 1;
      if (j == 0) {
         ++k;
      }

      for(int l = 0; l < k; ++l) {
         BlockPosition blockposition2 = blockposition.b(l + 1);
         if (!b(generatoraccess, blockposition2, null)) {
            return;
         }

         generatoraccess.a(blockposition2, blockchorusfruit.a(generatoraccess, blockposition2), 2);
         generatoraccess.a(blockposition2.d(), blockchorusfruit.a(generatoraccess, blockposition2.d()), 2);
      }

      boolean flag = false;
      if (j < 4) {
         int i1 = randomsource.a(4);
         if (j == 0) {
            ++i1;
         }

         for(int j1 = 0; j1 < i1; ++j1) {
            EnumDirection enumdirection = EnumDirection.EnumDirectionLimit.a.a(randomsource);
            BlockPosition blockposition3 = blockposition.b(k).a(enumdirection);
            if (Math.abs(blockposition3.u() - blockposition1.u()) < i
               && Math.abs(blockposition3.w() - blockposition1.w()) < i
               && generatoraccess.w(blockposition3)
               && generatoraccess.w(blockposition3.d())
               && b(generatoraccess, blockposition3, enumdirection.g())) {
               flag = true;
               generatoraccess.a(blockposition3, blockchorusfruit.a(generatoraccess, blockposition3), 2);
               generatoraccess.a(blockposition3.a(enumdirection.g()), blockchorusfruit.a(generatoraccess, blockposition3.a(enumdirection.g())), 2);
               a(generatoraccess, blockposition3, randomsource, blockposition1, i, j + 1);
            }
         }
      }

      if (!flag) {
         generatoraccess.a(blockposition.b(k), Blocks.ku.o().a(b, Integer.valueOf(5)), 2);
      }
   }

   @Override
   public void a(World world, IBlockData iblockdata, MovingObjectPositionBlock movingobjectpositionblock, IProjectile iprojectile) {
      BlockPosition blockposition = movingobjectpositionblock.a();
      if (!world.B && iprojectile.a(world, blockposition) && iprojectile.ae().a(TagsEntity.e)) {
         if (CraftEventFactory.callEntityChangeBlockEvent(iprojectile, blockposition, Blocks.a.o()).isCancelled()) {
            return;
         }

         world.a(blockposition, true, iprojectile);
      }
   }
}
