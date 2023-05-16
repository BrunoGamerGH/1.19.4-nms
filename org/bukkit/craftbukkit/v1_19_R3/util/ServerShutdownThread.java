package org.bukkit.craftbukkit.v1_19_R3.util;

import net.minecraft.server.MinecraftServer;
import org.spigotmc.AsyncCatcher;

public class ServerShutdownThread extends Thread {
   private final MinecraftServer server;

   public ServerShutdownThread(MinecraftServer server) {
      this.server = server;
   }

   @Override
   public void run() {
      try {
         AsyncCatcher.enabled = false;
         this.server.close();
      } finally {
         try {
            this.server.reader.getTerminal().restore();
         } catch (Exception var6) {
         }
      }
   }
}
