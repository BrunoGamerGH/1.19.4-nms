package net.minecraft.server.commands;

import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Set;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.Entity;

public class CommandTag {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.tag.add.failed"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.tag.remove.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("tag")
               .requires(var0x -> var0x.c(2)))
            .then(
               ((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b())
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("add")
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("name", StringArgumentType.word())
                                    .executes(
                                       var0x -> a(
                                             (CommandListenerWrapper)var0x.getSource(),
                                             ArgumentEntity.b(var0x, "targets"),
                                             StringArgumentType.getString(var0x, "name")
                                          )
                                    )
                              )
                        ))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("remove")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("name", StringArgumentType.word())
                                 .suggests((var0x, var1) -> ICompletionProvider.b(a(ArgumentEntity.b(var0x, "targets")), var1))
                                 .executes(
                                    var0x -> b(
                                          (CommandListenerWrapper)var0x.getSource(),
                                          ArgumentEntity.b(var0x, "targets"),
                                          StringArgumentType.getString(var0x, "name")
                                       )
                                 )
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("list")
                        .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentEntity.b(var0x, "targets")))
                  )
            )
      );
   }

   private static Collection<String> a(Collection<? extends Entity> var0) {
      Set<String> var1 = Sets.newHashSet();

      for(Entity var3 : var0) {
         var1.addAll(var3.ag());
      }

      return var1;
   }

   private static int a(CommandListenerWrapper var0, Collection<? extends Entity> var1, String var2) throws CommandSyntaxException {
      int var3 = 0;

      for(Entity var5 : var1) {
         if (var5.a(var2)) {
            ++var3;
         }
      }

      if (var3 == 0) {
         throw a.create();
      } else {
         if (var1.size() == 1) {
            var0.a(IChatBaseComponent.a("commands.tag.add.success.single", var2, var1.iterator().next().G_()), true);
         } else {
            var0.a(IChatBaseComponent.a("commands.tag.add.success.multiple", var2, var1.size()), true);
         }

         return var3;
      }
   }

   private static int b(CommandListenerWrapper var0, Collection<? extends Entity> var1, String var2) throws CommandSyntaxException {
      int var3 = 0;

      for(Entity var5 : var1) {
         if (var5.b(var2)) {
            ++var3;
         }
      }

      if (var3 == 0) {
         throw b.create();
      } else {
         if (var1.size() == 1) {
            var0.a(IChatBaseComponent.a("commands.tag.remove.success.single", var2, var1.iterator().next().G_()), true);
         } else {
            var0.a(IChatBaseComponent.a("commands.tag.remove.success.multiple", var2, var1.size()), true);
         }

         return var3;
      }
   }

   private static int a(CommandListenerWrapper var0, Collection<? extends Entity> var1) {
      Set<String> var2 = Sets.newHashSet();

      for(Entity var4 : var1) {
         var2.addAll(var4.ag());
      }

      if (var1.size() == 1) {
         Entity var3 = var1.iterator().next();
         if (var2.isEmpty()) {
            var0.a(IChatBaseComponent.a("commands.tag.list.single.empty", var3.G_()), false);
         } else {
            var0.a(IChatBaseComponent.a("commands.tag.list.single.success", var3.G_(), var2.size(), ChatComponentUtils.a(var2)), false);
         }
      } else if (var2.isEmpty()) {
         var0.a(IChatBaseComponent.a("commands.tag.list.multiple.empty", var1.size()), false);
      } else {
         var0.a(IChatBaseComponent.a("commands.tag.list.multiple.success", var1.size(), var2.size(), ChatComponentUtils.a(var2)), false);
      }

      return var2.size();
   }
}
