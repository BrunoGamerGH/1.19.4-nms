package net.minecraft.util.datafix.schemas;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;
import org.slf4j.Logger;

public class DataConverterSchemaV99 extends Schema {
   private static final Logger b = LogUtils.getLogger();
   static final Map<String, String> c = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      var0.put("minecraft:furnace", "Furnace");
      var0.put("minecraft:lit_furnace", "Furnace");
      var0.put("minecraft:chest", "Chest");
      var0.put("minecraft:trapped_chest", "Chest");
      var0.put("minecraft:ender_chest", "EnderChest");
      var0.put("minecraft:jukebox", "RecordPlayer");
      var0.put("minecraft:dispenser", "Trap");
      var0.put("minecraft:dropper", "Dropper");
      var0.put("minecraft:sign", "Sign");
      var0.put("minecraft:mob_spawner", "MobSpawner");
      var0.put("minecraft:noteblock", "Music");
      var0.put("minecraft:brewing_stand", "Cauldron");
      var0.put("minecraft:enhanting_table", "EnchantTable");
      var0.put("minecraft:command_block", "CommandBlock");
      var0.put("minecraft:beacon", "Beacon");
      var0.put("minecraft:skull", "Skull");
      var0.put("minecraft:daylight_detector", "DLDetector");
      var0.put("minecraft:hopper", "Hopper");
      var0.put("minecraft:banner", "Banner");
      var0.put("minecraft:flower_pot", "FlowerPot");
      var0.put("minecraft:repeating_command_block", "CommandBlock");
      var0.put("minecraft:chain_command_block", "CommandBlock");
      var0.put("minecraft:standing_sign", "Sign");
      var0.put("minecraft:wall_sign", "Sign");
      var0.put("minecraft:piston_head", "Piston");
      var0.put("minecraft:daylight_detector_inverted", "DLDetector");
      var0.put("minecraft:unpowered_comparator", "Comparator");
      var0.put("minecraft:powered_comparator", "Comparator");
      var0.put("minecraft:wall_banner", "Banner");
      var0.put("minecraft:standing_banner", "Banner");
      var0.put("minecraft:structure_block", "Structure");
      var0.put("minecraft:end_portal", "Airportal");
      var0.put("minecraft:end_gateway", "EndGateway");
      var0.put("minecraft:shield", "Banner");
   });
   protected static final HookFunction a = new HookFunction() {
      public <T> T apply(DynamicOps<T> var0, T var1) {
         return DataConverterSchemaV99.a(new Dynamic(var0, var1), DataConverterSchemaV99.c, "ArmorStand");
      }
   };

   public DataConverterSchemaV99(int var0, Schema var1) {
      super(var0, var1);
   }

   protected static TypeTemplate a(Schema var0) {
      return DSL.optionalFields("Equipment", DSL.list(DataConverterTypes.m.in(var0)));
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(var1, var2, () -> a(var0));
   }

   protected static void b(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(var1, var2, () -> DSL.optionalFields("inTile", DataConverterTypes.r.in(var0)));
   }

   protected static void c(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(var1, var2, () -> DSL.optionalFields("DisplayTile", DataConverterTypes.r.in(var0)));
   }

   protected static void d(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(var1, var2, () -> DSL.optionalFields("Items", DSL.list(DataConverterTypes.m.in(var0))));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = Maps.newHashMap();
      var0.register(var1, "Item", var1x -> DSL.optionalFields("Item", DataConverterTypes.m.in(var0)));
      var0.registerSimple(var1, "XPOrb");
      b(var0, var1, "ThrownEgg");
      var0.registerSimple(var1, "LeashKnot");
      var0.registerSimple(var1, "Painting");
      var0.register(var1, "Arrow", var1x -> DSL.optionalFields("inTile", DataConverterTypes.r.in(var0)));
      var0.register(var1, "TippedArrow", var1x -> DSL.optionalFields("inTile", DataConverterTypes.r.in(var0)));
      var0.register(var1, "SpectralArrow", var1x -> DSL.optionalFields("inTile", DataConverterTypes.r.in(var0)));
      b(var0, var1, "Snowball");
      b(var0, var1, "Fireball");
      b(var0, var1, "SmallFireball");
      b(var0, var1, "ThrownEnderpearl");
      var0.registerSimple(var1, "EyeOfEnderSignal");
      var0.register(var1, "ThrownPotion", var1x -> DSL.optionalFields("inTile", DataConverterTypes.r.in(var0), "Potion", DataConverterTypes.m.in(var0)));
      b(var0, var1, "ThrownExpBottle");
      var0.register(var1, "ItemFrame", var1x -> DSL.optionalFields("Item", DataConverterTypes.m.in(var0)));
      b(var0, var1, "WitherSkull");
      var0.registerSimple(var1, "PrimedTnt");
      var0.register(var1, "FallingSand", var1x -> DSL.optionalFields("Block", DataConverterTypes.r.in(var0), "TileEntityData", DataConverterTypes.l.in(var0)));
      var0.register(var1, "FireworksRocketEntity", var1x -> DSL.optionalFields("FireworksItem", DataConverterTypes.m.in(var0)));
      var0.registerSimple(var1, "Boat");
      var0.register(var1, "Minecart", () -> DSL.optionalFields("DisplayTile", DataConverterTypes.r.in(var0), "Items", DSL.list(DataConverterTypes.m.in(var0))));
      c(var0, var1, "MinecartRideable");
      var0.register(
         var1, "MinecartChest", var1x -> DSL.optionalFields("DisplayTile", DataConverterTypes.r.in(var0), "Items", DSL.list(DataConverterTypes.m.in(var0)))
      );
      c(var0, var1, "MinecartFurnace");
      c(var0, var1, "MinecartTNT");
      var0.register(var1, "MinecartSpawner", () -> DSL.optionalFields("DisplayTile", DataConverterTypes.r.in(var0), DataConverterTypes.u.in(var0)));
      var0.register(
         var1, "MinecartHopper", var1x -> DSL.optionalFields("DisplayTile", DataConverterTypes.r.in(var0), "Items", DSL.list(DataConverterTypes.m.in(var0)))
      );
      c(var0, var1, "MinecartCommandBlock");
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
      var0.registerSimple(var1, "EnderCrystal");
      var0.registerSimple(var1, "AreaEffectCloud");
      var0.registerSimple(var1, "ShulkerBullet");
      a(var0, var1, "Shulker");
      return var1;
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = Maps.newHashMap();
      d(var0, var1, "Furnace");
      d(var0, var1, "Chest");
      var0.registerSimple(var1, "EnderChest");
      var0.register(var1, "RecordPlayer", var1x -> DSL.optionalFields("RecordItem", DataConverterTypes.m.in(var0)));
      d(var0, var1, "Trap");
      d(var0, var1, "Dropper");
      var0.registerSimple(var1, "Sign");
      var0.register(var1, "MobSpawner", var1x -> DataConverterTypes.u.in(var0));
      var0.registerSimple(var1, "Music");
      var0.registerSimple(var1, "Piston");
      d(var0, var1, "Cauldron");
      var0.registerSimple(var1, "EnchantTable");
      var0.registerSimple(var1, "Airportal");
      var0.registerSimple(var1, "Control");
      var0.registerSimple(var1, "Beacon");
      var0.registerSimple(var1, "Skull");
      var0.registerSimple(var1, "DLDetector");
      d(var0, var1, "Hopper");
      var0.registerSimple(var1, "Comparator");
      var0.register(var1, "FlowerPot", var1x -> DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), DataConverterTypes.s.in(var0))));
      var0.registerSimple(var1, "Banner");
      var0.registerSimple(var1, "Structure");
      var0.registerSimple(var1, "EndGateway");
      return var1;
   }

   public void registerTypes(Schema var0, Map<String, Supplier<TypeTemplate>> var1, Map<String, Supplier<TypeTemplate>> var2) {
      var0.registerType(false, DataConverterTypes.a, DSL::remainder);
      var0.registerType(
         false,
         DataConverterTypes.b,
         () -> DSL.optionalFields("Inventory", DSL.list(DataConverterTypes.m.in(var0)), "EnderItems", DSL.list(DataConverterTypes.m.in(var0)))
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
                  DSL.list(DSL.fields("i", DataConverterTypes.r.in(var0)))
               )
            )
      );
      var0.registerType(true, DataConverterTypes.l, () -> DSL.taggedChoiceLazy("id", DSL.string(), var2));
      var0.registerType(true, DataConverterTypes.p, () -> DSL.optionalFields("Riding", DataConverterTypes.p.in(var0), DataConverterTypes.q.in(var0)));
      var0.registerType(false, DataConverterTypes.o, () -> DSL.constType(DataConverterSchemaNamed.a()));
      var0.registerType(true, DataConverterTypes.q, () -> DSL.taggedChoiceLazy("id", DSL.string(), var1));
      var0.registerType(
         true,
         DataConverterTypes.m,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  DSL.or(DSL.constType(DSL.intType()), DataConverterTypes.s.in(var0)),
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
               a,
               HookFunction.IDENTITY
            )
      );
      var0.registerType(false, DataConverterTypes.e, DSL::remainder);
      var0.registerType(false, DataConverterTypes.r, () -> DSL.or(DSL.constType(DSL.intType()), DSL.constType(DataConverterSchemaNamed.a())));
      var0.registerType(false, DataConverterTypes.s, () -> DSL.constType(DataConverterSchemaNamed.a()));
      var0.registerType(false, DataConverterTypes.g, DSL::remainder);
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
      var0.registerType(false, DataConverterTypes.w, DSL::remainder);
      var0.registerType(false, DataConverterTypes.x, DSL::remainder);
      var0.registerType(true, DataConverterTypes.u, DSL::remainder);
      var0.registerType(false, DataConverterTypes.j, DSL::remainder);
      var0.registerType(false, DataConverterTypes.A, DSL::remainder);
      var0.registerType(false, DataConverterTypes.k, () -> DSL.optionalFields("Entities", DSL.list(DataConverterTypes.p.in(var0))));
   }

   protected static <T> T a(Dynamic<T> var0, Map<String, String> var1, String var2) {
      return (T)var0.update("tag", var3 -> var3.update("BlockEntityTag", var2xx -> {
            String var3x = var0.get("id").asString().result().map(DataConverterSchemaNamed::a).orElse("minecraft:air");
            if (!"minecraft:air".equals(var3x)) {
               String var4 = var1.get(var3x);
               if (var4 != null) {
                  return var2xx.set("id", var0.createString(var4));
               }

               b.warn("Unable to resolve BlockEntity for ItemStack: {}", var3x);
            }

            return var2xx;
         }).update("EntityTag", var2xx -> {
            String var3x = var0.get("id").asString("");
            return "minecraft:armor_stand".equals(DataConverterSchemaNamed.a(var3x)) ? var2xx.set("id", var0.createString(var2)) : var2xx;
         })).getValue();
   }
}
