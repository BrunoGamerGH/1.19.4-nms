package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterSaddle extends DataConverterNamedEntity {
   public DataConverterSaddle(Schema var0, boolean var1) {
      super(var0, var1, "EntityHorseSaddleFix", DataConverterTypes.q, "EntityHorse");
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      OpticFinder<Pair<String, String>> var1 = DSL.fieldFinder("id", DSL.named(DataConverterTypes.s.typeName(), DataConverterSchemaNamed.a()));
      Type<?> var2 = this.getInputSchema().getTypeRaw(DataConverterTypes.m);
      OpticFinder<?> var3 = DSL.fieldFinder("SaddleItem", var2);
      Optional<? extends Typed<?>> var4 = var0.getOptionalTyped(var3);
      Dynamic<?> var5 = (Dynamic)var0.get(DSL.remainderFinder());
      if (!var4.isPresent() && var5.get("Saddle").asBoolean(false)) {
         Typed<?> var6 = (Typed)var2.pointTyped(var0.getOps()).orElseThrow(IllegalStateException::new);
         var6 = var6.set(var1, Pair.of(DataConverterTypes.s.typeName(), "minecraft:saddle"));
         Dynamic<?> var7 = var5.emptyMap();
         var7 = var7.set("Count", var7.createByte((byte)1));
         var7 = var7.set("Damage", var7.createShort((short)0));
         var6 = var6.set(DSL.remainderFinder(), var7);
         var5.remove("Saddle");
         return var0.set(var3, var6).set(DSL.remainderFinder(), var5);
      } else {
         return var0;
      }
   }
}
