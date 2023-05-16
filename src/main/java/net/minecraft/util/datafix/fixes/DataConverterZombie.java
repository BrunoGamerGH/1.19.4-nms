package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.RandomSource;

public class DataConverterZombie extends DataConverterNamedEntity {
   private static final int a = 6;
   private static final RandomSource b = RandomSource.a();

   public DataConverterZombie(Schema var0, boolean var1) {
      super(var0, var1, "EntityZombieVillagerTypeFix", DataConverterTypes.q, "Zombie");
   }

   public Dynamic<?> a(Dynamic<?> var0) {
      if (var0.get("IsVillager").asBoolean(false)) {
         if (!var0.get("ZombieType").result().isPresent()) {
            int var1 = this.a(var0.get("VillagerProfession").asInt(-1));
            if (var1 == -1) {
               var1 = this.a(b.a(6));
            }

            var0 = var0.set("ZombieType", var0.createInt(var1));
         }

         var0 = var0.remove("IsVillager");
      }

      return var0;
   }

   private int a(int var0) {
      return var0 >= 0 && var0 < 6 ? var0 : -1;
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), this::a);
   }
}
