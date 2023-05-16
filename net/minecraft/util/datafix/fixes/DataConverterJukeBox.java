package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class DataConverterJukeBox extends DataConverterNamedEntity {
   public DataConverterJukeBox(Schema var0, boolean var1) {
      super(var0, var1, "BlockEntityJukeboxFix", DataConverterTypes.l, "minecraft:jukebox");
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      Type<?> var1 = this.getInputSchema().getChoiceType(DataConverterTypes.l, "minecraft:jukebox");
      Type<?> var2 = var1.findFieldType("RecordItem");
      OpticFinder<?> var3 = DSL.fieldFinder("RecordItem", var2);
      Dynamic<?> var4 = (Dynamic)var0.get(DSL.remainderFinder());
      int var5 = var4.get("Record").asInt(0);
      if (var5 > 0) {
         var4.remove("Record");
         String var6 = DataConverterFlatten.a(DataConverterMaterialId.a(var5), 0);
         if (var6 != null) {
            Dynamic<?> var7 = var4.emptyMap();
            var7 = var7.set("id", var7.createString(var6));
            var7 = var7.set("Count", var7.createByte((byte)1));
            return var0.set(
                  var3,
                  (Typed)((Pair)var2.readTyped(var7).result().orElseThrow(() -> new IllegalStateException("Could not create record item stack."))).getFirst()
               )
               .set(DSL.remainderFinder(), var4);
         }
      }

      return var0;
   }
}
