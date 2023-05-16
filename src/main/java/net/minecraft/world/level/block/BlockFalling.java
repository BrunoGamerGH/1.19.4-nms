package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.EntityFallingBlock;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Material;

public class BlockFalling extends Block implements Fallable {
   public BlockFalling(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public void b(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      var1.a(var2, this, this.b());
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      var3.a(var4, this, this.b());
      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public void a(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      if (h(var1.a_(var2.d())) && var2.v() >= var1.v_()) {
         EntityFallingBlock var4 = EntityFallingBlock.a(var1, var2, var0);
         this.a(var4);
      }
   }

   protected void a(EntityFallingBlock var0) {
   }

   protected int b() {
      return 2;
   }

   public static boolean h(IBlockData var0) {
      Material var1 = var0.d();
      return var0.h() || var0.a(TagsBlock.aH) || var1.a() || var0.o();
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      if (var3.a(16) == 0) {
         BlockPosition var4 = var2.d();
         if (h(var1.a_(var4))) {
            ParticleUtils.a(var1, var2, var3, new ParticleParamBlock(Particles.z, var0));
         }
      }
   }

   public int d(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return -16777216;
   }
}
