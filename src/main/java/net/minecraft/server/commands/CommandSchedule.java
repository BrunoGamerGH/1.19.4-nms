package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.CustomFunction;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.ArgumentTime;
import net.minecraft.commands.arguments.item.ArgumentTag;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.timers.CustomFunctionCallback;
import net.minecraft.world.level.timers.CustomFunctionCallbackTag;
import net.minecraft.world.level.timers.CustomFunctionCallbackTimerQueue;

public class CommandSchedule {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.schedule.same_tick"));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(
      object -> IChatBaseComponent.a("commands.schedule.cleared.failure", object)
   );
   private static final SuggestionProvider<CommandListenerWrapper> c = (commandcontext, suggestionsbuilder) -> ICompletionProvider.b(
         ((CommandListenerWrapper)commandcontext.getSource()).l().aW().J().u().a(), suggestionsbuilder
      );

   public static void a(CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
      commanddispatcher.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                     "schedule"
                  )
                  .requires(commandlistenerwrapper -> commandlistenerwrapper.c(2)))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("function")
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("function", ArgumentTag.a())
                           .suggests(CommandFunction.a)
                           .then(
                              ((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("time", ArgumentTime.a())
                                       .executes(
                                          commandcontext -> a(
                                                (CommandListenerWrapper)commandcontext.getSource(),
                                                ArgumentTag.b(commandcontext, "function"),
                                                IntegerArgumentType.getInteger(commandcontext, "time"),
                                                true
                                             )
                                       ))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("append")
                                          .executes(
                                             commandcontext -> a(
                                                   (CommandListenerWrapper)commandcontext.getSource(),
                                                   ArgumentTag.b(commandcontext, "function"),
                                                   IntegerArgumentType.getInteger(commandcontext, "time"),
                                                   false
                                                )
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("replace")
                                       .executes(
                                          commandcontext -> a(
                                                (CommandListenerWrapper)commandcontext.getSource(),
                                                ArgumentTag.b(commandcontext, "function"),
                                                IntegerArgumentType.getInteger(commandcontext, "time"),
                                                true
                                             )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               net.minecraft.commands.CommandDispatcher.a("clear")
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("function", StringArgumentType.greedyString())
                        .suggests(c)
                        .executes(
                           commandcontext -> a((CommandListenerWrapper)commandcontext.getSource(), StringArgumentType.getString(commandcontext, "function"))
                        )
                  )
            )
      );
   }

   private static int a(
      CommandListenerWrapper commandlistenerwrapper, Pair<MinecraftKey, Either<CustomFunction, Collection<CustomFunction>>> pair, int i, boolean flag
   ) throws CommandSyntaxException {
      if (i == 0) {
         throw a.create();
      } else {
         long j = commandlistenerwrapper.e().U() + (long)i;
         MinecraftKey minecraftkey = (MinecraftKey)pair.getFirst();
         CustomFunctionCallbackTimerQueue<MinecraftServer> customfunctioncallbacktimerqueue = commandlistenerwrapper.e().J.J().u();
         ((Either)pair.getSecond()).ifLeft(customfunction -> {
            String s = minecraftkey.toString();
            if (flag) {
               customfunctioncallbacktimerqueue.a(s);
            }

            customfunctioncallbacktimerqueue.a(s, j, new CustomFunctionCallback(minecraftkey));
            commandlistenerwrapper.a(IChatBaseComponent.a("commands.schedule.created.function", minecraftkey, i, j), true);
         }).ifRight(collection -> {
            String s = "#" + minecraftkey;
            if (flag) {
               customfunctioncallbacktimerqueue.a(s);
            }

            customfunctioncallbacktimerqueue.a(s, j, new CustomFunctionCallbackTag(minecraftkey));
            commandlistenerwrapper.a(IChatBaseComponent.a("commands.schedule.created.tag", minecraftkey, i, j), true);
         });
         return Math.floorMod(j, Integer.MAX_VALUE);
      }
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper, String s) throws CommandSyntaxException {
      int i = commandlistenerwrapper.l().aW().J().u().a(s);
      if (i == 0) {
         throw b.create(s);
      } else {
         commandlistenerwrapper.a(IChatBaseComponent.a("commands.schedule.cleared.success", i, s), true);
         return i;
      }
   }
}
