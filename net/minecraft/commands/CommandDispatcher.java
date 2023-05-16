package net.minecraft.commands;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.commands.synchronization.CompletionProviders;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.IRegistry;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.gametest.framework.GameTestHarnessTestCommand;
import net.minecraft.network.chat.ChatClickable;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.protocol.game.PacketPlayOutCommands;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.commands.CommandAdvancement;
import net.minecraft.server.commands.CommandAttribute;
import net.minecraft.server.commands.CommandBan;
import net.minecraft.server.commands.CommandBanIp;
import net.minecraft.server.commands.CommandBanList;
import net.minecraft.server.commands.CommandBossBar;
import net.minecraft.server.commands.CommandClear;
import net.minecraft.server.commands.CommandClone;
import net.minecraft.server.commands.CommandDatapack;
import net.minecraft.server.commands.CommandDebug;
import net.minecraft.server.commands.CommandDeop;
import net.minecraft.server.commands.CommandDifficulty;
import net.minecraft.server.commands.CommandEffect;
import net.minecraft.server.commands.CommandEnchant;
import net.minecraft.server.commands.CommandExecute;
import net.minecraft.server.commands.CommandFill;
import net.minecraft.server.commands.CommandForceload;
import net.minecraft.server.commands.CommandFunction;
import net.minecraft.server.commands.CommandGamemode;
import net.minecraft.server.commands.CommandGamemodeDefault;
import net.minecraft.server.commands.CommandGamerule;
import net.minecraft.server.commands.CommandGive;
import net.minecraft.server.commands.CommandHelp;
import net.minecraft.server.commands.CommandIdleTimeout;
import net.minecraft.server.commands.CommandKick;
import net.minecraft.server.commands.CommandKill;
import net.minecraft.server.commands.CommandList;
import net.minecraft.server.commands.CommandLocate;
import net.minecraft.server.commands.CommandLoot;
import net.minecraft.server.commands.CommandMe;
import net.minecraft.server.commands.CommandOp;
import net.minecraft.server.commands.CommandPardon;
import net.minecraft.server.commands.CommandPardonIP;
import net.minecraft.server.commands.CommandParticle;
import net.minecraft.server.commands.CommandPlaySound;
import net.minecraft.server.commands.CommandPublish;
import net.minecraft.server.commands.CommandRecipe;
import net.minecraft.server.commands.CommandReload;
import net.minecraft.server.commands.CommandSaveAll;
import net.minecraft.server.commands.CommandSaveOff;
import net.minecraft.server.commands.CommandSaveOn;
import net.minecraft.server.commands.CommandSay;
import net.minecraft.server.commands.CommandSchedule;
import net.minecraft.server.commands.CommandScoreboard;
import net.minecraft.server.commands.CommandSeed;
import net.minecraft.server.commands.CommandSetBlock;
import net.minecraft.server.commands.CommandSetWorldSpawn;
import net.minecraft.server.commands.CommandSpawnpoint;
import net.minecraft.server.commands.CommandSpectate;
import net.minecraft.server.commands.CommandSpreadPlayers;
import net.minecraft.server.commands.CommandStop;
import net.minecraft.server.commands.CommandStopSound;
import net.minecraft.server.commands.CommandSummon;
import net.minecraft.server.commands.CommandTag;
import net.minecraft.server.commands.CommandTeam;
import net.minecraft.server.commands.CommandTeamMsg;
import net.minecraft.server.commands.CommandTeleport;
import net.minecraft.server.commands.CommandTell;
import net.minecraft.server.commands.CommandTellRaw;
import net.minecraft.server.commands.CommandTime;
import net.minecraft.server.commands.CommandTitle;
import net.minecraft.server.commands.CommandTrigger;
import net.minecraft.server.commands.CommandWeather;
import net.minecraft.server.commands.CommandWhitelist;
import net.minecraft.server.commands.CommandWorldBorder;
import net.minecraft.server.commands.CommandXp;
import net.minecraft.server.commands.DamageCommand;
import net.minecraft.server.commands.FillBiomeCommand;
import net.minecraft.server.commands.ItemCommands;
import net.minecraft.server.commands.JfrCommand;
import net.minecraft.server.commands.PerfCommand;
import net.minecraft.server.commands.PlaceCommand;
import net.minecraft.server.commands.RideCommand;
import net.minecraft.server.commands.SpawnArmorTrimsCommand;
import net.minecraft.server.commands.data.CommandData;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.slf4j.Logger;
import org.spigotmc.SpigotConfig;

