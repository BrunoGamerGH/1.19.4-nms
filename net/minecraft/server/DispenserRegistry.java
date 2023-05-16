package net.minecraft.server;

import com.mojang.logging.LogUtils;
import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.SharedConstants;
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.commands.arguments.selector.options.PlayerSelector;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.IDispenseBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.locale.LocaleLanguage;
import net.minecraft.util.datafix.fixes.DataConverterFlattenData;
import net.minecraft.util.datafix.fixes.DataConverterMaterialId;
import net.minecraft.util.datafix.fixes.DataConverterSpawnEgg;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeDefaults;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionBrewer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockComposter;
import net.minecraft.world.level.block.BlockFire;
import org.slf4j.Logger;

public class DispenserRegistry {
   public static final PrintStream a = System.out;
   private static volatile boolean b;
   private static final Logger c = LogUtils.getLogger();

   public static void a() {
      if (!b) {
         label27: {
            String name = DispenserRegistry.class.getSimpleName();
            switch(name.hashCode()) {
               case -1384613390:
                  if (name.equals("DispenserRegistry")) {
                     break label27;
                  }
                  break;
               case 9956942:
                  if (name.equals("Bootstrap")) {
                     System.err.println("***************************************************************************");
                     System.err.println("*** WARNING: This server jar may only be used for development purposes. ***");
                     System.err.println("***************************************************************************");
                     break label27;
                  }
            }

            System.err.println("**********************************************************************");
            System.err.println("*** WARNING: This server jar is unsupported, use at your own risk. ***");
            System.err.println("**********************************************************************");
         }

         b = true;
         if (BuiltInRegistries.an.e().isEmpty()) {
            throw new IllegalStateException("Unable to load registries");
         }

         BlockFire.b();
         BlockComposter.b();
         if (EntityTypes.a(EntityTypes.bt) == null) {
            throw new IllegalStateException("Failed loading EntityTypes");
         }

         PotionBrewer.a();
         PlayerSelector.a();
         IDispenseBehavior.c();
         CauldronInteraction.b();
         BuiltInRegistries.a();
         d();
         DataConverterFlattenData.a(
            1008, "{Name:'minecraft:oak_sign',Properties:{rotation:'0'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'0'}}"
         );
         DataConverterFlattenData.a(
            1009, "{Name:'minecraft:oak_sign',Properties:{rotation:'1'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'1'}}"
         );
         DataConverterFlattenData.a(
            1010, "{Name:'minecraft:oak_sign',Properties:{rotation:'2'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'2'}}"
         );
         DataConverterFlattenData.a(
            1011, "{Name:'minecraft:oak_sign',Properties:{rotation:'3'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'3'}}"
         );
         DataConverterFlattenData.a(
            1012, "{Name:'minecraft:oak_sign',Properties:{rotation:'4'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'4'}}"
         );
         DataConverterFlattenData.a(
            1013, "{Name:'minecraft:oak_sign',Properties:{rotation:'5'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'5'}}"
         );
         DataConverterFlattenData.a(
            1014, "{Name:'minecraft:oak_sign',Properties:{rotation:'6'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'6'}}"
         );
         DataConverterFlattenData.a(
            1015, "{Name:'minecraft:oak_sign',Properties:{rotation:'7'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'7'}}"
         );
         DataConverterFlattenData.a(
            1016, "{Name:'minecraft:oak_sign',Properties:{rotation:'8'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'8'}}"
         );
         DataConverterFlattenData.a(
            1017, "{Name:'minecraft:oak_sign',Properties:{rotation:'9'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'9'}}"
         );
         DataConverterFlattenData.a(
            1018, "{Name:'minecraft:oak_sign',Properties:{rotation:'10'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'10'}}"
         );
         DataConverterFlattenData.a(
            1019, "{Name:'minecraft:oak_sign',Properties:{rotation:'11'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'11'}}"
         );
         DataConverterFlattenData.a(
            1020, "{Name:'minecraft:oak_sign',Properties:{rotation:'12'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'12'}}"
         );
         DataConverterFlattenData.a(
            1021, "{Name:'minecraft:oak_sign',Properties:{rotation:'13'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'13'}}"
         );
         DataConverterFlattenData.a(
            1022, "{Name:'minecraft:oak_sign',Properties:{rotation:'14'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'14'}}"
         );
         DataConverterFlattenData.a(
            1023, "{Name:'minecraft:oak_sign',Properties:{rotation:'15'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'15'}}"
         );
         DataConverterMaterialId.a.put(323, "minecraft:oak_sign");
         DataConverterFlattenData.a(1440, "{Name:'minecraft:portal',Properties:{axis:'x'}}", "{Name:'minecraft:portal',Properties:{axis:'x'}}");
         DataConverterMaterialId.a.put(409, "minecraft:prismarine_shard");
         DataConverterMaterialId.a.put(410, "minecraft:prismarine_crystals");
         DataConverterMaterialId.a.put(411, "minecraft:rabbit");
         DataConverterMaterialId.a.put(412, "minecraft:cooked_rabbit");
         DataConverterMaterialId.a.put(413, "minecraft:rabbit_stew");
         DataConverterMaterialId.a.put(414, "minecraft:rabbit_foot");
         DataConverterMaterialId.a.put(415, "minecraft:rabbit_hide");
         DataConverterMaterialId.a.put(416, "minecraft:armor_stand");
         DataConverterMaterialId.a.put(423, "minecraft:mutton");
         DataConverterMaterialId.a.put(424, "minecraft:cooked_mutton");
         DataConverterMaterialId.a.put(425, "minecraft:banner");
         DataConverterMaterialId.a.put(426, "minecraft:end_crystal");
         DataConverterMaterialId.a.put(427, "minecraft:spruce_door");
         DataConverterMaterialId.a.put(428, "minecraft:birch_door");
         DataConverterMaterialId.a.put(429, "minecraft:jungle_door");
         DataConverterMaterialId.a.put(430, "minecraft:acacia_door");
         DataConverterMaterialId.a.put(431, "minecraft:dark_oak_door");
         DataConverterMaterialId.a.put(432, "minecraft:chorus_fruit");
         DataConverterMaterialId.a.put(433, "minecraft:chorus_fruit_popped");
         DataConverterMaterialId.a.put(434, "minecraft:beetroot");
         DataConverterMaterialId.a.put(435, "minecraft:beetroot_seeds");
         DataConverterMaterialId.a.put(436, "minecraft:beetroot_soup");
         DataConverterMaterialId.a.put(437, "minecraft:dragon_breath");
         DataConverterMaterialId.a.put(438, "minecraft:splash_potion");
         DataConverterMaterialId.a.put(439, "minecraft:spectral_arrow");
         DataConverterMaterialId.a.put(440, "minecraft:tipped_arrow");
         DataConverterMaterialId.a.put(441, "minecraft:lingering_potion");
         DataConverterMaterialId.a.put(442, "minecraft:shield");
         DataConverterMaterialId.a.put(443, "minecraft:elytra");
         DataConverterMaterialId.a.put(444, "minecraft:spruce_boat");
         DataConverterMaterialId.a.put(445, "minecraft:birch_boat");
         DataConverterMaterialId.a.put(446, "minecraft:jungle_boat");
         DataConverterMaterialId.a.put(447, "minecraft:acacia_boat");
         DataConverterMaterialId.a.put(448, "minecraft:dark_oak_boat");
         DataConverterMaterialId.a.put(449, "minecraft:totem_of_undying");
         DataConverterMaterialId.a.put(450, "minecraft:shulker_shell");
         DataConverterMaterialId.a.put(452, "minecraft:iron_nugget");
         DataConverterMaterialId.a.put(453, "minecraft:knowledge_book");
         DataConverterSpawnEgg.a[23] = "Arrow";
      }
   }

