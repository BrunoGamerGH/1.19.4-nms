package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.WorldServer;

public class CommandSaveOff {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.save.alreadyOff"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("save-off")
               .requires(var0x -> var0x.c(4)))
            .executes(var0x -> {
               CommandListenerWrapper var1 = (CommandListenerWrapper)var0x.getSource();
               boolean var2 = false;
      
               for(WorldServer var4 : var1.l().F()) {
                  if (var4 != null && !var4.e) {
                     var4.e = true;
                     var2 = true;
                  }
               }
      
               if (!var2) {
                  throw a.create();
               } else {
                  var1.a(IChatBaseComponent.c("commands.save.disabled"), true);
                  return 1;
               }
            })
      );
   }
}
