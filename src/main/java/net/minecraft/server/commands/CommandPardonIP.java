package net.minecraft.server.commands;

import com.google.common.net.InetAddresses;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.players.IpBanList;

public class CommandPardonIP {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.pardonip.invalid"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.pardonip.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("pardon-ip")
               .requires(var0x -> var0x.c(3)))
            .then(
               net.minecraft.commands.CommandDispatcher.a("target", StringArgumentType.word())
                  .suggests((var0x, var1) -> ICompletionProvider.a(((CommandListenerWrapper)var0x.getSource()).l().ac().g().a(), var1))
                  .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), StringArgumentType.getString(var0x, "target")))
            )
      );
   }

   private static int a(CommandListenerWrapper var0, String var1) throws CommandSyntaxException {
      if (!InetAddresses.isInetAddress(var1)) {
         throw a.create();
      } else {
         IpBanList var2 = var0.l().ac().g();
         if (!var2.a(var1)) {
            throw b.create();
         } else {
            var2.c(var1);
            var0.a(IChatBaseComponent.a("commands.pardonip.success", var1), true);
            return 1;
         }
      }
   }
}
