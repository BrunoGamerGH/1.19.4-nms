package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterPlayerUUID extends DataConverterUUIDBase {
   public DataConverterPlayerUUID(Schema var0) {
      super(var0, DataConverterTypes.b);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "PlayerUUIDFix",
         this.getInputSchema().getType(this.a),
         var0 -> {
            OpticFinder<?> var1 = var0.getType().findField("RootVehicle");
            return var0.updateTyped(
                  var1, var1.type(), var0x -> var0x.update(DSL.remainderFinder(), var0xx -> (Dynamic)c(var0xx, "Attach", "Attach").orElse(var0xx))
               )
               .update(DSL.remainderFinder(), var0x -> DataConverterEntityUUID.c(DataConverterEntityUUID.b(var0x)));
         }
      );
   }
}
