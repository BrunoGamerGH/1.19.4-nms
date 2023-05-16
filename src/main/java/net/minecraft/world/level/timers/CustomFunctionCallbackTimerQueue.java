package net.minecraft.world.level.timers;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.primitives.UnsignedLong;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.slf4j.Logger;

public class CustomFunctionCallbackTimerQueue<T> {
   private static final Logger a = LogUtils.getLogger();
   private static final String b = "Callback";
   private static final String c = "Name";
   private static final String d = "TriggerTime";
   private final CustomFunctionCallbackTimers<T> e;
   private final Queue<CustomFunctionCallbackTimerQueue.a<T>> f = new PriorityQueue<>(c());
   private UnsignedLong g = UnsignedLong.ZERO;
   private final Table<String, Long, CustomFunctionCallbackTimerQueue.a<T>> h = HashBasedTable.create();

   private static <T> Comparator<CustomFunctionCallbackTimerQueue.a<T>> c() {
      return Comparator.<CustomFunctionCallbackTimerQueue.a<T>>comparingLong(var0 -> var0.a).thenComparing(var0 -> var0.b);
   }

   public CustomFunctionCallbackTimerQueue(CustomFunctionCallbackTimers<T> var0, Stream<? extends Dynamic<?>> var1) {
      this(var0);
      this.f.clear();
      this.h.clear();
      this.g = UnsignedLong.ZERO;
      var1.forEach(var0x -> {
         NBTBase var1x = (NBTBase)var0x.convert(DynamicOpsNBT.a).getValue();
         if (var1x instanceof NBTTagCompound var2x) {
            this.a(var2x);
         } else {
            a.warn("Invalid format of events: {}", var1x);
         }
      });
   }

   public CustomFunctionCallbackTimerQueue(CustomFunctionCallbackTimers<T> var0) {
      this.e = var0;
   }

   public void a(T var0, long var1) {
      while(true) {
         CustomFunctionCallbackTimerQueue.a<T> var3 = this.f.peek();
         if (var3 == null || var3.a > var1) {
            return;
         }

         this.f.remove();
         this.h.remove(var3.c, var1);
         var3.d.handle(var0, this, var1);
      }
   }

   public void a(String var0, long var1, CustomFunctionCallbackTimer<T> var3) {
      if (!this.h.contains(var0, var1)) {
         this.g = this.g.plus(UnsignedLong.ONE);
         CustomFunctionCallbackTimerQueue.a<T> var4 = new CustomFunctionCallbackTimerQueue.a<>(var1, this.g, var0, var3);
         this.h.put(var0, var1, var4);
         this.f.add(var4);
      }
   }

   public int a(String var0) {
      Collection<CustomFunctionCallbackTimerQueue.a<T>> var1 = this.h.row(var0).values();
      var1.forEach(this.f::remove);
      int var2 = var1.size();
      var1.clear();
      return var2;
   }

   public Set<String> a() {
      return Collections.unmodifiableSet(this.h.rowKeySet());
   }

   private void a(NBTTagCompound var0) {
      NBTTagCompound var1 = var0.p("Callback");
      CustomFunctionCallbackTimer<T> var2 = this.e.a(var1);
      if (var2 != null) {
         String var3 = var0.l("Name");
         long var4 = var0.i("TriggerTime");
         this.a(var3, var4, var2);
      }
   }

   private NBTTagCompound a(CustomFunctionCallbackTimerQueue.a<T> var0) {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.a("Name", var0.c);
      var1.a("TriggerTime", var0.a);
      var1.a("Callback", this.e.a(var0.d));
      return var1;
   }

   public NBTTagList b() {
      NBTTagList var0 = new NBTTagList();
      this.f.stream().sorted(c()).map(this::a).forEach(var0::add);
      return var0;
   }

   public static class a<T> {
      public final long a;
      public final UnsignedLong b;
      public final String c;
      public final CustomFunctionCallbackTimer<T> d;

      a(long var0, UnsignedLong var2, String var3, CustomFunctionCallbackTimer<T> var4) {
         this.a = var0;
         this.b = var2;
         this.c = var3;
         this.d = var4;
      }
   }
}
