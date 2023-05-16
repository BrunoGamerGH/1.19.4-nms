package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.particles.Particles;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockCoralFanWallAbstract;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IBlockFragilePlantElement;
import net.minecraft.world.level.block.state.IBlockData;

public class ItemBoneMeal extends Item {
   public static final int a = 3;
   public static final int b = 1;
   public static final int c = 3;

   public ItemBoneMeal(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      return applyBonemeal(itemactioncontext);
   }

   public static EnumInteractionResult applyBonemeal(ItemActionContext itemactioncontext) {
      World world = itemactioncontext.q();
      BlockPosition blockposition = itemactioncontext.a();
      BlockPosition blockposition1 = blockposition.a(itemactioncontext.k());
      if (a(itemactioncontext.n(), world, blockposition)) {
         if (!world.B) {
            world.c(1505, blockposition, 0);
         }

         return EnumInteractionResult.a(world.B);
      } else {
         IBlockData iblockdata = world.a_(blockposition);
         boolean flag = iblockdata.d(world, blockposition, itemactioncontext.k());
         if (flag && a(itemactioncontext.n(), world, blockposition1, itemactioncontext.k())) {
            if (!world.B) {
               world.c(1505, blockposition1, 0);
            }

            return EnumInteractionResult.a(world.B);
         } else {
            return EnumInteractionResult.d;
         }
      }
   }

   public static boolean a(ItemStack itemstack, World world, BlockPosition blockposition) {
      IBlockData iblockdata = world.a_(blockposition);
      if (iblockdata.b() instanceof IBlockFragilePlantElement iblockfragileplantelement
         && iblockfragileplantelement.a(world, blockposition, iblockdata, world.B)) {
         if (world instanceof WorldServer) {
            if (iblockfragileplantelement.a(world, world.z, blockposition, iblockdata)) {
               iblockfragileplantelement.a((WorldServer)world, world.z, blockposition, iblockdata);
            }

            itemstack.h(1);
         }

         return true;
      }

      return false;
   }

   public static boolean a(ItemStack itemstack, World world, BlockPosition blockposition, @Nullable EnumDirection enumdirection) {
      if (world.a_(blockposition).a(Blocks.G) && world.b_(blockposition).e() == 8) {
         if (!(world instanceof WorldServer)) {
            return true;
         } else {
            RandomSource randomsource = world.r_();

            label76:
            for(int i = 0; i < 128; ++i) {
               BlockPosition blockposition1 = blockposition;
               IBlockData iblockdata = Blocks.bv.o();

               for(int j = 0; j < i / 16; ++j) {
                  blockposition1 = blockposition1.b(randomsource.a(3) - 1, (randomsource.a(3) - 1) * randomsource.a(3) / 2, randomsource.a(3) - 1);
                  if (world.a_(blockposition1).r(world, blockposition1)) {
                     continue label76;
                  }
               }

               Holder<BiomeBase> holder = world.v(blockposition1);
               if (holder.a(BiomeTags.aa)) {
                  if (i == 0 && enumdirection != null && enumdirection.o().d()) {
                     iblockdata = BuiltInRegistries.f
                        .b(TagsBlock.ao)
                        .flatMap(holderset_named -> holderset_named.a(world.z))
                        .map(holder1 -> holder1.a().o())
                        .orElse(iblockdata);
                     if (iblockdata.b(BlockCoralFanWallAbstract.a)) {
                        iblockdata = iblockdata.a(BlockCoralFanWallAbstract.a, enumdirection);
                     }
                  } else if (randomsource.a(4) == 0) {
                     iblockdata = BuiltInRegistries.f
                        .b(TagsBlock.am)
                        .flatMap(holderset_named -> holderset_named.a(world.z))
                        .map(holder1 -> holder1.a().o())
                        .orElse(iblockdata);
                  }
               }

               if (iblockdata.a(TagsBlock.ao, blockbase_blockdata -> blockbase_blockdata.b(BlockCoralFanWallAbstract.a))) {
                  for(int k = 0; !iblockdata.a((IWorldReader)world, blockposition1) && k < 4; ++k) {
                     iblockdata = iblockdata.a(BlockCoralFanWallAbstract.a, EnumDirection.EnumDirectionLimit.a.a(randomsource));
                  }
               }

               if (iblockdata.a((IWorldReader)world, blockposition1)) {
                  IBlockData iblockdata1 = world.a_(blockposition1);
                  if (iblockdata1.a(Blocks.G) && world.b_(blockposition1).e() == 8) {
                     world.a(blockposition1, iblockdata, 3);
                  } else if (iblockdata1.a(Blocks.bv) && randomsource.a(10) == 0) {
                     ((IBlockFragilePlantElement)Blocks.bv).a((WorldServer)world, randomsource, blockposition1, iblockdata1);
                  }
               }
            }

            itemstack.h(1);
            return true;
         }
      } else {
         return false;
      }
   }

   public static void a(GeneratorAccess generatoraccess, BlockPosition blockposition, int i) {
      if (i == 0) {
         i = 15;
      }

      IBlockData iblockdata = generatoraccess.a_(blockposition);
      if (!iblockdata.h()) {
         double d0 = 0.5;
         double d1;
         if (iblockdata.a(Blocks.G)) {
            i *= 3;
            d1 = 1.0;
            d0 = 3.0;
         } else if (iblockdata.i(generatoraccess, blockposition)) {
            blockposition = blockposition.c();
            i *= 3;
            d0 = 3.0;
            d1 = 1.0;
         } else {
            d1 = iblockdata.j(generatoraccess, blockposition).c(EnumDirection.EnumAxis.b);
         }

         generatoraccess.a(Particles.M, (double)blockposition.u() + 0.5, (double)blockposition.v() + 0.5, (double)blockposition.w() + 0.5, 0.0, 0.0, 0.0);
         RandomSource randomsource = generatoraccess.r_();

         for(int j = 0; j < i; ++j) {
            double d2 = randomsource.k() * 0.02;
            double d3 = randomsource.k() * 0.02;
            double d4 = randomsource.k() * 0.02;
            double d5 = 0.5 - d0;
            double d6 = (double)blockposition.u() + d5 + randomsource.j() * d0 * 2.0;
            double d7 = (double)blockposition.v() + randomsource.j() * d1;
            double d8 = (double)blockposition.w() + d5 + randomsource.j() * d0 * 2.0;
            if (!generatoraccess.a_(BlockPosition.a(d6, d7, d8).d()).h()) {
               generatoraccess.a(Particles.M, d6, d7, d8, d2, d3, d4);
            }
         }
      }
   }
}
