package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.Function;
import java.util.stream.Stream;

public class PoiTypeRenameFix extends AbstractPoiSectionFix {
   private final Function<String, String> a;

   public PoiTypeRenameFix(Schema var0, String var1, Function<String, String> var2) {
      super(var0, var1);
      this.a = var2;
   }

   @Override
   protected <T> Stream<Dynamic<T>> a(Stream<Dynamic<T>> var0) {
      return var0.map(
         var0x -> var0x.update("type", var0xx -> (Dynamic)DataFixUtils.orElse(var0xx.asString().map(this.a).map(var0xx::createString).result(), var0xx))
      );
   }
}
