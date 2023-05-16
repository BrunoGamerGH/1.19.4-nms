package net.minecraft.resources;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.HolderSet;
import net.minecraft.core.IRegistry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;

public class HolderSetCodec<E> implements Codec<HolderSet<E>> {
   private final ResourceKey<? extends IRegistry<E>> a;
   private final Codec<Holder<E>> b;
   private final Codec<List<Holder<E>>> c;
   private final Codec<Either<TagKey<E>, List<Holder<E>>>> d;

   private static <E> Codec<List<Holder<E>>> a(Codec<Holder<E>> var0, boolean var1) {
      Codec<List<Holder<E>>> var2 = ExtraCodecs.a(var0.listOf(), ExtraCodecs.b(Holder::f));
      return var1
         ? var2
         : Codec.either(var2, var0)
            .xmap(var0x -> (List)var0x.map(var0xx -> var0xx, List::of), var0x -> var0x.size() == 1 ? Either.right((Holder)var0x.get(0)) : Either.left(var0x));
   }

   public static <E> Codec<HolderSet<E>> a(ResourceKey<? extends IRegistry<E>> var0, Codec<Holder<E>> var1, boolean var2) {
      return new HolderSetCodec<>(var0, var1, var2);
   }

   private HolderSetCodec(ResourceKey<? extends IRegistry<E>> var0, Codec<Holder<E>> var1, boolean var2) {
      this.a = var0;
      this.b = var1;
      this.c = a(var1, var2);
      this.d = Codec.either(TagKey.b(var0), this.c);
   }

   public <T> DataResult<Pair<HolderSet<E>, T>> decode(DynamicOps<T> var0, T var1) {
      if (var0 instanceof RegistryOps var2) {
         Optional<HolderGetter<E>> var3 = var2.b(this.a);
         if (var3.isPresent()) {
            HolderGetter<E> var4 = var3.get();
            return this.d.decode(var0, var1).map(var1x -> var1x.mapFirst(var1xx -> (HolderSet)var1xx.map(var4::b, HolderSet::a)));
         }
      }

      return this.a(var0, var1);
   }

   public <T> DataResult<T> a(HolderSet<E> var0, DynamicOps<T> var1, T var2) {
      if (var1 instanceof RegistryOps var3) {
         Optional<HolderOwner<E>> var4 = var3.a(this.a);
         if (var4.isPresent()) {
            if (!var0.a(var4.get())) {
               return DataResult.error(() -> "HolderSet " + var0 + " is not valid in current registry set");
            }

            return this.d.encode(var0.c().mapRight(List::copyOf), var1, var2);
         }
      }

      return this.b(var0, var1, var2);
   }

   private <T> DataResult<Pair<HolderSet<E>, T>> a(DynamicOps<T> var0, T var1) {
      return this.b.listOf().decode(var0, var1).flatMap(var0x -> {
         List<Holder.a<E>> var1x = new ArrayList();

         for(Holder<E> var3 : (List)var0x.getFirst()) {
            if (!(var3 instanceof Holder.a)) {
               return DataResult.error(() -> "Can't decode element " + var3 + " without registry");
            }

            Holder.a<E> var4 = (Holder.a)var3;
            var1x.add(var4);
         }

         return DataResult.success(new Pair(HolderSet.a(var1x), var0x.getSecond()));
      });
   }

   private <T> DataResult<T> b(HolderSet<E> var0, DynamicOps<T> var1, T var2) {
      return this.c.encode(var0.a().toList(), var1, var2);
   }
}
