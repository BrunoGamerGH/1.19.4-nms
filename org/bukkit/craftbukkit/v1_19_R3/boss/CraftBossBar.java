package org.bukkit.craftbukkit.v1_19_R3.boss;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.network.protocol.game.PacketPlayOutBoss;
import net.minecraft.server.level.BossBattleServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.BossBattle;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

public class CraftBossBar implements BossBar {
   private final BossBattleServer handle;
   private Map<BarFlag, CraftBossBar.FlagContainer> flags;

   public CraftBossBar(String title, BarColor color, BarStyle style, BarFlag... flags) {
      this.handle = new BossBattleServer(CraftChatMessage.fromString(title, true)[0], this.convertColor(color), this.convertStyle(style));
      this.initialize();

      for(BarFlag flag : flags) {
         this.addFlag(flag);
      }

      this.setColor(color);
      this.setStyle(style);
   }

   public CraftBossBar(BossBattleServer bossBattleServer) {
      this.handle = bossBattleServer;
      this.initialize();
   }

   private void initialize() {
      this.flags = new HashMap<>();
      this.flags.put(BarFlag.DARKEN_SKY, new CraftBossBar.FlagContainer(this.handle::n, this.handle::a));
      this.flags.put(BarFlag.PLAY_BOSS_MUSIC, new CraftBossBar.FlagContainer(this.handle::o, this.handle::b));
      this.flags.put(BarFlag.CREATE_FOG, new CraftBossBar.FlagContainer(this.handle::p, this.handle::c));
   }

   private BarColor convertColor(BossBattle.BarColor color) {
      BarColor bukkitColor = BarColor.valueOf(color.name());
      return bukkitColor == null ? BarColor.WHITE : bukkitColor;
   }

   private BossBattle.BarColor convertColor(BarColor color) {
      BossBattle.BarColor nmsColor = BossBattle.BarColor.valueOf(color.name());
      return nmsColor == null ? BossBattle.BarColor.g : nmsColor;
   }

   private BossBattle.BarStyle convertStyle(BarStyle style) {
      switch(style) {
         case SOLID:
         default:
            return BossBattle.BarStyle.a;
         case SEGMENTED_6:
            return BossBattle.BarStyle.b;
         case SEGMENTED_10:
            return BossBattle.BarStyle.c;
         case SEGMENTED_12:
            return BossBattle.BarStyle.d;
         case SEGMENTED_20:
            return BossBattle.BarStyle.e;
      }
   }

   private BarStyle convertStyle(BossBattle.BarStyle style) {
      switch(style) {
         case a:
         default:
            return BarStyle.SOLID;
         case b:
            return BarStyle.SEGMENTED_6;
         case c:
            return BarStyle.SEGMENTED_10;
         case d:
            return BarStyle.SEGMENTED_12;
         case e:
            return BarStyle.SEGMENTED_20;
      }
   }

   public String getTitle() {
      return CraftChatMessage.fromComponent(this.handle.a);
   }

   public void setTitle(String title) {
      this.handle.a = CraftChatMessage.fromString(title, true)[0];
      this.handle.a(PacketPlayOutBoss::c);
   }

   public BarColor getColor() {
      return this.convertColor(this.handle.c);
   }

   public void setColor(BarColor color) {
      this.handle.c = this.convertColor(color);
      this.handle.a(PacketPlayOutBoss::d);
   }

   public BarStyle getStyle() {
      return this.convertStyle(this.handle.d);
   }

   public void setStyle(BarStyle style) {
      this.handle.d = this.convertStyle(style);
      this.handle.a(PacketPlayOutBoss::d);
   }

   public void addFlag(BarFlag flag) {
      CraftBossBar.FlagContainer flagContainer = this.flags.get(flag);
      if (flagContainer != null) {
         flagContainer.set.accept(true);
      }
   }

   public void removeFlag(BarFlag flag) {
      CraftBossBar.FlagContainer flagContainer = this.flags.get(flag);
      if (flagContainer != null) {
         flagContainer.set.accept(false);
      }
   }

   public boolean hasFlag(BarFlag flag) {
      CraftBossBar.FlagContainer flagContainer = this.flags.get(flag);
      return flagContainer != null ? flagContainer.get.get() : false;
   }

   public void setProgress(double progress) {
      Preconditions.checkArgument(progress >= 0.0 && progress <= 1.0, "Progress must be between 0.0 and 1.0 (%s)", progress);
      this.handle.a((float)progress);
   }

   public double getProgress() {
      return (double)this.handle.k();
   }

   public void addPlayer(Player player) {
      Preconditions.checkArgument(player != null, "player == null");
      Preconditions.checkArgument(((CraftPlayer)player).getHandle().b != null, "player is not fully connected (wait for PlayerJoinEvent)");
      this.handle.a(((CraftPlayer)player).getHandle());
   }

   public void removePlayer(Player player) {
      Preconditions.checkArgument(player != null, "player == null");
      this.handle.b(((CraftPlayer)player).getHandle());
   }

   public List<Player> getPlayers() {
      Builder<Player> players = ImmutableList.builder();

      for(EntityPlayer p : this.handle.h()) {
         players.add(p.getBukkitEntity());
      }

      return players.build();
   }

   public void setVisible(boolean visible) {
      this.handle.d(visible);
   }

   public boolean isVisible() {
      return this.handle.j;
   }

   public void show() {
      this.handle.d(true);
   }

   public void hide() {
      this.handle.d(false);
   }

   public void removeAll() {
      for(Player player : this.getPlayers()) {
         this.removePlayer(player);
      }
   }

   public BossBattleServer getHandle() {
      return this.handle;
   }

   private final class FlagContainer {
      private Supplier<Boolean> get;
      private Consumer<Boolean> set;

      private FlagContainer(Supplier<Boolean> get, Consumer<Boolean> set) {
         this.get = get;
         this.set = set;
      }
   }
}
