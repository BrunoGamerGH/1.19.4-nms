package org.spigotmc;

import net.minecraft.server.MinecraftServer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TicksPerSecondCommand extends Command {
   public TicksPerSecondCommand(String name) {
      super(name);
      this.description = "Gets the current ticks per second for the server";
      this.usageMessage = "/tps";
      this.setPermission("bukkit.command.tps");
   }

   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
      if (!this.testPermission(sender)) {
         return true;
      } else {
         StringBuilder sb = new StringBuilder(ChatColor.GOLD + "TPS from last 1m, 5m, 15m: ");

         double[] var9;
         for(double tps : var9 = MinecraftServer.getServer().recentTps) {
            sb.append(this.format(tps));
            sb.append(", ");
         }

         sender.sendMessage(sb.substring(0, sb.length() - 2));
         sender.sendMessage(
            ChatColor.GOLD
               + "Current Memory Usage: "
               + ChatColor.GREEN
               + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L
               + "/"
               + Runtime.getRuntime().totalMemory() / 1048576L
               + " mb (Max: "
               + Runtime.getRuntime().maxMemory() / 1048576L
               + " mb)"
         );
         return true;
      }
   }

   private String format(double tps) {
      return (tps > 18.0 ? ChatColor.GREEN : (tps > 16.0 ? ChatColor.YELLOW : ChatColor.RED)).toString()
         + (tps > 20.0 ? "*" : "")
         + Math.min((double)Math.round(tps * 100.0) / 100.0, 20.0);
   }
}
