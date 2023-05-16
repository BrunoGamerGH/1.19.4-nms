package net.minecraft.core;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;

public class RegistryBlockID<T> implements Registry<T> {
   private int b;
   private final Object2IntMap<T> c;
   private final List<T> d;

   public RegistryBlockID() {
      this(512);
   }

   public RegistryBlockID(int var0) {
      this.d = Lists.newArrayListWithExpectedSize(var0);
      this.c = new Object2IntOpenCustomHashMap(var0, SystemUtils.k());
      this.c.defaultReturnValue(-1);
   }

   public void a(T var0, int var1) {
      this.c.put(var0, var1);

      while(this.d.size() <= var1) {
         this.d.add((T)null);
      }

      this.d.set(var1, var0);
      if (this.b <= var1) {
         this.b = var1 + 1;
      }
   }

   public void b(T var0) {
      this.a(var0, this.b);
   }

   @Override
   public int a(T var0) {
      return this.c.getInt(var0);
   }

   @Nullable
   @Override
   public final T a(int var0) {
      return var0 >= 0 && var0 < this.d.size() ? this.d.get(var0) : null;
   }

   @Override
   public Iterator<T> iterator() {
      return Iterators.filter(this.d.iterator(), Objects::nonNull);
   }

   public boolean c(int var0) {
      return this.a(var0) != null;
   }

   @Override
   public int b() {
      return this.c.size();
   }
}
