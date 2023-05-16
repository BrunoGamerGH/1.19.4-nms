package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterBlockName extends DataFix {
   public DataConverterBlockName(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.r);
      Type<?> var1 = this.getOutputSchema().getType(DataConverterTypes.r);
      Type<Pair<String, Either<Integer, String>>> var2 = DSL.named(DataConverterTypes.r.typeName(), DSL.or(DSL.intType(), DataConverterSchemaNamed.a()));
      Type<Pair<String, String>> var3 = DSL.named(DataConverterTypes.r.typeName(), DataConverterSchemaNamed.a());
      if (Objects.equals(var0, var2) && Objects.equals(var1, var3)) {
         return this.fixTypeEverywhere(
            "BlockNameFlatteningFix",
            var2,
            var3,
            var0x -> var0xx -> var0xx.mapSecond(
                     var0xxx -> (String)var0xxx.map(DataConverterFlattenData::a, var0xxxx -> DataConverterFlattenData.a(DataConverterSchemaNamed.a(var0xxxx)))
                  )
         );
      } else {
         throw new IllegalStateException("Expected and actual types don't match.");
      }
   }
}
