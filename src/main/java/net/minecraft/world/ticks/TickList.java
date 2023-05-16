package net.minecraft.world.ticks;

import net.minecraft.core.BlockPosition;

public interface TickList<T> {
   void a(NextTickListEntry<T> var1);

   boolean a(BlockPosition var1, T var2);

   int a();
}
