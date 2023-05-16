package net.minecraft.world.level.chunk;

import java.io.IOException;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.lighting.LightEngine;

public abstract class IChunkProvider implements ILightAccess, AutoCloseable {
   @Nullable
   public Chunk a(int var0, int var1, boolean var2) {
      return (Chunk)this.a(var0, var1, ChunkStatus.o, var2);
   }

   @Nullable
   public Chunk a(int var0, int var1) {
      return this.a(var0, var1, false);
   }

   @Nullable
   @Override
   public IBlockAccess c(int var0, int var1) {
      return this.a(var0, var1, ChunkStatus.c, false);
   }

   public boolean b(int var0, int var1) {
      return this.a(var0, var1, ChunkStatus.o, false) != null;
   }

   @Nullable
   public abstract IChunkAccess a(int var1, int var2, ChunkStatus var3, boolean var4);

   public abstract void a(BooleanSupplier var1, boolean var2);

   public abstract String e();

   public abstract int j();

   @Override
   public void close() throws IOException {
   }

   public abstract LightEngine p();

   public void a(boolean var0, boolean var1) {
   }

   public void a(ChunkCoordIntPair var0, boolean var1) {
   }
}
