package org.bukkit.craftbukkit.v1_19_R3.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.logging.Level;
import net.minecraft.commands.CommandListenerWrapper;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;

public class BukkitCommandWrapper implements Command<CommandListenerWrapper>, Predicate<CommandListenerWrapper>, SuggestionProvider<CommandListenerWrapper> {
   private final CraftServer server;
   private final org.bukkit.command.Command command;

   public BukkitCommandWrapper(CraftServer server, org.bukkit.command.Command command) {
      this.server = server;
      this.command = command;
   }

   public LiteralCommandNode<CommandListenerWrapper> register(CommandDispatcher<CommandListenerWrapper> dispatcher, String label) {
      return dispatcher.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)LiteralArgumentBuilder.literal(label)
                  .requires(this))
               .executes(this))
            .then(RequiredArgumentBuilder.argument("args", StringArgumentType.greedyString()).suggests(this).executes(this))
      );
   }

   public boolean test(CommandListenerWrapper wrapper) {
      return this.command.testPermissionSilent(wrapper.getBukkitSender());
   }

   public int run(CommandContext<CommandListenerWrapper> context) throws CommandSyntaxException {
      CommandSender sender = ((CommandListenerWrapper)context.getSource()).getBukkitSender();

      try {
         return this.server.dispatchCommand(sender, context.getInput()) ? 1 : 0;
      } catch (CommandException var4) {
         sender.sendMessage(ChatColor.RED + "An internal error occurred while attempting to perform this command");
         this.server.getLogger().log(Level.SEVERE, null, var4);
         return 0;
      }
   }

   public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandListenerWrapper> context, SuggestionsBuilder builder) throws CommandSyntaxException {
      List<String> results = this.server
         .tabComplete(
            ((CommandListenerWrapper)context.getSource()).getBukkitSender(),
            builder.getInput(),
            ((CommandListenerWrapper)context.getSource()).e(),
            ((CommandListenerWrapper)context.getSource()).d(),
            true
         );
      builder = builder.createOffset(builder.getInput().lastIndexOf(32) + 1);

      for(String s : results) {
         builder.suggest(s);
      }

      return builder.buildFuture();
   }
}
