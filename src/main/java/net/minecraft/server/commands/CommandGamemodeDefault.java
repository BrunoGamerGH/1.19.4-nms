package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.GameModeArgument;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.level.EnumGamemode;

public class CommandGamemodeDefault {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("defaultgamemode")
               .requires(var0x -> var0x.c(2)))
            .then(
               net.minecraft.commands.CommandDispatcher.a("gamemode", GameModeArgument.a())
                  .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), GameModeArgument.a(var0x, "gamemode")))
            )
      );
   }

   private static int a(CommandListenerWrapper var0, EnumGamemode var1) {
      int var2 = 0;
      MinecraftServer var3 = var0.l();
      var3.a(var1);
      EnumGamemode var4 = var3.aZ();
      if (var4 != null) {
         for(EntityPlayer var6 : var3.ac().t()) {
            if (var6.a(var4)) {
               ++var2;
            }
         }
      }

      var0.a(IChatBaseComponent.a("commands.defaultgamemode.success", var1.d()), true);
      return var2;
   }
}
