package net.minecraft.world.entity.ai.gossip;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.UUIDUtil;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.VisibleForDebug;
import org.slf4j.Logger;

public class Reputation {
   private static final Logger b = LogUtils.getLogger();
   public static final int a = 2;
   private final Map<UUID, Reputation.a> c = Maps.newHashMap();

   @VisibleForDebug
   public Map<UUID, Object2IntMap<ReputationType>> a() {
      Map<UUID, Object2IntMap<ReputationType>> var0 = Maps.newHashMap();
      this.c.keySet().forEach(var1x -> {
         Reputation.a var2 = this.c.get(var1x);
         var0.put(var1x, var2.a);
      });
      return var0;
   }

   public void b() {
      Iterator<Reputation.a> var0 = this.c.values().iterator();

      while(var0.hasNext()) {
         Reputation.a var1 = var0.next();
         var1.a();
         if (var1.b()) {
            var0.remove();
         }
      }
   }

   private Stream<Reputation.b> c() {
      return this.c.entrySet().stream().flatMap(var0 -> var0.getValue().a(var0.getKey()));
   }

   private Collection<Reputation.b> a(RandomSource var0, int var1) {
      List<Reputation.b> var2 = this.c().toList();
      if (var2.isEmpty()) {
         return Collections.emptyList();
      } else {
         int[] var3 = new int[var2.size()];
         int var4 = 0;

         for(int var5 = 0; var5 < var2.size(); ++var5) {
            Reputation.b var6 = var2.get(var5);
            var4 += Math.abs(var6.a());
            var3[var5] = var4 - 1;
         }

         Set<Reputation.b> var5 = Sets.newIdentityHashSet();

         for(int var6 = 0; var6 < var1; ++var6) {
            int var7 = var0.a(var4);
            int var8 = Arrays.binarySearch(var3, var7);
            var5.add(var2.get(var8 < 0 ? -var8 - 1 : var8));
         }

         return var5;
      }
   }

   private Reputation.a a(UUID var0) {
      return this.c.computeIfAbsent(var0, var0x -> new Reputation.a());
   }

   public void a(Reputation var0, RandomSource var1, int var2) {
      Collection<Reputation.b> var3 = var0.a(var1, var2);
      var3.forEach(var0x -> {
         int var1x = var0x.e - var0x.d.m;
         if (var1x >= 2) {
            this.a(var0x.c).a.mergeInt(var0x.d, var1x, Reputation::a);
         }
      });
   }

   public int a(UUID var0, Predicate<ReputationType> var1) {
      Reputation.a var2 = this.c.get(var0);
      return var2 != null ? var2.a(var1) : 0;
   }

   public long a(ReputationType var0, DoublePredicate var1) {
      return this.c.values().stream().filter(var2x -> var1.test((double)(var2x.a.getOrDefault(var0, 0) * var0.j))).count();
   }

   public void a(UUID var0, ReputationType var1, int var2) {
      Reputation.a var3 = this.a(var0);
      var3.a.mergeInt(var1, var2, (var1x, var2x) -> this.a(var1, var1x, var2x));
      var3.a(var1);
      if (var3.b()) {
         this.c.remove(var0);
      }
   }

   public void b(UUID var0, ReputationType var1, int var2) {
      this.a(var0, var1, -var2);
   }

   public void a(UUID var0, ReputationType var1) {
      Reputation.a var2 = this.c.get(var0);
      if (var2 != null) {
         var2.b(var1);
         if (var2.b()) {
            this.c.remove(var0);
         }
      }
   }

   public void a(ReputationType var0) {
      Iterator<Reputation.a> var1 = this.c.values().iterator();

      while(var1.hasNext()) {
         Reputation.a var2 = var1.next();
         var2.b(var0);
         if (var2.b()) {
            var1.remove();
         }
      }
   }

   public <T> T a(DynamicOps<T> var0) {
      return (T)Reputation.b.b
         .encodeStart(var0, this.c().toList())
         .resultOrPartial(var0x -> b.warn("Failed to serialize gossips: {}", var0x))
         .orElseGet(var0::emptyList);
   }

   public void a(Dynamic<?> var0) {
      Reputation.b.b
         .decode(var0)
         .resultOrPartial(var0x -> b.warn("Failed to deserialize gossips: {}", var0x))
         .stream()
         .flatMap(var0x -> ((List)var0x.getFirst()).stream())
         .forEach(var0x -> this.a(var0x.c).a.put(var0x.d, var0x.e));
   }

   private static int a(int var0, int var1) {
      return Math.max(var0, var1);
   }

   private int a(ReputationType var0, int var1, int var2) {
      int var3 = var1 + var2;
      return var3 > var0.k ? Math.max(var0.k, var1) : var3;
   }

   static class a {
      final Object2IntMap<ReputationType> a = new Object2IntOpenHashMap();

      public int a(Predicate<ReputationType> var0) {
         return this.a
            .object2IntEntrySet()
            .stream()
            .filter(var1x -> var0.test((ReputationType)var1x.getKey()))
            .mapToInt(var0x -> var0x.getIntValue() * ((ReputationType)var0x.getKey()).j)
            .sum();
      }

      public Stream<Reputation.b> a(UUID var0) {
         return this.a.object2IntEntrySet().stream().map(var1x -> new Reputation.b(var0, (ReputationType)var1x.getKey(), var1x.getIntValue()));
      }

      public void a() {
         ObjectIterator<Entry<ReputationType>> var0 = this.a.object2IntEntrySet().iterator();

         while(var0.hasNext()) {
            Entry<ReputationType> var1 = (Entry)var0.next();
            int var2 = var1.getIntValue() - ((ReputationType)var1.getKey()).l;
            if (var2 < 2) {
               var0.remove();
            } else {
               var1.setValue(var2);
            }
         }
      }

      public boolean b() {
         return this.a.isEmpty();
      }

      public void a(ReputationType var0) {
         int var1 = this.a.getInt(var0);
         if (var1 > var0.k) {
            this.a.put(var0, var0.k);
         }

         if (var1 < 2) {
            this.b(var0);
         }
      }

      public void b(ReputationType var0) {
         this.a.removeInt(var0);
      }
   }

   static record b(UUID target, ReputationType type, int value) {
      final UUID c;
      final ReputationType d;
      final int e;
      public static final Codec<Reputation.b> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  UUIDUtil.a.fieldOf("Target").forGetter(Reputation.b::b),
                  ReputationType.n.fieldOf("Type").forGetter(Reputation.b::c),
                  ExtraCodecs.i.fieldOf("Value").forGetter(Reputation.b::d)
               )
               .apply(var0, Reputation.b::new)
      );
      public static final Codec<List<Reputation.b>> b = a.listOf();

      b(UUID var0, ReputationType var1, int var2) {
         this.c = var0;
         this.d = var1;
         this.e = var2;
      }

      public int a() {
         return this.e * this.d.j;
      }

      public UUID b() {
         return this.c;
      }

      public ReputationType c() {
         return this.d;
      }

      public int d() {
         return this.e;
      }
   }
}
