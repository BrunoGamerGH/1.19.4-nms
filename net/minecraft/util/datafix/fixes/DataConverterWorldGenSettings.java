package net.minecraft.util.datafix.fixes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.math.NumberUtils;

public class DataConverterWorldGenSettings extends DataFix {
   private static final String b = "generatorOptions";
   @VisibleForTesting
   static final String a = "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
   private static final Splitter c = Splitter.on(';').limit(5);
   private static final Splitter d = Splitter.on(',');
   private static final Splitter e = Splitter.on('x').limit(2);
   private static final Splitter f = Splitter.on('*').limit(2);
   private static final Splitter g = Splitter.on(':').limit(3);

   public DataConverterWorldGenSettings(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "LevelFlatGeneratorInfoFix", this.getInputSchema().getType(DataConverterTypes.a), var0 -> var0.update(DSL.remainderFinder(), this::a)
      );
   }

   private Dynamic<?> a(Dynamic<?> var0) {
      return var0.get("generatorName").asString("").equalsIgnoreCase("flat")
         ? var0.update("generatorOptions", var0x -> (Dynamic)DataFixUtils.orElse(var0x.asString().map(this::a).map(var0x::createString).result(), var0x))
         : var0;
   }

   @VisibleForTesting
   String a(String var0) {
      if (var0.isEmpty()) {
         return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
      } else {
         Iterator<String> var1 = c.split(var0).iterator();
         String var2 = var1.next();
         int var3;
         String var4;
         if (var1.hasNext()) {
            var3 = NumberUtils.toInt(var2, 0);
            var4 = var1.next();
         } else {
            var3 = 0;
            var4 = var2;
         }

         if (var3 >= 0 && var3 <= 3) {
            StringBuilder var5 = new StringBuilder();
            Splitter var6 = var3 < 3 ? e : f;
            var5.append(StreamSupport.<String>stream(d.split(var4).spliterator(), false).map(var2x -> {
               List<String> var5x = var6.splitToList(var2x);
               int var3x;
               String var4x;
               if (var5x.size() == 2) {
                  var3x = NumberUtils.toInt((String)var5x.get(0));
                  var4x = (String)var5x.get(1);
               } else {
                  var3x = 1;
                  var4x = (String)var5x.get(0);
               }

               List<String> var6x = g.splitToList(var4x);
               int var7x = ((String)var6x.get(0)).equals("minecraft") ? 1 : 0;
               String var8 = (String)var6x.get(var7x);
               int var9 = var3 == 3 ? DataConverterEntityBlockState.a("minecraft:" + var8) : NumberUtils.toInt(var8, 0);
               int var10 = var7x + 1;
               int var11 = var6x.size() > var10 ? NumberUtils.toInt((String)var6x.get(var10), 0) : 0;
               return (var3x == 1 ? "" : var3x + "*") + DataConverterFlattenData.b(var9 << 4 | var11).get("Name").asString("");
            }).collect(Collectors.joining(",")));

            while(var1.hasNext()) {
               var5.append(';').append(var1.next());
            }

            return var5.toString();
         } else {
            return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
         }
      }
   }
}
