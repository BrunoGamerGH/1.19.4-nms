package org.bukkit.craftbukkit.v1_19_R3.scoreboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ScoreboardServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.ScoreboardScore;
import net.minecraft.world.scores.ScoreboardTeam;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;
import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.util.WeakCollection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;
import org.spigotmc.AsyncCatcher;

public final class CraftScoreboardManager implements ScoreboardManager {
   private final CraftScoreboard mainScoreboard;
   private final MinecraftServer server;
   private final Collection<CraftScoreboard> scoreboards = new WeakCollection<>();
   private final Map<CraftPlayer, CraftScoreboard> playerBoards = new HashMap<>();

   public CraftScoreboardManager(MinecraftServer minecraftserver, Scoreboard scoreboardServer) {
      this.mainScoreboard = new CraftScoreboard(scoreboardServer);
      this.server = minecraftserver;
      this.scoreboards.add(this.mainScoreboard);
   }

   public CraftScoreboard getMainScoreboard() {
      return this.mainScoreboard;
   }

   public CraftScoreboard getNewScoreboard() {
      AsyncCatcher.catchOp("scoreboard creation");
      CraftScoreboard scoreboard = new CraftScoreboard(new ScoreboardServer(this.server));
      this.scoreboards.add(scoreboard);
      return scoreboard;
   }

   public CraftScoreboard getPlayerBoard(CraftPlayer player) {
      CraftScoreboard board = this.playerBoards.get(player);
      return board == null ? this.getMainScoreboard() : board;
   }

   public void setPlayerBoard(CraftPlayer player, org.bukkit.scoreboard.Scoreboard bukkitScoreboard) throws IllegalArgumentException {
      Validate.isTrue(bukkitScoreboard instanceof CraftScoreboard, "Cannot set player scoreboard to an unregistered Scoreboard");
      CraftScoreboard scoreboard = (CraftScoreboard)bukkitScoreboard;
      Scoreboard oldboard = this.getPlayerBoard(player).getHandle();
      Scoreboard newboard = scoreboard.getHandle();
      EntityPlayer entityplayer = player.getHandle();
      if (oldboard != newboard) {
         if (scoreboard == this.mainScoreboard) {
            this.playerBoards.remove(player);
         } else {
            this.playerBoards.put(player, scoreboard);
         }

         HashSet<ScoreboardObjective> removed = new HashSet<>();

         for(int i = 0; i < 3; ++i) {
            ScoreboardObjective scoreboardobjective = oldboard.a(i);
            if (scoreboardobjective != null && !removed.contains(scoreboardobjective)) {
               entityplayer.b.a(new PacketPlayOutScoreboardObjective(scoreboardobjective, 1));
               removed.add(scoreboardobjective);
            }
         }

         for(ScoreboardTeam scoreboardteam : oldboard.g()) {
            entityplayer.b.a(PacketPlayOutScoreboardTeam.a(scoreboardteam));
         }

         this.server.ac().a((ScoreboardServer)newboard, player.getHandle());
      }
   }

   public void removePlayer(Player player) {
      this.playerBoards.remove(player);
   }

   public void getScoreboardScores(IScoreboardCriteria criteria, String name, Consumer<ScoreboardScore> consumer) {
      for(CraftScoreboard scoreboard : this.scoreboards) {
         Scoreboard board = scoreboard.board;
         board.a(criteria, name, score -> consumer.accept(score));
      }
   }
}
