package net.minecraft.data;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.concurrent.CompletableFuture;
import java.util.function.ToIntFunction;
import net.minecraft.SystemUtils;
import net.minecraft.util.ChatDeserializer;
import org.slf4j.Logger;

public interface DebugReportProvider {
   ToIntFunction<String> a = SystemUtils.a(new Object2IntOpenHashMap(), var0 -> {
      var0.put("type", 0);
      var0.put("parent", 1);
      var0.defaultReturnValue(2);
   });
   Comparator<String> b = Comparator.comparingInt(a).thenComparing(var0 -> var0);
   Logger c = LogUtils.getLogger();

   CompletableFuture<?> a(CachedOutput var1);

   String a();

   static CompletableFuture<?> a(CachedOutput var0, JsonElement var1, Path var2) {
      return CompletableFuture.runAsync(() -> {
         try {
            ByteArrayOutputStream var3 = new ByteArrayOutputStream();
            HashingOutputStream var4 = new HashingOutputStream(Hashing.sha1(), var3);
            JsonWriter var5 = new JsonWriter(new OutputStreamWriter(var4, StandardCharsets.UTF_8));

            try {
               var5.setSerializeNulls(false);
               var5.setIndent("  ");
               ChatDeserializer.a(var5, var1, b);
            } catch (Throwable var9) {
               try {
                  var5.close();
               } catch (Throwable var8) {
                  var9.addSuppressed(var8);
               }

               throw var9;
            }

            var5.close();
            var0.writeIfNeeded(var2, var3.toByteArray(), var4.hash());
         } catch (IOException var10) {
            c.error("Failed to save file to {}", var2, var10);
         }
      }, SystemUtils.f());
   }

   @FunctionalInterface
   public interface a<T extends DebugReportProvider> {
      T create(PackOutput var1);
   }
}
