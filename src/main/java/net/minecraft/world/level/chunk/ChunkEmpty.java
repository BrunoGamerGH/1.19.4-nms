package net.minecraft.world.level.chunk;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.server.level.PlayerChunk;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;

public class ChunkEmpty extends Chunk {
   private final Holder<BiomeBase> l;

   public ChunkEmpty(World var0, ChunkCoordIntPair var1, Holder<BiomeBase> var2) {
      super(var0, var1);
      this.l = var2;
   }

   @Override
   public IBlockData a_(BlockPosition var0) {
      return Blocks.mX.o();
   }

   @Nullable
   @Override
   public IBlockData a(BlockPosition var0, IBlockData var1, boolean var2) {
      return null;
   }

   @Override
   public Fluid b_(BlockPosition var0) {
      return FluidTypes.a.g();
   }

   @Override
   public int h(BlockPosition var0) {
      return 0;
   }

   @Nullable
   @Override
   public TileEntity a(BlockPosition var0, Chunk.EnumTileEntityState var1) {
      return null;
   }

   @Override
   public void b(TileEntity var0) {
   }

   @Override
   public void a(TileEntity var0) {
   }

   @Override
   public void d(BlockPosition var0) {
   }

   @Override
   public boolean A() {
      return true;
   }

   @Override
   public boolean a(int var0, int var1) {
      return true;
   }

   @Override
   public PlayerChunk.State B() {
      return PlayerChunk.State.b;
   }

   @Override
   public Holder<BiomeBase> getNoiseBiome(int var0, int var1, int var2) {
      return this.l;
   }
}
