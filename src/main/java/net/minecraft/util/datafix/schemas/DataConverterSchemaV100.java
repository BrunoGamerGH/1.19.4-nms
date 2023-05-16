package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV100 extends Schema {
   public DataConverterSchemaV100(int var0, Schema var1) {
      super(var0, var1);
   }

   protected static TypeTemplate a(Schema var0) {
      return DSL.optionalFields("ArmorItems", DSL.list(DataConverterTypes.m.in(var0)), "HandItems", DSL.list(DataConverterTypes.m.in(var0)));
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(var1, var2, () -> a(var0));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerEntities(var0);
      a(var0, var1, "ArmorStand");
      a(var0, var1, "Creeper");
      a(var0, var1, "Skeleton");
      a(var0, var1, "Spider");
      a(var0, var1, "Giant");
      a(var0, var1, "Zombie");
      a(var0, var1, "Slime");
      a(var0, var1, "Ghast");
      a(var0, var1, "PigZombie");
      var0.register(var1, "Enderman", var1x -> DSL.optionalFields("carried", DataConverterTypes.r.in(var0), a(var0)));
      a(var0, var1, "CaveSpider");
      a(var0, var1, "Silverfish");
      a(var0, var1, "Blaze");
      a(var0, var1, "LavaSlime");
      a(var0, var1, "EnderDragon");
      a(var0, var1, "WitherBoss");
      a(var0, var1, "Bat");
      a(var0, var1, "Witch");
      a(var0, var1, "Endermite");
      a(var0, var1, "Guardian");
      a(var0, var1, "Pig");
      a(var0, var1, "Sheep");
      a(var0, var1, "Cow");
      a(var0, var1, "Chicken");
      a(var0, var1, "Squid");
      a(var0, var1, "Wolf");
      a(var0, var1, "MushroomCow");
      a(var0, var1, "SnowMan");
      a(var0, var1, "Ozelot");
      a(var0, var1, "VillagerGolem");
      var0.register(
         var1,
         "EntityHorse",
         var1x -> DSL.optionalFields(
               "Items",
               DSL.list(DataConverterTypes.m.in(var0)),
               "ArmorItem",
               DataConverterTypes.m.in(var0),
               "SaddleItem",
               DataConverterTypes.m.in(var0),
               a(var0)
            )
      );
      a(var0, var1, "Rabbit");
      var0.register(
         var1,
         "Villager",
         var1x -> DSL.optionalFields(
               "Inventory",
               DSL.list(DataConverterTypes.m.in(var0)),
               "Offers",
               DSL.optionalFields(
                  "Recipes",
                  DSL.list(
                     DSL.optionalFields("buy", DataConverterTypes.m.in(var0), "buyB", DataConverterTypes.m.in(var0), "sell", DataConverterTypes.m.in(var0))
                  )
               ),
               a(var0)
            )
      );
      a(var0, var1, "Shulker");
      var0.registerSimple(var1, "AreaEffectCloud");
      var0.registerSimple(var1, "ShulkerBullet");
      return var1;
   }

   public void registerTypes(Schema var0, Map<String, Supplier<TypeTemplate>> var1, Map<String, Supplier<TypeTemplate>> var2) {
      super.registerTypes(var0, var1, var2);
      var0.registerType(
         false,
         DataConverterTypes.f,
         () -> DSL.optionalFields(
               "entities",
               DSL.list(DSL.optionalFields("nbt", DataConverterTypes.p.in(var0))),
               "blocks",
               DSL.list(DSL.optionalFields("nbt", DataConverterTypes.l.in(var0))),
               "palette",
               DSL.list(DataConverterTypes.n.in(var0))
            )
      );
      var0.registerType(false, DataConverterTypes.n, DSL::remainder);
   }
}
