package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

public class DataConverterIglooMetadataRemoval extends DataFix {
   public DataConverterIglooMetadataRemoval(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.v);
      return this.fixTypeEverywhereTyped("IglooMetadataRemovalFix", var0, var0x -> var0x.update(DSL.remainderFinder(), DataConverterIglooMetadataRemoval::a));
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0) {
      boolean var1 = var0.get("Children").asStreamOpt().map(var0x -> var0x.allMatch(DataConverterIglooMetadataRemoval::c)).result().orElse(false);
      return var1 ? var0.set("id", var0.createString("Igloo")).remove("Children") : var0.update("Children", DataConverterIglooMetadataRemoval::b);
   }

   private static <T> Dynamic<T> b(Dynamic<T> var0) {
      return (Dynamic<T>)var0.asStreamOpt().map(var0x -> var0x.filter(var0xx -> !c(var0xx))).map(var0::createList).result().orElse((T)var0);
   }

   private static boolean c(Dynamic<?> var0) {
      return var0.get("id").asString("").equals("Iglu");
   }
}
