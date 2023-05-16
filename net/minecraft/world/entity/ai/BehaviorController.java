package net.minecraft.world.entity.ai;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.ExpirableMemory;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;

public class BehaviorController<E extends EntityLiving> {
   static final Logger a = LogUtils.getLogger();
   private final Supplier<Codec<BehaviorController<E>>> b;
   private static final int c = 20;
   private final Map<MemoryModuleType<?>, Optional<? extends ExpirableMemory<?>>> d = Maps.newHashMap();
   private final Map<SensorType<? extends Sensor<? super E>>, Sensor<? super E>> e = Maps.newLinkedHashMap();
   private final Map<Integer, Map<Activity, Set<BehaviorControl<? super E>>>> f = Maps.newTreeMap();
   private Schedule g = Schedule.c;
   private final Map<Activity, Set<Pair<MemoryModuleType<?>, MemoryStatus>>> h = Maps.newHashMap();
   private final Map<Activity, Set<MemoryModuleType<?>>> i = Maps.newHashMap();
   private Set<Activity> j = Sets.newHashSet();
   private final Set<Activity> k = Sets.newHashSet();
   private Activity l = Activity.b;
   private long m = -9999L;

   public static <E extends EntityLiving> BehaviorController.b<E> a(
      Collection<? extends MemoryModuleType<?>> var0, Collection<? extends SensorType<? extends Sensor<? super E>>> var1
   ) {
      return new BehaviorController.b<>(var0, var1);
   }

   public static <E extends EntityLiving> Codec<BehaviorController<E>> b(
      final Collection<? extends MemoryModuleType<?>> var0, final Collection<? extends SensorType<? extends Sensor<? super E>>> var1
   ) {
      final MutableObject<Codec<BehaviorController<E>>> var2 = new MutableObject();
      var2.setValue(
         (new MapCodec<BehaviorController<E>>() {
               public <T> Stream<T> keys(DynamicOps<T> var0x) {
                  return var0.stream()
                     .flatMap(var0xx -> var0xx.a().map(var1xxx -> BuiltInRegistries.B.b(var0xx)).stream())
                     .map(var1xx -> (T)var0.createString(var1xx.toString()));
               }
      
               public <T> DataResult<BehaviorController<E>> decode(DynamicOps<T> var0x, MapLike<T> var1x) {
                  MutableObject<DataResult<Builder<BehaviorController.a<?>>>> var2 = new MutableObject(DataResult.success(ImmutableList.builder()));
                  var1.entries().forEach(var2xxx -> {
                     DataResult<MemoryModuleType<?>> var3x = BuiltInRegistries.B.q().parse(var0, var2xxx.getFirst());
                     DataResult<? extends BehaviorController.a<?>> var4x = var3x.flatMap(var2xxxxx -> this.a(var2xxxxx, var0, (T)var2xxx.getSecond()));
                     var2.setValue(((DataResult)var2.getValue()).apply2(Builder::add, var4x));
                  });
                  ImmutableList<BehaviorController.a<?>> var3 = (ImmutableList)((DataResult)var2.getValue())
                     .resultOrPartial(BehaviorController.a::error)
                     .map(Builder::build)
                     .orElseGet(ImmutableList::of);
                  return DataResult.success(new BehaviorController<>(var0, var1, var3, var2::getValue));
               }
      
               private <T, U> DataResult<BehaviorController.a<U>> a(MemoryModuleType<U> var0x, DynamicOps<T> var1x, T var2x) {
                  return ((DataResult)var0.a().map(DataResult::success).orElseGet(() -> (T)DataResult.error(() -> "No codec for memory: " + var0)))
                     .flatMap(var2xxx -> var2xxx.parse(var1, var2))
                     .map(var1xxx -> new BehaviorController.a<>(var0, Optional.of(var1xxx)));
               }
      
               public <T> RecordBuilder<T> a(BehaviorController<E> var0x, DynamicOps<T> var1x, RecordBuilder<T> var2x) {
                  var0.j().forEach(var2xxx -> var2xxx.a(var1, var2));
                  return var2;
               }
            })
            .fieldOf("memories")
            .codec()
      );
      return (Codec<BehaviorController<E>>)var2.getValue();
   }

