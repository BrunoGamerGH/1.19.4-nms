package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementTree;
import net.minecraft.advancements.Advancements;
import net.minecraft.advancements.critereon.LootDeserializationContext;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.resources.IResourceManager;
import net.minecraft.server.packs.resources.ResourceDataJson;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.world.level.storage.loot.LootPredicateManager;
import org.slf4j.Logger;
import org.spigotmc.SpigotConfig;

public class AdvancementDataWorld extends ResourceDataJson {
   private static final Logger a = LogUtils.getLogger();
   public static final Gson b = new GsonBuilder().create();
   public Advancements c = new Advancements();
   private final LootPredicateManager d;

   public AdvancementDataWorld(LootPredicateManager lootpredicatemanager) {
      super(b, "advancements");
      this.d = lootpredicatemanager;
   }

   protected void a(Map<MinecraftKey, JsonElement> map, IResourceManager iresourcemanager, GameProfilerFiller gameprofilerfiller) {
      Map<MinecraftKey, Advancement.SerializedAdvancement> map1 = Maps.newHashMap();
      map.forEach(
         (minecraftkey, jsonelement) -> {
            if (SpigotConfig.disabledAdvancements == null
               || !SpigotConfig.disabledAdvancements.contains("*")
                  && !SpigotConfig.disabledAdvancements.contains(minecraftkey.toString())
                  && !SpigotConfig.disabledAdvancements.contains(minecraftkey.b())) {
               try {
                  JsonObject jsonobject = ChatDeserializer.m(jsonelement, "advancement");
                  Advancement.SerializedAdvancement advancement_serializedadvancement = Advancement.SerializedAdvancement.a(
                     jsonobject, new LootDeserializationContext(minecraftkey, this.d)
                  );
                  map1.put(minecraftkey, advancement_serializedadvancement);
               } catch (Exception var6) {
                  a.error("Parsing error loading custom advancement {}: {}", minecraftkey, var6.getMessage());
               }
            }
         }
      );
      Advancements advancements = new Advancements();
      advancements.a(map1);

      for(Advancement advancement : advancements.b()) {
         if (advancement.d() != null) {
            AdvancementTree.a(advancement);
         }
      }

      this.c = advancements;
   }

   @Nullable
   public Advancement a(MinecraftKey minecraftkey) {
      return this.c.a(minecraftkey);
   }

   public Collection<Advancement> a() {
      return this.c.c();
   }
}
