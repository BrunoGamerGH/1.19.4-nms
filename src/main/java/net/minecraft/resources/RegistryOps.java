package net.minecraft.resources;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.IRegistry;
import net.minecraft.util.ExtraCodecs;

public class RegistryOps<T> extends DynamicOpsWrapper<T> {
   private final RegistryOps.b b;

   private static RegistryOps.b a(final RegistryOps.b var0) {
      return new RegistryOps.b() {
         private final Map<ResourceKey<? extends IRegistry<?>>, Optional<? extends RegistryOps.a<?>>> b = new HashMap<>();

         @Override
         public <T> Optional<RegistryOps.a<T>> a(ResourceKey<? extends IRegistry<? extends T>> var0x) {
            return this.b.computeIfAbsent(var0, var0::a);
         }
      };
   }

   public static <T> RegistryOps<T> a(DynamicOps<T> var0, final HolderLookup.b var1) {
      return a(var0, a(new RegistryOps.b() {
         @Override
         public <E> Optional<RegistryOps.a<E>> a(ResourceKey<? extends IRegistry<? extends E>> var0) {
            return var1.a(var0).map(var0x -> new RegistryOps.a<>(var0x, var0x, var0x.g()));
         }
      }));
   }

   public static <T> RegistryOps<T> a(DynamicOps<T> var0, RegistryOps.b var1) {
      return new RegistryOps<>(var0, var1);
   }

   private RegistryOps(DynamicOps<T> var0, RegistryOps.b var1) {
      super(var0);
      this.b = var1;
   }

   public <E> Optional<HolderOwner<E>> a(ResourceKey<? extends IRegistry<? extends E>> var0) {
      return this.b.a(var0).map(RegistryOps.a::a);
   }

   public <E> Optional<HolderGetter<E>> b(ResourceKey<? extends IRegistry<? extends E>> var0) {
      return this.b.a(var0).map(RegistryOps.a::b);
   }

   public static <E, O> RecordCodecBuilder<O, HolderGetter<E>> c(ResourceKey<? extends IRegistry<? extends E>> var0) {
      return ExtraCodecs.a(
            (Function<DynamicOps<?>, DataResult<E>>)(var1 -> var1 instanceof RegistryOps var2
                  ? (DataResult)var2.b
                     .a(var0)
                     .map(var0xx -> DataResult.success(var0xx.b(), var0xx.c()))
                     .orElseGet(() -> (T)DataResult.error(() -> "Unknown registry: " + var0))
                  : DataResult.error(() -> "Not a registry ops"))
         )
         .forGetter(var0x -> null);
   }

   public static <E, O> RecordCodecBuilder<O, Holder.c<E>> d(ResourceKey<E> var0) {
      ResourceKey<? extends IRegistry<E>> var1 = ResourceKey.a(var0.b());
      return ExtraCodecs.a(
            (Function<DynamicOps<?>, DataResult<E>>)(var2 -> var2 instanceof RegistryOps var3
                  ? (DataResult)var3.b
                     .a(var1)
                     .flatMap(var1xx -> var1xx.b().a(var0))
                     .map(DataResult::success)
                     .orElseGet(() -> (T)DataResult.error(() -> "Can't find value: " + var0))
                  : DataResult.error(() -> "Not a registry ops"))
         )
         .forGetter(var0x -> null);
   }

   public static record a<T>(HolderOwner<T> owner, HolderGetter<T> getter, Lifecycle elementsLifecycle) {
      private final HolderOwner<T> a;
      private final HolderGetter<T> b;
      private final Lifecycle c;

      public a(HolderOwner<T> var0, HolderGetter<T> var1, Lifecycle var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }
   }

   public interface b {
      <T> Optional<RegistryOps.a<T>> a(ResourceKey<? extends IRegistry<? extends T>> var1);
   }
}
