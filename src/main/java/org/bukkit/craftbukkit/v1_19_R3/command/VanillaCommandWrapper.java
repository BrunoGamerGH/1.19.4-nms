package org.bukkit.craftbukkit.v1_19_R3.command;

import com.google.common.base.Joiner;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.tree.CommandNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftMinecartCommand;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;

public final class VanillaCommandWrapper extends BukkitCommand {
   private final CommandDispatcher dispatcher;
   public final CommandNode<CommandListenerWrapper> vanillaCommand;

   public VanillaCommandWrapper(CommandDispatcher dispatcher, CommandNode<CommandListenerWrapper> vanillaCommand) {
      super(vanillaCommand.getName(), "A Mojang provided command.", vanillaCommand.getUsageText(), Collections.EMPTY_LIST);
      this.dispatcher = dispatcher;
      this.vanillaCommand = vanillaCommand;
      this.setPermission(getPermission(vanillaCommand));
   }

   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
      if (!this.testPermission(sender)) {
         return true;
      } else {
         CommandListenerWrapper icommandlistener = getListener(sender);
         this.dispatcher.performPrefixedCommand(icommandlistener, this.toDispatcher(args, this.getName()), this.toDispatcher(args, commandLabel));
         return true;
      }
   }

   public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location location) throws IllegalArgumentException {
      Validate.notNull(sender, "Sender cannot be null");
      Validate.notNull(args, "Arguments cannot be null");
      Validate.notNull(alias, "Alias cannot be null");
      CommandListenerWrapper icommandlistener = getListener(sender);
      ParseResults<CommandListenerWrapper> parsed = this.dispatcher.a().parse(this.toDispatcher(args, this.getName()), icommandlistener);
      List<String> results = new ArrayList<>();
      this.dispatcher.a().getCompletionSuggestions(parsed).thenAccept(suggestions -> suggestions.getList().forEach(s -> results.add(s.getText())));
      return results;
   }

   public static CommandListenerWrapper getListener(CommandSender sender) {
      if (sender instanceof Player) {
         return ((CraftPlayer)sender).getHandle().cZ();
      } else if (sender instanceof BlockCommandSender) {
         return ((CraftBlockCommandSender)sender).getWrapper();
      } else if (sender instanceof CommandMinecart) {
         return ((CraftMinecartCommand)sender).getHandle().z().i();
      } else if (sender instanceof RemoteConsoleCommandSender) {
         return ((DedicatedServer)MinecraftServer.getServer()).s.g();
      } else if (sender instanceof ConsoleCommandSender) {
         return ((CraftServer)sender.getServer()).getServer().aD();
      } else if (sender instanceof ProxiedCommandSender) {
         return ((ProxiedNativeCommandSender)sender).getHandle();
      } else {
         throw new IllegalArgumentException("Cannot make " + sender + " a vanilla command listener");
      }
   }

   public static String getPermission(CommandNode<CommandListenerWrapper> vanillaCommand) {
      return "minecraft.command." + (vanillaCommand.getRedirect() == null ? vanillaCommand.getName() : vanillaCommand.getRedirect().getName());
   }

   private String toDispatcher(String[] args, String name) {
      return name + (args.length > 0 ? " " + Joiner.on(' ').join(args) : "");
   }
}
