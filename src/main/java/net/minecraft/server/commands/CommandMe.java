package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentChat;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.server.players.PlayerList;

public class CommandMe {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)net.minecraft.commands.CommandDispatcher.a("me")
            .then(net.minecraft.commands.CommandDispatcher.a("action", ArgumentChat.a()).executes(var0x -> {
               ArgumentChat.a(var0x, "action", var1 -> {
                  CommandListenerWrapper var2 = (CommandListenerWrapper)var0x.getSource();
                  PlayerList var3 = var2.l().ac();
                  var3.a(var1, var2, ChatMessageType.a(ChatMessageType.i, var2));
               });
               return 1;
            }))
      );
   }
}
