package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;

public class CommandIdleTimeout {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("setidletimeout")
               .requires(var0x -> var0x.c(3)))
            .then(
               net.minecraft.commands.CommandDispatcher.a("minutes", IntegerArgumentType.integer(0))
                  .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "minutes")))
            )
      );
   }

   private static int a(CommandListenerWrapper var0, int var1) {
      var0.l().c(var1);
      var0.a(IChatBaseComponent.a("commands.setidletimeout.success", var1), true);
      return var1;
   }
}
