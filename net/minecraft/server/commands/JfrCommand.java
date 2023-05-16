package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.minecraft.EnumChatFormat;
import net.minecraft.SharedConstants;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.ChatClickable;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.profiling.jfr.Environment;
import net.minecraft.util.profiling.jfr.JvmProfiler;

public class JfrCommand {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.jfr.start.failed"));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.jfr.dump.failed", var0));

   private JfrCommand() {
   }

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("jfr")
                  .requires(var0x -> var0x.c(4)))
               .then(net.minecraft.commands.CommandDispatcher.a("start").executes(var0x -> a((CommandListenerWrapper)var0x.getSource()))))
            .then(net.minecraft.commands.CommandDispatcher.a("stop").executes(var0x -> b((CommandListenerWrapper)var0x.getSource())))
      );
   }

   private static int a(CommandListenerWrapper var0) throws CommandSyntaxException {
      Environment var1 = Environment.a(var0.l());
      if (!JvmProfiler.e.a(var1)) {
         throw a.create();
      } else {
         var0.a(IChatBaseComponent.c("commands.jfr.started"), false);
         return 1;
      }
   }

   private static int b(CommandListenerWrapper var0) throws CommandSyntaxException {
      try {
         Path var1 = Paths.get(".").relativize(JvmProfiler.e.b().normalize());
         Path var2 = var0.l().p() && !SharedConstants.aO ? var1 : var1.toAbsolutePath();
         IChatBaseComponent var3 = IChatBaseComponent.b(var1.toString())
            .a(EnumChatFormat.t)
            .a(
               var1x -> var1x.a(new ChatClickable(ChatClickable.EnumClickAction.f, var2.toString()))
                     .a(new ChatHoverable(ChatHoverable.EnumHoverAction.a, IChatBaseComponent.c("chat.copy.click")))
            );
         var0.a(IChatBaseComponent.a("commands.jfr.stopped", var3), false);
         return 1;
      } catch (Throwable var4) {
         throw b.create(var4.getMessage());
      }
   }
}
