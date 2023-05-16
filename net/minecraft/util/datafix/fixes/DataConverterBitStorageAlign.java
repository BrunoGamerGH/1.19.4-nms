package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.stream.LongStream;
import net.minecraft.util.MathHelper;

public class DataConverterBitStorageAlign extends DataFix {
   private static final int a = 6;
   private static final int b = 16;
   private static final int c = 16;
   private static final int d = 4096;
   private static final int e = 9;
   private static final int f = 256;

   public DataConverterBitStorageAlign(Schema var0) {
      super(var0, false);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.c);
      Type<?> var1 = var0.findFieldType("Level");
      OpticFinder<?> var2 = DSL.fieldFinder("Level", var1);
      OpticFinder<?> var3 = var2.type().findField("Sections");
      Type<?> var4 = ((ListType)var3.type()).getElement();
      OpticFinder<?> var5 = DSL.typeFinder(var4);
      Type<Pair<String, Dynamic<?>>> var6 = DSL.named(DataConverterTypes.n.typeName(), DSL.remainderType());
      OpticFinder<List<Pair<String, Dynamic<?>>>> var7 = DSL.fieldFinder("Palette", DSL.list(var6));
      return this.fixTypeEverywhereTyped(
         "BitStorageAlignFix",
         var0,
         this.getOutputSchema().getType(DataConverterTypes.c),
         var4x -> var4x.updateTyped(var2, var3xx -> this.a(a(var3, var5, var7, var3xx)))
      );
   }

   private Typed<?> a(Typed<?> var0) {
      return var0.update(
         DSL.remainderFinder(),
         var0x -> var0x.update("Heightmaps", var1x -> var1x.updateMapValues(var1xx -> var1xx.mapSecond(var1xxx -> a(var0x, var1xxx, 256, 9))))
      );
   }

   private static Typed<?> a(OpticFinder<?> var0, OpticFinder<?> var1, OpticFinder<List<Pair<String, Dynamic<?>>>> var2, Typed<?> var3) {
      return var3.updateTyped(
         var0,
         var2x -> var2x.updateTyped(
               var1,
               var1xx -> {
                  int var2xx = var1xx.getOptional(var2).map(var0xxx -> Math.max(4, DataFixUtils.ceillog2(var0xxx.size()))).orElse(0);
                  return var2xx != 0 && !MathHelper.d(var2xx)
                     ? var1xx.update(DSL.remainderFinder(), var1xxx -> var1xxx.update("BlockStates", var2xxx -> a(var1xxx, var2xxx, 4096, var2xx)))
                     : var1xx;
               }
            )
      );
   }

   private static Dynamic<?> a(Dynamic<?> var0, Dynamic<?> var1, int var2, int var3) {
      long[] var4 = var1.asLongStream().toArray();
      long[] var5 = a(var2, var3, var4);
      return var0.createLongList(LongStream.of(var5));
   }

   public static long[] a(int var0, int var1, long[] var2) {
      int var3 = var2.length;
      if (var3 == 0) {
         return var2;
      } else {
         long var4 = (1L << var1) - 1L;
         int var6 = 64 / var1;
         int var7 = (var0 + var6 - 1) / var6;
         long[] var8 = new long[var7];
         int var9 = 0;
         int var10 = 0;
         long var11 = 0L;
         int var13 = 0;
         long var14 = var2[0];
         long var16 = var3 > 1 ? var2[1] : 0L;

         for(int var18 = 0; var18 < var0; ++var18) {
            int var19 = var18 * var1;
            int var20 = var19 >> 6;
            int var21 = (var18 + 1) * var1 - 1 >> 6;
            int var22 = var19 ^ var20 << 6;
            if (var20 != var13) {
               var14 = var16;
               var16 = var20 + 1 < var3 ? var2[var20 + 1] : 0L;
               var13 = var20;
            }

            long var23;
            if (var20 == var21) {
               var23 = var14 >>> var22 & var4;
            } else {
               int var25 = 64 - var22;
               var23 = (var14 >>> var22 | var16 << var25) & var4;
            }

            int var25 = var10 + var1;
            if (var25 >= 64) {
               var8[var9++] = var11;
               var11 = var23;
               var10 = var1;
            } else {
               var11 |= var23 << var10;
               var10 = var25;
            }
         }

         if (var11 != 0L) {
            var8[var9] = var11;
         }

         return var8;
      }
   }
}
