package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class OptionsProgrammerArtFix extends DataFix {
   public OptionsProgrammerArtFix(Schema var0) {
      super(var0, false);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsProgrammerArtFix",
         this.getInputSchema().getType(DataConverterTypes.e),
         var0 -> var0.update(DSL.remainderFinder(), var0x -> var0x.update("resourcePacks", this::a).update("incompatibleResourcePacks", this::a))
      );
   }

   private <T> Dynamic<T> a(Dynamic<T> var0) {
      return (Dynamic<T>)var0.asString().result().map(var1x -> var0.createString(var1x.replace("\"programer_art\"", "\"programmer_art\""))).orElse((T)var0);
   }
}
