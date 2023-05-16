package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentAngle;
import net.minecraft.commands.arguments.coordinates.ArgumentPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;

public class CommandSetWorldSpawn {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                     "setworldspawn"
                  )
                  .requires(var0x -> var0x.c(2)))
               .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), BlockPosition.a(((CommandListenerWrapper)var0x.getSource()).d()), 0.0F)))
            .then(
               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("pos", ArgumentPosition.a())
                     .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentPosition.c(var0x, "pos"), 0.0F)))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("angle", ArgumentAngle.a())
                        .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentPosition.c(var0x, "pos"), ArgumentAngle.a(var0x, "angle")))
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, BlockPosition var1, float var2) {
      var0.e().a(var1, var2);
      var0.a(IChatBaseComponent.a("commands.setworldspawn.success", var1.u(), var1.v(), var1.w(), var2), true);
      return 1;
   }
}
