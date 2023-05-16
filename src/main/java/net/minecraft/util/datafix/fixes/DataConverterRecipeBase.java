package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterRecipeBase extends DataFix {
   private final String a;
   private final Function<String, String> b;

   public DataConverterRecipeBase(Schema var0, boolean var1, String var2, Function<String, String> var3) {
      super(var0, var1);
      this.a = var2;
      this.b = var3;
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, String>> var0 = DSL.named(DataConverterTypes.y.typeName(), DataConverterSchemaNamed.a());
      if (!Objects.equals(var0, this.getInputSchema().getType(DataConverterTypes.y))) {
         throw new IllegalStateException("Recipe type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(this.a, var0, var0x -> var0xx -> var0xx.mapSecond(this.b));
      }
   }
}