   private static <T> void a(Iterable<T> iterable, Function<T, String> function, Set<String> set) {
      LocaleLanguage localelanguage = LocaleLanguage.a();
      iterable.forEach(object -> {
         String s = function.apply(object);
         if (!localelanguage.b(s)) {
            set.add(s);
         }
      });
   }

   private static void a(final Set<String> set) {
      final LocaleLanguage localelanguage = LocaleLanguage.a();
      GameRules.a(
         new GameRules.GameRuleVisitor() {
            @Override
            public <T extends GameRules.GameRuleValue<T>> void a(
               GameRules.GameRuleKey<T> gamerules_gamerulekey, GameRules.GameRuleDefinition<T> gamerules_gameruledefinition
            ) {
               if (!localelanguage.b(gamerules_gamerulekey.b())) {
                  set.add(gamerules_gamerulekey.a());
               }
            }
         }
      );
   }

   public static Set<String> b() {
      Set<String> set = new TreeSet<>();
      a(BuiltInRegistries.u, AttributeBase::c, set);
      a(BuiltInRegistries.h, EntityTypes::g, set);
      a(BuiltInRegistries.e, MobEffectList::d, set);
      a(BuiltInRegistries.i, Item::a, set);
      a(BuiltInRegistries.g, Enchantment::g, set);
      a(BuiltInRegistries.f, Block::h, set);
      a(BuiltInRegistries.n, minecraftkey -> {
         String s = minecraftkey.toString();
         return "stat." + s.replace(':', '.');
      }, set);
      a(set);
      return set;
   }

   public static void a(Supplier<String> supplier) {
      if (!b) {
         throw b(supplier);
      }
   }

   private static RuntimeException b(Supplier<String> supplier) {
      try {
         String s = supplier.get();
         return new IllegalArgumentException("Not bootstrapped (called from " + s + ")");
      } catch (Exception var3) {
         IllegalArgumentException illegalargumentexception = new IllegalArgumentException("Not bootstrapped (failed to resolve location)");
         illegalargumentexception.addSuppressed(var3);
         return illegalargumentexception;
      }
   }

   public static void c() {
      a(() -> "validate");
      if (SharedConstants.aO) {
         b().forEach(s -> c.error("Missing translations: {}", s));
         CommandDispatcher.b();
      }

      AttributeDefaults.a();
   }

   private static void d() {
      if (c.isDebugEnabled()) {
         System.setErr(new DebugOutputStream("STDERR", System.err));
         System.setOut(new DebugOutputStream("STDOUT", a));
      } else {
         System.setErr(new RedirectStream("STDERR", System.err));
         System.setOut(new RedirectStream("STDOUT", a));
      }
   }

   public static void a(String s) {
      a.println(s);
   }
}
