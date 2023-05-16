package net.minecraft.server.commands;

import com.google.common.net.InetAddresses;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentChat;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.players.IpBanEntry;
import net.minecraft.server.players.IpBanList;

public class CommandBanIp {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.banip.invalid"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.banip.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("ban-ip")
               .requires(var0x -> var0x.c(3)))
            .then(
               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("target", StringArgumentType.word())
                     .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), StringArgumentType.getString(var0x, "target"), null)))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("reason", ArgumentChat.a())
                        .executes(
                           var0x -> a(
                                 (CommandListenerWrapper)var0x.getSource(), StringArgumentType.getString(var0x, "target"), ArgumentChat.a(var0x, "reason")
                              )
                        )
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, String var1, @Nullable IChatBaseComponent var2) throws CommandSyntaxException {
      if (InetAddresses.isInetAddress(var1)) {
         return b(var0, var1, var2);
      } else {
         EntityPlayer var3 = var0.l().ac().a(var1);
         if (var3 != null) {
            return b(var0, var3.y(), var2);
         } else {
            throw a.create();
         }
      }
   }

   private static int b(CommandListenerWrapper var0, String var1, @Nullable IChatBaseComponent var2) throws CommandSyntaxException {
      IpBanList var3 = var0.l().ac().g();
      if (var3.a(var1)) {
         throw b.create();
      } else {
         List<EntityPlayer> var4 = var0.l().ac().b(var1);
         IpBanEntry var5 = new IpBanEntry(var1, null, var0.c(), null, var2 == null ? null : var2.getString());
         var3.a(var5);
         var0.a(IChatBaseComponent.a("commands.banip.success", var1, var5.d()), true);
         if (!var4.isEmpty()) {
            var0.a(IChatBaseComponent.a("commands.banip.info", var4.size(), EntitySelector.a(var4)), true);
         }

         for(EntityPlayer var7 : var4) {
            var7.b.b(IChatBaseComponent.c("multiplayer.disconnect.ip_banned"));
         }

         return var4.size();
      }
   }
}
