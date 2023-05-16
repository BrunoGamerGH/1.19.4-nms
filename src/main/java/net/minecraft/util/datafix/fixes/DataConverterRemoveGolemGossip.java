package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterRemoveGolemGossip extends DataConverterNamedEntity {
   public DataConverterRemoveGolemGossip(Schema var0, boolean var1) {
      super(var0, var1, "Remove Golem Gossip Fix", DataConverterTypes.q, "minecraft:villager");
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), DataConverterRemoveGolemGossip::a);
   }

   private static Dynamic<?> a(Dynamic<?> var0) {
      return var0.update("Gossips", var1 -> var0.createList(var1.asStream().filter(var0xx -> !var0xx.get("Type").asString("").equals("golem"))));
   }
}
