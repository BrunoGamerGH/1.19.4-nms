package net.minecraft.util.profiling;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;

public class MethodProfilerResultsFilled implements MethodProfilerResults {
   private static final Logger a = LogUtils.getLogger();
   private static final MethodProfilerResult b = new MethodProfilerResult() {
      @Override
      public long a() {
         return 0L;
      }

      @Override
      public long b() {
         return 0L;
      }

      @Override
      public long c() {
         return 0L;
      }

      @Override
      public Object2LongMap<String> d() {
         return Object2LongMaps.emptyMap();
      }
   };
   private static final Splitter c = Splitter.on('\u001e');
   private static final Comparator<Entry<String, MethodProfilerResultsFilled.a>> e = Entry.<String, MethodProfilerResultsFilled.a>comparingByValue(
         Comparator.comparingLong(var0 -> var0.b)
      )
      .reversed();
   private final Map<String, ? extends MethodProfilerResult> f;
   private final long g;
   private final int h;
   private final long i;
   private final int j;
   private final int k;

   public MethodProfilerResultsFilled(Map<String, ? extends MethodProfilerResult> var0, long var1, int var3, long var4, int var6) {
      this.f = var0;
      this.g = var1;
      this.h = var3;
      this.i = var4;
      this.j = var6;
      this.k = var6 - var3;
   }

   private MethodProfilerResult c(String var0) {
      MethodProfilerResult var1 = this.f.get(var0);
      return var1 != null ? var1 : b;
   }

   @Override
   public List<MethodProfilerResultsField> a(String var0) {
      String var1 = var0;
      MethodProfilerResult var2 = this.c("root");
      long var3 = var2.a();
      MethodProfilerResult var5 = this.c(var0);
      long var6 = var5.a();
      long var8 = var5.c();
      List<MethodProfilerResultsField> var10 = Lists.newArrayList();
      if (!var0.isEmpty()) {
         var0 = var0 + "\u001e";
      }

      long var11 = 0L;

      for(String var14 : this.f.keySet()) {
         if (a(var0, var14)) {
            var11 += this.c(var14).a();
         }
      }

      float var13 = (float)var11;
      if (var11 < var6) {
         var11 = var6;
      }

      if (var3 < var11) {
         var3 = var11;
      }

      for(String var15 : this.f.keySet()) {
         if (a(var0, var15)) {
            MethodProfilerResult var16 = this.c(var15);
            long var17 = var16.a();
            double var19 = (double)var17 * 100.0 / (double)var11;
            double var21 = (double)var17 * 100.0 / (double)var3;
            String var23 = var15.substring(var0.length());
            var10.add(new MethodProfilerResultsField(var23, var19, var21, var16.c()));
         }
      }

      if ((float)var11 > var13) {
         var10.add(
            new MethodProfilerResultsField(
               "unspecified", (double)((float)var11 - var13) * 100.0 / (double)var11, (double)((float)var11 - var13) * 100.0 / (double)var3, var8
            )
         );
      }

      Collections.sort(var10);
      var10.add(0, new MethodProfilerResultsField(var1, 100.0, (double)var11 * 100.0 / (double)var3, var8));
      return var10;
   }

   private static boolean a(String var0, String var1) {
      return var1.length() > var0.length() && var1.startsWith(var0) && var1.indexOf(30, var0.length() + 1) < 0;
   }

   private Map<String, MethodProfilerResultsFilled.a> h() {
      Map<String, MethodProfilerResultsFilled.a> var0 = Maps.newTreeMap();
      this.f.forEach((var1x, var2) -> {
         Object2LongMap<String> var3 = var2.d();
         if (!var3.isEmpty()) {
            List<String> var4 = c.splitToList(var1x);
            var3.forEach((var2x, var3x) -> var0.computeIfAbsent(var2x, var0xxx -> new MethodProfilerResultsFilled.a()).a(var4.iterator(), var3x));
         }
      });
      return var0;
   }

   @Override
   public long a() {
      return this.g;
   }

   @Override
   public int b() {
      return this.h;
   }

   @Override
   public long c() {
      return this.i;
   }

   @Override
   public int d() {
      return this.j;
   }

   @Override
   public boolean a(Path var0) {
      Writer var1 = null;

      boolean var4;
      try {
         Files.createDirectories(var0.getParent());
         var1 = Files.newBufferedWriter(var0, StandardCharsets.UTF_8);
         var1.write(this.a(this.g(), this.f()));
         return true;
      } catch (Throwable var8) {
         a.error("Could not save profiler results to {}", var0, var8);
         var4 = false;
      } finally {
         IOUtils.closeQuietly(var1);
      }

      return var4;
   }

