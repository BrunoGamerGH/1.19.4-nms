package net.minecraft.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.FileUtils;
import net.minecraft.SharedConstants;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionInstance;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutAdvancements;
import net.minecraft.network.protocol.game.PacketPlayOutSelectAdvancementTab;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.advancements.AdvancementVisibilityEvaluator;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.GameRules;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.slf4j.Logger;
import org.spigotmc.SpigotConfig;

public class AdvancementDataPlayer {
   private static final Logger a = LogUtils.getLogger();
   private static final Gson b = new GsonBuilder()
      .registerTypeAdapter(AdvancementProgress.class, new AdvancementProgress.a())
      .registerTypeAdapter(MinecraftKey.class, new MinecraftKey.b())
      .setPrettyPrinting()
      .create();
   private static final TypeToken<Map<MinecraftKey, AdvancementProgress>> c = new TypeToken<Map<MinecraftKey, AdvancementProgress>>() {
   };
   private final DataFixer d;
   private final PlayerList e;
   private final Path f;
   private final Map<Advancement, AdvancementProgress> g = new LinkedHashMap<>();
   private final Set<Advancement> h = new HashSet<>();
   private final Set<Advancement> i = new HashSet<>();
   private final Set<Advancement> j = new HashSet<>();
   private EntityPlayer k;
   @Nullable
   private Advancement l;
   private boolean m = true;

   public AdvancementDataPlayer(DataFixer datafixer, PlayerList playerlist, AdvancementDataWorld advancementdataworld, Path path, EntityPlayer entityplayer) {
      this.d = datafixer;
      this.e = playerlist;
      this.f = path;
      this.k = entityplayer;
      this.d(advancementdataworld);
   }

   public void a(EntityPlayer entityplayer) {
      this.k = entityplayer;
   }

   public void a() {
      for(CriterionTrigger<?> criteriontrigger : CriterionTriggers.a()) {
         criteriontrigger.a(this);
      }
   }

   public void a(AdvancementDataWorld advancementdataworld) {
      this.a();
      this.g.clear();
      this.h.clear();
      this.j.clear();
      this.i.clear();
      this.m = true;
      this.l = null;
      this.d(advancementdataworld);
   }

   private void b(AdvancementDataWorld advancementdataworld) {
      for(Advancement advancement : advancementdataworld.a()) {
         this.d(advancement);
      }
   }

   private void c(AdvancementDataWorld advancementdataworld) {
      for(Advancement advancement : advancementdataworld.a()) {
         if (advancement.g().isEmpty()) {
            this.a(advancement, "");
            advancement.e().a(this.k);
         }
      }
   }

   private void d(AdvancementDataWorld advancementdataworld) {
      if (Files.isRegularFile(this.f)) {
         try {
            JsonReader jsonreader = new JsonReader(Files.newBufferedReader(this.f, StandardCharsets.UTF_8));

            try {
               jsonreader.setLenient(false);
               Dynamic<JsonElement> dynamic = new Dynamic(JsonOps.INSTANCE, Streams.parse(jsonreader));
               int i = dynamic.get("DataVersion").asInt(1343);
               dynamic = dynamic.remove("DataVersion");
               dynamic = DataFixTypes.i.a(this.d, dynamic, i);
               Map<MinecraftKey, AdvancementProgress> map = (Map)b.getAdapter(c).fromJsonTree((JsonElement)dynamic.getValue());
               if (map == null) {
                  throw new JsonParseException("Found null for advancements");
               }

               map.entrySet().stream().sorted(Entry.comparingByValue()).forEach(entry -> {
                  Advancement advancement = advancementdataworld.a(entry.getKey());
                  if (advancement == null) {
                     if (entry.getKey().b().equals("minecraft")) {
                        a.warn("Ignored advancement '{}' in progress file {} - it doesn't exist anymore?", entry.getKey(), this.f);
                     }
                  } else {
                     this.a(advancement, entry.getValue());
                     this.i.add(advancement);
                     this.c(advancement);
                  }
               });
            } catch (Throwable var7) {
               try {
                  jsonreader.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }

               throw var7;
            }

            jsonreader.close();
         } catch (JsonParseException var8) {
            a.error("Couldn't parse player advancements in {}", this.f, var8);
         } catch (IOException var9) {
            a.error("Couldn't access player advancements in {}", this.f, var9);
         }
      }

      this.c(advancementdataworld);
      this.b(advancementdataworld);
   }

   public void b() {
      if (!SpigotConfig.disableAdvancementSaving) {
         Map<MinecraftKey, AdvancementProgress> map = new LinkedHashMap<>();

         for(Entry<Advancement, AdvancementProgress> entry : this.g.entrySet()) {
            AdvancementProgress advancementprogress = entry.getValue();
            if (advancementprogress.b()) {
               map.put(entry.getKey().i(), advancementprogress);
            }
         }

         JsonElement jsonelement = b.toJsonTree(map);
         jsonelement.getAsJsonObject().addProperty("DataVersion", SharedConstants.b().d().c());

         try {
            FileUtils.c(this.f.getParent());

            try (BufferedWriter bufferedwriter = Files.newBufferedWriter(this.f, StandardCharsets.UTF_8)) {
               b.toJson(jsonelement, bufferedwriter);
            }
         } catch (IOException var9) {
            a.error("Couldn't save player advancements to {}", this.f, var9);
         }
      }
   }

