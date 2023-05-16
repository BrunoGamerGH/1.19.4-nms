package net.minecraft.server.players;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.util.ChatDeserializer;
import org.bukkit.Bukkit;
import org.slf4j.Logger;

public abstract class JsonList<K, V extends JsonListEntry<K>> {
   private static final Logger a = LogUtils.getLogger();
   private static final Gson b = new GsonBuilder().setPrettyPrinting().create();
   private final File c;
   private final Map<String, V> d = Maps.newHashMap();

   public JsonList(File file) {
      this.c = file;
   }

   public File b() {
      return this.c;
   }

   public void a(V v0) {
      this.d.put(this.a(v0.g()), v0);

      try {
         this.e();
      } catch (IOException var3) {
         a.warn("Could not save the list after adding a user.", var3);
      }
   }

   @Nullable
   public V b(K k0) {
      this.g();
      return this.d.get(this.a(k0));
   }

   public void c(K k0) {
      this.d.remove(this.a(k0));

      try {
         this.e();
      } catch (IOException var3) {
         a.warn("Could not save the list after removing a user.", var3);
      }
   }

   public void b(JsonListEntry<K> jsonlistentry) {
      this.c(jsonlistentry.g());
   }

   public String[] a() {
      return this.d.keySet().toArray(new String[0]);
   }

   public Collection<V> getValues() {
      return this.d.values();
   }

   public boolean c() {
      return this.d.size() < 1;
   }

   protected String a(K k0) {
      return k0.toString();
   }

   protected boolean d(K k0) {
      return this.d.containsKey(this.a(k0));
   }

   private void g() {
      List<K> list = Lists.newArrayList();

      for(V v0 : this.d.values()) {
         if (v0.f()) {
            list.add(v0.g());
         }
      }

      for(K k0 : list) {
         this.d.remove(this.a(k0));
      }
   }

   protected abstract JsonListEntry<K> a(JsonObject var1);

   public Collection<V> d() {
      return this.d.values();
   }

   public void e() throws IOException {
      JsonArray jsonarray = new JsonArray();
      Stream<JsonObject> stream = this.d.values().stream().map(jsonlistentry -> {
         JsonObject jsonobject = new JsonObject();
         return SystemUtils.a(jsonobject, jsonlistentry::a);
      });
      stream.forEach(jsonarray::add);

      try (BufferedWriter bufferedwriter = Files.newWriter(this.c, StandardCharsets.UTF_8)) {
         b.toJson(jsonarray, bufferedwriter);
      }
   }

   public void f() throws IOException {
      if (this.c.exists()) {
         BufferedReader bufferedreader = Files.newReader(this.c, StandardCharsets.UTF_8);

         try {
            JsonArray jsonarray = (JsonArray)b.fromJson(bufferedreader, JsonArray.class);
            this.d.clear();

            for(JsonElement jsonelement : jsonarray) {
               JsonObject jsonobject = ChatDeserializer.m(jsonelement, "entry");
               JsonListEntry<K> jsonlistentry = this.a(jsonobject);
               if (jsonlistentry.g() != null) {
                  this.d.put(this.a(jsonlistentry.g()), (V)jsonlistentry);
               }
            }
         } catch (NullPointerException | JsonParseException var8) {
            Bukkit.getLogger().log(Level.WARNING, "Unable to read file " + this.c + ", backing it up to {0}.backup and creating new copy.", var8);
            File backup = new File(this.c + ".backup");
            this.c.renameTo(backup);
            this.c.delete();
         } catch (Throwable var9) {
            if (bufferedreader != null) {
               try {
                  bufferedreader.close();
               } catch (Throwable var7) {
                  var9.addSuppressed(var7);
               }
            }

            throw var9;
         }

         if (bufferedreader != null) {
            bufferedreader.close();
         }
      }
   }
}
