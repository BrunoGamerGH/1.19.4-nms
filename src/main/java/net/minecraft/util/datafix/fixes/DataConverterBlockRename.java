package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public abstract class DataConverterBlockRename extends DataFix {
   private final String a;

   public DataConverterBlockRename(Schema var0, String var1) {
      super(var0, false);
      this.a = var1;
   }

   public TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.r);
      Type<Pair<String, String>> var1 = DSL.named(DataConverterTypes.r.typeName(), DataConverterSchemaNamed.a());
      if (!Objects.equals(var0, var1)) {
         throw new IllegalStateException("block type is not what was expected.");
      } else {
         TypeRewriteRule var2 = this.fixTypeEverywhere(this.a + " for block", var1, var0x -> var0xx -> var0xx.mapSecond(this::a));
         TypeRewriteRule var3 = this.fixTypeEverywhereTyped(
            this.a + " for block_state", this.getInputSchema().getType(DataConverterTypes.n), var0x -> var0x.update(DSL.remainderFinder(), var0xx -> {
                  Optional<String> var1x = var0xx.get("Name").asString().result();
                  return var1x.isPresent() ? var0xx.set("Name", var0xx.createString(this.a((String)var1x.get()))) : var0xx;
               })
         );
         return TypeRewriteRule.seq(var2, var3);
      }
   }

   protected abstract String a(String var1);

   public static DataFix a(Schema var0, String var1, final Function<String, String> var2) {
      return new DataConverterBlockRename(var0, var1) {
         @Override
         protected String a(String var0) {
            return var2.apply(var0);
         }
      };
   }
}
