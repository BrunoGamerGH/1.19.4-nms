package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.EnumChatFormat;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentChatComponent;
import net.minecraft.commands.arguments.ArgumentChatFormat;
import net.minecraft.commands.arguments.ArgumentScoreboardTeam;
import net.minecraft.commands.arguments.ArgumentScoreholder;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardTeam;
import net.minecraft.world.scores.ScoreboardTeamBase;

public class CommandTeam {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.team.add.duplicate"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.team.empty.unchanged"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.team.option.name.unchanged"));
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.team.option.color.unchanged"));
   private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.team.option.friendlyfire.alreadyEnabled"));
   private static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(
      IChatBaseComponent.c("commands.team.option.friendlyfire.alreadyDisabled")
   );
   private static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(
      IChatBaseComponent.c("commands.team.option.seeFriendlyInvisibles.alreadyEnabled")
   );
   private static final SimpleCommandExceptionType h = new SimpleCommandExceptionType(
      IChatBaseComponent.c("commands.team.option.seeFriendlyInvisibles.alreadyDisabled")
   );
   private static final SimpleCommandExceptionType i = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.team.option.nametagVisibility.unchanged"));
   private static final SimpleCommandExceptionType j = new SimpleCommandExceptionType(
      IChatBaseComponent.c("commands.team.option.deathMessageVisibility.unchanged")
   );
   private static final SimpleCommandExceptionType k = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.team.option.collisionRule.unchanged"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                    "team"
                                 )
                                 .requires(var0x -> var0x.c(2)))
                              .then(
                                 ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("list")
                                       .executes(var0x -> a((CommandListenerWrapper)var0x.getSource())))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("team", ArgumentScoreboardTeam.a())
                                          .executes(var0x -> c((CommandListenerWrapper)var0x.getSource(), ArgumentScoreboardTeam.a(var0x, "team")))
                                    )
                              ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("add")
                                 .then(
                                    ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("team", StringArgumentType.word())
                                          .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), StringArgumentType.getString(var0x, "team"))))
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("displayName", ArgumentChatComponent.a())
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      StringArgumentType.getString(var0x, "team"),
                                                      ArgumentChatComponent.a(var0x, "displayName")
                                                   )
                                             )
                                       )
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("remove")
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("team", ArgumentScoreboardTeam.a())
                                    .executes(var0x -> b((CommandListenerWrapper)var0x.getSource(), ArgumentScoreboardTeam.a(var0x, "team")))
                              )
                        ))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("empty")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("team", ArgumentScoreboardTeam.a())
                                 .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentScoreboardTeam.a(var0x, "team")))
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("join")
                        .then(
                           ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("team", ArgumentScoreboardTeam.a())
                                 .executes(
                                    var0x -> a(
                                          (CommandListenerWrapper)var0x.getSource(),
                                          ArgumentScoreboardTeam.a(var0x, "team"),
                                          Collections.singleton(((CommandListenerWrapper)var0x.getSource()).g().cu())
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("members", ArgumentScoreholder.b())
                                    .suggests(ArgumentScoreholder.a)
                                    .executes(
                                       var0x -> a(
                                             (CommandListenerWrapper)var0x.getSource(),
                                             ArgumentScoreboardTeam.a(var0x, "team"),
                                             ArgumentScoreholder.c(var0x, "members")
                                          )
                                    )
                              )
                        )
                  ))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("leave")
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("members", ArgumentScoreholder.b())
                           .suggests(ArgumentScoreholder.a)
                           .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentScoreholder.c(var0x, "members")))
                     )
               ))
            .then(
               net.minecraft.commands.CommandDispatcher.a("modify")
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                   "team", ArgumentScoreboardTeam.a()
                                                )
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("displayName")
                                                      .then(
                                                         net.minecraft.commands.CommandDispatcher.a("displayName", ArgumentChatComponent.a())
                                                            .executes(
                                                               var0x -> a(
                                                                     (CommandListenerWrapper)var0x.getSource(),
                                                                     ArgumentScoreboardTeam.a(var0x, "team"),
                                                                     ArgumentChatComponent.a(var0x, "displayName")
                                                                  )
                                                            )
                                                      )
                                                ))
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("color")
                                                   .then(
                                                      net.minecraft.commands.CommandDispatcher.a("value", ArgumentChatFormat.a())
                                                         .executes(
                                                            var0x -> a(
                                                                  (CommandListenerWrapper)var0x.getSource(),
                                                                  ArgumentScoreboardTeam.a(var0x, "team"),
                                                                  ArgumentChatFormat.a(var0x, "value")
                                                               )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("friendlyFire")
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("allowed", BoolArgumentType.bool())
                                                      .executes(
                                                         var0x -> b(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               ArgumentScoreboardTeam.a(var0x, "team"),
                                                               BoolArgumentType.getBool(var0x, "allowed")
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("seeFriendlyInvisibles")
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("allowed", BoolArgumentType.bool())
                                                   .executes(
                                                      var0x -> a(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            ArgumentScoreboardTeam.a(var0x, "team"),
                                                            BoolArgumentType.getBool(var0x, "allowed")
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                      "nametagVisibility"
                                                   )
                                                   .then(
                                                      net.minecraft.commands.CommandDispatcher.a("never")
                                                         .executes(
                                                            var0x -> a(
                                                                  (CommandListenerWrapper)var0x.getSource(),
                                                                  ArgumentScoreboardTeam.a(var0x, "team"),
                                                                  ScoreboardTeamBase.EnumNameTagVisibility.b
                                                               )
                                                         )
                                                   ))
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("hideForOtherTeams")
                                                      .executes(
                                                         var0x -> a(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               ArgumentScoreboardTeam.a(var0x, "team"),
                                                               ScoreboardTeamBase.EnumNameTagVisibility.c
                                                            )
                                                      )
                                                ))
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("hideForOwnTeam")
                                                   .executes(
                                                      var0x -> a(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            ArgumentScoreboardTeam.a(var0x, "team"),
                                                            ScoreboardTeamBase.EnumNameTagVisibility.d
                                                         )
                                                   )
                                             ))
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("always")
                                                .executes(
                                                   var0x -> a(
                                                         (CommandListenerWrapper)var0x.getSource(),
                                                         ArgumentScoreboardTeam.a(var0x, "team"),
                                                         ScoreboardTeamBase.EnumNameTagVisibility.a
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                   "deathMessageVisibility"
                                                )
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("never")
                                                      .executes(
                                                         var0x -> b(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               ArgumentScoreboardTeam.a(var0x, "team"),
                                                               ScoreboardTeamBase.EnumNameTagVisibility.b
                                                            )
                                                      )
                                                ))
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("hideForOtherTeams")
                                                   .executes(
                                                      var0x -> b(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            ArgumentScoreboardTeam.a(var0x, "team"),
                                                            ScoreboardTeamBase.EnumNameTagVisibility.c
                                                         )
                                                   )
                                             ))
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("hideForOwnTeam")
                                                .executes(
                                                   var0x -> b(
                                                         (CommandListenerWrapper)var0x.getSource(),
                                                         ArgumentScoreboardTeam.a(var0x, "team"),
                                                         ScoreboardTeamBase.EnumNameTagVisibility.d
                                                      )
                                                )
                                          ))
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("always")
                                             .executes(
                                                var0x -> b(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      ArgumentScoreboardTeam.a(var0x, "team"),
                                                      ScoreboardTeamBase.EnumNameTagVisibility.a
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                "collisionRule"
                                             )
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("never")
                                                   .executes(
                                                      var0x -> a(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            ArgumentScoreboardTeam.a(var0x, "team"),
                                                            ScoreboardTeamBase.EnumTeamPush.b
                                                         )
                                                   )
                                             ))
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("pushOwnTeam")
                                                .executes(
                                                   var0x -> a(
                                                         (CommandListenerWrapper)var0x.getSource(),
                                                         ArgumentScoreboardTeam.a(var0x, "team"),
                                                         ScoreboardTeamBase.EnumTeamPush.d
                                                      )
                                                )
                                          ))
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("pushOtherTeams")
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      ArgumentScoreboardTeam.a(var0x, "team"),
                                                      ScoreboardTeamBase.EnumTeamPush.c
                                                   )
                                             )
                                       ))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("always")
                                          .executes(
                                             var0x -> a(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   ArgumentScoreboardTeam.a(var0x, "team"),
                                                   ScoreboardTeamBase.EnumTeamPush.a
                                                )
                                          )
                                    )
                              ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("prefix")
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("prefix", ArgumentChatComponent.a())
                                       .executes(
                                          var0x -> b(
                                                (CommandListenerWrapper)var0x.getSource(),
                                                ArgumentScoreboardTeam.a(var0x, "team"),
                                                ArgumentChatComponent.a(var0x, "prefix")
                                             )
                                       )
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("suffix")
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("suffix", ArgumentChatComponent.a())
                                    .executes(
                                       var0x -> c(
                                             (CommandListenerWrapper)var0x.getSource(),
                                             ArgumentScoreboardTeam.a(var0x, "team"),
                                             ArgumentChatComponent.a(var0x, "suffix")
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Collection<String> var1) {
      Scoreboard var2 = var0.l().aF();

      for(String var4 : var1) {
         var2.h(var4);
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.team.leave.success.single", var1.iterator().next()), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.team.leave.success.multiple", var1.size()), true);
      }

      return var1.size();
   }

   private static int a(CommandListenerWrapper var0, ScoreboardTeam var1, Collection<String> var2) {
      Scoreboard var3 = var0.l().aF();

      for(String var5 : var2) {
         var3.a(var5, var1);
      }

      if (var2.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.team.join.success.single", var2.iterator().next(), var1.d()), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.team.join.success.multiple", var2.size(), var1.d()), true);
      }

      return var2.size();
   }

   private static int a(CommandListenerWrapper var0, ScoreboardTeam var1, ScoreboardTeamBase.EnumNameTagVisibility var2) throws CommandSyntaxException {
      if (var1.j() == var2) {
         throw i.create();
      } else {
         var1.a(var2);
         var0.a(IChatBaseComponent.a("commands.team.option.nametagVisibility.success", var1.d(), var2.b()), true);
         return 0;
      }
   }

   private static int b(CommandListenerWrapper var0, ScoreboardTeam var1, ScoreboardTeamBase.EnumNameTagVisibility var2) throws CommandSyntaxException {
      if (var1.k() == var2) {
         throw j.create();
      } else {
         var1.b(var2);
         var0.a(IChatBaseComponent.a("commands.team.option.deathMessageVisibility.success", var1.d(), var2.b()), true);
         return 0;
      }
   }

   private static int a(CommandListenerWrapper var0, ScoreboardTeam var1, ScoreboardTeamBase.EnumTeamPush var2) throws CommandSyntaxException {
      if (var1.l() == var2) {
         throw k.create();
      } else {
         var1.a(var2);
         var0.a(IChatBaseComponent.a("commands.team.option.collisionRule.success", var1.d(), var2.a()), true);
         return 0;
      }
   }

   private static int a(CommandListenerWrapper var0, ScoreboardTeam var1, boolean var2) throws CommandSyntaxException {
      if (var1.i() == var2) {
         if (var2) {
            throw g.create();
         } else {
            throw h.create();
         }
      } else {
         var1.b(var2);
         var0.a(IChatBaseComponent.a("commands.team.option.seeFriendlyInvisibles." + (var2 ? "enabled" : "disabled"), var1.d()), true);
         return 0;
      }
   }

   private static int b(CommandListenerWrapper var0, ScoreboardTeam var1, boolean var2) throws CommandSyntaxException {
      if (var1.h() == var2) {
         if (var2) {
            throw e.create();
         } else {
            throw f.create();
         }
      } else {
         var1.a(var2);
         var0.a(IChatBaseComponent.a("commands.team.option.friendlyfire." + (var2 ? "enabled" : "disabled"), var1.d()), true);
         return 0;
      }
   }

   private static int a(CommandListenerWrapper var0, ScoreboardTeam var1, IChatBaseComponent var2) throws CommandSyntaxException {
      if (var1.c().equals(var2)) {
         throw c.create();
      } else {
         var1.a(var2);
         var0.a(IChatBaseComponent.a("commands.team.option.name.success", var1.d()), true);
         return 0;
      }
   }

   private static int a(CommandListenerWrapper var0, ScoreboardTeam var1, EnumChatFormat var2) throws CommandSyntaxException {
      if (var1.n() == var2) {
         throw d.create();
      } else {
         var1.a(var2);
         var0.a(IChatBaseComponent.a("commands.team.option.color.success", var1.d(), var2.g()), true);
         return 0;
      }
   }

   private static int a(CommandListenerWrapper var0, ScoreboardTeam var1) throws CommandSyntaxException {
      Scoreboard var2 = var0.l().aF();
      Collection<String> var3 = Lists.newArrayList(var1.g());
      if (var3.isEmpty()) {
         throw b.create();
      } else {
         for(String var5 : var3) {
            var2.b(var5, var1);
         }

         var0.a(IChatBaseComponent.a("commands.team.empty.success", var3.size(), var1.d()), true);
         return var3.size();
      }
   }

   private static int b(CommandListenerWrapper var0, ScoreboardTeam var1) {
      Scoreboard var2 = var0.l().aF();
      var2.d(var1);
      var0.a(IChatBaseComponent.a("commands.team.remove.success", var1.d()), true);
      return var2.g().size();
   }

   private static int a(CommandListenerWrapper var0, String var1) throws CommandSyntaxException {
      return a(var0, var1, IChatBaseComponent.b(var1));
   }

   private static int a(CommandListenerWrapper var0, String var1, IChatBaseComponent var2) throws CommandSyntaxException {
      Scoreboard var3 = var0.l().aF();
      if (var3.f(var1) != null) {
         throw a.create();
      } else {
         ScoreboardTeam var4 = var3.g(var1);
         var4.a(var2);
         var0.a(IChatBaseComponent.a("commands.team.add.success", var4.d()), true);
         return var3.g().size();
      }
   }

   private static int c(CommandListenerWrapper var0, ScoreboardTeam var1) {
      Collection<String> var2 = var1.g();
      if (var2.isEmpty()) {
         var0.a(IChatBaseComponent.a("commands.team.list.members.empty", var1.d()), false);
      } else {
         var0.a(IChatBaseComponent.a("commands.team.list.members.success", var1.d(), var2.size(), ChatComponentUtils.a(var2)), false);
      }

      return var2.size();
   }

   private static int a(CommandListenerWrapper var0) {
      Collection<ScoreboardTeam> var1 = var0.l().aF().g();
      if (var1.isEmpty()) {
         var0.a(IChatBaseComponent.c("commands.team.list.teams.empty"), false);
      } else {
         var0.a(IChatBaseComponent.a("commands.team.list.teams.success", var1.size(), ChatComponentUtils.b(var1, ScoreboardTeam::d)), false);
      }

      return var1.size();
   }

   private static int b(CommandListenerWrapper var0, ScoreboardTeam var1, IChatBaseComponent var2) {
      var1.b(var2);
      var0.a(IChatBaseComponent.a("commands.team.option.prefix.success", var2), false);
      return 1;
   }

   private static int c(CommandListenerWrapper var0, ScoreboardTeam var1, IChatBaseComponent var2) {
      var1.c(var2);
      var0.a(IChatBaseComponent.a("commands.team.option.suffix.success", var2), false);
      return 1;
   }
}
