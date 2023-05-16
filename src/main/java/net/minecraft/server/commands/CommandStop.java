package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;

public class CommandStop {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("stop")
               .requires(var0x -> var0x.c(4)))
            .executes(var0x -> {
               ((CommandListenerWrapper)var0x.getSource()).a(IChatBaseComponent.c("commands.stop.stopping"), true);
               ((CommandListenerWrapper)var0x.getSource()).l().a(false);
               return 1;
            })
      );
   }
}