public class CommandDispatcher {
   private static final Logger f = LogUtils.getLogger();
   public static final int a = 0;
   public static final int b = 1;
   public static final int c = 2;
   public static final int d = 3;
   public static final int e = 4;
   private final com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> g = new com.mojang.brigadier.CommandDispatcher<>();

   public CommandDispatcher(CommandDispatcher.ServerType commanddispatcher_servertype, CommandBuildContext commandbuildcontext) {
      this();
      CommandAdvancement.a(this.g);
      CommandAttribute.a(this.g, commandbuildcontext);
      CommandExecute.a(this.g, commandbuildcontext);
      CommandBossBar.a(this.g);
      CommandClear.a(this.g, commandbuildcontext);
      CommandClone.a(this.g, commandbuildcontext);
      DamageCommand.a(this.g, commandbuildcontext);
      CommandData.a(this.g);
      CommandDatapack.a(this.g);
      CommandDebug.a(this.g);
      CommandGamemodeDefault.a(this.g);
      CommandDifficulty.a(this.g);
      CommandEffect.a(this.g, commandbuildcontext);
      CommandMe.a(this.g);
      CommandEnchant.a(this.g, commandbuildcontext);
      CommandXp.a(this.g);
      CommandFill.a(this.g, commandbuildcontext);
      FillBiomeCommand.a(this.g, commandbuildcontext);
      CommandForceload.a(this.g);
      CommandFunction.a(this.g);
      CommandGamemode.a(this.g);
      CommandGamerule.a(this.g);
      CommandGive.a(this.g, commandbuildcontext);
      CommandHelp.a(this.g);
      ItemCommands.a(this.g, commandbuildcontext);
      CommandKick.a(this.g);
      CommandKill.a(this.g);
      CommandList.a(this.g);
      CommandLocate.a(this.g, commandbuildcontext);
      CommandLoot.a(this.g, commandbuildcontext);
      CommandTell.a(this.g);
      CommandParticle.a(this.g, commandbuildcontext);
      PlaceCommand.a(this.g);
      CommandPlaySound.a(this.g);
      CommandReload.a(this.g);
      CommandRecipe.a(this.g);
      RideCommand.a(this.g);
      CommandSay.a(this.g);
      CommandSchedule.a(this.g);
      CommandScoreboard.a(this.g);
      CommandSeed.a(this.g, commanddispatcher_servertype != CommandDispatcher.ServerType.c);
      CommandSetBlock.a(this.g, commandbuildcontext);
      CommandSpawnpoint.a(this.g);
      CommandSetWorldSpawn.a(this.g);
      CommandSpectate.a(this.g);
      CommandSpreadPlayers.a(this.g);
      CommandStopSound.a(this.g);
      CommandSummon.a(this.g, commandbuildcontext);
      CommandTag.a(this.g);
      CommandTeam.a(this.g);
      CommandTeamMsg.a(this.g);
      CommandTeleport.a(this.g);
      CommandTellRaw.a(this.g);
      CommandTime.a(this.g);
      CommandTitle.a(this.g);
      CommandTrigger.a(this.g);
      CommandWeather.a(this.g);
      CommandWorldBorder.a(this.g);
      if (JvmProfiler.e.d()) {
         JfrCommand.a(this.g);
      }

      if (SharedConstants.aO) {
         GameTestHarnessTestCommand.a(this.g);
         SpawnArmorTrimsCommand.a(this.g);
      }

      if (commanddispatcher_servertype.e) {
         CommandBanIp.a(this.g);
         CommandBanList.a(this.g);
         CommandBan.a(this.g);
         CommandDeop.a(this.g);
         CommandOp.a(this.g);
         CommandPardon.a(this.g);
         CommandPardonIP.a(this.g);
         PerfCommand.a(this.g);
         CommandSaveAll.a(this.g);
         CommandSaveOff.a(this.g);
         CommandSaveOn.a(this.g);
         CommandIdleTimeout.a(this.g);
         CommandStop.a(this.g);
         CommandWhitelist.a(this.g);
      }

      if (commanddispatcher_servertype.d) {
         CommandPublish.a(this.g);
      }
   }

