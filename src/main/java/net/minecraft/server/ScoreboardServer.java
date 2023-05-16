package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardScore;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.scores.PersistentScoreboard;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.ScoreboardScore;
import net.minecraft.world.scores.ScoreboardTeam;

public class ScoreboardServer extends Scoreboard {
   private final MinecraftServer g;
   private final Set<ScoreboardObjective> h = Sets.newHashSet();
   private final List<Runnable> i = Lists.newArrayList();

   public ScoreboardServer(MinecraftServer minecraftserver) {
      this.g = minecraftserver;
   }

   @Override
   public void a(ScoreboardScore scoreboardscore) {
      super.a(scoreboardscore);
      if (this.h.contains(scoreboardscore.d())) {
         this.broadcastAll(new PacketPlayOutScoreboardScore(ScoreboardServer.Action.a, scoreboardscore.d().b(), scoreboardscore.e(), scoreboardscore.b()));
      }

      this.a();
   }

   @Override
   public void a(String s) {
      super.a(s);
      this.broadcastAll(new PacketPlayOutScoreboardScore(ScoreboardServer.Action.b, null, s, 0));
      this.a();
   }

   @Override
   public void a(String s, ScoreboardObjective scoreboardobjective) {
      super.a(s, scoreboardobjective);
      if (this.h.contains(scoreboardobjective)) {
         this.broadcastAll(new PacketPlayOutScoreboardScore(ScoreboardServer.Action.b, scoreboardobjective.b(), s, 0));
      }

      this.a();
   }

   @Override
   public void a(int i, @Nullable ScoreboardObjective scoreboardobjective) {
      ScoreboardObjective scoreboardobjective1 = this.a(i);
      super.a(i, scoreboardobjective);
      if (scoreboardobjective1 != scoreboardobjective && scoreboardobjective1 != null) {
         if (this.h(scoreboardobjective1) > 0) {
            this.broadcastAll(new PacketPlayOutScoreboardDisplayObjective(i, scoreboardobjective));
         } else {
            this.g(scoreboardobjective1);
         }
      }

      if (scoreboardobjective != null) {
         if (this.h.contains(scoreboardobjective)) {
            this.broadcastAll(new PacketPlayOutScoreboardDisplayObjective(i, scoreboardobjective));
         } else {
            this.e(scoreboardobjective);
         }
      }

      this.a();
   }

   @Override
   public boolean a(String s, ScoreboardTeam scoreboardteam) {
      if (super.a(s, scoreboardteam)) {
         this.broadcastAll(PacketPlayOutScoreboardTeam.a(scoreboardteam, s, PacketPlayOutScoreboardTeam.a.a));
         this.a();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void b(String s, ScoreboardTeam scoreboardteam) {
      super.b(s, scoreboardteam);
      this.broadcastAll(PacketPlayOutScoreboardTeam.a(scoreboardteam, s, PacketPlayOutScoreboardTeam.a.b));
      this.a();
   }

   @Override
   public void a(ScoreboardObjective scoreboardobjective) {
      super.a(scoreboardobjective);
      this.a();
   }

   @Override
   public void b(ScoreboardObjective scoreboardobjective) {
      super.b(scoreboardobjective);
      if (this.h.contains(scoreboardobjective)) {
         this.broadcastAll(new PacketPlayOutScoreboardObjective(scoreboardobjective, 2));
      }

      this.a();
   }

   @Override
   public void c(ScoreboardObjective scoreboardobjective) {
      super.c(scoreboardobjective);
      if (this.h.contains(scoreboardobjective)) {
         this.g(scoreboardobjective);
      }

      this.a();
   }

   @Override
   public void a(ScoreboardTeam scoreboardteam) {
      super.a(scoreboardteam);
      this.broadcastAll(PacketPlayOutScoreboardTeam.a(scoreboardteam, true));
      this.a();
   }

   @Override
   public void b(ScoreboardTeam scoreboardteam) {
      super.b(scoreboardteam);
      this.broadcastAll(PacketPlayOutScoreboardTeam.a(scoreboardteam, false));
      this.a();
   }

   @Override
   public void c(ScoreboardTeam scoreboardteam) {
      super.c(scoreboardteam);
      this.broadcastAll(PacketPlayOutScoreboardTeam.a(scoreboardteam));
      this.a();
   }

   public void a(Runnable runnable) {
      this.i.add(runnable);
   }

   protected void a() {
      for(Runnable runnable : this.i) {
         runnable.run();
      }
   }

   public List<Packet<?>> d(ScoreboardObjective scoreboardobjective) {
      List<Packet<?>> list = Lists.newArrayList();
      list.add(new PacketPlayOutScoreboardObjective(scoreboardobjective, 0));

      for(int i = 0; i < 19; ++i) {
         if (this.a(i) == scoreboardobjective) {
            list.add(new PacketPlayOutScoreboardDisplayObjective(i, scoreboardobjective));
         }
      }

      for(ScoreboardScore scoreboardscore : this.i(scoreboardobjective)) {
         list.add(new PacketPlayOutScoreboardScore(ScoreboardServer.Action.a, scoreboardscore.d().b(), scoreboardscore.e(), scoreboardscore.b()));
      }

      return list;
   }

   public void e(ScoreboardObjective scoreboardobjective) {
      List<Packet<?>> list = this.d(scoreboardobjective);

      for(EntityPlayer entityplayer : this.g.ac().t()) {
         if (entityplayer.getBukkitEntity().getScoreboard().getHandle() == this) {
            for(Packet<?> packet : list) {
               entityplayer.b.a(packet);
            }
         }
      }

      this.h.add(scoreboardobjective);
   }

   public List<Packet<?>> f(ScoreboardObjective scoreboardobjective) {
      List<Packet<?>> list = Lists.newArrayList();
      list.add(new PacketPlayOutScoreboardObjective(scoreboardobjective, 1));

      for(int i = 0; i < 19; ++i) {
         if (this.a(i) == scoreboardobjective) {
            list.add(new PacketPlayOutScoreboardDisplayObjective(i, scoreboardobjective));
         }
      }

      return list;
   }

   public void g(ScoreboardObjective scoreboardobjective) {
      List<Packet<?>> list = this.f(scoreboardobjective);

      for(EntityPlayer entityplayer : this.g.ac().t()) {
         if (entityplayer.getBukkitEntity().getScoreboard().getHandle() == this) {
            for(Packet<?> packet : list) {
               entityplayer.b.a(packet);
            }
         }
      }

      this.h.remove(scoreboardobjective);
   }

   public int h(ScoreboardObjective scoreboardobjective) {
      int i = 0;

      for(int j = 0; j < 19; ++j) {
         if (this.a(j) == scoreboardobjective) {
            ++i;
         }
      }

      return i;
   }

   public PersistentScoreboard b() {
      PersistentScoreboard persistentscoreboard = new PersistentScoreboard(this);
      this.a(persistentscoreboard::b);
      return persistentscoreboard;
   }

   public PersistentScoreboard a(NBTTagCompound nbttagcompound) {
      return this.b().b(nbttagcompound);
   }

   private void broadcastAll(Packet packet) {
      for(EntityPlayer entityplayer : this.g.ac().k) {
         if (entityplayer.getBukkitEntity().getScoreboard().getHandle() == this) {
            entityplayer.b.a(packet);
         }
      }
   }

   public static enum Action {
      a,
      b;
   }
}
