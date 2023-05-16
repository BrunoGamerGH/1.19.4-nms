package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class DataConverterZombieType extends DataConverterEntityNameAbstract {
   public DataConverterZombieType(Schema var0, boolean var1) {
      super("EntityZombieSplitFix", var0, var1);
   }

   @Override
   protected Pair<String, Dynamic<?>> a(String var0, Dynamic<?> var1) {
      if (Objects.equals("Zombie", var0)) {
         String var2 = "Zombie";
         int var3 = var1.get("ZombieType").asInt(0);
         switch(var3) {
            case 0:
            default:
               break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
               var2 = "ZombieVillager";
               var1 = var1.set("Profession", var1.createInt(var3 - 1));
               break;
            case 6:
               var2 = "Husk";
         }

         var1 = var1.remove("ZombieType");
         return Pair.of(var2, var1);
      } else {
         return Pair.of(var0, var1);
      }
   }
}
