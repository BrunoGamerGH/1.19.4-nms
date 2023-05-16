package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterBedItem extends DataFix {
   public DataConverterBedItem(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<Pair<String, String>> var0 = DSL.fieldFinder("id", DSL.named(DataConverterTypes.s.typeName(), DataConverterSchemaNamed.a()));
      return this.fixTypeEverywhereTyped("BedItemColorFix", this.getInputSchema().getType(DataConverterTypes.m), var1x -> {
         Optional<Pair<String, String>> var2 = var1x.getOptional(var0);
         if (var2.isPresent() && Objects.equals(((Pair)var2.get()).getSecond(), "minecraft:bed")) {
            Dynamic<?> var3 = (Dynamic)var1x.get(DSL.remainderFinder());
            if (var3.get("Damage").asInt(0) == 0) {
               return var1x.set(DSL.remainderFinder(), var3.set("Damage", var3.createShort((short)14)));
            }
         }

         return var1x;
      });
   }
}
