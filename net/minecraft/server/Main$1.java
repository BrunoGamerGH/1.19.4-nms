package net.minecraft.server;

import net.minecraft.server.dedicated.DedicatedServer;

class Main$1 extends Thread {
   Main$1(String var0, DedicatedServer var2) {
      super(var0);
      this.a = var2;
   }

   @Override
   public void run() {
      this.a.a(true);
   }
}
