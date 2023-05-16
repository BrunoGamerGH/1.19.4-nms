package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

public class WeaponSmithChestLootTableFix extends DataConverterNamedEntity {
   public WeaponSmithChestLootTableFix(Schema var0, boolean var1) {
      super(var0, var1, "WeaponSmithChestLootTableFix", DataConverterTypes.l, "minecraft:chest");
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(
         DSL.remainderFinder(),
         var0x -> {
            String var1x = var0x.get("LootTable").asString("");
            return var1x.equals("minecraft:chests/village_blacksmith")
               ? var0x.set("LootTable", var0x.createString("minecraft:chests/village/village_weaponsmith"))
               : var0x;
         }
      );
   }
}
