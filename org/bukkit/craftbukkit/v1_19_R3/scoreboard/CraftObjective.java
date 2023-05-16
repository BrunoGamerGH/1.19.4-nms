package org.bukkit.craftbukkit.v1_19_R3.scoreboard;

import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;
import org.apache.commons.lang.Validate;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;

final class CraftObjective extends CraftScoreboardComponent implements Objective {
   private final ScoreboardObjective objective;
   private final CraftCriteria criteria;

   CraftObjective(CraftScoreboard scoreboard, ScoreboardObjective objective) {
      super(scoreboard);
      this.objective = objective;
      this.criteria = CraftCriteria.getFromNMS(objective);
   }

   ScoreboardObjective getHandle() {
      return this.objective;
   }

   public String getName() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      return this.objective.b();
   }

   public String getDisplayName() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      return CraftChatMessage.fromComponent(this.objective.d());
   }

   public void setDisplayName(String displayName) throws IllegalStateException, IllegalArgumentException {
      Validate.notNull(displayName, "Display name cannot be null");
      Validate.isTrue(displayName.length() <= 128, "Display name '" + displayName + "' is longer than the limit of 128 characters");
      CraftScoreboard scoreboard = this.checkState();
      this.objective.a(CraftChatMessage.fromString(displayName)[0]);
   }

   public String getCriteria() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      return this.criteria.bukkitName;
   }

   public Criteria getTrackedCriteria() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      return this.criteria;
   }

   public boolean isModifiable() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      return !this.criteria.criteria.e();
   }

   public void setDisplaySlot(DisplaySlot slot) throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      Scoreboard board = scoreboard.board;
      ScoreboardObjective objective = this.objective;

      for(int i = 0; i < 19; ++i) {
         if (board.a(i) == objective) {
            board.a(i, null);
         }
      }

      if (slot != null) {
         int slotNumber = CraftScoreboardTranslations.fromBukkitSlot(slot);
         board.a(slotNumber, this.getHandle());
      }
   }

   public DisplaySlot getDisplaySlot() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      Scoreboard board = scoreboard.board;
      ScoreboardObjective objective = this.objective;

      for(int i = 0; i < 19; ++i) {
         if (board.a(i) == objective) {
            return CraftScoreboardTranslations.toBukkitSlot(i);
         }
      }

      return null;
   }

   public void setRenderType(RenderType renderType) throws IllegalStateException {
      Validate.notNull(renderType, "RenderType cannot be null");
      CraftScoreboard scoreboard = this.checkState();
      this.objective.a(CraftScoreboardTranslations.fromBukkitRender(renderType));
   }

   public RenderType getRenderType() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      return CraftScoreboardTranslations.toBukkitRender(this.objective.f());
   }

   public Score getScore(OfflinePlayer player) throws IllegalArgumentException, IllegalStateException {
      Validate.notNull(player, "Player cannot be null");
      CraftScoreboard scoreboard = this.checkState();
      return new CraftScore(this, player.getName());
   }

   public Score getScore(String entry) throws IllegalArgumentException, IllegalStateException {
      Validate.notNull(entry, "Entry cannot be null");
      Validate.isTrue(entry.length() <= 32767, "Score '" + entry + "' is longer than the limit of 32767 characters");
      CraftScoreboard scoreboard = this.checkState();
      return new CraftScore(this, entry);
   }

   @Override
   public void unregister() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      scoreboard.board.j(this.objective);
   }

   @Override
   CraftScoreboard checkState() throws IllegalStateException {
      if (this.getScoreboard().board.d(this.objective.b()) == null) {
         throw new IllegalStateException("Unregistered scoreboard component");
      } else {
         return this.getScoreboard();
      }
   }

   @Override
   public int hashCode() {
      int hash = 7;
      return 31 * hash + (this.objective != null ? this.objective.hashCode() : 0);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         CraftObjective other = (CraftObjective)obj;
         return this.objective == other.objective || this.objective != null && this.objective.equals(other.objective);
      }
   }
}
