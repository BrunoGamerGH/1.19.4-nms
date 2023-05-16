package net.minecraft.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.chat.IChatBaseComponent;

public class CommandException extends RuntimeException {
   private final IChatBaseComponent a;

   public CommandException(IChatBaseComponent var0) {
      super(var0.getString(), null, CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES, CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES);
      this.a = var0;
   }

   public IChatBaseComponent a() {
      return this.a;
   }
}
