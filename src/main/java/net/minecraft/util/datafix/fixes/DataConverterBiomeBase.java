package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.Objects;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterBiomeBase extends DataFix {
   private final String a;
   private final Map<String, String> b;

   public DataConverterBiomeBase(Schema var0, boolean var1, String var2, Map<String, String> var3) {
      super(var0, var1);
      this.b = var3;
      this.a = var2;
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, String>> var0 = DSL.named(DataConverterTypes.z.typeName(), DataConverterSchemaNamed.a());
      if (!Objects.equals(var0, this.getInputSchema().getType(DataConverterTypes.z))) {
         throw new IllegalStateException("Biome type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(this.a, var0, var0x -> var0xx -> var0xx.mapSecond(var0xxx -> this.b.getOrDefault(var0xxx, var0xxx)));
      }
   }
}
