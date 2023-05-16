package net.minecraft.util.datafix.schemas;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV704 extends Schema {
   protected static final Map<String, String> a = (Map<String, String>)DataFixUtils.make(() -> {
      Map<String, String> var0 = Maps.newHashMap();
      var0.put("minecraft:furnace", "minecraft:furnace");
      var0.put("minecraft:lit_furnace", "minecraft:furnace");
      var0.put("minecraft:chest", "minecraft:chest");
      var0.put("minecraft:trapped_chest", "minecraft:chest");
      var0.put("minecraft:ender_chest", "minecraft:ender_chest");
      var0.put("minecraft:jukebox", "minecraft:jukebox");
      var0.put("minecraft:dispenser", "minecraft:dispenser");
      var0.put("minecraft:dropper", "minecraft:dropper");
      var0.put("minecraft:sign", "minecraft:sign");
      var0.put("minecraft:mob_spawner", "minecraft:mob_spawner");
      var0.put("minecraft:spawner", "minecraft:mob_spawner");
      var0.put("minecraft:noteblock", "minecraft:noteblock");
      var0.put("minecraft:brewing_stand", "minecraft:brewing_stand");
      var0.put("minecraft:enhanting_table", "minecraft:enchanting_table");
      var0.put("minecraft:command_block", "minecraft:command_block");
      var0.put("minecraft:beacon", "minecraft:beacon");
      var0.put("minecraft:skull", "minecraft:skull");
      var0.put("minecraft:daylight_detector", "minecraft:daylight_detector");
      var0.put("minecraft:hopper", "minecraft:hopper");
      var0.put("minecraft:banner", "minecraft:banner");
      var0.put("minecraft:flower_pot", "minecraft:flower_pot");
      var0.put("minecraft:repeating_command_block", "minecraft:command_block");
      var0.put("minecraft:chain_command_block", "minecraft:command_block");
      var0.put("minecraft:shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:white_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:orange_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:magenta_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:light_blue_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:yellow_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:lime_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:pink_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:gray_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:silver_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:cyan_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:purple_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:blue_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:brown_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:green_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:red_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:black_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:bed", "minecraft:bed");
      var0.put("minecraft:light_gray_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:banner", "minecraft:banner");
      var0.put("minecraft:white_banner", "minecraft:banner");
      var0.put("minecraft:orange_banner", "minecraft:banner");
      var0.put("minecraft:magenta_banner", "minecraft:banner");
      var0.put("minecraft:light_blue_banner", "minecraft:banner");
      var0.put("minecraft:yellow_banner", "minecraft:banner");
      var0.put("minecraft:lime_banner", "minecraft:banner");
      var0.put("minecraft:pink_banner", "minecraft:banner");
      var0.put("minecraft:gray_banner", "minecraft:banner");
      var0.put("minecraft:silver_banner", "minecraft:banner");
      var0.put("minecraft:light_gray_banner", "minecraft:banner");
      var0.put("minecraft:cyan_banner", "minecraft:banner");
      var0.put("minecraft:purple_banner", "minecraft:banner");
      var0.put("minecraft:blue_banner", "minecraft:banner");
      var0.put("minecraft:brown_banner", "minecraft:banner");
      var0.put("minecraft:green_banner", "minecraft:banner");
      var0.put("minecraft:red_banner", "minecraft:banner");
      var0.put("minecraft:black_banner", "minecraft:banner");
      var0.put("minecraft:standing_sign", "minecraft:sign");
      var0.put("minecraft:wall_sign", "minecraft:sign");
      var0.put("minecraft:piston_head", "minecraft:piston");
      var0.put("minecraft:daylight_detector_inverted", "minecraft:daylight_detector");
      var0.put("minecraft:unpowered_comparator", "minecraft:comparator");
      var0.put("minecraft:powered_comparator", "minecraft:comparator");
      var0.put("minecraft:wall_banner", "minecraft:banner");
      var0.put("minecraft:standing_banner", "minecraft:banner");
      var0.put("minecraft:structure_block", "minecraft:structure_block");
      var0.put("minecraft:end_portal", "minecraft:end_portal");
      var0.put("minecraft:end_gateway", "minecraft:end_gateway");
      var0.put("minecraft:sign", "minecraft:sign");
      var0.put("minecraft:shield", "minecraft:banner");
      var0.put("minecraft:white_bed", "minecraft:bed");
      var0.put("minecraft:orange_bed", "minecraft:bed");
      var0.put("minecraft:magenta_bed", "minecraft:bed");
      var0.put("minecraft:light_blue_bed", "minecraft:bed");
      var0.put("minecraft:yellow_bed", "minecraft:bed");
      var0.put("minecraft:lime_bed", "minecraft:bed");
      var0.put("minecraft:pink_bed", "minecraft:bed");
      var0.put("minecraft:gray_bed", "minecraft:bed");
      var0.put("minecraft:silver_bed", "minecraft:bed");
      var0.put("minecraft:light_gray_bed", "minecraft:bed");
      var0.put("minecraft:cyan_bed", "minecraft:bed");
      var0.put("minecraft:purple_bed", "minecraft:bed");
      var0.put("minecraft:blue_bed", "minecraft:bed");
      var0.put("minecraft:brown_bed", "minecraft:bed");
      var0.put("minecraft:green_bed", "minecraft:bed");
      var0.put("minecraft:red_bed", "minecraft:bed");
      var0.put("minecraft:black_bed", "minecraft:bed");
      var0.put("minecraft:oak_sign", "minecraft:sign");
      var0.put("minecraft:spruce_sign", "minecraft:sign");
      var0.put("minecraft:birch_sign", "minecraft:sign");
      var0.put("minecraft:jungle_sign", "minecraft:sign");
      var0.put("minecraft:acacia_sign", "minecraft:sign");
      var0.put("minecraft:dark_oak_sign", "minecraft:sign");
      var0.put("minecraft:crimson_sign", "minecraft:sign");
      var0.put("minecraft:warped_sign", "minecraft:sign");
      var0.put("minecraft:skeleton_skull", "minecraft:skull");
      var0.put("minecraft:wither_skeleton_skull", "minecraft:skull");
      var0.put("minecraft:zombie_head", "minecraft:skull");
      var0.put("minecraft:player_head", "minecraft:skull");
      var0.put("minecraft:creeper_head", "minecraft:skull");
      var0.put("minecraft:dragon_head", "minecraft:skull");
      var0.put("minecraft:barrel", "minecraft:barrel");
      var0.put("minecraft:conduit", "minecraft:conduit");
      var0.put("minecraft:smoker", "minecraft:smoker");
      var0.put("minecraft:blast_furnace", "minecraft:blast_furnace");
      var0.put("minecraft:lectern", "minecraft:lectern");
      var0.put("minecraft:bell", "minecraft:bell");
      var0.put("minecraft:jigsaw", "minecraft:jigsaw");
      var0.put("minecraft:campfire", "minecraft:campfire");
      var0.put("minecraft:bee_nest", "minecraft:beehive");
      var0.put("minecraft:beehive", "minecraft:beehive");
      var0.put("minecraft:sculk_sensor", "minecraft:sculk_sensor");
      return ImmutableMap.copyOf(var0);
   });
   protected static final HookFunction b = new HookFunction() {
      public <T> T apply(DynamicOps<T> var0, T var1) {
         return DataConverterSchemaV99.a(new Dynamic(var0, var1), DataConverterSchemaV704.a, "ArmorStand");
      }
   };

   public DataConverterSchemaV704(int var0, Schema var1) {
      super(var0, var1);
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(var1, var2, () -> DSL.optionalFields("Items", DSL.list(DataConverterTypes.m.in(var0))));
   }

   public Type<?> getChoiceType(TypeReference var0, String var1) {
      return Objects.equals(var0.typeName(), DataConverterTypes.l.typeName())
         ? super.getChoiceType(var0, DataConverterSchemaNamed.a(var1))
         : super.getChoiceType(var0, var1);
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = Maps.newHashMap();
      a(var0, var1, "minecraft:furnace");
      a(var0, var1, "minecraft:chest");
      var0.registerSimple(var1, "minecraft:ender_chest");
      var0.register(var1, "minecraft:jukebox", var1x -> DSL.optionalFields("RecordItem", DataConverterTypes.m.in(var0)));
      a(var0, var1, "minecraft:dispenser");
      a(var0, var1, "minecraft:dropper");
      var0.registerSimple(var1, "minecraft:sign");
      var0.register(var1, "minecraft:mob_spawner", var1x -> DataConverterTypes.u.in(var0));
      var0.registerSimple(var1, "minecraft:noteblock");
      var0.registerSimple(var1, "minecraft:piston");
      a(var0, var1, "minecraft:brewing_stand");
      var0.registerSimple(var1, "minecraft:enchanting_table");
      var0.registerSimple(var1, "minecraft:end_portal");
      var0.registerSimple(var1, "minecraft:beacon");
      var0.registerSimple(var1, "minecraft:skull");
      var0.registerSimple(var1, "minecraft:daylight_detector");
      a(var0, var1, "minecraft:hopper");
      var0.registerSimple(var1, "minecraft:comparator");
      var0.register(var1, "minecraft:flower_pot", var1x -> DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), DataConverterTypes.s.in(var0))));
      var0.registerSimple(var1, "minecraft:banner");
      var0.registerSimple(var1, "minecraft:structure_block");
      var0.registerSimple(var1, "minecraft:end_gateway");
      var0.registerSimple(var1, "minecraft:command_block");
      return var1;
   }

   public void registerTypes(Schema var0, Map<String, Supplier<TypeTemplate>> var1, Map<String, Supplier<TypeTemplate>> var2) {
      super.registerTypes(var0, var1, var2);
      var0.registerType(false, DataConverterTypes.l, () -> DSL.taggedChoiceLazy("id", DataConverterSchemaNamed.a(), var2));
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
