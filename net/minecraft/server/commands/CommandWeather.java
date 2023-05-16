package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentTime;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.valueproviders.IntProvider;

public class CommandWeather {
   private static final int a = -1;

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                        "weather"
                     )
                     .requires(var0x -> var0x.c(2)))
                  .then(
                     ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("clear")
                           .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), -1)))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("duration", ArgumentTime.a(1))
                              .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "duration")))
                        )
                  ))
               .then(
                  ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("rain")
                        .executes(var0x -> b((CommandListenerWrapper)var0x.getSource(), -1)))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("duration", ArgumentTime.a(1))
                           .executes(var0x -> b((CommandListenerWrapper)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "duration")))
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("thunder")
                     .executes(var0x -> c((CommandListenerWrapper)var0x.getSource(), -1)))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("duration", ArgumentTime.a(1))
                        .executes(var0x -> c((CommandListenerWrapper)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "duration")))
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, int var1, IntProvider var2) {
      return var1 == -1 ? var2.a(var0.e().r_()) : var1;
   }

   private static int a(CommandListenerWrapper var0, int var1) {
      var0.e().a(a(var0, var1, WorldServer.b), 0, false, false);
      var0.a(IChatBaseComponent.c("commands.weather.set.clear"), true);
      return var1;
   }

   private static int b(CommandListenerWrapper var0, int var1) {
      var0.e().a(0, a(var0, var1, WorldServer.c), true, false);
      var0.a(IChatBaseComponent.c("commands.weather.set.rain"), true);
      return var1;
   }

   private static int c(CommandListenerWrapper var0, int var1) {
      var0.e().a(0, a(var0, var1, WorldServer.d), true, true);
      var0.a(IChatBaseComponent.c("commands.weather.set.thunder"), true);
      return var1;
   }
}
