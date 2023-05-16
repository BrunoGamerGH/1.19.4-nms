package org.bukkit.craftbukkit.v1_19_R3.command;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.rcon.RemoteControlCommandListener;
import org.bukkit.command.RemoteConsoleCommandSender;

public class CraftRemoteConsoleCommandSender extends ServerCommandSender implements RemoteConsoleCommandSender {
   private final RemoteControlCommandListener listener;

   public CraftRemoteConsoleCommandSender(RemoteControlCommandListener listener) {
      this.listener = listener;
   }

   public void sendMessage(String message) {
      this.listener.a(IChatBaseComponent.b(message + "\n"));
   }

   public void sendMessage(String... messages) {
      for(String message : messages) {
         this.sendMessage(message);
      }
   }

   public String getName() {
      return "Rcon";
   }

   public boolean isOp() {
      return true;
   }

   public void setOp(boolean value) {
      throw new UnsupportedOperationException("Cannot change operator status of remote controller.");
   }
}
