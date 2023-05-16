package net.minecraft.world.level.lighting;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.chunk.NibbleArray;

public interface LightEngineLayerEventListener extends ILightEngine {
   @Nullable
   NibbleArray a(SectionPosition var1);

   int b(BlockPosition var1);

   public static enum Void implements LightEngineLayerEventListener {
      a;

      @Nullable
      @Override
      public NibbleArray a(SectionPosition var0) {
         return null;
      }

      @Override
      public int b(BlockPosition var0) {
         return 0;
      }

      @Override
      public void a(BlockPosition var0) {
      }

      @Override
      public void a(BlockPosition var0, int var1) {
      }

      @Override
      public boolean D_() {
         return false;
      }

      @Override
      public int a(int var0, boolean var1, boolean var2) {
         return var0;
      }

      @Override
      public void a(SectionPosition var0, boolean var1) {
      }

      @Override
      public void a(ChunkCoordIntPair var0, boolean var1) {
      }
   }
}
