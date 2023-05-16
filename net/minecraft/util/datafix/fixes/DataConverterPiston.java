package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class DataConverterPiston extends DataConverterNamedEntity {
   public DataConverterPiston(Schema var0, boolean var1) {
      super(var0, var1, "BlockEntityBlockStateFix", DataConverterTypes.l, "minecraft:piston");
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      Type<?> var1 = this.getOutputSchema().getChoiceType(DataConverterTypes.l, "minecraft:piston");
      Type<?> var2 = var1.findFieldType("blockState");
      OpticFinder<?> var3 = DSL.fieldFinder("blockState", var2);
      Dynamic<?> var4 = (Dynamic)var0.get(DSL.remainderFinder());
      int var5 = var4.get("blockId").asInt(0);
      var4 = var4.remove("blockId");
      int var6 = var4.get("blockData").asInt(0) & 15;
      var4 = var4.remove("blockData");
      Dynamic<?> var7 = DataConverterFlattenData.b(var5 << 4 | var6);
      Typed<?> var8 = (Typed)var1.pointTyped(var0.getOps()).orElseThrow(() -> new IllegalStateException("Could not create new piston block entity."));
      return var8.set(DSL.remainderFinder(), var4)
         .set(
            var3,
            (Typed)((Pair)var2.readTyped(var7).result().orElseThrow(() -> new IllegalStateException("Could not parse newly created block state tag.")))
               .getFirst()
         );
   }
}
