package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterEntityBlockState extends DataFix {
   private static final Map<String, Integer> a = (Map<String, Integer>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      var0.put("minecraft:air", 0);
      var0.put("minecraft:stone", 1);
      var0.put("minecraft:grass", 2);
      var0.put("minecraft:dirt", 3);
      var0.put("minecraft:cobblestone", 4);
      var0.put("minecraft:planks", 5);
      var0.put("minecraft:sapling", 6);
      var0.put("minecraft:bedrock", 7);
      var0.put("minecraft:flowing_water", 8);
      var0.put("minecraft:water", 9);
      var0.put("minecraft:flowing_lava", 10);
      var0.put("minecraft:lava", 11);
      var0.put("minecraft:sand", 12);
      var0.put("minecraft:gravel", 13);
      var0.put("minecraft:gold_ore", 14);
      var0.put("minecraft:iron_ore", 15);
      var0.put("minecraft:coal_ore", 16);
      var0.put("minecraft:log", 17);
      var0.put("minecraft:leaves", 18);
      var0.put("minecraft:sponge", 19);
      var0.put("minecraft:glass", 20);
      var0.put("minecraft:lapis_ore", 21);
      var0.put("minecraft:lapis_block", 22);
      var0.put("minecraft:dispenser", 23);
      var0.put("minecraft:sandstone", 24);
      var0.put("minecraft:noteblock", 25);
      var0.put("minecraft:bed", 26);
      var0.put("minecraft:golden_rail", 27);
      var0.put("minecraft:detector_rail", 28);
      var0.put("minecraft:sticky_piston", 29);
      var0.put("minecraft:web", 30);
      var0.put("minecraft:tallgrass", 31);
      var0.put("minecraft:deadbush", 32);
      var0.put("minecraft:piston", 33);
      var0.put("minecraft:piston_head", 34);
      var0.put("minecraft:wool", 35);
      var0.put("minecraft:piston_extension", 36);
      var0.put("minecraft:yellow_flower", 37);
      var0.put("minecraft:red_flower", 38);
      var0.put("minecraft:brown_mushroom", 39);
      var0.put("minecraft:red_mushroom", 40);
      var0.put("minecraft:gold_block", 41);
      var0.put("minecraft:iron_block", 42);
      var0.put("minecraft:double_stone_slab", 43);
      var0.put("minecraft:stone_slab", 44);
      var0.put("minecraft:brick_block", 45);
      var0.put("minecraft:tnt", 46);
      var0.put("minecraft:bookshelf", 47);
      var0.put("minecraft:mossy_cobblestone", 48);
      var0.put("minecraft:obsidian", 49);
      var0.put("minecraft:torch", 50);
      var0.put("minecraft:fire", 51);
      var0.put("minecraft:mob_spawner", 52);
      var0.put("minecraft:oak_stairs", 53);
      var0.put("minecraft:chest", 54);
      var0.put("minecraft:redstone_wire", 55);
      var0.put("minecraft:diamond_ore", 56);
      var0.put("minecraft:diamond_block", 57);
      var0.put("minecraft:crafting_table", 58);
      var0.put("minecraft:wheat", 59);
      var0.put("minecraft:farmland", 60);
      var0.put("minecraft:furnace", 61);
      var0.put("minecraft:lit_furnace", 62);
      var0.put("minecraft:standing_sign", 63);
      var0.put("minecraft:wooden_door", 64);
      var0.put("minecraft:ladder", 65);
      var0.put("minecraft:rail", 66);
      var0.put("minecraft:stone_stairs", 67);
      var0.put("minecraft:wall_sign", 68);
      var0.put("minecraft:lever", 69);
      var0.put("minecraft:stone_pressure_plate", 70);
      var0.put("minecraft:iron_door", 71);
      var0.put("minecraft:wooden_pressure_plate", 72);
      var0.put("minecraft:redstone_ore", 73);
      var0.put("minecraft:lit_redstone_ore", 74);
      var0.put("minecraft:unlit_redstone_torch", 75);
      var0.put("minecraft:redstone_torch", 76);
      var0.put("minecraft:stone_button", 77);
      var0.put("minecraft:snow_layer", 78);
      var0.put("minecraft:ice", 79);
      var0.put("minecraft:snow", 80);
      var0.put("minecraft:cactus", 81);
      var0.put("minecraft:clay", 82);
      var0.put("minecraft:reeds", 83);
      var0.put("minecraft:jukebox", 84);
      var0.put("minecraft:fence", 85);
      var0.put("minecraft:pumpkin", 86);
      var0.put("minecraft:netherrack", 87);
      var0.put("minecraft:soul_sand", 88);
      var0.put("minecraft:glowstone", 89);
      var0.put("minecraft:portal", 90);
      var0.put("minecraft:lit_pumpkin", 91);
      var0.put("minecraft:cake", 92);
      var0.put("minecraft:unpowered_repeater", 93);
      var0.put("minecraft:powered_repeater", 94);
      var0.put("minecraft:stained_glass", 95);
      var0.put("minecraft:trapdoor", 96);
      var0.put("minecraft:monster_egg", 97);
      var0.put("minecraft:stonebrick", 98);
      var0.put("minecraft:brown_mushroom_block", 99);
      var0.put("minecraft:red_mushroom_block", 100);
      var0.put("minecraft:iron_bars", 101);
      var0.put("minecraft:glass_pane", 102);
      var0.put("minecraft:melon_block", 103);
      var0.put("minecraft:pumpkin_stem", 104);
      var0.put("minecraft:melon_stem", 105);
      var0.put("minecraft:vine", 106);
      var0.put("minecraft:fence_gate", 107);
      var0.put("minecraft:brick_stairs", 108);
      var0.put("minecraft:stone_brick_stairs", 109);
      var0.put("minecraft:mycelium", 110);
      var0.put("minecraft:waterlily", 111);
      var0.put("minecraft:nether_brick", 112);
      var0.put("minecraft:nether_brick_fence", 113);
      var0.put("minecraft:nether_brick_stairs", 114);
      var0.put("minecraft:nether_wart", 115);
      var0.put("minecraft:enchanting_table", 116);
      var0.put("minecraft:brewing_stand", 117);
      var0.put("minecraft:cauldron", 118);
      var0.put("minecraft:end_portal", 119);
      var0.put("minecraft:end_portal_frame", 120);
      var0.put("minecraft:end_stone", 121);
      var0.put("minecraft:dragon_egg", 122);
      var0.put("minecraft:redstone_lamp", 123);
      var0.put("minecraft:lit_redstone_lamp", 124);
      var0.put("minecraft:double_wooden_slab", 125);
      var0.put("minecraft:wooden_slab", 126);
      var0.put("minecraft:cocoa", 127);
      var0.put("minecraft:sandstone_stairs", 128);
      var0.put("minecraft:emerald_ore", 129);
      var0.put("minecraft:ender_chest", 130);
      var0.put("minecraft:tripwire_hook", 131);
      var0.put("minecraft:tripwire", 132);
      var0.put("minecraft:emerald_block", 133);
      var0.put("minecraft:spruce_stairs", 134);
      var0.put("minecraft:birch_stairs", 135);
      var0.put("minecraft:jungle_stairs", 136);
      var0.put("minecraft:command_block", 137);
      var0.put("minecraft:beacon", 138);
      var0.put("minecraft:cobblestone_wall", 139);
      var0.put("minecraft:flower_pot", 140);
      var0.put("minecraft:carrots", 141);
      var0.put("minecraft:potatoes", 142);
      var0.put("minecraft:wooden_button", 143);
      var0.put("minecraft:skull", 144);
      var0.put("minecraft:anvil", 145);
      var0.put("minecraft:trapped_chest", 146);
      var0.put("minecraft:light_weighted_pressure_plate", 147);
      var0.put("minecraft:heavy_weighted_pressure_plate", 148);
      var0.put("minecraft:unpowered_comparator", 149);
      var0.put("minecraft:powered_comparator", 150);
      var0.put("minecraft:daylight_detector", 151);
      var0.put("minecraft:redstone_block", 152);
      var0.put("minecraft:quartz_ore", 153);
      var0.put("minecraft:hopper", 154);
      var0.put("minecraft:quartz_block", 155);
      var0.put("minecraft:quartz_stairs", 156);
      var0.put("minecraft:activator_rail", 157);
      var0.put("minecraft:dropper", 158);
      var0.put("minecraft:stained_hardened_clay", 159);
      var0.put("minecraft:stained_glass_pane", 160);
      var0.put("minecraft:leaves2", 161);
      var0.put("minecraft:log2", 162);
      var0.put("minecraft:acacia_stairs", 163);
      var0.put("minecraft:dark_oak_stairs", 164);
      var0.put("minecraft:slime", 165);
      var0.put("minecraft:barrier", 166);
      var0.put("minecraft:iron_trapdoor", 167);
      var0.put("minecraft:prismarine", 168);
      var0.put("minecraft:sea_lantern", 169);
      var0.put("minecraft:hay_block", 170);
      var0.put("minecraft:carpet", 171);
      var0.put("minecraft:hardened_clay", 172);
      var0.put("minecraft:coal_block", 173);
      var0.put("minecraft:packed_ice", 174);
      var0.put("minecraft:double_plant", 175);
      var0.put("minecraft:standing_banner", 176);
      var0.put("minecraft:wall_banner", 177);
      var0.put("minecraft:daylight_detector_inverted", 178);
      var0.put("minecraft:red_sandstone", 179);
      var0.put("minecraft:red_sandstone_stairs", 180);
      var0.put("minecraft:double_stone_slab2", 181);
      var0.put("minecraft:stone_slab2", 182);
      var0.put("minecraft:spruce_fence_gate", 183);
      var0.put("minecraft:birch_fence_gate", 184);
      var0.put("minecraft:jungle_fence_gate", 185);
      var0.put("minecraft:dark_oak_fence_gate", 186);
      var0.put("minecraft:acacia_fence_gate", 187);
      var0.put("minecraft:spruce_fence", 188);
      var0.put("minecraft:birch_fence", 189);
      var0.put("minecraft:jungle_fence", 190);
      var0.put("minecraft:dark_oak_fence", 191);
      var0.put("minecraft:acacia_fence", 192);
      var0.put("minecraft:spruce_door", 193);
      var0.put("minecraft:birch_door", 194);
      var0.put("minecraft:jungle_door", 195);
      var0.put("minecraft:acacia_door", 196);
      var0.put("minecraft:dark_oak_door", 197);
      var0.put("minecraft:end_rod", 198);
      var0.put("minecraft:chorus_plant", 199);
      var0.put("minecraft:chorus_flower", 200);
      var0.put("minecraft:purpur_block", 201);
      var0.put("minecraft:purpur_pillar", 202);
      var0.put("minecraft:purpur_stairs", 203);
      var0.put("minecraft:purpur_double_slab", 204);
      var0.put("minecraft:purpur_slab", 205);
      var0.put("minecraft:end_bricks", 206);
      var0.put("minecraft:beetroots", 207);
      var0.put("minecraft:grass_path", 208);
      var0.put("minecraft:end_gateway", 209);
      var0.put("minecraft:repeating_command_block", 210);
      var0.put("minecraft:chain_command_block", 211);
      var0.put("minecraft:frosted_ice", 212);
      var0.put("minecraft:magma", 213);
      var0.put("minecraft:nether_wart_block", 214);
      var0.put("minecraft:red_nether_brick", 215);
      var0.put("minecraft:bone_block", 216);
      var0.put("minecraft:structure_void", 217);
      var0.put("minecraft:observer", 218);
      var0.put("minecraft:white_shulker_box", 219);
      var0.put("minecraft:orange_shulker_box", 220);
      var0.put("minecraft:magenta_shulker_box", 221);
      var0.put("minecraft:light_blue_shulker_box", 222);
      var0.put("minecraft:yellow_shulker_box", 223);
      var0.put("minecraft:lime_shulker_box", 224);
      var0.put("minecraft:pink_shulker_box", 225);
      var0.put("minecraft:gray_shulker_box", 226);
      var0.put("minecraft:silver_shulker_box", 227);
      var0.put("minecraft:cyan_shulker_box", 228);
      var0.put("minecraft:purple_shulker_box", 229);
      var0.put("minecraft:blue_shulker_box", 230);
      var0.put("minecraft:brown_shulker_box", 231);
      var0.put("minecraft:green_shulker_box", 232);
      var0.put("minecraft:red_shulker_box", 233);
      var0.put("minecraft:black_shulker_box", 234);
      var0.put("minecraft:white_glazed_terracotta", 235);
      var0.put("minecraft:orange_glazed_terracotta", 236);
      var0.put("minecraft:magenta_glazed_terracotta", 237);
      var0.put("minecraft:light_blue_glazed_terracotta", 238);
      var0.put("minecraft:yellow_glazed_terracotta", 239);
      var0.put("minecraft:lime_glazed_terracotta", 240);
      var0.put("minecraft:pink_glazed_terracotta", 241);
      var0.put("minecraft:gray_glazed_terracotta", 242);
      var0.put("minecraft:silver_glazed_terracotta", 243);
      var0.put("minecraft:cyan_glazed_terracotta", 244);
      var0.put("minecraft:purple_glazed_terracotta", 245);
      var0.put("minecraft:blue_glazed_terracotta", 246);
      var0.put("minecraft:brown_glazed_terracotta", 247);
      var0.put("minecraft:green_glazed_terracotta", 248);
      var0.put("minecraft:red_glazed_terracotta", 249);
      var0.put("minecraft:black_glazed_terracotta", 250);
      var0.put("minecraft:concrete", 251);
      var0.put("minecraft:concrete_powder", 252);
      var0.put("minecraft:structure_block", 255);
   });

   public DataConverterEntityBlockState(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public static int a(String var0) {
      Integer var1 = a.get(var0);
      return var1 == null ? 0 : var1;
   }

   public TypeRewriteRule makeRule() {
      Schema var0 = this.getInputSchema();
      Schema var1 = this.getOutputSchema();
      Function<Typed<?>, Typed<?>> var2 = var0x -> this.a(var0x, "DisplayTile", "DisplayData", "DisplayState");
      Function<Typed<?>, Typed<?>> var3 = var0x -> this.a(var0x, "inTile", "inData", "inBlockState");
      Type<Pair<Either<Pair<String, Either<Integer, String>>, Unit>, Dynamic<?>>> var4 = DSL.and(
         DSL.optional(DSL.field("inTile", DSL.named(DataConverterTypes.r.typeName(), DSL.or(DSL.intType(), DataConverterSchemaNamed.a())))),
         DSL.remainderType()
      );
      Function<Typed<?>, Typed<?>> var5 = var1x -> var1x.update(var4.finder(), DSL.remainderType(), Pair::getSecond);
      return this.fixTypeEverywhereTyped("EntityBlockStateFix", var0.getType(DataConverterTypes.q), var1.getType(DataConverterTypes.q), var3x -> {
         var3x = this.a(var3x, "minecraft:falling_block", this::a);
         var3x = this.a(var3x, "minecraft:enderman", var0xx -> this.a(var0xx, "carried", "carriedData", "carriedBlockState"));
         var3x = this.a(var3x, "minecraft:arrow", var3);
         var3x = this.a(var3x, "minecraft:spectral_arrow", var3);
         var3x = this.a(var3x, "minecraft:egg", var5);
         var3x = this.a(var3x, "minecraft:ender_pearl", var5);
         var3x = this.a(var3x, "minecraft:fireball", var5);
         var3x = this.a(var3x, "minecraft:potion", var5);
         var3x = this.a(var3x, "minecraft:small_fireball", var5);
         var3x = this.a(var3x, "minecraft:snowball", var5);
         var3x = this.a(var3x, "minecraft:wither_skull", var5);
         var3x = this.a(var3x, "minecraft:xp_bottle", var5);
         var3x = this.a(var3x, "minecraft:commandblock_minecart", var2);
         var3x = this.a(var3x, "minecraft:minecart", var2);
         var3x = this.a(var3x, "minecraft:chest_minecart", var2);
         var3x = this.a(var3x, "minecraft:furnace_minecart", var2);
         var3x = this.a(var3x, "minecraft:tnt_minecart", var2);
         var3x = this.a(var3x, "minecraft:hopper_minecart", var2);
         return this.a(var3x, "minecraft:spawner_minecart", var2);
      });
   }

   private Typed<?> a(Typed<?> var0) {
      Type<Either<Pair<String, Either<Integer, String>>, Unit>> var1 = DSL.optional(
         DSL.field("Block", DSL.named(DataConverterTypes.r.typeName(), DSL.or(DSL.intType(), DataConverterSchemaNamed.a())))
      );
      Type<Either<Pair<String, Dynamic<?>>, Unit>> var2 = DSL.optional(
         DSL.field("BlockState", DSL.named(DataConverterTypes.n.typeName(), DSL.remainderType()))
      );
      Dynamic<?> var3 = (Dynamic)var0.get(DSL.remainderFinder());
      return var0.update(var1.finder(), var2, var1x -> {
         int var2x = var1x.map(var0xx -> (Integer)((Either)var0xx.getSecond()).map(var0xxx -> var0xxx, DataConverterEntityBlockState::a), var1xx -> {
            Optional<Number> var2xx = var3.get("TileID").asNumber().result();
            return var2xx.map(Number::intValue).orElseGet(() -> var3.get("Tile").asByte((byte)0) & 0xFF);
         });
         int var3x = var3.get("Data").asInt(0) & 15;
         return Either.left(Pair.of(DataConverterTypes.n.typeName(), DataConverterFlattenData.b(var2x << 4 | var3x)));
      }).set(DSL.remainderFinder(), var3.remove("Data").remove("TileID").remove("Tile"));
   }

   private Typed<?> a(Typed<?> var0, String var1, String var2, String var3) {
      Type<Pair<String, Either<Integer, String>>> var4 = DSL.field(
         var1, DSL.named(DataConverterTypes.r.typeName(), DSL.or(DSL.intType(), DataConverterSchemaNamed.a()))
      );
      Type<Pair<String, Dynamic<?>>> var5 = DSL.field(var3, DSL.named(DataConverterTypes.n.typeName(), DSL.remainderType()));
      Dynamic<?> var6 = (Dynamic)var0.getOrCreate(DSL.remainderFinder());
      return var0.update(var4.finder(), var5, var2x -> {
         int var3x = ((Either)var2x.getSecond()).map(var0xx -> var0xx, DataConverterEntityBlockState::a);
         int var4x = var6.get(var2).asInt(0) & 15;
         return Pair.of(DataConverterTypes.n.typeName(), DataConverterFlattenData.b(var3x << 4 | var4x));
      }).set(DSL.remainderFinder(), var6.remove(var2));
   }

   private Typed<?> a(Typed<?> var0, String var1, Function<Typed<?>, Typed<?>> var2) {
      Type<?> var3 = this.getInputSchema().getChoiceType(DataConverterTypes.q, var1);
      Type<?> var4 = this.getOutputSchema().getChoiceType(DataConverterTypes.q, var1);
      return var0.updateTyped(DSL.namedChoice(var1, var3), var4, var2);
   }
}
