package net.minecraft.world.ticks;

import net.minecraft.core.BlockPosition;

public interface LevelTickAccess<T> extends TickList<T> {
   boolean b(BlockPosition var1, T var2);
}
