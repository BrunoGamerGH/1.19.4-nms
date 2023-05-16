package net.minecraft.server.dedicated;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.logging.Handler;
import javax.annotation.Nullable;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.SystemUtils;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.IMinecraftServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerCommand;
import net.minecraft.server.Services;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.WorldStem;
import net.minecraft.server.gui.ServerGUI;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.level.progress.WorldLoadListenerFactory;
import net.minecraft.server.network.ITextFilter;
import net.minecraft.server.network.TextFilter;
import net.minecraft.server.packs.repository.ResourcePackRepository;
import net.minecraft.server.players.NameReferencingFileConverter;
import net.minecraft.server.players.UserCache;
import net.minecraft.server.rcon.RemoteControlCommandListener;
import net.minecraft.server.rcon.thread.RemoteControlListener;
import net.minecraft.server.rcon.thread.RemoteStatusListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.monitoring.jmx.MinecraftServerBeans;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import net.minecraft.world.level.storage.Convertable;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.io.IoBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.v1_19_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_19_R3.command.CraftRemoteConsoleCommandSender;
import org.bukkit.craftbukkit.v1_19_R3.util.ForwardLogHandler;
import org.bukkit.craftbukkit.v1_19_R3.util.TerminalConsoleWriterThread;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoadOrder;
import org.slf4j.Logger;
import org.spigotmc.SpigotConfig;

public class DedicatedServer extends MinecraftServer implements IMinecraftServer {
   static final Logger n = LogUtils.getLogger();
   private static final int o = 5000;
   private static final int p = 2;
   private final List<ServerCommand> q = Collections.synchronizedList(Lists.newArrayList());
   @Nullable
   private RemoteStatusListener r;
   public final RemoteControlCommandListener s;
   @Nullable
   private RemoteControlListener t;
   public DedicatedServerSettings u;
   @Nullable
   private ServerGUI v;
   @Nullable
   private final TextFilter w;

   public DedicatedServer(
      OptionSet options,
      WorldLoader.a worldLoader,
      Thread thread,
      Convertable.ConversionSession convertable_conversionsession,
      ResourcePackRepository resourcepackrepository,
      WorldStem worldstem,
      DedicatedServerSettings dedicatedserversettings,
      DataFixer datafixer,
      Services services,
      WorldLoadListenerFactory worldloadlistenerfactory
   ) {
      super(
         options,
         worldLoader,
         thread,
         convertable_conversionsession,
         resourcepackrepository,
         worldstem,
         Proxy.NO_PROXY,
         datafixer,
         services,
         worldloadlistenerfactory
      );
      this.u = dedicatedserversettings;
      this.s = new RemoteControlCommandListener(this);
      this.w = TextFilter.a(dedicatedserversettings.a().R);
   }

   @Override
   public boolean e() throws IOException {
      Thread thread = new Thread("Server console handler") {
         @Override
         public void run() {
            if (Main.useConsole) {
               ConsoleReader bufferedreader = DedicatedServer.this.reader;

               try {
                  System.in.available();
               } catch (IOException var5) {
                  return;
               }

               try {
                  while(!DedicatedServer.this.ab() && DedicatedServer.this.v()) {
                     String s;
                     if (Main.useJline) {
                        s = bufferedreader.readLine(">", null);
                     } else {
                        s = bufferedreader.readLine();
                     }

                     if (s == null) {
                        try {
                           Thread.sleep(50L);
                        } catch (InterruptedException var4) {
                           Thread.currentThread().interrupt();
                        }
                     } else if (s.trim().length() > 0) {
                        DedicatedServer.this.a(s, DedicatedServer.this.aD());
                     }
                  }
               } catch (IOException var6) {
                  DedicatedServer.n.error("Exception handling console input", var6);
               }
            }
         }
      };
      java.util.logging.Logger global = java.util.logging.Logger.getLogger("");
      global.setUseParentHandlers(false);

      Handler[] i;
      for(Handler handler : i = global.getHandlers()) {
         global.removeHandler(handler);
      }

      global.addHandler(new ForwardLogHandler());
      org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger)LogManager.getRootLogger();

      for(Appender appender : logger.getAppenders().values()) {
         if (appender instanceof ConsoleAppender) {
            logger.removeAppender(appender);
         }
      }

