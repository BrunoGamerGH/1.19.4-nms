package net.minecraft.server.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentChat;
import net.minecraft.commands.arguments.ArgumentProfile;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.players.GameProfileBanEntry;
import net.minecraft.server.players.GameProfileBanList;

public class CommandBan {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.ban.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("ban")
               .requires(var0x -> var0x.c(3)))
            .then(
               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("targets", ArgumentProfile.a())
                     .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentProfile.a(var0x, "targets"), null)))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("reason", ArgumentChat.a())
                        .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentProfile.a(var0x, "targets"), ArgumentChat.a(var0x, "reason")))
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Collection<GameProfile> var1, @Nullable IChatBaseComponent var2) throws CommandSyntaxException {
      GameProfileBanList var3 = var0.l().ac().f();
      int var4 = 0;

      for(GameProfile var6 : var1) {
         if (!var3.a(var6)) {
            GameProfileBanEntry var7 = new GameProfileBanEntry(var6, null, var0.c(), null, var2 == null ? null : var2.getString());
            var3.a(var7);
            ++var4;
            var0.a(IChatBaseComponent.a("commands.ban.success", ChatComponentUtils.a(var6), var7.d()), true);
            EntityPlayer var8 = var0.l().ac().a(var6.getId());
            if (var8 != null) {
               var8.b.b(IChatBaseComponent.c("multiplayer.disconnect.banned"));
            }
         }
      }

      if (var4 == 0) {
         throw a.create();
      } else {
         return var4;
      }
   }
}
