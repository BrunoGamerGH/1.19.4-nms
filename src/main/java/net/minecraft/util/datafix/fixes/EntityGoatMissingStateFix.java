package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

public class EntityGoatMissingStateFix extends DataConverterNamedEntity {
   public EntityGoatMissingStateFix(Schema var0) {
      super(var0, false, "EntityGoatMissingStateFix", DataConverterTypes.q, "minecraft:goat");
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), var0x -> var0x.set("HasLeftHorn", var0x.createBoolean(true)).set("HasRightHorn", var0x.createBoolean(true)));
   }
}
