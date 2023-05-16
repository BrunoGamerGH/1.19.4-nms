package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Optional;
import java.util.UUID;

public class DataConverterUUID extends DataFix {
   public DataConverterUUID(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "EntityStringUuidFix",
         this.getInputSchema().getType(DataConverterTypes.q),
         var0 -> var0.update(
               DSL.remainderFinder(),
               var0x -> {
                  Optional<String> var1 = var0x.get("UUID").asString().result();
                  if (var1.isPresent()) {
                     UUID var2 = UUID.fromString(var1.get());
                     return var0x.remove("UUID")
                        .set("UUIDMost", var0x.createLong(var2.getMostSignificantBits()))
                        .set("UUIDLeast", var0x.createLong(var2.getLeastSignificantBits()));
                  } else {
                     return var0x;
                  }
               }
            )
      );
   }
}
