package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import com.mojang.serialization.OptionalDynamic;
import java.util.List;

public class DataConverterDropChances extends DataFix {
   private static final Codec<List<Float>> a = Codec.FLOAT.listOf();

   public DataConverterDropChances(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "EntityRedundantChanceTagsFix", this.getInputSchema().getType(DataConverterTypes.q), var0 -> var0.update(DSL.remainderFinder(), var0x -> {
               if (a(var0x.get("HandDropChances"), 2)) {
                  var0x = var0x.remove("HandDropChances");
               }
   
               if (a(var0x.get("ArmorDropChances"), 4)) {
                  var0x = var0x.remove("ArmorDropChances");
               }
   
               return var0x;
            })
      );
   }

   private static boolean a(OptionalDynamic<?> var0, int var1) {
      return var0.flatMap(a::parse).map(var1x -> var1x.size() == var1 && var1x.stream().allMatch(var0xx -> var0xx == 0.0F)).result().orElse(false);
   }
}
