package net.minecraft.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;

public interface RegistryBlocks<T> extends IRegistry<T> {
   @Nonnull
   @Override
   MinecraftKey b(T var1);

   @Nonnull
   @Override
   T a(@Nullable MinecraftKey var1);

   @Nonnull
   @Override
   T a(int var1);

   MinecraftKey a();
}
