package org.bukkit.craftbukkit.v1_19_R3.scoreboard;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.scores.ScoreboardTeam;
import net.minecraft.world.scores.ScoreboardTeamBase;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

final class CraftTeam extends CraftScoreboardComponent implements Team {
   private final ScoreboardTeam team;

   CraftTeam(CraftScoreboard scoreboard, ScoreboardTeam team) {
      super(scoreboard);
      this.team = team;
   }

   public String getName() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      return this.team.b();
   }

   public String getDisplayName() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      return CraftChatMessage.fromComponent(this.team.c());
   }

   public void setDisplayName(String displayName) throws IllegalStateException {
      Validate.notNull(displayName, "Display name cannot be null");
      Validate.isTrue(ChatColor.stripColor(displayName).length() <= 128, "Display name '" + displayName + "' is longer than the limit of 128 characters");
      CraftScoreboard scoreboard = this.checkState();
      this.team.a(CraftChatMessage.fromString(displayName)[0]);
   }

   public String getPrefix() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      return CraftChatMessage.fromComponent(this.team.e());
   }

   public void setPrefix(String prefix) throws IllegalStateException, IllegalArgumentException {
      Validate.notNull(prefix, "Prefix cannot be null");
      Validate.isTrue(ChatColor.stripColor(prefix).length() <= 64, "Prefix '" + prefix + "' is longer than the limit of 64 characters");
      CraftScoreboard scoreboard = this.checkState();
      this.team.b(CraftChatMessage.fromStringOrNull(prefix));
   }

   public String getSuffix() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      return CraftChatMessage.fromComponent(this.team.f());
   }

   public void setSuffix(String suffix) throws IllegalStateException, IllegalArgumentException {
      Validate.notNull(suffix, "Suffix cannot be null");
      Validate.isTrue(ChatColor.stripColor(suffix).length() <= 64, "Suffix '" + suffix + "' is longer than the limit of 64 characters");
      CraftScoreboard scoreboard = this.checkState();
      this.team.c(CraftChatMessage.fromStringOrNull(suffix));
   }

   public ChatColor getColor() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      return CraftChatMessage.getColor(this.team.n());
   }

   public void setColor(ChatColor color) {
      Validate.notNull(color, "Color cannot be null");
      CraftScoreboard scoreboard = this.checkState();
      this.team.a(CraftChatMessage.getColor(color));
   }

   public boolean allowFriendlyFire() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      return this.team.h();
   }

   public void setAllowFriendlyFire(boolean enabled) throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      this.team.a(enabled);
   }

   public boolean canSeeFriendlyInvisibles() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      return this.team.i();
   }

   public void setCanSeeFriendlyInvisibles(boolean enabled) throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      this.team.b(enabled);
   }

   public NameTagVisibility getNameTagVisibility() throws IllegalArgumentException {
      CraftScoreboard scoreboard = this.checkState();
      return notchToBukkit(this.team.j());
   }

   public void setNameTagVisibility(NameTagVisibility visibility) throws IllegalArgumentException {
      CraftScoreboard scoreboard = this.checkState();
      this.team.a(bukkitToNotch(visibility));
   }

   public Set<OfflinePlayer> getPlayers() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      Builder<OfflinePlayer> players = ImmutableSet.builder();

      for(String playerName : this.team.g()) {
         players.add(Bukkit.getOfflinePlayer(playerName));
      }

      return players.build();
   }

   public Set<String> getEntries() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      Builder<String> entries = ImmutableSet.builder();

      for(String playerName : this.team.g()) {
         entries.add(playerName);
      }

      return entries.build();
   }

   public int getSize() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      return this.team.g().size();
   }

   public void addPlayer(OfflinePlayer player) throws IllegalStateException, IllegalArgumentException {
      Validate.notNull(player, "OfflinePlayer cannot be null");
      this.addEntry(player.getName());
   }

   public void addEntry(String entry) throws IllegalStateException, IllegalArgumentException {
      Validate.notNull(entry, "Entry cannot be null");
      CraftScoreboard scoreboard = this.checkState();
      scoreboard.board.a(entry, this.team);
   }

   public boolean removePlayer(OfflinePlayer player) throws IllegalStateException, IllegalArgumentException {
      Validate.notNull(player, "OfflinePlayer cannot be null");
      return this.removeEntry(player.getName());
   }

   public boolean removeEntry(String entry) throws IllegalStateException, IllegalArgumentException {
      Validate.notNull(entry, "Entry cannot be null");
      CraftScoreboard scoreboard = this.checkState();
      if (!this.team.g().contains(entry)) {
         return false;
      } else {
         scoreboard.board.b(entry, this.team);
         return true;
      }
   }

   public boolean hasPlayer(OfflinePlayer player) throws IllegalArgumentException, IllegalStateException {
      Validate.notNull(player, "OfflinePlayer cannot be null");
      return this.hasEntry(player.getName());
   }

   public boolean hasEntry(String entry) throws IllegalArgumentException, IllegalStateException {
      Validate.notNull("Entry cannot be null");
      CraftScoreboard scoreboard = this.checkState();
      return this.team.g().contains(entry);
   }

   @Override
   public void unregister() throws IllegalStateException {
      CraftScoreboard scoreboard = this.checkState();
      scoreboard.board.d(this.team);
   }

   public OptionStatus getOption(Option option) throws IllegalStateException {
      this.checkState();
      switch(option) {
         case NAME_TAG_VISIBILITY:
            return OptionStatus.values()[this.team.j().ordinal()];
         case DEATH_MESSAGE_VISIBILITY:
            return OptionStatus.values()[this.team.k().ordinal()];
         case COLLISION_RULE:
            return OptionStatus.values()[this.team.l().ordinal()];
         default:
            throw new IllegalArgumentException("Unrecognised option " + option);
      }
   }

   public void setOption(Option option, OptionStatus status) throws IllegalStateException {
      this.checkState();
      switch(option) {
         case NAME_TAG_VISIBILITY:
            this.team.a(ScoreboardTeamBase.EnumNameTagVisibility.values()[status.ordinal()]);
            break;
         case DEATH_MESSAGE_VISIBILITY:
            this.team.b(ScoreboardTeamBase.EnumNameTagVisibility.values()[status.ordinal()]);
            break;
         case COLLISION_RULE:
            this.team.a(ScoreboardTeamBase.EnumTeamPush.values()[status.ordinal()]);
            break;
         default:
            throw new IllegalArgumentException("Unrecognised option " + option);
      }
   }

   public static ScoreboardTeamBase.EnumNameTagVisibility bukkitToNotch(NameTagVisibility visibility) {
      switch(visibility) {
         case ALWAYS:
            return ScoreboardTeamBase.EnumNameTagVisibility.a;
         case NEVER:
            return ScoreboardTeamBase.EnumNameTagVisibility.b;
         case HIDE_FOR_OTHER_TEAMS:
            return ScoreboardTeamBase.EnumNameTagVisibility.c;
         case HIDE_FOR_OWN_TEAM:
            return ScoreboardTeamBase.EnumNameTagVisibility.d;
         default:
            throw new IllegalArgumentException("Unknown visibility level " + visibility);
      }
   }

   public static NameTagVisibility notchToBukkit(ScoreboardTeamBase.EnumNameTagVisibility visibility) {
      switch(visibility) {
         case a:
            return NameTagVisibility.ALWAYS;
         case b:
            return NameTagVisibility.NEVER;
         case c:
            return NameTagVisibility.HIDE_FOR_OTHER_TEAMS;
         case d:
            return NameTagVisibility.HIDE_FOR_OWN_TEAM;
         default:
            throw new IllegalArgumentException("Unknown visibility level " + visibility);
      }
   }

   @Override
   CraftScoreboard checkState() throws IllegalStateException {
      if (this.getScoreboard().board.f(this.team.b()) == null) {
         throw new IllegalStateException("Unregistered scoreboard component");
      } else {
         return this.getScoreboard();
      }
   }

   @Override
   public int hashCode() {
      int hash = 7;
      return 43 * hash + (this.team != null ? this.team.hashCode() : 0);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         CraftTeam other = (CraftTeam)obj;
         return this.team == other.team || this.team != null && this.team.equals(other.team);
      }
   }
}
