package net.minecraft.core;

import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;

public class SourceBlock implements ISourceBlock {
   private final WorldServer a;
   private final BlockPosition b;

   public SourceBlock(WorldServer var0, BlockPosition var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public WorldServer g() {
      return this.a;
   }

   @Override
   public double a() {
      return (double)this.b.u() + 0.5;
   }

   @Override
   public double b() {
      return (double)this.b.v() + 0.5;
   }

   @Override
   public double c() {
      return (double)this.b.w() + 0.5;
   }

   @Override
   public BlockPosition d() {
      return this.b;
   }

   @Override
   public IBlockData e() {
      return this.a.a_(this.b);
   }

   @Override
   public <T extends TileEntity> T f() {
      return (T)this.a.c_(this.b);
   }
}
