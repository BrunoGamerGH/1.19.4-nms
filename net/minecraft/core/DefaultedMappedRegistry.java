package net.minecraft.core;

import com.mojang.serialization.Lifecycle;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;

public class DefaultedMappedRegistry<T> extends RegistryMaterials<T> implements RegistryBlocks<T> {
   private final MinecraftKey b;
   private Holder.c<T> c;

   public DefaultedMappedRegistry(String var0, ResourceKey<? extends IRegistry<T>> var1, Lifecycle var2, boolean var3) {
      super(var1, var2, var3);
      this.b = new MinecraftKey(var0);
   }

   @Override
   public Holder.c<T> a(int var0, ResourceKey<T> var1, T var2, Lifecycle var3) {
      Holder.c<T> var4 = super.a(var0, var1, var2, var3);
      if (this.b.equals(var1.a())) {
         this.c = var4;
      }

      return var4;
   }

   @Override
   public int a(@Nullable T var0) {
      int var1 = super.a(var0);
      return var1 == -1 ? super.a(this.c.a()) : var1;
   }

   @Nonnull
   @Override
   public MinecraftKey b(T var0) {
      MinecraftKey var1 = super.b(var0);
      return var1 == null ? this.b : var1;
   }

   @Nonnull
   @Override
   public T a(@Nullable MinecraftKey var0) {
      T var1 = super.a(var0);
      return (T)(var1 == null ? this.c.a() : var1);
   }

   @Override
   public Optional<T> b(@Nullable MinecraftKey var0) {
      return Optional.ofNullable(super.a(var0));
   }

   @Nonnull
   @Override
   public T a(int var0) {
      T var1 = super.a(var0);
      return (T)(var1 == null ? this.c.a() : var1);
   }

   @Override
   public Optional<Holder.c<T>> a(RandomSource var0) {
      return super.a(var0).or(() -> Optional.of(this.c));
   }

   @Override
   public MinecraftKey a() {
      return this.b;
   }
}
