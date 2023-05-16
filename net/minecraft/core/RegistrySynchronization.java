package net.minecraft.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.SystemUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.RegistryLayer;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.dimension.DimensionManager;

public class RegistrySynchronization {
   private static final Map<ResourceKey<? extends IRegistry<?>>, RegistrySynchronization.a<?>> b = SystemUtils.a(() -> {
      Builder<ResourceKey<? extends IRegistry<?>>, RegistrySynchronization.a<?>> var0 = ImmutableMap.builder();
      a(var0, Registries.an, BiomeBase.b);
      a(var0, Registries.ao, ChatMessageType.a);
      a(var0, Registries.aC, TrimPattern.a);
      a(var0, Registries.aB, TrimMaterial.a);
      a(var0, Registries.as, DimensionManager.h);
      a(var0, Registries.o, DamageType.a);
      return var0.build();
   });
   public static final Codec<IRegistryCustom> a = a();

   private static <E> void a(
      Builder<ResourceKey<? extends IRegistry<?>>, RegistrySynchronization.a<?>> var0, ResourceKey<? extends IRegistry<E>> var1, Codec<E> var2
   ) {
      var0.put(var1, new RegistrySynchronization.a<>(var1, var2));
   }

   private static Stream<IRegistryCustom.d<?>> a(IRegistryCustom var0) {
      return var0.b().filter(var0x -> b.containsKey(var0x.a()));
   }

   private static <E> DataResult<? extends Codec<E>> a(ResourceKey<? extends IRegistry<E>> var0) {
      return (DataResult<? extends Codec<E>>)Optional.ofNullable(b.get(var0))
         .map(var0x -> var0x.b())
         .map(DataResult::success)
         .orElseGet(() -> DataResult.error(() -> "Unknown or not serializable registry: " + var0));
   }

   private static <E> Codec<IRegistryCustom> a() {
      Codec<ResourceKey<? extends IRegistry<E>>> var0 = MinecraftKey.a.xmap(ResourceKey::a, ResourceKey::a);
      Codec<IRegistry<E>> var1 = var0.partialDispatch(
         "type", var0x -> DataResult.success(var0x.c()), var0x -> a(var0x).map(var1x -> RegistryCodecs.a(var0x, Lifecycle.experimental(), var1x))
      );
      UnboundedMapCodec<? extends ResourceKey<? extends IRegistry<?>>, ? extends IRegistry<?>> var2 = Codec.unboundedMap(var0, var1);
      return a(var2);
   }

   private static <K extends ResourceKey<? extends IRegistry<?>>, V extends IRegistry<?>> Codec<IRegistryCustom> a(UnboundedMapCodec<K, V> var0) {
      return var0.xmap(IRegistryCustom.c::new, var0x -> a(var0x).collect(ImmutableMap.toImmutableMap(var0xx -> var0xx.a(), var0xx -> var0xx.b())));
   }

   public static Stream<IRegistryCustom.d<?>> a(LayeredRegistryAccess<RegistryLayer> var0) {
      return a(var0.c(RegistryLayer.b));
   }

   public static Stream<IRegistryCustom.d<?>> b(LayeredRegistryAccess<RegistryLayer> var0) {
      Stream<IRegistryCustom.d<?>> var1 = var0.a(RegistryLayer.a).b();
      Stream<IRegistryCustom.d<?>> var2 = a(var0);
      return Stream.concat(var2, var1);
   }

   static record a<E>(ResourceKey<? extends IRegistry<E>> key, Codec<E> networkCodec) {
      private final ResourceKey<? extends IRegistry<E>> a;
      private final Codec<E> b;

      a(ResourceKey<? extends IRegistry<E>> var0, Codec<E> var1) {
         this.a = var0;
         this.b = var1;
      }
   }
}
