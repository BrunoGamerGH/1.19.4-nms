package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.List;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentChat;
import net.minecraft.network.chat.ChatClickable;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.ScoreboardTeam;

public class CommandTeamMsg {
   private static final ChatModifier a = ChatModifier.a
      .a(new ChatHoverable(ChatHoverable.EnumHoverAction.a, IChatBaseComponent.c("chat.type.team.hover")))
      .a(new ChatClickable(ChatClickable.EnumClickAction.d, "/teammsg "));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.teammsg.failed.noteam"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      LiteralCommandNode<CommandListenerWrapper> var1 = var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)net.minecraft.commands.CommandDispatcher.a("teammsg")
            .then(net.minecraft.commands.CommandDispatcher.a("message", ArgumentChat.a()).executes(var0x -> {
               CommandListenerWrapper var1x = (CommandListenerWrapper)var0x.getSource();
               Entity var2 = var1x.g();
               ScoreboardTeam var3 = (ScoreboardTeam)var2.cb();
               if (var3 == null) {
                  throw b.create();
               } else {
                  List<EntityPlayer> var4 = var1x.l().ac().t().stream().filter(var2x -> var2x == var2 || var2x.cb() == var3).toList();
                  if (!var4.isEmpty()) {
                     ArgumentChat.a(var0x, "message", var4x -> a(var1x, var2, var3, var4, var4x));
                  }
      
                  return var4.size();
               }
            }))
      );
      var0.register((LiteralArgumentBuilder<CommandListenerWrapper>)net.minecraft.commands.CommandDispatcher.a("tm").redirect(var1));
   }

   private static void a(CommandListenerWrapper var0, Entity var1, ScoreboardTeam var2, List<EntityPlayer> var3, PlayerChatMessage var4) {
      IChatBaseComponent var5 = var2.d().c(a);
      ChatMessageType.a var6 = ChatMessageType.a(ChatMessageType.g, var0).c(var5);
      ChatMessageType.a var7 = ChatMessageType.a(ChatMessageType.h, var0).c(var5);
      OutgoingChatMessage var8 = OutgoingChatMessage.a(var4);
      boolean var9 = false;

      for(EntityPlayer var11 : var3) {
         ChatMessageType.a var12 = var11 == var1 ? var7 : var6;
         boolean var13 = var0.a(var11);
         var11.a(var8, var13, var12);
         var9 |= var13 && var4.i();
      }

      if (var9) {
         var0.a(PlayerList.f);
      }
   }
}
