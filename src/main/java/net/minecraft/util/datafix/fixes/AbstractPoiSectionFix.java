package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractPoiSectionFix extends DataFix {
   private final String a;

   public AbstractPoiSectionFix(Schema var0, String var1) {
      super(var0, false);
      this.a = var1;
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> var0 = DSL.named(DataConverterTypes.j.typeName(), DSL.remainderType());
      if (!Objects.equals(var0, this.getInputSchema().getType(DataConverterTypes.j))) {
         throw new IllegalStateException("Poi type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(this.a, var0, var0x -> var0xx -> var0xx.mapSecond(this::a));
      }
   }

   private <T> Dynamic<T> a(Dynamic<T> var0) {
      return var0.update("Sections", var0x -> var0x.updateMapValues(var0xx -> var0xx.mapSecond(this::b)));
   }

   private Dynamic<?> b(Dynamic<?> var0) {
      return var0.update("Records", this::c);
   }

   private <T> Dynamic<T> c(Dynamic<T> var0) {
      return (Dynamic<T>)DataFixUtils.orElse(var0.asStreamOpt().result().map(var1x -> var0.createList(this.a(var1x))), var0);
   }

   protected abstract <T> Stream<Dynamic<T>> a(Stream<Dynamic<T>> var1);
}
