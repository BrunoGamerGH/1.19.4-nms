package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

public class DataConverterLeavesBiome extends DataFix {
   public DataConverterLeavesBiome(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.c);
      OpticFinder<?> var1 = var0.findField("Level");
      return this.fixTypeEverywhereTyped("Leaves fix", var0, var1x -> var1x.updateTyped(var1, var0xx -> var0xx.update(DSL.remainderFinder(), var0xxx -> {
               Optional<IntStream> var1xx = var0xxx.get("Biomes").asIntStreamOpt().result();
               if (var1xx.isEmpty()) {
                  return var0xxx;
               } else {
                  int[] var2x = ((IntStream)var1xx.get()).toArray();
                  if (var2x.length != 256) {
                     return var0xxx;
                  } else {
                     int[] var3 = new int[1024];

                     for(int var4 = 0; var4 < 4; ++var4) {
                        for(int var5 = 0; var5 < 4; ++var5) {
                           int var6 = (var5 << 2) + 2;
                           int var7 = (var4 << 2) + 2;
                           int var8 = var7 << 4 | var6;
                           var3[var4 << 2 | var5] = var2x[var8];
                        }
                     }

                     for(int var4 = 1; var4 < 64; ++var4) {
                        System.arraycopy(var3, 0, var3, var4 * 16, 16);
                     }

                     return var0xxx.set("Biomes", var0xxx.createIntList(Arrays.stream(var3)));
                  }
               }
            })));
   }
}
