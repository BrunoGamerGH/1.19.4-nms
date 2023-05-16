package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;

public class CommandSeed {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0, boolean var1) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("seed")
               .requires(var1x -> !var1 || var1x.c(2)))
            .executes(var0x -> {
               long var1x = ((CommandListenerWrapper)var0x.getSource()).e().A();
               IChatBaseComponent var3 = ChatComponentUtils.a(String.valueOf(var1x));
               ((CommandListenerWrapper)var0x.getSource()).a(IChatBaseComponent.a("commands.seed.success", var3), false);
               return (int)var1x;
            })
      );
   }
}
