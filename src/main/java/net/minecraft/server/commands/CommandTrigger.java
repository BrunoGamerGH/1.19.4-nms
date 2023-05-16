package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.ArgumentScoreboardObjective;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.ScoreboardServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.ScoreboardScore;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;

public class CommandTrigger {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.trigger.failed.unprimed"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.trigger.failed.invalid"));

   public static void a(CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
      commanddispatcher.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)net.minecraft.commands.CommandDispatcher.a("trigger")
            .then(
               ((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("objective", ArgumentScoreboardObjective.a())
                        .suggests((commandcontext, suggestionsbuilder) -> a((CommandListenerWrapper)commandcontext.getSource(), suggestionsbuilder))
                        .executes(
                           commandcontext -> a(
                                 (CommandListenerWrapper)commandcontext.getSource(),
                                 a(((CommandListenerWrapper)commandcontext.getSource()).h(), ArgumentScoreboardObjective.a(commandcontext, "objective"))
                              )
                        ))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("add")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("value", IntegerArgumentType.integer())
                                 .executes(
                                    commandcontext -> a(
                                          (CommandListenerWrapper)commandcontext.getSource(),
                                          a(
                                             ((CommandListenerWrapper)commandcontext.getSource()).h(),
                                             ArgumentScoreboardObjective.a(commandcontext, "objective")
                                          ),
                                          IntegerArgumentType.getInteger(commandcontext, "value")
                                       )
                                 )
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("set")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("value", IntegerArgumentType.integer())
                              .executes(
                                 commandcontext -> b(
                                       (CommandListenerWrapper)commandcontext.getSource(),
                                       a(((CommandListenerWrapper)commandcontext.getSource()).h(), ArgumentScoreboardObjective.a(commandcontext, "objective")),
                                       IntegerArgumentType.getInteger(commandcontext, "value")
                                    )
                              )
                        )
                  )
            )
      );
   }

   public static CompletableFuture<Suggestions> a(CommandListenerWrapper commandlistenerwrapper, SuggestionsBuilder suggestionsbuilder) {
      Entity entity = commandlistenerwrapper.f();
      List<String> list = Lists.newArrayList();
      if (entity != null) {
         ScoreboardServer scoreboardserver = commandlistenerwrapper.l().aF();
         String s = entity.cu();

         for(ScoreboardObjective scoreboardobjective : scoreboardserver.c()) {
            if (scoreboardobjective.c() == IScoreboardCriteria.b && scoreboardserver.b(s, scoreboardobjective)) {
               ScoreboardScore scoreboardscore = scoreboardserver.c(s, scoreboardobjective);
               if (!scoreboardscore.g()) {
                  list.add(scoreboardobjective.b());
               }
            }
         }
      }

      return ICompletionProvider.b(list, suggestionsbuilder);
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper, ScoreboardScore scoreboardscore, int i) {
      scoreboardscore.a(i);
      commandlistenerwrapper.a(IChatBaseComponent.a("commands.trigger.add.success", scoreboardscore.d().e(), i), true);
      return scoreboardscore.b();
   }

   private static int b(CommandListenerWrapper commandlistenerwrapper, ScoreboardScore scoreboardscore, int i) {
      scoreboardscore.b(i);
      commandlistenerwrapper.a(IChatBaseComponent.a("commands.trigger.set.success", scoreboardscore.d().e(), i), true);
      return i;
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper, ScoreboardScore scoreboardscore) {
      scoreboardscore.a(1);
      commandlistenerwrapper.a(IChatBaseComponent.a("commands.trigger.simple.success", scoreboardscore.d().e()), true);
      return scoreboardscore.b();
   }

   private static ScoreboardScore a(EntityPlayer entityplayer, ScoreboardObjective scoreboardobjective) throws CommandSyntaxException {
      if (scoreboardobjective.c() != IScoreboardCriteria.b) {
         throw b.create();
      } else {
         Scoreboard scoreboard = entityplayer.cH().aF();
         String s = entityplayer.cu();
         if (!scoreboard.b(s, scoreboardobjective)) {
            throw a.create();
         } else {
            ScoreboardScore scoreboardscore = scoreboard.c(s, scoreboardobjective);
            if (scoreboardscore.g()) {
               throw a.create();
            } else {
               scoreboardscore.a(true);
               return scoreboardscore;
            }
         }
      }
   }
}
