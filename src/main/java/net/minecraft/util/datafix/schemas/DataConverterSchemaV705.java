package net.minecraft.util.datafix.schemas;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV705 extends DataConverterSchemaNamed {
   protected static final HookFunction b = new HookFunction() {
      public <T> T apply(DynamicOps<T> var0, T var1) {
         return DataConverterSchemaV99.a(new Dynamic(var0, var1), DataConverterSchemaV704.a, "minecraft:armor_stand");
      }
   };

   public DataConverterSchemaV705(int var0, Schema var1) {
      super(var0, var1);
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(var1, var2, () -> DataConverterSchemaV100.a(var0));
   }

   protected static void b(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(var1, var2, () -> DSL.optionalFields("inTile", DataConverterTypes.r.in(var0)));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = Maps.newHashMap();
      var0.registerSimple(var1, "minecraft:area_effect_cloud");
      a(var0, var1, "minecraft:armor_stand");
      var0.register(var1, "minecraft:arrow", var1x -> DSL.optionalFields("inTile", DataConverterTypes.r.in(var0)));
      a(var0, var1, "minecraft:bat");
      a(var0, var1, "minecraft:blaze");
      var0.registerSimple(var1, "minecraft:boat");
      a(var0, var1, "minecraft:cave_spider");
      var0.register(
         var1,
         "minecraft:chest_minecart",
         var1x -> DSL.optionalFields("DisplayTile", DataConverterTypes.r.in(var0), "Items", DSL.list(DataConverterTypes.m.in(var0)))
      );
      a(var0, var1, "minecraft:chicken");
      var0.register(var1, "minecraft:commandblock_minecart", var1x -> DSL.optionalFields("DisplayTile", DataConverterTypes.r.in(var0)));
      a(var0, var1, "minecraft:cow");
      a(var0, var1, "minecraft:creeper");
      var0.register(
         var1,
         "minecraft:donkey",
         var1x -> DSL.optionalFields(
               "Items", DSL.list(DataConverterTypes.m.in(var0)), "SaddleItem", DataConverterTypes.m.in(var0), DataConverterSchemaV100.a(var0)
            )
      );
      var0.registerSimple(var1, "minecraft:dragon_fireball");
      b(var0, var1, "minecraft:egg");
      a(var0, var1, "minecraft:elder_guardian");
      var0.registerSimple(var1, "minecraft:ender_crystal");
      a(var0, var1, "minecraft:ender_dragon");
      var0.register(var1, "minecraft:enderman", var1x -> DSL.optionalFields("carried", DataConverterTypes.r.in(var0), DataConverterSchemaV100.a(var0)));
      a(var0, var1, "minecraft:endermite");
      b(var0, var1, "minecraft:ender_pearl");
      var0.registerSimple(var1, "minecraft:eye_of_ender_signal");
      var0.register(
         var1, "minecraft:falling_block", var1x -> DSL.optionalFields("Block", DataConverterTypes.r.in(var0), "TileEntityData", DataConverterTypes.l.in(var0))
      );
      b(var0, var1, "minecraft:fireball");
      var0.register(var1, "minecraft:fireworks_rocket", var1x -> DSL.optionalFields("FireworksItem", DataConverterTypes.m.in(var0)));
      var0.register(var1, "minecraft:furnace_minecart", var1x -> DSL.optionalFields("DisplayTile", DataConverterTypes.r.in(var0)));
      a(var0, var1, "minecraft:ghast");
      a(var0, var1, "minecraft:giant");
      a(var0, var1, "minecraft:guardian");
      var0.register(
         var1,
         "minecraft:hopper_minecart",
         var1x -> DSL.optionalFields("DisplayTile", DataConverterTypes.r.in(var0), "Items", DSL.list(DataConverterTypes.m.in(var0)))
      );
      var0.register(
         var1,
         "minecraft:horse",
         var1x -> DSL.optionalFields("ArmorItem", DataConverterTypes.m.in(var0), "SaddleItem", DataConverterTypes.m.in(var0), DataConverterSchemaV100.a(var0))
      );
      a(var0, var1, "minecraft:husk");
      var0.register(var1, "minecraft:item", var1x -> DSL.optionalFields("Item", DataConverterTypes.m.in(var0)));
      var0.register(var1, "minecraft:item_frame", var1x -> DSL.optionalFields("Item", DataConverterTypes.m.in(var0)));
      var0.registerSimple(var1, "minecraft:leash_knot");
      a(var0, var1, "minecraft:magma_cube");
      var0.register(var1, "minecraft:minecart", var1x -> DSL.optionalFields("DisplayTile", DataConverterTypes.r.in(var0)));
      a(var0, var1, "minecraft:mooshroom");
      var0.register(
         var1,
         "minecraft:mule",
         var1x -> DSL.optionalFields(
               "Items", DSL.list(DataConverterTypes.m.in(var0)), "SaddleItem", DataConverterTypes.m.in(var0), DataConverterSchemaV100.a(var0)
            )
      );
      a(var0, var1, "minecraft:ocelot");
      var0.registerSimple(var1, "minecraft:painting");
      var0.registerSimple(var1, "minecraft:parrot");
      a(var0, var1, "minecraft:pig");
      a(var0, var1, "minecraft:polar_bear");
      var0.register(var1, "minecraft:potion", var1x -> DSL.optionalFields("Potion", DataConverterTypes.m.in(var0), "inTile", DataConverterTypes.r.in(var0)));
      a(var0, var1, "minecraft:rabbit");
      a(var0, var1, "minecraft:sheep");
      a(var0, var1, "minecraft:shulker");
      var0.registerSimple(var1, "minecraft:shulker_bullet");
      a(var0, var1, "minecraft:silverfish");
      a(var0, var1, "minecraft:skeleton");
      var0.register(
         var1, "minecraft:skeleton_horse", var1x -> DSL.optionalFields("SaddleItem", DataConverterTypes.m.in(var0), DataConverterSchemaV100.a(var0))
      );
      a(var0, var1, "minecraft:slime");
      b(var0, var1, "minecraft:small_fireball");
      b(var0, var1, "minecraft:snowball");
      a(var0, var1, "minecraft:snowman");
      var0.register(
         var1, "minecraft:spawner_minecart", var1x -> DSL.optionalFields("DisplayTile", DataConverterTypes.r.in(var0), DataConverterTypes.u.in(var0))
      );
      var0.register(var1, "minecraft:spectral_arrow", var1x -> DSL.optionalFields("inTile", DataConverterTypes.r.in(var0)));
      a(var0, var1, "minecraft:spider");
      a(var0, var1, "minecraft:squid");
      a(var0, var1, "minecraft:stray");
      var0.registerSimple(var1, "minecraft:tnt");
      var0.register(var1, "minecraft:tnt_minecart", var1x -> DSL.optionalFields("DisplayTile", DataConverterTypes.r.in(var0)));
      var0.register(
         var1,
         "minecraft:villager",
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
               DataConverterSchemaV100.a(var0)
            )
      );
      a(var0, var1, "minecraft:villager_golem");
      a(var0, var1, "minecraft:witch");
      a(var0, var1, "minecraft:wither");
      a(var0, var1, "minecraft:wither_skeleton");
      b(var0, var1, "minecraft:wither_skull");
      a(var0, var1, "minecraft:wolf");
      b(var0, var1, "minecraft:xp_bottle");
      var0.registerSimple(var1, "minecraft:xp_orb");
      a(var0, var1, "minecraft:zombie");
      var0.register(var1, "minecraft:zombie_horse", var1x -> DSL.optionalFields("SaddleItem", DataConverterTypes.m.in(var0), DataConverterSchemaV100.a(var0)));
      a(var0, var1, "minecraft:zombie_pigman");
      a(var0, var1, "minecraft:zombie_villager");
      var0.registerSimple(var1, "minecraft:evocation_fangs");
      a(var0, var1, "minecraft:evocation_illager");
      var0.registerSimple(var1, "minecraft:illusion_illager");
      var0.register(
         var1,
         "minecraft:llama",
         var1x -> DSL.optionalFields(
               "Items",
               DSL.list(DataConverterTypes.m.in(var0)),
               "SaddleItem",
               DataConverterTypes.m.in(var0),
               "DecorItem",
               DataConverterTypes.m.in(var0),
               DataConverterSchemaV100.a(var0)
            )
      );
      var0.registerSimple(var1, "minecraft:llama_spit");
      a(var0, var1, "minecraft:vex");
      a(var0, var1, "minecraft:vindication_illager");
      return var1;
   }

   public void registerTypes(Schema var0, Map<String, Supplier<TypeTemplate>> var1, Map<String, Supplier<TypeTemplate>> var2) {
      super.registerTypes(var0, var1, var2);
      var0.registerType(true, DataConverterTypes.q, () -> DSL.taggedChoiceLazy("id", a(), var1));
      var0.registerType(
         true,
         DataConverterTypes.m,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  DataConverterTypes.s.in(var0),
                  "tag",
                  DSL.optionalFields(
                     "EntityTag",
                     DataConverterTypes.p.in(var0),
                     "BlockEntityTag",
                     DataConverterTypes.l.in(var0),
                     "CanDestroy",
                     DSL.list(DataConverterTypes.r.in(var0)),
                     "CanPlaceOn",
                     DSL.list(DataConverterTypes.r.in(var0)),
                     "Items",
                     DSL.list(DataConverterTypes.m.in(var0))
                  )
               ),
               b,
               HookFunction.IDENTITY
            )
      );
   }
}
