package net.minecraft.server.players;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.UUIDUtil;
import org.slf4j.Logger;
import org.spigotmc.SpigotConfig;

public class UserCache {
   private static final Logger a = LogUtils.getLogger();
   private static final int b = 1000;
   private static final int c = 1;
   private static boolean d;
   private final Map<String, UserCache.UserCacheEntry> e = Maps.newConcurrentMap();
   private final Map<UUID, UserCache.UserCacheEntry> f = Maps.newConcurrentMap();
   private final Map<String, CompletableFuture<Optional<GameProfile>>> g = Maps.newConcurrentMap();
   private final GameProfileRepository h;
   private final Gson i = new GsonBuilder().create();
   private final File j;
   private final AtomicLong k = new AtomicLong();
   @Nullable
   private Executor l;

   public UserCache(GameProfileRepository gameprofilerepository, File file) {
      this.h = gameprofilerepository;
      this.j = file;
      Lists.reverse(this.b()).forEach(this::a);
   }

   private void a(UserCache.UserCacheEntry usercache_usercacheentry) {
      GameProfile gameprofile = usercache_usercacheentry.a();
      usercache_usercacheentry.a(this.e());
      String s = gameprofile.getName();
      if (s != null) {
         this.e.put(s.toLowerCase(Locale.ROOT), usercache_usercacheentry);
      }

      UUID uuid = gameprofile.getId();
      if (uuid != null) {
         this.f.put(uuid, usercache_usercacheentry);
      }
   }

