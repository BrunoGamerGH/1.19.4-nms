package net.minecraft.data.worldgen;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.IRegistry;
import net.minecraft.resources.ResourceKey;

public interface BootstapContext<T> {
   Holder.c<T> a(ResourceKey<T> var1, T var2, Lifecycle var3);

   default Holder.c<T> a(ResourceKey<T> var0, T var1) {
      return this.a(var0, var1, Lifecycle.stable());
   }

   <S> HolderGetter<S> a(ResourceKey<? extends IRegistry<? extends S>> var1);
}