   public BehaviorController(
      Collection<? extends MemoryModuleType<?>> var0,
      Collection<? extends SensorType<? extends Sensor<? super E>>> var1,
      ImmutableList<BehaviorController.a<?>> var2,
      Supplier<Codec<BehaviorController<E>>> var3
   ) {
      this.b = var3;

      for(MemoryModuleType<?> var5 : var0) {
         this.d.put(var5, Optional.empty());
      }

      for(SensorType<? extends Sensor<? super E>> var5 : var1) {
         this.e.put(var5, var5.a());
      }

      for(Sensor<? super E> var5 : this.e.values()) {
         for(MemoryModuleType<?> var7 : var5.a()) {
            this.d.put(var7, Optional.empty());
         }
      }

      UnmodifiableIterator var11 = var2.iterator();

      while(var11.hasNext()) {
         BehaviorController.a<?> var5 = (BehaviorController.a)var11.next();
         var5.a(this);
      }
   }

   public <T> DataResult<T> a(DynamicOps<T> var0) {
      return ((Codec)this.b.get()).encodeStart(var0, this);
   }

   Stream<BehaviorController.a<?>> j() {
      return this.d.entrySet().stream().map(var0 -> BehaviorController.a.a((MemoryModuleType<? extends ExpirableMemory<?>>)var0.getKey(), var0.getValue()));
   }

   public boolean a(MemoryModuleType<?> var0) {
      return this.a(var0, MemoryStatus.a);
   }

   public void a() {
      this.d.keySet().forEach(var0 -> this.d.put(var0, Optional.empty()));
   }

   public <U> void b(MemoryModuleType<U> var0) {
      this.a(var0, Optional.empty());
   }

   public <U> void a(MemoryModuleType<U> var0, @Nullable U var1) {
      this.a(var0, Optional.ofNullable(var1));
   }

   public <U> void a(MemoryModuleType<U> var0, U var1, long var2) {
      this.b(var0, Optional.of(ExpirableMemory.a(var1, var2)));
   }

   public <U> void a(MemoryModuleType<U> var0, Optional<? extends U> var1) {
      this.b(var0, var1.map(ExpirableMemory::a));
   }

   <U> void b(MemoryModuleType<U> var0, Optional<? extends ExpirableMemory<?>> var1) {
      if (this.d.containsKey(var0)) {
         if (var1.isPresent() && this.a(var1.get().c())) {
            this.b(var0);
         } else {
            this.d.put(var0, var1);
         }
      }
   }

   public <U> Optional<U> c(MemoryModuleType<U> var0) {
      Optional<? extends ExpirableMemory<?>> var1 = this.d.get(var0);
      if (var1 == null) {
         throw new IllegalStateException("Unregistered memory fetched: " + var0);
      } else {
         return var1.map(ExpirableMemory::c);
      }
   }

   @Nullable
   public <U> Optional<U> d(MemoryModuleType<U> var0) {
      Optional<? extends ExpirableMemory<?>> var1 = this.d.get(var0);
      return var1 == null ? null : var1.map(ExpirableMemory::c);
   }

   public <U> long e(MemoryModuleType<U> var0) {
      Optional<? extends ExpirableMemory<?>> var1 = this.d.get(var0);
      return var1.<Long>map(ExpirableMemory::b).orElse(0L);
   }

   @Deprecated
   @VisibleForDebug
   public Map<MemoryModuleType<?>, Optional<? extends ExpirableMemory<?>>> b() {
      return this.d;
   }

   public <U> boolean b(MemoryModuleType<U> var0, U var1) {
      return !this.a(var0) ? false : this.c(var0).filter(var1x -> var1x.equals(var1)).isPresent();
   }

   public boolean a(MemoryModuleType<?> var0, MemoryStatus var1) {
      Optional<? extends ExpirableMemory<?>> var2 = this.d.get(var0);
      if (var2 == null) {
         return false;
      } else {
         return var1 == MemoryStatus.c || var1 == MemoryStatus.a && var2.isPresent() || var1 == MemoryStatus.b && !var2.isPresent();
      }
   }

   public Schedule c() {
      return this.g;
   }

   public void a(Schedule var0) {
      this.g = var0;
   }

   public void a(Set<Activity> var0) {
      this.j = var0;
   }

   @Deprecated
   @VisibleForDebug
   public Set<Activity> d() {
      return this.k;
   }

