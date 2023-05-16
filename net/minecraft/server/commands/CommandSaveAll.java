package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.MinecraftServer;

public class CommandSaveAll {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.save.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                     "save-all"
                  )
                  .requires(var0x -> var0x.c(4)))
               .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), false)))
            .then(net.minecraft.commands.CommandDispatcher.a("flush").executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), true)))
      );
   }

   private static int a(CommandListenerWrapper var0, boolean var1) throws CommandSyntaxException {
      var0.a(IChatBaseComponent.c("commands.save.saving"), false);
      MinecraftServer var2 = var0.l();
      boolean var3 = var2.b(true, var1, true);
      if (!var3) {
         throw a.create();
      } else {
         var0.a(IChatBaseComponent.c("commands.save.success"), true);
         return 1;
      }
   }
}