   protected String a(long var0, int var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append("---- Minecraft Profiler Results ----\n");
      var3.append("// ");
      var3.append(i());
      var3.append("\n\n");
      var3.append("Version: ").append(SharedConstants.b().b()).append('\n');
      var3.append("Time span: ").append(var0 / 1000000L).append(" ms\n");
      var3.append("Tick span: ").append(var2).append(" ticks\n");
      var3.append("// This is approximately ")
         .append(String.format(Locale.ROOT, "%.2f", (float)var2 / ((float)var0 / 1.0E9F)))
         .append(" ticks per second. It should be ")
         .append(20)
         .append(" ticks per second\n\n");
      var3.append("--- BEGIN PROFILE DUMP ---\n\n");
      this.a(0, "root", var3);
      var3.append("--- END PROFILE DUMP ---\n\n");
      Map<String, MethodProfilerResultsFilled.a> var4 = this.h();
      if (!var4.isEmpty()) {
         var3.append("--- BEGIN COUNTER DUMP ---\n\n");
         this.a(var4, var3, var2);
         var3.append("--- END COUNTER DUMP ---\n\n");
      }

      return var3.toString();
   }

   @Override
   public String e() {
      StringBuilder var0 = new StringBuilder();
      this.a(0, "root", var0);
      return var0.toString();
   }

   private static StringBuilder a(StringBuilder var0, int var1) {
      var0.append(String.format(Locale.ROOT, "[%02d] ", var1));

      for(int var2 = 0; var2 < var1; ++var2) {
         var0.append("|   ");
      }

      return var0;
   }

   private void a(int var0, String var1, StringBuilder var2) {
      List<MethodProfilerResultsField> var3 = this.a(var1);
      Object2LongMap<String> var4 = ((MethodProfilerResult)ObjectUtils.firstNonNull(new MethodProfilerResult[]{this.f.get(var1), b})).d();
      var4.forEach((var2x, var3x) -> a(var2, var0).append('#').append(var2x).append(' ').append(var3x).append('/').append(var3x / (long)this.k).append('\n'));
      if (var3.size() >= 3) {
         for(int var5 = 1; var5 < var3.size(); ++var5) {
            MethodProfilerResultsField var6 = var3.get(var5);
            a(var2, var0)
               .append(var6.d)
               .append('(')
               .append(var6.c)
               .append('/')
               .append(String.format(Locale.ROOT, "%.0f", (float)var6.c / (float)this.k))
               .append(')')
               .append(" - ")
               .append(String.format(Locale.ROOT, "%.2f", var6.a))
               .append("%/")
               .append(String.format(Locale.ROOT, "%.2f", var6.b))
               .append("%\n");
            if (!"unspecified".equals(var6.d)) {
               try {
                  this.a(var0 + 1, var1 + "\u001e" + var6.d, var2);
               } catch (Exception var9) {
                  var2.append("[[ EXCEPTION ").append(var9).append(" ]]");
               }
            }
         }
      }
   }

   private void a(int var0, String var1, MethodProfilerResultsFilled.a var2, int var3, StringBuilder var4) {
      a(var4, var0)
         .append(var1)
         .append(" total:")
         .append(var2.a)
         .append('/')
         .append(var2.b)
         .append(" average: ")
         .append(var2.a / (long)var3)
         .append('/')
         .append(var2.b / (long)var3)
         .append('\n');
      var2.c.entrySet().stream().sorted(e).forEach(var3x -> this.a(var0 + 1, var3x.getKey(), var3x.getValue(), var3, var4));
   }

   private void a(Map<String, MethodProfilerResultsFilled.a> var0, StringBuilder var1, int var2) {
      var0.forEach((var2x, var3x) -> {
         var1.append("-- Counter: ").append(var2x).append(" --\n");
         this.a(0, "root", var3x.c.get("root"), var2, var1);
         var1.append("\n\n");
      });
   }

   private static String i() {
      String[] var0 = new String[]{
         "I'd Rather Be Surfing",
         "Shiny numbers!",
         "Am I not running fast enough? :(",
         "I'm working as hard as I can!",
         "Will I ever be good enough for you? :(",
         "Speedy. Zoooooom!",
         "Hello world",
         "40% better than a crash report.",
         "Now with extra numbers",
         "Now with less numbers",
         "Now with the same numbers",
         "You should add flames to things, it makes them go faster!",
         "Do you feel the need for... optimization?",
         "*cracks redstone whip*",
         "Maybe if you treated it better then it'll have more motivation to work faster! Poor server."
      };

      try {
         return var0[(int)(SystemUtils.c() % (long)var0.length)];
      } catch (Throwable var2) {
         return "Witty comment unavailable :(";
      }
   }

   @Override
   public int f() {
      return this.k;
   }

   static class a {
      long a;
      long b;
      final Map<String, MethodProfilerResultsFilled.a> c = Maps.newHashMap();

      public void a(Iterator<String> var0, long var1) {
         this.b += var1;
         if (!var0.hasNext()) {
            this.a += var1;
         } else {
            this.c.computeIfAbsent(var0.next(), var0x -> new MethodProfilerResultsFilled.a()).a(var0, var1);
         }
      }
   }
}
