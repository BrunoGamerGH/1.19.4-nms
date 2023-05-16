package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.EnumGamemode;

public class CommandSpectate {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.spectate.self"));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.spectate.not_spectator", var0));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                     "spectate"
                  )
                  .requires(var0x -> var0x.c(2)))
               .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), null, ((CommandListenerWrapper)var0x.getSource()).h())))
            .then(
               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("target", ArgumentEntity.a())
                     .executes(
                        var0x -> a(
                              (CommandListenerWrapper)var0x.getSource(), ArgumentEntity.a(var0x, "target"), ((CommandListenerWrapper)var0x.getSource()).h()
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("player", ArgumentEntity.c())
                        .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentEntity.a(var0x, "target"), ArgumentEntity.e(var0x, "player")))
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, @Nullable Entity var1, EntityPlayer var2) throws CommandSyntaxException {
      if (var2 == var1) {
         throw a.create();
      } else if (var2.d.b() != EnumGamemode.d) {
         throw b.create(var2.G_());
      } else {
         var2.c(var1);
         if (var1 != null) {
            var0.a(IChatBaseComponent.a("commands.spectate.success.started", var1.G_()), false);
         } else {
            var0.a(IChatBaseComponent.c("commands.spectate.success.stopped"), false);
         }

         return 1;
      }
   }
}
