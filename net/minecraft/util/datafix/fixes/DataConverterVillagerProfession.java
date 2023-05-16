package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterVillagerProfession extends DataConverterNamedEntity {
   public DataConverterVillagerProfession(Schema var0, String var1) {
      super(var0, false, "Villager profession data fix (" + var1 + ")", DataConverterTypes.q, var1);
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      Dynamic<?> var1 = (Dynamic)var0.get(DSL.remainderFinder());
      return var0.set(
         DSL.remainderFinder(),
         var1.remove("Profession")
            .remove("Career")
            .remove("CareerLevel")
            .set(
               "VillagerData",
               var1.createMap(
                  ImmutableMap.of(
                     var1.createString("type"),
                     var1.createString("minecraft:plains"),
                     var1.createString("profession"),
                     var1.createString(a(var1.get("Profession").asInt(0), var1.get("Career").asInt(0))),
                     var1.createString("level"),
                     (Dynamic)DataFixUtils.orElse(var1.get("CareerLevel").result(), var1.createInt(1))
                  )
               )
            )
      );
   }

   private static String a(int var0, int var1) {
      if (var0 == 0) {
         if (var1 == 2) {
            return "minecraft:fisherman";
         } else if (var1 == 3) {
            return "minecraft:shepherd";
         } else {
            return var1 == 4 ? "minecraft:fletcher" : "minecraft:farmer";
         }
      } else if (var0 == 1) {
         return var1 == 2 ? "minecraft:cartographer" : "minecraft:librarian";
      } else if (var0 == 2) {
         return "minecraft:cleric";
      } else if (var0 == 3) {
         if (var1 == 2) {
            return "minecraft:weaponsmith";
         } else {
            return var1 == 3 ? "minecraft:toolsmith" : "minecraft:armorer";
         }
      } else if (var0 == 4) {
         return var1 == 2 ? "minecraft:leatherworker" : "minecraft:butcher";
      } else {
         return var0 == 5 ? "minecraft:nitwit" : "minecraft:none";
      }
   }
}
