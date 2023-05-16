package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.EnumDifficulty;

public class CommandDifficulty {
   private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> IChatBaseComponent.a("commands.difficulty.failure", object));

   public static void a(CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
      LiteralArgumentBuilder<CommandListenerWrapper> literalargumentbuilder = net.minecraft.commands.CommandDispatcher.a("difficulty");

      for(EnumDifficulty enumdifficulty : EnumDifficulty.values()) {
         literalargumentbuilder.then(
            net.minecraft.commands.CommandDispatcher.a(enumdifficulty.e())
               .executes(commandcontext -> a((CommandListenerWrapper)commandcontext.getSource(), enumdifficulty))
         );
      }

      commanddispatcher.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)literalargumentbuilder.requires(
               commandlistenerwrapper -> commandlistenerwrapper.c(2)
            ))
            .executes(commandcontext -> {
               EnumDifficulty enumdifficulty1 = ((CommandListenerWrapper)commandcontext.getSource()).e().ah();
               ((CommandListenerWrapper)commandcontext.getSource()).a(IChatBaseComponent.a("commands.difficulty.query", enumdifficulty1.b()), false);
               return enumdifficulty1.a();
            })
      );
   }

   public static int a(CommandListenerWrapper commandlistenerwrapper, EnumDifficulty enumdifficulty) throws CommandSyntaxException {
      MinecraftServer minecraftserver = commandlistenerwrapper.l();
      WorldServer worldServer = commandlistenerwrapper.e();
      if (worldServer.ah() == enumdifficulty) {
         throw a.create(enumdifficulty.e());
      } else {
         worldServer.J.a(enumdifficulty);
         commandlistenerwrapper.a(IChatBaseComponent.a("commands.difficulty.success", enumdifficulty.b()), true);
         return 0;
      }
   }
}
