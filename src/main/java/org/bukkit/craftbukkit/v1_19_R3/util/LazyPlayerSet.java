package org.bukkit.craftbukkit.v1_19_R3.util;

import java.util.HashSet;
import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.entity.Player;

public class LazyPlayerSet extends LazyHashSet<Player> {
   private final MinecraftServer server;

   public LazyPlayerSet(MinecraftServer server) {
      this.server = server;
   }

   HashSet<Player> makeReference() {
      if (this.reference != null) {
         throw new IllegalStateException("Reference already created!");
      } else {
         List<EntityPlayer> players = this.server.ac().k;
         HashSet<Player> reference = new HashSet(players.size());

         for(EntityPlayer player : players) {
            reference.add(player.getBukkitEntity());
         }

         return reference;
      }
   }
}
