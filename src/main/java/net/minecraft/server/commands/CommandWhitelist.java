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
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.WhiteList;
import net.minecraft.server.players.WhiteListEntry;

public class CommandWhitelist {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.whitelist.alreadyOn"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.whitelist.alreadyOff"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.whitelist.add.failed"));
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.whitelist.remove.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                 "whitelist"
                              )
                              .requires(var0x -> var0x.c(3)))
                           .then(net.minecraft.commands.CommandDispatcher.a("on").executes(var0x -> b((CommandListenerWrapper)var0x.getSource()))))
                        .then(net.minecraft.commands.CommandDispatcher.a("off").executes(var0x -> c((CommandListenerWrapper)var0x.getSource()))))
                     .then(net.minecraft.commands.CommandDispatcher.a("list").executes(var0x -> d((CommandListenerWrapper)var0x.getSource()))))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("add")
                        .then(net.minecraft.commands.CommandDispatcher.a("targets", ArgumentProfile.a()).suggests((var0x, var1) -> {
                           PlayerList var2 = ((CommandListenerWrapper)var0x.getSource()).l().ac();
                           return ICompletionProvider.b(var2.t().stream().filter(var1x -> !var2.i().a(var1x.fI())).map(var0xx -> var0xx.fI().getName()), var1);
                        }).executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentProfile.a(var0x, "targets"))))
                  ))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("remove")
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("targets", ArgumentProfile.a())
                           .suggests((var0x, var1) -> ICompletionProvider.a(((CommandListenerWrapper)var0x.getSource()).l().ac().j(), var1))
                           .executes(var0x -> b((CommandListenerWrapper)var0x.getSource(), ArgumentProfile.a(var0x, "targets")))
                     )
               ))
            .then(net.minecraft.commands.CommandDispatcher.a("reload").executes(var0x -> a((CommandListenerWrapper)var0x.getSource())))
      );
   }

   private static int a(CommandListenerWrapper var0) {
      var0.l().ac().a();
      var0.a(IChatBaseComponent.c("commands.whitelist.reloaded"), true);
      var0.l().a(var0);
      return 1;
   }

   private static int a(CommandListenerWrapper var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      WhiteList var2 = var0.l().ac().i();
      int var3 = 0;

      for(GameProfile var5 : var1) {
         if (!var2.a(var5)) {
            WhiteListEntry var6 = new WhiteListEntry(var5);
            var2.a(var6);
            var0.a(IChatBaseComponent.a("commands.whitelist.add.success", ChatComponentUtils.a(var5)), true);
            ++var3;
         }
      }

      if (var3 == 0) {
         throw c.create();
      } else {
         return var3;
      }
   }

   private static int b(CommandListenerWrapper var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      WhiteList var2 = var0.l().ac().i();
      int var3 = 0;

      for(GameProfile var5 : var1) {
         if (var2.a(var5)) {
            WhiteListEntry var6 = new WhiteListEntry(var5);
            var2.b(var6);
            var0.a(IChatBaseComponent.a("commands.whitelist.remove.success", ChatComponentUtils.a(var5)), true);
            ++var3;
         }
      }

      if (var3 == 0) {
         throw d.create();
      } else {
         var0.l().a(var0);
         return var3;
      }
   }

   private static int b(CommandListenerWrapper var0) throws CommandSyntaxException {
      PlayerList var1 = var0.l().ac();
      if (var1.o()) {
         throw a.create();
      } else {
         var1.a(true);
         var0.a(IChatBaseComponent.c("commands.whitelist.enabled"), true);
         var0.l().a(var0);
         return 1;
      }
   }

   private static int c(CommandListenerWrapper var0) throws CommandSyntaxException {
      PlayerList var1 = var0.l().ac();
      if (!var1.o()) {
         throw b.create();
      } else {
         var1.a(false);
         var0.a(IChatBaseComponent.c("commands.whitelist.disabled"), true);
         return 1;
      }
   }

   private static int d(CommandListenerWrapper var0) {
      String[] var1 = var0.l().ac().j();
      if (var1.length == 0) {
         var0.a(IChatBaseComponent.c("commands.whitelist.none"), false);
      } else {
         var0.a(IChatBaseComponent.a("commands.whitelist.list", var1.length, String.join(", ", var1)), false);
      }

      return var1.length;
   }
}
