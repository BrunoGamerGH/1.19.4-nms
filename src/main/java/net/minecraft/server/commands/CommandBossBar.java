package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.ArgumentChatComponent;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ArgumentMinecraftKeyRegistered;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.bossevents.BossBattleCustom;
import net.minecraft.server.bossevents.BossBattleCustomData;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.BossBattle;
import net.minecraft.world.entity.player.EntityHuman;

public class CommandBossBar {
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.bossbar.create.failed", var0));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.bossbar.unknown", var0));
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.bossbar.set.players.unchanged"));
   private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.bossbar.set.name.unchanged"));
   private static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.bossbar.set.color.unchanged"));
   private static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.bossbar.set.style.unchanged"));
   private static final SimpleCommandExceptionType h = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.bossbar.set.value.unchanged"));
   private static final SimpleCommandExceptionType i = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.bossbar.set.max.unchanged"));
   private static final SimpleCommandExceptionType j = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.bossbar.set.visibility.unchanged.hidden"));
   private static final SimpleCommandExceptionType k = new SimpleCommandExceptionType(
      IChatBaseComponent.c("commands.bossbar.set.visibility.unchanged.visible")
   );
   public static final SuggestionProvider<CommandListenerWrapper> a = (var0, var1) -> ICompletionProvider.a(
         ((CommandListenerWrapper)var0.getSource()).l().aL().a(), var1
      );

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                              "bossbar"
                           )
                           .requires(var0x -> var0x.c(2)))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("add")
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("id", ArgumentMinecraftKeyRegistered.a())
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("name", ArgumentChatComponent.a())
                                          .executes(
                                             var0x -> a(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   ArgumentMinecraftKeyRegistered.e(var0x, "id"),
                                                   ArgumentChatComponent.a(var0x, "name")
                                                )
                                          )
                                    )
                              )
                        ))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("remove")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("id", ArgumentMinecraftKeyRegistered.a())
                                 .suggests(a)
                                 .executes(var0x -> e((CommandListenerWrapper)var0x.getSource(), a(var0x)))
                           )
                     ))
                  .then(net.minecraft.commands.CommandDispatcher.a("list").executes(var0x -> a((CommandListenerWrapper)var0x.getSource()))))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("set")
                     .then(
                        ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                "id", ArgumentMinecraftKeyRegistered.a()
                                             )
                                             .suggests(a)
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("name")
                                                   .then(
                                                      net.minecraft.commands.CommandDispatcher.a("name", ArgumentChatComponent.a())
                                                         .executes(
                                                            var0x -> a(
                                                                  (CommandListenerWrapper)var0x.getSource(), a(var0x), ArgumentChatComponent.a(var0x, "name")
                                                               )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                                     "color"
                                                                  )
                                                                  .then(
                                                                     net.minecraft.commands.CommandDispatcher.a("pink")
                                                                        .executes(
                                                                           var0x -> a(
                                                                                 (CommandListenerWrapper)var0x.getSource(), a(var0x), BossBattle.BarColor.a
                                                                              )
                                                                        )
                                                                  ))
                                                               .then(
                                                                  net.minecraft.commands.CommandDispatcher.a("blue")
                                                                     .executes(
                                                                        var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x), BossBattle.BarColor.b)
                                                                     )
                                                               ))
                                                            .then(
                                                               net.minecraft.commands.CommandDispatcher.a("red")
                                                                  .executes(
                                                                     var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x), BossBattle.BarColor.c)
                                                                  )
                                                            ))
                                                         .then(
                                                            net.minecraft.commands.CommandDispatcher.a("green")
                                                               .executes(
                                                                  var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x), BossBattle.BarColor.d)
                                                               )
                                                         ))
                                                      .then(
                                                         net.minecraft.commands.CommandDispatcher.a("yellow")
                                                            .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x), BossBattle.BarColor.e))
                                                      ))
                                                   .then(
                                                      net.minecraft.commands.CommandDispatcher.a("purple")
                                                         .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x), BossBattle.BarColor.f))
                                                   ))
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("white")
                                                      .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x), BossBattle.BarColor.g))
                                                )
                                          ))
                                       .then(
                                          ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                            "style"
                                                         )
                                                         .then(
                                                            net.minecraft.commands.CommandDispatcher.a("progress")
                                                               .executes(
                                                                  var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x), BossBattle.BarStyle.a)
                                                               )
                                                         ))
                                                      .then(
                                                         net.minecraft.commands.CommandDispatcher.a("notched_6")
                                                            .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x), BossBattle.BarStyle.b))
                                                      ))
                                                   .then(
                                                      net.minecraft.commands.CommandDispatcher.a("notched_10")
                                                         .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x), BossBattle.BarStyle.c))
                                                   ))
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("notched_12")
                                                      .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x), BossBattle.BarStyle.d))
                                                ))
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("notched_20")
                                                   .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x), BossBattle.BarStyle.e))
                                             )
                                       ))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("value")
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("value", IntegerArgumentType.integer(0))
                                                .executes(
                                                   var0x -> a(
                                                         (CommandListenerWrapper)var0x.getSource(), a(var0x), IntegerArgumentType.getInteger(var0x, "value")
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("max")
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("max", IntegerArgumentType.integer(1))
                                             .executes(
                                                var0x -> b((CommandListenerWrapper)var0x.getSource(), a(var0x), IntegerArgumentType.getInteger(var0x, "max"))
                                             )
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("visible")
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("visible", BoolArgumentType.bool())
                                          .executes(
                                             var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x), BoolArgumentType.getBool(var0x, "visible"))
                                          )
                                    )
                              ))
                           .then(
                              ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("players")
                                    .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x), Collections.emptyList())))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.d())
                                       .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x), ArgumentEntity.d(var0x, "targets")))
                                 )
                           )
                     )
               ))
            .then(
               net.minecraft.commands.CommandDispatcher.a("get")
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                    "id", ArgumentMinecraftKeyRegistered.a()
                                 )
                                 .suggests(a)
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("value")
                                       .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x)))
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("max").executes(var0x -> b((CommandListenerWrapper)var0x.getSource(), a(var0x)))
                              ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("visible").executes(var0x -> c((CommandListenerWrapper)var0x.getSource(), a(var0x)))
                           ))
                        .then(net.minecraft.commands.CommandDispatcher.a("players").executes(var0x -> d((CommandListenerWrapper)var0x.getSource(), a(var0x))))
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, BossBattleCustom var1) {
      var0.a(IChatBaseComponent.a("commands.bossbar.get.value", var1.e(), var1.c()), true);
      return var1.c();
   }

   private static int b(CommandListenerWrapper var0, BossBattleCustom var1) {
      var0.a(IChatBaseComponent.a("commands.bossbar.get.max", var1.e(), var1.d()), true);
      return var1.d();
   }

   private static int c(CommandListenerWrapper var0, BossBattleCustom var1) {
      if (var1.g()) {
         var0.a(IChatBaseComponent.a("commands.bossbar.get.visible.visible", var1.e()), true);
         return 1;
      } else {
         var0.a(IChatBaseComponent.a("commands.bossbar.get.visible.hidden", var1.e()), true);
         return 0;
      }
   }

   private static int d(CommandListenerWrapper var0, BossBattleCustom var1) {
      if (var1.h().isEmpty()) {
         var0.a(IChatBaseComponent.a("commands.bossbar.get.players.none", var1.e()), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.bossbar.get.players.some", var1.e(), var1.h().size(), ChatComponentUtils.b(var1.h(), EntityHuman::G_)), true);
      }

      return var1.h().size();
   }

   private static int a(CommandListenerWrapper var0, BossBattleCustom var1, boolean var2) throws CommandSyntaxException {
      if (var1.g() == var2) {
         if (var2) {
            throw k.create();
         } else {
            throw j.create();
         }
      } else {
         var1.d(var2);
         if (var2) {
            var0.a(IChatBaseComponent.a("commands.bossbar.set.visible.success.visible", var1.e()), true);
         } else {
            var0.a(IChatBaseComponent.a("commands.bossbar.set.visible.success.hidden", var1.e()), true);
         }

         return 0;
      }
   }

   private static int a(CommandListenerWrapper var0, BossBattleCustom var1, int var2) throws CommandSyntaxException {
      if (var1.c() == var2) {
         throw h.create();
      } else {
         var1.a(var2);
         var0.a(IChatBaseComponent.a("commands.bossbar.set.value.success", var1.e(), var2), true);
         return var2;
      }
   }

   private static int b(CommandListenerWrapper var0, BossBattleCustom var1, int var2) throws CommandSyntaxException {
      if (var1.d() == var2) {
         throw i.create();
      } else {
         var1.b(var2);
         var0.a(IChatBaseComponent.a("commands.bossbar.set.max.success", var1.e(), var2), true);
         return var2;
      }
   }

   private static int a(CommandListenerWrapper var0, BossBattleCustom var1, BossBattle.BarColor var2) throws CommandSyntaxException {
      if (var1.l().equals(var2)) {
         throw f.create();
      } else {
         var1.a(var2);
         var0.a(IChatBaseComponent.a("commands.bossbar.set.color.success", var1.e()), true);
         return 0;
      }
   }

   private static int a(CommandListenerWrapper var0, BossBattleCustom var1, BossBattle.BarStyle var2) throws CommandSyntaxException {
      if (var1.m().equals(var2)) {
         throw g.create();
      } else {
         var1.a(var2);
         var0.a(IChatBaseComponent.a("commands.bossbar.set.style.success", var1.e()), true);
         return 0;
      }
   }

   private static int a(CommandListenerWrapper var0, BossBattleCustom var1, IChatBaseComponent var2) throws CommandSyntaxException {
      IChatBaseComponent var3 = ChatComponentUtils.a(var0, var2, null, 0);
      if (var1.j().equals(var3)) {
         throw e.create();
      } else {
         var1.a(var3);
         var0.a(IChatBaseComponent.a("commands.bossbar.set.name.success", var1.e()), true);
         return 0;
      }
   }

   private static int a(CommandListenerWrapper var0, BossBattleCustom var1, Collection<EntityPlayer> var2) throws CommandSyntaxException {
      boolean var3 = var1.a(var2);
      if (!var3) {
         throw d.create();
      } else {
         if (var1.h().isEmpty()) {
            var0.a(IChatBaseComponent.a("commands.bossbar.set.players.success.none", var1.e()), true);
         } else {
            var0.a(IChatBaseComponent.a("commands.bossbar.set.players.success.some", var1.e(), var2.size(), ChatComponentUtils.b(var2, EntityHuman::G_)), true);
         }

         return var1.h().size();
      }
   }

   private static int a(CommandListenerWrapper var0) {
      Collection<BossBattleCustom> var1 = var0.l().aL().b();
      if (var1.isEmpty()) {
         var0.a(IChatBaseComponent.c("commands.bossbar.list.bars.none"), false);
      } else {
         var0.a(IChatBaseComponent.a("commands.bossbar.list.bars.some", var1.size(), ChatComponentUtils.b(var1, BossBattleCustom::e)), false);
      }

      return var1.size();
   }

   private static int a(CommandListenerWrapper var0, MinecraftKey var1, IChatBaseComponent var2) throws CommandSyntaxException {
      BossBattleCustomData var3 = var0.l().aL();
      if (var3.a(var1) != null) {
         throw b.create(var1.toString());
      } else {
         BossBattleCustom var4 = var3.a(var1, ChatComponentUtils.a(var0, var2, null, 0));
         var0.a(IChatBaseComponent.a("commands.bossbar.create.success", var4.e()), true);
         return var3.b().size();
      }
   }

   private static int e(CommandListenerWrapper var0, BossBattleCustom var1) {
      BossBattleCustomData var2 = var0.l().aL();
      var1.b();
      var2.a(var1);
      var0.a(IChatBaseComponent.a("commands.bossbar.remove.success", var1.e()), true);
      return var2.b().size();
   }

   public static BossBattleCustom a(CommandContext<CommandListenerWrapper> var0) throws CommandSyntaxException {
      MinecraftKey var1 = ArgumentMinecraftKeyRegistered.e(var0, "id");
      BossBattleCustom var2 = ((CommandListenerWrapper)var0.getSource()).l().aL().a(var1);
      if (var2 == null) {
         throw c.create(var1.toString());
      } else {
         return var2;
      }
   }
}
