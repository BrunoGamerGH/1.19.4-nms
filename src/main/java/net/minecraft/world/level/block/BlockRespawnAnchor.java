package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.vehicle.DismountUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.ICollisionAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;

public class BlockRespawnAnchor extends Block {
   public static final int a = 0;
   public static final int b = 4;
   public static final BlockStateInteger c = BlockProperties.aZ;
   private static final ImmutableList<BaseBlockPosition> d = ImmutableList.of(
      new BaseBlockPosition(0, 0, -1),
      new BaseBlockPosition(-1, 0, 0),
      new BaseBlockPosition(0, 0, 1),
      new BaseBlockPosition(1, 0, 0),
      new BaseBlockPosition(-1, 0, -1),
      new BaseBlockPosition(1, 0, -1),
      new BaseBlockPosition(-1, 0, 1),
      new BaseBlockPosition(1, 0, 1)
   );
   private static final ImmutableList<BaseBlockPosition> e = new Builder()
      .addAll(d)
      .addAll(d.stream().map(BaseBlockPosition::o).iterator())
      .addAll(d.stream().map(BaseBlockPosition::p).iterator())
      .add(new BaseBlockPosition(0, 1, 0))
      .build();

   public BlockRespawnAnchor(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(c, Integer.valueOf(0)));
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      ItemStack var6 = var3.b(var4);
      if (var4 == EnumHand.a && !a(var6) && a(var3.b(EnumHand.b))) {
         return EnumInteractionResult.d;
      } else if (a(var6) && h(var0)) {
         a(var3, var1, var2, var0);
         if (!var3.fK().d) {
            var6.h(1);
         }

         return EnumInteractionResult.a(var1.B);
      } else if (var0.c(c) == 0) {
         return EnumInteractionResult.d;
      } else if (!a(var1)) {
         if (!var1.B) {
            this.d(var0, var1, var2);
         }

         return EnumInteractionResult.a(var1.B);
      } else {
         if (!var1.B) {
            EntityPlayer var7 = (EntityPlayer)var3;
            if (var7.P() != var1.ab() || !var2.equals(var7.N())) {
               var7.a(var1.ab(), var2, 0.0F, false, true);
               var1.a(null, (double)var2.u() + 0.5, (double)var2.v() + 0.5, (double)var2.w() + 0.5, SoundEffects.tw, SoundCategory.e, 1.0F, 1.0F);
               return EnumInteractionResult.a;
            }
         }

         return EnumInteractionResult.b;
      }
   }

   private static boolean a(ItemStack var0) {
      return var0.a(Items.eW);
   }

   private static boolean h(IBlockData var0) {
      return var0.c(c) < 4;
   }

   private static boolean a(BlockPosition var0, World var1) {
      Fluid var2 = var1.b_(var0);
      if (!var2.a(TagsFluid.a)) {
         return false;
      } else if (var2.b()) {
         return true;
      } else {
         float var3 = (float)var2.e();
         if (var3 < 2.0F) {
            return false;
         } else {
            Fluid var4 = var1.b_(var0.d());
            return !var4.a(TagsFluid.a);
         }
      }
   }

   private void d(IBlockData var0, World var1, final BlockPosition var2) {
      var1.a(var2, false);
      boolean var3 = EnumDirection.EnumDirectionLimit.a.a().map(var2::a).anyMatch(var1x -> a(var1x, var1));
      final boolean var4 = var3 || var1.b_(var2.c()).a(TagsFluid.a);
      ExplosionDamageCalculator var5 = new ExplosionDamageCalculator() {
         @Override
         public Optional<Float> a(Explosion var0, IBlockAccess var1, BlockPosition var2x, IBlockData var3, Fluid var4x) {
            return var2.equals(var2) && var4 ? Optional.of(Blocks.G.e()) : super.a(var0, var1, var2, var3, var4);
         }
      };
      Vec3D var6 = var2.b();
      var1.a(null, var1.af().a(var6), var5, var6, 5.0F, true, World.a.b);
   }

   public static boolean a(World var0) {
      return var0.q_().m();
   }

   public static void a(@Nullable Entity var0, World var1, BlockPosition var2, IBlockData var3) {
      IBlockData var4 = var3.a(c, Integer.valueOf(var3.c(c) + 1));
      var1.a(var2, var4, 3);
      var1.a(GameEvent.c, var2, GameEvent.a.a(var0, var4));
      var1.a(null, (double)var2.u() + 0.5, (double)var2.v() + 0.5, (double)var2.w() + 0.5, SoundEffects.tu, SoundCategory.e, 1.0F, 1.0F);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      if (var0.c(c) != 0) {
         if (var3.a(100) == 0) {
            var1.a(null, (double)var2.u() + 0.5, (double)var2.v() + 0.5, (double)var2.w() + 0.5, SoundEffects.tt, SoundCategory.e, 1.0F, 1.0F);
         }

         double var4 = (double)var2.u() + 0.5 + (0.5 - var3.j());
         double var6 = (double)var2.v() + 1.0;
         double var8 = (double)var2.w() + 0.5 + (0.5 - var3.j());
         double var10 = (double)var3.i() * 0.04;
         var1.a(Particles.aD, var4, var6, var8, 0.0, var10, 0.0);
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(c);
   }

   @Override
   public boolean d_(IBlockData var0) {
      return true;
   }

   public static int a(IBlockData var0, int var1) {
      return MathHelper.d((float)(var0.c(c) - 0) / 4.0F * (float)var1);
   }

   @Override
   public int a(IBlockData var0, World var1, BlockPosition var2) {
      return a(var0, 15);
   }

   public static Optional<Vec3D> a(EntityTypes<?> var0, ICollisionAccess var1, BlockPosition var2) {
      Optional<Vec3D> var3 = a(var0, var1, var2, true);
      return var3.isPresent() ? var3 : a(var0, var1, var2, false);
   }

   private static Optional<Vec3D> a(EntityTypes<?> var0, ICollisionAccess var1, BlockPosition var2, boolean var3) {
      BlockPosition.MutableBlockPosition var4 = new BlockPosition.MutableBlockPosition();
      UnmodifiableIterator var5 = e.iterator();

      while(var5.hasNext()) {
         BaseBlockPosition var6 = (BaseBlockPosition)var5.next();
         var4.g(var2).h(var6);
         Vec3D var7 = DismountUtil.a(var0, var1, var4, var3);
         if (var7 != null) {
            return Optional.of(var7);
         }
      }

      return Optional.empty();
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
