package net.minecraft.server.network;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.network.chat.FilterMask;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.thread.ThreadedMailbox;
import org.slf4j.Logger;

public class TextFilter implements AutoCloseable {
   private static final Logger a = LogUtils.getLogger();
   private static final AtomicInteger b = new AtomicInteger(1);
   private static final ThreadFactory c = var0 -> {
      Thread var1 = new Thread(var0);
      var1.setName("Chat-Filter-Worker-" + b.getAndIncrement());
      return var1;
   };
   private static final String d = "v1/chat";
   private final URL e;
   private final TextFilter.c f;
   final URL g;
   final TextFilter.b h;
   final URL i;
   final TextFilter.b j;
   private final String k;
   final TextFilter.a l;
   final ExecutorService m;

   private TextFilter(URL var0, TextFilter.c var1, URL var2, TextFilter.b var3, URL var4, TextFilter.b var5, String var6, TextFilter.a var7, int var8) {
      this.k = var6;
      this.l = var7;
      this.e = var0;
      this.f = var1;
      this.g = var2;
      this.h = var3;
      this.i = var4;
      this.j = var5;
      this.m = Executors.newFixedThreadPool(var8, c);
   }

   private static URL a(URI var0, @Nullable JsonObject var1, String var2, String var3) throws MalformedURLException {
      String var4 = a(var1, var2, var3);
      return var0.resolve("/" + var4).toURL();
   }

   private static String a(@Nullable JsonObject var0, String var1, String var2) {
      return var0 != null ? ChatDeserializer.a(var0, var1, var2) : var2;
   }

   @Nullable
   public static TextFilter a(String var0) {
      if (Strings.isNullOrEmpty(var0)) {
         return null;
      } else {
         try {
            JsonObject var1 = ChatDeserializer.a(var0);
            URI var2 = new URI(ChatDeserializer.h(var1, "apiServer"));
            String var3 = ChatDeserializer.h(var1, "apiKey");
            if (var3.isEmpty()) {
               throw new IllegalArgumentException("Missing API key");
            } else {
               int var4 = ChatDeserializer.a(var1, "ruleId", 1);
               String var5 = ChatDeserializer.a(var1, "serverId", "");
               String var6 = ChatDeserializer.a(var1, "roomId", "Java:Chat");
               int var7 = ChatDeserializer.a(var1, "hashesToDrop", -1);
               int var8 = ChatDeserializer.a(var1, "maxConcurrentRequests", 7);
               JsonObject var9 = ChatDeserializer.a(var1, "endpoints", null);
               String var10 = a(var9, "chat", "v1/chat");
               boolean var11 = var10.equals("v1/chat");
               URL var12 = var2.resolve("/" + var10).toURL();
               URL var13 = a(var2, var9, "join", "v1/join");
               URL var14 = a(var2, var9, "leave", "v1/leave");
               TextFilter.b var15 = var2x -> {
                  JsonObject var3x = new JsonObject();
                  var3x.addProperty("server", var5);
                  var3x.addProperty("room", var6);
                  var3x.addProperty("user_id", var2x.getId().toString());
                  var3x.addProperty("user_display_name", var2x.getName());
                  return var3x;
               };
               TextFilter.c var16;
               if (var11) {
                  var16 = (var3x, var4x) -> {
                     JsonObject var5x = new JsonObject();
                     var5x.addProperty("rule", var4);
                     var5x.addProperty("server", var5);
                     var5x.addProperty("room", var6);
                     var5x.addProperty("player", var3x.getId().toString());
                     var5x.addProperty("player_display_name", var3x.getName());
                     var5x.addProperty("text", var4x);
                     var5x.addProperty("language", "*");
                     return var5x;
                  };
               } else {
                  String var17 = String.valueOf(var4);
                  var16 = (var3x, var4x) -> {
                     JsonObject var5x = new JsonObject();
                     var5x.addProperty("rule_id", var17);
                     var5x.addProperty("category", var5);
                     var5x.addProperty("subcategory", var6);
                     var5x.addProperty("user_id", var3x.getId().toString());
                     var5x.addProperty("user_display_name", var3x.getName());
                     var5x.addProperty("text", var4x);
                     var5x.addProperty("language", "*");
                     return var5x;
                  };
               }

               TextFilter.a var17 = TextFilter.a.select(var7);
               String var18 = Base64.getEncoder().encodeToString(var3.getBytes(StandardCharsets.US_ASCII));
               return new TextFilter(var12, var16, var13, var15, var14, var15, var18, var17, var8);
            }
         } catch (Exception var19) {
            a.warn("Failed to parse chat filter config {}", var0, var19);
            return null;
         }
      }
   }

   void a(GameProfile var0, URL var1, TextFilter.b var2, Executor var3) {
      var3.execute(() -> {
         JsonObject var3x = var2.encode(var0);

         try {
            this.b(var3x, var1);
         } catch (Exception var6) {
            a.warn("Failed to send join/leave packet to {} for player {}", new Object[]{var1, var0, var6});
         }
      });
   }

