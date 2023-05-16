package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentChatComponent;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.server.level.EntityPlayer;

public class CommandTellRaw {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("tellraw")
               .requires(var0x -> var0x.c(2)))
            .then(
               net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.d())
                  .then(net.minecraft.commands.CommandDispatcher.a("message", ArgumentChatComponent.a()).executes(var0x -> {
                     int var1 = 0;
            
                     for(EntityPlayer var3 : ArgumentEntity.f(var0x, "targets")) {
                        var3.b(ChatComponentUtils.a((CommandListenerWrapper)var0x.getSource(), ArgumentChatComponent.a(var0x, "message"), var3, 0), false);
                        ++var1;
                     }
            
                     return var1;
                  }))
            )
      );
   }
}
