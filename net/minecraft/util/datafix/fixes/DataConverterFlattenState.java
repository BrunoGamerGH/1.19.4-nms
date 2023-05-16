package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

public class DataConverterFlattenState extends DataFix {
   public DataConverterFlattenState(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "BlockStateStructureTemplateFix",
         this.getInputSchema().getType(DataConverterTypes.n),
         var0 -> var0.update(DSL.remainderFinder(), DataConverterFlattenData::a)
      );
   }
}
