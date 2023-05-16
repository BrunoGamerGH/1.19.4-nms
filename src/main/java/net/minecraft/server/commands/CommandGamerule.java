package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.level.GameRules;

public class CommandGamerule {
   public static void a(CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
      final LiteralArgumentBuilder<CommandListenerWrapper> literalargumentbuilder = (LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
            "gamerule"
         )
         .requires(commandlistenerwrapper -> commandlistenerwrapper.c(2));
      GameRules.a(
         new GameRules.GameRuleVisitor() {
            @Override
            public <T extends GameRules.GameRuleValue<T>> void a(
               GameRules.GameRuleKey<T> gamerules_gamerulekey, GameRules.GameRuleDefinition<T> gamerules_gameruledefinition
            ) {
               literalargumentbuilder.then(
                  ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(gamerules_gamerulekey.a())
                        .executes(commandcontext -> CommandGamerule.a((CommandListenerWrapper)commandcontext.getSource(), gamerules_gamerulekey)))
                     .then(gamerules_gameruledefinition.a("value").executes(commandcontext -> CommandGamerule.a(commandcontext, gamerules_gamerulekey)))
               );
            }
         }
      );
      commanddispatcher.register(literalargumentbuilder);
   }

   static <T extends GameRules.GameRuleValue<T>> int a(CommandContext<CommandListenerWrapper> commandcontext, GameRules.GameRuleKey<T> gamerules_gamerulekey) {
      CommandListenerWrapper commandlistenerwrapper = (CommandListenerWrapper)commandcontext.getSource();
      T t0 = commandlistenerwrapper.e().W().a(gamerules_gamerulekey);
      t0.b(commandcontext, "value");
      commandlistenerwrapper.a(IChatBaseComponent.a("commands.gamerule.set", gamerules_gamerulekey.a(), t0.toString()), true);
      return t0.c();
   }

   static <T extends GameRules.GameRuleValue<T>> int a(CommandListenerWrapper commandlistenerwrapper, GameRules.GameRuleKey<T> gamerules_gamerulekey) {
      T t0 = commandlistenerwrapper.e().W().a(gamerules_gamerulekey);
      commandlistenerwrapper.a(IChatBaseComponent.a("commands.gamerule.query", gamerules_gamerulekey.a(), t0.toString()), false);
      return t0.c();
   }
}
