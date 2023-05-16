package net.minecraft.core;

public interface HolderOwner<T> {
   default boolean a(HolderOwner<T> var0) {
      return var0 == this;
   }
}
