package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.GameModeArgument;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.util.HttpUtilities;
import net.minecraft.world.level.EnumGamemode;

public class CommandPublish {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.publish.failed"));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(
      var0 -> IChatBaseComponent.a("commands.publish.alreadyPublished", var0)
   );

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                     "publish"
                  )
                  .requires(var0x -> var0x.c(4)))
               .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), HttpUtilities.a(), false, null)))
            .then(
               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("allowCommands", BoolArgumentType.bool())
                     .executes(
                        var0x -> a((CommandListenerWrapper)var0x.getSource(), HttpUtilities.a(), BoolArgumentType.getBool(var0x, "allowCommands"), null)
                     ))
                  .then(
                     ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("gamemode", GameModeArgument.a())
                           .executes(
                              var0x -> a(
                                    (CommandListenerWrapper)var0x.getSource(),
                                    HttpUtilities.a(),
                                    BoolArgumentType.getBool(var0x, "allowCommands"),
                                    GameModeArgument.a(var0x, "gamemode")
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("port", IntegerArgumentType.integer(0, 65535))
                              .executes(
                                 var0x -> a(
                                       (CommandListenerWrapper)var0x.getSource(),
                                       IntegerArgumentType.getInteger(var0x, "port"),
                                       BoolArgumentType.getBool(var0x, "allowCommands"),
                                       GameModeArgument.a(var0x, "gamemode")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, int var1, boolean var2, @Nullable EnumGamemode var3) throws CommandSyntaxException {
      if (var0.l().p()) {
         throw b.create(var0.l().M());
      } else if (!var0.l().a(var3, var2, var1)) {
         throw a.create();
      } else {
         var0.a(a(var1), true);
         return var1;
      }
   }

   public static IChatMutableComponent a(int var0) {
      IChatBaseComponent var1 = ChatComponentUtils.a(String.valueOf(var0));
      return IChatBaseComponent.a("commands.publish.started", var1);
   }
}
