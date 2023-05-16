package net.minecraft.server.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.ArgumentProfile;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.players.GameProfileBanList;

public class CommandPardon {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.pardon.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("pardon")
               .requires(var0x -> var0x.c(3)))
            .then(
               net.minecraft.commands.CommandDispatcher.a("targets", ArgumentProfile.a())
                  .suggests((var0x, var1) -> ICompletionProvider.a(((CommandListenerWrapper)var0x.getSource()).l().ac().f().a(), var1))
                  .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentProfile.a(var0x, "targets")))
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      GameProfileBanList var2 = var0.l().ac().f();
      int var3 = 0;

      for(GameProfile var5 : var1) {
         if (var2.a(var5)) {
            var2.c(var5);
            ++var3;
            var0.a(IChatBaseComponent.a("commands.pardon.success", ChatComponentUtils.a(var5)), true);
         }
      }

      if (var3 == 0) {
         throw a.create();
      } else {
         return var3;
      }
   }
}
