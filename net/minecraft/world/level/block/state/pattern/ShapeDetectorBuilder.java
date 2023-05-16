package net.minecraft.world.level.block.state.pattern;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class ShapeDetectorBuilder {
   private static final Joiner a = Joiner.on(",");
   private final List<String[]> b = Lists.newArrayList();
   private final Map<Character, Predicate<ShapeDetectorBlock>> c = Maps.newHashMap();
   private int d;
   private int e;

   private ShapeDetectorBuilder() {
      this.c.put(' ', var0 -> true);
   }

   public ShapeDetectorBuilder a(String... var0) {
      if (!ArrayUtils.isEmpty(var0) && !StringUtils.isEmpty(var0[0])) {
         if (this.b.isEmpty()) {
            this.d = var0.length;
            this.e = var0[0].length();
         }

         if (var0.length != this.d) {
            throw new IllegalArgumentException("Expected aisle with height of " + this.d + ", but was given one with a height of " + var0.length + ")");
         } else {
            for(String var4 : var0) {
               if (var4.length() != this.e) {
                  throw new IllegalArgumentException(
                     "Not all rows in the given aisle are the correct width (expected " + this.e + ", found one with " + var4.length() + ")"
                  );
               }

               for(char var8 : var4.toCharArray()) {
                  if (!this.c.containsKey(var8)) {
                     this.c.put(var8, null);
                  }
               }
            }

            this.b.add(var0);
            return this;
         }
      } else {
         throw new IllegalArgumentException("Empty pattern for aisle");
      }
   }

   public static ShapeDetectorBuilder a() {
      return new ShapeDetectorBuilder();
   }

   public ShapeDetectorBuilder a(char var0, Predicate<ShapeDetectorBlock> var1) {
      this.c.put(var0, var1);
      return this;
   }

   public ShapeDetector b() {
      return new ShapeDetector(this.c());
   }

   private Predicate<ShapeDetectorBlock>[][][] c() {
      this.d();
      Predicate<ShapeDetectorBlock>[][][] var0 = (Predicate[][][])Array.newInstance(Predicate.class, this.b.size(), this.d, this.e);

      for(int var1 = 0; var1 < this.b.size(); ++var1) {
         for(int var2 = 0; var2 < this.d; ++var2) {
            for(int var3 = 0; var3 < this.e; ++var3) {
               var0[var1][var2][var3] = this.c.get(this.b.get(var1)[var2].charAt(var3));
            }
         }
      }

      return var0;
   }

   private void d() {
      List<Character> var0 = Lists.newArrayList();

      for(Entry<Character, Predicate<ShapeDetectorBlock>> var2 : this.c.entrySet()) {
         if (var2.getValue() == null) {
            var0.add(var2.getKey());
         }
      }

      if (!var0.isEmpty()) {
         throw new IllegalStateException("Predicates for character(s) " + a.join(var0) + " are missing");
      }
   }
}
