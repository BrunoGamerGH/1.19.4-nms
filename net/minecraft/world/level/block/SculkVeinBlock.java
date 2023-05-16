package net.minecraft.world.level.block;

import java.util.Collection;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.material.Material;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class SculkVeinBlock extends MultifaceBlock implements SculkBehaviour, IBlockWaterlogged {
   private static final BlockStateBoolean b = BlockProperties.C;
   private final MultifaceSpreader c = new MultifaceSpreader(new SculkVeinBlock.a(MultifaceSpreader.a));
   private final MultifaceSpreader d = new MultifaceSpreader(new SculkVeinBlock.a(MultifaceSpreader.e.a));

   public SculkVeinBlock(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.o().a(b, Boolean.valueOf(false)));
   }

   @Override
   public MultifaceSpreader c() {
      return this.c;
   }

   public MultifaceSpreader d() {
      return this.d;
   }

   public static boolean a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, Collection<EnumDirection> collection) {
      boolean flag = false;
      IBlockData iblockdata1 = Blocks.qB.o();

      for(EnumDirection enumdirection : collection) {
         BlockPosition blockposition1 = blockposition.a(enumdirection);
         if (a(generatoraccess, enumdirection, blockposition1, generatoraccess.a_(blockposition1))) {
            iblockdata1 = iblockdata1.a(b(enumdirection), Boolean.valueOf(true));
            flag = true;
         }
      }

      if (!flag) {
         return false;
      } else {
         if (!iblockdata.r().c()) {
            iblockdata1 = iblockdata1.a(b, Boolean.valueOf(true));
         }

         generatoraccess.a(blockposition, iblockdata1, 3);
         return true;
      }
   }

   @Override
   public void a(GeneratorAccess generatoraccess, IBlockData iblockdata, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.a(this)) {
         for(EnumDirection enumdirection : a) {
            BlockStateBoolean blockstateboolean = b(enumdirection);
            if (iblockdata.c(blockstateboolean) && generatoraccess.a_(blockposition.a(enumdirection)).a(Blocks.qA)) {
               iblockdata = iblockdata.a(blockstateboolean, Boolean.valueOf(false));
            }
         }

         if (!n(iblockdata)) {
            Fluid fluid = generatoraccess.b_(blockposition);
            iblockdata = (fluid.c() ? Blocks.a : Blocks.G).o();
         }

         generatoraccess.a(blockposition, iblockdata, 3);
         SculkBehaviour.super.a(generatoraccess, iblockdata, blockposition, randomsource);
      }
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
      return flag && this.attemptPlaceSculk(sculkspreader, generatoraccess, sculkspreader_a.a(), randomsource, blockposition)
         ? sculkspreader_a.b() - 1
         : (randomsource.a(sculkspreader.f()) == 0 ? MathHelper.d((float)sculkspreader_a.b() * 0.5F) : sculkspreader_a.b());
   }

   private boolean attemptPlaceSculk(
      SculkSpreader sculkspreader, GeneratorAccess generatoraccess, BlockPosition blockposition, RandomSource randomsource, BlockPosition sourceBlock
   ) {
      IBlockData iblockdata = generatoraccess.a_(blockposition);
      TagKey<Block> tagkey = sculkspreader.c();

      for(EnumDirection enumdirection : EnumDirection.a(randomsource)) {
         if (a(iblockdata, enumdirection)) {
            BlockPosition blockposition1 = blockposition.a(enumdirection);
            IBlockData iblockdata1 = generatoraccess.a_(blockposition1);
            if (iblockdata1.a(tagkey)) {
               IBlockData iblockdata2 = Blocks.qA.o();
               if (!CraftEventFactory.handleBlockSpreadEvent(generatoraccess, sourceBlock, blockposition1, iblockdata2, 3)) {
                  return false;
               }

               Block.a(iblockdata1, iblockdata2, generatoraccess, blockposition1);
               generatoraccess.a(null, blockposition1, SoundEffects.tQ, SoundCategory.e, 1.0F, 1.0F);
               this.c.a(iblockdata2, generatoraccess, blockposition1, sculkspreader.h());
               EnumDirection enumdirection1 = enumdirection.g();

               for(EnumDirection enumdirection2 : a) {
                  if (enumdirection2 != enumdirection1) {
                     BlockPosition blockposition2 = blockposition1.a(enumdirection2);
                     IBlockData iblockdata3 = generatoraccess.a_(blockposition2);
                     if (iblockdata3.a(this)) {
                        this.a(generatoraccess, iblockdata3, blockposition2, randomsource);
                     }
                  }
               }

               return true;
            }
         }
      }

      return false;
   }

   public static boolean a(GeneratorAccess generatoraccess, IBlockData iblockdata, BlockPosition blockposition) {
      if (!iblockdata.a(Blocks.qB)) {
         return false;
      } else {
         for(EnumDirection enumdirection : a) {
            if (a(iblockdata, enumdirection) && generatoraccess.a_(blockposition.a(enumdirection)).a(TagsBlock.bG)) {
               return true;
            }
         }

         return false;
      }
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
      if (iblockdata.c(b)) {
         generatoraccess.a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      super.a(blockstatelist_a);
      blockstatelist_a.a(b);
   }

   @Override
   public boolean a(IBlockData iblockdata, BlockActionContext blockactioncontext) {
      return !blockactioncontext.n().a(Items.fK) || super.a(iblockdata, blockactioncontext);
   }

   @Override
   public Fluid c_(IBlockData iblockdata) {
      return iblockdata.c(b) ? FluidTypes.c.a(false) : super.c_(iblockdata);
   }

   @Override
   public EnumPistonReaction d(IBlockData iblockdata) {
      return EnumPistonReaction.b;
   }

   private class a extends MultifaceSpreader.a {
      private final MultifaceSpreader.e[] c;

      public a(MultifaceSpreader.e... amultifacespreader_e) {
         super(SculkVeinBlock.this);
         this.c = amultifacespreader_e;
      }

      @Override
      public boolean a(
         IBlockAccess iblockaccess, BlockPosition blockposition, BlockPosition blockposition1, EnumDirection enumdirection, IBlockData iblockdata
      ) {
         IBlockData iblockdata1 = iblockaccess.a_(blockposition1.a(enumdirection));
         if (!iblockdata1.a(Blocks.qA) && !iblockdata1.a(Blocks.qC) && !iblockdata1.a(Blocks.bP)) {
            if (blockposition.k(blockposition1) == 2) {
               BlockPosition blockposition2 = blockposition.a(enumdirection.g());
               if (iblockaccess.a_(blockposition2).d(iblockaccess, blockposition2, enumdirection)) {
                  return false;
               }
            }

            Fluid fluid = iblockdata.r();
            if (!fluid.c() && !fluid.b(FluidTypes.c)) {
               return false;
            } else {
               Material material = iblockdata.d();
               return material == Material.n ? false : iblockdata.o() || super.a(iblockaccess, blockposition, blockposition1, enumdirection, iblockdata);
            }
         } else {
            return false;
         }
      }

      @Override
      public MultifaceSpreader.e[] a() {
         return this.c;
      }

      @Override
      public boolean a(IBlockData iblockdata) {
         return !iblockdata.a(Blocks.qB);
      }
   }
}
