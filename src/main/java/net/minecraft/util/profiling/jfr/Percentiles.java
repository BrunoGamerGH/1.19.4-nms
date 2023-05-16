package net.minecraft.util.profiling.jfr;

import com.google.common.math.Quantiles;
import com.google.common.math.Quantiles.ScaleAndIndexes;
import it.unimi.dsi.fastutil.ints.Int2DoubleRBTreeMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleSortedMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleSortedMaps;
import java.util.Comparator;
import java.util.Map;
import net.minecraft.SystemUtils;

public class Percentiles {
   public static final ScaleAndIndexes a = Quantiles.scale(100).indexes(new int[]{50, 75, 90, 99});

   private Percentiles() {
   }

   public static Map<Integer, Double> a(long[] var0) {
      return var0.length == 0 ? Map.of() : a(a.compute(var0));
   }

   public static Map<Integer, Double> a(double[] var0) {
      return var0.length == 0 ? Map.of() : a(a.compute(var0));
   }

   private static Map<Integer, Double> a(Map<Integer, Double> var0) {
      Int2DoubleSortedMap var1 = SystemUtils.a(new Int2DoubleRBTreeMap(Comparator.reverseOrder()), var1x -> var1x.putAll(var0));
      return Int2DoubleSortedMaps.unmodifiable(var1);
   }
}
