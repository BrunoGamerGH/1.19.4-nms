package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class TileEntityEnderChest extends TileEntity implements LidBlockEntity {
   private final ChestLidController a = new ChestLidController();
   public final ContainerOpenersCounter b = new ContainerOpenersCounter() {
      @Override
      protected void a(World var0, BlockPosition var1, IBlockData var2) {
         var0.a(null, (double)var1.u() + 0.5, (double)var1.v() + 0.5, (double)var1.w() + 0.5, SoundEffects.gO, SoundCategory.e, 0.5F, var0.z.i() * 0.1F + 0.9F);
      }

      @Override
      protected void b(World var0, BlockPosition var1, IBlockData var2) {
         var0.a(null, (double)var1.u() + 0.5, (double)var1.v() + 0.5, (double)var1.w() + 0.5, SoundEffects.gN, SoundCategory.e, 0.5F, var0.z.i() * 0.1F + 0.9F);
      }

      @Override
      protected void a(World var0, BlockPosition var1, IBlockData var2, int var3, int var4) {
         var0.a(TileEntityEnderChest.this.p, Blocks.fF, 1, var4);
      }

      @Override
      protected boolean a(EntityHuman var0) {
         return var0.fW().b(TileEntityEnderChest.this);
      }
   };

   public TileEntityEnderChest(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.d, var0, var1);
   }

   public static void a(World var0, BlockPosition var1, IBlockData var2, TileEntityEnderChest var3) {
      var3.a.a();
   }

   @Override
   public boolean a_(int var0, int var1) {
      if (var0 == 1) {
         this.a.a(var1 > 0);
         return true;
      } else {
         return super.a_(var0, var1);
      }
   }

   public void a(EntityHuman var0) {
      if (!this.q && !var0.F_()) {
         this.b.a(var0, this.k(), this.p(), this.q());
      }
   }

   public void b(EntityHuman var0) {
      if (!this.q && !var0.F_()) {
         this.b.b(var0, this.k(), this.p(), this.q());
      }
   }

   public boolean c(EntityHuman var0) {
      return IInventory.a(this, var0);
   }

   public void c() {
      if (!this.q) {
         this.b.c(this.k(), this.p(), this.q());
      }
   }

   @Override
   public float a(float var0) {
      return this.a.a(var0);
   }
}
