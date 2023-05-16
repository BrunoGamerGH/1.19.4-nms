package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableList.Builder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import net.minecraft.SystemUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.Graph;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.apache.commons.lang3.mutable.MutableInt;

public class FeatureSorter {
   public static <T> List<FeatureSorter.b> a(List<T> var0, Function<T, List<HolderSet<PlacedFeature>>> var1, boolean var2) {
      Object2IntMap<PlacedFeature> var3 = new Object2IntOpenHashMap();
      MutableInt var4 = new MutableInt(0);

      record a(int featureIndex, int step, PlacedFeature feature) {
         private final int a;
         private final int b;
         private final PlacedFeature c;

         a(int var0, int var1, PlacedFeature var2) {
            this.a = var0;
            this.b = var1;
            this.c = var2;
         }
      }

      Comparator<a> var5 = Comparator.comparingInt(a::b).thenComparingInt(a::a);
      Map<a, Set<a>> var6 = new TreeMap<>(var5);
      int var7 = 0;

      for(T var9 : var0) {
         List<a> var10 = Lists.newArrayList();
         List<HolderSet<PlacedFeature>> var11 = var1.apply(var9);
         var7 = Math.max(var7, var11.size());

         for(int var12 = 0; var12 < var11.size(); ++var12) {
            for(Holder<PlacedFeature> var14 : var11.get(var12)) {
               PlacedFeature var15 = var14.a();
               var10.add(new a(var3.computeIfAbsent(var15, var1x -> var4.getAndIncrement()), var12, var15));
            }
         }

         for(int var12 = 0; var12 < var10.size(); ++var12) {
            Set<a> var13 = var6.computeIfAbsent(var10.get(var12), var1x -> new TreeSet<>(var5));
            if (var12 < var10.size() - 1) {
               var13.add(var10.get(var12 + 1));
            }
         }
      }

      Set<a> var8 = new TreeSet<>(var5);
      Set<a> var9 = new TreeSet<>(var5);
      List<a> var10 = Lists.newArrayList();

      for(a var12 : var6.keySet()) {
         if (!var9.isEmpty()) {
            throw new IllegalStateException("You somehow broke the universe; DFS bork (iteration finished with non-empty in-progress vertex set");
         }

         if (!var8.contains(var12) && Graph.a(var6, var8, var9, var10::add, var12)) {
            if (!var2) {
               throw new IllegalStateException("Feature order cycle found");
            }

            List<T> var13 = new ArrayList<>(var0);

            int var14;
            do {
               var14 = var13.size();
               ListIterator<T> var15 = var13.listIterator();

               while(var15.hasNext()) {
                  T var16 = var15.next();
                  var15.remove();

                  try {
                     a(var13, var1, false);
                  } catch (IllegalStateException var18) {
                     continue;
                  }

                  var15.add(var16);
               }
            } while(var14 != var13.size());

            throw new IllegalStateException("Feature order cycle found, involved sources: " + var13);
         }
      }

      Collections.reverse(var10);
      Builder<FeatureSorter.b> var11 = ImmutableList.builder();

      for(int var12 = 0; var12 < var7; ++var12) {
         int var13 = var12;
         List<PlacedFeature> var14 = var10.stream().filter(var1x -> var1x.b() == var13).map(a::c).collect(Collectors.toList());
         var11.add(new FeatureSorter.b(var14));
      }

      return var11.build();
   }

   public static record b(List<PlacedFeature> features, ToIntFunction<PlacedFeature> indexMapping) {
      private final List<PlacedFeature> a;
      private final ToIntFunction<PlacedFeature> b;

      b(List<PlacedFeature> var0) {
         this(var0, SystemUtils.a(var0, var0x -> new Object2IntOpenCustomHashMap(var0x, SystemUtils.k())));
      }

      public b(List<PlacedFeature> var0, ToIntFunction<PlacedFeature> var1) {
         this.a = var0;
         this.b = var1;
      }
   }
}
