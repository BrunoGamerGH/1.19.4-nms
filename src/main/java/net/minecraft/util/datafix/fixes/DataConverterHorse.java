package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class DataConverterHorse extends DataConverterEntityName {
   public DataConverterHorse(Schema var0, boolean var1) {
      super("EntityHorseSplitFix", var0, var1);
   }

   @Override
   protected Pair<String, Typed<?>> a(String var0, Typed<?> var1) {
      Dynamic<?> var2 = (Dynamic)var1.get(DSL.remainderFinder());
      if (Objects.equals("EntityHorse", var0)) {
         int var4 = var2.get("Type").asInt(0);

         String var3 = switch(var4) {
            default -> "Horse";
            case 1 -> "Donkey";
            case 2 -> "Mule";
            case 3 -> "ZombieHorse";
            case 4 -> "SkeletonHorse";
         };
         var2.remove("Type");
         Type<?> var5 = (Type)this.getOutputSchema().findChoiceType(DataConverterTypes.q).types().get(var3);
         return Pair.of(
            var3,
            (Typed)((Pair)var1.write().flatMap(var5::readTyped).result().orElseThrow(() -> new IllegalStateException("Could not parse the new horse")))
               .getFirst()
         );
      } else {
         return Pair.of(var0, var1);
      }
   }
}
