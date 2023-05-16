package net.minecraft.nbt;

import java.util.AbstractList;

public abstract class NBTList<T extends NBTBase> extends AbstractList<T> implements NBTBase {
   public abstract T d(int var1, T var2);

   public abstract void c(int var1, T var2);

   public abstract T c(int var1);

   public abstract boolean a(int var1, NBTBase var2);

   public abstract boolean b(int var1, NBTBase var2);

   public abstract byte f();
}
