package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ArgumentMinecraftKeyRegistered;
import net.minecraft.commands.synchronization.CompletionProviders;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.item.crafting.IRecipe;

public class CommandRecipe {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.recipe.give.failed"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.recipe.take.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("recipe")
                  .requires(var0x -> var0x.c(2)))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("give")
                     .then(
                        ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.d())
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("recipe", ArgumentMinecraftKeyRegistered.a())
                                    .suggests(CompletionProviders.b)
                                    .executes(
                                       var0x -> a(
                                             (CommandListenerWrapper)var0x.getSource(),
                                             ArgumentEntity.f(var0x, "targets"),
                                             Collections.singleton(ArgumentMinecraftKeyRegistered.b(var0x, "recipe"))
                                          )
                                    )
                              ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("*")
                                 .executes(
                                    var0x -> a(
                                          (CommandListenerWrapper)var0x.getSource(),
                                          ArgumentEntity.f(var0x, "targets"),
                                          ((CommandListenerWrapper)var0x.getSource()).l().aE().b()
                                       )
                                 )
                           )
                     )
               ))
            .then(
               net.minecraft.commands.CommandDispatcher.a("take")
                  .then(
                     ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.d())
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("recipe", ArgumentMinecraftKeyRegistered.a())
                                 .suggests(CompletionProviders.b)
                                 .executes(
                                    var0x -> b(
                                          (CommandListenerWrapper)var0x.getSource(),
                                          ArgumentEntity.f(var0x, "targets"),
                                          Collections.singleton(ArgumentMinecraftKeyRegistered.b(var0x, "recipe"))
                                       )
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("*")
                              .executes(
                                 var0x -> b(
                                       (CommandListenerWrapper)var0x.getSource(),
                                       ArgumentEntity.f(var0x, "targets"),
                                       ((CommandListenerWrapper)var0x.getSource()).l().aE().b()
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Collection<EntityPlayer> var1, Collection<IRecipe<?>> var2) throws CommandSyntaxException {
      int var3 = 0;

      for(EntityPlayer var5 : var1) {
         var3 += var5.a(var2);
      }

      if (var3 == 0) {
         throw a.create();
      } else {
         if (var1.size() == 1) {
            var0.a(IChatBaseComponent.a("commands.recipe.give.success.single", var2.size(), var1.iterator().next().G_()), true);
         } else {
            var0.a(IChatBaseComponent.a("commands.recipe.give.success.multiple", var2.size(), var1.size()), true);
         }

         return var3;
      }
   }

   private static int b(CommandListenerWrapper var0, Collection<EntityPlayer> var1, Collection<IRecipe<?>> var2) throws CommandSyntaxException {
      int var3 = 0;

      for(EntityPlayer var5 : var1) {
         var3 += var5.b(var2);
      }

      if (var3 == 0) {
         throw b.create();
      } else {
         if (var1.size() == 1) {
            var0.a(IChatBaseComponent.a("commands.recipe.take.success.single", var2.size(), var1.iterator().next().G_()), true);
         } else {
            var0.a(IChatBaseComponent.a("commands.recipe.take.success.multiple", var2.size(), var1.size()), true);
         }

         return var3;
      }
   }
}
