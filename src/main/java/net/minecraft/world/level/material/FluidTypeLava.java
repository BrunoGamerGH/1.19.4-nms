package net.minecraft.world.level.material;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockFireAbstract;
import net.minecraft.world.level.block.BlockFluids;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public abstract class FluidTypeLava extends FluidTypeFlowing {
   public static final float e = 0.44444445F;

   @Override
   public FluidType d() {
      return FluidTypes.d;
   }

   @Override
   public FluidType e() {
      return FluidTypes.e;
   }

   @Override
   public Item a() {
      return Items.pI;
   }

   @Override
   public void a(World world, BlockPosition blockposition, Fluid fluid, RandomSource randomsource) {
      BlockPosition blockposition1 = blockposition.c();
      if (world.a_(blockposition1).h() && !world.a_(blockposition1).i(world, blockposition1)) {
         if (randomsource.a(100) == 0) {
            double d0 = (double)blockposition.u() + randomsource.j();
            double d1 = (double)blockposition.v() + 1.0;
            double d2 = (double)blockposition.w() + randomsource.j();
            world.a(Particles.V, d0, d1, d2, 0.0, 0.0, 0.0);
            world.a(d0, d1, d2, SoundEffects.mg, SoundCategory.e, 0.2F + randomsource.i() * 0.2F, 0.9F + randomsource.i() * 0.15F, false);
         }

         if (randomsource.a(200) == 0) {
            world.a(
               (double)blockposition.u(),
               (double)blockposition.v(),
               (double)blockposition.w(),
               SoundEffects.me,
               SoundCategory.e,
               0.2F + randomsource.i() * 0.2F,
               0.9F + randomsource.i() * 0.15F,
               false
            );
         }
      }
   }

   @Override
   public void b(World world, BlockPosition blockposition, Fluid fluid, RandomSource randomsource) {
      if (world.W().b(GameRules.b)) {
         int i = randomsource.a(3);
         if (i > 0) {
            BlockPosition blockposition1 = blockposition;

            for(int j = 0; j < i; ++j) {
               blockposition1 = blockposition1.b(randomsource.a(3) - 1, 1, randomsource.a(3) - 1);
               if (!world.o(blockposition1)) {
                  return;
               }

               IBlockData iblockdata = world.a_(blockposition1);
               if (iblockdata.h()) {
                  if (this.a((IWorldReader)world, blockposition1)
                     && (
                        world.a_(blockposition1).b() == Blocks.cq
                           || !CraftEventFactory.callBlockIgniteEvent(world, blockposition1, blockposition).isCancelled()
                     )) {
                     world.b(blockposition1, BlockFireAbstract.a(world, blockposition1));
                     return;
                  }
               } else if (iblockdata.d().c()) {
                  return;
               }
            }
         } else {
            for(int k = 0; k < 3; ++k) {
               BlockPosition blockposition2 = blockposition.b(randomsource.a(3) - 1, 0, randomsource.a(3) - 1);
               if (!world.o(blockposition2)) {
                  return;
               }

               if (world.w(blockposition2.c()) && this.b(world, blockposition2)) {
                  BlockPosition up = blockposition2.c();
                  if (world.a_(up).b() == Blocks.cq || !CraftEventFactory.callBlockIgniteEvent(world, up, blockposition).isCancelled()) {
                     world.b(blockposition2.c(), BlockFireAbstract.a(world, blockposition2));
                  }
               }
            }
         }
      }
   }

   private boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
      for(EnumDirection enumdirection : EnumDirection.values()) {
         if (this.b(iworldreader, blockposition.a(enumdirection))) {
            return true;
         }
      }

      return false;
   }

   private boolean b(IWorldReader iworldreader, BlockPosition blockposition) {
      return blockposition.v() >= iworldreader.v_() && blockposition.v() < iworldreader.ai() && !iworldreader.D(blockposition)
         ? false
         : iworldreader.a_(blockposition).d().d();
   }

   @Nullable
   @Override
   public ParticleParam h() {
      return Particles.j;
   }

   @Override
   protected void a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
      this.a(generatoraccess, blockposition);
   }

   @Override
   public int b(IWorldReader iworldreader) {
      return iworldreader.q_().i() ? 4 : 2;
   }

   @Override
   public IBlockData b(Fluid fluid) {
      return Blocks.H.o().a(BlockFluids.a, Integer.valueOf(e(fluid)));
   }

   @Override
   public boolean a(FluidType fluidtype) {
      return fluidtype == FluidTypes.e || fluidtype == FluidTypes.d;
   }

   @Override
   public int c(IWorldReader iworldreader) {
      return iworldreader.q_().i() ? 1 : 2;
   }

   @Override
   public boolean a(Fluid fluid, IBlockAccess iblockaccess, BlockPosition blockposition, FluidType fluidtype, EnumDirection enumdirection) {
      return fluid.a(iblockaccess, blockposition) >= 0.44444445F && fluidtype.a(TagsFluid.a);
   }

   @Override
   public int a(IWorldReader iworldreader) {
      return iworldreader.q_().i() ? 10 : 30;
   }

   @Override
   public int a(World world, BlockPosition blockposition, Fluid fluid, Fluid fluid1) {
      int i = this.a((IWorldReader)world);
      if (!fluid.c()
         && !fluid1.c()
         && !fluid.c(a)
         && !fluid1.c(a)
         && fluid1.a((IBlockAccess)world, blockposition) > fluid.a((IBlockAccess)world, blockposition)
         && world.r_().a(4) != 0) {
         i *= 4;
      }

      return i;
   }

   private void a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
      generatoraccess.c(1501, blockposition, 0);
   }

   @Override
   protected boolean a(World world) {
      return world.W().b(GameRules.R);
   }

   @Override
   protected void a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection, Fluid fluid) {
      if (enumdirection == EnumDirection.a) {
         Fluid fluid1 = generatoraccess.b_(blockposition);
         if (this.a(TagsFluid.b) && fluid1.a(TagsFluid.a)) {
            if (iblockdata.b() instanceof BlockFluids
               && !CraftEventFactory.handleBlockFormEvent(generatoraccess.getMinecraftWorld(), blockposition, Blocks.b.o(), 3)) {
               return;
            }

            this.a(generatoraccess, blockposition);
            return;
         }
      }

      super.a(generatoraccess, blockposition, iblockdata, enumdirection, fluid);
   }

   @Override
   protected boolean i() {
      return true;
   }

   @Override
   protected float c() {
      return 100.0F;
   }

   @Override
   public Optional<SoundEffect> j() {
      return Optional.of(SoundEffects.cA);
   }

   public static class a extends FluidTypeLava {
      @Override
      protected void a(BlockStateList.a<FluidType, Fluid> blockstatelist_a) {
         super.a(blockstatelist_a);
         blockstatelist_a.a(b);
      }

      @Override
      public int d(Fluid fluid) {
         return fluid.c(b);
      }

      @Override
      public boolean c(Fluid fluid) {
         return false;
      }
   }

   public static class b extends FluidTypeLava {
      @Override
      public int d(Fluid fluid) {
         return 8;
      }

      @Override
      public boolean c(Fluid fluid) {
         return true;
      }
   }
}