   CompletableFuture<FilteredText> a(GameProfile var0, String var1, TextFilter.a var2, Executor var3) {
      return var1.isEmpty() ? CompletableFuture.completedFuture(FilteredText.a) : CompletableFuture.supplyAsync(() -> {
         JsonObject var3x = this.f.encode(var0, var1);

         try {
            JsonObject var4x = this.a(var3x, this.e);
            boolean var5 = ChatDeserializer.a(var4x, "response", false);
            if (var5) {
               return FilteredText.a(var1);
            } else {
               String var6 = ChatDeserializer.a(var4x, "hashed", null);
               if (var6 == null) {
                  return FilteredText.b(var1);
               } else {
                  JsonArray var7 = ChatDeserializer.u(var4x, "hashes");
                  FilterMask var8 = this.a(var1, var7, var2);
                  return new FilteredText(var1, var8);
               }
            }
         } catch (Exception var10) {
            a.warn("Failed to validate message '{}'", var1, var10);
            return FilteredText.b(var1);
         }
      }, var3);
   }

   private FilterMask a(String var0, JsonArray var1, TextFilter.a var2) {
      if (var1.isEmpty()) {
         return FilterMask.c;
      } else if (var2.shouldIgnore(var0, var1.size())) {
         return FilterMask.b;
      } else {
         FilterMask var3 = new FilterMask(var0.length());

         for(int var4 = 0; var4 < var1.size(); ++var4) {
            var3.a(var1.get(var4).getAsInt());
         }

         return var3;
      }
   }

   @Override
   public void close() {
      this.m.shutdownNow();
   }

   private void a(InputStream var0) throws IOException {
      byte[] var1 = new byte[1024];

      while(var0.read(var1) != -1) {
      }
   }

   private JsonObject a(JsonObject var0, URL var1) throws IOException {
      HttpURLConnection var2 = this.c(var0, var1);

      JsonObject var5;
      try (InputStream var3 = var2.getInputStream()) {
         if (var2.getResponseCode() == 204) {
            return new JsonObject();
         }

         try {
            var5 = Streams.parse(new JsonReader(new InputStreamReader(var3, StandardCharsets.UTF_8))).getAsJsonObject();
         } finally {
            this.a(var3);
         }
      }

      return var5;
   }

   private void b(JsonObject var0, URL var1) throws IOException {
      HttpURLConnection var2 = this.c(var0, var1);

      try (InputStream var3 = var2.getInputStream()) {
         this.a(var3);
      }
   }

   private HttpURLConnection c(JsonObject var0, URL var1) throws IOException {
      HttpURLConnection var2 = (HttpURLConnection)var1.openConnection();
      var2.setConnectTimeout(15000);
      var2.setReadTimeout(2000);
      var2.setUseCaches(false);
      var2.setDoOutput(true);
      var2.setDoInput(true);
      var2.setRequestMethod("POST");
      var2.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      var2.setRequestProperty("Accept", "application/json");
      var2.setRequestProperty("Authorization", "Basic " + this.k);
      var2.setRequestProperty("User-Agent", "Minecraft server" + SharedConstants.b().c());
      OutputStreamWriter var3 = new OutputStreamWriter(var2.getOutputStream(), StandardCharsets.UTF_8);

      try {
         JsonWriter var4 = new JsonWriter(var3);

         try {
            Streams.write(var0, var4);
         } catch (Throwable var10) {
            try {
               var4.close();
            } catch (Throwable var9) {
               var10.addSuppressed(var9);
            }

            throw var10;
         }

         var4.close();
      } catch (Throwable var11) {
         try {
            var3.close();
         } catch (Throwable var8) {
            var11.addSuppressed(var8);
         }

         throw var11;
      }

      var3.close();
      int var3x = var2.getResponseCode();
      if (var3x >= 200 && var3x < 300) {
         return var2;
      } else {
         throw new TextFilter.e(var3x + " " + var2.getResponseMessage());
      }
   }

   public ITextFilter a(GameProfile var0) {
      return new TextFilter.d(var0);
   }

   @FunctionalInterface
   public interface a {
      TextFilter.a a = (var0, var1) -> false;
      TextFilter.a b = (var0, var1) -> var0.length() == var1;

      static TextFilter.a ignoreOverThreshold(int var0) {
         return (var1, var2) -> var2 >= var0;
      }

      static TextFilter.a select(int var0) {
         return switch(var0) {
            case -1 -> a;
            case 0 -> b;
            default -> ignoreOverThreshold(var0);
         };
      }

      boolean shouldIgnore(String var1, int var2);
   }

   @FunctionalInterface
   interface b {
      JsonObject encode(GameProfile var1);
   }

   @FunctionalInterface
   interface c {
      JsonObject encode(GameProfile var1, String var2);
   }

   class d implements ITextFilter {
      private final GameProfile c;
      private final Executor d;

      d(GameProfile var1) {
         this.c = var1;
         ThreadedMailbox<Runnable> var2 = ThreadedMailbox.a(TextFilter.this.m, "chat stream for " + var1.getName());
         this.d = var2::a;
      }

      @Override
      public void a() {
         TextFilter.this.a(this.c, TextFilter.this.g, TextFilter.this.h, this.d);
      }

      @Override
      public void b() {
         TextFilter.this.a(this.c, TextFilter.this.i, TextFilter.this.j, this.d);
      }

      @Override
      public CompletableFuture<List<FilteredText>> a(List<String> var0) {
         List<CompletableFuture<FilteredText>> var1 = var0.stream()
            .map(var0x -> TextFilter.this.a(this.c, var0x, TextFilter.this.l, this.d))
            .collect(ImmutableList.toImmutableList());
         return SystemUtils.c(var1).exceptionally(var0x -> ImmutableList.of());
      }

      @Override
      public CompletableFuture<FilteredText> a(String var0) {
         return TextFilter.this.a(this.c, var0, TextFilter.this.l, this.d);
      }
   }

   public static class e extends RuntimeException {
      e(String var0) {
         super(var0);
      }
   }
}
