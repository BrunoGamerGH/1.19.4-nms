package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.ArgumentChatComponent;
import net.minecraft.commands.arguments.ArgumentMathOperation;
import net.minecraft.commands.arguments.ArgumentScoreboardCriteria;
import net.minecraft.commands.arguments.ArgumentScoreboardObjective;
import net.minecraft.commands.arguments.ArgumentScoreboardSlot;
import net.minecraft.commands.arguments.ArgumentScoreholder;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.ScoreboardScore;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;

public class CommandScoreboard {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.scoreboard.objectives.add.duplicate"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(
      IChatBaseComponent.c("commands.scoreboard.objectives.display.alreadyEmpty")
   );
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(
      IChatBaseComponent.c("commands.scoreboard.objectives.display.alreadySet")
   );
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.scoreboard.players.enable.failed"));
   private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.scoreboard.players.enable.invalid"));
   private static final Dynamic2CommandExceptionType f = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("commands.scoreboard.players.get.null", var0, var1)
   );

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                     "scoreboard"
                  )
                  .requires(var0x -> var0x.c(2)))
               .then(
                  ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                    "objectives"
                                 )
                                 .then(net.minecraft.commands.CommandDispatcher.a("list").executes(var0x -> b((CommandListenerWrapper)var0x.getSource()))))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("add")
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("objective", StringArgumentType.word())
                                          .then(
                                             ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("criteria", ArgumentScoreboardCriteria.a())
                                                   .executes(
                                                      var0x -> a(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            StringArgumentType.getString(var0x, "objective"),
                                                            ArgumentScoreboardCriteria.a(var0x, "criteria"),
                                                            IChatBaseComponent.b(StringArgumentType.getString(var0x, "objective"))
                                                         )
                                                   ))
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("displayName", ArgumentChatComponent.a())
                                                      .executes(
                                                         var0x -> a(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               StringArgumentType.getString(var0x, "objective"),
                                                               ArgumentScoreboardCriteria.a(var0x, "criteria"),
                                                               ArgumentChatComponent.a(var0x, "displayName")
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("modify")
                                 .then(
                                    ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("objective", ArgumentScoreboardObjective.a())
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("displayname")
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("displayName", ArgumentChatComponent.a())
                                                      .executes(
                                                         var0x -> a(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               ArgumentScoreboardObjective.a(var0x, "objective"),
                                                               ArgumentChatComponent.a(var0x, "displayName")
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(a())
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("remove")
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("objective", ArgumentScoreboardObjective.a())
                                    .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentScoreboardObjective.a(var0x, "objective")))
                              )
                        ))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("setdisplay")
                           .then(
                              ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("slot", ArgumentScoreboardSlot.a())
                                    .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentScoreboardSlot.a(var0x, "slot"))))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("objective", ArgumentScoreboardObjective.a())
                                       .executes(
                                          var0x -> a(
                                                (CommandListenerWrapper)var0x.getSource(),
                                                ArgumentScoreboardSlot.a(var0x, "slot"),
                                                ArgumentScoreboardObjective.a(var0x, "objective")
                                             )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                          "players"
                                       )
                                       .then(
                                          ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("list")
                                                .executes(var0x -> a((CommandListenerWrapper)var0x.getSource())))
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("target", ArgumentScoreholder.a())
                                                   .suggests(ArgumentScoreholder.a)
                                                   .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentScoreholder.a(var0x, "target")))
                                             )
                                       ))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("set")
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("targets", ArgumentScoreholder.b())
                                                .suggests(ArgumentScoreholder.a)
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("objective", ArgumentScoreboardObjective.a())
                                                      .then(
                                                         net.minecraft.commands.CommandDispatcher.a("score", IntegerArgumentType.integer())
                                                            .executes(
                                                               var0x -> a(
                                                                     (CommandListenerWrapper)var0x.getSource(),
                                                                     ArgumentScoreholder.c(var0x, "targets"),
                                                                     ArgumentScoreboardObjective.b(var0x, "objective"),
                                                                     IntegerArgumentType.getInteger(var0x, "score")
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("get")
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("target", ArgumentScoreholder.a())
                                             .suggests(ArgumentScoreholder.a)
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("objective", ArgumentScoreboardObjective.a())
                                                   .executes(
                                                      var0x -> a(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            ArgumentScoreholder.a(var0x, "target"),
                                                            ArgumentScoreboardObjective.a(var0x, "objective")
                                                         )
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("add")
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("targets", ArgumentScoreholder.b())
                                          .suggests(ArgumentScoreholder.a)
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("objective", ArgumentScoreboardObjective.a())
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("score", IntegerArgumentType.integer(0))
                                                      .executes(
                                                         var0x -> b(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               ArgumentScoreholder.c(var0x, "targets"),
                                                               ArgumentScoreboardObjective.b(var0x, "objective"),
                                                               IntegerArgumentType.getInteger(var0x, "score")
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("remove")
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("targets", ArgumentScoreholder.b())
                                       .suggests(ArgumentScoreholder.a)
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("objective", ArgumentScoreboardObjective.a())
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("score", IntegerArgumentType.integer(0))
                                                   .executes(
                                                      var0x -> c(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            ArgumentScoreholder.c(var0x, "targets"),
                                                            ArgumentScoreboardObjective.b(var0x, "objective"),
                                                            IntegerArgumentType.getInteger(var0x, "score")
                                                         )
                                                   )
                                             )
                                       )
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("reset")
                              .then(
                                 ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("targets", ArgumentScoreholder.b())
                                       .suggests(ArgumentScoreholder.a)
                                       .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentScoreholder.c(var0x, "targets"))))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("objective", ArgumentScoreboardObjective.a())
                                          .executes(
                                             var0x -> b(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   ArgumentScoreholder.c(var0x, "targets"),
                                                   ArgumentScoreboardObjective.a(var0x, "objective")
                                                )
                                          )
                                    )
                              )
                        ))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("enable")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("targets", ArgumentScoreholder.b())
                                 .suggests(ArgumentScoreholder.a)
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("objective", ArgumentScoreboardObjective.a())
                                       .suggests((var0x, var1) -> a((CommandListenerWrapper)var0x.getSource(), ArgumentScoreholder.c(var0x, "targets"), var1))
                                       .executes(
                                          var0x -> a(
                                                (CommandListenerWrapper)var0x.getSource(),
                                                ArgumentScoreholder.c(var0x, "targets"),
                                                ArgumentScoreboardObjective.a(var0x, "objective")
                                             )
                                       )
                                 )
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("operation")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("targets", ArgumentScoreholder.b())
                              .suggests(ArgumentScoreholder.a)
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("targetObjective", ArgumentScoreboardObjective.a())
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("operation", ArgumentMathOperation.a())
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("source", ArgumentScoreholder.b())
                                                .suggests(ArgumentScoreholder.a)
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("sourceObjective", ArgumentScoreboardObjective.a())
                                                      .executes(
                                                         var0x -> a(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               ArgumentScoreholder.c(var0x, "targets"),
                                                               ArgumentScoreboardObjective.b(var0x, "targetObjective"),
                                                               ArgumentMathOperation.a(var0x, "operation"),
                                                               ArgumentScoreholder.c(var0x, "source"),
                                                               ArgumentScoreboardObjective.a(var0x, "sourceObjective")
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

   private static LiteralArgumentBuilder<CommandListenerWrapper> a() {
      LiteralArgumentBuilder<CommandListenerWrapper> var0 = net.minecraft.commands.CommandDispatcher.a("rendertype");

      for(IScoreboardCriteria.EnumScoreboardHealthDisplay var4 : IScoreboardCriteria.EnumScoreboardHealthDisplay.values()) {
         var0.then(
            net.minecraft.commands.CommandDispatcher.a(var4.a())
               .executes(var1 -> a((CommandListenerWrapper)var1.getSource(), ArgumentScoreboardObjective.a(var1, "objective"), var4))
         );
      }

      return var0;
   }

   private static CompletableFuture<Suggestions> a(CommandListenerWrapper var0, Collection<String> var1, SuggestionsBuilder var2) {
      List<String> var3 = Lists.newArrayList();
      Scoreboard var4 = var0.l().aF();

      for(ScoreboardObjective var6 : var4.c()) {
         if (var6.c() == IScoreboardCriteria.b) {
            boolean var7 = false;

            for(String var9 : var1) {
               if (!var4.b(var9, var6) || var4.c(var9, var6).g()) {
                  var7 = true;
                  break;
               }
            }

            if (var7) {
               var3.add(var6.b());
            }
         }
      }

      return ICompletionProvider.b(var3, var2);
   }

   private static int a(CommandListenerWrapper var0, String var1, ScoreboardObjective var2) throws CommandSyntaxException {
      Scoreboard var3 = var0.l().aF();
      if (!var3.b(var1, var2)) {
         throw f.create(var2.b(), var1);
      } else {
         ScoreboardScore var4 = var3.c(var1, var2);
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.get.success", var1, var4.b(), var2.e()), false);
         return var4.b();
      }
   }

   private static int a(
      CommandListenerWrapper var0,
      Collection<String> var1,
      ScoreboardObjective var2,
      ArgumentMathOperation.a var3,
      Collection<String> var4,
      ScoreboardObjective var5
   ) throws CommandSyntaxException {
      Scoreboard var6 = var0.l().aF();
      int var7 = 0;

      for(String var9 : var1) {
         ScoreboardScore var10 = var6.c(var9, var2);

         for(String var12 : var4) {
            ScoreboardScore var13 = var6.c(var12, var5);
            var3.apply(var10, var13);
         }

         var7 += var10.b();
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.operation.success.single", var2.e(), var1.iterator().next(), var7), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.operation.success.multiple", var2.e(), var1.size()), true);
      }

      return var7;
   }

   private static int a(CommandListenerWrapper var0, Collection<String> var1, ScoreboardObjective var2) throws CommandSyntaxException {
      if (var2.c() != IScoreboardCriteria.b) {
         throw e.create();
      } else {
         Scoreboard var3 = var0.l().aF();
         int var4 = 0;

         for(String var6 : var1) {
            ScoreboardScore var7 = var3.c(var6, var2);
            if (var7.g()) {
               var7.a(false);
               ++var4;
            }
         }

         if (var4 == 0) {
            throw d.create();
         } else {
            if (var1.size() == 1) {
               var0.a(IChatBaseComponent.a("commands.scoreboard.players.enable.success.single", var2.e(), var1.iterator().next()), true);
            } else {
               var0.a(IChatBaseComponent.a("commands.scoreboard.players.enable.success.multiple", var2.e(), var1.size()), true);
            }

            return var4;
         }
      }
   }

   private static int a(CommandListenerWrapper var0, Collection<String> var1) {
      Scoreboard var2 = var0.l().aF();

      for(String var4 : var1) {
         var2.d(var4, null);
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.reset.all.single", var1.iterator().next()), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.reset.all.multiple", var1.size()), true);
      }

      return var1.size();
   }

   private static int b(CommandListenerWrapper var0, Collection<String> var1, ScoreboardObjective var2) {
      Scoreboard var3 = var0.l().aF();

      for(String var5 : var1) {
         var3.d(var5, var2);
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.reset.specific.single", var2.e(), var1.iterator().next()), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.reset.specific.multiple", var2.e(), var1.size()), true);
      }

      return var1.size();
   }

   private static int a(CommandListenerWrapper var0, Collection<String> var1, ScoreboardObjective var2, int var3) {
      Scoreboard var4 = var0.l().aF();

      for(String var6 : var1) {
         ScoreboardScore var7 = var4.c(var6, var2);
         var7.b(var3);
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.set.success.single", var2.e(), var1.iterator().next(), var3), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.set.success.multiple", var2.e(), var1.size(), var3), true);
      }

      return var3 * var1.size();
   }

   private static int b(CommandListenerWrapper var0, Collection<String> var1, ScoreboardObjective var2, int var3) {
      Scoreboard var4 = var0.l().aF();
      int var5 = 0;

      for(String var7 : var1) {
         ScoreboardScore var8 = var4.c(var7, var2);
         var8.b(var8.b() + var3);
         var5 += var8.b();
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.add.success.single", var3, var2.e(), var1.iterator().next(), var5), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.add.success.multiple", var3, var2.e(), var1.size()), true);
      }

      return var5;
   }

   private static int c(CommandListenerWrapper var0, Collection<String> var1, ScoreboardObjective var2, int var3) {
      Scoreboard var4 = var0.l().aF();
      int var5 = 0;

      for(String var7 : var1) {
         ScoreboardScore var8 = var4.c(var7, var2);
         var8.b(var8.b() - var3);
         var5 += var8.b();
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.remove.success.single", var3, var2.e(), var1.iterator().next(), var5), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.remove.success.multiple", var3, var2.e(), var1.size()), true);
      }

      return var5;
   }

   private static int a(CommandListenerWrapper var0) {
      Collection<String> var1 = var0.l().aF().e();
      if (var1.isEmpty()) {
         var0.a(IChatBaseComponent.c("commands.scoreboard.players.list.empty"), false);
      } else {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.list.success", var1.size(), ChatComponentUtils.a(var1)), false);
      }

      return var1.size();
   }

   private static int a(CommandListenerWrapper var0, String var1) {
      Map<ScoreboardObjective, ScoreboardScore> var2 = var0.l().aF().e(var1);
      if (var2.isEmpty()) {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.list.entity.empty", var1), false);
      } else {
         var0.a(IChatBaseComponent.a("commands.scoreboard.players.list.entity.success", var1, var2.size()), false);

         for(Entry<ScoreboardObjective, ScoreboardScore> var4 : var2.entrySet()) {
            var0.a(IChatBaseComponent.a("commands.scoreboard.players.list.entity.entry", var4.getKey().e(), var4.getValue().b()), false);
         }
      }

      return var2.size();
   }

   private static int a(CommandListenerWrapper var0, int var1) throws CommandSyntaxException {
      Scoreboard var2 = var0.l().aF();
      if (var2.a(var1) == null) {
         throw b.create();
      } else {
         var2.a(var1, null);
         var0.a(IChatBaseComponent.a("commands.scoreboard.objectives.display.cleared", Scoreboard.h()[var1]), true);
         return 0;
      }
   }

   private static int a(CommandListenerWrapper var0, int var1, ScoreboardObjective var2) throws CommandSyntaxException {
      Scoreboard var3 = var0.l().aF();
      if (var3.a(var1) == var2) {
         throw c.create();
      } else {
         var3.a(var1, var2);
         var0.a(IChatBaseComponent.a("commands.scoreboard.objectives.display.set", Scoreboard.h()[var1], var2.d()), true);
         return 0;
      }
   }

   private static int a(CommandListenerWrapper var0, ScoreboardObjective var1, IChatBaseComponent var2) {
      if (!var1.d().equals(var2)) {
         var1.a(var2);
         var0.a(IChatBaseComponent.a("commands.scoreboard.objectives.modify.displayname", var1.b(), var1.e()), true);
      }

      return 0;
   }

   private static int a(CommandListenerWrapper var0, ScoreboardObjective var1, IScoreboardCriteria.EnumScoreboardHealthDisplay var2) {
      if (var1.f() != var2) {
         var1.a(var2);
         var0.a(IChatBaseComponent.a("commands.scoreboard.objectives.modify.rendertype", var1.e()), true);
      }

      return 0;
   }

   private static int a(CommandListenerWrapper var0, ScoreboardObjective var1) {
      Scoreboard var2 = var0.l().aF();
      var2.j(var1);
      var0.a(IChatBaseComponent.a("commands.scoreboard.objectives.remove.success", var1.e()), true);
      return var2.c().size();
   }

   private static int a(CommandListenerWrapper var0, String var1, IScoreboardCriteria var2, IChatBaseComponent var3) throws CommandSyntaxException {
      Scoreboard var4 = var0.l().aF();
      if (var4.d(var1) != null) {
         throw a.create();
      } else {
         var4.a(var1, var2, var3, var2.f());
         ScoreboardObjective var5 = var4.d(var1);
         var0.a(IChatBaseComponent.a("commands.scoreboard.objectives.add.success", var5.e()), true);
         return var4.c().size();
      }
   }

   private static int b(CommandListenerWrapper var0) {
      Collection<ScoreboardObjective> var1 = var0.l().aF().c();
      if (var1.isEmpty()) {
         var0.a(IChatBaseComponent.c("commands.scoreboard.objectives.list.empty"), false);
      } else {
         var0.a(IChatBaseComponent.a("commands.scoreboard.objectives.list.success", var1.size(), ChatComponentUtils.b(var1, ScoreboardObjective::e)), false);
      }

      return var1.size();
   }
}
