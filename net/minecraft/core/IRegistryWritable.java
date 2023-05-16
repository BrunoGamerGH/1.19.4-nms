package net.minecraft.core;

import com.mojang.serialization.Lifecycle;
import net.minecraft.resources.ResourceKey;

public interface IRegistryWritable<T> extends IRegistry<T> {
   Holder<T> b(int var1, ResourceKey<T> var2, T var3, Lifecycle var4);

   Holder.c<T> a(ResourceKey<T> var1, T var2, Lifecycle var3);

   boolean k();

   HolderGetter<T> n();
}