   private static Optional<GameProfile> a(GameProfileRepository gameprofilerepository, String s) {
      final AtomicReference<GameProfile> atomicreference = new AtomicReference();
      ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
         public void onProfileLookupSucceeded(GameProfile gameprofile) {
            atomicreference.set(gameprofile);
         }

         public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
            atomicreference.set(null);
         }
      };
      gameprofilerepository.findProfilesByNames(new String[]{s}, Agent.MINECRAFT, profilelookupcallback);
      GameProfile gameprofile = (GameProfile)atomicreference.get();
      if (!d() && gameprofile == null) {
         UUID uuid = UUIDUtil.a(new GameProfile(null, s));
         return Optional.of(new GameProfile(uuid, s));
      } else {
         return Optional.ofNullable(gameprofile);
      }
   }

   public static void a(boolean flag) {
      d = flag;
   }

   private static boolean d() {
      return d;
   }

   public void a(GameProfile gameprofile) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(2, 1);
      Date date = calendar.getTime();
      UserCache.UserCacheEntry usercache_usercacheentry = new UserCache.UserCacheEntry(gameprofile, date);
      this.a(usercache_usercacheentry);
      if (!SpigotConfig.saveUserCacheOnStopOnly) {
         this.c();
      }
   }

   private long e() {
      return this.k.incrementAndGet();
   }

   public Optional<GameProfile> a(String s) {
      String s1 = s.toLowerCase(Locale.ROOT);
      UserCache.UserCacheEntry usercache_usercacheentry = this.e.get(s1);
      boolean flag = false;
      if (usercache_usercacheentry != null && new Date().getTime() >= usercache_usercacheentry.b.getTime()) {
         this.f.remove(usercache_usercacheentry.a().getId());
         this.e.remove(usercache_usercacheentry.a().getName().toLowerCase(Locale.ROOT));
         flag = true;
         usercache_usercacheentry = null;
      }

      Optional optional;
      if (usercache_usercacheentry != null) {
         usercache_usercacheentry.a(this.e());
         optional = Optional.of(usercache_usercacheentry.a());
      } else {
         optional = a(this.h, s);
         if (optional.isPresent()) {
            this.a((GameProfile)optional.get());
            flag = false;
         }
      }

      if (flag && !SpigotConfig.saveUserCacheOnStopOnly) {
         this.c();
      }

      return optional;
   }

   public void a(String s, Consumer<Optional<GameProfile>> consumer) {
      if (this.l == null) {
         throw new IllegalStateException("No executor");
      } else {
         CompletableFuture<Optional<GameProfile>> completablefuture = this.g.get(s);
         if (completablefuture != null) {
            this.g.put(s, completablefuture.whenCompleteAsync((optional, throwable) -> consumer.accept(optional), this.l));
         } else {
            this.g
               .put(
                  s,
                  CompletableFuture.<Optional<GameProfile>>supplyAsync(() -> this.a(s), SystemUtils.f())
                     .whenCompleteAsync((optional, throwable) -> this.g.remove(s), this.l)
                     .whenCompleteAsync((optional, throwable) -> consumer.accept(optional), this.l)
               );
         }
      }
   }

   public Optional<GameProfile> a(UUID uuid) {
      UserCache.UserCacheEntry usercache_usercacheentry = this.f.get(uuid);
      if (usercache_usercacheentry == null) {
         return Optional.empty();
      } else {
         usercache_usercacheentry.a(this.e());
         return Optional.of(usercache_usercacheentry.a());
      }
   }

   public void a(Executor executor) {
      this.l = executor;
   }

   public void a() {
      this.l = null;
   }

   private static DateFormat f() {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.ROOT);
   }

   public List<UserCache.UserCacheEntry> b() {
      ArrayList arraylist = Lists.newArrayList();

      try {
         ArrayList arraylist1;
         try (BufferedReader bufferedreader = Files.newReader(this.j, StandardCharsets.UTF_8)) {
            JsonArray jsonarray = (JsonArray)this.i.fromJson(bufferedreader, JsonArray.class);
            if (jsonarray != null) {
               DateFormat dateformat = f();
               jsonarray.forEach(jsonelement -> {
                  Optional optional = a(jsonelement, dateformat);
                  optional.ifPresent(arraylist::add);
               });
               return arraylist;
            }

            arraylist1 = arraylist;
         }

         return arraylist1;
      } catch (FileNotFoundException var8) {
      } catch (NullPointerException | JsonSyntaxException var9) {
         a.warn("Usercache.json is corrupted or has bad formatting. Deleting it to prevent further issues.");
         this.j.delete();
      } catch (IOException | JsonParseException var10) {
         a.warn("Failed to load profile cache {}", this.j, var10);
      }

      return arraylist;
   }

   public void c() {
      JsonArray jsonarray = new JsonArray();
      DateFormat dateformat = f();
      this.a(SpigotConfig.userCacheCap).forEach(usercache_usercacheentry -> jsonarray.add(a(usercache_usercacheentry, dateformat)));
      String s = this.i.toJson(jsonarray);

      try (BufferedWriter bufferedwriter = Files.newWriter(this.j, StandardCharsets.UTF_8)) {
         bufferedwriter.write(s);
      } catch (IOException var9) {
      }
   }

   private Stream<UserCache.UserCacheEntry> a(int i) {
      return ImmutableList.copyOf(this.f.values()).stream().sorted(Comparator.comparing(UserCache.UserCacheEntry::c).reversed()).limit((long)i);
   }

   private static JsonElement a(UserCache.UserCacheEntry usercache_usercacheentry, DateFormat dateformat) {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("name", usercache_usercacheentry.a().getName());
      UUID uuid = usercache_usercacheentry.a().getId();
      jsonobject.addProperty("uuid", uuid == null ? "" : uuid.toString());
      jsonobject.addProperty("expiresOn", dateformat.format(usercache_usercacheentry.b()));
      return jsonobject;
   }

   private static Optional<UserCache.UserCacheEntry> a(JsonElement jsonelement, DateFormat dateformat) {
      if (jsonelement.isJsonObject()) {
         JsonObject jsonobject = jsonelement.getAsJsonObject();
         JsonElement jsonelement1 = jsonobject.get("name");
         JsonElement jsonelement2 = jsonobject.get("uuid");
         JsonElement jsonelement3 = jsonobject.get("expiresOn");
         if (jsonelement1 != null && jsonelement2 != null) {
            String s = jsonelement2.getAsString();
            String s1 = jsonelement1.getAsString();
            Date date = null;
            if (jsonelement3 != null) {
               try {
                  date = dateformat.parse(jsonelement3.getAsString());
               } catch (ParseException var12) {
               }
            }

            if (s1 != null && s != null && date != null) {
               UUID uuid;
               try {
                  uuid = UUID.fromString(s);
               } catch (Throwable var11) {
                  return Optional.empty();
               }

               return Optional.of(new UserCache.UserCacheEntry(new GameProfile(uuid, s1), date));
            } else {
               return Optional.empty();
            }
         } else {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   private static class UserCacheEntry {
      private final GameProfile a;
      final Date b;
      private volatile long c;

      UserCacheEntry(GameProfile gameprofile, Date date) {
         this.a = gameprofile;
         this.b = date;
      }

      public GameProfile a() {
         return this.a;
      }

      public Date b() {
         return this.b;
      }

      public void a(long i) {
         this.c = i;
      }

      public long c() {
         return this.c;
      }
   }
}
