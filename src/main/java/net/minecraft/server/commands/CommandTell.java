package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentChat;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.players.PlayerList;

public class CommandTell {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      LiteralCommandNode<CommandListenerWrapper> var1 = var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)net.minecraft.commands.CommandDispatcher.a("msg")
            .then(
               net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.d())
                  .then(net.minecraft.commands.CommandDispatcher.a("message", ArgumentChat.a()).executes(var0x -> {
                     Collection<EntityPlayer> var1x = ArgumentEntity.f(var0x, "targets");
                     if (!var1x.isEmpty()) {
                        ArgumentChat.a(var0x, "message", var2 -> a((CommandListenerWrapper)var0x.getSource(), var1x, var2));
                     }
            
                     return var1x.size();
                  }))
            )
      );
      var0.register((LiteralArgumentBuilder<CommandListenerWrapper>)net.minecraft.commands.CommandDispatcher.a("tell").redirect(var1));
      var0.register((LiteralArgumentBuilder<CommandListenerWrapper>)net.minecraft.commands.CommandDispatcher.a("w").redirect(var1));
   }

   private static void a(CommandListenerWrapper var0, Collection<EntityPlayer> var1, PlayerChatMessage var2) {
      ChatMessageType.a var3 = ChatMessageType.a(ChatMessageType.e, var0);
      OutgoingChatMessage var4 = OutgoingChatMessage.a(var2);
      boolean var5 = false;

      for(EntityPlayer var7 : var1) {
         ChatMessageType.a var8 = ChatMessageType.a(ChatMessageType.f, var0).c(var7.G_());
         var0.a(var4, false, var8);
         boolean var9 = var0.a(var7);
         var7.a(var4, var9, var3);
         var5 |= var9 && var2.i();
      }

      if (var5) {
         var0.a(PlayerList.f);
      }
   }
}
