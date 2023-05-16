package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Optional;

public class DataConverterZombieVillagerLevelXp extends DataConverterNamedEntity {
   public DataConverterZombieVillagerLevelXp(Schema var0, boolean var1) {
      super(var0, var1, "Zombie Villager XP rebuild", DataConverterTypes.q, "minecraft:zombie_villager");
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), var0x -> {
         Optional<Number> var1x = var0x.get("Xp").asNumber().result();
         if (!var1x.isPresent()) {
            int var2 = var0x.get("VillagerData").get("level").asInt(1);
            return var0x.set("Xp", var0x.createInt(DataConverterVillagerLevelXp.a(var2)));
         } else {
            return var0x;
         }
      });
   }
}
