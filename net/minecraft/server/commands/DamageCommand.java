package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.coordinates.ArgumentVec3;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class DamageCommand {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.damage.invulnerable"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0, CommandBuildContext var1) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("damage")
               .requires(var0x -> var0x.c(2)))
            .then(
               net.minecraft.commands.CommandDispatcher.a("target", ArgumentEntity.a())
                  .then(
                     ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("amount", FloatArgumentType.floatArg(0.0F))
                           .executes(
                              var0x -> a(
                                    (CommandListenerWrapper)var0x.getSource(),
                                    ArgumentEntity.a(var0x, "target"),
                                    FloatArgumentType.getFloat(var0x, "amount"),
                                    ((CommandListenerWrapper)var0x.getSource()).e().af().n()
                                 )
                           ))
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                       "damageType", ResourceArgument.a(var1, Registries.o)
                                    )
                                    .executes(
                                       var0x -> a(
                                             (CommandListenerWrapper)var0x.getSource(),
                                             ArgumentEntity.a(var0x, "target"),
                                             FloatArgumentType.getFloat(var0x, "amount"),
                                             new DamageSource(ResourceArgument.a(var0x, "damageType", Registries.o))
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("at")
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("location", ArgumentVec3.a())
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      ArgumentEntity.a(var0x, "target"),
                                                      FloatArgumentType.getFloat(var0x, "amount"),
                                                      new DamageSource(
                                                         ResourceArgument.a(var0x, "damageType", Registries.o), ArgumentVec3.a(var0x, "location")
                                                      )
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("by")
                                    .then(
                                       ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("entity", ArgumentEntity.a())
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      ArgumentEntity.a(var0x, "target"),
                                                      FloatArgumentType.getFloat(var0x, "amount"),
                                                      new DamageSource(
                                                         ResourceArgument.a(var0x, "damageType", Registries.o), ArgumentEntity.a(var0x, "entity")
                                                      )
                                                   )
                                             ))
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("from")
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("cause", ArgumentEntity.a())
                                                      .executes(
                                                         var0x -> a(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               ArgumentEntity.a(var0x, "target"),
                                                               FloatArgumentType.getFloat(var0x, "amount"),
                                                               new DamageSource(
                                                                  ResourceArgument.a(var0x, "damageType", Registries.o),
                                                                  ArgumentEntity.a(var0x, "entity"),
                                                                  ArgumentEntity.a(var0x, "cause")
                                                               )
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Entity var1, float var2, DamageSource var3) throws CommandSyntaxException {
      if (var1.a(var3, var2)) {
         var0.a(IChatBaseComponent.a("commands.damage.success", var2, var1.G_()), true);
         return 1;
      } else {
         throw a.create();
      }
   }
}
