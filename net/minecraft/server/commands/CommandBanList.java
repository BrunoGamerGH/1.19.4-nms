package net.minecraft.server.commands;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Collection;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.players.ExpirableListEntry;
import net.minecraft.server.players.PlayerList;

public class CommandBanList {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                        "banlist"
                     )
                     .requires(var0x -> var0x.c(3)))
                  .executes(var0x -> {
                     PlayerList var1 = ((CommandListenerWrapper)var0x.getSource()).l().ac();
                     return a((CommandListenerWrapper)var0x.getSource(), Lists.newArrayList(Iterables.concat(var1.f().d(), var1.g().d())));
                  }))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("ips")
                     .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ((CommandListenerWrapper)var0x.getSource()).l().ac().g().d()))
               ))
            .then(
               net.minecraft.commands.CommandDispatcher.a("players")
                  .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ((CommandListenerWrapper)var0x.getSource()).l().ac().f().d()))
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Collection<? extends ExpirableListEntry<?>> var1) {
      if (var1.isEmpty()) {
         var0.a(IChatBaseComponent.c("commands.banlist.none"), false);
      } else {
         var0.a(IChatBaseComponent.a("commands.banlist.list", var1.size()), false);

         for(ExpirableListEntry<?> var3 : var1) {
            var0.a(IChatBaseComponent.a("commands.banlist.entry", var3.e(), var3.b(), var3.d()), false);
         }
      }

      return var1.size();
   }
}
