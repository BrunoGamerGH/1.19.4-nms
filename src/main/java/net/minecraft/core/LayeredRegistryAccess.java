package net.minecraft.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.SystemUtils;
import net.minecraft.resources.ResourceKey;

public class LayeredRegistryAccess<T> {
   private final List<T> a;
   private final List<IRegistryCustom.Dimension> b;
   private final IRegistryCustom.Dimension c;

   public LayeredRegistryAccess(List<T> var0) {
      this(var0, SystemUtils.a(() -> {
         IRegistryCustom.Dimension[] var1x = new IRegistryCustom.Dimension[var0.size()];
         Arrays.fill(var1x, IRegistryCustom.b);
         return Arrays.asList(var1x);
      }));
   }

   private LayeredRegistryAccess(List<T> var0, List<IRegistryCustom.Dimension> var1) {
      this.a = List.copyOf(var0);
      this.b = List.copyOf(var1);
      this.c = new IRegistryCustom.c(a(var1.stream())).c();
   }

   private int d(T var0) {
      int var1 = this.a.indexOf(var0);
      if (var1 == -1) {
         throw new IllegalStateException("Can't find " + var0 + " inside " + this.a);
      } else {
         return var1;
      }
   }

   public IRegistryCustom.Dimension a(T var0) {
      int var1 = this.d(var0);
      return this.b.get(var1);
   }

   public IRegistryCustom.Dimension b(T var0) {
      int var1 = this.d(var0);
      return this.a(0, var1);
   }

   public IRegistryCustom.Dimension c(T var0) {
      int var1 = this.d(var0);
      return this.a(var1, this.b.size());
   }

   private IRegistryCustom.Dimension a(int var0, int var1) {
      return new IRegistryCustom.c(a(this.b.subList(var0, var1).stream())).c();
   }

   public LayeredRegistryAccess<T> a(T var0, IRegistryCustom.Dimension... var1) {
      return this.a(var0, Arrays.asList(var1));
   }

   public LayeredRegistryAccess<T> a(T var0, List<IRegistryCustom.Dimension> var1) {
      int var2 = this.d(var0);
      if (var1.size() > this.b.size() - var2) {
         throw new IllegalStateException("Too many values to replace");
      } else {
         List<IRegistryCustom.Dimension> var3 = new ArrayList<>();

         for(int var4 = 0; var4 < var2; ++var4) {
            var3.add(this.b.get(var4));
         }

         var3.addAll(var1);

         while(var3.size() < this.b.size()) {
            var3.add(IRegistryCustom.b);
         }

         return new LayeredRegistryAccess<>(this.a, var3);
      }
   }

   public IRegistryCustom.Dimension a() {
      return this.c;
   }

   private static Map<ResourceKey<? extends IRegistry<?>>, IRegistry<?>> a(Stream<? extends IRegistryCustom> var0) {
      Map<ResourceKey<? extends IRegistry<?>>, IRegistry<?>> var1 = new HashMap<>();
      var0.forEach(var1x -> var1x.b().forEach(var1xx -> {
            if (var1.put(var1xx.a(), var1xx.b()) != null) {
               throw new IllegalStateException("Duplicated registry " + var1xx.a());
            }
         }));
      return var1;
   }
}