   public CommandDispatcher() {
      this.g.setConsumer((commandcontext, flag, i) -> ((CommandListenerWrapper)commandcontext.getSource()).a(commandcontext, flag, i));
   }

   public static <S> ParseResults<S> a(ParseResults<S> parseresults, UnaryOperator<S> unaryoperator) {
      CommandContextBuilder<S> commandcontextbuilder = parseresults.getContext();
      CommandContextBuilder<S> commandcontextbuilder1 = commandcontextbuilder.withSource(unaryoperator.apply((S)commandcontextbuilder.getSource()));
      return new ParseResults(commandcontextbuilder1, parseresults.getReader(), parseresults.getExceptions());
   }

   public int dispatchServerCommand(CommandListenerWrapper sender, String command) {
      Joiner joiner = Joiner.on(" ");
      if (command.startsWith("/")) {
         command = command.substring(1);
      }

      ServerCommandEvent event = new ServerCommandEvent(sender.getBukkitSender(), command);
      Bukkit.getPluginManager().callEvent(event);
      if (event.isCancelled()) {
         return 0;
      } else {
         command = event.getCommand();
         String[] args = command.split(" ");
         String cmd = args[0];
         if (cmd.startsWith("minecraft:")) {
            cmd = cmd.substring("minecraft:".length());
         }

         if (cmd.startsWith("bukkit:")) {
            cmd = cmd.substring("bukkit:".length());
         }

         if (!cmd.equalsIgnoreCase("stop")
            && !cmd.equalsIgnoreCase("kick")
            && !cmd.equalsIgnoreCase("op")
            && !cmd.equalsIgnoreCase("deop")
            && !cmd.equalsIgnoreCase("ban")
            && !cmd.equalsIgnoreCase("ban-ip")
            && !cmd.equalsIgnoreCase("pardon")
            && !cmd.equalsIgnoreCase("pardon-ip")
            && !cmd.equalsIgnoreCase("reload")) {
            if (sender.e().getCraftServer().getCommandBlockOverride(args[0])) {
               args[0] = "minecraft:" + args[0];
            }

            String newCommand = joiner.join(args);
            return this.performPrefixedCommand(sender, newCommand, newCommand);
         } else {
            return 0;
         }
      }
   }

   public int a(CommandListenerWrapper commandlistenerwrapper, String s) {
      return this.performPrefixedCommand(commandlistenerwrapper, s, s);
   }

   public int performPrefixedCommand(CommandListenerWrapper commandlistenerwrapper, String s, String label) {
      s = s.startsWith("/") ? s.substring(1) : s;
      return this.performCommand(this.g.parse(s, commandlistenerwrapper), s, label);
   }

   public int a(ParseResults<CommandListenerWrapper> parseresults, String s) {
      return this.performCommand(parseresults, s, s);
   }