   public boolean a(Advancement advancement, String s) {
      boolean flag = false;
      AdvancementProgress advancementprogress = this.b(advancement);
      boolean flag1 = advancementprogress.a();
      if (advancementprogress.a(s)) {
         this.e(advancement);
         this.i.add(advancement);
         flag = true;
         if (!flag1 && advancementprogress.a()) {
            this.k.H.getCraftServer().getPluginManager().callEvent(new PlayerAdvancementDoneEvent(this.k.getBukkitEntity(), advancement.bukkit));
            advancement.e().a(this.k);
            if (advancement.d() != null && advancement.d().i() && this.k.H.W().b(GameRules.y)) {
               this.e.a(IChatBaseComponent.a("chat.type.advancement." + advancement.d().e().a(), this.k.G_(), advancement.k()), false);
            }
         }
      }

      if (!flag1 && advancementprogress.a()) {
         this.c(advancement);
      }

      return flag;
   }

   public boolean b(Advancement advancement, String s) {
      boolean flag = false;
      AdvancementProgress advancementprogress = this.b(advancement);
      boolean flag1 = advancementprogress.a();
      if (advancementprogress.b(s)) {
         this.d(advancement);
         this.i.add(advancement);
         flag = true;
      }

      if (flag1 && !advancementprogress.a()) {
         this.c(advancement);
      }

      return flag;
   }

   private void c(Advancement advancement) {
      this.j.add(advancement.c());
   }

   private void d(Advancement advancement) {
      AdvancementProgress advancementprogress = this.b(advancement);
      if (!advancementprogress.a()) {
         for(Entry<String, Criterion> entry : advancement.g().entrySet()) {
            CriterionProgress criterionprogress = advancementprogress.c(entry.getKey());
            if (criterionprogress != null && !criterionprogress.a()) {
               CriterionInstance criterioninstance = entry.getValue().a();
               if (criterioninstance != null) {
                  CriterionTrigger<CriterionInstance> criteriontrigger = CriterionTriggers.a(criterioninstance.a());
                  if (criteriontrigger != null) {
                     criteriontrigger.a(this, new CriterionTrigger.a<>(criterioninstance, advancement, entry.getKey()));
                  }
               }
            }
         }
      }
   }

   private void e(Advancement advancement) {
      AdvancementProgress advancementprogress = this.b(advancement);

      for(Entry<String, Criterion> entry : advancement.g().entrySet()) {
         CriterionProgress criterionprogress = advancementprogress.c(entry.getKey());
         if (criterionprogress != null && (criterionprogress.a() || advancementprogress.a())) {
            CriterionInstance criterioninstance = entry.getValue().a();
            if (criterioninstance != null) {
               CriterionTrigger<CriterionInstance> criteriontrigger = CriterionTriggers.a(criterioninstance.a());
               if (criteriontrigger != null) {
                  criteriontrigger.b(this, new CriterionTrigger.a<>(criterioninstance, advancement, entry.getKey()));
               }
            }
         }
      }
   }

   public void b(EntityPlayer entityplayer) {
      if (this.m || !this.j.isEmpty() || !this.i.isEmpty()) {
         Map<MinecraftKey, AdvancementProgress> map = new HashMap<>();
         Set<Advancement> set = new HashSet<>();
         Set<MinecraftKey> set1 = new HashSet<>();

         for(Advancement advancement : this.j) {
            this.a(advancement, set, set1);
         }

         this.j.clear();

         for(Advancement advancement : this.i) {
            if (this.h.contains(advancement)) {
               map.put(advancement.i(), this.g.get(advancement));
            }
         }

         this.i.clear();
         if (!map.isEmpty() || !set.isEmpty() || !set1.isEmpty()) {
            entityplayer.b.a(new PacketPlayOutAdvancements(this.m, set, set1, map));
         }
      }

      this.m = false;
   }

   public void a(@Nullable Advancement advancement) {
      Advancement advancement1 = this.l;
      if (advancement != null && advancement.b() == null && advancement.d() != null) {
         this.l = advancement;
      } else {
         this.l = null;
      }

      if (advancement1 != this.l) {
         this.k.b.a(new PacketPlayOutSelectAdvancementTab(this.l == null ? null : this.l.i()));
      }
   }

   public AdvancementProgress b(Advancement advancement) {
      AdvancementProgress advancementprogress = this.g.get(advancement);
      if (advancementprogress == null) {
         advancementprogress = new AdvancementProgress();
         this.a(advancement, advancementprogress);
      }

      return advancementprogress;
   }

   private void a(Advancement advancement, AdvancementProgress advancementprogress) {
      advancementprogress.a(advancement.g(), advancement.j());
      this.g.put(advancement, advancementprogress);
   }

   private void a(Advancement advancement, Set<Advancement> set, Set<MinecraftKey> set1) {
      AdvancementVisibilityEvaluator.a(advancement, advancement1 -> this.b(advancement1).a(), (advancement1, flag) -> {
         if (flag) {
            if (this.h.add(advancement1)) {
               set.add(advancement1);
               if (this.g.containsKey(advancement1)) {
                  this.i.add(advancement1);
               }
            }
         } else if (this.h.remove(advancement1)) {
            set1.add(advancement1.i());
         }
      });
   }
}
