package net.minecraft.world.level.lighting;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.world.level.ChunkCoordIntPair;

public interface ILightEngine {
   void a(BlockPosition var1);

   void a(BlockPosition var1, int var2);

   boolean D_();

   int a(int var1, boolean var2, boolean var3);

   default void a(BlockPosition var0, boolean var1) {
      this.a(SectionPosition.a(var0), var1);
   }

   void a(SectionPosition var1, boolean var2);

   void a(ChunkCoordIntPair var1, boolean var2);
}