   public int performCommand(ParseResults<CommandListenerWrapper> parseresults, String s, String label) {
      CommandListenerWrapper commandlistenerwrapper = (CommandListenerWrapper)parseresults.getContext().getSource();
      commandlistenerwrapper.l().aP().a(() -> "/" + s);

      byte var12;
      try {
         try {
            return this.g.execute(parseresults);
         } catch (CommandException var17) {
            commandlistenerwrapper.b(var17.a());
            return 0;
         } catch (CommandSyntaxException var18) {
            commandlistenerwrapper.b(ChatComponentUtils.a(var18.getRawMessage()));
            if (var18.getInput() != null && var18.getCursor() >= 0) {
               int j = Math.min(var18.getInput().length(), var18.getCursor());
               IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.h()
                  .a(EnumChatFormat.h)
                  .a(chatmodifier -> chatmodifier.a(new ChatClickable(ChatClickable.EnumClickAction.d, label)));
               if (j > 10) {
                  ichatmutablecomponent.b(CommonComponents.p);
               }

               ichatmutablecomponent.f(var18.getInput().substring(Math.max(0, j - 10), j));
               if (j < var18.getInput().length()) {
                  IChatMutableComponent ichatmutablecomponent1 = IChatBaseComponent.b(var18.getInput().substring(j)).a(EnumChatFormat.m, EnumChatFormat.t);
                  ichatmutablecomponent.b(ichatmutablecomponent1);
               }

               ichatmutablecomponent.b(IChatBaseComponent.c("command.context.here").a(EnumChatFormat.m, EnumChatFormat.u));
               commandlistenerwrapper.b(ichatmutablecomponent);
            }
         } catch (Exception var19) {
            IChatMutableComponent ichatmutablecomponent2 = IChatBaseComponent.b(var19.getMessage() == null ? var19.getClass().getName() : var19.getMessage());
            if (f.isDebugEnabled()) {
               f.error("Command exception: /{}", s, var19);
               StackTraceElement[] astacktraceelement = var19.getStackTrace();

               for(int k = 0; k < Math.min(astacktraceelement.length, 3); ++k) {
                  ichatmutablecomponent2.f("\n\n")
                     .f(astacktraceelement[k].getMethodName())
                     .f("\n ")
                     .f(astacktraceelement[k].getFileName())
                     .f(":")
                     .f(String.valueOf(astacktraceelement[k].getLineNumber()));
               }
            }

            commandlistenerwrapper.b(
               IChatBaseComponent.c("command.failed")
                  .a(chatmodifier -> chatmodifier.a(new ChatHoverable(ChatHoverable.EnumHoverAction.a, ichatmutablecomponent2)))
            );
            if (SharedConstants.aO) {
               commandlistenerwrapper.b(IChatBaseComponent.b(SystemUtils.c(var19)));
               f.error("'/{}' threw an exception", s, var19);
            }

            return 0;
         }

         byte b1 = 0;
         var12 = b1;
      } finally {
         commandlistenerwrapper.l().aP().c();
      }

      return var12;
   }

   public void a(EntityPlayer entityplayer) {
      if (SpigotConfig.tabComplete >= 0) {
         Map<CommandNode<CommandListenerWrapper>, CommandNode<ICompletionProvider>> map = Maps.newIdentityHashMap();
         RootCommandNode vanillaRoot = new RootCommandNode();
         RootCommandNode<CommandListenerWrapper> vanilla = entityplayer.c.vanillaCommandDispatcher.a().getRoot();
         map.put(vanilla, vanillaRoot);
         this.a(vanilla, vanillaRoot, entityplayer.cZ(), map);
         RootCommandNode<ICompletionProvider> rootcommandnode = new RootCommandNode();
         map.put(this.g.getRoot(), rootcommandnode);
         this.a(this.g.getRoot(), rootcommandnode, entityplayer.cZ(), map);
         Collection<String> bukkit = new LinkedHashSet<>();

         for(CommandNode node : rootcommandnode.getChildren()) {
            bukkit.add(node.getName());
         }

         PlayerCommandSendEvent event = new PlayerCommandSendEvent(entityplayer.getBukkitEntity(), new LinkedHashSet<>(bukkit));
         event.getPlayer().getServer().getPluginManager().callEvent(event);

         for(String orig : bukkit) {
            if (!event.getCommands().contains(orig)) {
               rootcommandnode.removeCommand(orig);
            }
         }

         entityplayer.b.a(new PacketPlayOutCommands(rootcommandnode));
      }
   }

   private void a(
      CommandNode<CommandListenerWrapper> commandnode,
      CommandNode<ICompletionProvider> commandnode1,
      CommandListenerWrapper commandlistenerwrapper,
      Map<CommandNode<CommandListenerWrapper>, CommandNode<ICompletionProvider>> map
   ) {
      for(CommandNode<CommandListenerWrapper> commandnode2 : commandnode.getChildren()) {
         if ((SpigotConfig.sendNamespaced || !commandnode2.getName().contains(":")) && commandnode2.canUse(commandlistenerwrapper)) {
            ArgumentBuilder argumentbuilder = commandnode2.createBuilder();
            argumentbuilder.requires(icompletionprovider -> true);
            if (argumentbuilder.getCommand() != null) {
               argumentbuilder.executes(commandcontext -> 0);
            }

            if (argumentbuilder instanceof RequiredArgumentBuilder requiredargumentbuilder && requiredargumentbuilder.getSuggestionsProvider() != null) {
               requiredargumentbuilder.suggests(CompletionProviders.b(requiredargumentbuilder.getSuggestionsProvider()));
            }

            if (argumentbuilder.getRedirect() != null) {
               argumentbuilder.redirect(map.get(argumentbuilder.getRedirect()));
            }

            CommandNode commandnode3 = argumentbuilder.build();
            map.put(commandnode2, commandnode3);
            commandnode1.addChild(commandnode3);
            if (!commandnode2.getChildren().isEmpty()) {
               this.a(commandnode2, commandnode3, commandlistenerwrapper, map);
            }
         }
      }
   }

