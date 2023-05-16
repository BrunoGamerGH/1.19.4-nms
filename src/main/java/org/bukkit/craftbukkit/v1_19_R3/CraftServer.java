package org.bukkit.craftbukkit.v1_19_R3;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import jline.console.ConsoleReader;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.HolderSet;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerCommand;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.bossevents.BossBattleCustom;
import net.minecraft.server.bossevents.BossBattleCustomData;
import net.minecraft.server.commands.CommandReload;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.dedicated.DedicatedServerSettings;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.players.GameProfileBanEntry;
import net.minecraft.server.players.JsonListEntry;
import net.minecraft.server.players.OpListEntry;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.WhiteListEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.datafix.DataConverterRegistry;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.entity.npc.MobSpawnerCat;
import net.minecraft.world.entity.npc.MobSpawnerTrader;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerWorkbench;
import net.minecraft.world.inventory.Containers;
import net.minecraft.world.inventory.InventoryCraftResult;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemWorldMap;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.crafting.RecipeCrafting;
import net.minecraft.world.item.crafting.RecipeRepair;
import net.minecraft.world.item.crafting.Recipes;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.MobSpawner;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.levelgen.MobSpawnerPatrol;
import net.minecraft.world.level.levelgen.MobSpawnerPhantom;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.saveddata.maps.MapIcon;
import net.minecraft.world.level.saveddata.maps.WorldMap;
import net.minecraft.world.level.storage.Convertable;
import net.minecraft.world.level.storage.SaveData;
import net.minecraft.world.level.storage.WorldDataServer;
import net.minecraft.world.level.storage.WorldNBTStorage;
import net.minecraft.world.level.storage.loot.LootTableRegistry;
import net.minecraft.world.phys.Vec3D;
import org.apache.commons.lang.Validate;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Fluid;
import org.bukkit.GameMode;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Registry;
import org.bukkit.Server;
import org.bukkit.StructureType;
import org.bukkit.Tag;
import org.bukkit.UnsafeValues;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.BanList.Type;
import org.bukkit.Server.Spigot;
import org.bukkit.Warning.WarningState;
import org.bukkit.World.Environment;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.conversations.Conversable;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.boss.CraftBossBar;
import org.bukkit.craftbukkit.v1_19_R3.boss.CraftKeyedBossbar;
import org.bukkit.craftbukkit.v1_19_R3.command.BukkitCommandWrapper;
import org.bukkit.craftbukkit.v1_19_R3.command.CraftCommandMap;
import org.bukkit.craftbukkit.v1_19_R3.command.VanillaCommandWrapper;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.generator.CraftWorldInfo;
import org.bukkit.craftbukkit.v1_19_R3.generator.OldCraftChunkData;
import org.bukkit.craftbukkit.v1_19_R3.help.SimpleHelpMap;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftBlastingRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftCampfireRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftFurnaceRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftMerchantCustom;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftShapedRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftShapelessRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftSmithingRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftSmokingRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftStonecuttingRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.RecipeIterator;
import org.bukkit.craftbukkit.v1_19_R3.inventory.util.CraftInventoryCreator;
import org.bukkit.craftbukkit.v1_19_R3.map.CraftMapColorCache;
import org.bukkit.craftbukkit.v1_19_R3.map.CraftMapView;
import org.bukkit.craftbukkit.v1_19_R3.metadata.EntityMetadataStore;
import org.bukkit.craftbukkit.v1_19_R3.metadata.PlayerMetadataStore;
import org.bukkit.craftbukkit.v1_19_R3.metadata.WorldMetadataStore;
import org.bukkit.craftbukkit.v1_19_R3.potion.CraftPotionBrewer;
import org.bukkit.craftbukkit.v1_19_R3.profile.CraftPlayerProfile;
import org.bukkit.craftbukkit.v1_19_R3.scheduler.CraftScheduler;
import org.bukkit.craftbukkit.v1_19_R3.scoreboard.CraftCriteria;
import org.bukkit.craftbukkit.v1_19_R3.scoreboard.CraftScoreboardManager;
import org.bukkit.craftbukkit.v1_19_R3.structure.CraftStructureManager;
import org.bukkit.craftbukkit.v1_19_R3.tag.CraftBlockTag;
import org.bukkit.craftbukkit.v1_19_R3.tag.CraftEntityTag;
import org.bukkit.craftbukkit.v1_19_R3.tag.CraftFluidTag;
import org.bukkit.craftbukkit.v1_19_R3.tag.CraftItemTag;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftIconCache;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftSpawnCategory;
import org.bukkit.craftbukkit.v1_19_R3.util.DatFileFilter;
import org.bukkit.craftbukkit.v1_19_R3.util.Versioning;
import org.bukkit.craftbukkit.v1_19_R3.util.permissions.CraftDefaultPermissions;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.server.BroadcastMessageEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.event.server.ServerLoadEvent.LoadType;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.ComplexRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.SmithingTransformRecipe;
import org.bukkit.inventory.SmithingTrimRecipe;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.loot.LootTable;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapView.Scale;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.SimpleServicesManager;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scheduler.BukkitWorker;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.structure.StructureManager;
import org.bukkit.util.StringUtil;
import org.bukkit.util.permissions.DefaultPermissions;
import org.spigotmc.AsyncCatcher;
import org.spigotmc.RestartCommand;
import org.spigotmc.SpigotConfig;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.MarkedYAMLException;

public final class CraftServer implements Server {
   private final String serverName = "CraftBukkit";
   private final String serverVersion;
   private final String bukkitVersion = Versioning.getBukkitVersion();
   private final Logger logger = Logger.getLogger("Minecraft");
   private final ServicesManager servicesManager = new SimpleServicesManager();
   private final CraftScheduler scheduler = new CraftScheduler();
   private final CraftCommandMap commandMap = new CraftCommandMap(this);
   private final SimpleHelpMap helpMap = new SimpleHelpMap(this);
   private final StandardMessenger messenger = new StandardMessenger();
   private final SimplePluginManager pluginManager = new SimplePluginManager(this, this.commandMap);
   private final StructureManager structureManager;
   protected final DedicatedServer console;
   protected final DedicatedPlayerList playerList;
   private final Map<String, org.bukkit.World> worlds = new LinkedHashMap<>();
   private final Map<Class<?>, Registry<?>> registries = new HashMap<>();
   private YamlConfiguration configuration;
   private YamlConfiguration commandsConfiguration;
   private final Yaml yaml = new Yaml(new SafeConstructor(new LoaderOptions()));
   private final Map<UUID, OfflinePlayer> offlinePlayers = new MapMaker().weakValues().makeMap();
   private final EntityMetadataStore entityMetadata = new EntityMetadataStore();
   private final PlayerMetadataStore playerMetadata = new PlayerMetadataStore();
   private final WorldMetadataStore worldMetadata = new WorldMetadataStore();
   private final Object2IntOpenHashMap<SpawnCategory> spawnCategoryLimit = new Object2IntOpenHashMap();
   private File container;
   private WarningState warningState = WarningState.DEFAULT;
   public String minimumAPI;
   public CraftScoreboardManager scoreboardManager;
   public boolean playerCommandState;
   private boolean printSaveWarning;
   private CraftIconCache icon;
   private boolean overrideAllCommandBlockCommands = false;
   public boolean ignoreVanillaPermissions = false;
   private final List<CraftPlayer> playerView;
   public int reloadCount;
   private final Spigot spigot = new Spigot() {
      public YamlConfiguration getConfig() {
         return SpigotConfig.config;
      }

      public void restart() {
         RestartCommand.restart();
      }

      public void broadcast(BaseComponent component) {
         for(Player player : CraftServer.this.getOnlinePlayers()) {
            player.spigot().sendMessage(component);
         }
      }

      public void broadcast(BaseComponent... components) {
         for(Player player : CraftServer.this.getOnlinePlayers()) {
            player.spigot().sendMessage(components);
         }
      }
   };

   static {
      ConfigurationSerialization.registerClass(CraftOfflinePlayer.class);
      ConfigurationSerialization.registerClass(CraftPlayerProfile.class);
      CraftItemFactory.instance();
   }

   public CraftServer(DedicatedServer console, PlayerList playerList) {
      this.console = console;
      this.playerList = (DedicatedPlayerList)playerList;
      this.playerView = Collections.unmodifiableList(Lists.transform(playerList.k, new Function<EntityPlayer, CraftPlayer>() {
         public CraftPlayer apply(EntityPlayer player) {
            return player.getBukkitEntity();
         }
      }));
      this.serverVersion = CraftServer.class.getPackage().getImplementationVersion();
      this.structureManager = new CraftStructureManager(console.aV());
      Bukkit.setServer(this);
      Enchantments.n.getClass();
      Enchantment.stopAcceptingRegistrations();
      Potion.setPotionBrewer(new CraftPotionBrewer());
      MobEffects.o.getClass();
      PotionEffectType.stopAcceptingRegistrations();
      if (!Main.useConsole) {
         this.getLogger().info("Console input is disabled due to --noconsole command argument");
      }

      this.configuration = YamlConfiguration.loadConfiguration(this.getConfigFile());
      this.configuration.options().copyDefaults(true);
      this.configuration
         .setDefaults(
            YamlConfiguration.loadConfiguration(
               new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("configurations/bukkit.yml"), Charsets.UTF_8)
            )
         );
      ConfigurationSection legacyAlias = null;
      if (!this.configuration.isString("aliases")) {
         legacyAlias = this.configuration.getConfigurationSection("aliases");
         this.configuration.set("aliases", "now-in-commands.yml");
      }