   @Deprecated
   @VisibleForDebug
   public List<BehaviorControl<? super E>> e() {
      List<BehaviorControl<? super E>> var0 = new ObjectArrayList();

      for(Map<Activity, Set<BehaviorControl<? super E>>> var2 : this.f.values()) {
         for(Set<BehaviorControl<? super E>> var4 : var2.values()) {
            for(BehaviorControl<? super E> var6 : var4) {
               if (var6.a() == Behavior.Status.b) {
                  var0.add(var6);
               }
            }
         }
      }

      return var0;
   }

   public void f() {
      this.d(this.l);
   }

   public Optional<Activity> g() {
      for(Activity var1 : this.k) {
         if (!this.j.contains(var1)) {
            return Optional.of(var1);
         }
      }

      return Optional.empty();
   }

   public void a(Activity var0) {
      if (this.f(var0)) {
         this.d(var0);
      } else {
         this.f();
      }
   }

   private void d(Activity var0) {
      if (!this.c(var0)) {
         this.e(var0);
         this.k.clear();
         this.k.addAll(this.j);
         this.k.add(var0);
      }
   }

   private void e(Activity var0) {
      for(Activity var2 : this.k) {
         if (var2 != var0) {
            Set<MemoryModuleType<?>> var3 = this.i.get(var2);
            if (var3 != null) {
               for(MemoryModuleType<?> var5 : var3) {
                  this.b(var5);
               }
            }
         }
      }
   }

   public void a(long var0, long var2) {
      if (var2 - this.m > 20L) {
         this.m = var2;
         Activity var4 = this.c().a((int)(var0 % 24000L));
         if (!this.k.contains(var4)) {
            this.a(var4);
         }
      }
   }

   public void a(List<Activity> var0) {
      for(Activity var2 : var0) {
         if (this.f(var2)) {
            this.d(var2);
            break;
         }
      }
   }

   public void b(Activity var0) {
      this.l = var0;
   }

   public void a(Activity var0, int var1, ImmutableList<? extends BehaviorControl<? super E>> var2) {
      this.a(var0, this.a(var1, var2));
   }

   public void a(Activity var0, int var1, ImmutableList<? extends BehaviorControl<? super E>> var2, MemoryModuleType<?> var3) {
      Set<Pair<MemoryModuleType<?>, MemoryStatus>> var4 = ImmutableSet.of(Pair.of(var3, MemoryStatus.a));
      Set<MemoryModuleType<?>> var5 = ImmutableSet.of(var3);
      this.a(var0, this.a(var1, var2), var4, var5);
   }

   public void a(Activity var0, ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super E>>> var1) {
      this.a(var0, var1, ImmutableSet.of(), Sets.newHashSet());
   }

   public void a(
      Activity var0, ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super E>>> var1, Set<Pair<MemoryModuleType<?>, MemoryStatus>> var2
   ) {
      this.a(var0, var1, var2, Sets.newHashSet());
   }

   public void a(
      Activity var0,
      ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super E>>> var1,
      Set<Pair<MemoryModuleType<?>, MemoryStatus>> var2,
      Set<MemoryModuleType<?>> var3
   ) {
      this.h.put(var0, var2);
      if (!var3.isEmpty()) {
         this.i.put(var0, var3);
      }

      UnmodifiableIterator var5 = var1.iterator();

      while(var5.hasNext()) {
         Pair<Integer, ? extends BehaviorControl<? super E>> var5x = (Pair)var5.next();
         this.f
            .computeIfAbsent((Integer)var5x.getFirst(), var0x -> Maps.newHashMap())
            .computeIfAbsent(var0, var0x -> Sets.newLinkedHashSet())
            .add((BehaviorControl<? super E>)var5x.getSecond());
      }
   }

   @VisibleForTesting
   public void h() {
      this.f.clear();
   }

   public boolean c(Activity var0) {
      return this.k.contains(var0);
   }

   public BehaviorController<E> i() {
      BehaviorController<E> var0 = new BehaviorController<>(this.d.keySet(), this.e.keySet(), ImmutableList.of(), this.b);

      for(Entry<MemoryModuleType<?>, Optional<? extends ExpirableMemory<?>>> var2 : this.d.entrySet()) {
         MemoryModuleType<?> var3 = var2.getKey();
         if (var2.getValue().isPresent()) {
            var0.d.put(var3, var2.getValue());
         }
      }

      return var0;
   }

