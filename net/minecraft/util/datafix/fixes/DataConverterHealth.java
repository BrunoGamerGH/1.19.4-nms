package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;

public class DataConverterHealth extends DataFix {
   private static final Set<String> a = Sets.newHashSet(
      new String[]{
         "ArmorStand",
         "Bat",
         "Blaze",
         "CaveSpider",
         "Chicken",
         "Cow",
         "Creeper",
         "EnderDragon",
         "Enderman",
         "Endermite",
         "EntityHorse",
         "Ghast",
         "Giant",
         "Guardian",
         "LavaSlime",
         "MushroomCow",
         "Ozelot",
         "Pig",
         "PigZombie",
         "Rabbit",
         "Sheep",
         "Shulker",
         "Silverfish",
         "Skeleton",
         "Slime",
         "SnowMan",
         "Spider",
         "Squid",
         "Villager",
         "VillagerGolem",
         "Witch",
         "WitherBoss",
         "Wolf",
         "Zombie"
      }
   );

   public DataConverterHealth(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public Dynamic<?> a(Dynamic<?> var0) {
      Optional<Number> var2 = var0.get("HealF").asNumber().result();
      Optional<Number> var3 = var0.get("Health").asNumber().result();
      float var1;
      if (var2.isPresent()) {
         var1 = var2.get().floatValue();
         var0 = var0.remove("HealF");
      } else {
         if (!var3.isPresent()) {
            return var0;
         }

         var1 = var3.get().floatValue();
      }

      return var0.set("Health", var0.createFloat(var1));
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "EntityHealthFix", this.getInputSchema().getType(DataConverterTypes.q), var0 -> var0.update(DSL.remainderFinder(), this::a)
      );
   }
}
