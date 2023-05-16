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

public abstract class DataConverterItemName extends DataFix {
   private final String a;

   public DataConverterItemName(Schema var0, String var1) {
      super(var0, false);
      this.a = var1;
   }

   public TypeRewriteRule makeRule() {
      Type<Pair<String, String>> var0 = DSL.named(DataConverterTypes.s.typeName(), DataConverterSchemaNamed.a());
      if (!Objects.equals(this.getInputSchema().getType(DataConverterTypes.s), var0)) {
         throw new IllegalStateException("item name type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(this.a, var0, var0x -> var0xx -> var0xx.mapSecond(this::a));
      }
   }

   protected abstract String a(String var1);

   public static DataFix a(Schema var0, String var1, final Function<String, String> var2) {
      return new DataConverterItemName(var0, var1) {
         @Override
         protected String a(String var0) {
            return var2.apply(var0);
         }
      };
   }
}
