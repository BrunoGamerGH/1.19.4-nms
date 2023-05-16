package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.RegistryID;
import net.minecraft.util.datafix.DataBitsPacked;
import org.slf4j.Logger;

public class ChunkConverterPalette extends DataFix {
   private static final int a = 128;
   private static final int b = 64;
   private static final int c = 32;
   private static final int d = 16;
   private static final int e = 8;
   private static final int f = 4;
   private static final int g = 2;
   private static final int h = 1;
   static final Logger i = LogUtils.getLogger();
   static final BitSet j = new BitSet(256);
   static final BitSet k = new BitSet(256);
   static final Dynamic<?> l = DataConverterFlattenData.b("{Name:'minecraft:pumpkin'}");
   static final Dynamic<?> m = DataConverterFlattenData.b("{Name:'minecraft:podzol',Properties:{snowy:'true'}}");
   static final Dynamic<?> n = DataConverterFlattenData.b("{Name:'minecraft:grass_block',Properties:{snowy:'true'}}");
   static final Dynamic<?> o = DataConverterFlattenData.b("{Name:'minecraft:mycelium',Properties:{snowy:'true'}}");
   static final Dynamic<?> p = DataConverterFlattenData.b("{Name:'minecraft:sunflower',Properties:{half:'upper'}}");
   static final Dynamic<?> q = DataConverterFlattenData.b("{Name:'minecraft:lilac',Properties:{half:'upper'}}");
   static final Dynamic<?> r = DataConverterFlattenData.b("{Name:'minecraft:tall_grass',Properties:{half:'upper'}}");
   static final Dynamic<?> s = DataConverterFlattenData.b("{Name:'minecraft:large_fern',Properties:{half:'upper'}}");
   static final Dynamic<?> t = DataConverterFlattenData.b("{Name:'minecraft:rose_bush',Properties:{half:'upper'}}");
   static final Dynamic<?> u = DataConverterFlattenData.b("{Name:'minecraft:peony',Properties:{half:'upper'}}");
   static final Map<String, Dynamic<?>> v = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      var0.put("minecraft:air0", DataConverterFlattenData.b("{Name:'minecraft:flower_pot'}"));
      var0.put("minecraft:red_flower0", DataConverterFlattenData.b("{Name:'minecraft:potted_poppy'}"));
      var0.put("minecraft:red_flower1", DataConverterFlattenData.b("{Name:'minecraft:potted_blue_orchid'}"));
      var0.put("minecraft:red_flower2", DataConverterFlattenData.b("{Name:'minecraft:potted_allium'}"));
      var0.put("minecraft:red_flower3", DataConverterFlattenData.b("{Name:'minecraft:potted_azure_bluet'}"));
      var0.put("minecraft:red_flower4", DataConverterFlattenData.b("{Name:'minecraft:potted_red_tulip'}"));
      var0.put("minecraft:red_flower5", DataConverterFlattenData.b("{Name:'minecraft:potted_orange_tulip'}"));
      var0.put("minecraft:red_flower6", DataConverterFlattenData.b("{Name:'minecraft:potted_white_tulip'}"));
      var0.put("minecraft:red_flower7", DataConverterFlattenData.b("{Name:'minecraft:potted_pink_tulip'}"));
      var0.put("minecraft:red_flower8", DataConverterFlattenData.b("{Name:'minecraft:potted_oxeye_daisy'}"));
      var0.put("minecraft:yellow_flower0", DataConverterFlattenData.b("{Name:'minecraft:potted_dandelion'}"));
      var0.put("minecraft:sapling0", DataConverterFlattenData.b("{Name:'minecraft:potted_oak_sapling'}"));
      var0.put("minecraft:sapling1", DataConverterFlattenData.b("{Name:'minecraft:potted_spruce_sapling'}"));
      var0.put("minecraft:sapling2", DataConverterFlattenData.b("{Name:'minecraft:potted_birch_sapling'}"));
      var0.put("minecraft:sapling3", DataConverterFlattenData.b("{Name:'minecraft:potted_jungle_sapling'}"));
      var0.put("minecraft:sapling4", DataConverterFlattenData.b("{Name:'minecraft:potted_acacia_sapling'}"));
      var0.put("minecraft:sapling5", DataConverterFlattenData.b("{Name:'minecraft:potted_dark_oak_sapling'}"));
      var0.put("minecraft:red_mushroom0", DataConverterFlattenData.b("{Name:'minecraft:potted_red_mushroom'}"));
      var0.put("minecraft:brown_mushroom0", DataConverterFlattenData.b("{Name:'minecraft:potted_brown_mushroom'}"));
      var0.put("minecraft:deadbush0", DataConverterFlattenData.b("{Name:'minecraft:potted_dead_bush'}"));
      var0.put("minecraft:tallgrass2", DataConverterFlattenData.b("{Name:'minecraft:potted_fern'}"));
      var0.put("minecraft:cactus0", DataConverterFlattenData.b(2240));
   });
   static final Map<String, Dynamic<?>> w = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      a(var0, 0, "skeleton", "skull");
      a(var0, 1, "wither_skeleton", "skull");
      a(var0, 2, "zombie", "head");
      a(var0, 3, "player", "head");
      a(var0, 4, "creeper", "head");
      a(var0, 5, "dragon", "head");
   });
   static final Map<String, Dynamic<?>> x = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      a(var0, "oak_door", 1024);
      a(var0, "iron_door", 1136);
      a(var0, "spruce_door", 3088);
      a(var0, "birch_door", 3104);
      a(var0, "jungle_door", 3120);
      a(var0, "acacia_door", 3136);
      a(var0, "dark_oak_door", 3152);
   });
   static final Map<String, Dynamic<?>> y = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      for(int var1 = 0; var1 < 26; ++var1) {
         var0.put("true" + var1, DataConverterFlattenData.b("{Name:'minecraft:note_block',Properties:{powered:'true',note:'" + var1 + "'}}"));
         var0.put("false" + var1, DataConverterFlattenData.b("{Name:'minecraft:note_block',Properties:{powered:'false',note:'" + var1 + "'}}"));
      }
   });
   private static final Int2ObjectMap<String> z = (Int2ObjectMap<String>)DataFixUtils.make(new Int2ObjectOpenHashMap(), var0 -> {
      var0.put(0, "white");
      var0.put(1, "orange");
      var0.put(2, "magenta");
      var0.put(3, "light_blue");
      var0.put(4, "yellow");
      var0.put(5, "lime");
      var0.put(6, "pink");
      var0.put(7, "gray");
      var0.put(8, "light_gray");
      var0.put(9, "cyan");
      var0.put(10, "purple");
      var0.put(11, "blue");
      var0.put(12, "brown");
      var0.put(13, "green");
      var0.put(14, "red");
      var0.put(15, "black");
   });
   static final Map<String, Dynamic<?>> A = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      ObjectIterator var1 = z.int2ObjectEntrySet().iterator();

      while(var1.hasNext()) {
         Entry<String> var2 = (Entry)var1.next();
         if (!Objects.equals(var2.getValue(), "red")) {
            a(var0, var2.getIntKey(), (String)var2.getValue());
         }
      }
   });
   static final Map<String, Dynamic<?>> B = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      ObjectIterator var1 = z.int2ObjectEntrySet().iterator();

      while(var1.hasNext()) {
         Entry<String> var2 = (Entry)var1.next();
         if (!Objects.equals(var2.getValue(), "white")) {
            b(var0, 15 - var2.getIntKey(), (String)var2.getValue());
         }
      }
   });
   static final Dynamic<?> C = DataConverterFlattenData.b(0);
   private static final int D = 4096;

   public ChunkConverterPalette(Schema var0, boolean var1) {
      super(var0, var1);
   }

   private static void a(Map<String, Dynamic<?>> var0, int var1, String var2, String var3) {
      var0.put(var1 + "north", DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_wall_" + var3 + "',Properties:{facing:'north'}}"));
      var0.put(var1 + "east", DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_wall_" + var3 + "',Properties:{facing:'east'}}"));
      var0.put(var1 + "south", DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_wall_" + var3 + "',Properties:{facing:'south'}}"));
      var0.put(var1 + "west", DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_wall_" + var3 + "',Properties:{facing:'west'}}"));

      for(int var4 = 0; var4 < 16; ++var4) {
         var0.put("" + var1 + var4, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_" + var3 + "',Properties:{rotation:'" + var4 + "'}}"));
      }
   }

   private static void a(Map<String, Dynamic<?>> var0, String var1, int var2) {
      var0.put(
         "minecraft:" + var1 + "eastlowerleftfalsefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "eastlowerleftfalsetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "eastlowerlefttruefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "eastlowerlefttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      var0.put("minecraft:" + var1 + "eastlowerrightfalsefalse", DataConverterFlattenData.b(var2));
      var0.put(
         "minecraft:" + var1 + "eastlowerrightfalsetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      var0.put("minecraft:" + var1 + "eastlowerrighttruefalse", DataConverterFlattenData.b(var2 + 4));
      var0.put(
         "minecraft:" + var1 + "eastlowerrighttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      var0.put("minecraft:" + var1 + "eastupperleftfalsefalse", DataConverterFlattenData.b(var2 + 8));
      var0.put("minecraft:" + var1 + "eastupperleftfalsetrue", DataConverterFlattenData.b(var2 + 10));
      var0.put(
         "minecraft:" + var1 + "eastupperlefttruefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "eastupperlefttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      var0.put("minecraft:" + var1 + "eastupperrightfalsefalse", DataConverterFlattenData.b(var2 + 9));
      var0.put("minecraft:" + var1 + "eastupperrightfalsetrue", DataConverterFlattenData.b(var2 + 11));
      var0.put(
         "minecraft:" + var1 + "eastupperrighttruefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "eastupperrighttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "northlowerleftfalsefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "northlowerleftfalsetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "northlowerlefttruefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "northlowerlefttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      var0.put("minecraft:" + var1 + "northlowerrightfalsefalse", DataConverterFlattenData.b(var2 + 3));
      var0.put(
         "minecraft:" + var1 + "northlowerrightfalsetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      var0.put("minecraft:" + var1 + "northlowerrighttruefalse", DataConverterFlattenData.b(var2 + 7));
      var0.put(
         "minecraft:" + var1 + "northlowerrighttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "northupperleftfalsefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "northupperleftfalsetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "northupperlefttruefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "northupperlefttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "northupperrightfalsefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "northupperrightfalsetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "northupperrighttruefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "northupperrighttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "southlowerleftfalsefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "southlowerleftfalsetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "southlowerlefttruefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "southlowerlefttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      var0.put("minecraft:" + var1 + "southlowerrightfalsefalse", DataConverterFlattenData.b(var2 + 1));
      var0.put(
         "minecraft:" + var1 + "southlowerrightfalsetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      var0.put("minecraft:" + var1 + "southlowerrighttruefalse", DataConverterFlattenData.b(var2 + 5));
      var0.put(
         "minecraft:" + var1 + "southlowerrighttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "southupperleftfalsefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "southupperleftfalsetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "southupperlefttruefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "southupperlefttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "southupperrightfalsefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "southupperrightfalsetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "southupperrighttruefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "southupperrighttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "westlowerleftfalsefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "westlowerleftfalsetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "westlowerlefttruefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "westlowerlefttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      var0.put("minecraft:" + var1 + "westlowerrightfalsefalse", DataConverterFlattenData.b(var2 + 2));
      var0.put(
         "minecraft:" + var1 + "westlowerrightfalsetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      var0.put("minecraft:" + var1 + "westlowerrighttruefalse", DataConverterFlattenData.b(var2 + 6));
      var0.put(
         "minecraft:" + var1 + "westlowerrighttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "westupperleftfalsefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "westupperleftfalsetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "westupperlefttruefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "westupperlefttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "westupperrightfalsefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "westupperrightfalsetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}")
      );
      var0.put(
         "minecraft:" + var1 + "westupperrighttruefalse",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      var0.put(
         "minecraft:" + var1 + "westupperrighttruetrue",
         DataConverterFlattenData.b("{Name:'minecraft:" + var1 + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
   }

   private static void a(Map<String, Dynamic<?>> var0, int var1, String var2) {
      var0.put(
         "southfalsefoot" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_bed',Properties:{facing:'south',occupied:'false',part:'foot'}}")
      );
      var0.put(
         "westfalsefoot" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_bed',Properties:{facing:'west',occupied:'false',part:'foot'}}")
      );
      var0.put(
         "northfalsefoot" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_bed',Properties:{facing:'north',occupied:'false',part:'foot'}}")
      );
      var0.put(
         "eastfalsefoot" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_bed',Properties:{facing:'east',occupied:'false',part:'foot'}}")
      );
      var0.put(
         "southfalsehead" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_bed',Properties:{facing:'south',occupied:'false',part:'head'}}")
      );
      var0.put(
         "westfalsehead" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_bed',Properties:{facing:'west',occupied:'false',part:'head'}}")
      );
      var0.put(
         "northfalsehead" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_bed',Properties:{facing:'north',occupied:'false',part:'head'}}")
      );
      var0.put(
         "eastfalsehead" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_bed',Properties:{facing:'east',occupied:'false',part:'head'}}")
      );
      var0.put(
         "southtruehead" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_bed',Properties:{facing:'south',occupied:'true',part:'head'}}")
      );
      var0.put("westtruehead" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_bed',Properties:{facing:'west',occupied:'true',part:'head'}}"));
      var0.put(
         "northtruehead" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_bed',Properties:{facing:'north',occupied:'true',part:'head'}}")
      );
      var0.put("easttruehead" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_bed',Properties:{facing:'east',occupied:'true',part:'head'}}"));
   }

   private static void b(Map<String, Dynamic<?>> var0, int var1, String var2) {
      for(int var3 = 0; var3 < 16; ++var3) {
         var0.put(var3 + "_" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_banner',Properties:{rotation:'" + var3 + "'}}"));
      }

      var0.put("north_" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_wall_banner',Properties:{facing:'north'}}"));
      var0.put("south_" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_wall_banner',Properties:{facing:'south'}}"));
      var0.put("west_" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_wall_banner',Properties:{facing:'west'}}"));
      var0.put("east_" + var1, DataConverterFlattenData.b("{Name:'minecraft:" + var2 + "_wall_banner',Properties:{facing:'east'}}"));
   }

   public static String a(Dynamic<?> var0) {
      return var0.get("Name").asString("");
   }

   public static String a(Dynamic<?> var0, String var1) {
      return var0.get("Properties").get(var1).asString("");
   }

   public static int a(RegistryID<Dynamic<?>> var0, Dynamic<?> var1) {
      int var2 = var0.a(var1);
      if (var2 == -1) {
         var2 = var0.c(var1);
      }

      return var2;
   }

   private Dynamic<?> b(Dynamic<?> var0) {
      Optional<? extends Dynamic<?>> var1 = var0.get("Level").result();
      return var1.isPresent() && ((Dynamic)var1.get()).get("Sections").asStreamOpt().result().isPresent()
         ? var0.set("Level", new ChunkConverterPalette.d((Dynamic<?>)var1.get()).a())
         : var0;
   }

   public TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.c);
      Type<?> var1 = this.getOutputSchema().getType(DataConverterTypes.c);
      return this.writeFixAndRead("ChunkPalettedStorageFix", var0, var1, this::b);
   }

   public static int a(boolean var0, boolean var1, boolean var2, boolean var3) {
      int var4 = 0;
      if (var2) {
         if (var1) {
            var4 |= 2;
         } else if (var0) {
            var4 |= 128;
         } else {
            var4 |= 1;
         }
      } else if (var3) {
         if (var0) {
            var4 |= 32;
         } else if (var1) {
            var4 |= 8;
         } else {
            var4 |= 16;
         }
      } else if (var1) {
         var4 |= 4;
      } else if (var0) {
         var4 |= 64;
      }

      return var4;
   }

   static {
      k.set(2);
      k.set(3);
      k.set(110);
      k.set(140);
      k.set(144);
      k.set(25);
      k.set(86);
      k.set(26);
      k.set(176);
      k.set(177);
      k.set(175);
      k.set(64);
      k.set(71);
      k.set(193);
      k.set(194);
      k.set(195);
      k.set(196);
      k.set(197);
      j.set(54);
      j.set(146);
      j.set(25);
      j.set(26);
      j.set(51);
      j.set(53);
      j.set(67);
      j.set(108);
      j.set(109);
      j.set(114);
      j.set(128);
      j.set(134);
      j.set(135);
      j.set(136);
      j.set(156);
      j.set(163);
      j.set(164);
      j.set(180);
      j.set(203);
      j.set(55);
      j.set(85);
      j.set(113);
      j.set(188);
      j.set(189);
      j.set(190);
      j.set(191);
      j.set(192);
      j.set(93);
      j.set(94);
      j.set(101);
      j.set(102);
      j.set(160);
      j.set(106);
      j.set(107);
      j.set(183);
      j.set(184);
      j.set(185);
      j.set(186);
      j.set(187);
      j.set(132);
      j.set(139);
      j.set(199);
   }

   public static enum Direction {
      a(ChunkConverterPalette.Direction.AxisDirection.b, ChunkConverterPalette.Direction.Axis.b),
      b(ChunkConverterPalette.Direction.AxisDirection.a, ChunkConverterPalette.Direction.Axis.b),
      c(ChunkConverterPalette.Direction.AxisDirection.b, ChunkConverterPalette.Direction.Axis.c),
      d(ChunkConverterPalette.Direction.AxisDirection.a, ChunkConverterPalette.Direction.Axis.c),
      e(ChunkConverterPalette.Direction.AxisDirection.b, ChunkConverterPalette.Direction.Axis.a),
      f(ChunkConverterPalette.Direction.AxisDirection.a, ChunkConverterPalette.Direction.Axis.a);

      private final ChunkConverterPalette.Direction.Axis g;
      private final ChunkConverterPalette.Direction.AxisDirection h;

      private Direction(ChunkConverterPalette.Direction.AxisDirection var2, ChunkConverterPalette.Direction.Axis var3) {
         this.g = var3;
         this.h = var2;
      }

      public ChunkConverterPalette.Direction.AxisDirection a() {
         return this.h;
      }

      public ChunkConverterPalette.Direction.Axis b() {
         return this.g;
      }

      public static enum Axis {
         a,
         b,
         c;
      }

      public static enum AxisDirection {
         a(1),
         b(-1);

         private final int c;

         private AxisDirection(int var2) {
            this.c = var2;
         }

         public int a() {
            return this.c;
         }
      }
   }

   static class a {
      private static final int a = 2048;
      private static final int b = 4;
      private final byte[] c;

      public a() {
         this.c = new byte[2048];
      }

      public a(byte[] var0) {
         this.c = var0;
         if (var0.length != 2048) {
            throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + var0.length);
         }
      }

      public int a(int var0, int var1, int var2) {
         int var3 = this.b(var1 << 8 | var2 << 4 | var0);
         return this.a(var1 << 8 | var2 << 4 | var0) ? this.c[var3] & 15 : this.c[var3] >> 4 & 15;
      }

      private boolean a(int var0) {
         return (var0 & 1) == 0;
      }

      private int b(int var0) {
         return var0 >> 1;
      }
   }

   static class c {
      private final RegistryID<Dynamic<?>> b = RegistryID.c(32);
      private final List<Dynamic<?>> c;
      private final Dynamic<?> d;
      private final boolean e;
      final Int2ObjectMap<IntList> f = new Int2ObjectLinkedOpenHashMap();
      final IntList g = new IntArrayList();
      public final int a;
      private final Set<Dynamic<?>> h = Sets.newIdentityHashSet();
      private final int[] i = new int[4096];

      public c(Dynamic<?> var0) {
         this.c = Lists.newArrayList();
         this.d = var0;
         this.a = var0.get("Y").asInt(0);
         this.e = var0.get("Blocks").result().isPresent();
      }

      public Dynamic<?> a(int var0) {
         if (var0 >= 0 && var0 <= 4095) {
            Dynamic<?> var1 = (Dynamic)this.b.a(this.i[var0]);
            return var1 == null ? ChunkConverterPalette.C : var1;
         } else {
            return ChunkConverterPalette.C;
         }
      }

      public void a(int var0, Dynamic<?> var1) {
         if (this.h.add(var1)) {
            this.c.add("%%FILTER_ME%%".equals(ChunkConverterPalette.a(var1)) ? ChunkConverterPalette.C : var1);
         }

         this.i[var0] = ChunkConverterPalette.a(this.b, var1);
      }

      public int b(int var0) {
         if (!this.e) {
            return var0;
         } else {
            ByteBuffer var1 = (ByteBuffer)this.d.get("Blocks").asByteBufferOpt().result().get();
            ChunkConverterPalette.a var2 = this.d
               .get("Data")
               .asByteBufferOpt()
               .map(var0x -> new ChunkConverterPalette.a(DataFixUtils.toArray(var0x)))
               .result()
               .orElseGet(ChunkConverterPalette.a::new);
            ChunkConverterPalette.a var3 = this.d
               .get("Add")
               .asByteBufferOpt()
               .map(var0x -> new ChunkConverterPalette.a(DataFixUtils.toArray(var0x)))
               .result()
               .orElseGet(ChunkConverterPalette.a::new);
            this.h.add(ChunkConverterPalette.C);
            ChunkConverterPalette.a(this.b, ChunkConverterPalette.C);
            this.c.add(ChunkConverterPalette.C);

            for(int var4 = 0; var4 < 4096; ++var4) {
               int var5 = var4 & 15;
               int var6 = var4 >> 8 & 15;
               int var7 = var4 >> 4 & 15;
               int var8 = var3.a(var5, var6, var7) << 12 | (var1.get(var4) & 255) << 4 | var2.a(var5, var6, var7);
               if (ChunkConverterPalette.k.get(var8 >> 4)) {
                  this.a(var8 >> 4, var4);
               }

               if (ChunkConverterPalette.j.get(var8 >> 4)) {
                  int var9 = ChunkConverterPalette.a(var5 == 0, var5 == 15, var7 == 0, var7 == 15);
                  if (var9 == 0) {
                     this.g.add(var4);
                  } else {
                     var0 |= var9;
                  }
               }

               this.a(var4, DataConverterFlattenData.b(var8));
            }

            return var0;
         }
      }

      private void a(int var0, int var1) {
         IntList var2 = (IntList)this.f.get(var0);
         if (var2 == null) {
            var2 = new IntArrayList();
            this.f.put(var0, var2);
         }

         var2.add(var1);
      }

      public Dynamic<?> a() {
         Dynamic<?> var0 = this.d;
         if (!this.e) {
            return var0;
         } else {
            var0 = var0.set("Palette", var0.createList(this.c.stream()));
            int var1 = Math.max(4, DataFixUtils.ceillog2(this.h.size()));
            DataBitsPacked var2 = new DataBitsPacked(var1, 4096);

            for(int var3 = 0; var3 < this.i.length; ++var3) {
               var2.a(var3, this.i[var3]);
            }

            var0 = var0.set("BlockStates", var0.createLongList(Arrays.stream(var2.a())));
            var0 = var0.remove("Blocks");
            var0 = var0.remove("Data");
            return var0.remove("Add");
         }
      }
   }

   static final class d {
      private int a;
      private final ChunkConverterPalette.c[] b = new ChunkConverterPalette.c[16];
      private final Dynamic<?> c;
      private final int d;
      private final int e;
      private final Int2ObjectMap<Dynamic<?>> f = new Int2ObjectLinkedOpenHashMap(16);

      public d(Dynamic<?> var0) {
         this.c = var0;
         this.d = var0.get("xPos").asInt(0) << 4;
         this.e = var0.get("zPos").asInt(0) << 4;
         var0.get("TileEntities")
            .asStreamOpt()
            .result()
            .ifPresent(
               var0x -> var0x.forEach(
                     var0xx -> {
                        int var1x = var0xx.get("x").asInt(0) - this.d & 15;
                        int var2x = var0xx.get("y").asInt(0);
                        int var3 = var0xx.get("z").asInt(0) - this.e & 15;
                        int var4 = var2x << 8 | var3 << 4 | var1x;
                        if (this.f.put(var4, var0xx) != null) {
                           ChunkConverterPalette.i
                              .warn(
                                 "In chunk: {}x{} found a duplicate block entity at position: [{}, {}, {}]", new Object[]{this.d, this.e, var1x, var2x, var3}
                              );
                        }
                     }
                  )
            );
         boolean var1 = var0.get("convertedFromAlphaFormat").asBoolean(false);
         var0.get("Sections").asStreamOpt().result().ifPresent(var0x -> var0x.forEach(var0xx -> {
               ChunkConverterPalette.c var1x = new ChunkConverterPalette.c(var0xx);
               this.a = var1x.b(this.a);
               this.b[var1x.a] = var1x;
            }));

         for(ChunkConverterPalette.c var5 : this.b) {
            if (var5 != null) {
               ObjectIterator var7 = var5.f.entrySet().iterator();

               while(var7.hasNext()) {
                  java.util.Map.Entry<Integer, IntList> var7x = (java.util.Map.Entry)var7.next();
                  int var8 = var5.a << 12;
                  switch(var7x.getKey()) {
                     case 2:
                        IntListIterator var30 = ((IntList)var7x.getValue()).iterator();

                        while(var30.hasNext()) {
                           int var10 = var30.next();
                           var10 |= var8;
                           Dynamic<?> var11 = this.a(var10);
                           if ("minecraft:grass_block".equals(ChunkConverterPalette.a(var11))) {
                              String var12 = ChunkConverterPalette.a(this.a(a(var10, ChunkConverterPalette.Direction.b)));
                              if ("minecraft:snow".equals(var12) || "minecraft:snow_layer".equals(var12)) {
                                 this.a(var10, ChunkConverterPalette.n);
                              }
                           }
                        }
                        break;
                     case 3:
                        IntListIterator var29 = ((IntList)var7x.getValue()).iterator();

                        while(var29.hasNext()) {
                           int var10 = var29.next();
                           var10 |= var8;
                           Dynamic<?> var11 = this.a(var10);
                           if ("minecraft:podzol".equals(ChunkConverterPalette.a(var11))) {
                              String var12 = ChunkConverterPalette.a(this.a(a(var10, ChunkConverterPalette.Direction.b)));
                              if ("minecraft:snow".equals(var12) || "minecraft:snow_layer".equals(var12)) {
                                 this.a(var10, ChunkConverterPalette.m);
                              }
                           }
                        }
                        break;
                     case 25:
                        IntListIterator var28 = ((IntList)var7x.getValue()).iterator();

                        while(var28.hasNext()) {
                           int var10 = var28.next();
                           var10 |= var8;
                           Dynamic<?> var11 = this.c(var10);
                           if (var11 != null) {
                              String var12 = Boolean.toString(var11.get("powered").asBoolean(false))
                                 + (byte)Math.min(Math.max(var11.get("note").asInt(0), 0), 24);
                              this.a(var10, (Dynamic<?>)ChunkConverterPalette.y.getOrDefault(var12, (Dynamic)ChunkConverterPalette.y.get("false0")));
                           }
                        }
                        break;
                     case 26:
                        IntListIterator var27 = ((IntList)var7x.getValue()).iterator();

                        while(var27.hasNext()) {
                           int var10 = var27.next();
                           var10 |= var8;
                           Dynamic<?> var11 = this.b(var10);
                           Dynamic<?> var12 = this.a(var10);
                           if (var11 != null) {
                              int var13 = var11.get("color").asInt(0);
                              if (var13 != 14 && var13 >= 0 && var13 < 16) {
                                 String var14 = ChunkConverterPalette.a(var12, "facing")
                                    + ChunkConverterPalette.a(var12, "occupied")
                                    + ChunkConverterPalette.a(var12, "part")
                                    + var13;
                                 if (ChunkConverterPalette.A.containsKey(var14)) {
                                    this.a(var10, (Dynamic<?>)ChunkConverterPalette.A.get(var14));
                                 }
                              }
                           }
                        }
                        break;
                     case 64:
                     case 71:
                     case 193:
                     case 194:
                     case 195:
                     case 196:
                     case 197:
                        IntListIterator var26 = ((IntList)var7x.getValue()).iterator();

                        while(var26.hasNext()) {
                           int var10 = var26.next();
                           var10 |= var8;
                           Dynamic<?> var11 = this.a(var10);
                           if (ChunkConverterPalette.a(var11).endsWith("_door")) {
                              Dynamic<?> var12 = this.a(var10);
                              if ("lower".equals(ChunkConverterPalette.a(var12, "half"))) {
                                 int var13 = a(var10, ChunkConverterPalette.Direction.b);
                                 Dynamic<?> var14 = this.a(var13);
                                 String var15 = ChunkConverterPalette.a(var12);
                                 if (var15.equals(ChunkConverterPalette.a(var14))) {
                                    String var16 = ChunkConverterPalette.a(var12, "facing");
                                    String var17 = ChunkConverterPalette.a(var12, "open");
                                    String var18 = var1 ? "left" : ChunkConverterPalette.a(var14, "hinge");
                                    String var19 = var1 ? "false" : ChunkConverterPalette.a(var14, "powered");
                                    this.a(var10, (Dynamic<?>)ChunkConverterPalette.x.get(var15 + var16 + "lower" + var18 + var17 + var19));
                                    this.a(var13, (Dynamic<?>)ChunkConverterPalette.x.get(var15 + var16 + "upper" + var18 + var17 + var19));
                                 }
                              }
                           }
                        }
                        break;
                     case 86:
                        IntListIterator var25 = ((IntList)var7x.getValue()).iterator();

                        while(var25.hasNext()) {
                           int var10 = var25.next();
                           var10 |= var8;
                           Dynamic<?> var11 = this.a(var10);
                           if ("minecraft:carved_pumpkin".equals(ChunkConverterPalette.a(var11))) {
                              String var12 = ChunkConverterPalette.a(this.a(a(var10, ChunkConverterPalette.Direction.a)));
                              if ("minecraft:grass_block".equals(var12) || "minecraft:dirt".equals(var12)) {
                                 this.a(var10, ChunkConverterPalette.l);
                              }
                           }
                        }
                        break;
                     case 110:
                        IntListIterator var24 = ((IntList)var7x.getValue()).iterator();

                        while(var24.hasNext()) {
                           int var10 = var24.next();
                           var10 |= var8;
                           Dynamic<?> var11 = this.a(var10);
                           if ("minecraft:mycelium".equals(ChunkConverterPalette.a(var11))) {
                              String var12 = ChunkConverterPalette.a(this.a(a(var10, ChunkConverterPalette.Direction.b)));
                              if ("minecraft:snow".equals(var12) || "minecraft:snow_layer".equals(var12)) {
                                 this.a(var10, ChunkConverterPalette.o);
                              }
                           }
                        }
                        break;
                     case 140:
                        IntListIterator var23 = ((IntList)var7x.getValue()).iterator();

                        while(var23.hasNext()) {
                           int var10 = var23.next();
                           var10 |= var8;
                           Dynamic<?> var11 = this.c(var10);
                           if (var11 != null) {
                              String var12 = var11.get("Item").asString("") + var11.get("Data").asInt(0);
                              this.a(var10, (Dynamic<?>)ChunkConverterPalette.v.getOrDefault(var12, (Dynamic)ChunkConverterPalette.v.get("minecraft:air0")));
                           }
                        }
                        break;
                     case 144:
                        IntListIterator var22 = ((IntList)var7x.getValue()).iterator();

                        while(var22.hasNext()) {
                           int var10 = var22.next();
                           var10 |= var8;
                           Dynamic<?> var11 = this.b(var10);
                           if (var11 != null) {
                              String var12 = String.valueOf(var11.get("SkullType").asInt(0));
                              String var13 = ChunkConverterPalette.a(this.a(var10), "facing");
                              String var14;
                              if (!"up".equals(var13) && !"down".equals(var13)) {
                                 var14 = var12 + var13;
                              } else {
                                 var14 = var12 + var11.get("Rot").asInt(0);
                              }

                              var11.remove("SkullType");
                              var11.remove("facing");
                              var11.remove("Rot");
                              this.a(var10, (Dynamic<?>)ChunkConverterPalette.w.getOrDefault(var14, (Dynamic)ChunkConverterPalette.w.get("0north")));
                           }
                        }
                        break;
                     case 175:
                        IntListIterator var21 = ((IntList)var7x.getValue()).iterator();

                        while(var21.hasNext()) {
                           int var10 = var21.next();
                           var10 |= var8;
                           Dynamic<?> var11 = this.a(var10);
                           if ("upper".equals(ChunkConverterPalette.a(var11, "half"))) {
                              Dynamic<?> var12 = this.a(a(var10, ChunkConverterPalette.Direction.a));
                              String var13 = ChunkConverterPalette.a(var12);
                              if ("minecraft:sunflower".equals(var13)) {
                                 this.a(var10, ChunkConverterPalette.p);
                              } else if ("minecraft:lilac".equals(var13)) {
                                 this.a(var10, ChunkConverterPalette.q);
                              } else if ("minecraft:tall_grass".equals(var13)) {
                                 this.a(var10, ChunkConverterPalette.r);
                              } else if ("minecraft:large_fern".equals(var13)) {
                                 this.a(var10, ChunkConverterPalette.s);
                              } else if ("minecraft:rose_bush".equals(var13)) {
                                 this.a(var10, ChunkConverterPalette.t);
                              } else if ("minecraft:peony".equals(var13)) {
                                 this.a(var10, ChunkConverterPalette.u);
                              }
                           }
                        }
                        break;
                     case 176:
                     case 177:
                        IntListIterator var10 = ((IntList)var7x.getValue()).iterator();

                        while(var10.hasNext()) {
                           int var10x = var10.next();
                           var10x |= var8;
                           Dynamic<?> var11 = this.b(var10x);
                           Dynamic<?> var12 = this.a(var10x);
                           if (var11 != null) {
                              int var13 = var11.get("Base").asInt(0);
                              if (var13 != 15 && var13 >= 0 && var13 < 16) {
                                 String var14 = ChunkConverterPalette.a(var12, var7x.getKey() == 176 ? "rotation" : "facing") + "_" + var13;
                                 if (ChunkConverterPalette.B.containsKey(var14)) {
                                    this.a(var10x, (Dynamic<?>)ChunkConverterPalette.B.get(var14));
                                 }
                              }
                           }
                        }
                  }
               }
            }
         }
      }

      @Nullable
      private Dynamic<?> b(int var0) {
         return (Dynamic<?>)this.f.get(var0);
      }

      @Nullable
      private Dynamic<?> c(int var0) {
         return (Dynamic<?>)this.f.remove(var0);
      }

      public static int a(int var0, ChunkConverterPalette.Direction var1) {
         switch(var1.b()) {
            case a:
               int var2 = (var0 & 15) + var1.a().a();
               return var2 >= 0 && var2 <= 15 ? var0 & -16 | var2 : -1;
            case b:
               int var3 = (var0 >> 8) + var1.a().a();
               return var3 >= 0 && var3 <= 255 ? var0 & 0xFF | var3 << 8 : -1;
            case c:
               int var4 = (var0 >> 4 & 15) + var1.a().a();
               return var4 >= 0 && var4 <= 15 ? var0 & -241 | var4 << 4 : -1;
            default:
               return -1;
         }
      }

      private void a(int var0, Dynamic<?> var1) {
         if (var0 >= 0 && var0 <= 65535) {
            ChunkConverterPalette.c var2 = this.d(var0);
            if (var2 != null) {
               var2.a(var0 & 4095, var1);
            }
         }
      }

      @Nullable
      private ChunkConverterPalette.c d(int var0) {
         int var1 = var0 >> 12;
         return var1 < this.b.length ? this.b[var1] : null;
      }

      public Dynamic<?> a(int var0) {
         if (var0 >= 0 && var0 <= 65535) {
            ChunkConverterPalette.c var1 = this.d(var0);
            return var1 == null ? ChunkConverterPalette.C : var1.a(var0 & 4095);
         } else {
            return ChunkConverterPalette.C;
         }
      }

      public Dynamic<?> a() {
         Dynamic<?> var0 = this.c;
         if (this.f.isEmpty()) {
            var0 = var0.remove("TileEntities");
         } else {
            var0 = var0.set("TileEntities", var0.createList(this.f.values().stream()));
         }

         Dynamic<?> var1 = var0.emptyMap();
         List<Dynamic<?>> var2 = Lists.newArrayList();

         for(ChunkConverterPalette.c var6 : this.b) {
            if (var6 != null) {
               var2.add(var6.a());
               var1 = var1.set(String.valueOf(var6.a), var1.createIntList(Arrays.stream(var6.g.toIntArray())));
            }
         }

         Dynamic<?> var3 = var0.emptyMap();
         var3 = var3.set("Sides", var3.createByte((byte)this.a));
         var3 = var3.set("Indices", var1);
         return var0.set("UpgradeData", var3).set("Sections", var3.createList(var2.stream()));
      }
   }
}
