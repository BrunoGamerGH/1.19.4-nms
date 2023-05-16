package org.bukkit.craftbukkit.v1_19_R3.scoreboard;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.ImmutableSet.Builder;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.ScoreboardTeam;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public final class CraftScoreboard implements Scoreboard {
   final net.minecraft.world.scores.Scoreboard board;

   CraftScoreboard(net.minecraft.world.scores.Scoreboard board) {
      this.board = board;
   }

   public CraftObjective registerNewObjective(String name, String criteria) throws IllegalArgumentException {
      return this.registerNewObjective(name, criteria, name);
   }

   public CraftObjective registerNewObjective(String name, String criteria, String displayName) throws IllegalArgumentException {
      return this.registerNewObjective(name, CraftCriteria.getFromBukkit(criteria), displayName, RenderType.INTEGER);
   }

   public CraftObjective registerNewObjective(String name, String criteria, String displayName, RenderType renderType) throws IllegalArgumentException {
      return this.registerNewObjective(name, CraftCriteria.getFromBukkit(criteria), displayName, renderType);
   }

   public CraftObjective registerNewObjective(String name, Criteria criteria, String displayName) throws IllegalArgumentException {
      return this.registerNewObjective(name, criteria, displayName, RenderType.INTEGER);
   }

   public CraftObjective registerNewObjective(String name, Criteria criteria, String displayName, RenderType renderType) throws IllegalArgumentException {
      Validate.notNull(name, "Objective name cannot be null");
      Validate.notNull(criteria, "Criteria cannot be null");
      Validate.notNull(displayName, "Display name cannot be null");
      Validate.notNull(renderType, "RenderType cannot be null");
      Validate.isTrue(name.length() <= 32767, "The name '" + name + "' is longer than the limit of 32767 characters");
      Validate.isTrue(displayName.length() <= 128, "The display name '" + displayName + "' is longer than the limit of 128 characters");
      Validate.isTrue(this.board.d(name) == null, "An objective of name '" + name + "' already exists");
      ScoreboardObjective objective = this.board
         .a(name, ((CraftCriteria)criteria).criteria, CraftChatMessage.fromStringOrNull(displayName), CraftScoreboardTranslations.fromBukkitRender(renderType));
      return new CraftObjective(this, objective);
   }

   public Objective getObjective(String name) throws IllegalArgumentException {
      Validate.notNull(name, "Name cannot be null");
      ScoreboardObjective nms = this.board.d(name);
      return nms == null ? null : new CraftObjective(this, nms);
   }

   public ImmutableSet<Objective> getObjectivesByCriteria(String criteria) throws IllegalArgumentException {
      Validate.notNull(criteria, "Criteria cannot be null");
      Builder<Objective> objectives = ImmutableSet.builder();

      for(ScoreboardObjective netObjective : this.board.c()) {
         CraftObjective objective = new CraftObjective(this, netObjective);
         if (objective.getCriteria().equals(criteria)) {
            objectives.add(objective);
         }
      }

      return objectives.build();
   }

   public ImmutableSet<Objective> getObjectivesByCriteria(Criteria criteria) throws IllegalArgumentException {
      Validate.notNull(criteria, "Criteria cannot be null");
      Builder<Objective> objectives = ImmutableSet.builder();

      for(ScoreboardObjective netObjective : this.board.c()) {
         CraftObjective objective = new CraftObjective(this, netObjective);
         if (objective.getTrackedCriteria().equals(criteria)) {
            objectives.add(objective);
         }
      }

      return objectives.build();
   }

   public ImmutableSet<Objective> getObjectives() {
      return ImmutableSet.copyOf(Iterables.transform(this.board.c(), new Function<ScoreboardObjective, Objective>() {
         public Objective apply(ScoreboardObjective input) {
            return new CraftObjective(CraftScoreboard.this, input);
         }
      }));
   }

   public Objective getObjective(DisplaySlot slot) throws IllegalArgumentException {
      Validate.notNull(slot, "Display slot cannot be null");
      ScoreboardObjective objective = this.board.a(CraftScoreboardTranslations.fromBukkitSlot(slot));
      return objective == null ? null : new CraftObjective(this, objective);
   }

   public ImmutableSet<Score> getScores(OfflinePlayer player) throws IllegalArgumentException {
      Validate.notNull(player, "OfflinePlayer cannot be null");
      return this.getScores(player.getName());
   }

   public ImmutableSet<Score> getScores(String entry) throws IllegalArgumentException {
      Validate.notNull(entry, "Entry cannot be null");
      Builder<Score> scores = ImmutableSet.builder();

      for(ScoreboardObjective objective : this.board.c()) {
         scores.add(new CraftScore(new CraftObjective(this, objective), entry));
      }

      return scores.build();
   }

   public void resetScores(OfflinePlayer player) throws IllegalArgumentException {
      Validate.notNull(player, "OfflinePlayer cannot be null");
      this.resetScores(player.getName());
   }

   public void resetScores(String entry) throws IllegalArgumentException {
      Validate.notNull(entry, "Entry cannot be null");

      for(ScoreboardObjective objective : this.board.c()) {
         this.board.d(entry, objective);
      }
   }

   public Team getPlayerTeam(OfflinePlayer player) throws IllegalArgumentException {
      Validate.notNull(player, "OfflinePlayer cannot be null");
      ScoreboardTeam team = this.board.i(player.getName());
      return team == null ? null : new CraftTeam(this, team);
   }

   public Team getEntryTeam(String entry) throws IllegalArgumentException {
      Validate.notNull(entry, "Entry cannot be null");
      ScoreboardTeam team = this.board.i(entry);
      return team == null ? null : new CraftTeam(this, team);
   }

   public Team getTeam(String teamName) throws IllegalArgumentException {
      Validate.notNull(teamName, "Team name cannot be null");
      ScoreboardTeam team = this.board.f(teamName);
      return team == null ? null : new CraftTeam(this, team);
   }

   public ImmutableSet<Team> getTeams() {
      return ImmutableSet.copyOf(Iterables.transform(this.board.g(), new Function<ScoreboardTeam, Team>() {
         public Team apply(ScoreboardTeam input) {
            return new CraftTeam(CraftScoreboard.this, input);
         }
      }));
   }

   public Team registerNewTeam(String name) throws IllegalArgumentException {
      Validate.notNull(name, "Team name cannot be null");
      Validate.isTrue(name.length() <= 32767, "Team name '" + name + "' is longer than the limit of 32767 characters");
      Validate.isTrue(this.board.f(name) == null, "Team name '" + name + "' is already in use");
      return new CraftTeam(this, this.board.g(name));
   }

   public ImmutableSet<OfflinePlayer> getPlayers() {
      Builder<OfflinePlayer> players = ImmutableSet.builder();

      for(Object playerName : this.board.e()) {
         players.add(Bukkit.getOfflinePlayer(playerName.toString()));
      }

      return players.build();
   }

   public ImmutableSet<String> getEntries() {
      Builder<String> entries = ImmutableSet.builder();

      for(Object entry : this.board.e()) {
         entries.add(entry.toString());
      }

      return entries.build();
   }

   public void clearSlot(DisplaySlot slot) throws IllegalArgumentException {
      Validate.notNull(slot, "Slot cannot be null");
      this.board.a(CraftScoreboardTranslations.fromBukkitSlot(slot), null);
   }

   public net.minecraft.world.scores.Scoreboard getHandle() {
      return this.board;
   }
}
