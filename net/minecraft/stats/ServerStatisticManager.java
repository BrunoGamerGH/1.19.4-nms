package net.minecraft.stats;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutStatistic;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.EntityHuman;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.spigotmc.SpigotConfig;

public class ServerStatisticManager extends StatisticManager {
   private static final Logger b = LogUtils.getLogger();
   private final MinecraftServer c;
   private final File d;
   private final Set<Statistic<?>> e = Sets.newHashSet();

   public ServerStatisticManager(MinecraftServer minecraftserver, File file) {
      this.c = minecraftserver;
      this.d = file;

      for(Entry<MinecraftKey, Integer> entry : SpigotConfig.forcedStats.entrySet()) {
         Statistic<MinecraftKey> wrapper = StatisticList.i.b(entry.getKey());
         this.a.put(wrapper, entry.getValue());
      }

      if (file.isFile()) {
         try {
            this.a(minecraftserver.ay(), FileUtils.readFileToString(file));
         } catch (IOException var6) {
            b.error("Couldn't read statistics file {}", file, var6);
         } catch (JsonParseException var7) {
            b.error("Couldn't parse statistics file {}", file, var7);
         }
      }
   }

   public void a() {
      if (!SpigotConfig.disableStatSaving) {
         try {
            FileUtils.writeStringToFile(this.d, this.b());
         } catch (IOException var2) {
            b.error("Couldn't save stats", var2);
         }
      }
   }

   @Override
   public void a(EntityHuman entityhuman, Statistic<?> statistic, int i) {
      if (!SpigotConfig.disableStatSaving) {
         super.a(entityhuman, statistic, i);
         this.e.add(statistic);
      }
   }

   private Set<Statistic<?>> d() {
      Set<Statistic<?>> set = Sets.newHashSet(this.e);
      this.e.clear();
      return set;
   }

   public void a(DataFixer datafixer, String s) {
      try {
         JsonReader jsonreader = new JsonReader(new StringReader(s));

         label50: {
            try {
               jsonreader.setLenient(false);
               JsonElement jsonelement = Streams.parse(jsonreader);
               if (!jsonelement.isJsonNull()) {
                  NBTTagCompound nbttagcompound = a(jsonelement.getAsJsonObject());
                  nbttagcompound = DataFixTypes.g.a(datafixer, nbttagcompound, GameProfileSerializer.b(nbttagcompound, 1343));
                  if (!nbttagcompound.b("stats", 10)) {
                     break label50;
                  }

                  NBTTagCompound nbttagcompound1 = nbttagcompound.p("stats");
                  Iterator iterator = nbttagcompound1.e().iterator();

                  while(true) {
                     if (!iterator.hasNext()) {
                        break label50;
                     }

                     String s1 = (String)iterator.next();
                     if (nbttagcompound1.b(s1, 10)) {
                        SystemUtils.a(
                           BuiltInRegistries.x.b(new MinecraftKey(s1)),
                           statisticwrapper -> {
                              NBTTagCompound nbttagcompound2 = nbttagcompound1.p(s1);
   
                              for(String s2 : nbttagcompound2.e()) {
                                 if (nbttagcompound2.b(s2, 99)) {
                                    SystemUtils.a(
                                       this.a(statisticwrapper, s2),
                                       statistic -> this.a.put(statistic, nbttagcompound2.h(s2)),
                                       () -> b.warn("Invalid statistic in {}: Don't know what {} is", this.d, s2)
                                    );
                                 } else {
                                    b.warn("Invalid statistic value in {}: Don't know what {} is for key {}", new Object[]{this.d, nbttagcompound2.c(s2), s2});
                                 }
                              }
                           },
                           () -> b.warn("Invalid statistic type in {}: Don't know what {} is", this.d, s1)
                        );
                     }
                  }
               }

               b.error("Unable to parse Stat data from {}", this.d);
            } catch (Throwable var10) {
               try {
                  jsonreader.close();
               } catch (Throwable var9) {
                  var10.addSuppressed(var9);
               }

               throw var10;
            }

            jsonreader.close();
            return;
         }

         jsonreader.close();
      } catch (JsonParseException | IOException var11) {
         b.error("Unable to parse Stat data from {}", this.d, var11);
      }
   }

   private <T> Optional<Statistic<T>> a(StatisticWrapper<T> statisticwrapper, String s) {
      Optional<MinecraftKey> optional = Optional.ofNullable(MinecraftKey.a(s));
      IRegistry<T> iregistry = statisticwrapper.a();
      return optional.<T>flatMap(iregistry::b).map(statisticwrapper::b);
   }

   private static NBTTagCompound a(JsonObject jsonobject) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();

      for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
         JsonElement jsonelement = (JsonElement)entry.getValue();
         if (jsonelement.isJsonObject()) {
            nbttagcompound.a(entry.getKey(), a(jsonelement.getAsJsonObject()));
         } else if (jsonelement.isJsonPrimitive()) {
            JsonPrimitive jsonprimitive = jsonelement.getAsJsonPrimitive();
            if (jsonprimitive.isNumber()) {
               nbttagcompound.a(entry.getKey(), jsonprimitive.getAsInt());
            }
         }
      }

      return nbttagcompound;
   }

   protected String b() {
      Map<StatisticWrapper<?>, JsonObject> map = Maps.newHashMap();
      ObjectIterator objectiterator = this.a.object2IntEntrySet().iterator();

      while(objectiterator.hasNext()) {
         it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Statistic<?>> it_unimi_dsi_fastutil_objects_object2intmap_entry = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry)objectiterator.next(
            
         );
         Statistic<?> statistic = (Statistic)it_unimi_dsi_fastutil_objects_object2intmap_entry.getKey();
         ((JsonObject)map.computeIfAbsent(statistic.a(), statisticwrapper -> new JsonObject()))
            .addProperty(b(statistic).toString(), it_unimi_dsi_fastutil_objects_object2intmap_entry.getIntValue());
      }

      JsonObject jsonobject = new JsonObject();

      for(Entry<StatisticWrapper<?>, JsonObject> entry : map.entrySet()) {
         jsonobject.add(BuiltInRegistries.x.b(entry.getKey()).toString(), (JsonElement)entry.getValue());
      }

      JsonObject jsonobject1 = new JsonObject();
      jsonobject1.add("stats", jsonobject);
      jsonobject1.addProperty("DataVersion", SharedConstants.b().d().c());
      return jsonobject1.toString();
   }

   private static <T> MinecraftKey b(Statistic<T> statistic) {
      return statistic.a().a().b(statistic.b());
   }

   public void c() {
      this.e.addAll(this.a.keySet());
   }

   public void a(EntityPlayer entityplayer) {
      Object2IntMap<Statistic<?>> object2intmap = new Object2IntOpenHashMap();

      for(Statistic<?> statistic : this.d()) {
         object2intmap.put(statistic, this.a(statistic));
      }

      entityplayer.b.a(new PacketPlayOutStatistic(object2intmap));
   }
}
