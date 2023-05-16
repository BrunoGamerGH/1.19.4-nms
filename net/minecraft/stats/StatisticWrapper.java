package net.minecraft.stats;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.IChatBaseComponent;

public class StatisticWrapper<T> implements Iterable<Statistic<T>> {
   private final IRegistry<T> a;
   private final Map<T, Statistic<T>> b = new IdentityHashMap<>();
   @Nullable
   private IChatBaseComponent c;

   public StatisticWrapper(IRegistry<T> var0) {
      this.a = var0;
   }

   public boolean a(T var0) {
      return this.b.containsKey(var0);
   }

   public Statistic<T> a(T var0, Counter var1) {
      return this.b.computeIfAbsent(var0, var1x -> new Statistic<>(this, var1x, var1));
   }

   public IRegistry<T> a() {
      return this.a;
   }

   @Override
   public Iterator<Statistic<T>> iterator() {
      return this.b.values().iterator();
   }

   public Statistic<T> b(T var0) {
      return this.a(var0, Counter.b);
   }

   public String b() {
      return "stat_type." + BuiltInRegistries.x.b(this).toString().replace(':', '.');
   }

   public IChatBaseComponent c() {
      if (this.c == null) {
         this.c = IChatBaseComponent.c(this.b());
      }

      return this.c;
   }
}
