package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;

public class RideCommand {
   private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.ride.not_riding", var0));
   private static final Dynamic2CommandExceptionType b = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("commands.ride.already_riding", var0, var1)
   );
   private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("commands.ride.mount.failure.generic", var0, var1)
   );
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.ride.mount.failure.cant_ride_players"));
   private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.ride.mount.failure.loop"));
   private static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.ride.mount.failure.wrong_dimension"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("ride")
               .requires(var0x -> var0x.c(2)))
            .then(
               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("target", ArgumentEntity.a())
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("mount")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("vehicle", ArgumentEntity.a())
                                 .executes(
                                    var0x -> a(
                                          (CommandListenerWrapper)var0x.getSource(), ArgumentEntity.a(var0x, "target"), ArgumentEntity.a(var0x, "vehicle")
                                       )
                                 )
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("dismount")
                        .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentEntity.a(var0x, "target")))
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Entity var1, Entity var2) throws CommandSyntaxException {
      Entity var3 = var1.cV();
      if (var3 != null) {
         throw b.create(var1.G_(), var3.G_());
      } else if (var2.ae() == EntityTypes.bt) {
         throw d.create();
      } else if (var1.cO().anyMatch(var1x -> var1x == var2)) {
         throw e.create();
      } else if (var1.Y() != var2.Y()) {
         throw f.create();
      } else if (!var1.a(var2, true)) {
         throw c.create(var1.G_(), var2.G_());
      } else {
         var0.a(IChatBaseComponent.a("commands.ride.mount.success", var1.G_(), var2.G_()), true);
         return 1;
      }
   }

   private static int a(CommandListenerWrapper var0, Entity var1) throws CommandSyntaxException {
      Entity var2 = var1.cV();
      if (var2 == null) {
         throw a.create(var1.G_());
      } else {
         var1.bz();
         var0.a(IChatBaseComponent.a("commands.ride.dismount.success", var1.G_(), var2.G_()), true);
         return 1;
      }
   }
}
