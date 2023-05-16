package net.minecraft.server.commands;

import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Map;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;

public class CommandHelp {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.help.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("help").executes(var1 -> {
               Map<CommandNode<CommandListenerWrapper>, String> var2 = var0.getSmartUsage(var0.getRoot(), (CommandListenerWrapper)var1.getSource());
      
               for(String var4 : var2.values()) {
                  ((CommandListenerWrapper)var1.getSource()).a(IChatBaseComponent.b("/" + var4), false);
               }
      
               return var2.size();
            }))
            .then(
               net.minecraft.commands.CommandDispatcher.a("command", StringArgumentType.greedyString())
                  .executes(
                     var1 -> {
                        ParseResults<CommandListenerWrapper> var2 = var0.parse(
                           StringArgumentType.getString(var1, "command"), (CommandListenerWrapper)var1.getSource()
                        );
                        if (var2.getContext().getNodes().isEmpty()) {
                           throw a.create();
                        } else {
                           Map<CommandNode<CommandListenerWrapper>, String> var3 = var0.getSmartUsage(
                              ((ParsedCommandNode)Iterables.getLast(var2.getContext().getNodes())).getNode(), (CommandListenerWrapper)var1.getSource()
                           );
               
                           for(String var5 : var3.values()) {
                              ((CommandListenerWrapper)var1.getSource()).a(IChatBaseComponent.b("/" + var2.getReader().getString() + " " + var5), false);
                           }
               
                           return var3.size();
                        }
                     }
                  )
            )
      );
   }
}
