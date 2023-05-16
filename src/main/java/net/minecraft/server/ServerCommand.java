package net.minecraft.server;

import net.minecraft.commands.CommandListenerWrapper;

public class ServerCommand {
   public final String a;
   public final CommandListenerWrapper b;

   public ServerCommand(String var0, CommandListenerWrapper var1) {
      this.a = var0;
      this.b = var1;
   }
}