      this.saveConfig();
      if (this.getCommandsConfigFile().isFile()) {
         legacyAlias = null;
      }

      this.commandsConfiguration = YamlConfiguration.loadConfiguration(this.getCommandsConfigFile());
      this.commandsConfiguration.options().copyDefaults(true);
      this.commandsConfiguration
         .setDefaults(
            YamlConfiguration.loadConfiguration(
               new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("configurations/commands.yml"), Charsets.UTF_8)
            )
         );
      this.saveCommandsConfig();
      if (legacyAlias != null) {
         ConfigurationSection aliases = this.commandsConfiguration.createSection("aliases");

         for(String key : legacyAlias.getKeys(false)) {
            ArrayList<String> commands = new ArrayList<>();
            if (legacyAlias.isList(key)) {
               for(String command : legacyAlias.getStringList(key)) {
                  commands.add(command + " $1-");
               }
            } else {
               commands.add(legacyAlias.getString(key) + " $1-");
            }

            aliases.set(key, commands);
         }
      }

      this.saveCommandsConfig();
      this.overrideAllCommandBlockCommands = this.commandsConfiguration.getStringList("command-block-overrides").contains("*");
      this.ignoreVanillaPermissions = this.commandsConfiguration.getBoolean("ignore-vanilla-permissions");
      this.pluginManager.useTimings(this.configuration.getBoolean("settings.plugin-profiling"));
      this.overrideSpawnLimits();
      console.autosavePeriod = this.configuration.getInt("ticks-per.autosave");
      this.warningState = WarningState.value(this.configuration.getString("settings.deprecated-verbose"));
      TicketType.PLUGIN.k = (long)this.configuration.getInt("chunk-gc.period-in-ticks");
      this.minimumAPI = this.configuration.getString("settings.minimum-api");
      this.loadIcon();
      if (this.configuration.getBoolean("settings.use-map-color-cache")) {
         MapPalette.setMapColorCache(new CraftMapColorCache(this.logger));
      }
   }

   public boolean getCommandBlockOverride(String command) {
      return this.overrideAllCommandBlockCommands || this.commandsConfiguration.getStringList("command-block-overrides").contains(command);
   }

   private File getConfigFile() {
      return (File)this.console.options.valueOf("bukkit-settings");
   }

   private File getCommandsConfigFile() {
      return (File)this.console.options.valueOf("commands-settings");
   }

   private void overrideSpawnLimits() {
      SpawnCategory[] var4;
      for(SpawnCategory spawnCategory : var4 = SpawnCategory.values()) {
         if (CraftSpawnCategory.isValidForLimits(spawnCategory)) {
            this.spawnCategoryLimit.put(spawnCategory, this.configuration.getInt(CraftSpawnCategory.getConfigNameSpawnLimit(spawnCategory)));
         }
      }
   }

   private void saveConfig() {
      try {
         this.configuration.save(this.getConfigFile());
      } catch (IOException var2) {
         Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, "Could not save " + this.getConfigFile(), (Throwable)var2);
      }
   }

   private void saveCommandsConfig() {
      try {
         this.commandsConfiguration.save(this.getCommandsConfigFile());
      } catch (IOException var2) {
         Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, "Could not save " + this.getCommandsConfigFile(), (Throwable)var2);
      }
   }

   public void loadPlugins() {
      this.pluginManager.registerInterface(JavaPluginLoader.class);
      File pluginFolder = (File)this.console.options.valueOf("plugins");
      if (pluginFolder.exists()) {
         Plugin[] plugins = this.pluginManager.loadPlugins(pluginFolder);

         for(Plugin plugin : plugins) {
            try {
               String message = String.format("Loading %s", plugin.getDescription().getFullName());
               plugin.getLogger().info(message);
               plugin.onLoad();
            } catch (Throwable var8) {
               Logger.getLogger(CraftServer.class.getName())
                  .log(Level.SEVERE, var8.getMessage() + " initializing " + plugin.getDescription().getFullName() + " (Is it up to date?)", var8);
            }
         }
      } else {
         pluginFolder.mkdir();
      }
   }

   public void enablePlugins(PluginLoadOrder type) {
      if (type == PluginLoadOrder.STARTUP) {
         this.helpMap.clear();
         this.helpMap.initializeGeneralTopics();
      }

      Plugin[] plugins = this.pluginManager.getPlugins();

      for(Plugin plugin : plugins) {
         if (!plugin.isEnabled() && plugin.getDescription().getLoad() == type) {
            this.enablePlugin(plugin);
         }
      }

      if (type == PluginLoadOrder.POSTWORLD) {
         this.setVanillaCommands(true);
         this.commandMap.setFallbackCommands();
         this.setVanillaCommands(false);
         this.commandMap.registerServerAliases();
         DefaultPermissions.registerCorePermissions();
         CraftDefaultPermissions.registerCorePermissions();
         this.loadCustomPermissions();
         this.helpMap.initializeCommands();
         this.syncCommands();
      }
   }

   public void disablePlugins() {
      this.pluginManager.disablePlugins();
   }

   private void setVanillaCommands(boolean first) {
      CommandDispatcher dispatcher = this.console.vanillaCommandDispatcher;

      for(CommandNode<CommandListenerWrapper> cmd : dispatcher.a().getRoot().getChildren()) {
         VanillaCommandWrapper wrapper = new VanillaCommandWrapper(dispatcher, cmd);
         if (SpigotConfig.replaceCommands.contains(wrapper.getName())) {
            if (first) {
               this.commandMap.register("minecraft", wrapper);
            }
         } else if (!first) {
            this.commandMap.register("minecraft", wrapper);
         }
      }
   }

   public void syncCommands() {
      CommandDispatcher dispatcher = this.console.au.b().d = new CommandDispatcher();

      for(Entry<String, Command> entry : this.commandMap.getKnownCommands().entrySet()) {
         String label = entry.getKey();
         Command command = (Command)entry.getValue();
         if (command instanceof VanillaCommandWrapper) {
            LiteralCommandNode<CommandListenerWrapper> node = (LiteralCommandNode)((VanillaCommandWrapper)command).vanillaCommand;
            if (!node.getLiteral().equals(label)) {
               LiteralCommandNode<CommandListenerWrapper> clone = new LiteralCommandNode(
                  label, node.getCommand(), node.getRequirement(), node.getRedirect(), node.getRedirectModifier(), node.isFork()
               );

               for(CommandNode<CommandListenerWrapper> child : node.getChildren()) {
                  clone.addChild(child);
               }

               node = clone;
            }

            dispatcher.a().getRoot().addChild(node);
         } else {
            new BukkitCommandWrapper(this, (Command)entry.getValue()).register(dispatcher.a(), label);
         }
      }

      for(EntityPlayer player : this.getHandle().k) {
         dispatcher.a(player);
      }
   }

   private void enablePlugin(Plugin plugin) {
      try {
         for(Permission perm : plugin.getDescription().getPermissions()) {
            try {
               this.pluginManager.addPermission(perm, false);
            } catch (IllegalArgumentException var6) {
               this.getLogger()
                  .log(
                     Level.WARNING,
                     "Plugin " + plugin.getDescription().getFullName() + " tried to register permission '" + perm.getName() + "' but it's already registered",
                     (Throwable)var6
                  );
            }
         }

         this.pluginManager.dirtyPermissibles();
         this.pluginManager.enablePlugin(plugin);
      } catch (Throwable var7) {
         Logger.getLogger(CraftServer.class.getName())
            .log(Level.SEVERE, var7.getMessage() + " loading " + plugin.getDescription().getFullName() + " (Is it up to date?)", var7);
      }
   }

   public String getName() {
      return "CraftBukkit";
   }

   public String getVersion() {
      return this.serverVersion + " (MC: " + this.console.G() + ")";
   }

   public String getBukkitVersion() {
      return this.bukkitVersion;
   }

   public List<CraftPlayer> getOnlinePlayers() {
      return this.playerView;
   }

   @Deprecated
   public Player getPlayer(String name) {
      Validate.notNull(name, "Name cannot be null");
      Player found = this.getPlayerExact(name);
      if (found != null) {
         return found;
      } else {
         String lowerName = name.toLowerCase(Locale.ENGLISH);
         int delta = Integer.MAX_VALUE;

         for(Player player : this.getOnlinePlayers()) {
            if (player.getName().toLowerCase(Locale.ENGLISH).startsWith(lowerName)) {
               int curDelta = Math.abs(player.getName().length() - lowerName.length());
               if (curDelta < delta) {
                  found = player;
                  delta = curDelta;
               }

               if (curDelta == 0) {
                  break;
               }
            }
         }

         return found;
      }
   }

   @Deprecated
   public Player getPlayerExact(String name) {
      Validate.notNull(name, "Name cannot be null");
      EntityPlayer player = this.playerList.a(name);
      return player != null ? player.getBukkitEntity() : null;
   }

   public Player getPlayer(UUID id) {
      EntityPlayer player = this.playerList.a(id);
      return player != null ? player.getBukkitEntity() : null;
   }

   public int broadcastMessage(String message) {
      return this.broadcast(message, "bukkit.broadcast.user");
   }

   @Deprecated
   public List<Player> matchPlayer(String partialName) {
      Validate.notNull(partialName, "PartialName cannot be null");
      List<Player> matchedPlayers = new ArrayList();

      for(Player iterPlayer : this.getOnlinePlayers()) {
         String iterPlayerName = iterPlayer.getName();
         if (partialName.equalsIgnoreCase(iterPlayerName)) {
            matchedPlayers.clear();
            matchedPlayers.add(iterPlayer);
            break;
         }

         if (iterPlayerName.toLowerCase(Locale.ENGLISH).contains(partialName.toLowerCase(Locale.ENGLISH))) {
            matchedPlayers.add(iterPlayer);
         }
      }

      return matchedPlayers;
   }

   public int getMaxPlayers() {
      return this.playerList.n();
   }

   public int getPort() {
      return this.getServer().M();
   }

   public int getViewDistance() {
      return this.getProperties().F;
   }

   public int getSimulationDistance() {
      return this.getProperties().G;
   }

   public String getIp() {
      return this.getServer().u();
   }

   public String getWorldType() {
      return this.getProperties().Y.getProperty("level-type");
   }

   public boolean getGenerateStructures() {
      return this.getServer().aW().A().c();
   }

   public int getMaxWorldSize() {
      return this.getProperties().L;
   }

   public boolean getAllowEnd() {
      return this.configuration.getBoolean("settings.allow-end");
   }

   public boolean getAllowNether() {
      return this.getServer().B();
   }

   public boolean getWarnOnOverload() {
      return this.configuration.getBoolean("settings.warn-on-overload");
   }

   public boolean getQueryPlugins() {
      return this.configuration.getBoolean("settings.query-plugins");
   }

   public String getResourcePack() {
      return this.getServer().S().map(MinecraftServer.ServerResourcePackInfo::a).orElse("");
   }

   public String getResourcePackHash() {
      return this.getServer().S().map(MinecraftServer.ServerResourcePackInfo::b).orElse("").toUpperCase(Locale.ROOT);
   }

   public String getResourcePackPrompt() {
      return this.getServer().S().map(MinecraftServer.ServerResourcePackInfo::d).map(CraftChatMessage::fromComponent).orElse("");
   }

   public boolean isResourcePackRequired() {
      return this.getServer().T();
   }

   public boolean hasWhitelist() {
      return this.getProperties().V.get();
   }

   private DedicatedServerProperties getProperties() {
      return this.console.a();
   }

   public String getUpdateFolder() {
      return this.configuration.getString("settings.update-folder", "update");
   }

   public File getUpdateFolderFile() {
      return new File((File)this.console.options.valueOf("plugins"), this.configuration.getString("settings.update-folder", "update"));
   }

   public long getConnectionThrottle() {
      return SpigotConfig.bungee ? -1L : (long)this.configuration.getInt("settings.connection-throttle");
   }

   @Deprecated
   public int getTicksPerAnimalSpawns() {
      return this.getTicksPerSpawns(SpawnCategory.ANIMAL);
   }

   @Deprecated
   public int getTicksPerMonsterSpawns() {
      return this.getTicksPerSpawns(SpawnCategory.MONSTER);
   }

   @Deprecated
   public int getTicksPerWaterSpawns() {
      return this.getTicksPerSpawns(SpawnCategory.WATER_ANIMAL);
   }

   @Deprecated
   public int getTicksPerWaterAmbientSpawns() {
      return this.getTicksPerSpawns(SpawnCategory.WATER_AMBIENT);
   }

   @Deprecated
   public int getTicksPerWaterUndergroundCreatureSpawns() {
      return this.getTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE);
   }

   @Deprecated
   public int getTicksPerAmbientSpawns() {
      return this.getTicksPerSpawns(SpawnCategory.AMBIENT);
   }

   public int getTicksPerSpawns(SpawnCategory spawnCategory) {
      Validate.notNull(spawnCategory, "SpawnCategory cannot be null");
      Validate.isTrue(CraftSpawnCategory.isValidForLimits(spawnCategory), "SpawnCategory." + spawnCategory + " are not supported.");
      return this.configuration.getInt(CraftSpawnCategory.getConfigNameTicksPerSpawn(spawnCategory));
   }

   public PluginManager getPluginManager() {
      return this.pluginManager;
   }

   public CraftScheduler getScheduler() {
      return this.scheduler;
   }

   public ServicesManager getServicesManager() {
      return this.servicesManager;
   }

   public List<org.bukkit.World> getWorlds() {
      return new ArrayList(this.worlds.values());
   }

   public DedicatedPlayerList getHandle() {
      return this.playerList;
   }

   public boolean dispatchServerCommand(CommandSender sender, ServerCommand serverCommand) {
      if (sender instanceof Conversable conversable && conversable.isConversing()) {
         conversable.acceptConversationInput(serverCommand.a);
         return true;
      }

      try {
         this.playerCommandState = true;
         return this.dispatchCommand(sender, serverCommand.a);
      } catch (Exception var8) {
         this.getLogger().log(Level.WARNING, "Unexpected exception while parsing console command \"" + serverCommand.a + 34, (Throwable)var8);
      } finally {
         this.playerCommandState = false;
      }

      return false;
   }

   public boolean dispatchCommand(CommandSender sender, String commandLine) {
      Validate.notNull(sender, "Sender cannot be null");
      Validate.notNull(commandLine, "CommandLine cannot be null");
      AsyncCatcher.catchOp("command dispatch");
      if (this.commandMap.dispatch(sender, commandLine)) {
         return true;
      } else {
         if (!SpigotConfig.unknownCommandMessage.isEmpty()) {
            sender.sendMessage(SpigotConfig.unknownCommandMessage);
         }

         return false;
      }
   }

   public void reload() {
      ++this.reloadCount;
      this.configuration = YamlConfiguration.loadConfiguration(this.getConfigFile());
      this.commandsConfiguration = YamlConfiguration.loadConfiguration(this.getCommandsConfigFile());
      this.console.u = new DedicatedServerSettings(this.console.options);
      DedicatedServerProperties config = this.console.u.a();
      this.console.f(config.f);
      this.console.g(config.g);
      this.console.d(config.h);
      this.overrideSpawnLimits();
      this.warningState = WarningState.value(this.configuration.getString("settings.deprecated-verbose"));
      TicketType.PLUGIN.k = (long)this.configuration.getInt("chunk-gc.period-in-ticks");
      this.minimumAPI = this.configuration.getString("settings.minimum-api");
      this.printSaveWarning = false;
      this.console.autosavePeriod = this.configuration.getInt("ticks-per.autosave");
      this.loadIcon();

      try {
         this.playerList.g().f();
      } catch (IOException var12) {
         this.logger.log(Level.WARNING, "Failed to load banned-ips.json, " + var12.getMessage());
      }

      try {
         this.playerList.f().f();
      } catch (IOException var11) {
         this.logger.log(Level.WARNING, "Failed to load banned-players.json, " + var11.getMessage());
      }

      SpigotConfig.init((File)this.console.options.valueOf("spigot-settings"));

      for(WorldServer world : this.console.F()) {
         world.J.a(config.k);
         world.b(config.w, config.d);

         SpawnCategory[] var7;
         for(SpawnCategory spawnCategory : var7 = SpawnCategory.values()) {
            if (CraftSpawnCategory.isValidForLimits(spawnCategory)) {
               long ticksPerCategorySpawn = (long)this.getTicksPerSpawns(spawnCategory);
               if (ticksPerCategorySpawn < 0L) {
                  world.ticksPerSpawnCategory.put(spawnCategory, CraftSpawnCategory.getDefaultTicksPerSpawn(spawnCategory));
               } else {
                  world.ticksPerSpawnCategory.put(spawnCategory, ticksPerCategorySpawn);
               }
            }
         }

         world.spigotConfig.init();
      }

      this.pluginManager.clearPlugins();
      this.commandMap.clearCommands();
      this.reloadData();
      SpigotConfig.registerCommands();
      this.overrideAllCommandBlockCommands = this.commandsConfiguration.getStringList("command-block-overrides").contains("*");
      this.ignoreVanillaPermissions = this.commandsConfiguration.getBoolean("ignore-vanilla-permissions");

      for(int pollCount = 0; pollCount < 50 && this.getScheduler().getActiveWorkers().size() > 0; ++pollCount) {
         try {
            Thread.sleep(50L);
         } catch (InterruptedException var10) {
         }
      }

      for(BukkitWorker worker : this.getScheduler().getActiveWorkers()) {
         Plugin plugin = worker.getOwner();
         this.getLogger()
            .log(
               Level.SEVERE,
               String.format(
                  "Nag author(s): '%s' of '%s' about the following: %s",
                  plugin.getDescription().getAuthors(),
                  plugin.getDescription().getFullName(),
                  "This plugin is not properly shutting down its async tasks when it is being reloaded.  This may cause conflicts with the newly loaded version of the plugin"
               )
            );
      }

      this.loadPlugins();
      this.enablePlugins(PluginLoadOrder.STARTUP);
      this.enablePlugins(PluginLoadOrder.POSTWORLD);
      this.getPluginManager().callEvent(new ServerLoadEvent(LoadType.RELOAD));
   }

   public void reloadData() {
      CommandReload.reload(this.console);
   }

   private void loadIcon() {
      this.icon = new CraftIconCache(null);

      try {
         File file = new File(new File("."), "server-icon.png");
         if (file.isFile()) {
            this.icon = loadServerIcon0(file);
         }
      } catch (Exception var2) {
         this.getLogger().log(Level.WARNING, "Couldn't load server icon", (Throwable)var2);
      }
   }

   private void loadCustomPermissions() {
      File file = new File(this.configuration.getString("settings.permissions-file"));

      FileInputStream stream;
      try {
         stream = new FileInputStream(file);
      } catch (FileNotFoundException var28) {
         try {
            file.createNewFile();
         } finally {
            ;
         }

         return;
      }

      Map<String, Map<String, Object>> perms;
      label164: {
         try {
            perms = (Map)this.yaml.load(stream);
            break label164;
         } catch (MarkedYAMLException var29) {
            this.getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML: " + var29.toString());
            return;
         } catch (Throwable var30) {
            this.getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML.", var30);
         } finally {
            try {
               stream.close();
            } catch (IOException var25) {
            }
         }

         return;
      }

      if (perms == null) {
         this.getLogger().log(Level.INFO, "Server permissions file " + file + " is empty, ignoring it");
      } else {
         for(Permission perm : Permission.loadPermissions(perms, "Permission node '%s' in " + file + " is invalid", Permission.DEFAULT_PERMISSION)) {
            try {
               this.pluginManager.addPermission(perm);
            } catch (IllegalArgumentException var27) {
               this.getLogger().log(Level.SEVERE, "Permission in " + file + " was already defined", (Throwable)var27);
            }
         }
      }
   }

   @Override
   public String toString() {
      return "CraftServer{serverName=CraftBukkit,serverVersion=" + this.serverVersion + ",minecraftVersion=" + this.console.G() + 125;
   }

   public org.bukkit.World createWorld(String name, Environment environment) {
      return WorldCreator.name(name).environment(environment).createWorld();
   }

   public org.bukkit.World createWorld(String name, Environment environment, long seed) {
      return WorldCreator.name(name).environment(environment).seed(seed).createWorld();
   }

   public org.bukkit.World createWorld(String name, Environment environment, ChunkGenerator generator) {
      return WorldCreator.name(name).environment(environment).generator(generator).createWorld();
   }

   public org.bukkit.World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {
      return WorldCreator.name(name).environment(environment).seed(seed).generator(generator).createWorld();
   }

   public org.bukkit.World createWorld(WorldCreator creator) {
      Preconditions.checkState(this.console.F().iterator().hasNext(), "Cannot create additional worlds on STARTUP");
      Validate.notNull(creator, "Creator may not be null");
      String name = creator.name();
      ChunkGenerator generator = creator.generator();
      BiomeProvider biomeProvider = creator.biomeProvider();
      File folder = new File(this.getWorldContainer(), name);
      org.bukkit.World world = this.getWorld(name);
      if (world != null) {
         return world;
      } else if (folder.exists() && !folder.isDirectory()) {
         throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
      } else {
         if (generator == null) {
            generator = this.getGenerator(name);
         }

         if (biomeProvider == null) {
            biomeProvider = this.getBiomeProvider(name);
         }
         ResourceKey actualDimension = switch(creator.environment()) {
            case NORMAL -> WorldDimension.b;
            case NETHER -> WorldDimension.c;
            case THE_END -> WorldDimension.d;
            default -> throw new IllegalArgumentException("Illegal dimension");
         };

         Convertable.ConversionSession worldSession;
         try {
            worldSession = Convertable.a(this.getWorldContainer().toPath()).createAccess(name, actualDimension);
         } catch (IOException var23) {
            throw new RuntimeException(var23);
         }

         boolean hardcore = creator.hardcore();
         WorldLoader.a worldloader_a = this.console.worldLoader;
         IRegistry<WorldDimension> iregistry = worldloader_a.d().d(Registries.aG);
         DynamicOps<NBTBase> dynamicops = RegistryOps.a(DynamicOpsNBT.a, worldloader_a.c());
         Pair<SaveData, WorldDimensions.b> pair = worldSession.a(dynamicops, worldloader_a.b(), iregistry, worldloader_a.c().d());
         WorldDataServer worlddata;
         if (pair != null) {
            worlddata = (WorldDataServer)pair.getFirst();
            iregistry = ((WorldDimensions.b)pair.getSecond()).c();
         } else {
            WorldOptions worldoptions = new WorldOptions(creator.seed(), creator.generateStructures(), false);
            DedicatedServerProperties.WorldDimensionData properties = new DedicatedServerProperties.WorldDimensionData(
               ChatDeserializer.a(creator.generatorSettings().isEmpty() ? "{}" : creator.generatorSettings()), creator.type().name().toLowerCase(Locale.ROOT)
            );
            WorldSettings worldsettings = new WorldSettings(
               name, EnumGamemode.a(this.getDefaultGameMode().getValue()), hardcore, EnumDifficulty.b, false, new GameRules(), worldloader_a.b()
            );
            WorldDimensions worlddimensions = properties.a(worldloader_a.c());
            WorldDimensions.b worlddimensions_b = worlddimensions.a(iregistry);
            Lifecycle lifecycle = worlddimensions_b.a().add(worldloader_a.c().d());
            worlddata = new WorldDataServer(worldsettings, worldoptions, worlddimensions_b.d(), lifecycle);
            iregistry = worlddimensions_b.c();
         }

         worlddata.customDimensions = iregistry;
         worlddata.checkName(name);
         worlddata.a(this.console.getServerModName(), this.console.K().a());
         if (this.console.options.has("forceUpgrade")) {
            net.minecraft.server.Main.a(worldSession, DataConverterRegistry.a(), this.console.options.has("eraseCache"), () -> true, iregistry);
         }

         long j = BiomeManager.a(creator.seed());
         List<MobSpawner> list = ImmutableList.of(
            new MobSpawnerPhantom(), new MobSpawnerPatrol(), new MobSpawnerCat(), new VillageSiege(), new MobSpawnerTrader(worlddata)
         );
         WorldDimension worlddimension = iregistry.a(actualDimension);
         WorldInfo worldInfo = new CraftWorldInfo(worlddata, worldSession, creator.environment(), worlddimension.a().a());
         if (biomeProvider == null && generator != null) {
            biomeProvider = generator.getDefaultBiomeProvider(worldInfo);
         }

         String levelName = this.getServer().a().m;
         ResourceKey<World> worldKey;
         if (name.equals(levelName + "_nether")) {
            worldKey = World.i;
         } else if (name.equals(levelName + "_the_end")) {
            worldKey = World.j;
         } else {
            worldKey = ResourceKey.a(Registries.aF, new MinecraftKey(name.toLowerCase(Locale.ENGLISH)));
         }

         WorldServer internal = new WorldServer(
            this.console,
            this.console.as,
            worldSession,
            worlddata,
            worldKey,
            worlddimension,
            this.getServer().H.create(11),
            worlddata.C(),
            j,
            (List<MobSpawner>)(creator.environment() == Environment.NORMAL ? list : ImmutableList.of()),
            true,
            creator.environment(),
            generator,
            biomeProvider
         );
         if (!this.worlds.containsKey(name.toLowerCase(Locale.ENGLISH))) {
            return null;
         } else {
            this.console.initWorld(internal, worlddata, worlddata, worlddata.A());
            internal.b(true, true);
            this.console.addLevel(internal);
            this.getServer().prepareLevels(internal.k().a.E, internal);
            internal.L.a();
            this.pluginManager.callEvent(new WorldLoadEvent(internal.getWorld()));
            return internal.getWorld();
         }
      }
   }

   public boolean unloadWorld(String name, boolean save) {
      return this.unloadWorld(this.getWorld(name), save);
   }

   public boolean unloadWorld(org.bukkit.World world, boolean save) {
      if (world == null) {
         return false;
      } else {
         WorldServer handle = ((CraftWorld)world).getHandle();
         if (this.console.a(handle.ab()) == null) {
            return false;
         } else if (handle.ab() == World.h) {
            return false;
         } else if (handle.v().size() > 0) {
            return false;
         } else {
            WorldUnloadEvent e = new WorldUnloadEvent(handle.getWorld());
            this.pluginManager.callEvent(e);
            if (e.isCancelled()) {
               return false;
            } else {
               try {
                  if (save) {
                     handle.a(null, true, true);
                  }

                  handle.k().close(save);
                  handle.L.close(save);
                  handle.convertable.close();
               } catch (Exception var6) {
                  this.getLogger().log(Level.SEVERE, null, var6);
               }

               this.worlds.remove(world.getName().toLowerCase(Locale.ENGLISH));
               this.console.removeLevel(handle);
               return true;
            }
         }
      }
   }

   public DedicatedServer getServer() {
      return this.console;
   }

   public org.bukkit.World getWorld(String name) {
      Validate.notNull(name, "Name cannot be null");
      return (org.bukkit.World)this.worlds.get(name.toLowerCase(Locale.ENGLISH));
   }

   public org.bukkit.World getWorld(UUID uid) {
      for(org.bukkit.World world : this.worlds.values()) {
         if (world.getUID().equals(uid)) {
            return world;
         }
      }

      return null;
   }

   public void addWorld(org.bukkit.World world) {
      if (this.getWorld(world.getUID()) != null) {
         System.out
            .println(
               "World "
                  + world.getName()
                  + " is a duplicate of another world and has been prevented from loading. Please delete the uid.dat file from "
                  + world.getName()
                  + "'s world directory if you want to be able to load the duplicate world."
            );
      } else {
         this.worlds.put(world.getName().toLowerCase(Locale.ENGLISH), world);
      }
   }

   public WorldBorder createWorldBorder() {
      return new CraftWorldBorder(new net.minecraft.world.level.border.WorldBorder());
   }

   public Logger getLogger() {
      return this.logger;
   }

   public ConsoleReader getReader() {
      return this.console.reader;
   }

   public PluginCommand getPluginCommand(String name) {
      Command command = this.commandMap.getCommand(name);
      return command instanceof PluginCommand ? (PluginCommand)command : null;
   }

   public void savePlayers() {
      this.checkSaveState();
      this.playerList.h();
   }

   public boolean addRecipe(Recipe recipe) {
      CraftRecipe toAdd;
      if (recipe instanceof CraftRecipe) {
         toAdd = (CraftRecipe)recipe;
      } else if (recipe instanceof ShapedRecipe) {
         toAdd = CraftShapedRecipe.fromBukkitRecipe((ShapedRecipe)recipe);
      } else if (recipe instanceof ShapelessRecipe) {
         toAdd = CraftShapelessRecipe.fromBukkitRecipe((ShapelessRecipe)recipe);
      } else if (recipe instanceof FurnaceRecipe) {
         toAdd = CraftFurnaceRecipe.fromBukkitRecipe((FurnaceRecipe)recipe);
      } else if (recipe instanceof BlastingRecipe) {
         toAdd = CraftBlastingRecipe.fromBukkitRecipe((BlastingRecipe)recipe);
      } else if (recipe instanceof CampfireRecipe) {
         toAdd = CraftCampfireRecipe.fromBukkitRecipe((CampfireRecipe)recipe);
      } else if (recipe instanceof SmokingRecipe) {
         toAdd = CraftSmokingRecipe.fromBukkitRecipe((SmokingRecipe)recipe);
      } else if (recipe instanceof StonecuttingRecipe) {
         toAdd = CraftStonecuttingRecipe.fromBukkitRecipe((StonecuttingRecipe)recipe);
      } else if (recipe instanceof SmithingRecipe) {
         toAdd = CraftSmithingRecipe.fromBukkitRecipe((SmithingRecipe)recipe);
      } else if (recipe instanceof SmithingTransformRecipe) {
         toAdd = CraftSmithingRecipe.fromBukkitRecipe((SmithingTransformRecipe)recipe);
      } else {
         if (!(recipe instanceof SmithingTrimRecipe)) {
            if (recipe instanceof ComplexRecipe) {
               throw new UnsupportedOperationException("Cannot add custom complex recipe");
            }

            return false;
         }

         toAdd = CraftSmithingRecipe.fromBukkitRecipe((SmithingTrimRecipe)recipe);
      }

      toAdd.addToCraftingManager();
      return true;
   }

   public List<Recipe> getRecipesFor(ItemStack result) {
      Validate.notNull(result, "Result cannot be null");
      List<Recipe> results = new ArrayList();
      Iterator<Recipe> iter = this.recipeIterator();

      while(iter.hasNext()) {
         Recipe recipe = (Recipe)iter.next();
         ItemStack stack = recipe.getResult();
         if (stack.getType() == result.getType() && (result.getDurability() == -1 || result.getDurability() == stack.getDurability())) {
            results.add(recipe);
         }
      }

      return results;
   }

   public Recipe getRecipe(NamespacedKey recipeKey) {
      Preconditions.checkArgument(recipeKey != null, "recipeKey == null");
      return (Recipe)this.getServer().aE().a(CraftNamespacedKey.toMinecraft(recipeKey)).map(IRecipe::toBukkitRecipe).orElse(null);
   }

   public Recipe getCraftingRecipe(ItemStack[] craftingMatrix, org.bukkit.World world) {
      Container container = new Container(null, -1) {
         @Override
         public InventoryView getBukkitView() {
            return null;
         }

         @Override
         public boolean a(EntityHuman entityhuman) {
            return false;
         }

         @Override
         public net.minecraft.world.item.ItemStack a(EntityHuman entityhuman, int i) {
            return net.minecraft.world.item.ItemStack.b;
         }
      };
      InventoryCrafting inventoryCrafting = new InventoryCrafting(container, 3, 3);
      return (Recipe)this.getNMSRecipe(craftingMatrix, inventoryCrafting, (CraftWorld)world).map(IRecipe::toBukkitRecipe).orElse(null);
   }

   public ItemStack craftItem(ItemStack[] craftingMatrix, org.bukkit.World world, Player player) {
      Preconditions.checkArgument(world != null, "world must not be null");
      Preconditions.checkArgument(player != null, "player must not be null");
      CraftWorld craftWorld = (CraftWorld)world;
      CraftPlayer craftPlayer = (CraftPlayer)player;
      ContainerWorkbench container = new ContainerWorkbench(-1, craftPlayer.getHandle().fJ());
      InventoryCrafting inventoryCrafting = container.r;
      InventoryCraftResult craftResult = container.s;
      Optional<RecipeCrafting> recipe = this.getNMSRecipe(craftingMatrix, inventoryCrafting, craftWorld);
      net.minecraft.world.item.ItemStack itemstack = net.minecraft.world.item.ItemStack.b;
      if (recipe.isPresent()) {
         RecipeCrafting recipeCrafting = recipe.get();
         if (craftResult.a(craftWorld.getHandle(), craftPlayer.getHandle(), recipeCrafting)) {
            itemstack = recipeCrafting.a(inventoryCrafting, craftWorld.getHandle().u_());
         }
      }

      net.minecraft.world.item.ItemStack result = CraftEventFactory.callPreCraftEvent(
         inventoryCrafting, craftResult, itemstack, container.getBukkitView(), recipe.orElse(null) instanceof RecipeRepair
      );

      for(int i = 0; i < craftingMatrix.length; ++i) {
         Item remaining = inventoryCrafting.getContents().get(i).c().s();
         craftingMatrix[i] = remaining != null ? CraftItemStack.asBukkitCopy(remaining.ad_()) : null;
      }

      return CraftItemStack.asBukkitCopy(result);
   }

   private Optional<RecipeCrafting> getNMSRecipe(ItemStack[] craftingMatrix, InventoryCrafting inventoryCrafting, CraftWorld world) {
      Preconditions.checkArgument(craftingMatrix != null, "craftingMatrix must not be null");
      Preconditions.checkArgument(craftingMatrix.length == 9, "craftingMatrix must be an array of length 9");
      Preconditions.checkArgument(world != null, "world must not be null");

      for(int i = 0; i < craftingMatrix.length; ++i) {
         inventoryCrafting.a(i, CraftItemStack.asNMSCopy(craftingMatrix[i]));
      }

      return this.getServer().aE().a(Recipes.a, inventoryCrafting, world.getHandle());
   }

   public Iterator<Recipe> recipeIterator() {
      return new RecipeIterator();
   }

   public void clearRecipes() {
      this.console.aE().clearRecipes();
   }

   public void resetRecipes() {
      this.reloadData();
   }

   public boolean removeRecipe(NamespacedKey recipeKey) {
      Preconditions.checkArgument(recipeKey != null, "recipeKey == null");
      MinecraftKey mcKey = CraftNamespacedKey.toMinecraft(recipeKey);
      return this.getServer().aE().removeRecipe(mcKey);
   }

   public Map<String, String[]> getCommandAliases() {
      ConfigurationSection section = this.commandsConfiguration.getConfigurationSection("aliases");
      Map<String, String[]> result = new LinkedHashMap<>();
      if (section != null) {
         for(String key : section.getKeys(false)) {
            List<String> commands;
            if (section.isList(key)) {
               commands = section.getStringList(key);
            } else {
               commands = ImmutableList.of(section.getString(key));
            }

            result.put(key, commands.toArray(new String[commands.size()]));
         }
      }

      return result;
   }

   public void removeBukkitSpawnRadius() {
      this.configuration.set("settings.spawn-radius", null);
      this.saveConfig();
   }

   public int getBukkitSpawnRadius() {
      return this.configuration.getInt("settings.spawn-radius", -1);
   }

   public String getShutdownMessage() {
      return this.configuration.getString("settings.shutdown-message");
   }

   public int getSpawnRadius() {
      return this.getServer().ah();
   }

   public void setSpawnRadius(int value) {
      this.configuration.set("settings.spawn-radius", value);
      this.saveConfig();
   }

   public boolean shouldSendChatPreviews() {
      return false;
   }

   public boolean isEnforcingSecureProfiles() {
      return this.getServer().aw();
   }

   public boolean getHideOnlinePlayers() {
      return this.console.aj();
   }

   public boolean getOnlineMode() {
      return this.console.U();
   }

   public boolean getAllowFlight() {
      return this.console.Z();
   }

   public boolean isHardcore() {
      return this.console.h();
   }

   public ChunkGenerator getGenerator(String world) {
      ConfigurationSection section = this.configuration.getConfigurationSection("worlds");
      ChunkGenerator result = null;
      if (section != null) {
         section = section.getConfigurationSection(world);
         if (section != null) {
            String name = section.getString("generator");
            if (name != null && !name.equals("")) {
               String[] split = name.split(":", 2);
               String id = split.length > 1 ? split[1] : null;
               Plugin plugin = this.pluginManager.getPlugin(split[0]);
               if (plugin == null) {
                  this.getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + split[0] + "' does not exist");
               } else if (!plugin.isEnabled()) {
                  this.getLogger()
                     .severe(
                        "Could not set generator for default world '"
                           + world
                           + "': Plugin '"
                           + plugin.getDescription().getFullName()
                           + "' is not enabled yet (is it load:STARTUP?)"
                     );
               } else {
                  try {
                     result = plugin.getDefaultWorldGenerator(world, id);
                     if (result == null) {
                        this.getLogger()
                           .severe(
                              "Could not set generator for default world '"
                                 + world
                                 + "': Plugin '"
                                 + plugin.getDescription().getFullName()
                                 + "' lacks a default world generator"
                           );
                     }
                  } catch (Throwable var9) {
                     plugin.getLogger()
                        .log(Level.SEVERE, "Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName(), var9);
                  }
               }
            }
         }
      }

      return result;
   }

   public BiomeProvider getBiomeProvider(String world) {
      ConfigurationSection section = this.configuration.getConfigurationSection("worlds");
      BiomeProvider result = null;
      if (section != null) {
         section = section.getConfigurationSection(world);
         if (section != null) {
            String name = section.getString("biome-provider");
            if (name != null && !name.equals("")) {
               String[] split = name.split(":", 2);
               String id = split.length > 1 ? split[1] : null;
               Plugin plugin = this.pluginManager.getPlugin(split[0]);
               if (plugin == null) {
                  this.getLogger().severe("Could not set biome provider for default world '" + world + "': Plugin '" + split[0] + "' does not exist");
               } else if (!plugin.isEnabled()) {
                  this.getLogger()
                     .severe(
                        "Could not set biome provider for default world '"
                           + world
                           + "': Plugin '"
                           + plugin.getDescription().getFullName()
                           + "' is not enabled yet (is it load:STARTUP?)"
                     );
               } else {
                  try {
                     result = plugin.getDefaultBiomeProvider(world, id);
                     if (result == null) {
                        this.getLogger()
                           .severe(
                              "Could not set biome provider for default world '"
                                 + world
                                 + "': Plugin '"
                                 + plugin.getDescription().getFullName()
                                 + "' lacks a default world biome provider"
                           );
                     }
                  } catch (Throwable var9) {
                     plugin.getLogger()
                        .log(
                           Level.SEVERE,
                           "Could not set biome provider for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName(),
                           var9
                        );
                  }
               }
            }
         }
      }

      return result;
   }

   @Deprecated
   public CraftMapView getMap(int id) {
      WorldMap worldmap = this.console.a(World.h).a("map_" + id);
      return worldmap == null ? null : worldmap.mapView;
   }

   public CraftMapView createMap(org.bukkit.World world) {
      Validate.notNull(world, "World cannot be null");
      World minecraftWorld = ((CraftWorld)world).getHandle();
      int newId = ItemWorldMap.a(minecraftWorld, minecraftWorld.n_().a(), minecraftWorld.n_().c(), 3, false, false, minecraftWorld.ab());
      return minecraftWorld.a(ItemWorldMap.a(newId)).mapView;
   }

   public ItemStack createExplorerMap(org.bukkit.World world, Location location, StructureType structureType) {
      return this.createExplorerMap(world, location, structureType, 100, true);
   }

   public ItemStack createExplorerMap(org.bukkit.World world, Location location, StructureType structureType, int radius, boolean findUnexplored) {
      Validate.notNull(world, "World cannot be null");
      Validate.notNull(structureType, "StructureType cannot be null");
      Validate.notNull(structureType.getMapIcon(), "Cannot create explorer maps for StructureType " + structureType.getName());
      WorldServer worldServer = ((CraftWorld)world).getHandle();
      Location structureLocation = world.locateNearestStructure(location, structureType, radius, findUnexplored);
      BlockPosition structurePosition = new BlockPosition(structureLocation.getBlockX(), structureLocation.getBlockY(), structureLocation.getBlockZ());
      net.minecraft.world.item.ItemStack stack = ItemWorldMap.a(worldServer, structurePosition.u(), structurePosition.w(), Scale.NORMAL.getValue(), true, true);
      ItemWorldMap.a(worldServer, stack);
      ItemWorldMap.a(stack, worldServer);
      WorldMap.a(stack, structurePosition, "+", MapIcon.Type.a(structureType.getMapIcon().getValue()));
      return CraftItemStack.asBukkitCopy(stack);
   }

   public void shutdown() {
      this.console.a(false);
   }

   public int broadcast(String message, String permission) {
      Set<CommandSender> recipients = new HashSet();

      for(Permissible permissible : this.getPluginManager().getPermissionSubscriptions(permission)) {
         if (permissible instanceof CommandSender && permissible.hasPermission(permission)) {
            recipients.add((CommandSender)permissible);
         }
      }

      BroadcastMessageEvent broadcastMessageEvent = new BroadcastMessageEvent(!Bukkit.isPrimaryThread(), message, recipients);
      this.getPluginManager().callEvent(broadcastMessageEvent);
      if (broadcastMessageEvent.isCancelled()) {
         return 0;
      } else {
         message = broadcastMessageEvent.getMessage();

         for(CommandSender recipient : recipients) {
            recipient.sendMessage(message);
         }

         return recipients.size();
      }
   }

   @Deprecated
   public OfflinePlayer getOfflinePlayer(String name) {
      Validate.notNull(name, "Name cannot be null");
      Validate.notEmpty(name, "Name cannot be empty");
      OfflinePlayer result = this.getPlayerExact(name);
      if (result == null) {
         GameProfile profile = null;
         if (this.getOnlineMode() || SpigotConfig.bungee) {
            profile = (GameProfile)this.console.ap().a(name).orElse(null);
         }

         if (profile == null) {
            result = this.getOfflinePlayer(new GameProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8)), name));
         } else {
            result = this.getOfflinePlayer(profile);
         }
      } else {
         this.offlinePlayers.remove(result.getUniqueId());
      }

      return result;
   }

   public OfflinePlayer getOfflinePlayer(UUID id) {
      Validate.notNull(id, "UUID cannot be null");
      OfflinePlayer result = this.getPlayer(id);
      if (result == null) {
         result = (OfflinePlayer)this.offlinePlayers.get(id);
         if (result == null) {
            result = new CraftOfflinePlayer(this, new GameProfile(id, null));
            this.offlinePlayers.put(id, result);
         }
      } else {
         this.offlinePlayers.remove(id);
      }

      return result;
   }

   public PlayerProfile createPlayerProfile(UUID uniqueId, String name) {
      return new CraftPlayerProfile(uniqueId, name);
   }

   public PlayerProfile createPlayerProfile(UUID uniqueId) {
      return new CraftPlayerProfile(uniqueId, null);
   }

   public PlayerProfile createPlayerProfile(String name) {
      return new CraftPlayerProfile(null, name);
   }

   public OfflinePlayer getOfflinePlayer(GameProfile profile) {
      OfflinePlayer player = new CraftOfflinePlayer(this, profile);
      this.offlinePlayers.put(profile.getId(), player);
      return player;
   }

   public Set<String> getIPBans() {
      return this.playerList.g().d().stream().map(JsonListEntry::g).collect(Collectors.toSet());
   }

   public void banIP(String address) {
      Validate.notNull(address, "Address cannot be null.");
      this.getBanList(Type.IP).addBan(address, null, null, null);
   }

   public void unbanIP(String address) {
      Validate.notNull(address, "Address cannot be null.");
      this.getBanList(Type.IP).pardon(address);
   }

   public Set<OfflinePlayer> getBannedPlayers() {
      Set<OfflinePlayer> result = new HashSet();

      for(GameProfileBanEntry entry : this.playerList.f().getValues()) {
         result.add(this.getOfflinePlayer(entry.g()));
      }

      return result;
   }

   public BanList getBanList(Type type) {
      Validate.notNull(type, "Type cannot be null");
      switch(type) {
         case NAME:
         default:
            return new CraftProfileBanList(this.playerList.f());
         case IP:
            return new CraftIpBanList(this.playerList.g());
      }
   }

   public void setWhitelist(boolean value) {
      this.playerList.a(value);
      this.console.i(value);
   }

   public boolean isWhitelistEnforced() {
      return this.console.aM();
   }

   public void setWhitelistEnforced(boolean value) {
      this.console.h(value);
   }

   public Set<OfflinePlayer> getWhitelistedPlayers() {
      Set<OfflinePlayer> result = new LinkedHashSet();

      for(WhiteListEntry entry : this.playerList.i().getValues()) {
         result.add(this.getOfflinePlayer(entry.g()));
      }

      return result;
   }

   public Set<OfflinePlayer> getOperators() {
      Set<OfflinePlayer> result = new HashSet();

      for(OpListEntry entry : this.playerList.k().getValues()) {
         result.add(this.getOfflinePlayer(entry.g()));
      }

      return result;
   }

   public void reloadWhitelist() {
      this.playerList.a();
   }

   public GameMode getDefaultGameMode() {
      return GameMode.getByValue(this.console.a(World.h).J.m().a());
   }

   public void setDefaultGameMode(GameMode mode) {
      Validate.notNull(mode, "Mode cannot be null");

      for(org.bukkit.World world : this.getWorlds()) {
         ((CraftWorld)world).getHandle().J.a(EnumGamemode.a(mode.getValue()));
      }
   }

   public ConsoleCommandSender getConsoleSender() {
      return this.console.console;
   }

   public EntityMetadataStore getEntityMetadata() {
      return this.entityMetadata;
   }

   public PlayerMetadataStore getPlayerMetadata() {
      return this.playerMetadata;
   }

   public WorldMetadataStore getWorldMetadata() {
      return this.worldMetadata;
   }

   public File getWorldContainer() {
      return this.getServer().h.a(World.h).getParent().toFile();
   }

   public OfflinePlayer[] getOfflinePlayers() {
      WorldNBTStorage storage = this.console.i;
      String[] files = storage.getPlayerDir().list(new DatFileFilter());
      Set<OfflinePlayer> players = new HashSet();

      for(String file : files) {
         try {
            players.add(this.getOfflinePlayer(UUID.fromString(file.substring(0, file.length() - 4))));
         } catch (IllegalArgumentException var9) {
         }
      }

      players.addAll(this.getOnlinePlayers());
      return players.toArray(new OfflinePlayer[players.size()]);
   }

   public Messenger getMessenger() {
      return this.messenger;
   }

   public void sendPluginMessage(Plugin source, String channel, byte[] message) {
      StandardMessenger.validatePluginMessage(this.getMessenger(), source, channel, message);

      for(Player player : this.getOnlinePlayers()) {
         player.sendPluginMessage(source, channel, message);
      }
   }

   public Set<String> getListeningPluginChannels() {
      Set<String> result = new HashSet<>();

      for(Player player : this.getOnlinePlayers()) {
         result.addAll(player.getListeningPluginChannels());
      }

      return result;
   }

   public Inventory createInventory(InventoryHolder owner, InventoryType type) {
      Validate.isTrue(type.isCreatable(), "Cannot open an inventory of type ", type);
      return CraftInventoryCreator.INSTANCE.createInventory(owner, type);
   }

   public Inventory createInventory(InventoryHolder owner, InventoryType type, String title) {
      Validate.isTrue(type.isCreatable(), "Cannot open an inventory of type ", type);
      return CraftInventoryCreator.INSTANCE.createInventory(owner, type, title);
   }

   public Inventory createInventory(InventoryHolder owner, int size) throws IllegalArgumentException {
      Validate.isTrue(9 <= size && size <= 54 && size % 9 == 0, "Size for custom inventory must be a multiple of 9 between 9 and 54 slots (got " + size + ")");
      return CraftInventoryCreator.INSTANCE.createInventory(owner, size);
   }

   public Inventory createInventory(InventoryHolder owner, int size, String title) throws IllegalArgumentException {
      Validate.isTrue(9 <= size && size <= 54 && size % 9 == 0, "Size for custom inventory must be a multiple of 9 between 9 and 54 slots (got " + size + ")");
      return CraftInventoryCreator.INSTANCE.createInventory(owner, size, title);
   }

   public Merchant createMerchant(String title) {
      return new CraftMerchantCustom(title == null ? InventoryType.MERCHANT.getDefaultTitle() : title);
   }

   public int getMaxChainedNeighborUpdates() {
      return this.getServer().bf();
   }

   public HelpMap getHelpMap() {
      return this.helpMap;
   }

   public SimpleCommandMap getCommandMap() {
      return this.commandMap;
   }

   @Deprecated
   public int getMonsterSpawnLimit() {
      return this.getSpawnLimit(SpawnCategory.MONSTER);
   }

   @Deprecated
   public int getAnimalSpawnLimit() {
      return this.getSpawnLimit(SpawnCategory.ANIMAL);
   }

   @Deprecated
   public int getWaterAnimalSpawnLimit() {
      return this.getSpawnLimit(SpawnCategory.WATER_ANIMAL);
   }

   @Deprecated
   public int getWaterAmbientSpawnLimit() {
      return this.getSpawnLimit(SpawnCategory.WATER_AMBIENT);
   }

   @Deprecated
   public int getWaterUndergroundCreatureSpawnLimit() {
      return this.getSpawnLimit(SpawnCategory.WATER_UNDERGROUND_CREATURE);
   }

   @Deprecated
   public int getAmbientSpawnLimit() {
      return this.getSpawnLimit(SpawnCategory.AMBIENT);
   }

   public int getSpawnLimit(SpawnCategory spawnCategory) {
      return this.spawnCategoryLimit.getOrDefault(spawnCategory, -1);
   }

   public boolean isPrimaryThread() {
      return Thread.currentThread().equals(this.console.ag) || this.console.hasStopped() || !AsyncCatcher.enabled;
   }

   public String getMotd() {
      return this.console.aa();
   }

   public WarningState getWarningState() {
      return this.warningState;
   }

   public List<String> tabComplete(CommandSender sender, String message, WorldServer world, Vec3D pos, boolean forceCommand) {
      if (!(sender instanceof Player)) {
         return ImmutableList.of();
      } else {
         Player player = (Player)sender;
         List<String> offers;
         if (!message.startsWith("/") && !forceCommand) {
            offers = this.tabCompleteChat(player, message);
         } else {
            offers = this.tabCompleteCommand(player, message, world, pos);
         }

         TabCompleteEvent tabEvent = new TabCompleteEvent(player, message, offers);
         this.getPluginManager().callEvent(tabEvent);
         return tabEvent.isCancelled() ? Collections.EMPTY_LIST : tabEvent.getCompletions();
      }
   }

   public List<String> tabCompleteCommand(Player player, String message, WorldServer world, Vec3D pos) {
      if ((SpigotConfig.tabComplete < 0 || message.length() <= SpigotConfig.tabComplete) && !message.contains(" ")) {
         return ImmutableList.of();
      } else {
         List<String> completions = null;

         try {
            if (message.startsWith("/")) {
               message = message.substring(1);
            }

            if (pos == null) {
               completions = this.getCommandMap().tabComplete(player, message);
            } else {
               completions = this.getCommandMap().tabComplete(player, message, new Location(world.getWorld(), pos.c, pos.d, pos.e));
            }
         } catch (CommandException var7) {
            player.sendMessage(ChatColor.RED + "An internal error occurred while attempting to tab-complete this command");
            this.getLogger().log(Level.SEVERE, "Exception when " + player.getName() + " attempted to tab complete " + message, var7);
         }

         return (List<String>)(completions == null ? ImmutableList.of() : completions);
      }
   }

   public List<String> tabCompleteChat(Player player, String message) {
      List<String> completions = new ArrayList<>();
      PlayerChatTabCompleteEvent event = new PlayerChatTabCompleteEvent(player, message, completions);
      String token = event.getLastToken();

      for(Player p : this.getOnlinePlayers()) {
         if (player.canSee(p) && StringUtil.startsWithIgnoreCase(p.getName(), token)) {
            completions.add(p.getName());
         }
      }

      this.pluginManager.callEvent(event);
      Iterator<?> it = completions.iterator();

      while(it.hasNext()) {
         Object current = it.next();
         if (!(current instanceof String)) {
            it.remove();
         }
      }

      Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
      return completions;
   }

   public CraftItemFactory getItemFactory() {
      return CraftItemFactory.instance();
   }

   public CraftScoreboardManager getScoreboardManager() {
      return this.scoreboardManager;
   }

   public Criteria getScoreboardCriteria(String name) {
      return CraftCriteria.getFromBukkit(name);
   }

   public void checkSaveState() {
      if (!this.playerCommandState && !this.printSaveWarning && this.console.autosavePeriod > 0) {
         this.printSaveWarning = true;
         this.getLogger()
            .log(
               Level.WARNING,
               "A manual (plugin-induced) save has been detected while server is configured to auto-save. This may affect performance.",
               this.warningState == WarningState.ON ? new Throwable() : null
            );
      }
   }

   public CraftIconCache getServerIcon() {
      return this.icon;
   }

   public CraftIconCache loadServerIcon(File file) throws Exception {
      Validate.notNull(file, "File cannot be null");
      if (!file.isFile()) {
         throw new IllegalArgumentException(file + " is not a file");
      } else {
         return loadServerIcon0(file);
      }
   }

   static CraftIconCache loadServerIcon0(File file) throws Exception {
      return loadServerIcon0(ImageIO.read(file));
   }

   public CraftIconCache loadServerIcon(BufferedImage image) throws Exception {
      Validate.notNull(image, "Image cannot be null");
      return loadServerIcon0(image);
   }

   static CraftIconCache loadServerIcon0(BufferedImage image) throws Exception {
      Validate.isTrue(image.getWidth() == 64, "Must be 64 pixels wide");
      Validate.isTrue(image.getHeight() == 64, "Must be 64 pixels high");
      ByteArrayOutputStream bytebuf = new ByteArrayOutputStream();
      ImageIO.write(image, "PNG", bytebuf);
      return new CraftIconCache(bytebuf.toByteArray());
   }

   public void setIdleTimeout(int threshold) {
      this.console.c(threshold);
   }

   public int getIdleTimeout() {
      return this.console.al();
   }

   public ChunkData createChunkData(org.bukkit.World world) {
      Validate.notNull(world, "World cannot be null");
      WorldServer handle = ((CraftWorld)world).getHandle();
      return new OldCraftChunkData(world.getMinHeight(), world.getMaxHeight(), handle.u_().d(Registries.an));
   }

   public BossBar createBossBar(String title, BarColor color, BarStyle style, BarFlag... flags) {
      return new CraftBossBar(title, color, style, flags);
   }

   public KeyedBossBar createBossBar(NamespacedKey key, String title, BarColor barColor, BarStyle barStyle, BarFlag... barFlags) {
      Preconditions.checkArgument(key != null, "key");
      BossBattleCustom bossBattleCustom = this.getServer().aL().a(CraftNamespacedKey.toMinecraft(key), CraftChatMessage.fromString(title, true)[0]);
      CraftKeyedBossbar craftKeyedBossbar = new CraftKeyedBossbar(bossBattleCustom);
      craftKeyedBossbar.setColor(barColor);
      craftKeyedBossbar.setStyle(barStyle);

      for(BarFlag flag : barFlags) {
         craftKeyedBossbar.addFlag(flag);
      }

      return craftKeyedBossbar;
   }

   public Iterator<KeyedBossBar> getBossBars() {
      return Iterators.unmodifiableIterator(Iterators.transform(this.getServer().aL().b().iterator(), new Function<BossBattleCustom, KeyedBossBar>() {
         public KeyedBossBar apply(BossBattleCustom bossBattleCustom) {
            return bossBattleCustom.getBukkitEntity();
         }
      }));
   }

   public KeyedBossBar getBossBar(NamespacedKey key) {
      Preconditions.checkArgument(key != null, "key");
      BossBattleCustom bossBattleCustom = this.getServer().aL().a(CraftNamespacedKey.toMinecraft(key));
      return bossBattleCustom == null ? null : bossBattleCustom.getBukkitEntity();
   }

   public boolean removeBossBar(NamespacedKey key) {
      Preconditions.checkArgument(key != null, "key");
      BossBattleCustomData bossBattleCustomData = this.getServer().aL();
      BossBattleCustom bossBattleCustom = bossBattleCustomData.a(CraftNamespacedKey.toMinecraft(key));
      if (bossBattleCustom != null) {
         bossBattleCustomData.a(bossBattleCustom);
         return true;
      } else {
         return false;
      }
   }

   public org.bukkit.entity.Entity getEntity(UUID uuid) {
      Validate.notNull(uuid, "UUID cannot be null");

      for(WorldServer world : this.getServer().F()) {
         Entity entity = world.a(uuid);
         if (entity != null) {
            return entity.getBukkitEntity();
         }
      }

      return null;
   }

   public Advancement getAdvancement(NamespacedKey key) {
      Preconditions.checkArgument(key != null, "key");
      net.minecraft.advancements.Advancement advancement = this.console.az().a(CraftNamespacedKey.toMinecraft(key));
      return advancement == null ? null : advancement.bukkit;
   }

   public Iterator<Advancement> advancementIterator() {
      return Iterators.unmodifiableIterator(
         Iterators.transform(this.console.az().a().iterator(), new Function<net.minecraft.advancements.Advancement, Advancement>() {
            public Advancement apply(net.minecraft.advancements.Advancement advancement) {
               return advancement.bukkit;
            }
         })
      );
   }

   public BlockData createBlockData(Material material) {
      Validate.isTrue(material != null, "Must provide material");
      return this.createBlockData(material, null);
   }

   public BlockData createBlockData(Material material, Consumer<BlockData> consumer) {
      BlockData data = this.createBlockData(material);
      if (consumer != null) {
         consumer.accept(data);
      }

      return data;
   }

   public BlockData createBlockData(String data) throws IllegalArgumentException {
      Validate.isTrue(data != null, "Must provide data");
      return this.createBlockData(null, data);
   }

   public BlockData createBlockData(Material material, String data) {
      Validate.isTrue(material != null || data != null, "Must provide one of material or data");
      return CraftBlockData.newData(material, data);
   }

   public <T extends Keyed> Tag<T> getTag(String registry, NamespacedKey tag, Class<T> clazz) {
      Validate.notNull(registry, "registry cannot be null");
      Validate.notNull(tag, "NamespacedKey cannot be null");
      Validate.notNull(clazz, "Class cannot be null");
      MinecraftKey key = CraftNamespacedKey.toMinecraft(tag);
      switch(registry.hashCode()) {
         case -1386164858:
            if (!registry.equals("blocks")) {
               throw new IllegalArgumentException();
            }

            Preconditions.checkArgument(clazz == Material.class, "Block namespace must have material type");
            TagKey<Block> blockTagKey = TagKey.a(Registries.e, key);
            if (BuiltInRegistries.f.b(blockTagKey).isPresent()) {
               return new CraftBlockTag(BuiltInRegistries.f, blockTagKey);
            }
            break;
         case -1271463959:
            if (!registry.equals("fluids")) {
               throw new IllegalArgumentException();
            }

            Preconditions.checkArgument(clazz == Fluid.class, "Fluid namespace must have fluid type");
            TagKey<FluidType> fluidTagKey = TagKey.a(Registries.v, key);
            if (BuiltInRegistries.d.b(fluidTagKey).isPresent()) {
               return new CraftFluidTag(BuiltInRegistries.d, fluidTagKey);
            }
            break;
         case 100526016:
            if (!registry.equals("items")) {
               throw new IllegalArgumentException();
            }

            Preconditions.checkArgument(clazz == Material.class, "Item namespace must have material type");
            TagKey<Item> itemTagKey = TagKey.a(Registries.C, key);
            if (BuiltInRegistries.i.b(itemTagKey).isPresent()) {
               return new CraftItemTag(BuiltInRegistries.i, itemTagKey);
            }
            break;
         case 1078323485:
            if (registry.equals("entity_types")) {
               Preconditions.checkArgument(clazz == EntityType.class, "Entity type namespace must have entity type");
               TagKey<EntityTypes<?>> entityTagKey = TagKey.a(Registries.r, key);
               if (BuiltInRegistries.h.b(entityTagKey).isPresent()) {
                  return new CraftEntityTag(BuiltInRegistries.h, entityTagKey);
               }
               break;
            }

            throw new IllegalArgumentException();
         default:
            throw new IllegalArgumentException();
      }

      return null;
   }

   public <T extends Keyed> Iterable<Tag<T>> getTags(String registry, Class<T> clazz) {
      Validate.notNull(registry, "registry cannot be null");
      Validate.notNull(clazz, "Class cannot be null");
      switch(registry.hashCode()) {
         case -1386164858:
            if (registry.equals("blocks")) {
               Preconditions.checkArgument(clazz == Material.class, "Block namespace must have material type");
               IRegistry<Block> blockTags = BuiltInRegistries.f;
               return blockTags.i().map(pair -> new CraftBlockTag(blockTags, (TagKey<Block>)pair.getFirst())).collect(ImmutableList.toImmutableList());
            }
            break;
         case -1271463959:
            if (registry.equals("fluids")) {
               Preconditions.checkArgument(clazz == Material.class, "Fluid namespace must have fluid type");
               IRegistry<FluidType> fluidTags = BuiltInRegistries.d;
               return fluidTags.i().map(pair -> new CraftFluidTag(fluidTags, (TagKey<FluidType>)pair.getFirst())).collect(ImmutableList.toImmutableList());
            }
            break;
         case 100526016:
            if (registry.equals("items")) {
               Preconditions.checkArgument(clazz == Material.class, "Item namespace must have material type");
               IRegistry<Item> itemTags = BuiltInRegistries.i;
               return itemTags.i().map(pair -> new CraftItemTag(itemTags, (TagKey<Item>)pair.getFirst())).collect(ImmutableList.toImmutableList());
            }
            break;
         case 1078323485:
            if (registry.equals("entity_types")) {
               Preconditions.checkArgument(clazz == EntityType.class, "Entity type namespace must have entity type");
               IRegistry<EntityTypes<?>> entityTags = BuiltInRegistries.h;
               return entityTags.i()
                  .map(pair -> new CraftEntityTag(entityTags, (TagKey<EntityTypes<?>>)pair.getFirst()))
                  .collect(ImmutableList.toImmutableList());
            }
      }

      throw new IllegalArgumentException();
   }

   public LootTable getLootTable(NamespacedKey key) {
      Validate.notNull(key, "NamespacedKey cannot be null");
      LootTableRegistry registry = this.getServer().aH();
      return new CraftLootTable(key, registry.a(CraftNamespacedKey.toMinecraft(key)));
   }

   public List<org.bukkit.entity.Entity> selectEntities(CommandSender sender, String selector) {
      Preconditions.checkArgument(selector != null, "Selector cannot be null");
      Preconditions.checkArgument(sender != null, "Sender cannot be null");
      ArgumentEntity arg = ArgumentEntity.b();

      List<? extends Entity> nms;
      try {
         StringReader reader = new StringReader(selector);
         nms = arg.parse(reader, true).b(VanillaCommandWrapper.getListener(sender));
         Preconditions.checkArgument(!reader.canRead(), "Spurious trailing data in selector: " + selector);
      } catch (CommandSyntaxException var6) {
         throw new IllegalArgumentException("Could not parse selector: " + selector, var6);
      }

      return new ArrayList(Lists.transform(nms, entity -> entity.getBukkitEntity()));
   }

   public StructureManager getStructureManager() {
      return this.structureManager;
   }

   public <T extends Keyed> Registry<T> getRegistry(Class<T> aClass) {
      return (Registry<T>)this.registries.computeIfAbsent(aClass, key -> CraftRegistry.createRegistry(aClass, this.console.aX()));
   }

   @Deprecated
   public UnsafeValues getUnsafe() {
      return CraftMagicNumbers.INSTANCE;
   }

   public Spigot spigot() {
      return this.spigot;
   }
}
