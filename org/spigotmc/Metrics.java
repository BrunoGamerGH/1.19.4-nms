package org.spigotmc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Metrics {
   private static final int REVISION = 6;
   private static final String BASE_URL = "https://mcstats.spigotmc.org";
   private static final String REPORT_URL = "/report/%s";
   private static final String CUSTOM_DATA_SEPARATOR = "~~";
   private static final int PING_INTERVAL = 10;
   private final Set<Metrics.Graph> graphs = Collections.synchronizedSet(new HashSet<>());
   private final Metrics.Graph defaultGraph = new Metrics.Graph("Default");
   private final YamlConfiguration configuration;
   private final File configurationFile;
   private final String guid;
   private final boolean debug;
   private final Object optOutLock = new Object();
   private volatile Timer task = null;

   public Metrics() throws IOException {
      this.configurationFile = this.getConfigFile();
      this.configuration = YamlConfiguration.loadConfiguration(this.configurationFile);
      this.configuration.addDefault("opt-out", false);
      this.configuration.addDefault("guid", UUID.randomUUID().toString());
      this.configuration.addDefault("debug", false);
      if (this.configuration.get("guid", null) == null) {
         this.configuration.options().header("http://mcstats.org").copyDefaults(true);
         this.configuration.save(this.configurationFile);
      }

      this.guid = this.configuration.getString("guid");
      this.debug = this.configuration.getBoolean("debug", false);
   }

   public Metrics.Graph createGraph(String name) {
      if (name == null) {
         throw new IllegalArgumentException("Graph name cannot be null");
      } else {
         Metrics.Graph graph = new Metrics.Graph(name);
         this.graphs.add(graph);
         return graph;
      }
   }

   public void addGraph(Metrics.Graph graph) {
      if (graph == null) {
         throw new IllegalArgumentException("Graph cannot be null");
      } else {
         this.graphs.add(graph);
      }
   }

   public void addCustomData(Metrics.Plotter plotter) {
      if (plotter == null) {
         throw new IllegalArgumentException("Plotter cannot be null");
      } else {
         this.defaultGraph.addPlotter(plotter);
         this.graphs.add(this.defaultGraph);
      }
   }

   public boolean start() {
      synchronized(this.optOutLock) {
         if (this.isOptOut()) {
            return false;
         } else if (this.task != null) {
            return true;
         } else {
            this.task = new Timer("Spigot Metrics Thread", true);
            this.task.scheduleAtFixedRate(new TimerTask() {
               private boolean firstPost = true;

               @Override
               public void run() {
                  try {
                     synchronized(Metrics.this.optOutLock) {
                        if (Metrics.this.isOptOut() && Metrics.this.task != null) {
                           Metrics.this.task.cancel();
                           Metrics.this.task = null;

                           for(Metrics.Graph graph : Metrics.this.graphs) {
                              graph.onOptOut();
                           }
                        }
                     }

                     Metrics.this.postPlugin(!this.firstPost);
                     this.firstPost = false;
                  } catch (IOException var5) {
                     if (Metrics.this.debug) {
                        Bukkit.getLogger().log(Level.INFO, "[Metrics] " + var5.getMessage());
                     }
                  }
               }
            }, 0L, TimeUnit.MINUTES.toMillis(10L));
            return true;
         }
      }
   }

   public boolean isOptOut() {
      synchronized(this.optOutLock) {
         try {
            this.configuration.load(this.getConfigFile());
         } catch (IOException var3) {
            if (this.debug) {
               Bukkit.getLogger().log(Level.INFO, "[Metrics] " + var3.getMessage());
            }

            return true;
         } catch (InvalidConfigurationException var4) {
            if (this.debug) {
               Bukkit.getLogger().log(Level.INFO, "[Metrics] " + var4.getMessage());
            }

            return true;
         }

         return this.configuration.getBoolean("opt-out", false);
      }
   }

   public void enable() throws IOException {
      synchronized(this.optOutLock) {
         if (this.isOptOut()) {
            this.configuration.set("opt-out", false);
            this.configuration.save(this.configurationFile);
         }

         if (this.task == null) {
            this.start();
         }
      }
   }

   public void disable() throws IOException {
      synchronized(this.optOutLock) {
         if (!this.isOptOut()) {
            this.configuration.set("opt-out", true);
            this.configuration.save(this.configurationFile);
         }

         if (this.task != null) {
            this.task.cancel();
            this.task = null;
         }
      }
   }

   public File getConfigFile() {
      return new File(new File((File)MinecraftServer.getServer().options.valueOf("plugins"), "PluginMetrics"), "config.yml");
   }

   private void postPlugin(boolean isPing) throws IOException {
      String pluginName = "Spigot";
      boolean onlineMode = Bukkit.getServer().getOnlineMode();
      String pluginVersion = Metrics.class.getPackage().getImplementationVersion() != null ? Metrics.class.getPackage().getImplementationVersion() : "unknown";
      String serverVersion = Bukkit.getVersion();
      int playersOnline = Bukkit.getServer().getOnlinePlayers().size();
      StringBuilder data = new StringBuilder();
      data.append(encode("guid")).append('=').append(encode(this.guid));
      encodeDataPair(data, "version", pluginVersion);
      encodeDataPair(data, "server", serverVersion);
      encodeDataPair(data, "players", Integer.toString(playersOnline));
      encodeDataPair(data, "revision", String.valueOf(6));
      String osname = System.getProperty("os.name");
      String osarch = System.getProperty("os.arch");
      String osversion = System.getProperty("os.version");
      String java_version = System.getProperty("java.version");
      int coreCount = Runtime.getRuntime().availableProcessors();
      if (osarch.equals("amd64")) {
         osarch = "x86_64";
      }

      encodeDataPair(data, "osname", osname);
      encodeDataPair(data, "osarch", osarch);
      encodeDataPair(data, "osversion", osversion);
      encodeDataPair(data, "cores", Integer.toString(coreCount));
      encodeDataPair(data, "online-mode", Boolean.toString(onlineMode));
      encodeDataPair(data, "java_version", java_version);
      if (isPing) {
         encodeDataPair(data, "ping", "true");
      }

      synchronized(this.graphs) {
         for(Metrics.Graph graph : this.graphs) {
            for(Metrics.Plotter plotter : graph.getPlotters()) {
               String key = String.format("C%s%s%s%s", "~~", graph.getName(), "~~", plotter.getColumnName());
               String value = Integer.toString(plotter.getValue());
               encodeDataPair(data, key, value);
            }
         }
      }

      URL url = new URL("https://mcstats.spigotmc.org" + String.format("/report/%s", encode(pluginName)));
      URLConnection connection;
      if (this.isMineshafterPresent()) {
         connection = url.openConnection(Proxy.NO_PROXY);
      } else {
         connection = url.openConnection();
      }

      connection.setDoOutput(true);
      OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
      writer.write(data.toString());
      writer.flush();
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String response = reader.readLine();
      writer.close();
      reader.close();
      if (response != null && !response.startsWith("ERR")) {
         if (response.contains("OK This is your first update this hour")) {
            synchronized(this.graphs) {
               for(Metrics.Graph graph : this.graphs) {
                  for(Metrics.Plotter plotter : graph.getPlotters()) {
                     plotter.reset();
                  }
               }
            }
         }
      } else {
         throw new IOException(response);
      }
   }

   private boolean isMineshafterPresent() {
      try {
         Class.forName("mineshafter.MineServer");
         return true;
      } catch (Exception var2) {
         return false;
      }
   }

   private static void encodeDataPair(StringBuilder buffer, String key, String value) throws UnsupportedEncodingException {
      buffer.append('&').append(encode(key)).append('=').append(encode(value));
   }

   private static String encode(String text) throws UnsupportedEncodingException {
      return URLEncoder.encode(text, "UTF-8");
   }

   public static class Graph {
      private final String name;
      private final Set<Metrics.Plotter> plotters = new LinkedHashSet<>();

      private Graph(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      public void addPlotter(Metrics.Plotter plotter) {
         this.plotters.add(plotter);
      }

      public void removePlotter(Metrics.Plotter plotter) {
         this.plotters.remove(plotter);
      }

      public Set<Metrics.Plotter> getPlotters() {
         return Collections.unmodifiableSet(this.plotters);
      }

      @Override
      public int hashCode() {
         return this.name.hashCode();
      }

      @Override
      public boolean equals(Object object) {
         if (!(object instanceof Metrics.Graph)) {
            return false;
         } else {
            Metrics.Graph graph = (Metrics.Graph)object;
            return graph.name.equals(this.name);
         }
      }

      protected void onOptOut() {
      }
   }

   public abstract static class Plotter {
      private final String name;

      public Plotter() {
         this("Default");
      }

      public Plotter(String name) {
         this.name = name;
      }

      public abstract int getValue();

      public String getColumnName() {
         return this.name;
      }

      public void reset() {
      }

      @Override
      public int hashCode() {
         return this.getColumnName().hashCode();
      }

      @Override
      public boolean equals(Object object) {
         if (!(object instanceof Metrics.Plotter)) {
            return false;
         } else {
            Metrics.Plotter plotter = (Metrics.Plotter)object;
            return plotter.name.equals(this.name) && plotter.getValue() == this.getValue();
         }
      }
   }
}