      new TerminalConsoleWriterThread(System.out, this.reader).start();
      System.setOut(IoBuilder.forLogger(logger).setLevel(Level.INFO).buildPrintStream());
      System.setErr(IoBuilder.forLogger(logger).setLevel(Level.WARN).buildPrintStream());
      thread.setDaemon(true);
      thread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(n));
      thread.start();
      n.info("Starting minecraft server version {}", SharedConstants.b().c());
      if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
         n.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
      }

      n.info("Loading properties");
      DedicatedServerProperties dedicatedserverproperties = this.u.a();
      if (this.O()) {
         this.a_("127.0.0.1");
      } else {
         this.d(dedicatedserverproperties.a);
         this.e(dedicatedserverproperties.b);
         this.a_(dedicatedserverproperties.c);
      }

      this.a(new DedicatedPlayerList(this, this.aY(), this.i));
      SpigotConfig.init((File)this.options.valueOf("spigot-settings"));
      SpigotConfig.registerCommands();
      this.f(dedicatedserverproperties.f);
      this.g(dedicatedserverproperties.g);
      this.d(dedicatedserverproperties.h);
      super.c(dedicatedserverproperties.U.get());
      this.h(dedicatedserverproperties.j);
      n.info("Default game type: {}", dedicatedserverproperties.l);
      InetAddress inetaddress = null;
      if (!this.u().isEmpty()) {
         inetaddress = InetAddress.getByName(this.u());
      }

      if (this.M() < 0) {
         this.a(dedicatedserverproperties.n);
      }

      this.P();
      n.info("Starting Minecraft server on {}:{}", this.u().isEmpty() ? "*" : this.u(), this.M());

      try {
         this.ad().a(inetaddress, this.M());
      } catch (IOException var11) {
         n.warn("**** FAILED TO BIND TO PORT!");
         n.warn("The exception was: {}", var11.toString());
         n.warn("Perhaps a server is already running on that port?");
         return false;
      }

      this.server.loadPlugins();
      this.server.enablePlugins(PluginLoadOrder.STARTUP);
      if (!this.U()) {
         n.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
         n.warn("The server will make no attempt to authenticate usernames. Beware.");
         if (SpigotConfig.bungee) {
            n.warn(
               "Whilst this makes it possible to use BungeeCord, unless access to your server is properly restricted, it also opens up the ability for hackers to connect with any username they choose."
            );
            n.warn("Please see http://www.spigotmc.org/wiki/firewall-guide/ for further information.");
         } else {
            n.warn(
               "While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose."
            );
         }

         n.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
      }

      if (this.bk()) {
         this.ap().c();
      }

      if (!NameReferencingFileConverter.e(this)) {
         return false;
      } else {
         long i = SystemUtils.c();
         TileEntitySkull.a(this.l, this);
         UserCache.a(this.U());
         n.info("Preparing level \"{}\"", this.q());
         this.loadLevel(this.h.a());
         long j = SystemUtils.c() - i;
         String s = String.format(Locale.ROOT, "%.3fs", (double)j / 1.0E9);
         n.info("Done ({})! For help, type \"help\"", s);
         if (dedicatedserverproperties.o != null) {
            this.aK().a(GameRules.y).a(dedicatedserverproperties.o, this);
         }

         if (dedicatedserverproperties.p) {
            n.info("Starting GS4 status listener");
            this.r = RemoteStatusListener.a(this);
         }

         if (dedicatedserverproperties.r) {
            n.info("Starting remote control listener");
            this.t = RemoteControlListener.a(this);
            this.remoteConsole = new CraftRemoteConsoleCommandSender(this.s);
         }

         if (dedicatedserverproperties.N) {
            MinecraftServerBeans.a(this);
            n.info("JMX monitoring enabled");
         }

         return true;
      }
   }

   @Override
   public boolean W() {
      return this.a().d && super.W();
   }

   @Override
   public boolean Q() {
      return this.u.a().w && super.Q();
   }

   @Override
   public boolean X() {
      return this.u.a().e && super.X();
   }

   @Override
   public DedicatedServerProperties a() {
      return this.u.a();
   }

   @Override
   public void r() {
      this.a(this.a().k, true);
   }

   @Override
   public boolean h() {
      return this.a().u;
   }

   @Override
   public SystemReport a(SystemReport systemreport) {
      systemreport.a("Is Modded", () -> this.K().b());
      systemreport.a("Type", () -> "Dedicated Server (map_server.txt)");
      return systemreport;
   }

   @Override
   public void a(Path path) throws IOException {
      DedicatedServerProperties dedicatedserverproperties = this.a();

      try (BufferedWriter bufferedwriter = Files.newBufferedWriter(path)) {
         bufferedwriter.write(String.format(Locale.ROOT, "sync-chunk-writes=%s%n", dedicatedserverproperties.M));
         bufferedwriter.write(String.format(Locale.ROOT, "gamemode=%s%n", dedicatedserverproperties.l));
         bufferedwriter.write(String.format(Locale.ROOT, "spawn-monsters=%s%n", dedicatedserverproperties.w));
         bufferedwriter.write(String.format(Locale.ROOT, "entity-broadcast-range-percentage=%d%n", dedicatedserverproperties.Q));
         bufferedwriter.write(String.format(Locale.ROOT, "max-world-size=%d%n", dedicatedserverproperties.L));
         bufferedwriter.write(String.format(Locale.ROOT, "spawn-npcs=%s%n", dedicatedserverproperties.e));
         bufferedwriter.write(String.format(Locale.ROOT, "view-distance=%d%n", dedicatedserverproperties.F));
         bufferedwriter.write(String.format(Locale.ROOT, "simulation-distance=%d%n", dedicatedserverproperties.G));
         bufferedwriter.write(String.format(Locale.ROOT, "spawn-animals=%s%n", dedicatedserverproperties.d));
         bufferedwriter.write(String.format(Locale.ROOT, "generate-structures=%s%n", dedicatedserverproperties.X.c()));
         bufferedwriter.write(String.format(Locale.ROOT, "use-native=%s%n", dedicatedserverproperties.x));
         bufferedwriter.write(String.format(Locale.ROOT, "rate-limit=%d%n", dedicatedserverproperties.E));
      }
   }

   @Override
   public void g() {
      if (this.w != null) {
         this.w.close();
      }

      if (this.v != null) {
         this.v.b();
      }

      if (this.t != null) {
         this.t.b();
      }

      if (this.r != null) {
         this.r.b();
      }

      System.exit(0);
   }

   @Override
   public void b(BooleanSupplier booleansupplier) {
      super.b(booleansupplier);
      this.bh();
   }

   @Override
   public boolean B() {
      return this.a().v;
   }

   public void a(String s, CommandListenerWrapper commandlistenerwrapper) {
      this.q.add(new ServerCommand(s, commandlistenerwrapper));
   }

   public void bh() {
      SpigotTimings.serverCommandTimer.startTiming();

      while(!this.q.isEmpty()) {
         ServerCommand servercommand = this.q.remove(0);
         ServerCommandEvent event = new ServerCommandEvent(this.console, servercommand.a);
         this.server.getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            servercommand = new ServerCommand(event.getCommand(), servercommand.b);
            this.server.dispatchServerCommand(this.console, servercommand);
         }
      }

      SpigotTimings.serverCommandTimer.stopTiming();
   }

   @Override
   public boolean l() {
      return true;
   }

   @Override
   public int m() {
      return this.a().E;
   }

   @Override
   public boolean n() {
      return this.a().x;
   }

   public DedicatedPlayerList bi() {
      return (DedicatedPlayerList)super.ac();
   }

   @Override
   public boolean p() {
      return true;
   }

   @Override
   public String b() {
      return this.u();
   }

   @Override
   public int d() {
      return this.M();
   }

   @Override
   public String f() {
      return this.aa();
   }

   public void bj() {
      if (this.v == null) {
         this.v = ServerGUI.a(this);
      }
   }

   @Override
   public boolean af() {
      return this.v != null;
   }

   @Override
   public boolean o() {
      return this.a().y;
   }

   @Override
   public int ah() {
      return this.a().z;
   }

   @Override
   public boolean a(WorldServer worldserver, BlockPosition blockposition, EntityHuman entityhuman) {
      if (worldserver.ab() != World.h) {
         return false;
      } else if (this.bi().k().c()) {
         return false;
      } else if (this.bi().f(entityhuman.fI())) {
         return false;
      } else if (this.ah() <= 0) {
         return false;
      } else {
         BlockPosition blockposition1 = worldserver.Q();
         int i = MathHelper.a(blockposition.u() - blockposition1.u());
         int j = MathHelper.a(blockposition.w() - blockposition1.w());
         int k = Math.max(i, j);
         return k <= this.ah();
      }
   }

   @Override
   public boolean ai() {
      return this.a().O;
   }

   @Override
   public boolean aj() {
      return this.a().P;
   }

   @Override
   public int i() {
      return this.a().A;
   }

   @Override
   public int j() {
      return this.a().B;
   }

   @Override
   public void c(int i) {
      super.c(i);
      this.u.a(dedicatedserverproperties -> dedicatedserverproperties.U.a(this.aX(), i));
   }

   @Override
   public boolean k() {
      return this.a().J;
   }

   @Override
   public boolean M_() {
      return this.a().K;
   }

   @Override
   public int as() {
      return this.a().L;
   }

   @Override
   public int av() {
      return this.a().I;
   }

   @Override
   public boolean aw() {
      return this.a().W && this.a().a;
   }

   protected boolean bk() {
      boolean flag = false;

      for(int i = 0; !flag && i <= 2; ++i) {
         if (i > 0) {
            n.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
            this.bu();
         }

         flag = NameReferencingFileConverter.a((MinecraftServer)this);
      }

      boolean flag1 = false;

      for(int var7 = 0; !flag1 && var7 <= 2; ++var7) {
         if (var7 > 0) {
            n.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
            this.bu();
         }

         flag1 = NameReferencingFileConverter.b(this);
      }

      boolean flag2 = false;

      for(int var8 = 0; !flag2 && var8 <= 2; ++var8) {
         if (var8 > 0) {
            n.warn("Encountered a problem while converting the op list, retrying in a few seconds");
            this.bu();
         }

         flag2 = NameReferencingFileConverter.c(this);
      }

      boolean flag3 = false;

      for(int var9 = 0; !flag3 && var9 <= 2; ++var9) {
         if (var9 > 0) {
            n.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
            this.bu();
         }

         flag3 = NameReferencingFileConverter.d(this);
      }

      boolean flag4 = false;

      for(int var10 = 0; !flag4 && var10 <= 2; ++var10) {
         if (var10 > 0) {
            n.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
            this.bu();
         }

         flag4 = NameReferencingFileConverter.a(this);
      }

      return flag || flag1 || flag2 || flag3 || flag4;
   }

   private void bu() {
      try {
         Thread.sleep(5000L);
      } catch (InterruptedException var2) {
      }
   }

   public long bl() {
      return this.a().C;
   }

   @Override
   public int bf() {
      return this.a().D;
   }

   @Override
   public String s() {
      StringBuilder result = new StringBuilder();
      Plugin[] plugins = this.server.getPluginManager().getPlugins();
      result.append(this.server.getName());
      result.append(" on Bukkit ");
      result.append(this.server.getBukkitVersion());
      if (plugins.length > 0 && this.server.getQueryPlugins()) {
         result.append(": ");

         for(int i = 0; i < plugins.length; ++i) {
            if (i > 0) {
               result.append("; ");
            }

            result.append(plugins[i].getDescription().getName());
            result.append(" ");
            result.append(plugins[i].getDescription().getVersion().replaceAll(";", ","));
         }
      }

      return result.toString();
   }

   @Override
   public String a(String s) {
      this.s.e();
      this.h(() -> {
         RemoteServerCommandEvent event = new RemoteServerCommandEvent(this.remoteConsole, s);
         this.server.getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            ServerCommand serverCommand = new ServerCommand(event.getCommand(), this.s.g());
            this.server.dispatchServerCommand(this.remoteConsole, serverCommand);
         }
      });
      return this.s.f();
   }

   public void i(boolean flag) {
      this.u.a(dedicatedserverproperties -> dedicatedserverproperties.V.a(this.aX(), flag));
   }

   @Override
   public void t() {
      super.t();
      SystemUtils.h();
      TileEntitySkull.c();
   }

   @Override
   public boolean a(GameProfile gameprofile) {
      return false;
   }

   @Override
   public int b(int i) {
      return this.a().Q * i / 100;
   }

   @Override
   public String q() {
      return this.h.a();
   }

   @Override
   public boolean aU() {
      return this.u.a().M;
   }

   @Override
   public ITextFilter a(EntityPlayer entityplayer) {
      return this.w != null ? this.w.a(entityplayer.fI()) : ITextFilter.a;
   }

   @Nullable
   @Override
   public EnumGamemode aZ() {
      return this.u.a().i ? this.m.m() : null;
   }

   @Override
   public Optional<MinecraftServer.ServerResourcePackInfo> S() {
      return this.u.a().S;
   }

   @Override
   public boolean isDebugging() {
      return this.a().debug;
   }

   @Override
   public CommandSender getBukkitSender(CommandListenerWrapper wrapper) {
      return this.console;
   }
}
