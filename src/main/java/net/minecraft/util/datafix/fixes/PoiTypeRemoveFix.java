package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PoiTypeRemoveFix extends AbstractPoiSectionFix {
   private final Predicate<String> a;

   public PoiTypeRemoveFix(Schema var0, String var1, Predicate<String> var2) {
      super(var0, var1);
      this.a = var2.negate();
   }

   @Override
   protected <T> Stream<Dynamic<T>> a(Stream<Dynamic<T>> var0) {
      return var0.filter(this::a);
   }

   private <T> boolean a(Dynamic<T> var0) {
      return var0.get("type").asString().result().filter(this.a).isPresent();
   }
}