   public static LiteralArgumentBuilder<CommandListenerWrapper> a(String s) {
      return LiteralArgumentBuilder.literal(s);
   }

   public static <T> RequiredArgumentBuilder<CommandListenerWrapper, T> a(String s, ArgumentType<T> argumenttype) {
      return RequiredArgumentBuilder.argument(s, argumenttype);
   }

   public static Predicate<String> a(CommandDispatcher.b commanddispatcher_b) {
      return s -> {
         try {
            commanddispatcher_b.parse(new StringReader(s));
            return true;
         } catch (CommandSyntaxException var3) {
            return false;
         }
      };
   }

   public com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> a() {
      return this.g;
   }

   @Nullable
   public static <S> CommandSyntaxException a(ParseResults<S> parseresults) {
      return !parseresults.getReader().canRead()
         ? null
         : (
            parseresults.getExceptions().size() == 1
               ? (CommandSyntaxException)parseresults.getExceptions().values().iterator().next()
               : (
                  parseresults.getContext().getRange().isEmpty()
                     ? CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext(parseresults.getReader())
                     : CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(parseresults.getReader())
               )
         );
   }

   public static CommandBuildContext a(final HolderLookup.b holderlookup_b) {
      return new CommandBuildContext() {
         @Override
         public <T> HolderLookup<T> a(ResourceKey<? extends IRegistry<T>> resourcekey) {
            final HolderLookup.c<T> holderlookup_c = holderlookup_b.b(resourcekey);
            return new HolderLookup.a<T>(holderlookup_c) {
               @Override
               public Optional<HolderSet.Named<T>> a(TagKey<T> tagkey) {
                  return Optional.of(this.b(tagkey));
               }

               @Override
               public HolderSet.Named<T> b(TagKey<T> tagkey) {
                  Optional<HolderSet.Named<T>> optional = holderlookup_c.a(tagkey);
                  return optional.orElseGet(() -> HolderSet.a(holderlookup_c, tagkey));
               }
            };
         }
      };
   }

   public static void b() {
      CommandBuildContext commandbuildcontext = a(VanillaRegistries.a());
      com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> com_mojang_brigadier_commanddispatcher = new CommandDispatcher(
            CommandDispatcher.ServerType.a, commandbuildcontext
         )
         .a();
      RootCommandNode<CommandListenerWrapper> rootcommandnode = com_mojang_brigadier_commanddispatcher.getRoot();
      com_mojang_brigadier_commanddispatcher.findAmbiguities(
         (commandnode, commandnode1, commandnode2, collection) -> f.warn(
               "Ambiguity between arguments {} and {} with inputs: {}",
               new Object[]{
                  com_mojang_brigadier_commanddispatcher.getPath(commandnode1), com_mojang_brigadier_commanddispatcher.getPath(commandnode2), collection
               }
            )
      );
      Set<ArgumentType<?>> set = ArgumentUtils.a(rootcommandnode);
      Set<ArgumentType<?>> set1 = set.stream().filter(argumenttype -> !ArgumentTypeInfos.a(argumenttype.getClass())).collect(Collectors.toSet());
      if (!set1.isEmpty()) {
         f.warn(
            "Missing type registration for following arguments:\n {}",
            set1.stream().map(argumenttype -> "\t" + argumenttype).collect(Collectors.joining(",\n"))
         );
         throw new IllegalStateException("Unregistered argument types");
      }
   }

   public static enum ServerType {
      a(true, true),
      b(false, true),
      c(true, false);

      final boolean d;
      final boolean e;

      private ServerType(boolean flag, boolean flag1) {
         this.d = flag;
         this.e = flag1;
      }
   }

   @FunctionalInterface
   public interface b {
      void parse(StringReader var1) throws CommandSyntaxException;
   }
}
