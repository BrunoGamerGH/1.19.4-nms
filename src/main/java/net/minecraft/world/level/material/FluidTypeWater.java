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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockFluids;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;

public abstract class FluidTypeWater extends FluidTypeFlowing {
   @Override
   public FluidType d() {
      return FluidTypes.b;
   }

   @Override
   public FluidType e() {
      return FluidTypes.c;
   }

   @Override
   public Item a() {
      return Items.pH;
   }

   @Override
   public void a(World var0, BlockPosition var1, Fluid var2, RandomSource var3) {
      if (!var2.b() && !var2.c(a)) {
         if (var3.a(64) == 0) {
            var0.a(
               (double)var1.u() + 0.5,
               (double)var1.v() + 0.5,
               (double)var1.w() + 0.5,
               SoundEffects.zq,
               SoundCategory.e,
               var3.i() * 0.25F + 0.75F,
               var3.i() + 0.5F,
               false
            );
         }
      } else if (var3.a(10) == 0) {
         var0.a(Particles.ah, (double)var1.u() + var3.j(), (double)var1.v() + var3.j(), (double)var1.w() + var3.j(), 0.0, 0.0, 0.0);
      }
   }

   @Nullable
   @Override
   public ParticleParam h() {
      return Particles.m;
   }

   @Override
   protected boolean a(World var0) {
      return var0.W().b(GameRules.Q);
   }

   @Override
   protected void a(GeneratorAccess var0, BlockPosition var1, IBlockData var2) {
      TileEntity var3 = var2.q() ? var0.c_(var1) : null;
      Block.a(var2, var0, var1, var3);
   }

   @Override
   public int b(IWorldReader var0) {
      return 4;
   }

   @Override
   public IBlockData b(Fluid var0) {
      return Blocks.G.o().a(BlockFluids.a, Integer.valueOf(e(var0)));
   }

   @Override
   public boolean a(FluidType var0) {
      return var0 == FluidTypes.c || var0 == FluidTypes.b;
   }

   @Override
   public int c(IWorldReader var0) {
      return 1;
   }

   @Override
   public int a(IWorldReader var0) {
      return 5;
   }

   @Override
   public boolean a(Fluid var0, IBlockAccess var1, BlockPosition var2, FluidType var3, EnumDirection var4) {
      return var4 == EnumDirection.a && !var3.a(TagsFluid.a);
   }

   @Override
   protected float c() {
      return 100.0F;
   }

   @Override
   public Optional<SoundEffect> j() {
      return Optional.of(SoundEffects.cx);
   }

   public static class a extends FluidTypeWater {
      @Override
      protected void a(BlockStateList.a<FluidType, Fluid> var0) {
         super.a(var0);
         var0.a(b);
      }

      @Override
      public int d(Fluid var0) {
         return var0.c(b);
      }

      @Override
      public boolean c(Fluid var0) {
         return false;
      }
   }

   public static class b extends FluidTypeWater {
      @Override
      public int d(Fluid var0) {
         return 8;
      }

      @Override
      public boolean c(Fluid var0) {
         return true;
      }
   }
}
