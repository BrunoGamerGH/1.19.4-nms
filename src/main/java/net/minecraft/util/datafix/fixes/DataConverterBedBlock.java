package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DataConverterBedBlock extends DataFix {
   public DataConverterBedBlock(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      Type<?> var0 = this.getOutputSchema().getType(DataConverterTypes.c);
      Type<?> var1 = var0.findFieldType("Level");
      Type<?> var2 = var1.findFieldType("TileEntities");
      if (!(var2 instanceof ListType)) {
         throw new IllegalStateException("Tile entity type is not a list type.");
      } else {
         ListType<?> var3 = (ListType)var2;
         return this.a(var1, var3);
      }
   }

   private <TE> TypeRewriteRule a(Type<?> var0, ListType<TE> var1) {
      Type<TE> var2 = var1.getElement();
      OpticFinder<?> var3 = DSL.fieldFinder("Level", var0);
      OpticFinder<List<TE>> var4 = DSL.fieldFinder("TileEntities", var1);
      int var5 = 416;
      return TypeRewriteRule.seq(
         this.fixTypeEverywhere(
            "InjectBedBlockEntityType",
            this.getInputSchema().findChoiceType(DataConverterTypes.l),
            this.getOutputSchema().findChoiceType(DataConverterTypes.l),
            var0x -> var0xx -> var0xx
         ),
         this.fixTypeEverywhereTyped(
            "BedBlockEntityInjecter",
            this.getOutputSchema().getType(DataConverterTypes.c),
            var3x -> {
               Typed<?> var4x = var3x.getTyped(var3);
               Dynamic<?> var5x = (Dynamic)var4x.get(DSL.remainderFinder());
               int var6x = var5x.get("xPos").asInt(0);
               int var7 = var5x.get("zPos").asInt(0);
               List<TE> var8 = Lists.newArrayList((Iterable)var4x.getOrCreate(var4));
               List<? extends Dynamic<?>> var9 = var5x.get("Sections").asList(Function.identity());
      
               for(int var10 = 0; var10 < var9.size(); ++var10) {
                  Dynamic<?> var11 = (Dynamic)var9.get(var10);
                  int var12 = var11.get("Y").asInt(0);
                  Streams.mapWithIndex(var11.get("Blocks").asIntStream(), (var4xx, var5xx) -> {
                        if (416 == (var4xx & 0xFF) << 4) {
                           int var7x = (int)var5xx;
                           int var8x = var7x & 15;
                           int var9x = var7x >> 8 & 15;
                           int var10x = var7x >> 4 & 15;
                           Map<Dynamic<?>, Dynamic<?>> var11x = Maps.newHashMap();
                           var11x.put(var11.createString("id"), var11.createString("minecraft:bed"));
                           var11x.put(var11.createString("x"), var11.createInt(var8x + (var6x << 4)));
                           var11x.put(var11.createString("y"), var11.createInt(var9x + (var12 << 4)));
                           var11x.put(var11.createString("z"), var11.createInt(var10x + (var7 << 4)));
                           var11x.put(var11.createString("color"), var11.createShort((short)14));
                           return var11x;
                        } else {
                           return null;
                        }
                     })
                     .forEachOrdered(
                        var3xx -> {
                           if (var3xx != null) {
                              var8.add(
                                 (TE)((Pair)var2.read(var11.createMap(var3xx))
                                       .result()
                                       .orElseThrow(() -> new IllegalStateException("Could not parse newly created bed block entity.")))
                                    .getFirst()
                              );
                           }
                        }
                     );
               }
      
               return !var8.isEmpty() ? var3x.set(var3, var4x.set(var4, var8)) : var3x;
            }
         )
      );
   }
}
