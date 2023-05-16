package net.minecraft.tags;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.IRegistry;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.RegistryLayer;

public class TagNetworkSerialization {
   public static Map<ResourceKey<? extends IRegistry<?>>, TagNetworkSerialization.a> a(LayeredRegistryAccess<RegistryLayer> var0) {
      return RegistrySynchronization.b(var0)
         .map(var0x -> Pair.of(var0x.a(), a(var0x.b())))
         .filter(var0x -> !((TagNetworkSerialization.a)var0x.getSecond()).a())
         .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
   }

   private static <T> TagNetworkSerialization.a a(IRegistry<T> var0) {
      Map<MinecraftKey, IntList> var1 = new HashMap<>();
      var0.i().forEach(var2 -> {
         HolderSet<T> var3 = (HolderSet)var2.getSecond();
         IntList var4 = new IntArrayList(var3.b());

         for(Holder<T> var6 : var3) {
            if (var6.f() != Holder.b.a) {
               throw new IllegalStateException("Can't serialize unregistered value " + var6);
            }

            var4.add(var0.a(var6.a()));
         }

         var1.put(((TagKey)var2.getFirst()).b(), var4);
      });
      return new TagNetworkSerialization.a(var1);
   }

   public static <T> void a(ResourceKey<? extends IRegistry<T>> var0, IRegistry<T> var1, TagNetworkSerialization.a var2, TagNetworkSerialization.b<T> var3) {
      var2.a.forEach((var3x, var4) -> {
         TagKey<T> var5 = TagKey.a(var0, var3x);
         List<Holder<T>> var6 = var4.intStream().mapToObj(var1::c).flatMap(Optional::stream).collect(Collectors.toUnmodifiableList());
         var3.accept(var5, var6);
      });
   }

   public static final class a {
      final Map<MinecraftKey, IntList> a;

      a(Map<MinecraftKey, IntList> var0) {
         this.a = var0;
      }

      public void a(PacketDataSerializer var0) {
         var0.a(this.a, PacketDataSerializer::a, PacketDataSerializer::a);
      }

      public static TagNetworkSerialization.a b(PacketDataSerializer var0) {
         return new TagNetworkSerialization.a(var0.a(PacketDataSerializer::t, PacketDataSerializer::a));
      }

      public boolean a() {
         return this.a.isEmpty();
      }
   }

   @FunctionalInterface
   public interface b<T> {
      void accept(TagKey<T> var1, List<Holder<T>> var2);
   }
}
