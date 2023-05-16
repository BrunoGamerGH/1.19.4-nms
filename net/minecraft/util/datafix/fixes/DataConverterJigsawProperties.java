package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterJigsawProperties extends DataConverterNamedEntity {
   public DataConverterJigsawProperties(Schema var0, boolean var1) {
      super(var0, var1, "JigsawPropertiesFix", DataConverterTypes.l, "minecraft:jigsaw");
   }

   private static Dynamic<?> a(Dynamic<?> var0) {
      String var1 = var0.get("attachement_type").asString("minecraft:empty");
      String var2 = var0.get("target_pool").asString("minecraft:empty");
      return var0.set("name", var0.createString(var1))
         .set("target", var0.createString(var1))
         .remove("attachement_type")
         .set("pool", var0.createString(var2))
         .remove("target_pool");
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), DataConverterJigsawProperties::a);
   }
}