   public void a(WorldServer var0, E var1) {
      this.k();
      this.c(var0, var1);
      this.d(var0, var1);
      this.e(var0, var1);
   }

   private void c(WorldServer var0, E var1) {
      for(Sensor<? super E> var3 : this.e.values()) {
         var3.b(var0, var1);
      }
   }

   private void k() {
      for(Entry<MemoryModuleType<?>, Optional<? extends ExpirableMemory<?>>> var1 : this.d.entrySet()) {
         if (var1.getValue().isPresent()) {
            ExpirableMemory<?> var2 = var1.getValue().get();
            if (var2.d()) {
               this.b(var1.getKey());
            }

            var2.a();
         }
      }
   }

   public void b(WorldServer var0, E var1) {
      long var2 = var1.H.U();

      for(BehaviorControl<? super E> var5 : this.e()) {
         var5.g(var0, var1, var2);
      }
   }

   private void d(WorldServer var0, E var1) {
      long var2 = var0.U();

      for(Map<Activity, Set<BehaviorControl<? super E>>> var5 : this.f.values()) {
         for(Entry<Activity, Set<BehaviorControl<? super E>>> var7 : var5.entrySet()) {
            Activity var8 = var7.getKey();
            if (this.k.contains(var8)) {
               for(BehaviorControl<? super E> var11 : var7.getValue()) {
                  if (var11.a() == Behavior.Status.a) {
                     var11.e(var0, var1, var2);
                  }
               }
            }
         }
      }
   }

   private void e(WorldServer var0, E var1) {
      long var2 = var0.U();

      for(BehaviorControl<? super E> var5 : this.e()) {
         var5.f(var0, var1, var2);
      }
   }

   private boolean f(Activity var0) {
      if (!this.h.containsKey(var0)) {
         return false;
      } else {
         for(Pair<MemoryModuleType<?>, MemoryStatus> var2 : this.h.get(var0)) {
            MemoryModuleType<?> var3 = (MemoryModuleType)var2.getFirst();
            MemoryStatus var4 = (MemoryStatus)var2.getSecond();
            if (!this.a(var3, var4)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean a(Object var0) {
      return var0 instanceof Collection && ((Collection)var0).isEmpty();
   }

   ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super E>>> a(int var0, ImmutableList<? extends BehaviorControl<? super E>> var1) {
      int var2 = var0;
      Builder<Pair<Integer, ? extends BehaviorControl<? super E>>> var3 = ImmutableList.builder();
      UnmodifiableIterator var5 = var1.iterator();

      while(var5.hasNext()) {
         BehaviorControl<? super E> var5x = (BehaviorControl)var5.next();
         var3.add(Pair.of(var2++, var5x));
      }

      return var3.build();
   }

   static final class a<U> {
      private final MemoryModuleType<U> a;
      private final Optional<? extends ExpirableMemory<U>> b;

      static <U> BehaviorController.a<U> a(MemoryModuleType<U> var0, Optional<? extends ExpirableMemory<?>> var1) {
         return new BehaviorController.a<>(var0, var1);
      }

      a(MemoryModuleType<U> var0, Optional<? extends ExpirableMemory<U>> var1) {
         this.a = var0;
         this.b = var1;
      }

      void a(BehaviorController<?> var0) {
         var0.b(this.a, this.b);
      }

      public <T> void a(DynamicOps<T> var0, RecordBuilder<T> var1) {
         this.a.a().ifPresent(var2x -> this.b.ifPresent(var3x -> var1.add(BuiltInRegistries.B.q().encodeStart(var0, this.a), var2x.encodeStart(var0, var3x))));
      }
   }

   public static final class b<E extends EntityLiving> {
      private final Collection<? extends MemoryModuleType<?>> a;
      private final Collection<? extends SensorType<? extends Sensor<? super E>>> b;
      private final Codec<BehaviorController<E>> c;

      b(Collection<? extends MemoryModuleType<?>> var0, Collection<? extends SensorType<? extends Sensor<? super E>>> var1) {
         this.a = var0;
         this.b = var1;
         this.c = BehaviorController.b(var0, var1);
      }

      public BehaviorController<E> a(Dynamic<?> var0) {
         return this.c
            .parse(var0)
            .resultOrPartial(BehaviorController.a::error)
            .orElseGet(() -> new BehaviorController<>(this.a, this.b, ImmutableList.of(), () -> this.c));
      }
   }
}
