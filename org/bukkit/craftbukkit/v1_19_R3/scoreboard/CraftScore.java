package org.bukkit.craftbukkit.v1_19_R3.scoreboard;

import java.util.Map;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.ScoreboardScore;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

final class CraftScore implements Score {
   private final String entry;
   private final CraftObjective objective;

   CraftScore(CraftObjective objective, String entry) {
      this.objective = objective;
      this.entry = entry;
   }

   public OfflinePlayer getPlayer() {
      return Bukkit.getOfflinePlayer(this.entry);
   }

   public String getEntry() {
      return this.entry;
   }

   public Objective getObjective() {
      return this.objective;
   }

   public int getScore() throws IllegalStateException {
      Scoreboard board = this.objective.checkState().board;
      if (board.e().contains(this.entry)) {
         Map<ScoreboardObjective, ScoreboardScore> scores = board.e(this.entry);
         ScoreboardScore score = scores.get(this.objective.getHandle());
         if (score != null) {
            return score.b();
         }
      }

      return 0;
   }

   public void setScore(int score) throws IllegalStateException {
      this.objective.checkState().board.c(this.entry, this.objective.getHandle()).b(score);
   }

   public boolean isScoreSet() throws IllegalStateException {
      Scoreboard board = this.objective.checkState().board;
      return board.e().contains(this.entry) && board.e(this.entry).containsKey(this.objective.getHandle());
   }

   public CraftScoreboard getScoreboard() {
      return this.objective.getScoreboard();
   }
}
