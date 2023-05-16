package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.item.ArgumentItemPredicate;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.item.ItemStack;

public class CommandClear {
   private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("clear.failed.single", var0));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("clear.failed.multiple", var0));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0, CommandBuildContext var1) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("clear")
                  .requires(var0x -> var0x.c(2)))
               .executes(
                  var0x -> a(
                        (CommandListenerWrapper)var0x.getSource(), Collections.singleton(((CommandListenerWrapper)var0x.getSource()).h()), var0xx -> true, -1
                     )
               ))
            .then(
               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.d())
                     .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentEntity.f(var0x, "targets"), var0xx -> true, -1)))
                  .then(
                     ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("item", ArgumentItemPredicate.a(var1))
                           .executes(
                              var0x -> a(
                                    (CommandListenerWrapper)var0x.getSource(), ArgumentEntity.f(var0x, "targets"), ArgumentItemPredicate.a(var0x, "item"), -1
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("maxCount", IntegerArgumentType.integer(0))
                              .executes(
                                 var0x -> a(
                                       (CommandListenerWrapper)var0x.getSource(),
                                       ArgumentEntity.f(var0x, "targets"),
                                       ArgumentItemPredicate.a(var0x, "item"),
                                       IntegerArgumentType.getInteger(var0x, "maxCount")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Collection<EntityPlayer> var1, Predicate<ItemStack> var2, int var3) throws CommandSyntaxException {
      int var4 = 0;

      for(EntityPlayer var6 : var1) {
         var4 += var6.fJ().a(var2, var3, var6.bO.q());
         var6.bP.d();
         var6.bO.a(var6.fJ());
      }

      if (var4 == 0) {
         if (var1.size() == 1) {
            throw a.create(var1.iterator().next().Z());
         } else {
            throw b.create(var1.size());
         }
      } else {
         if (var3 == 0) {
            if (var1.size() == 1) {
               var0.a(IChatBaseComponent.a("commands.clear.test.single", var4, var1.iterator().next().G_()), true);
            } else {
               var0.a(IChatBaseComponent.a("commands.clear.test.multiple", var4, var1.size()), true);
            }
         } else if (var1.size() == 1) {
            var0.a(IChatBaseComponent.a("commands.clear.success.single", var4, var1.iterator().next().G_()), true);
         } else {
            var0.a(IChatBaseComponent.a("commands.clear.success.multiple", var4, var1.size()), true);
         }

         return var4;
      }
   }
}
