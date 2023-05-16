package net.minecraft.world.ticks;

import java.util.function.Function;
import net.minecraft.nbt.NBTBase;

public interface SerializableTickContainer<T> {
   NBTBase b(long var1, Function<T, String> var3);
}
