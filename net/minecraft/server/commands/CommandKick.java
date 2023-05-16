package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentChat;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;

public class CommandKick {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("kick")
               .requires(var0x -> var0x.c(3)))
            .then(
               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.d())
                     .executes(
                        var0x -> a(
                              (CommandListenerWrapper)var0x.getSource(),
                              ArgumentEntity.f(var0x, "targets"),
                              IChatBaseComponent.c("multiplayer.disconnect.kicked")
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("reason", ArgumentChat.a())
                        .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentEntity.f(var0x, "targets"), ArgumentChat.a(var0x, "reason")))
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Collection<EntityPlayer> var1, IChatBaseComponent var2) {
      for(EntityPlayer var4 : var1) {
         var4.b.b(var2);
         var0.a(IChatBaseComponent.a("commands.kick.success", var4.G_(), var2), true);
      }

      return var1.size();
   }
}
