package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.GameModeArgument;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.GameRules;

public class CommandGamemode {
   public static final int a = 2;

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("gamemode")
               .requires(var0x -> var0x.c(2)))
            .then(
               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("gamemode", GameModeArgument.a())
                     .executes(
                        var0x -> a(var0x, Collections.singleton(((CommandListenerWrapper)var0x.getSource()).h()), GameModeArgument.a(var0x, "gamemode"))
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("target", ArgumentEntity.d())
                        .executes(var0x -> a(var0x, ArgumentEntity.f(var0x, "target"), GameModeArgument.a(var0x, "gamemode")))
                  )
            )
      );
   }

   private static void a(CommandListenerWrapper var0, EntityPlayer var1, EnumGamemode var2) {
      IChatBaseComponent var3 = IChatBaseComponent.c("gameMode." + var2.b());
      if (var0.f() == var1) {
         var0.a(IChatBaseComponent.a("commands.gamemode.success.self", var3), true);
      } else {
         if (var0.e().W().b(GameRules.o)) {
            var1.a(IChatBaseComponent.a("gameMode.changed", var3));
         }

         var0.a(IChatBaseComponent.a("commands.gamemode.success.other", var1.G_(), var3), true);
      }
   }

   private static int a(CommandContext<CommandListenerWrapper> var0, Collection<EntityPlayer> var1, EnumGamemode var2) {
      int var3 = 0;

      for(EntityPlayer var5 : var1) {
         if (var5.a(var2)) {
            a((CommandListenerWrapper)var0.getSource(), var5, var2);
            ++var3;
         }
      }

      return var3;
   }
}
