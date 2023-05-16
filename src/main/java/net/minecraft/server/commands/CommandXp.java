package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.player.EntityHuman;

public class CommandXp {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.experience.set.points.invalid"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      LiteralCommandNode<CommandListenerWrapper> var1 = var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                        "experience"
                     )
                     .requires(var0x -> var0x.c(2)))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("add")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.d())
                              .then(
                                 ((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                             "amount", IntegerArgumentType.integer()
                                          )
                                          .executes(
                                             var0x -> a(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   ArgumentEntity.f(var0x, "targets"),
                                                   IntegerArgumentType.getInteger(var0x, "amount"),
                                                   CommandXp.Unit.a
                                                )
                                          ))
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("points")
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      ArgumentEntity.f(var0x, "targets"),
                                                      IntegerArgumentType.getInteger(var0x, "amount"),
                                                      CommandXp.Unit.a
                                                   )
                                             )
                                       ))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("levels")
                                          .executes(
                                             var0x -> a(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   ArgumentEntity.f(var0x, "targets"),
                                                   IntegerArgumentType.getInteger(var0x, "amount"),
                                                   CommandXp.Unit.b
                                                )
                                          )
                                    )
                              )
                        )
                  ))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("set")
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.d())
                           .then(
                              ((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                          "amount", IntegerArgumentType.integer(0)
                                       )
                                       .executes(
                                          var0x -> b(
                                                (CommandListenerWrapper)var0x.getSource(),
                                                ArgumentEntity.f(var0x, "targets"),
                                                IntegerArgumentType.getInteger(var0x, "amount"),
                                                CommandXp.Unit.a
                                             )
                                       ))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("points")
                                          .executes(
                                             var0x -> b(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   ArgumentEntity.f(var0x, "targets"),
                                                   IntegerArgumentType.getInteger(var0x, "amount"),
                                                   CommandXp.Unit.a
                                                )
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("levels")
                                       .executes(
                                          var0x -> b(
                                                (CommandListenerWrapper)var0x.getSource(),
                                                ArgumentEntity.f(var0x, "targets"),
                                                IntegerArgumentType.getInteger(var0x, "amount"),
                                                CommandXp.Unit.b
                                             )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               net.minecraft.commands.CommandDispatcher.a("query")
                  .then(
                     ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.c())
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("points")
                                 .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentEntity.e(var0x, "targets"), CommandXp.Unit.a))
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("levels")
                              .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentEntity.e(var0x, "targets"), CommandXp.Unit.b))
                        )
                  )
            )
      );
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("xp")
               .requires(var0x -> var0x.c(2)))
            .redirect(var1)
      );
   }

   private static int a(CommandListenerWrapper var0, EntityPlayer var1, CommandXp.Unit var2) {
      int var3 = var2.f.applyAsInt(var1);
      var0.a(IChatBaseComponent.a("commands.experience.query." + var2.e, var1.G_(), var3), false);
      return var3;
   }

   private static int a(CommandListenerWrapper var0, Collection<? extends EntityPlayer> var1, int var2, CommandXp.Unit var3) {
      for(EntityPlayer var5 : var1) {
         var3.c.accept(var5, var2);
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.experience.add." + var3.e + ".success.single", var2, var1.iterator().next().G_()), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.experience.add." + var3.e + ".success.multiple", var2, var1.size()), true);
      }

      return var1.size();
   }

   private static int b(CommandListenerWrapper var0, Collection<? extends EntityPlayer> var1, int var2, CommandXp.Unit var3) throws CommandSyntaxException {
      int var4 = 0;

      for(EntityPlayer var6 : var1) {
         if (var3.d.test(var6, var2)) {
            ++var4;
         }
      }

      if (var4 == 0) {
         throw a.create();
      } else {
         if (var1.size() == 1) {
            var0.a(IChatBaseComponent.a("commands.experience.set." + var3.e + ".success.single", var2, var1.iterator().next().G_()), true);
         } else {
            var0.a(IChatBaseComponent.a("commands.experience.set." + var3.e + ".success.multiple", var2, var1.size()), true);
         }

         return var1.size();
      }
   }

   static enum Unit {
      a("points", EntityHuman::d, (var0, var1) -> {
         if (var1 >= var0.fS()) {
            return false;
         } else {
            var0.a(var1);
            return true;
         }
      }, var0 -> MathHelper.d(var0.ce * (float)var0.fS())),
      b("levels", EntityPlayer::c, (var0, var1) -> {
         var0.b(var1);
         return true;
      }, var0 -> var0.cc);

      public final BiConsumer<EntityPlayer, Integer> c;
      public final BiPredicate<EntityPlayer, Integer> d;
      public final String e;
      final ToIntFunction<EntityPlayer> f;

      private Unit(String var2, BiConsumer var3, BiPredicate var4, ToIntFunction var5) {
         this.c = var3;
         this.e = var2;
         this.d = var4;
         this.f = var5;
      }
   }
}
