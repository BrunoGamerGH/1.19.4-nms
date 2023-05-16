package net.minecraft.util.datafix.schemas;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV1460 extends DataConverterSchemaNamed {
   public DataConverterSchemaV1460(int var0, Schema var1) {
      super(var0, var1);
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(var1, var2, () -> DataConverterSchemaV100.a(var0));
   }

   protected static void b(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(var1, var2, () -> DSL.optionalFields("Items", DSL.list(DataConverterTypes.m.in(var0))));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = Maps.newHashMap();
      var0.registerSimple(var1, "minecraft:area_effect_cloud");
      a(var0, var1, "minecraft:armor_stand");
      var0.register(var1, "minecraft:arrow", var1x -> DSL.optionalFields("inBlockState", DataConverterTypes.n.in(var0)));
      a(var0, var1, "minecraft:bat");
      a(var0, var1, "minecraft:blaze");
      var0.registerSimple(var1, "minecraft:boat");
      a(var0, var1, "minecraft:cave_spider");
      var0.register(
         var1,
         "minecraft:chest_minecart",
         var1x -> DSL.optionalFields("DisplayState", DataConverterTypes.n.in(var0), "Items", DSL.list(DataConverterTypes.m.in(var0)))
      );
      a(var0, var1, "minecraft:chicken");
      var0.register(var1, "minecraft:commandblock_minecart", var1x -> DSL.optionalFields("DisplayState", DataConverterTypes.n.in(var0)));
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
      var0.registerSimple(var1, "minecraft:egg");
      a(var0, var1, "minecraft:elder_guardian");
      var0.registerSimple(var1, "minecraft:ender_crystal");
      a(var0, var1, "minecraft:ender_dragon");
      var0.register(
         var1, "minecraft:enderman", var1x -> DSL.optionalFields("carriedBlockState", DataConverterTypes.n.in(var0), DataConverterSchemaV100.a(var0))
      );
      a(var0, var1, "minecraft:endermite");
      var0.registerSimple(var1, "minecraft:ender_pearl");
      var0.registerSimple(var1, "minecraft:evocation_fangs");
      a(var0, var1, "minecraft:evocation_illager");
      var0.registerSimple(var1, "minecraft:eye_of_ender_signal");
      var0.register(
         var1,
         "minecraft:falling_block",
         var1x -> DSL.optionalFields("BlockState", DataConverterTypes.n.in(var0), "TileEntityData", DataConverterTypes.l.in(var0))
      );
      var0.registerSimple(var1, "minecraft:fireball");
      var0.register(var1, "minecraft:fireworks_rocket", var1x -> DSL.optionalFields("FireworksItem", DataConverterTypes.m.in(var0)));
      var0.register(var1, "minecraft:furnace_minecart", var1x -> DSL.optionalFields("DisplayState", DataConverterTypes.n.in(var0)));
      a(var0, var1, "minecraft:ghast");
      a(var0, var1, "minecraft:giant");
      a(var0, var1, "minecraft:guardian");
      var0.register(
         var1,
         "minecraft:hopper_minecart",
         var1x -> DSL.optionalFields("DisplayState", DataConverterTypes.n.in(var0), "Items", DSL.list(DataConverterTypes.m.in(var0)))
      );
      var0.register(
         var1,
         "minecraft:horse",
         var1x -> DSL.optionalFields("ArmorItem", DataConverterTypes.m.in(var0), "SaddleItem", DataConverterTypes.m.in(var0), DataConverterSchemaV100.a(var0))
      );
      a(var0, var1, "minecraft:husk");
      var0.registerSimple(var1, "minecraft:illusion_illager");
      var0.register(var1, "minecraft:item", var1x -> DSL.optionalFields("Item", DataConverterTypes.m.in(var0)));
      var0.register(var1, "minecraft:item_frame", var1x -> DSL.optionalFields("Item", DataConverterTypes.m.in(var0)));
      var0.registerSimple(var1, "minecraft:leash_knot");
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
      a(var0, var1, "minecraft:magma_cube");
      var0.register(var1, "minecraft:minecart", var1x -> DSL.optionalFields("DisplayState", DataConverterTypes.n.in(var0)));
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
      var0.register(var1, "minecraft:potion", var1x -> DSL.optionalFields("Potion", DataConverterTypes.m.in(var0)));
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
      var0.registerSimple(var1, "minecraft:small_fireball");
      var0.registerSimple(var1, "minecraft:snowball");
      a(var0, var1, "minecraft:snowman");
      var0.register(
         var1, "minecraft:spawner_minecart", var1x -> DSL.optionalFields("DisplayState", DataConverterTypes.n.in(var0), DataConverterTypes.u.in(var0))
      );
      var0.register(var1, "minecraft:spectral_arrow", var1x -> DSL.optionalFields("inBlockState", DataConverterTypes.n.in(var0)));
      a(var0, var1, "minecraft:spider");
      a(var0, var1, "minecraft:squid");
      a(var0, var1, "minecraft:stray");
      var0.registerSimple(var1, "minecraft:tnt");
      var0.register(var1, "minecraft:tnt_minecart", var1x -> DSL.optionalFields("DisplayState", DataConverterTypes.n.in(var0)));
      a(var0, var1, "minecraft:vex");
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
      a(var0, var1, "minecraft:vindication_illager");
      a(var0, var1, "minecraft:witch");
      a(var0, var1, "minecraft:wither");
      a(var0, var1, "minecraft:wither_skeleton");
      var0.registerSimple(var1, "minecraft:wither_skull");
      a(var0, var1, "minecraft:wolf");
      var0.registerSimple(var1, "minecraft:xp_bottle");
      var0.registerSimple(var1, "minecraft:xp_orb");
      a(var0, var1, "minecraft:zombie");
      var0.register(var1, "minecraft:zombie_horse", var1x -> DSL.optionalFields("SaddleItem", DataConverterTypes.m.in(var0), DataConverterSchemaV100.a(var0)));
      a(var0, var1, "minecraft:zombie_pigman");
      a(var0, var1, "minecraft:zombie_villager");
      return var1;
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = Maps.newHashMap();
      b(var0, var1, "minecraft:furnace");
      b(var0, var1, "minecraft:chest");
      b(var0, var1, "minecraft:trapped_chest");
      var0.registerSimple(var1, "minecraft:ender_chest");
      var0.register(var1, "minecraft:jukebox", var1x -> DSL.optionalFields("RecordItem", DataConverterTypes.m.in(var0)));
      b(var0, var1, "minecraft:dispenser");
      b(var0, var1, "minecraft:dropper");
      var0.registerSimple(var1, "minecraft:sign");
      var0.register(var1, "minecraft:mob_spawner", var1x -> DataConverterTypes.u.in(var0));
      var0.register(var1, "minecraft:piston", var1x -> DSL.optionalFields("blockState", DataConverterTypes.n.in(var0)));
      b(var0, var1, "minecraft:brewing_stand");
      var0.registerSimple(var1, "minecraft:enchanting_table");
      var0.registerSimple(var1, "minecraft:end_portal");
      var0.registerSimple(var1, "minecraft:beacon");
      var0.registerSimple(var1, "minecraft:skull");
      var0.registerSimple(var1, "minecraft:daylight_detector");
      b(var0, var1, "minecraft:hopper");
      var0.registerSimple(var1, "minecraft:comparator");
      var0.registerSimple(var1, "minecraft:banner");
      var0.registerSimple(var1, "minecraft:structure_block");
      var0.registerSimple(var1, "minecraft:end_gateway");
      var0.registerSimple(var1, "minecraft:command_block");
      b(var0, var1, "minecraft:shulker_box");
      var0.registerSimple(var1, "minecraft:bed");
      return var1;
   }

   public void registerTypes(Schema var0, Map<String, Supplier<TypeTemplate>> var1, Map<String, Supplier<TypeTemplate>> var2) {
      var0.registerType(false, DataConverterTypes.a, DSL::remainder);
      var0.registerType(false, DataConverterTypes.y, () -> DSL.constType(a()));
      var0.registerType(
         false,
         DataConverterTypes.b,
         () -> DSL.optionalFields(
               "RootVehicle",
               DSL.optionalFields("Entity", DataConverterTypes.p.in(var0)),
               "Inventory",
               DSL.list(DataConverterTypes.m.in(var0)),
               "EnderItems",
               DSL.list(DataConverterTypes.m.in(var0)),
               DSL.optionalFields(
                  "ShoulderEntityLeft",
                  DataConverterTypes.p.in(var0),
                  "ShoulderEntityRight",
                  DataConverterTypes.p.in(var0),
                  "recipeBook",
                  DSL.optionalFields("recipes", DSL.list(DataConverterTypes.y.in(var0)), "toBeDisplayed", DSL.list(DataConverterTypes.y.in(var0)))
               )
            )
      );
      var0.registerType(
         false,
         DataConverterTypes.c,
         () -> DSL.fields(
               "Level",
               DSL.optionalFields(
                  "Entities",
                  DSL.list(DataConverterTypes.p.in(var0)),
                  "TileEntities",
                  DSL.list(DSL.or(DataConverterTypes.l.in(var0), DSL.remainder())),
                  "TileTicks",
                  DSL.list(DSL.fields("i", DataConverterTypes.r.in(var0))),
                  "Sections",
                  DSL.list(DSL.optionalFields("Palette", DSL.list(DataConverterTypes.n.in(var0))))
               )
            )
      );
      var0.registerType(true, DataConverterTypes.l, () -> DSL.taggedChoiceLazy("id", a(), var2));
      var0.registerType(
         true, DataConverterTypes.p, () -> DSL.optionalFields("Passengers", DSL.list(DataConverterTypes.p.in(var0)), DataConverterTypes.q.in(var0))
      );
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
               DataConverterSchemaV705.b,
               HookFunction.IDENTITY
            )
      );
      var0.registerType(false, DataConverterTypes.d, () -> DSL.compoundList(DSL.list(DataConverterTypes.m.in(var0))));
      var0.registerType(false, DataConverterTypes.e, DSL::remainder);
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
      var0.registerType(false, DataConverterTypes.r, () -> DSL.constType(a()));
      var0.registerType(false, DataConverterTypes.s, () -> DSL.constType(a()));
      var0.registerType(false, DataConverterTypes.n, DSL::remainder);
      Supplier<TypeTemplate> var3 = () -> DSL.compoundList(DataConverterTypes.s.in(var0), DSL.constType(DSL.intType()));
      var0.registerType(
         false,
         DataConverterTypes.g,
         () -> DSL.optionalFields(
               "stats",
               DSL.optionalFields(
                  "minecraft:mined",
                  DSL.compoundList(DataConverterTypes.r.in(var0), DSL.constType(DSL.intType())),
                  "minecraft:crafted",
                  (TypeTemplate)var3.get(),
                  "minecraft:used",
                  (TypeTemplate)var3.get(),
                  "minecraft:broken",
                  (TypeTemplate)var3.get(),
                  "minecraft:picked_up",
                  (TypeTemplate)var3.get(),
                  DSL.optionalFields(
                     "minecraft:dropped",
                     (TypeTemplate)var3.get(),
                     "minecraft:killed",
                     DSL.compoundList(DataConverterTypes.o.in(var0), DSL.constType(DSL.intType())),
                     "minecraft:killed_by",
                     DSL.compoundList(DataConverterTypes.o.in(var0), DSL.constType(DSL.intType())),
                     "minecraft:custom",
                     DSL.compoundList(DSL.constType(a()), DSL.constType(DSL.intType()))
                  )
               )
            )
      );
      var0.registerType(
         false,
         DataConverterTypes.h,
         () -> DSL.optionalFields(
               "data",
               DSL.optionalFields(
                  "Features",
                  DSL.compoundList(DataConverterTypes.v.in(var0)),
                  "Objectives",
                  DSL.list(DataConverterTypes.w.in(var0)),
                  "Teams",
                  DSL.list(DataConverterTypes.x.in(var0))
               )
            )
      );
      var0.registerType(false, DataConverterTypes.v, DSL::remainder);
      Map<String, Supplier<TypeTemplate>> var4 = DataConverterSchemaV1451_6.a(var0);
      var0.registerType(
         false,
         DataConverterTypes.w,
         () -> DSL.hook(
               DSL.optionalFields("CriteriaType", DSL.taggedChoiceLazy("type", DSL.string(), var4)),
               DataConverterSchemaV1451_6.c,
               DataConverterSchemaV1451_6.d
            )
      );
      var0.registerType(false, DataConverterTypes.x, DSL::remainder);
      var0.registerType(
         true,
         DataConverterTypes.u,
         () -> DSL.optionalFields("SpawnPotentials", DSL.list(DSL.fields("Entity", DataConverterTypes.p.in(var0))), "SpawnData", DataConverterTypes.p.in(var0))
      );
      var0.registerType(
         false,
         DataConverterTypes.i,
         () -> DSL.optionalFields(
               "minecraft:adventure/adventuring_time",
               DSL.optionalFields("criteria", DSL.compoundList(DataConverterTypes.z.in(var0), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_a_mob",
               DSL.optionalFields("criteria", DSL.compoundList(DataConverterTypes.o.in(var0), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_all_mobs",
               DSL.optionalFields("criteria", DSL.compoundList(DataConverterTypes.o.in(var0), DSL.constType(DSL.string()))),
               "minecraft:husbandry/bred_all_animals",
               DSL.optionalFields("criteria", DSL.compoundList(DataConverterTypes.o.in(var0), DSL.constType(DSL.string())))
            )
      );
      var0.registerType(false, DataConverterTypes.z, () -> DSL.constType(a()));
      var0.registerType(false, DataConverterTypes.o, () -> DSL.constType(a()));
      var0.registerType(false, DataConverterTypes.j, DSL::remainder);
      var0.registerType(false, DataConverterTypes.A, DSL::remainder);
      var0.registerType(false, DataConverterTypes.k, () -> DSL.optionalFields("Entities", DSL.list(DataConverterTypes.p.in(var0))));
   }
}
