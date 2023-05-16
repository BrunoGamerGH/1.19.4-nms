package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterItemStackUUID extends DataConverterUUIDBase {
   public DataConverterItemStackUUID(Schema var0) {
      super(var0, DataConverterTypes.m);
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<Pair<String, String>> var0 = DSL.fieldFinder("id", DSL.named(DataConverterTypes.s.typeName(), DataConverterSchemaNamed.a()));
      return this.fixTypeEverywhereTyped("ItemStackUUIDFix", this.getInputSchema().getType(this.a), var1x -> {
         OpticFinder<?> var2 = var1x.getType().findField("tag");
         return var1x.updateTyped(var2, var2x -> var2x.update(DSL.remainderFinder(), var2xx -> {
               var2xx = this.b(var2xx);
               if (var1x.getOptional(var0).map(var0xxxx -> "minecraft:player_head".equals(var0xxxx.getSecond())).orElse(false)) {
                  var2xx = this.c(var2xx);
               }

               return var2xx;
            }));
      });
   }

   private Dynamic<?> b(Dynamic<?> var0) {
      return var0.update("AttributeModifiers", var1x -> var0.createList(var1x.asStream().map(var0xx -> (Dynamic)c(var0xx, "UUID", "UUID").orElse(var0xx))));
   }

   private Dynamic<?> c(Dynamic<?> var0) {
      return var0.update("SkullOwner", var0x -> (Dynamic)a(var0x, "Id", "Id").orElse(var0x));
   }
}
