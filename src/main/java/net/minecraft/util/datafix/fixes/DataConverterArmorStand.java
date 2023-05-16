package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterArmorStand extends DataConverterNamedEntity {
   public DataConverterArmorStand(Schema var0, boolean var1) {
      super(var0, var1, "EntityArmorStandSilentFix", DataConverterTypes.q, "ArmorStand");
   }

   public Dynamic<?> a(Dynamic<?> var0) {
      return var0.get("Silent").asBoolean(false) && !var0.get("Marker").asBoolean(false) ? var0.remove("Silent") : var0;
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), this::a);
   }
}
