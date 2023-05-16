package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class DataConverterEntityCatSplit extends DataConverterEntityNameAbstract {
   public DataConverterEntityCatSplit(Schema var0, boolean var1) {
      super("EntityCatSplitFix", var0, var1);
   }

   @Override
   protected Pair<String, Dynamic<?>> a(String var0, Dynamic<?> var1) {
      if (Objects.equals("minecraft:ocelot", var0)) {
         int var2 = var1.get("CatType").asInt(0);
         if (var2 == 0) {
            String var3 = var1.get("Owner").asString("");
            String var4 = var1.get("OwnerUUID").asString("");
            if (var3.length() > 0 || var4.length() > 0) {
               var1.set("Trusting", var1.createBoolean(true));
            }
         } else if (var2 > 0 && var2 < 4) {
            var1 = var1.set("CatType", var1.createInt(var2));
            var1 = var1.set("OwnerUUID", var1.createString(var1.get("OwnerUUID").asString("")));
            return Pair.of("minecraft:cat", var1);
         }
      }

      return Pair.of(var0, var1);
   }
}
