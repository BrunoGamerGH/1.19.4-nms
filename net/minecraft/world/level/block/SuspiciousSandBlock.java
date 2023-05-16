package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.EntityFallingBlock;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.SuspiciousSandBlockEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.phys.Vec3D;

public class SuspiciousSandBlock extends BlockTileEntity implements Fallable {
   private static final BlockStateInteger b = BlockProperties.bv;
   public static final int a = 2;

   public SuspiciousSandBlock(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(b, Integer.valueOf(0)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(b);
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.c;
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new SuspiciousSandBlockEntity(var0, var1);
   }

   @Override
   public EnumPistonReaction d(IBlockData var0) {
      return EnumPistonReaction.b;
   }

   @Override
   public void b(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      var1.a(var2, this, 2);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      var3.a(var4, this, 2);
      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public void a(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      TileEntity var6 = var1.c_(var2);
      if (var6 instanceof SuspiciousSandBlockEntity var4) {
         var4.c();
      }

      if (BlockFalling.h(var1.a_(var2.d())) && var2.v() >= var1.v_()) {
         EntityFallingBlock var4 = EntityFallingBlock.a(var1, var2, var0);
         var4.j();
      }
   }

   @Override
   public void a(World var0, BlockPosition var1, EntityFallingBlock var2) {
      Vec3D var3 = var2.cD().f();
      var0.c(2001, BlockPosition.a(var3), Block.i(var2.k()));
      var0.a(var2, GameEvent.f, var3);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      if (var3.a(16) == 0) {
         BlockPosition var4 = var2.d();
         if (BlockFalling.h(var1.a_(var4))) {
            double var5 = (double)var2.u() + var3.j();
            double var7 = (double)var2.v() - 0.05;
            double var9 = (double)var2.w() + var3.j();
            var1.a(new ParticleParamBlock(Particles.z, var0), var5, var7, var9, 0.0, 0.0, 0.0);
         }
      }
   }
}
