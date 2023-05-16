package net.minecraft.util.profiling.jfr;

import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import jdk.jfr.Configuration;
import jdk.jfr.Event;
import jdk.jfr.FlightRecorder;
import jdk.jfr.FlightRecorderListener;
import jdk.jfr.Recording;
import jdk.jfr.RecordingState;
import net.minecraft.FileUtils;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.jfr.callback.ProfiledDuration;
import net.minecraft.util.profiling.jfr.event.ChunkGenerationEvent;
import net.minecraft.util.profiling.jfr.event.NetworkSummaryEvent;
import net.minecraft.util.profiling.jfr.event.PacketReceivedEvent;
import net.minecraft.util.profiling.jfr.event.PacketSentEvent;
import net.minecraft.util.profiling.jfr.event.ServerTickTimeEvent;
import net.minecraft.util.profiling.jfr.event.WorldLoadFinishedEvent;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.World;
import org.slf4j.Logger;

public class JfrProfiler implements JvmProfiler {
   private static final Logger f = LogUtils.getLogger();
   public static final String a = "Minecraft";
   public static final String b = "World Generation";
   public static final String c = "Ticking";
   public static final String d = "Network";
   private static final List<Class<? extends Event>> g = List.of(
      ChunkGenerationEvent.class,
      PacketReceivedEvent.class,
      PacketSentEvent.class,
      NetworkSummaryEvent.class,
      ServerTickTimeEvent.class,
      WorldLoadFinishedEvent.class
   );
   private static final String h = "/flightrecorder-config.jfc";
   private static final DateTimeFormatter i = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd-HHmmss").toFormatter().withZone(ZoneId.systemDefault());
   private static final JfrProfiler j = new JfrProfiler();
   @Nullable
   Recording k;
   private float l;
   private final Map<String, NetworkSummaryEvent.b> m = new ConcurrentHashMap<>();

   private JfrProfiler() {
      g.forEach(FlightRecorder::register);
      FlightRecorder.addPeriodicEvent(ServerTickTimeEvent.class, () -> new ServerTickTimeEvent(this.l).commit());
      FlightRecorder.addPeriodicEvent(NetworkSummaryEvent.class, () -> {
         Iterator<NetworkSummaryEvent.b> var0 = this.m.values().iterator();

         while(var0.hasNext()) {
            var0.next().a();
            var0.remove();
         }
      });
   }

   public static JfrProfiler a() {
      return j;
   }

   @Override
   public boolean a(Environment var0) {
      URL var1 = JfrProfiler.class.getResource("/flightrecorder-config.jfc");
      if (var1 == null) {
         f.warn("Could not find default flight recorder config at {}", "/flightrecorder-config.jfc");
         return false;
      } else {
         try {
            boolean var4;
            try (BufferedReader var2 = new BufferedReader(new InputStreamReader(var1.openStream()))) {
               var4 = this.a(var2, var0);
            }

            return var4;
         } catch (IOException var8) {
            f.warn("Failed to start flight recorder using configuration at {}", var1, var8);
            return false;
         }
      }
   }

   @Override
   public Path b() {
      if (this.k == null) {
         throw new IllegalStateException("Not currently profiling");
      } else {
         this.m.clear();
         Path var0 = this.k.getDestination();
         this.k.stop();
         return var0;
      }
   }

   @Override
   public boolean c() {
      return this.k != null;
   }

   @Override
   public boolean d() {
      return FlightRecorder.isAvailable();
   }

   private boolean a(Reader var0, Environment var1) {
      if (this.c()) {
         f.warn("Profiling already in progress");
         return false;
      } else {
         try {
            Configuration var2 = Configuration.create(var0);
            String var3 = i.format(Instant.now());
            this.k = SystemUtils.a(new Recording(var2), var2x -> {
               g.forEach(var2x::enable);
               var2x.setDumpOnExit(true);
               var2x.setToDisk(true);
               var2x.setName(String.format(Locale.ROOT, "%s-%s-%s", var1.a(), SharedConstants.b().c(), var3));
            });
            Path var4 = Paths.get(String.format(Locale.ROOT, "debug/%s-%s.jfr", var1.a(), var3));
            FileUtils.c(var4.getParent());
            this.k.setDestination(var4);
            this.k.start();
            this.f();
         } catch (ParseException | IOException var6) {
            f.warn("Failed to start jfr profiling", var6);
            return false;
         }

         f.info(
            "Started flight recorder profiling id({}):name({}) - will dump to {} on exit or stop command",
            new Object[]{this.k.getId(), this.k.getName(), this.k.getDestination()}
         );
         return true;
      }
   }

   private void f() {
      FlightRecorder.addListener(new FlightRecorderListener() {
         final SummaryReporter a = new SummaryReporter(() -> JfrProfiler.this.k = null);

         @Override
         public void recordingStateChanged(Recording var0) {
            if (var0 == JfrProfiler.this.k && var0.getState() == RecordingState.STOPPED) {
               this.a.a(var0.getDestination());
               FlightRecorder.removeListener(this);
            }
         }
      });
   }

   @Override
   public void a(float var0) {
      if (ServerTickTimeEvent.TYPE.isEnabled()) {
         this.l = var0;
      }
   }

   @Override
   public void a(int var0, int var1, SocketAddress var2, int var3) {
      if (PacketReceivedEvent.TYPE.isEnabled()) {
         new PacketReceivedEvent(var0, var1, var2, var3).commit();
      }

      if (NetworkSummaryEvent.TYPE.isEnabled()) {
         this.a(var2).b(var3);
      }
   }

   @Override
   public void b(int var0, int var1, SocketAddress var2, int var3) {
      if (PacketSentEvent.TYPE.isEnabled()) {
         new PacketSentEvent(var0, var1, var2, var3).commit();
      }

      if (NetworkSummaryEvent.TYPE.isEnabled()) {
         this.a(var2).a(var3);
      }
   }

   private NetworkSummaryEvent.b a(SocketAddress var0) {
      return this.m.computeIfAbsent(var0.toString(), NetworkSummaryEvent.b::new);
   }

   @Nullable
   @Override
   public ProfiledDuration e() {
      if (!WorldLoadFinishedEvent.TYPE.isEnabled()) {
         return null;
      } else {
         WorldLoadFinishedEvent var0 = new WorldLoadFinishedEvent();
         var0.begin();
         return var0::commit;
      }
   }

   @Nullable
   @Override
   public ProfiledDuration a(ChunkCoordIntPair var0, ResourceKey<World> var1, String var2) {
      if (!ChunkGenerationEvent.TYPE.isEnabled()) {
         return null;
      } else {
         ChunkGenerationEvent var3 = new ChunkGenerationEvent(var0, var1, var2);
         var3.begin();
         return var3::commit;
      }
   }
}
