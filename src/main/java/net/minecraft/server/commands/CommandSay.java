package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentChat;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.server.players.PlayerList;

public class CommandSay {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("say")
               .requires(var0x -> var0x.c(2)))
            .then(net.minecraft.commands.CommandDispatcher.a("message", ArgumentChat.a()).executes(var0x -> {
               ArgumentChat.a(var0x, "message", var1 -> {
                  CommandListenerWrapper var2 = (CommandListenerWrapper)var0x.getSource();
                  PlayerList var3 = var2.l().ac();
                  var3.a(var1, var2, ChatMessageType.a(ChatMessageType.d, var2));
               });
               return 1;
            }))
      );
   }
}
