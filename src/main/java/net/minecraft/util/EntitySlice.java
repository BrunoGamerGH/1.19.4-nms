package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class EntitySlice<T> extends AbstractCollection<T> {
   private final Map<Class<?>, List<T>> a = Maps.newHashMap();
   private final Class<T> b;
   private final List<T> c = Lists.newArrayList();

   public EntitySlice(Class<T> var0) {
      this.b = var0;
      this.a.put(var0, this.c);
   }

   @Override
   public boolean add(T var0) {
      boolean var1 = false;

      for(Entry<Class<?>, List<T>> var3 : this.a.entrySet()) {
         if (var3.getKey().isInstance(var0)) {
            var1 |= var3.getValue().add(var0);
         }
      }

      return var1;
   }

   @Override
   public boolean remove(Object var0) {
      boolean var1 = false;

      for(Entry<Class<?>, List<T>> var3 : this.a.entrySet()) {
         if (var3.getKey().isInstance(var0)) {
            List<T> var4 = var3.getValue();
            var1 |= var4.remove(var0);
         }
      }

      return var1;
   }

   @Override
   public boolean contains(Object var0) {
      return this.a(var0.getClass()).contains(var0);
   }

   public <S> Collection<S> a(Class<S> var0) {
      if (!this.b.isAssignableFrom(var0)) {
         throw new IllegalArgumentException("Don't know how to search for " + var0);
      } else {
         List<? extends T> var1 = this.a.computeIfAbsent(var0, var0x -> this.c.stream().filter(var0x::isInstance).collect(Collectors.toList()));
         return Collections.unmodifiableCollection(var1);
      }
   }

   @Override
   public Iterator<T> iterator() {
      return (Iterator<T>)(this.c.isEmpty() ? Collections.emptyIterator() : Iterators.unmodifiableIterator(this.c.iterator()));
   }

   public List<T> a() {
      return ImmutableList.copyOf(this.c);
   }

   @Override
   public int size() {
      return this.c.size();
   }
}
